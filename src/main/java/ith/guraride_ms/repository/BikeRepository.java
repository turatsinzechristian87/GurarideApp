package ith.guraride_ms.repository;

import ith.guraride_ms.models.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

 public interface BikeRepository extends JpaRepository<Bike, Long> {
}
