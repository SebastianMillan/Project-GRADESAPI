package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.*;
import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.repositorios.AlumnoRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
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
import java.util.Optional;

@RestController
@RequestMapping("/alumno")
@RequiredArgsConstructor
@Tag(name="Alumno", description = "Controlador de Alumno API REST con operaciones CRUD")
public class AlumnoController {

    private final AlumnoRepositorio alumnoRepositorio;
    private final AsignaturaRepositorio asignaturaRepositorio;
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
        return ResponseEntity.status(201).body(GetAlumnoDto.of(alumnoServicio.saveToCreate(nuevo)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAlumnoListDto.class))
                    )})
    }
    )
    @Operation(summary = "editById Alumno", description = "Editar alumno obtenido por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<GetAlumnoListDto> editAlumno(@RequestBody EditAlumnoDto editado, @PathVariable Long id){
        if(alumnoRepositorio.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(GetAlumnoListDto.of(alumnoServicio.saveToEdit(editado, id)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "")
    })
    @Operation(summary = "addAsignaturaToAlumno", description = "Matriculación de un Alumno en una Asignatura")
    @PostMapping("/{id}/matricula/{id_asig}")
    public ResponseEntity<?> addAsigToAlum(@PathVariable Long id, @PathVariable Long id_asig){
        Optional<Alumno> a = alumnoRepositorio.findById(id);
        Optional<Asignatura> as = asignaturaRepositorio.findById(id_asig);

        if(a.isEmpty() || as.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            alumnoServicio.addAsig(a.get(),as.get());
            return ResponseEntity.status(201).build();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "")
    })
    @Operation(summary = "deleteAlumno", description = "Eliminar Alumno obtenido por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlum(@PathVariable Long id){
        Optional<Alumno> a = alumnoRepositorio.findById(id);
        if(a.isEmpty())
            return ResponseEntity.notFound().build();

        alumnoServicio.deleteCalToAlum(a.get().getId());
        alumnoRepositorio.delete(a.get());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/matricula/{id_asig}")
    public ResponseEntity<?> deleteMatrAlum(@PathVariable Long id, @PathVariable Long id_asig){
        Optional<Alumno> a = alumnoRepositorio.findById(id);
        if(a.isEmpty())
            return ResponseEntity.notFound().build();

        alumnoServicio.deleteCalByAsig(id, id_asig);

        return ResponseEntity.noContent().build();
    }
}
