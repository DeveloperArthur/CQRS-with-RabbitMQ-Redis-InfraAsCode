package br.com.arthur.cqrs.core.service;

import br.com.arthur.cqrs.core.gateways.QueueMessenger;
import br.com.arthur.cqrs.core.gateways.WriteDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: tirar essa dependencia de rabbitmq
@Service
@EnableRabbit
public class SalvaVeiculo {
    private WriteDatabase writeDatabase;
    private QueueMessenger queueMessenger;

    @Autowired
    public SalvaVeiculo(WriteDatabase writeDatabase, QueueMessenger queueMessenger) {
        this.writeDatabase = writeDatabase;
        this.queueMessenger = queueMessenger;
    }

    public Veiculo write(Veiculo veiculo) {
        Veiculo veiculoSalvo = null;

        try {
            veiculoSalvo = writeDatabase.write(veiculo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (veiculoSalvo == null) throw new RuntimeException("Não foi possível salvar o veículo");
        queueMessenger.envia(veiculoSalvo);

        return veiculoSalvo;
    }
}
