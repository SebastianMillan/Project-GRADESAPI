package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Asignatura;

public record GetAsignaturaEnAlumnoDto(Long id, String nombre) {

    public static GetAsignaturaEnAlumnoDto of(Asignatura a){
        return new GetAsignaturaEnAlumnoDto(
                a.getId(),
                a.getNombre()
        );
    }
}
