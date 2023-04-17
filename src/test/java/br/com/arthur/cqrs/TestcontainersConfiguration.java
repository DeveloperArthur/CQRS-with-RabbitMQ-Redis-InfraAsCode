package br.com.arthur.cqrs;

import br.com.arthur.cqrs.core.service.ConsultaVeiculo;
import br.com.arthur.cqrs.core.service.SalvaVeiculo;
import br.com.arthur.cqrs.infra.caching.redis.RedisServiceClient;
import br.com.arthur.cqrs.infra.dao.JsonServerReadDBClient;
import br.com.arthur.cqrs.infra.dao.JsonServerWriteDBClient;
import br.com.arthur.cqrs.infra.event.RabbitMQSender;
import br.com.arthur.cqrs.infra.event.VeiculoSalvoEvent;
import java.io.File;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.DockerComposeContainer;

@Configuration
public class TestcontainersConfiguration {
  @Value("${spring.rabbitmq.host}")
  private String RABBITMQ_HOST;
  @Value("${spring.rabbitmq.port}")
  private String RABBITMQ_PORT;
  @Value("${spring.rabbitmq.username}")
  private String RABBITMQ_USER;
  @Value("${spring.rabbitmq.password}")
  private String RABBITMQ_PASS;
  @Value("${spring.rabbitmq.queue}")
  private String RABBITMQ_QUEUE;

  public static final DockerComposeContainer<?> container =
      new DockerComposeContainer<>(new File("docker-compose.yml"))
          .withLocalCompose(true);

  @Bean
  public RabbitMQSender beanRabbitMQSender(){
    return new RabbitMQSender();
  }

  @Bean
  public RabbitTemplate beanRabbitTemplate() throws Exception {
    RabbitTemplate template = new RabbitTemplate();
    template.setConnectionFactory(beanConnectionFactory());
    return template;
  }

  @Bean
  public ConnectionFactory beanConnectionFactory() throws Exception {
    CachingConnectionFactory factory = new CachingConnectionFactory();
    factory.setHost(RABBITMQ_HOST);
    factory.setPort(Integer.parseInt(RABBITMQ_PORT));
    factory.setUsername(RABBITMQ_USER);
    factory.setPassword(RABBITMQ_PASS);
    return factory;
  }

  @Bean
  public Queue beanQueue(){
    return new Queue(RABBITMQ_QUEUE);
  }

  @Bean
  public SalvaVeiculo beanSalvaVeiculo() {
    return new SalvaVeiculo(beanJsonServerWriteDBClient(), beanVeiculoSalvoEvent());
  }

  @Bean
  public JsonServerWriteDBClient beanJsonServerWriteDBClient(){
    return new JsonServerWriteDBClient();
  }

  @Bean
  public VeiculoSalvoEvent beanVeiculoSalvoEvent(){
    return new VeiculoSalvoEvent();
  }

  @Bean
  public ConsultaVeiculo beanConsultaVeiculo() {
    return new ConsultaVeiculo(beanJsonServerReadDBClient(), beanRedisServiceClient());
  }

  @Bean
  public RedisServiceClient beanRedisServiceClient() {
    return new RedisServiceClient();
  }

  @Bean
  public JsonServerReadDBClient beanJsonServerReadDBClient(){
    return new JsonServerReadDBClient();
  }

}
