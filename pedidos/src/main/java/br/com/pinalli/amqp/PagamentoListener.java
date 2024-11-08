package br.com.pinalli.amqp;


import br.com.pinalli.dto.PagamentoDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    // A anotação @RabbitListener faz com que o método escute mensagens de uma fila específica no RabbitMQ.
// Quando uma mensagem é recebida na fila "pagamentos.detalhes-pedido", o método `recebeMensagem` é executado.
    @RabbitListener(queues = "pagamentos.detalhes-pedido")
    public void recebeMensagem(PagamentoDTO pagamento) {

        // Formata uma mensagem com os detalhes do objeto `PagamentoDTO` recebido. .
        String mensagem = """
                 Dados do pagamento:  %s
                 Número do pedido: %s
                 Valor R$: %s
                 Status: %s               \s
                \s""".formatted(pagamento.getId(), pagamento.getPedidoId(),
                pagamento.getValor(), pagamento.getStatus());
        System.out.println("Recebi a mensagem " + mensagem);
    }
}