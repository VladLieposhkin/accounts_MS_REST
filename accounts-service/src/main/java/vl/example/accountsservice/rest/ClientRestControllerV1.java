package vl.example.accountsservice.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vl.example.accountscommon.dto.ClientDTO;
import vl.example.accountscommon.dto.ClientDetailedDTO;
import vl.example.accountsservice.service.ClientService;
import vl.example.accountsservice.validator.ClientValidator;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientRestControllerV1 {

    private final ClientService clientService;
    private final ClientValidator clientValidator;

    @PostMapping
    public ResponseEntity<ClientDTO> create(@RequestBody @Valid ClientDTO clientDTO,
                                            BindingResult bindingResult) {
        clientValidator.validate(clientDTO, bindingResult);
        return new ResponseEntity<>(clientService.create(clientDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {

        return new ResponseEntity<>(clientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailedDTO> findOne(@PathVariable("id") Integer clientId) {

        return new ResponseEntity<>(clientService.findOneWithDetails(clientId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable("id") Integer clientId,
                                            @RequestBody @Valid ClientDTO clientDTO,
                                            BindingResult bindingResult) {
        clientValidator.validate(clientDTO, bindingResult);
        return new ResponseEntity<>(clientService.update(clientDTO, clientId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer clientId) {

        clientService.delete(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
