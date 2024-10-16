package br.com.pinalli.repository;

import br.com.pinalli.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

//necessario para o crud
public interface PagamentoRepository extends JpaRepository <Pagamento, Long> {
}
