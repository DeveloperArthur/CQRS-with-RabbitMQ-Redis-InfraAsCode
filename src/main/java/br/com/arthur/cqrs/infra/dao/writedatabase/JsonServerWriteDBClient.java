package br.com.arthur.cqrs.infra.dao.writedatabase;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.WriteDatabase;
import br.com.arthur.cqrs.infra.JsonUtilAdapterWithGson;
import br.com.arthur.cqrs.infra.dao.VeiculoDtoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JsonServerWriteDBClient implements WriteDatabase {
    private String endpointDatabase;
    private RestTemplate restTemplate;

    @Autowired
    public JsonServerWriteDBClient(@Value("${write-database.endpoint}") String endpointDatabase,
        RestTemplate restTemplate) {
        this.endpointDatabase = endpointDatabase;
        this.restTemplate = restTemplate;
    }

    @Override
    public Veiculo write(Veiculo veiculo) {
        System.out.println("Salvando no banco");
        try {
            VeiculoDtoDatabase veiculoDtoDatabase = new VeiculoDtoDatabase(veiculo);
            String veiculoJson = JsonUtilAdapterWithGson.toJson(veiculoDtoDatabase);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpEntity = new HttpEntity<>(veiculoJson, httpHeaders);

            ResponseEntity<VeiculoDtoDatabase> response = restTemplate.exchange(
                    this.endpointDatabase, HttpMethod.POST,
                    httpEntity, VeiculoDtoDatabase.class
            );
            if (response.getBody() != null) return response.getBody().converte();
            return null;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
