package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.*;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    })
    @Operation(summary = "create Alumno", description = "Creación de una asignatura")
    @PostMapping("/")
    public ResponseEntity<GetAsignaturaDto> createAsig(@RequestBody CreateAsignaturaDto nuevo){
        return ResponseEntity.status(201).body(GetAsignaturaDto.of(asignaturaServicio.saveToCreate(nuevo)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAsignaturaListDto.class))
                    )})
    })
    @Operation(summary = "findAll Asignatura", description = "Obtener todos los alumnos")
    @GetMapping("/")
    public ResponseEntity<List<GetAsignaturaListDto>> findAll(){
        List<Asignatura> asignaturas = asignaturaRepositorio.findAll();
        if(asignaturas.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                asignaturas.stream()
                        .map(GetAsignaturaListDto::of)
                        .toList()
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAsignaturaDetailsDto.class))
                    )})
    })
    @Operation(summary = "findById Asignatura", description = "Obtener una asignatura por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<GetAsignaturaDetailsDto> findById(@PathVariable Long id){
        return ResponseEntity.of(asignaturaRepositorio.findById(id)
                .map(GetAsignaturaDetailsDto::of));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAsignaturaDetailsDto.class))
                    )})
    })
    @Operation(summary = "addReferenteEvaluacionToAsignatura", description = "Añadir uno o varios Referentes de Evaluación a una Asignatura")
    @PostMapping("/{id}/referente")
    public ResponseEntity<GetAsignaturaDetailsDto> addRefToAsig(@PathVariable Long id, @RequestBody GetReferenteShortDto[] referentes){
        Optional<Asignatura> as = asignaturaRepositorio.findById(id);
        if(as.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.status(201).body(GetAsignaturaDetailsDto.of(asignaturaServicio.addRefToAsig(as.get(), referentes)));



    }
}
