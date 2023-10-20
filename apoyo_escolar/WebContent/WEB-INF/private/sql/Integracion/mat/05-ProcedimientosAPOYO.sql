/*<TOAD_FILE_CHUNK>*/
--PROCEDIMIENTO ALMACENADO DEL LADO DEL ESQUEMA DE DATOS DE APOYO ESCOLAR 
--UTILIZADO PARA EJECUTAR ALGUNAS TRANSACCIONES QUE POR SU COMPLEJIDAD 
--SE DEBEN EJECUTAR POR PL-SQL Y NOSE PUEDEN EHJJECUTAR CON SENTENCIAS DESDE LA APLICACION  
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
/
/*<TOAD_FILE_CHUNK>*/


CREATE OR REPLACE PACKAGE BODY Pk_Integracion AS
	   --Validado 	   
   PROCEDURE updateSede(daneOld IN NUMERIC,	daneNew IN NUMERIC,	daneSedeOld IN NUMERIC,	daneSedeNew IN NUMERIC,	sedeOld IN NUMERIC,	sedeNew IN NUMERIC,	nombre IN VARCHAR,	direccion IN VARCHAR,telefono IN VARCHAR, estado IN VARCHAR) IS
	   codInstOld NUMBER(12);
	   codInstNew NUMBER(12);
	   munOld NUMBER(5);
	   munNew NUMBER(5);
	   locOld NUMBER(5);
	   locNew NUMBER(5);
	   band  NUMBER(1);
		CURSOR cursor1 IS SELECT INSCODMUN,INSCODLOCAL,inscodigo FROM INSTITUCION WHERE inscoddane=daneOld;
		CURSOR cursor2 IS SELECT INSCODMUN,INSCODLOCAL,inscodigo FROM INSTITUCION WHERE inscoddane=daneNew;
		CURSOR cursor3 IS
			SELECT G_JERDEPTO,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO 
			FROM G_JERARQUIA 
			WHERE G_JERINST=codInstOld 
			AND G_JERSEDE=sedeOld 
			AND G_JERTIPO=1 
			AND G_JERNIVEL=7 
			AND (G_JERMETOD,G_JERGRADO) 
			NOT IN(SELECT GRACODMETOD,GRACODIGO 
			FROM GRADO WHERE GRACODINST=codInstNew);
	   
	    BEGIN
	    codInstOld:=0;
	    codInstNew:=0;
		band:=0;
				
		FOR rec IN cursor1 LOOP
		   munOld:=rec.INSCODMUN;
		   locOld:=rec.INSCODLOCAL;
		   codInstOld:=rec.inscodigo;
		END LOOP;
		FOR rec IN cursor2 LOOP
		   munNew:=rec.INSCODMUN;
		   locNew:=rec.INSCODLOCAL;
		   codInstNew:=rec.inscodigo;
		END LOOP;
		-- CASO 1: MODIFICACION DEL DANE DE LA INSTITUCION    
		IF daneOld<>daneNew THEN 
		   --insertar las jornadas que no esten en la nueva inst  
			INSERT INTO JORNADA(JORCODINS,JORCODIGO)
			SELECT codInstNew AS inst,SEDJORCODJOR 
				   FROM SEDE_JORNADA 
				   WHERE SEDJORCODINST=codInstOld 
				   AND SEDJORCODSEDE=sedeOld 
				   AND SEDJORCODJOR NOT IN(
				   	   SELECT JORCODIGO FROM JORNADA WHERE JORCODINS=codInstNew);
			--insertar las metodologias que no esten en la nueva inst
			INSERT INTO METODOLOGIA(METCODINST,METCODIGO)
			SELECT DISTINCT codInstNew AS inst, G_JERMETOD 
			FROM G_JERARQUIA 
			WHERE G_JERINST=codInstOld 
			AND G_JERSEDE=sedeOld 
			AND G_JERTIPO=1 
			AND G_JERNIVEL=7 
			AND G_JERMETOD NOT IN(SELECT METCODIGO FROM METODOLOGIA WHERE METCODINST=codInstNew);
			--Insertar los grados que no estan en la nueva inst		
			INSERT INTO GRADO(GRACODINST,GRACODMETOD,GRACODIGO,GRANOMBRE)
				SELECT DISTINCT codInstNew AS inst, G_JERMETOD,G_JERGRADO,G_GRANOMBRE 
				FROM G_JERARQUIA,G_GRADO 
				WHERE G_JERINST=codInstOld 
				AND G_JERSEDE=sedeOld 
				AND G_JERTIPO=1 
				AND G_JERNIVEL=7 
				AND G_GRACODIGO=G_JERGRADO
				AND (G_JERMETOD,G_JERGRADO) 
				NOT IN(SELECT GRACODMETOD,GRACODIGO 
				FROM GRADO 
				WHERE GRACODINST=codInstNew);
			--Insertar los grados en jerarquia que no estan en la nueva institucion     
			FOR rec IN cursor3 LOOP
			   INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO)
			   VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,7,
			   rec.G_JERDEPTO,munNew,locNew,codInstNew,rec.G_JERSEDE,rec.G_JERJORN,rec.G_JERMETOD,rec.G_JERGRADO);		
			END LOOP;
			
			UPDATE SEDE SET SEDCODINS=codInstNew,SEDCODIGO=sedeNew,sednombre=nombre, sedestado=estado,SEDDIRECCION=direccion,SEDTELEFONO=telefono WHERE SEDCODINS=codInstOld AND SEDCODIGO=sedeOld;
			UPDATE G_JERARQUIA SET G_JerMunic=munNew,G_JerLocal=locNew,G_JerInst=codInstNew,G_JERSEDE=sedeNew WHERE G_JerInst=codInstOld AND G_JERSEDE=sedeOld;
			UPDATE SEDE_JORNADA SET SEDJORCODINST=codInstNew,SEDJORCODSEDE=sedeNew WHERE SEDJORCODINST=codInstOld AND SEDJORCODSEDE=sedeOld;
			UPDATE SEDE_JORNADA_NIVEL SET SEDJORNIVCODINST=codInstNew,SEDJORNIVCODSEDE=sedeNew WHERE SEDJORNIVCODINST=codInstOld AND SEDJORNIVCODSEDE=sedeOld;
			UPDATE SEDE_JORNADA_ESPACIOFISICO SET SEDJORESPCODINST=codInstNew,SEDJORESPCODSEDE=sedeNew WHERE SEDJORESPCODINST=codInstOld AND SEDJORESPCODSEDE=sedeOld;
			UPDATE DOCENTE_SEDE_JORNADA SET DOCSEDJORCODINST=codInstNew, DOCSEDJORCODSEDE=sedeNew WHERE DOCSEDJORCODINST=codInstOld AND DOCSEDJORCODSEDE=sedeOld;
	
			INSERT INTO HISTORICO_NOVEDAD_INSTITUCION(HISNOVINSCODIGO,HISNOVINSTIPO,HISNOVINSINSCODIGO1,HISNOVINSINSCODIGO2,HISNOVINSCODIGO1, HISNOVINSCODIGO2) VALUES 
			(seq_his_nov_inst.NEXTVAL,6,codInstOld,codInstNew,sedeOld,sedeNew);
			
			INSERT INTO HISTORICO_NOVEDAD_ESTUDIANTE (HISNOVESTFECHA, HISNOVESTCODIGO, HISNOVESTTIPO, HISNOVESTINST1, HISNOVESTINST2, HISNOVESTSEDE1, HISNOVESTSEDE2, HISNOVESTJORN1, HISNOVESTJORN2, HISNOVESTMETOD1, HISNOVESTMETOD2, HISNOVESTGRADO1, HISNOVESTGRADO2, HISNOVESTGRUPO1, HISNOVESTGRUPO2, HISNOVESTSEDENOMBRE, HISNOVESTESTCODIGO) 
			SELECT SYSDATE AS fecha,seq_his_nov_est.NEXTVAL AS consecutivo,
			7 AS tipo,codInstOld AS oldinstitucion,G_JERINST,sedeOld AS OLDSEDE,G_JERSEDE,G_JERJORN,G_JERJORN,G_JERMETOD,G_JERMETOD,G_JERGRADO,G_JERGRADO,G_JERGRUPO,G_JERGRUPO,nombre AS nombredese,ESTCODIGO 
			FROM ESTUDIANTE,  G_JERARQUIA 
			WHERE ESTGRUPO=G_JERCODIGO 
			AND G_JERTIPO=1 
			AND G_JERNIVEL=8 
			AND G_JERINST=codInstNew 
			AND G_JERSEDE=sedeNew;
		
		END IF;
		--CASO 2: MODIFICACION DEL CONSECUTIVO DE LA SEDE   	
		IF daneOld=daneNew AND sedeOld<>sedeNew THEN
		    UPDATE SEDE SET SEDCODIGO=sedeNew,SEDCODDANEANTERIOR=daneSedeNew,sednombre=nombre, sedestado=estado,SEDDIRECCION=direccion,SEDTELEFONO=telefono WHERE SEDCODINS=codInstOld AND SEDCODIGO=sedeOld;
			UPDATE G_JERARQUIA SET G_JERSEDE=sedeNew WHERE G_JERTIPO=1 AND G_JerInst=codInstOld AND G_JERSEDE=sedeOld;
			UPDATE SEDE_JORNADA SET SEDJORCODSEDE=sedeNew WHERE SEDJORCODINST=codInstOld AND SEDJORCODSEDE=sedeOld;
			UPDATE SEDE_JORNADA_NIVEL SET SEDJORNIVCODSEDE=sedeNew WHERE SEDJORNIVCODINST=codInstOld AND SEDJORNIVCODSEDE=sedeOld;
			UPDATE SEDE_JORNADA_ESPACIOFISICO SET SEDJORESPCODSEDE=sedeNew WHERE SEDJORESPCODINST=codInstOld AND SEDJORESPCODSEDE=sedeOld;
			UPDATE DOCENTE_SEDE_JORNADA SET DOCSEDJORCODSEDE=sedeNew WHERE DOCSEDJORCODINST=codInstOld AND DOCSEDJORCODSEDE=sedeOld;
	
			INSERT INTO HISTORICO_NOVEDAD_INSTITUCION(HISNOVINSCODIGO,HISNOVINSTIPO,HISNOVINSINSCODIGO1,HISNOVINSINSCODIGO2,HISNOVINSCODIGO1, HISNOVINSCODIGO2, HISNOVINSNOMBRE1, HISNOVINSNOMBRE2) 
			VALUES (seq_his_nov_inst.NEXTVAL,5,codInstOld,NULL,sedeOld,sedeNew,NULL,NULL);
		END IF;
		--CASO 3: MODIFICACION DEL DANE DE LA SEDE	
		IF daneOld=daneNew AND sedeOld=sedeNew AND daneSedeOld<>daneSedeNew THEN
		   UPDATE SEDE SET SEDCODDANEANTERIOR=daneSedeNew,sednombre=nombre, sedestado=estado,SEDDIRECCION=direccion,SEDTELEFONO=telefono WHERE SEDCODINS=codInstOld AND SEDCODIGO=sedeOld;
		   INSERT INTO HISTORICO_NOVEDAD_INSTITUCION(HISNOVINSCODIGO,HISNOVINSTIPO,HISNOVINSINSCODIGO1,HISNOVINSINSCODIGO2,HISNOVINSCODIGO1, HISNOVINSCODIGO2, HISNOVINSNOMBRE1, HISNOVINSNOMBRE2) 
		   VALUES (seq_his_nov_inst.NEXTVAL,4,codInstOld,NULL,daneSedeOld,daneSedeNew,NULL,NULL);
		END IF;
		--CASO 4: MODIFICACION DEL NOMBRE DE LA SEDE	
		IF daneOld=daneNew AND sedeOld=sedeNew AND daneSedeOld=daneSedeNew THEN
		   UPDATE SEDE SET sednombre=nombre, sedestado=estado,SEDDIRECCION=direccion,SEDTELEFONO=telefono WHERE SEDCODINS=codInstOld AND SEDCODIGO=sedeOld;
		   INSERT INTO HISTORICO_NOVEDAD_INSTITUCION(HISNOVINSCODIGO,HISNOVINSTIPO,HISNOVINSINSCODIGO1,HISNOVINSINSCODIGO2,HISNOVINSCODIGO1, HISNOVINSCODIGO2, HISNOVINSNOMBRE1, HISNOVINSNOMBRE2) 
		   VALUES (seq_his_nov_inst.NEXTVAL,4,codInstOld,NULL,daneSedeOld,daneSedeNew,NULL,NULL);
		END IF;
		COMMIT;	
  END updateSede;  

	PROCEDURE updateAlumno(
		codigo  IN NUMERIC,
		anhovig  IN NUMERIC,
		daneInst IN NUMERIC,
		SEDE IN NUMERIC,
		jor IN NUMERIC,
		met IN NUMERIC,
		gra IN NUMERIC,
		gru IN NUMERIC,
		daneInstOld IN NUMERIC,--INSTITUCION QUE TIENE EN MATRICULAS 
		sedeOld IN NUMERIC,--SEDE QUE TIENE EN MATRICULAS 
		jorOld IN NUMERIC,--JORNADA QUE TIENE EN MATRICULAS 
		metOld IN NUMERIC,--METODOLOGIA QUE TIENE EN MATRICULAS 
		graOld IN NUMERIC,--GRADO QUE TIENE EN MATRICULAS 
		gruOld IN NUMERIC,--GRUPO QUE TIENE EN MATRICULAS 
		tipoId IN NUMERIC,
		numId IN VARCHAR,
		estado IN NUMERIC,
		nomGrupo IN VARCHAR,
		id IN NUMERIC
	) IS
  		codInst NUMBER(12);
		codSede NUMBER(12);
		deptoInst NUMBER(5);
  		munInst NUMBER(5);
   		locInst NUMBER(5);
		contador NUMBER(5);
		graNom VARCHAR(50);
		jerGrado NUMBER(12);
		jerGrupo NUMBER(12);
		band NUMBER(1);
	    oldInst NUMBER(12);--INSTITUCION CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		oldSede NUMBER(12);--SEDE CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		oldJor NUMBER(2);--JORNADA CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		oldMet NUMBER(2);--METODOLOGIA CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		oldGra NUMBER(2);--GRADO CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		oldGru NUMBER(2);--GRUPO CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		tipo NUMBER(1);
		CURSOR cursor1 IS SELECT insCodDepto,INSCODMUN,INSCODLOCAL,inscodigo FROM INSTITUCION WHERE inscoddane=daneInst;
		CURSOR cursor2 IS SELECT G_JERINST, G_JERSEDE, G_JERJORN, G_JERMETOD,G_JERGRADO,G_JERGRUPO FROM G_JERARQUIA,ESTUDIANTE WHERE G_JERCODIGO=ESTGRUPO AND ESTTIPODOC=tipoId AND ESTNUMDOC=numId;
	    BEGIN
		    codInst:=0;
			codSede:=0;
			deptoInst:=0;
			munInst:=0;
			locInst:=0;
			band:=0;
			jerGrado:=0;
			jerGrupo:=0;
		    oldInst:=0;
			oldSede:=0;
			oldJor:=0;
			oldMet:=0;
			oldGra:=0;
			oldGru:=0;
			tipo:=0;
			FOR rec IN cursor1 LOOP
			   deptoInst:=rec.insCodDepto;
			   munInst:=rec.INSCODMUN;
			   locInst:=rec.INSCODLOCAL;
			   codInst:=rec.inscodigo;
			END LOOP;
			FOR rec IN cursor2 LOOP
			    oldInst:=rec.G_JERINST;
				oldSede:=rec.G_JERSEDE;
				oldJor:=rec.G_JERJORN;
				oldMet:=rec.G_JERMETOD;
				oldGra:=rec.G_JERGRADO;
				oldGru:=rec.G_JERGRUPO;
			END LOOP;
			IF codInst=0 THEN
			 	INSERT INTO INTEGRACION_LOG (ID, TIPO,MENSAJE)VALUES (id,2,'No hay Instituciñn registrada');
				RETURN;
			END IF;
			   SELECT COUNT(SEDCODIGO) INTO codSede FROM SEDE WHERE SEDCODINS=codInst AND SEDCODIGO=SEDE;
			   IF codSede=0 THEN
			   	   --SOLO MODIFICA EL ESTADO PORQUE LA UBICACION NO SE PUEDE PORQUE NO TIENE GRUPO=0  
				   INSERT INTO INTEGRACION_LOG (ID, TIPO,MENSAJE)VALUES (id,1,'No se encontro una sede-');
				   RETURN;
			   END IF;
			   	  SELECT (SEDCODIGO) INTO codSede FROM SEDE WHERE SEDCODINS=codInst AND SEDCODIGO=SEDE;
			   	  --PONER LAS JORNADAS QUE NO ESTEN   
			   	  contador:=0; 
			   	  SELECT COUNT(JORCODIGO) INTO contador FROM JORNADA WHERE JORCODINS=codInst AND JORCODIGO=jor;
				  IF contador=0 THEN
				    INSERT INTO JORNADA(JORCODINS,JORCODIGO) VALUES(codInst,jor);
					contador:=0;
					SELECT COUNT(SEDJORCODJOR) INTO contador FROM SEDE_JORNADA WHERE SEDJORCODINST=codInst AND SEDJORCODSEDE=codSede AND SEDJORCODJOR=jor; 
					IF contador=0 THEN
					   INSERT INTO SEDE_JORNADA (SEDJORCODINST, SEDJORCODSEDE, SEDJORCODJOR)VALUES(codInst,codSede,jor);
					END IF;
					contador:=0;
					SELECT COUNT(g_jerCodigo) INTO contador FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=6 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor;					
					IF contador=0 THEN
						INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN) 
						VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,6,deptoInst,munInst,locInst,codInst,codSede,jor);
					END IF;	
				  ELSE
				  	  contador:=0;
					  SELECT COUNT(SEDJORCODJOR) INTO contador FROM SEDE_JORNADA WHERE SEDJORCODINST=codInst AND SEDJORCODSEDE=codSede AND SEDJORCODJOR=jor;
					  IF contador=0 THEN
						  INSERT INTO SEDE_JORNADA (SEDJORCODINST, SEDJORCODSEDE, SEDJORCODJOR)VALUES(codInst,codSede,jor);
					  END IF;					  
					  contador:=0;
				  	  SELECT COUNT(g_jerCodigo) INTO contador FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=6 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor;
					  IF contador=0 THEN
						  INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN) 
						  VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,6,deptoInst,munInst,locInst,codInst,codSede,jor);
					  END IF;
				  END IF;
			   	  --PONER LA METODOLOGIA QUE NO ESTEN   
				  contador:=0;
				  SELECT COUNT(METCODIGO) INTO contador FROM METODOLOGIA WHERE METCODINST=codInst AND METCODIGO=met;
				  IF contador=0 THEN
				  	 INSERT INTO METODOLOGIA(METCODINST,METCODIGO)VALUES (codInst,met);
					 --el registro en jerarquias es por otro trigger   
				  END IF;		
				  --PONER EL GRADO SI NO ESTA  
				  contador:=0;    
				  SELECT COUNT(GRACODIGO) INTO contador FROM GRADO WHERE GRACODINST=codInst AND GRACODMETOD=met AND GRACODIGO=gra;
				  IF contador=0 THEN
				  	 SELECT G_GRANOMBRE INTO graNom FROM G_GRADO WHERE G_GRACODIGO=gra;				  	 
				  	 INSERT INTO GRADO(GRACODINST,GRACODMETOD,GRACODIGO,GRANOMBRE)VALUES(codInst,met,gra,graNom);
					 contador:=0;
					 SELECT COUNT(G_JERGRADO) INTO contador FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=7 AND G_JERINST=codInst AND G_JERSEDE=codSede AND G_JERJORN=jor AND G_JERMETOD=met AND G_JERGRADO=gra;
					 IF contador=0 THEN
						 INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO) 
						 VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,7,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra);
					 END IF;	 
				  ELSE
				  	contador:=0;
				  	SELECT COUNT(g_jerCodigo) INTO contador FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=7 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor AND g_jermetod=met AND g_jerGrado=gra;
					IF contador=0 THEN
						 INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO) 
						 VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,7,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra);
					END IF;
				  END IF;
				  -- PONER EL GRUPO SI NO ESTA    
				  IF gru<>0 THEN				  
				  	 SELECT g_jerCodigo INTO jerGrado FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=7 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor AND g_jermetod=met AND g_jerGrado=gra;
					 IF jerGrado<>0 THEN
					 	contador:=0;
					 	SELECT COUNT(GRUCODIGO) INTO contador FROM GRUPO WHERE GRUCODJERAR=jerGrado AND GRUCODIGO=gru;
						IF contador=0 THEN
						   INSERT INTO GRUPO(GRUCODJERAR,GRUCODIGO,GRUNOMBRE,GRUCUPO)VALUES(jerGrado,gru,nomGrupo,1);
						   contador:=0;
						   SELECT COUNT(g_jerCodigo) INTO contador FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=8 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor AND g_jermetod=met AND g_jerGrado=gra AND G_JERGRUPO=gru;
						   IF contador=0 THEN
							   INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO,G_JERGRUPO)
							   VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,8,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra,gru);
							END IF;	   
						ELSE
						   UPDATE GRUPO SET GRUCUPO=GRUCUPO+1 WHERE GRUCODJERAR=jerGrado AND GRUCODIGO=gru;
						END IF;
					 END IF;
					 SELECT g_jerCodigo INTO jerGrupo FROM G_JERARQUIA 
					 WHERE g_jertipo=1 
					 AND g_jerNivel=8 
					 AND g_jerinst=codInst 
					 AND g_jerSede=codSede 
					 AND g_jerJorn=jor 
					 AND g_jermetod=met 
					 AND g_jerGrado=gra 
					 AND g_jergrupo=gru;
				  END IF;
				--ACTUALIZAR AL NIñO COMO TAL  		
				UPDATE ESTUDIANTE SET 
					   ESTESTADO=estado,
					   ESTGRUPO=jerGrupo
					   WHERE Estcodigo=codigo;  

				IF codInst<>oldInst THEN tipo:=1; END IF;
				IF codSede<>oldSede THEN tipo:=2; END IF;
				IF jor<>oldJor THEN tipo:=3; END IF;
				IF met<>oldMet THEN tipo:=4; END IF;
				IF gra<>oldGra THEN tipo:=5; END IF;
				IF gru<>oldGru THEN tipo:=6; END IF;
				
				INSERT INTO HISTORICO_NOVEDAD_ESTUDIANTE(HISNOVESTCODIGO, HISNOVESTTIPO, HISNOVESTINST1, HISNOVESTINST2, HISNOVESTSEDE1, HISNOVESTSEDE2, HISNOVESTJORN1, HISNOVESTJORN2, HISNOVESTMETOD1, HISNOVESTMETOD2, HISNOVESTGRADO1, HISNOVESTGRADO2, HISNOVESTGRUPO1, HISNOVESTGRUPO2,HISNOVESTESTCODIGO, HISNOVESTSEDENOMBRE) 
				VALUES (
					seq_his_nov_est.NEXTVAL,
					tipo,
					oldInst,
					codInst,				
					oldSede,
					codSede,
					oldJor,
					jor,
					oldMet,
					met,
					oldGra,
					gra,
					oldGru,
					gru,
					codigo,
					tipoId);
			COMMIT;	
    END updateAlumno;  

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
	) IS
  		codInst NUMBER(12);
		codSede NUMBER(12);
		deptoInst NUMBER(5);
  		munInst NUMBER(5);
   		locInst NUMBER(5);
		contador NUMBER(5);
		graNom VARCHAR(50);
		jerGrado NUMBER(12);
		jerGrupo NUMBER(12);
		band NUMBER(1);
		CURSOR cursor1 IS SELECT insCodDepto,INSCODMUN,INSCODLOCAL,inscodigo FROM INSTITUCION WHERE inscoddane=daneInst;
	    BEGIN
		    codInst:=0;
			codSede:=0;
			deptoInst:=0;
			munInst:=0;
			locInst:=0;
			band:=0;
			jerGrado:=0;
			jerGrupo:=0;
			FOR rec IN cursor1 LOOP
			   deptoInst:=rec.insCodDepto;
			   munInst:=rec.INSCODMUN;
			   locInst:=rec.INSCODLOCAL;
			   codInst:=rec.inscodigo;
			END LOOP;
			--BUSCAR SI EXISTE LA INST  
			IF codInst=0 THEN
			 	INSERT INTO INTEGRACION_LOG (ID, TIPO,MENSAJE)VALUES (id,2,'No hay Instituciñn registrada');
				RETURN;
			END IF;
			--BUSCAR SI EXISTE LA SEDE  
			   SELECT COUNT(SEDCODIGO) INTO codSede FROM SEDE WHERE SEDCODINS=codInst AND SEDCODIGO=SEDE;
			   IF codSede=0 THEN
				   INSERT INTO INTEGRACION_LOG (ID, TIPO,MENSAJE)VALUES (id,1,'No se encontro una sede-');
				   RETURN;
			   END IF;
			   	  --PONER LAS JORNADAS QUE NO ESTEN   
			   	  contador:=0; 
			   	  SELECT COUNT(JORCODIGO) INTO contador FROM JORNADA WHERE JORCODINS=codInst AND JORCODIGO=jor;
				  IF contador=0 THEN
				    INSERT INTO JORNADA(JORCODINS,JORCODIGO) VALUES(codInst,jor);
					contador:=0;
					SELECT COUNT(SEDJORCODJOR) INTO contador FROM SEDE_JORNADA WHERE SEDJORCODINST=codInst AND SEDJORCODSEDE=codSede AND SEDJORCODJOR=jor; 
					IF contador=0 THEN
						INSERT INTO SEDE_JORNADA (SEDJORCODINST, SEDJORCODSEDE, SEDJORCODJOR)VALUES(codInst,codSede,jor);
					END IF;
					contador:=0;
					SELECT COUNT(g_jerCodigo) INTO contador FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=6 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor;					
					IF contador=0 THEN
						INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN) 
						VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,6,deptoInst,munInst,locInst,codInst,codSede,jor);
					END IF;	
				  ELSE
				  	  contador:=0;
					  SELECT COUNT(SEDJORCODJOR) INTO contador FROM SEDE_JORNADA WHERE SEDJORCODINST=codInst AND SEDJORCODSEDE=codSede AND SEDJORCODJOR=jor;
					  IF contador=0 THEN
						  INSERT INTO SEDE_JORNADA (SEDJORCODINST, SEDJORCODSEDE, SEDJORCODJOR)VALUES(codInst,codSede,jor);
					  END IF;
				  	  contador:=0;
				  	  SELECT COUNT(g_jerCodigo) INTO contador FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=6 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor;
					  IF contador=0 THEN
						  INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN) 
						  VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,6,deptoInst,munInst,locInst,codInst,codSede,jor);
					  END IF;
				  END IF;
			   	  --PONER LA METODOLOGIA QUE NO ESTEN   
				  contador:=0;
				  SELECT COUNT(METCODIGO) INTO contador FROM METODOLOGIA WHERE METCODINST=codInst AND METCODIGO=met;
				  IF contador=0 THEN
				  	 INSERT INTO METODOLOGIA(METCODINST,METCODIGO)VALUES (codInst,met);
					 --el registro en jerarquia lo hace un trigger aparte.
				  END IF;		
				  --PONER EL GRADO SI NO ESTA  
				  contador:=0;    
				  SELECT COUNT(GRACODIGO) INTO contador FROM GRADO WHERE GRACODINST=codInst AND GRACODMETOD=met AND GRACODIGO=gra;
				  IF contador=0 THEN
				  	 SELECT G_GRANOMBRE INTO graNom FROM G_GRADO WHERE G_GRACODIGO=gra;				  	 
				  	 INSERT INTO GRADO(GRACODINST,GRACODMETOD,GRACODIGO,GRANOMBRE)VALUES(codInst,met,gra,graNom);
					 contador:=0;
					 SELECT COUNT(G_JERGRADO) INTO contador FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=7 AND G_JERINST=codInst AND G_JERSEDE=codSede AND G_JERJORN=jor AND G_JERMETOD=met AND G_JERGRADO=gra;
					 IF contador=0 THEN
						 INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO) 
						 VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,7,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra);
					END IF;	 
				  ELSE
				  	contador:=0;
				  	SELECT COUNT(g_jerCodigo) INTO contador FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=7 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor AND g_jermetod=met AND g_jerGrado=gra;
					IF contador=0 THEN
						 INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO) 
						 VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,7,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra);
					END IF;
				  END IF;
				  --PONER EL GRUPO SI NO ESTA    
				  IF gru<>0 THEN  
				  	 SELECT g_jerCodigo INTO jerGrado FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=7 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor AND g_jermetod=met AND g_jerGrado=gra;
					 IF jerGrado<>0 THEN
					 	contador:=0;
					 	SELECT COUNT(GRUCODIGO) INTO contador FROM GRUPO WHERE GRUCODJERAR=jerGrado AND GRUCODIGO=gru;
						IF contador=0 THEN
						   INSERT INTO GRUPO(GRUCODJERAR,GRUCODIGO,GRUNOMBRE,GRUCUPO)VALUES(jerGrado,gru,nomGrupo,1);
						   contador:=0;
						   SELECT COUNT(g_jerCodigo) INTO contador FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=8 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor AND g_jermetod=met AND g_jerGrado=gra AND G_JERGRUPO=gru;
						   IF contador=0 THEN
							   INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO,G_JERGRUPO)
							   VALUES(NVL((SELECT MAX(G_JERARQUIA.G_JERCODIGO)+1 FROM G_JERARQUIA),1),1,8,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra,gru);
							END IF;   
						ELSE
						   UPDATE GRUPO SET GRUCUPO=GRUCUPO+1 WHERE GRUCODJERAR=jerGrado AND GRUCODIGO=gru;
						END IF;
					 END IF;
					 SELECT g_jerCodigo INTO jerGrupo FROM G_JERARQUIA WHERE g_jertipo=1 AND g_jerNivel=8 AND g_jerinst=codInst AND g_jerSede=codSede AND g_jerJorn=jor AND g_jermetod=met AND g_jerGrado=gra AND g_jergrupo=gru;
				  END IF;
				--INGRESAR AL NIñO COMO TAL  		
				INSERT INTO ESTUDIANTE (
					ESTCODIGO,
					estVigencia,
					ESTEXPDOCCODDEP,
					ESTEXPDOCCODMUN, 
					ESTTIPODOC, 
					ESTNUMDOC,
					ESTAPELLIDO1, 
					ESTAPELLIDO2, 
					ESTNOMBRE1, 
					ESTNOMBRE2,
					ESTESTADO,
					ESTGRUPO,
					ESTLUGNACCODDEP,
					ESTLUGNACCODMUN, 
					ESTGENERO,
					ESTFECHANAC
				)VALUES (
					codigo,
					anhovig,
					deptoExp,
					munExp,
					tipoId,
					numId,
					ape1,
					ape2,
					nom1,
					nom2,
					SUBSTR(estado,1,3),
					jerGrupo,
					deptoNaci,
				  	munNaci,
				    genero,
				    TO_DATE(fechaNaci,'dd/mm/yyyy')				
				);
				
			COMMIT;	
    END insertAlumno;  
	
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
	) IS
		tipoIdOld NUMERIC;
		numIdOld VARCHAR(20);
  		cont NUMBER(2);
		TEMP NUMBER(5);
	    BEGIN
		cont:=0;
		TEMP:=0;
		--calcular el tipo y numero del estudiante
		SELECT ESTTIPODOC, ESTNUMDOC INTO tipoIdOld,numIdOld FROM ESTUDIANTE WHERE ESTCODIGO=codigo;
		--validar si hay problema de duplicados 
		IF tipoId=tipoIdOld AND numId=numIdOld THEN
		   cont:=1;
		ELSE
			SELECT COUNT(ESTCODIGO) INTO TEMP FROM ESTUDIANTE WHERE ESTTIPODOC=tipoId AND ESTNUMDOC=numId;
		   	--si es cero significa que no hay alguien repetido 
			IF TEMP=0 THEN
			   cont:=1;
			END IF;	   
		END IF;
		
		IF cont=1 THEN
		   --Se puede actualizar el registro
		   UPDATE ESTUDIANTE SET
		   ESTTIPODOC=tipoId,
		   ESTNUMDOC=numId,
		   ESTVIGENCIA=anhovig, 
		   ESTEXPDOCCODDEP=deptoExp, 
		   ESTEXPDOCCODMUN=munExp, 
		   ESTAPELLIDO1=apellido1,
		   ESTAPELLIDO2=apellido2,
		   ESTNOMBRE1=nombre1,
		   ESTNOMBRE2=nombre2,
		   ESTGENERO=genero,
		   ESTESTADO=estado
		   WHERE EStCODIGO=codigo; 
		ELSE
			--ya hay alguien con la identificaciñn que se quiere poner
		 	INSERT INTO INTEGRACION_LOG (ID, TIPO,MENSAJE)VALUES (id,7,'La identificaciñn ya existe');
			RETURN;
		END IF;
    END updateIdAlumno;  
	
END Pk_Integracion;
/
/*<TOAD_FILE_CHUNK>*/
