package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.*;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.Calificacion;
import com.salesianostriana.dam.gradesapi.modelo.Instrumento;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.CalificacionRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.InstrumentoRepositorio;
import com.salesianostriana.dam.gradesapi.servicios.AlumnoServicio;
import com.salesianostriana.dam.gradesapi.servicios.CalificacionServicio;
import com.salesianostriana.dam.gradesapi.servicios.InstrumentoServicio;
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
@RequestMapping("/calificacion")
@RequiredArgsConstructor
@Tag(name="Calificacion", description = "Controlador de Calificacion API REST con operaciones CRUD")
public class CalificacionController {

    private final CalificacionServicio calificacionServicio;
    private final InstrumentoRepositorio instrumentoRepositorio;
    private final CalificacionRepositorio calificacionRepositorio;
    private final AsignaturaRepositorio asignaturaRepositorio;
    private final AlumnoServicio alumnoServicio;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Añadir Calificación con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCalificacionListDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "idInstrumento": 1,
                                                "nombre": "Examen Tema 3",
                                                "calificaciones": [
                                                    {
                                                        "id": 13,
                                                        "codReferente": "RA02.a",
                                                        "nombre": "Se diseñan y utilizan las clases necesarias para producir o consumir información en formato JSON o XML",
                                                        "calificacion": 4.0
                                                    },
                                                    {
                                                        "id": 14,
                                                        "codReferente": "RA01.a",
                                                        "nombre": "Integra correctamente el código fuente de los ejemplos.",
                                                        "calificacion": 8.0
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Calificaciones no encontradas", content = @Content)
    })
    @Operation(summary = "create Calificaciones", description = "Creación de un o varias Calificaciones")
    @PostMapping("/")
    public ResponseEntity<?> addCalificaciones(@RequestBody CreateCalificacionDto nueva){
        List<Calificacion> calificaciones = calificacionServicio.saveToCreate(nueva);
        if(calificaciones.isEmpty()){
            return ResponseEntity.badRequest().body((new MensajeError("No se puede crear la calificación." +
                    " Compruebe que los datos del instrumento y los referentes son correctos")));
        }

        return ResponseEntity.ok(GetCalificacionListDto.of(calificaciones));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtención de la Lista de Calificaciones de un Instrumento realizada con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCalificacionPorInstrumentoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "idInstrumento": 2,
                                                "nombre": "Examen Tema 5",
                                                "alumnos": [
                                                    {
                                                        "id": 3,
                                                        "nombre": "Pepe",
                                                        "apellidos": "Sánchez",
                                                        "calificaciones": [
                                                            {
                                                                "codReferente": "RA01.a",
                                                                "nombre": "Integra correctamente el código fuente de los ejemplos.",
                                                                "calificacion": 5.0
                                                            },
                                                            {
                                                                "codReferente": "RA02.a",
                                                                "nombre": "Se diseñan y utilizan las clases necesarias para producir o consumir información en formato JSON o XML",
                                                                "calificacion": 7.0
                                                            },
                                                            {
                                                                "codReferente": "RA03.a",
                                                                "nombre": "Se ha documentado correctamente la API REST",
                                                                "calificacion": 9.5
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        "id": 1,
                                                        "nombre": "Sebastián",
                                                        "apellidos": "Millán Ordóñez",
                                                        "calificaciones": [
                                                            {
                                                                "codReferente": "RA01.a",
                                                                "nombre": "Integra correctamente el código fuente de los ejemplos.",
                                                                "calificacion": 6.0
                                                            },
                                                            {
                                                                "codReferente": "RA02.a",
                                                                "nombre": "Se diseñan y utilizan las clases necesarias para producir o consumir información en formato JSON o XML",
                                                                "calificacion": 3.5
                                                            },
                                                            {
                                                                "codReferente": "RA03.a",
                                                                "nombre": "Se ha documentado correctamente la API REST",
                                                                "calificacion": 6.9
                                                            }
                                                        ]
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Instrumento no encontrado", content = @Content)
    })
    @Operation(summary = "getAll Calificaciones by Instrumento", description = "Obtener todas las Calificaciónes de un Instrumento de todos los Alumnos calificados")
    @GetMapping("/instrumento/{id}")
    public ResponseEntity<GetCalificacionPorInstrumentoDto> getAllCalByIns(@PathVariable Long id){
        Optional<Instrumento> ins =  instrumentoRepositorio.findById(id);

        if(ins.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                new GetCalificacionPorInstrumentoDto(
                        id,
                        ins.get().getNombre(),
                        alumnoServicio.findAlumsByIdIns(id).stream()
                                .map(a -> GetAlumnoEnCalificacionDto.of(a, calificacionServicio.findCalificacionesByInsAndByAl(id, a.getId())))
                                .toList())

        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtención de la Lista de Calificaciones de una Asignatura realizada con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCalificacionPorAsigDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "idAsignatura": 1,
                                                "codReferente": "RA02.a",
                                                "alumnos": [
                                                    {
                                                        "id": 1,
                                                        "nombre": "Sebastián",
                                                        "apellidos": "Millán Ordóñez",
                                                        "calificaciones": [
                                                            {
                                                                "idInstrumento": 3,
                                                                "calificacion": 6.0
                                                            },
                                                            {
                                                                "idInstrumento": 2,
                                                                "calificacion": 3.5
                                                            },
                                                            {
                                                                "idInstrumento": 1,
                                                                "calificacion": 4.0
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        "id": 3,
                                                        "nombre": "Pepe",
                                                        "apellidos": "Sánchez",
                                                        "calificaciones": [
                                                            {
                                                                "idInstrumento": 3,
                                                                "calificacion": 6.0
                                                            },
                                                            {
                                                                "idInstrumento": 2,
                                                                "calificacion": 3.5
                                                            },
                                                            {
                                                                "idInstrumento": 1,
                                                                "calificacion": 4.0
                                                            }
                                                        ]
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Referentes de Evaluación de la Asignatura no encontrados", content = @Content),
    })
    @Operation(summary = "getAll Calificaciones by ReferenteEvaluacion", description = "Obtener todas las Calificaciónes de un Referente de Evaluación de todos los Alumnos calificados")
    @GetMapping("/referente/{id}/{cod_ref}")
    public ResponseEntity<GetCalificacionPorAsigDto> findAllCalByAsig(@PathVariable Long id, @PathVariable String cod_ref){
        Optional<Asignatura> a = asignaturaRepositorio.findById(id);

        if(a.isEmpty() || a.get().getReferentes().isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                new GetCalificacionPorAsigDto(id, cod_ref, alumnoServicio.findAlumsByRef(cod_ref).stream()
                        .map(al -> GetAlumnoEnCalPorAsigDto.of(al,calificacionServicio.findCalificacionesByRefAndByAl(cod_ref, id)))
                        .toList())
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Borrado de Calificación realizado con exito", content = @Content),
            @ApiResponse(responseCode = "404", description = "Calificación no encontrada", content = @Content)
    })
    @Operation(summary = "deleteCalificacion", description = "Eliminar una Calificación por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCal(@PathVariable Long id){
        Optional<Calificacion> cal = calificacionRepositorio.findById(id);

        if(cal.isEmpty())
            return ResponseEntity.notFound().build();

        calificacionRepositorio.delete(cal.get());
        return ResponseEntity.noContent().build();
    }

}
