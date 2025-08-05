package org.example.parking_ud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.example.parking_ud.dao.Usuario;
import org.example.parking_ud.dto.UsuarioDto;
import org.example.parking_ud.repositories.UsuarioRepository;
import org.example.parking_ud.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock private UsuarioRepository usuarioRepository;
    @InjectMocks private UsuarioService usuarioService;

    // Caso: Login exitoso
    @Test
    void login_ValidCredentials_ReturnsUserDto() {
        Usuario dbUser = new Usuario();
        dbUser.setEmail("example@email.com");
        dbUser.setPassword("validPassword");
        
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(dbUser));
        
        Usuario input = new Usuario();
        input.setEmail("example@email.com");
        input.setPassword("validPassword");
        
        UsuarioDto result = usuarioService.login(input);
        assertNotNull(result);
    }

    // Caso: Credenciales inválidas
    @Test
    void login_InvalidPassword_ReturnsNull() {
        Usuario dbUser = new Usuario();
        dbUser.setEmail("example@email.com");
        dbUser.setPassword("validPassword");
        
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(dbUser));
        
        Usuario input = new Usuario();
        input.setPassword("wrongPassword");
        input.setEmail("example@email.com");
        
        assertNull(usuarioService.login(input));
    }

    // Caso: Registro exitoso
    @Test
    void register_ValidUser_ReturnsTrue() {
        when(usuarioRepository.count()).thenReturn(5L);
        when(usuarioRepository.save(any())).thenReturn(new Usuario());
        
        Usuario user = new Usuario();
        user.setEmail("test@example.com");
        user.setPassword("password");
        
        assertTrue(usuarioService.register(user));
    }

    // Caso: Error en registro
    @Test
    void register_DuplicateEmail_ReturnsFalse() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(new Usuario()));
        
        Usuario user = new Usuario();
        user.setEmail("duplicate@example.com");
        user.setPassword("validPassword");
        assertFalse(usuarioService.register(user));
    }

    // Caso adicional: Campos obligatorios faltantes
    @Test
    void register_MissingEmail_ReturnsFalse() {
        Usuario user = new Usuario();
        user.setPassword("password");
        assertFalse(usuarioService.register(user));
    }
}