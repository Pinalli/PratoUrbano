package br.com.pinalli.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// A anotação @Configuration indica que esta classe contém definições
// de beans do Spring, que serão gerenciados pelo contêiner IoC.
@Configuration
public class PedidoAMQPconfig {

    // Cria um bean que converte mensagens para o formato JSON usando Jackson.
    // Este conversor é útil para serialização e deserialização
    // de mensagens enviadas e recebidas pelo RabbitMQ.
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Cria e configura um bean RabbitTemplate, que é usado para enviar e receber mensagens do RabbitMQ.
    // Ele é configurado para usar o messageConverter para converter mensagens em JSON.
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    // Cria uma fila não durável chamada "pagamentos.detalhes-pedido".
    // Fila não durável significa que ela não será mantida após a reinicialização do broker.
    @Bean
    public Queue filaDetalhesPedido() {
        return QueueBuilder
                .nonDurable("pagamentos.detalhes-pedido")
                .build();
    }

    // Cria uma exchange do tipo `FanoutExchange` chamada "pagamentos.ex".
    // Uma exchange `fanout` distribui mensagens para todas as filas vinculadas a ela.
    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange("pagamentos.ex")
                .build();
    }

    // Cria uma ligação (binding) entre a fila "pagamentos.detalhes-pedido" e a `fanoutExchange` criada.
    // Isso garante que todas as mensagens publicadas na exchange sejam encaminhadas para essa fila.
    @Bean
    public Binding bindPagamentoPedido(FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(filaDetalhesPedido())
                .to(fanoutExchange);
    }

    // Cria um bean RabbitAdmin, que é uma ferramenta para gerenciar componentes
    // do RabbitMQ, como filas, exchanges e bindings.
    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    // Cria um listener de evento que inicializa o RabbitAdmin quando a aplicação estiver pronta.
    // Isso assegura que as filas, exchanges e bindings configurados sejam registrados
    // no RabbitMQ assim que a aplicação for iniciada.
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
