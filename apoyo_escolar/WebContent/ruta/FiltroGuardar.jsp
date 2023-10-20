<jsp:useBean id="filtroVO" class="siges.ruta.vo.FiltroVO" scope="session"/><jsp:setProperty name="filtroVO" property="*"/>
<jsp:useBean id="nutricionVO" class="siges.ruta.vo.NutricionVO" scope="session"/><jsp:setProperty name="nutricionVO" property="*"/>
<jsp:useBean id="gestacionVO" class="siges.ruta.vo.GestacionVO" scope="session"/><jsp:setProperty name="gestacionVO" property="*"/>
<jsp:useBean id="rutaVO" class="siges.ruta.vo.RutaVO" scope="session"/><jsp:setProperty name="rutaVO" property="*"/>
<jsp:include page="Guardar.do"/>