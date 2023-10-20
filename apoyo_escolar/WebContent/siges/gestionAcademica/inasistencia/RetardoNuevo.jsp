<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/parametros.jsp"/>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.inasistencia.vo.ParamVO" scope="page"/>
<html>
<head>
		<script language="JavaScript">
			<!--
			var soloLectura='<c:out value="${sessionScope.NivelPermiso}"/>';
			var nav4=window.Event ? true : false;

			function cancelar(){
				parent.location.href='<c:url value="/bienvenida.jsp"/>';
			}
			
			function hacerValidaciones_frmNuevo(forma){
			}
			
			function guardar(){
				if(validarForma(document.frmNuevo)){
						document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_GUARDAR}"/>';
						document.frmNuevo.submit();
				}
			}

			function cambio(forma,indice){
				if(forma.falla[indice].checked==true){
					forma.falla[indice].value=forma.falla2[indice].value;
				}else{
					forma.falla[indice].value='-';
				}
			}
			
						
			//-->
		</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:130px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="/inasistencia/Filtro.do"/></div>
		</td></tr>
	</table>
	<FORM ACTION='<c:url value="/siges/gestionAcademica/inasistencia/Save.jsp"/>' METHOD="POST" name='frmNuevo' onSubmit="return validarForma(frmNuevo)">
		<input type="hidden" name="cmd">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_RETARDO}"/>'>
		<c:if test="${empty requestScope.lEstudiante}">
	    <table border="0" align="center" width="100%">
			<tr><th>No hay estudiantes a evaluar<th></tr>
		</table>
		</c:if>
		<c:if test="${!empty requestScope.lEstudiante}">
			<table style="display:" id='t1' name='t1' width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
			<caption>Registro de retardos</caption>
				<tr>
			    <td colspan="6">
						<input class='boton' id="cmd0" name="cmd0" type="button" value="Guardar" style="display:" onClick="javaScript:guardar()">
					</td>
				</tr>
			</table>
	    <table style="display:" id='t2' name='t2' width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>
					<th class="EncabezadoColumna" colspan='3'>Datos de estudiante</th>
					<th class="EncabezadoColumna" colspan='<c:out value="${sessionScope.filtroInasistencia.filTotalDias}"/>'>Mes<br></th>
				</tr>
				<tr>
					<th class="EncabezadoColumna" width='1%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='23%'>Apellidos</th>
					<th class="EncabezadoColumna" width='20%'>Nombres</th>
					<c:forEach begin="0" items="${lDias}" var="dia" >
						<th class="EncabezadoColumna" width='5%'><c:out value="${dia.nombre}"/><br>(<c:out value="${dia.codigo}"/>)</th>
					</c:forEach>
				</tr>
				<c:set var="indice" value="-1" scope="page"/>
				<c:forEach begin="0" items="${requestScope.lEstudiante}" var="estudiante" varStatus="st2">
				<tr>
					<td class='Fila<c:out value="${st2.count%2}"/>' align='center'><b><c:out value="${estudiante.evalConsecutivo}"/></b></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${estudiante.evalApellido}"/></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${estudiante.evalNombre}"/></td>
					<c:forEach begin="0" items="${estudiante.evalFallas}" var="falla" varStatus="st3"><c:set var="indice" value="${pageScope.indice+1}" scope="page"/>
						<th class='Fila<c:out value="${st2.count%2}"/>'>
						<input type="hidden" name="falla2" value='<c:out value="${estudiante.evalCodigo}"/>|<c:out value="${falla.faDia}"/>'>
						<input type="checkbox" onclick='javaScript:cambio(document.frmNuevo,<c:out value="${pageScope.indice}"/>);' name="falla" <c:if test="${falla.faChecked==1}">value='<c:out value="${estudiante.evalCodigo}"/>|<c:out value="${falla.faDia}"/>' CHECKED</c:if>></th>
					</c:forEach></tr>
					<c:if test="${st2.count%10==0}"><th class="EncabezadoColumna" colspan="3">&nbsp;</th><c:forEach begin="0" items="${lDias}" var="dia"><th class="EncabezadoColumna" width='5%'><c:out value="${dia.nombre}"/><br>(<c:out value="${dia.codigo}"/>)</th></c:forEach></c:if>
					</c:forEach>
					<tr><th class="EncabezadoColumna" colspan="3">&nbsp;</th><c:forEach begin="0" items="${lDias}" var="dia"><th class="EncabezadoColumna" width='5%'><c:out value="${dia.nombre}"/><br>(<c:out value="${dia.codigo}"/>)</th></c:forEach></tr>
			</table>
		</c:if>
		</form>
</body>
</html>		
