package br.com.arthur.cqrs.infra.event;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.EventHandler;
import br.com.arthur.cqrs.infra.JsonUtilAdapterWithGson;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class VeiculoSalvoEvent implements EventHandler {
    @Autowired
    RabbitMQSender rabbitMQSender;

    @Override
    public void envia(Veiculo veiculo) {
        System.out.println("Enviando mensagem para RabbitMQ");
        String veiculoEmJson = JsonUtilAdapterWithGson.toJson(veiculo);
        rabbitMQSender.notifyAsync(veiculoEmJson);
    }
}
