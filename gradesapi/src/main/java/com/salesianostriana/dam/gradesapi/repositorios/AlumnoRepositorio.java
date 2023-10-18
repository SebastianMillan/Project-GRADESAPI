package com.salesianostriana.dam.gradesapi.repositorios;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlumnoRepositorio extends JpaRepository<Alumno, Long> {

    @Query("SELECT a FROM Calificacion c JOIN c.alumno a WHERE c.instrumento.id = ?1")
    List<Alumno> findAlumsByIdIns(Long idInstrumento);

    @Query("SELECT a FROM Alumno a JOIN a.asignaturas as JOIN as.referentes rf WHERE rf.codReferente LIKE ?1")
    List<Alumno> findAlumsByRef(String cod_ref);

    @Query("SELECT c FROM Calificacion c WHERE c.alumno.id = ?1")
    List<Calificacion> findCalByAlum(Long id);
}
