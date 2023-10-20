<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/parametros.jsp" />
<jsp:useBean id="paramsVO" class="siges.importarFotografia.vo.ParamsVO"
	scope="page" />
<html>
<head>
<script language="JavaScript">
			<!--
    var extensiones = new Array();
    extensiones[0]=".zip";
    
			function guardar(){
				if(validarForma(document.frmNuevo)){
						document.frmNuevo.encoding="multipart/form-data";
						document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_GUARDAR}"/>';
						document.frmNuevo.submit();
				}
			}
			
		function hacerValidaciones_frmNuevo(forma){
			validarExtension(forma.filArchivo, "- Archivo (comprimido en formato zip)", extensiones)
		}

		function lanzar(tipo){
			document.frmNuevo.tipo.value=tipo;
			document.frmNuevo.cmd.value='';
			document.frmNuevo.submit();
		}

		//-->
		</script>
</head>
<c:import url="/mensaje.jsp" />
<body onLoad="mensaje(document.getElementById('msg'));">
<!-- TABS -->
<!-- /TABS -->
<FORM ACTION='<c:url value="/importarFotografia/Nuevo.do"/>'
	METHOD="POST" name='frmNuevo'><INPUT TYPE="hidden" NAME="tipo"
	VALUE='<c:out value="${paramsVO.FICHA_IMPORTAR_PERSONAL}"/>'> <input
	type="hidden" name="cmd">
<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
	<tr height="1">
		<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
		<td width="600" bgcolor="#FFFFFF">
			<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_IMPORTAR_ESTUDIANTE}"/>)"><img src='<c:url value="/etc/img/tabs/importar_estudiantes_0.gif"/>' alt="Estudiantes" height="26" border="0"></a>
			<img src='<c:url value="/etc/img/tabs/importar_personal_1.gif"/>' alt="-Personal-" width="84"  height="26" border="0">
		</td>
		</tr>
	</table>
<table width="95%" border="0" align="center" cellpadding="1"
	cellspacing="0">
	<caption>Importación de fotografias de personal</caption>
	<tr>
		<td><input class='boton' id="cmd0" name="cmd0" type="button"
			value="Guardar" onClick="javaScript:guardar()"></td>
	</tr>
</table>
<table width="95%" border="0" align="center" cellpadding="1"
	cellspacing="0">
	<tr>
		<td>Archivo:</td>
		<td><input type='file' name='filArchivo' style='width:400px;'></td>
	</tr>
</table>
</form>
<c:if test="${requestScope.resultado==1}">
	<table width="60%" border="1" align="center" cellpadding="1"
		cellspacing="0">
		<caption>Resultado de la importación</caption>
		<tr>
			<th>Item</th>
			<th>Cantidad</th>
		</tr>
		<c:forEach begin="0" items="${requestScope.resultadoImportar}"
			var="item">
			<tr>
				<td><c:out value="${item.nombre}" /></td>
				<td align="center"><c:out value="${item.codigo}" /></td>
			</tr>
		</c:forEach>
	</table>
</c:if>
</body>
</html>
