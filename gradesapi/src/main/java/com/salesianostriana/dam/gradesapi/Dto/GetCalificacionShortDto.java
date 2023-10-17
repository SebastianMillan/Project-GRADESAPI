package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Calificacion;

public record GetCalificacionShortDto(String codReferente, Double calificacion){

    public static GetCalificacionShortDto of(Calificacion c){
        return new GetCalificacionShortDto(
                c.getReferente().getCodReferente(),
                c.getCalificacion()
        );
    }
}
