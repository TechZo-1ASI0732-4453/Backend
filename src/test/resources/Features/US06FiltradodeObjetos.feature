Feature: US06 - Filtrado de Objetos

  Como usuario intercambiador  
  Quiero la capacidad de filtrar los objetos disponibles de intercambio  
  Para encontrar la opción que mejor se adapte a mis preferencias de intercambio

  Scenario Outline: Aplicación de Filtros
    Given el <usuario> está en la sección de objetos disponibles para intercambio
    When el usuario selecciona los filtros: categoría "<categoria>", ubicación "<ubicacion>" y precio "<precio>"
    And pulsa el botón de "Aplicar Filtros"
    Then la lista de objetos se ajustará automáticamente según los criterios seleccionados

    Examples:
      | usuario | categoria  | ubicacion    | precio    |
      | Pedro   | Ropa      | Lima         | 50-100    |
      | Juan    | Electrónica | Arequipa     | 100-200   |

  Scenario Outline: Visualización de Resultados Filtrados
    Given el <usuario> ha aplicado filtros por estado "<estado>" a la lista de objetos disponibles
    When la aplicación muestra únicamente los objetos que cumplen con el estado seleccionado
    Then el usuario puede ver y explorar los resultados filtrados según sus preferencias

    Examples:
      | usuario | estado |
      | Pedro   | Nuevo  |
      | Juan    | Usado  |

  Scenario Outline: Filtrar por cantidad de Vistas
    Given el <usuario> está buscando objetos con la mayor cantidad de vistas para intercambiar
    When el usuario selecciona la opción de "Filtrar por Vistas"
    And pulsa el botón de "Aplicar Filtros"
    Then la aplicación presenta los objetos más populares en la parte superior de la lista de resultados

    Examples:
      | usuario |
      | Pedro   |
