<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>
<c:import url="/observacion/Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_OBSERVACION_PERIODO}"/><c:param name="cmd" value="${paramsVO.CMD_NUEVO}"/></c:import>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		//validarLista(forma.obsPeriodo, "- Periodo", 1)
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/observacion/Save.jsp"/>' method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_OBSERVACION_PERIODO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<caption>OBSERVACIÓN POR PERIODO - REGISTRO</caption>
			<tr><td width="45%">
        <input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
		  </td></tr>	
	  </table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			<tr>
				<th class="EncabezadoColumna">Sede - Jornada</th>
				<th class="EncabezadoColumna">Observación</th>
			</tr>
			<c:forEach begin="0" items="${requestScope.listaObservacionPeriodo}" var="obs">
				<tr>
					<td><input type="hidden" name="obsJerarquia" value='<c:out value="${obs.codigo}"/>'><c:out value="${obs.nombre}"/></td>
					<td><textarea name="obsObservacion" rows="2" cols="60" onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);"><c:out value="${obs.observacion}"/></textarea></td>
				</tr>
			</c:forEach>
		</table>		
	</form>
</body>
</html>