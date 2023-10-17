package com.salesianostriana.dam.gradesapi.repositorios;

import com.salesianostriana.dam.gradesapi.modelo.Calificacion;
import com.salesianostriana.dam.gradesapi.modelo.ReferenteEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CalificacionRepositorio extends JpaRepository<Calificacion, Long> {

    @Query("SELECT rf FROM ReferenteEvaluacion rf WHERE rf.codReferente LIKE ?1")
    ReferenteEvaluacion findRefByCod(String codRef);
}
