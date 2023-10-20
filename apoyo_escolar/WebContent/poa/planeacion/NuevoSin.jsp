<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="planeacionSinVO" class="poa.planeacion.vo.PlaneacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.planeacion.vo.ParamsVO" scope="page"/>
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
		
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/poa/planeacion/Nuevo.do"/>';
		document.frmNuevo.submit();
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
		//campo presupuesto
		if(forma.plaPresupuesto &&  trim(forma.plaPresupuesto.value)==''){
			forma.plaPresupuesto.value=0;
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
		if(parseInt(forma.plaUnidades[forma.plaMetaAnualUnidad.selectedIndex].value)==1){
			validarCampo(forma.plaMetaAnualCual, "- ¿Cuál?", 1, 100)
		}
		//fin de validacion del cual
		//validarSeleccion(forma.plaFuenteFinanciera, "- Fuente financiera")
		//validarEnteroOpcional(forma.plaPresupuesto, "- Presupuesto", 1, 9999999999)
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
	
	function setCeros(){
		var forma=document.frmNuevo;
		if(trim(forma.plaMetaAnualCantidad.value).length>0){
			if(parseFloat(trim(forma.plaMetaAnualCantidad.value))==0.0){
				forma.plaMetaAnualCantidad.value="";
			}
		}
		if( forma.plaPresupuesto && trim(forma.plaPresupuesto.value).length>0){
			if(parseFloat(trim(forma.plaPresupuesto.value))==0.0){
				forma.plaPresupuesto.value="";
			}
		}
	}
	
	function setVisible(){ 
	  if( document.frmNuevo.plaVigencia.value >  2010){
	   document.getElementById('plaObjetivo').style.display='none';
	   document.getElementById('placcodobjetivo').style.display='';
	   
	  }else{
	   document.getElementById('plaObjetivo').style.display='';
	   document.getElementById('placcodobjetivo').style.display='none';
	  }
	}

</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	
	<form method="post" name="frmAjax" action='<c:url value="/poa/planeacion/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ACTIVIDAD_SIN}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/planeacion/SaveSin.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD_SIN}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="plaInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="plaUnidades" value='0'>
		<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
		<input type="hidden" name="plaUnidades" value='<c:out value="${unidad.padre}"/>'>
		</c:forEach>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>)"><img src='<c:url value="/etc/img/tabs/actividades_0.gif"/>' alt="Actividades" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/problemas_1.gif"/>' alt="Actividades sin recursos" height="26" border="0">
				</td>
			</tr>
		</table>
		<table border="2" align="center" bordercolor="#FFFFFF" width="95%">
			<caption>Registro / edición de problema</caption>
				<tr>
					<td >
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.filtroPlaneacionSinVO.filFechaHabil==true}">
   						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
    				</c:if>
						<c:if test="${sessionScope.filtroPlaneacionSinVO.filFechaHabil==false}"><span class="style2">No se puede registrar actividades porque esta por fuera de la fecha establecida </span></c:if>
			  		</td>
			 	</tr>	
		 </table>
		  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
			<td width="10%" title='<c:out value="${sessionScope.filtroPlaneacionSinVO.lblVigencia}"/>'><span class="style2">*</span>Vigencia:</td>
			<td   colspan="3">
				<select name="plaVigencia" style="width:80px;" onchange="setVisible();" <c:out value="${sessionScope.planeacionSinVO.plaDisabled}"/>>
					<option value="-99" selected>-Seleccione uno-</option>
					<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
						<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.planeacionSinVO.plaVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
		 	</tr>	
			<tr><td  title='<c:out value="${sessionScope.filtroPlaneacionSinVO.lblObjetivo}"/>'><span class="style2">*</span>Objetivo:</td>
				<td colspan="3" align="left">
					<select  style="width: 400;display:none"  name="placcodobjetivo" id="placcodobjetivo" >
						<option value="-99" selected>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${sessionScope.listaObjetivos}" var="vig">
							<option  title='<c:out value="${vig.nombre}"/>' value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo == sessionScope.planeacionSinVO.placcodobjetivo  }">selected</c:if>><c:out value="${vig.nombre}"/></option>
						</c:forEach>					
					</select>
					<textarea name="plaObjetivo"  id="plaObjetivo"  style="width: 100%" rows="3" <c:out value="${sessionScope.planeacionSinVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.planeacionSinVO.plaObjetivo}"/></textarea>
				</td>
		 	</tr>	
		 	<tr><td>&nbsp;&nbsp;</td></tr>
			<tr><td colspan="4" title='<c:out value="${sessionScope.filtroPlaneacionSinVO.lblActividad}"/>'><span class="style2">*</span>Descripción del Problema o la Necesidad:</td>
			</tr>	
			<tr>	
			   <td>&nbsp;
			   </td>
				<td colspan="3" align="right">
					<textarea name="plaActividad"  style="width: 100%"  rows="3" <c:out value="${sessionScope.planeacionSinVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.planeacionSinVO.plaActividad}"/></textarea>
				</td>
		 	</tr>	
		 	<tr>
				<td title='<c:out value="${sessionScope.filtroPlaneacionSinVO.lblAreaGestion}"/>' width="30%"><span class="style2">*</span>Área de gestión:</td>
				<td width="40%">
					<select name="plaAreaGestion" style="width:180px;" <c:out value="${sessionScope.planeacionSinVO.plaDisabled}"/> onchange="ajaxLinea()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
							<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.planeacionSinVO.plaAreaGestion}">selected</c:if>><c:out value="${area.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td title='<c:out value="${sessionScope.filtroPlaneacionSinVO.lblLineaAccion}"/>' width="40%"><span class="style2">*</span>Línea de acción / proceso / proyecto:</td>
				<td width="55%" align="right">
					<select name="plaLineaAccion" style="width:180px;" <c:out value="${sessionScope.planeacionSinVO.plaDisabled}"/>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaLineaAccion}" var="linea">
							<option value="<c:out value="${linea.codigo}"/>" <c:if test="${linea.codigo==sessionScope.planeacionSinVO.plaLineaAccion}">selected</c:if>><c:out value="${linea.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
		 	</tr>
		</table>	
		<table align="center" width="95%" border="1" cellpadding="0" cellspacing="0"><tr><td>
				<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
						<tr><th colspan="6" class="Fila0">Cantidad del problema</th></tr>
						<tr>
						
						<td title='<c:out value="${sessionScope.filtroPlaneacionSinVO.lblMetaAnualUnidad}"/>'><span class="style2">*</span> Unidad de medida:</td>
						<td>
							<select name="plaMetaAnualUnidad" style="width:120px;" <c:out value="${sessionScope.planeacionSinVO.plaDisabled}"/> onchange="javaScript:setUnidad()">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
									<option value="<c:out value="${unidad.codigo}"/>" <c:if test="${unidad.codigo==sessionScope.planeacionSinVO.plaMetaAnualUnidad}">selected</c:if>><c:out value="${unidad.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
						<td  title='<c:out value="${sessionScope.filtroPlaneacionSinVO.lblMetaAnualCual}"/>'>¿Cuál?:</td>
						<td>
						<input type="text" name="plaMetaAnualCual" maxlength="100" size="20" <c:out value="${sessionScope.planeacionSinVO.plaDisabled}"/> value='<c:out value="${sessionScope.planeacionSinVO.plaMetaAnualCual}"/>'></input>
						</td>
						<td title='<c:out value="${sessionScope.filtroPlaneacionSinVO.lblMetaAnualCantidad}"/>'><span class="style2">*</span> Cantidad:</td>
						<td>
						<input type="text" name="plaMetaAnualCantidad" maxlength="7" size="4" <c:out value="${sessionScope.planeacionSinVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.planeacionSinVO.plaMetaAnualCantidad}"/>'></input>
						</td>
						</tr>
						<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
				</table>
			</td>
			</tr>
		</table>
		<!-- 
		<table align="center" width="95%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td title='<c:out value="${sessionScope.filtroPlaneacionSinVO.lblFuenteFinanciera}"/>'><span class="style2">*</span> Fuente financiera:</td>
				<td>
					<c:forEach begin="0" items="${requestScope.listaFuenteFinanciera}" var="fuente">
						<input type="checkbox" name="plaFuenteFinanciera" <c:out value="${sessionScope.planeacionSinVO.plaDisabled}"/> value='<c:out value="${fuente.codigo}"/>' <c:forEach begin="0" items="${sessionScope.planeacionSinVO.plaFuenteFinanciera}" var="fuenteF"><c:if test="${fuente.codigo==fuenteF}">checked</c:if></c:forEach>><c:out value="${fuente.nombre}"/><br>
					</c:forEach>
				</td>
				<td  title='<c:out value="${sessionScope.filtroPlaneacionSinVO.lblPresupuesto}"/>'> Presupuesto:</td>
				<td>
				$<input type="text" name="plaPresupuesto" maxlength="10" size="5" <c:out value="${sessionScope.planeacionSinVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.planeacionSinVO.plaPresupuesto}"/>'></input>
				</td>
			</tr>
			<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
		</table>
		 -->	
	</form> 
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:350px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/poa/planeacion/Filtro.do"/>
			</div>
		</td></tr>
	</table>
</body>
<script type="text/javascript">

	setVisible();
 

	<c:if test="${ !sessionScope.planeacionSinVO.plaDesHabilitado}">
	  try{
	  setUnidad();
	  }catch(e){
	    alert(e);
	  }
	</c:if>
	 try{
	setCeros();
	}catch(e){
	    alert(e);
	  }

</script>
</html>