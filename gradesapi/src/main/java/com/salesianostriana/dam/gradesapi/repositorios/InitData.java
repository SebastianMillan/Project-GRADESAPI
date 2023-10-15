package com.salesianostriana.dam.gradesapi.repositorios;

import com.salesianostriana.dam.gradesapi.modelo.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitData {

    private final AsignaturaRepositorio asignaturaRepositorio;
    private final AlumnoRepositorio alumnoRepositorio;
    private final CalificacionRepositorio calificacionRepositorio;
    private final InstrumentoRepositorio instrumentoRepositorio;

    @PostConstruct
    public void init(){

        List<ReferenteEvaluacion> referentes1 = new ArrayList<>();
        List<ReferenteEvaluacion> referentes2 = new ArrayList<>();


        Asignatura a1 = new Asignatura(
                1L,
                "Programación",
                25,
                "Creación de páginas web",
                referentes1
        );
        Asignatura a2 = new Asignatura(
                2L,
                "Base de Datos",
                32,
                "Administrar datos",
                referentes2
        );

        ReferenteEvaluacion rf1 = new ReferenteEvaluacion(
                a1,
                "RA01.a",
                "Integra correctamente el código fuente de los ejemplos."
        );
        ReferenteEvaluacion rf2 = new ReferenteEvaluacion(
                a1,
                "RA02.a",
                "Se diseñan y utilizan las clases necesarias para producir o consumir información en formato JSON o XML"
        );
        ReferenteEvaluacion rf3 = new ReferenteEvaluacion(
                a1,
                "RA03.a",
                "Se ha documentado correctamente la API REST"
        );
        ReferenteEvaluacion rf4 = new ReferenteEvaluacion(
                a2,
                "RA01.b",
                "Se han utilizado las diferentes clases y anotaciones "
        );
        ReferenteEvaluacion rf5 = new ReferenteEvaluacion(
                a2,
                "RA02.b",
                "Desarrolla servicios web sencillos utilizando la arquitectura REST"
        );

        a1.addReferente(rf1);
        a1.addReferente(rf2);
        a1.addReferente(rf3);
        a2.addReferente(rf4);
        a2.addReferente(rf5);

        asignaturaRepositorio.saveAll(List.of(a1,a2));

        Alumno al1 = new Alumno(
                1L,
                "Sebastián",
                "Millán Ordóñez",
                "sebastian@gmail.com",
                "123123321",
                LocalDate.of(2000, 7, 26),
                Set.of(a1,a2)
        );
        Alumno al2 = new Alumno(
                2L,
                "Fernando",
                "Claro De Diego",
                "fernando@gmail.com",
                "987987987",
                LocalDate.of(2002, 2, 15),
                Set.of(a2)
        );
        Alumno al3 = new Alumno(
                3L,
                "Pepe",
                "Sánchez",
                "pepe@gmail.com",
                "567567444",
                LocalDate.of(1994, 1, 3),
                Set.of(a1)
        );

        alumnoRepositorio.saveAll(List.of(al1,al2,al3));

        Instrumento i1 = new Instrumento(
                1L,
                "Examen Tema 3",
                LocalDateTime.of(2024,3,20,12, 30),
                "Creación de tablas y consultas",
                Set.of(rf5,rf4)
        );
        Instrumento i2 = new Instrumento(
                2L,
                "Examen Tema 5",
                LocalDateTime.of(2024,2,10,9, 0),
                "Creación de Clases y métodos",
                Set.of(rf1,rf2,rf3)
        );
        Instrumento i3 = new Instrumento(
                3L,
                "Test",
                LocalDateTime.of(2023,12,24,13, 30),
                "Clases CRUD",
                Set.of(rf2)
        );

        instrumentoRepositorio.saveAll(List.of(i1,i2,i3));

        Calificacion c1 = new Calificacion(1L, i1, rf5, al1, 7.5);
        Calificacion c2 = new Calificacion(2L, i1, rf4, al1, 5.5);
        Calificacion c3 = new Calificacion(3L, i1, rf4, al2, 8);
        Calificacion c4 = new Calificacion(4L, i1, rf5, al2, 6);

        Calificacion c5 = new Calificacion(5L, i2, rf1, al3, 5);
        Calificacion c6 = new Calificacion(6L, i2, rf2, al3, 7);
        Calificacion c7 = new Calificacion(7L, i2, rf3, al3, 9.5);

        Calificacion c8 = new Calificacion(8L, i3, rf2, al1, 6);
        Calificacion c9 = new Calificacion(9L, i3, rf2, al3, 8.2);

        Calificacion c10 = new Calificacion(10L, i2, rf1, al1, 6);
        Calificacion c11 = new Calificacion(11L, i2, rf2, al1, 3.5);
        Calificacion c12 = new Calificacion(12L, i2, rf3, al1, 6.9);

        calificacionRepositorio.saveAll(List.of(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12));

    }
}
