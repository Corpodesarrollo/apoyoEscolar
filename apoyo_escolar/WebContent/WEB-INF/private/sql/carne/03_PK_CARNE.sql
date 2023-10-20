CREATE OR REPLACE PACKAGE Pk_Carnes AS
 PROCEDURE prepareds_carnes(codusuario IN NUMERIC,codinstitucion IN NUMERIC,codsede IN NUMERIC,codjornada IN NUMERIC, codmetodologia IN NUMERIC,codgrado IN NUMERIC,codgrupo IN NUMERIC,cedula IN VARCHAR,ano_actual IN NUMERIC);
 PROCEDURE carnes_borrar(inicial IN NUMERIC);
 PROCEDURE carnes_vaciar(inicial IN NUMERIC);
END Pk_Carnes;
/

CREATE OR REPLACE PACKAGE BODY Pk_Carnes AS
/*PAQUETE PADRE PRINCIPAL */
	PROCEDURE prepareds_carnes(codusuario IN NUMERIC,codinstitucion IN NUMERIC,codsede IN NUMERIC,codjornada IN NUMERIC, codmetodologia IN NUMERIC,codgrado IN NUMERIC,codgrupo IN NUMERIC,cedula IN VARCHAR,ano_actual IN NUMERIC) IS
		band NUMBER(4);
    BEGIN
			band := 1;
			IF(codsede<>-9 AND codjornada<>-9 AND codmetodologia<>-9  AND codgrado<>-9 AND codgrupo=-9 AND cedula IS NULL AND NVL(ano_actual,0)<>0) THEN
				INSERT INTO CARNE_ESTUDIANTE(
					 CARESTINST, CARESTINSTCODDANE, CARESTSEDE, 
					 CARESTJORN, CARESTAPELLIDOS, CARESTNOMBRES, 
					 CARESTNUMDOC, CARESTCODGRADO, CARESTGRADO, 
					 CARESTCODIGO, CARESTANO, CARESTNOMRECTOR, 
					 CARESTAPERECTOR, CARESTID, CARESTCODGRUPO, 
					 CARESTGRUPO,CARESTTIPDOC,CARESTFECNAC) 
					 SELECT INSNOMBRE,INSCODDANE,sedeest,
					 jornadaest,apellidosest,nombresest,
					 numdocest,gradocod,gradoest,
					 estucodigo,ano_actual,INSRECTORNOMBRE,
					 NULL,codusuario,CODGRUPOEST,
					 GRUPOEST,TIPDOCEST,FECHANACEST
					 FROM datos_estudiante2,INSTITUCION
					 WHERE INSCODIGO=codinstitucion
					 AND codinstitucionest=codinstitucion
					 AND CODSEDEEST=codsede
					 AND CODJORNEST=codjornada
					 AND METODOLOGIACOD=codmetodologia
					 AND gradocod=codgrado;
			ELSIF(codsede<>-9 AND codjornada<>-9 AND codmetodologia<>-9  AND codgrado<>-9 AND codgrupo<>-9 AND cedula IS NULL AND NVL(ano_actual,0)<>0) THEN
				INSERT INTO CARNE_ESTUDIANTE (
				 CARESTINST, CARESTINSTCODDANE, CARESTSEDE, 
				 CARESTJORN, CARESTAPELLIDOS, CARESTNOMBRES, 
				 CARESTNUMDOC, CARESTCODGRADO, CARESTGRADO, 
				 CARESTCODIGO, CARESTANO, CARESTNOMRECTOR, 
				 CARESTAPERECTOR, CARESTID, CARESTCODGRUPO, 
				 CARESTGRUPO,CARESTTIPDOC,CARESTFECNAC) 
				 SELECT INSNOMBRE,INSCODDANE,sedeest,
				 jornadaest,apellidosest,nombresest,
				 numdocest,gradocod,gradoest,
				 estucodigo,ano_actual,INSRECTORNOMBRE,
				 NULL,codusuario,CODGRUPOEST,
				 GRUPOEST,TIPDOCEST,FECHANACEST
				 FROM datos_estudiante2,INSTITUCION
				 WHERE INSCODIGO=codinstitucion
				 AND codinstitucionest=codinstitucion
				 AND CODSEDEEST=codsede
				 AND CODJORNEST=codjornada
				 AND METODOLOGIACOD=codmetodologia
				 AND gradocod=codgrado
				 AND codgrupoest=codgrupo;
		ELSIF(cedula IS NOT NULL AND NVL(ano_actual,0)<>0) THEN
			INSERT INTO CARNE_ESTUDIANTE (
			 CARESTINST, CARESTINSTCODDANE, CARESTSEDE, 
			 CARESTJORN, CARESTAPELLIDOS, CARESTNOMBRES, 
			 CARESTNUMDOC, CARESTCODGRADO, CARESTGRADO, 
			 CARESTCODIGO, CARESTANO, CARESTNOMRECTOR, 
			 CARESTAPERECTOR, CARESTID, CARESTCODGRUPO, 
			 CARESTGRUPO,CARESTTIPDOC,CARESTFECNAC) 
			 SELECT INSNOMBRE,INSCODDANE,sedeest,
			 jornadaest,apellidosest,nombresest,
			 numdocest,gradocod,gradoest,
			 estucodigo,ano_actual,INSRECTORNOMBRE,
			 NULL,codusuario,CODGRUPOEST,
			 GRUPOEST,TIPDOCEST,FECHANACEST
			 FROM datos_estudiante2,INSTITUCION
			 WHERE INSCODIGO=codinstitucion
			 AND codinstitucionest=codinstitucion
			 AND numdocest LIKE cedula;
		END IF;
 COMMIT;
 END prepareds_carnes;

/**********************************************************************************************************************************************************************/

 PROCEDURE carnes_borrar(inicial IN NUMERIC) IS
		band NUMBER(4);
		BEGIN
		band := 1;
		DELETE FROM CARNE_ESTUDIANTE WHERE CARESTID=inicial;
  COMMIT;
 END carnes_borrar;

/**********************************************************************************************************************************************************************/

 PROCEDURE carnes_vaciar(inicial IN NUMERIC) IS
  band NUMBER(4);
  BEGIN
  band := 1;
		DELETE FROM CARNE_ESTUDIANTE;
  COMMIT;
 END carnes_vaciar;

END Pk_Carnes;
/
