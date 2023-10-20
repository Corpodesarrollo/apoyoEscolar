<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroPlantillaComportamiento" class="siges.plantilla.beans.FiltroComportamiento" scope="session"/>
<jsp:setProperty name="filtroPlantillaComportamiento" property="*"/>
<%  System.out.println("ddd"); %>
<c:import url="/plantilla/comportamiento/Nuevo.do"/>