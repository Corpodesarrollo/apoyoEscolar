<jsp:useBean id="filtroEvaluacion" class="siges.evaluacion.beans.FiltroBeanEvaluacion" scope="session"/>
<jsp:setProperty name="filtroEvaluacion" property="*"/>
<jsp:include page="ControllerEvaluacionEdit.do"/>
