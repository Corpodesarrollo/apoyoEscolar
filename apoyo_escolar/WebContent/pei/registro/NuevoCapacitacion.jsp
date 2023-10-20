<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="capacitacionPEIVO" class="pei.registro.vo.CapacitacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<head>
<SCRIPT language=javascript src='<c:url value="/pei/registro/NuevoCapacitacion.js"/>'></SCRIPT>
<script language="javaScript">var nav4=window.Event ? true : false;</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/pei/registro/SaveCapacitacion.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CAPACITACION}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="capInstitucion" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desInstitucion}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
				<caption>AGREGAR CAPACITACION</caption>
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
				<td><span class="style2">*</span> Universidad:</td>
				<td>
					<select name="capUniversidad">
						<option value='-99'>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaUniversidad}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.capacitacionPEIVO.capUniversidad}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Nombre:</td>
				<td>
					<input type="text" name="capNombreFormacion" maxlength="120" size="40" value='<c:out value="${sessionScope.capacitacionPEIVO.capNombreFormacion}"/>'></input>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Número de docentes:</td>
				<td>
					<input type="text" name="capDocentes" maxlength="6" size="5" value='<c:out value="${sessionScope.capacitacionPEIVO.capDocentes}"/>' onKeyPress='return acepteNumeros(event)'></input>
				</td>
			</tr>	
		</table>	
	</form>
</body>
</html>