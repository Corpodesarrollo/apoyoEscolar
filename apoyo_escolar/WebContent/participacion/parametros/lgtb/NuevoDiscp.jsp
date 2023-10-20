<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="lgtbVO" class="participacion.parametros.vo.LgtbVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.parametros.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/participacion/parametros/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
	
		if(validarForma(document.frmNuevo)){
		document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
		
			document.frmNuevo.submit();
		}
	}

	function hacerValidaciones_frmNuevo(forma){
		validarEntero(forma.codigo, "- Código ", 1, 999)
		validarCampo(forma.nombre , "- Nombre ", 1, 150)
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/participacion/parametros/Lista.do?tipo=3"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/participacion/parametros/Save.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_LGTB}"/>'>
		
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>

		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Información de LGTB</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        			<c:if test="${sessionScope.lgtbVO.formaEstado==1}">
		        				<input name="cmd1" type="button" value="Actualizar" onClick="guardar()" class="boton">
		        			</c:if>
	    					<c:if test="${sessionScope.lgtbVO.formaEstado!=1}">
	    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">

	    					</c:if>
	    				</c:if>
			  		</td>
			 	</tr>	
		  </table>
		  <table width="100%" border="0" cellspacing="2" cellpadding="2">
				<tr>
					<td>
						<span class="style2">*</span><b>Código</b>
					</td>
					<td>
						<input type="text" name="codigo" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.lgtbVO.codigo}"/>'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2">*</span><b>Nombre</b>
					</td>
					<td>
						<input type="text" name="nombre" maxlength="60" size="50"  value='<c:out value="${sessionScope.lgtbVO.nombre}"/>'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2"></span><b>Descripción</b>
					</td>
					<td>
						<textarea name="descripcion" cols="47" rows="2" onKeyDown="textCounter(this.form.texto,4000,4000);" onKeyUp="textCounter(this.form.texto,4000,4000);"><c:out value="${sessionScope.lgtbVO.descripcion}"/></textarea>
					</td>
				</tr>
			
			</table>
		</form>
</body>
</html>