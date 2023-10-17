package com.salesianostriana.dam.gradesapi.repositorios;

import com.salesianostriana.dam.gradesapi.modelo.Instrumento;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstrumentoRepositorio extends JpaRepository<Instrumento, Long> {
    @Query("SELECT rf FROM ReferenteEvaluacion rf WHERE rf.codReferente ILIKE ?1")
    ReferenteEvaluacion findRefByCodRef(String codRef);

    @Query("SELECT DISTINCT i FROM Instrumento i JOIN i.referentes r WHERE r.asignatura.id = ?1")
    List<Instrumento> findAllInstByAsig(Long codAsig);
}
