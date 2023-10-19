package com.salesianostriana.dam.gradesapi.servicios;

import com.salesianostriana.dam.gradesapi.Dto.CreateInstrumentoDto;
import com.salesianostriana.dam.gradesapi.Dto.EditInstrumentoDto;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.Instrumento;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacionPK;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.CalificacionRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.InstrumentoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstrumentoServicio {

    private final InstrumentoRepositorio repositorio;
    private final CalificacionRepositorio calificacionRepositorio;
    private final AsignaturaRepositorio asignaturaRepositorio;

    public Long getIdAsignaturaRefInst(Set<ReferenteEvaluacion> referentes){
        Iterator<ReferenteEvaluacion> it = referentes.iterator();
        if(it.hasNext())
            return it.next().getAsignatura().getId();

        return null;
    }

    public Instrumento createIns(CreateInstrumentoDto nuevo){
        Instrumento i = new Instrumento();
        i.setContenidos(nuevo.contenidos());
        i.setFecha(LocalDateTime.parse(nuevo.fecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        i.setNombre(nuevo.nombre());
        i.setReferentes(nuevo.referentes().stream()
                .map(repositorio::findRefByCodRef)
                .collect(Collectors.toSet()));
        return repositorio.save(i);
    }
    public Instrumento saveToEdit(EditInstrumentoDto edicion, Instrumento antiguo){
        antiguo.setFecha(LocalDateTime.parse(edicion.fecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        antiguo.setNombre(edicion.nombre());
        antiguo.setContenidos(edicion.contenidos());
        return repositorio.save(antiguo);
    }
    public void deleteIns(Instrumento i){
        calificacionRepositorio.deleteAll(calificacionRepositorio.findAllCalifByIns(i.getId()));
        repositorio.delete(i);
    }
    public Instrumento addRefToInst(Instrumento i, String cod_ref){
        ReferenteEvaluacion rf = findRefByCodRef(cod_ref);
        if(rf!=null){
            if(!i.getReferentes().contains(rf)){
                i.getReferentes().add(rf);
                return repositorio.save(i);
            }
            return repositorio.save(i);
        }else{
            return i;
        }

    }

    public void deleteReferenteByIns(Instrumento i, String cod_ref){
        Optional<ReferenteEvaluacion> rf = i.getReferentes().stream().filter(refe -> refe.getCodReferente().equalsIgnoreCase(cod_ref)).findAny();
        if(rf.isPresent()){
            i.getReferentes().removeIf(refe -> refe.getCodReferente().equalsIgnoreCase(rf.get().getCodReferente()));
            repositorio.save(i);
        }
    }

    public List<Instrumento> findAllInstr(Long idAsig){
        return repositorio.findAllInstByAsig(idAsig);
    }

    public Optional<Asignatura> findAsigByInsId(Long id){
        return repositorio.findAsigByInsId(id);
    }

    public ReferenteEvaluacion findRefByCodRef(String cod_ref){
        return repositorio.findRefByCodRef(cod_ref);
    }
}
