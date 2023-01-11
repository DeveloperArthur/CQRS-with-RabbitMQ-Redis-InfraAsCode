package br.com.arthur.cqrs.integrationtests;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.service.CommandService;
import br.com.arthur.cqrs.core.service.QueryService;
import br.com.arthur.cqrs.integrationtests.mocks.CachingServiceMock;
import br.com.arthur.cqrs.integrationtests.mocks.QueueMessengerMock;
import br.com.arthur.cqrs.integrationtests.mocks.ReadDatabaseMock;
import br.com.arthur.cqrs.integrationtests.mocks.WriteDatabaseMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TestCoreEndToEnd {
    @Test
    public void test(){
        Veiculo veiculo = new Veiculo.Builder()
                .comMarca("GREAT WALL")
                .comModelo("HOVER CUV 2.4 16V 5p Mec.")
                .comAno("2008")
                .comRenavam("02115064318")
                .comPlaca("JTH-7774")
                .comCor("Branco")
                .build();

        CommandService command = new CommandService(new WriteDatabaseMock(), new QueueMessengerMock());
        command.write(veiculo);

        //checa se gravou no banco de write
        Assertions.assertNotNull(WriteDatabaseMock.veiculosWriteDBMock.get(veiculo.getPlaca()));
        //checa se a fila enviou o veiculo para o banco de read
        Assertions.assertNotNull(ReadDatabaseMock.veiculosReadDBMock.get(veiculo.getPlaca()));

        QueryService query = new QueryService(new ReadDatabaseMock(), new CachingServiceMock());
        Optional<Veiculo> veiculoOptional = query.read(veiculo.getPlaca());

        //checa veiculo no banco de read, nao deve ser nulo
        Assertions.assertNotNull(veiculoOptional.get());
        //checa se veiculo foi gravado no cache
        Assertions.assertNotNull(CachingServiceMock.veiculosCachingMock.get(veiculo.getPlaca()));
    }
}
