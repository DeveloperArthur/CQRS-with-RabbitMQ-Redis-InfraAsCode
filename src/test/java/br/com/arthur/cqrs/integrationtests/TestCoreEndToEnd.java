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
        Veiculo veiculo = montaObjetoDeVeiculo();
        salvaVeiculoNoBancoDeEscrita(veiculo);
        verificaSeGravouNoBancoCorretamente(veiculo.getId());
        verificaSeFilaEnviouVeiculoParaBancoDeLeitura(veiculo);
        verificaSeBancosForamSincronizados(veiculo);
        verificaSeVeiculoFoiGravadoNoCache(veiculo);
    }

    private Veiculo montaObjetoDeVeiculo() {
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
        return veiculo;
    }

    private void salvaVeiculoNoBancoDeEscrita(Veiculo veiculo) {
        SalvaVeiculo command = new SalvaVeiculo(new WriteDatabaseMock(), new QueueMessengerMock(new ReadDatabaseMock()));
        command.write(veiculo);
    }

    private void verificaSeGravouNoBancoCorretamente(String id) {
        Veiculo veiculo = WriteDatabaseMock.veiculosWriteDBMock.get(id);
        Assertions.assertNotNull(veiculo);
    }

    private void verificaSeFilaEnviouVeiculoParaBancoDeLeitura(Veiculo veiculo) {
        Assertions.assertNotNull(ReadDatabaseMock.veiculosReadDBMock.get(veiculo.getId()));
    }

    private void verificaSeBancosForamSincronizados(Veiculo veiculo) {
        ConsultaVeiculo query = new ConsultaVeiculo(new ReadDatabaseMock(), new CachingServiceMock());
        Optional<Veiculo> veiculoOptional = query.read(veiculo.getId());
        Assertions.assertNotNull(veiculoOptional.get());
    }

    private void verificaSeVeiculoFoiGravadoNoCache(Veiculo veiculo) {
        Assertions.assertNotNull(CachingServiceMock.veiculosCachingMock.get(veiculo.getId()));
    }






}
