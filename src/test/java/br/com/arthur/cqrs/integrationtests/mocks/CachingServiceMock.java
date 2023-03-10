package br.com.arthur.cqrs.integrationtests.mocks;

import br.com.arthur.cqrs.core.gateways.CachingService;
import br.com.arthur.cqrs.core.domain.Veiculo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CachingServiceMock implements CachingService {
    public static Map<String, Veiculo> veiculosCachingMock = new HashMap<>();

    @Override
    public Optional<Veiculo> get(String id) {
        Veiculo veiculo = veiculosCachingMock.get(id);
        if (veiculo == null) return Optional.empty();
        return Optional.of(veiculo);
    }

    @Override
    public void salva(Veiculo veiculo) {
        veiculosCachingMock.put(veiculo.getId(), veiculo);
    }
}
