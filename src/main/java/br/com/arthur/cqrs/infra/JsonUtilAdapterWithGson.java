package br.com.arthur.cqrs.infra;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.infra.caching.VeiculoDtoCache;
import br.com.arthur.cqrs.infra.dao.VeiculoDtoDatabase;
import com.google.gson.Gson;

public interface JsonUtilAdapterWithGson {

  static String toJson(Veiculo veiculo) {
    Gson gson = new Gson();
    return gson.toJson(veiculo);
  }

  static String toJson(VeiculoDtoCache veiculo) {
    Gson gson = new Gson();
    return gson.toJson(veiculo);
  }

  static String toJson(VeiculoDtoDatabase veiculo) {
    Gson gson = new Gson();
    return gson.toJson(veiculo);
  }

  static Veiculo veiculofromJson(String veiculoJson) {
    Gson gson = new Gson();
    return gson.fromJson(veiculoJson, Veiculo.class);
  }

  static VeiculoDtoCache veiculoDtoCachefromJson(String veiculoJson) {
    Gson gson = new Gson();
    return gson.fromJson(veiculoJson, VeiculoDtoCache.class);
  }
}
