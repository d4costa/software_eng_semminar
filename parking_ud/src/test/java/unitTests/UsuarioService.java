package unitTests;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock private UsuarioRepository usuarioRepository;
    @InjectMocks private UsuarioService usuarioService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    // Caso: Login exitoso
    @Test
    void login_ValidCredentials_ReturnsUserDto() {
        // Arrange
        Usuario dbUser = new Usuario();
        dbUser.setEmail("example@email.com");
        dbUser.setPassword("validPassword"); // Use passwordHash field

        // Mock repository and encoder
        when(usuarioRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(dbUser));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true); // Simulate password match

        Usuario input = new Usuario();
        input.setEmail("example@email.com");
        input.setPassword("validPassword"); // Raw password

        // Act
        UsuarioDto result = usuarioService.login(input);

        // Assert
        assertNotNull(result);
    }


    // Caso: Credenciales inválidas
    @Test
    void login_InvalidPassword_ReturnsNull() {
        // Arrange
        Usuario dbUser = new Usuario();
        dbUser.setEmail("example@email.com");
        dbUser.setPassword("hashedPassword");

        when(usuarioRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(dbUser));
        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(false); // Simulate password mismatch

        Usuario input = new Usuario();
        input.setPassword("wrongPassword");
        input.setEmail("example@email.com");

        // Act & Assert
        assertNull(usuarioService.login(input));
    }

    // Caso: Registro exitoso
    @Test
    void register_ValidUser_ReturnsTrue() {
        // Arrange
        when(usuarioRepository.count()).thenReturn(5L);
        when(usuarioRepository.save(any())).thenReturn(new Usuario());

        // Mock password encoder
        when(passwordEncoder.encode(anyString()))
                .thenReturn("hashedPassword");

        Usuario user = new Usuario();
        user.setEmail("test@example.com");
        user.setPassword("password");

        // Act & Assert
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