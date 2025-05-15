Feature: US04 - Cambiar Contraseña
  Como usuario registrado
  Quiero realizar cambios en la contraseña de mi cuenta
  Para reforzar la seguridad de mi cuenta

  Scenario Outline: Acceso del usuario a la configuración de la cuenta
    Given el <usuario> ha iniciado sesión en la aplicación
    When acceda a la sección de configuración de la cuenta
    Then la aplicación mostrará la opción para modificar la contraseña

    Examples:
      | usuario |
      | Pedro   |

  Scenario Outline: Cambio exitoso de la contraseña
    Given el <usuario> está en la página de cambio de contraseña
    When ingrese su nueva contraseña: <nueva_contraseña> y la repita: <repetir_contraseña>
    And pulse el botón "Cambiar Contraseña"
    Then la aplicación ejecutará el cambio de contraseña
    And mostrará un mensaje de confirmación

    Examples:
      | usuario | nueva_contraseña | repetir_contraseña |
      | Pedro   | nuevaClave123    | nuevaClave123      |

  Scenario Outline: Intento de cambio de contraseña fallido
    Given el <usuario> se encuentra en la página de cambio de contraseña
    When ingrese una nueva contraseña: <nueva_contraseña> y la repita: <repetir_contraseña>
    And pulse el botón "Cambiar Contraseña"
    Then la aplicación mostrará un mensaje de error indicando que las contraseñas no coinciden o no cumplen con los requisitos

    Examples:
      | usuario | nueva_contraseña | repetir_contraseña |
      | Pedro   | nuevaClave123    | clave123           |
