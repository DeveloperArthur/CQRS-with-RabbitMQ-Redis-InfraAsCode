package br.com.arthur.cqrs;

import br.com.arthur.cqrs.core.domain.Veiculo;
import org.junit.jupiter.api.Assertions;

public abstract class AbstractTest {
  protected static final String ID_VEICULO = "id_veiculo";
  protected static final String MARCA = "CHERY";
  protected static final String MODELO = "Tiggo 2.0 16V Aut. 5p";
  protected static final String ANO = "2013";
  protected static final String RENAVAM_VALIDO = "63843842707";
  protected static final String RENAVAM_INVALIDO = "63";
  protected static final String PLACA = "IAL-0989";
  protected static final String COR = "Amarelo";

  protected Veiculo criaVeiculoFakeComRenavamValido(){
    return new Veiculo.Builder()
        .comMarca(MARCA)
        .comModelo(MODELO)
        .comAno(ANO)
        .comRenavam(RENAVAM_VALIDO)
        .comPlaca(PLACA)
        .comCor(COR)
        .build();
  }

  protected Veiculo criaVeiculoFakeComRenavamInvalido(){
    return new Veiculo.Builder()
        .comMarca(MARCA)
        .comModelo(MODELO)
        .comAno(ANO)
        .comRenavam(RENAVAM_INVALIDO)
        .comPlaca(PLACA)
        .comCor(COR)
        .build();
  }

  protected String criaVeiculoEmJson(){
    return "{\n"
        + "    \"id\": \"id_veiculo\",\n"
        + "    \"marca\": \"CHERY\",\n"
        + "    \"modelo\": \"Tiggo 2.0 16V Aut. 5p\",\n"
        + "    \"ano\": \"2013\",\n"
        + "    \"renavam\": \"63843842707\",\n"
        + "    \"placa\": \"IAL-0989\",\n"
        + "    \"cor\": \"Amarelo\"\n"
        + "}";
  }

  protected void assertEqualsVeiculo(Veiculo expected, Veiculo actual){
    Assertions.assertEquals(expected.getId(), actual.getId());
    Assertions.assertEquals(expected.getMarca(), actual.getMarca());
    Assertions.assertEquals(expected.getModelo(), actual.getModelo());
    Assertions.assertEquals(expected.getAno(), actual.getAno());
    Assertions.assertEquals(expected.getRenavam(), actual.getRenavam());
    Assertions.assertEquals(expected.getPlaca(), actual.getPlaca());
    Assertions.assertEquals(expected.getCor(), actual.getCor());
  }

}
