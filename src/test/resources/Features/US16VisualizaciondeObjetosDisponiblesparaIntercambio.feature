Feature: US16 - Visualización de objetos disponibles para intercambio

  Como usuario  
  Necesito poder ver objetos disponibles para intercambio  
  De manera que pueda navegar y seleccionar aquellos que me interesen

  Scenario Outline: Visualización de objetos
    Given el usuario ha iniciado sesión en la aplicación
    And está en la sección de Explorar en la barra de navegación del aplicativo móvil
    When accede a dicha sección
    Then el sistema le mostrará una barra de búsqueda, un botón para filtros, un slide con las categorías disponibles y una lista de las publicaciones de los objetos, incluyendo:
      | Foto del objeto                | Valor aproximado | Nombre del objeto          | Ubicación     | Descripción breve                              |
      | <foto_objeto>                  | <valor_objeto>   | <nombre_objeto>            | <ubicacion>   | <descripcion_objeto>                           |

    Examples:
      | foto_objeto                  | valor_objeto | nombre_objeto      | ubicacion   | descripcion_objeto                     |
      | imagen_raqueta_tenis.jpg     | S/.300       | Raqueta de Tenis   | Miraflores  | Raqueta profesional con tecnología de absorción de impacto |
      | imagen_pelota_futbol.jpg     | S/.90        | Pelota de Fútbol   | Barranco    | Pelota firmada por CR7                   |
      | imagen_camiseta_barcelona.jpg| S/.150       | Camiseta Barcelona | San Isidro  | Camiseta oficial del FC Barcelona       |

  Scenario Outline: Búsqueda de objetos
    Given el usuario está en la sección de Explorar
    When utiliza la barra de búsqueda para ingresar un término "<termino_busqueda>"
    Then el sistema le mostrará los objetos que coinciden con el término buscado, mostrando los resultados con la misma información: foto, valor aproximado, nombre, ubicación y descripción

    Examples:
      | termino_busqueda |
      | Raqueta de tenis |
      | Pelota de fútbol |
      | Camiseta Barcelona |
