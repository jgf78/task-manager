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

🍃 Persistencia en MongoDB

🔄 Selección de base de datos mediante variables de entorno

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

🍃 MongoDB

🔄 Persistencia intercambiable

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
                    ┌──────────┴───────────┐
                    │                      │
                    ▼                      ▼
          ┌──────────────────┐   ┌──────────────────┐
          │ PostgreSQL       │   │ MongoDB          │
          │ JPA Adapter      │   │ Mongo Adapter     │
          └──────────────────┘   └──────────────────┘
```

La lógica de negocio no depende directamente de PostgreSQL, MongoDB, JPA o Spring Data.

Las tecnologías externas se conectan al dominio mediante adaptadores y puertos.

La implementación de persistencia se selecciona mediante la variable:

```text
DATABASE_TYPE
```

Valores disponibles:

```text
postgres
mongodb
```

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
│   │   │           │   ├── OpenApiConfiguration
│   │   │           │   ├── MongoPersistenceConfiguration
│   │   │           │   └── PostgresPersistenceConfiguration
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
│   │   │
│   │   │                   ├── persistence/
│   │   │                   │
│   │   │                   │   ├── TaskEntity
│   │   │                   │   ├── TaskJpaRepository
│   │   │                   │   ├── TaskPersistenceAdapter
│   │   │                   │   └── TaskSpecifications
│   │   │                   │
│   │   │                   └── mongodb/
│   │   │
│   │   │                       ├── TaskMongoDocument
│   │   │                       ├── TaskMongoRepository
│   │   │                       ├── TaskMongoPersistenceAdapter
│   │   │                       ├── TaskMongoIdGenerator
│   │   │                       └── TaskMongoCounter
│   │   │
│   │   └── resources/
│   │
│   │       ├── application.yml
│   │       ├── application-postgres.yml
│   │       └── application-mongodb.yml
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

La respuesta incluye:

```text
content
page
size
totalElements
totalPages
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

La ordenación está implementada tanto para PostgreSQL como para MongoDB.

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

La búsqueda por título realiza una búsqueda parcial sin distinguir entre mayúsculas y minúsculas.

Los filtros pueden combinarse con paginación y ordenación.

Ejemplo:

```http
GET /api/tasks?page=0&size=10&sortBy=CREATED_AT&direction=DESC&status=PENDING&title=Docker
```

Estos filtros están implementados tanto para PostgreSQL como para MongoDB.

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

La persistencia está desacoplada mediante el puerto:

```text
TaskRepository
```

La aplicación dispone actualmente de **dos implementaciones de persistencia**:

```text
PostgreSQL
MongoDB
```

La implementación utilizada se selecciona mediante:

```text
DATABASE_TYPE
```

Valores disponibles:

```text
postgres
mongodb
```

La lógica de negocio y los casos de uso no necesitan conocer qué base de datos está siendo utilizada.

---

# 🐘 Persistencia PostgreSQL

============================

Cuando:

```text
DATABASE_TYPE=postgres
```

se utiliza Spring Data JPA para acceder a PostgreSQL.

La arquitectura de persistencia es:

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

El adaptador PostgreSQL utiliza:

```text
TaskEntity
TaskJpaRepository
TaskPersistenceAdapter
TaskSpecifications
```

Los filtros dinámicos se implementan mediante Spring Data JPA `Specification`.

---

# 🍃 Persistencia MongoDB

==========================

Cuando:

```text
DATABASE_TYPE=mongodb
```

se utiliza Spring Data MongoDB.

La arquitectura de persistencia es:

```text
Application

     │
     ▼

TaskRepository

     │
     ▼

TaskMongoPersistenceAdapter

     │
     ├───────────────┐
     ▼               ▼

TaskMongoRepository  MongoOperations

     │               │
     ▼               ▼

MongoDB          Consultas dinámicas
```

El adaptador MongoDB utiliza:

```text
TaskMongoDocument
TaskMongoRepository
TaskMongoPersistenceAdapter
TaskMongoIdGenerator
TaskMongoCounter
```

Las tareas se almacenan en la colección:

```text
tasks
```

MongoDB utiliza un contador independiente para generar identificadores numéricos compatibles con el modelo de dominio:

```text
counters
```

El contador no debe eliminarse mientras se utilice la persistencia MongoDB.

---

# 🔄 Selección de base de datos

===============================

La base de datos se selecciona mediante la variable:

```text
DATABASE_TYPE
```

## PostgreSQL

```text
DATABASE_TYPE=postgres
```

Utiliza:

```text
application-postgres.yml
```

y las variables:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
```

## MongoDB

```text
DATABASE_TYPE=mongodb
```

Utiliza:

```text
application-mongodb.yml
```

y la variable:

```text
MONGODB_URI
```

Por ejemplo:

```text
MONGODB_URI=mongodb://usuario:password@host:27017/taskmanager?authSource=admin
```

Las dos configuraciones pueden estar presentes simultáneamente.

Únicamente la configuración correspondiente al valor de `DATABASE_TYPE` será utilizada por la aplicación.

Esto permite cambiar de PostgreSQL a MongoDB sin modificar el código de la aplicación.

---

# 🔍 Specifications

====================

En PostgreSQL, los filtros dinámicos de tareas se implementan mediante:

```text
TaskSpecifications
```

Esta clase utiliza Spring Data JPA `Specification` para construir dinámicamente las consultas.

Actualmente permite filtrar por:

```text
status
title
```

En MongoDB, el equivalente se implementa mediante `MongoOperations` y consultas dinámicas con `Criteria` y `Query`.

Esto permite mantener un comportamiento equivalente entre ambas implementaciones de persistencia.

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

La aplicación utiliza una configuración común y configuraciones específicas para cada sistema de persistencia.

```text
application.yml
application-postgres.yml
application-mongodb.yml
```

La configuración común define, entre otras propiedades:

```text
server.port
spring.application.name
logging
DATABASE_TYPE
```

La base de datos se selecciona mediante:

```text
DATABASE_TYPE
```

Valores disponibles:

```text
postgres
mongodb
```

Por defecto:

```text
DATABASE_TYPE=postgres
```

si la variable no está definida.

---

# 🐘 Configuración PostgreSQL

=============================

Variables:

```text
DATABASE_TYPE
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
```

Ejemplo:

```text
DATABASE_TYPE=postgres

DB_HOST=localhost
DB_PORT=5432
DB_NAME=taskmanager
DB_USERNAME=********
DB_PASSWORD=********
```

---

# 🍃 Configuración MongoDB

==========================

Variables:

```text
DATABASE_TYPE
MONGODB_URI
```

Ejemplo:

```text
DATABASE_TYPE=mongodb

MONGODB_URI=mongodb://********:********@localhost:27017/taskmanager?authSource=admin
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

La selección de la base de datos se realiza mediante variables de entorno.

El contenedor expone:

```text
Port 8087
```

La aplicación puede conectarse a PostgreSQL o MongoDB dependiendo de:

```text
DATABASE_TYPE
```

Arquitectura:

```text
                         Docker

              ┌────────────────────────┐
              │      Task Manager       │
              │                        │
              │      Spring Boot       │
              │         Java 25        │
              │                        │
              │        Port 8087       │
              └───────────┬────────────┘
                          │
                 DATABASE_TYPE
                          │
             ┌────────────┴────────────┐
             │                         │
             ▼                         ▼
      ┌───────────────┐        ┌───────────────┐
      │  PostgreSQL   │        │    MongoDB    │
      │               │        │               │
      │ iagente_      │        │ mongodb_      │
      │ backend       │        │ default       │
      └───────────────┘        └───────────────┘
```

Cuando los servicios se ejecutan en el mismo host Docker, `task-manager` utiliza las redes Docker internas para comunicarse con las bases de datos.

PostgreSQL:

```text
postgres:5432
```

MongoDB:

```text
mongodb:27017
```

De esta forma, la comunicación entre contenedores no necesita utilizar el hostname público del servidor.

---

# 🐳 Docker Compose

====================

Ejemplo de despliegue con PostgreSQL y MongoDB disponibles en redes Docker externas:

```yaml
services:

  task-manager:
    image: jgf78/task-manager:latest
    container_name: task-manager

    ports:
      - "8087:8087"

    environment:

      # Base de datos seleccionada
      DATABASE_TYPE: mongodb

      # PostgreSQL
      DB_HOST: postgres
      DB_PORT: 5432
      DB_NAME: taskmanager
      DB_USERNAME: ********
      DB_PASSWORD: ********

      # MongoDB
      MONGODB_URI: "mongodb://********:********@mongodb:27017/taskmanager?authSource=admin"

    networks:
      - iagente_backend
      - mongodb_default

    restart: unless-stopped

networks:

  iagente_backend:
    external: true

  mongodb_default:
    external: true
```

Para utilizar PostgreSQL:

```yaml
DATABASE_TYPE: postgres
```

Para utilizar MongoDB:

```yaml
DATABASE_TYPE: mongodb
```

Las redes:

```text
iagente_backend
mongodb_default
```

son redes Docker externas ya existentes.

Los nombres de los contenedores se utilizan como hostname interno:

```text
postgres
mongodb
```

---

# 🔧 Configuración local

========================

Clonar el proyecto:

```bash
git clone https://github.com/jgf78/task-manager.git

cd task-manager
```

Configurar las variables de entorno necesarias.

Para PostgreSQL:

```text
DATABASE_TYPE=postgres

DB_HOST=localhost
DB_PORT=5432
DB_NAME=taskmanager
DB_USERNAME=********
DB_PASSWORD=********
```

Para MongoDB:

```text
DATABASE_TYPE=mongodb

MONGODB_URI=mongodb://********:********@localhost:27017/taskmanager?authSource=admin
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

✅ MongoDB

✅ Persistencia intercambiable

✅ Selección de base de datos mediante `DATABASE_TYPE`

✅ Swagger / OpenAPI

✅ Docker

✅ Docker Compose

✅ Redes Docker internas

✅ Configuración mediante variables de entorno

---

# 🧩 Tecnologías

================

```text
Java 25              -> Lenguaje principal

Spring Boot          -> Framework backend

Spring Web           -> API REST

Spring Data JPA      -> Persistencia PostgreSQL

Hibernate            -> ORM

Spring Data MongoDB  -> Persistencia MongoDB

PostgreSQL           -> Base de datos relacional

MongoDB              -> Base de datos documental

Jakarta Validation   -> Validación

Springdoc OpenAPI    -> Documentación API

Swagger UI           -> Documentación interactiva

Maven                -> Build tool

Docker               -> Contenerización

Docker Compose       -> Despliegue
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

🍃 Persistencia relacional y documental

🔎 Consultas dinámicas

📄 Paginación

🔀 Ordenación

🔎 Filtrado

📚 Documentación de APIs

🐳 Contenerización

🔧 Configuración por entornos

🔄 Intercambio de tecnología de persistencia

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

🍃 MongoDB

📚 OpenAPI

---

# 🧠 Frase final

===============

> "El dominio define las reglas.
>
> Los casos de uso definen lo que hacemos.
>
> Los puertos definen cómo nos comunicamos.
>
> Los adapters conectan el mundo exterior.
>
> Y la arquitectura permite que cada pieza pueda evolucionar sin arrastrar a las demás."
