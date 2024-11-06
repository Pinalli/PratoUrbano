package br.com.pinalli.model;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dataHora;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="pedido")
    private List<ItemDoPedido> itens = new ArrayList<>();
}
