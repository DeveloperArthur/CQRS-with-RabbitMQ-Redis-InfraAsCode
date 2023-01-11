package br.com.arthur.cqrs.core.service;

import br.com.arthur.cqrs.core.gateways.QueueMessenger;
import br.com.arthur.cqrs.core.gateways.WriteDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;
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

    public void write(Veiculo veiculo) {
        writeDatabase.write(veiculo);
        queueMessenger.envia(veiculo);
    }
}
