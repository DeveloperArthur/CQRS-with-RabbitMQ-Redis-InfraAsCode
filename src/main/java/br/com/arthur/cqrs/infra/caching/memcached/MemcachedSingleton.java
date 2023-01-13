package br.com.arthur.cqrs.infra.caching.memcached;

import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MemcachedSingleton {
    private static MemcachedClient instancia = null;

    public static MemcachedClient getInstancia(String hostname){
        if (instancia == null){
            try {
                instancia = new MemcachedClient(new InetSocketAddress(hostname, 11211));
                System.out.println("conectado no memcached com sucesso");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instancia;
    }
}
