<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.retiroEstudiantes.vo.ParamsVO" scope="page"/>
<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate value="${now}" type="time" timeStyle="short" pattern="yyyy" var="ano"/>
<html>
<head>
<script languaje="javaScript">
	
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function filtrar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
			document.frmFiltro.action='<c:url value="/articulacion/retiroEstudiantes/Save0.jsp"/>';
			document.frmFiltro.submit();
		}
	}
	
	function cambioSelect(objeto,x){
	
		var variable=objeto.options[objeto.selectedIndex].value;
		 if(variable=='2'){
		       document.frmFiltro.especialidad.disabled=false;
		} if(variable=='1'){
			   document.frmFiltro.especialidad.disabled=true;
			   document.frmFiltro.especialidad.selectedIndex=0;
		}
		if(x==0){
			ajaxGrupo(document.frmFiltro.semestre);
		}
	}

	function hacerValidaciones_frmFiltro(forma){
		//validarLista(forma.especialidad, "- Seleccione un especialidad", 1);
		validarLista(forma.semestre, "- Seleccione un semestre", 1);
		validarLista(forma.grupo, "- Seleccione una grupo", 1);
		validarLista(forma.estudiante, "- Seleccione una estudiante", 1);
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--","0");
	}
	
	function iniciar(){
		document.frmFiltro.perVigencia.selectedIndex=0;
		borrar_combo(document.frmFiltro.grupo);
	}
	
	function ajaxGrupo(obj){
		borrar_combo(document.frmFiltro.grupo); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){//
			document.frmAjaxGrupo.ajax[1].value=document.frmFiltro.sede.value;
			document.frmAjaxGrupo.ajax[2].value=document.frmFiltro.jornada.value;
			document.frmAjaxGrupo.ajax[4].value=val;
			document.frmAjaxGrupo.cmd.value='<c:out value="${paramsVO.CMD_AJAX_GRUPO}"/>';
	 		document.frmAjaxGrupo.target="frameAjax";
			document.frmAjaxGrupo.submit();
		}
		
	}
	
	function ajaxEstudiante(obj){
	
		borrar_combo(document.frmFiltro.estudiante); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){//
			document.frmAjaxEstudiante.ajax[1].value=document.frmFiltro.sede.value;
			document.frmAjaxEstudiante.ajax[2].value=document.frmFiltro.jornada.value;
			document.frmAjaxEstudiante.ajax[3].value=document.frmFiltro.semestre.value;
			document.frmAjaxEstudiante.ajax[4].value=val;
			document.frmAjaxEstudiante.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ESTUDIANTE}"/>';
	 		document.frmAjaxEstudiante.target="frameAjax";
			document.frmAjaxEstudiante.submit();
		}
	}
	
	
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body>
<form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
	<tr height="1">
		<td width="10">&nbsp;</td>
		<td rowspan="2" width="400">
			<img src='<c:url value="/etc/img/tabs/novedades_articulacion_1.gif"/>' alt="Novedades Articulación" height="26" border="0">
		</td>
	</tr>
</table>
			 <input type="hidden" name="cmd" value=''>
			 <input type="hidden" name="inst" value='<c:out value="${sessionScope.login.instId}"/>'>
			 <input type="hidden" name="institucion" value='<c:out value="${sessionScope.login.instId}"/>'>	 
			 <input type="hidden" name="FILTRO" value='<c:out value="${paramsVO.CMD_FILTRAR}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Filtro de Búsqueda estudiantes articulación para retiro</caption>
			<c:if test="${!empty requestScope.listaSedeVO}">
					<tr>
				 		<td style='width:30px;'>
				 			<input name="cmd" type="button" value="Buscar" onClick="filtrar()" class="boton">
				 		</td>
			 		</tr>
			</c:if>
			<tr>
				<td>
					<span class="style2">*</span><b>Sede</b>
				</td>
		 		<td>
					<select name="sede" onchange="setJornadas(this,document.frmFiltro.jornada)">
						<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="sede">
							<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.FilEvaluacionRVO.sede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
						</c:forEach>
					</select>
	  			</td>
	  			<td>
					<span class="style2">*</span><b>Jornada:</b>
				</td>
		  		<td>
					<select name="jornada" onchange="ajaxGrupo(document.frmFiltro.semestre)">
						<c:forEach begin="0" items="${requestScope.listaJornadaVO}" var="jornada">
							<option value="<c:out value="${jornada.codigo}"/>"<c:if test="${jornada.codigo==sessionScope.FilEvaluacionRVO.jornada}">selected</c:if>><c:out value="${jornada.nombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
				
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b>Vigencia</b>
				</td>
				<td>
					<select name="anVigencia">
						<option value="<c:out value="${ano}"/>" <c:if test="${ano==sessionScope.FilEvaluacionRVO.anVigencia}">selected</c:if> ><c:out value="${ano}"/></option>
						<option value="<c:out value="${ano+1}"/>" <c:if test="${ano+1==sessionScope.FilEvaluacionRVO.anVigencia}">selected</c:if> ><c:out value="${ano+1}"/></option>
					</select>
		  		    -
					<select name="perVigencia">
						<option value="1" <c:if test="${1==sessionScope.FilEvaluacionRVO.perVigencia}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.FilEvaluacionRVO.perVigencia}">selected</c:if>>2</option>
					</select>
		  		</td>
		  		<td id='per'>
						<span class="style2">*</span><b>Semestre</b>
					</td>
			  		<td id='per2'>
						<select name="semestre" onchange="ajaxGrupo(this)">
						<option value="-99">--</option>
							<option value="1" <c:if test="${1==sessionScope.FilEvaluacionRVO.semestre}">selected</c:if>>1</option>
							<option value="2" <c:if test="${2==sessionScope.FilEvaluacionRVO.semestre}">selected</c:if>>2</option>
							<option value="3" <c:if test="${3==sessionScope.FilEvaluacionRVO.semestre}">selected</c:if>>3</option>
							<option value="4" <c:if test="${4==sessionScope.FilEvaluacionRVO.semestre}">selected</c:if>>4</option>
						</select>
			  		</td>
		  		</tr>
		  		<TR>
		  		
		  		<td>
					<span class="style2">*</span><b>Componente</b>
				</td>
				<td >
					<select id="comp" name="componente" onChange='javaScript:cambioSelect(this,0);'>
						
						<option value="1" <c:if test="${1==sessionScope.FilEvaluacionRVO.componente}">selected</c:if>>Académico</option>
						<option value="2" <c:if test="${2==sessionScope.FilEvaluacionRVO.componente}">selected</c:if>>Técnico</option>
					</select>
		  		</td>
		  		
		  		<td id='esp' >
					<span class="style2">*</span><b>Especialidad:</b>
				</td>
		  		<td id='esp1' colspan=3 style="width:100px;">
					<select name="especialidad" style='width:300px;'>
						<option value="0" size=20>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaEspecialidadVO}" var="especialidad">
							<option value="<c:out value="${especialidad.codigo}"/>"<c:if test="${especialidad.codigo==sessionScope.FilEvaluacionRVO.especialidad}">selected</c:if>><c:out value="${especialidad.nombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
			
			
			  		
				</tr>  	
			<tr>
		  		<td>
					<span class="style2">*</span><b>Grupo</b>
				</td>
				<td>
					<select name="grupo" onchange="ajaxEstudiante(this)">
					<option value="-99">--</option>
					<c:if test="${!empty(sessionScope.ajaxGrupo)}">
						<c:forEach begin="0" items="${sessionScope.ajaxGrupo}" var="grupo" varStatus="st">
							<option value='<c:out value="${grupo.consecutivo}"/>' <c:if test="${grupo.codigo==sessionScope.FilEvaluacionRVO.grupo}">selected</c:if>><c:out value="${grupo.consecutivo}"/></option>
						</c:forEach>
					</c:if>
					</select>
		  		</td>
		  	
				<td>
					<span class="style2">*</span><b>Estudiante</b>
				</td>
				<td>
					<select name="estudiante"  style='width:300px;'>
						<option value="-99" size=40>--Seleccione uno--</option>
						<c:if test="${!empty(sessionScope.ajaxEstudiante)}">
							<c:forEach begin="0" items="${sessionScope.ajaxEstudiante}" var="estudiante">
								<option value="<c:out value="${estudiante.codigo}"/>"<c:if test="${estudiante.codigo==sessionScope.FilEvaluacionRVO.estudiante}">selected</c:if>><c:out value="${estudiante.nombre}"/></option>
							</c:forEach>
						</c:if>
					</select>
		  		</td>
			</tr>  	
			<tr>
				<td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td>
			</tr>
		 </table>
	</form>
		<form method="post" name="frmAjaxGrupo" action="<c:url value="/articulacion/retiroEstudiantes/Ajax.do"/>">
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="ajax" value=''>
			<input type="hidden" name="ajax" value=''>
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="tipo" value=''>
			<input type="hidden" name="cmd">
		</form>
		
		<form method="post" name="frmAjaxEstudiante" action="<c:url value="/articulacion/retiroEstudiantes/Ajax.do"/>">
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="ajax" value=''>
			<input type="hidden" name="ajax" value=''>
			<input type="hidden" name="ajax" value=''>
			<input type="hidden" name="ajax" value=''>
			
			<input type="hidden" name="cmd">
		</form>
		
</body>
<script>
   //  setJornadas(document.frmFiltro.sede,document.frmFiltro.jornada);
	 cambioSelect(document.frmFiltro.componente,1);
	// ajaxGrupo(document.frmFiltro.semestre);
	 //ajaxPrueba(document.frmFiltro.asignatura);
</script>
</html>