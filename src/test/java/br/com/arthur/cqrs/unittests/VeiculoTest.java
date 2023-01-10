package br.com.arthur.cqrs.unittests;

import br.com.arthur.cqrs.domain.Veiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VeiculoTest {

    @Test
    public void builderDeveCriarVeiculoComSucesso(){
        Veiculo veiculo = new Veiculo.Builder()
                .comMarca("CHERY")
                .comModelo("Tiggo 2.0 16V Aut. 5p")
                .comAno("2013")
                .comRenavam("63843842707")
                .comPlaca("IAL-0989")
                .comCor("Amarelo")
                .build();

        Assertions.assertEquals("CHERY", veiculo.getMarca());
        Assertions.assertEquals("Tiggo 2.0 16V Aut. 5p", veiculo.getModelo());
        Assertions.assertEquals("2013", veiculo.getAno());
        Assertions.assertEquals("63843842707", veiculo.getRenavam());
        Assertions.assertEquals("IAL-0989", veiculo.getPlaca());
        Assertions.assertEquals("Amarelo", veiculo.getCor());
    }

    @Test
    public void builderNaoDeveCriarVeiculoComSucesso(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Veiculo veiculo = new Veiculo.Builder()
                    .comMarca("CHERY")
                    .comModelo("Tiggo 2.0 16V Aut. 5p")
                    .comAno("2013")
                    .comRenavam("63")
                    .comPlaca("IAL-0989")
                    .comCor("Amarelo")
                    .build();
        });
    }

    @Test
    public void deveRetornarRenavamInvalido(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Veiculo veiculo = new Veiculo();
            veiculo.validacaoDigitosRenavam("123");
        });
    }

    @Test
    public void naoDeveRetornarRenavamInvalido(){
        Assertions.assertDoesNotThrow(() -> {
            Veiculo veiculo = new Veiculo();
            veiculo.validacaoDigitosRenavam("12345678900");
        });
    }
}
