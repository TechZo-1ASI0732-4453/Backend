Feature: US17 - Visualizar el perfil del usuario que publique un producto

  Como usuario  
  Me gustaría tener la capacidad de visualizar el perfil de la persona que haya publicado un intercambio  
  Para poder obtener información detallada de su confiabilidad

  Scenario: Acceso al perfil del creador de la publicación
    Given el usuario se encuentra en la publicación de su interés
    When pulsa al recuadro que muestra el perfil del autor de la publicación
    Then el usuario podrá visualizar su nombre completo, tiempo que lleva en la aplicación, la cantidad de intercambios exitosos realizados
    And valoraciones de otros usuarios
    And se mostrará la siguiente información:
      | Imagen de usuario          | Nombre completo    | Correo electrónico             | Valoración | Reseñas | Productos publicados recientes   |
      | <imagen_usuario>            | <nombre_usuario>   | <correo_usuario>               | <valoracion> | <numero_reseñas> | <productos_publicados>           |

    Examples:
      | imagen_usuario        | nombre_usuario   | correo_usuario         | valoracion     | numero_reseñas | productos_publicados                        |
      | imagen_maria.jpg      | María Pérez      | maria.perez@hotmail.com | (5/5)   | 1               | Raqueta de Tenis, Camiseta Barcelona         |

  Scenario: Visualizar reseñas de otros usuarios hacia un perfil en específico
    Given el usuario se encuentra en el perfil del autor del intercambio
    When se dirige a la sección llamada "Reseñas"
    Then el usuario podrá visualizar todas las reseñas que dicho autor ha recibido por todas sus publicaciones exitosas
    And podrá ver los detalles de las reseñas, incluyendo:
      | Reseña                              | Usuario que la dejó  | Fecha de publicación  |
      | <reseña>                            | <usuario_reseña>     | <fecha_reseña>        |

    Examples:
      | reseña                                         | usuario_reseña | fecha_reseña  |
      | "Excelente intercambio, todo como se acordó."  | Juan López     | 2025-05-10    |
      | "Muy buena comunicación y producto en estado excelente." | Carla Ruiz    | 2025-05-12    |

  Scenario: Visualizar el tipo de productos que suele intercambiar el usuario
    Given el usuario está en el perfil del intercambiador
    When se dirige a la sección "Preferencias"
    Then el usuario podrá visualizar la variedad de productos que suele intercambiar el autor del perfil
    And podrá ver si hace match con las preferencias del usuario interesado
    And se mostrará una lista de productos con los siguientes detalles:
      | Producto intercambiado        | Categoría      | Descripción corta                   |
      | <producto_intercambiado>      | <categoria>    | <descripcion_producto>              |

    Examples:
      | producto_intercambiado      | categoria    | descripcion_producto                      |
      | Raqueta de Tenis            | Deportes     | Raqueta profesional con tecnología de absorción de impacto |
      | Camiseta del Barcelona      | Ropa         | Camiseta oficial del FC Barcelona           |
