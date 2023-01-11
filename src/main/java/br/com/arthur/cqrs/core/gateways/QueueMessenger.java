package br.com.arthur.cqrs.core.gateways;

import br.com.arthur.cqrs.core.domain.Veiculo;

public interface QueueMessenger {
    void envia(Veiculo veiculo);
}
