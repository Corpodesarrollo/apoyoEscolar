/*<TOAD_FILE_CHUNK>*/
CREATE OR REPLACE PACKAGE Pk_Rotacion2 AS
	PROCEDURE generarRotacion(rotId IN NUMERIC);
	PROCEDURE setLog(rotId IN NUMERIC,sms IN VARCHAR,v1 IN NUMERIC,v2 IN NUMERIC,v3 IN NUMERIC);
	PROCEDURE setHorarioNuevo(rotId IN NUMERIC,rotInstitucion IN NUMERIC,rotSede IN NUMERIC,rotJornada IN NUMERIC,rotMetodologia IN NUMERIC,rotVigencia IN NUMERIC);
	PROCEDURE actualizarEstadoGrupos(rotId IN NUMERIC,rotInstitucion IN NUMERIC,rotSede IN NUMERIC,rotJornada IN NUMERIC,rotMetodologia IN NUMERIC,rotGrado IN NUMERIC,rotGrupo IN NUMERIC,rotUsuario IN NUMERIC,rotVigencia IN NUMERIC);
    PROCEDURE listaAsignatura(rotId IN NUMERIC,rotInstitucion IN NUMERIC,rotSede IN NUMERIC,rotJornada IN NUMERIC,rotMetodologia IN NUMERIC,rotVigencia IN NUMERIC,rotUsuario IN NUMERIC);

END Pk_Rotacion2;
/
/*<TOAD_FILE_CHUNK>*/

CREATE OR REPLACE PACKAGE BODY Pk_Rotacion2 AS
	PROCEDURE generarRotacion(rotId IN NUMERIC) IS
		cont NUMBER(3);
		rotInstitucion NUMBER(11);
		rotSede NUMBER(3);
		rotJornada NUMBER(3);
		rotMetodologia NUMBER(3);
		rotGrado NUMBER(3);
		rotGrupo NUMBER(3);
		rotVigencia NUMBER(3);
		rotUsuario VARCHAR2(20);
		BEGIN
		cont:=0;
		--INICIAL EL PROCESO 
		--setLog(idR,'Inicial el proceso',NULL,NULL,NULL);
		setLog(rotId,'Inicia el proceso',0,0,0);
		--traer los datos de la peticion
		SELECT DAROTINST, DAROTSEDE, DAROTJOR, DAROTMET, DAROTGRADO, DAROTGRUPO,  DAROTUSUARIO, DAROTVIGENCIA 
		INTO rotInstitucion,rotSede,rotJornada,rotMetodologia,rotGrado,rotGrupo,rotUsuario,rotVigencia
		FROM DATOS_ROTACION WHERE DAROTID=rotId;
		--borrar horario actual
		setHorarioNuevo(rotId,rotInstitucion,rotSede,rotJornada,rotMetodologia,rotVigencia);
		--actualizar el estado de los grupos para bloquearlos
		actualizarEstadoGrupos(rotId,rotInstitucion,rotSede,rotJornada,rotMetodologia,rotGrado,rotGrupo,rotUsuario,rotVigencia);
		--Calcular la lista de Asignaturas a procesar
        --Iterar por cada asignatura
        --Calcular la lista de grupos a procesar
        --Iterar por cada grupo
        --Calcular la lista de docentes a procesar
        --Iterar por cada docente
        --Buscar el hueco en horario (usando combinacion) de dias
        --Buscar y asignar espacio 
		--Fin de iteracion de Docente
        --Fin de iteracion de Grupo
        --Fin de iteracion de Asignatura
        
		
	END generarRotacion;

	PROCEDURE setLog(rotId IN NUMERIC,sms IN VARCHAR,v1 IN NUMERIC,v2 IN NUMERIC,v3 IN NUMERIC) IS
		BEGIN
		INSERT INTO LOG_ROTACION2 (ID, CONT, MENSAJE, VALOR1, VALOR2, VALOR3) VALUES (rotId,LOG_ROTACION2_SEQ.NEXTVAL,sms ,v1,v2,v3);
	END setLog;
	
	PROCEDURE setHorarioNuevo(rotId IN NUMERIC,rotInstitucion IN NUMERIC,rotSede IN NUMERIC,rotJornada IN NUMERIC,rotMetodologia IN NUMERIC,rotVigencia IN NUMERIC) IS
		BEGIN
		--borrar horario propuesto
		DELETE FROM HORARIO_PROPUESTO WHERE HORGRUPO IN(SELECT G_JERCODIGO FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=8 AND G_JERINST=rotInstitucion AND G_JERSEDE=rotSede AND G_JERJORN=rotJornada AND G_JERMETOD=rotMetodologia);
		--insertar horario propuesto
		INSERT INTO HORARIO_PROPUESTO SELECT HORGRUPO, HORCLASE, HORDOC1,HORASIG1, HORESP1, HORDOC2,HORASIG2, HORESP2, HORDOC3,HORASIG3, HORESP3, HORDOC4,HORASIG4, HORESP4, HORDOC5,HORASIG5, HORESP5, HORDOC6,HORASIG6, HORESP6, HORDOC7,HORASIG7, HORESP7 FROM HORARIO WHERE HORGRUPO IN(SELECT G_JERCODIGO FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=8 AND G_JERINST=rotInstitucion AND G_JERSEDE=rotSede AND G_JERJORN=rotJornada AND G_JERMETOD=rotMetodologia);
		--borrar las inconsistencia para ese id
		DELETE FROM ROT_INCONSISTENCIAS WHERE ROTINID=rotId;
		--poner en 0 la ih propuesta para todos los docentes de esa inst-sed-jor
		UPDATE ROT_DOC_ASIG_GRADO SET ROTDAGIHPROP=0 WHERE ROTDAGJERGRADO IN(SELECT G_JERCODIGO FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=7 AND G_JERINST=rotInstitucion AND G_JERSEDE=rotSede AND G_JERJORN=rotJornada) AND ROTDAGVIGENCIA=rotVigencia;
		--borra los niveles ya generados de asignacion de horario
		DELETE FROM ROT_NIVEL WHERE ROTNGRUPO IN(SELECT G_JERCODIGO FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=8 AND G_JERINST=rotInstitucion AND G_JERSEDE=rotSede AND G_JERJORN=rotJornada);
		--borra el horario temporal de backups
		DELETE FROM HORARIO_PROPUESTO_TEMP2 WHERE HORGRUPO IN(SELECT G_JERCODIGO FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=8 AND G_JERINST=rotInstitucion AND G_JERSEDE=rotSede AND G_JERJORN=rotJornada AND G_JERMETOD=rotMetodologia);
		--borra el registro de asignaturas falladas
		DELETE FROM HORARIO_PROPUESTO_TEMP3 WHERE HORGRUPO IN(SELECT G_JERCODIGO FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=8 AND G_JERINST=rotInstitucion AND G_JERSEDE=rotSede AND G_JERJORN=rotJornada AND G_JERMETOD=rotMetodologia);		 	  			  	
	END setHorarioNuevo;
	
	PROCEDURE actualizarEstadoGrupos(rotId IN NUMERIC,rotInstitucion IN NUMERIC,rotSede IN NUMERIC,rotJornada IN NUMERIC,rotMetodologia IN NUMERIC,rotGrado IN NUMERIC,rotGrupo IN NUMERIC,rotUsuario IN NUMERIC,rotVigencia IN NUMERIC) IS
		estado NUMBER(1);
		BEGIN
		estado:=0; --ESTADO EN PROCESO
		--toda la sede-jornada
		IF rotGrado=-9 AND rotGrupo=-9 THEN
		   UPDATE GRUPO SET GRUESTADO=estado,GRURESPONSABLE=rotUsuario,GRUFECHA=SYSDATE WHERE GRUCODJERAR IN(SELECT G_JERCODIGO FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=7 AND G_JERINST=rotInstitucion AND G_JERSEDE=rotSede AND G_JERJORN=rotJornada AND G_JERMETOD=rotMetodologia);
		END IF;
		--todo el grado		
		IF rotGrado<>-9 AND rotGrupo=-9 THEN
		   UPDATE GRUPO SET GRUESTADO=estado,GRURESPONSABLE=rotUsuario,GRUFECHA=SYSDATE WHERE GRUCODJERAR IN(SELECT G_JERCODIGO FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=7 AND G_JERINST=rotInstitucion AND G_JERSEDE=rotSede AND G_JERJORN=rotJornada AND G_JERMETOD=rotMetodologia AND G_JERGRADO=rotGrado);		   
		END IF;
		--todo el grupo
		IF rotGrado<>-9 AND rotGrupo<>-9 THEN
		   UPDATE GRUPO SET GRUESTADO=estado,GRURESPONSABLE=rotUsuario,GRUFECHA=SYSDATE WHERE GRUCODJERAR IN(SELECT G_JERCODIGO FROM G_JERARQUIA WHERE G_JERTIPO=1 AND G_JERNIVEL=7 AND G_JERINST=rotInstitucion AND G_JERSEDE=rotSede AND G_JERJORN=rotJornada AND G_JERMETOD=rotMetodologia AND G_JERGRADO=rotGrado) AND grucodigo=rotGrupo;		   
		END IF;
	END actualizarEstadoGrupos;

    PROCEDURE listaAsignatura(rotId IN NUMERIC,rotInstitucion IN NUMERIC,rotSede IN NUMERIC,rotJornada IN NUMERIC,rotMetodologia IN NUMERIC,rotVigencia IN NUMERIC,rotUsuario IN NUMERIC) IS
        bandera NUMBER(1);	    
		BEGIN
		bandera:=0; --
        
        
    END listaAsignatura;
		
END Pk_Rotacion2;
/
