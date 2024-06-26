package vl.example.accountsservice.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountscommon.dto.enums.Status;
import vl.example.accountsservice.entity.Account;
import vl.example.accountsservice.entity.Coin;
import vl.example.accountsservice.exception.CustomBadRequestException;
import vl.example.accountsservice.exception.CustomNotFoundException;
import vl.example.accountsservice.mapper.AccountMapper;
import vl.example.accountsservice.repository.AccountRepository;
import vl.example.accountsservice.service.AccountService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final String NOT_FOUND = "Account not found. ID = ";

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    @Override
    public AccountDTO create(AccountDTO accountDTO) {

        return Optional.of(accountDTO)
                .map(accountMapper::fromDTO)
                .map(accountRepository::save)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new CustomBadRequestException("Can't create Coin", Collections.EMPTY_LIST));
    }

    @Override
    public AccountDTO findOne(Integer accountId) {

        return accountRepository.findById(accountId)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new CustomNotFoundException(NOT_FOUND + accountId));
    }

    @Override
    public List<AccountDTO> findAll() {

        return accountRepository.findAll().stream()
                .map(accountMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public AccountDTO update(AccountDTO accountDTO, Integer accountId) {

        return accountRepository.findById(accountId)
                .map(account -> accountMapper.fromDTO(accountDTO, account))
                .map(accountRepository::saveAndFlush)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new CustomNotFoundException(NOT_FOUND + accountId));
    }

    @Transactional
    @Override
    public void delete(Integer accountId) {

        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent()) {
            Account entity = account.get();
            entity.setStatus(Status.DELETED);
            accountRepository.saveAndFlush(entity);
        }
        else throw new CustomNotFoundException(NOT_FOUND + accountId);
    }

    @Override
    public boolean checkByNumber(String accountNumber, Integer accountId) {

        return accountRepository.checkByNumberAndId(accountNumber, accountId).isPresent();
    }

    @Transactional
    @Override
    public void updateByCoin(Coin coin) {

        accountRepository.updateAccountsByCoin(coin, coin.getPrice());
    }

    @Override
    public List<AccountDTO> getAccountsData(Integer threshold) {

        return accountRepository.getAccountsByChangedCoins(threshold).stream()
                .map(accountMapper::toDTO)
                .toList();
    }


}
