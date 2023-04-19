package br.com.arthur.cqrs.infra;

import br.com.arthur.cqrs.AbstractTest;
import br.com.arthur.cqrs.TestcontainersConfiguration;
import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.ReadDatabase;
import br.com.arthur.cqrs.infra.event.RabbitMQReceiver;
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
  ReadDatabase readDatabase;

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
  public void deveExistirVeiculoNoReadDatabase_quandoSincronizarDados(){
    String veiculoJson = criaVeiculoEmJson();
    Veiculo veiculoExpected = JsonUtilAdapterWithGson.veiculofromJson(veiculoJson);

    //Método que recebe evento e sincroniza as bases de dados
    rabbitMQReceiver.receiveManage(veiculoJson);

    //Buscando por id no json-server
    Optional<Veiculo> veiculoGravado = readDatabase.read(ID_VEICULO);

    assertEqualsVeiculo(veiculoExpected, veiculoGravado.get());
  }
}
