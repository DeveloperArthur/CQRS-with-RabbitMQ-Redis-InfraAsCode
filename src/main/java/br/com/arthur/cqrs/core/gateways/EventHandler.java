package br.com.arthur.cqrs.core.gateways;

import br.com.arthur.cqrs.core.domain.Veiculo;

public interface EventHandler {
    void envia(Veiculo veiculo);
}
