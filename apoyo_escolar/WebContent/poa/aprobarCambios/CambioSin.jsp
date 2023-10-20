<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="cambioActividadesSinVO" class="poa.aprobacionActividades.vo.PlaneacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.aprobacionActividades.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="poa.aprobarCambios.vo.ParamsVO" scope="page"/>
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

	function lanzar(tipo){
  		document.frmNuevo.tipo.value=tipo;
  		document.frmNuevo.cmd.value=document.frmNuevo.BUSCAR.value;
		document.frmNuevo.target="";
		document.frmNuevo.submit();
	}


	function editar(n,m,o){
		if(document.frmNuevo.id){
				document.frmNuevo.id.value=n;
				document.frmNuevo.id2.value=m;
				document.frmNuevo.id3.value=o;
				document.frmNuevo.cmd.value=document.frmNuevo.EDITAR.value;
				document.frmNuevo.submit();
		}
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			validarCampos(document.frmNuevo);
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}

	function validarCampos(forma){
		//campo Cual
		forma.plaMetaAnualCual.disabled=false;
		if(trim(forma.plaMetaAnualCual.value)==''){
			forma.plaMetaAnualCual.value=' ';
		}		
	}


	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.plaVigencia, "- Vigencia", 1)
	   
	   if(  document.getElementById('placcodobjetivo').style.display == 'none' ){
		 validarCampo(forma.plaObjetivo, "- Objetivo", 1, 320)
		}else{
		 validarLista(forma.placcodobjetivo, "- Objetivo", 1)
		}
		
		validarCampo(forma.plaActividad, "- Problema", 1, 320)
		validarLista(forma.plaAreaGestion, "- Área de gestión", 1)
		validarLista(forma.plaLineaAccion, "- Línea de acción / proceso / proyecto", 1)
		validarEntero(forma.plaMetaAnualCantidad, "- Cantidad", 1, 9999999)
		validarLista(forma.plaMetaAnualUnidad, "- Unidad de medida", 1)
		//validacion del cual
		if(parseInt(forma.plaUnidades[0.3].value)==1){
			validarCampo(forma.plaMetaAnualCual, "- ¿Cuál?", 1, 100)
		}	
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
				<a href="javaScript:lanzar(<c:out value="${params2VO.FICHA_CAMBIO}"/>)"><img src='<c:url value="/etc/img/tabs/aprobarCambios_0.gif"/>' alt="Aprobar Cambios POA" height="26" border="0"></a>										
					<img src='<c:url value="/etc/img/tabs/realizarCambio_1.gif"/>' alt="Realizar Cambio POA" height="26" border="0">
				</td>
			</tr>
</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:120px;overflow:auto;vertical-align:top;background-color:#ffffff;">
			<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
					<tr>
					<td width="45%"><span class="style2"></span> Vigencia:</td>
					<td width="25%" colspan="1">
						<c:out value="${sessionScope.cambio2VO.vigencia}"/>				
					</td>
					<c:if test="${sessionScope.cambio2VO.fechaSol!=null}">
							<td><b>Fecha Solicitud:</b>
								<c:out value="${sessionScope.cambio2VO.fechaSol}"/>
							</td>
						</c:if>
				 	</tr>	
					<tr><td><span class="style2">*</span> Asunto:</td>
						<td colspan="3" align="left">
						<input type="text" name="asunto" maxlength="180" size="70" <c:out value="${sessionScope.cambio2VO.disabled}"/> value='<c:out value="${sessionScope.cambio2VO.asunto}"/>'></input>
						</td>
							
				 	</tr>	
					<tr>
						<td><span class="style2">*</span> Solicitud:</td>
						<td colspan="3" align="left">
							<textarea name="solicitud" cols="72" rows="3"  <c:out value="${sessionScope.cambio2VO.disabled}"/> <c:out value="${sessionScope.cambio2VO.solicitud}"/> onKeyDown="textCounter(this,1800,1800);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.cambio2VO.solicitud}"/></textarea>
						</td>
				 	</tr>	
				 	<tr>
						<td width="30%"><span class="style2">*</span> Estado:</td>
						<td width="25%">
							<b><c:out value="${sessionScope.cambio2VO.nombreEstado}"/></b>
						</td>
						<c:if test="${sessionScope.cambio2VO.fechaEstado!=null}">
						<td><b>Fecha Estado:</b> <c:out
							value="${sessionScope.cambio2VO.fechaEstado}" /></td>
					</c:if>
						</tr>
		</table>	
			</div>
		</td></tr>
	</table>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<a href="javaScript:lanzar(<c:out value="${params2VO.FICHA_ACTIVIDAD}"/>)"><img src='<c:url value="/etc/img/tabs/actividades_0.gif"/>' alt="Actividades" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/problemas_1.gif"/>' alt="Actividades sin recursos" height="26" border="0">
				</td>
			</tr>
		</table>
		<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>POA. <c:out value="${sessionScope.filtroCambioVO.filNombreColegio}"/></caption>
		 	<c:if test="${empty requestScope.listaActividades}">
				<tr>
					<th class="Fila1" colspan='6'>No hay actividades</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaActividades}">
				<tr>
					<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Objetivo</td>
					<td class="EncabezadoColumna" align="center">Problema</td>
					<td class="EncabezadoColumna" align="center">Área de gestión</td>
					<td class="EncabezadoColumna" align="center">Línea de acción</td>
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
					</tr>
				</c:forEach>
			</c:if>
		</table>
		</div>
	<form method="post" name="frmAjax" action='<c:url value="/poa/aprobacion/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ACTIVIDAD_SIN}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/aprobarCambios/SavePro.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${params2VO.FICHA_ACTIVIDAD_SIN}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="plaInstitucion" value='<c:out value="${sessionScope.filtroCambioVO.filEntidad}"/>'>
		<input type="hidden" name="id" value=""><input type="hidden" name="id2" value=""><input type="hidden" name="id3" value="">
		<input type="hidden" name="plaUnidades" value='0'>
		<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
		<input type="hidden" name="plaUnidades" value='<c:out value="${unidad.padre}"/>'>
		</c:forEach>
	<c:if test="${sessionScope.cambioActividadesSinVO.formaEstado==1}">
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
			<caption>Detalle del Problema</caption>
				<tr>
					<td width="45%">
							<c:if test="${sessionScope.NivelPermiso==2}">
    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">								
	    				</c:if>					
			  		</td>
			 	</tr>	
		 </table>
		  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		 
			<tr>
			<td width="30%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblVigencia}"/>'><span class="style2">*</span> Vigencia:</td>
			<td width="25%" colspan="3">
				<select name="plaVigencia" style="width:80px;" <c:out value="${sessionScope.cambioActividadesSinVO.plaDisabled}"/>>
					<option value="-99" selected>-Seleccione uno-</option>
					<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
						<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.cambioActividadesSinVO.plaVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
		 	</tr>	
			<tr>
				<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblObjetivo}"/>'><span class="style2">*</span> Objetivo:</td>
				<td colspan="3" align="right">
				<select  style="width: 470;display:none"  name="placcodobjetivo" id="placcodobjetivo" >
						<option value="-99" selected>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${sessionScope.listaObjetivos}" var="vig">
							<option  title='<c:out value="${vig.nombre}"/>' value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo == sessionScope.cambioActividadesSinVO.placcodobjetivo  }">selected</c:if>><c:out value="${vig.nombre}"/></option>
						</c:forEach>					
					</select>
					
					<textarea name="plaObjetivo" cols="70" rows="3" <c:out value="${sessionScope.cambioActividadesSinVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.cambioActividadesSinVO.plaObjetivo}"/></textarea>
				</td>
		 	</tr>	
			<tr>
				<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblActividad}"/>'><span class="style2">*</span> Problema:</td>
				<td colspan="3" align="right">
					<textarea name="plaActividad" cols="70" rows="3" <c:out value="${sessionScope.cambioActividadesSinVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.cambioActividadesSinVO.plaActividad}"/></textarea>
				</td>
		 	</tr>	
		 	<tr>
				<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblAreaGestion}"/>' width="30%"><span class="style2">*</span> Área de gestión:</td>
				<td width="25%">
					<select name="plaAreaGestion" style="width:180px;" <c:out value="${sessionScope.cambioActividadesSinVO.plaDisabled}"/> onchange="ajaxLinea()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
							<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.cambioActividadesSinVO.plaAreaGestion}">selected</c:if>><c:out value="${area.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblLineaAccion}"/>' width="30%"><span class="style2">*</span> Línea de acción / proceso / proyecto:</td>
				<td width="25%" align="right">
					<select name="plaLineaAccion" style="width:180px;" <c:out value="${sessionScope.cambioActividadesSinVO.plaDisabled}"/>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaLineaAccion}" var="linea">
							<option value="<c:out value="${linea.codigo}"/>" <c:if test="${linea.codigo==sessionScope.cambioActividadesSinVO.plaLineaAccion}">selected</c:if>><c:out value="${linea.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
		 	</tr>
		</table>	
		<table align="center" width="95%" border="1" cellpadding="0" cellspacing="0"><tr><td>
				<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
						<tr><th colspan="6" class="Fila0">Cantidad del Problema</th></tr>
						<tr>
						
						<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblMetaAnualUnidad}"/>'><span class="style2">*</span> Unidad de medida:</td>
						<td>
							<select name="plaMetaAnualUnidad" style="width:120px;" <c:out value="${sessionScope.cambioActividadesSinVO.plaDisabled}"/> onchange="javaScript:setUnidad()">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
									<option value="<c:out value="${unidad.codigo}"/>" <c:if test="${unidad.codigo==sessionScope.cambioActividadesSinVO.plaMetaAnualUnidad}">selected</c:if>><c:out value="${unidad.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
						<td  title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblMetaAnualCual}"/>'>¿Cuál?:</td>
						<td>
						<input type="text" name="plaMetaAnualCual" maxlength="100" size="20" <c:out value="${sessionScope.cambioActividadesSinVO.plaDisabled}"/> value='<c:out value="${sessionScope.cambioActividadesSinVO.plaMetaAnualCual}"/>'></input>
						</td>
						<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblMetaAnualCantidad}"/>'><span class="style2">*</span> Cantidad:</td>
						<td>
						<input type="text" name="plaMetaAnualCantidad" maxlength="5" size="3" <c:out value="${sessionScope.cambioActividadesSinVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.cambioActividadesSinVO.plaMetaAnualCantidad}"/>'></input>
						</td>
						</tr>
				</table>
			</td>
			</tr>
			<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
		</table>
		
	</c:if>	
	</form>
</body>
<c:if test="${sessionScope.cambioActividadesSinVO.formaEstado==1}">
	<script type="text/javascript">
		<c:if test="${!sessionScope.cambioActividadesSinVO.plaDesHabilitado}">setUnidad();</c:if>
	</script>
</c:if>	
</html>