package br.com.arthur.cqrs.infra.dao;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.ReadDatabase;
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
}
