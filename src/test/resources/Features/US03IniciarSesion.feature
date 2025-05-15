Feature: US03 - Iniciar sesión en la aplicación
  Como usuario registrado
  Quiero iniciar sesión en la aplicación
  Para poder acceder a todas sus funcionalidades

  Scenario Outline: Acceso del usuario a la página de inicio de sesión
    Given el <usuario> abre la aplicación
    And no ha iniciado sesión previamente o se ha desconectado
    When el usuario seleccione el botón "Iniciar Sesión"
    Then la aplicación mostrará la página de inicio de sesión

    Examples:
      | usuario |
      | Pedro   |

  Scenario Outline: Inicio de sesión exitoso del usuario
    Given el <usuario> se encuentra en la página de inicio de sesión
    When ingresa sus credenciales válidas: <correo>, <contraseña>
    And pulse el botón "Iniciar Sesión"
    Then el usuario será autenticado
    And dirigido a la página principal de la aplicación

    Examples:
      | usuario | correo              | contraseña   |
      | Pedro   | pedro@email.com     | clave123     |

  Scenario Outline: Intento de inicio de sesión fallido
    Given el <usuario> está en la página de inicio de sesión
    When ingresa credenciales inválidas: <correo>, <contraseña>
    And pulse el botón "Iniciar Sesión"
    Then la aplicación mostrará un mensaje de error indicando que las credenciales son incorrectas

    Examples:
      | usuario | correo              | contraseña   |
      | Pedro   | pedro@email.com     | incorrecta   |
