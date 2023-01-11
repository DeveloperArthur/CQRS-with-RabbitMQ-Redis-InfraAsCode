package br.com.arthur.cqrs.ports.web;

import br.com.arthur.cqrs.application.CQRSApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/query")
public class QueryController {
    @Autowired
    CQRSApplication application;

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDto> read(@PathVariable String id){
        return application.read(id);
    }
}
