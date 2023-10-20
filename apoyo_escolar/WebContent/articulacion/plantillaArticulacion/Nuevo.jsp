<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="grupoVO" class="articulacion.grupoArt.vo.GrupoArtVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.grupoArt.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'><c:import url="/articulacion/plantillaArticulacion/Filtro.do"/></td></tr>
	</table>
</body>
</html>