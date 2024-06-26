package vl.example.accountsexaminer.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountsexaminer.config.RabbitConfiguration;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountsQueueProcessor {

    private static final TypeReference<List<AccountDTO>> ACCOUNTS_REFERENCE = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public void requestAccountsDataMessage() {

        log.info("PROCESSOR. Mail processing: Started");
        try {
            String request = objectMapper.writeValueAsString("request_accounts");
            rabbitTemplate.convertAndSend(RabbitConfiguration.DIRECT_EXCHANGE,
                    RabbitConfiguration.ROUTING_KEY_REQUEST_ACCOUNTS, request);
        } catch (JsonProcessingException e) {
            log.error("PROCESSOR. External data processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = RabbitConfiguration.ACCOUNTS_TO_GET_QUEUE)
    public void getAccountsDataMessage(String queueMessage) {

        List<AccountDTO> accounts = new ArrayList<>();
        try {
            accounts = objectMapper.readValue(queueMessage, ACCOUNTS_REFERENCE);
        } catch (JsonProcessingException e) {
            log.error("PROCESSOR. External data processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        try {
            String mail = objectMapper.writeValueAsString(accounts);
            rabbitTemplate.convertAndSend(RabbitConfiguration.DIRECT_EXCHANGE,
                    RabbitConfiguration.ROUTING_KEY_UPDATE_ACCOUNTS, mail);
        } catch (JsonProcessingException e) {
            log.error("PROCESSOR. External data processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        log.info("PROCESSOR. Mail processing: Data about {} accounts is processed", accounts.size());
    }
}
