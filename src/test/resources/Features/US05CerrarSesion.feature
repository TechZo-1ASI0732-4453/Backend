Feature: US05 - Cerrar Sesión

  Como usuario registrado
  Quiero cerrar sesión en la aplicación
  Para asegurarme de que mi cuenta no quede almacenada en mi dispositivo móvil

  Scenario Outline: Acceso del usuario a la opción de cerrar sesión
    Given el <usuario> ha iniciado sesión en la aplicación
    When acceda a la sección de configuración de la cuenta
    Then la aplicación mostrará la opción para cerrar sesión

    Examples:
      | usuario |
      | Pedro   |

  Scenario Outline: Cierre de sesión exitoso por parte del usuario
    Given el <usuario> se encuentra en la página de cierre de sesión
    When seleccione el botón "Cerrar Sesión"
    Then la aplicación concluirá la sesión del usuario
    And lo redirigirá a la página de inicio de sesión

    Examples:
      | usuario |
      | Pedro   |
