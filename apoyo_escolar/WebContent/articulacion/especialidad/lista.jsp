<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.especialidad.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function lanzarServicio(n){
		if(n==1){//especialidad
			document.frmLista.action='<c:url value="/articulacion/especialidad/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		if(n==2){//Area
			document.frmLista.action='<c:url value="/articulacion/area/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		if(n==3){//Asignatura
			document.frmLista.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';  	
			document.frmLista.tipo.value='<c:out value="${params2VO.FICHA_ASIGNATURA}"/>';
  	}
		if(n==4){//Prueba
			document.frmLista.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';  	
			document.frmLista.tipo.value='<c:out value="${params2VO.FICHA_PRUEBA}"/>';
  	}
		if(n==5){//Asignación académica
  		document.frmLista.action='<c:url value="/articulacion/asigAcademica/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		if(n==6){//Escala valorativa
  		document.frmLista.action='<c:url value="/articulacion/escValorativa/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		document.frmLista.target="";
		document.frmLista.submit();
	}

	function hacerValidaciones_frmLista(forma){
		validarSeleccion(forma.id, "- Debe seleccionar un item");
	}
	
	function editar(n,f){
	
		if(document.frmLista.id && document,frmLista.id2){
				document.frmLista.id.value=n;
				document.frmLista.id2.value=f;
				document.frmLista.cmd.value=document.frmLista.EDITAR.value;
				document.frmLista.submit();
		}
	}
	
	function eliminar(n,f){
		if(document.frmLista.id && document,frmLista.id2){
			if(confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
				document.frmLista.id.value=n;
				document.frmLista.id2.value=f;
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
	<form action='<c:url value="/articulacion/especialidad/Nuevo.do"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value="">
		<input type="hidden" name="id2" value="">
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">

		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="520">
					<img src='<c:url value="/etc/img/tabs/especialidad_1.gif"/>' alt="Especialidad" height="26" border="0">
					<a href="javaScript:lanzarServicio(2)"><img src='<c:url value="/etc/img/tabs/areas_0.gif"/>' alt="Area" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(3)"><img src='<c:url value="/etc/img/tabs/asignatura_0.gif"/>' alt="Asignatura" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(4)"><img src='<c:url value="/etc/img/tabs/prueba_0.gif"/>' alt="Prueba" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(5)"><img src='<c:url value="/etc/img/tabs/carga_academica_0.gif"/>' alt="Asignación académica" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(6)"><img src='<c:url value="/etc/img/tabs/escalas_valorativas_0.gif"/>' alt="Escala valorativa" height="26" border="0"></a>
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO ESPECIALIDAD</caption>
		 	<c:if test="${empty requestScope.listaEspecialidadVO}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ESPECIALIDADES</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaEspecialidadVO}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Código Especialidad</td>
					<td class="EncabezadoColumna" align="center">Nombre Especialidad</td>
					<td class="EncabezadoColumna" align="center">Universidad</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaEspecialidadVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.carCodinst},${lista.carCodigo}"/>);'>
								<img border='0' src='../../etc/img/<c:if test="${requestScope.guia==lista.carCodigo}">X</c:if>editar.png' width='15' height='15'>
							</a>
							<c:if test="${sessionScope.NivelPermiso==2}">
								<a href='javaScript:eliminar(<c:out value="${lista.carCodinst},${lista.carCodigo}"/>);'>
								<img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a>
							</c:if>
						</th>
						<!-- <td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.carCodigo}"/></td>-->
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.carCodigo}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.carNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.carUniversidad}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>