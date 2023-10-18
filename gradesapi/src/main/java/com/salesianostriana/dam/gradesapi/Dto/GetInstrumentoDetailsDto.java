package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Instrumento;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public record GetInstrumentoDetailsDto(Long id, String fecha, String nombre, String contenidos, Long idAsignatura, Set<GetReferenteEnAsigDto> referentes) {

    public static GetInstrumentoDetailsDto of(Instrumento i, Long idAsignatura){
        return new GetInstrumentoDetailsDto(
                i.getId(),
                i.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                i.getNombre(),
                i.getContenidos(),
                idAsignatura,
                i.getReferentes().stream().map(GetReferenteEnAsigDto::of).collect(Collectors.toSet())
        );
    }
}
