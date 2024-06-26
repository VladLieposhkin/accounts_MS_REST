package vl.example.accountsexaminer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import vl.example.accountsexaminer.external.ExternalRestServiceRestClientImpl;
import vl.example.accountsexaminer.internal.impl.InternalMailServiceRestClientImpl;
import vl.example.accountsexaminer.internal.impl.InternalRestServiceRestClientImpl;

@Configuration
public class ExaminerBeans {

    @Bean
    public InternalRestServiceRestClientImpl internalClient(@Value("${services.internal.url:}") String internalUrl) {
        return new InternalRestServiceRestClientImpl(
                RestClient.builder()
                        .baseUrl(internalUrl)
                        .build());
    }

    @Bean
    public ExternalRestServiceRestClientImpl externalClient(@Value("${services.external.url:}") String externalUrl) {

        return new ExternalRestServiceRestClientImpl(RestClient.builder()
                .baseUrl(externalUrl)
                .build());
    }

    @Bean
    public InternalMailServiceRestClientImpl mailClient(@Value("${services.mail-sender.url:}") String mailSenderUrl) {
        return new InternalMailServiceRestClientImpl(
                RestClient.builder()
                        .baseUrl(mailSenderUrl)
                        .build());
    }
}
