package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.*;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.Instrumento;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.InstrumentoRepositorio;
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

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instrumento")
@Tag(name="Instrumento", description = "Controlador de Instrumento API REST con operaciones CRUD")
public class InstrumentoController {

    private final InstrumentoServicio instrumentoServicio;
    private final InstrumentoRepositorio instrumentoRepositorio;
    private final AsignaturaRepositorio asignaturaRepositorio;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creación del Instrumento con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetInstrumentoDetailsDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 4,
                                                "fecha": "20/01/2024",
                                                "nombre": "Test",
                                                "contenidos": "Muchos",
                                                "idAsignatura": 1,
                                                "referentes": [
                                                    {
                                                        "codReferente": "RA02.a",
                                                        "descripcion": "Se diseñan y utilizan las clases necesarias para producir o consumir información en formato JSON o XML"
                                                    },
                                                    {
                                                        "codReferente": "RA01.a",
                                                        "descripcion": "Integra correctamente el código fuente de los ejemplos."
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Error en los datos", content = @Content)
    })
    @Operation(summary = "create Instrumento", description = "Creación de una Instrumento de Evaluación")
    @PostMapping("/")
    public ResponseEntity<GetInstrumentoDetailsDto> createIns(@RequestBody CreateInstrumentoDto nuevo){
        return ResponseEntity.status(201).body(GetInstrumentoDetailsDto.of(instrumentoServicio.createIns(nuevo), nuevo.idAsignatura()));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de los Instrumentos creado con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetInstrumentoListDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 1,
                                                    "fecha": "Examen Tema 3",
                                                    "nombre": "20/03/2024 12:30",
                                                    "contenidos": "Creación de tablas y consultas",
                                                    "numeroReferentes": 2
                                                },
                                                {
                                                    "id": 3,
                                                    "fecha": "Test",
                                                    "nombre": "24/12/2023 13:30",
                                                    "contenidos": "Clases CRUD",
                                                    "numeroReferentes": 1
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Lista de Instrumentos vacia", content = @Content),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content)
    })
    @Operation(summary = "findAll Instrumentos", description = "Obtener todos los Instrumentos de una Asignatura")
    @GetMapping("/{id_asig}")
    public ResponseEntity<List<GetInstrumentoListDto>> getAll(@PathVariable Long id_asig){
        Optional<Asignatura> a = asignaturaRepositorio.findById(id_asig);
        List<Instrumento> instrumentos = instrumentoServicio.findAllInstr(id_asig);
        if(a.isEmpty() || instrumentos.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(instrumentos.stream()
                            .map(GetInstrumentoListDto::of)
                            .toList()
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado detallado del Instrumento creado con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetInstrumentoCompleteDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 2,
                                                "fecha": "10/02/2024",
                                                "nombre": "Examen Tema 5",
                                                "contenidos": "Creación de Clases y métodos",
                                                "asignatura": {
                                                    "id": 1,
                                                    "nombre": "Programación"
                                                },
                                                "referentes": [
                                                    {
                                                        "codReferente": "RA03.a",
                                                        "descripcion": "Se ha documentado correctamente la API REST"
                                                    },
                                                    {
                                                        "codReferente": "RA02.a",
                                                        "descripcion": "Se diseñan y utilizan las clases necesarias para producir o consumir información en formato JSON o XML"
                                                    },
                                                    {
                                                        "codReferente": "RA01.a",
                                                        "descripcion": "Integra correctamente el código fuente de los ejemplos."
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Instrumento no encotnrado", content = @Content)
    })
    @Operation(summary = "findById Instrumentos", description = "Obtener el detalle de un instrumento por su ID")
    @GetMapping("/detalle/{id}")
    public ResponseEntity<GetInstrumentoCompleteDto> detailsById(@PathVariable Long id){
        Optional<Instrumento> i = instrumentoRepositorio.findById(id);
        Optional<Asignatura> a = instrumentoServicio.findAsigByInsId(id);
        if(i.isEmpty() || a.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(GetInstrumentoCompleteDto.of(i.get(), a.get()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instrumento editado con exito", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetInstrumentoListDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "id": 1,
                                                 "fecha": "Examen T10",
                                                 "nombre": "10/02/2024 12:00",
                                                 "contenidos": "Creación proyecto Angular",
                                                 "numeroReferentes": 2
                                             }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Instrumento no encontrado", content = @Content)
    })
    @Operation(summary = "edit Instrumentos", description = "Editar un Instrumento por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<GetInstrumentoListDto> editIns(@RequestBody EditInstrumentoDto editado, @PathVariable Long id){
        Optional<Instrumento> i = instrumentoRepositorio.findById(id);
        if(i.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(GetInstrumentoListDto.of(instrumentoServicio.saveToEdit(editado, i.get())));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Instrumento eliminado con exito", content = @Content),
            @ApiResponse(responseCode = "404", description = "Instrumento no encontrado", content = @Content)
    })
    @Operation(summary = "deleteInstrumento", description = "Eliminar un Instrumento por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIns(@PathVariable Long id){
        Optional<Instrumento> i = instrumentoRepositorio.findById(id);
        if(i.isEmpty())
            return ResponseEntity.notFound().build();

        instrumentoServicio.deleteIns(i.get());
        return ResponseEntity.noContent().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El Referente de Evaluación se ha añadido con exito al Instrumento", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetInstrumentoCompleteDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "fecha": "10/02/2024",
                                                "nombre": "Examen T10",
                                                "contenidos": "Creación proyecto Angular",
                                                "asignatura": {
                                                    "id": 1,
                                                    "nombre": "Programación"
                                                },
                                                "referentes": [
                                                    {
                                                        "codReferente": "RA02.b",
                                                        "descripcion": "Desarrolla servicios web sencillos utilizando la arquitectura REST"
                                                    },
                                                    {
                                                        "codReferente": "RA01.b",
                                                        "descripcion": "Se han utilizado las diferentes clases y anotaciones "
                                                    },
                                                    {
                                                        "codReferente": "RA01.a",
                                                        "descripcion": "Integra correctamente el código fuente de los ejemplos."
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "Instrumento no encontrado", content = @Content)
    })
    @Operation(summary = "addReferenteToInstrumento", description = "Añadir un Referente de Evaluación a un Instrumento")
    @PostMapping("/{id}/referente/{cod_ref}")
    public ResponseEntity<GetInstrumentoCompleteDto> addRefToIns(@PathVariable Long id, @PathVariable String cod_ref){
        Optional<Instrumento> i = instrumentoRepositorio.findById(id);
        if(i.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.status(201).body(GetInstrumentoCompleteDto.of(instrumentoServicio.addRefToInst(i.get(), cod_ref), i.get().getAsignatura()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Referente eliminado del Instrumento con exito", content = @Content),
            @ApiResponse(responseCode = "404", description = "Intrumento no encontrado", content = @Content)
    })
    @Operation(summary = "deleteReferente de Instrumento", description = "Eliminar un Referente de Evaluación de un Instrumento")
    @DeleteMapping("/{id}/referente/{cod_ref}")
    public ResponseEntity<?> deleteRefByIns(@PathVariable Long id, @PathVariable String cod_ref){
        Optional<Instrumento> i = instrumentoRepositorio.findById(id);
        if(i.isEmpty())
            return ResponseEntity.notFound().build();

        instrumentoServicio.deleteReferenteByIns(i.get(), cod_ref);
        return ResponseEntity.noContent().build();
    }

}
