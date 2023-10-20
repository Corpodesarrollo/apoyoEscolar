<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="observacionPeriodoVO" class="siges.observacion.vo.ObservacionPeriodoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	function lanzarServicio(n){
		document.frmLista.action='<c:url value="/grupoPeriodo/ControllerAbrirEdit.do"/>';  	
		document.frmLista.tipo.value=n;
		document.frmLista.target="";
		document.frmLista.submit();
	}
	function lanzar(n){
		document.frmLista.action='<c:url value="/observacion/Nuevo.do"/>';  	
		document.frmLista.tipo.value=n;
		document.frmLista.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
		document.frmLista.target="";
		document.frmLista.submit();
	}

	function buscar(){
		if(validarForma(document.frmLista)){
			document.frmLista.cmd.value=document.frmLista.BUSCAR.value;
			document.frmLista.submit();
		}
	}
	function hacerValidaciones_frmLista(forma){
		validarLista(forma.obsPeriodo, "- Periodo", 1)
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/observacion/Save.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_OBSERVACION_PERIODO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="obsInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="obsMetodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<caption>OBSERVACIÓN POR PERIODO - FILTRO DE BÚSQUEDA</caption>
			<tr><td width="45%">
        <input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
		  </td></tr>	
	  </table>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="520">
					<a href="javaScript:lanzarServicio(1)"><img src="../etc/img/tabs/abrir_grupo_0.gif" height="26" border="0"></a>
					<c:if test="${sessionScope.login.perfil!=421}"><a href="javaScript:lanzarServicio(2)"><img src="../etc/img/tabs/cerrar_periodo_0.gif" alt="Cerrar Periodo"  height="26" border="0"></a></c:if>
					<img src='<c:url value="/etc/img/tabs/observacion_periodo_1.gif"/>' alt="Observacion" height="26" border="0">
					<a href='javaScript:lanzar(<c:out value="${paramsVO.FICHA_OBSERVACION_GRUPO}"/>)'><img src="../etc/img/tabs/observacion_grupo_0.gif" alt="Observacion grupo"  height="26" border="0"></a>
				</td>
				</tr>
		</table>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<tr>
			<td><span class="style2">*</span>Periodo:</td>
			<td>
					<select name="obsPeriodo" style='width:100px;'>
						<option value="-99">-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaPeriodo}" var="per">
							<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==sessionScope.observacionPeriodoVO.obsPeriodo}">selected</c:if>><c:out value="${per.nombre}"/></option>
						</c:forEach>
					</select>
			</td>
			</tr>
		</table>		
	</form>
</body>
</html>