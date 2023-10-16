package com.salesianostriana.dam.gradesapi.repositorios;

import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AsignaturaRepositorio extends JpaRepository<Asignatura, Long> {

    @Query("SELECT rf FROM ReferenteEvaluacion rf WHERE rf.asignatura = :asignatura AND rf.codReferente = :cod_ref")
    Optional<ReferenteEvaluacion> findByCodAndAsig(@Param("asignatura") Asignatura asignatura, @Param("cod_ref") String cod_ref);

}
