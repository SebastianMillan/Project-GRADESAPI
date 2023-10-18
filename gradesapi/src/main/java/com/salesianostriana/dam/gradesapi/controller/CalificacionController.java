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
    private final InstrumentoServicio instrumentoServicio;
    private final AsignaturaRepositorio asignaturaRepositorio;
    private final AlumnoServicio alumnoServicio;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCalificacionListDto.class))
                    )})
    })
    @Operation(summary = "create Calificaciones", description = "Creación de un o varias Calificaciones")
    @PostMapping("/")
    public ResponseEntity<?> addCalificaciones(@RequestBody CreateCalificacionDto nueva){
        List<Calificacion> calificaciones = calificacionServicio.saveToCreate(nueva);
        if(calificaciones.isEmpty()){
            return ResponseEntity.status(400).body((new MensajeError("No se puede crear la calificación." +
                    " Compruebe que los datos del instrumento y los referentes son correctos")));
        }

        return ResponseEntity.ok(GetCalificacionListDto.of(calificaciones));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCalificacionPorInstrumentoDto.class))
                    )})
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
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCalificacionPorAsigDto.class))
                    )})
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

}
