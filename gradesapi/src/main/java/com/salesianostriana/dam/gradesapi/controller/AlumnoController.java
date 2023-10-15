package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.CreateAlumnoDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAlumnoDetailsDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAlumnoDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAlumnoListDto;
import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.repositorios.AlumnoRepositorio;
import com.salesianostriana.dam.gradesapi.servicios.AlumnoServicio;
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

import java.util.List;

@RestController
@RequestMapping("/alumno")
@RequiredArgsConstructor
@Tag(name="Alumno", description = "Controlador de Alumno API REST con operaciones CRUD")
public class AlumnoController {

    private final AlumnoRepositorio alumnoRepositorio;
    private final AlumnoServicio alumnoServicio;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = GetAlumnoListDto.class))
                    )})
            }
    )
    @Operation(summary = "findAll Alumnos", description = "Obtener todos los alumnos")
    @GetMapping("/")
    public ResponseEntity<List<GetAlumnoListDto>> getAll(){
        List<Alumno> alumnos = alumnoRepositorio.findAll();
        if(alumnos.isEmpty()){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                    alumnos.stream()
                            .map(GetAlumnoListDto::of)
                            .toList()
        );

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAlumnoDetailsDto.class))
                    )})
    }
    )
    @Operation(summary = "findById Alumno", description = "Obtener un alumno por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<GetAlumnoDetailsDto> findById(@PathVariable Long id){
        return ResponseEntity.of(alumnoRepositorio.findById(id)
                .map(GetAlumnoDetailsDto::of));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAlumnoDto.class))
                    )})
    }
    )
    @Operation(summary = "create Alumno", description = "Creación de un alumno")
    @PostMapping("/")
    public ResponseEntity<GetAlumnoDto> createAlumno(@RequestBody CreateAlumnoDto nuevo){
        return ResponseEntity.status(201).body(GetAlumnoDto.of(alumnoServicio.save(nuevo)));
    }
}
