package vl.example.accountsservice.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountsservice.exception.CustomBadRequestException;
import vl.example.accountsservice.service.AccountService;
import vl.example.accountsservice.service.ClientService;
import vl.example.accountsservice.service.CoinService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountValidator implements Validator {

    private final AccountService accountService;
    private final CoinService coinService;
    private final ClientService clientService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(AccountDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        AccountDTO accountDTO = (AccountDTO) target;

        if (accountDTO.getClient().getId() == null) {
            errors.rejectValue("client", "", "Field Client should have a value");
        }
        else if (!clientService.checkById(accountDTO.getClient().getId())) {
            errors.rejectValue("client", "", "Client not found. ID = " + accountDTO.getClient().getId());
        }

        if (accountDTO.getCoin().getId() == null) {
            errors.rejectValue("coin", "", "Field Coin should have a value");
        }
        else if (!coinService.checkById(accountDTO.getCoin().getId())) {
            errors.rejectValue("coin", "", "Coin not found. ID = " + accountDTO.getCoin().getId());
        }

        if (accountService.checkByNumber(accountDTO.getNumber(), accountDTO.getId()))
            errors.rejectValue("number", "", "Account with same Number already present");

        if (errors.hasErrors()) {
            List<String> bindingErrors = errors.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .toList();
            throw new CustomBadRequestException("BAD REQUEST", bindingErrors);
        }
    }
}
