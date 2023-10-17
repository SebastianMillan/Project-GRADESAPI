package com.salesianostriana.dam.gradesapi.Dto;

import java.util.Set;

public record CreateInstrumentoDto(String fecha, String nombre, String contenidos, Long idAsignatura, Set<String> referentes) {
}
