package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.*;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
import com.salesianostriana.dam.gradesapi.servicios.AsignaturaServicio;
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
            @ApiResponse(responseCode = "201", description = "Creación de una Asignatura con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAsignaturaDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 3,
                                                "nombre": "Programación",
                                                "horas": 25,
                                                "descripcion": "Creación de páginas web"
                                            }
                                            """
                            )}
                    )})
    })
    @Operation(summary = "create Alumno", description = "Creación de una asignatura")
    @PostMapping("/")
    public ResponseEntity<GetAsignaturaDto> createAsig(@RequestBody CreateAsignaturaDto nuevo){
        return ResponseEntity.status(201).body(GetAsignaturaDto.of(asignaturaServicio.saveToCreate(nuevo)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtención de la Lista de Asignaturas realizada con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAsignaturaListDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                 {
                                                     "id": 1,
                                                     "nombre": "Programación",
                                                     "horas": 25,
                                                     "descripcion": "Creación de páginas web",
                                                     "numReferentes": 3
                                                 },
                                                 {
                                                     "id": 2,
                                                     "nombre": "Base de Datos",
                                                     "horas": 32,
                                                     "descripcion": "Administrar datos",
                                                     "numReferentes": 2
                                                 }
                                             ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Lista de Asignaturas no encontrada", content = @Content),
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
            @ApiResponse(responseCode = "200", description = "Conseguir Asignatura con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAsignaturaDetailsDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "nombre": "Programación",
                                                "horas": 25,
                                                "descripcion": "Creación de páginas web",
                                                "referentes": [
                                                    {
                                                        "codReferente": "RA01.a",
                                                        "descripcion": "Integra correctamente el código fuente de los ejemplos."
                                                    },
                                                    {
                                                        "codReferente": "RA02.a",
                                                        "descripcion": "Se diseñan y utilizan las clases necesarias para producir o consumir información en formato JSON o XML"
                                                    },
                                                    {
                                                        "codReferente": "RA03.a",
                                                        "descripcion": "Se ha documentado correctamente la API REST"
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content),
    })
    @Operation(summary = "findById Asignatura", description = "Obtener una asignatura por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<GetAsignaturaDetailsDto> findById(@PathVariable Long id){
        return ResponseEntity.of(asignaturaRepositorio.findById(id)
                .map(GetAsignaturaDetailsDto::of));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Crear Referentes de Evaluación para una Asignatura realizado con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAsignaturaDetailsDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "nombre": "Programación",
                                                "horas": 25,
                                                "descripcion": "Creación de páginas web",
                                                "referentes": [
                                                    {
                                                        "codReferente": "RA01.a",
                                                        "descripcion": "Integra correctamente el código fuente de los ejemplos."
                                                    },
                                                    {
                                                        "codReferente": "RA02.a",
                                                        "descripcion": "Se diseñan y utilizan las clases necesarias para producir o consumir información en formato JSON o XML"
                                                    },
                                                    {
                                                        "codReferente": "RA03.a",
                                                        "descripcion": "Se ha documentado correctamente la API REST"
                                                    },
                                                    {
                                                        "codReferente": "RA01.Progr.3.077102667294083",
                                                        "descripcion": "El alumno sabe hacer la O con un canuto"
                                                    },
                                                    {
                                                        "codReferente": "RA01.Progr.2.5693772294779427",
                                                        "descripcion": "Elasdasdcanuto"
                                                    },
                                                    {
                                                        "codReferente": "RA01.Progr.5.267801037275929",
                                                        "descripcion": "El alumno sabeasdasdasd con un canuto"
                                                    },
                                                    {
                                                        "codReferente": "RA01.Progr.4.084958812368009",
                                                        "descripcion": "El alumsssscon un canuto"
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content),

    })
    @Operation(summary = "addReferenteEvaluacionToAsignatura", description = "Añadir uno o varios Referentes de Evaluación a una Asignatura")
    @PostMapping("/{id}/referente")
    public ResponseEntity<GetAsignaturaDetailsDto> addRefToAsig(@PathVariable Long id, @RequestBody GetReferenteShortDto[] referentes){
        Optional<Asignatura> as = asignaturaRepositorio.findById(id);
        if(as.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.status(201).body(GetAsignaturaDetailsDto.of(asignaturaServicio.addRefToAsig(as.get(), referentes)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edición de la Asignatura realizada con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAsignaturaListDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 2,
                                                "nombre": "AD-PSP",
                                                "horas": 55,
                                                "descripcion": "Creación DTOs",
                                                "numReferentes": 2
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos introducidos erroneos", content = @Content)
    })
    @Operation(summary = "editarAsignatura", description = "Editar una Asignatura")
    @PutMapping("/{id}")
    public ResponseEntity<GetAsignaturaListDto> editAsig(@PathVariable Long id, @RequestBody CreateAsignaturaDto editado){
        Optional<Asignatura> as = asignaturaRepositorio.findById(id);
        if(as.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(GetAsignaturaListDto.of(asignaturaServicio.saveToEdit(editado, id)));

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edición del Referente de Evaluación de la Asignatura realizado con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetAsignaturaDetailsDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "nombre": "Programación",
                                                "horas": 25,
                                                "descripcion": "Creación de páginas web",
                                                "referentes": [
                                                    {
                                                        "codReferente": "RA01.a",
                                                        "descripcion": "Integra correctamente el código fuente de los ejemplos."
                                                    },
                                                    {
                                                        "codReferente": "RA02.a",
                                                        "descripcion": "Se diseñan y utilizan las clases necesarias para producir o consumir información en formato JSON o XML"
                                                    },
                                                    {
                                                        "codReferente": "RA03.a",
                                                        "descripcion": "Se ha documentado correctamente la API REST"
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content)
    })
    @Operation(summary = "editarDescripcion ReferenteEvaluacion", description = "Editar la descripción del Referente de Evaluación de una Asignatura")
    @PutMapping("/{id}/referente/{cod_ref}")
    public ResponseEntity<GetAsignaturaDetailsDto> editRefOfAsig(@PathVariable Long id, @PathVariable String cod_ref, @RequestBody GetReferenteShortDto textRefEdit){
        Optional<Asignatura> as = asignaturaRepositorio.findById(id);
        if(as.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(GetAsignaturaDetailsDto.of(asignaturaServicio.editRefOfAsig(cod_ref, as.get(), textRefEdit)));


    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asignatura eliminada con exito", content = @Content),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content)
    })
    @Operation(summary = "deleteMatricula de Alumno", description = "Eliminar Asignatura")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAsig(@PathVariable Long id){
        if(!asignaturaRepositorio.existsById(id))
            return ResponseEntity.notFound().build();

        asignaturaServicio.deleteAsig(id);
        return ResponseEntity.noContent().build();
    }
}
