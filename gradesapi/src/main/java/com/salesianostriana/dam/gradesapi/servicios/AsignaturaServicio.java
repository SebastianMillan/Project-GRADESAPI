package com.salesianostriana.dam.gradesapi.servicios;

import com.salesianostriana.dam.gradesapi.Dto.CreateAsignaturaDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAsignaturaDto;
import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsignaturaServicio {

    private final AsignaturaRepositorio repositorio;

    public Asignatura saveToCreate(CreateAsignaturaDto nuevo){
        List<ReferenteEvaluacion> referentes = new ArrayList<>();
        Asignatura a= new Asignatura();
        a.setDescripcion(nuevo.descripcion());
        a.setHoras(nuevo.horas());
        a.setNombre(nuevo.nombre());
        a.setReferentes(referentes);
        return repositorio.save(a);
    }
}
