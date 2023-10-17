package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Calificacion;

import java.util.List;

public record GetAlumnoEnCalificacionDto(Long id, String nombre, String apellidos, List<GetCalificacionEnAlumnoDto> calificaciones) {

    public static GetAlumnoEnCalificacionDto of(Alumno a, List<Calificacion> calificaciones){
        return new GetAlumnoEnCalificacionDto(
                a.getId(),
                a.getNombre(),
                a.getApellidos(),
                calificaciones.stream().map(GetCalificacionEnAlumnoDto::of).toList()
        );
    }
}
