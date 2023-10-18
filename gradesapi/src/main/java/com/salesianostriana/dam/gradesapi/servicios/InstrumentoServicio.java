package com.salesianostriana.dam.gradesapi.servicios;

import com.salesianostriana.dam.gradesapi.Dto.CreateInstrumentoDto;
import com.salesianostriana.dam.gradesapi.Dto.EditInstrumentoDto;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.Instrumento;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import com.salesianostriana.dam.gradesapi.repositorios.InstrumentoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstrumentoServicio {

    private final InstrumentoRepositorio repositorio;

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
