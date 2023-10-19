package com.salesianostriana.dam.gradesapi.repositorios;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Calificacion;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalificacionRepositorio extends JpaRepository<Calificacion, Long> {

    @Query("SELECT rf FROM ReferenteEvaluacion rf WHERE rf.codReferente LIKE ?1")
    ReferenteEvaluacion findRefByCod(String codRef);

    @Query("SELECT c FROM Calificacion c WHERE c.instrumento.id = ?1")
    List<Calificacion> findAllCalifByIns(Long idInstrumento);

    @Query("SELECT c FROM Calificacion c WHERE c.instrumento.id = ?1 AND c.alumno.id = ?2")
    List<Calificacion> findCalificacionesByInsAndByAl(Long idInstrumento, Long idAlumno);

    @Query("SELECT c FROM Calificacion c WHERE c.referente.codReferente LIKE ?1 AND c.alumno.id = ?2")
    List<Calificacion> findCalificacionesByRefAndByAl(String cod_ref, Long idAlumno);

}
