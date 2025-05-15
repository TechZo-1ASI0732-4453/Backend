Feature: US08 - Brindar Reseña sobre el Intercambiador

  Como usuario intercambiador  
  Deseo dejar una reseña sobre mi experiencia con el intercambiador  
  Para que otros usuarios puedan leer y considerar mi opinión antes de intercambiar

  Scenario Outline: Dejar una Reseña
    Given el <usuario> ha completado un intercambio con otro usuario
    When visita la página de la experiencia de intercambio realizada
    Then el usuario encuentra una opción para dejar una reseña sobre su experiencia con el intercambiador
    And el usuario puede escribir su reseña detallando su experiencia
    And el usuario puede calificar al intercambiador con un puntaje de 1 a 5 estrellas
    And el usuario pulsa el botón "Enviar Reseña"
    Then la reseña y la calificación se guardan y se muestran en la página de experiencia de intercambio

    Examples:
      | usuario | reseña                         | estrellas |
      | Pedro   | "Excelente intercambio, todo bien." | 5        |
      | Ana     | "Fue un buen intercambio, pero un poco lento." | 3        |

  Scenario Outline: Visualización de Reseñas
    Given otros usuarios visitan la página de la experiencia de intercambio
    When exploran las reseñas dejadas por otros intercambiadores
    Then pueden leer y considerar las opiniones de otros usuarios antes de realizar un intercambio con ese intercambiador
    And pueden ver el puntaje de estrellas de cada reseña

    Examples:
      | usuario | reseña                             | estrellas |
      | Pedro   | "Excelente intercambio, todo bien." | 5         |
      | Ana     | "Fue un buen intercambio, pero un poco lento." | 3         |
