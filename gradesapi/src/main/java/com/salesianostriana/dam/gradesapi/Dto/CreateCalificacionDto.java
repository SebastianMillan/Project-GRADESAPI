package com.salesianostriana.dam.gradesapi.Dto;

import java.util.List;

public record CreateCalificacionDto(Long idInstrumento, Long idAlumno, List<GetCalificacionShortDto> calificaciones) {
}
