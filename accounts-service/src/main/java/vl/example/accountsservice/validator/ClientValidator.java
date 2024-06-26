package vl.example.accountsservice.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vl.example.accountscommon.dto.ClientDTO;
import vl.example.accountsservice.exception.CustomBadRequestException;
import vl.example.accountsservice.service.ClientService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientValidator implements Validator {

    private final ClientService clientService;

    @Override
    public boolean supports(Class<?> clazz) {

        return clazz.equals(ClientDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ClientDTO clientDTO = (ClientDTO) target;

        if (clientService.checkByName(clientDTO.getName(), clientDTO.getId()))
            errors.rejectValue("name", "", "Client with same Name already present");

        if (clientService.checkByEmail(clientDTO.getEmail(), clientDTO.getId()))
            errors.rejectValue("email", "", "Client with same Email already present");

        if (errors.hasErrors()) {
            List<String> bindingErrors = errors.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .toList();
            throw new CustomBadRequestException("BAD REQUEST", bindingErrors);
        }
    }
}
