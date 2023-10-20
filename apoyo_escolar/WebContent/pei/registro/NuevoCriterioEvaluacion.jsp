<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="criterioEvaluacionPEIVO" class="pei.registro.vo.CriterioEvaluacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<head>
<SCRIPT language=javascript src='<c:url value="/pei/registro/NuevoCriterioEvaluacion.js"/>'></SCRIPT>
<script language="javaScript">var nav4=window.Event ? true : false;</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/pei/registro/SaveCriterioEvaluacion.jsp"></c:url>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CRITERIO_EVALUACION}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="criCodigoInstitucion" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desInstitucion}"/>'>
		<input type="hidden" name="criConsecutivo" value='<c:out value="${sessionScope.criterioEvaluacionPEIVO.criConsecutivo}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
				<caption>AGREGAR SÍNTESIS DEL ESTADO DEL PROCESO DE DESARROLLO CURRICULAR</caption>
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
				<td><span class="style2">*</span> Alcance:</td>
				<td>
				<textarea name="criAlcance" rows="2" cols="40" onKeyDown="textCounter(this,300,300);" onKeyUp="textCounter(this,300,300);"><c:out value="${sessionScope.criterioEvaluacionPEIVO.criAlcance}"/></textarea>				
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Dificultad:</td>
				<td>
				<textarea name="criDificultad" rows="2" cols="40" onKeyDown="textCounter(this,300,300);" onKeyUp="textCounter(this,300,300);"><c:out value="${sessionScope.criterioEvaluacionPEIVO.criDificultad}"/></textarea>				
				</td>
			</tr>	
		</table>
	</form>
</body>
</html>