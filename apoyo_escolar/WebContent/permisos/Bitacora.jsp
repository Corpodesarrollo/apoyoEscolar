<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bitacora" class="siges.permisos.vo.BitacoraVO" scope="session"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script languaje="javaScript">
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.usuario, "- Usuario", 1);
		validarCampo(forma.fechainicio, "- Fecha inicio", 1);
		validarCampo(forma.fechafin, "- Fecha final", 1);
	}
	
	function buscar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.submit();
		}
	}
	
	function download(name){
		document.listado.dir.value='/apoyo_escolar/private/resultados/bitacora/'+name;
		document.listado.tipo.value='zip';
		document.listado.action='/apoyo_escolar/private/resultados/bitacora/'+name;
		document.listado.submit();
	}
	
</script>
</head>
<body>
	<form  name='listado' method='post' target='_blank'>
		<input type='hidden' name='aut' value='1'>
		<input type='hidden' name='dir'>
		<input type='hidden' name='tipo'>
	</form>
	<FORM action='<c:url value="/permisos/Save.jsp"/>'METHOD="POST" name='frmNuevo'>
				<input type="hidden" name="cmd" value="Buscar">
				
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<caption>Filtro de busqueda</caption>
				<tr>
					<td colspan="6">
					<input id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
					</td>
				</tr>	
				<tr>
					<td width='20%' align="right"><span class="style2" >*</span>Usuario : </td>
					<td colspan="3" >
						<input maxlength="100" style='width:180px;' name="usuario" id="usuario" value="<c:out value="${sessionScope.bitacora.usuario}"/>"/>
					</td>
				</tr>				
				<tr>
					<td align="right"><span class="style2" >*</span>Fecha Inicio: </td>
					<td>
						<input type="text" name="fechainicio" id="fechainicio" readonly value='<c:out value="${sessionScope.bitacora.fechainicio}"/>'>
						<img 	
							src='<c:url value="/etc/img/calendario.gif"/>' 
							alt="Seleccione fecha" id="imgFechai"
							style="cursor: pointer"
							title="Date selector"
							onmouseover="this.style.background='red';"
							onmouseout="this.style.background=''" 
						/>
					</td>
				</tr>
				<tr>
					<td align="right"><span class="style2" >*</span>Fecha Final: </td>
					<td>
						<input type="text" name="fechafin" id="fechafin" readonly value='<c:out value="${sessionScope.bitacora.fechafin}"/>'>
						<img 	
							src='<c:url value="/etc/img/calendario.gif"/>' 
							alt="Seleccione fecha" id="imgFechaf"
							style="cursor: pointer"
							title="Date selector"
							onmouseover="this.style.background='red';"
							onmouseout="this.style.background=''" 
						/>
					</td>
				</tr>
		</table>
			<c:if test="${sessionScope.bitacora.generado == 1}">
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<caption>Descargar</caption>
				<th>
					<input name="cmd2" type="button" value="Descargar Reporte" onClick='download("<c:out value="${sessionScope.bitacora.fileName}"/>")' class="boton">
				</th>
		</table>
	</c:if>
	</form>

</body>
<script type="text/javascript">
			Calendar.setup({inputField:"fechainicio",ifFormat:"%d/%m/%Y",button:"imgFechai",align:"Br"});
			Calendar.setup({inputField:"fechafin",ifFormat:"%d/%m/%Y",button:"imgFechaf",align:"Br"});
		</script>
</html>
