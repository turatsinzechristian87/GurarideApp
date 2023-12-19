package ith.guraride_ms.mapper;

import ith.guraride_ms.dto.BikeDto;
import ith.guraride_ms.models.Bike;
public class BikeMapper {
    public static Bike mapToBike(BikeDto bike){
        Bike bikeDto = Bike.builder()
                .bikeId(bike.getBikeId())
                .model(bike.getModel())
                .brand(bike.getBrand())
                .status(bike.getStatus())
                .rentPrice(bike.getRentPrice())
                .createdOn(bike.getCreatedOn())
                .updateOn(bike.getUpdateOn())
                .build();
        return bikeDto;
    }
    public static BikeDto mapToBikeDto(Bike bike){
        BikeDto bikeDto = BikeDto.builder()
                .bikeId(bike.getBikeId())
                .model(bike.getModel())
                .brand(bike.getBrand())
                .status(bike.getStatus())
                .rentPrice(bike.getRentPrice())
                .createdOn(bike.getCreatedOn())
                .updateOn(bike.getUpdateOn())
                .build();
        return bikeDto;
    }
}
