package vl.example.accountsservice.service.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountsservice.entity.Account;
import vl.example.accountscommon.dto.enums.Status;
import vl.example.accountsservice.exception.CustomNotFoundException;
import vl.example.accountsservice.mapper.AccountMapper;
import vl.example.accountsservice.repository.AccountRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class AccountServiceImplTests {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl service;

    @Test
    void givenAccountToCreateDTO_whenCreate_thenAccountIsCreated () {
        // given
        AccountDTO accountToCreateDTO = AccountDTO.builder()
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        Account accountToCreate = Account.builder()
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        Account createdAccount = Account.builder()
                .id(1)
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        AccountDTO createdAccountDTO = AccountDTO.builder()
                .id(1)
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(accountMapper.fromDTO(any(AccountDTO.class)))
                .willReturn(accountToCreate);
        BDDMockito.given(accountRepository.save(any(Account.class)))
                .willReturn(createdAccount);
        BDDMockito.given(accountMapper.toDTO(any(Account.class)))
                .willReturn(createdAccountDTO);
        // when
        AccountDTO result = service.create(accountToCreateDTO);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void givenCorrectId_whenFindOne_thenAccountIsFound() {
        // given
        Integer accountId = 1;
        Account foundAccount = Account.builder()
                .id(1)
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        AccountDTO foundAccountDTO = AccountDTO.builder()
                .id(1)
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(accountRepository.findById(anyInt()))
                .willReturn(Optional.of(foundAccount));
        BDDMockito.given(accountMapper.toDTO(any(Account.class)))
                .willReturn(foundAccountDTO);
        // when
        AccountDTO result = service.findOne(accountId);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(accountId);
    }

    @Test
    void givenIncorrectId_whenFindOne_thenExceptionIsThrown() {
        // given
        Integer accountId = -1;
        BDDMockito.given(accountRepository.findById(anyInt()))
                .willThrow(new CustomNotFoundException("Account with ID" + accountId + "not found"));
        // when
        // then
        assertThrows(CustomNotFoundException.class, () -> service.findOne(accountId));
    }

    @Test
    void givenCorrectIdAndAccountToUpdateDTO_whenUpdate_thenAccountIsUpdated() {
        // given
        Integer accountId = 1;
        AccountDTO accountToUpdateDTO = AccountDTO.builder()
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        Account accountToUpdate = Account.builder()
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        Account updatedAccount = Account.builder()
                .id(1)
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        AccountDTO updatedAccountDTO = AccountDTO.builder()
                .id(1)
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(accountRepository.findById(anyInt()))
                .willReturn(Optional.of(accountToUpdate));
        BDDMockito.given(accountMapper.fromDTO(any(AccountDTO.class), any(Account.class)))
                .willReturn(accountToUpdate);
        BDDMockito.given(accountRepository.saveAndFlush(any(Account.class)))
                .willReturn(updatedAccount);
        BDDMockito.given(accountMapper.toDTO(any(Account.class)))
                .willReturn(updatedAccountDTO);
        // when
        AccountDTO result = service.update(accountToUpdateDTO, accountId);
        // then
        assertThat(result).isNotNull();
        verify(accountRepository, times(1)).saveAndFlush(accountToUpdate);
    }

    @Test
    void givenIncorrectIdAndAccountToUpdateDTO_whenUpdate_thenExceptionIsThrown() {
        // given
        Integer accountId = -1;
        AccountDTO accountToUpdateDTO = AccountDTO.builder()
                .id(1)
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(accountRepository.findById(anyInt()))
                .willThrow(new CustomNotFoundException("Account with ID" + accountId + "not found"));
        // when
        // then
        assertThrows(CustomNotFoundException.class, () -> service.update(accountToUpdateDTO, accountId));
        verify(accountRepository, never()).saveAndFlush(any(Account.class));
    }

}