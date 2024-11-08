package br.com.pinalli.controller;


import br.com.pinalli.dto.PagamentoDTO;
import br.com.pinalli.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;


@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {


    private final RabbitTemplate rabbitTemplate;
    private final PagamentoService pagamentoService;

    public PagamentoController(RabbitTemplate rabbitTemplate, PagamentoService pagamentoService) {
        this.rabbitTemplate = rabbitTemplate;
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> register(@RequestBody @Valid PagamentoDTO pagamentoDTO, UriComponentsBuilder uriBuilder) {
        PagamentoDTO pagamento = pagamentoService.criarPagamento(pagamentoDTO);
        URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        //Message message = new Message(("Criei um pagamento com o id  " + pagamento.getId()).getBytes());
        rabbitTemplate.convertAndSend("pagamentos.ex", "", pagamento);
        return ResponseEntity.created(endereco).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> update(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDTO pagamentoDTO) {
        if (pagamentoDTO.getId() != null && !pagamentoDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        PagamentoDTO atualizado = pagamentoService.atualizarPagamento(id, pagamentoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public Page<PagamentoDTO> list(@PageableDefault() Pageable paginacao) {
        return pagamentoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> details(@PathVariable @NotNull Long id) {
        PagamentoDTO dto = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDTO> delete(@PathVariable @NotNull Long id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    public void confirmarPagamento(@PathVariable @NotNull Long id) {
        pagamentoService.confirmarPagamento(id);
    }

    public void pagamentoAutorizadoComIntegracaoPendente(Long id, Exception e) {
        pagamentoService.alteraStatus(id);
    }
}
