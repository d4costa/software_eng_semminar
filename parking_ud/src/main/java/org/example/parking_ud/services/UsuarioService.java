package org.example.parking_ud.services;

import org.example.parking_ud.repositories.BicycleRepository;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository usuarioRepository;

}
