<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="adminconflicto" class="siges.adminconflicto.beans.AdminConflicto" scope="session"/>
<jsp:setProperty name="adminconflicto" property="*"/>
<jsp:useBean id="filtroconflicto" class="siges.adminconflicto.beans.FiltroConflicto" scope="session"/>
<jsp:setProperty name="filtroconflicto" property="*"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:220px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="adminconflicto/ControllerFiltro.do?tipo=20"/></div>
	</td></tr>
</table>
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

	function nuevo(){
		document.frmNuevo.tipo.value=20;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}
	
	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.tipo.value=21;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}
	
	function hacerValidaciones_frmNuevo(forma){
		//validarCampo(forma.idtipo, "- Código de Tipo", 1, 10)
		validarCampo(forma.valortipo, "- Nombre de Tipo", 1, 250)
		validarLista(forma.categoriatipo, "- Seleccione una categoria",1)
		validarLista(forma.clasetipo, "- Seleccione una clase",1)
		//validarCampo(forma.ordentipo, "- Orden de Tipo", 1, 3)
	}
	
	function mostrar(){
		if(document.frmNuevo.tipodesctipo.value==2)
			document.getElementById("desc").style.display="";
		else
			document.getElementById("desc").style.display="none";
	}
//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action="<c:url value="/adminconflicto/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="210">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="estadof" value="1">
			<input type="hidden" name="catnombre" value="<c:out value="${sessionScope.filtroconflicto.catnombre}"/>">
			<input type="hidden" name="clanombre" value="<c:out value="${sessionScope.filtroconflicto.clanombre}"/>">
			<input type="hidden" name="fcategoria" value="<c:out value="${sessionScope.filtroconflicto.fcategoria}"/>">
			<input type="hidden" name="fclase" value="<c:out value="${sessionScope.filtroconflicto.fclase}"/>">
			
			<table align="center" width="100%">
				<tr>
				  <td width="45%" align="left">
						<input name="cmd1" class="boton" type="button" value="Nuevo" onClick="nuevo()">
					  <input name="cmd2" class="boton" type="button" value="Guardar" onClick="guardar()">
					</td>
				</tr>
  	  </table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>Nueva Tipo de Conflicto Escolar</caption>
				<tr>
					<td><span class="style2">*</span> Nombre:</td>
					<td colspan="3">
						<input type="text" size="80" name="valortipo" maxlength="250" <c:if test="${sessionScope.adminconflicto.estado==1}">value='<c:out value="${sessionScope.adminconflicto.valortipo}"/>'</c:if>>
					</td>
				</tr>
				<tr>
					<td><span class="style2" >*</span>Categoria:</td>
			    <td colspan="3">
						<select name="categoriatipo">
							<option value='-1'>-- Seleccione Uno --</option>
							<c:forEach begin="0" items="${requestScope.tcategoria}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.adminconflicto.categoriatipo == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>
			    </td>
				</tr>
				<tr>
					<td><span class="style2" >*</span>Clase:</td>
			    <td colspan="3">
						<select name="clasetipo">
							<option value='-1'>-- Seleccione Uno --</option>
							<c:forEach begin="0" items="${requestScope.tclase}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.adminconflicto.clasetipo == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>
			    </td>
				</tr>
				<tr>
					<td>Tipo de Descripción</td>
					<td colspan="3">
						<select name="tipodesctipo" onchange="mostrar()">
							<option value='-1'>-- Seleccione Uno --</option>
							<option value='2' <c:if test="${sessionScope.adminconflicto.tipodesctipo == 2}">SELECTED</c:if>>Comentario</option>
							<option value='1' <c:if test="${sessionScope.adminconflicto.tipodesctipo == 1}">SELECTED</c:if>>Opción</option>
						</select>
					</td>
				</tr>
				<tr id="desc" name="desc" style="display:none">
					<td>Descripción</td>
					<td colspan="3"><textarea cols="100" rows="5" name="descripciontipo"><c:if test="${sessionScope.adminconflicto.estado==1}"><c:out value="${sessionScope.adminconflicto.descripciontipo}"/></c:if></textarea></td>
				</tr>
				<tr>
					<td><span class="style2"></span> Orden:</td>
					<td>
						<input type="text" name="ordentipo" onKeyPress='return acepteNumeros(event)' size="3" maxlength="3" <c:if test="${sessionScope.adminconflicto.estado==1}">value='<c:out value="${sessionScope.adminconflicto.ordentipo}"/>'</c:if>>
					</td>
				</tr>
  	  </table>
 	  </form>
  </font>
<script>mostrar()</script>