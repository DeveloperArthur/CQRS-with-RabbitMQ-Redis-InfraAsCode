package br.com.arthur.cqrs.infra.dao;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.WriteDatabase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JsonServerWriteDBClient implements WriteDatabase {
    @Value("${write-database.endpoint}")
    private String endpointDatabase;

    @Override
    public Veiculo write(Veiculo veiculo) {
        System.out.println("Salvando no banco");
        try {
            RestTemplate restTemplate = new RestTemplate();

            VeiculoJson veiculoDto = new VeiculoJson(veiculo);
            String veiculoJson = new ObjectMapper().writeValueAsString(veiculoDto);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpEntity = new HttpEntity<>(veiculoJson, httpHeaders);

            ResponseEntity<VeiculoJson> response = restTemplate.exchange(
                    this.endpointDatabase, HttpMethod.POST,
                    httpEntity, VeiculoJson.class
            );
            if (response.getBody() != null) return response.getBody().converte();
            return null;
        }catch (JsonProcessingException e){
            throw new RuntimeException();
        }
    }
}
