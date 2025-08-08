package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.example.parking_ud.dao.Bicycle;
import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.BicycleDTO;
import org.example.parking_ud.repositories.BicycleRepository;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.example.parking_ud.services.BicycleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BicycleServiceTest {

    @Mock private BicycleRepository bicycleRepository;
    @Mock private UsuarioRepository usuarioRepository;
    
    @InjectMocks private BicycleService bicycleService;
    
    private BicycleDTO validBikeDTO;
    private Usuario validUser;

    @BeforeEach
    void setUp() {
        validBikeDTO = new BicycleDTO();
        validBikeDTO.userId = 1;
        validBikeDTO.color = "Rojo";
        validBikeDTO.description = "Bicicleta de montaña";
        validBikeDTO.brand = "Trek";
        validBikeDTO.chasisCode = "CH12345";
        
        validUser = new Usuario();
        validUser.setId(1);
    }

    // Caso 1: Registro exitoso
    @Test
    void register_ValidBikeAndUser_ReturnsTrue() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(validUser));
        when(bicycleRepository.count()).thenReturn(5L);
        when(bicycleRepository.save(any())).thenReturn(new Bicycle());
        
        assertTrue(bicycleService.register(validBikeDTO));
        verify(bicycleRepository).save(any());
    }

    // Caso 2: Usuario no encontrado
    @Test
    void register_UserNotFound_ReturnsFalse() {
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertFalse(bicycleService.register(validBikeDTO));
    }

    // Caso 3: Error al guardar
    @Test
    void register_SaveThrowsException_ReturnsFalse() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(validUser));
        when(bicycleRepository.save(any())).thenThrow(new RuntimeException("DB Error"));
        assertFalse(bicycleService.register(validBikeDTO));
    }

    // Caso 6: Datos inválidos (campos nulos)
    @Test
    void register_NullFields_ReturnsFalse() {
        BicycleDTO invalidDTO = new BicycleDTO();
        invalidDTO.userId = 1; // Solo ID presente
        assertFalse(bicycleService.register(invalidDTO));
    }

    // Caso 7: Usuario con ID inválido
    @Test
    void register_InvalidUserId_ReturnsFalse() {
        validBikeDTO.userId = -1;
        assertFalse(bicycleService.register(validBikeDTO));
    }

    // Caso 4: Obtener bicicleta existente
    @Test
    void getBike_ValidUserWithBike_ReturnsBicycleDTO() {
        Bicycle bike = new Bicycle();
        bike.setColor("Azul");
        when(bicycleRepository.findByUser_id(1)).thenReturn(Optional.of(bike));
        
        BicycleDTO result = bicycleService.getBike(1);
        assertEquals("Azul", result.color);
    }

    // Caso 5: No existe bicicleta
    @Test
    void getBike_NoBikeAssociated_ReturnsNull() {
        when(bicycleRepository.findByUser_id(anyInt())).thenReturn(Optional.empty());
        assertNull(bicycleService.getBike(1));
    }

    // Caso 8: Bicicleta con campos vacíos
    @Test
    void getBike_BikeWithEmptyFields_ReturnsDTOWithNulls() {
        Bicycle bike = new Bicycle();
        bike.setDescription(null);
        when(bicycleRepository.findByUser_id(1)).thenReturn(Optional.of(bike));
        
        BicycleDTO result = bicycleService.getBike(1);
        assertNull(result.description);
    }

    // Caso 9: ID de usuario inexistente
    @Test
    void getBike_InvalidUserId_ReturnsNull() {
        assertNull(bicycleService.getBike(null));
    }
}