<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="documentoPEIVO" class="pei.registro.vo.DocumentoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function eliminar(inst,cod){
		if(confirm('¿Confirma la eliminación del documento?')){
			document.frmLista.docInstitucion.value=inst;
			document.frmLista.docCodigo.value=cod;
			document.frmLista.cmd.value=document.frmLista.ELIMINAR.value;
			document.frmLista.submit();
		}
	}

	function editar(inst,cod){
		document.frmLista.docInstitucion.value=inst;
		document.frmLista.docCodigo.value=cod;
		document.frmLista.cmd.value=document.frmLista.EDITAR.value;
		document.frmLista.submit();
	}
	
	function nuevo(){
		document.frmLista.cmd.value=document.frmLista.NUEVO.value;
		document.frmLista.submit();
	}
	
	function descargar(obj){
		document.frmDoc.action=document.frmLista.doc[obj].value;
		document.frmDoc.submit();
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmDoc" target="_blank"><input type="hidden" name="tipo" value="pdf"/></form>
	<form action='<c:url value="SaveDocumento.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DOCUMENTO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="docInstitucion" value='<c:out value="${sessionScope.documentoPEIVO.docInstitucion}"/>'>
		<input type="hidden" name="docCodigo" value='<c:out value="${sessionScope.documentoPEIVO.docCodigo}"/>'>
		<input type="hidden" name="doc"></input>
		<table border="0" align="center" width="95%">
		<caption>Estado PEI: <span class='estadoPEI'><c:out value="${sessionScope.identificacionPEIVO.idenEstadoNombre}"/></span></caption>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
				<caption>LISTADO DE DOCUMENTOS</caption>
				<tr>
					<th width='70' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Nombre</td>
					<td class="EncabezadoColumna" align="center">Fecha</td>
					<td class="EncabezadoColumna" align="center">Descripción</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaDocumento}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.docInstitucion}"/>,<c:out value="${lista.docCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.filtroRegistroPEIVO.filDisabled==false && sessionScope.identificacionPEIVO.idenDisabled==false}"><a href='javaScript:eliminar(<c:out value="${lista.docInstitucion}"/>,<c:out value="${lista.docCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
							<a href='javaScript:descargar(<c:out value="${st.count}"/>);'><img border='0' src='<c:url value="/etc/img/pdf.gif"/>' width='15' height='15'></a>
							<input type="hidden" name="doc" value='<c:url value="/${lista.docRuta}"/>'>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.docNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.docFecha}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.docDescripcion}"/></td>
					</tr>
				</c:forEach>
		</table>
	</form>
</body>
</html>