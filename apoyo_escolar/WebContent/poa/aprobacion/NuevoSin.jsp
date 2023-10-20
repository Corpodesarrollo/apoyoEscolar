<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="aprobacionSinVO" class="poa.aprobacion.vo.PlaneacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.aprobacion.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxLinea(){
		borrar_combo(document.frmNuevo.plaLineaAccion); 
		document.frmAjax.ajax[0].value=document.frmNuevo.plaAreaGestion.value;
		document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_LINEA}"/>';
 		document.frmAjax.target="frameAjax";
		document.frmAjax.submit();
	}
		
	function setUnidad(){
		var forma=document.frmNuevo;
		if(parseInt(forma.plaUnidades[forma.plaMetaAnualUnidad.selectedIndex].value)==1){
			forma.plaMetaAnualCual.disabled=false;
		}else{
			forma.plaMetaAnualCual.value="";
			forma.plaMetaAnualCual.disabled=true;
		}
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:300px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/poa/aprobacion/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmAjax" action='<c:url value="/poa/aprobacion/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ACTIVIDAD_SIN}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/aprobacion/SaveSin.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD_SIN}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="plaInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="plaUnidades" value='0'>
		<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
		<input type="hidden" name="plaUnidades" value='<c:out value="${unidad.padre}"/>'>
		</c:forEach>
	<c:if test="${sessionScope.aprobacionSinVO.formaEstado==1}">
		  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Detalle de actividad</caption>
			<tr>
			<td width="30%" title='<c:out value="${sessionScope.filtroAprobacionVO.lblVigencia}"/>'><span class="style2">*</span> Vigencia:</td>
			<td width="25%" colspan="3">
				<select name="plaVigencia" style="width:80px;" <c:out value="${sessionScope.aprobacionSinVO.plaDisabled}"/>>
					<option value="-99" selected>-Seleccione uno-</option>
					<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
						<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.aprobacionSinVO.plaVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
		 	</tr>	
			<tr>
				<td title='<c:out value="${sessionScope.filtroAprobacionVO.lblObjetivo}"/>'><span class="style2">*</span> Objetivo estratégico:</td>
				<td colspan="3" align="right">
					<textarea name="plaObjetivo" cols="70" rows="3" <c:out value="${sessionScope.aprobacionSinVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.aprobacionSinVO.plaObjetivo}"/></textarea>
				</td>
		 	</tr>	
			<tr>
				<td title='<c:out value="${sessionScope.filtroAprobacionVO.lblActividad}"/>'><span class="style2">*</span> Actividad / Tarea:</td>
				<td colspan="3" align="right">
					<textarea name="plaActividad" cols="70" rows="3" <c:out value="${sessionScope.aprobacionSinVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.aprobacionSinVO.plaActividad}"/></textarea>
				</td>
		 	</tr>	
		 	<tr>
				<td title='<c:out value="${sessionScope.filtroAprobacionVO.lblAreaGestion}"/>' width="30%"><span class="style2">*</span> Área de gestión:</td>
				<td width="25%">
					<select name="plaAreaGestion" style="width:180px;" <c:out value="${sessionScope.aprobacionSinVO.plaDisabled}"/> onchange="ajaxLinea()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
							<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.aprobacionSinVO.plaAreaGestion}">selected</c:if>><c:out value="${area.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td title='<c:out value="${sessionScope.filtroAprobacionVO.lblLineaAccion}"/>' width="30%"><span class="style2">*</span> Línea de acción / proceso / proyecto:</td>
				<td width="25%" align="right">
					<select name="plaLineaAccion" style="width:180px;" <c:out value="${sessionScope.aprobacionSinVO.plaDisabled}"/>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaLineaAccion}" var="linea">
							<option value="<c:out value="${linea.codigo}"/>" <c:if test="${linea.codigo==sessionScope.aprobacionSinVO.plaLineaAccion}">selected</c:if>><c:out value="${linea.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
		 	</tr>
		</table>	
		<table align="center" width="95%" border="1" cellpadding="0" cellspacing="0"><tr><td>
				<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
						<tr><th colspan="6" class="Fila0">Meta anual / producto final</th></tr>
						<tr>
						
						<td title='<c:out value="${sessionScope.filtroAprobacionVO.lblMetaAnualUnidad}"/>'><span class="style2">*</span> Unidad de medida:</td>
						<td>
							<select name="plaMetaAnualUnidad" style="width:120px;" <c:out value="${sessionScope.aprobacionSinVO.plaDisabled}"/> onchange="javaScript:setUnidad()">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
									<option value="<c:out value="${unidad.codigo}"/>" <c:if test="${unidad.codigo==sessionScope.aprobacionSinVO.plaMetaAnualUnidad}">selected</c:if>><c:out value="${unidad.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
						<td  title='<c:out value="${sessionScope.filtroAprobacionVO.lblMetaAnualCual}"/>'>¿Cuál?:</td>
						<td>
						<input type="text" name="plaMetaAnualCual" maxlength="100" size="20" <c:out value="${sessionScope.aprobacionSinVO.plaDisabled}"/> value='<c:out value="${sessionScope.aprobacionSinVO.plaMetaAnualCual}"/>'></input>
						</td>
						<td title='<c:out value="${sessionScope.filtroAprobacionVO.lblMetaAnualCantidad}"/>'><span class="style2">*</span> Cantidad:</td>
						<td>
						<input type="text" name="plaMetaAnualCantidad" maxlength="5" size="3" <c:out value="${sessionScope.aprobacionSinVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.aprobacionSinVO.plaMetaAnualCantidad}"/>'></input>
						</td>
						</tr>
				</table>
			</td>
			</tr>
		</table>
		<table align="center" width="95%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td title='<c:out value="${sessionScope.filtroAprobacionVO.lblFuenteFinanciera}"/>'><span class="style2">*</span> Fuente financiera:</td>
				<td>
					<c:forEach begin="0" items="${requestScope.listaFuenteFinanciera}" var="fuente">
						<input type="checkbox" name="plaFuenteFinanciera" <c:out value="${sessionScope.aprobacionSinVO.plaDisabled}"/> value='<c:out value="${fuente.codigo}"/>' <c:forEach begin="0" items="${sessionScope.aprobacionSinVO.plaFuenteFinanciera}" var="fuenteF"><c:if test="${fuente.codigo==fuenteF}">checked</c:if></c:forEach>><c:out value="${fuente.nombre}"/><br>
					</c:forEach>
				</td>
				<td  title='<c:out value="${sessionScope.filtroAprobacionVO.lblPresupuesto}"/>'>Presupuesto:</td>
				<td>
				$<input type="text" name="plaPresupuesto" maxlength="10" size="5" <c:out value="${sessionScope.aprobacionSinVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.aprobacionSinVO.plaPresupuesto}"/>'></input>
				</td>
			</tr>						 	
			<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>	
		</table>	
	</c:if>	
	</form>
</body>
<c:if test="${sessionScope.aprobacionSinVO.formaEstado==1}">
	<script type="text/javascript">
		<c:if test="${!sessionScope.aprobacionSinVO.plaDesHabilitado}">setUnidad();</c:if>
	</script>
</c:if>	
</html>