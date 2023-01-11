package br.com.arthur.cqrs.integrationtests;

import br.com.arthur.cqrs.adapters.gateways.QueueMessenger;
import br.com.arthur.cqrs.core.service.SalvaVeiculo;
import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.integrationtests.mocks.QueueMessengerMock;
import br.com.arthur.cqrs.integrationtests.mocks.WriteDatabaseMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SalvaVeiculoTest {

    @Test
    public void deveGravarVeiculoNoWriteDatabase(){
        Veiculo veiculo = new Veiculo.Builder()
                .comMarca("CHERY")
                .comModelo("Tiggo 2.0 16V Aut. 5p")
                .comAno("2013")
                .comRenavam("63843842707")
                .comPlaca("IAL-0989")
                .comCor("Amarelo")
                .build();

        SalvaVeiculo command = new SalvaVeiculo(new WriteDatabaseMock(), new QueueMessengerMock());
        command.write(veiculo);

        //checar se o veiculo existe no banco
        Veiculo veiculoNoBanco = WriteDatabaseMock.veiculosWriteDBMock.get(veiculo.getPlaca());
        Assertions.assertNotNull(veiculoNoBanco);
    }

    @Test
    public void deveEnviarVeiculoParaQueueMessenger(){
        QueueMessenger queueMessenger = mock(QueueMessenger.class);

        Veiculo veiculo = new Veiculo();
        SalvaVeiculo command = new SalvaVeiculo(new WriteDatabaseMock(), queueMessenger);
        command.write(veiculo);

        verify(queueMessenger).envia(veiculo);
    }
}
