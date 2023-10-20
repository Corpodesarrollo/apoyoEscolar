<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.gAsignatura.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function hacerValidaciones_frmLista(forma){
		validarSeleccion(forma.id, "- Debe seleccionar un item");
	}
	
	function editar(n){
		if(document.frmLista.id){
				document.frmLista.id.value=n;
				document.frmLista.cmd.value=document.frmLista.EDITAR.value;
				document.frmLista.submit();
		}
	}
	
	function eliminar(n){
		if(document.frmLista.id){
			if(confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
				document.frmLista.id.value=n;
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
	<form action='<c:url value="/articulacion/gAsignatura/Nuevo.do"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value="">
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">

		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE ASIGNATURAS</caption>
		 	<c:if test="${empty requestScope.listaGAsignaturaVO}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ASIGNATURAS</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaGAsignaturaVO}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Código</td>
					<td class="EncabezadoColumna" align="center">Nombre de la Asignatura</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaGAsignaturaVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.geArtAsigCodigo}"/>);'>
								<img border='0' src='../../etc/img/<c:if test="${requestScope.guia==lista.geArtAsigCodigo}">X</c:if>editar.png' width='15' height='15'>
							</a>
							<c:if test="${sessionScope.NivelPermiso==2}">
								<a href='javaScript:eliminar(<c:out value="${lista.geArtAsigCodigo}"/>);'>
								<img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a>
							</c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.geArtAsigCodigo}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.geArtAsigNombre}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
	
</body>
</html>