<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="participacion.instancia.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function eliminarRepresentante(n){
		if(confirm('¿Desea eliminar el participante indicado?')){
			if(document.frmFiltroRepresentante.filRepresentante){
					document.frmFiltroRepresentante.filRepresentante.value=n;
					document.frmFiltroRepresentante.cmd.value=document.frmFiltroRepresentante.ELIMINAR.value;
					document.frmFiltroRepresentante.ext.value='';
					document.frmFiltroRepresentante.target='';
					document.frmFiltroRepresentante.submit();
			}
		}	
	}

	function nuevoRepresentante(){
		remote = window.open("","3","width=650,height=250,resizable=yes,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes");
		remote.moveTo(200,200);
		if (remote.opener == null) remote.opener = window;
		remote.opener.name = "centro";
		remote.focus();
		document.frmFiltroRepresentante.cmd.value=document.frmFiltroRepresentante.NUEVO.value;
		document.frmFiltroRepresentante.target='3';
		document.frmFiltroRepresentante.ext.value='1';
		document.frmFiltroRepresentante.submit();
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>' method="post" name="frmFiltroRepresentante">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_REPRESENTANTE}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="ext" value=''>
		<input type="hidden" name="filRepresentante" value='<c:out value="${sessionScope.filtroInstanciaVO.filRepresentante}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de participantes</caption>
		 	<tr>
			 	<td colspan="4"><c:if test="${sessionScope.NivelPermiso==2 && sessionScope.instanciaVO.formaEstado==1}"><input name="cmd1" type="button" value="Agregar/Editar" onClick="nuevoRepresentante()" class="boton"></c:if></td>
		 	</tr>
		 	<c:if test="${empty requestScope.listaRepresentanteVO}"><tr><th class="Fila1" colspan='6'>No hay participantes</th></tr></c:if>
			<c:if test="${!empty requestScope.listaRepresentanteVO}">
				<tr>
					<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Nombre</td>
					<td class="EncabezadoColumna" align="center">Cantidad</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaRepresentanteVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></th>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminarRepresentante(<c:out value="${lista.repCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.repNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.repCantidad}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>