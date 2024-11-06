package br.com.pinalli.config;

import br.com.pinalli.dto.ItemDoPedidoDto;
import br.com.pinalli.model.ItemDoPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuracao {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configura o mapeamento de ItemDoPedidoDto para ItemDoPedido
        modelMapper.typeMap(ItemDoPedidoDto.class, ItemDoPedido.class).addMappings(mapper -> {
            mapper.map(ItemDoPedidoDto::getQuantidade, ItemDoPedido::setQuantidade);
            mapper.map(ItemDoPedidoDto::getDescricao, ItemDoPedido::setDescricao);
        });

        return modelMapper;
    }
}