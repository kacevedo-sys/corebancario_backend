package com.umg.bo;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Serdeable
@MappedEntity("CLIENTE")
public class Cliente {
    @Id
    @MappedProperty(value = "CLIENTE_ID")
    //Validaciones REGEX
    private BigInteger clienteId;
    @NotBlank(message = "El DPI es requerido")
    @Pattern(regexp = "\\d{13}", message = "El DPI debe contener exactamente 13 dígitos")
    private String dpi;
    @NotBlank(message = "Los nombres son requeridos")
    @Pattern(regexp = "^[\\p{L}]+(?:[ '\\-][\\p{L}]+)*$", message = "Los nombres solo pueden contener letras")
    private String nombres;
    @NotBlank(message = "Los apellidos son requeridos")
    @Pattern(regexp = "^[\\p{L}]+(?:[ '\\-][\\p{L}]+)*$", message = "Los apellidos solo pueden contener letras")
    private String apellidos;
    private LocalDate fechaNacimiento;
    @Pattern(regexp = "^$|^\\+\\d{5,15}$", message = "El teléfono debe incluir el código de país y solamente números")
    private String telefono;
    @Email(message = "El correo electrónico no es válido")
    private String correo;
    private String direccion;
    private LocalDateTime fechaRegistro;
    private String estado;

    public Cliente() {}

}
