<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="otroProyectoPEIVO" class="pei.registro.vo.ProyectoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<head>
<SCRIPT language=javascript src='<c:url value="/pei/registro/NuevoOtroProyecto.js"/>'></SCRIPT>
<script language="javaScript">var nav4=window.Event ? true : false;</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/pei/registro/SaveOtroProyecto.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_OTRO_PROYECTO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="proyInstitucion" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desInstitucion}"/>'>
		<input type="hidden" name="proyCodigo" value='<c:out value="${sessionScope.otroProyectoPEIVO.proyCodigo}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
				<caption>AGREGAR/EDITAR PROYECTO</caption>
				<tr>
					<td align="left" width="70%">
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.desarrolloCurricularPEIVO.desDisabled==false}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    				</c:if>
	    				<input name="cmd1" type="button" value="Cancelar" onClick="cerrarVentana()" class="boton">
			  		</td>
			 	</tr>	
		 </table>
		  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<td><span class="style2">*</span> Nombre:</td>
				<td>
					<input type="text" name="proyNombre" maxlength="120" size="40" value='<c:out value="${sessionScope.otroProyectoPEIVO.proyNombre}"/>'></input>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Alcance:</td>
				<td>
				<textarea name="proyAlcance" rows="2" cols="40" onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.otroProyectoPEIVO.proyAlcance}"/></textarea>				
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Dificultad:</td>
				<td>
				<textarea name="proyDificultad" rows="2" cols="40" onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.otroProyectoPEIVO.proyDificultad}"/></textarea>				
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Entidad:</td>
				<td>
					<input type="text" name="proyEntidad" maxlength="120" size="40" value='<c:out value="${sessionScope.otroProyectoPEIVO.proyEntidad}"/>'></input>
				</td>
			</tr>	
		</table>	
	</form>
</body>
</html>