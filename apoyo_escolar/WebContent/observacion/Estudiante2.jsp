<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>
<c:import url="/observacion/Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_OBSERVACION_ESTUDIANTE}"/><c:param name="cmd" value="${paramsVO.CMD_NUEVO}"/></c:import>
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
	}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/observacion/SaveEval.jsp"/>' method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_OBSERVACION_ESTUDIANTE}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<caption>Listado de estudiantes</caption>
			<tr><td width="45%">
	        <input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
		  </td></tr>	
	  </table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			<tr>
				<th width="60%" class="EncabezadoColumna" colspan="3">Datos de Estudiante</th>
				<th width="40%" class="EncabezadoColumna" rowspan="2" >Observación</th>
			</tr>
			<tr>
				<th width="8%" class="EncabezadoColumna" colspan="1">&nbsp;</th>
				<th width="30%"  class="EncabezadoColumna" colspan="1">Apellidos</th>
				<th width="30%"  class="EncabezadoColumna" colspan="1">Nombres</th>
				
			</tr>
			<c:forEach begin="0" items="${requestScope.listaObservacionEstudiante}" var="obs"  varStatus="varStatu">
				<tr>
				    <td  class='Fila<c:out value="${varStatu.count%2}"/>'>&nbsp;<c:out value="${varStatu.count}"/></td>
					<td class='Fila<c:out value="${varStatu.count%2}"/>'><input type="hidden" name="obsEstudiante" value='<c:out value="${obs.codigo}"/>'><c:out value="${obs.apellido}"/></td>
					<td class='Fila<c:out value="${varStatu.count%2}"/>'><c:out value="${obs.nombre}"/></td>
					<td class='Fila<c:out value="${varStatu.count%2}"/>'><textarea name="obsObservacion" rows="2" cols="40" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);"><c:out value="${obs.observacion}"/></textarea></td>
				</tr>
			</c:forEach>
		</table>		
	</form>
</body>
</html>