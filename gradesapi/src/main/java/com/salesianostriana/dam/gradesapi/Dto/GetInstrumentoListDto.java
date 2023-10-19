package com.salesianostriana.dam.gradesapi.Dto;

import com.salesianostriana.dam.gradesapi.modelo.Instrumento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record GetInstrumentoListDto(Long id, String fecha, String nombre, String contenidos, Integer numeroReferentes) {

    public static GetInstrumentoListDto of(Instrumento i){
        return new GetInstrumentoListDto(
                i.getId(),
                i.getNombre(),
                i.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                i.getContenidos(),
                i.getReferentes().size()
        );

    }
}
