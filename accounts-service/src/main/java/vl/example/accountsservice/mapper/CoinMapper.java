package vl.example.accountsservice.mapper;

import vl.example.accountscommon.dto.CoinDTO;
import vl.example.accountsservice.entity.Coin;
import vl.example.accountscommon.dto.enums.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CoinMapper {

    //  Read Coin case
    public CoinDTO toDTO(Coin entity) {

        return CoinDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .price(entity.getPrice())
                .change1h(entity.getChange1h())
                .change24h(entity.getChange24h())
                .change7d(entity.getChange7d())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .status(entity.getStatus())
                .build();
    }

    //  Create Coin case
    public Coin fromDTO(CoinDTO object) {

        return Coin.builder()
                .id(object.getId())
                .name(object.getName())
                .code(object.getCode())
                .price(object.getPrice())
                .change1h(object.getChange1h())
                .change24h(object.getChange24h())
                .change7d(object.getChange7d())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
    }

    //  Update Coin case
    public Coin fromDTO(CoinDTO object, Coin entity) {

//      id - skip;
        entity.setName(object.getName());
        entity.setCode(object.getCode());
        entity.setPrice(object.getPrice());
        entity.setChange1h(object.getChange1h());
        entity.setChange24h(object.getChange24h());
        entity.setChange7d(object.getChange7d());
//      createdAt - skip;
        entity.setUpdatedAt(LocalDateTime.now());
//      Status - skip;
        return entity;
    }
}
