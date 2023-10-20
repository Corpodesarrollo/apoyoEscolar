<jsp:useBean  id="filtroBoletinVO" class="siges.gestionAdministrativa.padreFlia.vo.FiltroBoletinVO" scope="session"/>
<jsp:setProperty name="filtroBoletinVO" property="*"/>
<jsp:useBean  id="estudinateMarcarVO" class="siges.gestionAdministrativa.padreFlia.vo.EstudianteMarcarVO" scope="session"/>
<jsp:setProperty name="estudinateMarcarVO" property="*"/>
<jsp:include page="/siges/gestionAdministrativa/padreFlia/Nuevo.do"/>