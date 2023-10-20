<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="ajustePEIVO" class="pei.registro.vo.AjusteVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<head>
<SCRIPT language=javascript src='<c:url value="/pei/registro/NuevoAjuste.js"/>'></SCRIPT>
<script language="javaScript">var nav4=window.Event ? true : false;</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/pei/registro/SaveAjuste.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_AJUSTES}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="ajuCodigoInstitucion" value='<c:out value="${sessionScope.identificacionPEIVO.idenInstitucion}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
				<caption>AGREGAR LOS AJUSTES HECHOS AL PEI</caption>
				<tr>
					<td align="left" width="70%">
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.identificacionPEIVO.idenDisabled==false}">
    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    				</c:if>
	    				<input name="cmd1" type="button" value="Cancelar" onClick="cerrarVentana()" class="boton">
			  		</td>
			 	</tr>	
		 </table>
		  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<td><span class="style2">*</span>Año:</td>
				<td>
					<select name="ajuVigencia">			
						<option value="-1">--Seleccione--</option>
						<c:forEach var="i" begin="2005" end="${sessionScope.vigenciaActual}" varStatus ="status">
							<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span>Ajuste:</td>
				<td>
					<textarea name="ajuAjuste" rows="2" cols="40" onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);"><c:out value="${sessionScope.ajustePEIVO.ajuAjuste}"/></textarea>				
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>No. de resolución:</td>
				<td>
					<input type="text" name="ajuResolucion" maxlength="10" size="20" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.ajustePEIVO.ajuResolucion}"/>' onKeyPress='return acepteNumeros(event)'></input>			
				</td>
			</tr>		
		</table>
	</form>
</body>
</html>