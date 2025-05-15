Feature: US24 - Ver reseñas recibidas

  Como usuario de la aplicación
  Quiero poder ver las reseñas y calificaciones que he recibido
  Para tener una referencia de mi reputación en la plataforma

  Scenario Outline: Ver la calificación general y reseñas recibidas
    Given estoy en mi perfil
    When selecciono la opción "Mis Reseñas"
    Then se me mostrará mi calificación general de "<CalificacionGeneral>" estrellas
    And el número total de reseñas recibidas es "<TotalResenas>"
    And una lista de reseñas con los siguientes detalles:
      | Usuario          | Calificación | Comentario               |
      | <Usuario>        | <Estrellas>  | <Comentario>             |

    Examples:
      | CalificacionGeneral | TotalResenas | Usuario         | Estrellas | Comentario              |
      | 4.00                | 1            | Joseph Huamani  | 5         | Buen intercambio jose   |
      | 3.75                | 4            | Ana Torres      | 4         | Producto en buen estado |
      | 3.75                | 4            | Carlos Perez    | 3         | Llegó un poco tarde     |
