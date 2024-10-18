package br.com.pinalli.dto;

import br.com.pinalli.config.StatusPagamentoDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;


@Getter
@Setter
public class PagamentoDTO {
    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;

    @JsonDeserialize(using = StatusPagamentoDeserializer.class)
    private StatusPagamento status;

    private Long formaDePagamentoId;
    private Long pedidoId;

    public enum StatusPagamento {
        CRIADO,
        CONFIRMADO,
        CANCELADO;
    }
}