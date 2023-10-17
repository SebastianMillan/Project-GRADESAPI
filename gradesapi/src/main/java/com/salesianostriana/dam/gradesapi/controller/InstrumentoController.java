package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.CreateInstrumentoDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAsignaturaDto;
import com.salesianostriana.dam.gradesapi.Dto.GetInstrumentoDetailsDto;
import com.salesianostriana.dam.gradesapi.Dto.GetInstrumentoListDto;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.Instrumento;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
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
@RequiredArgsConstructor
@RequestMapping("/instrumento")
@Tag(name="Instrumento", description = "Controlador de Instrumento API REST con operaciones CRUD")
public class InstrumentoController {

    private final InstrumentoServicio instrumentoServicio;
    private final AsignaturaRepositorio asignaturaRepositorio;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetInstrumentoDetailsDto.class))
                    )})
    })
    @Operation(summary = "create Instrumento", description = "Creación de una Instrumento de Evaluación")
    @PostMapping("/")
    public ResponseEntity<GetInstrumentoDetailsDto> createIns(@RequestBody CreateInstrumentoDto nuevo){
        return ResponseEntity.status(201).body(GetInstrumentoDetailsDto.of(instrumentoServicio.createIns(nuevo), nuevo.idAsignatura()));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetInstrumentoListDto.class))
                    )})
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

}
