package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.CreateAsignaturaDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAlumnoDetailsDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAsignaturaDto;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
import com.salesianostriana.dam.gradesapi.servicios.AsignaturaServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asignatura")
@RequiredArgsConstructor
@Tag(name="Asignatura", description = "Controlador de Asignatura API REST con operaciones CRUD")
public class AsignaturaController {

    private final AsignaturaRepositorio asignaturaRepositorio;
    private final AsignaturaServicio asignaturaServicio;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAsignaturaDto.class))
                    )})
    }
    )
    @Operation(summary = "create Alumno", description = "Creación de una asignatura")
    @PostMapping("/")
    public ResponseEntity<GetAsignaturaDto> createAsig(@RequestBody CreateAsignaturaDto nuevo){
        return ResponseEntity.status(201).body(GetAsignaturaDto.of(asignaturaServicio.saveToCreate(nuevo)));
    }
}
