<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="plantillaFinalVO" class="articulacion.artPlantillaFinal.vo.PlantillaFinalVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.artPlantillaFinal.vo.ParamsVO" scope="page"/>
<c:import url="/articulacion/artPlantillaFinal/Filtro.do"><c:param name="tipo" value="${paramsVO.FICHA_FILTRO_EVAL}"/><c:param name="cmd" value="${paramsVO.CMD_NUEVO}"/></c:import>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	var nav4=window.Event ? true : false;
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57) || key==46);
	}
	
	function guardar(){
		if(validarForma(document.frmNuevo)){
			setValores(document.frmNuevo);
			habilitarCampos();
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function setValores(forma){
		var nota=0;
		if(forma.plaNota){
				if(forma.plaNota.length){
					for(var i=0;i<forma.plaNota.length;i++){
						if((trim(forma.plaNota[i].value)).length==0){
							nota=-99;
						}else{
							nota=parseFloat(trim(forma.plaNota[i].value));
						}
						forma.plaData[i].value=forma.plaData[i].value+nota;
					}
				}else{
						if((trim(forma.plaNota.value)).length==0){
							nota=-99;
						}else{
							nota=parseFloat(trim(forma.plaNota.value));
						}
						forma.plaData.value=forma.plaData.value+nota;
				}
			}
	 }
	
	function hacerValidaciones_frmNuevo(forma){
		if(forma.plaNota){
				if(forma.plaNota.length){
					for(var i=0;i<forma.plaNota.length;i++){
						validarFloat(forma.plaNota[i], "- Nota debe estar en el rango 0 y "+forma.plaNotaMax.value, 0,forma.plaNotaMax.value)
					}
				}
			}
	}
	
	function habilitarCampos(){
		var elements = document.getElementsByTagName('input');
		
		for(var i = 0; i < elements.length; i++){
			if(elements[i].type == "text"){
				elements[i].disabled="";
			}
		}
	}
	
	function validarNotaSuperacion(caja){  
		var nombreCajaNivelacion = caja.id.replace("Superacion","Nivelacion");
		var cajaNivelacion = document.getElementById(nombreCajaNivelacion);
		if(cajaNivelacion && caja.value != ""){
			if (cajaNivelacion.value == ""){
				caja.value="";
				alert("Para ingresar una nota de Superacion debe ingresar primero una nota de Nivelacion")
			}
		}
		
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/artPlantillaFinal/Save.jsp"/>' method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_FILTRO_EVAL}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="plaNotaMax" value='<c:out value="${sessionScope.plantillaFinalVO.plaNotaMax}"/>'>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<caption>REGISTRO DE EVALUACIÓN FINAL</caption>
			<tr><td width="45%">
        <input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
		  </td></tr>	
	  </table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			<tr>
				<th class="EncabezadoColumna">&nbsp;</th>
				<th class="EncabezadoColumna">Apellidos</th>
				<th class="EncabezadoColumna">Nombres</th>
				<th class="EncabezadoColumna" colspan="2">Nota Final Semestre</th>
				<th class="EncabezadoColumna" colspan="2">Nota Nivelacion Semestre</th>
				<th class="EncabezadoColumna" colspan="2">Nota Superacion Semestre</th>
			</tr>
			<c:forEach begin="0" items="${sessionScope.listaEstudiante}" var="est">
				<tr>
					<td><input type="hidden" name="plaEst" value='<c:out value="${est.estCodigo}"/>'><c:out value="${est.estConsecutivo}"/></td>
					<td><c:out value="${est.estApellido}"/></td>
					<td><c:out value="${est.estNombre}"/></td>
					
					<!-- Nota Definitiva Semestre -->
					<td align="center">
						<input type="hidden" name="<c:out value="${est.estCodigo}"/>notaDefSemestreNum_" value='<c:out value="${est.estCodigo}"/>|<c:out value="${nota.codigo}"/>|'>
						<input type="text" name="<c:out value="${est.estCodigo}"/>notaDefSemestre" 
							value='<c:forEach items="${requestScope.listaNotas}" var="nota"><c:if test="${nota.codigoEstudiante eq est.estCodigo}"><c:out value="${nota.notaNumericaFinal}"/></c:if></c:forEach>' 
							size="3" maxlength="4" style="width:40px;" onKeyPress='return acepteNumeros(event)'>
						
					</td>
					<td align="center">
						<input type="hidden" name="<c:out value="${est.estCodigo}"/>notaDefSemestreConc_" value='<c:out value="${est.estCodigo}"/>|<c:out value="${nota.codigo}"/>|'>
						<input type="text" name='<c:out value="${est.estCodigo}"/>notaDefSemestre' disabled="disabled" 
							value='<c:forEach items="${requestScope.listaNotas}" var="nota"><c:if test="${nota.codigoEstudiante eq est.estCodigo}"><c:out value="${nota.notaConceptualFinal}"/></c:if></c:forEach>' 
							size="3" maxlength="4" style="width:40px;" onKeyPress='return acepteNumeros(event)'>
					</td>
					<!-- Nota Nivelacion Semestre-->
					<td align="center">
						<input type="hidden" name="<c:out value="${est.estCodigo}"/>notaNivelacionSemestreNum_" value='<c:out value="${est.estCodigo}"/>|<c:out value="${nota.codigo}"/>|'>
						<input type="text" name='<c:out value="${est.estCodigo}"/>notaNivelacionSemestre' id="<c:out value="${est.estCodigo}"/>notaNumericaNivelacionSemestre"
							value='<c:forEach items="${requestScope.listaNotas}" var="nota"><c:if test="${nota.codigoEstudiante eq est.estCodigo}"><c:out value="${nota.notaNumericaNivelacion}"/></c:if></c:forEach>' 
							size="3" maxlength="4" style="width:40px;" onKeyPress='return acepteNumeros(event)'>
					</td>
					<td align="center">
						<input type="hidden" name="<c:out value="${est.estCodigo}"/>notaNivelacionSemestreConc_" value='<c:out value="${est.estCodigo}"/>|<c:out value="${nota.codigo}"/>|'>
						<input type="text" name='<c:out value="${est.estCodigo}"/>notaNivelacionSemestre' disabled="disabled" id="<c:out value="${est.estCodigo}"/>notaConcNivelacionSemestre"
							value='<c:forEach items="${requestScope.listaNotas}" var="nota"><c:if test="${nota.codigoEstudiante eq est.estCodigo}"><c:out value="${nota.notaConceptualNivelacion}"/></c:if></c:forEach>' 
							size="3" maxlength="4" style="width:40px;" onKeyPress='return acepteNumeros(event)'>
					</td>
					<!-- Nota Superacion Semestre-->
					<td align="center">
						<input type="hidden" name="<c:out value="${est.estCodigo}"/>notaSuperacionSemestreNum_" value='<c:out value="${est.estCodigo}"/>|<c:out value="${nota.codigo}"/>|'>
						<input type="text" name='<c:out value="${est.estCodigo}"/>notaSuperacionSemestre' id="<c:out value="${est.estCodigo}"/>notaNumericaSuperacionSemestre"
							value='<c:forEach items="${requestScope.listaNotas}" var="nota"><c:if test="${nota.codigoEstudiante eq est.estCodigo}"><c:out value="${nota.notaNumericaSuperacion}"/></c:if></c:forEach>' 
							size="3" maxlength="4" style="width:40px;" onKeyPress='return acepteNumeros(event)' onblur="validarNotaSuperacion(this);">
						
					</td>
					<td align="center">
						<input type="hidden" name="<c:out value="${est.estCodigo}"/>notaSuperacionSemestreConc_" value='<c:out value="${est.estCodigo}"/>|<c:out value="${nota.codigo}"/>|'>
						<input type="text" name='<c:out value="${est.estCodigo}"/>notaSuperacionSemestre' id="<c:out value="${est.estCodigo}"/>notaConcSuperacionSemestre" disabled="disabled" 
							value='<c:forEach items="${requestScope.listaNotas}" var="nota"><c:if test="${nota.codigoEstudiante eq est.estCodigo}"><c:out value="${nota.notaConceptualSuperacion}"/></c:if></c:forEach>' 
							size="3" maxlength="4" style="width:40px;">
					</td>
				</tr>
			</c:forEach>
		</table>		
	</form>
</body>
</html>