package org.example.parking_ud.controllers;

import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.BicycleDTO;
import org.example.parking_ud.services.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
public class BicycleController {
    @Autowired
    BicycleService bicycleService;

    //@CrossOrigin(origins = "http://localhost:5173")
    @CrossOrigin(origins = "*")
    @PostMapping("/registerBike")
    // este método debe recibir un json con campos nombrados de forma consistente con los atributos de la clase Bicycle
    //
    public boolean registerBike(
            @RequestBody Bicycle bike
    ) {
        return bicycleService.register(bike);
    }

// la respuesta de este método debe asingarse a un campo del localStorage del FrontEnd, dicho dato se usará para enviar
//las peticiones de checkIn
    //@CrossOrigin(origins = "http://localhost:5173")
    @CrossOrigin(origins = "*")
    @GetMapping("/getUserBike")
    public ResponseEntity<BicycleDTO> getUserBike(
            @RequestParam int usuario
    ) {
        BicycleDTO bike = bicycleService.getBike(usuario);
        if (bike == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }


}
