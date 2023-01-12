package br.com.arthur.cqrs.integrationtests;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.service.SalvaVeiculo;
import br.com.arthur.cqrs.core.service.ConsultaVeiculo;
import br.com.arthur.cqrs.integrationtests.mocks.CachingServiceMock;
import br.com.arthur.cqrs.integrationtests.mocks.QueueMessengerMock;
import br.com.arthur.cqrs.integrationtests.mocks.ReadDatabaseMock;
import br.com.arthur.cqrs.integrationtests.mocks.WriteDatabaseMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

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
        String id = String.valueOf(UUID.randomUUID());
        veiculo.setId(id);

        SalvaVeiculo command = new SalvaVeiculo(new WriteDatabaseMock(), new QueueMessengerMock(new ReadDatabaseMock()));
        command.write(veiculo);

        //checa se gravou no banco de write
        Assertions.assertNotNull(WriteDatabaseMock.veiculosWriteDBMock.get(veiculo.getId()));
        //checa se a fila enviou o veiculo para o banco de read
        Assertions.assertNotNull(ReadDatabaseMock.veiculosReadDBMock.get(veiculo.getId()));

        ConsultaVeiculo query = new ConsultaVeiculo(new ReadDatabaseMock(), new CachingServiceMock());
        Optional<Veiculo> veiculoOptional = query.read(veiculo.getId());

        //checa veiculo no banco de read, nao deve ser nulo
        Assertions.assertNotNull(veiculoOptional.get());
        //checa se veiculo foi gravado no cache
        Assertions.assertNotNull(CachingServiceMock.veiculosCachingMock.get(veiculo.getId()));
    }
}
