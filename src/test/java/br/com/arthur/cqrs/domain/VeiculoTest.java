package br.com.arthur.cqrs.domain;

import br.com.arthur.cqrs.AbstractTest;
import br.com.arthur.cqrs.core.domain.Veiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VeiculoTest extends AbstractTest {

    @Test
    public void builderDeveCriarVeiculoComSucesso_quandoVeiculoTiverRenavamValido(){
        Assertions.assertDoesNotThrow(() -> {
            Veiculo veiculo = criaVeiculoFakeComRenavamValido();
        });
    }

    @Test
    public void builderNaoDeveCriarVeiculoComSucesso_quandoVeiculoNaoTiverRenavamValido(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Veiculo veiculo = criaVeiculoFakeComRenavamInvalido();
        });
    }

    @Test
    public void deveRetornarRenavamInvalido(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Veiculo veiculo = new Veiculo();
            veiculo.validacaoDigitosRenavam(RENAVAM_INVALIDO);
        });
    }

    @Test
    public void naoDeveRetornarRenavamInvalido(){
        Assertions.assertDoesNotThrow(() -> {
            Veiculo veiculo = new Veiculo();
            veiculo.validacaoDigitosRenavam(RENAVAM_VALIDO);
        });
    }
}
