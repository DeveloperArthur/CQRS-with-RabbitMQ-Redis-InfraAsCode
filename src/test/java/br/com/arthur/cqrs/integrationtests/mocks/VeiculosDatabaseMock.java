package br.com.arthur.cqrs.integrationtests.mocks;

import br.com.arthur.cqrs.core.domain.Veiculo;

import java.util.HashMap;
import java.util.Map;

public class VeiculosDatabaseMock {
    public static Map<String, Veiculo> veiculosDBMock = new HashMap<>();
}
