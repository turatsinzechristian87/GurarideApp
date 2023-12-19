package ith.guraride_ms.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BikeDto {

    private Long bikeId;
    @NotEmpty(message = "Model should not be empty")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Model should only contain letters and numbers")
    private String model;
    @NotEmpty(message = "Brand should not be empty")
    private String brand;
    @NotEmpty(message = "Status should not be empty")
    private String status;
    @Min(value = 0, message = "Rent price must be positive")
    private Long rentPrice;
    private LocalDateTime createdOn;
    private LocalDateTime updateOn;
    @Valid
    private List<RentalDto> rentals;
}
