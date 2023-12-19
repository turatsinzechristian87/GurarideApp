package ith.guraride_ms.implementation;

import ith.guraride_ms.dto.BikeDto;
import ith.guraride_ms.dto.RentalDto;
import ith.guraride_ms.models.Bike;
import ith.guraride_ms.models.Rental;
import ith.guraride_ms.service.RentalService;
import ith.guraride_ms.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.stream.Collectors;

import static ith.guraride_ms.mapper.BikeMapper.mapToBikeDto;
import static ith.guraride_ms.mapper.RentalMapper.mapToRentalDto;
import static ith.guraride_ms.mapper.RentalMapper.mapToRental;

@Service
public class RentalImp implements RentalService {
    private RentalRepository rentalRepository;
    //private PasswordEncoder passwordEncoder;
    @Autowired
    public RentalImp(RentalRepository rentalRepository)
    {

        this.rentalRepository = rentalRepository;
    }
    @Override
    public List<RentalDto> findAllRental() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream().map((rental) -> mapToRentalDto(rental)).collect(Collectors.toList());
    }

//    @Override
//    public Page<RentalDto> findPage(int pageNumber) {
//        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
//        Page<Rental> rentalPage = rentalRepository.findAll(pageable);
//        return rentalPage.map((rental) -> mapToRentalDto(rental));
//    }
    @Override
    @Cacheable("rentals")
    public Rental saveRental(RentalDto rentalDto) {
        Rental rental = mapToRental(rentalDto);
        return rentalRepository.save(rental);
    }
    @Override
    public RentalDto findRentalById(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).get();
        return mapToRentalDto(rental);
    }

    @Override
    public void updateRental(RentalDto rentalDto) {
        Rental rental = mapToRental(rentalDto);
        rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(Long rentalId)
    {
        rentalRepository.deleteById(rentalId);
    }

    @Override
    public List<RentalDto> findUserRentedBikes(String email, String status) {
        List<Rental> rentals = rentalRepository.findUserRentedBikes(email, status);
        return rentals.stream().map(rental -> mapToRentalDto(rental)).collect(Collectors.toList());
    }


}
