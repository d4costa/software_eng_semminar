package org.example.parking_ud.controllers;

import org.example.parking_ud.dto.ResetPasswordRequest;
import org.example.parking_ud.dto.UsuarioDto;
import org.example.parking_ud.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.parking_ud.dao.*;


@RestController
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;



   public static Usuario clienteObj;
    //@CrossOrigin(origins = "http://localhost:5173")
    @CrossOrigin(origins = "*")
    @PostMapping ("/login")

    public ResponseEntity<UsuarioDto> login(
            @RequestBody Usuario usuario
    ) {
        UsuarioDto cliente = usuarioService.login(usuario);

        if (cliente!=null) {

            return ResponseEntity.ok(cliente); // Retorna ID con status 200

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Credenciales inválidas
    }

   // @CrossOrigin(origins = "http://localhost:5173")
   @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody Usuario usuario) {
        boolean res = usuarioService.register(usuario);
        if (res)
        return  ResponseEntity.ok(res);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


    //@CrossOrigin(origins = "http://localhost:5173")
    @CrossOrigin(origins = "*")
    @GetMapping ("/logout")
    public  Integer cerrar() {
        UsuarioController.clienteObj = null;
        return  1;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/resetpassword")
    public ResponseEntity<Void> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        boolean success = usuarioService.updatePassword(
                request.getUserId(),
                request.getPassword1(),
                request.getPassword2()
        );

        return success
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/forgotPassword1")
    public ResponseEntity<Usuario> getEmail(
            @RequestBody Usuario usuario
    ) {
        Usuario cliente = usuarioService.getUser(usuario);

        if (cliente!=null) {

            return ResponseEntity.ok(cliente); // Retorna ID con status 200

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
