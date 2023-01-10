package br.com.arthur.cqrs.integrationtests.mocks;

import br.com.arthur.cqrs.adapters.WriteDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;

public class WriteDatabaseMock implements WriteDatabase {
    @Override
    public void write(Veiculo veiculo) {
        VeiculosDatabaseMock.veiculosDBMock.put(veiculo.getPlaca(), veiculo);
    }
}
