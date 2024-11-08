package br.com.pinalli.amqp;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// A anotação @Configuration indica que esta classe é uma configuração do Spring,
// permitindo a definição de beans que serão gerenciados pelo contêiner do Spring.
@Configuration
public class PagamentoAMQPconfig {

    // Cria um bean RabbitAdmin, que é uma classe do Spring AMQP
    // usada para gerenciar componentes de broker do RabbitMQ, como filas, exchanges e bindings.
    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    // Cria um ApplicationListener que inicializa o RabbitAdmin quando a
    // aplicação estiver pronta. Isso garante que as exchanges, filas, etc.,
    // sejam criadas no RabbitMQ assim que a aplicação for iniciada.
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    // Cria um conversor de mensagens que converte objetos J
    // ava em formato JSON para serem enviados pelo RabbitMQ.
    // O `Jackson2JsonMessageConverter` é utilizado para serialização/deserialização.
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Configura o RabbitTemplate, que é uma classe do Spring usada para enviar e receber mensagens
    // no RabbitMQ. Aqui, o template é configurado para usar o
    // `messageConverter` para converter mensagens em JSON.
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    // Cria uma exchange do tipo `FanoutExchange`, que é uma exchange que
    // encaminha mensagens para todas as filas que estão vinculadas a ela.
    // A exchange é construída com o nome "pagamentos.ex".
    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange("pagamentos.ex")
                .build();
    }
}
