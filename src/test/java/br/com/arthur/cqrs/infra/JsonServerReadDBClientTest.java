package br.com.arthur.cqrs.infra;

import br.com.arthur.cqrs.AbstractTest;
import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.infra.dao.JsonServerReadDBClient;
import br.com.arthur.cqrs.infra.dao.VeiculoJson;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class JsonServerReadDBClientTest extends AbstractTest {
  JsonServerReadDBClient client;
  @Mock
  RestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    this.client = Mockito.spy(new JsonServerReadDBClient(ENDPOINT_MOCK, restTemplate));
  }

  @Test
  public void deveRetornarEmpty_quandoBodyDoResponseForNulo(){
    Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(),
            Mockito.any(), Mockito.eq(VeiculoJson.class)))
        .thenReturn(criaResponseEntityComBodyNull());

    Optional<Veiculo> veiculo = client.read(ID_VEICULO);

    Assertions.assertTrue(veiculo.isEmpty());
  }
}
