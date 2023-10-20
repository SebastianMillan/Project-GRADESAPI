# GRADESAPP API-REST
El proyecto consiste en la implementación de una API REST que permita gestionar información sobre las calificaciones de alumnos en instrumentos de evaluación (exámenes, ejercicios, proyectos) de diversas asignaturas

### Alumno

| Método     | Ruta                               | Acción                                                  |
|------------|-----------------------------------|---------------------------------------------------------|
| `POST`     | /alumno/                        | createAlumno |
| `GET`     | /alumno/                        | getAllAlumno |
| `GET`     | /alumno/id                        | getByIdAlumno |
| `PUT`     | /alumno/id                       | editAlumno |
| `POST`     | /alumno/id/matricula/id_asig                        | addAsignaturaToAlumno |
| `DELETE`     | /alumno/id/                       | removeAlumno |
| `DELETE`     | /alumno/id/matricula/id_asig                        | removeAsignaturaToAlumno |


### Asignatura

| Método     | Ruta                               | Acción                                                  |
|------------|-----------------------------------|---------------------------------------------------------|
| `POST`     | /asignatura/                        | createAsignatura |
| `POST`     | /asignatura/id/referente                        | addReferenteToAsignatura |
| `GET`     | /asignatura/                      | getAllAsignatura |
| `GET`     | /asignatura/id                       | getByIdAsignatura |
| `PUT`     | /asignatura/id                        | editAsignatura |
| `PUT`     | /asignatura/id/referente/cod_ref                     | editReferenteToAsignatura |
| `DELETE`     | /asignatura/id                        | removeAsignatura |

### Instrumento

| Método     | Ruta                               | Acción                                                  |
|------------|-----------------------------------|---------------------------------------------------------|
| `POST`     | /instrumento/                        | createInstrumento |
| `GET`     | /instrumento/id_asig                        | getAllInstrumentosByAsig |
| `GET`     | /instrumento/detalle/id                     | getByIdIstrumento |
| `PUT`     | /instrumento/id                       | editInstrumento |
| `DELETE`     | /instrumento/id                       | deleteInstrumento |
| `POST`     | /instrumento/id/referente/cod_ref                   | addReferenteToInstrumento |
| `DELETE`     | /instrumento/id/referente/cod_ref                        | removeReferenteToInstrumento |

### Calificación

| Método     | Ruta                               | Acción                                                  |
|------------|-----------------------------------|---------------------------------------------------------|
| `POST`     | /alumno/                        | createAlumno |
| `GET`     | /alumno/                        | getAllAlumno |
| `GET`     | /alumno/id                        | getByIdAlumno |
| `PUT`     | /alumno/id                       | editAlumno |
| `POST`     | /alumno/id/matricula/id_asig                        | addAsignaturaToAlumno |
| `DELETE`     | /alumno/id/                       | removeAlumno |
| `DELETE`     | /alumno/id/matricula/id_asig                        | removeAsignaturaToAlumno |
