package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Calificacion;

import java.util.List;

public record GetCalificacionListDto(Long idInstrumento, String nombre, List<GetCalificacionDto> calificaciones) {

    public static GetCalificacionListDto of(List<Calificacion> calificaciones){

        return new GetCalificacionListDto(
                calificaciones.get(0).getInstrumento().getId(),
                calificaciones.get(0).getInstrumento().getNombre(),
                calificaciones.stream()
                        .map(GetCalificacionDto::of)
                        .toList()
        );
    }
}
