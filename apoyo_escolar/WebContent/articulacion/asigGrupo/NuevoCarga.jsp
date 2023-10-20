<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/><jsp:setProperty name="nuevoPersonal" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="carga" class="siges.personal.beans.Carga" scope="session"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="laboralVO" class="siges.personal.beans.LaboralVO" scope="session"/><jsp:setProperty name="laboralVO" property="*"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="formacionVO" class="siges.personal.beans.FormacionVO" scope="session"/><jsp:setProperty name="formacionVO" property="*"/>

<jsp:useBean id="paramsVO" class="articulacion.asigGrupo.vo.ParamsVO" scope="session"/>
<jsp:useBean id="datosVO" class="articulacion.asigGrupo.vo.DatosVO" scope="session"/>


<%@include file="../../parametros.jsp"%>
<c:set var="tip" value="8" scope="page"/>
<script language="javaScript">
<!--
			var nav4=window.Event ? true : false;

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-- Seleccione uno --","-1");
	}
	
	function limpiar_combo(combo){
		combo.selectedIndex = 0;
	}

	function borrar_combo3(combo){
		if(combo.length){
			for(var i=0;i<combo.length;i++) {
				combo[i].checked=false;
				combo[i].disabled=true;
				if(document.getElementById('td'+combo[i].value))document.getElementById('td'+combo[i].value).style.display='none';
			}
		 }else{
			combo.checked=false;
			combo.disabled=true;
			if(document.getElementById('td'+combo.value))
				document.getElementById('td'+combo.value).style.display='none';
		 }
	}
	
	
	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.rotdagsede, "- Seleccione una sede",1)
		validarLista(forma.rotdagjornada, "- Seleccione una jornada",1)
		/*validarLista(forma.rotdagasignatura, "- Seleccione una asignatura",1)
		validarEntero(forma.rotdaghoras, "- Total horas semanales", 1, 99)
		if(forma.rotdaggrados.length){
			for(var i=0;i<forma.rotdaggrados.length;i++){
				if(forma.rotdaggrados_[i].disabled==false && forma.rotdaggrados_[i].checked==true){
					validarEntero(forma.rotdagIHgrados_[i], "- Total horas semanales de grados seleccionados", 1, 99)
				}
			}
		}else{
				if(forma.rotdaggrados_.disabled==false && forma.rotdaggrados_.checked==true){
					validarEntero(forma.rotdagIHgrados_, "- Total horas semanales de grados seleccionados", 1, 99)
				}
		}*/
	}

	function guardarCarga(){
		var listaCarga = document.getElementById('frmListaCarga').contentWindow.document;
		listaCarga.frmCarga.tipo.value=8;
		listaCarga.frmCarga.ihTotal2=document.frmNuevo.rotdaghoras.value;
		listaCarga.frmCarga.target="_parent";
		listaCarga.frmCarga.action ='<c:url value="/personal/NuevoGuardar.jsp"/>';
		listaCarga.frmCarga.cmd.value="Guardar";
		listaCarga.frmCarga.rotdagsede.value=document.frmNuevo.rotdagsede.value;
		listaCarga.frmCarga.rotdagjornada.value=document.frmNuevo.rotdagjornada.value;
		listaCarga.frmCarga.rotdagmetodologia.value=document.frmNuevo.rotdagmetodologia.value;
		listaCarga.frmCarga.rotdaghoras.value=document.frmNuevo.rotdaghoras.value;
		
		var elements = listaCarga.getElementsByClassName("txtHorasAsignatura");
		var sumaCarga=0;
		for(var i=0; i<elements.length; i++) {
		    if (elements[i].value != ""){
		    	sumaCarga+=parseInt(elements[i].value);
		    }
		}
		if(sumaCarga > document.frmNuevo.rotdaghoras.value){
			alert("el total de horas asignadas excede en "+(sumaCarga-document.frmNuevo.rotdaghoras.value)+" el numero de horas semanales disponibles del docente");
		}else{
			listaCarga.frmCarga.submit();	
		}
		return false;
		
	}
	
	function lanzar(tipo){
		document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.action='<c:url value="/personal/ControllerNuevoEdit.do"/>';
		document.frmNuevo.target="";
		
		document.frmNuevo.submit();
	}
	
	function buscar(){
		document.frmAjax.anho.value = document.frmNuevo.anoVigencia.value;
		document.frmAjax.metodologia.value = document.frmNuevo.metodologia.value;
		document.frmAjax.especialidad.value = document.frmNuevo.especialidad.value;
		document.frmAjax.asignatura.value = document.frmNuevo.asignatura.value;
		document.frmAjax.semestre.value = document.frmNuevo.semestre.value;
		document.frmAjax.grado.value = document.frmNuevo.grado.value;
		document.frmAjax.grupo.value = document.frmNuevo.grupo.value;
		
		document.frmAjax.cmd.value='<c:out value="${sessionScope.paramsVO.CMD_AJAX_BUSCAR}"/>';
		document.frmAjax.tipo.value = '<c:out value="${sessionScope.paramsVO.FICHA_BUSCAR}"/>';
		document.frmAjax.target = 'frmListaCarga';
		document.frmAjax.submit();
		
	}
	
	function guardar(){
		document.frmListaCarga.document.frmCarga.asignatura.value = document.frmNuevo.asignatura.value;
		document.frmListaCarga.document.frmCarga.grupo.value = document.frmNuevo.grupo.value;
		document.frmListaCarga.document.frmCarga.target = 'frmListaCarga';
		document.frmListaCarga.document.frmCarga.submit();
		
	}

	
	
	function resizeIframe(iframe) {
		iframe.height = iframe.contentWindow.document.body.scrollHeight + "px";
	}
  
	function ajaxEspecialidad(){
		document.frmAjax.anho.value = document.frmNuevo.anoVigencia.value;
		document.frmAjax.semestre.value = document.frmNuevo.semestre.value;
		document.frmAjax.especialidad.value = document.frmNuevo.especialidad.value;
		document.frmAjax.cmd.value='<c:out value="${sessionScope.paramsVO.CMD_AJAX_GET_ASIGNATURA}"/>';
		document.frmAjax.tipo.value = '<c:out value="${sessionScope.paramsVO.FICHA_ASIGNAR_GRUPO}"/>';
		document.frmAjax.target='frameAjax';
		document.frmAjax.submit();
		//document.frmCarga.submit();
		//document.getElementById('frmListaCarga').contentDocument.location.reload(true);
	}
	
	function ajaxGrado(){
		document.frmAjax.anho.value = document.frmNuevo.anoVigencia.value;
		document.frmAjax.grado.value = document.frmNuevo.grado.value;
		document.frmAjax.metodologia.value = document.frmNuevo.metodologia.value;
		document.frmAjax.cmd.value='<c:out value="${sessionScope.paramsVO.CMD_AJAX_GET_GRUPOS}"/>';
		document.frmAjax.tipo.value = '<c:out value="${sessionScope.paramsVO.FICHA_ASIGNAR_GRUPO}"/>';
		document.frmAjax.target='frameAjax';
		document.frmAjax.submit();
		//document.frmCarga.submit();
		//document.getElementById('frmListaCarga').contentDocument.location.reload(true);
	}
	
	function ajaxListaCarga(){
		document.frmAjaxListaCarga.sedeSeleccionada.value = document.frmNuevo.rotdagsede.value;
		document.frmAjaxListaCarga.jornadaSeleccionada.value = document.frmNuevo.rotdagjornada.value;
		document.frmAjaxListaCarga.metodologiaSeleccionada.value = document.frmNuevo.rotdagmetodologia.value;
		document.frmAjaxListaCarga.tipo.value=11;
		document.frmAjaxListaCarga.target='frameAjax';
		document.frmAjaxListaCarga.submit();
		//document.frmCarga.submit();
		//document.getElementById('frmListaCarga').contentDocument.location.reload(true);
	}
	
	function resizeIframe(iframe) {
		iframe.height = iframe.contentWindow.document.body.scrollHeight + "px";
	}
	
//-->
</script>
<%@include file="../../mensaje.jsp" %>
<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)"
	action='<c:url value="/personal/NuevoGuardar.jsp"/>'>
		<input type="hidden" name="cmd" value=""> 
		<input type="hidden" name="ext" value=""> 
		<input TYPE="hidden" name="height" value="180"> 
		<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'> 
		<input type="hidden" name="rotdagdocente" value='<c:out value="${sessionScope.nuevoPersonal.pernumdocum}"/>'>
		<input type="hidden" name="rotdagVigencia" value='<c:out value="${sessionScope.nuevoPersonal2.perVigencia}"/>'>
		<input type="hidden" name="ihTotal2" value='<c:out value="${requestScope.ihTotal2}"/>'> 
		<input type="hidden" name="ihTotal3" value='0'> 
		<input type="hidden" name="sedeSeleccionada" value='0'> 
		<input type="hidden" name="jornadaSeleccionada" value='0'> 
		<input type="hidden" name="metodologiaSeleccionada" value='0'>

	<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
		<caption>Filtro de Búsqueda</caption>
		<tr>
			<td colspan="2" width="50%"><input name="cmd2" class="boton"
				type="button" value="Buscar" onClick="buscar()"></td>
		</tr>
	</table>
	<br>
	<table>
		<tr>
			<td width="45%" align="left"><input name="cmd2" class="boton"
				type="button" value="Guardar" onClick="guardar();"></td>
		</tr>
	</table>

	<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		<tr>
			<td>
				<span class="style2">*</span><b>Vigencia:</b></td>
			<td>
				<select name="anoVigencia" style='width: 50px;'>
					<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
						<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.login.vigencia_actual}">selected</c:if>>
							<c:out value="${item.nombre}" />
						</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<span class="style2">*</span> Metodología:
			</td>
			<td>
				<select name="metodologia" style='width: 120px;'>
					<option value="0">--Seleccione una--</option>
					<c:forEach begin="0" items="${requestScope.listaMetodologias}" var="fila">
						<option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.login.vigencia_actual==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td><span class="style2">*</span><b>Semestre:</b></td>
			<td>
				<select name="semestre" style="width: 50px;">
					<option value="0">--</option>
					<option value="1" <c:if test="${1==sessionScope.datosVO.anho}">selected</c:if>>1</option>
					<option value="2" <c:if test="${2==sessionScope.datosVO.anho}">selected</c:if>>2</option>
				</select>
			</td>
		</tr>
		<tr>
			<td id='esp'><span class="style2">*</span><b>Especialidad:</b></td>
			<td id='esp1' colspan="4">
				<select name="especialidad" onchange="ajaxEspecialidad();">
					<option value="0">--Seleccione una--</option>
					<c:forEach begin="0" items="${requestScope.lEspecialidadVO}" var="especialidad">
						<option value="<c:out value="${especialidad.codigo}"/>"
							<c:if test="${especialidad.codigo==sessionScope.datosVO.especialidad}">selected</c:if>>
							<c:out value="${especialidad.nombre}" />
						</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td id='esp'><span class="style2">*</span><b>Asignatura:</b></td>
			<td id='esp1' colspan="4"><select name="asignatura" id="asignatura">
					<option value="0">--Seleccione una--</option>
					
			</select></td>
		</tr>
		<tr>
			<td id='esp'><span class="style2">*</span><b>Grado:</b></td>
			<td id='esp1' colspan="4">
				<select name="grado" id="grado" onchange="ajaxGrado();">
					<option value="0">--Seleccione una--</option>
					<option value="10">Grado 10</option>
					<option value="11">Grado 11</option>
				</select>
			</td>
		</tr>
		<tr>
			<td id='esp'><span class="style2">*</span><b>Grupo:</b></td>
			<td id='esp1' colspan="4">
				<select name="grupo" id="grupo">
					<option value="0">--Seleccione una--</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="display: none"><iframe name="frameAjax"	id="frameAjax"></iframe></td>
		</tr>
		<tr>
			<td colspan="4">
				<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
					<caption>Matriz de Asignacion</caption>
					<tr>
						<td>
							<iframe id="frmListaCarga" name="frmListaCarga" onload="resizeIframe(this)" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="500"
								src='<c:url value="/articulacion/asigGrupo/AjaxCarga.do?cmd=12&tipo=2"></c:url>' onload='reSize()'>
							</iframe>
						</td>
					</tr>
				</table>
				
			</td>
		</tr>
	</table>
</form>
	<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		<tr>
			<td>
				<form method="post" name="frmAjax" action="<c:url value="/articulacion/asigGrupo/AjaxCargaSave.jsp"/>">
					<input type="hidden" name="institucion" value='<c:out value="${sessionScope.login.instId}"/>'> 
					<input type="hidden" name="anho">
					<input type="hidden" name="metodologia">
					
					<input type="hidden" name="especialidad">
					<input type="hidden" name="asignatura">
					
					<input type="hidden" name="semestre">
					
					<input type="hidden" name="grado">
					<input type="hidden" name="grupo">
					
					<input TYPE="hidden" name="tipo" value='1'>
					<input type="hidden" name="cmd" value='<c:out value="${sessionScope.paramsVO.CMD_AJAX_GET_ASIGNATURA}"/>'>
					
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<form method="post" name="frmAjaxListaCarga" action='<c:url value="/personal/ControllerNuevoEdit.do"/>'>
					<input type="hidden" name="cmd" value="">
					<input type="hidden" name="tipo" value="">
					
					<input type="hidden" name="rotdagdocente" value='<c:out value="${sessionScope.nuevoPersonal.pernumdocum}"/>'>
					<input type="hidden" name="rotdagVigencia" value='<c:out value="${sessionScope.nuevoPersonal2.perVigencia}"/>'>
		            <input type="hidden" name="ihTotal2" value='<c:out value="${requestScope.ihTotal2}"/>'>
					<input type="hidden" name="ihTotal3" value='0'>
					
					<input type="hidden" name="sedeSeleccionada" value='0'>
					<input type="hidden" name="jornadaSeleccionada" value='0'>
					<input type="hidden" name="metodologiaSeleccionada" value='0'>
				</form>
			</td>
		</tr>
	</table>
	
	
