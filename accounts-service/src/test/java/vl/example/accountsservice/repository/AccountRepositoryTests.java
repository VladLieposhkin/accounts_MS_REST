package vl.example.accountsservice.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vl.example.accountsservice.entity.Account;
import vl.example.accountsservice.entity.Client;
import vl.example.accountsservice.entity.Coin;
import vl.example.accountscommon.dto.enums.Status;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CoinRepository coinRepository;

    @Test
    void givenAccountToCreate_whenSave_thenAccountIsCreated() {
        //given
        Coin coin = Coin.builder()
                .code("100")
                .name("TEST_COIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        coinRepository.save(coin);

        Client client = Client.builder()
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        clientRepository.save(client);

        Account accountToCreate = Account.builder()
                .client(client)
                .coin(coin)
                .number("TEST_COIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        //when
        Account createdAccount = accountRepository.save(accountToCreate);

        //then
        assertThat(createdAccount).isNotNull();
        assertThat((createdAccount.getId())).isNotNull();
    }

    @Test
    public void givenAccountToUpdate_whenSave_thenAccountIsUpdated() {
        //given
        Coin coin = Coin.builder()
                .code("100")
                .name("TEST_COIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        coinRepository.save(coin);

        Client client = Client.builder()
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        clientRepository.save(client);

        Account accountToCreate = Account.builder()
                .client(client)
                .coin(coin)
                .number("TEST_COIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        accountRepository.save(accountToCreate);

        Account accountToUpdate = accountRepository.findById(accountToCreate.getId()).orElseThrow(NoSuchElementException::new);
        String updatedNumber = "UPDATED_TEST_ACCOUNT";
        accountToUpdate.setNumber(updatedNumber);

        //when
        Account updatedAccount = accountRepository.save(accountToUpdate);

        //then
        assertThat(updatedAccount).isNotNull();
        assertThat(updatedAccount.getNumber()).isEqualTo(updatedNumber);
    }

    @Test
    public void givenAccountId_whenFindById_thenAccountIsFound() {
        //given
        Coin coin = Coin.builder()
                .code("100")
                .name("TEST_COIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        coinRepository.save(coin);

        Client client = Client.builder()
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        clientRepository.save(client);

        Account accountToCreate = Account.builder()
                .client(client)
                .coin(coin)
                .number("TEST_COIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        accountRepository.save(accountToCreate);
        Integer accountId = accountToCreate.getId();

        //when
        Optional<Account> foundAccount = accountRepository.findById(accountId);

        //then
        assertThat(foundAccount.isPresent()).isTrue();
        assertThat(foundAccount.get().getId()).isEqualTo(accountId);
    }
}
