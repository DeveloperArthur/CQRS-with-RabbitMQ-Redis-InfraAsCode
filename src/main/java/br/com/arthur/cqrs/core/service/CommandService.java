package br.com.arthur.cqrs.core.service;

import br.com.arthur.cqrs.adapters.gateways.QueueMessenger;
import br.com.arthur.cqrs.adapters.gateways.WriteDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;

public class CommandService {
    private WriteDatabase writeDatabase;
    private QueueMessenger queueMessenger;

    public CommandService(WriteDatabase writeDatabase, QueueMessenger queueMessenger) {
        this.writeDatabase = writeDatabase;
        this.queueMessenger = queueMessenger;
    }

    public void write(Veiculo veiculo) {
        writeDatabase.write(veiculo);
        queueMessenger.envia(veiculo);
    }
}
