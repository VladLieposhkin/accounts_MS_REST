package vl.example.accountsservice.service;

import vl.example.accountscommon.dto.CoinDTO;
import vl.example.accountsservice.entity.Coin;

import java.util.List;

public interface CoinService {
    List<CoinDTO> findAll();
    CoinDTO findOne(Integer coinId);
    CoinDTO create(CoinDTO coinDTO);
    CoinDTO update(CoinDTO coinDTO, Integer coinId);
    void delete(Integer coinId);
    boolean checkById(Integer coinId);
    boolean checkByCode(String code, Integer coinId);
    boolean checkByName(String name, Integer coinId);
    Coin updateByCode(Coin coin);
    Integer updateByExternalData(List<CoinDTO> coinDTOs);
}
