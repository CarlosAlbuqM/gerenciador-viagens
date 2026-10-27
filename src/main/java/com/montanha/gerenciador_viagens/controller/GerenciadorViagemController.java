package com.montanha.gerenciador_viagens.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.montanha.gerenciador_viagens.dtos.ViagemDto;
import com.montanha.gerenciador_viagens.entities.Viagem;
import com.montanha.gerenciador_viagens.responses.Response;
import com.montanha.gerenciador_viagens.services.ViagemServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/viagens")
public class GerenciadorViagemController {

    @Autowired
    private ViagemServices viagemService;

    @PostMapping(path = "/new")
    public ResponseEntity<Response<Viagem>> cadastrar(@Valid @RequestBody ViagemDto viagemDto,
            BindingResult result) {

        Response<Viagem> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Viagem viagemSalva = viagemService.salvar(viagemDto);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/viagens/{id}")
                .buildAndExpand(viagemSalva.getId())
                .toUri();

        response.setData(viagemSalva);
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Viagem>> listar() {
        List<Viagem> viagens = viagemService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(viagens);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<Viagem>> buscar(@PathVariable("id") Long id) {
        Viagem viagem = viagemService.buscar(id);
        Response<Viagem> response = new Response<>();
        response.setData(viagem);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}