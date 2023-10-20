--TRIGGERS QUE SE EJECUTAN AL EFECTUARSE EXITOSAMENTE LAS TRANSACCIONES DESDE LA APLICACION DE MATRICULAS   
-- TODOS SON DE TIPO AFTER. NO SE UTILIZA NINGUNO BEFORE PORQUE LAS REGLAS DE ATHENEA TECNOLOGIA ASI LO DISPOCIERON  

--PARA LA INSERCION DE INSTITUCION   
CREATE OR REPLACE TRIGGER TRG_insertInstitucion AFTER INSERT ON INSTITUCION
	FOR EACH ROW
	BEGIN
		INSERT INTO NOTAS_INTEGRACION (CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, ID) VALUES (
		1,
		1,
		:new.IN_CODI_ID,
		:new.LC_CODI_ID,
		:new.IN_NOMB,
		:new.IN_ESTA,
		:new.TN_CODI_ID,
		:new.IN_DIRE_COMP,
		id_Notas_integracion.nextval);	
	END;
/


--PARA LA ACTUALIZACION DE INSTITUCION   
CREATE OR REPLACE TRIGGER TRG_updateInstitucion AFTER UPDATE OF
	   IN_CODI_ID,LC_CODI_ID,IN_NOMB,IN_ESTA,TN_CODI_ID,IN_DIRE_COMP 
	   ON INSTITUCION
	FOR EACH ROW
	BEGIN
		INSERT INTO NOTAS_INTEGRACION (CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6,P7,ID) VALUES (
		1,
		2,
		:old.IN_CODI_ID,
		:new.IN_CODI_ID,		
		:new.LC_CODI_ID,
		:new.IN_NOMB,
		:new.IN_ESTA,
		:new.TN_CODI_ID,
		:new.IN_DIRE_COMP,
		id_Notas_integracion.nextval);
	END;
/

--PARA LA ELIMINACION DE INSTITUCION   
CREATE OR REPLACE TRIGGER TRG_deleteInstitucion AFTER DELETE ON INSTITUCION
	FOR EACH ROW
	BEGIN
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2) VALUES (
		id_Notas_integracion.nextval,
		1,
		6,
		:old.IN_CODI_ID,		
		:old.LC_CODI_ID
		);
	END;
/

--PARA LA INSERCION DE SEDE   
CREATE OR REPLACE TRIGGER TRG_insertSede AFTER INSERT ON INST_SEDE
	FOR EACH ROW
	BEGIN
		INSERT INTO NOTAS_INTEGRACION (CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7,ID) VALUES ( 
		2, 
		1, 
		:new.IN_CODI_ID,
		:new.IS_DANE_SEDE,
		case length(:new.IS_CONS_SEDE) when 12 then (to_number(substr(lpad(:new.IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(:new.IS_CONS_SEDE,13,0),12,2))) end,
		:new.IS_NOMB,
		:NEW.IS_DIRE_SEDE, 
		:NEW.IS_TELE_SEDE, 
		:NEW.IS_ESTA_SEDE, 
		id_Notas_integracion.nextval);
	END;
/


--PARA LA ACTUALIZACION DE LA SEDE    
CREATE OR REPLACE TRIGGER TRG_updateSede AFTER UPDATE OF IN_CODI_ID,IS_DANE_SEDE,IS_CONS_SEDE,IS_NOMB,IS_ESTA_SEDE,IS_DIRE_SEDE,IS_TELE_SEDE ON INST_SEDE
	   FOR EACH ROW
	   BEGIN
		INSERT INTO NOTAS_INTEGRACION (CATEGORIA, FUNCION, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10,ID) VALUES ( 
		2, 
		2, 
		:old.IN_CODI_ID,
		:new.IN_CODI_ID,
		:old.IS_DANE_SEDE,
		:new.IS_DANE_SEDE,
		case length(:old.IS_CONS_SEDE) when 12 then (to_number(substr(lpad(:old.IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(:old.IS_CONS_SEDE,13,0),12,2))) end,
		case length(:new.IS_CONS_SEDE) when 12 then (to_number(substr(lpad(:new.IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(:new.IS_CONS_SEDE,13,0),12,2))) end,
		:new.IS_NOMB,
		:NEW.IS_DIRE_SEDE, 
		:NEW.IS_TELE_SEDE, 
		:new.IS_ESTA_SEDE,		 
		id_Notas_integracion.nextval);
	   END;
/

--PARA LA ELIMINACION DE SEDE   
CREATE OR REPLACE TRIGGER TRG_deleteSede AFTER DELETE ON INST_SEDE
	FOR EACH ROW
	BEGIN
		INSERT INTO NOTAS_INTEGRACION (ID,CATEGORIA, FUNCION, P1, P2, P3) VALUES ( 
		id_Notas_integracion.nextval,
		2, 
		7, 
		:old.IN_CODI_ID,
		:old.IS_DANE_SEDE,
		case length(:old.IS_CONS_SEDE) when 12 then (to_number(substr(lpad(:old.IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(:old.IS_CONS_SEDE,13,0),12,2))) end
		);
	END;
/


--validados hacia arriba 
---PARA EL INGRESO DE UN ALUMNO      
CREATE OR REPLACE TRIGGER TRG_insertAlumno AFTER INSERT ON ALUMNO
	FOR EACH ROW
	BEGIN
		PK_INTEGRACION2.insertAlumno2(
		:new.AL_CODI_ID,:new.AL_AO_ESTA,
		:new.IN_CODI_ID,
		:new.JR_CODI_ID,
		nvl(:new.GR_CODI_ID,-9),
		nvl(:new.GP_CODI_ID,0),
		nvl(:new.DD_CODI_ID_EXPU,-9), 
		nvl(:new.DM_CODI_ID_EXPU,-9),
		:new.TI_CODI_ID,
		:new.AL_NUME_ID,
		:new.AL_PRIM_APEL,
		:new.AL_SEGU_APEL,
		:new.AL_PRIM_NOMB,
		:new.AL_SEGU_NOMB,
		:new.ES_CODI_ID,
		(case upper(:new.AL_GENE) when 'M' then 1 else 2 end),
		to_char(:new.AL_FECH_NACI,'dd/mm/yyyy'),
		nvl(:new.DD_CODI_ID_NACE,-9), 
		nvl(:new.DM_CODI_ID_NACE,-9));
	END;
/


---PARA LA ACUTALIZACION DE LA UBICACION DEL ALUMNO        
CREATE OR REPLACE TRIGGER TRG_updateUbicacionAlumno AFTER UPDATE OF
IN_CODI_ID,
JR_CODI_ID,
GR_CODI_ID,
GP_CODI_ID
ON ALUMNO
	FOR EACH ROW
	BEGIN
		PK_INTEGRACION2.updateAlumno2(
		:new.AL_CODI_ID,:new.AL_AO_ESTA,
		:new.IN_CODI_ID,
		:new.JR_CODI_ID,
		nvl(:new.GR_CODI_ID,-9),
		nvl(:new.GP_CODI_ID,0),
		:new.TI_CODI_ID,
		:new.AL_NUME_ID,
		:new.ES_CODI_ID,
		:old.IN_CODI_ID,
		:old.JR_CODI_ID,
		nvl(:old.GR_CODI_ID,-9),
		nvl(:old.GP_CODI_ID,0));
	END;
/

-- PARA LA ACTUALIZACION DEL TIPO Y  / O NUMERO DE DOCUMENTO DEL ESTUDIANTE    
CREATE OR REPLACE TRIGGER TRG_updateIdAlumno AFTER UPDATE OF
TI_CODI_ID,
AL_NUME_ID,
AL_PRIM_APEL,
AL_SEGU_APEL,
AL_PRIM_NOMB,
AL_SEGU_NOMB,
DD_CODI_ID_EXPU,
DM_CODI_ID_EXPU,
AL_GENE
ON ALUMNO
	FOR EACH ROW
	BEGIN
		PK_INTEGRACION2.updateIdAlumno2(
		:new.AL_CODI_ID,
		:new.AL_AO_ESTA,
		:old.TI_CODI_ID,
		:old.AL_NUME_ID,
		:new.TI_CODI_ID,
		:new.AL_NUME_ID,
		:new.AL_PRIM_APEL,
		:new.AL_SEGU_APEL,
		:new.AL_PRIM_NOMB,
		:new.AL_SEGU_NOMB,
		:new.ES_CODI_ID,
		nvl(:new.DD_CODI_ID_EXPU,0),
		nvl(:new.DM_CODI_ID_EXPU,0),
		(case upper(:new.AL_GENE) when 'M' then 1 else 2 end)
		);
		
	END;
/



