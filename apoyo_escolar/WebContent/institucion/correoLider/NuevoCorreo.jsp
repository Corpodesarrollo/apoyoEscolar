<jsp:useBean id="correoLiderVO" class="siges.institucion.correoLider.beans.CorreoLiderVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.institucion.correoLider.beans.ParamsVO" scope="page"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<c:import url="/parametros.jsp"/>
<script languaje='javaScript'>
		<!--
			var extensiones = new Array();
			extensiones[0]=".exe";
			extensiones[1]=".bat";

			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}

			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Todos--","-99");
			}
			
			function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			
			function validarFile(){
				if(validarForma(document.frmFile)){
			 		document.frmFile.target="frameFile";
			 		if(document.getElementById('msgFile')){
				 		document.getElementById('msgFile').style.display='';
			 		}
			 		if(document.getElementById('msgFile2')){
			 			document.getElementById('msgFile2').innerHTML='Espere mientras el archivo es adjuntado...';
			 		}
					document.frmNuevo.cmd1.disabled=true;
					document.frmNuevo.cmd12.disabled=true;
					document.frmFile.submit();
				}
			}
			
			function validadoFile(){
		 		if(document.getElementById('msgFile')){
			 		document.getElementById('msgFile').style.display='none';
		 		}
	 			guardar();
			}
			
			function hacerValidaciones_frmFile(forma){
        validarNoExtension(forma.archivo, "- Archivo no puede ser un ejecutable",extensiones)
			}
			
	function setFile(){
		document.frmNuevo.corrBandera.value='';
	}
	
	function guardar(){
		if(validarForma(document.frmNuevo)){
			var band=document.frmNuevo.corrBandera.value;
			var file=document.frmFile.archivo.value;
			if(file!=''){
				if(band=='-1'){//toca disparar el subido automático
					alert('No se pudo cargar el archivo adjunto, intente mas tarde el envio del correo');
					return false;
				}
				if(band!='1'){//toca disparar el subido automático
					validarFile();
					return false;
				}
			}	
			document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_GUARDAR}"/>';
			document.frmNuevo.submit();
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.corrAsunto, "- Asunto", 1,70)
		validarCampo(forma.corrMensaje, "- Mensaje", 1,2000)
	}

	function ajaxInstitucion(){
		borrar_combo(document.frmNuevo.corrInstitucion); 
		document.frmAjax.ajax[0].value=document.frmNuevo.corrLocalidad.options[document.frmNuevo.corrLocalidad.selectedIndex].value;
		document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_INSTITUCION}"/>';
 		document.frmAjax.target="frameAjax";
		document.frmAjax.submit();
	}
	function ajaxCargo(){
		borrar_combo(document.frmNuevo.corrCargo); 
		document.frmAjax.ajax[0].value=document.frmNuevo.corrTipoCargo.options[document.frmNuevo.corrTipoCargo.selectedIndex].value;
		document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_CARGO}"/>';
 		document.frmAjax.target="frameAjax";
		document.frmAjax.submit();
	}
			
	//-->
	</script>
<c:import url="/mensaje.jsp"/>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/institucion/correoLider/Save.jsp"/>'>
	<br>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Correo Lideres</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
				<input class='boton' name="cmd1" type="button" value="Aceptar" onClick="guardar(document.frmNuevo)">
				<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		  </td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
	<input type="hidden" name="cmd" value=''>
	<input type="hidden" name="corrAdjunto" value='<c:out value="${sessionScope.correoLiderVO.corrAdjunto}"/>'>
	<input type="hidden" name="corrNombreAdjunto" value='<c:out value="${sessionScope.correoLiderVO.corrNombreAdjunto}"/>'>
	<input type="hidden" name="corrBandera" value='<c:out value="${sessionScope.correoLiderVO.corrBandera}"/>'>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="98%" >
		<tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<img src='<c:url value="/etc/img/tabs/correoLider_1.gif"/>' border="0"  height="26" alt=''>
			</td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE width="98%" cellpadding="1" cellSpacing="0" border=0>
			<tr id="msgFile" style="display:none">
			<td colspan="2"><span id="msgFile2" class="style2">Espere mientras el archivo es adjuntado...</span></td>
			</tr>
			<tr>
				<td>Localidad:</td>
				<td>
					<select name="corrLocalidad" style='width:150px;' onChange='ajaxInstitucion()'>
						<option value="-99">--Todos--</option>
						<c:forEach begin="0" items="${requestScope.lLocalidad}" var="loc">
							<option value="<c:out value="${loc.codigo}"/>" <c:if test="${loc.codigo==sessionScope.correoLiderVO.corrLocalidad}">selected</c:if>><c:out value="${loc.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Colegio:</td>
				<td>
					<select name="corrInstitucion" style='width:300px;'>
						<option value="-99">--Todos--</option>
						<c:forEach begin="0" items="${requestScope.lInstitucion}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.correoLiderVO.corrInstitucion}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Tipo de cargo:</td>
				<td>
					<select name="corrTipoCargo" style='width:100px;' onChange='ajaxCargo()'>
						<option value='<c:out value="${paramsVO.TIPO_CARGO_TODOS}"/>'>--Todos--</option>
						<option value='<c:out value="${paramsVO.TIPO_CARGO_LIDER}"/>' <c:if test="${paramsVO.TIPO_CARGO_LIDER==sessionScope.correoLiderVO.corrTipoCargo}">selected</c:if>>Lideres</option>
						<option value='<c:out value="${paramsVO.TIPO_CARGO_GOBIERNO}"/>' <c:if test="${paramsVO.TIPO_CARGO_GOBIERNO==sessionScope.correoLiderVO.corrTipoCargo}">selected</c:if>>Gobierno escolar</option>
						<option value='<c:out value="${paramsVO.TIPO_CARGO_ASOCIACION}"/>' <c:if test="${paramsVO.TIPO_CARGO_ASOCIACION==sessionScope.correoLiderVO.corrTipoCargo}">selected</c:if>>Asociaciones</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Cargo:</td>
				<td>
					<select name="corrCargo" style='width:250px;'>
						<option value="-99">--Todos--</option>
						<c:forEach begin="0" items="${requestScope.lCargo}" var="cargo">
							<option value="<c:out value="${cargo.codigo}"/>" <c:if test="${cargo.codigo==sessionScope.correoLiderVO.corrCargo}">selected</c:if>><c:out value="${cargo.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		<tr>
			<td><span class="style2">*</span>Asunto:</td>
			<td>
				<input type="text" name="corrAsunto" size="50" maxlength="70" value='<c:out value="${sessionScope.correoLiderVO.corrAsunto}"/>'>
			</td>
		</tr>
		<tr>
			<td><span class="style2">*</span>Mensaje:</td>
			<td>
				<textarea name="corrMensaje" cols="80" rows="5"><c:out value="${sessionScope.correoLiderVO.corrAsunto}"/></textarea>
			</td>
		</tr>
		<tr><td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
	</TABLE>
</form>
<form method="post" enctype="multipart/form-data" name="frmFile" onSubmit="return validarForma(frmFile)" action='<c:url value="/institucion/correoLider/File.do"/>'>
	<input type='hidden' name='cmd' value='<c:out value="${paramsVO.CMD_FILE_ADJUNTO}"/>'>
	<TABLE width="100%" cellpadding="1" cellSpacing="0" border=0>
		<tr>
			<td>Archivo adjunto: </td>
			<td><input type='file' name='archivo' style='width:300px;' onchange="setFile()"></td>
		</tr>
		<tr><td>Archivo actual: </td>
		<td>[<span class="style3"><c:out value="${sessionScope.correoLiderVO.corrAdjunto}"/></span>]</td>
		</tr>		
		<tr><td style="display:none"><iframe name="frameFile" id="frameFile"></iframe></td></tr>
	</TABLE>	
</form>
<form method="post" name="frmAjax" action="<c:url value="/institucion/correoLider/Ajax.do"/>">
	<input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax">
	<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_CORREO}"/>'>
	<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
</form>
	