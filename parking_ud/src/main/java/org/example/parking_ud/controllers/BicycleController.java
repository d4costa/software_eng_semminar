package org.example.parking_ud.controllers;

import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dto.BicycleDTO;
import org.example.parking_ud.services.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BicycleController {
    @Autowired
    BicycleService bicycleService;

    //@CrossOrigin(origins = "http://localhost:5173")
    @CrossOrigin(origins = "*")
    @PostMapping("/registerBike")
    //
    public ResponseEntity<Boolean> registerBike(
            @RequestBody BicycleDTO bike
    ) {
        boolean res = bicycleService.register(bike);
        if (res){
        return ResponseEntity.ok(res);}
        else return ResponseEntity.badRequest().body(res);
    }

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
