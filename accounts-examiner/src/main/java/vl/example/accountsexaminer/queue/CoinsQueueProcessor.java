package vl.example.accountsexaminer.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vl.example.accountscommon.dto.CoinDTO;
import vl.example.accountscommon.dto.CoinExternalDTO;
import vl.example.accountsexaminer.config.RabbitConfiguration;
import vl.example.accountsexaminer.external.ExternalRestServiceRestClient;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CoinsQueueProcessor {

    private static final TypeReference<List<CoinDTO>> COINS_REFERENCE = new TypeReference<>() {
    };

    @Value("${services.examiner.threshold:}")
    private Integer threshold;

    private final ExternalRestServiceRestClient externalService;

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public void requestCoinsDataMessage() {

        log.info("PROCESSOR.External data processing: Started.");
        try {
            String request = objectMapper.writeValueAsString("request_coins");
            rabbitTemplate.convertAndSend(RabbitConfiguration.DIRECT_EXCHANGE,
                    RabbitConfiguration.ROUTING_KEY_REQUEST_COINS, request);
        } catch (JsonProcessingException e) {
            log.error("PROCESSOR. External data processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = RabbitConfiguration.COINS_TO_GET_QUEUE)
    public void getCoinsDataMessage(String queueMessage) {

        List<CoinDTO> coinDTOs = new ArrayList<>();
        try {
            coinDTOs = objectMapper.readValue(queueMessage, COINS_REFERENCE);
        } catch (JsonProcessingException e) {
            log.error("PROCESSOR. External data processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        List<CoinExternalDTO> externalCoins = new ArrayList<>();
        try {
            externalCoins = externalService.getExternalCoinsData(coinDTOs);
        } catch (Exception e) {
            log.error("PROCESSOR. External data processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        List<CoinDTO> updatedCoins = externalCoins.stream()
                .map(CoinExternalDTO::toCoinDTO)
                .peek(coin -> {
                    if (Math.abs(coin.getChange7d()) > threshold) {
                        log.info("ATTENTION: Coin {} is changed significantly, % = {}",
                                coin.getName(), coin.getChange7d());
                    }
                })
                .toList();

        try {
            String updatedCoinsMessage = objectMapper.writeValueAsString(updatedCoins);
            rabbitTemplate.convertAndSend(RabbitConfiguration.DIRECT_EXCHANGE,
                    RabbitConfiguration.ROUTING_KEY_UPDATE_COINS, updatedCoinsMessage);
        } catch (JsonProcessingException e) {
            log.info("PROCESSOR. External data processing: Unable to process data, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        log.info("PROCESSOR.External data processing: Data about {} coins is processed", updatedCoins.size());
    }
}
