--PAQUETE QUE INVOLUCRA LOS PROCEDIMIENTOS QUE ALGUNOS TRIGGERS DEL ESQUEMA DE MATRICULAS UTILIZAN PARA CALCULAR DATOS 
--QUE NO ES POSIBLE CALCULAR EN TIEMPO DE TRIGGER.  

CREATE OR REPLACE PACKAGE PK_INTEGRACION2 AS
	   --Ejecutado por el trigger de insercion de estudiante para calcular la metodologia del colegio  
	PROCEDURE insertAlumno2(
		codigo in NUMERIC,
		anhovig in NUMERIC,
		inst in NUMERIC,
		jorn in NUMERIC,
		gra in NUMERIC,
		gru in NUMERIC,
		dd_exp in NUMERIC,
		dm_exp in NUMERIC,
		tipo_Id in NUMERIC,
		num_Id in NUMERIC,
		apellido1 in VARCHAR,
		apellido2 in VARCHAR,
		nombre1 in VARCHAR,
		nombre2 in VARCHAR,
		estado in NUMERIC,
		genero in NUMERIC,
		fecha_naci in date,
		dd_naci in NUMERIC,
		dm_naci in NUMERIC
	);

	   --Ejecutado por el trigger de actualizacion de ubicacion de estudiante para calcular la metodologia del colegio anterior y nuevo  
	   	PROCEDURE updateAlumno2(
		codigo in NUMERIC,
		anhovig in NUMERIC,
		inst in NUMERIC,
		jorn in NUMERIC,
		gra in NUMERIC,
		gru in NUMERIC,
		tipo_Id in NUMERIC,
		num_Id in NUMERIC,
		estado in NUMERIC,
		instOld in NUMERIC,
		jornOld in NUMERIC,
		graOld in NUMERIC,
		gruOld in NUMERIC
	);

	   --Ejecutado por el trigger de actualizacion de identificacion de estudiante para calcular la metodologia del colegio anterior y nuevo   
	   	PROCEDURE updateIdAlumno2(
		codigo in NUMERIC,
		anhovig in NUMERIC,
		tipo_IdOld in NUMERIC,
		num_IdOld in NUMERIC,
		tipo_IdNew in NUMERIC,
		num_IdNew in NUMERIC,
		apellido1 in VARCHAR,
		apellido2 in VARCHAR,
		nombre1 in VARCHAR,
		nombre2 in VARCHAR,
		estado in NUMERIC,
		dd_exp in NUMERIC,
		dm_exp in NUMERIC,
		genero in NUMERIC
	);
	
END PK_INTEGRACION2;
/

--CUERPO DEL PAQUETE  
CREATE OR REPLACE PACKAGE BODY PK_INTEGRACION2 AS

   	PROCEDURE updateIdAlumno2(
		codigo in NUMERIC,
		anhovig in NUMERIC,
		tipo_IdOld in NUMERIC,
		num_IdOld in NUMERIC,
		tipo_IdNew in NUMERIC,
		num_IdNew in NUMERIC,
		apellido1 in VARCHAR,
		apellido2 in VARCHAR,
		nombre1 in VARCHAR,
		nombre2 in VARCHAR,
		estado in NUMERIC,
		dd_exp in NUMERIC,
		dm_exp in NUMERIC,
		genero in NUMERIC
	) is
 		cont number(2);
		anhoreal number(4);
	 BEGIN
	    cont:=0;
		--calcular el año real 
		SELECT to_number(VALOR) into anhoreal FROM NOTAS_PARAMETRO where NOMBRE='VIGENCIA';
		if anhovig=anhoreal then
			--insertar el registro realmente a la tabla NOTAS_INTEGRACION    
			INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14) VALUES (
			 id_Notas_integracion.nextval,
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
		end if;		
    END updateIdAlumno2;  

	PROCEDURE insertAlumno2(codigo in NUMERIC,anhovig in NUMERIC,inst in NUMERIC,jorn in NUMERIC,gra in NUMERIC,gru in NUMERIC,dd_exp in NUMERIC,dm_exp in NUMERIC,tipo_Id in NUMERIC,num_Id in NUMERIC,apellido1 in VARCHAR,apellido2 in VARCHAR,nombre1 in VARCHAR,nombre2 in VARCHAR,estado in NUMERIC,genero in NUMERIC,fecha_naci in date,dd_naci in NUMERIC,dm_naci in NUMERIC)is
  		cont number(2);
		metod number(2);
		consSede number(18);
		sede number(3);
		anho number(4);
		grupo varchar(10);
		anhoreal number(4);
    BEGIN
	    cont:=0;
		metod:=1;
	    sede:=0;
		--calcular el año real 
		SELECT to_number(VALOR) into anhoreal FROM NOTAS_PARAMETRO where NOMBRE='VIGENCIA';
		if anhovig=anhoreal then  
			--calcular la metodologia del colegio  
			SELECT count(METODOLOGIA) into cont FROM INST_JORN_GRAD_METO where IN_CODI_ID=inst and JR_CODI_ID=jorn and GR_CODI_ID=gra;
			if cont<>0 then
				SELECT min(METODOLOGIA) into metod FROM INST_JORN_GRAD_METO where IN_CODI_ID=inst and JR_CODI_ID=jorn and GR_CODI_ID=gra;		
			end if;
			--calcular la sede del colegio 
			cont:=0;      
			SELECT count(IG_CONS_SEDE) into cont FROM INST_GRUP where IN_CODI_ID=inst and JR_CODI_ID=jorn and GR_CODI_ID=gra and GP_CODI_ID=gru  and ig_ao=anhoreal and  IG_CONS_SEDE is not null;
			if cont<>0 then
				SELECT IG_CONS_SEDE,IG_GRUP into consSede,grupo
				FROM INST_GRUP
				where IN_CODI_ID=inst
				and JR_CODI_ID=jorn
				and GR_CODI_ID=gra
				and GP_CODI_ID=gru
 				and ig_ao=anhoreal
				and IG_CONS_SEDE is not null;
				sede:=case length(consSede) when 12 then (to_number(substr(lpad(consSede,12,0),12,1))) when 13 then (to_number(substr(lpad(consSede,13,0),12,2))) end;
			end if;
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
			id_Notas_integracion.nextval);
		end if;	
    END insertAlumno2;  

	PROCEDURE updateAlumno2(codigo in NUMERIC,anhovig in NUMERIC,inst in NUMERIC,jorn in NUMERIC,gra in NUMERIC,gru in NUMERIC,tipo_Id in NUMERIC,num_Id in NUMERIC,estado in NUMERIC,instOld in NUMERIC,jornOld in NUMERIC,graOld in NUMERIC,gruOld in NUMERIC)is
  	    cont number(2);
		metod number(2);
		metodOld number(2);		
		sede number(3);
		sedeOld number(3);
		consSede number(18);
		consSedeOld number(18);
		anho number(4);
		grupo varchar(10);
		anhoreal number(4);
    BEGIN
	    cont:=0;
		metod:=0;
	    sede:=0;
		--calcular el año real 
		SELECT to_number(VALOR) into anhoreal FROM NOTAS_PARAMETRO where NOMBRE='VIGENCIA';
		if anhovig=anhoreal then  
			--calcular la metodologia del colegio nuevo  
			SELECT count(METODOLOGIA) into cont FROM INST_JORN_GRAD_METO where IN_CODI_ID=inst and JR_CODI_ID=jorn and GR_CODI_ID=gra;
			if cont<>0 then
				SELECT min(METODOLOGIA) into metod FROM INST_JORN_GRAD_METO where IN_CODI_ID=inst and JR_CODI_ID=jorn and GR_CODI_ID=gra;		
			end if;
			--calcular la metodologia del colegio anterior   
			SELECT count(METODOLOGIA) into cont FROM INST_JORN_GRAD_METO where IN_CODI_ID=instOld and JR_CODI_ID=jornOld and GR_CODI_ID=graOld;
			if cont<>0 then
				SELECT min(METODOLOGIA) into metodOld FROM INST_JORN_GRAD_METO where IN_CODI_ID=instOld and JR_CODI_ID=jornOld and GR_CODI_ID=graOld;		
			end if;
			--calcular la sede del colegio  nuevo 
			cont:=0;      
			SELECT count(IG_CONS_SEDE) into cont FROM INST_GRUP where IN_CODI_ID=inst and JR_CODI_ID=jorn and GR_CODI_ID=gra and GP_CODI_ID=gru and ig_ao=anhoreal and IG_CONS_SEDE is not null;
			if cont<>0 then
				SELECT IG_CONS_SEDE,IG_GRUP into consSede,grupo
				FROM INST_GRUP
				where IN_CODI_ID=inst
				and JR_CODI_ID=jorn
				and GR_CODI_ID=gra
				and GP_CODI_ID=gru
				and ig_ao=anhoreal
				and IG_CONS_SEDE is not null;
				sede:=case length(consSede) when 12 then (to_number(substr(lpad(consSede,12,0),12,1))) when 13 then (to_number(substr(lpad(consSede,13,0),12,2))) end;
			end if;
			--calcular la sede del colegio anterior    
			cont:=0;      
			SELECT count(IG_CONS_SEDE) into cont FROM INST_GRUP where IN_CODI_ID=instOld and JR_CODI_ID=jornOld and GR_CODI_ID=graOld and GP_CODI_ID=gruOld and ig_ao=anhoreal and IG_CONS_SEDE is not null;
			if cont<>0 then
				SELECT IG_CONS_SEDE into consSedeOld
				FROM INST_GRUP
				where IN_CODI_ID=instOld
				and JR_CODI_ID=jornOld
				and GR_CODI_ID=graOld
				and GP_CODI_ID=gruOld
				and ig_ao=anhoreal
				and IG_CONS_SEDE is not null;
				sedeOld:=case length(consSedeOld) when 12 then (to_number(substr(lpad(consSedeOld,12,0),12,1))) when 13 then (to_number(substr(lpad(consSedeOld,13,0),12,2))) end;
			end if;
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
			id_Notas_integracion.nextval
			);
		end if;			
    END updateAlumno2;  
END PK_INTEGRACION2;
/
commit;

--select * from user_errors
