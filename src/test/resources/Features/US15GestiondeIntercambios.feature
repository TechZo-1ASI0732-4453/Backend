Feature: US15 - Gestión de intercambios

  Como usuario de la aplicación  
  Quiero revisar el estado de los intercambios que he enviado, recibido o aceptado  
  Para poder ver los detalles y gestionar mis transacciones de intercambio de manera eficiente

  Scenario Outline: Revisión de intercambios enviados
    Given estoy en la pantalla de Intercambios
    When selecciono la pestaña de Enviados
    Then se me mostrará una lista de los intercambios que he enviado, junto con los artículos involucrados y el estado de cada intercambio
    And podré hacer clic en cualquiera de ellos para ver más detalles del intercambio
    And en la vista detallada, veré un intercambio como el siguiente:
      | Artículo ofrecido                 | <artículo_ofrecido> |
      | Valor aproximado                  | <valor_ofrecido> |
      | Estado de la oferta               | <estado_oferta> |
      | Ubicación                         | <ubicacion> |
      | ¿Qué solicitas?                   | <articulo_solicitado> |
      | ¿Qué le ofreciste?                | <articulo_ofrecido> |
      | Nombre del usuario destinatario   | <nombre_usuario> |
      | Descripción del artículo ofertado | <descripcion_articulo_ofrecido> |

    Examples:
      | artículo_ofrecido               | valor_ofrecido | estado_oferta | ubicacion | articulo_solicitado   | articulo_ofrecido    | nombre_usuario    | descripcion_articulo_ofrecido                          |
      | Raqueta de Tenis Profesional    | S/.300         | Pendiente     | Miraflores| Camiseta del Barcelona | Raqueta de tenis     | Joseph Huamani    | Raqueta de tenis profesional con tecnología de absorción de impacto |
      | Pelota de Futbol Firmada por CR7| S/.90          | Aceptada      | Barranco  | Camiseta del Real Madrid| Pelota Champions    | Maria Gómez       | Pelota de fútbol Champions firmada por CR7             |

  Scenario Outline: Revisión de intercambios recibidos
    Given estoy en la pantalla de Intercambios
    When selecciono la pestaña de Recibidos
    Then se me mostrará una lista de los intercambios que he recibido, donde se verán los objetos ofrecidos y solicitados en cada transacción, así como el estado actual de cada intercambio
    And podré hacer clic en cualquiera de ellos para ver más detalles del intercambio
    And en la vista detallada, veré un intercambio como el siguiente:
      | Artículo recibido                  | <articulo_recibido> |
      | Valor estimado                     | <valor_recibido> |
      | Estado de la oferta                | <estado_oferta> |
      | Ubicación                          | <ubicacion> |
      | ¿Qué solicita <nombre_usuario>?    | <articulo_solicitado> |
      | ¿Qué le ofreciste?                 | <articulo_ofrecido> |
      | Nombre del usuario ofertante       | <nombre_usuario> |
      | Descripción del artículo recibido  | <descripcion_articulo_recibido> |

    Examples:
      | articulo_recibido                | valor_recibido | estado_oferta | ubicacion | articulo_solicitado  | articulo_ofrecido   | nombre_usuario    | descripcion_articulo_recibido                           |
      | Pelota de Futbol Champions Firmada| S/.90          | Pendiente     | Miraflores| Camiseta del Barcelona | Raqueta de tenis    | Joseph Huamani    | Pelota de fútbol Champions firmada por CR7              |
      | Raqueta de Tenis Profesional     | S/.300         | Aceptada      | San Isidro| Camiseta del Real Madrid| Pelota Champions    | María Gómez       | Raqueta de tenis profesional con tecnología de absorción de impacto|

  Scenario Outline: Revisión de intercambios aceptados
    Given estoy en la pantalla de Intercambios
    When selecciono la pestaña de Aceptados
    Then veré un resumen breve de los intercambios que ya he aceptado, junto con los artículos intercambiados
    And si hago clic en uno de ellos, podré ver más detalles de ese intercambio
    And en la vista detallada, veré un intercambio como el siguiente:
      | Artículo intercambiado             | <articulo_intercambiado> |
      | Artículo recibido                  | <articulo_recibido> |
      | Valor estimado                     | <valor_estimado> |
      | Estado del intercambio             | <estado_intercambio> |
      | Ubicación                          | <ubicacion> |
      | ¿Qué se solicitó?                  | <articulo_solicitado> |
      | ¿Qué se ofreció?                   | <articulo_ofrecido> |
      | Nombre de usuario que aceptó       | <nombre_usuario> |
      | Descripción del artículo intercambiado | <descripcion_articulo_intercambiado> |

    Examples:
      | articulo_intercambiado           | articulo_recibido               | valor_estimado | estado_intercambio | ubicacion | articulo_solicitado   | articulo_ofrecido    | nombre_usuario   | descripcion_articulo_intercambiado                  |
      | Raqueta de Tenis Profesional    | Pelota de Futbol Champions Firmada | S/.300 & S/.90 | Aceptado            | Miraflores | Camiseta del Barcelona | Raqueta de tenis     | Joseph Huamani    | Raqueta de tenis profesional con tecnología de absorción de impacto |
      | Pelota de Futbol Firmada CR7   | Raqueta de Tenis Profesional    | S/.90 & S/.300 | Aceptado            | Barranco  | Camiseta del Real Madrid| Pelota Champions    | María Gómez      | Pelota de fútbol Champions firmada por CR7             |
