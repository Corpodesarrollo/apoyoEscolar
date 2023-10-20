<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@include file="parametros.jsp"%>
<html>
	<head>
		<title>Sistema de Gestión Académica - DATOS MAESTROS</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">		
</head>
<%if(request.getParameter("tipo")!=null){%>
	<frameset rows='100%,*' border='1' frameborder='1' framespacing='1'>		
		<frame name='1' id='1' src='<c:url value="/datoMaestro/DatoMaestroListaEdit?tipo=2"/>' ></frame>
<%}else{%>
	<frameset rows='50%,50%' border='1' frameborder='1' framespacing='1'>		
		<frame name='1' id='1' src='<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1"/>' ></frame>
		<frame name='2' id='2' src='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>'></frame>
<%}%>
</frameset>
</html>