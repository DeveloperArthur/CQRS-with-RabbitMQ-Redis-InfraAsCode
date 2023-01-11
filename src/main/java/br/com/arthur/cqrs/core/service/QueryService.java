package br.com.arthur.cqrs.core.service;

import br.com.arthur.cqrs.adapters.gateways.CachingService;
import br.com.arthur.cqrs.adapters.gateways.ReadDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;

import java.util.Optional;

public class QueryService {
    private ReadDatabase readDatabase;
    private CachingService cachingService;

    public QueryService(ReadDatabase readDatabase, CachingService cachingService) {
        this.readDatabase = readDatabase;
        this.cachingService = cachingService;
    }

    public Optional<Veiculo> read(String placa) {
        Optional<Veiculo> veiculoOptional;
        veiculoOptional = cachingService.get(placa);
        if (veiculoOptional.isPresent()){
            return veiculoOptional;
        } else {
            veiculoOptional = readDatabase.read(placa);
            if (veiculoOptional.isPresent()){
                cachingService.salva(veiculoOptional.get());
                return veiculoOptional;
            }
        }
        return Optional.empty();
    }
}
