Feature: US21 - Cancelar una suscripción

  Como usuario
  Quiero poder cancelar mi suscripción en cualquier momento
  Para no pagar mensualmente

  Scenario Outline: Acceso a la ventana de suscripciones
    Given el usuario desea verificar la información del estado de su suscripción
    When se dirige a la sección de configuración
    Then observará un botón que dice "<BotonSuscripcion>"
    When el usuario pulsa en "<BotonSuscripcion>"
    Then podrá ver los detalles de su suscripción

    Examples:
      | BotonSuscripcion  |
      | Mi suscripción    |

  Scenario Outline: Verificación de los detalles de la suscripción
    Given el usuario se encuentra en la ventana que muestra el estado de su suscripción
    Then podrá visualizar la fecha en la que se renueva su suscripción
    And los beneficios que obtiene: "<Beneficios>"
    And si está en un plan "<TipoPlan>"

    Examples:
      | Beneficios                             | TipoPlan    |
      | Acceso a funciones premium            | Mensual     |
      | Acceso a funciones premium            | Semianual   |

  Scenario Outline: Proceder a la cancelación
    Given el usuario se encuentra en la ventana que muestra el estado de su suscripción
    And ha decidido no continuar con el premium de CambiaZo
    When pulsa en el botón rojo que dice "<BotonCancelar>"
    Then la suscripción será cancelada y no se renovará hasta la próxima fecha

    Examples:
      | BotonCancelar        |
      | Anular suscripción   |
