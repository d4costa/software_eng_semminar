package org.example.parking_ud.services;
import org.example.parking_ud.controllers.UsuarioController;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.UsuarioDto;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioDto login(Usuario usuario){

        Optional<Usuario> cliente = usuarioRepository.findByEmail(usuario.getEmail());

        if (cliente.isPresent()) {
            boolean matches = passwordEncoder.matches(
                    usuario.getPassword(),                    // raw password from login form
                    cliente.get().getPassword()               // hashed password from DB
            );
            System.out.println(passwordEncoder.encode(usuario.getPassword())+" "+ cliente.get().getPassword());
            if (matches) {
                Usuario clienteOb = cliente.get();
                System.out.println(cliente.get().getId());
                return new UsuarioDto(clienteOb.getId(), clienteOb.getNombre(), clienteOb.getApellido());
            }
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
            clienteObj.setPassword(passwordEncoder.encode(usuario.getPassword()));
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

    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        if (userId <=0  || currentPassword == null || newPassword == null) {
            return false;
        }

        Usuario user = usuarioRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        // Validate new password strength
        if (newPassword.length() < 8) {
            return false;
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            return false;
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        usuarioRepository.save(user);

        return true;

    }
}
