package br.com.arthur.cqrs.application;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.service.ConsultaVeiculo;
import br.com.arthur.cqrs.core.service.SalvaVeiculo;
import br.com.arthur.cqrs.ports.web.VeiculoDtoWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CQRSApplication {
    @Autowired
    ConsultaVeiculo consultaVeiculo;
    @Autowired
    SalvaVeiculo salvaVeiculo;

    public ResponseEntity<VeiculoDtoWeb> read(String id) {
        Optional<Veiculo> veiculoOptional = consultaVeiculo.read(id);
        if (veiculoOptional.isPresent())
            return ResponseEntity.ok(new VeiculoDtoWeb(veiculoOptional.get()));

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<VeiculoDtoWeb> write(VeiculoDtoWeb dto) {
        Veiculo veiculo = dto.converte();
        Veiculo veiculoSalvo = salvaVeiculo.write(veiculo);
        return ResponseEntity.ok(new VeiculoDtoWeb(veiculoSalvo));
    }
}
