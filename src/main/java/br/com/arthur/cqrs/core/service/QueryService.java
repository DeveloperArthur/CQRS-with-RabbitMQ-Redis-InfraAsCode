package br.com.arthur.cqrs.core.service;

import br.com.arthur.cqrs.adapters.CachingService;
import br.com.arthur.cqrs.adapters.ReadDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;

public class QueryService {
    private ReadDatabase readDatabase;
    private CachingService cachingService;

    public QueryService(ReadDatabase readDatabase, CachingService cachingService) {
        this.readDatabase = readDatabase;
        this.cachingService = cachingService;
    }

    public Veiculo read(String placa) {
        return readDatabase.read(placa);
    }
}
