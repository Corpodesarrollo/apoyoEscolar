<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.artAusencias.vo.ParamsVO" scope="page"/>
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
			document.frmFiltro.action='<c:url value="/articulacion/artAusencias/Save0.jsp"/>';
			document.frmFiltro.submit();
		}
	}
	
	function cambioSelect(objeto){
		var variable=objeto.options[objeto.selectedIndex].value
		if(variable=='0'){
			   //document.getElementById ('esp').style.display='none';
		       document.frmFiltro.especialidad.disabled=true;
		       document.frmFiltro.especialidad.selectedIndex=0;
		}
		else if(variable=='2'){
		       document.frmFiltro.especialidad.disabled=false;
		       ajaxGrupo(document.frmFiltro.especialidad);
		}else if(variable=='1'){
			   document.frmFiltro.especialidad.disabled=true;
			   document.frmFiltro.especialidad.selectedIndex=0;
		       ajaxGrupo(document.frmFiltro.especialidad);
		}
	}

	function hacerValidaciones_frmFiltro(forma){
	//	validarLista(forma.componente, "- Seleccione un componente", 1);
		validarLista(forma.grupo, "- Seleccione un grupo", 1);
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
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body>
 <form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<img src='<c:url value="/etc/img/tabs/ausencas_0.gif"/>' alt="Asignaturas" height="26" border="0">
			</td>
		</tr>
	</table>
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="institucion" value='<c:out value="${sessionScope.login.instId}"/>'>	 
	 <input type="hidden" name="sede" value='<c:out value="${sessionScope.login.sedeId}"/>'>	 
	 <input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>	 
	 <input type="hidden" name="FILTRO" value='<c:out value="${paramsVO.CMD_FILTRAR}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
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
							<option value="<c:out value="${jornada.codigo}"/>"<c:if test="${jornada.codigo==sessionScope.FilAusenciasVO.jornada}">selected</c:if>><c:out value="${jornada.nombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
				<td>
					<span class="style2">*</span><b>Vigencia:</b>
				</td>
				<td>
					<select name="anVigencia" style='width:50px;' onchange="ajaxGrupo(document.frmFiltro.especialidad)">
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.FilAusenciasVO.anVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>
					-
					<select name="perVigencia" onchange="ajaxGrupo(document.frmFiltro.especialidad)">
						<option value="1" <c:if test="${1==sessionScope.FilAusenciasVO.perVigencia}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.FilAusenciasVO.perVigencia}">selected</c:if>>2</option>
					</select>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>
					<span class="style2">*</span><b>Componente:</b>
				</td>
				<td>
					<select id="comp" name="componente" onChange='javaScript:cambioSelect(this);'>
						<option value="1" <c:if test="${1==sessionScope.FilAusenciasVO.componente}">selected</c:if>>Académico</option>
						<option value="2" <c:if test="${2==sessionScope.FilAusenciasVO.componente}">selected</c:if>>Técnico</option>
					</select>
		  		</td>
		  		<td id='esp'>
					<span class="style2">*</span><b>Especialidad:</b>
				</td>
		  		<td id='esp1'>
					<select style="width:200px" name="especialidad" onchange="ajaxGrupo(this)" disabled="disabled">
						<option value="0">--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaEspecialidadVO}" var="especialidad">
							<option value="<c:out value="${especialidad.codigo}"/>"<c:if test="${especialidad.codigo==sessionScope.FilAusenciasVO.especialidad}">selected</c:if>><c:out value="${especialidad.nombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
			</tr> 		
			<tr>
				<td>
					<span class="style2">*</span><b>Grupo:</b>
				</td>
				<td>
					<select style='width:55px;' name="grupo">
						<option value="-99">----</option>
						<c:if test="${!empty(sessionScope.ajaxGrupo)}">
							<c:forEach begin="0" items="${sessionScope.ajaxGrupo}" var="grupo">
								<option value="<c:out value="${grupo.codigo}"/>"<c:if test="${grupo.codigo==sessionScope.FilAusenciasVO.grupo}">selected</c:if>><c:out value="${grupo.consecutivo}"/></option>
							</c:forEach>
						</c:if>
					</select>
		  		</td>
		  		<td>
					<span class="style2">*</span><b>Mes:</b>
				</td>
				<td colspan="2">
					<select name="mes">
					<c:if test='${sessionScope.FilAusenciasVO.mes==""}'>
						<option value="1" <c:if test="${mes==01}">selected</c:if>>Enero</option>
						<option value="2" <c:if test="${mes==02}">selected</c:if>>Febrero</option>
						<option value="3" <c:if test="${mes==03}">selected</c:if>>Marzo</option>
						<option value="4" <c:if test="${mes==04}">selected</c:if>>Abril</option>
						<option value="5" <c:if test="${mes==05}">selected</c:if>>Mayo</option>
						<option value="6" <c:if test="${mes==06}">selected</c:if>>Junio</option>
						<option value="7" <c:if test="${mes==07}">selected</c:if>>Julio</option>
						<option value="8" <c:if test="${mes==08}">selected</c:if>>Agosto</option>
						<option value="9" <c:if test="${mes==09}">selected</c:if>>Septiembre</option>
						<option value="10" <c:if test="${mes==10}">selected</c:if>>Octubre</option>
						<option value="11" <c:if test="${mes==11}">selected</c:if>>Noviembre</option>
						<option value="12" <c:if test="${mes==12}">selected</c:if>>Diciembre</option>
					</c:if>
					<c:if test='${sessionScope.FilAusenciasVO.mes!=""}'>
						<option value="1" <c:if test="${sessionScope.FilAusenciasVO.mes==1}">selected</c:if>>Enero</option>
						<option value="2" <c:if test="${sessionScope.FilAusenciasVO.mes==2}">selected</c:if>>Febrero</option>
						<option value="3" <c:if test="${sessionScope.FilAusenciasVO.mes==3}">selected</c:if>>Marzo</option>
						<option value="4" <c:if test="${sessionScope.FilAusenciasVO.mes==4}">selected</c:if>>Abril</option>
						<option value="5" <c:if test="${sessionScope.FilAusenciasVO.mes==5}">selected</c:if>>Mayo</option>
						<option value="6" <c:if test="${sessionScope.FilAusenciasVO.mes==6}">selected</c:if>>Junio</option>
						<option value="7" <c:if test="${sessionScope.FilAusenciasVO.mes==7}">selected</c:if>>Julio</option>
						<option value="8" <c:if test="${sessionScope.FilAusenciasVO.mes==8}">selected</c:if>>Agosto</option>
						<option value="9" <c:if test="${sessionScope.FilAusenciasVO.mes==9}">selected</c:if>>Septiembre</option>
						<option value="10" <c:if test="${sessionScope.FilAusenciasVO.mes==10}">selected</c:if>>Octubre</option>
						<option value="11" <c:if test="${sessionScope.FilAusenciasVO.mes==11}">selected</c:if>>Noviembre</option>
						<option value="12" <c:if test="${sessionScope.FilAusenciasVO.mes==12}">selected</c:if>>Diciembre</option>
					</c:if>
					</select>
				
		  		</td>
		  	</tr>
		  	<tr>
				<td>
					<span class="style2">*</span><b>Ordenado por:</b>
				</td>
				<td colspan="2">
					<select name="ordenado">
						<option value="1" <c:if test="${sessionScope.FilAusenciasVO.ordenado==1}">selected</c:if>>Apellidos</option>
						<option value="2" <c:if test="${sessionScope.FilAusenciasVO.ordenado==2}">selected</c:if>>Nombres</option>
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
</body>
<script>
//	cambioSelect(document.frmFiltro.componente);
</script>
</html>