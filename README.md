# PinAppChallenge
Este es el proyecto prueba de PinApp, en esta app se muestra un listado de Posts y se puede acceder a cada uno para ver sus comentarios.

# Experiencia de usuario
Este proyecto contiene las siguientes características:

Utilizo Jetpack Compose para mostrar:
* La pantalla principal donde se ve un listado de posts.
* Una vista con un post específico con los comentarios de este (se accede seleccionado un post del listado de la primera pantalla).
# Capturas de pantalla

<p align="center">
  <img width="270" height="555" src="">
  <img width="270" height="555" src="">
</p>

## Guía de implementación
Traigo la información desde los endpoints: 
- https://jsonplaceholder.typicode.com/posts
- https://jsonplaceholder.typicode.com/comments?postId=1

### Arquitectura
Este proyecto implementa el patrón de arquitectura MVVM y sigue buenas prácticas de Clean Architecture para hacer un código más independiente, mantenible y sencillo.

#### Capas
* Presentation: Fragments, Activities, Viewmodels.
* Data: contiene la implementación del repositorio y los sources donde se conecta con la api y con la base de datos.
* Domain: contiene los casos de uso y la definición del repositorio.
Este proyecto usa ViewModel para almacenar y manejar datos, así como comunicar cambios hacia la vista.

### Administrador de solicitudes: Retrofit

Este proyecto utiliza Retrofit para mostrar los posts y comments desde una API.

### Inyección de dependencia - Dagger

Este proyecto utiliza Dagger para gestionar la inyección de dependencia.

### Persistencia de datos - Room

Este proyecto utiliza la base de datos de Room para almacenar los posts y comments.

### Testing

La app posee tests hechos con JUnit4 y Espresso

### Patrones de diseño

Utilizo algunos patrones de diseño como Observer, Singleton, Builder

# Guía de instalación
En caso de no tener instalado Android Studio, descargue la última versión estable. Una vez que tenemos el programa instalado vamos a Get from Version Control y vamos a pegar https://github.com/axel-sanchez/PinAppChallenge.git. Una vez hecho eso se va a clonar el proyecto, lo que resta sería conectar un celular y darle al botón verde de Run 'app'
