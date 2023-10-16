package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public record GetAlumnoDetailsDto(Long id, String nombre, String apellidos, String email, String telefono, String fechaNacimiento,
                                  Set<GetAsignaturaEnAlumnoDto> asignaturas) {

    public static GetAlumnoDetailsDto of(Alumno a){
        return new GetAlumnoDetailsDto(
                a.getId(),
                a.getNombre(),
                a.getApellidos(),
                a.getEmail(),
                a.getTelefono(),
                a.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                a.getAsignaturas().stream()
                        .map(GetAsignaturaEnAlumnoDto::of)
                        .collect(Collectors.toSet())
        );
    }
}
