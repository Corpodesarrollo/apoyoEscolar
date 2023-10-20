<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	function hacerValidaciones_frmLista(forma){
		validarSeleccion(forma.id, "- Debe seleccionar un item");
	}
	
	function eliminaCoreq(n){
	
		if(document.frmCoreq.idReq){
			if(confirm('¿DESEA ELIMINAR ESTE COREQUISITO?')){
				document.frmCoreq.idReq.value=n;
				document.frmCoreq.cmd.value=document.frmCoreq.ELIMINAR.value;
				document.frmCoreq.submit();
			}
		}
	}

	function refrescar(){
		document.frmCoreq.action='<c:url value="/articulacion/asignatura/ListaCoreqIns.do"/>';
		document.frmCoreq.submit();
	}
//-->

</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/asignatura/Nuevo.do"/>' method="post" name="frmCoreq" onSubmit="return validarForma(frmLista)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="idReq" value=''>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIM_COREQ}"/>'>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
			<caption>LISTADO DE ASIGNATURAS COREQUISITO</caption>
		 	<c:if test="${empty requestScope.listaCoRequisitos}">
			<tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ASIGNATURAS</th></tr>
			</c:if>
			<c:if test="${!empty requestScope.listaCoRequisitos}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Código</td>
					<td class="EncabezadoColumna" align="center">Nombre de la Asignatura</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaCoRequisitos}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'><c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminaCoreq(<c:out value="${lista.asigCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if></th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asigReCodigo}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asigNombre}"/></td>
					</tr>
				</c:forEach>
			</c:if>
			<tr><td colspan="3" align="center"><c:if test="${sessionScope.asignaturaVO.formaEstado==1}"><input name="cmd1" type="button" value="Adicionar" onclick="javaScript:window.open('<c:url value="/articulacion/asignatura/CoRequisito.do"/>','3','width=450,height=350,resizable=yes,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes')" class="boton"></c:if></td></tr>
		</table>
	</form>
</body>
</html>