package ith.guraride_ms.mapper;

import ith.guraride_ms.dto.RentalDto;
import ith.guraride_ms.models.Rental;

public class RentalMapper {
    public static Rental mapToRental(RentalDto rentalDto){
       return Rental.builder()
                .rental_id(rentalDto.getRental_id())
                .startTime(rentalDto.getStartTime())
                .endTime(rentalDto.getEndTime())
                .isReturned(rentalDto.getIsReturned())
                .status(rentalDto.getStatus())
                .createdOn(rentalDto.getCreatedOn())
                .updatedOn(rentalDto.getUpdatedOn())
                .user(rentalDto.getUser())
                .bike(rentalDto.getBike())
                .build();
    }
    public static RentalDto mapToRentalDto(Rental rental){
       return RentalDto.builder()
                .rental_id(rental.getRental_id())
                .startTime(rental.getStartTime())
                .endTime(rental.getEndTime())
                .isReturned(rental.getIsReturned())
                .status(rental.getStatus())
                .createdOn(rental.getCreatedOn())
                .updatedOn(rental.getUpdatedOn())
                .user(rental.getUser())
                .bike(rental.getBike())
                .build();
    }
}
