package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Asignatura;

public record GetAsignaturaDto(Long id, String nombre, Integer horas, String descripcion) {
    public static GetAsignaturaDto of(Asignatura a){
        return new GetAsignaturaDto(
                a.getId(),
                a.getNombre(),
                a.getHoras(),
                a.getDescripcion()
        );
    }
}
