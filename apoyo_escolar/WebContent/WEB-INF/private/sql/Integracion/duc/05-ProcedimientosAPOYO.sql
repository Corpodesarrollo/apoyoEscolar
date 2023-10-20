/*<TOAD_FILE_CHUNK>*/
CREATE OR REPLACE PACKAGE PK_INTEGRACIONduc2 AS
   PROCEDURE insertColegio(dane12 IN NUMERIC,dane11 IN NUMERIC,codigo IN NUMERIC,localidad IN NUMERIC,nombre IN VARCHAR,estado IN NUMERIC,tipo IN NUMERIC,numeroR IN VARCHAR,fechaR IN VARCHAR);
   PROCEDURE updateColegio(dane12Old IN NUMERIC,dane11Old IN NUMERIC,dane12New IN NUMERIC,dane11New IN NUMERIC,codigo IN NUMERIC,localidad IN NUMERIC,nombre IN VARCHAR,estado IN NUMERIC,tipo IN NUMERIC,numeroR IN VARCHAR,fechaR IN VARCHAR);
   PROCEDURE deleteColegio(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigo IN NUMERIC);
   PROCEDURE insertSede(dane12 IN NUMERIC,dane11 IN NUMERIC,codigoColegio IN NUMERIC,codigo IN NUMERIC,nombre IN VARCHAR,direccion IN VARCHAR,telefono IN VARCHAR,estado IN NUMERIC);
   PROCEDURE updateSede(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigoOld IN NUMERIC,dane12New IN NUMERIC,dane11New IN NUMERIC,codigoColegio IN NUMERIC,codigoNew IN NUMERIC,nombre IN VARCHAR,direccion IN VARCHAR,telefono IN VARCHAR,estado IN NUMERIC);
   PROCEDURE deleteSede(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigoColegio IN NUMERIC,codigoOld IN NUMERIC);
   
END PK_INTEGRACIONduc2;
/

/*<TOAD_FILE_CHUNK>*/

CREATE OR REPLACE PACKAGE BODY PK_INTEGRACIONduc2 AS

   PROCEDURE insertColegio(dane12 IN NUMERIC,dane11 IN NUMERIC,codigo IN NUMERIC,localidad IN NUMERIC,nombre IN VARCHAR,estado IN NUMERIC,tipo IN NUMERIC,numeroR IN VARCHAR,fechaR IN VARCHAR) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
	END insertColegio;  

   PROCEDURE updateColegio(dane12Old IN NUMERIC,dane11Old IN NUMERIC,dane12New IN NUMERIC,dane11New IN NUMERIC,codigo IN NUMERIC,localidad IN NUMERIC,nombre IN VARCHAR,estado IN NUMERIC,tipo IN NUMERIC,numeroR IN VARCHAR,fechaR IN VARCHAR) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
   END updateColegio;	   
	   
   PROCEDURE deleteColegio(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigo IN NUMERIC) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
   END deleteColegio;	   

   PROCEDURE insertSede(dane12 IN NUMERIC,dane11 IN NUMERIC,codigoColegio IN NUMERIC,codigo IN NUMERIC,nombre IN VARCHAR,direccion IN VARCHAR,telefono IN VARCHAR,estado IN NUMERIC) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
	END insertSede;

   PROCEDURE updateSede(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigoOld IN NUMERIC,dane12New IN NUMERIC,dane11New IN NUMERIC,codigoColegio IN NUMERIC,codigoNew IN NUMERIC,nombre IN VARCHAR,direccion IN VARCHAR,telefono IN VARCHAR,estado IN NUMERIC) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
	END updateSede;

   PROCEDURE deleteSede(dane12Old IN NUMERIC,dane11Old IN NUMERIC,codigoColegio IN NUMERIC,codigoOld IN NUMERIC) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
	END deleteSede;
	  
END PK_INTEGRACIONduc2;
/



