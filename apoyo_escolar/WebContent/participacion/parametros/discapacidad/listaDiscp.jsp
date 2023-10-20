<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="participacion.parametros.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function lanzarServicio(n){
		if(n==1){//universidad
			document.frmLista.action='<c:url value="/participacion/parametros/Nuevo.do?tipo=1"/>';  	
  	}
		if(n==2){//tipo de programa
			document.frmLista.action='<c:url value="/participacion/parametros/Nuevo.do?tipo=2"/>';  	
  	}
		if(n==3){//especialidad base
			document.frmLista.action='<c:url value="/participacion/parametros/Nuevo.do?tipo=3"/>';  	
  	}
		if(n==4){//area base
			document.frmLista.action='<c:url value="/participacion/parametros/Nuevo.do?tipo=4"/>';  	
  	}
		if(n==5){//asignatura base
  		document.frmLista.action='<c:url value="/participacion/parametros/Nuevo.do?tipo=5"/>';  	
  	}
		document.frmLista.target="";
		document.frmLista.submit();
	}
	
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
	<form action='<c:url value="/participacion/parametros/Nuevo.do"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value="">
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">

		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/participacion_discapacidad_1.gif"/>' alt="Parametros discapacidad" height="26" border="0">
					<a href="javaScript:lanzarServicio(2)"><img src='<c:url value="/etc/img/tabs/participacion_etnias_0.gif"/>' alt="Etnias" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(3)"><img src='<c:url value="/etc/img/tabs/participacion_lgtb_0.gif"/>' alt="LGTB" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(4)"><img src='<c:url value="/etc/img/tabs/participacion_ocupacion_0.gif"/>' alt="Ocupaci&oacute;" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(5)"><img src='<c:url value="/etc/img/tabs/participacion_rol_0.gif"/>' alt="Rol" height="26" border="0"></a>
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE DISCAPACIDADES</caption>
		 	<c:if test="${empty requestScope.listaDiscapacidadVO}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE DISCAPÀCIDAD</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaDiscapacidadVO}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Código</td>
					<td class="EncabezadoColumna" align="center">Nombre/td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaDiscapacidadVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.codigo}"/>);'>
								<img border='0' src='../../etc/img/<c:if test="${requestScope.guia==lista.codigo}">X</c:if>editar.png' width='15' height='15'>
							</a>
							<c:if test="${sessionScope.NivelPermiso==2}">
								<a href='javaScript:eliminar(<c:out value="${lista.codigo}"/>);'>
								<img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a>
							</c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.codigo}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.nombre}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>