Feature: US14 - Eliminar publicación de intercambio

  Como usuario  
  Quiero tener la opción de eliminar una publicación de intercambio que ya no deseo ofrecer para intercambiar  
  Para que no permanezca disponible en la plataforma

  Scenario Outline: Acceso a la eliminación de una publicación de intercambio
    Given el <usuario> ha iniciado sesión en la aplicación
    And tiene una publicación de intercambio existente
    When accede a la opción de eliminar la publicación desde la interfaz de la aplicación
    Then el sistema le muestra una confirmación para confirmar si realmente desea eliminar la publicación

    Examples:
      | usuario |
      | Pedro   |

  Scenario Outline: Confirmación de eliminación
    Given el <usuario> ha seleccionado eliminar una publicación de intercambio
    When confirma la acción de eliminación
    Then el sistema elimina la publicación de manera permanente de la plataforma
    And se muestra un mensaje de confirmación al usuario

    Examples:
      | usuario |
      | Pedro   |

  Scenario Outline: Cancelación de la eliminación
    Given el <usuario> ha seleccionado eliminar una publicación de intercambio
    When decide cancelar la eliminación
    Then la publicación no se elimina
    And el sistema redirige al usuario al inicio de la aplicación

    Examples:
      | usuario |
      | Pedro   |
