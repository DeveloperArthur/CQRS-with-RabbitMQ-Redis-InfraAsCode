package br.com.arthur.cqrs.integrationtests;

import br.com.arthur.cqrs.core.gateways.CachingService;
import br.com.arthur.cqrs.core.gateways.ReadDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.service.ConsultaVeiculo;
import br.com.arthur.cqrs.integrationtests.mocks.CachingServiceMock;
import br.com.arthur.cqrs.integrationtests.mocks.ReadDatabaseMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ConsultaVeiculoTest {

    @Test
    public void deveConsultarVeiculoNoReadDatabaseESalvarNoCache(){
        //insere no banco mock só pra conseguir consultar
        Veiculo veiculoInserido = new Veiculo.Builder()
                .comMarca("Toyota")
                .comModelo("Hilux SW4 4x4 3.0 12V V6")
                .comAno("1993")
                .comRenavam("09668776149")
                .comPlaca("MBY-1670")
                .comCor("Preto")
                .build();
        String idGerado = String.valueOf(UUID.randomUUID());
        veiculoInserido.setId(idGerado);
        ReadDatabaseMock.veiculosReadDBMock.put(veiculoInserido.getId(), veiculoInserido);

        CachingService cachingService = mock(CachingService.class);

        ConsultaVeiculo query = new ConsultaVeiculo(new ReadDatabaseMock(), cachingService);
        Optional<Veiculo> veiculoOptional = query.read(idGerado);

        Assertions.assertNotNull(veiculoOptional.get());
        verify(cachingService).salva(veiculoOptional.get());
    }

    @Test
    public void devePegarVeiculoDoCacheENaoChamarReadDatabase(){
        //salvar no mock do banco
        Veiculo veiculoInserido = new Veiculo.Builder()
                .comMarca("Hyundai")
                .comModelo("HB20 Copa do Mundo 1.0 Flex 12V Mec.")
                .comAno("2014")
                .comRenavam("58270760239")
                .comPlaca("KDC-1191")
                .comCor("Branco")
                .build();
        String idGerado = String.valueOf(UUID.randomUUID());
        veiculoInserido.setId(idGerado);
        CachingServiceMock.veiculosCachingMock.put(veiculoInserido.getId(), veiculoInserido);

        ReadDatabase readDatabase = mock(ReadDatabase.class);

        ConsultaVeiculo query = new ConsultaVeiculo(readDatabase, new CachingServiceMock());
        Optional<Veiculo> veiculoOptional = query.read(idGerado);

        Assertions.assertNotNull(veiculoOptional.get());
        verify(readDatabase, never()).read("KDC-1191");
    }
}
