package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;

import java.time.LocalDate;
import java.util.Set;

public record CreateAlumnoDto(String nombre, String apellidos, String email, String telefono, String fechaNacimiento, Set<Long> asignaturas) {
}
