package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Calificacion;

public record GetCalificacionEnAlumnoDto(String codReferente, String nombre, Double calificacion) {

    public static GetCalificacionEnAlumnoDto of(Calificacion c){
        return new GetCalificacionEnAlumnoDto(
                c.getReferente().getCodReferente(),
                c.getReferente().getDescripcion(),
                c.getCalificacion()
        );
    }
}
