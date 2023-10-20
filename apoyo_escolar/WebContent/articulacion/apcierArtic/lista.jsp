<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.apcierArtic.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	
	function guardar(numero,estado){
		document.frmLista.cmd.value=document.frmLista.ACTUALIZAR.value;
		document.frmLista.codigo.value=numero;
		document.frmLista.estado.value=estado;
		document.frmLista.submit();

	}
		
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/apcierArtic/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	
	<form action='<c:url value="/articulacion/apcierArtic/Save.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input type="hidden" name="cmd" value=''>
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="ACTUALIZAR" value='<c:out value="${paramsVO.CMD_ACTUALIZAR}"/>'>
		<input type="hidden" name="codigo" value=''>
		<input type="hidden" name="estado" value=''>
		
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE PRUEBAS</caption>
		 	<c:if test="${empty requestScope.listaPruebasVO}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE PRUEBAS</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaPruebasVO}">
				<tr>
					<td align="center" class="EncabezadoColumna">Prueba</td>
					<td align="center" class="EncabezadoColumna">Estado</td>
					<td align="center" class="EncabezadoColumna">Fecha de Última Actialización</td>
					<td style="width:40px;" align="center" class="EncabezadoColumna">&nbsp</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaPruebasVO}" var="lista" varStatus="st">
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.nombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:if test="${lista.estado=='true'}">Cerrado</c:if><c:if test="${lista.estado=='false'}">Abierto</c:if></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.fecha}"/></td>
						
	 					<td style="width:40px;"><input name="cmd1" type="button" value='<c:if test="${lista.estado=='true'}">Abrir</c:if><c:if test="${lista.estado=='false'}">Cerrar</c:if>' onClick='guardar(<c:out value="${lista.codigo}"/>,<c:out value="${lista.estado}"/>)' class="boton"></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>