package br.com.arthur.cqrs.integrationtests.mocks;

import br.com.arthur.cqrs.core.gateways.EventHandler;
import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.ReadDatabase;

public class QueueMessengerMock implements EventHandler {
    private ReadDatabaseMock readDatabase;

    public QueueMessengerMock(ReadDatabase readDatabase){
        this.readDatabase = (ReadDatabaseMock) readDatabase;
    }

    @Override
    public void envia(Veiculo veiculo) {
        readDatabase.veiculosReadDBMock.put(veiculo.getId(), veiculo);
    }
}