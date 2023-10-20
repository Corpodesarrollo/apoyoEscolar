UPDATE notas_alumno_2
SET is_nomb=(
SELECT (IS_NOMB) FROM bogota.inst_grup A
WHERE 
A.IN_CODI_ID=notas_alumno_2.in_codi_id                  
AND A.JR_CODI_ID=notas_alumno_2.jr_codi_id                   
AND A.GR_CODI_ID=notas_alumno_2.gr_codi_id                  
AND A.GP_CODI_ID=notas_alumno_2.gp_codi_id
and IG_AO=2006
group by a.IS_NOMB
);

commit;


UPDATE notas_alumno_2 SET is_nomb=trim(is_nomb) where 1=1;


UPDATE notas_alumno_2 
SET (sedcodigo)=(
SELECT ((case length(a.IS_CONS_SEDE) when 12 then (to_number(substr(lpad(a.IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(a.IS_CONS_SEDE,13,0),12,2))) end)) 
FROM bogota.inst_sede A
WHERE A.IN_CODI_ID=notas_alumno_2.in_codi_id
and trim(a.IS_NOMB) like trim(notas_alumno_2.is_nomb)                   
group by ((case length(a.IS_CONS_SEDE) when 12 then (to_number(substr(lpad(a.IS_CONS_SEDE,12,0),12,1))) when 13 then (to_number(substr(lpad(a.IS_CONS_SEDE,13,0),12,2))) end))  
);

commit;


select distinct sedcodigo from notas_alumno_2;


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

select * from notas_objetos_nuevos_2 where sedcodigo is not null 
 
 
drop table notas_jorn_nuevas_2;

create table notas_jorn_nuevas_2 as
select distinct inscodigo, jornada
from notas_objetos_nuevos_2
where (inscodigo, jornada) not in (
select jorcodins, jorcodigo
from jornada_2);

commit;


drop table notas_jorn_nuevas_2;

create table notas_jorn_nuevas_2 as
select distinct inscodigo, jornada
from notas_objetos_nuevos_2
where (inscodigo, jornada) not in (
select jorcodins, jorcodigo
from jornada_2);

commit;

insert into jornada_2
(jorcodins, jorcodigo)
select inscodigo, jornada
from notas_jorn_nuevas_2;

commit;

-----------------------
drop table notas_sedes_jorn_nuevas_2;

create table notas_sedes_jorn_nuevas_2
as
select distinct inscodigo, sedcodigo, jornada
from notas_objetos_nuevos_2
where (inscodigo, sedcodigo, jornada) not in (
select sedjorcodinst, sedjorcodsede, sedjorcodjor
from sede_jornada_2);

commit;

select * from notas_sedes_jorn_nuevas_2 where sedcodigo is not null


insert into sede_jornada_2
(sedjorcodinst, sedjorcodsede, sedjorcodjor)
select inscodigo, sedcodigo, jornada
from notas_sedes_jorn_nuevas_2
where sedcodigo is not null;


commit;

drop sequence codigo_jerarquia

select max(g_jercodigo)+1
from g_jerarquia

create sequence codigo_jerarquia start with 2158 increment by 1

insert into g_jerarquia_2
(g_jertipo, g_jernivel, g_jerdepto, g_jermunic, g_jerlocal, g_jerinst, g_jersede, g_jerjorn, g_jercodigo)
select 1, 6, inscoddepto, inscodmun, inscodlocal, s.inscodigo, sedcodigo, jornada, codigo_jerarquia.nextval
from notas_sedes_jorn_nuevas_2 s, institucion_2 i
where s.inscodigo = i.inscodigo
and sedcodigo is not null;

commit;

select count(*) from g_jerarquia_2 where g_jersede is null

commit;