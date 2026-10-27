package com.montanha.gerenciador_viagens.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ViagemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "Local de Destino é uma informação obrigatória")
    @Size(min = 3, max = 40, message = "Local de Destino deve estar entre 3 e 40 caracteres")
    private String localDeDestino;

    @NotNull(message = "Data da Partida é uma informação obrigatória")
    private LocalDate dataPartida;

    private LocalDate dataRetorno;

    private String acompanhante;

    public ViagemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalDeDestino() {
        return localDeDestino;
    }

    public void setLocalDeDestino(String localDeDestino) {
        this.localDeDestino = localDeDestino;
    }

    public LocalDate getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(LocalDate dataPartida) {
        this.dataPartida = dataPartida;
    }

    public LocalDate getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(LocalDate dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public String getAcompanhante() {
        return acompanhante;
    }

    public void setAcompanhante(String acompanhante) {
        this.acompanhante = acompanhante;
    }
}