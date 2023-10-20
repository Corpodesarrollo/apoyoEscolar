<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.evaluacionArt.vo.ParamsVO" scope="page"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<script languaje="javaScript">
	function lanzarServicio(n){
		if(n==0){//linea
			document.frmFiltro.action='<c:url value="/articulacion/evaluacionArt/Lista.do"/>';
  	}
		if(n==1){//plantilla
			document.frmFiltro.action='<c:url value="/articulacion/plantillaEvaluacion/Lista.do"/>';  	
  	}
		if(n==2){//importar
			document.frmFiltro.action='<c:url value="/articulacion/importarEvaluacion/Lista.do"/>';  	
  	}
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function filtrar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
			document.frmFiltro.action='<c:url value="/articulacion/evaluacionArt/Save0.jsp"/>';
			document.frmFiltro.submit();
		}
	}
	
	function cambioSelect(objeto,x){
		var variable=objeto.options[objeto.selectedIndex].value
		if(variable=='0'){
			   //document.getElementById ('esp').style.display='none';
		       document.frmFiltro.especialidad.disabled=true;
		       document.frmFiltro.especialidad.selectedIndex=0;
		}
		else if(variable=='2'){
		       document.frmFiltro.especialidad.disabled=false;
		}else if(variable=='1'){
			   document.frmFiltro.especialidad.disabled=true;
			   document.frmFiltro.especialidad.selectedIndex=0;
		}
		if(x==0)
			ajaxGrupo(document.frmFiltro.especialidad);
	}

	function hacerValidaciones_frmFiltro(forma){
	//	validarLista(forma.componente, "- Seleccione un componente", 1);
		validarLista(forma.grupo, "- Seleccione un grupo", 1);
		validarLista(forma.asignatura, "- Seleccione una asignatura", 1);
		//validarLista(forma.prueba, "- Seleccione una prueba", 1);
		validarLista(forma.bimestre, "- Seleccione el bimestre", 1);
		
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
			document.frmAjaxGrupo.ajax[2].value=document.frmFiltro.sede.value;
			document.frmAjaxGrupo.ajax[3].value=document.frmFiltro.jornada.value;
			document.frmAjaxGrupo.ajax[4].value=document.frmFiltro.anVigencia.value;
			document.frmAjaxGrupo.ajax[5].value=document.frmFiltro.perVigencia.value;
			document.frmAjaxGrupo.ajax[6].value=document.frmFiltro.componente.value;
			document.frmAjaxGrupo.ajax[7].value=val;
			document.frmAjaxGrupo.cmd.value='<c:out value="${paramsVO.CMD_AJAX_GRUPO}"/>';
	 		document.frmAjaxGrupo.target="frameAjax";
			document.frmAjaxGrupo.submit();
		}
	}
	
	function ajaxAsignatura(obj){
		borrar_combo(document.frmFiltro.asignatura); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){//
			document.frmAjaxPrueba.ajax[0].value=val;
			document.frmAjaxPrueba.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ASIGNATURA}"/>';
	 		document.frmAjaxPrueba.target="frameAjax";
			document.frmAjaxPrueba.submit();
		}
	}
	
	function ajaxPrueba(obj){
		//borrar_combo(document.frmFiltro.prueba); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){
			document.frmAjaxPrueba.ajax[0].value=document.frmFiltro.asignatura.value;;
			document.frmAjaxPrueba.ajax[3].value=document.frmFiltro.jornada.value;
			document.frmAjaxPrueba.ajax[4].value=document.frmFiltro.grupo.value;
			document.frmAjaxPrueba.ajax[5].value=val
			document.frmAjaxPrueba.cmd.value='<c:out value="${paramsVO.CMD_AJAX_PRUEBA}"/>';
	 		document.frmAjaxPrueba.target="frameAjax";
			document.frmAjaxPrueba.submit();
		}
	}
	
</script>
<c:import url="/mensaje.jsp"/>
<body>
 <form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<img src='<c:url value="/etc/img/tabs/art_enlinea_1.gif"/>' alt="Evaluación en Linea" height="26" border="0">
				<a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/art_generar_plantillas_0.gif"/>' alt="" height="26" border="0"></a>
				<a href="javaScript:lanzarServicio(2)"><img src='<c:url value="/etc/img/tabs/art_importar_plantillas_0.gif"/>' alt="" height="26" border="0"></a>
			</td>
		</tr>
	</table>
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="institucion" value='<c:out value="${sessionScope.login.instId}"/>'>	 
	 <input type="hidden" name="sede" value='<c:out value="${sessionScope.login.sedeId}"/>'>	 
	 <input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>	 
	 <input type="hidden" name="FILTRO" value='<c:out value="${paramsVO.CMD_FILTRAR}"/>'>
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
					<span class="style2">*</span><b>Jornada:</b>
				</td>
		  		<td>
					<select name="jornada" onchange="ajaxGrupo(document.frmFiltro.especialidad)">
						<c:forEach begin="0" items="${requestScope.listaJornadaVO}" var="jornada">
							<option value="<c:out value="${jornada.codigo}"/>"<c:if test="${jornada.codigo==sessionScope.FilEvaluacionVO.jornada}">selected</c:if>><c:out value="${jornada.nombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
				<td>
					<span class="style2">*</span><b>Vigencia:</b>
				</td>
				<td colspan="2">
					<select name="anVigencia" style='width:50px;' onchange="ajaxGrupo(document.frmFiltro.especialidad)">
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.FilEvaluacionVO.anVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>
					-
					<select name="perVigencia" onchange="ajaxGrupo(document.frmFiltro.especialidad)">
						<option value="1" <c:if test="${1==sessionScope.FilEvaluacionVO.perVigencia}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.FilEvaluacionVO.perVigencia}">selected</c:if>>2</option>
					</select>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td id='esp'>
					<span class="style2">*</span><b>Especialidad:</b>
				</td>
		  		<td id='esp1' colspan="3">
					<select style="width:200px" name="especialidad" onchange="ajaxGrupo(this)">
						<option value="0">--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaEspecialidadVO}" var="especialidad">
							<option value="<c:out value="${especialidad.codigo}"/>"<c:if test="${especialidad.codigo==sessionScope.FilEvaluacionVO.especialidad}">selected</c:if>><c:out value="${especialidad.nombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
			</tr> 		
			<tr>
				<td>
					<span class="style2">*</span><b>Grupo:</b>
				</td>
				<td>
					<select style='width:55px;' name="grupo" onchange="ajaxAsignatura(this)">
						<option value="-99">--</option>
						<c:if test="${!empty(sessionScope.ajaxGrupo)}">
							<c:forEach begin="0" items="${sessionScope.ajaxGrupo}" var="grupo">
								<option value="<c:out value="${grupo.codigo}"/>"<c:if test="${grupo.codigo==sessionScope.FilEvaluacionVO.grupo}">selected</c:if>><c:out value="${grupo.consecutivo}"/></option>
							</c:forEach>
						</c:if>
					</select>
		  		</td>
		  		<td>
					<span class="style2">*</span><b>Asignatura:</b>
				</td>
				<td colspan="3">
					<select style='width:200px;' name="asignatura">
						<option value="-99">--Seleccione uno--</option>
						<c:if test="${!empty(sessionScope.ajaxAsignatura)}">
							<c:forEach begin="0" items="${sessionScope.ajaxAsignatura}" var="asignatura">
								<option value="<c:out value="${asignatura.codigo}"/>"<c:if test="${asignatura.codigo==sessionScope.FilEvaluacionVO.asignatura}">selected</c:if>><c:out value="${asignatura.nombre}"/></option>
							</c:forEach>
						</c:if>
					</select>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>
					<span class="style2">*</span><b>Bimestre:</b>
				</td>
					<td>
						<select name="bimestre" onchange="ajaxPrueba(this)">
							<option value="-99">--</option>
							<option value="1" <c:if test="${1==sessionScope.FilEvaluacionVO.bimestre}">selected</c:if> >1</option>
							<option value="2" <c:if test="${2==sessionScope.FilEvaluacionVO.bimestre}">selected</c:if> >2</option>
						</select>
		  		</td>
		  		<!-- <td>
					<span class="style2">*</span><b>Prueba Principal:</b>
				</td>
				<td>
					<select style='width:150px;' name="prueba">
						<option value="-99">--Seleccione uno--</option>
						<c:if test="${!empty(sessionScope.ajaxPrueba)}">
							<c:forEach begin="0" items="${sessionScope.ajaxPrueba}" var="prueba">
								<option value="<c:out value="${prueba.codigo}"/>"<c:if test="${prueba.codigo==sessionScope.FilEvaluacionVO.prueba}">selected</c:if>><c:out value="${prueba.nombre}"/></option>
							</c:forEach>
						</c:if>
					</select>
		  		</td> -->
			</tr>  	
			<tr>
				<td><b>Docente:<b></td>
				<td colspan="2"><input type="text" size="40" readonly="readonly" name="nombreDocente" value='<c:out value="${sessionScope.docente}" />' ></td>
				<td>
					<span class="style2">*</span><b>Ordenado por:</b>&nbsp;
					<select name="ordenado">
						<option value="1">Apellidos</option>
						<option value="2">Nombres</option>
					</select>
		  		</td>		  		
			</tr>
			<tr>
				<td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td>
			</tr>
		 </table>
	</form>
		<form method="post" name="frmAjaxGrupo" action="<c:url value="/articulacion/evaluacionArt/Ajax.do"/>">
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
			<input type="hidden" name="ajax" value=''>
			<input type="hidden" name="ajax" value=''>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="tipo" value=''>
			<input type="hidden" name="cmd">
		</form>
		<form method="post" name="frmAjaxAsignatura" action="<c:url value="/articulacion/evaluacionArt/Ajax.do"/>">
			<input type="hidden" name="ajax">
			<input type="hidden" name="tipo" value=''>
			<input type="hidden" name="cmd">
		</form>
		<form method="post" name="frmAjaxPrueba" action="<c:url value="/articulacion/evaluacionArt/Ajax.do"/>">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.sedeId}"/>'>
			<input type="hidden" name="ajax" value=''>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="tipo" value=''>
			<input type="hidden" name="cmd">
		</form>
</body>

</html>