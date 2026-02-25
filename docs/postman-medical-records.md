# Probar endpoints de Medical Records en Postman

Base URL (con la app corriendo): **http://localhost:8080**

---

## 1. Datos de prueba (ejecutar en DBeaver antes)

Las tablas usan **secuencias** (no DEFAULT en la columna ID). En un INSERT manual debes dar el valor de `id` con la secuencia. Ejecuta **conectado al schema REWOW** (o anteponiendo `REWOW.` a tabla y secuencia):

```sql
-- Owner (Oracle guarda las secuencias en mayúsculas: owner_seq → OWNER_SEQ)
INSERT INTO OWNER (id, name, email, password)
VALUES (OWNER_SEQ.NEXTVAL, 'Juan Pérez', 'juan@mail.com', 'clave123');

-- Ver el id generado para usarlo en PET (p. ej. 1)
SELECT id FROM OWNER WHERE email = 'juan@mail.com';

-- Pet (columnas en tabla: pet_type, pet_size)
INSERT INTO PET (id, name, pet_type, pet_size, description, owner_id)
VALUES (PET_SEQ.NEXTVAL, 'Firulais', 'Perro', 'Mediano', 'Labrador', 1);

COMMIT;
```

Sustituye el `1` de `owner_id` por el id que devolvió el SELECT (o usa el que tengas).

---

## 2. Endpoints en Postman

### POST – Crear historial médico (con vacunas)

- **Método:** POST  
- **URL:** `http://localhost:8080/api/medical-records`  
- **Headers:** `Content-Type: application/json`  
- **Body (raw, JSON):**

```json
{
  "petId": 1,
  "vaccinations": [
    {
      "numberVaccine": 1,
      "type": "Rabia",
      "dateVaccine": "2025-01-15"
    },
    {
      "numberVaccine": 2,
      "type": "Parvovirus",
      "dateVaccine": "2025-02-01"
    }
  ]
}
```

- Respuesta esperada: **201 Created** con el historial creado (id, createdAt, petId, petName, vaccinations).

---

### GET – Listar todos

- **Método:** GET  
- **URL:** `http://localhost:8080/api/medical-records`  
- Respuesta esperada: **200 OK**, array de historiales.

---

### GET – Obtener uno por id

- **Método:** GET  
- **URL:** `http://localhost:8080/api/medical-records/1`  
- (Sustituye `1` por el id que te devolvió el POST.)  
- Respuesta esperada: **200 OK**, un objeto historial.

---

### PUT – Actualizar (reemplaza la lista de vacunas)

- **Método:** PUT  
- **URL:** `http://localhost:8080/api/medical-records/1`  
- **Headers:** `Content-Type: application/json`  
- **Body (raw, JSON):**

```json
{
  "vaccinations": [
    {
      "numberVaccine": 1,
      "type": "Rabia",
      "dateVaccine": "2025-01-15"
    },
    {
      "numberVaccine": 3,
      "type": "Polivalente",
      "dateVaccine": "2025-03-10"
    }
  ]
}
```

- Respuesta esperada: **200 OK** con el historial actualizado.

---

### DELETE – Eliminar (y sus vacunas en cascada)

- **Método:** DELETE  
- **URL:** `http://localhost:8080/api/medical-records/1`  
- Respuesta esperada: **204 No Content**.

---

## Orden recomendado

1. Arrancar la aplicación Spring Boot.  
2. Insertar Owner y Pet en DBeaver (y anotar el `id` de la mascota).  
3. POST `/api/medical-records` con ese `petId` y la lista de vacunas.  
4. GET `/api/medical-records` y GET `/api/medical-records/1`.  
5. PUT `/api/medical-records/1` para cambiar vacunas.  
6. DELETE `/api/medical-records/1` para borrar.

Si no tienes Owner/Pet, POST devolverá **404** (Pet no encontrado) o **400** (la mascota ya tiene historial).
