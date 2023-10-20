<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="especialidadVO" class="articulacion.especialidad.vo.EspecialidadVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.especialidad.vo.ParamsVO" scope="page"/>
<link rel="stylesheet" type="text/css" media="all" href='<c:url value="/etc/css/calendar-win2k-1.css"/>' title="win2k-cold-1" />
<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/articulacion/especialidad/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}

	function textCounter(field, countfield, maxlimit) {
	if (field.value.length > maxlimit) // if too long...trim it!
	field.value = field.value.substring(0, maxlimit);
	// otherwise, update 'characters left' counter
	else 
	countfield.value = maxlimit - field.value.length;
	}

	function hacerValidaciones_frmNuevo(forma){
		//validarEnteroOpcional(forma.carNumperiodo, "- Número de Semestres",999)
		validarCampo(forma.carNombre, "- Nombre de Especialidad", 1, 150)
		validarCampoOpcional(forma.carRegicfes, "- Registro Icfes", 1, 20)
		validarCampo(forma.carTitulo, "- Título Especialidad", 1, 100)
		validarLista(forma.carCouniv, "- Universidad", 1)
		validarLista(forma.carCodigo, "- Especialidad Base", 1)
		validarLista(forma.carNumaprob, "- Número de Resolución",999)
//		validarFechaUnCampo(forma.carFecaprob, "- Fecha")
		validarFechaOpcional(forma.carFecaprob, "- El formato de fecha no es correcto",0/0/0)
	}
	
	function asig(nom){         
           document.frmNuevo.carNombre.value=nom.options[nom.selectedIndex].text;
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/especialidad/Lista.do"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/especialidad/Save.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>

		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Información Especialidad</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        			<c:if test="${sessionScope.especialidadVO.formaEstado==1}">
		        				<input name="cmd1" type="button" value="Actualizar" onClick="guardar()" class="boton">
		        			</c:if>
	    					<c:if test="${sessionScope.especialidadVO.formaEstado!=1}">
	    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    					</c:if>
	    				</c:if>
			  		</td>
			 	</tr>	
		  </table>
		  <table width="100%" border="0" cellspacing="3" cellpadding="0">
		<input type="hidden" name="carCodinst" value='<c:out value="${sessionScope.login.instId}"/>'>				
		  <tr>
					<td>
						<span class="style2">*</span><b>Universidad:</b>
					</td>
					<td colspan="3">
							<select name="carCouniv" style='width:300px;' >
								<c:forEach begin="0" items="${requestScope.listaUniversidad}" var="universidad">
									<option value="<c:out value="${universidad.codigo}"/>" <c:if test="${universidad.codigo==sessionScope.especialidadVO.carCouniv}">selected</c:if>><c:out value="${universidad.nombre}"/></option>
								</c:forEach>
							</select>
					</td>
			</tr>
			<tr>
					<td>
						<span class="style2">*</span><b>Especialidad Base:</b>
					</td>
					<td colspan="3">
							<select name="carCodigo" style='width:300px;' onchange="javaScript:asig(this)">
								<c:forEach begin="0" items="${requestScope.listaespecialidadBase}" var="carbase">
									<option value="<c:out value="${carbase.codigo}"/>" <c:if test="${carbase.codigo==sessionScope.especialidadVO.carCodigo}">selected</c:if>><c:out value="${carbase.nombre}"/></option>
								</c:forEach>
							</select>
					</td>
					
				</tr>
				
				<tr>
					<td >
						<span class="style2" colspan="2">*</span><b>Nombre Especialidad:</b>
					</td>
					<td align="left" colspan="3">
						<input type="text" name="carNombre" maxlength="100" size="74"  value='<c:out value="${sessionScope.especialidadVO.carNombre}"/>'></input>
					</td>
				
				</tr>
				
									
								
				<tr>
					
					<td>
						<span class="style2">*</span><b>Título:</b>
					</td>
					<td>
						<input type="text" name="carTitulo" maxlength="100" size="22"  value='<c:out value="${sessionScope.especialidadVO.carTitulo}"/>'></input>
					</td>
					
					<td>
						<span class="style2"></span><b>Número Resolución:</b>
					</td>
					<td>
						<input type="text" name="carNumaprob" maxlength="20" size="22"  value='<c:out value="${sessionScope.especialidadVO.carNumaprob}"/>'></input>
					</td>
				</tr>
				
				<tr>
			
				<td>
					<b>Fecha:</b>
				</td>
				<td>
					<input type="text"  readonly=true name="carFecaprob" id="carFecaprob" maxlength="10" size="10" value='<c:out value="${sessionScope.especialidadVO.carFecaprob}"/>'>
						<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione fecha inicial" id="imgfechaini"
						 style="cursor: pointer;"
						 title="Date selector"
						 onmouseover="this.style.background='red';"
						 onmouseout="this.style.background=''" />
				</td>
				
			<td>
						<b>Registro Icfes:</b>
					</td>
					<td>
						<input type="text" name="carRegicfes" maxlength="20" size="22"  value='<c:out value="${sessionScope.especialidadVO.carRegicfes}"/>'></input>
					</td>
					
				</tr>
				
				<tr>
				
		<td>
						<span class="style2"></span><b>Número Semestres:</b>
					</td>
					<td>
						<input type="text" name="carNumperiodo" maxlength="1" size="1" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.especialidadVO.carNumperiodo}"/>'></input>
					</td>
				
				<td>
						<span class="style2"></span><b>Tipo Programa:</b>
					</td>
					<td>
							<select name="carTipoPrograma" style='width:170px;'>
								<c:forEach begin="0" items="${requestScope.listaTipoPrograma}" var="tipoPrograma">
									<option value="<c:out value="${tipoPrograma.codigo}"/>" <c:if test="${tipoPrograma.codigo==sessionScope.especialidadVO.carTipoPrograma}">selected</c:if>><c:out value="${tipoPrograma.nombre}"/></option>
								</c:forEach>
							</select>
					</td>
					
					
				</tr>
				
				<tr>
				<td>
						<span class="style2"></span><b>Descripción:</b>
					</td>
					<td colspan="3">
						<textarea name="carDescripcion" cols="72" rows="3"
						onKeyDown="textCounter(this.form.carDescripcion,199,199);" onKeyUp="textCounter(this.form.carDescripcion,199,199);" ><c:out value="${sessionScope.especialidadVO.carDescripcion}"/></textarea>
					</td>
				</tr>
			</table>
		  
		  
		</form>
		<script>
Calendar.setup({
		inputField     :    "carFecaprob",
		ifFormat       :    "%d/%m/%Y",
		button         :    "imgfechaini",
		align          :    "Br"
});
</script>
</body>
</html>