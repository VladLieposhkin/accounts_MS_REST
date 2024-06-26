package vl.example.accountsmailsender.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountscommon.dto.ClientDTO;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class AccountRestControllerV1 {

//    private JavaMailSender mailSender;
    @PostMapping
    public ResponseEntity<Integer> sendMail(@RequestBody List<AccountDTO> accountDTOs) {

        List<ClientDTO> clients = accountDTOs.stream()
                .map(AccountDTO::getClient)
                .distinct()
                .peek(clientDTO -> {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(clientDTO.getEmail());
                    message.setSubject("Action is required");
                    message.setText("Dear " + clientDTO.getName() + "! Accounts: ...");

//                    mailSender.send(message);

                    log.info("Mail to client {} has been sent", clientDTO.getName());
                })
                .toList();
        return new ResponseEntity<>(clients.size(), HttpStatus.OK);
    }
}
