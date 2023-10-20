<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="documentoVO" class="participacion.instancia.vo.DocumentoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.instancia.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
<script languaje="javaScript">
  var extensiones = new Array();
  extensiones[0]=".zip";
  extensiones[1]=".doc";
  extensiones[2]=".xls";
  extensiones[3]=".pdf";
  extensiones[4]=".ppt";
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function guardarDocumento(){
		if(validarForma(document.frmNuevoDocumento)){
			if(document.frmNuevoDocumento.archivo) document.frmNuevoDocumento.encoding="multipart/form-data";
			document.frmNuevoDocumento.target='centro';
			document.frmNuevoDocumento.cmd.value=document.frmNuevoDocumento.GUARDAR.value;
			document.frmNuevoDocumento.submit();
			parent.close();
		}
	}

	function cancelarDocumento(){
		parent.close();
	}
	
	function hacerValidaciones_frmNuevoDocumento(forma){
		validarCampo(forma.docNombre, "- Nombre", 1, 60)
		validarFechaUnCampo(forma.docFecha, "- Fecha")
		if(forma.archivo){ 
			validarCampo(forma.archivo, "- Archivo", 1, 200)
			validarExtension(forma.archivo, "- Archivo (zip,doc,xls,pdf,ppt)", extensiones)
		}
		validarCampoOpcional(forma.docDescripcion, "- Descripción", 1, 200)
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmNuevoDocumento" action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_DOCUMENTO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="docInstancia" value='<c:out value="${sessionScope.instanciaVO.instCodigo}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / Edición de documento</caption>
				<tr>
					<td width="45%"><c:if test="${sessionScope.NivelPermiso==2}"><input name="cmd1" type="button" value="Guardar" onClick="guardarDocumento()" class="boton">&nbsp;<input name="cmd1" type="button" value="Cancelar" onClick="cancelarDocumento()" class="boton"></c:if></td>
			 	</tr>	
		</table>	 	
		<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<td><span class="style2">*</span> Nombre:</td>
				<td>
					<input type="text" name="docNombre" size="40" maxlength="250" value='<c:out value="${sessionScope.documentoVO.docNombre}"/>'>
				</td>
				<td><span class="style2">*</span> Fecha:</td>
				<td>
					<input type="text" name="docFecha" size="10" maxlength="10" value='<c:out value="${sessionScope.documentoVO.docFecha}"/>'>
					<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Fecha" id="imgfechaIni" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''"/>
				</td>
			</tr>
			<c:if test="${sessionScope.documentoVO.formaEstado!=1}">
			<tr>
				<td><span class="style2">*</span> Archivo:</td>
				<td colspan="3"><input type="file" name="archivo" style="width:200px;"></td>
			</tr>
			</c:if>
			<tr>
				<td>Descripción:</td>
				<td colspan="3"><textarea name="docDescripcion" cols="60" rows="2" onKeyDown="textCounter(this,200,200);" onKeyUp="textCounter(this,200,200);" ><c:out value="${sessionScope.documentoVO.docDescripcion}"/></textarea></td>
		 	</tr>
		</table>
	</form>
</body>
	<script type="text/javascript">
	    Calendar.setup({inputField:"docFecha",ifFormat:"%d/%m/%Y",button:"imgfechaIni",align:"Br"});
	</script>
</html>