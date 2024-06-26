package vl.example.accountsexaminer.internal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountsexaminer.internal.InternalMailServiceRestClient;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class InternalMailServiceRestClientImpl implements InternalMailServiceRestClient {

    private final RestClient restClient;

    @Override
    public Integer sendMail(List<AccountDTO> accountDTOs) {

        return restClient
                .post()
                .uri("/mail")
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountDTOs)
                .retrieve()
                .body(Integer.class);
    }
}
