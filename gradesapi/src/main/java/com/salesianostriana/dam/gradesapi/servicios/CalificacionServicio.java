package com.salesianostriana.dam.gradesapi.servicios;

import com.salesianostriana.dam.gradesapi.Dto.CreateCalificacionDto;
import com.salesianostriana.dam.gradesapi.Dto.GetCalificacionListDto;
import com.salesianostriana.dam.gradesapi.Dto.GetCalificacionShortDto;
import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Calificacion;
import com.salesianostriana.dam.gradesapi.modelo.Instrumento;
import com.salesianostriana.dam.gradesapi.repositorios.AlumnoRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.CalificacionRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.InstrumentoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalificacionServicio {

    private final CalificacionRepositorio repositorio;
    private final InstrumentoRepositorio instrumentoRepositorio;
    private final AlumnoRepositorio alumnoRepositorio;

    public List<Calificacion> saveToCreate(CreateCalificacionDto nueva){
        List<Calificacion> calificaciones = new ArrayList<>();
        Optional<Alumno> a = alumnoRepositorio.findById(nueva.idAlumno());
        Optional<Instrumento> i = instrumentoRepositorio.findById(nueva.idInstrumento());

        if(a.isEmpty())
            return null;
        else if (i.isEmpty())
            return null;
        else{
            for(GetCalificacionShortDto c : nueva.calificaciones()){
                Calificacion cal = new Calificacion();
                cal.setCalificacion(c.calificacion());
                cal.setInstrumento(i.get());
                cal.setAlumno(a.get());
                cal.setReferente(repositorio.findRefByCod(c.codReferente()));
                calificaciones.add(cal);
            }
            return repositorio.saveAll(calificaciones);
        }
    }
}
