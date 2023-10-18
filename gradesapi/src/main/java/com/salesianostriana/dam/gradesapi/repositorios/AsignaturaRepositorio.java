package com.salesianostriana.dam.gradesapi.repositorios;

import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AsignaturaRepositorio extends JpaRepository<Asignatura, Long> {

    @Query("SELECT DISTINCT rf FROM ReferenteEvaluacion rf WHERE rf.asignatura.id = ?1 AND rf.codReferente = ?2")
    Optional<ReferenteEvaluacion> findByCodAndAsig(Long id, String cod_ref);

}
