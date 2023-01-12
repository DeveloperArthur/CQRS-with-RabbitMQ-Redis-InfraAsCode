package br.com.arthur.cqrs.core.gateways;

import br.com.arthur.cqrs.core.domain.Veiculo;

import java.util.Optional;

public interface ReadDatabase {
    Optional<Veiculo> read(String id);

    void sincronizaBancos(Veiculo veiculo);
}
