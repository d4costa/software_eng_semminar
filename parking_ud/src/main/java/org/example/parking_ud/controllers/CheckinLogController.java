package org.example.parking_ud.controllers;

import org.example.parking_ud.dto.CheckLogDTO;
import org.example.parking_ud.dto.CheckRequest;
import org.example.parking_ud.services.CheckinLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CheckinLogController {
    @Autowired
    CheckinLogService checkinLogService;
    // @CrossOrigin(origins = "http://localhost:5173")
   @CrossOrigin(origins = "*")
    @PostMapping ("/CheckIn")
    public ResponseEntity<String> CheckIn(@RequestBody CheckRequest checkRequest) {
      // System.out.println(checkRequest.bikeId +" "+ checkRequest.userId);
        return checkinLogService.checkIn(checkRequest.userId, checkRequest.bikeId,checkRequest.parkingId);
    }
    //@CrossOrigin(origins = "http://localhost:5173")
    @CrossOrigin(origins = "*")
    @PostMapping("/CheckOut")
    public ResponseEntity<String> CheckOut( @RequestBody CheckRequest checkRequest ){
        return checkinLogService.checkOut(checkRequest.bikeId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/GetLastLog")
    public ResponseEntity<String> getLastLog(@RequestParam int userId) {
        return checkinLogService.getLastLog(userId);
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getAllLogs")
    public ResponseEntity<List<CheckLogDTO>> getLogsByUser(@RequestParam int userId) {
        List<CheckLogDTO> logs = checkinLogService.getLogs(userId);
        return ResponseEntity.ok(logs);
    }

}
