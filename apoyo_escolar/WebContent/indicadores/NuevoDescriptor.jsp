<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="indicadorDescriptorVO" class="siges.indicadores.vo.DescriptorVO" scope="session"/>
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
		validarLista(forma.desMetodologia, "- Metodologia", 1)
		validarLista(forma.desGrado, "- Grado", 1)
		validarLista(forma.desArea, "- Area", 1)
		if(forma.desDocente) validarLista(forma.desDocente, "- Docente", 1)
		validarLista(forma.desTipo, "- Tipo de descriptor", 1)
		validarLista(forma.desPeriodoIni, "- Periodo inicial", 1)
		validarLista(forma.desPeriodoFin, "- Periodo final", 1)
		
		if(forma.desPeriodoIni.value > forma.desPeriodoFin.value ){
		     appendErrorMessage("- El periodo inicial deber ser menor o igual al periodo final.")
	        if (_campoError == null) {
	            _campoError = forma.desPeriodoIni;
	        }
		}
		
		validarCampo(forma.desNombre, "- Nombre", 1, 1000)
		validarCampo(forma.desAbreviatura, "- Abreviatura", 1, 10)
		validarCampoOpcional(forma.desDescripcion, "- Descripción", 1, 3000)
		if(forma.desDocente) validarLista(forma.desDocente, "- Docente", 1)
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
		borrar_combo(document.frmNuevo.desGrado); 
		document.frmAjax.ajax[0].value=document.frmNuevo.desInstitucion.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.desMetodologia.value;
		if(parseInt(document.frmAjax.ajax[1].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_GRADO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
	
	function ajaxArea(){
		borrar_combo(document.frmNuevo.desArea); 
		document.frmAjax.ajax[0].value=document.frmNuevo.desInstitucion.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.desMetodologia.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.desVigencia.value;
		document.frmAjax.ajax[3].value=document.frmNuevo.desGrado.value;
		if(parseInt(document.frmAjax.ajax[3].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_AREA.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
		
	function ajaxDocente(){
		if(document.frmNuevo.desDocente){
			borrar_combo(document.frmNuevo.desDocente); 
			document.frmAjax.ajax[0].value=document.frmNuevo.desInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmNuevo.desMetodologia.value;
			document.frmAjax.ajax[2].value=document.frmNuevo.desVigencia.value;
			document.frmAjax.ajax[3].value=document.frmNuevo.desGrado.value;
			document.frmAjax.ajax[4].value=document.frmNuevo.desArea.value;
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
	<form method="post" name="frmAjax" action='<c:url value="/indicadores/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DESCRIPTOR}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach></form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/indicadores/SaveDescriptor.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_DESCRIPTOR}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="AJAX_AREA" value='<c:out value="${paramsVO.CMD_AJAX_AREA2}"/>'>
		<input type="hidden" name="AJAX_DOCENTE" value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE2}"/>'>
		<input type="hidden" name="AJAX_GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRADO2}"/>'>
		<input type="hidden" name="desInstitucion" value='<c:out value="${sessionScope.indicadorDescriptorVO.desInstitucion}"/>'>
		<input type="hidden" name="desVigencia" value='<c:out value="${sessionScope.indicadorDescriptorVO.desVigencia}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / edición de descriptor</caption>
				<tr>
					<td colspan="4">
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.filtroDescriptorVO.filInHabilitado==0}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
	    				</c:if>
			  		</td>
			 	</tr>	
			<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>	
			<tr>
			<td width="30%"><span class="style2">*</span> Metodologia:</td>
			<td width="25%">
				<select name="desMetodologia" style="width:120px;" onchange="javaScript:ajaxGrado()" <c:out value="${sessionScope.indicadorDescriptorVO.desDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="metod">
						<option value="<c:out value="${metod.codigo}"/>" <c:if test="${requestScope.metodologiaRefdes==metod.codigo}">selected</c:if><c:if test="${metod.codigo==sessionScope.indicadorDescriptorVO.desMetodologia && requestScope.descriptorNuevo!=1}">selected</c:if>><c:out value="${metod.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
			<td width="30%"><span class="style2">*</span> Grado:</td>
			<td width="25%">
				<select name="desGrado" style="width:120px;" onchange="javaScript:ajaxArea()" <c:out value="${sessionScope.indicadorDescriptorVO.desDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaGrado}" var="grad">
						<option value="<c:out value="${grad.codigo}"/>" <c:if test="${requestScope.gradoRefdes==grad.codigo}">selected</c:if><c:if test="${grad.codigo==sessionScope.indicadorDescriptorVO.desGrado}">selected</c:if>><c:out value="${grad.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
	 	</tr>	
	 	<tr>
			<td width="30%"><span class="style2">*</span> Area:</td>
			<td width="25%">
				<select name="desArea" style="width:200px;" onchange="javaScript:ajaxDocente()"  <c:out value="${sessionScope.indicadorDescriptorVO.desDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaArea}" var="asig">
						<option value="<c:out value="${asig.codigo}"/>" <c:if test="${requestScope.areaRefdes==asig.codigo}">selected</c:if><c:if test="${asig.codigo==sessionScope.indicadorDescriptorVO.desArea}">selected</c:if>><c:out value="${asig.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
				<td><span class="style2">*</span> Tipo de descriptor:</td>
				<td>
					<select name="desTipo" style="width:100px;" <c:out value="${sessionScope.indicadorDescriptorVO.desDisabled}"/>>
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaTipo}" var="per">
							<option value="<c:out value="${per.codigo}"/>" <c:if test="${requestScope.tipoRefdes==per.codigo}">selected</c:if><c:if test="${per.codigo==sessionScope.indicadorDescriptorVO.desTipo && requestScope.descriptorNuevo!=1}">selected</c:if>><c:out value="${per.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			 	<c:if test="${sessionScope.indicadorDescriptorVO.desPlanEstudios==1}">
				<tr>
				<td><span class="style2">*</span> Docente:</td>
				<td>
					<select name="desDocente" style="width:200px;"  <c:out value="${sessionScope.indicadorDescriptorVO.desDisabled}"/>>
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaDocente}" var="doc">
							<option value="<c:out value="${doc.codigo}"/>" <c:if test="${doc.codigo==sessionScope.indicadorDescriptorVO.desDocente}">selected</c:if>><c:out value="${doc.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				</tr>
				</c:if>	 	
		 	<tr>
				<td><span class="style2">*</span> Periodo inicial:</td>
				<td>
					<select name="desPeriodoIni" style="width:100px;">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaPeriodo}" var="per">
							<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==requestScope.periniref}">selected</c:if><c:if test="${per.codigo==sessionScope.indicadorDescriptorVO.desPeriodoIni}">selected</c:if>><c:out value="${per.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Periodo final:</td>
				<td>
					<select name="desPeriodoFin" style="width:100px;">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaPeriodo}" var="per">
							<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==requestScope.perfinref}">selected</c:if><c:if test="${per.codigo==sessionScope.indicadorDescriptorVO.desPeriodoFin}">selected</c:if>><c:out value="${per.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
		 	</tr>
			<tr>
				<td><span class="style2">*</span> Descriptor:</td>
				<td colspan="3">
				<input type="text" name="desNombre" maxlength="1000" size="50" value='<c:out value="${sessionScope.indicadorDescriptorVO.desNombre}"/><c:out value="${requestScope.logroref}"/>'></input>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Abreviatura:</td>
				<td colspan="1">
				<input type="text" name="desAbreviatura" maxlength="10" size="10" value='<c:out value="${sessionScope.indicadorDescriptorVO.desAbreviatura}"/><c:out value="${requestScope.abreref}"/>'></input>
				</td>
				
				<td> Orden:</td>
				<td colspan="1">
				<input type="text" name="desOrden" maxlength="10" size="10" value='<c:if test="${empty requestScope.ordref}"><c:out value="${sessionScope.indicadorDescriptorVO.desOrden}"/></c:if><c:if test="${!empty requestScope.ordref}"><c:out value="${requestScope.ordref}"/></c:if>'></input>
				</td>
			</tr>	
			<tr>			
				<td>Comentario:</td>
				<td colspan="3">
					<textarea name="desDescripcion" cols="50" rows="3" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);" ><c:out value="${sessionScope.indicadorDescriptorVO.desDescripcion}"/><c:out value="${requestScope.comref}"/></textarea>
				</td>
		 	</tr>	
		</table>	
	</form>
</body>
</html>