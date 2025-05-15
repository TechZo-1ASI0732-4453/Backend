Feature: US11 - Realización de una oferta de intercambio

  Como usuario de la aplicación de intercambio  
  Quiero seleccionar uno de mis artículos y enviarlo como oferta de intercambio  
  Para poder ofrecerlo a cambio de otro artículo publicado por otro usuario

  Scenario Outline: Usuario con artículos publicados para ofertar
    Given el <usuario> ha visto un artículo que quiere intercambiar
    When presiona el botón "Ofertar"
    Then se le mostrará una lista de sus artículos disponibles para intercambio
    And podrá seleccionar uno de sus artículos para ofrecer

    Examples:
      | usuario | artículo_ofrecido | descripción_artículo  |
      | Pedro   | Reloj de pulsera  | Reloj deportivo de marca |
      | María   | Bicicleta         | Bicicleta de montaña usada |

  Scenario Outline: Confirmación de oferta
    Given el <usuario> ha seleccionado un artículo para ofrecer
    When procede a confirmar la oferta
    Then se le mostrará una pantalla de confirmación con los detalles del artículo que está solicitando y el que está ofreciendo
    And podrá presionar "Listo" para enviar la oferta

    Examples:
      | usuario | artículo_ofrecido   | artículo_solicitado  |
      | Pedro   | Reloj de pulsera    | Cámara fotográfica   |
      | María   | Bicicleta           | Patines en línea     |

  Scenario Outline: Oferta enviada con éxito
    Given el <usuario> ha confirmado su oferta
    When la oferta se haya enviado correctamente
    Then verá un mensaje que dice “¡Oferta Enviada!” confirmando que su oferta fue enviada exitosamente
    And se le notificará que el otro usuario recibirá la oferta para su consideración

    Examples:
      | usuario | artículo_ofrecido   | artículo_solicitado  |
      | Pedro   | Reloj de pulsera    | Cámara fotográfica   |
      | María   | Bicicleta           | Patines en línea     |

  Scenario Outline: Usuario sin artículos publicados
    Given el <usuario> no tiene artículos publicados para ofrecer
    When presiona el botón "Ofertar"
    Then verá un mensaje que le indica que no tiene artículos publicados
    And se le dará la opción de publicar un nuevo artículo directamente desde la pantalla de la oferta, con un botón “+ Publicar”

    Examples:
      | usuario |
      | Pedro   |
      | María   |

  Scenario Outline: Publicación de un nuevo artículo
    Given el <usuario> no tiene artículos y desea publicar uno
    When presiona el botón “+ Publicar” desde el mensaje de que no tiene productos
    Then será llevado a la pantalla de publicación de un artículo, donde puede agregar los detalles del artículo (imagen, título, categoría, etc.)
    And después de publicar exitosamente el artículo, verá un mensaje de confirmación de publicación exitosa
    And podrá continuar con el proceso de realizar una oferta usando el nuevo artículo publicado

    Examples:
      | usuario | artículo_publicado    | categoría     |
      | Pedro   | Reloj de pulsera       | Accesorios    |
      | María   | Bicicleta de montaña   | Deportes      |
