<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script language="javaScript">
<!--
	function editar(n,o,p){
		if(document.frmLista.asig){
				document.frmLista.asig.value=n;
				document.frmLista.cod.value=o;
				document.frmLista.sub.value=p;
				document.frmLista.cmd.value=document.frmLista.EDITAR.value;
				document.frmLista.submit();
		}
	}
	
	function eliminar(n,o,p){
		if(document.frmLista.asig){
			if(confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
				document.frmLista.asig.value=n;
				document.frmLista.cod.value=o;
				document.frmLista.sub.value=p;
				document.frmLista.cmd.value=document.frmLista.ELIMINAR.value;
				document.frmLista.submit();
			}
		}
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/asignatura/Nuevo.do"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_PRUEBA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="asig" value=''>
		<input type="hidden" name="cod" value=''>
		<input type="hidden" name="sub" value=''>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE PRUEBAS</caption>
		 	<c:if test="${empty requestScope.listaPruebaVO}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE PRUEBAS</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaPruebaVO}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Nombre</td>
					<!-- <td class="EncabezadoColumna" align="center">Principal</td>
					<td class="EncabezadoColumna" align="center">Sub Prueba</td> -->
					<td class="EncabezadoColumna" align="center">Porcentaje</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaPruebaVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.artPruCodAsig}"/>,<c:out value="${lista.artPruCodigo}"/>,<c:out value="${lista.pruebaPrincipal}"/>);'>
								<img border='0' src='../../etc/img/<c:if test="${requestScope.guia==lista.artPruCodigo && sessionScope.pruebaVO.pruebaPrincipal==lista.pruebaPrincipal}">X</c:if>editar.png' width='15' height='15'>
							</a>
							<c:if test="${sessionScope.NivelPermiso==2}">
								<a href='javaScript:eliminar(<c:out value="${lista.artPruCodAsig}"/>,<c:out value="${lista.artPruCodigo}"/>,<c:out value="${lista.pruebaPrincipal}"/>);'>
								<img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a>
							</c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.artPruNombre}"/></td>
						<!-- <td class='Fila<c:out value="${st.count%2}"/>' align="center"> <c:if test="${lista.pruebaPrincipal==0}">X</c:if><c:if test="${lista.pruebaPrincipal!=0}">&nbsp;</c:if></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"> <c:if test="${lista.pruebaPrincipal!=0}">X</c:if><c:if test="${lista.pruebaPrincipal==0}">&nbsp;</c:if></td> -->
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.artPruPorcentaje}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>