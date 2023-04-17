package br.com.arthur.cqrs.infra;

import br.com.arthur.cqrs.AbstractTest;
import br.com.arthur.cqrs.TestcontainersConfiguration;
import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.infra.caching.redis.RedisServiceClient;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestcontainersConfiguration.class)
public class RedisServiceClientIntegrationTest extends AbstractTest {
  @Autowired
  RedisServiceClient redisClient;

  @BeforeAll
  public static void startContainers() {
    TestcontainersConfiguration.container.start();
  }

  @AfterAll
  public static void stopContainers() {
    TestcontainersConfiguration.container.stop();
  }

  @Test
  public void veiculoDeveEstarNoCacheAposSalvo(){
    Veiculo veiculo = criaVeiculoFakeComRenavamValido();
    veiculo.setId(ID_VEICULO);

    redisClient.salva(veiculo);

    Optional<Veiculo> veiculoOptional = redisClient.get(ID_VEICULO);

    Assertions.assertEquals(veiculo.getId(), veiculoOptional.get().getId());
    Assertions.assertEquals(veiculo.getMarca(), veiculoOptional.get().getMarca());
    Assertions.assertEquals(veiculo.getModelo(), veiculoOptional.get().getModelo());
    Assertions.assertEquals(veiculo.getAno(), veiculoOptional.get().getAno());
    Assertions.assertEquals(veiculo.getRenavam(), veiculoOptional.get().getRenavam());
    Assertions.assertEquals(veiculo.getPlaca(), veiculoOptional.get().getPlaca());
    Assertions.assertEquals(veiculo.getCor(), veiculoOptional.get().getCor());
  }

  @Test
  public void deveRetornarEmpty_quandoNaoTiverVeiculoNoCache() {
    Optional<Veiculo> veiculoOptional = redisClient.get(ID_VEICULO);
    Assertions.assertEquals(Optional.empty(), veiculoOptional);
  }
}
