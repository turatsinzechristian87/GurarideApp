package ith.guraride_ms.repository;

import ith.guraride_ms.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("SELECT u from User u WHERE u.status LIKE CONCAT('%', :status, '%')")
    List<User> findRentersByStatus(String status);
    @Query("SELECT u from User u WHERE u.status LIKE CONCAT('%', :status, '%')")
    List<User> findWorkersByStatus(String status);

}
