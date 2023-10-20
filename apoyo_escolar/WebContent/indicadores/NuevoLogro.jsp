<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="indicadorLogroVO" class="siges.indicadores.vo.LogroVO" scope="session"/>

<jsp:useBean id="paramsVO" class="siges.indicadores.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.logMetodologia, "- Metodologia", 1)
		validarLista(forma.logGrado, "- Grado", 1)
		validarLista(forma.logAsignatura, "- Asignatura", 1)
		if(forma.logDocente) validarLista(forma.logDocente, "- Docente", 1)
		
		validarLista(forma.logPeriodoIni, "- Periodo inicial", 1)
		validarLista(forma.logPeriodoFin, "- Periodo final", 1)
		
		if(forma.logPeriodoIni.value > forma.logPeriodoFin.value ){
		     appendErrorMessage("- El periodo inicial deber ser menor o igual al periodo final.")
	        if (_campoError == null) {
	            _campoError = forma.logPeriodoIni;
	        }
		}
		
		validarCampo(forma.logNombre, "- Nombre", 1, 1000)
		validarCampo(forma.logAbreviatura, "- Abreviatura", 1, 10)
		validarCampoOpcional(forma.logDescripcion, "- Descripción", 1, 3000)
	}
		
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/indicadores/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--Seleccione una--","-99");
	}
		
	function ajaxGrado(){
		borrar_combo(document.frmNuevo.logGrado); 
		document.frmAjax.ajax[0].value=document.frmNuevo.logInstitucion.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.logMetodologia.value;
		if(parseInt(document.frmAjax.ajax[1].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_GRADO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
	
	function ajaxAsignatura(){
		borrar_combo(document.frmNuevo.logAsignatura); 
		document.frmAjax.ajax[0].value=document.frmNuevo.logInstitucion.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.logMetodologia.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.logVigencia.value;
		document.frmAjax.ajax[3].value=document.frmNuevo.logGrado.value;
		if(parseInt(document.frmAjax.ajax[3].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_ASIGNATURA.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
		
	function ajaxDocente(){
		if(document.frmNuevo.logDocente){
			borrar_combo(document.frmNuevo.logDocente); 
			document.frmAjax.ajax[0].value=document.frmNuevo.logInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmNuevo.logMetodologia.value;
			document.frmAjax.ajax[2].value=document.frmNuevo.logVigencia.value;
			document.frmAjax.ajax[3].value=document.frmNuevo.logGrado.value;
			document.frmAjax.ajax[4].value=document.frmNuevo.logAsignatura.value;
			if(parseInt(document.frmAjax.ajax[4].value)!=-99){
				document.frmAjax.cmd.value=document.frmNuevo.AJAX_DOCENTE.value;
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
			}	
		}
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/indicadores/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmAjax" action='<c:url value="/indicadores/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_LOGRO}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach></form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/indicadores/SaveLogro.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_LOGRO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="AJAX_ASIGNATURA" value='<c:out value="${paramsVO.CMD_AJAX_ASIGNATURA2}"/>'>
		<input type="hidden" name="AJAX_DOCENTE" value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE2}"/>'>
		<input type="hidden" name="AJAX_GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRADO2}"/>'>
		<input type="hidden" name="logInstitucion" value='<c:out value="${sessionScope.indicadorLogroVO.logInstitucion}"/>'>
		<input type="hidden" name="logVigencia" value='<c:out value="${sessionScope.indicadorLogroVO.logVigencia}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / edición de logro</caption>
				<tr>
					<td colspan="4">
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.filtroLogroVO.filInHabilitado==0}">
    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
	    				</c:if>
			  		</td>
			 	</tr>	
			<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>	
			<tr>
			<td width="30%"><span class="style2">*</span> Metodologia:</td>
			<td width="25%">
				<select name="logMetodologia" style="width:120px;" onchange="javaScript:ajaxGrado()" <c:out value="${sessionScope.indicadorLogroVO.logDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="metod">
						<option value="<c:out value="${metod.codigo}"/>" <c:if test="${requestScope.metodologiaRef==metod.codigo}">selected</c:if><c:if test="${metod.codigo==sessionScope.indicadorLogroVO.logMetodologia && requestScope.logroNuevo!=1}">selected</c:if>><c:out value="${metod.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
			<td width="30%"><span class="style2">*</span> Grado:</td>
			<td width="25%">
				<select name="logGrado" style="width:120px;" onchange="javaScript:ajaxAsignatura()" <c:out value="${sessionScope.indicadorLogroVO.logDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaGrado}" var="grad">
						<option value="<c:out value="${grad.codigo}"/>" <c:if test="${requestScope.gradoRef==grad.codigo}">selected</c:if><c:if test="${grad.codigo==sessionScope.indicadorLogroVO.logGrado}">selected</c:if>><c:out value="${grad.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
			</tr>
			<tr>
			<td width="30%"><span class="style2">*</span> Asignatura:</td>
			<td width="25%" colspan="3">
				<select name="logAsignatura" style="width:200px;" onchange="javaScript:ajaxDocente()" <c:out value="${sessionScope.indicadorLogroVO.logDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaAsignatura}" var="asig">
						<option value="<c:out value="${asig.codigo}"/>" <c:if test="${requestScope.asignaturaRef==asig.codigo}">selected</c:if><c:if test="${asig.codigo==sessionScope.indicadorLogroVO.logAsignatura}">selected</c:if>><c:out value="${asig.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
		 	</tr>	
		 	<c:if test="${sessionScope.indicadorLogroVO.logPlanEstudios==1}">
			 	<tr>
					<td width="30%"><span class="style2">*</span> Docente:</td>
					<td width="25%">
						<select name="logDocente" style="width:200px;" <c:out value="${sessionScope.indicadorLogroVO.logDisabled}"/>>
							<option value="-99" selected>--Seleccione uno--</option>
							<c:forEach begin="0" items="${requestScope.listaDocente}" var="doc">
								<option value="<c:out value="${doc.codigo}"/>" <c:if test="${doc.codigo==sessionScope.indicadorLogroVO.logDocente}">selected</c:if>><c:out value="${doc.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
			 	</tr>	
		 	</c:if>
		 	<tr>
				<td><span class="style2">*</span> Periodo inicial:</td>
				<td>
					<select name="logPeriodoIni" style="width:100px;" <c:if test="${sessionScope.filtroLogroVO.filInHabilitado==1}">disabled</c:if>>
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaPeriodo}" var="per">
							<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==requestScope.perinirefdes}">selected</c:if><c:if test="${per.codigo==sessionScope.indicadorLogroVO.logPeriodoIni}">selected</c:if>><c:out value="${per.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Periodo final:</td>
				<td>
					<select name="logPeriodoFin" style="width:100px;" <c:if test="${sessionScope.filtroLogroVO.filInHabilitado==1}">disabled</c:if>>
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaPeriodo}" var="per">
							<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==requestScope.perfinrefdes}">selected</c:if><c:if test="${per.codigo==sessionScope.indicadorLogroVO.logPeriodoFin}">selected</c:if>><c:out value="${per.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
		 	</tr>
			<tr>
				<td><span class="style2">*</span> Logro:</td>
				<td colspan="3">
				<input type="text" name="logNombre" maxlength="1000" size="50" value='<c:out value="${sessionScope.indicadorLogroVO.logNombre}"/><c:out value="${requestScope.logrorefdes}"/>' <c:if test="${sessionScope.filtroLogroVO.filInHabilitado==1}">disabled</c:if>></input>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Abreviatura:</td>
				<td colspan="1">
				<input type="text" name="logAbreviatura" maxlength="10" size="10" value='<c:out value="${sessionScope.indicadorLogroVO.logAbreviatura}"/><c:out value="${requestScope.abrerefdes}"/>' <c:if test="${sessionScope.filtroLogroVO.filInHabilitado==1}">disabled</c:if>></input>
				</td>
				<td > Orden:</td>
				<td colspan="1">
				<input type="text" name="logOrden" maxlength="10" size="10" value='<c:if test="${empty requestScope.ordrefdes}"><c:out value="${sessionScope.indicadorLogroVO.logOrden}"/></c:if><c:if test="${!empty requestScope.ordrefdes}"><c:out value="${requestScope.ordrefdes}"/></c:if>' <c:if test="${sessionScope.filtroLogroVO.filInHabilitado==1}">disabled</c:if>></input>
				</td>
			</tr>	
			<tr>			
				<td>Comentario:</td>
				<td colspan="3">
					<textarea name="logDescripcion" cols="50" rows="3" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);" <c:if test="${sessionScope.filtroLogroVO.filInHabilitado==1}">disabled</c:if>><c:out value="${sessionScope.indicadorLogroVO.logDescripcion}"/><c:out value="${requestScope.comrefdes}"/></textarea>
				</td>
		 	</tr>	
		</table>	
	</form>
</body>
</html>