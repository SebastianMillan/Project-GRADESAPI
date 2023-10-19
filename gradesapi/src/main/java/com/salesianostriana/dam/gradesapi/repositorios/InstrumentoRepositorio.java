package com.salesianostriana.dam.gradesapi.repositorios;

import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.Instrumento;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InstrumentoRepositorio extends JpaRepository<Instrumento, Long> {
    @Query("SELECT rf FROM ReferenteEvaluacion rf WHERE rf.codReferente ILIKE ?1")
    ReferenteEvaluacion findRefByCodRef(String codRef);

    @Query("SELECT i FROM Instrumento i JOIN i.asignatura a WHERE a.id = ?1")
    List<Instrumento> findAllInstByAsig(Long codAsig);

    @Query("SELECT DISTINCT a FROM Instrumento i JOIN i.referentes r JOIN r.asignatura a WHERE i.id = ?1")
    Optional<Asignatura> findAsigByInsId(Long id);
}
