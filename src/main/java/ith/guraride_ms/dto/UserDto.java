package ith.guraride_ms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotEmpty(message = "First Name should not be empty")
    private String firstName;
    @NotEmpty(message = "Last Name should not be empty")
    private String lastName;
    @NotEmpty(message = "National ID should not be empty")
    @Pattern(regexp="(^$|[0-9]{16})", message = "National ID have 16 digits")
    private String nationalId;
    @NotEmpty(message = "Email should not be empty")
    private String email;
    @NotEmpty(message = "Phone Number should not be empty")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Phone number should have 10 digits")
    private String phoneNumber;
    @NotEmpty(message = "Address should not be empty")
    private String address;
    @NotEmpty(message = "Status should not be empty")
    private String status;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
    private LocalDateTime createdOn;
    private LocalDateTime updateOn;
    private List<RentalDto> rentals;
}
