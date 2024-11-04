package br.com.pinalli.model;

import jakarta.validation.constraints.Positive;
import lombok.*;
import jakarta.persistence.*;




@Entity
@Table(name = "item_do_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDoPedido {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Positive
    private Integer quantidade;

    private String descricao;

    @ManyToOne(optional=false)
    private Pedido pedido;

}
