package br.com.arthur.cqrs.service;

import br.com.arthur.cqrs.AbstractTest;
import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.gateways.EventHandler;
import br.com.arthur.cqrs.core.gateways.WriteDatabase;
import br.com.arthur.cqrs.core.service.SalvaVeiculo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SalvaVeiculoTest extends AbstractTest {
  @Mock
  private WriteDatabase writeDatabase;
  @Mock
  private EventHandler eventoDeVeiculoSalvo;
  SalvaVeiculo salvaVeiculo;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    this.salvaVeiculo = Mockito.spy(new SalvaVeiculo(writeDatabase, eventoDeVeiculoSalvo));
  }

  @Test
  public void deveEnviarEvento_quandoSalvarVeiculoComSucesso() throws JsonProcessingException {
    Mockito.doNothing().when(eventoDeVeiculoSalvo).envia(Mockito.any());

    Veiculo veiculoMock = criaVeiculoFakeComRenavamValido();
    Mockito.when(writeDatabase.write(Mockito.any()))
        .thenReturn(veiculoMock);

    salvaVeiculo.write(veiculoMock);

    Mockito.verify(eventoDeVeiculoSalvo, Mockito
        .times(1)).envia(Mockito.any());
  }

  @Test
  public void deveLancarRuntimeException_quandoVeiculoRetornadoDoBancoForNulo(){
    Assertions.assertThrows(RuntimeException.class, () -> {
      Mockito.doNothing().when(eventoDeVeiculoSalvo).envia(Mockito.any());

      Mockito.when(writeDatabase.write(Mockito.any()))
          .thenReturn(null);

      Veiculo veiculoMock = criaVeiculoFakeComRenavamValido();
      salvaVeiculo.write(veiculoMock);
    });
  }
}