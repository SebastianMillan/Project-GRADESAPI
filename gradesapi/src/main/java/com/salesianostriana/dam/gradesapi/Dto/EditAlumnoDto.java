package com.salesianostriana.dam.gradesapi.Dto;

import java.time.LocalDate;
import java.util.Set;

public record EditAlumnoDto(String nombre, String apellidos, String email, String telefono, String fechaNacimiento) {

}
