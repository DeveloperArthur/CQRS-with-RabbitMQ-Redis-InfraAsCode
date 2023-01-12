package br.com.arthur.cqrs.infra.event;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.EventHandler;
import com.google.gson.Gson;
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
        Gson gson = new Gson();
        String veiculoEmJson = gson.toJson(veiculo);
        rabbitMQSender.notifyAsync(veiculoEmJson);
    }
}
