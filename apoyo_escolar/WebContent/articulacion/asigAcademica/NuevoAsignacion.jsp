<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asigAcademica.vo.ParamsVO" scope="page"/>
<html>
<head>
<script type="text/javascript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function ajaxEspecialidad(obj){
		borrar_combo(document.frmNuevo.asigEspecialidad); 
		var val=obj.options[obj.selectedIndex].value;
		document.frmNuevo.asigSemestre.selectedIndex=0;
		if(val==1){//academico
			document.frmNuevo.asigEspecialidad.disabled=true;
		}else{//tecnico
			document.frmNuevo.asigEspecialidad.disabled=false;
			document.frmAjax2.ajax[0].value=document.frmNuevo.asigInstitucion.value;
			document.frmAjax2.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ESPECIALIDAD}"/>';
	 		document.frmAjax2.target="frameAjax2";
			document.frmAjax2.submit();
		}
	}
	
	function ajaxSemestre(obj){
		document.frmNuevo.asigSemestre.selectedIndex=0;
	}
	
	function ajaxAsignatura(){
		borrar_combo(document.frmNuevo.asigAsignatura); 
		document.frmAjax2.ajax[0].value=document.frmNuevo.asigInstitucion.value;
		document.frmAjax2.ajax[1].value=document.frmNuevo.asigComponente.value;//options[document.frmNuevo.asigComponente.selectedIndex].value;
		document.frmAjax2.ajax[2].value=document.frmNuevo.asigEspecialidad.options[document.frmNuevo.asigEspecialidad.selectedIndex].value;
		document.frmAjax2.ajax[3].value=document.frmNuevo.asigSemestre.options[document.frmNuevo.asigSemestre.selectedIndex].value;
		document.frmAjax2.ajax[4].value=document.frmNuevo.asigAnho.value;
		document.frmAjax2.ajax[5].value=document.frmNuevo.asigPeriodo.value;
		document.frmAjax2.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ASIGNATURA}"/>';
 		document.frmAjax2.target="frameAjax2";
		document.frmAjax2.submit();
	}
	
	function initEspecialidad(obj){
		var val=obj.options[obj.selectedIndex].value;
		if(val==1){
			document.frmNuevo.asigEspecialidad.disabled=true;
		}
	}
	function guardar(){
		if(validarForma(document.frmNuevo)){
				if(validarIntensidad(document.frmNuevo)){
						document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
						document.frmNuevo.submit();
				}		
		 }
	}
	
	function validarIntensidad(forma){
		var ihReal=parseInt(document.frmNuevo.asigIntHorReal.value);
		var ih=parseInt(document.frmNuevo.asigIntHor.value);
		var ihTotal=parseInt(document.frmNuevo.asigIntHorTotal.value);
		if((ihReal+ih)>ihTotal){
			alert(' No se puede guardar la asignación porque excede el total permitido. Solo dispone de '+(ihTotal-ihReal)+' horas para asignar');
			return false;
		}
		return true;
	}
	
	function nuevo(){
			document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
			document.frmNuevo.submit();
	}
	
		function hacerValidaciones_frmNuevo(forma){
			validarEntero(forma.asigIntHorTotal, "- Intensidad horaria total de clases", 1,99)
			validarEntero(forma.asigIntHorExtra, "- Intensidad horaria total de extras", 1,99)
			validarEntero(forma.asigDocente, "- Docente (Debe buscar por docente en el filtro)", 1,999999999999)
			//validarLista(forma.asigComponente, "- Componente", 1)
			//var j=forma.asigComponente.options[forma.asigComponente.selectedIndex].value;
			//if(j==2){
				validarLista(forma.asigEspecialidad, "- Especialidad", 1)
			//}
			validarLista(forma.asigSemestre, "- Semestre", 1)
			validarLista(forma.asigAsignatura, "- Asignatura", 1)
			validarEntero(forma.asigIntHor, "- Intensidad horaria semanal", 1,99)
		}
</script>
<c:import url="/parametros.jsp"/>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:300px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="/articulacion/asigAcademica/Lista.do"/></div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/asigAcademica/SaveAsignacion.jsp"/>'>
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ASIGNACION}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="asigInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="asigDocente" value='<c:out value="${sessionScope.filAsigAcaVO.docente}"/>'>
			<input type="hidden" name="asigAnho" value='<c:out value="${sessionScope.filAsigAcaVO.anho}"/>'>
			<input type="hidden" name="asigPeriodo" value='<c:out value="${sessionScope.filAsigAcaVO.periodo}"/>'>
			<input type="hidden" name="asigIntHorReal" value='<c:out value="${sessionScope.filAsigAcaVO.asigIntHorReal}"/>'>
			<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="asigComponente" value='2'>
			
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Asignación académica</caption>
				<tr><td width="45%">
					<c:if test="${sessionScope.NivelPermiso==2}">
						<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		    		<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    		</c:if>
	    		Cantidad de horas disponibles: <c:out value="${sessionScope.filAsigAcaVO.asigIntHorTotal-sessionScope.filAsigAcaVO.asigIntHorReal}"/>
			  </td></tr>	
	  </table>
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
				<td>
					<span class="style2">*</span><b>&nbsp;Intensidad horaria total de clases:</b>
				</td>
				<td>
					<input name="asigIntHorTotal" type="text" maxlength="2" size="3" style="width:25px;" value='<c:out value="${sessionScope.filAsigAcaVO.asigIntHorTotal}"/>' onKeyPress='return acepteNumeros(event)'>
				</td>
				<td>
					<span class="style2">*</span><b>&nbsp;Intensidad horaria total de extras:</b>
				</td>
				<td>
					<input name="asigIntHorExtra" type="text" maxlength="2" size="3" style="width:25px;" value='<c:out value="${sessionScope.filAsigAcaVO.asigIntHorExtra}"/>' onKeyPress='return acepteNumeros(event)'>
				</td>
			</tr>
			<tr>
				<!-- <td>
					<span class="style2">*</span><b>&nbsp;Componente:</b>
				</td>
				<td>
					<select name="asigComponente" style='width:100px;' onchange="ajaxEspecialidad(this)" <c:if test="${sessionScope.asignacionVO.formaEstado==1}">disabled</c:if>>
						<c:forEach begin="0" items="${requestScope.lComponenteVO}" var="comp">
							<option value="<c:out value="${comp.codigo}"/>" <c:if test="${comp.codigo==sessionScope.asignacionVO.asigComponente}">selected</c:if>><c:out value="${comp.nombre}"/></option>
						</c:forEach>
					</select>
				</td> -->
				<td>
					<span class="style2">*</span><b>&nbsp;Especialidad:</b>
				</td>
				<td>
					<select name="asigEspecialidad" style='width:250px;' onchange="ajaxSemestre(this)" <c:if test="${sessionScope.asignacionVO.formaEstado==1}">disabled</c:if>>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lEspecialidadVO}" var="esp">
							<option value="<c:out value="${esp.codigo}"/>" <c:if test="${esp.codigo==sessionScope.asignacionVO.asigEspecialidad}">selected</c:if>><c:out value="${esp.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td>
					<span class="style2">*</span><b>&nbsp;Semestre:</b>
				</td>
				<td>
					<select name="asigSemestre" style='width:35px;' onchange="ajaxAsignatura()" <c:if test="${sessionScope.asignacionVO.formaEstado==1}">disabled</c:if>>
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.lSemestreVO}" var="sem">
							<option value="<c:out value="${sem}"/>" <c:if test="${sem==sessionScope.asignacionVO.asigSemestre}">selected</c:if>><c:out value="${sem}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b>&nbsp;Asignatura:</b>
				</td>
				<td>
					<select name="asigAsignatura" style='width:200px;' <c:if test="${sessionScope.asignacionVO.formaEstado==1}">disabled</c:if>>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lAsignaturaVO}" var="asig">
							<option value="<c:out value="${asig.codigo}"/>" <c:if test="${asig.codigo==sessionScope.asignacionVO.asigAsignatura}">selected</c:if>><c:out value="${asig.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td>
					<span class="style2">*</span><b>&nbsp;Intensidad horaria asignatura:</b>
				</td>
				<td>
					<input name="asigIntHor" type="text" maxlength="2" size="3" style="width:25px;" value='<c:out value="${sessionScope.asignacionVO.asigIntHor}"/>' onKeyPress='return acepteNumeros(event)'>
				</td>
			</tr>
			
			<tr><td style="display:none"><iframe name="frameAjax2" id="frameAjax2"></iframe></td></tr>
	</table>			
	</form>
		<form method="post" name="frmAjax2" action="<c:url value="/articulacion/asigAcademica/Ajax.do"/>">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ASIGNACION}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		</form>
</body>
<script>
initEspecialidad(document.frmNuevo.asigComponente);
</script>
</html>