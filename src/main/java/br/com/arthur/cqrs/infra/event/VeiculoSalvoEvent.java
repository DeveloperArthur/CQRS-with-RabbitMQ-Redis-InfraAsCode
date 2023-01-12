package br.com.arthur.cqrs.core.gateways;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.infra.queue.RabbitMQSender;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;

@EnableRabbit
public class VeiculoSalvoEventHandler implements EventHandler {
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
