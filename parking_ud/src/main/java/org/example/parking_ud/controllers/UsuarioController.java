package org.example.parking_ud.controllers;

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
    Usuario clienteObj;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping ("/login")
    public ResponseEntity<Integer> login(
            @RequestBody Usuario usuario
    ) {
        Optional<Usuario> cliente = usuarioService.usuarioRepository.findByEmail(usuario.getEmail());

        if (cliente.isPresent() && usuario.getPassword().equals(cliente.get().getPassword())) {

            clienteObj = cliente.get();
            System.out.println(cliente.get().getId());
            return ResponseEntity.ok(cliente.get().getId()); // Retorna ID con status 200

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Credenciales inválidas
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/register")
    public boolean register(@RequestParam String nombre,
                            @RequestParam String apellido,
                            @RequestParam String contrasena,
                            @RequestParam String email,
                            @RequestParam String numero_id) {


        try {
            // System.out.println(registro.getApellido());
            Usuario clienteObj = new Usuario();
            clienteObj.setId(((int)usuarioService.usuarioRepository.count())+10001);
            clienteObj.setNombre(nombre);
            clienteObj.setApellido(apellido);
            clienteObj.setPassword(contrasena);
            clienteObj.setEmail(email);
            BigDecimal numeroId = new BigDecimal(numero_id);
            clienteObj.setStudentId(numeroId);


            usuarioService.usuarioRepository.save(clienteObj);
            this.clienteObj = clienteObj;


            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/registerBike")
    public boolean registerBike(
  @RequestBody Bicycle bike
    ) {
        try {
            // Optional: check if user exists first
            Optional<Usuario> usuarioOpt = usuarioService.usuarioRepository.findById(clienteObj.getId());
            if (usuarioOpt.isEmpty()) {
                System.out.println("User not found");
                return false;
            }

            // Create new bike
            Bicycle newbBike = new Bicycle();
            newbBike.setId((short) (usuarioService.bicycleRepository.count() + 1));
            newbBike.setColor(bike.getColor());
            newbBike.setDescription(bike.getDescription());
            newbBike.setBrand(bike.brand);
            newbBike.setChasisCode(bike.chasisCode);
            newbBike.setUser(usuarioOpt.get());

            // Save
            usuarioService.bicycleRepository.save(newbBike);

            return true;

        } catch (Exception e) {
            System.out.println("Error registering bike: " + e.getMessage());
            return false;
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping ("/cerrarsesion")
    public  Integer cerrar() {
        this.clienteObj = null;
        return  1;
    }


}
