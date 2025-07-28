package org.example.parking_ud.controllers;

import org.example.parking_ud.dto.CheckRequest;
import org.example.parking_ud.repositories.CheckinLogRepository;
import org.example.parking_ud.services.CheckinLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckinLogController {
    @Autowired
    CheckinLogService checkinLogService;
    @Autowired
    private CheckinLogRepository checkinLogRepository;

    // @CrossOrigin(origins = "http://localhost:5173")
   @CrossOrigin(origins = "*")
    @PostMapping ("/CheckIn")
    // para los parámetros de este método, se requiere usar campos del localStorge,
    public ResponseEntity<String> CheckIn(@RequestBody CheckRequest checkRequest) {
       System.out.println(checkRequest.bikeId +" "+ checkRequest.userId);
        return checkinLogService.checkIn(checkRequest.userId, checkRequest.bikeId);
    }
    //@CrossOrigin(origins = "http://localhost:5173")
    @CrossOrigin(origins = "*")
    @PostMapping("/CheckOut")
    // para los parámetros de este método, se requiere usar campos del localStorge,
    public ResponseEntity<String> CheckOut( @RequestBody CheckRequest checkRequest ){
        return checkinLogService.checkOut(checkRequest.bikeId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/GetLastLog")
    // para los parámetros de este método, se requiere usar campos del localStorge,
    public ResponseEntity<String> getLastLog(@RequestParam int userId) {
        return checkinLogService.getLastLog(userId);
    }

}
