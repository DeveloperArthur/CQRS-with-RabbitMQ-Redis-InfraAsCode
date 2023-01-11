package br.com.arthur.cqrs.adapters;

import br.com.arthur.cqrs.core.domain.Veiculo;

import java.util.Optional;

public interface CachingService {
    Optional<Veiculo> get(String placa);
    void salva(Veiculo veiculo);
}
