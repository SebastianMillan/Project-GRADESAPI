package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Calificacion;
import com.salesianostriana.dam.gradesapi.modelo.Instrumento;

import java.util.List;

public record GetCalificacionPorInstrumentoDto(Long idInstrumento, String nombre, List<GetAlumnoEnCalificacionDto> alumnos) {

    public static GetCalificacionPorInstrumentoDto of(List<Alumno> alumnos, Instrumento i, List<Calificacion> calificaciones){
        return new GetCalificacionPorInstrumentoDto(
                i.getId(),
                i.getNombre(),
                alumnos.stream().map(a -> GetAlumnoEnCalificacionDto.of(a, calificaciones)).toList()

        );
    }
}
