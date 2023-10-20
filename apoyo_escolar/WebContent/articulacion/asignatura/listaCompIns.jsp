<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function hacerValidaciones_frmLista(forma){
		validarSeleccion(forma.id, "- Debe seleccionar un item");
	}
	
	function eliminaComp(n){
	
		if(document.frmComp.idCom){
			if(confirm('¿DESEA ELIMINAR ESTA MATERIA COMPLEMENTARIA?')){
				document.frmComp.idCom.value=n;
				document.frmComp.cmd.value=document.frmComp.ELIMINAR.value;
				document.frmComp.submit();
			}
		}
	}

	function refrescar(){
		document.frmComp.action='<c:url value="/articulacion/asignatura/ListaCompIns.do"/>';
		document.frmComp.submit();
	}
//-->

</script>

</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/asignatura/Nuevo.do"/>' method="post" name="frmComp" onSubmit="return validarForma(frmLista)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="idCom" value=''>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIM_COMPLE}"/>'>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		<tr><td><caption>LISTADO DE ASIGNATURAS COMPLEMENTARIAS</caption></td></tr>
		 	<c:if test="${empty requestScope.Complementarias}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ASIGNATURAS</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.Complementarias}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Código</td>
					<td class="EncabezadoColumna" align="center">Asignatura</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.Complementarias}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminaComp(<c:out value="${lista.asigCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asigComCodigo}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asigNombre}"/></td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td colspan="3" align="center">
					<c:if test="${sessionScope.asignaturaVO.formaEstado==1}">
						<input name="cmd1" type="button" value="Adicionar" onclick="javaScript:window.open('<c:url value="/articulacion/asignatura/Complementaria.do"/>','3','width=450,height=350,resizable=no,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes')" class="boton">
					</c:if>
				</td>				
			</tr>
		</table>
	</form>
</body>
</html>