package ith.guraride_ms.service;

import ith.guraride_ms.dto.BikeDto;
import ith.guraride_ms.dto.UserDto;
import ith.guraride_ms.models.Bike;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BikeService {
    List<BikeDto> findAllBike();
    Page<BikeDto> findPage(int pageNumber);
    Bike saveBike(BikeDto bikeDto);
    BikeDto findBikeById(Long bikeId);
    void updateBike(BikeDto bike);
    void deleteBike(Long bikeId);
}
