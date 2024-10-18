package br.com.pinalli;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.pinalli.dto.PagamentoDTO;

public class TesteDeserializacao {
    public static void main(String[] args) throws Exception {
        String json = "{\"id\":1,\"valor\":100.00,\"nome\":\"Anderso Lima\",\"numero\":\"1234567890123456\",\"expiracao\":\"12/25\",\"codigo\":\"123\",\"status\":\"CRIADO\",\"formaDePagamentoId\":1,\"pedidoId\":1}";

        ObjectMapper objectMapper = new ObjectMapper();
        PagamentoDTO pagamentoDTO = objectMapper.readValue(json, PagamentoDTO.class);

        System.out.println("Status: " + pagamentoDTO.getStatus());
    }
}