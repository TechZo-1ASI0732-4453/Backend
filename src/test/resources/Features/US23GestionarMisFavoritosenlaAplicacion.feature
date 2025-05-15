Feature: US23 - Gestionar mis favoritos en la aplicación

  Como usuario de la aplicación
  Quiero poder acceder a los objetos que he guardado como favoritos
  Para visualizarlos y eliminar los que ya no me interesen

  Scenario Outline: Ver objetos guardados como favoritos
    Given estoy en mi perfil
    When selecciono la opción "Favoritos"
    Then se me mostrará una lista de objetos guardados como favoritos que incluye:
      | Imagen       | Nombre     | Descripción breve       | Valor aproximado |
      | <Imagen>     | <Nombre>   | <Descripcion>           | <Valor>          |

    Examples:
      | Imagen       | Nombre   | Descripcion               | Valor    |
      | pelota.jpg   | Pelota   | Peluche o algún juguete   | S/. 15   |
      | libro.png   | Libro    | Novela de aventuras       | S/. 30   |

  Scenario Outline: Eliminar un objeto de mis favoritos
    Given estoy visualizando la lista de favoritos que incluye el objeto "<Nombre>"
    When selecciono el botón de favoritos (ícono de corazón) del objeto "<Nombre>" para eliminarlo
    Then se mostrará un popup de confirmación con el mensaje: "¿Estás seguro de que deseas eliminar este objeto de tus favoritos?"
    When selecciono "<Accion>"
    Then el objeto "<Nombre>" <Resultado> de la lista de favoritos

    Examples:
      | Nombre  | Accion   | Resultado                        |
      | Pelota  | Eliminar | desaparecerá                    |
      | Pelota  | Cancelar | permanecerá sin cambios         |
