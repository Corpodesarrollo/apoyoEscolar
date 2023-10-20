<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="primeraInfanciaPEIVO" class="pei.registro.vo.PrimeraInfanciaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<head>
<SCRIPT language=javascript src='<c:url value="/pei/registro/NuevoPrimeraInfancia.js"/>'></SCRIPT>
<script language="javaScript">var nav4=window.Event ? true : false;</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/pei/registro/SavePrimeraInfancia.jsp"></c:url>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_PRIMERA_INFANCIA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="priCodigoInstitucion" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desInstitucion}"/>'>
		<input type="hidden" name="priConsecutivo" value='<c:out value="${sessionScope.primeraInfanciaPEIVO.priConsecutivo}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
				<caption>AGREGAR REGISTRO DE CURSOS DE PRIMERA INFANCIA</caption>
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
				<td><span class="style2">*</span> Grado:</td>
				<td>
					<textarea name="priGrado" rows="1" cols="40" onKeyDown="textCounter(this,60,60);" onKeyUp="textCounter(this,60,60);"><c:out value="${sessionScope.primeraInfanciaPEIVO.priGrado}"/></textarea>				
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Cantidad de Cursos:</td>
				<td>
					<input type="text" name="priCursos" onKeyPress='return acepteNumeros(event)' maxlength="3" size="8" value='<c:out value="${sessionScope.primeraInfanciaPEIVO.priCursos}"/>'></input>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Cantidad de Estudiantes:</td>
				<td>
					<input type="text" name="priEstudiantes" onKeyPress='return acepteNumeros(event)' maxlength="11" size="8" value='<c:out value="${sessionScope.primeraInfanciaPEIVO.priEstudiantes}"/>'></input>
				</td>
			</tr>	
		</table>
	</form>
</body>
</html>