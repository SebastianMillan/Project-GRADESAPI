package com.salesianostriana.dam.gradesapi.servicios;

import com.salesianostriana.dam.gradesapi.Dto.CreateAsignaturaDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAsignaturaDto;
import com.salesianostriana.dam.gradesapi.Dto.GetReferenteShortDto;
import com.salesianostriana.dam.gradesapi.modelo.*;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.CalificacionRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.InstrumentoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignaturaServicio {

    private final AsignaturaRepositorio repositorio;
    private final InstrumentoServicio instrumentoServicio;
    private final InstrumentoRepositorio instrumentoRepositorio;
    private final CalificacionRepositorio calificacionRepositorio;

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

    public Optional<ReferenteEvaluacion> findByCodAndAsig(String cod_ref, Long id){
        return repositorio.findByCodAndAsig(id, cod_ref);
    }

    public Asignatura editRefOfAsig(String cod_ref, Asignatura a, GetReferenteShortDto textToEdit){
        Optional<ReferenteEvaluacion> rf = findByCodAndAsig(cod_ref, a.getId());
        if(rf.isEmpty()){
            return a;
        }else{
            rf.get().setDescripcion(textToEdit.descripcion());
            return repositorio.save(a);
        }
    }

    public void deleteAsig(Long id){

        Optional<Asignatura> a =repositorio.findById(id);
        a.get().getReferentes().removeIf(rf -> Objects.equals(rf.getAsignatura().getId(), a.get().getId()));
        calificacionRepositorio.deleteAll(calificacionRepositorio.findAllCalifByAsg(id));
        instrumentoRepositorio.deleteAll(instrumentoRepositorio.findAllInstByAsig(id));
        repositorio.save(a.get());
        repositorio.delete(a.get());

    }
}
