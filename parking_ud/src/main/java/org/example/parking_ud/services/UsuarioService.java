package org.example.parking_ud.services;
import org.example.parking_ud.controllers.UsuarioController;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.UsuarioDto;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository usuarioRepository;
    public UsuarioDto login(Usuario usuario){

        Optional<Usuario> cliente = usuarioRepository.findByEmail(usuario.getEmail());

        if (cliente.isPresent() && usuario.getPassword().equals(cliente.get().getPassword())) {

           Usuario clienteOb = cliente.get();
            System.out.println(cliente.get().getId());
            return new UsuarioDto(clienteOb.getId(), clienteOb.getNombre(), clienteOb.getApellido());

        }
        return null;

    }

    public boolean register(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            return false;
        }
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            return false;
        }

        try {
            Optional<Usuario> existUser = usuarioRepository.findByEmail(usuario.getEmail());
            if (existUser.isPresent()) {return false;};
            Usuario clienteObj = new Usuario();
            clienteObj.setId(((int)usuarioRepository.count())+10001);
            clienteObj.setNombre(usuario.getNombre());
            clienteObj.setApellido(usuario.getApellido());
            clienteObj.setPassword(usuario.getPassword());
            clienteObj.setEmail(usuario.getEmail());
            clienteObj.setStudentId(usuario.getStudentId());
            usuarioRepository.save(clienteObj);
           // UsuarioController.clienteObj = clienteObj;


            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
