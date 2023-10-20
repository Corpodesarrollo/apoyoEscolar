<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.gArea.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function lanzarServicio(n){
		if(n==1){//universidad
			document.frmLista.action='<c:url value="/articulacion/universidad/Nuevo.do"/>';  	
  	}
		if(n==2){//tipo de programa
			document.frmLista.action='<c:url value="/articulacion/universidad/NuevoTipoPrograma.do"/>';  	
  	}
		if(n==3){//especialidad base
			document.frmLista.action='<c:url value="/articulacion/especialidadBase/Nuevo.do"/>';  	
  	}
		if(n==4){//area base
			document.frmLista.action='<c:url value="/articulacion/gArea/Nuevo.do"/>';  	
  	}
		if(n==5){//asignatura base
  		document.frmLista.action='<c:url value="/articulacion/gAsignatura/Nuevo.do"/>';  	
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
	<form action='<c:url value="/articulacion/gArea/Nuevo.do"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
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
					<a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/universidad_0.gif"/>' alt="Universidad" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(3)"><img src='<c:url value="/etc/img/tabs/esp_base_0.gif"/>' alt="Especialidad" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/g_art_area_1.gif"/>' alt="Areas" height="26" border="0">
					<a href="javaScript:lanzarServicio(5)"><img src='<c:url value="/etc/img/tabs/asignatura_0.gif"/>' alt="Asignatura" height="26" border="0"></a>
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE ÁREAS</caption>
		 	<c:if test="${empty requestScope.listaAreaVO}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE AREAS</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaAreaVO}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Código</td>
					<td class="EncabezadoColumna" align="center">Nombre del Área</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaAreaVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.geArtAreaCodigo}"/>);'>
								<img border='0' src='../../etc/img/<c:if test="${requestScope.guia==lista.geArtAreaCodigo}">X</c:if>editar.png' width='15' height='15'>
							</a>
							<c:if test="${sessionScope.NivelPermiso==2}">
								<a href='javaScript:eliminar(<c:out value="${lista.geArtAreaCodigo}"/>);'>
								<img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a>
							</c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.geArtAreaCodigo}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.geArtAreaNombre}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>