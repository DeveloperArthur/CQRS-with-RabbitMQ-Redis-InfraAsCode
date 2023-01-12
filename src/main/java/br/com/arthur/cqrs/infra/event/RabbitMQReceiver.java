package br.com.arthur.cqrs.infra.event;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.ReadDatabase;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {
    @Autowired
    ReadDatabase readDatabase;

    @RabbitListener(queues = "#{rabbitMQConfig.getQueueName()}")
    public void receiveManage(@Payload String message){
        System.out.println("Mensagem do RabbitMQ recebida");
        System.out.println("processando...");
        Gson gson = new Gson();
        Veiculo veiculo = gson.fromJson(message, Veiculo.class);
        readDatabase.sincronizaBancos(veiculo);
    }
}
