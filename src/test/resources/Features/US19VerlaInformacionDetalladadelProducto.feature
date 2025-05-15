Feature: US19 - Ver la información detallada de un producto publicado

  Como usuario de la aplicación
  Quiero poder ver la información completa de un producto en el que estoy interesado
  Para poder decidir si quiero guardarlo en mis favoritos o proponer un intercambio

  Scenario Outline: Ver la información detallada del producto
    Given he seleccionado la publicación del objeto "<Título>"
    When se me abre la pantalla de información del artículo
    Then debo poder ver los siguientes detalles del producto:
      | Imagen del objeto                | <Imagen>                       |
      | Valor aproximado del objeto      | <Valor>                       |
      | Nombre del usuario              | <Usuario>                     |
      | Calificación del usuario        | <Calificación>                |
      | Título del producto             | <Título>                     |
      | Descripción del producto        | <Descripción>                |
      | Ubicación aproximada            | <Ubicación>                  |
      | Artículos que le interesa recibir a cambio | <Intereses>          |

    Examples:
      | Imagen             | Valor   | Usuario     | Calificación  | Título               | Descripción                                   | Ubicación | Intereses                       |
      | ChocolateMrBeast.jpg | 10 USD  | Usuario123  | 4.5 estrellas | Chocolate MrBeast Bar | Delicioso chocolate de cacahuate              | Lima      | Reloj Inteligente              |
      | LamparaColgante.jpg | S/. 80  | Usuario456  | 4 estrellas  | Lámpara Colgante     | Lámpara colgante de diseño moderno, ideal para el comedor | Surquillo | Mesa de centro minimalista    |

  Scenario: Ver más información del usuario que publicó el producto
    Given estoy visualizando la información del producto
    When selecciono el nombre o la imagen del usuario que lo publicó
    Then se me redirigirá a una pantalla con más información sobre ese usuario
    And debo poder ver su perfil, su calificación general y sus reseñas recibidas

  Scenario: Guardar el producto en mis favoritos
    Given estoy visualizando la información del producto
    When selecciono el ícono de favoritos (corazón)
    Then el objeto se guardará en mi lista de favoritos
    And el ícono de corazón cambiará para indicar que el producto ya está guardado

  Scenario: Proponer un intercambio
    Given estoy visualizando la información del producto
    When selecciono el botón "Intercambiar"
    Then se me redirigirá a una pantalla donde podré hacer una oferta de intercambio
    And podré seleccionar el objeto que quiero ofrecer a cambio del artículo publicado
