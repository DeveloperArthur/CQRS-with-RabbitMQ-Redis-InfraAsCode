package br.com.arthur.cqrs.integrationtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestEndToEnd {
    @Test
    public void testCoreEndToEnd(){
        //enviar veiculo pro command, ver se gravou no banco
        //ver se chamou a fila
        //ver se veiculo chegou no banco de read
        //consultar veiculo no banco
        //deve gravar no cache
        //ver se gravou no cache
        //consultar veiculo
        //nao pode chamar o banco, tem q pegar do cache
        Assertions.fail();
    }
}
