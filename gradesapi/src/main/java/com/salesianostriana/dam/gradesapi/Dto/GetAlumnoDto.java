package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;

import java.time.format.DateTimeFormatter;

public record GetAlumnoDto(Long id, String nombre, String apellidos, String email, String telefono, String fechaNacimiento) {

    public static GetAlumnoDto of(Alumno a){
        return new GetAlumnoDto(
                a.getId(),
                a.getNombre(),
                a.getApellidos(),
                a.getEmail(),
                a.getTelefono(),
                a.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }
}
