package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Calificacion;

public record GetCalificacionDeInstEnAlumnoDto(Long idInstrumento, Double calificacion) {
    public static GetCalificacionDeInstEnAlumnoDto of(Calificacion c){
        return new GetCalificacionDeInstEnAlumnoDto(
                c.getInstrumento().getId(),
                c.getCalificacion()
        );
    }
}
