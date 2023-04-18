package br.com.arthur.cqrs.core.service;

import br.com.arthur.cqrs.core.gateways.EventHandler;
import br.com.arthur.cqrs.core.gateways.WriteDatabase;
import br.com.arthur.cqrs.core.domain.Veiculo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaVeiculo {
    private WriteDatabase writeDatabase;
    private EventHandler eventoDeVeiculoSalvo;

    @Autowired
    public SalvaVeiculo(WriteDatabase writeDatabase, EventHandler eventoDeVeiculoSalvo) {
        this.writeDatabase = writeDatabase;
        this.eventoDeVeiculoSalvo = eventoDeVeiculoSalvo;
    }

    public Veiculo write(Veiculo veiculo) {
        try {
            Veiculo veiculoSalvo = writeDatabase.write(veiculo);
            if (veiculoSalvo == null) {
                throw new RuntimeException("Não foi possível salvar o veículo");
            }
            eventoDeVeiculoSalvo.envia(veiculoSalvo);
            return veiculoSalvo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
