# 📋 Task Manager - API de Gestión de Tareas

====================================================

Task Manager es una API REST desarrollada con Spring Boot + Java 25 para la gestión de tareas.

El proyecto está desarrollado siguiendo los principios de **Arquitectura Hexagonal (Ports & Adapters)**, separando la lógica de negocio, los casos de uso y las tecnologías externas.

La aplicación proporciona:

🌐 API REST

📝 Gestión de tareas

✅ Creación y actualización de tareas

✔️ Completar tareas

🗑️ Eliminación de tareas

📄 Paginación

🔀 Ordenación

🔎 Filtrado por estado

🔍 Filtrado por título

💾 Persistencia en PostgreSQL

✅ Validación de datos

❌ Gestión centralizada de errores

📚 Swagger / OpenAPI

🐳 Docker

🔧 Configuración mediante variables de entorno

---

# 🚀 Características principales

==============================

⚡ Backend con Spring Boot

☕ Java 25

🌐 API REST

🏗️ Arquitectura Hexagonal

🧩 Ports & Adapters

📝 CRUD de tareas

✔️ Reglas de negocio

📄 Paginación

🔀 Ordenación dinámica

🔎 Filtros dinámicos

🔍 Búsqueda por título

💾 PostgreSQL

✅ Bean Validation

❌ Gestión global de excepciones

📚 Swagger / OpenAPI

🐳 Docker

🔧 Configuración mediante variables de entorno

---

# 🏗️ Arquitectura

================

El proyecto utiliza **Arquitectura Hexagonal**, también conocida como **Ports & Adapters**.

La idea principal es mantener la lógica de negocio independiente de las tecnologías utilizadas para exponer y almacenar la información.

La arquitectura se divide principalmente en:

```text
                    ┌──────────────────────┐
                    │      REST API        │
                    │   Web Controller     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      Input Port      │
                    │     Use Case         │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │     Application      │
                    │      Services        │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │       Domain         │
                    │   Business Rules     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      Output Port     │
                    │   TaskRepository     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │   Persistence        │
                    │   JPA / PostgreSQL   │
                    └──────────────────────┘
```

La lógica de negocio no depende directamente de Spring, JPA o PostgreSQL.

Las tecnologías externas se conectan al dominio mediante adaptadores y puertos.

---

# 🧩 Estructura de capas

========================

El proyecto está organizado de la siguiente manera:

```text
task-manager/

├── src/
│
│   ├── main/
│   │
│   │   ├── java/
│   │   │
│   │   │   └── com/julian/task_manager/
│   │   │
│   │   │       ├── application/
│   │   │       │
│   │   │       │   ├── dto/
│   │   │       │   │
│   │   │       │   │   ├── CreateTaskRequest
│   │   │       │   │   ├── UpdateTaskRequest
│   │   │       │   │   ├── TaskResponse
│   │   │       │   │   ├── TaskPageResponse
│   │   │       │   │   └── ErrorResponse
│   │   │       │   │
│   │   │       │   └── service/
│   │   │       │       │
│   │   │       │       ├── CreateTaskService
│   │   │       │       ├── GetTaskService
│   │   │       │       ├── GetAllTasksService
│   │   │       │       ├── UpdateTaskService
│   │   │       │       ├── DeleteTaskService
│   │   │       │       └── CompleteTaskService
│   │   │       │
│   │   │       ├── domain/
│   │   │       │
│   │   │       │   ├── model/
│   │   │       │   │
│   │   │       │   │   ├── Task
│   │   │       │   │   ├── TaskStatus
│   │   │       │   │   ├── TaskPage
│   │   │       │   │   ├── TaskPageRequest
│   │   │       │   │   ├── TaskSort
│   │   │       │   │   ├── TaskSortField
│   │   │       │   │   ├── SortDirection
│   │   │       │   │   └── TaskFilter
│   │   │       │   │
│   │   │       │   ├── port/
│   │   │       │   │
│   │   │       │   │   ├── in/
│   │   │       │   │   │
│   │   │       │   │   │   ├── CreateTaskUseCase
│   │   │       │   │   │   ├── GetTaskUseCase
│   │   │       │   │   │   ├── GetAllTasksUseCase
│   │   │       │   │   │   ├── UpdateTaskUseCase
│   │   │       │   │   │   ├── DeleteTaskUseCase
│   │   │       │   │   │   └── CompleteTaskUseCase
│   │   │       │   │   │
│   │   │       │   │   └── out/
│   │   │       │   │
│   │   │       │   │       └── TaskRepository
│   │   │       │   │
│   │   │       │   └── exception/
│   │   │       │
│   │   │       │       └── TaskNotFoundException
│   │   │       │
│   │   │       └── infrastructure/
│   │   │
│   │   │           ├── config/
│   │   │           │
│   │   │           │   ├── BeanConfiguration
│   │   │           │   └── OpenApiConfiguration
│   │   │           │
│   │   │           └── adapter/
│   │   │
│   │   │               ├── in/
│   │   │               │   └── web/
│   │   │               │
│   │   │               │       ├── TaskController
│   │   │               │       └── GlobalExceptionHandler
│   │   │               │
│   │   │               └── out/
│   │   │                   └── persistence/
│   │   │
│   │   │                       ├── TaskEntity
│   │   │                       ├── TaskJpaRepository
│   │   │                       ├── TaskPersistenceAdapter
│   │   │                       └── TaskSpecifications
│   │   │
│   │   └── resources/
│   │
│   │       ├── application.yml
│   │       └── application-docker.yml
│   │
│   └── test/
│
└── pom.xml
```

---

# 🧠 Dominio

================

El dominio contiene las reglas de negocio de la aplicación.

La entidad principal es:

```text
Task
```

Una tarea contiene:

```text
Task

├── id
├── title
├── description
├── status
├── createdAt
└── updatedAt
```

El estado de una tarea puede ser:

```text
PENDING
COMPLETED
```

Las reglas de negocio se encuentran dentro del modelo de dominio.

Por ejemplo, una tarea no puede completarse dos veces.

```text
PENDING
   │
   │ complete()
   ▼
COMPLETED
```

Si se intenta completar una tarea que ya está completada, se produce un error de negocio.

---

# 📝 Operaciones disponibles

===========================

La API proporciona las siguientes operaciones:

```text
POST   /api/tasks
GET    /api/tasks
GET    /api/tasks/{id}
PUT    /api/tasks/{id}
PATCH  /api/tasks/{id}/complete
DELETE /api/tasks/{id}
```

---

# 🌐 API REST

================

## ➕ Crear tarea

```http
POST /api/tasks
```

Ejemplo:

```json
{
  "title": "Learn Hexagonal Architecture",
  "description": "Study ports, adapters and domain separation"
}
```

Una nueva tarea se crea inicialmente con estado:

```text
PENDING
```

---

## 🔎 Obtener tarea

```http
GET /api/tasks/{id}
```

Ejemplo:

```http
GET /api/tasks/1
```

Devuelve la información de la tarea solicitada.

---

## 📋 Obtener tareas

```http
GET /api/tasks
```

La API permite consultar las tareas utilizando:

📄 Paginación

🔀 Ordenación

🔎 Filtrado por estado

🔍 Filtrado por título

Ejemplo:

```http
GET /api/tasks?page=0&size=10
```

---

# 📄 Paginación

================

La API utiliza paginación mediante los parámetros:

```text
page
size
```

Ejemplo:

```http
GET /api/tasks?page=0&size=10
```

Donde:

```text
page = página solicitada, comenzando en 0

size = número de elementos por página
```

El tamaño máximo permitido es:

```text
100
```

---

# 🔀 Ordenación

================

Las tareas pueden ordenarse mediante:

```text
sortBy
direction
```

Campos disponibles:

```text
ID
TITLE
STATUS
CREATED_AT
UPDATED_AT
```

Direcciones disponibles:

```text
ASC
DESC
```

Ejemplo:

```http
GET /api/tasks?sortBy=CREATED_AT&direction=DESC
```

---

# 🔎 Filtrado

================

La API permite filtrar tareas por estado:

```text
status
```

Valores disponibles:

```text
PENDING
COMPLETED
```

Ejemplo:

```http
GET /api/tasks?status=PENDING
```

También permite buscar tareas por título:

```text
title
```

Ejemplo:

```http
GET /api/tasks?title=Docker
```

Los filtros pueden combinarse con paginación y ordenación.

Ejemplo:

```http
GET /api/tasks?page=0&size=10&sortBy=CREATED_AT&direction=DESC&status=PENDING&title=Docker
```

---

# ✏️ Actualizar tarea

=====================

```http
PUT /api/tasks/{id}
```

Ejemplo:

```http
PUT /api/tasks/1
```

Body:

```json
{
  "title": "Learn Spring Boot",
  "description": "Study Spring Boot and Hexagonal Architecture"
}
```

---

# ✔️ Completar tarea

===================

```http
PATCH /api/tasks/{id}/complete
```

Ejemplo:

```http
PATCH /api/tasks/1/complete
```

La operación cambia el estado:

```text
PENDING
   │
   ▼
COMPLETED
```

Una tarea que ya está completada no puede volver a completarse.

En ese caso la API devuelve:

```text
HTTP 409 Conflict
```

---

# 🗑️ Eliminar tarea

==================

```http
DELETE /api/tasks/{id}
```

Ejemplo:

```http
DELETE /api/tasks/1
```

Si la tarea no existe, la API devuelve:

```text
HTTP 404 Not Found
```

---

# ❌ Gestión de errores

======================

La aplicación dispone de un `GlobalExceptionHandler` encargado de centralizar el tratamiento de errores HTTP.

Las respuestas de error utilizan el modelo:

```text
ErrorResponse

├── timestamp
├── status
├── errors
└── path
```

Ejemplo:

```json
{
  "timestamp": "2026-10-01T09:15:00Z",
  "status": 404,
  "errors": [
    "Task not found"
  ],
  "path": "/api/tasks/99"
}
```

Se controlan, entre otros:

❌ Errores de validación

❌ Parámetros incorrectos

❌ Valores de enum inválidos

❌ Tareas inexistentes

❌ Reglas de negocio incumplidas

❌ Errores internos

---

# ✅ Validación

================

La API utiliza Jakarta Bean Validation.

Las tareas validan, entre otros aspectos:

```text
title
```

El título:

✔️ Es obligatorio

✔️ No puede estar vacío

✔️ Tiene una longitud máxima de 100 caracteres

La descripción:

✔️ Es opcional

✔️ Tiene una longitud máxima de 255 caracteres

Los parámetros de paginación también están validados.

Por ejemplo:

```text
page >= 0

size >= 1

size <= 100
```

---

# 💾 Persistencia

================

La aplicación utiliza PostgreSQL para almacenar las tareas.

La persistencia está desacoplada mediante el puerto:

```text
TaskRepository
```

La infraestructura implementa este puerto mediante:

```text
TaskPersistenceAdapter
```

Spring Data JPA se utiliza exclusivamente en la capa de infraestructura.

Arquitectura:

```text
Application
     │
     ▼
TaskRepository
     │
     ▼
TaskPersistenceAdapter
     │
     ▼
TaskJpaRepository
     │
     ▼
PostgreSQL
```

---

# 🔌 Ports & Adapters

=====================

## 🔵 Input Ports

Los puertos de entrada representan los casos de uso disponibles para los adaptadores externos.

```text
CreateTaskUseCase
GetTaskUseCase
GetAllTasksUseCase
UpdateTaskUseCase
DeleteTaskUseCase
CompleteTaskUseCase
```

El controlador REST depende de estos puertos y no directamente de las implementaciones de los servicios.

---

## 🟢 Output Ports

El principal puerto de salida es:

```text
TaskRepository
```

La aplicación utiliza esta abstracción para acceder a las tareas sin conocer PostgreSQL, JPA o Spring Data.

---

## 🟠 Adapters

### Web Adapter

```text
TaskController
```

Se encarga de:

* Recibir peticiones HTTP
* Validar los datos de entrada
* Construir los objetos de dominio necesarios
* Delegar en los casos de uso
* Devolver las respuestas HTTP

### Persistence Adapter

```text
TaskPersistenceAdapter
```

Se encarga de:

* Convertir entidades JPA a modelos de dominio
* Convertir modelos de dominio a entidades JPA
* Consultar PostgreSQL
* Gestionar paginación
* Gestionar ordenación
* Aplicar filtros dinámicos

---

# 🔍 Specifications

====================

Los filtros dinámicos de tareas se implementan mediante:

```text
TaskSpecifications
```

Esta clase utiliza Spring Data JPA `Specification` para construir dinámicamente las consultas.

Actualmente permite filtrar por:

```text
status
title
```

Esto permite combinar filtros sin introducir métodos específicos en el repositorio para cada combinación posible.

---

# 📚 Swagger / OpenAPI

=====================

La API REST dispone de documentación mediante Swagger / OpenAPI.

Una vez arrancada la aplicación puede accederse desde:

```text
http://localhost:8080/swagger-ui.html
```

En Docker:

```text
http://localhost:8087/swagger-ui.html
```

La documentación incluye:

📌 Endpoints

📌 Parámetros

📌 Modelos de entrada

📌 Modelos de respuesta

📌 Respuestas de error

📌 Códigos HTTP

📌 Validaciones

---

# ⚙️ Configuración

==================

La aplicación utiliza diferentes configuraciones dependiendo del entorno.

```text
application.yml

application-docker.yml
```

La configuración Docker activa el perfil:

```text
docker
```

El proyecto utiliza variables de entorno para configurar la conexión con PostgreSQL.

Variables:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
```

Ejemplo:

```text
DB_HOST=********
DB_PORT=5432
DB_NAME=taskmanager
DB_USERNAME=********
DB_PASSWORD=********
```

Las credenciales reales no deben almacenarse directamente en el código fuente.

---

# 🐳 Docker

===========

La aplicación está preparada para ejecutarse mediante Docker.

El contenedor utiliza:

```text
Java 25
```

El perfil Docker se activa automáticamente desde el `Dockerfile`.

Arquitectura:

```text
Docker

┌──────────────────────────────┐
│        Task Manager          │
│                              │
│       Spring Boot            │
│          Java 25             │
│                              │
│         Port 8087            │
└──────────────┬───────────────┘
               │
               │ JDBC
               ▼
        ┌───────────────┐
        │  PostgreSQL   │
        └───────────────┘
```

El despliegue mediante Docker utiliza las variables:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
```

---

# 🐳 Docker Compose

====================

El proyecto puede desplegarse mediante Docker Compose.

Ejemplo:

```yaml
services:

  task-manager:
    image: jgf78/task-manager:latest
    container_name: task-manager

    ports:
      - "8087:8087"

    environment:
      DB_HOST: ********
      DB_PORT: 5432
      DB_NAME: taskmanager
      DB_USERNAME: ********
      DB_PASSWORD: ********

    restart: unless-stopped
```

El servicio queda disponible en:

```text
http://localhost:8087
```

o utilizando la IP/hostname del servidor donde se despliegue.

---

# 🔧 Configuración local

========================

Clonar el proyecto:

```bash
git clone https://github.com/jgf78/task-manager.git

cd task-manager
```

Configurar las variables de entorno necesarias:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
```

Compilar:

```bash
mvn clean install -DskipTests
```

Arrancar la aplicación:

```bash
mvn spring-boot:run
```

La aplicación estará disponible en:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

---

# 🗄️ PostgreSQL

================

Task Manager utiliza PostgreSQL como sistema de persistencia.

Configuración mediante variables de entorno:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
```

Ejemplo:

```text
DB_HOST=localhost
DB_PORT=5432
DB_NAME=taskmanager
DB_USERNAME=********
DB_PASSWORD=********
```

La aplicación utiliza Spring Data JPA e Hibernate para acceder a PostgreSQL.

---

# 🧪 Estado del proyecto

========================

Funcionalidades disponibles:

✅ API REST

✅ Arquitectura Hexagonal

✅ CRUD de tareas

✅ Reglas de negocio

✅ Validación

✅ Gestión centralizada de errores

✅ Paginación

✅ Ordenación

✅ Filtrado por estado

✅ Filtrado por título

✅ Specifications dinámicas

✅ PostgreSQL

✅ Swagger / OpenAPI

✅ Docker

✅ Docker Compose

✅ Configuración mediante variables de entorno

---

# 🧩 Tecnologías

================

```text
Java 25              -> Lenguaje principal

Spring Boot          -> Framework backend

Spring Web           -> API REST

Spring Data JPA      -> Persistencia

Hibernate             -> ORM

PostgreSQL           -> Base de datos

Jakarta Validation    -> Validación

Springdoc OpenAPI     -> Documentación API

Swagger UI            -> Documentación interactiva

Maven                 -> Build tool

Docker                -> Contenerización

Docker Compose        -> Despliegue
```

---

# 🎯 Objetivo del proyecto

=========================

Task Manager ha sido desarrollado como un proyecto práctico para trabajar y profundizar en:

🏗️ Arquitectura Hexagonal

🧩 Ports & Adapters

🧠 Separación de dominio y tecnología

📦 Casos de uso

🔌 Puertos de entrada y salida

💾 Persistencia desacoplada

🔎 Consultas dinámicas

📄 Paginación

🔀 Ordenación

🔎 Filtrado

📚 Documentación de APIs

🐳 Contenerización

🔧 Configuración por entornos

El objetivo principal es mantener una arquitectura limpia y preparada para evolucionar sin acoplar la lógica de negocio a frameworks o tecnologías concretas.

---

# 📄 Licencia

===========

MIT License — uso libre y modificación.

---

# 👤 Autor

========

Julián Gómez Fernández

💻 Java Developer

⚙️ Java · Spring Boot · Docker · REST

🏗️ Arquitectura Hexagonal

🗄️ PostgreSQL

📚 OpenAPI

---

# 🧠 Frase final

===============

> "El dominio define las reglas.

> Los casos de uso definen lo que hacemos.

> Los puertos definen cómo nos comunicamos.

> Los adapters conectan el mundo exterior.

> Y la arquitectura permite que cada pieza pueda evolucionar sin arrastrar a las demás."
