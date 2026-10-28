package com.montanha.gerenciador_viagens.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.montanha.gerenciador_viagens.responses.Response;
import com.montanha.gerenciador_viagens.services.exceptions.ViagemServiceException;
import com.montanha.gerenciador_viagens.services.exceptions.RegraNegocioException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ViagemServiceException.class)
    public ResponseEntity<Response<Void>> tratarViagemNaoEncontrada(ViagemServiceException e) {
        Response<Void> response = new Response<>();
        response.getErrors().add(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Response<Void>> tratarRegraNegocioException(RegraNegocioException e) {
        Response<Void> response = new Response<>();
        response.getErrors().add(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);


    }

}