-----SEDE        
drop table notas_a1_archivo3_3;

create table notas_a1_archivo3_3
as
select matribog2.inst_sede.*, 
(case length(IS_CONS_SEDE) when 12 then (to_number(substr(lpad(IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(IS_CONS_SEDE,13,0),12,2))) end) as sedcodigo, 
inscodigo,
1 as sedtipo, 
inscodlocal as nucleo
from matribog2.inst_sede, 
institucion_2
where IN_CODI_ID= inscoddane
and IS_ESTA_SEDE like 'A'
and IS_CONS_SEDE is not null
and IS_CONS_SEDE <>0
and (inscodigo, (case length(IS_CONS_SEDE) when 12 then (to_number(substr(lpad(IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(IS_CONS_SEDE,13,0),12,2))) end), IS_DANE_SEDE) not in (
select sedcodins, sedcodigo, sedcoddaneanterior
from sede_2);

commit;

update notas_a1_archivo3_3
set sedcodigo=nvl(sedcodigo,0),
IS_DANE_SEDE=nvl(IS_DANE_SEDE,0),
IS_NOMB=trim(IS_NOMB);
  
commit;  

update notas_a1_archivo3_3
set sedcodigo=to_number(substr(IS_NOMB,1,(case when (substr(IS_NOMB,2,1)='_') then 1 else 2 end)))
where sedcodigo=0
and substr(IS_NOMB,1,2) not like 'CA';

COMMIT;

DELETE FROM notas_a1_archivo3_3 where
sedcodigo not in(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19)

COMMIT;

insert into sede_2
(sedcodins, sedcodigo, sedcoddaneanterior, sednombre, sedtipo)
select distinct inscodigo, sedcodigo, IS_DANE_SEDE, IS_NOMB, sedtipo
from notas_a1_archivo3_3;

COMMIT;

drop sequence codigo_jerarquia;

select max(g_jercodigo)+1 from g_jerarquia;

create sequence codigo_jerarquia start with 2160 increment by 1;

insert into g_jerarquia_2
(g_jertipo, g_jernivel, g_jerdepto, g_jermunic, g_jerlocal, g_jerinst, g_jersede, g_jercodigo)
select 1, 5, b.INSCODDEPTO, b.INSCODMUN, b.INSCODLOCAL, a.inscodigo, a.sedcodigo, codigo_jerarquia.nextval
from notas_a1_archivo3_3 a,institucion_2 b
where a.inscodigo=b.inscodigo;

commit;

-----ESTUDIANTES QUE CUMPLEN LAS CONDICIONES PARA SIGES    

drop table notas_alumno_3;

create table notas_alumno_3 as
select a.al_nume_id, a.ti_codi_id, a.in_codi_id, a.jr_codi_id,a.gr_codi_id, a.gp_codi_id, a.es_codi_id, a.pe_codi_id,a.al_prim_apel, 
	   a.al_segu_apel,a.al_prim_nomb,a.al_segu_nomb,a.al_gene, a.al_fech_naci, a.al_ao_esta,a.al_fech_esta,
	   1 as IS_CONS_SEDE,
	   1 as codigo_metodologia, 
	   c.inscodigo, 
	   0 as sedcodigo, 
	   0 as jercodgrupo, 
	   a.DD_CODI_ID_EXPU, 
	   a.DM_CODI_ID_EXPU,
	   '                                                                                                    ' as is_nomb
from matribog2.alumno a,
institucion_2 c
where a.ES_CODI_ID between 100 and 199
and a.AL_AO_ESTA = 2006
and a.in_codi_id = c.inscoddane
and c.insestado = 'A'

create unique index pk_notas_alumno_3 on notas_alumno_3(ti_codi_id, al_nume_id);

UPDATE notas_alumno_3
SET is_nomb=(
SELECT (IS_NOMB) FROM bogota.inst_grup A
WHERE 
A.IN_CODI_ID=notas_alumno_3.in_codi_id                  
AND A.JR_CODI_ID=notas_alumno_3.jr_codi_id                   
AND A.GR_CODI_ID=notas_alumno_3.gr_codi_id                  
AND A.GP_CODI_ID=notas_alumno_3.gp_codi_id
and IG_AO=2006
group by a.IS_NOMB
);

commit;


UPDATE notas_alumno_3 SET is_nomb=trim(is_nomb) where 1=1;


UPDATE notas_alumno_3 
SET (sedcodigo)=(
SELECT ((case length(a.IS_CONS_SEDE) when 12 then (to_number(substr(lpad(a.IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(a.IS_CONS_SEDE,13,0),12,2))) end)) 
FROM bogota.inst_sede A
WHERE A.IN_CODI_ID=notas_alumno_3.in_codi_id
and trim(a.IS_NOMB) like trim(notas_alumno_3.is_nomb)                   
group by ((case length(a.IS_CONS_SEDE) when 12 then (to_number(substr(lpad(a.IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(a.IS_CONS_SEDE,13,0),12,2))) end))  
);

commit;

--cambio para poner codigo de sede 1 a las sedes de tipo consecion

UPDATE notas_alumno_3 
SET sedcodigo=1
WHERE inscodigo in(
select inscodigo from institucion_2,inst_nuevas
where  inscoddane=in_codi_id
) 

--cambio para poner el grupo 1 por defecto a los niños de las instituciones de tipo consecion
UPDATE notas_alumno_3 
SET gp_codi_id=1
WHERE inscodigo in(
select inscodigo from institucion_2,inst_nuevas
where  inscoddane=in_codi_id
) 

--AGREGAR UN CAMPOR LLAMADO GRUPO Y LUEGO PONER EL NOMBRE DEL GRUPO

UPDATE notas_alumno_3
SET grupo=(
SELECT (IG_GRUP) FROM bogota.inst_grup A
WHERE 
A.IN_CODI_ID=notas_alumno_3.in_codi_id                  
and trim(a.IS_NOMB) like trim(notas_alumno_3.is_nomb)                   
AND A.JR_CODI_ID=notas_alumno_3.jr_codi_id                   
AND A.GR_CODI_ID=notas_alumno_3.gr_codi_id                  
AND A.GP_CODI_ID=notas_alumno_3.gp_codi_id
and IG_AO=2006
);



SELECT COUNT(*) FROM notas_alumno_3 WHERE GRUPO IS NULL

COMMIT;
----OBJETOS EXISTENTYES
drop table notas_objetos_existentes;

create table notas_objetos_existentes as
select distinct g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado, g_jergrupo 
from estudiante, g_jerarquia
where estgrupo = g_jercodigo;

commit;

drop table notas_objetos_nuevos_3;

create table notas_objetos_nuevos_3 as
select distinct inscodigo, 
sedcodigo, 
jr_codi_id as jornada, 
codigo_metodologia as metod, 
gr_codi_id as grado, 
gp_codi_id as grupo
from notas_alumno_3;

commit;

--JORNADAS   
drop table notas_jorn_nuevas_3;

create table notas_jorn_nuevas_3 as
select distinct inscodigo, jornada
from notas_objetos_nuevos_3
where (inscodigo, jornada) not in (
select jorcodins, jorcodigo
from jornada_2);

commit;

insert into jornada_2
(jorcodins, jorcodigo)
select inscodigo, jornada
from notas_jorn_nuevas_3;

commit;

--SEDES JORNADAS
drop table notas_sedes_jorn_nuevas_3;

create table notas_sedes_jorn_nuevas_3
as
select distinct inscodigo, sedcodigo, jornada
from notas_objetos_nuevos_3
where (inscodigo, sedcodigo, jornada) not in (
select sedjorcodinst, sedjorcodsede, sedjorcodjor
from sede_jornada_3);

commit;


insert into sede_jornada_3
(sedjorcodinst, sedjorcodsede, sedjorcodjor)
select inscodigo, sedcodigo, jornada
from notas_sedes_jorn_nuevas_3
where sedcodigo is not null;

commit;

drop sequence codigo_jerarquia

select max(g_jercodigo)+1
from g_jerarquia

create sequence codigo_jerarquia start with ? increment by 1

insert into g_jerarquia_3
(g_jertipo, g_jernivel, g_jerdepto, g_jermunic, g_jerlocal, g_jerinst, g_jersede, g_jerjorn, g_jercodigo)
select 1, 6, inscoddepto, inscodmun, inscodlocal, s.inscodigo, sedcodigo, jornada, codigo_jerarquia.nextval
from notas_sedes_jorn_nuevas_3 s, institucion_2 i
where s.inscodigo = i.inscodigo
and sedcodigo is not null;

commit;

---NUEVAS METODOLOGIAS   

drop table notas_metod_nuevas_3;

create table notas_metod_nuevas_3
as
select distinct inscodigo, metod
from notas_objetos_nuevos_3
where (inscodigo, metod) 
not in (select metcodinst, metcodigo
from metodologia_2);

commit;

insert into metodologia
(metcodinst, metcodigo)
select inscodigo, metod
from notas_metod_nuevas;

commit;

select count(*) from g_jerarquia where g_jertipo=1 and g_jernivel=9
--EN LA TABLA DE JERARQUIAS SE INSERTA AUTOMATICAMENTE    
-------------CREAR NUEVOS GRADOS   
drop table notas_grados_nuevos_3;

create table notas_grados_nuevos_3
as
select distinct inscodigo, metod, grado
from notas_objetos_nuevos_3
where (inscodigo, metod, grado) not in (
select gracodinst, gracodmetod, gracodigo
from grado_3);

commit;

insert into grado_3
(gracodinst, gracodmetod, gracodigo, granombre, graorden)
select inscodigo, metod, grado, G_GRANOMBRE, G_GRAORDEN
from notas_grados_nuevos_3, G_GRADO_3
where G_GRACODIGO = grado;

commit;


----NUEVOS SEDE_JORN_METOD_GRADOS   
drop table notas_s_j_m_g_nuevos_3;

create table notas_s_j_m_g_nuevos_3
as
select distinct inscodigo, sedcodigo, jornada, metod, grado
from notas_objetos_nuevos_3
where (inscodigo, sedcodigo, jornada, metod, grado) not in (
select g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado
from g_jerarquia_3
where g_jertipo = 1
and g_jernivel = 7);

commit;

drop sequence codigo_jerarquia;

select max(g_jercodigo)+1
from g_jerarquia_3

create sequence codigo_jerarquia start with ? increment by 1

insert into g_jerarquia_3
(g_jertipo, g_jernivel, g_jerdepto, g_jermunic, g_jerlocal, g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado, g_jercodigo)
select 1, 7, inscoddepto, inscodmun, inscodlocal, s.inscodigo, sedcodigo, jornada, metod, grado, codigo_jerarquia.nextval
from notas_s_j_m_g_nuevos_3 s, institucion_2 i
where s.inscodigo = i.inscodigo;

commit;

---GRUPOS

drop table notas_grupos_nuevos_3;

create table notas_grupos_nuevos_3
as
select distinct inscodigo, sedcodigo, jornada, metod, grado, grupo
from notas_objetos_nuevos_3
where (inscodigo, sedcodigo, jornada, metod, grado, grupo) not in (
select g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado, g_jergrupo
from g_jerarquia_3
where g_jertipo = 1
and g_jernivel = 8)
and grupo <> 0;

commit;

---grupos con nombre de grupo que sale de inst_grup

drop table notas_grupos_nuevos_3;

create table notas_grupos_nuevos_3
as
select distinct inscodigo, sedcodigo, jornada, metod, grado, grupo,NVL(NOMGRUPO,TO_CHAR(grupo)) AS nomgrupo
from notas_objetos_nuevos_3_BACKUP
where (inscodigo, sedcodigo, jornada, metod, grado, grupo) not in (
select g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado, g_jergrupo
from J_g_jerarquia
where g_jertipo = 1
and g_jernivel = 8)
and grupo <> 0;

commit;

---
--insercion de grupo con nombre

insert into j_grupo
(grucodjerar, grucodigo, grunombre)
select g_jercodigo, grupo, nomgrupo
from notas_grupos_nuevos_3, j_g_jerarquia
where g_jertipo = 1
and g_jernivel = 7
and g_jerinst = inscodigo
and g_jersede = sedcodigo
and g_jerjorn = jornada
and g_jermetod = metod
and g_jergrado = grado;

commit;
-----

insert into grupo_3
(grucodjerar, grucodigo, grunombre)
select g_jercodigo, grupo, DECODE(grupo, 99, 'UNICO', lpad(grupo,2,'0'))
from notas_grupos_nuevos_3, g_jerarquia_3
where g_jertipo = 1
and g_jernivel = 7
and g_jerinst = inscodigo
and g_jersede = sedcodigo
and g_jerjorn = jornada
and g_jermetod = metod
and g_jergrado = grado;

commit;

delete from notas_objetos_nuevos_3_BACKUP
where (inscodigo,sedcodigo,jornada,metod,grado,grupo)
in (
SELECT inscodigo,sedcodigo,jornada,metod,grado,grupo
FROM notas_objetos_nuevos_3_BACKUP
GROUP BY (inscodigo,sedcodigo,jornada,metod,grado,grupo) 
HAVING COUNT(*)>1)
and NOMGRUPO is null

drop sequence codigo_jerarquia

select max(g_jercodigo)+1
from g_jerarquia_3

create sequence codigo_jerarquia start with ? increment by 1

insert into g_jerarquia_3
(g_jertipo, g_jernivel, g_jerdepto, g_jermunic, g_jerlocal, g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado, g_jergrupo, g_jercodigo)
select 1, 8, inscoddepto, inscodmun, inscodlocal, s.inscodigo, sedcodigo, jornada, metod, grado, grupo, codigo_jerarquia.nextval
from notas_grupos_nuevos_3 s, institucion_2 i
where s.inscodigo = i.inscodigo;

commit;

---voy aca
----------ACTUALIZAR GRUPO DE ESTUDIANTES     

update notas_alumno_3
set jercodgrupo = (
select g_jercodigo
from j_g_jerarquia a
where a.g_jertipo = 1
and a.g_jernivel = 8
and a.g_jerinst = notas_alumno_3.inscodigo
and a.g_jersede = notas_alumno_3.sedcodigo
and a.g_jerjorn = notas_alumno_3.jr_codi_id
and a.g_jermetod = notas_alumno_3.codigo_metodologia
and a.g_jergrado = notas_alumno_3.gr_codi_id
and a.g_jergrupo = notas_alumno_3.gp_codi_id)
where notas_alumno_3.gp_codi_id <> 0;


update notas_alumno_3
set jercodgrupo = (
select g_jercodigo
from g_jerarquia_3
where g_jertipo = 1
and g_jernivel = 8
and g_jerinst = inscodigo
and g_jersede = sedcodigo
and g_jerjorn = jr_codi_id
and g_jermetod = codigo_metodologia
and g_jergrado = gr_codi_id
and g_jergrupo = gp_codi_id)
where gp_codi_id <> 0;

commit;

update notas_alumno_3
set jercodgrupo = (
select g_jercodigo
from j_g_jerarquia a
where a.g_jertipo = 1
and a.g_jernivel = 8
and a.g_jerinst = notas_alumno_3.inscodigo
and a.g_jersede = notas_alumno_3.sedcodigo
and a.g_jerjorn = notas_alumno_3.jr_codi_id
and a.g_jermetod = notas_alumno_3.codigo_metodologia
and a.g_jergrado = notas_alumno_3.gr_codi_id
and a.g_jergrupo = notas_alumno_3.gp_codi_id)
where notas_alumno_3.gp_codi_id <> 0
and notas_alumno_3.inscodigo>700
and notas_alumno_3.inscodigo<=700


drop table notas_estudiantes_nuevos_3;

create table notas_estudiantes_nuevos_3
as
select al_nume_id, ti_codi_id, in_codi_id, jr_codi_id,gr_codi_id, gp_codi_id, es_codi_id, pe_codi_id,al_prim_apel, 
	   al_segu_apel,al_prim_nomb,al_segu_nomb,al_gene, al_fech_naci, al_ao_esta,al_fech_esta,is_cons_sede,
	   codigo_metodologia, inscodigo, sedcodigo, jercodgrupo, 
	   DD_CODI_ID_EXPU, DM_CODI_ID_EXPU
from notas_alumno_3
where (ti_codi_id, al_nume_id) not in (
select esttipodoc, estnumdoc
from estudiante);

commit;

--  INSERTAR ESTUDIANTES QUE QUEDARON EN notas_estudiantes_nuevos_3

drop sequence codigo_estudiante

select max(estcodigo)+1 from estudiante

create sequence codigo_estudiante start with 947447 increment by 1

	
insert into estudiante(
esttipodoc, 
estnumdoc, 
estnombre1, 
estnombre2, 
estapellido1, 
estapellido2, 
estestado, 
estgenero, 
estfechanac, 
estexpdoccoddep,
estexpdoccodmun, 
estcodigo, 
estgrupo,
ESTDISCAPACIDAD,
ESTVIGENCIA)
select ti_codi_id, 
al_nume_id, 
al_prim_nomb,
al_segu_nomb, 
al_prim_apel, 
al_segu_apel, 
es_codi_id, 
(case when al_gene like 'F' then 2 else 1 end), 
al_fech_naci, 
nvl(DD_CODI_ID_EXPU,0), 
nvl(DM_CODI_ID_EXPU,0), 
codigo_estudiante.nextval, 
nvl(jercodgrupo,0),
pe_codi_id,
2006
from notas_estudiantes_nuevos_3;

commit;
