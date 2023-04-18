package br.com.arthur.cqrs;

import br.com.arthur.cqrs.core.service.ConsultaVeiculo;
import br.com.arthur.cqrs.infra.caching.redis.RedisServiceClient;
import br.com.arthur.cqrs.infra.dao.JsonServerReadDBClient;
import br.com.arthur.cqrs.infra.event.RabbitMQReceiver;
import java.io.File;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.DockerComposeContainer;

@Configuration
public class TestcontainersConfiguration {
  public static final DockerComposeContainer<?> container =
      new DockerComposeContainer<>(new File("docker-compose.yml"))
          .withLocalCompose(true);

  @Bean
  public RabbitMQReceiver beanRabbitMQReceiver(){
    return new RabbitMQReceiver();
  }

  @Bean
  public ConsultaVeiculo beanConsultaVeiculo() {
    return new ConsultaVeiculo(beanJsonServerReadDBClient(), beanRedisServiceClient());
  }

  @Bean
  public JsonServerReadDBClient beanJsonServerReadDBClient(){
    return new JsonServerReadDBClient();
  }

  @Bean
  public RedisServiceClient beanRedisServiceClient() {
    return new RedisServiceClient();
  }
}
