package br.com.arthur.cqrs.integrationtests.mocks;

import br.com.arthur.cqrs.adapters.ReadDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;

public class ReadDatabaseMock implements ReadDatabase {
    @Override
    public Veiculo read(String placa) {
        return VeiculosDatabaseMock.veiculosDBMock.get(placa);
    }
}
