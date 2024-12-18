# Segundas Huellas | Frontend | Guía para instalación local

> [!NOTE]
> Para poder hacer uso de la totalidad de funcionalidades de este frontend, es necesario tener instalado y corriendo el [proyecto backend de Segundas Huellas](https://github.com/No-Country-simulation/C22-23-t-webapp/tree/main/backend)._

<details>
  <summary>Instalación usando git clone 🔧</summary>

### Cómo clonar el proyecto

Sigue estos pasos para clonar el repositorio e instalar las dependencias necesarias:

1. **Clonar el repositorio**    
    Ejecuta el siguiente comando en tu terminal, reemplazando `URL_DEL_PROYECTO` por el enlace del proyecto:

```sh
git clone URL_DEL_PROYECTO
```

2. **OPCIONAL - Cambiar de rama**   
    Ejecuta el siguiente comando en tu terminal, reemplazando `RAMA` por la rama a utilizar:

```sh
git checkout RAMA
```

### Cómo instalar las dependencias del proyecto

1. Ejecuta el siguiente comando en tu terminal:

```sh
npm install
```

_Nota: Puedes utilizar otro package manager si así lo deseas; procura revisar las `Tecnologías principales` y también las `Librerías` para comprobar que son compatibles primero_

### Crear archivo `.env`

1. En la raíz del proyecto, crea un archivo llamado **.env**. Este archivo almacenará las variables de entorno utilizadas en el proyecto.
   
2. A continuación, define las variables de entorno necesarias para el proyecto. 
    
    _Nota: `Vite` tiene una forma particular de implementar variables de entorno con `.env`. La primer cuestión a tener en cuenta es usar el prefijo `VITE` a cualquier variable como se muestra en el ejemplo. Luego, la forma de invocar cada una de estas variables en el código, es haciendo uso del objeto `import.meta.env`. Para más información consultar la [documentación oficial de Vite](https://vite.dev/guide/env-and-mode)_

#### Ejemplo de archivo `.env`

Acomodar cada URL y puertos acorde a la configuración que se haya hecho al levantar el backend

```sh
VITE_AUTH_ADOPTER_URL="http://127.0.0.1:8080/api/auth/adopters/"
VITE_AUTH_LOGIN_URL="http://127.0.0.1:8080/api/auth/login"
VITE_AUTH_REFRESH_URL="http://127.0.0.1:8080/api/auth/refresh"
VITE_PET_DETAILS_URL="http://127.0.0.1:8080/api/pets/"
VITE_PET_SEARCH_URL="http://127.0.0.1:8080/api/pets/search"
VITE_PET_SEARCH_FILTER_BREED_URL="http://127.0.0.1:8080/api/pets/reference-data/breeds"
```

### Iniciar servidor (en modo `dev`)

```sh
npm run dev
```
</details>

<details>
  <summary>Instalación descargando comprimido ZIP 🔧</summary>

## Instalación descargando comprimido ZIP 🔧

### Cómo descargar el proyecto

Sigue estos pasos para clonar el repositorio e instalar las dependencias necesarias:

1. **Descargar el proyecto en formato ZIP** 
    Ir a “code” > download ZIP

2. **Descomprimir el archivo**

3. **OPCIONAL - Cambiar de rama**   
    Ejecuta el siguiente comando en tu terminal, reemplazando `RAMA` por la rama a utilizar:

```sh
git checkout RAMA
```

### Cómo instalar las dependencias del proyecto

1. En la carpeta donde se encuentra “package.json” ejecutar en terminal:

```sh
npm install
```

_Nota: Puedes utilizar otro package manager si así lo deseas; procura revisar las `Tecnologías principales` y también las `Librerías` para comprobar que son compatibles primero_

### Crear archivo `.env`

1. En la raíz del proyecto, crea un archivo llamado **.env**. Este archivo almacenará las variables de entorno utilizadas en el proyecto.
   
2. A continuación, define las variables de entorno necesarias para el proyecto. 
    
    _Nota: `Vite` tiene una forma particular de implementar variables de entorno con `.env`. La primer cuestión a tener en cuenta es usar el prefijo `VITE` a cualquier variable como se muestra en el ejemplo. Luego, la forma de invocar cada una de estas variables en el código, es haciendo uso del objeto `import.meta.env`. Para más información consultar la [documentación oficial de Vite](https://vite.dev/guide/env-and-mode)_

#### Ejemplo de archivo `.env`

Acomodar cada URL y puertos acorde a la configuración que se haya hecho al levantar el backend

```sh
VITE_AUTH_ADOPTER_URL="http://127.0.0.1:8080/api/auth/adopters/"
VITE_AUTH_LOGIN_URL="http://127.0.0.1:8080/api/auth/login"
VITE_AUTH_REFRESH_URL="http://127.0.0.1:8080/api/auth/refresh"
VITE_PET_DETAILS_URL="http://127.0.0.1:8080/api/pets/"
VITE_PET_SEARCH_URL="http://127.0.0.1:8080/api/pets/search"
VITE_PET_SEARCH_FILTER_BREED_URL="http://127.0.0.1:8080/api/pets/reference-data/breeds"
```

### Iniciar servidor (en modo `dev`)

```sh
npm run dev
```
</details>
