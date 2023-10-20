<jsp:useBean  id="filtroRepEstadisticoVO" class="siges.gestionAcademica.repEstadisticos.vo.FiltroRepEstadisticoVO" scope="session"/>
<jsp:setProperty name="filtroRepEstadisticoVO" property="*"/>
<jsp:include page="/gestionAcademica/RepEstadistico/Nuevo.do"/>