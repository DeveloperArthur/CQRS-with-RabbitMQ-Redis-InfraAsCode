package br.com.arthur.cqrs.integrationtests;

import br.com.arthur.cqrs.core.gateways.EventHandler;
import br.com.arthur.cqrs.core.service.SalvaVeiculo;
import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.integrationtests.mocks.QueueMessengerMock;
import br.com.arthur.cqrs.integrationtests.mocks.ReadDatabaseMock;
import br.com.arthur.cqrs.integrationtests.mocks.WriteDatabaseMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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
        String id = String.valueOf(UUID.randomUUID());
        veiculo.setId(id);

        SalvaVeiculo command = new SalvaVeiculo(new WriteDatabaseMock(), new QueueMessengerMock(new ReadDatabaseMock()));
        command.write(veiculo);

        //checar se o veiculo existe no banco
        Veiculo veiculoNoBanco = WriteDatabaseMock.veiculosWriteDBMock.get(veiculo.getId());
        Assertions.assertNotNull(veiculoNoBanco);
    }

    @Test
    public void deveEnviarVeiculoParaQueueMessenger(){
        EventHandler eventHandler = mock(EventHandler.class);

        Veiculo veiculo = new Veiculo();
        SalvaVeiculo command = new SalvaVeiculo(new WriteDatabaseMock(), eventHandler);
        command.write(veiculo);

        verify(eventHandler).envia(veiculo);
    }
}
