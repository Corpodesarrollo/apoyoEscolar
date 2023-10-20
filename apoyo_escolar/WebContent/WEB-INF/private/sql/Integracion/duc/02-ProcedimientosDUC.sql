/*<TOAD_FILE_CHUNK>*/
CREATE OR REPLACE PACKAGE PK_INTEGRACIONduc1 AS
   PROCEDURE insertColegio(dane12 IN NUMERIC,dane11 IN NUMERIC,codigo IN NUMERIC,localidad IN NUMERIC,nombre IN VARCHAR,estado IN NUMERIC,tipo IN NUMERIC,numeroR IN VARCHAR,fechaR IN VARCHAR);
   PROCEDURE updateColegio(dane12Old IN NUMERIC,dane11Old IN NUMERIC,dane12New IN NUMERIC,dane11New IN NUMERIC,codigo IN NUMERIC,localidad IN NUMERIC,nombre IN VARCHAR,estado IN NUMERIC,tipo IN NUMERIC,numeroR IN VARCHAR,fechaR IN VARCHAR);
   PROCEDURE deleteColegio(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigo IN NUMERIC);

   PROCEDURE insertSede(dane12 IN NUMERIC,dane11 IN NUMERIC,codigoColegio IN NUMERIC,codigo IN NUMERIC,nombre IN VARCHAR,direccion IN VARCHAR,telefono IN VARCHAR,estado IN NUMERIC);

   PROCEDURE updateSede(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigoOld IN NUMERIC,dane12New IN NUMERIC,dane11New IN NUMERIC,codigoColegio IN NUMERIC,codigoNew IN NUMERIC,nombre IN VARCHAR,direccion IN VARCHAR,telefono IN VARCHAR,estado IN NUMERIC);

   PROCEDURE deleteSede(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigoColegio IN NUMERIC,codigoOld IN NUMERIC);
   
END PK_INTEGRACIONduc1;
/
/*<TOAD_FILE_CHUNK>*/

CREATE OR REPLACE PACKAGE BODY PK_INTEGRACIONduc1 AS

   PROCEDURE insertColegio(dane12 IN NUMERIC,dane11 IN NUMERIC,codigo IN NUMERIC,localidad IN NUMERIC,nombre IN VARCHAR,estado IN NUMERIC,tipo IN NUMERIC,numeroR IN VARCHAR,fechaR IN VARCHAR) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8, P9) VALUES (
		id_Notas_integracion.NEXTVAL,
		1,
		1,
		dane12,dane11,codigo,localidad,nombre,estado,tipo, numeroR,fechaR
		);	
	END insertColegio;  

   PROCEDURE updateColegio(dane12Old IN NUMERIC,dane11Old IN NUMERIC,dane12New IN NUMERIC,dane11New IN NUMERIC,codigo IN NUMERIC,localidad IN NUMERIC,nombre IN VARCHAR,estado IN NUMERIC,tipo IN NUMERIC,numeroR IN VARCHAR,fechaR IN VARCHAR) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8, P9,P10,P11) VALUES (
		id_Notas_integracion.NEXTVAL,
		1,
		2,
		dane12Old,dane11Old,dane12New,dane11New,codigo,localidad,nombre,estado,tipo, numeroR,fechaR
		);	
   END updateColegio;	   
	   
   PROCEDURE deleteColegio(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigo IN NUMERIC) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3) VALUES (
		id_Notas_integracion.NEXTVAL,
		1,
		6,
		dane12Old,dane11Old,codigo
		);	
   END deleteColegio;	   

   PROCEDURE insertSede(dane12 IN NUMERIC,dane11 IN NUMERIC,codigoColegio IN NUMERIC,codigo IN NUMERIC,nombre IN VARCHAR,direccion IN VARCHAR,telefono IN VARCHAR,estado IN NUMERIC) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8) VALUES (
		id_Notas_integracion.NEXTVAL,
		2,
		1,
		dane12,dane11,codigoColegio,codigo,nombre,direccion,telefono,estado
		);	
	END insertSede;

   PROCEDURE updateSede(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigoOld IN NUMERIC,dane12New IN NUMERIC,dane11New IN NUMERIC,codigoColegio IN NUMERIC,codigoNew IN NUMERIC,nombre IN VARCHAR,direccion IN VARCHAR,telefono IN VARCHAR,estado IN NUMERIC) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8,P9,P10,P11) VALUES (
		id_Notas_integracion.NEXTVAL,
		2,
		2,
		dane12Old,dane11Old,codigoOld,dane12New,dane11New,codigoColegio,codigoNew,nombre,direccion,telefono,estado
		);	
	END updateSede;

   PROCEDURE deleteSede(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigoColegio IN NUMERIC,codigoOld IN NUMERIC) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4) VALUES (
		id_Notas_integracion.NEXTVAL,
		2,
		7,
		dane12Old,dane11Old,codigoColegio,codigoOld
		);	
	END deleteSede;
	  
END PK_INTEGRACIONduc1;
/



