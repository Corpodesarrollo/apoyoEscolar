CREATE OR REPLACE VIEW DATOS_ESTUDIANTE2
(CODINSTITUCIONEST, INSTITUCIONEST, ESTUCODIGO, CODGRUPOEST, JERARQUIAEST, 
 CODSEDEEST, SEDEEST, CODJORNEST, JORNADAEST, METODOLOGIACOD, 
 GRADOCOD, GRADOEST, GRUPOEST, TIPDOCEST, NUMDOCEST, 
 SEXOEST, EDADEST, NOMBRESEST, APELLIDOSEST, VIGENCIAEST, 
 FECHANACEST)
AS 
SELECT inscodigo,insnombre,estcodigo,grucodigo,estgrupo,SEDCODIGO,sednombre,gc.G_ConCodigo,gc.G_ConNombre,GRACODMETOD,gracodigo,granombre,grunombre,gc2.G_ConAbrev,estnumdoc,CASE estgenero WHEN 1 THEN 'M' ELSE 'F' END AS genero,estedad,CONCAT(EstNombre1,CONCAT(' ',EstNombre2)),CONCAT(EstApellido1,CONCAT(' ',EstApellido2)),EstVigencia,
ESTFECHANAC
FROM SEDE,G_CONSTANTE gc,G_CONSTANTE gc2,GRADO,G_JERARQUIA a,ESTUDIANTE,INSTITUCION,GRUPO,G_JERARQUIA b
WHERE a.g_jertipo=1 AND a.g_jernivel=8
AND estgrupo=a.g_jercodigo
AND inscodigo=a.g_jerinst
AND a.g_jerinst=sedcodins
AND a.g_jersede=sedcodigo
AND gc2.g_contipo=10
AND esttipodoc=gc2.G_ConCodigo
AND gc.g_contipo=5
AND a.g_jerjorn=gc.G_ConCodigo
AND gracodinst=a.g_jerinst
AND gracodmetod=a.g_jermetod
AND gracodigo=a.g_jergrado
AND grucodigo=a.g_jergrupo
AND grucodjerar=b.g_jercodigo
and b.g_jertipo=1 
AND b.g_jernivel=7 
AND b.g_jerinst=a.g_jerinst 
AND b.g_jersede=a.g_jersede 
AND b.g_jerjorn=a.g_jerjorn 
AND b.g_jergrado=a.g_jergrado 
AND b.g_jermetod=a.g_jermetod
/