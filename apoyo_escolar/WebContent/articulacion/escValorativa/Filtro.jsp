<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.escValorativa.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<jsp:useBean id="filtroEscalasVO" class="articulacion.escValorativa.vo.DatosVO" scope="session"/>
<html>
<head>
<script languaje="javaScript">
<!--

	function lanzarServicio(n){
		if(n==1){//especialidad
			document.frmFiltro.action='<c:url value="/articulacion/especialidad/Nuevo.do"/>';  	
  	}
		if(n==2){//Area
			document.frmFiltro.action='<c:url value="/articulacion/area/Nuevo.do"/>';  	
  	}
		if(n==3){//Asignatura
			document.frmFiltro.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='<c:out value="${params2VO.FICHA_ASIGNATURA}"/>';
  	}
		if(n==4){//Prueba
			document.frmFiltro.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='<c:out value="${params2VO.FICHA_PRUEBA}"/>';
  	}
		if(n==5){//Asignación académica
  		document.frmFiltro.action='<c:url value="/articulacion/asigAcademica/Nuevo.do"/>';  	
  	}
		if(n==6){//Escala valorativa
  		document.frmFiltro.action='<c:url value="/articulacion/escValorativa/Nuevo.do"/>';  	
  	}
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function filtrar(){
		document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
		document.frmFiltro.action='<c:url value="/articulacion/escValorativa/Save0.jsp"/>';
		document.frmFiltro.submit();
	}
	
	

	function hacerValidaciones_frmFiltro(forma){
	//		validarLista(forma.especialidad, "- Seleccione una especialidad", 1)
	}
//-->	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body>
 <form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="tipo" value=''>
	 <input type="hidden" name="FILTRO" value='<c:out value="${2}"/>'>
	 <input type="hidden" name="institucion" value='<c:out value="${sessionScope.login.instId}"/>'>
	 <input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="520">
					<a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/especialidad_0.gif"/>' alt="Especialidad" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(2)"><img src='<c:url value="/etc/img/tabs/areas_0.gif"/>' alt="Area" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(3)"><img src='<c:url value="/etc/img/tabs/asignatura_0.gif"/>' alt="Asignatura" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(4)"><img src='<c:url value="/etc/img/tabs/prueba_0.gif"/>' alt="Prueba" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(5)"><img src='<c:url value="/etc/img/tabs/carga_academica_0.gif"/>' alt="Asignación académica" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/escalas_valorativas_1.gif"/>' alt="Escala Valorativa" height="26" border="0">
				</td>
			</tr>
		</table>

		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Filtro de Búsqueda</caption>
			<tr>
		 		<td style='width:30px;'>
		 			<input name="cmd" type="button" value="Buscar" onClick="filtrar()" class="boton">
		 		</td>
	 		</tr>
	  		<tr>
				<td>
					<span class="style2">*</span><b>Vigencia:</b>
				</td>
				<td>
					<select name="anVigencia" style='width:50px;'>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.filtroEscalasVO.anVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>-
					<select name="perVigencia">
						<option value="1" <c:if test="${1==sessionScope.filtroEscalasVO.perVigencia}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.filtroEscalasVO.perVigencia}">selected</c:if>>2</option>
					</select>
		  		</td>
			</tr>
		  </table>
	</form>
</body>
</html>