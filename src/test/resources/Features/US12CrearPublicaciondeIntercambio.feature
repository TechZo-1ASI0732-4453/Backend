Feature: US12 - Crear publicación de intercambio

  Como usuario de la aplicación  
  Quiero poder crear una nueva publicación de intercambio  
  Para ofrecer un artículo que deseo intercambiar

  Scenario Outline: Creación de una nueva publicación de intercambio
    Given el <usuario> accede a la opción de crear una nueva publicación de intercambio desde la interfaz de la aplicación
    When completa el formulario con los detalles del artículo que desea intercambiar, incluyendo:
      | campo                     | valor                  |
      | Nombre                    | <nombre>               |
      | Correo electrónico        | <correo>               |
      | Teléfono                  | <telefono>             |
      | País                      | <pais>                 |
      | Departamentos             | <departamento>         |
      | Ciudades                  | <ciudad>               |
      | Categoría                 | <categoria>            |
      | Producto                  | <producto>             |
      | Descripción del producto  | <descripcion>          |
      | ¿Qué quieres a cambio?    | <cambio>               |
      | Valor Aproximado          | <valor>                |
    Then se le permite adjuntar imágenes del artículo como un enlace

    Examples:
      | usuario | nombre   | correo                | telefono   | pais     | departamento | ciudad    | categoria   | producto         | descripcion        | cambio          | valor   |
      | Pedro   | Reloj   | pedro@example.com     | 987654321 | Perú     | Lima         | Lima      | Accesorios  | Reloj de pulsera | Reloj deportivo    | Cámaras fotográficas | 50      |

  Scenario Outline: Publicación de intercambio creada
    Given el <usuario> está completando el formulario de creación de la publicación de intercambio
    When intenta enviar la publicación
    Then el sistema valida los campos del formulario
    And crea la publicación de intercambio

    Examples:
      | usuario |
      | Pedro   |
      | María   |

  Scenario Outline: Visualización de publicación de intercambio
    Given la publicación de intercambio ha sido creada
    When el <usuario> accede a su perfil
    And va a la sección de "Mis artículos"
    Then el sistema le mostrará la publicación del artículo que desea intercambiar con todos los campos llenados: 
      | usuario | nombre   | correo                | telefono   | pais     | departamento | ciudad    | categoria   | producto         | descripcion        | cambio          | valor   |
      | Pedro   | Reloj   | pedro@example.com     | 987654321 | Perú     | Lima         | Lima      | Accesorios  | Reloj de pulsera | Reloj deportivo    | Cámaras fotográficas | 50      |

    Examples:
      | usuario |
      | Pedro   |
