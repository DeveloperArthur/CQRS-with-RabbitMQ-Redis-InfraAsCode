package br.com.arthur.cqrs.integrationtests.mocks;

import br.com.arthur.cqrs.core.gateways.WriteDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;

import java.util.HashMap;
import java.util.Map;

public class WriteDatabaseMock implements WriteDatabase {
    public static Map<String, Veiculo> veiculosWriteDBMock = new HashMap<>();
    @Override
    public Veiculo write(Veiculo veiculo) {
        veiculosWriteDBMock.put(veiculo.getPlaca(), veiculo);
        return veiculo;
    }
}
