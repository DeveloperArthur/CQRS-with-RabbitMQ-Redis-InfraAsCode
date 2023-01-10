package br.com.arthur.cqrs.adapters;

import br.com.arthur.cqrs.core.domain.Veiculo;

public interface ReadDatabase {
    Veiculo read(String placa);
}
