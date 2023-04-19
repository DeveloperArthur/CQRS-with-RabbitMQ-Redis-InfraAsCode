package br.com.arthur.cqrs.ports.web;

import br.com.arthur.cqrs.application.CQRSApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/command")
public class CommandController {
    @Autowired
    CQRSApplication application;

    @PostMapping
    public ResponseEntity<VeiculoDtoWeb> write(@RequestBody VeiculoDtoWeb dto){
        return application.write(dto);
    }
}
