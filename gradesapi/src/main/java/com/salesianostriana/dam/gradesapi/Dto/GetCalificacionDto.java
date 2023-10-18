package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Calificacion;

public record GetCalificacionDto(Long id, String codReferente, String nombre, Double calificacion) {

    public static GetCalificacionDto of(Calificacion c){
        return new GetCalificacionDto(
                c.getId(),
                c.getReferente().getCodReferente(),
                c.getReferente().getDescripcion(),
                c.getCalificacion()
        );
    }
}
