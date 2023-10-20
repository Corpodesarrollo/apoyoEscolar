<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="navegador.adminNavegador.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script language="javaScript">
<!--
	function lanzarServicio(n){
		if(n==1){//seccion navegador
				document.frmLista.action='<c:url value="/navegador/adminNavegador/Nuevo.do"/>';
				document.frmLista.tipo.value='';
  	}
		document.frmLista.target="";
		document.frmLista.submit();
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
			if(confirm('¿Confirma la eliminación de esta sección?')){
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
	<form action='<c:url value="/navegador/adminNavegador/Nuevo.do"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_SECCION}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value="">
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">

		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="520">
					<img src='<c:url value="/etc/img/tabs/secciones_navegador_1.gif"/>' alt="Seccion Navegador" height="26" border="0">
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>Lista de secciones</caption>
		 	<c:if test="${empty requestScope.listaSeccionNavegadorVO}">
				<tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE SECCIONES</th></tr>
			</c:if>
			<c:if test="${!empty requestScope.listaSeccionNavegadorVO}">
				<tr>
					<th width='10%' class="EncabezadoColumna">&nbsp;</th>
					<td width='45%' class="EncabezadoColumna" align="center">Nombre</td>
					<td width='45%' class="EncabezadoColumna" align="center">Orden</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaSeccionNavegadorVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.codigo}"/>);'><img border='0' <c:if test="${requestScope.guia==lista.codigo}">src='<c:url value="/etc/img/Xeditar.png"/>'</c:if> <c:if test="${requestScope.guia!=lista.codigo}">src='<c:url value="/etc/img/editar.png"/>'</c:if> width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.nombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.orden}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>