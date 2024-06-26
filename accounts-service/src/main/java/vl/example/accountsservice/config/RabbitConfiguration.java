package vl.example.accountsservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public static final String DIRECT_EXCHANGE = "direct-exchange";
    public static final String COINS_TO_GET_QUEUE = "coins-get-queue";
    public static final String COINS_TO_UPDATE_QUEUE = "coins-update-queue";
    public static final String COINS_REQUEST_QUEUE = "coins-request-queue";
    public static final String ACCOUNTS_TO_GET_QUEUE = "accounts-get-queue";
    public static final String ACCOUNTS_TO_UPDATE_QUEUE = "accounts-update-queue";
    public static final String ACCOUNTS_REQUEST_QUEUE = "accounts-request-queue";

    // Routing keys for different object types
    public static final String ROUTING_KEY_GET_ACCOUNTS = "accounts-get-key";
    public static final String ROUTING_KEY_UPDATE_ACCOUNTS = "accounts-update-key";
    public static final String ROUTING_KEY_REQUEST_ACCOUNTS = "accounts-request-key";
    public static final String ROUTING_KEY_GET_COINS = "coins-get-key";
    public static final String ROUTING_KEY_UPDATE_COINS = "coins-update-key";
    public static final String ROUTING_KEY_REQUEST_COINS = "coins-requests-key";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Queue coinsRequestQueue() {
        return new Queue(COINS_REQUEST_QUEUE);
    }

    @Bean
    public Queue coinsGetQueue() {
        return new Queue(COINS_TO_GET_QUEUE);
    }

    @Bean
    public Queue coinsUpdateQueue() {
        return new Queue(COINS_TO_UPDATE_QUEUE);
    }

    @Bean
    public Queue accountsRequestQueue() {
        return new Queue(ACCOUNTS_REQUEST_QUEUE);
    }

    @Bean
    public Queue accountsGetQueue() {
        return new Queue(ACCOUNTS_TO_GET_QUEUE);
    }

    @Bean
    public Queue accountsUpdateQueue() {
        return new Queue(ACCOUNTS_TO_UPDATE_QUEUE);
    }

    @Bean
    public Binding coinsRequestBinding() {
        return BindingBuilder.bind(coinsRequestQueue()).to(directExchange()).with(ROUTING_KEY_REQUEST_COINS);
    }

    @Bean
    public Binding coinsGetBinding() {
        return BindingBuilder.bind(coinsGetQueue()).to(directExchange()).with(ROUTING_KEY_GET_COINS);
    }

    @Bean
    public Binding coinsUpdateBinding() {
        return BindingBuilder.bind(coinsUpdateQueue()).to(directExchange()).with(ROUTING_KEY_UPDATE_COINS);
    }

    @Bean
    public Binding accountsRequestBinding() {
        return BindingBuilder.bind(accountsRequestQueue()).to(directExchange()).with(ROUTING_KEY_REQUEST_ACCOUNTS);
    }

    @Bean
    public Binding accountsGetBinding() {
        return BindingBuilder.bind(accountsGetQueue()).to(directExchange()).with(ROUTING_KEY_GET_ACCOUNTS);
    }

    @Bean
    public Binding accountsUpdateBinding() {
        return BindingBuilder.bind(accountsUpdateQueue()).to(directExchange()).with(ROUTING_KEY_UPDATE_ACCOUNTS);
    }
}
