package vl.example.accountsexaminer.external;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import vl.example.accountscommon.dto.CoinDTO;
import vl.example.accountscommon.dto.CoinExternalDTO;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ExternalRestServiceRestClientImpl implements ExternalRestServiceRestClient {

    private static final ParameterizedTypeReference<List<CoinExternalDTO>>
           COINS_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    private final RestClient restClient;


    @Override
    public List<CoinExternalDTO> getExternalCoinsData(List<CoinDTO> coins) {

        String request = coins.stream()
                .map(CoinDTO::getCode)
                .collect(Collectors.joining(","));

        return restClient
                .get()
                .uri("/?id={request}", request)
                .retrieve()
                .body(COINS_TYPE_REFERENCE);
    }
}
