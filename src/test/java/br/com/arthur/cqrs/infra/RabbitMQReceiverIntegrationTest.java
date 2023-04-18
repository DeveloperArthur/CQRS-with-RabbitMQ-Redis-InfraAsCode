package br.com.arthur.cqrs.infra;

import br.com.arthur.cqrs.AbstractTest;
import br.com.arthur.cqrs.TestcontainersConfiguration;
import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.service.ConsultaVeiculo;
import br.com.arthur.cqrs.infra.event.RabbitMQReceiver;
import com.google.gson.Gson;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestcontainersConfiguration.class)
public class RabbitMQReceiverIntegrationTest extends AbstractTest {
  @Autowired
  RabbitMQReceiver rabbitMQReceiver;
  @Autowired
  ConsultaVeiculo consultaVeiculo;

  @BeforeAll
  public static void startContainers() {
    TestcontainersConfiguration.container.start();

    //Necessário pois json-server demora alguns segundos
    //até ficar disponível mesmo o container já estando up
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @AfterAll
  public static void stopContainers() {
    TestcontainersConfiguration.container.stop();
  }

  @Test
  public void deveSincronizarDadosNoReadDatabase(){
    String veiculoJson = criaVeiculoEmJson();

    rabbitMQReceiver.receiveManage(veiculoJson);

    Veiculo veiculoExpected = new Gson().fromJson(veiculoJson, Veiculo.class);
    Optional<Veiculo> veiculoGravado = consultaVeiculo.read(ID_VEICULO);

    assertEqualsVeiculo(veiculoExpected, veiculoGravado.get());
  }
}
