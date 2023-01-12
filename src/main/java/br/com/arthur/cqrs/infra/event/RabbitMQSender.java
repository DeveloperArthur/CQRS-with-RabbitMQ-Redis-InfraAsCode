package br.com.arthur.cqrs.infra.event;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RabbitMQSender {
    @Autowired
    private Queue queue;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //método com programação reativa e nao bloqueante
    public void notifyAsync(String veiculo){
        //somente 1 task será executada
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(createTask(veiculo));
    }

    private Runnable createTask(String veiculo) {
        return new Runnable() {
            @Override
            public void run() {
                sendMessage(veiculo);
            }
        };
    }

    public void sendMessage(String message){
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }
}
