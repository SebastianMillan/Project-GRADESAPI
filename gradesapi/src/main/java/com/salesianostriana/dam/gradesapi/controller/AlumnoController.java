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
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
            @ApiResponse(responseCode = "200", description = "Listado de Alumnos con exito", content = {
                    @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = GetAlumnoListDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 1,
                                                    "nombre": "Sebastián",
                                                    "apellidos": "Millán Ordóñez",
                                                    "email": "sebastian@gmail.com",
                                                    "telefono": "123123321",
                                                    "fechaNacimiento": "26/07/2000",
                                                    "cantidadAsignaturas": 2
                                                },
                                                {
                                                    "id": 2,
                                                    "nombre": "Fernando",
                                                    "apellidos": "Claro De Diego",
                                                    "email": "fernando@gmail.com",
                                                    "telefono": "987987987",
                                                    "fechaNacimiento": "15/02/2002",
                                                    "cantidadAsignaturas": 1
                                                },
                                                {
                                                    "id": 3,
                                                    "nombre": "Pepe",
                                                    "apellidos": "Sánchez",
                                                    "email": "pepe@gmail.com",
                                                    "telefono": "567567444",
                                                    "fechaNacimiento": "03/01/1994",
                                                    "cantidadAsignaturas": 1
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Lista de Alumnos no encontrada", content = @Content)
    })
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
            @ApiResponse(responseCode = "200", description = "Alumno encontrado con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAlumnoDetailsDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "nombre": "Sebastián",
                                                "apellidos": "Millán Ordóñez",
                                                "email": "sebastian@gmail.com",
                                                "telefono": "123123321",
                                                "fechaNacimiento": "26/07/2000",
                                                "asignaturas": [
                                                    {
                                                        "id": 2,
                                                        "nombre": "Base de Datos"
                                                    },
                                                    {
                                                        "id": 1,
                                                        "nombre": "Programación"
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado", content = @Content)
    })
    @Operation(summary = "findById Alumno", description = "Obtener un alumno por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<GetAlumnoDetailsDto> findById(@PathVariable Long id){
        return ResponseEntity.of(alumnoRepositorio.findById(id)
                .map(GetAlumnoDetailsDto::of));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creación de Alumno con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAlumnoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "nombre": "Sebastián",
                                                "apellidos": "Millán Ordóñez",
                                                "email": "sebastian@gmail.com",
                                                "telefono": "123123321",
                                                "fechaNacimiento": "26/07/2000",
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Error en los datos", content = @Content)
    })
    @Operation(summary = "create Alumno", description = "Creación de un alumno")
    @PostMapping("/")
    public ResponseEntity<GetAlumnoDto> createAlumno(@RequestBody CreateAlumnoDto nuevo){
        return ResponseEntity.status(201).body(GetAlumnoDto.of(alumnoServicio.saveToCreate(nuevo)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alumno editado con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAlumnoListDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 1,
                                                    "nombre": "Sebastián",
                                                    "apellidos": "Millán Ordóñez",
                                                    "email": "sebastian@gmail.com",
                                                    "telefono": "123123321",
                                                    "fechaNacimiento": "26/07/2000",
                                                    "cantidadAsignaturas": 2
                                                },
                                                {
                                                    "id": 2,
                                                    "nombre": "Fernando",
                                                    "apellidos": "Claro De Diego",
                                                    "email": "fernando@gmail.com",
                                                    "telefono": "987987987",
                                                    "fechaNacimiento": "15/02/2002",
                                                    "cantidadAsignaturas": 1
                                                },
                                                {
                                                    "id": 3,
                                                    "nombre": "Pepe",
                                                    "apellidos": "Sánchez",
                                                    "email": "pepe@gmail.com",
                                                    "telefono": "567567444",
                                                    "fechaNacimiento": "03/01/1994",
                                                    "cantidadAsignaturas": 1
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos introducidos erroneos", content = @Content)
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
            @ApiResponse(responseCode = "201", description = "Matriculación de un Alumno a la Asignatura con exito", content = @Content),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content)
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
            @ApiResponse(responseCode = "204", description = "Alumno eliminado con exito", content = @Content),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado", content = @Content)
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Matrícula eliminada del Alumno con exito", content = @Content),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado", content = @Content)
    })
    @Operation(summary = "deleteMatricula de Alumno", description = "Eliminar matricula de una Asignatura de un Alumno")
    @DeleteMapping("/{id}/matricula/{id_asig}")
    public ResponseEntity<?> deleteMatrAlum(@PathVariable Long id, @PathVariable Long id_asig){
        Optional<Alumno> a = alumnoRepositorio.findById(id);
        if(a.isEmpty())
            return ResponseEntity.notFound().build();

        alumnoServicio.deleteCalByAsig(id_asig, id);

        return ResponseEntity.noContent().build();
    }
}
