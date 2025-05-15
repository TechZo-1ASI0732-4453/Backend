Feature: US22 - Visualizar el perfil de las ONG'S registradas

  Como usuario de la aplicación
  Quiero tener la opción de ver todas las ONG's disponibles para realizar donaciones
  Para apoyar a las causas que me interesan

  Scenario Outline: Acceso a la pestaña de ONG's
    Given el usuario se encuentra en la pestaña principal
    When pulsa en la etiqueta "ONG's"
    Then se mostrarán las ONG's registradas:
      | ONG             |
      | <NombreONG>     |

    Examples:
      | NombreONG   |
      | ASPPA Perú |

  Scenario Outline: Ver perfil de una ONG
    Given el usuario se encuentra dentro de la pestaña "ONG's"
    When pulsa en el recuadro que muestra el perfil de la ONG "<NombreONG>"
    Then aparecerán los datos y características de la ONG seleccionada:
      | Nombre                | <NombreONG>                       |
      | Categoría             | <Categoria>                      |
      | Dirección             | <Direccion>                     |
      | Teléfono de contacto  | <Telefono>                      |
      | Correo electrónico    | <Correo>                        |
      | Horario de atención   | <Horario>                       |
      | Misión y Visión       | <MisionVision>                  |
      | Proyectos             | <Proyectos>                    |

    Examples:
      | NombreONG   | Categoria          | Direccion                      | Telefono     | Correo                 | Horario             | MisionVision                                                                                                              | Proyectos                                  |
      | ASPPA Perú | Cuidado de animales | Av. Canada 3297, San Borja, Lima, Peru | (01) 793-1211 | web@asppa-peru.org     | Lun-Vie 7:00 am - 6:00 pm | Asegurar vida digna y bienestar de animales y comunidad. Ser fundación nacional e internacional. | Esterilización masiva, rescate, educación |

  Scenario Outline: Donar a una ONG
    Given el usuario se encuentra en el perfil de la ONG "<NombreONG>"
    When pulsa en la opción "Donar"
    Then aparecerán las opciones de donación (objetos o dinero)
    When el usuario selecciona la opción "<TipoDonacion>"
    Then podrá realizar la donación

    Examples:
      | NombreONG   | TipoDonacion |
      | ASPPA Perú | objetos      |
      | ASPPA Perú | dinero       |
