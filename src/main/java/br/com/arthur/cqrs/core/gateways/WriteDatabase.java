package br.com.arthur.cqrs.core.gateways;

import br.com.arthur.cqrs.core.domain.Veiculo;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface WriteDatabase {
    Veiculo write(Veiculo veiculo) throws JsonProcessingException;
}
