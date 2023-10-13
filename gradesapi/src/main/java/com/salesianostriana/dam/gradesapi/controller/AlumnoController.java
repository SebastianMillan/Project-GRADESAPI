package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.GetAlumnoListDto;
import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.repositorios.AlumnoRepositorio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alumno")
@RequiredArgsConstructor
@Tag(name="Alumno", description = "Controlador de Alumno API REST con operaciones CRUD")
public class AlumnoController {

    private final AlumnoRepositorio alumnoRepositorio;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = GetAlumnoListDto.class))
                    )})
            }
    )
    @Operation(summary = "findAll alumnos", description = "Obtener todos los alumnos")
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
}
