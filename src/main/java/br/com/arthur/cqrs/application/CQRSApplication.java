package br.com.arthur.cqrs.application;

import br.com.arthur.cqrs.core.domain.Veiculo;
import br.com.arthur.cqrs.core.service.ConsultaVeiculo;
import br.com.arthur.cqrs.core.service.SalvaVeiculo;
import br.com.arthur.cqrs.ports.web.VeiculoDto;
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

    public ResponseEntity<VeiculoDto> read(String placa) {
        Optional<Veiculo> veiculoOptional = consultaVeiculo.read(placa);
        if (veiculoOptional.isPresent())
            return ResponseEntity.ok(new VeiculoDto(veiculoOptional.get()));

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<VeiculoDto> write(VeiculoDto dto) {
        Veiculo veiculo = dto.converte();
        Veiculo veiculoSalvo = salvaVeiculo.write(veiculo);
        return ResponseEntity.ok(new VeiculoDto(veiculoSalvo));
    }
}
