package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.CreateInstrumentoDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAsignaturaDto;
import com.salesianostriana.dam.gradesapi.Dto.GetInstrumentoDetailsDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instrumento")
@Tag(name="Instrumento", description = "Controlador de Instrumento API REST con operaciones CRUD")
public class InstrumentoController {

    private final InstrumentoServicio instrumentoServicio;

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

}
