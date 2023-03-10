package br.com.arthur.cqrs.integrationtests.mocks;

import br.com.arthur.cqrs.core.gateways.ReadDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReadDatabaseMock implements ReadDatabase {
    public static Map<String, Veiculo> veiculosReadDBMock = new HashMap<>();
    @Override
    public Optional<Veiculo> read(String id) {
        return Optional.of(veiculosReadDBMock.get(id));
    }

    @Override
    public void sincronizaBancos(Veiculo veiculo) {
        ReadDatabaseMock.veiculosReadDBMock.put(veiculo.getId(), veiculo);
    }
}
