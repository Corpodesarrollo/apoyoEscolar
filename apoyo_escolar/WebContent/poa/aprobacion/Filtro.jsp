<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroAprobacionVO" class="poa.aprobacion.vo.FiltroPlaneacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.aprobacion.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
	<STYLE type='text/css'>
		.estadoPOA{
			COLOR: red;
			FONT-WEIGHT: bold;
			FONT-FAMILY: Arial, Helvetica, sans-serif;
			FONT-SIZE: 10pt;
		}
	</STYLE>
<script language="javaScript">
<!--
	function lanzar(tipo){
  	document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.filLocalidad, "- Localidad");
		validarLista(forma.filInstitucion, "- Colegio");
		validarLista(forma.filVigencia, "- Vigencia");
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function aprobar(){
		if(validarForma(document.frmFiltro)){
			if(confirm("�Confirma la aprobaci�n del POA? \n Nota: Una vez aprobado no se podr� devolver")){
				document.frmFiltro.cmd.value=document.frmFiltro.APROBAR.value;
				document.frmFiltro.submit();
			}	
		}
	}
	
	function validarObservacion(forma){
		if(forma.filMotivo){
			if(trim(forma.filMotivo.value).length==0 || trim(forma.filMotivo.value).length>500){
				alert('- Observaci�n por devoluci�n (hasta 500 caracteres)');
				return false;
			}
		}
		return true;
	}
	function rechazar(){
		if(validarForma(document.frmFiltro)){
			if(confirm("�Confirma la devoluci�n del POA? \n Nota: Una vez devuelto no se podr� ver o editar actividades")){
				//var motivo='';
				//while(trim(motivo).length==0){
				//	motivo=prompt('Indique el motivo del rechazo:','');
				//	if(motivo==null){return;}
				//}
				//if(trim(motivo).length>0){
				if(validarObservacion(document.frmFiltro)){
					//document.frmFiltro.filMotivo.value=motivo;
					document.frmFiltro.cmd.value=document.frmFiltro.RECHAZAR.value;
					document.frmFiltro.submit();
				}	
			}
		}
	}
	
	function editar(n,m,o){
		if(document.frmFiltro.id){
				document.frmFiltro.id.value=n;
				document.frmFiltro.id2.value=m;
				document.frmFiltro.id3.value=o;
				document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
				document.frmFiltro.submit();
		}
	}

	function inhabilitar(){
		if(document.getElementById("cmdPOA")){
			document.getElementById("cmdPOA").style.display='none';
		}
		if(document.getElementById("cmdPOA2")){
			document.getElementById("cmdPOA2").style.display='none';
		}
	}

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxColegio(){
		borrar_combo(document.frmFiltro.filInstitucion); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filLocalidad.value;
		document.frmAjax0.cmd.value='<c:out value="${paramsVO.CMD_AJAX_COLEGIO}"/>';
 		document.frmAjax0.target="frameAjax0";
		document.frmAjax0.submit();
	}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmAjax0" action='<c:url value="/poa/aprobacion/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<form action='<c:url value="/poa/aprobacion/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value=""><input type="hidden" name="id2" value=""><input type="hidden" name="id3" value="">
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="APROBAR" value='<c:out value="${paramsVO.CMD_APROBAR_SED}"/>'>
		<input type="hidden" name="RECHAZAR" value='<c:out value="${paramsVO.CMD_RECHAZAR_SED}"/>'>
		<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/filtroPOA_1.gif"/>' alt="Filtro" height="26" border="0">
				</td>
			</tr>
		</table>
		<table border="0" align="center" width="95%">
		 	<caption>Filtro de b�squeda</caption>
			<tr>
				<td>
					<c:if test="${sessionScope.NivelPermiso==2}">
   						<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
   						<c:if test="${sessionScope.filtroAprobacionVO.filHabilitado==1}">
   						<input name="cmdPOA" id='cmdPOA' type="button" value="Aprobar" onClick="aprobar()" class="boton">
   						<input name="cmdPOA2" id='cmdPOA2' type="button" value="Devolver" onClick="rechazar()" class="boton">
   						</c:if>
   				</c:if>
		  		</td>
		  		<th><span class="estadoPOA">Estado del POA: <c:out value="${sessionScope.filtroAprobacionVO.filEstado}"/></span></th>
		 	</tr>	
		</table>
		<table border="0" align="center" width="95%">
			<tr>
				<td><span class="style2">*</span> Localidad:</td>
				<td colspan="3">
					<select name="filLocalidad" style="width:150px" onchange="javaScript:ajaxColegio();inhabilitar();">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaLocalidad}" var="log">
							<option value="<c:out value="${log.codigo}"/>" <c:if test="${log.codigo==sessionScope.filtroAprobacionVO.filLocalidad}">selected</c:if>><c:out value="${log.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Colegio:</td>
				<td colspan="3">
					<select name="filInstitucion" style="width:300px" onchange="javaScript:inhabilitar()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaColegio}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.filtroAprobacionVO.filInstitucion}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Vigencia:</td>
				<td>
					<select name="filVigencia" style="width:100px" onchange="javaScript:inhabilitar()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
							<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroAprobacionVO.filVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> �rea de gesti�n:</td>
				<td>
					<select name="filAreaGestion" style="width:150px">
						<option value="-99" selected>-Todos-</option>
						<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
							<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.filtroAprobacionVO.filAreaGestion}">selected</c:if>><c:out value="${area.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>	
			<c:if test="${sessionScope.filtroAprobacionVO.filHabilitado==1}">
				<tr>
				<td>Observaci�n por devoluci�n:</td>
				<td colspan="3"><textarea name="filMotivo" cols="80" rows="2" onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);" ></textarea></td>
				</tr>
			</c:if>
		</table>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/actividades_1.gif"/>' alt="Actividades" height="26" border="0">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_ACTIVIDAD_SIN}"/>)"><img src='<c:url value="/etc/img/tabs/problemas_0.gif"/>' alt="Actividades sin recursos" height="26" border="0"></a>
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de actividades</caption>
		 	<c:if test="${empty requestScope.listaActividades}">
				<tr>
					<th class="Fila1" colspan='6'>No hay actividades</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaActividades}">
				<tr>
					<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Objetivo estrat�gico</td>
					<td class="EncabezadoColumna" align="center">Actividad / tarea</td>
					<td class="EncabezadoColumna" align="center">�rea de gesti�n</td>
					<td class="EncabezadoColumna" align="center">L�nea de acci�n</td>
					<td class="EncabezadoColumna" align="center">Ponderador</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaActividades}" var="lista" varStatus="st">
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaConsecutivo}"/></td>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.plaVigencia}"/>,<c:out value="${lista.plaInstitucion}"/>,<c:out value="${lista.plaCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaObjetivo}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaActividad}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaAreaGestionNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaLineaAccionNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaPonderador}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>