<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroActaVO" class="participacion.acta.vo.FiltroActaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.acta.vo.ParamsVO" scope="page"/>
<jsp:useBean id="paramsVO2" class="participacion.common.vo.ParamParticipacion" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function lanzar(tipo){
  	document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.filNivel, "- Nivel",1);
		validarLista(forma.filInstancia, "- Instancia",1);
		validarLista(forma.filRango, "- Rango",1);
		var nivel=forma.filNivel.options[forma.filNivel.selectedIndex].value
		if(parseInt(nivel)==parseInt(forma.NIVEL_LOCAL.value)){
			validarLista(forma.filLocalidad, "- Localidad",1);
		}
		if(parseInt(nivel)==parseInt(forma.NIVEL_COLEGIO.value)){
			validarLista(forma.filLocalidad, "- Localidad",1);
			validarLista(forma.filColegio, "- Colegio",1);
		}
	}
	
	function cambioNivel(forma){
			var nivel=forma.filNivel.options[forma.filNivel.selectedIndex].value
			if(parseInt(nivel)==parseInt(forma.NIVEL_DISTRITAL.value) || parseInt(nivel)==parseInt(forma.NIVEL_CENTRAL.value)){
				forma.filLocalidad.selectedIndex=0;
				forma.filColegio.selectedIndex=0;
				forma.filLocalidad.disabled=true;
				forma.filColegio.disabled=true;
			}
			if(parseInt(nivel)==parseInt(forma.NIVEL_LOCAL.value)){
				if(parseInt(forma.filTieneLocalidad.value)==0) forma.filLocalidad.disabled=false;
				if(parseInt(forma.filTieneColegio.value)==0){
					forma.filColegio.selectedIndex=0;
					forma.filColegio.disabled=true;
				}	
			}
			if(parseInt(nivel)==parseInt(forma.NIVEL_COLEGIO.value)){
				if(parseInt(forma.filTieneLocalidad.value)==0) forma.filLocalidad.disabled=false;
				if(parseInt(forma.filTieneColegio.value)==0) forma.filColegio.disabled=false;
			}
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.filLocalidad.disabled=false;
			document.frmFiltro.filColegio.disabled=false;
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.action='<c:url value="/participacion/acta/SaveActa.jsp"/>';
			document.frmFiltro.ext.value='';
			document.frmFiltro.target='';
			document.frmFiltro.submit();
		}
	}
	
	function editar(n){
		if(document.frmFiltro.filActa){
				document.frmFiltro.filActa.value=n;
				document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
				document.frmFiltro.action='<c:url value="/participacion/acta/SaveActa.jsp"/>';
				document.frmFiltro.ext.value='';
				document.frmFiltro.target='';
				document.frmFiltro.submit();
		}
	}
	
	function descargar(n){
		if(document.frmFiltro.filActa){
				document.frmFiltro.filActa.value=n;
				document.frmFiltro.action='<c:url value="/participacion/acta/Nuevo.do"/>';
				document.frmFiltro.cmd.value=document.frmFiltro.DESCARGAR.value;
				document.frmFiltro.ext.value='1';
				document.frmFiltro.target='3';
				document.frmFiltro.submit();
		}
	}
	
	function eliminar(n){
		if(confirm('¿Desea eliminar el acta indicada?')){
			if(document.frmFiltro.filActa){
					document.frmFiltro.filActa.value=n;
					document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
					document.frmFiltro.action='<c:url value="/participacion/acta/SaveActa.jsp"/>';
					document.frmFiltro.ext.value='';
					document.frmFiltro.target='';
					document.frmFiltro.submit();
			}
		}	
	}

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxInstancia0(){
		cambioNivel(document.frmFiltro);
		borrar_combo(document.frmFiltro.filInstancia); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filNivel.value;
		if(parseInt(document.frmAjax0.ajax[0].value)>0){
			document.frmAjax0.cmd.value=document.frmFiltro.AJAX_INSTANCIA0.value;
	 		document.frmAjax0.target="frameAjax0";
			document.frmAjax0.submit();
		}	
	}
	
	function ajaxRango0(){
		borrar_combo(document.frmFiltro.filRango); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filInstancia.value;
		if(parseInt(document.frmAjax0.ajax[0].value)>0){
			document.frmAjax0.cmd.value=document.frmFiltro.AJAX_RANGO0.value;
	 		document.frmAjax0.target="frameAjax0";
			document.frmAjax0.submit();
		}	
	}

	function ajaxColegio0(){
		borrar_combo(document.frmFiltro.filColegio); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filLocalidad.value;
		if(parseInt(document.frmAjax0.ajax[0].value)>0){
			document.frmAjax0.cmd.value=document.frmFiltro.AJAX_COLEGIO0.value;
	 		document.frmAjax0.target="frameAjax0";
			document.frmAjax0.submit();
		}	
	}
	
	
	
	function generaReporte(n){
		if(document.frmFiltro.filActa){		
			document.frmFiltro.filActa.value=n;
			document.frmFiltro.cmd.value=document.frmFiltro.GENERAR.value;
			document.frmFiltro.action='<c:url value="/participacion/acta/Nuevo.do"/>';
			document.frmFiltro.ext.value=1;
			document.frmFiltro.target='';
			document.frmFiltro.submit();
	    }
	}
			
			
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmAjax0" action='<c:url value="/participacion/acta/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ACTA}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<form action='<c:url value="/participacion/acta/SaveActa.jsp"/>' method="post" name="frmFiltro">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ACTA}"/>'>
		<input type="hidden" name="cmd" value=''><input type="hidden" name="ext" value=''>
		<input type="hidden" name="filActa" value='<c:out value="${sessionScope.filtroActaVO.filActa}"/>'>
		<input type="hidden" name="filTieneLocalidad" value='<c:out value="${sessionScope.filtroActaVO.filTieneLocalidad}"/>'>
		<input type="hidden" name="filTieneColegio" value='<c:out value="${sessionScope.filtroActaVO.filTieneColegio}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="DESCARGAR" value='<c:out value="${paramsVO.CMD_DESCARGAR}"/>'>
		<input type="hidden" name="GENERAR" value='<c:out value="${paramsVO.CMD_GENERAR}"/>'>
		<input type="hidden" name="AJAX_INSTANCIA0" value='<c:out value="${paramsVO.CMD_AJAX_INSTANCIA0}"/>'>
		<input type="hidden" name="AJAX_RANGO0" value='<c:out value="${paramsVO.CMD_AJAX_RANGO0}"/>'>
		<input type="hidden" name="AJAX_COLEGIO0" value='<c:out value="${paramsVO.CMD_AJAX_COLEGIO0}"/>'>
		<input type="hidden" name="NIVEL_DISTRITAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_DISTRITAL}"/>'>
		<input type="hidden" name="NIVEL_CENTRAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_CENTRAL}"/>'>
		<input type="hidden" name="NIVEL_LOCAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_LOCAL}"/>'>
		<input type="hidden" name="NIVEL_COLEGIO" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_COLEGIO}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/acta_1.gif"/>' alt="Acta" height="26" border="0">
				</td>
			</tr>
		</table>
		<table border="0" align="center" width="95%">
		 	<caption>Filtro de búsqueda</caption>
			<tr>
				<td><input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton"></td>
		 	</tr>	
		 	<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>
		</table>
		<table border="0" align="center" width="95%">
			<tr>
				<td><span class="style2">*</span> Nivel:</td>
				<td>
					<select name="filNivel" style="width:100px" onchange="javaScript:ajaxInstancia0()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaNivelVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.filtroActaVO.filNivel}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Instancia:</td>
				<td>
					<select name="filInstancia" style="width:200px" onchange="javaScript:ajaxRango0()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaInstanciaVO}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.filtroActaVO.filInstancia}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Rango:</td>
				<td>
					<select name="filRango" style="width:200px">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaRangoVO}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.filtroActaVO.filRango}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td>Localidad:</td>
				<td>
					<select name="filLocalidad" style="width:100px" onchange="javaScript:ajaxColegio0()" <c:if test="${sessionScope.filtroActaVO.filTieneLocalidad==1}">disabled</c:if>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaLocalidadVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.filtroActaVO.filLocalidad}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Colegio:</td>
				<td colspan="3">
					<select name="filColegio" style="width:300px" <c:if test="${sessionScope.filtroActaVO.filTieneLocalidad==1}">disabled</c:if>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaColegioVO}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.filtroActaVO.filColegio}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de actas</caption>
		 	<c:if test="${empty requestScope.listaActaVO}"><tr><th class="Fila1" colspan='6'>No hay actas</th></tr></c:if>
			<c:if test="${!empty requestScope.listaActaVO}">
				<tr>
					
					<th width='10%' class="EncabezadoColumna" colspan="2">&nbsp;</th>
					<td class="EncabezadoColumna" align="center" width="40%">Asunto</td>
					<td class="EncabezadoColumna" align="center" width="20%">Fecha de reunión</td>
					<td class="EncabezadoColumna" align="center" width="20%">Hora</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaActaVO}" var="lista" varStatus="st">
					<tr>
					   
						<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></th>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.actCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.actCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
							<a href='javaScript:generaReporte(<c:out value="${lista.actCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/pdf.gif"/>' width='15' height='15' alt="Imprimir Acta"></a>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.actNombreAsunto}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.actFecha}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.actHora1}"/> - <c:out value="${lista.actHora2}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
<script>
cambioNivel(document.frmFiltro);
</script>
</html>