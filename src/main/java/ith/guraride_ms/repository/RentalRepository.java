package ith.guraride_ms.repository;

import ith.guraride_ms.models.Rental;
import ith.guraride_ms.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long>{
    @Query("SELECT r FROM Rental r WHERE r.user.email LIKE CONCAT('%', :email, '%') AND r.status LIKE CONCAT('%', :status, '%')")
    List<Rental> findUserRentedBikes(String email, String status);
} 