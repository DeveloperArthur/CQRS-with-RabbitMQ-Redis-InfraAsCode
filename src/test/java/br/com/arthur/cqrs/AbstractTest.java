package br.com.arthur.cqrs;

import br.com.arthur.cqrs.core.domain.Veiculo;

public abstract class AbstractTest {

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

}
