package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.Calificacion;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;

import java.util.List;

public record GetCalificacionPorAsigDto(Long idAsignatura, String codReferente, List<GetAlumnoEnCalPorAsigDto> alumnos) {

    public static GetCalificacionPorAsigDto of(ReferenteEvaluacion rf, List<Alumno> alumnos, List<Calificacion> calificaciones){
        return new GetCalificacionPorAsigDto(
                rf.getAsignatura().getId(),
                rf.getCodReferente(),
                alumnos.stream().map(a -> GetAlumnoEnCalPorAsigDto.of(a, calificaciones)).toList()
        );

    }
}
