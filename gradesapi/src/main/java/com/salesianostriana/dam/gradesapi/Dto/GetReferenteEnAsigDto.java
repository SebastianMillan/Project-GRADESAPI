package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;

public record GetReferenteEnAsigDto(String codReferente, String descripcion) {

    public static GetReferenteEnAsigDto of(ReferenteEvaluacion rf){
        return new GetReferenteEnAsigDto(
                rf.getCodReferente(),
                rf.getDescripcion()
        );
    }
}
