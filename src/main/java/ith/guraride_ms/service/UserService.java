package ith.guraride_ms.service;

import ith.guraride_ms.dto.UserDto;
import ith.guraride_ms.models.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUser();

    Page<UserDto> findPage(int pageNumber);

    List<UserDto> findRentersByStatus(String status);
    List<UserDto> findWorkersByStatus(String status);

    User saveUser(UserDto userDto);

    UserDto findUserById(Long userId);

    void updateUser(UserDto user);

    void deleteUser(Long userId);
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
