package com.salesianostriana.dam.gradesapi.repositorios;

import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlumnoRepositorio extends JpaRepository<Alumno, Long> {

    @Query("SELECT a FROM Calificacion c JOIN c.alumno a WHERE c.instrumento.id = ?1")
    List<Alumno> findAlumsByIdIns(Long idInstrumento);
}
