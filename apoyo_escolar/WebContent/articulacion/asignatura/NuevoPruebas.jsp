<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	
	var sumPP=0;
	var sumPS=0;
	var cantPS=0;
	var maxP=0;
	var combo=0;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}

	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.artPruNombre, "- Nombre de la Prueba", 1, 60);
		validarCampo(forma.artPruAbreviatura, "- Abreviatura de la Prueba", 1, 4);
		validarFloat(forma.artPruPorcentaje,'- Porcentaje de la Prueba',0.1,100);
		if(parseFloat(forma.estado.value)!=1){ //NO ESTA EDITANDO  
			if(forma.total.length){
				var usuario=forma.artPruPorcentaje.value;
				var total=forma.total[/*forma.pruebaPrincipal.selectedIndex*/0].value;
				if(parseFloat(usuario)+parseFloat(total)>100){
		      appendErrorMessage("El porcentaje excede el 100 % solo dispone de un "+(100-parseFloat(total))+" %");
		      if (_campoError == null) {
		           _campoError = forma.artPruPorcentaje;
		       }
		       return false;
				}
			}	
		}else{ //SI ESTA EDITANDO
				var usuario=forma.artPruPorcentaje.value;
				var total=forma.artPruTotal.value;
				if(parseFloat(usuario)+parseFloat(total)>100){
		      appendErrorMessage("El porcentaje excede el 100 % solo dispone de un "+(100-parseFloat(total))+" %");
		      if (_campoError == null) {
		           _campoError = forma.artPruPorcentaje;
		       }
		       return false;
				}
		}	
	}
	
	
	/*
	function maximoPrincipales(){
		maxP=0;
		if(document.pruebas.pp){
			if(document.pruebas.pp.length){
				for(var i=0;i<document.pruebas.pp.length;i++){
					if(document.pruebas.ps[i].value=="0"){
						maxP=parseFloat(maxP)+parseFloat(document.pruebas.por[i].value);
					}
				}
			}else{
				if(document.pruebas.ps.value=="0"){
					maxP=maxP+parseInt(document.pruebas.por.value);
				}
			}
		}
		sumPP=maxP;
		if(maxP>=100){
			deshabilitar();
		}
	}
	*/
	
	function cambioCombo(cmb){
		sumPP=0;
		sumPS=0;
		cantPS=0;
		combo=cmb.options[cmb.selectedIndex].value;
		if(cmb.selectedIndex>0){
			document.frmNuevo.artPruBimestre.disabled=true;
		}else{
			document.frmNuevo.artPruBimestre.disabled=false;
		}
		habilitar();
		if(document.pruebas.pp){
			if(document.pruebas.pp.length){
				for(var i=0;i<document.pruebas.pp.length;i++){
					if(document.pruebas.ps[i].value=="0"){
						sumPP=parseFloat(sumPP)+parseFloat(document.pruebas.por[i].value);
						if(document.pruebas.pp[i].value==combo){
							porPP=document.pruebas.por[i].value;
						}
					}
					else{
						if(document.pruebas.pp[i].value==combo){
							cantPS=cantPS+1;
							sumPS=parseFloat(sumPS)+parseFloat(document.pruebas.por[i].value);
						}
					}
				}
			}else{
				if(document.pruebas.ps.value=="0"){
					sumPP=document.pruebas.por.value;
					cantPP=1;
				}
				if(document.pruebas.pp.value==combo){
					porPP=document.pruebas.por.value;
				}
			}
		}
	//	alert("sumPP="+sumPP+" porPP="+porPP+" cantPS="+cantPS+" sumPS="+sumPS);
		if(combo==0){
			if(sumPP>=100){
				alert("No se pueden insertar más Pruebas Principales");
				document.frmNuevo.artPruNombre.disabled=true;
			}else{
				document.frmNuevo.artPruPorcentaje.value=100-sumPP;
			}
		}else{
			if(parseFloat(porPP)-parseFloat(sumPS)<=0 || cantPS>=5){
				alert("No se pueden insertar más sub Pruebas para esta Prueba Principal");
				deshabilitar();
			}else{
				document.frmNuevo.artPruPorcentaje.value=parseFloat(porPP)-parseFloat(sumPS);
			}
		}
	}
	
	function deshabilitar(){
		document.frmNuevo.artPruPorcentaje.disabled=true;
	}
	function habilitar(){
		document.frmNuevo.artPruPorcentaje.disabled=false;
	}
	
	function xxxx(caja){
		//alert(caja.value+" "+combo+" "+maxP);
		if(maxP==0){
			if(caja.value>100){
				alert("Una prueba no puede superar el 100%");
			}
		}else{
			if(combo==0){
				if((parseFloat(sumPP)+parseFloat(caja.value))>100){
					alert("El porcentaje de las pruebas principales supera el 100%");
					caja.value=100-parseFloat(sumPP);
				}
			}else{
				if((parseFloat(sumPS)+parseFloat(caja.value))>parseFloat(porPP)){
					alert("El porcentaje de la sub Prueba no debe superar el "+(parseFloat(porPP)-parseFloat(sumPS))+" %");
					caja.value=parseFloat(porPP)-parseFloat(sumPS);
				}
			}
		}
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:145px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/asignatura/Filtro.do"/>
			</div>
			<div style="width:100%;height:120px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/asignatura/Lista.do"/>
			</div>
		</td></tr>
	</table>
 	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/asignatura/SaveP.jsp"/>'>
 		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_PRUEBA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="artPruCodAsig" value='<c:out value="${sessionScope.datosVOP.asignatura}"/>'>
		<input type="hidden" name="artPruCodigo" value='<c:out value="${sessionScope.pruebaVO.artPruCodigo}"/>'>
		<input type="hidden" name="artPruTotal" value='<c:out value="${sessionScope.pruebaVO.artPruTotal}"/>'>
		<input type="hidden" name="estado" value='0<c:out value="${sessionScope.pruebaVO.formaEstado}"/>'>
		<input type="hidden" name="pruebaPrincipal" value='0'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Información Pruebas</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        			<c:if test="${sessionScope.pruebaVO.formaEstado==1}">
		        				<input name="cmd1" type="button" value="Actualizar" onClick="guardar()" class="boton">
		        			</c:if>
	    					<c:if test="${sessionScope.pruebaVO.formaEstado!=1}">
	    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    					</c:if>
	    				</c:if>
			  		</td>
			 	</tr>	
		  </table>
		  <table width="100%" border="0" cellspacing="2" cellpadding="0">
			  <!-- <tr>
		  		<td><span class="style2">*</span><b>Prueba Principal:</b></td>
		  		<td colspan="3">
						<c:forEach begin="0" items="${requestScope.listaPruebasPrinVO}" var="pruebaP">
							<input type="hidden" name="total" value='<c:out value="${pruebaP.artPruPorcentaje}"/>'>
						</c:forEach>
						<select name="pruebaPrincipal" <c:if test="${sessionScope.pruebaVO.formaEstado==1}">disabled</c:if>>
							<c:forEach begin="0" items="${requestScope.listaPruebasPrinVO}" var="pruebaP">
								<option value='<c:out value="${pruebaP.artPruCodigo}"/>' <c:if test="${pruebaP.artPruCodigo==sessionScope.pruebaVO.artPruCodigo && sessionScope.pruebaVO.pruebaPrincipal>0}">selected</c:if>><c:out value="${pruebaP.artPruNombre}"/></option>
							</c:forEach>
						</select>
		  		</td>
		  	</tr> -->
				<tr>
					<td><span class="style2">*</span><b>Nombre:</b></td>
					<td colspan="3">
						<input type="text" name="artPruNombre" maxlength="60" size="60"  value='<c:out value="${sessionScope.pruebaVO.artPruNombre}"/>'></input>
					</td>
				</tr>
				<tr>
					<td><span class="style2">*</span><b>Abreviatura:</b></td>
					<td>
						<input type="text" name="artPruAbreviatura" maxlength="4" size="4"  value='<c:out value="${sessionScope.pruebaVO.artPruAbreviatura}"/>'></input>
					</td>
					<td><span class="style2">*</span><b>Porcentaje:</b></td>
					<td>
						<c:forEach begin="0" items="${requestScope.listaPruebasPrinVO}" var="pruebaP">
							<input type="hidden" name="total" value='<c:out value="${pruebaP.artPruPorcentaje}"/>'>
						</c:forEach>
						<input type="text" name="artPruPorcentaje" maxlength="5" size="5"  value='<c:out value="${sessionScope.pruebaVO.artPruPorcentaje}"/>'><b>&nbsp;%</b>
					</td>
				</tr>
				<tr>
					<td><b>Orden:</b></td>
					<td>
						<input type="text" name="artPruOrden" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)'  value='<c:out value="${sessionScope.pruebaVO.artPruOrden}"/>'></input>
					</td>
					<td><b><span class="style2">*</span>Aplica para:</b></td>
					<td>
						<select name="artPruBimestre" style="width:50px;" <c:if test="${0!=sessionScope.pruebaVO.pruebaPrincipal && sessionScope.pruebaVO.formaEstado==1}">disabled</c:if>>
							<!-- <option value="0" <c:if test="${0==sessionScope.pruebaVO.artPruBimestre}">selected</c:if>>Ambos bimestres</option> -->
							<option value="1" <c:if test="${1==sessionScope.pruebaVO.artPruBimestre}">selected</c:if>>1</option>
							<option value="2" <c:if test="${2==sessionScope.pruebaVO.artPruBimestre}">selected</c:if>>2</option>
							<option value="3" <c:if test="${2==sessionScope.pruebaVO.artPruBimestre}">selected</c:if>>3</option>
						</select> Periodo
					</td>
				</tr>
			</table>
	</form>
	<form method="post" name="pruebas">
		<c:forEach begin="0" items="${requestScope.listaPruebasAsigVO}" var="pruebaP">
			<input type="hidden" name="pp" value="<c:out value="${pruebaP.artPruCodigo}"/>">
			<input type="hidden" name="ps" value="<c:out value="${pruebaP.pruebaPrincipal}"/>">
			<input type="hidden" name="por" value="<c:out value="${pruebaP.artPruPorcentaje}"/>">
		</c:forEach>
	</form>
</body>
<script>
	//maximoPrincipales();
</script>
</html>