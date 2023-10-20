<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>

<!--	VERSION		FECHA			AUTOR			DESCRIPCION
			1.0		10/05/2021		JORGE CAMACHO	Código espagueti
-->

<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/><jsp:setProperty name="nuevoBasica" property="*"/>
<jsp:useBean id="nuevoFamiliar" class="siges.estudiante.beans.Familiar" scope="session"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>

<%pageContext.setAttribute("filtroDocumento",Recursos.recurso[Recursos.TIPODOCUMENTO]);%>

<%@include file="../parametros.jsp"%><c:set var="tip" value="2" scope="page"/>

<script languaje='javaScript'>
	<!--			
	var nav4=window.Event ? true : false;
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function inhabilitarFormulario(){
		for(var i=0;i<document.frmNuevo.elements.length;i++){
			if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
				document.frmNuevo.elements[i].disabled=true;
		}
	}
	
	function cancelar(){
		if (confirm('¿DESEA CANCELAR LA INSERCIÓN DE UN NUEVO REGISTRO?')){
			document.frmNuevo.cmd.value="Cancelar";
       		document.frmNuevo.submit();
		}		
	}		
	
	function hacerValidaciones_frmNuevo(forma){
		if(forma.madre[0].checked){
			validarCampo(forma.famnommadre, "- Nombre de la madre", 1, 60)
			validarLista(forma.famtipdocmadre, "- Tipo de documento de identificación", 1)
			validarCampo(forma.famnumdocmadre, "- Número de identificación", 1, 12)
			validarCampo(forma.famocumadre, "- Ocupación de la madre", 1, 60)
			validarCampo(forma.famdirmadre, "- Dirección de la madre", 1, 50)
			validarCampo(forma.famtelmadre, "- Teléfono de la madre", 1, 50)
		}
		
		if(forma.padre[0].checked){
			validarCampo(forma.famnompadre, "- Nombre del padre", 1, 60)
			validarLista(forma.famtipdocpadre, "- Tipo de documento de identificación", 1)
			validarCampo(forma.famnumdocpadre, "- Número de identificación", 1, 12)
			validarCampo(forma.famocupadre, "- Ocupación del padre", 1, 60)
			validarCampo(forma.famdirpadre, "- Dirección del padre", 1, 50)
			validarCampo(forma.famtelpadre, "- Teléfono del padre", 1, 50)
		}
		
		validarCampo(forma.famnomacudi, "- Nombre del acudiente", 1, 60)
		validarLista(forma.famtipdocacudi, "- Tipo de documento de identificación", 1)
		validarCampo(forma.famnumdocacudi, "- Número de identificación", 1, 12)
		validarCampo(forma.famocuacudi, "- Ocupación del acudiente", 1, 60)
		validarCampo(forma.famdiracudi, "- Dirección del acudiente", 1, 50)
		validarCampo(forma.famtelacudi, "- Teléfono del acudiente", 1, 50)
		validarEntero(forma.famnumhermanos, "- Número de hermanos (entre 0 y 99)", 0,99)
	}
	
	function lanzar(tipo){				
		document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.action="<c:url value="/estudiante/ControllerNuevoEdit.do"/>";
		document.frmNuevo.submit();
	}
	
	function guardar(tipo){
		if(validarForma(frmNuevo)){
			document.frmNuevo.tipo.value=tipo;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}
	}
	
	function inhabilitar(tipo){
		if(tipo==1){
			document.frmNuevo.famnommadre.disabled= true;
			document.frmNuevo.famnommadre.value=' ' ;
			document.frmNuevo.famtipdocmadre.selectedIndex=0;
			document.frmNuevo.famtipdocmadre.disabled= true;
			document.frmNuevo.famnumdocmadre.disabled= true;
			document.frmNuevo.famnumdocmadre.value= ' ';
			document.frmNuevo.famocumadre.disabled= true;
			document.frmNuevo.famocumadre.value= ' ';
			document.frmNuevo.famdirmadre.disabled= true;
			document.frmNuevo.famdirmadre.value= ' ';
			document.frmNuevo.famtelmadre.disabled= true;
			document.frmNuevo.famtelmadre.value= ' ';
		}
		
		if(tipo==2){
			document.frmNuevo.famnompadre.disabled= true;
			document.frmNuevo.famnompadre.value=' ' ;
			document.frmNuevo.famtipdocpadre.selectedIndex=0;
			document.frmNuevo.famtipdocpadre.disabled= true;
			document.frmNuevo.famnumdocpadre.disabled= true;
			document.frmNuevo.famnumdocpadre.value= ' ';
			document.frmNuevo.famocupadre.disabled= true;
			document.frmNuevo.famocupadre.value= ' ';
			document.frmNuevo.famdirpadre.disabled= true;
			document.frmNuevo.famdirpadre.value= ' ';
			document.frmNuevo.famtelpadre.disabled= true;
			document.frmNuevo.famtelpadre.value= ' ';			
		}		
	}
	
	function habilitar(tipo){
		if(tipo==1){
			document.frmNuevo.famnommadre.disabled= false;
			document.frmNuevo.famtipdocmadre.disabled= false;
			document.frmNuevo.famnumdocmadre.disabled= false;
			document.frmNuevo.famocumadre.disabled= false;
			document.frmNuevo.famdirmadre.disabled= false;
			document.frmNuevo.famtelmadre.disabled= false;
		}
		
		if(tipo==2){
			document.frmNuevo.famnompadre.disabled= false;
			document.frmNuevo.famtipdocpadre.disabled= false;
			document.frmNuevo.famnumdocpadre.disabled= false;
			document.frmNuevo.famocupadre.disabled= false;
			document.frmNuevo.famdirpadre.disabled= false;
			document.frmNuevo.famtelpadre.disabled= false;
		}
	}
	
	function ponerSeleccionado(){
	
		if(document.frm){
		
			if(document.frm.id){
			
				if(document.frm.id.length){
					for(var i=0;i<document.frm.id.length;i++){
						if(document.frm.id[i].value==document.frmNuevo.ELHPSELECCIONADO.value){
							document.frm.id[i].checked=true;
							document.frm.id[i].focus();
							break;
						}
					}
				}else{
					if(document.frm.id.value==document.frmNuevo.ELHPSELECCIONADO.value){
						document.frm.id.checked=true;;
						document.frm.id.focus();
					}
				}
			}
		}
	}
	//-->
</script>

<%@include file="../mensaje.jsp"%>	

<font size="1">

	<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/estudiante/NuevoGuardar.jsp"/>">
		
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>
	  			<td width="45%" bgcolor="#FFFFFF">
					<!--<input class="boton" name="delete" type="reset" value="Borrar">-->
					<input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)">
	  			</td>
			</tr>
		</table>
		
		<!--//////////////////-->		
		<!-- FICHAS A MOSTRAR AL USUARIO -->	
		<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoBasica.estcodigo}"/>">	
		<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
		<input type="hidden" name="cmd" value="Cancelar">
		<input type="hidden" name="height" value='130'>
		<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
		
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
 				<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
   				<td rowspan="2" width="504" bgcolor="#FFFFFF">
					<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Info. B&aacute;sica"  height="26" border="0"></a>
					<img src="../etc/img/tabs/inf_familiar_1.gif" alt="info. Familiar"  height="26" border="0">
					<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>
					<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>
					<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_academica_0.gif" alt="Inf. Académica"  height="26" border="0"></a>
					<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/atencion_especial_0.gif" alt="Atención Especial"  height="26" border="0"></a>
					<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/fotografia_0.gif" alt="Subir Foto Estudiante"  height="26" border="0"></a>
				</td>
				<td align='CENTER'>
					<b><c:out value="${sessionScope.nuevoBasica.estnombre1}"/> <c:out value="${sessionScope.nuevoBasica.estnombre2}"/> <c:out value="${sessionScope.nuevoBasica.estapellido1}"/>
					</b>
				</td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		
		<TABLE width="100%" cellpadding="3" cellSpacing="0">
			<tr>
				<td colspan='4'>
					Posee información de la madre
					<input type="radio" name="madre" onClick="habilitar(1)" value="1" <c:if test="${sessionScope.nuevoFamiliar.madre== '1'}">CHECKED</c:if>>Sí
					<input type="radio" name="madre" onClick="inhabilitar(1)" value="2" <c:if test="${sessionScope.nuevoFamiliar.madre== '2'}">CHECKED</c:if>>No
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Nombre de la madre:</td>
				<td>
					<input name="famnommadre" type="text" size="20" maxlength="60" value='<c:out value="${sessionScope.nuevoFamiliar.famnommadre}"/>'>
				</td>
				<td><span class="style2">*</span> Tipo de documento:</td>
				<td>
                 		<select name="famtipdocmadre">
                 			<option value="-1">-- Seleccione uno --</option>
						<c:forEach begin="0" items="${filtroDocumento}" var="fila">
							<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoFamiliar.famtipdocmadre== fila[0]}">SELECTED</c:if>>
							<c:out value="${fila[1]}"/></option>
						</c:forEach>													
                  	</select>
                 	</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Número de identificación:</td>
				<td>
					<input name="famnumdocmadre" type="text" maxlength="12" value='<c:out value="${sessionScope.nuevoFamiliar.famnumdocmadre}"/>' onKeyPress='return acepteNumeros(event)'>
				</td>
				<td><span class="style2">*</span> Ocupación:</td>
				<td>											
					<input name="famocumadre" type="text" maxlength="60" value='<c:out value="${sessionScope.nuevoFamiliar.famocumadre}"/>'>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Dirección:</td>
				<td>
                 		<input name="famdirmadre" type="text" maxlength="50" value='<c:out value="${sessionScope.nuevoFamiliar.famdirmadre}"/>' >
                 	</td>
           		<td><span class="style2">*</span> Teléfono:</td>
				<td>
                 		<input name="famtelmadre" type="text" maxlength="50" value='<c:out value="${sessionScope.nuevoFamiliar.famtelmadre}"/>' >
                 	</td>
			</tr>
			<tr>
				<td><br><br></td>
			</tr>
			<tr>
				<td colspan='4'>
					Posee información del padre
					<input type="radio" name="padre" onClick="habilitar(2)" value="1" <c:if test="${sessionScope.nuevoFamiliar.padre== '1'}">CHECKED</c:if>>Sí
					<input type="radio" name="padre" onClick="inhabilitar(2)" value="2" <c:if test="${sessionScope.nuevoFamiliar.padre== '2'}">CHECKED</c:if>>No
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Nombre del padre:</td>
				<td>
					<input name="famnompadre" type="text" size="20" maxlength="60" value='<c:out value="${sessionScope.nuevoFamiliar.famnompadre}"/>'>
				</td>
				<td><span class="style2">*</span> Tipo de documento:</td>
				<td>
                 		<select name="famtipdocpadre">
                 			<option value="-1">-- Seleccione uno --</option>
						<c:forEach begin="0" items="${filtroDocumento}" var="fila">
							<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoFamiliar.famtipdocpadre== fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}"/>
							</option>
						</c:forEach>													
                 		</select>
                 	</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Número de identificación:</td>
				<td>
					<input name="famnumdocpadre" type="text" maxlength="12" value='<c:out value="${sessionScope.nuevoFamiliar.famnumdocpadre}"/>' onKeyPress='return acepteNumeros(event)'>
				</td>
				<td><span class="style2">*</span> Ocupación:</td>
				<td>
					<input name="famocupadre" type="text" maxlength="60" value='<c:out value="${sessionScope.nuevoFamiliar.famocupadre}"/>'>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Dirección:</td>
				<td>
                 		<input name="famdirpadre" type="text" maxlength="50" value='<c:out value="${sessionScope.nuevoFamiliar.famdirpadre}"/>' >
                 	</td>
           		<td><span class="style2">*</span> Teléfono:</td>
				<td>
                 		<input name="famtelpadre" type="text" maxlength="50" value='<c:out value="${sessionScope.nuevoFamiliar.famtelpadre}"/>' >
                 	</td>
			</tr>
			<tr>
				<td><br><br></td>
			</tr>
			<tr>
				<td colspan=4>Datos del Acudiente</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Nombre del acudiente:</td>
				<td>
					<input name="famnomacudi" type="text" size="20" maxlength="60" value='<c:out value="${sessionScope.nuevoFamiliar.famnomacudi}"/>'>
				</td>
				<td><span class="style2">*</span> Tipo de documento:</td>
				<td>
                   	<select name="famtipdocacudi">
                       	<option value="-1">-- Seleccione uno --</option>
						<c:forEach begin="0" items="${filtroDocumento}" var="fila">
							<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoFamiliar.famtipdocacudi== fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}"/>
							</option>
						</c:forEach>													
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Número de identificación:</td>
				<td>
					<input name="famnumdocacudi" type="text" maxlength="12" value='<c:out value="${sessionScope.nuevoFamiliar.famnumdocacudi}"/>' onKeyPress='return acepteNumeros(event)'>
				</td>
				<td><span class="style2">*</span> Ocupación:</td>
				<td>
					<input name="famocuacudi" type="text" maxlength="60" value='<c:out value="${sessionScope.nuevoFamiliar.famocuacudi}"/>'>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Dirección:</td>
				<td>
                   	<input name="famdiracudi" type="text" maxlength="50" value='<c:out value="${sessionScope.nuevoFamiliar.famdiracudi}"/>' >
				</td>
           		<td><span class="style2">*</span> Teléfono:</td>
				<td>
                   	<input name="famtelacudi" type="text" maxlength="50" value='<c:out value="${sessionScope.nuevoFamiliar.famtelacudi}"/>' >
				</td>
			</tr>
			<tr>
				<td><br><br></td>
			</tr>
			<tr>
				<td colspan=4>Otros Datos</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Número de hermanos:</td>
				<td>
					<input name="famnumhermanos" type="text" maxlength="2" value='<c:out value="${sessionScope.nuevoFamiliar.famnumhermanos}"/>' onKeyPress='return acepteNumeros(event)'>
				</td>
           		<td>Nombre de los hijos de la familia:</td>
				<td>
               		<textarea name="famnomhijos"><c:out value="${sessionScope.nuevoFamiliar.famnomhijos}"/></textarea>
				</td>
			</tr>
			<tr>
				<td><br><br></td>
			</tr>
		</TABLE>
	</font>		
	
<!--//////////////////////////////-->
</form>

<c:remove var="filtroDocumento"/>
<script>
	if(!document.frmNuevo.madre[0].checked && !document.frmNuevo.madre[1].checked)
		document.frmNuevo.madre[0].checked=true;
	if(document.frmNuevo.madre[0].checked)
		habilitar(1);
	if(document.frmNuevo.madre[1].checked)
		inhabilitar(1);				
	if(!document.frmNuevo.padre[0].checked && !document.frmNuevo.padre[1].checked)
		document.frmNuevo.padre[0].checked=true;
	if(document.frmNuevo.padre[0].checked)
		habilitar(2);
	if(document.frmNuevo.padre[1].checked)
		inhabilitar(2);
	<c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
	//<c:if test="${sessionScope.nuevoFamiliar.estado=='1'}">inhabilitarFormulario();</c:if>	
	ponerSeleccionado();
</script>
