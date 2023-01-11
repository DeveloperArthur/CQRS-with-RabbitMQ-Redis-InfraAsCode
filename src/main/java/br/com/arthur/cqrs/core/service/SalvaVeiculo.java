package br.com.arthur.cqrs.core.service;

import br.com.arthur.cqrs.core.gateways.QueueMessenger;
import br.com.arthur.cqrs.core.gateways.WriteDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
            if (veiculoSalvo == null) throw new RuntimeException("Não foi possível salvar o veículo");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        queueMessenger.envia(veiculoSalvo);
        return veiculoSalvo;
    }
}
