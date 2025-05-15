Feature: US01 - Registro de usuario
  Como un nuevo usuario
  Quiero completar el proceso de registro en la aplicación
  Para poder crear mi propia cuenta

  Scenario Outline: Acceso del usuario a la página de registro
    Given el <usuario> ha descargado la aplicación y la ha iniciado
    And está en la sección de "Inicio de sesión"
    When elige hacer clic en el botón de "Registrarse"
    Then la aplicación desplegará la página de registro

    Examples:
      | usuario |
      | Pedro   |

  Scenario Outline: Registro exitoso del usuario
    Given el <usuario> está en la página de registro
    When ingresa los datos: <nombre>, <correo>, <contraseña>, <repetir contraseña> y <teléfono>
    And procede a hacer clic en el botón de "Registrarse"
    Then la aplicación registrará al usuario y guardará su cuenta

    Examples:
      | usuario | nombre  | correo               | contraseña | repetir contraseña | teléfono   |
      | Pedro   | Pedro M | pedro@email.com      | abc12345   | abc12345           | 987654321  |

  Scenario Outline: Registro del usuario con datos inválidos
    Given el <usuario> está en la página de registro
    When proporciona datos incompletos o inválidos: <nombre>, <correo>, <contraseña>, <repetir contraseña> y <teléfono>
    And luego hace clic en el botón de "Registrarse"
    Then la aplicación mostrará un mensaje de error
    And eliminará los datos introducidos

    Examples:
      | usuario | nombre  | correo          | contraseña | repetir contraseña | teléfono   |
      | Pedro   |         | pedro@email.com | abc12345   | abc12345           |            |
