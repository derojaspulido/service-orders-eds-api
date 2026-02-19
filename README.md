
# Service Orders API

API REST desarrollada con **Spring Boot 3.5.10** para la gestión de órdenes de servicio asociadas a estaciones de servicio.

El proyecto implementa operaciones CRUD, validación de datos, persistencia en PostgreSQL, documentación OpenAPI (Swagger) y ejecución mediante Docker.

---

## 📌 Características

✔ Creación de órdenes  
✔ Consulta de órdenes (paginadas)  
✔ Consulta por ID  
✔ Actualización de estado  
✔ Validación de datos  
✔ Seguridad básica (Basic Auth)  
✔ Documentación Swagger/OpenAPI  
✔ Healthcheck con Actuator  
✔ Contenerización con Docker  

---

## 🛠 Stack Tecnológico

- **Java 21**
- **Spring Boot 3.5.10**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Springdoc OpenAPI (Swagger)**
- **Lombok**
- **MapStruct**

---

## 🏗 Arquitectura

El proyecto sigue una estructura en capas:

```

Controller → Service → Repository → Database

````

Separando responsabilidades:

- **Controller** → Exposición REST
- **Service** → Lógica de negocio
- **Repository** → Persistencia JPA
- **DTOs** → Contratos API
- **Entities** → Modelo de datos

---

## 🐳 Ejecución con Docker

### ✅ Requisitos

- Docker
- Docker Compose

---

### ▶ Levantar servicios

```bash
docker compose up --build
````

Esto iniciará:

✔ Backend (Spring Boot)
✔ Base de datos PostgreSQL

---

### 🔎 Verificar estado

**Healthcheck:**

```
http://localhost:8080/actuator/health
```

Respuesta esperada:

```json
{"status":"UP"}
```

---

## 📚 Documentación API (Swagger)

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔐 Seguridad

La API usa **Basic Authentication**.

Credenciales por defecto:

```
user / user123
```

Ejemplo curl:

```bash
-u user:user123
```

---

## 📡 Endpoints Principales

---

### ✅ Crear Orden

**POST** `/service-orders`

```json
{
  "stationId": "ST123",
  "type": "INVOICE",
  "description": "Orden de prueba",
  "status": "CREATED"
}
```

---

### ✅ Consultar Órdenes

**GET** `/service-orders`

Parámetros opcionales:

* `stationId`
* `status`
* `page`
* `size`

Ejemplo:

```bash
curl -X GET "http://localhost:8080/service-orders?page=0&size=10" \
-u user:user123
```

---

### ✅ Consultar por ID

**GET** `/service-orders/{id}`

---

### ✅ Actualizar Estado

**PATCH** `/service-orders/{id}/status`

```json
{
  "status": "DONE"
}
```

---

## 🧪 Ejemplo curl – Crear Orden

```bash
curl -X POST http://localhost:8080/service-orders \
-u user:user123 \
-H "Content-Type: application/json" \
-d '{
  "stationId": "ST123",
  "type": "INVOICE",
  "description": "Orden de prueba",
  "status": "CREATED"
}'
```

---

## 🗄 Base de Datos

Motor:

**PostgreSQL**

Persistencia mediante:

✔ Spring Data JPA
✔ Hibernate ORM

---

## 📂 Estructura del Proyecto

```
src/main/java
 ├── controller
 ├── service
 ├── repository
 ├── dto
 ├── entity
 └── config
```

---

## ⚙ Configuración

Archivo principal:

```
application.yml
```

Configuraciones incluidas:

✔ Datasource
✔ JPA / Hibernate
✔ Swagger
✔ Seguridad
✔ Actuator

---

## 🚧 Mejoras Futuras

* JWT Authentication
* Auditoría avanzada
* Soft deletes
* Filtros dinámicos
* Tests de integración completos
* CI/CD con GitHub Actions

---

## 👨‍💻 Autor: Daniel Eduardo Rojas Pulido

Proyecto desarrollado como práctica de:

✔ Spring Boot
✔ Arquitectura REST
✔ Docker
✔ PostgreSQL

---

## 📜 Licencia

Uso académico / educativo