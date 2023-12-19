package ith.guraride_ms.service;

import ith.guraride_ms.dto.BikeDto;
import ith.guraride_ms.dto.RentalDto;
import ith.guraride_ms.dto.UserDto;
import ith.guraride_ms.models.Rental;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RentalService {
    List<RentalDto> findAllRental();

    //Page<RentalDto> findPage(int pageNumber);
    Rental saveRental(RentalDto rentalDto);
    RentalDto findRentalById(Long RentalId);
    void updateRental(RentalDto rental);
    void deleteRental(Long rentalId);
    List<RentalDto> findUserRentedBikes(String email, String status);
}
