package com.salesianostriana.dam.gradesapi.servicios;

import com.salesianostriana.dam.gradesapi.Dto.CreateAsignaturaDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAsignaturaDto;
import com.salesianostriana.dam.gradesapi.Dto.GetReferenteShortDto;
import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacionPK;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Asignatura saveToEdit(CreateAsignaturaDto editado, Long id){
        List<ReferenteEvaluacion> referentes = new ArrayList<>();
        Asignatura a = new Asignatura(
                id,
                editado.nombre(),
                editado.horas(),
                editado.descripcion(),
                repositorio.findById(id).isPresent() ?
                        repositorio.findById(id).get().getReferentes()
                        : referentes

        );
        return repositorio.save(a);
    }

    public Asignatura addRefToAsig(Asignatura a, GetReferenteShortDto[] referentes){
        for (GetReferenteShortDto referente : referentes) {
            a.addReferente(new ReferenteEvaluacion(
                    a,
                    "RA0"+a.getId()+"."+a.getNombre().substring(0,5)+"."+(Math.random()*a.getReferentes().size()+1),
                    referente.descripcion()
            ));
        }
        return repositorio.save(a);
    }

    public Optional<ReferenteEvaluacion> findByCodAndAsig(String cod_ref, Asignatura a){
        return repositorio.findByCodAndAsig(a, cod_ref);
    }

    public Asignatura editRefOfAsig(String cod_ref, Asignatura a, GetReferenteShortDto textToEdit){
        Optional<ReferenteEvaluacion> rf = findByCodAndAsig(cod_ref, a);
        if(rf.isEmpty())
            return a;

        a.removeReferente(rf.get());
        ReferenteEvaluacion rfNuevo = new ReferenteEvaluacion(
                a,
                cod_ref,
                textToEdit.descripcion()
        );
        a.addReferente(rfNuevo);
        return repositorio.save(a);
    }
}
