SELECT
CARESTINST, CARESTINSTCODDANE, CARESTSEDE,
CARESTJORN, CARESTAPELLIDOS, CARESTNOMBRES,
CARESTNUMDOC, CARESTCODGRADO, CARESTGRADO,
CARESTCODIGO, CARESTANO, CARESTNOMRECTOR,
CARESTAPERECTOR, CARESTID, CARESTCODGRUPO,
CARESTGRUPO, CARESTTIPDOC, to_char(CARESTFECNAC, 'dd, yyyy') as FEC_NAC,ESTFOTO,
case to_char(CARESTFECNAC,'MM')
when '01' then 'Enero'
when '02' then 'Febrero'
when '03' then 'Marzo'
when '04' then 'Abril'
when '05' then 'Mayo'
when '06' then 'Junio'
when '07' then 'Julio'
when '08' then 'Agosto'
when '09' then 'Septiembre'
when '10' then 'Octubre'
when '11' then 'Noviembre'
when '12' then 'Diciembre'
end MES
FROM CARNE_ESTUDIANTE,ESTUDIANTE
WHERE CARESTCODIGO = ESTCODIGO
AND CARESTID=$P{usuario}
ORDER BY CARESTAPELLIDOS