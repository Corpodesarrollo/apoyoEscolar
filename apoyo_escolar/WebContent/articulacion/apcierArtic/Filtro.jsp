<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.apcierArtic.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	
	function lanzar(tipo){
	  	document.frmLista.tipo.value=tipo;
		document.frmLista.target="";
		document.frmLista.submit();
	}
	
	
	function filtrar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
			document.frmFiltro.action='<c:url value="/articulacion/apcierArtic/Save0.jsp"/>';
			document.frmFiltro.submit();
		}
	}
	
	function hacerValidaciones_frmFiltro(forma){
	//	validarLista(forma.componente, "- Seleccione un componente", 1);
		validarLista(forma.grupo, "- Seleccione un grupo", 1);
		validarLista(forma.asignatura, "- Seleccione una asignatura", 1);
		/*if(forma.componente.selectedIndex==2){
			validarLista(forma.componente, "- Seleccione un componente", 1);
		}*/
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
//		alert(val);
		if(val!=-99){
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
		//	alert(val);
			document.frmAjaxGrupo.ajax[0].value=val;
			document.frmAjaxGrupo.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ASIGNATURA}"/>';
	 		document.frmAjaxGrupo.target="frameAjax";
			document.frmAjaxGrupo.submit();
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
			<td rowspan="2" width="469">
				<img src='<c:url value="/etc/img/tabs/art_cierre_apertura_prueba_0.gif"/>' alt="Asignaturas" height="26" border="0">
			</td>
		</tr>
	</table>
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="institucion" value='<c:out value="${sessionScope.login.instId}"/>'>	 
	 <input type="hidden" name="sede" value='<c:out value="${sessionScope.login.sedeId}"/>'>	 
	 <input type="hidden" name="jornada" value='<c:out value="${sessionScope.login.jornadaId}"/>'>	 
	 <input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>	 
	 <input type="hidden" name="FILTRO" value='<c:out value="${paramsVO.CMD_FILTRAR}"/>'>
	 <input type="hidden" name="componente" value='2'>
		<table border="0" align="center" width="100%">
			<caption>Filtro de Búsqueda</caption>
			<c:if test="${empty requestScope.listaSedeVO}">
					<tr>
				 		<td colspan="4">
				 			<input name="cmd" type="button" value="Buscar" onClick="filtrar()" class="boton">
				 		</td>
			 		</tr>
			</c:if>
			<tr>
				<td width="20%">
					<span class="style2">*</span><b>Vigencia:</b>
				</td>
				<td width="50%">
					<select name="anVigencia" style='width:50px;' onChange='javaScript:ajaxGrupo(document.frmFiltro.especialidad,0);'>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.filApCierreVO.anVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select> - 
					<select name="perVigencia" onChange='javaScript:ajaxGrupo(document.frmFiltro.especialidad,0);'>
						<option value="1" <c:if test="${1==sessionScope.filApCierreVO.perVigencia}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.filApCierreVO.perVigencia}">selected</c:if>>2</option>
					</select>
		  		</td>
		  		<td width="10%">
					<span class="style2">*</span><b>Bimestre:</b>
		  		</td>
		  		<td width="20%">
		  			<select name="bimestre">
						<option value="1" <c:if test="${1==sessionScope.filApCierreVO.bimestre}">selected</c:if> >1</option>
						<option value="2" <c:if test="${2==sessionScope.filApCierreVO.bimestre}">selected</c:if> >2</option>
					</select>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td id='esp'>
					<span class="style2">*</span><b>Especialidad:</b>
				</td>
		  		<td id='esp1'>
					<select style="width:300px;" name="especialidad" onchange="ajaxGrupo(this)">
						<option value="0">--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaEspecialidadVO}" var="especialidad">
							<option value="<c:out value="${especialidad.codigo}"/>"<c:if test="${especialidad.codigo==sessionScope.filApCierreVO.especialidad}">selected</c:if>><c:out value="${especialidad.nombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
		  		<td>
					<span class="style2">*</span><b>Grupo:</b>
				</td>
				<td>
					<select name="grupo" onchange="ajaxAsignatura(this)">
						<option value="-99">--</option>
						<c:if test="${!empty(sessionScope.ajaxAPGrupo)}">
							<c:forEach begin="0" items="${sessionScope.ajaxAPGrupo}" var="grupo">
								<option value="<c:out value="${grupo.codigo}"/>"<c:if test="${grupo.codigo==sessionScope.filApCierreVO.grupo}">selected</c:if>><c:out value="${grupo.consecutivo}"/></option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			 </tr> 		
			<tr>
		  		<td>
					<span class="style2">*</span><b>Asignatura:</b>
				</td>
				<td colspan="3">
					<select style="width:300px;" name="asignatura">
						<option value="-99">--Seleccione uno--</option>
						<c:if test="${!empty(sessionScope.ajaxAPAsignatura)}">
							<c:forEach begin="0" items="${sessionScope.ajaxAPAsignatura}" var="asignatura">
								<option value="<c:out value="${asignatura.codigo}"/>"<c:if test="${asignatura.codigo==sessionScope.filApCierreVO.asignatura}">selected</c:if>><c:out value="${asignatura.nombre}"/></option>
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
		<form method="post" name="frmAjaxGrupo" action='<c:url value="/articulacion/apcierArtic/Ajax.do"/>'>
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.sedeId}"/>'>
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.jornadaId}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="tipo" value=''>
			<input type="hidden" name="cmd">
		</form>
		<form method="post" name="frmAjaxAsignatura" action='<c:url value="/articulacion/apcierArtic/Ajax.do"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="tipo" value=''>
			<input type="hidden" name="cmd">
		</form>
</body>
</html>