<jsp:useBean id="abrirGrupo" class="siges.grupoPeriodo.beans.AbrirGrupo" scope="session"/><jsp:setProperty name="abrirGrupo" property="*"/>
<jsp:useBean id="cierreVO" class="siges.grupoPeriodo.beans.CierreVO" scope="session"/><jsp:setProperty name="cierreVO" property="*"/>
<jsp:include page="ControllerAbrirSave.do"/>

