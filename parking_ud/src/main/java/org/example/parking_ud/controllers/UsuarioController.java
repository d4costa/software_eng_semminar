package org.example.parking_ud.controllers;

import org.example.parking_ud.dto.UsuarioDto;
import org.example.parking_ud.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.parking_ud.dao.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
   public static Usuario clienteObj;
// este método tiene una entrada en forma de un json con campos llamados "email" y "password"
    // debe asignarse la respuesta de este método a un campo llamado UsuarioId en el localStorage del frontEnd,
//  con el fin de poder usar este dato en otros endPoints
    //@CrossOrigin(origins = "http://localhost:5173")
    @CrossOrigin(origins = "*")
    @PostMapping ("/login")

    public ResponseEntity<UsuarioDto> login(
            @RequestBody Usuario usuario
    ) {
        Optional<Usuario> cliente = usuarioService.usuarioRepository.findByEmail(usuario.getEmail());

        if (cliente.isPresent() && usuario.getPassword().equals(cliente.get().getPassword())) {

            clienteObj = cliente.get();
            System.out.println(cliente.get().getId());
             UsuarioDto usuarioObj = new UsuarioDto(clienteObj.getId(), clienteObj.getNombre(), clienteObj.getApellido());
            return ResponseEntity.ok(usuarioObj); // Retorna ID con status 200

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Credenciales inválidas
    }

   // @CrossOrigin(origins = "http://localhost:5173")
   @CrossOrigin(origins = "*")
    @PostMapping("/register")
    // este método tiene una entrada en forma de un json con campos llamados de forma consistente a los atributos de la clase Usuario
    public boolean register(@RequestBody Usuario usuario) {


        try {
            Usuario clienteObj = new Usuario();
            clienteObj.setId(((int)usuarioService.usuarioRepository.count())+10001);
            clienteObj.setNombre(usuario.getNombre());
            clienteObj.setApellido(usuario.getApellido());
            clienteObj.setPassword(usuario.getPassword());
            clienteObj.setEmail(usuario.getEmail());
            clienteObj.setStudentId(usuario.getStudentId());


            usuarioService.usuarioRepository.save(clienteObj);
            this.clienteObj = clienteObj;


            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }


    //@CrossOrigin(origins = "http://localhost:5173")
    @CrossOrigin(origins = "*")
    @GetMapping ("/logout")
    // al usar este método, debe limpiarse el localStorage de el frontEnd
    public  Integer cerrar() {
        UsuarioController.clienteObj = null;
        return  1;
    }


}
