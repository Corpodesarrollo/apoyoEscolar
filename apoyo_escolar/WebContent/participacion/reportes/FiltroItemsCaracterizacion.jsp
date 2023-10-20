<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroItemsVO" class="participacion.reportes.vo.FiltroItemsVO" scope="session"/>

<jsp:useBean id="paramsVO" class="participacion.reportes.vo.ParamsVO" scope="page"/>
<jsp:useBean id="paramsVO2" class="participacion.common.vo.ParamParticipacion" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function generaReporte(){		
		//document.frmFiltro.target='_blank';
		reporteParticipantes();
	}

	function reporteParticipantes(){
		document.frmFiltro.cmd.value=document.frmFiltro.GENERAR.value;
		document.frmFiltro.action='<c:url value="/participacion/reportes/Nuevo.do"/>';
		document.frmFiltro.ext.value=1;
		document.frmFiltro.submit();
	}


	function lanzar(tipo){
  		document.frmFiltro.tipo.value=tipo;
  		document.frmFiltro.cmd.value=6;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		//validarLista(forma.filNivel, "- Nivel",1);
		//validarLista(forma.filInstancia, "- Instancia",1);
		//validarLista(forma.filRango, "- Rango",1);
		//validarLista(forma.filRol, "- Rol",1);
		
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
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.action='<c:url value="/participacion/reportes/Save.jsp"/>';
			document.frmFiltro.ext.value='';
			document.frmFiltro.target='';
			document.frmFiltro.submit();
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
		borrar_combo(document.frmFiltro.filRol);
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
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmAjax0" action='<c:url value="/participacion/reportes/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_LIDERES}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<form action='<c:url value="/participacion/reportes/Save.jsp"/>' method="post" name="frmFiltro">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_LIDERES}"/>'>
		<input type="hidden" name="cmd" value=''><input type="hidden" name="ext" value=''>
		<input type="hidden" name="filParticipante" value='<c:out value="${sessionScope.filtroItemsVO.filParticipante}"/>'>
		<input type="hidden" name="filTieneLocalidad" value='<c:out value="${sessionScope.filtroItemsVO.filTieneLocalidad}"/>'>
		<input type="hidden" name="filTieneColegio" value='<c:out value="${sessionScope.filtroItemsVO.filTieneColegio}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
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
					<img src='<c:url value="/etc/img/tabs/lideres_1.gif"/>' alt="Acta" height="26" border="0">
					<a href="javaScript:lanzar(2)"><img src='<c:url value="/etc/img/tabs/acta_0.gif"/>' alt="Actas" height="26" border="0"></a>
				</td>
			</tr>
		</table>
		<table border="0" align="center" width="95%">
		 	<caption>Filtro de búsqueda</caption>
			<tr>
				<td><c:if test="${sessionScope.NivelPermiso==2}"><input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton"></c:if></td>
		 	</tr>	
		 	<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>
		</table>
		<table border="0" align="center" width="95%">
			<tr>
				<td width="80"> Nivel:</td>
				<td>
					<select name="filNivel" style="width:100px" onchange="javaScript:ajaxInstancia0()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaNivelVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.filtroItemsVO.filNivel}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				</tr>
			<tr>
				<td>Instancia:</td>
				<td>
					<select name="filInstancia" style="width:200px" onchange="javaScript:ajaxRango0()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaInstanciaVO}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.filtroItemsVO.filInstancia}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Rango:</td>
				<td>
					<select name="filRango" style="width:200px">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaRangoVO}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.filtroItemsVO.filRango}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td>Localidad:</td>
				<td>
					<select name="filLocalidad" style="width:120px" onchange="javaScript:ajaxColegio0()" <c:if test="${sessionScope.filtroItemsVO.filTieneLocalidad==1}">disabled</c:if>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaLocalidadVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.filtroItemsVO.filLocalidad}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Colegio:</td>
				<td colspan="3">
					<select name="filColegio" style="width:300px" <c:if test="${sessionScope.filtroItemsVO.filTieneColegio==1}">disabled</c:if>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaColegioVO}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.filtroItemsVO.filColegio}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr style="display:none">	
				<td >Rol:</td>
				<td>
					<select name="filRol" style="width:200px">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaRolVO}" var="rol">
							<option value="<c:out value="${rol.codigo}"/>" <c:if test="${rol.codigo==sessionScope.filtroItemsVO.filRol}">selected</c:if>><c:out value="${rol.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>

<c:if test="${empty requestScope.listaItemsCaracterizacionVO}">
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de items de caracterización</caption>
		 	<tr><th class="Fila1" colspan='6'>No hay participantes</th></tr>
		 	</table>
		</c:if>

	<c:if test="${!empty requestScope.listaItemsCaracterizacionVO}">
	<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
	 	<caption>Listado de items de caracterización</caption>		 	
	  <tr><th class="Fila1" colspan='6' align="left"><a href='javaScript:generaReporte();'><img border='0' src="../../etc/img/xls.gif" width='15' height='15'></a> Reporte Listado de Items de Caracterización</th></tr>			
	</table>
	<br>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		
				<tr>
					<th width='20' class="EncabezadoColumna" colspan="1">&nbsp;</th>					
					<td class="EncabezadoColumna" align="center">Nivel</td>
					<td class="EncabezadoColumna" align="center">Instancia</td>
					<td class="EncabezadoColumna" align="center">Dane Colegio</td>
					<td class="EncabezadoColumna" align="center">Colegio</td>
					<td class="EncabezadoColumna" align="center">Sede</td>
					<td class="EncabezadoColumna" align="center">Jornada</td>
					<td class="EncabezadoColumna" align="center">Tipo Doc</td>
					<td class="EncabezadoColumna" align="center">Documento</td>
					<td class="EncabezadoColumna" align="center">Primer Apellido</td>
					<td class="EncabezadoColumna" align="center">Segundo Apellido</td>
					<td class="EncabezadoColumna" align="center">Primer Nombre</td>
					<td class="EncabezadoColumna" align="center">Segundo Nombre</td>
					<td class="EncabezadoColumna" align="center">Rol</td>
					<td class="EncabezadoColumna" align="center">Teléfono</td>
					<td class="EncabezadoColumna" align="center">Celular</td>
					<td class="EncabezadoColumna" align="center">Correo</td>
					<td class="EncabezadoColumna" align="center">Edad</td>
					<td class="EncabezadoColumna" align="center">Genero</td>
					<td class="EncabezadoColumna" align="center">Etnia</td>
					<td class="EncabezadoColumna" align="center">Localidad Estudio</td>
					<td class="EncabezadoColumna" align="center">Localidad Residencia</td>
					<td class="EncabezadoColumna" align="center">Entidad</td>
					<td class="EncabezadoColumna" align="center">Sector Económico</td>
					<td class="EncabezadoColumna" align="center">Discapacidad</td>
					<td class="EncabezadoColumna" align="center">LGTB</td>
					<td class="EncabezadoColumna" align="center">Ocupación</td>
					<td class="EncabezadoColumna" align="center">Desplazado</td>
					<td class="EncabezadoColumna" align="center">Amenazado</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaItemsCaracterizacionVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></th>
						
						
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemNivel}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemInstancia}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemDaneColegio}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemColegio}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemSede}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemJornada}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemTipoDocumento}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemNumeroDocumento}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemApellido1}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemApellido2}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemNombre1}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemNombre2}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemNombreRol}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemTelefono}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemCelular}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemCorreoElectronico}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemEdad}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemGenero}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemEtnia}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemLocalidadEstudio}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemLocalidadResidencia}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.itemEntidad}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemSectorEconomico}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemDiscapacidad}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemLgtb}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemOcupacion}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemDesplazado}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.nombreItemAmenazado}"/></td>
						
						
						
					</tr>
				</c:forEach>
			
		</table>
		</c:if>
	</form>
</body>
<script>
cambioNivel(document.frmFiltro);
</script>
</html>
