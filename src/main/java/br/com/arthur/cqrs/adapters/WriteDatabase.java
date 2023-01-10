package br.com.arthur.cqrs.adapters;

import br.com.arthur.cqrs.core.domain.Veiculo;

public interface WriteDatabase {
    void write(Veiculo veiculo);
}
