package vl.example.accountscommon.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import vl.example.accountscommon.dto.enums.Status;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountDTO {

    private Integer id;

    private ClientDTO client;

    private CoinDTO coin;

    @NotEmpty(message = "Field Number should not be empty")
    @Size(min = 3, max = 50, message = "Field Number should be between 2 and 50 characters")
    private String number;

    private Float quantity;

    private Float price;

    private Float amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Status status;
}
