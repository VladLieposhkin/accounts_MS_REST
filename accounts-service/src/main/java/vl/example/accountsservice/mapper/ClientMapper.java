package vl.example.accountsservice.mapper;

import vl.example.accountscommon.dto.ClientDTO;
import vl.example.accountsservice.entity.Client;
import vl.example.accountscommon.dto.enums.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClientMapper {

//  Read Client case
    public ClientDTO toDTO(Client entity) {

        return ClientDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .status(entity.getStatus())
                .build();
    }

//  Create Client case
    public Client fromDTO(ClientDTO object) {

        return Client.builder()
                .id(object.getId())
                .name(object.getName())
                .email(object.getEmail())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
    }

//  Update Client case
    public Client fromDTO(ClientDTO object, Client entity) {

//      id - skip;
        entity.setName(object.getName());
        entity.setEmail(object.getEmail());
//      createdAt - skip;
        entity.setUpdatedAt(LocalDateTime.now());
//      Status - skip;
        return entity;
    }
}
