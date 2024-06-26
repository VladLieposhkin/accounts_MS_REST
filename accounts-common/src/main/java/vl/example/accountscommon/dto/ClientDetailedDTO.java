package vl.example.accountscommon.dto;

import lombok.Builder;
import lombok.Data;
import vl.example.accountscommon.dto.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ClientDetailedDTO {

    private Integer id;

    private String name;

    private String email;

    private String password;

    private List<AccountDTO> accounts;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Status status;
}
