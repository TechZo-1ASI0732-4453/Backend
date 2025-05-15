Feature: US07 - Visualización de ONGs registradas y filtrado  
  Como usuario Donante  
  Quiero visualizar la lista de ONGs registradas y poder filtrarlas por nombre usando el buscador  
  Para encontrar la ONG específica en la cual me gustaría hacer mi donación  

  Scenario: Visualización por Defecto de ONGs Registradas  
    Given el usuario donante ha accedido a la sección de ONGs registradas para donación  
    When el usuario ingresa a esta sección por primera vez o no ha aplicado ningún filtro  
    Then la aplicación muestra por defecto una lista de ONGs registradas con su imagen de logo, nombre, tipo de organización (ej. educación, salud), y ubicación (ej. San Borja, Lima)  

  Scenario Outline: Aplicación de Filtros por Nombre  
    Given el usuario donante está en la sección de ONGs registradas para donación  
    When el usuario escribe el nombre o parte del nombre de una ONG en el campo de búsqueda que se muestra en la parte superior  
    And pulsa la tecla "Enter" o selecciona la opción de búsqueda  
    Then la lista de ONGs se ajustará automáticamente mostrando solo aquellas cuyo nombre coincida con el término ingresado  
    And la aplicación mostrará para cada ONG filtrada los siguientes detalles:  
      | Nombre de la ONG  | Tipo de Organización  | Ubicación     | Imagen del Logo  |  
      | Fundación ABC     | Educación             | San Borja, Lima | [Logo Fundación ABC] |  
      | Salud Global      | Salud                 | Miraflores, Lima | [Logo Salud Global] |  

    Examples:  
      | nombre_buscar |  
      | Fundación ABC |  
      | Salud Global  |  

  Scenario Outline: Visualización de Resultados Filtrados por Nombre  
    Given el usuario donante ha aplicado un filtro por nombre en el buscador  
    When la aplicación muestra únicamente las ONGs cuyos nombres coincidan con el término buscado  
    Then el usuario podrá ver y explorar los resultados filtrados, mostrando los siguientes detalles de cada ONG:  
      | Nombre de la ONG  | Tipo de Organización  | Ubicación     | Imagen del Logo  |  
      | Fundación ABC     | Educación             | San Borja, Lima | [Logo Fundación ABC] |  
      | Salud Global      | Salud                 | Miraflores, Lima | [Logo Salud Global] |  

    Examples:  
      | nombre_buscar |  
      | Fundación ABC |  
      | Salud Global  |  
