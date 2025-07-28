package org.example.parking_ud.controllers;

import org.example.parking_ud.services.CheckinLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckinLogController {
    @Autowired
    CheckinLogService checkinLogService;
   // @CrossOrigin(origins = "http://localhost:5173")
   @CrossOrigin(origins = "*")
    @GetMapping("/CheckIn")
    // para los parámetros de este método, se requiere usar campos del localStorge,
    public ResponseEntity<String> CheckIn(@RequestParam int userId, @RequestParam short bikeId) {
        return checkinLogService.checkIn(userId,bikeId);
    }
    //@CrossOrigin(origins = "http://localhost:5173")
    @CrossOrigin(origins = "*")
    @GetMapping("/CheckOut")
    // para los parámetros de este método, se requiere usar campos del localStorge,
    public ResponseEntity<String> CheckOut( @RequestParam short bikeId) {
        return checkinLogService.checkOut(bikeId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/GetLastLog")
    // para los parámetros de este método, se requiere usar campos del localStorge,
    public ResponseEntity<String> getLastLog(@RequestParam int userId) {
        return checkinLogService.getLastLog(userId);
    }

}
