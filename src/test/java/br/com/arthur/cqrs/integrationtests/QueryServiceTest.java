package br.com.arthur.cqrs.integrationtests;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.service.QueryService;
import br.com.arthur.cqrs.integrationtests.mocks.CachingServiceMock;
import br.com.arthur.cqrs.integrationtests.mocks.ReadDatabaseMock;
import br.com.arthur.cqrs.integrationtests.mocks.VeiculosDatabaseMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueryServiceTest {

    @Test
    public void deveConsultarVeiculoNoReadDatabase(){
        //insere no banco mock só pra conseguir consultar
        Veiculo veiculoInserido = new Veiculo.Builder()
                .comMarca("CHERY")
                .comModelo("Tiggo 2.0 16V Aut. 5p")
                .comAno("2013")
                .comRenavam("63843842707")
                .comPlaca("IAL-0989")
                .comCor("Amarelo")
                .build();
        VeiculosDatabaseMock.veiculosDBMock.put(veiculoInserido.getPlaca(), veiculoInserido);

        QueryService query = new QueryService(new ReadDatabaseMock(), new CachingServiceMock());
        Veiculo veiculo = query.read("IAL-0989");

        Assertions.assertNotNull(veiculo);
    }

    @Test
    public void devePegarVeiculoDoCache(){
        //funcao do banco de dados nao deve ser chamada
        Assertions.assertTrue(false);
    }

    @Test
    public void naoDevePegarVeiculoDoCache(){
        //funcao do banco de dados deve ser chamada
        Assertions.assertTrue(false);
    }
}
