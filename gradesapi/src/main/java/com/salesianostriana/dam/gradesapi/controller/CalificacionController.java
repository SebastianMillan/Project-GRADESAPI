package com.salesianostriana.dam.gradesapi.controller;

import com.salesianostriana.dam.gradesapi.Dto.CreateCalificacionDto;
import com.salesianostriana.dam.gradesapi.Dto.GetCalificacionListDto;
import com.salesianostriana.dam.gradesapi.Dto.GetInstrumentoDetailsDto;
import com.salesianostriana.dam.gradesapi.Dto.MensajeError;
import com.salesianostriana.dam.gradesapi.modelo.Calificacion;
import com.salesianostriana.dam.gradesapi.repositorios.CalificacionRepositorio;
import com.salesianostriana.dam.gradesapi.servicios.CalificacionServicio;
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

import java.util.List;

@RestController
@RequestMapping("/calificacion")
@RequiredArgsConstructor
@Tag(name="Calificacion", description = "Controlador de Calificacion API REST con operaciones CRUD")
public class CalificacionController {

    private final CalificacionServicio calificacionServicio;
    private final CalificacionRepositorio calificacionRepositorio;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
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

}
