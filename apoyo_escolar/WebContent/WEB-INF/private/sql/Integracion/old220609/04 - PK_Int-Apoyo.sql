--PROCEDIMIENTO ALMACENADO DEL LADO DEL ESQUEMA DE DATOS DE APOYO ESCOLAR 
--UTILIZADO PARA EJECUTAR ALGUNAS TRANSACCIONES QUE POR SU COMPLEJIDAD 
--SE DEBEN EJECUTAR POR PL-SQL Y NOSE PUEDEN EHJJECUTAR CON SENTENCIAS DESDE LA APLICACION  
CREATE OR REPLACE PACKAGE PK_INTEGRACION AS

	--Ingresa el alumno basado en los parametros enviados  	
	PROCEDURE insertAlumno(
		codigo  in NUMERIC,
		anhovig  in NUMERIC,
		daneInst in NUMERIC,
		sede in NUMERIC,
		jor in NUMERIC,
		met in NUMERIC,
		gra in NUMERIC,
		gru in NUMERIC,
		tipoId in NUMERIC,
		numId in VARCHAR,
		ape1 in VARCHAR,
		ape2 in VARCHAR,
		nom1 in VARCHAR,
		nom2 in VARCHAR,
		deptoNaci in NUMERIC,
		munNaci in NUMERIC,
		genero in NUMERIC,
		fechaNaci in VARCHAR,
		deptoExp in NUMERIC,
		munExp in NUMERIC,
		estado in NUMERIC,
		nomGrupo in VARCHAR,
		id in NUMERIC
	);
	
	--actualiza la ubicacion del alumno basado en los parametros de ingreso  
	PROCEDURE updateAlumno(
		codigo  in NUMERIC,
		anhovig  in NUMERIC,
		daneInst in NUMERIC,
		sede in NUMERIC,
		jor in NUMERIC,
		met in NUMERIC,
		gra in NUMERIC,
		gru in NUMERIC,
		daneInstOld in NUMERIC,
		sedeOld in NUMERIC,
		jorOld in NUMERIC,
		metOld in NUMERIC,
		graOld in NUMERIC,
		gruOld in NUMERIC,
		tipoId in NUMERIC,
		numId in VARCHAR,
		estado in NUMERIC,
		nomGrupo in VARCHAR,
		id in NUMERIC
	);
	
	--actualiza la identificacion del alumno basado en los parametros de ingreso  
	PROCEDURE updateIdAlumno(
		id in NUMERIC,
		codigo  in NUMERIC,
		anhovig  in NUMERIC,
		tipoIdOld in NUMERIC,
		numIdOld in NUMERIC,
		tipoId in NUMERIC,
		numId in NUMERIC,
		apellido1 in VARCHAR,
		apellido2 in VARCHAR,
		nombre1 in VARCHAR,
		nombre2 in VARCHAR,
		estado in NUMERIC,
		deptoExp in NUMERIC,
		munExp in NUMERIC,
		genero in NUMERIC
	);


	--Actualiza la sede basado en los parametros enviados  
	PROCEDURE updateSede(
	daneOld in NUMERIC,
	daneNew in NUMERIC,
	daneSedeOld in NUMERIC,
	daneSedeNew in NUMERIC,
	sedeOld in NUMERIC,
	sedeNew in NUMERIC,
	nombre in VARCHAR,
	direccion in VARCHAR,
	telefono in VARCHAR,
	estado in VARCHAR
	);
	
END PK_INTEGRACION;
/


CREATE OR REPLACE PACKAGE BODY PK_INTEGRACION AS
	   --Validado 	   
   PROCEDURE updateSede(daneOld in NUMERIC,	daneNew in NUMERIC,	daneSedeOld in NUMERIC,	daneSedeNew in NUMERIC,	sedeOld in NUMERIC,	sedeNew in NUMERIC,	nombre in VARCHAR,	direccion in VARCHAR,telefono in VARCHAR, estado in VARCHAR) is
	   codInstOld number(12);
	   codInstNew number(12);
	   munOld number(5);
	   munNew number(5);
	   locOld number(5);
	   locNew number(5);
	   band  number(1);
	    BEGIN
		DECLARE CURSOR cursor1 IS select INSCODMUN,INSCODLOCAL,inscodigo from institucion where inscoddane=daneOld;
				CURSOR cursor2 IS select INSCODMUN,INSCODLOCAL,inscodigo from institucion where inscoddane=daneNew;
				CURSOR cursor3 IS
				   SELECT G_JERDEPTO,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO 
				   FROM G_JERARQUIA 
				   WHERE G_JERINST=codInstOld 
				   AND G_JERSEDE=sedeOld 
				   AND G_JERTIPO=1 
				   AND G_JERNIVEL=7 
				   and (G_JERMETOD,G_JERGRADO) 
				   not in(SELECT GRACODMETOD,GRACODIGO 
				   FROM GRADO WHERE GRACODINST=codInstNew);
	
	    BEGIN
	    codInstOld:=0;
	    codInstNew:=0;
		band:=0;
				
		FOR rec in cursor1 LOOP
		   munOld:=rec.INSCODMUN;
		   locOld:=rec.INSCODLOCAL;
		   codInstOld:=rec.inscodigo;
		END LOOP;
		FOR rec in cursor2 LOOP
		   munNew:=rec.INSCODMUN;
		   locNew:=rec.INSCODLOCAL;
		   codInstNew:=rec.inscodigo;
		END LOOP;
		-- CASO 1: MODIFICACION DEL DANE DE LA INSTITUCION    
		if daneOld<>daneNew then 
		   --insertar las jornadas que no esten en la nueva inst  
			INSERT INTO JORNADA(JORCODINS,JORCODIGO)
			SELECT codInstNew as inst,SEDJORCODJOR 
				   FROM SEDE_JORNADA 
				   where SEDJORCODINST=codInstOld 
				   and SEDJORCODSEDE=sedeOld 
				   and SEDJORCODJOR not in(
				   	   SELECT JORCODIGO FROM JORNADA where JORCODINS=codInstNew);
			--insertar las metodologias que no esten en la nueva inst
			INSERT INTO METODOLOGIA(METCODINST,METCODIGO)
			SELECT distinct codInstNew as inst, G_JERMETOD 
			FROM G_JERARQUIA 
			WHERE G_JERINST=codInstOld 
			AND G_JERSEDE=sedeOld 
			AND G_JERTIPO=1 
			AND G_JERNIVEL=7 
			and G_JERMETOD not in(SELECT METCODIGO FROM METODOLOGIA WHERE METCODINST=codInstNew);
			--Insertar los grados que no estan en la nueva inst		
			INSERT INTO GRADO(GRACODINST,GRACODMETOD,GRACODIGO,GRANOMBRE)
				SELECT distinct codInstNew as inst, G_JERMETOD,G_JERGRADO,G_GRANOMBRE 
				FROM G_JERARQUIA,G_GRADO 
				WHERE G_JERINST=codInstOld 
				AND G_JERSEDE=sedeOld 
				AND G_JERTIPO=1 
				AND G_JERNIVEL=7 
				and G_GRACODIGO=G_JERGRADO
				and (G_JERMETOD,G_JERGRADO) 
				not in(SELECT GRACODMETOD,GRACODIGO 
				FROM GRADO 
				WHERE GRACODINST=codInstNew);
			--Insertar los grados en jerarquia que no estan en la nueva institucion     
			FOR rec in cursor3 LOOP
			   INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO)
			   VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,7,
			   rec.G_JERDEPTO,munNew,locNew,codInstNew,rec.G_JERSEDE,rec.G_JERJORN,rec.G_JERMETOD,rec.G_JERGRADO);		
			END LOOP;
			
			UPDATE SEDE SET SEDCODINS=codInstNew,SEDCODIGO=sedeNew,sednombre=nombre, sedestado=estado,SEDDIRECCION=direccion,SEDTELEFONO=telefono WHERE SEDCODINS=codInstOld AND SEDCODIGO=sedeOld;
			UPDATE G_JERARQUIA SET G_JerMunic=munNew,G_JerLocal=locNew,G_JerInst=codInstNew,G_JERSEDE=sedeNew WHERE G_JerInst=codInstOld and G_JERSEDE=sedeOld;
			UPDATE SEDE_JORNADA SET SEDJORCODINST=codInstNew,SEDJORCODSEDE=sedeNew WHERE SEDJORCODINST=codInstOld AND SEDJORCODSEDE=sedeOld;
			UPDATE SEDE_JORNADA_NIVEL SET SEDJORNIVCODINST=codInstNew,SEDJORNIVCODSEDE=sedeNew WHERE SEDJORNIVCODINST=codInstOld AND SEDJORNIVCODSEDE=sedeOld;
			UPDATE SEDE_JORNADA_ESPACIOFISICO SET SEDJORESPCODINST=codInstNew,SEDJORESPCODSEDE=sedeNew WHERE SEDJORESPCODINST=codInstOld AND SEDJORESPCODSEDE=sedeOld;
			UPDATE DOCENTE_SEDE_JORNADA SET DOCSEDJORCODINST=codInstNew, DOCSEDJORCODSEDE=sedeNew WHERE DOCSEDJORCODINST=codInstOld AND DOCSEDJORCODSEDE=sedeOld;
	
			INSERT INTO HISTORICO_NOVEDAD_INSTITUCION(HISNOVINSCODIGO,HISNOVINSTIPO,HISNOVINSINSCODIGO1,HISNOVINSINSCODIGO2,HISNOVINSCODIGO1, HISNOVINSCODIGO2) VALUES 
			(seq_his_nov_inst.nextval,6,codInstOld,codInstNew,sedeOld,sedeNew);
			
			INSERT INTO HISTORICO_NOVEDAD_ESTUDIANTE (HISNOVESTFECHA, HISNOVESTCODIGO, HISNOVESTTIPO, HISNOVESTINST1, HISNOVESTINST2, HISNOVESTSEDE1, HISNOVESTSEDE2, HISNOVESTJORN1, HISNOVESTJORN2, HISNOVESTMETOD1, HISNOVESTMETOD2, HISNOVESTGRADO1, HISNOVESTGRADO2, HISNOVESTGRUPO1, HISNOVESTGRUPO2, HISNOVESTSEDENOMBRE, HISNOVESTESTCODIGO) 
			SELECT sysdate as fecha,seq_his_nov_est.nextval as consecutivo,
			7 as tipo,codInstOld as oldinstitucion,G_JERINST,sedeOld AS OLDSEDE,G_JERSEDE,G_JERJORN,G_JERJORN,G_JERMETOD,G_JERMETOD,G_JERGRADO,G_JERGRADO,G_JERGRUPO,G_JERGRUPO,nombre AS nombredese,ESTCODIGO 
			FROM ESTUDIANTE,  G_JERARQUIA 
			where ESTGRUPO=G_JERCODIGO 
			and G_JERTIPO=1 
			and G_JERNIVEL=8 
			and G_JERINST=codInstNew 
			and G_JERSEDE=sedeNew;
		
		end if;
		--CASO 2: MODIFICACION DEL CONSECUTIVO DE LA SEDE   	
		if daneOld=daneNew and sedeOld<>sedeNew then
		    UPDATE SEDE SET SEDCODIGO=sedeNew,SEDCODDANEANTERIOR=daneSedeNew,sednombre=nombre, sedestado=estado,SEDDIRECCION=direccion,SEDTELEFONO=telefono WHERE SEDCODINS=codInstOld AND SEDCODIGO=sedeOld;
			UPDATE G_JERARQUIA SET G_JERSEDE=sedeNew WHERE G_JERTIPO=1 and G_JerInst=codInstOld and G_JERSEDE=sedeOld;
			UPDATE SEDE_JORNADA SET SEDJORCODSEDE=sedeNew WHERE SEDJORCODINST=codInstOld AND SEDJORCODSEDE=sedeOld;
			UPDATE SEDE_JORNADA_NIVEL SET SEDJORNIVCODSEDE=sedeNew WHERE SEDJORNIVCODINST=codInstOld AND SEDJORNIVCODSEDE=sedeOld;
			UPDATE SEDE_JORNADA_ESPACIOFISICO SET SEDJORESPCODSEDE=sedeNew WHERE SEDJORESPCODINST=codInstOld AND SEDJORESPCODSEDE=sedeOld;
			UPDATE DOCENTE_SEDE_JORNADA SET DOCSEDJORCODSEDE=sedeNew WHERE DOCSEDJORCODINST=codInstOld AND DOCSEDJORCODSEDE=sedeOld;
	
			INSERT INTO HISTORICO_NOVEDAD_INSTITUCION(HISNOVINSCODIGO,HISNOVINSTIPO,HISNOVINSINSCODIGO1,HISNOVINSINSCODIGO2,HISNOVINSCODIGO1, HISNOVINSCODIGO2, HISNOVINSNOMBRE1, HISNOVINSNOMBRE2) 
			VALUES (seq_his_nov_inst.nextval,5,codInstOld,null,sedeOld,sedeNew,null,null);
		end if;
		--CASO 3: MODIFICACION DEL DANE DE LA SEDE	
		if daneOld=daneNew and sedeOld=sedeNew and daneSedeOld<>daneSedeNew then
		   UPDATE SEDE SET SEDCODDANEANTERIOR=daneSedeNew,sednombre=nombre, sedestado=estado,SEDDIRECCION=direccion,SEDTELEFONO=telefono WHERE SEDCODINS=codInstOld AND SEDCODIGO=sedeOld;
		   INSERT INTO HISTORICO_NOVEDAD_INSTITUCION(HISNOVINSCODIGO,HISNOVINSTIPO,HISNOVINSINSCODIGO1,HISNOVINSINSCODIGO2,HISNOVINSCODIGO1, HISNOVINSCODIGO2, HISNOVINSNOMBRE1, HISNOVINSNOMBRE2) 
		   VALUES (seq_his_nov_inst.nextval,4,codInstOld,null,daneSedeOld,daneSedeNew,null,null);
		end if;
		--CASO 4: MODIFICACION DEL NOMBRE DE LA SEDE	
		if daneOld=daneNew and sedeOld=sedeNew and daneSedeOld=daneSedeNew then
		   UPDATE SEDE SET sednombre=nombre, sedestado=estado,SEDDIRECCION=direccion,SEDTELEFONO=telefono WHERE SEDCODINS=codInstOld AND SEDCODIGO=sedeOld;
		   INSERT INTO HISTORICO_NOVEDAD_INSTITUCION(HISNOVINSCODIGO,HISNOVINSTIPO,HISNOVINSINSCODIGO1,HISNOVINSINSCODIGO2,HISNOVINSCODIGO1, HISNOVINSCODIGO2, HISNOVINSNOMBRE1, HISNOVINSNOMBRE2) 
		   VALUES (seq_his_nov_inst.nextval,4,codInstOld,null,daneSedeOld,daneSedeNew,null,null);
		end if;
		COMMIT;	
		END;
  END updateSede;  

	PROCEDURE updateAlumno(
		codigo  in NUMERIC,
		anhovig  in NUMERIC,
		daneInst in NUMERIC,
		sede in NUMERIC,
		jor in NUMERIC,
		met in NUMERIC,
		gra in NUMERIC,
		gru in NUMERIC,
		daneInstOld in NUMERIC,--INSTITUCION QUE TIENE EN MATRICULAS 
		sedeOld in NUMERIC,--SEDE QUE TIENE EN MATRICULAS 
		jorOld in NUMERIC,--JORNADA QUE TIENE EN MATRICULAS 
		metOld in NUMERIC,--METODOLOGIA QUE TIENE EN MATRICULAS 
		graOld in NUMERIC,--GRADO QUE TIENE EN MATRICULAS 
		gruOld in NUMERIC,--GRUPO QUE TIENE EN MATRICULAS 
		tipoId in NUMERIC,
		numId in VARCHAR,
		estado in NUMERIC,
		nomGrupo in VARCHAR,
		id in NUMERIC
	) is
  		codInst number(12);
		codSede number(12);
		deptoInst number(5);
  		munInst number(5);
   		locInst number(5);
		contador number(5);
		graNom varchar(50);
		jerGrado number(12);
		jerGrupo number(12);
		band number(1);
	    oldInst number(12);--INSTITUCION CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		oldSede number(12);--SEDE CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		oldJor number(2);--JORNADA CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		oldMet number(2);--METODOLOGIA CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		oldGra number(2);--GRADO CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		oldGru number(2);--GRUPO CALCULADA DEL GRUPO LOCAL DEL ESTUDIANTE  
		tipo number(1);
		CURSOR cursor1 IS select insCodDepto,INSCODMUN,INSCODLOCAL,inscodigo from institucion where inscoddane=daneInst;
		CURSOR cursor2 IS SELECT G_JERINST, G_JERSEDE, G_JERJORN, G_JERMETOD,G_JERGRADO,G_JERGRUPO FROM G_JERARQUIA,ESTUDIANTE where G_JERCODIGO=ESTGRUPO and ESTTIPODOC=tipoId and ESTNUMDOC=numId;
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
			FOR rec in cursor1 LOOP
			   deptoInst:=rec.insCodDepto;
			   munInst:=rec.INSCODMUN;
			   locInst:=rec.INSCODLOCAL;
			   codInst:=rec.inscodigo;
			END LOOP;
			FOR rec in cursor2 LOOP
			    oldInst:=rec.G_JERINST;
				oldSede:=rec.G_JERSEDE;
				oldJor:=rec.G_JERJORN;
				oldMet:=rec.G_JERMETOD;
				oldGra:=rec.G_JERGRADO;
				oldGru:=rec.G_JERGRUPO;
			END LOOP;
			if codInst=0 then
			 	INSERT INTO INTEGRACION_LOG (ID, TIPO,MENSAJE)VALUES (id,2,'No hay Instituciñn registrada');
				return;
			end if;
			   SELECT count(SEDCODIGO) into codSede FROM SEDE where SEDCODINS=codInst and SEDCODIGO=sede;
			   if codSede=0 then
			   	   --SOLO MODIFICA EL ESTADO PORQUE LA UBICACION NO SE PUEDE PORQUE NO TIENE GRUPO=0  
				   INSERT INTO INTEGRACION_LOG (ID, TIPO,MENSAJE)VALUES (id,1,'No se encontro una sede-');
				   return;
			   end if;
			   	  SELECT (SEDCODIGO) into codSede FROM SEDE where SEDCODINS=codInst and SEDCODIGO=sede;
			   	  --PONER LAS JORNADAS QUE NO ESTEN   
			   	  contador:=0; 
			   	  SELECT count(JORCODIGO) into contador FROM JORNADA where JORCODINS=codInst and JORCODIGO=jor;
				  if contador=0 then
				    INSERT INTO JORNADA(JORCODINS,JORCODIGO) values(codInst,jor);
					INSERT INTO SEDE_JORNADA (SEDJORCODINST, SEDJORCODSEDE, SEDJORCODJOR)VALUES(codInst,codSede,jor);
					INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN) 
					VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,6,deptoInst,munInst,locInst,codInst,codSede,jor);
				  else
				  	  contador:=0;
				  	  select count(g_jerCodigo) into contador from g_jerarquia where g_jertipo=1 and g_jerNivel=6 and g_jerinst=codInst and g_jerSede=codSede and g_jerJorn=jor;
					  if contador=0 then
						  INSERT INTO SEDE_JORNADA (SEDJORCODINST, SEDJORCODSEDE, SEDJORCODJOR)VALUES(codInst,codSede,jor);
						  INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN) 
						  VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,6,deptoInst,munInst,locInst,codInst,codSede,jor);
					  end if;
				  end if;
			   	  --PONER LA METODOLOGIA QUE NO ESTEN   
				  contador:=0;
				  SELECT count(METCODIGO) into contador FROM METODOLOGIA where METCODINST=codInst and METCODIGO=met;
				  if contador=0 then
				  	 INSERT INTO METODOLOGIA(METCODINST,METCODIGO)VALUES (codInst,met);
					 --el registro en jerarquias es por otro trigger   
				  end if;		
				  --PONER EL GRADO SI NO ESTA  
				  contador:=0;    
				  SELECT count(GRACODIGO) into contador FROM GRADO where GRACODINST=codInst and GRACODMETOD=met and GRACODIGO=gra;
				  if contador=0 then
				  	 SELECT G_GRANOMBRE into graNom FROM G_GRADO where G_GRACODIGO=gra;				  	 
				  	 INSERT INTO GRADO(GRACODINST,GRACODMETOD,GRACODIGO,GRANOMBRE)VALUES(codInst,met,gra,graNom);
					 INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO) 
					 VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,7,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra);
				  else
				  	contador:=0;
				  	select count(g_jerCodigo) into contador from g_jerarquia where g_jertipo=1 and g_jerNivel=7 and g_jerinst=codInst and g_jerSede=codSede and g_jerJorn=jor and g_jermetod=met and g_jerGrado=gra;
					if contador=0 then
						 INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO) 
						 VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,7,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra);
					end if;
				  end if;
				  -- PONER EL GRUPO SI NO ESTA    
				  if gru<>0 then				  
				  	 select g_jerCodigo into jerGrado from g_jerarquia where g_jertipo=1 and g_jerNivel=7 and g_jerinst=codInst and g_jerSede=codSede and g_jerJorn=jor and g_jermetod=met and g_jerGrado=gra;
					 if jerGrado<>0 then
					 	contador:=0;
					 	SELECT count(GRUCODIGO) into contador FROM GRUPO where GRUCODJERAR=jerGrado and GRUCODIGO=gru;
						if contador=0 then
						   INSERT INTO GRUPO(GRUCODJERAR,GRUCODIGO,GRUNOMBRE,GRUCUPO)VALUES(jerGrado,gru,nomGrupo,1);
						   INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO,G_JERGRUPO)
						   VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,8,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra,gru);
						else
						   update grupo set GRUCUPO=GRUCUPO+1 where GRUCODJERAR=jerGrado and GRUCODIGO=gru;
						end if;
					 end if;
					 select g_jerCodigo into jerGrupo from g_jerarquia 
					 where g_jertipo=1 
					 and g_jerNivel=8 
					 and g_jerinst=codInst 
					 and g_jerSede=codSede 
					 and g_jerJorn=jor 
					 and g_jermetod=met 
					 and g_jerGrado=gra 
					 and g_jergrupo=gru;
				  end if;
				--ACTUALIZAR AL NIñO COMO TAL  		
				update ESTUDIANTE set 
					   ESTESTADO=estado,
					   ESTGRUPO=jerGrupo
					   where Estcodigo=codigo;  

				if codInst<>oldInst then tipo:=1; end if;
				if codSede<>oldSede then tipo:=2; end if;
				if jor<>oldJor then tipo:=3; end if;
				if met<>oldMet then tipo:=4; end if;
				if gra<>oldGra then tipo:=5; end if;
				if gru<>oldGru then tipo:=6; end if;
				
				INSERT INTO HISTORICO_NOVEDAD_ESTUDIANTE(HISNOVESTCODIGO, HISNOVESTTIPO, HISNOVESTINST1, HISNOVESTINST2, HISNOVESTSEDE1, HISNOVESTSEDE2, HISNOVESTJORN1, HISNOVESTJORN2, HISNOVESTMETOD1, HISNOVESTMETOD2, HISNOVESTGRADO1, HISNOVESTGRADO2, HISNOVESTGRUPO1, HISNOVESTGRUPO2,HISNOVESTESTCODIGO, HISNOVESTSEDENOMBRE) 
				VALUES (
					seq_his_nov_est.nextval,
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
					numId,
					tipoId);
			COMMIT;	
    END updateAlumno;  

	PROCEDURE insertAlumno(
		codigo  in NUMERIC,
		anhovig  in NUMERIC,
		daneInst in NUMERIC,
		sede in NUMERIC,
		jor in NUMERIC,
		met in NUMERIC,
		gra in NUMERIC,
		gru in NUMERIC,
		tipoId in NUMERIC,
		numId in VARCHAR,
		ape1 in VARCHAR,
		ape2 in VARCHAR,
		nom1 in VARCHAR,
		nom2 in VARCHAR,
		deptoNaci in NUMERIC,
		munNaci in NUMERIC,
		genero in NUMERIC,
		fechaNaci in VARCHAR,
		deptoExp in NUMERIC,
		munExp in NUMERIC,
		estado in NUMERIC,
		nomGrupo in VARCHAR,
		id in NUMERIC
	) is
  		codInst number(12);
		codSede number(12);
		deptoInst number(5);
  		munInst number(5);
   		locInst number(5);
		contador number(5);
		graNom varchar(50);
		jerGrado number(12);
		jerGrupo number(12);
		band number(1);
		CURSOR cursor1 IS select insCodDepto,INSCODMUN,INSCODLOCAL,inscodigo from institucion where inscoddane=daneInst;
	    BEGIN
		    codInst:=0;
			codSede:=0;
			deptoInst:=0;
			munInst:=0;
			locInst:=0;
			band:=0;
			jerGrado:=0;
			jerGrupo:=0;
			FOR rec in cursor1 LOOP
			   deptoInst:=rec.insCodDepto;
			   munInst:=rec.INSCODMUN;
			   locInst:=rec.INSCODLOCAL;
			   codInst:=rec.inscodigo;
			END LOOP;
			--BUSCAR SI EXISTE LA INST  
			if codInst=0 then
			 	INSERT INTO INTEGRACION_LOG (ID, TIPO,MENSAJE)VALUES (id,2,'No hay Instituciñn registrada');
				return;
			end if;
			--BUSCAR SI EXISTE LA SEDE  
			   SELECT count(SEDCODIGO) into codSede FROM SEDE where SEDCODINS=codInst and SEDCODIGO=sede;
			   if codSede=0 then
				   INSERT INTO INTEGRACION_LOG (ID, TIPO,MENSAJE)VALUES (id,1,'No se encontro una sede-');
				   return;
			   end if;
			   	  --PONER LAS JORNADAS QUE NO ESTEN   
			   	  contador:=0; 
			   	  SELECT count(JORCODIGO) into contador FROM JORNADA where JORCODINS=codInst and JORCODIGO=jor;
				  if contador=0 then
				    INSERT INTO JORNADA(JORCODINS,JORCODIGO) values(codInst,jor);
					INSERT INTO SEDE_JORNADA (SEDJORCODINST, SEDJORCODSEDE, SEDJORCODJOR)VALUES(codInst,codSede,jor);
					INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN) 
					VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,6,deptoInst,munInst,locInst,codInst,codSede,jor);
				  else
				  	  contador:=0;
				  	  select count(g_jerCodigo) into contador from g_jerarquia where g_jertipo=1 and g_jerNivel=6 and g_jerinst=codInst and g_jerSede=codSede and g_jerJorn=jor;
					  if contador=0 then
						  INSERT INTO SEDE_JORNADA (SEDJORCODINST, SEDJORCODSEDE, SEDJORCODJOR)VALUES(codInst,codSede,jor);
						  INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN) 
						  VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,6,deptoInst,munInst,locInst,codInst,codSede,jor);
					  end if;
				  end if;
			   	  --PONER LA METODOLOGIA QUE NO ESTEN   
				  contador:=0;
				  SELECT count(METCODIGO) into contador FROM METODOLOGIA where METCODINST=codInst and METCODIGO=met;
				  if contador=0 then
				  	 INSERT INTO METODOLOGIA(METCODINST,METCODIGO)VALUES (codInst,met);
					 --el registro en jerarquia lo hace un trigger aparte.
				  end if;		
				  --PONER EL GRADO SI NO ESTA  
				  contador:=0;    
				  SELECT count(GRACODIGO) into contador FROM GRADO where GRACODINST=codInst and GRACODMETOD=met and GRACODIGO=gra;
				  if contador=0 then
				  	 SELECT G_GRANOMBRE into graNom FROM G_GRADO where G_GRACODIGO=gra;				  	 
				  	 INSERT INTO GRADO(GRACODINST,GRACODMETOD,GRACODIGO,GRANOMBRE)VALUES(codInst,met,gra,graNom);
					 INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO) 
					 VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,7,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra);
				  else
				  	contador:=0;
				  	select count(g_jerCodigo) into contador from g_jerarquia where g_jertipo=1 and g_jerNivel=7 and g_jerinst=codInst and g_jerSede=codSede and g_jerJorn=jor and g_jermetod=met and g_jerGrado=gra;
					if contador=0 then
						 INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO) 
						 VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,7,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra);
					end if;
				  end if;
				  --PONER EL GRUPO SI NO ESTA    
				  if gru<>0 then  
				  	 select g_jerCodigo into jerGrado from g_jerarquia where g_jertipo=1 and g_jerNivel=7 and g_jerinst=codInst and g_jerSede=codSede and g_jerJorn=jor and g_jermetod=met and g_jerGrado=gra;
					 if jerGrado<>0 then
					 	contador:=0;
					 	SELECT count(GRUCODIGO) into contador FROM GRUPO where GRUCODJERAR=jerGrado and GRUCODIGO=gru;
						if contador=0 then
						   INSERT INTO GRUPO(GRUCODJERAR,GRUCODIGO,GRUNOMBRE,GRUCUPO)VALUES(jerGrado,gru,nomGrupo,1);
						   INSERT INTO G_JERARQUIA(G_JERCODIGO,G_JERTIPO,G_JERNIVEL,G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERMETOD,G_JERGRADO,G_JERGRUPO)
						   VALUES(nvl((select max(G_JERARQUIA.G_JERCODIGO)+1 from G_JERARQUIA),1),1,8,deptoInst,munInst,locInst,codInst,codSede,jor,met,gra,gru);
						else
						   update grupo set GRUCUPO=GRUCUPO+1 where GRUCODJERAR=jerGrado and GRUCODIGO=gru;
						end if;
					 end if;
					 select g_jerCodigo into jerGrupo from g_jerarquia where g_jertipo=1 and g_jerNivel=8 and g_jerinst=codInst and g_jerSede=codSede and g_jerJorn=jor and g_jermetod=met and g_jerGrado=gra and g_jergrupo=gru;
				  end if;
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
				    to_date(fechaNaci,'dd/mm/yyyy')				
				);
				
			COMMIT;	
    END insertAlumno;  
	
	PROCEDURE updateIdAlumno(
		id in NUMERIC,
		codigo  in NUMERIC,
		anhovig  in NUMERIC,
		tipoIdOld in NUMERIC,
		numIdOld in NUMERIC,
		tipoId in NUMERIC,
		numId in NUMERIC,
		apellido1 in VARCHAR,
		apellido2 in VARCHAR,
		nombre1 in VARCHAR,
		nombre2 in VARCHAR,
		estado in NUMERIC,
		deptoExp in NUMERIC,
		munExp in NUMERIC,
		genero in NUMERIC
	) is
  		cont number(2);
		temp number(5);
	    BEGIN
		cont:=0;
		temp:=0;
		if tipoId=tipoIdOld and numId=numIdOld then
		   cont:=1;
		else
			SELECT count(ESTCODIGO) into temp FROM ESTUDIANTE where ESTTIPODOC=tipoId and ESTNUMDOC=numId;
		   	if temp=0 then
			   cont:=1;
			end if;	   
		end if;
		if cont=1 then
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
		   where EStCODIGO=codigo; 
		else
		 	INSERT INTO INTEGRACION_LOG (ID, TIPO,MENSAJE)VALUES (id,7,'La identificaciñn ya existe');
			return;
		end if;
    END updateIdAlumno;  
	
END PK_INTEGRACION;
/
commit;
--select * from user_errors
  
--drop PACKAGE PK_INTEGRACION
