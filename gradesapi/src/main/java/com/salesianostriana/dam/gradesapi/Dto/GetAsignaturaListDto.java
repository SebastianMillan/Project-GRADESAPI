package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Asignatura;

public record GetAsignaturaListDto(Long id, String nombre, Integer horas, String descripcion, Integer numReferentes) {

    public static GetAsignaturaListDto of(Asignatura a){
        return new GetAsignaturaListDto(
                a.getId(),
                a.getNombre(),
                a.getHoras(),
                a.getDescripcion(),
                a.getReferentes().size()
        );
    }
}
