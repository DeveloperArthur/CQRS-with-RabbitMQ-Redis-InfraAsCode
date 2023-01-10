package br.com.arthur.cqrs.integrationtests.mocks;

import br.com.arthur.cqrs.adapters.QueueMessenger;
import br.com.arthur.cqrs.core.domain.Veiculo;

public class QueueMessengerMock implements QueueMessenger {
    @Override
    public void envia(Veiculo veiculo) {

    }
}