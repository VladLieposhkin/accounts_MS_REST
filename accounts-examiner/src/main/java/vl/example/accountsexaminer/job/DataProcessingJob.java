package vl.example.accountsexaminer.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vl.example.accountsexaminer.external.ExternalRestServiceRestClient;
import vl.example.accountsexaminer.internal.InternalMailServiceRestClient;
import vl.example.accountsexaminer.internal.InternalRestServiceRestClient;
import vl.example.accountsexaminer.queue.AccountsQueueProcessor;
import vl.example.accountsexaminer.queue.CoinsQueueProcessor;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataProcessingJob {

    private final InternalRestServiceRestClient internalRestServiceRestClient;
    private final ExternalRestServiceRestClient externalRestServiceRestClient;
    private final InternalMailServiceRestClient internalMailServiceRestClient;


    private final CoinsQueueProcessor coinsQueueProcessor;
    private final AccountsQueueProcessor accountsQueueProcessor;


    @Value("${services.mail-sender.threshold:}")
    private Integer threshold;

    @Scheduled(fixedDelayString = "${services.examiner.fixed-delay:}", initialDelayString = "${services.examiner.initial-delay:}", timeUnit = TimeUnit.SECONDS)
    public void processExternalData() {

        coinsQueueProcessor.requestCoinsDataMessage();

//        log.info("PROCESSOR.External data processing: Started.");
//        CompletableFuture.supplyAsync(internalRestServiceRestClient::getCoinsData)
//
//                .thenApply(externalRestServiceRestClient::getExternalCoinsData)
//
//                .thenApply(list -> list.stream()
//                        .map(CoinExternalDTO::toCoinDTO)
//                        .peek(coin -> {
//                            if (Math.abs(coin.getChange7d()) > threshold) {
//                                log.info("ATTENTION: Coin {} is changed significantly, % = {}",
//                                        coin.getName(), coin.getChange7d());
//                            }
//                        })
//                        .toList())
//
//                .thenApply(internalRestServiceRestClient::setCoinsData)
//
//                .exceptionally(exception -> {
//                    log.error("PROCESSOR.External data processing: Unable to process data, {}",
//                            exception.getMessage());
//                    return 0;
//                })
//
//                .thenAccept(result -> log.info("PROCESSOR.External data processing: Data about {} coins is processed",
//                        result))
//                .join();
    }

    @Scheduled(fixedDelayString = "${services.examiner.fixed-delay:}", initialDelayString = "${services.examiner.initial-delay:}", timeUnit = TimeUnit.SECONDS)
    public void processMail() {

        accountsQueueProcessor.requestAccountsDataMessage();

//        log.info("PROCESSOR. Mail processing: Started");
//        CompletableFuture.supplyAsync(() -> internalRestServiceRestClient.getAccountsData(threshold))
//
//                .thenApply(internalMailServiceRestClient::sendMail)
//
//                .exceptionally(exception -> {
//                    log.error("PROCESSOR. Mail processing: Unable to process data, {}",
//                            exception.getMessage());
//                    return 0;
//                })
//
//                .thenAccept(result -> log.info("PROCESSOR. Mail processing: Data about {} accounts is processed",
//                        result))
//                .join();
    }
}
