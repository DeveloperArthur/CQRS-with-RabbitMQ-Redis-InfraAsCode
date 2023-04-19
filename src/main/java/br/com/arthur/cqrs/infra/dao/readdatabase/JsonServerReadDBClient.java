package br.com.arthur.cqrs.infra.dao.readdatabase;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.ReadDatabase;
import br.com.arthur.cqrs.infra.JsonUtilAdapterWithGson;
import br.com.arthur.cqrs.infra.dao.VeiculoDtoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class JsonServerReadDBClient implements ReadDatabase {
    private String endpointDatabase;
    private RestTemplate restTemplate;

    @Autowired
    public JsonServerReadDBClient(@Value("${read-database.endpoint}") String endpointDatabase,
        RestTemplate restTemplate) {
        this.endpointDatabase = endpointDatabase;
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Veiculo> read(String id) {
        System.out.println("Veiculo não está no cache, buscando no banco...");

        ResponseEntity<VeiculoDtoDatabase> response = null;
        try {
             response = restTemplate.exchange(
                    this.endpointDatabase + "/" + id, HttpMethod.GET,
                    null, VeiculoDtoDatabase.class);

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
            VeiculoDtoDatabase veiculoDtoDatabase = new VeiculoDtoDatabase(veiculo);
            String veiculoJson = JsonUtilAdapterWithGson.toJson(veiculoDtoDatabase);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpEntity = new HttpEntity<>(veiculoJson, httpHeaders);

            ResponseEntity<VeiculoDtoDatabase> response = restTemplate.exchange(
                    this.endpointDatabase, HttpMethod.POST,
                    httpEntity, VeiculoDtoDatabase.class
            );
            if (response.getStatusCode() == HttpStatus.OK)
                System.out.println("Bancos de dados sincronizados com sucesso");
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
