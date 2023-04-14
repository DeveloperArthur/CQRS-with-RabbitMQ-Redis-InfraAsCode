package br.com.arthur.cqrs.unittests.service;

import br.com.arthur.cqrs.AbstractTest;
import br.com.arthur.cqrs.core.gateways.CachingService;
import br.com.arthur.cqrs.core.gateways.ReadDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.service.ConsultaVeiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ConsultaVeiculoTest extends AbstractTest {
    @Mock
    private ReadDatabase readDatabase;
    @Mock
    private CachingService cachingService;
    ConsultaVeiculo consultaVeiculo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.consultaVeiculo = Mockito.spy(new ConsultaVeiculo(readDatabase, cachingService));
    }

    @Test
    public void deveRetornarVeiculoDoCache_quandoTiverVeiculoNoCache(){
        //Arrange
        Mockito.when(cachingService.get(Mockito.anyString()))
            .thenReturn(Optional.of(criaVeiculoFakeComRenavamValido()));

        //Action
        Optional<Veiculo> veiculo = consultaVeiculo.read(ID_VEICULO);

        //Assert
        Assertions.assertEquals(MARCA, veiculo.get().getMarca());
        Assertions.assertEquals(MODELO, veiculo.get().getModelo());
        Assertions.assertEquals(ANO, veiculo.get().getAno());
        Assertions.assertEquals(RENAVAM_VALIDO, veiculo.get().getRenavam());
        Assertions.assertEquals(PLACA, veiculo.get().getPlaca());
        Assertions.assertEquals(COR, veiculo.get().getCor());
        Mockito.verify(readDatabase, Mockito.times(0))
            .read(Mockito.anyString());
    }

    @Test
    public void deveRetornarVeiculoDoBanco_quandoNaoTiverVeiculoNoCache_eTiverVeiculoNoBanco(){
        //Arrange
        Mockito.when(cachingService.get(Mockito.anyString()))
            .thenReturn(Optional.empty());

        Mockito.when(readDatabase.read(Mockito.anyString()))
            .thenReturn(Optional.of(criaVeiculoFakeComRenavamValido()));

        Mockito.doNothing().when(cachingService).salva(Mockito.any());

        //Action
        Optional<Veiculo> veiculo = consultaVeiculo.read(ID_VEICULO);

        //Assert
        Assertions.assertEquals(MARCA, veiculo.get().getMarca());
        Assertions.assertEquals(MODELO, veiculo.get().getModelo());
        Assertions.assertEquals(ANO, veiculo.get().getAno());
        Assertions.assertEquals(RENAVAM_VALIDO, veiculo.get().getRenavam());
        Assertions.assertEquals(PLACA, veiculo.get().getPlaca());
        Assertions.assertEquals(COR, veiculo.get().getCor());
        Mockito.verify(readDatabase, Mockito.times(1))
            .read(Mockito.anyString());
        Mockito.verify(cachingService, Mockito.times(1))
            .salva(Mockito.any());
    }

    @Test
    public void deveRetornarEmpty_quandoNaoTiverVeiculoNoCache_eNaoTiverVeiculoNoBanco(){
        //Arrange
        Mockito.when(cachingService.get(Mockito.anyString()))
            .thenReturn(Optional.empty());

        Mockito.when(readDatabase.read(Mockito.anyString()))
            .thenReturn(Optional.empty());

        //Action
        Optional<Veiculo> veiculo = consultaVeiculo.read(ID_VEICULO);

        //Assert
        Assertions.assertEquals(Optional.empty(), veiculo);
        Mockito.verify(cachingService, Mockito.times(0))
            .salva(Mockito.any());
    }
}
