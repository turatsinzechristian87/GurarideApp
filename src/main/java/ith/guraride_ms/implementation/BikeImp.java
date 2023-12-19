package ith.guraride_ms.implementation;

import ith.guraride_ms.dto.BikeDto;
import ith.guraride_ms.dto.UserDto;
import ith.guraride_ms.models.Bike;
import ith.guraride_ms.models.User;
import ith.guraride_ms.service.BikeService;
import ith.guraride_ms.repository.BikeRepository;
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
import static ith.guraride_ms.mapper.BikeMapper.mapToBike;
import static ith.guraride_ms.mapper.UserMapper.mapToUserDto;

@Service
public class BikeImp implements BikeService {
    private BikeRepository bikeRepository;
    //private PasswordEncoder passwordEncoder;
    @Autowired
    public BikeImp(BikeRepository bikeRepository)
    {
        this.bikeRepository = bikeRepository;
    }
    @Override
    public List<BikeDto> findAllBike() {
        List<Bike> bikes = bikeRepository.findAll();
        return bikes.stream().map((bike) -> mapToBikeDto(bike)).collect(Collectors.toList());
    }
    @Override
    @Cacheable("bikes")
    public Page<BikeDto> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        Page<Bike> bikePage = bikeRepository.findAll(pageable);
        return bikePage.map((bike) -> mapToBikeDto(bike));
    }

    @Override
    public Bike saveBike(BikeDto bikeDto) {

        Bike bike = mapToBike(bikeDto);
        return bikeRepository.save(bike);
    }

    @Override
    public BikeDto findBikeById(Long bikeId) {
        Bike bike = bikeRepository.findById(bikeId).get();
        return mapToBikeDto(bike);
    }
    @Override
    public void updateBike(BikeDto bikeDto) {
    Bike bike = mapToBike(bikeDto);
    bikeRepository.save(bike);
    }

    @Override
    public void deleteBike(Long bikeId) {
        bikeRepository.deleteById(bikeId);
    }
}
