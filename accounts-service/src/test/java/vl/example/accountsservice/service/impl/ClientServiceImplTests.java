package vl.example.accountsservice.service.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vl.example.accountscommon.dto.ClientDTO;
import vl.example.accountsservice.entity.Client;
import vl.example.accountscommon.dto.enums.Status;
import vl.example.accountsservice.exception.CustomNotFoundException;
import vl.example.accountsservice.mapper.ClientDetailedMapper;
import vl.example.accountsservice.mapper.ClientMapper;
import vl.example.accountsservice.repository.ClientRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class ClientServiceImplTests {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ClientDetailedMapper clientDetailedMapper;

    @InjectMocks
    private ClientServiceImpl service;

    @Test
    void givenClientToCreateDTO_whenCreate_thenClientIsCreated() {
        // given
        ClientDTO clientToCreateDTO = ClientDTO.builder()
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        Client clientToCreate = Client.builder()
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        Client createdClient = Client.builder()
                .id(1)
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        ClientDTO createdClientDTO = ClientDTO.builder()
                .id(1)
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();

        BDDMockito.given(clientMapper.fromDTO(any(ClientDTO.class)))
                .willReturn(clientToCreate);
        BDDMockito.given((clientRepository.save(any(Client.class))))
                .willReturn(createdClient);
        BDDMockito.given(clientMapper.toDTO(any(Client.class)))
                .willReturn(createdClientDTO);
        // when
        ClientDTO result = service.create(clientToCreateDTO);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void givenCorrectId_whenFindOne_thenClientIsFound() {
        // given
        Integer clientId = 1;
        Client foundClient = Client.builder()
                .id(1)
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        ClientDTO foundClientDTO = ClientDTO.builder()
                .id(1)
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(clientMapper.toDTO(any(Client.class)))
                .willReturn(foundClientDTO);
        BDDMockito.given((clientRepository.findById(anyInt())))
                .willReturn(Optional.of(foundClient));
        // when
        ClientDTO result = service.findOne(clientId);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(clientId);
    }

    @Test
    void givenIncorrectId_whenFindById_thenExceptionIsThrown() {
        // given
        Integer clientId = 1;
        BDDMockito.given(clientRepository.findById(anyInt()))
                .willThrow(new CustomNotFoundException("Client with ID " + clientId + "not found"));
        // when
        // then
        assertThrows(CustomNotFoundException.class, () -> service.findOne(clientId));
    }

    @Test
    void givenCorrectIdAndClientToUpdateDTO_whenUpdate_thenClientIsUpdated() {
        // given
        Integer clientId = 1;
        ClientDTO clientToUpdateDTO = ClientDTO.builder()
                .id(1)
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        Client clientToUpdate = Client.builder()
                .id(1)
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        Client updatedClient = Client.builder()
                .id(1)
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        ClientDTO updatedClientDTO = ClientDTO.builder()
                .id(1)
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(clientRepository.findById(anyInt()))
                .willReturn(Optional.of(clientToUpdate));
        BDDMockito.given(clientMapper.fromDTO(any(ClientDTO.class), any(Client.class)))
                .willReturn(clientToUpdate);
        BDDMockito.given((clientRepository.saveAndFlush(any(Client.class))))
                .willReturn(updatedClient);
        BDDMockito.given(clientMapper.toDTO(any(Client.class)))
                .willReturn(updatedClientDTO);
        // when
        ClientDTO result = service.update(clientToUpdateDTO, clientId);
        // then
        assertThat(result).isNotNull();
        verify(clientRepository, times(1)).saveAndFlush(clientToUpdate);
    }

    @Test
    void givenIncorrectIdAndClientToUpdateDTO_whenUpdate_thenExceptionIsThrown() {
        // given
        Integer clientId = -1;
        ClientDTO clientToUpdateDTO = ClientDTO.builder()
                .id(1)
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.given(clientRepository.findById(anyInt()))
                .willThrow(new CustomNotFoundException("Client with ID \" + clientId + \"not found"));
        // when
        // then
        assertThrows(CustomNotFoundException.class, () -> service.update(clientToUpdateDTO, clientId));
        verify(clientRepository, never()).saveAndFlush(any(Client.class));
    }
}