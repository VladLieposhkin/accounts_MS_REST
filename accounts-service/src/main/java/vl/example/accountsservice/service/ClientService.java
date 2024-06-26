package vl.example.accountsservice.service;

import vl.example.accountscommon.dto.ClientDTO;
import vl.example.accountscommon.dto.ClientDetailedDTO;

import java.util.List;

public interface ClientService {
    ClientDTO findOne(Integer clientId);
    ClientDetailedDTO findOneWithDetails(Integer clientId);
    List<ClientDTO> findAll();
    ClientDTO create(ClientDTO clientDTO);
    ClientDTO update(ClientDTO clientDTO, Integer clientId);
    void delete(Integer clientId);
    boolean checkById(Integer clientId);
    boolean checkByEmail(String email, Integer id);
    boolean checkByName(String name, Integer id);
}
