<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="participacion.instancia.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function editarDocumento(n){
		remote = window.open("","3","width=650,height=250,resizable=yes,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes");
		remote.moveTo(200,200);
		if (remote.opener == null) remote.opener = window;
		remote.opener.name = "centro";
		remote.focus();
		document.frmFiltroDocumento.filDocumento.value=n;
		document.frmFiltroDocumento.cmd.value=document.frmFiltroDocumento.EDITAR.value;
		document.frmFiltroDocumento.target='3';
		document.frmFiltroDocumento.ext.value='1';
		document.frmFiltroDocumento.action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>';
		document.frmFiltroDocumento.submit();
	}
	
	function eliminarDocumento(n){
		if(confirm('¿Desea eliminar el objetivo indicado?')){
			if(document.frmFiltroDocumento.filDocumento){
					document.frmFiltroDocumento.filDocumento.value=n;
					document.frmFiltroDocumento.cmd.value=document.frmFiltroDocumento.ELIMINAR.value;
					document.frmFiltroDocumento.action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>';
					document.frmFiltroDocumento.target='';
					document.frmFiltroDocumento.ext.value='';
					document.frmFiltroDocumento.submit();
			}
		}	
	}

	function descargarDocumento(n){
		if(document.frmFiltroDocumento.filDocumento){
				document.frmFiltroDocumento.filDocumento.value=n;
				document.frmFiltroDocumento.cmd.value=document.frmFiltroDocumento.DESCARGAR.value;
				document.frmFiltroDocumento.action='<c:url value="/participacion/instancia/Nuevo.do"/>';
				document.frmFiltroDocumento.target='3';
				document.frmFiltroDocumento.ext.value='1';
				document.frmFiltroDocumento.submit();
		}
	}
	
	function nuevoDocumento(){
		remote = window.open("","3","width=650,height=250,resizable=yes,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes");
		remote.moveTo(200,200);
		if (remote.opener == null) remote.opener = window;
		remote.opener.name = "centro";
		remote.focus();
		document.frmFiltroDocumento.cmd.value=document.frmFiltroDocumento.NUEVO.value;
		document.frmFiltroDocumento.ext.value='1';
		document.frmFiltroDocumento.target='3';
		document.frmFiltroDocumento.action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>';
		document.frmFiltroDocumento.submit();
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>' method="post" name="frmFiltroDocumento">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DOCUMENTO}"/>'>
		<input type="hidden" name="cmd" value=''><input type="hidden" name="ext" value=''>
		<input type="hidden" name="filInstancia" value='<c:out value="${sessionScope.filtroInstanciaVO.filInstancia}"/>'>
		<input type="hidden" name="filDocumento" value='<c:out value="${sessionScope.filtroInstanciaVO.filDocumento}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="DESCARGAR" value='<c:out value="${paramsVO.CMD_DESCARGAR}"/>'>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de documentos</caption>
		 	<tr>
			 	<td colspan="5"><c:if test="${sessionScope.NivelPermiso==2 && sessionScope.instanciaVO.formaEstado==1}"><input name="cmd1" type="button" value="Agregar" onClick="nuevoDocumento()" class="boton"></c:if></td>
		 	</tr>
		 	<c:if test="${empty requestScope.listaDocumentoVO}"><tr><th class="Fila1" colspan='6'>No hay documentos</th></tr></c:if>
			<c:if test="${!empty requestScope.listaDocumentoVO}">
				<tr>
					<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Nombre</td>
					<td class="EncabezadoColumna" align="center">Fecha</td>
					<td class="EncabezadoColumna" align="center">Descargar</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaDocumentoVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></th>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editarDocumento(<c:out value="${lista.docCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminarDocumento(<c:out value="${lista.docCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.docNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.docFecha}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><a href='javaScript:descargarDocumento(<c:out value="${lista.docCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/zip.gif"/>' width='15' height='15'></a></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>