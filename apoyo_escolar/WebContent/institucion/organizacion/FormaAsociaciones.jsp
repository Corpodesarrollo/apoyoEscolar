<%@ page contentType="text/html; charset=iso-8859-1" language="java"	errorPage=""%>
<%@ page import="siges.util.Recursos" %>
<jsp:useBean id="organizacionParams"	class="siges.institucion.organizacion.beans.Params" scope="page" />
<jsp:useBean id="asociacionIntegrantesVO"	class="siges.institucion.organizacion.beans.AsociacionIntegrantesVO"	scope="session" />
<%@include file="../../parametros.jsp"%>
<%pageContext.setAttribute("filtroDocumento",Recursos.recurso[Recursos.TIPODOCUMENTO]);
pageContext.setAttribute("filtroTipoCargo",Recursos.recurso[Recursos.TIPOCARGO]);%>
<html>
<head><title>Registro/Edición de integrante de asociación</title></head>
<style type="text/css">@import url(<c:url value="/etc/css/calendar-win2k-1.css"/>);</style>
<script type="text/javascript"	src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript"	src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript"	src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<c:if test="${sessionScope.NivelPermiso==1}">	<c:set var="permisoBoton" value="none" /></c:if>
<script languaje='javaScript'>			
			<!--
		  var nav4=window.Event ? true : false;
		  
		  function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		  }
			
			function cancelar(){
					parent.close();
			}
			
  		function hacerValidaciones_frmNuevo(forma){				
			validarLista(forma.asoIntTipodoc, "- Tipo de documento",1)
			validarLista(forma.asoIntCargo, "- Cargo",1)
			validarLista(forma.asoIntGenero, "- Género",1)
		    validarCampo(forma.asoIntNumdoc, "- Número de documento", 1, 15)
		    validarCampo(forma.asoIntNombre, "- Nombre", 1, 100)
		    validarCampo(forma.asoIntApellido, "- Apellido", 1,100)
  			validarCorreoOpcional(forma.asoIntCorreo, "- Correo electrónico")
		    
		}
			
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					if(document.frmNuevo.asoIntCodigo.value==''){
						alert('No puede ingresar integrantes hasta no registrar la asociación');
						return false;
					}
					document.frmNuevo.target='centro';
					document.frmNuevo.tipo2.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
					parent.close();
				}
			}
		//-->
	</script>
<%@include file="../../mensaje.jsp"%>
<form method="post" name="frmNuevo"	onSubmit=" return validarForma(frmNuevo)"	action='<c:url value="/institucion/organizacion/OrganizacionGuardar.jsp"/>'>
<input type="hidden" name="tipo2"	value='<c:out value="${pageScope.organizacionParams.FICHA_ASO2}"/>'> 
<input type="hidden" name="cmd" value="Cancelar">
<INPUT TYPE="hidden" NAME="height" VALUE='150'> 
<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
<input type="hidden" name="asoIntCodigo" value='<c:out value="${sessionScope.asociacionVO.asoCodigo}"/>'>
<input type="hidden" name="asoIntInst" value='<c:out value="${sessionScope.asociacionVO.asoInst}"/>'>
<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
	<tr>
		<td width="45%">
			<input name="cmd1" type="button" class='boton' value="Guardar" onClick="guardar(document.frmNuevo.tipo2.value)" style='display:<c:out value="${permisoBoton}"/>'> 
			<input name="cmd1" type="button" class='boton' value="Cancelar" onClick="cancelar()">
		</td>
	</tr>
</table>
<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
									  <td><span class="style2">*</span>Tipo de documento:</td>
										<td>
											<select name="asoIntTipodoc" style='width:150px;'>
                        <option value="-1">--seleccione uno--</option>
                        <c:forEach begin="0" items="${filtroDocumento}" var="fila">
                        <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.asociacionIntegrantesVO.asoIntTipodoc==fila[0]}">SELECTED</c:if>> <c:out value="${fila[1]}"/></option> </c:forEach>
                      </select>
										</td>
									  <td><span class="style2">*</span>N&uacute;mero de documento:</td>
										<td><input type="text" name="asoIntNumdoc"  maxlength="12" size="20" value='<c:out value="${sessionScope.asociacionIntegrantesVO.asoIntNumdoc}"/>'></td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Cargo:</td>
										<td>
										<select name="asoIntCargo" style='width:200px;' >
                      <option value="-1">--seleccione uno--</option>
                      <c:forEach begin="0" items="${filtroTipoCargo}" var="fila">
                      <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.asociacionIntegrantesVO.asoIntCargo==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option> </c:forEach>
                     </select>
										 </td>
									  <td><span class="style2">*</span> Género:</td>
										<td>
										<select name="asoIntGenero" style='width:40px;' >
                      <option value="-1">--</option>
                      <option value="1" <c:if test="${sessionScope.asociacionIntegrantesVO.asoIntGenero==1}">SELECTED</c:if>>M</option>
                      <option value="2" <c:if test="${sessionScope.asociacionIntegrantesVO.asoIntGenero==2}">SELECTED</c:if>>F</option>
                     </select>
										</td> 
									</tr>
									<tr>
										<td><span class="style2">*</span> Nombre:</td>
										<td>
											<input type="text" name="asoIntNombre" maxlength="100" size="35" value='<c:out value="${sessionScope.asociacionIntegrantesVO.asoIntNombre}"/>'>
										</td>
										<td><span class="style2">*</span> Apellido:</td>
										<td>
											<input type="text" name="asoIntApellido" maxlength="100" size="35" value='<c:out value="${sessionScope.asociacionIntegrantesVO.asoIntApellido}"/>'>
										</td>
									</tr>
									<tr>
										<td> Teléfono:</td>
										<td>
											<input type="text" name="asoIntTelefono" maxlength="20" size="10" value='<c:out value="${sessionScope.asociacionIntegrantesVO.asoIntTelefono}"/>'>
										</td>
										<td> Correo:</td>
										<td>
											<input type="text" name="asoIntCorreo" maxlength="100" size="35" value='<c:out value="${sessionScope.asociacionIntegrantesVO.asoIntCorreo}"/>'>
										</td>
									</tr>
							</TABLE>
<script type="text/javascript"><c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if></script>