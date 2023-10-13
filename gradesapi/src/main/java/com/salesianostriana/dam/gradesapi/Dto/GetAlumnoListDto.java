package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;

import java.time.format.DateTimeFormatter;

public record GetAlumnoListDto(Long id, String nombre, String apellidos, String email, String telefono, String fechaNacimiento, Integer cantidadAsignaturas) {

    public static GetAlumnoListDto of(Alumno a){
        return new GetAlumnoListDto(
                a.getId(),
                a.getNombre(),
                a.getApellidos(),
                a.getEmail(),
                a.getTelefono(),
                a.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                a.getAsignaturas().size()

        );
    }
}
