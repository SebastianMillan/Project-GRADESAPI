package com.salesianostriana.dam.gradesapi.servicios;

import com.salesianostriana.dam.gradesapi.Dto.CreateAlumnoDto;
import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.repositorios.AlumnoRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumnoServicio {

    private final AlumnoRepositorio alumnoRepositorio;
    private final AsignaturaRepositorio asignaturaRepositorio;

    public Alumno save(CreateAlumnoDto nuevo){
        Set<Asignatura> asignatura = nuevo.asignaturas().stream()
                .map(asignaturaRepositorio::getReferenceById)
                .collect(Collectors.toSet());

        Alumno a = new Alumno(
                nuevo.id(),
                nuevo.nombre(),
                nuevo.apellidos(),
                nuevo.email(),
                nuevo.telefono(),
                nuevo.fechaNacimiento(),
                asignatura
        );
        return alumnoRepositorio.save(a);
    }
}
