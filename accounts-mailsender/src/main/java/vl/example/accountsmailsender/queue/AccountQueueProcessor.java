package vl.example.accountsmailsender.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountscommon.dto.ClientDTO;
import vl.example.accountsmailsender.config.RabbitConfiguration;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountQueueProcessor {

    private static final TypeReference<List<AccountDTO>> ACCOUNTS_TYPE_REFERENCE = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;
    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = RabbitConfiguration.ACCOUNTS_TO_UPDATE_QUEUE)
    public void mailQueueProcess(String queueMessage) {

        List<AccountDTO> accountDTOs = new ArrayList<>();

        try {
            accountDTOs = objectMapper.readValue(queueMessage, ACCOUNTS_TYPE_REFERENCE);
        } catch (JsonProcessingException e) {
            log.error("MAIL PROCESSOR. Mail processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        Map<ClientDTO, List<AccountDTO>> mails = accountDTOs.stream()
                .collect(Collectors.groupingBy(AccountDTO::getClient));

        for (Map.Entry<ClientDTO, List<AccountDTO>> entry : mails.entrySet()) {

            ClientDTO client = entry.getKey();
            List<AccountDTO> accounts = entry.getValue();

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(client.getEmail());
            mailMessage.setSubject("Action is required");
            mailMessage.setText("Dear " + client.getName() + "\n" +
                    accounts.stream()
                            .map(account ->
                                    "Account: " + account.getNumber() +
                                            ", Coin : " + account.getCoin().getName() +
                                            ", Change : " + account.getCoin().getChange7d())
                            .collect(Collectors.joining("\n")) + "\nBest regards");

//            javaMailSender.send(mailMessage);

            log.info("MAIL PROCESSOR. Mail processing: Mail to client {} has been sent", client.getName());
        }
        log.info("MAIL PROCESSOR. Mail processing: Data about {} accounts is processed", accountDTOs.size());
    }
}
