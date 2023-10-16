package com.salesianostriana.dam.gradesapi.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Asignatura {

    @Id @GeneratedValue
    private Long id;

    private String nombre;

    private int horas;

    private String descripcion;

    @OneToMany(mappedBy = "asignatura", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ReferenteEvaluacion> referentes = new ArrayList<>();

    // Helpers

    // En este caso el lado propietario es Asignatura, ya que tiene CascadeType.ALL
    public void addReferente(ReferenteEvaluacion referenteEvaluacion) {
        referentes.add(referenteEvaluacion);
        referenteEvaluacion.setAsignatura(this);
    }

    public void removeReferente(ReferenteEvaluacion referenteEvaluacion) {
        referentes.remove(referenteEvaluacion);
        referenteEvaluacion.setAsignatura(null);
    }

}
