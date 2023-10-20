<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="participacion.instancia.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function editarObjetivo(n){
		remote = window.open("","3","width=650,height=250,resizable=yes,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes");
		remote.moveTo(200,200);
		if (remote.opener == null) remote.opener = window;
		remote.opener.name = "centro";
		remote.focus();
		document.frmFiltroObjetivo.filObjetivo.value=n;
		document.frmFiltroObjetivo.cmd.value=document.frmFiltroObjetivo.EDITAR.value;
		document.frmFiltroObjetivo.target='3';
		document.frmFiltroObjetivo.ext.value='1';
		document.frmFiltroObjetivo.submit();
	}
	
	function eliminarObjetivo(n){
		if(confirm('¿Desea eliminar la función indicada?')){
			if(document.frmFiltroObjetivo.filObjetivo){
					document.frmFiltroObjetivo.filObjetivo.value=n;
					document.frmFiltroObjetivo.cmd.value=document.frmFiltroObjetivo.ELIMINAR.value;
					document.frmFiltroObjetivo.target='';
					document.frmFiltroObjetivo.ext.value='';
					document.frmFiltroObjetivo.submit();
			}
		}	
	}

	function nuevoObjetivo(){
		remote = window.open("","3","width=650,height=250,resizable=yes,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes");
		remote.moveTo(200,200);
		if (remote.opener == null) remote.opener = window;
		remote.opener.name = "centro";
		remote.focus();
		document.frmFiltroObjetivo.cmd.value=document.frmFiltroObjetivo.NUEVO.value;
		document.frmFiltroObjetivo.target='3';
		document.frmFiltroObjetivo.ext.value='1';
		document.frmFiltroObjetivo.submit();
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>' method="post" name="frmFiltroObjetivo">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_OBJETIVO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="ext" value=''>
		<input type="hidden" name="filObjetivo" value='<c:out value="${sessionScope.filtroInstanciaVO.filObjetivo}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de funciones</caption>
		 	<tr>
			 	<td colspan="3"><c:if test="${sessionScope.NivelPermiso==2 && sessionScope.instanciaVO.formaEstado==1}"><input name="cmd1" type="button" value="Agregar" onClick="nuevoObjetivo()" class="boton"></c:if></td>
		 	</tr>
		 	<c:if test="${empty requestScope.listaObjetivoVO}"><tr><th class="Fila1" colspan='6'>No hay funciones</th></tr></c:if>
			<c:if test="${!empty requestScope.listaObjetivoVO}">
				<tr>
					<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Nombre</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaObjetivoVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></th>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editarObjetivo(<c:out value="${lista.objCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminarObjetivo(<c:out value="${lista.objCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.objDescripcion}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>