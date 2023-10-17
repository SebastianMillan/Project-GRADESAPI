package com.salesianostriana.dam.gradesapi.servicios;

import com.salesianostriana.dam.gradesapi.Dto.CreateAlumnoDto;
import com.salesianostriana.dam.gradesapi.Dto.EditAlumnoDto;
import com.salesianostriana.dam.gradesapi.Dto.GetAlumnoDto;
import com.salesianostriana.dam.gradesapi.modelo.Alumno;
import com.salesianostriana.dam.gradesapi.modelo.Asignatura;
import com.salesianostriana.dam.gradesapi.repositorios.AlumnoRepositorio;
import com.salesianostriana.dam.gradesapi.repositorios.AsignaturaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumnoServicio {

    private final AlumnoRepositorio alumnoRepositorio;
    private final AsignaturaRepositorio asignaturaRepositorio;

    public Alumno saveToCreate(CreateAlumnoDto nuevo){
        Set<Asignatura> asignaturas = nuevo.asignaturas().stream()
                .map(asignaturaRepositorio::getReferenceById)
                .collect(Collectors.toSet());

        Alumno a = new Alumno();
        a.setApellidos(nuevo.apellidos());
        a.setEmail(nuevo.email());
        a.setNombre(nuevo.nombre());
        a.setFechaNacimiento(LocalDate.parse(nuevo.fechaNacimiento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        a.setTelefono(nuevo.telefono());
        a.setAsignaturas(asignaturas);

        return alumnoRepositorio.save(a);
    }
    public Alumno saveToEdit(EditAlumnoDto nuevo, Long id){
        Set<Asignatura> asignaturas = new HashSet<>();
        Alumno a = new Alumno(
                id,
                nuevo.nombre(),
                nuevo.apellidos(),
                nuevo.email(),
                nuevo.telefono(),
                LocalDate.parse(nuevo.fechaNacimiento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                alumnoRepositorio.findById(id).isPresent()
                        ? alumnoRepositorio.findById(id).get().getAsignaturas()
                        : asignaturas
        );
        return alumnoRepositorio.save(a);
    }

    public void addAsig(Alumno a, Asignatura as){
        a.getAsignaturas().add(as);
        alumnoRepositorio.save(a);
    }

    public List<Alumno> findAlumsByIdIns(Long idInstrumento){
        return alumnoRepositorio.findAlumsByIdIns(idInstrumento);
    }
}
