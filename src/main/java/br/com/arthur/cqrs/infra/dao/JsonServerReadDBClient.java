package br.com.arthur.cqrs.infra.dao;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.ReadDatabase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class JsonServerReadDBClient implements ReadDatabase {
    @Value("${read-database.endpoint}")
    private String endpointDatabase;
    private RestTemplate restTemplate;

    @Autowired
    public JsonServerReadDBClient(String endpointDatabase, RestTemplate restTemplate) {
        this.endpointDatabase = endpointDatabase;
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Veiculo> read(String id) {
        System.out.println("Veiculo não está no cache, buscando no banco...");

        ResponseEntity<VeiculoJson> response = null;
        try {
             response = restTemplate.exchange(
                    this.endpointDatabase + "/" + id, HttpMethod.GET,
                    null, VeiculoJson.class);

            if (response.getBody() != null) {
                return Optional.of(response.getBody().converte());
            }

            return Optional.empty();
        } catch (Exception e){
            System.err.println("Ocorreu um erro ao tentar buscar veiculo do banco de dados");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sincronizaBancos(Veiculo veiculo) {
        System.out.println("Sincronizando bancos de dados");
        try {
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
