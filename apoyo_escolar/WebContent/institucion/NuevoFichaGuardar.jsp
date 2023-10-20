<jsp:useBean id="fichaVO" class="siges.institucion.beans.FichaVO" scope="session"/>
<jsp:setProperty name="fichaVO" property="*"/>
<jsp:include page="ControllerFichaSave.do"/>