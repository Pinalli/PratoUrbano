package br.com.pinalli.config;

import br.com.pinalli.dto.PagamentoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(String.class, PagamentoDTO.StatusPagamento.class)
                .setConverter(context -> PagamentoDTO.StatusPagamento.valueOf(context.getSource()));

        modelMapper.createTypeMap(PagamentoDTO.StatusPagamento.class, String.class)
                .setConverter(context -> context.getSource().name());

        return modelMapper;
    }
}