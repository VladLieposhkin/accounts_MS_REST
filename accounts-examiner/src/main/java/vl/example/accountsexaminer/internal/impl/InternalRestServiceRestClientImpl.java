package vl.example.accountsexaminer.internal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountscommon.dto.CoinDTO;
import vl.example.accountsexaminer.internal.InternalRestServiceRestClient;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class InternalRestServiceRestClientImpl implements InternalRestServiceRestClient {

    private static final ParameterizedTypeReference<List<CoinDTO>>
            COINS_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    private static final ParameterizedTypeReference<List<AccountDTO>>
            ACCOUNTS_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    private final RestClient restClient;

    @Override
    public List<CoinDTO> getCoinsData() {

        return restClient
                .get()
                .uri("/coins")
                .retrieve()
                .body(COINS_TYPE_REFERENCE);
    }

    @Override
    public Integer setCoinsData(List<CoinDTO> coinDTOs) {

        return restClient
                .post()
                .uri("/coins/update")
                .contentType(MediaType.APPLICATION_JSON)
                .body(coinDTOs)
                .retrieve()
                .body(Integer.class);
    }

    @Override
    public List<AccountDTO> getAccountsData(Integer threshold) {

        return restClient
                .get()
                .uri("/accounts/updated/" + threshold)
                .retrieve()
                .body(ACCOUNTS_TYPE_REFERENCE);
    }
}
