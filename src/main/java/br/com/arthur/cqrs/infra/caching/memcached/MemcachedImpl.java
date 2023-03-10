package br.com.arthur.cqrs.infra.caching.memcached;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.CachingService;
import br.com.arthur.cqrs.infra.caching.VeiculoDtoCache;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Qualifier("MemcachedImpl")
public class MemcachedImpl implements CachingService {
    @Value("${hostname}")
    private String hostname;

    @Override
    public Optional<Veiculo> get(String id) {
        System.out.println("Buscando do cache");
        MemcachedClient memcached = MemcachedSingleton.getInstancia(hostname);
        VeiculoDtoCache veiculoRetornadoPeloCache = (VeiculoDtoCache) memcached.get(id);
        if (veiculoRetornadoPeloCache == null) return Optional.empty();

        Veiculo veiculo = veiculoRetornadoPeloCache.converte();
        return Optional.of(veiculo);
    }

    @Override
    public void salva(Veiculo veiculo) {
        VeiculoDtoCache veiculoCache = new VeiculoDtoCache(veiculo);
        MemcachedClient memcached = MemcachedSingleton.getInstancia(hostname);
        memcached.set(veiculoCache.getId(), 86400, veiculoCache).isDone();
        System.out.println("Salvo no cache com sucesso");
    }
}
