package br.com.pinalli.service;

import br.com.pinalli.dto.PagamentoDTO;
import br.com.pinalli.http.PedidoClient;
import br.com.pinalli.model.Pagamento;
import br.com.pinalli.model.Status;
import br.com.pinalli.repository.PagamentoRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class PagamentoService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PagamentoRepository repository;
    @Autowired
    private PedidoClient pedido;


    @Transactional
    public PagamentoDTO criarPagamento(PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Transactional
    public Page<PagamentoDTO> obterTodos(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    @Transactional
    public PagamentoDTO obterPorId(Long id) {
        Pagamento pagamento = repository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Transactional
    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO dto) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));

        // Atualizar campos manualmente, mantendo o ID original
        pagamento.setValor(dto.getValor());
        pagamento.setNome(dto.getNome());
        pagamento.setNumero(dto.getNumero());
        pagamento.setExpiracao(dto.getExpiracao());
        pagamento.setCodigo(dto.getCodigo());
        pagamento.setStatus(dto.getStatus());
        pagamento.setFormaDePagamentoId(dto.getFormaDePagamentoId());
        pagamento.setPedidoId(dto.getPedidoId());

        // Salvar as alterações
        Pagamento atualizadaPagamento = repository.save(pagamento);

        // Retornar o DTO atualizado
        return modelMapper.map(atualizadaPagamento, PagamentoDTO.class);
    }

    public void confirmarPagamento(Long id) {
        Optional<Pagamento> pagamento = repository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        repository.save(pagamento.get());
        pedido.atualizaPagamento(pagamento.get().getPedidoId());
    }

    @Transactional
    public void excluirPagamento(Long id) {
        repository.deleteById(id);
    }
}

