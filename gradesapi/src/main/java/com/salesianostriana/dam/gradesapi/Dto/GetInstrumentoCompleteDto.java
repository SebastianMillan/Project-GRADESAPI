package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.Instrumento;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record GetInstrumentoCompleteDto(Long id, String fecha, String nombre, String contenidos, GetAsignaturaShortDto asignatura, Set<GetReferenteEnAsigDto> referentes) {

    public static GetInstrumentoCompleteDto of(Instrumento i, Asignatura a){

        return new GetInstrumentoCompleteDto(
                i.getId(),
                i.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                i.getNombre(),
                i.getContenidos(),
                GetAsignaturaShortDto.of(a),
                i.getReferentes().stream()
                        .map(GetReferenteEnAsigDto::of)
                        .collect(Collectors.toSet())
        );
    }
}
