--Tabla que esta en el esquema de matriculas y servirñ para almacenar todo tipo de novedades de las tablas que se quieren integrar.
CREATE TABLE NOTAS_INTEGRACION(
  ID         NUMBER(6)                          NOT NULL,
  CATEGORIA  NUMBER(1)                          NOT NULL,
  FUNCION    NUMBER(2)                          NOT NULL,
  FECHA      DATE                               DEFAULT SYSDATE               NOT NULL,
  ESTADO     NUMBER(1)                          DEFAULT 0                     NOT NULL,
  MENSAJE    VARCHAR2(200 BYTE),
  P1         VARCHAR2(200 BYTE),
  P2         VARCHAR2(200 BYTE),
  P3         VARCHAR2(200 BYTE),
  P4         VARCHAR2(200 BYTE),
  P5         VARCHAR2(200 BYTE),
  P6         VARCHAR2(200 BYTE),
  P7         VARCHAR2(200 BYTE),
  P8         VARCHAR2(200 BYTE),
  P9         VARCHAR2(200 BYTE),
  P10        VARCHAR2(200 BYTE),
  P11        VARCHAR2(200 BYTE),
  P12        VARCHAR2(200 BYTE),
  P13        VARCHAR2(200 BYTE),
  P14        VARCHAR2(200 BYTE),
  P15        VARCHAR2(200 BYTE),
  P16        VARCHAR2(200 BYTE),
  P17        VARCHAR2(200 BYTE),
  P18        VARCHAR2(200 BYTE),
  P19        VARCHAR2(200 BYTE),
  P20        VARCHAR2(200 BYTE),
  P21        VARCHAR2(200 BYTE),
  P22        VARCHAR2(200 BYTE),
  P23        VARCHAR2(200 BYTE)
);

--constraint de unicidad   
CREATE UNIQUE INDEX NOTAS_INTEGRACION_PK ON NOTAS_INTEGRACION
(ID, CATEGORIA, FUNCION)
LOGGING
NOPARALLEL;

--llave primaria  
ALTER TABLE NOTAS_INTEGRACION ADD (
  CONSTRAINT NOTAS_INTEGRACION_PK
 PRIMARY KEY
 (ID, CATEGORIA, FUNCION));

--vista basada en la tabla NOTAS_INTEGRACION  
CREATE OR REPLACE VIEW NOTAS_INTEGRACION_ AS 
SELECT 
ID ,
CATEGORIA, 
FUNCION, 
ESTADO, 
FECHA, 
P1, 
P2, 
P3, 
P4, 
P5, 
P6, 
P7, 
P8, 
P9, 
P10, 
P11, 
P12, 
P13, 
P14, 
P15, 
P16, 
P17, 
P18, 
P19, 
P20, 
P21, 
P22, 
P23 
FROM NOTAS_INTEGRACION 
WHERE ESTADO=0 
AND CATEGORIA<>0 
AND FUNCION<>0 
ORDER BY fecha; 

--secuencia para generar la llave primaria
--drop SEQUENCE ID_NOTAS_INTEGRACION
CREATE SEQUENCE ID_NOTAS_INTEGRACION
  START WITH 1
  MAXVALUE 999999999
  MINVALUE 0
  CYCLE
  CACHE 20
  ORDER;
