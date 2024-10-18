package br.com.pinalli.service;

import br.com.pinalli.dto.PagamentoDTO;
import br.com.pinalli.model.Pagamento;
import br.com.pinalli.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public PagamentoDTO criarPagamento(PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(PagamentoDTO.StatusPagamento.CRIADO);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public Page<PagamentoDTO> obterTodos(Pageable paginacao) {
        return pagamentoRepository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    public PagamentoDTO obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        modelMapper.map(dto, pagamento);
        pagamento = pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }


public void excluirPagamento(Long id) {
    pagamentoRepository.deleteById(id);
}
}
