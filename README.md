# Gestor Documental de Proyectos de Deducciones de I+D+i

Este proyecto es un backend desarrollado con Spring Boot que forma parte de un sistema de gestión documental para proyectos de deducciones de I+D+i. Es el Backend del proyecto Gestor Documental FrontEnd
## Características

- **Gestión Documental**: Permite compartir y organizar archivos entre usuarios.
- **Resolución de No Conformidades (NC)**:
    - Responder no conformidades generadas por gestores de proyecto, contables, comités, expertos 4D o técnicos.
    - Gestión dinámica de estados de las no conformidades hasta su cierre.
- **Gestión de Proyectos**:
    - Crear, editar y eliminar proyectos.
    - Asignar usuarios a proyectos.
    - Asignar archivos a proyectos.
- **Gestión de Usuarios**:
    - Crear, editar y eliminar usuarios.
    - Restringir el acceso a ciertas rutas según el rol del usuario.

## Tecnologías Utilizadas
- ![Spring](https://img.shields.io/badge/-Spring-6DB33F?style=flat&logo=spring&logoColor=white) Spring Framework
- ![Spring Security](https://img.shields.io/badge/-Spring%20Security-6DB33F?style=flat&logo=spring&logoColor=white) Spring Security (para autenticación y autorización)
- ![JWT](https://img.shields.io/badge/-JWT-000000?style=flat&logo=json-web-tokens&logoColor=white) JWT (JSON Web Tokens para autenticación)
- ![MySQL](https://img.shields.io/badge/-MySQL-4479A1?style=flat&logo=mysql&logoColor=white) MySQL (base de datos)
- ![JPA](https://img.shields.io/badge/-JPA-59666C?style=flat&logo=java&logoColor=white) JPA (Java Persistence API para ORM)
- ![Lombok](https://img.shields.io/badge/-Lombok-000000?style=flat&logo=lombok&logoColor=white) Lombok (para reducir código boilerplate)
- ![Swagger](https://img.shields.io/badge/-Swagger-85EA2D?style=flat&logo=swagger&logoColor=white) Swagger (para documentación de APIs)

## Instalación
1. Clonar el repositorio
```bash
  git clone
```

2. Ejectuar el servidor de desarrollo: Ve a la clase principal `GestorDocumentalBackEndApplication` y ejecútala.

3. Ejectuar el cliente de desarrollo: Ve a la carpeta `GestorDocumentalFrontEnd` y ejecuta el comando `ng serve`.

4. Accede a `http://localhost:4200/` en tu navegador.

5. ¡Listo!

## Datos de Ejemplo

El archivo DataLoaderExample.java contiene una carga de ejemplo de datos en la base de datos.


## Autores

El proyecto esta integramente desarrollado por Mario Borrego

## Copyright and license

### Licencia

Este proyecto está licenciado bajo la [Licencia Creative Commons Atribución-NoComercial 4.0 Internacional (CC BY-NC 4.0)](https://creativecommons.org/licenses/by-nc/4.0/deed.es).

### Permisos:
✅ Compartir — copiar y redistribuir el material en cualquier medio o formato.  
✅ Adaptar — remezclar, transformar y construir a partir del material.

### Bajo las siguientes condiciones:
- **Atribución** — Debe otorgar el crédito adecuado, proporcionar un enlace a la licencia e indicar si se han realizado cambios.
- **No Comercial** — No puede utilizarse el material con fines comerciales.
- No puede hacerlo de manera que sugiera que tiene el apoyo del licenciante o que este aprueba su uso.

Para más detalles, consulte el texto completo de la licencia en [Creative Commons](https://creativecommons.org/licenses/by-nc/4.0/legalcode.es).
[![Licencia CC BY-NC 4.0](https://licensebuttons.net/l/by-nc/4.0/88x31.png)](https://creativecommons.org/licenses/by-nc/4.0/deed.es)
