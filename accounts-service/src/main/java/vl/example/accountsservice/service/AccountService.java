package vl.example.accountsservice.service;

import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountsservice.entity.Coin;

import java.util.List;

public interface AccountService {

    AccountDTO findOne(Integer accountId);
    List<AccountDTO> findAll();
    AccountDTO create(AccountDTO accountDTO);
    AccountDTO update(AccountDTO accountDTO, Integer accountId);
    void delete(Integer accountId);
    boolean checkByNumber(String accountNumber, Integer accountId);
    void updateByCoin(Coin coin);
    List<AccountDTO> getAccountsData(Integer threshold);
}
