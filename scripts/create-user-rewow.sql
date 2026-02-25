-- Ejecutar como SYSTEM o SYSDBA en DBeaver (cada sentencia por separado si falla el bloque).
-- Opción 1: Solo usuario y permisos (Oracle asigna tablespace por defecto)
CREATE USER REWOW IDENTIFIED BY "123";

GRANT CONNECT TO REWOW;
GRANT RESOURCE TO REWOW;
GRANT CREATE SESSION TO REWOW;
GRANT CREATE TABLE TO REWOW;
GRANT CREATE SEQUENCE TO REWOW;

-- Opción 2: Si quieres asignar tablespace y cuota, ejecuta después de lo anterior (y si existen USERS y TEMP):
-- ALTER USER REWOW DEFAULT TABLESPACE USERS;
-- ALTER USER REWOW TEMPORARY TABLESPACE TEMP;
-- ALTER USER REWOW QUOTA UNLIMITED ON USERS;
