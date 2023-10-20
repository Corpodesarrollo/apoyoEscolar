<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="actaVO" class="participacion.acta.vo.ActaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.acta.vo.ParamsVO" scope="page"/>
<jsp:useBean id="paramsVO2" class="participacion.common.vo.ParamParticipacion" scope="page"/>
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
	var cont=1;
	var msg="Revise la siguiente información: \n";	
		if(validarForma(document.frmNuevo)){
		cont=compareFechaActa(document.frmNuevo.actFecha.value);
		
		if(cont==1){
		//alert("cont lenth1 "+document.frmNuevo.actFechaProxima.value);
			if(document.frmNuevo.actFechaProxima.value!=''){
			//alert("cont lenth2 "+cont);
				cont=compareFechaProx(document.frmNuevo.actFechaProxima.value);
				//alert("cont lenth33 "+cont);
				if(cont==0){
					msg=msg+" - La fecha de la próxima reunión no puede ser menor a la actual. \n"
				}
			}
		}else{
			msg=msg+" - La fecha del acta no puede ser mayor a la actual. \n"
		}		
			if(cont==1){
			//alert("cont submit"+cont);
				validarDatos(document.frmNuevo);
				document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
				document.frmNuevo.submit();
			}else{
				alert(msg);
			}
		}
	}

	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.CANCELAR.value;
		document.frmNuevo.submit();
	}
	
	
	function validarDatos(forma){
		forma.actLocalidad.disabled=false;
		forma.actColegio.disabled=false;
	}
	
function compareFechaActa(a) {
	//alert("ENTRO A COMPARAR FECHAS");
	var firstDateArray, secondDateArray;
	firstDateArray = a.split("/");	
	aDate = new Date(firstDateArray[2], firstDateArray[1]-1, firstDateArray[0]);
	bDate = new Date();
	if (aDate.getTime() > bDate.getTime()) {
		//alert("FECHA ACTA MAYOR A ACTUAL");
		return 0;
	}else{
		//alert("FECHA ACTA MENOR O IGUAL A ACTUAL");
		return 1;
	}	
	//return 1;
}

function compareFechaProx(a) {
	//alert("ENTRO A COMPARAR FECHAS");
	var firstDateArray, secondDateArray;
	firstDateArray = a.split("/");	
	aDate = new Date(firstDateArray[2], firstDateArray[1]-1, firstDateArray[0]);
	bDate = new Date();
	if (aDate.getTime() < bDate.getTime()) {
		//alert("FECHA ACTA MAYOR A ACTUAL");
		return 0;
	}else{
		//alert("FECHA ACTA MENOR O IGUAL A ACTUAL");
		return 1;
	}	
	//return 1;
}

	function hacerValidaciones_frmNuevo(forma){
		validarFechaUnCampo(forma.actFecha, "- Fecha de reunión")
		validarLista(forma.actHoraIni, "- Hora inicial", 1)
		validarLista(forma.actMinIni, "- Minuto inicial", 1)
		validarLista(forma.actHoraFin, "- Hora final", 1)
		validarLista(forma.actMinFin, "- Minuto final", 1)
		validarCampoOpcional(forma.actLugar, "- Lugar", 1, 60)
		validarLista(forma.actAsunto, "- Asunto", 1)
		validarCampoOpcional(forma.actAsistentes, "- Asistentes", 1, 3000)
		validarCampoOpcional(forma.actAsistentesExt, "- Asistentes externos", 1, 3000)
		validarCampo(forma.actElaborado, "- Elaborado por", 1, 200)
		validarCampo(forma.actElaboradoRol, "- Rol", 1, 200)
		validarFechaOpcional(forma.actFechaProxima, "- Fecha de próxima reunión")
		validarCampoOpcional(forma.actDescripcion, "- Descripción de actividades", 1, 3000)
		validarCampoOpcional(forma.actDecision, "- Decisiones y / o acuerdos", 1, 3000)
		validarCampoOpcional(forma.actCompromiso, "- Compromisos adquiridos", 1, 3000)
		validarCampoOpcional(forma.actResponsabilidad, "- Asignación de responsabilidades", 1, 3000)
		validarCampoOpcional(forma.actDocumento, "- Documentos y objeto de la entrega", 1, 3000)
		validarCampoOpcional(forma.actObservacion, "- Observaciones", 1, 3000)
		validarLista(forma.actRol, "- Rol", 0)
		validarLista(forma.actParticipante, "- Participante", 0)
	}
	
	


	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxParticipante(){
		borrar_combo(document.frmNuevo.actParticipante); 
		document.frmAjax.ajax[0].value=document.frmNuevo.actInstancia.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.actRango.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.actRol.value;
		if(parseInt(document.frmAjax.ajax[2].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_PARTICIPANTE.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
	
	function ajaxRol(){
		document.frmNuevo.actRol.selectedIndex=0;
		document.frmNuevo.actParticipantes.selectedIndex=0;
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:220px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/participacion/acta/FiltroActa.jsp"/>
			</div>
		</td></tr>
		<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
	</table>
	<form method="post" name="frmAjax" action='<c:url value="/participacion/acta/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ACTA}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<c:if test="${requestScope.buscarActaVO==1}">
	<form method="post" name="frmNuevo" action='<c:url value="/participacion/acta/SaveActa.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="actTieneLocalidad" value='<c:out value="${sessionScope.actaVO.actTieneLocalidad}"/>'>
		<input type="hidden" name="actTieneColegio" value='<c:out value="${sessionScope.actaVO.actTieneColegio}"/>'>
		<input type="hidden" name="formaEstado" value='<c:out value="${sessionScope.actaVO.formaEstado}"/>'>
		<input type="hidden" name="actNivel" value='<c:out value="${sessionScope.actaVO.actNivel}"/>'>
		<input type="hidden" name="actInstancia" value='<c:out value="${sessionScope.actaVO.actInstancia}"/>'>
		<input type="hidden" name="actRango" value='<c:out value="${sessionScope.actaVO.actRango}"/>'>
		<input type="hidden" name="actLocalidad" value='<c:out value="${sessionScope.actaVO.actLocalidad}"/>'>
		<input type="hidden" name="actColegio" value='<c:out value="${sessionScope.actaVO.actColegio}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="AJAX_INSTANCIA" value='<c:out value="${paramsVO.CMD_AJAX_INSTANCIA}"/>'>
		<input type="hidden" name="AJAX_RANGO" value='<c:out value="${paramsVO.CMD_AJAX_RANGO}"/>'>
		<input type="hidden" name="AJAX_COLEGIO" value='<c:out value="${paramsVO.CMD_AJAX_COLEGIO}"/>'>
		<input type="hidden" name="AJAX_PARTICIPANTE" value='<c:out value="${paramsVO.CMD_AJAX_PARTICIPANTE}"/>'>
		<input type="hidden" name="NIVEL_DISTRITAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_DISTRITAL}"/>'>
		<input type="hidden" name="NIVEL_CENTRAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_CENTRAL}"/>'>
		<input type="hidden" name="NIVEL_LOCAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_LOCAL}"/>'>
		<input type="hidden" name="NIVEL_COLEGIO" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_COLEGIO}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / Edición de acta</caption>
				<tr>
					<td width="45%"><c:if test="${sessionScope.NivelPermiso==2}"><input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">&nbsp;<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton"></c:if></td>
			 	</tr>	
		</table>	 	
		<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<td><span class="style2">*</span> Fecha:</td>
				<td>
					<input type="text" name="actFecha" id="actFecha" size="10" maxlength="10" value='<c:out value="${sessionScope.actaVO.actFecha}"/>' readonly="true">
					<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Fecha" id="imgfechaIni" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''"/>
				</td>
				<td><span class="style2">*</span> Hora inicial:</td>
				<td>
					<select name="actHoraIni" style="width:40px;"><option value="-99" selected>--</option><c:forEach begin="0" items="${requestScope.listaHoraVO}" var="niv"><option value='<c:out value="${niv.codigo}"/>' <c:if test="${niv.codigo==sessionScope.actaVO.actHoraIni}">selected</c:if>><c:out value="${niv.nombre}"/></option></c:forEach></select>:
					<select name="actMinIni" style="width:40px;"><option value="-99" selected>--</option><c:forEach begin="0" items="${requestScope.listaMinutoVO}" var="niv"><option value='<c:out value="${niv.codigo}"/>' <c:if test="${niv.codigo==sessionScope.actaVO.actMinIni}">selected</c:if>><c:out value="${niv.nombre}"/></option></c:forEach></select>
				</td>
		 	</tr>
		 	<tr>
				<td><span class="style2">*</span> Hora final:</td>
				<td>
					<select name="actHoraFin" style="width:40px;"><option value="-99" selected>--</option><c:forEach begin="0" items="${requestScope.listaHoraVO}" var="niv"><option value='<c:out value="${niv.codigo}"/>' <c:if test="${niv.codigo==sessionScope.actaVO.actHoraFin}">selected</c:if>><c:out value="${niv.nombre}"/></option></c:forEach></select>:
					<select name="actMinFin" style="width:40px;"><option value="-99" selected>--</option><c:forEach begin="0" items="${requestScope.listaMinutoVO}" var="niv"><option value='<c:out value="${niv.codigo}"/>' <c:if test="${niv.codigo==sessionScope.actaVO.actMinFin}">selected</c:if>><c:out value="${niv.nombre}"/></option></c:forEach></select>
				</td>
				<td><span class="style2">*</span> Asunto:</td>
				<td>
					<select name="actAsunto" style="width:200px;" >
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaAsuntoVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.actaVO.actAsunto}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
		 	</tr>
			<tr>
				<td>Lugar:</td>
				<td colspan="3">
					<input type="text" name="actLugar" size="50" maxlength="60" value='<c:out value="${sessionScope.actaVO.actLugar}"/>'>
				</td>
		 	</tr>
		 	<tr>
				<td>Asistentes:</td>
				<td colspan="3"><textarea name="actAsistentes" cols="80" rows="3" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);" ><c:out value="${sessionScope.actaVO.actAsistentes}"/></textarea></td>
		 	</tr>
		 	<tr>
				<td>Asistentes externos:</td>
				<td colspan="3"><textarea name="actAsistentesExt" cols="80" rows="3" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);" ><c:out value="${sessionScope.actaVO.actAsistentesExt}"/></textarea></td>
		 	</tr>
		 	<tr>
				<td><span class="style2">*</span> Elaborado por:</td>
				<td>
					<input type="text" name="actElaborado" size="30" maxlength="200" value='<c:out value="${sessionScope.actaVO.actElaborado}"/>'>
				</td>
				<td><span class="style2">*</span>Rol:</td>
				<td>
					<!-- input type="text" name="actElaboradoRol" size="30" maxlength="200" value='<c:out value="${sessionScope.actaVO.actElaboradoRol}"/>'-->
					
					<select name="actElaboradoRol" style="width:150px;">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaRolVO}" var="niv">
							<option value="<c:out value="${niv.nombre}"/>" <c:if test="${niv.nombre==sessionScope.actaVO.actElaboradoRol}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
					
				</td>
		 	</tr>
			<tr>
				<td>Próxima reunión:</td>
				<td>
					<input type="text" name="actFechaProxima" id="actFechaProxima" size="10" maxlength="10" value='<c:out value="${sessionScope.actaVO.actFechaProxima}"/>' readonly="true">
					<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Fecha" id="imgfechaFin" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''"/>
				</td>
		 	</tr>
		 	<tr>
			 	<td colspan="4">
					<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0">
					<caption>Desarrollo de la agenda</caption>
					<tr><td>
						<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0">
							<tr>
								<td>Descripción de actividades:</td>
								<td colspan="3" align="right"><textarea name="actDescripcion" cols="70" rows="3" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);" ><c:out value="${sessionScope.actaVO.actDescripcion}"/></textarea></td>
							</tr>
							<tr>
								<td>Decisiones y/o acuerdos:</td>
								<td colspan="3" align="right"><textarea name="actDecision" cols="70" rows="3" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);" ><c:out value="${sessionScope.actaVO.actDecision}"/></textarea></td>
							</tr>
							<tr>
								<td>Compromisos adquiridos:</td>
								<td colspan="3" align="right"><textarea name="actCompromiso" cols="70" rows="3" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);" ><c:out value="${sessionScope.actaVO.actCompromiso}"/></textarea></td>
							</tr>
							<tr>
								<td>Asignación de responsabilidades:</td>
								<td colspan="3" align="right"><textarea name="actResponsabilidad" cols="70" rows="3" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);" ><c:out value="${sessionScope.actaVO.actResponsabilidad}"/></textarea></td>
							</tr>
						</table>
					</td></tr></table>
			 	</td>
		 	</tr>
		 	<tr>
				<td>Documentos y objeto de la entrega:</td>
				<td colspan="3"><textarea name="actDocumento" cols="80" rows="3" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);" ><c:out value="${sessionScope.actaVO.actDocumento}"/></textarea></td>
		 	</tr>
		 	<tr>
				<td>Observaciones:</td>
				<td colspan="3"><textarea name="actObservacion" cols="80" rows="3" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);" ><c:out value="${sessionScope.actaVO.actObservacion}"/></textarea></td>
		 	</tr>
			<tr>
				<td>Rol de quien elabora el acta:</td>
				<td>
					<select name="actRol" style="width:150px;" onchange="javaScript:ajaxParticipante()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaRolVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.actaVO.actRol}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td>Nombre de quien elabora el acta:</td>
				<td>
					<select name="actParticipante" style="width:200px;">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaParticipanteVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.actaVO.actParticipante}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>	
	</form>	
	<script type="text/javascript">
	    Calendar.setup({inputField:"actFecha",ifFormat:"%d/%m/%Y",button:"imgfechaIni",align:"Br"});
	    Calendar.setup({inputField:"actFechaProxima",ifFormat:"%d/%m/%Y",button:"imgfechaFin",align:"Br"});
	</script>
	</c:if>
</body>
</html>