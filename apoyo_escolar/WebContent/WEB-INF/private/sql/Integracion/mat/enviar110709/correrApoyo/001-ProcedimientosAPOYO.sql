CREATE OR REPLACE PACKAGE Pk_Integracion AS
	--Ingresa el alumno basado en los parametros enviados  	
	PROCEDURE insertAlumno(
		codigo  IN NUMERIC,
		anhovig  IN NUMERIC,
		daneInst IN NUMERIC,
		SEDE IN NUMERIC,
		jor IN NUMERIC,
		met IN NUMERIC,
		gra IN NUMERIC,
		gru IN NUMERIC,
		tipoId IN NUMERIC,
		numId IN VARCHAR,
		ape1 IN VARCHAR,
		ape2 IN VARCHAR,
		nom1 IN VARCHAR,
		nom2 IN VARCHAR,
		deptoNaci IN NUMERIC,
		munNaci IN NUMERIC,
		genero IN NUMERIC,
		fechaNaci IN VARCHAR,
		deptoExp IN NUMERIC,
		munExp IN NUMERIC,
		estado IN NUMERIC,
		nomGrupo IN VARCHAR,
		id IN NUMERIC
	);
	
	--actualiza la ubicacion del alumno basado en los parametros de ingreso  
	PROCEDURE updateAlumno(
		codigo  IN NUMERIC,
		anhovig  IN NUMERIC,
		daneInst IN NUMERIC,
		SEDE IN NUMERIC,
		jor IN NUMERIC,
		met IN NUMERIC,
		gra IN NUMERIC,
		gru IN NUMERIC,
		daneInstOld IN NUMERIC,
		sedeOld IN NUMERIC,
		jorOld IN NUMERIC,
		metOld IN NUMERIC,
		graOld IN NUMERIC,
		gruOld IN NUMERIC,
		tipoId IN NUMERIC,
		numId IN VARCHAR,
		estado IN NUMERIC,
		nomGrupo IN VARCHAR,
		id IN NUMERIC
	);
	
	--actualiza la identificacion del alumno basado en los parametros de ingreso  
	PROCEDURE updateIdAlumno(
		id IN NUMERIC,
		codigo  IN NUMERIC,
		anhovig  IN NUMERIC,
		tipoId IN NUMERIC,
		numId IN VARCHAR,
		apellido1 IN VARCHAR,
		apellido2 IN VARCHAR,
		nombre1 IN VARCHAR,
		nombre2 IN VARCHAR,
		estado IN NUMERIC,
		deptoExp IN NUMERIC,
		munExp IN NUMERIC,
		genero IN NUMERIC
	);


	--Actualiza la sede basado en los parametros enviados  
	PROCEDURE updateSede(
	daneOld IN NUMERIC,
	daneNew IN NUMERIC,
	daneSedeOld IN NUMERIC,
	daneSedeNew IN NUMERIC,
	sedeOld IN NUMERIC,
	sedeNew IN NUMERIC,
	nombre IN VARCHAR,
	direccion IN VARCHAR,
	telefono IN VARCHAR,
	estado IN VARCHAR
	);
	
END Pk_Integracion;