Feature: US20 - Adquirir la suscripción premium

  Como usuario
  Quiero poder adquirir una suscripción premium
  Para poder obtener beneficios adicionales que mejoren mi experiencia

  Scenario Outline: Localizar la sección para adquirir una suscripción
    Given el usuario desea adquirir una suscripción para CambiaZo
    When pulsa a la sección de configuración
    Then le aparecerán varias opciones, entre las que figura "<Opcion>"
    When el usuario pulsa al botón "<Boton>"
    Then será redirigido a una nueva ventana que le mostrará las suscripciones disponibles

    Examples:
      | Opcion       | Boton       |
      | Ser premium  | Ser premium |

  Scenario Outline: Visualizar los beneficios de las distintas suscripciones
    Given el usuario se encuentra en la ventana correspondiente sobre las suscripciones
    When pulsa a la suscripción "<TipoSuscripcion>"
    Then podrá ver el precio por mes de esa suscripción: "<Precio>"
    And podrá ver los beneficios que incluye:
      """
      <Beneficios>
      """

    Examples:
      | TipoSuscripcion | Precio    | Beneficios                                                                                             |
      | Lite            | GRATIS    | - Gratis para todos los usuarios                                                                      |
      |                 |           | - Publicaciones de artículos al mes (limitado)                                                       |
      |                 |           | - Intercambios al mes (limitado)                                                                     |
      | Plus            | $2.99c/mes| - Para usuarios aficionados al cambiazo                                                              |
      |                 |           | - 10 publicaciones de artículos al mes                                                              |
      |                 |           | - 10 intercambios al mes                                                                              |
      |                 |           | - 2 Boost semanales                                                                                   |
      |                 |           | - Sin anuncios                                                                                       |
      | Premium         | $3.99c/mes| - Para usuarios expertos al cambiazo                                                                  |
      |                 |           | - 35 publicaciones de artículos al mes                                                              |
      |                 |           | - 35 intercambios al mes                                                                              |
      |                 |           | - Boost diario                                                                                       |
      |                 |           | - Sin anuncios                                                                                       |

  Scenario Outline: Compra de la suscripción
    Given el usuario ha decidido la suscripción "<TipoSuscripcion>"
    When pulsa a "Suscribirse"
    Then se le redireccionará a una pasarela de pago
    And podrá introducir su método de pago
    And procesar el pago respectivo para adquirir los nuevos beneficios

    Examples:
      | TipoSuscripcion |
      | Lite            |
      | Plus            |
      | Premium         |
