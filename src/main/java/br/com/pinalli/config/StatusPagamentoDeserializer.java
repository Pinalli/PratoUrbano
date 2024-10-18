package br.com.pinalli.config;

import br.com.pinalli.dto.PagamentoDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StatusPagamentoDeserializer extends JsonDeserializer<PagamentoDTO.StatusPagamento> {
    @Override
    public PagamentoDTO.StatusPagamento deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        return PagamentoDTO.StatusPagamento.valueOf(value);
    }
}