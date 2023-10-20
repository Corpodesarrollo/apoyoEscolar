<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="objetivoVO" class="participacion.instancia.vo.ObjetivoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.instancia.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function guardarObjetivo(){
		if(validarForma(document.frmNuevoObjetivo)){
			document.frmNuevoObjetivo.target='centro';
			document.frmNuevoObjetivo.cmd.value=document.frmNuevoObjetivo.GUARDAR.value;
			document.frmNuevoObjetivo.submit();
			parent.close();
		}
	}

	function cancelarObjetivo(){
		parent.close();
	}
	
	function hacerValidaciones_frmNuevoObjetivo(forma){
		validarCampo(forma.objDescripcion, "- Función", 1, 1000)
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmNuevoObjetivo" action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_OBJETIVO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="objInstancia" value='<c:out value="${sessionScope.instanciaVO.instCodigo}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / Edición de funciones</caption>
				<tr>
					<td width="45%"><c:if test="${sessionScope.NivelPermiso==2}"><input name="cmd1" type="button" value="Guardar" onClick="guardarObjetivo()" class="boton">&nbsp;<input name="cmd1" type="button" value="Cancelar" onClick="cancelarObjetivo()" class="boton"></c:if></td>
			 	</tr>	
		</table>	 	
		<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<td><span class="style2">*</span> Función:</td>
				<td>
					<textarea name="objDescripcion" cols="70" rows="5" onKeyDown="textCounter(this,1000,1000);" onKeyUp="textCounter(this,1000,1000);" ><c:out value="${sessionScope.objetivoVO.objDescripcion}"/></textarea>
				</td>
		 	</tr>
		</table>
	</form>
</body>
</html>