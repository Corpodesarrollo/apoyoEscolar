/*<TOAD_FILE_CHUNK>*/
CREATE OR REPLACE PACKAGE Pk_Integracion_Mat_Apoyo AS
   --ASOCIADOS AL COLEGIO
	 PROCEDURE insertColegio(
		dane12 IN NUMERIC,
		dane11 IN NUMERIC,
		LOCALIDAD IN NUMERIC,
		nombre IN VARCHAR,
		estado IN VARCHAR,
		tipo IN VARCHAR,
		zona IN VARCHAR,
		direccion IN VARCHAR);
   PROCEDURE updateColegio(
		dane12Old IN NUMERIC,
		dane11Old IN NUMERIC,
		dane12New IN NUMERIC,
		dane11New IN NUMERIC,
		LOCALIDAD IN NUMERIC,
		nombre IN VARCHAR,
		estado IN VARCHAR,
		tipo IN VARCHAR,
		zona IN VARCHAR,
		direccion IN VARCHAR);
   PROCEDURE deleteColegio(
		dane12Old IN NUMERIC,
		dane11Old IN NUMERIC);

   --ASOCIADOS A LA SEDE
   PROCEDURE insertSede(
		daneColegio IN NUMERIC,
		daneSede IN NUMERIC,
		codigo IN NUMERIC,
		nombre IN VARCHAR,
		direccion IN VARCHAR,
		telefono IN VARCHAR,
		estado IN VARCHAR);
   PROCEDURE updateSede(
		daneColegioOld IN NUMERIC,
		daneColegioNew IN NUMERIC,
		daneSedeOld IN NUMERIC,
		daneSedeNew IN NUMERIC,
		codigoOld IN NUMERIC,
		codigoNew IN NUMERIC,
		nombre IN VARCHAR,
		direccion IN VARCHAR,
		telefono IN VARCHAR,
		estado IN VARCHAR);
   PROCEDURE deleteSede(
		daneColegioOld IN NUMERIC,
		daneSedeOld IN NUMERIC,
		codigoOld IN NUMERIC);
		
		

   --ASOCIADOS AL ESTUDIANTE
	   --Ejecutado por el trigger de insercion de estudiante para calcular la metodologia del colegio  
	PROCEDURE insertAlumno2(
		codigo IN NUMERIC,
		anhovig IN NUMERIC,
		inst IN NUMERIC,
		jorn IN NUMERIC,
		gra IN NUMERIC,
		gru IN NUMERIC,
		dd_exp IN NUMERIC,
		dm_exp IN NUMERIC,
		tipo_Id IN NUMERIC,
		num_Id IN VARCHAR,
		apellido1 IN VARCHAR,
		apellido2 IN VARCHAR,
		nombre1 IN VARCHAR,
		nombre2 IN VARCHAR,
		estado IN NUMERIC,
		genero IN NUMERIC,
		fecha_naci IN VARCHAR,
		dd_naci IN NUMERIC,
		dm_naci IN NUMERIC
	);

	   --Ejecutado por el trigger de actualizacion de ubicacion de estudiante para calcular la metodologia del colegio anterior y nuevo  
	 	PROCEDURE updateAlumno2(
		codigo IN NUMERIC,
		anhovig IN NUMERIC,
		inst IN NUMERIC,
		jorn IN NUMERIC,
		gra IN NUMERIC,
		gru IN NUMERIC,
		tipo_Id IN NUMERIC,
		num_Id IN VARCHAR,
		estado IN NUMERIC,
		instOld IN NUMERIC,
		jornOld IN NUMERIC,
		graOld IN NUMERIC,
		gruOld IN NUMERIC
	);

	   --Ejecutado por el trigger de actualizacion de identificacion de estudiante para calcular la metodologia del colegio anterior y nuevo   
 	PROCEDURE updateIdAlumno2(
		codigo IN NUMERIC,
		anhovig IN NUMERIC,
		tipo_IdNew IN NUMERIC,
		num_IdNew IN VARCHAR,
		apellido1 IN VARCHAR,
		apellido2 IN VARCHAR,
		nombre1 IN VARCHAR,
		nombre2 IN VARCHAR,
		estado IN NUMERIC,
		dd_exp IN NUMERIC,
		dm_exp IN NUMERIC,
		genero IN NUMERIC
	);
	
END Pk_Integracion_Mat_Apoyo;