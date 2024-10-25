package br.com.pinalli.model;

import br.com.pinalli.dto.PagamentoDTO;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Size(max = 19)
    private String numero;

    @Size(max = 7)
    private String expiracao;

    @NotBlank
    @Size(min = 3, max = 19)
    private String codigo;

    @Enumerated(EnumType.STRING)
    private PagamentoDTO.StatusPagamento status;

    @NotNull
    private Long pedidoId;

    @NotNull
    private Long formaDePagamentoId;
}