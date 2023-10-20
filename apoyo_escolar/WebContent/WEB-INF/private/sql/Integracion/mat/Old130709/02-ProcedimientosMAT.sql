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
		fecha_naci IN DATE,
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
		tipo_IdOld IN NUMERIC,
		num_IdOld IN VARCHAR,
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
/
/*<TOAD_FILE_CHUNK>*/

--CUERPO DEL PAQUETE  
CREATE OR REPLACE PACKAGE BODY Pk_Integracion_Mat_Apoyo AS

   PROCEDURE insertColegio(
		dane12 IN NUMERIC,
		dane11 IN NUMERIC,
		LOCALIDAD IN NUMERIC,
		nombre IN VARCHAR,
		estado IN VARCHAR,
		tipo IN VARCHAR,
		zona IN VARCHAR,
		direccion IN VARCHAR
	) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8) VALUES (
		id_Notas_integracion.NEXTVAL,
		1,
		1,
		dane12,dane11,LOCALIDAD,nombre,estado,tipo, zona,direccion
		);	
	END insertColegio;  

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
		direccion IN VARCHAR	 
	 ) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8, P9,P10) VALUES (
		id_Notas_integracion.NEXTVAL,
		1,
		2,
		dane12Old,dane11Old,dane12New,dane11New,LOCALIDAD,nombre,estado,tipo, zona,direccion
		);	
   END updateColegio;	   
	   
   PROCEDURE deleteColegio(
		dane12Old IN NUMERIC,
		dane11Old IN NUMERIC
	) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2) VALUES (
		id_Notas_integracion.NEXTVAL,
		1,
		6,
		dane12Old,dane11Old
		);	
   END deleteColegio;	   

   PROCEDURE insertSede(
		daneColegio IN NUMERIC,
		daneSede IN NUMERIC,
		codigo IN NUMERIC,
		nombre IN VARCHAR,
		direccion IN VARCHAR,
		telefono IN VARCHAR,
		estado IN VARCHAR
	) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7) VALUES (
		id_Notas_integracion.NEXTVAL,
		2,
		1,
		daneColegio,daneSede,codigo,nombre,direccion,telefono,estado
		);	
	END insertSede;

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
		estado IN VARCHAR
	) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8,P9,P10) VALUES (
		id_Notas_integracion.NEXTVAL,
		2,
		2,
		daneColegioOld,daneColegioNew,daneSedeOld,daneSedeNew,codigoOld,codigoNew,nombre,direccion,telefono,estado
		);	
	END updateSede;

   PROCEDURE deleteSede(
		daneColegioOld IN NUMERIC,
		daneSedeOld IN NUMERIC,
		codigoOld IN NUMERIC	 
	 ) IS
	   cont NUMBER(2);
	   BEGIN
	   cont:=0;
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3) VALUES (
		id_Notas_integracion.NEXTVAL,
		2,
		7,
		daneColegioOld,daneSedeOld,codigoOld
		);	
	END deleteSede;

	
 	PROCEDURE updateIdAlumno2(
		codigo IN NUMERIC,
		anhovig IN NUMERIC,
		tipo_IdOld IN NUMERIC,
		num_IdOld IN VARCHAR,
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
	) IS
 		cont NUMBER(2);
		anhoreal NUMBER(4);
	 BEGIN
	    cont:=0;
		--calcular el año real 
		SELECT TO_NUMBER(VALOR) INTO anhoreal FROM NOTAS_PARAMETRO WHERE NOMBRE='VIGENCIA';
		IF anhovig=anhoreal THEN
			--insertar el registro realmente a la tabla NOTAS_INTEGRACION    
			INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14) VALUES (
			 id_Notas_integracion.NEXTVAL,
			 7,
			 5,
			 codigo,
			 anhovig,
			 tipo_IdOld,
			 num_IdOld,
			 tipo_IdNew,
			 num_IdNew,
			 apellido1,
			 apellido2,
			 nombre1,
			 nombre2,
			 estado,
			 dd_exp,
			 dm_exp,
			 genero
			);		  
		END IF;		
    END updateIdAlumno2;  

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
		fecha_naci IN DATE,
		dd_naci IN NUMERIC,
		dm_naci IN NUMERIC
	)IS
  	cont NUMBER(2);
		metod NUMBER(2);
		consSede NUMBER(18);
		sede NUMBER(3);
		anho NUMBER(4);
		grupo VARCHAR(10);
		anhoreal NUMBER(4);
    BEGIN
	    cont:=0;
			metod:=1;
	    sede:=0;
		--calcular el año real 
		SELECT TO_NUMBER(VALOR) INTO anhoreal FROM NOTAS_PARAMETRO WHERE NOMBRE='VIGENCIA';
		IF anhovig=anhoreal THEN  
			--calcular la metodologia del colegio  
			SELECT COUNT(METODOLOGIA) INTO cont FROM INST_JORN_GRAD_METO WHERE IN_CODI_ID=inst AND JR_CODI_ID=jorn AND GR_CODI_ID=gra;
			IF cont<>0 THEN
				SELECT MIN(METODOLOGIA) INTO metod FROM INST_JORN_GRAD_METO WHERE IN_CODI_ID=inst AND JR_CODI_ID=jorn AND GR_CODI_ID=gra;		
			END IF;
			--calcular la sede del colegio 
			cont:=0;      
			SELECT COUNT(IG_CONS_SEDE) INTO cont FROM INST_GRUP WHERE IN_CODI_ID=inst AND JR_CODI_ID=jorn AND GR_CODI_ID=gra AND GP_CODI_ID=gru  AND ig_ao=anhoreal AND  IG_CONS_SEDE IS NOT NULL;
			IF cont<>0 THEN
				SELECT IG_CONS_SEDE,IG_GRUP INTO consSede,grupo
				FROM INST_GRUP
				WHERE IN_CODI_ID=inst
				AND JR_CODI_ID=jorn
				AND GR_CODI_ID=gra
				AND GP_CODI_ID=gru
 				AND ig_ao=anhoreal
				AND IG_CONS_SEDE IS NOT NULL;
				sede:=CASE LENGTH(consSede) WHEN 12 THEN (TO_NUMBER(SUBSTR(LPAD(consSede,12,0),12,1))) WHEN 13 THEN (TO_NUMBER(SUBSTR(LPAD(consSede,13,0),12,2))) WHEN 14 THEN (TO_NUMBER(SUBSTR(LPAD(consSede,14,0),13,2))) END;
			END IF;
			--insertar el registro realmente a la tabla NOTAS_INTEGRACION    
			INSERT INTO NOTAS_INTEGRACION (CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15,P16,P17,P18,P19,P20,P21,P22,ID) VALUES ( 
			7, 
			1, 
			codigo,
			anhovig,
			inst,
			sede,
			jorn,
			gra,
			gru,
			metod,
			tipo_Id,
			num_Id,
			apellido1,
			apellido2,
			nombre1,
			nombre2,
			estado,
			genero,
			fecha_naci,
			dd_naci, 
			dm_naci,
			dd_exp, 
			dm_exp,
			grupo,
			id_Notas_integracion.NEXTVAL);
		END IF;	
    END insertAlumno2;  

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
	)IS
  	    cont NUMBER(2);
		metod NUMBER(2);
		metodOld NUMBER(2);		
		sede NUMBER(3);
		sedeOld NUMBER(3);
		consSede NUMBER(18);
		consSedeOld NUMBER(18);
		anho NUMBER(4);
		grupo VARCHAR(10);
		anhoreal NUMBER(4);
    BEGIN
	    cont:=0;
		metod:=0;
	    sede:=0;
		sedeOld:=0;
		--calcular el año real 
		SELECT TO_NUMBER(VALOR) INTO anhoreal FROM NOTAS_PARAMETRO WHERE NOMBRE='VIGENCIA';
		IF anhovig=anhoreal THEN  
			--calcular la metodologia del colegio nuevo  
			SELECT COUNT(METODOLOGIA) INTO cont FROM INST_JORN_GRAD_METO WHERE IN_CODI_ID=inst AND JR_CODI_ID=jorn AND GR_CODI_ID=gra;
			IF cont<>0 THEN
				SELECT MIN(METODOLOGIA) INTO metod FROM INST_JORN_GRAD_METO WHERE IN_CODI_ID=inst AND JR_CODI_ID=jorn AND GR_CODI_ID=gra;		
			END IF;
			--calcular la metodologia del colegio anterior   
			SELECT COUNT(METODOLOGIA) INTO cont FROM INST_JORN_GRAD_METO WHERE IN_CODI_ID=instOld AND JR_CODI_ID=jornOld AND GR_CODI_ID=graOld;
			IF cont<>0 THEN
				SELECT MIN(METODOLOGIA) INTO metodOld FROM INST_JORN_GRAD_METO WHERE IN_CODI_ID=instOld AND JR_CODI_ID=jornOld AND GR_CODI_ID=graOld;		
			END IF;
			--calcular la sede del colegio  nuevo 
			cont:=0;      
			SELECT COUNT(IG_CONS_SEDE) INTO cont FROM INST_GRUP WHERE IN_CODI_ID=inst AND JR_CODI_ID=jorn AND GR_CODI_ID=gra AND GP_CODI_ID=gru AND ig_ao=anhoreal AND IG_CONS_SEDE IS NOT NULL;
			IF cont<>0 THEN
				SELECT IG_CONS_SEDE,IG_GRUP INTO consSede,grupo
				FROM INST_GRUP
				WHERE IN_CODI_ID=inst
				AND JR_CODI_ID=jorn
				AND GR_CODI_ID=gra
				AND GP_CODI_ID=gru
				AND ig_ao=anhoreal
				AND IG_CONS_SEDE IS NOT NULL;
				sede:=CASE LENGTH(consSede) WHEN 12 THEN (TO_NUMBER(SUBSTR(LPAD(consSede,12,0),12,1))) WHEN 13 THEN (TO_NUMBER(SUBSTR(LPAD(consSede,13,0),12,2)))  WHEN 14 THEN (TO_NUMBER(SUBSTR(LPAD(consSede,14,0),13,2))) END;
			ELSE
				consSede:=0;
				grupo:='0';
				sede:=0;
			END IF;
			--calcular la sede del colegio anterior    
			cont:=0;      
			SELECT COUNT(IG_CONS_SEDE) INTO cont FROM INST_GRUP WHERE IN_CODI_ID=instOld AND JR_CODI_ID=jornOld AND GR_CODI_ID=graOld AND GP_CODI_ID=gruOld AND ig_ao=anhoreal AND IG_CONS_SEDE IS NOT NULL;
			IF cont<>0 THEN
				SELECT IG_CONS_SEDE INTO consSedeOld
				FROM INST_GRUP
				WHERE IN_CODI_ID=instOld
				AND JR_CODI_ID=jornOld
				AND GR_CODI_ID=graOld
				AND GP_CODI_ID=gruOld
				AND ig_ao=anhoreal
				AND IG_CONS_SEDE IS NOT NULL;
				sedeOld:=CASE LENGTH(consSedeOld) WHEN 12 THEN (TO_NUMBER(SUBSTR(LPAD(consSedeOld,12,0),12,1))) WHEN 13 THEN (TO_NUMBER(SUBSTR(LPAD(consSedeOld,13,0),12,2)))  WHEN 14 THEN (TO_NUMBER(SUBSTR(LPAD(consSedeOld,14,0),13,2))) END;
			ELSE
				consSedeOld:=0;
				sedeOld:=0;
			END IF;
			--insertar el registro realmente a la tabla NOTAS_INTEGRACION    
			INSERT INTO NOTAS_INTEGRACION (CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18,ID) VALUES ( 
			7, 
			7, 
			codigo,
			anhovig,
			inst,
			sede,
			jorn,
			gra,
			gru,
			metod,
			tipo_Id,
			num_Id,
			estado,
			instOld,
			sedeOld,
			jornOld,
			graOld,
			gruOld,
			metodOld,
			grupo,
			id_Notas_integracion.NEXTVAL
			);
		END IF;			
    END updateAlumno2;  
END Pk_Integracion_Mat_Apoyo;
/
