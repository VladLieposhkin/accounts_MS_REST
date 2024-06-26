package vl.example.accountsservice.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vl.example.accountsservice.entity.Coin;
import vl.example.accountscommon.dto.enums.Status;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class CoinRepositoryTests {

    @Autowired
    private CoinRepository coinRepository;

    @Test
    void givenCoinToCreate_whenSave_thenCoinIsCreated() {
        //given
        Coin coinToCreate = Coin.builder()
                .code("100")
                .name("TEST_COIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        coinRepository.save(coinToCreate);

        //when
        Coin createdCoin = coinRepository.save(coinToCreate);

        //then
        assertThat(createdCoin).isNotNull();
        assertThat((createdCoin.getId())).isNotNull();
    }

    @Test
    public void givenCoinToUpdate_whenSave_thenCoinIsUpdated() {
        //given
        Coin coinToCreate = Coin.builder()
                .code("100")
                .name("TEST_COIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        coinRepository.save(coinToCreate);

        Coin coinToUpdate = coinRepository.findById(coinToCreate.getId()).orElseThrow(NoSuchElementException::new);
        String updatedName = "UPDATED_TEST_COIN";
        coinToUpdate.setName(updatedName);

        //when
        Coin updatedCoin = coinRepository.save(coinToUpdate);

        //then
        assertThat(updatedCoin).isNotNull();
        assertThat(updatedCoin.getName()).isEqualTo(updatedName);
    }

    @Test
    public void givenCoinId_whenFindById_thenCoinIsFound() {
        //given
        Coin coinToCreate = Coin.builder()
                .code("100")
                .name("TEST_COIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        coinRepository.save(coinToCreate);
        Integer coinId = coinToCreate.getId();

        //when
        Optional<Coin> foundCoin = coinRepository.findById(coinId);

        //then
        assertThat(foundCoin.isPresent()).isTrue();
        assertThat(foundCoin.get().getId()).isEqualTo(coinId);
    }
}
