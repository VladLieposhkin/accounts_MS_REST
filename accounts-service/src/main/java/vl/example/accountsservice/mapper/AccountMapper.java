package vl.example.accountsservice.mapper;

import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountsservice.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vl.example.accountscommon.dto.enums.Status;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final ClientMapper clientMapper;
    private final CoinMapper coinMapper;

//  Read Account case
    public AccountDTO toDTO(Account entity) {

        return AccountDTO.builder()
                .id(entity.getId())
                .client(clientMapper.toDTO(entity.getClient()))
                .coin(coinMapper.toDTO(entity.getCoin()))
                .number(entity.getNumber())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .status(entity.getStatus())
                .build();
    }

//  Create Account case
    public Account fromDTO(AccountDTO object) {

        return Account.builder()
                .id(object.getId())
                .client(clientMapper.fromDTO(object.getClient()))
                .coin(coinMapper.fromDTO(object.getCoin()))
                .number(object.getNumber())
                .quantity(object.getQuantity())
                .price(object.getPrice())
                .amount(object.getAmount())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
    }

//  Update Account case
    public Account fromDTO(AccountDTO object, Account entity) {

//      id - skip;
        entity.setClient(clientMapper.fromDTO(object.getClient()));
        entity.setCoin(coinMapper.fromDTO(object.getCoin()));
        entity.setNumber(object.getNumber());
        entity.setQuantity(object.getQuantity());
        entity.setPrice(object.getPrice());
        entity.setAmount(object.getAmount());
//      createdAt - skip;
        entity.setUpdatedAt(LocalDateTime.now());
//      Status - skip;
        return entity;
    }
}
