package br.com.arthur.cqrs;

import br.com.arthur.cqrs.core.gateways.ReadDatabase;
import br.com.arthur.cqrs.infra.caching.redis.RedisServiceClient;
import br.com.arthur.cqrs.infra.dao.readdatabase.JsonServerReadDBClient;
import br.com.arthur.cqrs.infra.event.RabbitMQReceiver;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;

@Configuration
public class TestcontainersConfiguration {
  @Value("${read-database.endpoint}")
  private String endpointDatabase;

  public static final DockerComposeContainer<?> container =
      new DockerComposeContainer<>(new File("docker-compose.yml"))
          .withLocalCompose(true);

  @Bean
  public RabbitMQReceiver beanRabbitMQReceiver(){
    return new RabbitMQReceiver();
  }

  @Bean
  public ReadDatabase beanReadDatabase(){
    return new JsonServerReadDBClient(endpointDatabase, new RestTemplate());
  }

  @Bean
  public RedisServiceClient beanRedisServiceClient() {
    return new RedisServiceClient();
  }
}
