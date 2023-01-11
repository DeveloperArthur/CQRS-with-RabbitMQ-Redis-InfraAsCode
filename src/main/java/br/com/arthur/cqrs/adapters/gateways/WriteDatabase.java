package br.com.arthur.cqrs.adapters.gateways;

import br.com.arthur.cqrs.core.domain.Veiculo;

public interface WriteDatabase {
    void write(Veiculo veiculo);
}
