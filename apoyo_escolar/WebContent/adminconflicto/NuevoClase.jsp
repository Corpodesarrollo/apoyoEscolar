<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="adminconflicto" class="siges.adminconflicto.beans.AdminConflicto" scope="session"/>
<jsp:setProperty name="adminconflicto" property="*"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="adminconflicto/ControllerFiltro.do?tipo=10"/></div>
	</td></tr>
</table>
<script languaje="javaScript">
<!--
	var nav4=window.Event ? true : false;

	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function nuevo(){
		document.frmNuevo.tipo.value=10;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}
	
	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.tipo.value=11;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}
	
	function hacerValidaciones_frmNuevo(forma){
		//validarCampo(forma.idclase, "- Código de Clase", 1, 10)
		validarCampo(forma.valorclase, "- Nombre de Clase", 1, 250)
		//validarCampo(forma.ordencategoria, "- Orden de Clase", 1, 3)
	}
//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action="<c:url value="/adminconflicto/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="210">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<table align="center" width="100%">
				<tr>
				  <td width="45%" align="left">
						<input name="cmd1" class="boton" type="button" value="Nuevo" onClick="nuevo()">
					  <input name="cmd2" class="boton" type="button" value="Guardar" onClick="guardar()">
					</td>
				</tr>
  	  </table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>Nueva Clase de Conflicto Escolar</caption>
				<tr>
					<td><span class="style2">*</span> Nombre:</td>
					<td>
						<input type="text" size="80" name="valorclase" maxlength="250" <c:if test="${sessionScope.adminconflicto.estado==1}">value='<c:out value="${sessionScope.adminconflicto.valorclase}"/>'</c:if>>
					</td>
				</tr>
				<tr>
					<td><span class="style2"></span> Orden:</td>
					<td>
						<input type="text" name="ordenclase" onKeyPress='return acepteNumeros(event)' size="3" maxlength="3" <c:if test="${sessionScope.adminconflicto.estado==1}">value='<c:out value="${sessionScope.adminconflicto.ordenclase}"/>'</c:if>>
					</td>
				</tr>
  	  </table>
 	  </form>
  </font>