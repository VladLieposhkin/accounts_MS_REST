package vl.example.accountscommon.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import vl.example.accountscommon.dto.enums.Status;

import java.time.LocalDateTime;

@Data
@Builder
public class CoinDTO {

    private Integer id;

    @NotEmpty(message = "Field Code should not be empty")
    @Size(min = 1, max = 5, message = "Field Code should be between 2 and 5 characters")
    private String code;

    @NotEmpty(message = "Field Name should not be empty")
    @Size(min = 3, max = 50, message = "Field Name should be between 2 and 50 characters")
    private String name;

    private Float price;

    private Float change1h;

    private Float change24h;

    private Float change7d;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Status status;
}
