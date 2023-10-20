<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<%@page import="siges.util.Recursos"%>
<%pageContext.setAttribute("filtroLoc",Recursos.recurso[Recursos.LOCALIDAD]);%>
<%pageContext.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);%>
<jsp:useBean id="filtroreportes" class="siges.conflictoReportes.beans.ConflictoFiltro" scope="session"/>
<jsp:setProperty name="filtroreportes" property="*"/>
<script languaje="javaScript">
<!--
	var nav4=window.Event ? true : false;

	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.nomlocal1.value=document.frmFiltro.local1.options[document.frmFiltro.local1.selectedIndex].text;
			document.frmFiltro.nomperiodo1.value=document.frmFiltro.periodo1.options[document.frmFiltro.periodo1.selectedIndex].text;
			document.frmFiltro.tipo.value=6;
			document.frmFiltro.cmd.value="Guardar";
			document.frmFiltro.submit();
		}	
	}
	
	function lanzar(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.action='<c:url value="/conflictoReportes/ControllerFiltro.do"/>';
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.periodo1, "- Seleccione un periodo",1)
	}

//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<form method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)" action="<c:url value="/conflictoReportes/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="nomlocal1" value="">
			<input type="hidden" name="nomperiodo1" value="">
			<!--<input type="hidden" name="periodo1" value="0">-->
			<input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
			
			<table>
				<tr>
				  <td width="45%" align="left">
					  <input name="cmd2" class="boton" type="button" value="Generar Reporte" onClick="buscar()">
				  </td>
				</tr>
				<tr>
					<td rowspan="2" width="780">
						<!--<a href="javaScript:lanzar(0)"><img src='<c:url value="/etc/img/tabs/reporte1_0.gif"/>' alt="Colegios que no han cerrado Conflicto Escolar" height="26" border="0"></a>
						<a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/reporte2_0.gif"/>' alt='Grupos que no han cerrado Conflicto Escolar' height="26" border="0"></a> -->
						<a href="javaScript:lanzar(2)"><img src='<c:url value="/etc/img/tabs/reporte3_0.gif"/>' alt='Reporte general de Conflicto Escolar' height="26" border="0"></a>
						<img src='<c:url value="/etc/img/tabs/reporte1_1.gif"/>' alt='Colegios que han cerrado Conflicto Escolar' height="26" border="0">
					</td>
				</tr>
			</table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>Filtro para reporte</caption>
				<tr>
				<td>
				
				<c:if test="${sessionScope.login.nivel != 2 and sessionScope.login.nivel != 4 and sessionScope.login.nivel != 6}">
					<span class="style2"></span>&nbsp;Localidad</td>
					<td colspan="3">
					
					<select name="local1" class="entrada1" id="local1" onChange="">
							<option value="-1" selected>TODOS</option>
							<c:forEach begin="0" items="${filtroLoc}" var="fila">
								<option value="<c:out value="${fila[0]}"/>"
									<c:if test="${sessionScope.filtroreportes.local1 == fila[0]}">SELECTED</c:if>>
									<c:out value="${fila[1]}" />
								</option>
							</c:forEach>
					</select>
				</c:if>
				<c:if test="${sessionScope.login.nivel == 2 or sessionScope.login.nivel == 4 or sessionScope.login.nivel == 6}">
					<select name="local1" class="entrada1" id="local1" style='width:133px;'>
			        	<option value="<c:out value='${sessionScope.login.locId}'/>" selected><c:out value="${sessionScope.login.loc}"/></option>
			        </select>
				</c:if>
				</td>
				<td><span class="style2">*</span>Periodo:</td>
				<td>
					<select name="periodo1">
							<option value="-1">--seleccione uno--</option>
							<c:if test="${sessionScope.login.vigencia_actual != null and sessionScope.login.vigencia_actual < 2013}">
								<c:forEach begin="0" items="${filtroPeriodo}" var="fila">
									<option value="<c:out value="${fila[0]}"/>"
										<c:if test="${sessionScope.nuevoConflicto.ceperiodo== fila[0]}">SELECTED</c:if>>
										<c:out value="${fila[1]}" />
								</c:forEach>
							</c:if>
							<c:if test="${sessionScope.login.vigencia_actual != null and sessionScope.login.vigencia_actual >= 2013}">
								<c:forEach begin="0" items="${sessionScope.listaPeriodos}" var="fila">
									<option value="<c:out value="${fila[0]}"/>"	
										<c:if test="${sessionScope.nuevoConflicto.ceperiodo==fila[0]}">SELECTED</c:if>> 
										<c:out value="${fila[1]}"/>
								</c:forEach>
							</c:if>
					</select>
				</td>
			</tr>
		</form>
				<c:if test="${!empty requestScope.reporte1 && requestScope.reporte1!='--' && requestScope.reporte1!='0'}">
					<tr>
						<td colspan="6" align='center' valign="top">
							<form name='frmGenerar' action='<c:url value="/${requestScope.reporte1}"/>' method='post' target='_blank'>
								<input type='hidden' name='dir' value='<c:out value="${requestScope.reporte1}"/>'>
								<input type='hidden' name='tipo' value='xls'>
								<input type='hidden' name='accion' value='0'>
								<input type='hidden' name='aut' value='1'>El reporte fue generado satisfactoriamente.<br>Pulse en el Icono para descargar el archivo.<br>
							</form>
							>><a href='javaScript:document.frmGenerar.submit();'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A><<
						</td>
					</tr>
				</c:if>
				<c:if test="${requestScope.reporte1=='--'}">
					<tr>
						<td colspan="6" valign="top" align='center'>No se pudo generar el reporte.<br></td>
					</tr>
				</c:if>
  	  </table>
  </font>
  <script>
  </script>