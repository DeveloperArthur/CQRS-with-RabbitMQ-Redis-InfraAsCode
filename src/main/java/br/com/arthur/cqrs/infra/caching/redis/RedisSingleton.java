package br.com.arthur.cqrs.infra.caching.redis;
import redis.clients.jedis.Jedis;

public class RedisSingleton {

    private static Jedis instancia = null;

    public static Jedis getInstancia(String hostname, String port){
        if (instancia == null){
            try {
                instancia = new Jedis(hostname, Integer.parseInt(port));
                System.out.println("conectado no redis com sucesso");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instancia;
    }
}
