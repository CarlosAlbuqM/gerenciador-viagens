package com.montanha.gerenciador_viagens.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.montanha.gerenciador_viagens.dtos.ViagemDto;
import com.montanha.gerenciador_viagens.entities.Viagem;
import com.montanha.gerenciador_viagens.repositories.ViagemRepository;
import com.montanha.gerenciador_viagens.services.exceptions.ViagemServiceException;

@Service
public class ViagemServices {

    @Autowired
    private ViagemRepository viagemRepository;

    public List<Viagem> listar() {
        return viagemRepository.findAll();
    }

    public Viagem salvar(ViagemDto viagemDto) {
        Viagem viagem = new Viagem();
        viagem.setLocalDeDestino(viagemDto.getLocalDeDestino());
        viagem.setDataPartida(viagemDto.getDataPartida());
        viagem.setDataRetorno(viagemDto.getDataRetorno());
        viagem.setAcompanhante(viagemDto.getAcompanhante());
        return viagemRepository.save(viagem);
    }

    public Viagem buscar(Long id) {
        return viagemRepository.findById(id)
                .orElseThrow(() -> new ViagemServiceException("Não existe esta viagem cadastrada"));
    }

    public Viagem atualizar(Long id, ViagemDto viagemDto) {
    Viagem viagem = buscar(id);

    viagem.setLocalDeDestino(viagemDto.getLocalDeDestino());
    viagem.setDataPartida(viagemDto.getDataPartida());
    viagem.setDataRetorno(viagemDto.getDataRetorno());
    viagem.setAcompanhante(viagemDto.getAcompanhante());

    return viagemRepository.save(viagem);
}

public void remover(Long id) {
    Viagem viagem = buscar(id);
    viagemRepository.delete(viagem);
}


}