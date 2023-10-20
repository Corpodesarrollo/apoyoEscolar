<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="ajustePEIVO" class="pei.registro.vo.AjusteVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function eliminar(inst,cod){
		if(confirm('¿Confirma la eliminación del Ajuste?')){
			document.frmFiltro.ajuCodigoInstitucion.value=inst;
			document.frmFiltro.ajuResolucion.value=cod;
			document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
			document.frmFiltro.target='';
			document.frmFiltro.submit();
		}
	}

	function agregar(){
		var frmFiltroNuevo = document.frmFiltro;
		document.frmFiltro.cmd.value=document.frmFiltro.NUEVO.value;
		
		remote = window.open("","1","width=450,height=200,resizable=yes,toolbar=no,directories=no,menubar=no,status=no");
		remote.moveTo(250,350);
		document.frmFiltro.target='1';
		document.frmFiltro.submit();
		if(remote.opener == null) remote.opener = window;
		remote.opener.name = "frameAjuste";
		remote.focus();
	}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/pei/registro/SaveAjuste.jsp"/>' method="post" id="frmFiltro" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_AJUSTES}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="ajuCodigoInstitucion" value='<c:out value="${sessionScope.identificacionPEIVO.idenInstitucion}"/>'>
		<input type="hidden" name="ajuResolucion" value='<c:out value="${sessionScope.ajustePEIVO.ajuResolucion}"/>'>
		<table border="0" align="center" width="95%">
			<tr>
				<td>
				<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.identificacionPEIVO.idenDisabled==false}">
					<input name="cmd1" type="button" value="Agregar" onClick="agregar()" class="boton">
   				</c:if>
		  		</td>
		 	</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
				<tr>
					<th width='20' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Año</td>
					<td class="EncabezadoColumna" align="center">Temas de Modificacion</td>
					<td class="EncabezadoColumna" align="center">No de Resolucion</td>
					<td class="EncabezadoColumna" align="center">Fecha de modificacion</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaAjuste}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>' title='Eliminar'>
							<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.identificacionPEIVO.idenDisabled==false}">
								<a href='javaScript:eliminar(<c:out value="${lista.ajuCodigoInstitucion}"/>,<c:out value="${lista.ajuResolucion}"/>);'>
									<img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'>
								</a>
							</c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<c:out value="${lista.ajuVigencia}"/>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<textarea rows="2" cols="40" disabled><c:out value="${lista.ajuAjuste}"/></textarea>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<c:out value="${lista.ajuResolucion}"/>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<c:out value="${lista.ajuFecha}"/>
						</td>
					</tr>
				</c:forEach>
		</table>
	</form>
</body>
</html>