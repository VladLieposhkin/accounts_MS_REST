package vl.example.accountsservice.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountsservice.config.RabbitConfiguration;
import vl.example.accountsservice.service.AccountService;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountsQueueProcessor {

    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitConfiguration.ACCOUNTS_REQUEST_QUEUE)
    public void requestAccountsDataMessage(String queueMessage) {

        log.info("CORE SERVICE. Mail processing: Started.");
        String request;
        try {
            request = objectMapper.readValue(queueMessage, String.class);
        } catch (JsonProcessingException e) {
            log.error("CORE SERVICE. Mail processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        List<AccountDTO> accountDTOs = accountService.getAccountsData(1);

        try {
            String accounts = objectMapper.writeValueAsString(accountDTOs);
            rabbitTemplate.convertAndSend(RabbitConfiguration.DIRECT_EXCHANGE,
                    RabbitConfiguration.ROUTING_KEY_GET_ACCOUNTS, accounts);
        } catch (JsonProcessingException e) {
            log.error("CORE SERVICE. Mail processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        log.info("CORE SERVICE. Mail processing: Data about {} accounts is processed", accountDTOs.size());
    }
}
