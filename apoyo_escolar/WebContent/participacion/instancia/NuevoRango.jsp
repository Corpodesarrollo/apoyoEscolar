<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="rangoVO" class="participacion.instancia.vo.RangoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.instancia.vo.ParamsVO" scope="page"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}

	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.CANCELAR.value;
		document.frmNuevo.submit();
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.ranNivel, "- Nivel", 1)
		validarLista(forma.ranInstancia, "- Instancia", 1)
		validarCampo(forma.ranNombre, "- Nombre", 1, 250)
		validarFechaUnCampo(forma.ranFechaIni, "- Fecha inicial")
		validarFechaUnCampo(forma.ranFechaFin, "- Fecha final")
		validarRangoFechas(forma.ranFechaIni,forma.ranFechaFin,"- La fecha inicial no puede ser mayor a la final")
		validarSeleccion(forma.ranEstado_, "- ¿Es activo?")
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxInstancia(){
		borrar_combo(document.frmNuevo.ranInstancia); 
		document.frmAjax.ajax[0].value=document.frmNuevo.ranNivel.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_INSTANCIA.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
	
	function setValor(obj){
		document.frmNuevo.ranEstado.value=obj.value;
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/participacion/instancia/FiltroRango.jsp"/>
			</div>
		</td></tr>
		<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
	</table>
	<form method="post" name="frmAjax" action='<c:url value="/participacion/instancia/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_RANGO}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<form method="post" name="frmNuevo" action='<c:url value="/participacion/instancia/SaveRango.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_RANGO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="AJAX_INSTANCIA" value='<c:out value="${paramsVO.CMD_AJAX_INSTANCIA}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / Edición de rango</caption>
				<tr>
					<td width="45%"><c:if test="${sessionScope.NivelPermiso==2}"><input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">&nbsp;<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton"></c:if></td>
			 	</tr>	
		</table>	 	
		<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<td><span class="style2">*</span> Nivel:</td>
				<td>
					<select name="ranNivel" style="width:100px;" onchange="javaScript:ajaxInstancia()" <c:out value="${sessionScope.rangoVO.formaDisabled}"/>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaNivelVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.rangoVO.ranNivel}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Instancia:</td>
				<td>
					<select name="ranInstancia" style="width:250px;" <c:out value="${sessionScope.rangoVO.formaDisabled}"/>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaInstanciaVO}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.rangoVO.ranInstancia}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
		 	</tr>	
			<tr>
				<td><span class="style2">*</span> Nombre:</td>
				<td colspan="3">
					<input type="text" name="ranNombre" size="40" maxlength="60" value='<c:out value="${sessionScope.rangoVO.ranNombre}"/>'>
				</td>
		 	</tr>
			<tr>
				<td><span class="style2">*</span> Fecha inicial:</td>
				<td>
					<input type="text" name="ranFechaIni" id="ranFechaIni" size="10" maxlength="10" value='<c:out value="${sessionScope.rangoVO.ranFechaIni}"/>'>
					<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Fecha" id="imgfechaIni" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''"/>
				</td>
				<td><span class="style2">*</span> Fecha final:</td>
				<td>
					<input type="text" name="ranFechaFin" id="ranFechaFin" size="10" maxlength="10" value='<c:out value="${sessionScope.rangoVO.ranFechaFin}"/>'>
					<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Fecha" id="imgfechaFin" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''"/>
				</td>
		 	</tr>
		 	<tr>
				<td><span class="style2">*</span> ¿Es activo?:</td>
				<td>
					<input type="hidden" name="ranEstado" value='<c:out value="${sessionScope.rangoVO.ranEstado}"/>'>
					<input type="radio" name="ranEstado_" <c:if test="${1==sessionScope.rangoVO.ranEstado}">checked</c:if> value="1" onclick="javaScript:setValor(this)">Si<br>
					<input type="radio" name="ranEstado_" <c:if test="${0==sessionScope.rangoVO.ranEstado}">checked</c:if> value="0" onclick="javaScript:setValor(this)">No<br>
				</td>
		 	</tr>
		</table>	
	</form>	
</body>
	<script type="text/javascript">
	    Calendar.setup({inputField:"ranFechaIni",ifFormat:"%d/%m/%Y",button:"imgfechaIni",align:"Br"});
	    Calendar.setup({inputField:"ranFechaFin",ifFormat:"%d/%m/%Y",button:"imgfechaFin",align:"Br"});
	</script>
</html>