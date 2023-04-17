package br.com.arthur.cqrs.infra.caching.redis;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.CachingService;
import br.com.arthur.cqrs.infra.caching.VeiculoDtoCache;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Optional;

@Component
@Qualifier("RedisServiceClient")
public class RedisServiceClient implements CachingService {
    @Value("${hostname}")
    private String hostname;

    @Value("${spring.redis.port}")
    private String port;

    @Override
    public Optional<Veiculo> get(String id) {
        try {
            Jedis jedis = RedisSingleton.getInstancia(hostname, port);
            System.out.println("Buscando do cache");
            String veiculoEmJson = jedis.get(id);

            Gson gson = new Gson();
            VeiculoDtoCache veiculoRetornadoPeloCache = gson.fromJson(veiculoEmJson, VeiculoDtoCache.class);

            if (veiculoRetornadoPeloCache == null) return Optional.empty();

            Veiculo veiculo = veiculoRetornadoPeloCache.converte();
            return Optional.of(veiculo);
        }catch (Exception e){
            System.err.println("Ocorreu um erro ao tentar buscar veiculo no Redis");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void salva(Veiculo veiculo) {
        try {
            VeiculoDtoCache veiculoCache = new VeiculoDtoCache(veiculo);
            Jedis jedis = RedisSingleton.getInstancia(hostname, port);

            Gson gson = new Gson();
            String veiculoEmJson = gson.toJson(veiculo);

            jedis.set(veiculoCache.getId(), veiculoEmJson);
            System.out.println("Salvo no cache com sucesso");
        } catch (Exception e){
            System.err.println("Ocorreu um erro ao tentar salvar veiculo no Redis");
            throw new RuntimeException(e);
        }
    }
}
