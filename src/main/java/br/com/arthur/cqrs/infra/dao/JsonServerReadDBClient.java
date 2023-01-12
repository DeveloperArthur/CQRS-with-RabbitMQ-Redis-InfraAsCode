package br.com.arthur.cqrs.infra.dao;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.ReadDatabase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class JsonServerReadDBClient implements ReadDatabase {
    @Value("${read-database.endpoint}")
    private String endpointDatabase;

    @Override
    public Optional<Veiculo> read(String id) {
        System.out.println("Veiculo não está no cache, buscando no banco...");
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<VeiculoJson> response = restTemplate.exchange(
                this.endpointDatabase + "/" + id, HttpMethod.GET,
                null, VeiculoJson.class
        );

        if (response.getBody() != null) return Optional.of(response.getBody().converte());
        return null;
    }

    @Override
    public void sincronizaBancos(Veiculo veiculo) {
        System.out.println("Sincronizando bancos de dados");
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
            if (response.getStatusCode() == HttpStatus.OK)
                System.out.println("Bancos de dados sincronizados com sucesso");
        }catch (JsonProcessingException e){
            throw new RuntimeException();
        }
    }
}
