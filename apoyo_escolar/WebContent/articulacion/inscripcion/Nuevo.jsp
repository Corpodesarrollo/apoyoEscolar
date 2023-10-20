<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function asig(nom){         
    document.frmNuevo.carNombre.value=nom.options[nom.selectedIndex].text;
	}
</script>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:120px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/inscripcion/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:600px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/inscripcion/Lista.do"/>
			</div>
		</td></tr>
	</table>
</body>
</html>