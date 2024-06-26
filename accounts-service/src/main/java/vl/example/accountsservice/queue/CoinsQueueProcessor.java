package vl.example.accountsservice.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import vl.example.accountscommon.dto.CoinDTO;
import vl.example.accountsservice.config.RabbitConfiguration;
import vl.example.accountsservice.service.CoinService;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CoinsQueueProcessor {

    private static final TypeReference<List<CoinDTO>> COINS_REFERENCE = new TypeReference<>() {
    };

    private final CoinService coinService;

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitConfiguration.COINS_REQUEST_QUEUE)
    public void requestCoinsDataMessage(String queueMessage) {

        log.info("CORE SERVICE. External data processing: Started.");
        String request;
        try {
            request = objectMapper.readValue(queueMessage, String.class);
        } catch (JsonProcessingException e) {
            log.error("CORE SERVICE. External data processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        List<CoinDTO> coinDTOs = coinService.findAll();

        try {
            String coins = objectMapper.writeValueAsString(coinDTOs);
            rabbitTemplate.convertAndSend(RabbitConfiguration.DIRECT_EXCHANGE,
                    RabbitConfiguration.ROUTING_KEY_GET_COINS, coins);
        } catch (JsonProcessingException e) {
            log.error("CORE SERVICE. External data processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = RabbitConfiguration.COINS_TO_UPDATE_QUEUE)
    public void updateCoinsDataMessage(String queueMessage) {

        List<CoinDTO> externalCoins = new ArrayList<>();
        try {
            externalCoins = objectMapper.readValue(queueMessage, COINS_REFERENCE);
        } catch (JsonProcessingException e) {
            log.error("CORE SERVICE. External data processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        coinService.updateByExternalData(externalCoins);
        log.info("CORE SERVICE. External data processing: Data about {} coins is processed", externalCoins.size());
    }
}
