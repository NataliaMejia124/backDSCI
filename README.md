# Test Rewow

API REST para gestión de historiales médicos de mascotas (Pets), dueños (Owners) y vacunaciones. Desarrollada con **Spring Boot 4**, **JPA/Hibernate** y **Oracle XE**.

---

## Requisitos

- **Java 17**
- **Maven 3.6+**
- **Oracle Database XE** (21c recomendado) en ejecución

---

## Configuración

### 1. Base de datos Oracle

Crear el usuario y permisos (ejecutar como `SYSTEM` o SYSDBA):

```sql
CREATE USER REWOW IDENTIFIED BY "123";
GRANT CONNECT TO REWOW;
GRANT RESOURCE TO REWOW;
GRANT CREATE SESSION TO REWOW;
GRANT CREATE TABLE TO REWOW;
GRANT CREATE SEQUENCE TO REWOW;
ALTER USER REWOW QUOTA UNLIMITED ON USERS;
```

### 2. Variables de la aplicación

Editar `src/main/resources/application.properties` según tu entorno:

| Propiedad | Descripción | Valor por defecto |
|-----------|-------------|-------------------|
| `spring.datasource.url` | JDBC URL de Oracle | `jdbc:oracle:thin:@//localhost:1521/XEPDB1` |
| `spring.datasource.username` | Usuario de BD | `REWOW` |
| `spring.datasource.password` | Contraseña | `123` |

**Ejemplos de URL según instalación:**

- Pluggable database (XE 21c): `jdbc:oracle:thin:@//localhost:1521/XEPDB1`
- SID clásico: `jdbc:oracle:thin:@localhost:1521:XE`

### 3. CORS

La API permite peticiones desde cualquier origen (`/api/**`). Configuración en `config/WebConfig.java`. Para producción conviene restringir orígenes.

---

## Ejecutar la aplicación

```bash
# Con Maven
./mvnw spring-boot:run

# O compilar y ejecutar el JAR
./mvnw clean package
java -jar target/testrewow-0.0.1-SNAPSHOT.jar
```

La API queda disponible en **http://localhost:8080**.

---

## Swagger / OpenAPI

Con la aplicación en marcha:

| Recurso | URL |
|---------|-----|
| **Swagger UI** (probar endpoints) | http://localhost:8080/swagger-ui.html |
| **OpenAPI JSON** | http://localhost:8080/v3/api-docs |

Desde Swagger UI puedes ver todos los endpoints, esquemas de request/response y ejecutar peticiones de prueba.

---

## API – Endpoints

Base URL: **http://localhost:8080**

### Mascotas (Pets)

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/pets` | Crear mascota (body: `ownerId`, `name`, `type`, `size`, `description`) |

### Historiales médicos (Medical Records)

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/medical-records` | Crear historial con lista de vacunas |
| GET | `/api/medical-records` | Listar todos los historiales |
| GET | `/api/medical-records/{id}` | Obtener uno por id |
| PUT | `/api/medical-records/{id}` | Actualizar (reemplaza la lista de vacunas) |
| DELETE | `/api/medical-records/{id}` | Eliminar (borra vacunas en cascada) |

La respuesta de medical-records incluye: datos del dueño, nombre y tipo de mascota, tamaño, descripción y lista de vacunas (número, tipo, fecha).

---

## Modelo de datos (resumen)

- **Owner**: id, name, email, password → dueños.
- **Pet**: id, name, type, size, description, owner_id → mascotas (type: Cat, Dog, Fish, Other; size: Small, Medium, Big).
- **MedicalRecord**: id, created_at, updated_at, pet_id (único) → un historial por mascota.
- **Vaccination**: id, number_vaccine, type, date_vaccine, medical_record_id → vacunas de cada historial.

Las tablas se crean/actualizan automáticamente con `spring.jpa.hibernate.ddl-auto=update` al arrancar.

---

## Estructura del proyecto

```
src/main/java/com/rewow/testrewow/
├── config/          # CORS, OpenAPI
├── controller/      # REST (MedicalRecord, Pet)
├── dto/             # Request/Response
├── entity/          # JPA (Owner, Pet, MedicalRecord, Vaccination)
├── exception/       # Manejo de errores (404, 400)
├── repository/      # JpaRepository
├── service/         # Lógica de negocio
└── TestrewowApplication.java
```

---

## Documentación adicional

- Pruebas en Postman y datos de prueba (Owner/Pet con secuencias): `docs/postman-medical-records.md`
- Script de usuario Oracle: `scripts/create-user-rewow.sql`
