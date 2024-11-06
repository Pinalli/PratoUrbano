package br.com.pinalli.repository;

import br.com.pinalli.model.Pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//necessario para o crud
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long > {

}