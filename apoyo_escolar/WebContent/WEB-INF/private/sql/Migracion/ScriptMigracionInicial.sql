------- INSTITUCION        

create table notas_a1_archivo1
as
select bogota.institucion.*, 0 as inscodigo
from bogota.institucion
where IN_ESTA like 'A'
and IN_CODI_ID not in (
select inscoddane
from institucion);

create unique index pk_notas_a1_archivo1 on notas_a1_archivo1(IN_CODI_ID);
commit;

select count(*) from notas_a1_archivo1;


update notas_a1_archivo1
set inscodigo = notased.codigo_institucion.nextval
where IN_CODI_ID is not null;

commit;

insert into institucion 
(inscodigo, inscoddane, inscoddepto, inscodmun, inscodlocal, insnombre, inssector, insestado)
select inscodigo,IN_CODI_ID,25,LC_CODI_ID,LC_CODI_ID,IN_NOMB,TN_CODI_ID,IN_ESTA from notas_a1_archivo1;


commit;

drop sequence codigo_jerarquia;

select max(g_jercodigo)+1 from g_jerarquia;

create sequence codigo_jerarquia start with 43464 increment by 1;

insert into g_jerarquia
(g_jertipo, g_jernivel, g_jerdepto, g_jermunic, g_jerlocal, g_jerinst, g_jercodigo)
select 1, 4, 25, LC_CODI_ID,LC_CODI_ID, inscodigo, notased.codigo_jerarquia.nextval
from notas_a1_archivo1;

commit;

-----SEDE        

drop table notas_a1_archivo3;
create table notas_a1_archivo3
as
select bogota.inst_sede.*, 
(case length(IS_CONS_SEDE) when 12 then (to_number(substr(lpad(IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(IS_CONS_SEDE,13,0),12,2))) end) as sedcodigo, 
inscodigo,
1 as sedtipo, 
inscodlocal as nucleo
from bogota.inst_sede, 
institucion
where IN_CODI_ID= inscoddane
and IS_ESTA_SEDE like 'A'
and IS_CONS_SEDE is not null
and IS_CONS_SEDE <>0
and (inscodigo, (case length(IS_CONS_SEDE) when 12 then (to_number(substr(lpad(IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(IS_CONS_SEDE,13,0),12,2))) end), IS_DANE_SEDE) not in (
select sedcodins, sedcodigo, sedcoddaneanterior
from sede);

commit;

update notas_a1_archivo3
set sedcodigo=nvl(sedcodigo,0),
IS_DANE_SEDE=nvl(IS_DANE_SEDE,0),
IS_NOMB=trim(IS_NOMB);
  
commit;  

update notas_a1_archivo3
set sedcodigo=to_number(substr(IS_NOMB,1,(case when (substr(IS_NOMB,2,1)='_') then 1 else 2 end)))
where sedcodigo=0
and substr(IS_NOMB,1,2) not like 'CA';

COMMIT;

DELETE FROM notas_a1_archivo3 where
sedcodigo not in(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19)

COMMIT;

insert into sede 
(sedcodins, sedcodigo, sedcoddaneanterior, sednombre, sedtipo)
select distinct inscodigo, sedcodigo, IS_DANE_SEDE, IS_NOMB, sedtipo
from notas_a1_archivo3;

COMMIT;

drop sequence codigo_jerarquia;

select max(g_jercodigo)+1 from g_jerarquia;

create sequence codigo_jerarquia start with 43465 increment by 1;

insert into g_jerarquia
(g_jertipo, g_jernivel, g_jerdepto, g_jermunic, g_jerlocal, g_jerinst, g_jersede, g_jercodigo)
select 1, 5, b.INSCODDEPTO, b.INSCODMUN, b.INSCODLOCAL, a.inscodigo, a.sedcodigo, codigo_jerarquia.nextval
from notas_a1_archivo3 a,institucion b
where a.inscodigo=b.inscodigo;

commit;

-----ESTUDIANTES QUE CUMPLEN LAS CONDICIONES PARA SIGES    

drop table notas_alumno_2;

create table notas_alumno_2 as
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
from bogota.alumno a,
institucion_2 c
where a.ES_CODI_ID between 100 and 199
and a.AL_AO_ESTA = 2006
and a.in_codi_id = c.inscoddane
and c.insestado = 'A'

create unique index pk_notas_alumno on notas_alumno(ti_codi_id, al_nume_id);

UPDATE notas_alumno_2
SET is_nomb=(
SELECT (IS_NOMB) FROM bogota.inst_grup A
WHERE 
A.IN_CODI_ID=in_codi_id                  
AND A.JR_CODI_ID=jr_codi_id                   
AND A.GR_CODI_ID=gr_codi_id                  
AND A.GP_CODI_ID=gp_codi_id
and IG_AO=2006
group by a.IS_NOMB
)

commit;

UPDATE notas_alumno_2 SET is_nomb=trim(is_nomb) where 1;

commit;

UPDATE notas_alumno 
SET (sedcodigo)=(
SELECT ((case length(a.IS_CONS_SEDE) when 12 then (to_number(substr(lpad(a.IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(a.IS_CONS_SEDE,13,0),12,2))) end)) 
FROM bogota.inst_sede A
WHERE A.IN_CODI_ID=notas_alumno.in_codi_id
and trim(a.IS_NOMB) like trim(notas_alumno.is_nomb)                   
group by ((case length(a.IS_CONS_SEDE) when 12 then (to_number(substr(lpad(a.IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(a.IS_CONS_SEDE,13,0),12,2))) end))  
)

commit;

----OBJETOS EXISTENTYES
drop table notas_objetos_existentes;

create table notas_objetos_existentes as
select distinct g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado, g_jergrupo 
from estudiante, g_jerarquia
where estgrupo = g_jercodigo;

commit;

drop table notas_objetos_nuevos_2;

create table notas_objetos_nuevos_2 as
select distinct inscodigo, 
sedcodigo, 
jr_codi_id as jornada, 
codigo_metodologia as metod, 
gr_codi_id as grado, 
gp_codi_id as grupo
from notas_alumno_2;

commit;


-----CREAR NUEVAS JORNADAS      
drop table notas_jorn_nuevas_2;

create table notas_jorn_nuevas_2 as
select distinct inscodigo, jornada
from notas_objetos_nuevos_2
where (inscodigo, jornada) not in (
select jorcodins, jorcodigo
from jornada);

commit;

insert into jornada
(jorcodins, jorcodigo)
select inscodigo, jornada
from notas_jorn_nuevas;

commit;

-----CREAR NUEVAS SEDES JORNADAS 


drop table notas_sedes_jorn_nuevas_2;

create table notas_sedes_jorn_nuevas_2
as
select distinct inscodigo, sedcodigo, jornada
from notas_objetos_nuevos_2
where (inscodigo, sedcodigo, jornada) not in (
select sedjorcodinst, sedjorcodsede, sedjorcodjor
from sede_jornada);

commit;

insert into sede_jornada
(sedjorcodinst, sedjorcodsede, sedjorcodjor)
select inscodigo, sedcodigo, jornada
from notas_sedes_jorn_nuevas_2;

commit;

drop sequence codigo_jerarquia

select max(g_jercodigo)+1
from g_jerarquia

create sequence codigo_jerarquia start with 1434 increment by 1

insert into g_jerarquia
(g_jertipo, g_jernivel, g_jerdepto, g_jermunic, g_jerlocal, g_jerinst, g_jersede, g_jerjorn, g_jercodigo)
select 1, 6, inscoddepto, inscodmun, inscodlocal, s.inscodigo, sedcodigo, jornada, codigo_jerarquia.nextval
from notas_sedes_jorn_nuevas s, institucion i
where s.inscodigo = i.inscodigo;

commit;

--------------------------------------------------------------
---NUEVAS METODOLOGIAS   

drop table notas_metod_nuevas;

create table notas_metod_nuevas
as
select distinct inscodigo, metod
from notas_objetos_nuevos
where (inscodigo, metod) not in (
select metcodinst, metcodigo
from metodologia);

commit;

insert into metodologia
(metcodinst, metcodigo)
select inscodigo, metod
from notas_metod_nuevas;

commit;

--EN LA TABLA DE JERARQUIAS SE INSERTA AUTOMATICAMENTE    

-------------CREAR NUEVOS GRADOS   
drop table notas_grados_nuevos;

create table notas_grados_nuevos
as
select distinct inscodigo, metod, grado
from notas_objetos_nuevos
where (inscodigo, metod, grado) not in (
select gracodinst, gracodmetod, gracodigo
from grado);

commit;

insert into grado
(gracodinst, gracodmetod, gracodigo, granombre, graorden)
select inscodigo, metod, grado, g_connombre, grado
from notas_grados_nuevos, g_constante
where g_contipo = 30
and g_concodigo = grado;

commit;

----NUEVOS SEDE_JORN_METOD_GRADOS   
drop table notas_s_j_m_g_nuevos;

create table notas_s_j_m_g_nuevos
as
select distinct inscodigo, sedcodigo, jornada, metod, grado
from notas_objetos_nuevos
where (inscodigo, sedcodigo, jornada, metod, grado) not in (
select g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado
from g_jerarquia
where g_jertipo = 1
and g_jernivel = 7);

commit;

drop sequence codigo_jerarquia;

select max(g_jercodigo)+1 from g_jerarquia

create sequence codigo_jerarquia start with 43468 increment by 1

insert into g_jerarquia
(g_jertipo, g_jernivel, g_jerdepto, g_jermunic, g_jerlocal, g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado, g_jercodigo)
select 1, 7, inscoddepto, inscodmun, inscodlocal, s.inscodigo, sedcodigo, jornada, metod, grado, codigo_jerarquia.nextval
from notas_s_j_m_g_nuevos s, institucion i
where s.inscodigo = i.inscodigo;

commit;

drop table notas_grupos_nuevos;

create table notas_grupos_nuevos
as
select distinct inscodigo, sedcodigo, jornada, metod, grado, grupo
from notas_objetos_nuevos
where (inscodigo, sedcodigo, jornada, metod, grado, grupo) not in (
select g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado, g_jergrupo
from g_jerarquia
where g_jertipo = 1
and g_jernivel = 8)
and grupo <> 0;

commit;


insert into grupo
(grucodjerar, grucodigo, grunombre)
select g_jercodigo, grupo, DECODE(grupo, 99, 'UNICO', lpad(grupo,2,'0'))
from notas_grupos_nuevos, g_jerarquia
where g_jertipo = 1
and g_jernivel = 7
and g_jerinst = inscodigo
and g_jersede = sedcodigo
and g_jerjorn = jornada
and g_jermetod = metod
and g_jergrado = grado;

commit;


drop sequence codigo_jerarquia

select max(g_jercodigo)+1
from g_jerarquia

create sequence codigo_jerarquia start with 14299 increment by 1

insert into g_jerarquia
(g_jertipo, g_jernivel, g_jerdepto, g_jermunic, g_jerlocal, g_jerinst, g_jersede, g_jerjorn, g_jermetod, g_jergrado, g_jergrupo, g_jercodigo)
select 1, 8, inscoddepto, inscodmun, inscodlocal, s.inscodigo, sedcodigo, jornada, metod, grado, grupo, codigo_jerarquia.nextval
from notas_grupos_nuevos s, institucion i
where s.inscodigo = i.inscodigo;

commit;

----------ACTUALIZAR GRUPO DE ESTUDIANTES     

update notas_alumno
set jercodgrupo = (
select g_jercodigo
from g_jerarquia
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

drop table notas_estudiantes_nuevos;

create table notas_estudiantes_nuevos
as
select al_nume_id, ti_codi_id, in_codi_id, jr_codi_id,gr_codi_id, gp_codi_id, es_codi_id, pe_codi_id,al_prim_apel, 
	   al_segu_apel,al_prim_nomb,al_segu_nomb,al_gene, al_fech_naci, al_ao_esta,al_fech_esta,is_cons_sede,
	   codigo_metodologia, inscodigo, sedcodigo, jercodgrupo, 
	   DD_CODI_ID_EXPU, DM_CODI_ID_EXPU
from notas_alumno
where (ti_codi_id, al_nume_id) not in (
select esttipodoc, estnumdoc
from estudiante);

commit;
------  ACTUALIZAR DATOS BASICOS DE ESTUDIANTES ANTIGUOS    

update estudiante
set (estnombre1, estnombre2, estapellido1, estapellido2, estestado, estgenero, estfechanac, estexpdoccoddep, estexpdoccodmun,
estgrupo) = (
select al_prim_nomb,al_segu_nomb, al_prim_apel, al_segu_apel, es_codi_id, al_gene, al_fech_naci, dd_codi_id_expu, dm_codi_id_expu,
jercodgrupo
from notas_alumno
where ti_codi_id = esttipodoc
and al_nume_id = estnumdoc)
where (esttipodoc, estnumdoc) in (
select ti_codi_id, al_nume_id
from notas_alumno
where al_nume_id is not null);

commit;

----------- ACTUALIZAR CñDIGO DE GRUPO DE ESTUDIANTES ANTIGUOS  

update siges.estudiante
set estgrupo = (
select jercodgrupo
from siges2_alumno
where ti_codi_id = esttipodoc
and al_nume_id = estnumdoc
)
where siges.estudiante.estcodigo > 0;

commit;








commit;


/*
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,-2,'PreJardin');
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,-1,'Jardin I o A o Kinder');
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,0,'Jardin II o B, Transiciñn o Grado 0'   );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,1,'Primero'    );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,2,'Segundo'    );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,3,'Tercero'    );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,4,'Cuarto'   );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,5,'Quinto'    );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,6,'Sexto'    );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,7,'Sñptimo'    );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,8,'Octavo'    );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,9,'Noveno'    );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,10,'Dñcimo'    );
INSERT INTO G_CONSTANTE (G_CONTIPO, G_CONCODIGO, G_CONNOMBRE) VALUES (30,11,'Once' );
*/





  





 IN_CODI_ID                                NOT NULL NUMBER(18)                  
 IS_NOMB                                   NOT NULL VARCHAR2(200)               
 IS_DANE_SEDE                                       NUMBER(12)                  
 IS_CONS_SEDE                                       VARCHAR2(14)                
 IS_DIRE_SEDE                                       VARCHAR2(100)               
 IS_TELE_SEDE                                       VARCHAR2(50)                
 IS_ESTA_SEDE                                       VARCHAR2(1)                 



select max(length(is_nomb)) from bogota.inst_grup

select length('                                                                                                    ') from dual



select ti_codi_id,al_nume_id,count(*) from notas_alumno
group by ti_codi_id,al_nume_id
having count(*)>1



IN_CODI_ID                  
JR_CODI_ID                   
GR_CODI_ID                   
GP_CODI_ID                   
IS_NOMB                
IG_AO                   
IG_GRUP                 





commit;

desc bogota.alumno
desc bogota.inst_grup
desc bogota.inst_sede



TABLE bogota.alumno
 Name                                      Null?    Type                        
 ----------------------------------------- -------- ----------------------------
 a.AL_NUME_ID                
 a. TI_CODI_ID                   
 a. EP_CODI_ID                   
 a. AR_CODI_ID                   
 a. IP_CODI_ID                   
 a. IN_CODI_ID                  
 a. JR_CODI_ID                   
 a. GR_CODI_ID                   
 a. GP_CODI_ID                   
 a. ES_CODI_ID                   
 a. PE_CODI_ID                   
 a. AL_PRIM_APEL                
 a. AL_SEGU_APEL                
 a. AL_PRIM_NOMB                
 a. AL_SEGU_NOMB                
 a. AL_GENE                 
 a. AL_FECH_NACI                        
 a. AL_TELE_RESI                
 a. AL_ENCU_SISB                 
 a. AL_NIVE_SISB                 
 a. AL_GRUP_SANG                 
 a. AL_FACT_RH                 
 a. AL_ESTR                 
 a. AL_NOMB_ACUD                
 a. AL_EMPL_ACUD                 
 a. AL_TELE_RESI_ACUD                
 a. AL_TELE_OFIC_ACUD                
 a. AR_CODI_ID_ACUD                   
 a. EP_CODI_ID_ACUD                   
 a. IP_CODI_ID_ACUD                   
 a. AL_AO_ESTA                   
 a. LC_CODI_ID                   
 a. AL_TALE                 
 a. AL_FECH_ESTA                        
 a. TC_CODI_ID                   
 a. BR_CODI_ID                   
 a. AL_RUTA                 
 a. AL_BARR                
 a. a. AL_PROC                 
 a. AL_CEDU_ACUD                  
 a. AL_PADR_NOMB                
 a. AL_PADR_ID                  
 a. AL_PADR_TEL                
 a. AL_MADR_NOMB                
 a. AL_MADR_ID                  
 a. AL_MADR_TEL                
 a. AL_DEFC_NOMB                
 a. AL_EXTR_EDAD                 
 a. FF_CODI_ID                   
 a. AL_CONT_ESFI                 
 a. AL_PUNT_CI                 
 a. AL_INDI_DAIC                
 a. EI_CODI_ID                 
 a. TP_CODI_ID                   
 a. DD_CODI_ID_NACE                 
 a. DM_CODI_ID_NACE                 
 a. AL_TIPO_DIRE                 
 a. AL_PUNT_SISB                 
 a. DD_CODI_ID_EXPU                 
 a. DM_CODI_ID_EXPU                 
 a. AL_INDI_EXVA                 
 a. AL_INDI_CONDE                 
 a. AL_MADR_FECH                        
 a. AL_PADR_FECH                        
 a. AL_FECH_ACUD                        
 a. FC_LOGI                
 a. FC_NUME_ID                  
 a. FC_NIVE_ID                   
 a. AL_NIVE_SISB_DANE                   
 a. AL_NOMB_ASO               
 a. TE_CODI_ID                   
 a. AL_INDI_VATE                 
 a. AL_VALO_TE               
 a. AL_INDI_VAPE                 
 a. AL_VALO_PE               
 a. AL_INDI_VAVC                 
 a. AL_INDI_SEPR                 
 a. AL_FECH_MATR_REAL                        
 a. AL_SISBEN_REAL                   
 a. AL_ESTR_CALC                 
 a. AL_FUNC_LOGI                
 a. AL_INDI_SUBS                 

