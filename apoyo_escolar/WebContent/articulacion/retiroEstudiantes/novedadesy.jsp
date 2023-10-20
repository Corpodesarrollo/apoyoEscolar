<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.retiroEstudiantes.vo.ParamsVO" scope="page"/>
<jsp:useBean id="popUpMotivoVO" class="articulacion.retiroEstudiantes.vo.PopUpMotivoVO" scope="session"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script languaje="javaScript">
	function enviar(){
		/*
		copiarChecks(document.frmNovedades);
		document.frmNovedades.cmd.value=document.frmNovedades.GUARDAR.value;
		document.frmNovedades.submit();
		opener.refrescar();
		*/
		if (validarForma(document.frmNovedades)){
		   if(confirm('¿Esta seguro que desea cancelar esta Asignatura?')){
		var indice=document.frmNovedades.indice.value;
		alert(opener.document.forms['frmNuevo'].elements['x'][indice].value);
		opener.document.forms['frmNuevo'].elements['mot'][indice].value=document.frmNovedades.motivo.options[document.frmNovedades.motivo.selectedIndex].value;
		opener.document.forms['frmNuevo'].elements['desc'][indice].value=document.frmNovedades.noveDescripcion.value;
		opener.document.forms['frmNuevo'].elements['band'][indice].value=1;
		alert(opener.document.forms['frmNuevo'].elements['mot'][indice].value);
		alert(opener.document.forms['frmNuevo'].elements['desc'][indice].value);
		parent.close();
		}}
	}
	

	function cancelar(){
	
		var indice=document.frmNovedades.indice.value;
		alert(indice);
		opener.document.forms['frmNuevo'].elements['band'][indice].value=0;
		parent.close();
	}

	function textCounter(field, countfield, maxlimit) {
		if (field.value.length > maxlimit) // if too long...trim it!
		field.value = field.value.substring(0, maxlimit);
		// otherwise, update 'characters left' counter
		else 
		countfield.value = maxlimit - field.value.length;
    }
    
    function hacerValidaciones_frmNovedades(forma)
	{
	  
		validarLista(forma.motivo, "- Seleccione un Motivo", 1)
		  
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmNovedades" onSubmit="return validarForma(frmNovedades)" action='<c:url value="/articulacion/retiroEstudiantes/Save3.jsp"/>'>
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="indice" value='<c:out value="${sessionScope.popUpMotivoVO.indice}"/>'>
		<input type="hidden" name="codigoAsignatura" value='<c:out value="${sessionScope.popUpMotivoVO.codigoAsignatura}"/>'>
		<input type="hidden" name="codigoGrupo" value='<c:out value="${sessionScope.popUpMotivoVO.codigoGrupo}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>REGISTRO MOTIVO RETIRO ASIGNATURA</caption>
			
					<tr>
						
						 <td>
							<span class="style2">*</span><b>Motivo Retiro</b>
						</td>
						<td>
							<select name="motivo" <c:if test="${sessionScope.popUpMotivoVO.formaEstado==1}">disabled</c:if>>
								<option value="0">--Seleccione uno--</option>
								<c:forEach begin="0" items="${sessionScope.listaMotivoVO}" var="motivo">
									<option value="<c:out value="${motivo.codigo}"/>"<c:if test="${motivo.codigo==sessionScope.popUpMotivoVO.codigoMotivo}">selected</c:if>><c:out value="${motivo.motivo}"/></option>
								</c:forEach>
							</select>
					    </td>
					   </tr>
					    <tr>
					    <td>
							<span class="style2">*</span><b>Descripción</b>
						</td>
						<td>
						
						<textarea name="noveDescripcion" cols="50" rows="3" <c:if test="${sessionScope.popUpMotivoVO.formaEstado==1}">disabled</c:if>
						onKeyDown="textCounter(this.form.noveDescripcion,199,199);" onKeyUp="textCounter(this.form.noveDescripcion,199,199);" ><c:out value="${sessionScope.popUpMotivoVO.descripcion}"/></textarea>
					</td>
											
					</tr>
				<tr>
				<td>Fecha:</td>
				<td><c:out value="${sessionScope.popUpMotivoVO.fecha}"/></td>
				</tr>
				<c:if test="${sessionScope.popUpMotivoVO.formaEstado!=1}">
					<tr>
						<td colspan="3" align="center">
							<input name="cmd1" type="button" value="Aceptar" onClick="enviar()" class="boton">
							<input name="cmd1" type="button" value="Cancelar" onClick="cancelar()" class="boton">
						</td>
					</tr>
				</c:if>		
		</table>
	</form>
</body>
</html>