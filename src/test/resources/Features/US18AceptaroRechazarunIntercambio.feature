Feature: US18 - Aceptar o Rechazar un Intercambio

  Como usuario que ha recibido una oferta de intercambio  
  Quiero poder revisar los detalles de la oferta y tomar una decisión para aceptar o rechazar el intercambio  
  Para poder gestionar mis transacciones de manera eficiente y asegurada

  Scenario Outline: Revisión de detalles de una oferta de intercambio recibida
    Given estoy en la pantalla de "Intercambios"
    When selecciono una oferta de intercambio pendiente con producto ofrecido "<producto_ofrecido>"
    Then se me mostrará una pantalla con los detalles del intercambio
    And podré ver el producto que el otro usuario ofrece (e.g., <producto_ofrecido>)
    And el producto que yo tengo y que el otro usuario solicita (e.g., <producto_solicitado>)
    And el valor aproximado de los artículos involucrados (S/. <valor_ofrecido> y S/. <valor_solicitado>)
    And el estado actual de la oferta (Pendiente)
    And podré elegir entre dos opciones: aceptar la oferta o rechazarla

  Scenario Outline: Aceptar una oferta de intercambio
    Given estoy visualizando los detalles de una oferta de intercambio con producto ofrecido "<producto_ofrecido>"
    When selecciono la opción "Aceptar"
    Then el sistema debe mostrar un popup de confirmación con el mensaje "¡Intercambio Aceptado! Felicidades por completar tu intercambio."
    And el usuario podrá confirmar la finalización del proceso seleccionando el botón "Aceptar" del popup
    And después de aceptar, la oferta debe moverse a la lista de intercambios aceptados
    And podré coordinar la entrega con el otro usuario

  Scenario Outline: Rechazar una oferta de intercambio
    Given estoy visualizando los detalles de una oferta de intercambio con producto ofrecido "<producto_ofrecido>"
    When selecciono la opción "Rechazar"
    Then se me mostrará un popup de confirmación que dice "¿Estás seguro de que deseas rechazar la oferta? Si rechazas la oferta, no podrás volver a verla."
    And podré seleccionar entre rechazar la oferta, que confirmará la acción y eliminará la oferta de la lista de intercambios pendientes
    Or cancelar, que cerrará el popup y me devolverá a la pantalla de detalles del intercambio sin tomar ninguna acción

  Scenario: Confirmación de rechazo de una oferta
    Given he decidido rechazar una oferta de intercambio y he seleccionado "Rechazar Oferta" en el popup de confirmación
    When confirmo la acción
    Then la oferta desaparecerá de la lista de intercambios pendientes
    And no podré volver a ver ni gestionar esa oferta

  Examples:
    | producto_ofrecido       | producto_solicitado | valor_ofrecido | valor_solicitado |
    | Zapatillas Deportivas   | Reloj Inteligente   | 150            | 200              |
    | Cámara Fotográfica      | Tablet              | 400            | 350              |
    | Libro de Cocina         | Juego de Mesa       | 80             | 100              |
