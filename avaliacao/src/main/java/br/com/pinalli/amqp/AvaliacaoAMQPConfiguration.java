package br.com.pinalli.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
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

@Configuration // Indica que esta classe contém definições de beans de configuração do Spring.
public class AvaliacaoAMQPConfiguration {

    // Cria e retorna um conversor de mensagens que transforma mensagens em formato JSON.
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Cria e configura o RabbitTemplate, que é usado para enviar mensagens para o RabbitMQ.
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // Define o conversor de mensagens como o criado acima, para serializar as mensagens em JSON.
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    // Cria uma fila não-durável chamada "pagamentos.detalhes-avaliacao".
    // Define que a fila possui um "Dead Letter Exchange" (DLX) associado para mensagens não processadas.
    @Bean
    public Queue filaDetalhesAvaliacao() {
        return QueueBuilder
                .nonDurable("pagamentos.detalhes-avaliacao")
                .deadLetterExchange("pagamentos.dlx")
                .build();
    }

    // Cria uma fila não-durável chamada "pagamentos.detalhes-avaliacao-dlq",
    // que será usada para mensagens não processadas.
    @Bean
    public Queue filaDlqDetalhesAvaliacao() {
        return QueueBuilder
                .nonDurable("pagamentos.detalhes-avaliacao-dlq")
                .build();
    }

    // Cria uma exchange do tipo fanout chamada "pagamentos.ex",
    // que envia mensagens para todas as filas vinculadas a ela.
    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange("pagamentos.ex")
                .build();
    }

    // Cria uma exchange do tipo fanout chamada "pagamentos.dlx",
    // usada para tratar mensagens que não puderam ser entregues ou processadas (DLX).
    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder
                .fanoutExchange("pagamentos.dlx")
                .build();
    }

    // Cria um vínculo entre a fila "pagamentos.detalhes-avaliacao" e a exchange "pagamentos.ex".
    @Bean
    public Binding bindPagamentoPedido() {
        return BindingBuilder
                .bind(filaDetalhesAvaliacao())
                .to(fanoutExchange());
    }

    // Cria um vínculo entre a fila "pagamentos.detalhes-avaliacao-dlq" e a exchange "pagamentos.dlx".
    @Bean
    public Binding bindDlxPagamentoPedido() {
        return BindingBuilder
                .bind(filaDlqDetalhesAvaliacao())
                .to(deadLetterExchange());
    }

    // Cria um RabbitAdmin que facilita a criação de filas, exchanges e bindings automaticamente.
    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    // Inicializa o RabbitAdmin assim que a aplicação estiver pronta para garantir que todas as configurações sejam aplicadas.
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
