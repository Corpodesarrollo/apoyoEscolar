<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="EncuestaVO" class="articulacion.artEncuesta.vo.EncuestaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.artEncuesta.vo.ParamsVO" scope="page"/>
<c:import url="/articulacion/artEncuesta/Filtro.do"><c:param name="tipo" value="${paramsVO.FICHA_FILTRO}"/><c:param name="cmd" value="${paramsVO.CMD_NUEVO}"/></c:import>
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
			document.frmNuevo.cmd.value=document.frmNuevo.DESCARGAR.value;
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
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/artEncuesta/Filtro.do"/>' method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_FILTRO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="modulo" value='48'>
		<input type="hidden" name="DESCARGAR" value='<c:out value="${paramsVO.CMD_DESCARGAR}"/>'>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<caption>LISTADO ESTUDIANTES - ENCUESTA</caption>
		</table>	
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
				<tr><td width="45%">
		        <input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
			  </td></tr>	
	  </table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			<tr>
				<th class="EncabezadoColumna">&nbsp;</th>
				<th class="EncabezadoColumna">Estado</th>
				<th class="EncabezadoColumna">Cantida</th>
				<th class="EncabezadoColumna">Porcentaje</th>
				<th class="EncabezadoColumna">Estado</th>
				<th class="EncabezadoColumna">Fecha</th>				
			</tr>
			<c:forEach begin="0" items="${requestScope.listaEstudiante}" var="est">
				<tr>
					<td><input type="hidden" name="plaEst" value='<c:out value="${est.estCodigo}"/>'><c:out value="${est.estConsecutivo}"/></td>
					<td><c:out value="${est.estApellido}"/></td>
					<td><c:out value="${est.estNombre}"/></td>
					<td><c:out value="${est.estTipoDoc}"/>. <c:out value="${est.estNumDoc}"/></td>
					<td><c:out value="${est.estEstado}"/></td>
					<td><c:out value="${est.estFechaEnc}"/> &nbsp;</td>
				</tr>
			</c:forEach>
		</table>		
	</form>
</body>
</html>