package br.com.arthur.cqrs.adapters.api.controllers;

import br.com.arthur.cqrs.adapters.api.controllers.dto.VeiculoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/query")
public class QueryController {

    //@GetMapping("/{placa}")
    //public ResponseEntity<VeiculoDto> read(@PathVariable String placa){

    //}

}
