package br.com.pinalli.amqp;


import br.com.pinalli.dto.PagamentoDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    @RabbitListener(queues = "pagamentos.detalhes-pedido")
    public void recebeMensagem(PagamentoDTO pagamento) {
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