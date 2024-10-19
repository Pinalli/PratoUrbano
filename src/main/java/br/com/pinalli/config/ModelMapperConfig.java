package br.com.pinalli.config;

import br.com.pinalli.dto.PagamentoDTO;
import br.com.pinalli.model.Pagamento;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuração existente para StatusPagamento
        modelMapper.createTypeMap(String.class, PagamentoDTO.StatusPagamento.class)
                .setConverter(context -> PagamentoDTO.StatusPagamento.valueOf(context.getSource()));

        modelMapper.createTypeMap(PagamentoDTO.StatusPagamento.class, String.class)
                .setConverter(context -> context.getSource().name());

        // Nova configuração para evitar sobrescrita do ID
        TypeMap<PagamentoDTO, Pagamento> pagamentoDTOToPagamento = modelMapper.createTypeMap(PagamentoDTO.class, Pagamento.class);
        pagamentoDTOToPagamento.addMappings(mapper -> mapper.skip(Pagamento::setId));

        // Configuração adicional para melhorar o mapeamento
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}
/**
 * Esta configuração faz o seguinte:
 *
 * Mantém as configurações existentes para o mapeamento entre String e PagamentoDTO.StatusPagamento.
 * Adiciona um novo TypeMap para o mapeamento entre PagamentoDTO e Pagamento, ignorando explicitamente o mapeamento do campo id.
 * Configura o ModelMapper para pular propriedades nulas (setSkipNullEnabled(true)), o que é útil para atualizações parciais.
 * Define a estratégia de correspondência como STRICT, o que ajuda a evitar mapeamentos incorretos.
 */