package br.com.arthur.cqrs.infra.event;


import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.queue}")
    private String QUEUE_NAME;

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME);
    }

    public String getQueueName(){
        return QUEUE_NAME;
    }
}
