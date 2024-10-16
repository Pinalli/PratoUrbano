package br.com.pinalli.controller;


import br.com.pinalli.dto.PagamentoDTO;
import br.com.pinalli.service.PagamentoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {


    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> register(@RequestBody @Valid PagamentoDTO pagamentoDTO, UriComponentsBuilder uriBuilder) {
        PagamentoDTO pagamento = pagamentoService.criarPagamento(pagamentoDTO);
        URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(endereco).body(pagamento);
    }

    @GetMapping
    public Page<PagamentoDTO> list(@PageableDefault(size = 10) Pageable paginacao) {
        return pagamentoService.obterTodos((org.springframework.data.domain.Pageable) paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> details(@PathVariable @NotNull Long id) {
        PagamentoDTO dto = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> uodate(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDTO pagamentoDTO) {
        PagamentoDTO atualizado = pagamentoService.atualizarPagamento(id, pagamentoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDTO> delete(@PathVariable @NotNull Long id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
