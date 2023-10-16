package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Asignatura;

import java.util.List;

public record GetAsignaturaDetailsDto(Long id, String nombre, Integer horas, String descripcion, List<GetReferenteEnAsigDto> referentes) {

    public static GetAsignaturaDetailsDto of(Asignatura a){
        return new GetAsignaturaDetailsDto(
                a.getId(),
                a.getNombre(),
                a.getHoras(),
                a.getDescripcion(),
                a.getReferentes().stream().map(GetReferenteEnAsigDto::of).toList()
        );
    }
}
