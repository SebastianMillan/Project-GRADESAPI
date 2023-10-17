package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Calificacion;

import java.util.List;

public record GetAlumnoEnCalPorAsigDto(Long id, String nombre, String apellidos, List<GetCalificacionDeInstEnAlumnoDto> calificaciones) {
    public static GetAlumnoEnCalPorAsigDto of(Alumno a, List<Calificacion> calificaciones){
        return new GetAlumnoEnCalPorAsigDto(
                a.getId(),
                a.getNombre(),
                a.getApellidos(),
                calificaciones.stream().map(GetCalificacionDeInstEnAlumnoDto::of).toList()
        );
    }

}
