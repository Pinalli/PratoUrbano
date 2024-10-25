package br.com.pinalli.service;

import br.com.pinalli.dto.PagamentoDTO;
import br.com.pinalli.model.Pagamento;
import br.com.pinalli.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


@Service
public class PagamentoService {

    private final ModelMapper modelMapper;
    private final PagamentoRepository pagamentoRepository;

    @Autowired
    public PagamentoService(ModelMapper modelMapper, PagamentoRepository pagamentoRepository) {
        this.modelMapper = modelMapper;
        this.pagamentoRepository = pagamentoRepository;
    }

    @Transactional
    public PagamentoDTO criarPagamento(PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(PagamentoDTO.StatusPagamento.CRIADO);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Transactional
    public Page<PagamentoDTO> obterTodos(Pageable paginacao) {
        return pagamentoRepository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    @Transactional
    public PagamentoDTO obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Transactional
    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id)
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
        Pagamento atualizadaPagamento = pagamentoRepository.save(pagamento);

        // Retornar o DTO atualizado
        return modelMapper.map(atualizadaPagamento, PagamentoDTO.class);
    }

        // Não é necessário chamar save() explicitamente devido ao @Transactional
        // O Hibernate detectará as mudanças e atualizará a entidade

     /*   PagamentoDTO updatedDto = new PagamentoDTO();
        updatedDto.setId(pagamento.getId());
        updatedDto.setValor(pagamento.getValor());
        updatedDto.setNumero(pagamento.getNumero());
        updatedDto.setExpiracao(pagamento.getExpiracao());
        updatedDto.setCodigo(pagamento.getCodigo());
        updatedDto.setStatus(pagamento.getStatus());
        updatedDto.setFormaDePagamentoId(pagamento.getFormaDePagamentoId());
        updatedDto.setPedidoId(pagamento.getPedidoId());
*/


    @Transactional
    public void excluirPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
