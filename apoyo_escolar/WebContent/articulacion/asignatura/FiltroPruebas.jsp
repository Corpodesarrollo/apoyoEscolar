<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="asignaturaVO" class="articulacion.asignatura.vo.AsignaturaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<jsp:useBean id="datosVOP" class="articulacion.asignatura.vo.DatosVO" scope="session"/>
<html>
<head>
<script languaje="javaScript">
	<!--
	function lanzarServicio(n){
		if(n==1){//especialidad
			document.frmFiltro.action='<c:url value="/articulacion/especialidad/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='';
  	}
		if(n==2){//Area
			document.frmFiltro.action='<c:url value="/articulacion/area/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='';
  	}
		if(n==3){//Asignatura
			document.frmFiltro.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='<c:out value="${paramsVO.FICHA_ASIGNATURA}"/>';
  	}
		if(n==4){//Prueba
			document.frmFiltro.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='<c:out value="${paramsVO.FICHA_PRUEBA}"/>';
  	}
		if(n==5){//Asignación académica
  		document.frmFiltro.action='<c:url value="/articulacion/asigAcademica/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='';
  	}
		if(n==6){//Escala valorativa
  		document.frmFiltro.action='<c:url value="/articulacion/escValorativa/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='';
  	}
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function lanzar(tipo){
	  	document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function filtrar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
			document.frmFiltro.action='<c:url value="/articulacion/asignatura/SaveP1.jsp"/>';
			document.frmFiltro.submit();
		}
	}
	
	function cambioSelect2(){
		document.frmFiltro.perVigencia.selectedIndex=0;
	}

	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.asignatura, "- Seleccione una Asignatura", 1);
		if(forma.especialidad.disabled==false)
			validarLista(forma.especialidad, "- Seleccione una Especialidad", 1);
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--","0");
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	
	function iniciar(){
		document.frmFiltro.perVigencia.selectedIndex=0;
	}
	
	function ajaxArea(obj){
		borrar_combo(document.frmFiltro.area); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){//
			document.frmAjax.ajax[1].value=document.frmFiltro.anoVigencia.value;
			document.frmAjax.ajax[2].value=val;
			document.frmAjax.ajax[3].value=document.frmFiltro.componente.value;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_AREA}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	function ajaxAsignatura(){
		borrar_combo(document.frmFiltro.asignatura); 
		//var val=obj.options[obj.selectedIndex].value;
		//if(val!=-99){
			document.frmAjaxAsignatura.ajax[1].value=document.frmFiltro.componente.value;
			document.frmAjaxAsignatura.ajax[2].value=document.frmFiltro.especialidad.value;
			//document.frmAjaxAsignatura.ajax[3].value=val;
			document.frmAjaxAsignatura.ajax[4].value=document.frmFiltro.periodo.value;
			document.frmAjaxAsignatura.ajax[6].value=document.frmFiltro.anoVigencia.value;
			document.frmAjaxAsignatura.ajax[7].value=document.frmFiltro.perVigencia.value;
			document.frmAjaxAsignatura.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ASIGNATURA}"/>';
	 		document.frmAjaxAsignatura.target="frameAjax";
			document.frmAjaxAsignatura.submit();
		//}
	}
//-->
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body>
 <form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
	<tr height="1">
		<td width="10">&nbsp;</td>
		<td rowspan="2" width="520">
			<a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/especialidad_0.gif"/>' alt="Especialidad" height="26" border="0"></a>
			<a href="javaScript:lanzarServicio(2)"><img src='<c:url value="/etc/img/tabs/areas_0.gif"/>' alt="Area" height="26" border="0"></a>
			<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_ASIGNATURA}"/>)"><img src='<c:url value="/etc/img/tabs/asignatura_0.gif"/>' alt="Asignatura" height="26" border="0"></a>
			<img src='<c:url value="/etc/img/tabs/prueba_1.gif"/>' alt="Pruebas" height="26" border="0">
			<a href="javaScript:lanzarServicio(5)"><img src='<c:url value="/etc/img/tabs/carga_academica_0.gif"/>' alt="Asignación académica" height="26" border="0"></a>
			<a href="javaScript:lanzarServicio(6)"><img src='<c:url value="/etc/img/tabs/escalas_valorativas_0.gif"/>' alt="Escala valorativa" height="26" border="0"></a>
		</td>
	</tr>
</table>

	 <input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_PRUEBA}"/>'>
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="FILTRO" value='<c:out value="${2}"/>'>
	 <input type="hidden" name="inst" value='<c:out value="${sessionScope.login.instId}"/>'>
	 <input type="hidden" name="componente" value='2'>	 
		<table border="0" align="center" width="100%">
			<caption>Filtro de Búsqueda</caption>
			<c:if test="${empty requestScope.listaSedeVO}">
					<tr>
				 		<td style='width:30px;'>
				 			<input name="cmd" type="button" value="Buscar" onClick="filtrar()" class="boton">
				 		</td>
			 		</tr>
			</c:if>
			<tr>
	  			<td>
					<span class="style2">*</span><b>Especialidad:</b>
				</td>
		  		<td>
					<select name="especialidad" onchange="ajaxAsignatura()">
						<option value="0">--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaEspecialidadVO}" var="especialidad">
							<option value="<c:out value="${especialidad.codigo}"/>"  <c:if test="${especialidad.codigo==sessionScope.datosVOP.especialidad}">selected</c:if>><c:out value="${especialidad.nombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
				<td><span class="style2">*</span><b>Vigencia:</b></td>
				<td>
					<select name="anoVigencia" style='width:50px;' onchange="javaScript:iniciar()">
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.datosVOP.anoVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>-
					<select name="perVigencia" onchange="ajaxAsignatura();" style='width:30px;'>
							<option value="0">--</option>
							<option value="1" <c:if test="${1==sessionScope.datosVOP.perVigencia}">selected</c:if>>1</option>
							<option value="2" <c:if test="${2==sessionScope.datosVOP.perVigencia}">selected</c:if>>2</option>
					</select>
		  		</td>
		  	 </tr> 	
			<tr>
				<td>
					<span class="style2">*</span><b>Semestre:&nbsp;&nbsp;</b>
				</td>
				<td>
					
					<select name="periodo" >
						<option value="1" <c:if test="${1==sessionScope.datosVOP.periodo}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.datosVOP.periodo}">selected</c:if>>2</option>
						<option value="3" <c:if test="${3==sessionScope.datosVOP.periodo}">selected</c:if>>3</option>
						<option value="4" <c:if test="${4==sessionScope.datosVOP.periodo}">selected</c:if>>4</option>
					</select>
		  		</td>
		  	</tr>
		  	<tr>
		  		
		  		<td>
					<span class="style2">*</span><b>Asignatura:</b>
				</td> 
				<td colspan="2">
					<select style="width:200px;" name="asignatura">
					<option value="0">--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaAsignaturaPVO}" var="asignatura">
							<option value="<c:out value="${asignatura.artAsigCodigo}"/>" <c:if test="${asignatura.artAsigCodigo==sessionScope.datosVOP.asignatura}">selected</c:if>><c:out value="${asignatura.artAsigNombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
		  	</tr>
			<tr>
				<td style="display:none" colspan='6'><iframe name="frameAjax" id="frameAjax"></iframe></td>
			</tr>
		 </table>
	</form>
		<form method="post" name="frmAjax" action="<c:url value="/articulacion/asignatura/Ajax.do"/>">
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<INPUT TYPE="hidden" NAME="tipo" VALUE=''>
			<input type="hidden" name="cmd">
		</form>
		<form method="post" name="frmAjaxAsignatura" action="<c:url value="/articulacion/asignatura/Ajax.do"/>">
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax" value="1">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<INPUT TYPE="hidden" NAME="tipo" VALUE=''>
			<input type="hidden" name="cmd">
		</form>
</body>
</html>