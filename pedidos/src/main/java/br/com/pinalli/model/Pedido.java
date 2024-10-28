package br.com.pinalli.model;


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
    private LocalDateTime dataHora;
    @Enumerated(EnumType.STRING)
    private br.com.pinalli.model.Status status;

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="pedido")
    private List<br.com.pinalli.model.ItemDoPedido> itens = new ArrayList<>();
}
