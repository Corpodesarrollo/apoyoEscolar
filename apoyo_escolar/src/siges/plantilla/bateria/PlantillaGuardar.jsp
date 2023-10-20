<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="logros" class="siges.plantilla.beans.Logros" scope="session"/><jsp:setProperty name="logros" property="*"/>
<c:import url="/plantilla/bateria/ControllerPlantillaSave.do"/>