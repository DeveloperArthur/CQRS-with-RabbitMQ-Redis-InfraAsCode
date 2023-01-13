package br.com.arthur.cqrs.core.service;

import br.com.arthur.cqrs.core.gateways.CachingService;
import br.com.arthur.cqrs.core.gateways.ReadDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultaVeiculo {
    private ReadDatabase readDatabase;
    private CachingService cachingService;

    @Autowired
    public ConsultaVeiculo(ReadDatabase readDatabase, @Qualifier("RedisServiceClient") CachingService cachingService) {
        this.readDatabase = readDatabase;
        this.cachingService = cachingService;
    }

    public Optional<Veiculo> read(String id) {
        Optional<Veiculo> veiculoNoCache = cachingService.get(id);
        if (veiculoNoCache.isEmpty()){
            Optional<Veiculo> veiculoNoBanco = readDatabase.read(id);
            if (veiculoNoBanco.isPresent()) {
                cachingService.salva(veiculoNoBanco.get());
                return veiculoNoBanco;
            }
        } else return veiculoNoCache;
        return Optional.empty();
    }
}
