package org.example.parking_ud.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.parking_ud.dao.Usuario}
 */
@Value
public class UsuarioDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 50)
    String nombre;
    @NotNull
    @Size(max = 50)
    String apellido;
}