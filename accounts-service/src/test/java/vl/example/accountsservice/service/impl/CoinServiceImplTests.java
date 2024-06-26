package vl.example.accountsservice.service.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vl.example.accountscommon.dto.CoinDTO;
import vl.example.accountsservice.entity.Coin;
import vl.example.accountscommon.dto.enums.Status;
import vl.example.accountsservice.exception.CustomNotFoundException;
import vl.example.accountsservice.mapper.CoinMapper;
import vl.example.accountsservice.repository.CoinRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class CoinServiceImplTests {

    @Mock
    private CoinRepository coinRepository;

    @Mock
    private CoinMapper coinMapper;

    @InjectMocks
    private CoinServiceImpl service;

    @Test
    public void givenCoinToCreateDTO_whenCreate_thenCoinIsCreated() {
        // given
        CoinDTO coinToCreateDTO = CoinDTO.builder()
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        Coin coinToCreate = Coin.builder()
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        Coin createdCoin = Coin.builder()
                .id(1)
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        CoinDTO createdCoinDTO = CoinDTO.builder()
                .id(1)
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(coinMapper.fromDTO(any(CoinDTO.class)))
                .willReturn(coinToCreate);
        BDDMockito.given(coinRepository.save(coinToCreate))
                .willReturn(createdCoin);
        BDDMockito.given(coinMapper.toDTO(any(Coin.class)))
                .willReturn(createdCoinDTO);
        // when
        CoinDTO result = service.create(coinToCreateDTO);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void givenCorrectId_whenFindOne_thenCoinIsFound() {
        // given
        Integer coinId = 1;
        Coin foundCoin = Coin.builder()
                .id(1)
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        CoinDTO foundCoinDTO = CoinDTO.builder()
                .id(1)
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(coinRepository.findById(anyInt()))
                .willReturn(Optional.of(foundCoin));
        BDDMockito.given(coinMapper.toDTO(foundCoin))
                .willReturn(foundCoinDTO);
        // when
        CoinDTO result = service.findOne(coinId);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(coinId);
    }

    @Test
    void givenIncorrectId_whenFindOne_thenExceptionIsThrown() {
        // given
        Integer coinId = -1;
        BDDMockito.given(coinRepository.findById(anyInt()))
                .willThrow(new CustomNotFoundException("Coin with ID " + coinId + "not found"));
        // when
        // then
        assertThrows(CustomNotFoundException.class, () -> service.findOne(coinId));
    }

    @Test
    void givenCorrectIdAndCoinToUpdateDTO_whenUpdate_thenCoinIsUpdated() {
        // given
        Integer coinId = 1;

        CoinDTO coinToUpdateDTO = CoinDTO.builder()
                .id(1)
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        Coin coinToUpdate = Coin.builder()
                .id(1)
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        CoinDTO updatedCoinDTO = CoinDTO.builder()
                .id(1)
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(coinRepository.findById(anyInt()))
                .willReturn(Optional.of(coinToUpdate));
        BDDMockito.given(coinMapper.fromDTO(any(CoinDTO.class), any(Coin.class)))
                .willReturn(coinToUpdate);
        BDDMockito.given(coinRepository.saveAndFlush(any(Coin.class)))
                .willReturn(coinToUpdate);
        BDDMockito.given(coinMapper.toDTO(any(Coin.class)))
                .willReturn(updatedCoinDTO);
        // when
        CoinDTO result = service.update(coinToUpdateDTO, coinId);
        // then
        assertThat(result).isNotNull();
        verify(coinRepository, times(1)).saveAndFlush(coinToUpdate);
    }

    @Test
    void givenIncorrectIdAndCoinToUpdateDTO_whenUpdate_thenExceptionIsThrown() {
        // given
        Integer coinId = -1;
        CoinDTO coinToUpdateDTO = CoinDTO.builder()
                .id(1)
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(coinRepository.findById(anyInt()))
                .willThrow(new CustomNotFoundException("Coin with ID " + coinId + "not found"));
        // when
        // then
        assertThrows(CustomNotFoundException.class, () -> service.update(coinToUpdateDTO, coinId));
        verify(coinRepository, never()).saveAndFlush(any(Coin.class));
    }
}