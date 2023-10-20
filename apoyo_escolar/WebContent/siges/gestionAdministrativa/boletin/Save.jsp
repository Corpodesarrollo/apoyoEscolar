<jsp:useBean  id="plantillaBoletionVO" class="siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO" scope="session"/>
<jsp:setProperty name="plantillaBoletionVO" property="*"/>
<jsp:include page="/siges/gestionAdministrativa/plantillaBoletin/PlantillaBoletin.do"/>