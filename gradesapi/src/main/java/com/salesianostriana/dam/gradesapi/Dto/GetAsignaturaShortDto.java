package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Asignatura;

public record GetAsignaturaShortDto(Long id, String nombre) {

    public static GetAsignaturaShortDto of(Asignatura a){
        return new GetAsignaturaShortDto(a.getId(), a.getNombre());
    }
}
