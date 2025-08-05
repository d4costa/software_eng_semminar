package org.example.parking_ud.services;


import org.example.parking_ud.controllers.UsuarioController;
import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.BicycleDTO;
import org.example.parking_ud.repositories.BicycleRepository;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BicycleService {
    @Autowired
    public BicycleRepository bicycleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public  boolean register(BicycleDTO bike){
        try {
            Optional<Usuario> usuarioOpt = (usuarioRepository.findById(bike.userId));
            if (usuarioOpt.isEmpty()) {
                System.out.println("User not found");
                return false;
            }

            // Create new bike
            Bicycle newbBike = new Bicycle();
            newbBike.setId((short) (bicycleRepository.count() + 1));
            newbBike.setColor(bike.color);
            newbBike.setDescription(bike.description);
            newbBike.setBrand(bike.brand);
            newbBike.setChasisCode(bike.chasisCode);
            newbBike.setUser(usuarioOpt.get());

            // Save
            bicycleRepository.save(newbBike);

            return true;

        } catch (Exception e) {
            System.out.println("Error registering bike: " + e.getMessage());
            return false;
        }


    }

    public BicycleDTO getBike(Integer usuario) {
       Bicycle  bike = bicycleRepository.findByUser_id(usuario)
                .orElse(null);
        if (bike == null) return null;
        BicycleDTO dto = new BicycleDTO();
        dto.id=bike.getId();
        dto.color=bike.getColor();
        dto.description=bike.getDescription();
        dto.brand=bike.getBrand();
        dto.chasisCode=bike.getChasisCode();

        return dto;
    }
}
