<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/><jsp:setProperty name="nuevoBasica" property="*"/>
<jsp:useBean id="nuevoFamiliar" class="siges.estudiante.beans.Familiar" scope="session"/><jsp:setProperty name="nuevoFamiliar" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/>
<jsp:useBean id="nuevoAtencion" class="siges.estudiante.beans.AtencionEspecial" scope="session"/>
<%pageContext.setAttribute("filtroTipoConvivencia",Recursos.recurso[Recursos.CONVIVENCIA]);%>
<%pageContext.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);%>
<%@include file="../parametros.jsp"%><c:set var="tip" value="7" scope="page"/>
<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
<style type="text/css">@import url(<c:url value="/etc/css/calendar-green.css"/>);</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script languaje=' javaScript'>			
<!--
	function cancelar(){
		if(confirm('¿DESEA CANCELAR LA INSERCIÓN DE UN NUEVO REGISTRO?')){
			document.frmNuevo.cmd.value="Cancelar";
      document.frmNuevo.submit();
		}
	}

	function inhabilitarFormulario(){
		for(var i=0;i<document.frmNuevo.elements.length;i++){
			if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
				document.frmNuevo.elements[i].disabled=true;
		}	
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.atestipo, "- Tipo de atención epecial",1)
		validarLista(forma.atesperiodo, "- Periodo",1)
		validarCampo(forma.atesasunto, "- Asunto",1,70)
		validarCampo(forma.atesdescripcion, "- Descripción",1,250)
		validarCampo(forma.atesfecha, "- Fecha en que ocurrio",6,10)
		valFecha(forma.atesfecha,"- Formato de Fecha (dd/mm/aaaa)")
		validarCampo(forma.atesmostrar, "- Mostrar en el Boletín",1,70)
	}					

	function lanzar(tipo){				
		document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.action='<c:url value="/estudiante/ControllerNuevoEdit.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(tipo){
		if(validarForma(document.frmNuevo)){
			if(document.frmNuevo.atesmostrar[0].checked || document.frmNuevo.atesmostrar[1].checked){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value="Guardar";
				document.frmNuevo.submit();
			}
			else{
				alert("- Mostar en boletin")
			}
		}
	}
	
	


	function editar(tipo,nombre){	
		document.frmNuevoAtencion.tipo.value=tipo;
		document.frmNuevoAtencion.cmd.value=nombre;
		if(document.frmNuevoAtencion.cmd.value=='Eliminar'){
			if (isChecked(document.frmNuevoAtencion.r)){
				if(!confirm("Realmente desea eliminarlo?"))
					return false;
			}
		}
		return true;	
	}

	function hacerValidaciones_frmConvivencia(forma){
		if(forma.cmd.value!='Nuevo')
			validarSeleccion(forma.r, "- Debe seleccionar un item")
	}

	function ponerSeleccionado(){
		if(document.frm){
			if(document.frm.id){
				if(document.frm.id.length){
					for(var i=0;i<document.frm.id.length;i++){
						if(document.frm.id[i].value==document.frmNuevo.SELECCIONADO.value){
							document.frm.id[i].checked=true;
							document.frm.id[i].focus();
							break;
						}
					}
				}else{
					if(document.frm.id.value==document.frmNuevo.SELECCIONADO.value){
						document.frm.id.checked=true;;
						document.frm.id.focus();
					}
				}
			}
		}
	}
-->
</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<c:if test="${!empty sessionScope.filtroAtencion}">
 <center>
 <div style="width:100%;height:100px;overflow:auto;">
 
	<form method="post" name="frmNuevoAtencion"  onSubmit="return validarForma(frmNuevoAtencion)" action='<c:url value="/estudiante/ControllerNuevoEdit.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
		<input type="hidden" name="cmd" value=""><INPUT TYPE="hidden" NAME="height" VALUE='130'>
		<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'><br>
		<table border="0" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#F0F0F0">
			<tr bgcolor="#CCCCCC"> 
				<th colspan="4">Seguimiento a atención especial de <c:out value="${sessionScope.nuevoBasica.estnombre1}"/> <c:out value="${sessionScope.nuevoBasica.estnombre2}"/> <c:out value="${sessionScope.nuevoBasica.estapellido1}"/></td>
			</tr>
			<tr>
							
				
				<td colspan='4' align='left'>
					<input class='boton' name="cmd1" type="submit" value="<c:if test="${empty sessionScope.ModoConsulta}">Editar</c:if><c:if test="${!empty sessionScope.ModoConsulta}">Ver</c:if>" onClick="return editar(document.frmNuevo.tipo.value,'Editar')">
					<c:if test="${empty sessionScope.ModoConsulta}"><input class='boton' name="cmd1" type="submit" value="Nuevo" onClick="return editar(document.frmNuevo.tipo.value,'Nuevo')" style='display:<c:out value="${permisoBoton}"/>'></c:if>
					<c:if test="${empty sessionScope.ModoConsulta}"><input class='boton' name="cmd1" type="submit" value="Eliminar" onClick="return editar(document.frmNuevoAtencion.tipo.value,'Eliminar')" style='display:<c:out value="${permisoBoton}"/>'></c:if>
				</td>
				
				
				
			</tr>
			<tr bgcolor="#CCCCCC"> 
				<td width='1%'></td>				
				<th>Tipo de atención especial</th>				
				<th>Asunto</b></th>				
				<th>Fecha</b></th>
			</tr>
			<c:forEach begin="0" items="${sessionScope.filtroAtencion}" var="fila">
			<tr>				
				<td width='1%'>
					<input type='radio' name='r' value='<c:out value="${fila[0]}"/>'>
				</td>
				<td><c:out value="${fila[1]}"/></td>
				<td><c:out value="${fila[2]}"/></td>
				<td><c:out value="${fila[3]}"/></td>
			</tr>
			</c:forEach>
		</table>
	</center>
	</form>
	</div>
	</c:if>
		
		
	   <form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/estudiante/NuevoGuardar.jsp"/>">
		
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>
			  <td width="45%" bgcolor="#FFFFFF">
          <input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)" style='display:<c:out value="${permisoBoton}"/>'>
           
          
          </td>
			</tr>		
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="SELECCIONADO" value="<c:out value="${sessionScope.nuevoBasica.estcodigo}"/>">
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='130'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
    <tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
      <td rowspan="2" width="504" bgcolor="#FFFFFF">
			<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>
			<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_familiar_0.gif" alt="Ficha Familiar"  height="26" border="0"></a>
			<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>
			<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>
			<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_academica_0.gif" alt="Inf. Académica"  height="26" border="0"></a>
			<img src="../etc/img/tabs/atencion_especial_1.gif" alt="Atención Especial"  height="26" border="0">
			<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/fotografia_0.gif" alt="Subir Foto Estudiante"  height="26" border="0"></a></td>
			<td align='CENTER'><b><c:out value="${sessionScope.nuevoBasica.estnombre1}"/> <c:out value="${sessionScope.nuevoBasica.estnombre2}"/> <c:out value="${sessionScope.nuevoBasica.estapellido1}"/></b></td>
			</tr>
		</table>
		<!--FIN DE FICHAS A MOSTRAR AL USUARIO-->
								<TABLE width="100%" cellpadding="3" cellSpacing="0" border="0">
									<tr><td><hr></td><td><font style='FONT-SIZE:11'>Seguimiento a atención especial</font></td></tr>
								    <tr>
										<td><span class="style2">*</span> Tipo de atención especial:</td>
										<td colspan="2">
											<select name="atestipo" style='width:200px;'>
												<option value="-1">--seleccione uno--</option>
													<c:forEach begin="0" items="${filtroTipoConvivencia}" var="fila">
														<option value="<c:out value="${fila[0]}"/>"  <c:if test="${sessionScope.nuevoAtencion.atestipo == fila[0]}">SELECTED</c:if>>
															<c:out value="${fila[1]}"/></option>
													</c:forEach>													
											</select>
										</td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Periodo:</td>
										<td colspan="2">
											<select name="atesperiodo" style='width:100px;'>
												<option value="-1">--Seleccione uno--</option>
													<c:forEach begin="0" items="${filtroPeriodo}" var="fila">
														<option value="<c:out value="${fila[0]}"/>"  <c:if test="${sessionScope.nuevoAtencion.atesperiodo == fila[0]}">SELECTED</c:if>>
															<c:out value="${fila[1]}"/></option>
													</c:forEach>
											</select>
										</td>
										</tr>
									<tr>
									  <td><span class="style2">*</span> Asunto:</td>
									  <td colspan="2"><input type="text" name="atesasunto"  maxlength="70" size="70" value='<c:out value="${sessionScope.nuevoAtencion.atesasunto}"/>'></td>
                  </tr>
									<tr>
										<td><span class="style2">*</span> Descripción del Seguimiento:</td>
										<td colspan="2"><textarea name="atesdescripcion" cols="70" rows="5"><c:out value="${sessionScope.nuevoAtencion.atesdescripcion}"/></textarea></td>
									</tr>
									
									<tr>
									  <td><span class="style2">*</span> Mostrar en el Boletín:</td>
									  <td colspan="2">
										<input type="radio" name="atesmostrar" value='1'>No&nbsp;&nbsp;&nbsp;<input type="radio" name="atesmostrar" value='2'>Si</td>
									</tr>
									 
									<tr>
									  <td valign="top"><span class="style2">*</span> Fecha de Seguimiento (dd/mm/yyyy):</td>
									  <td valign="top"><input type="text" name="atesfecha"  maxlength="10" size="10" value='<c:out value="${sessionScope.nuevoAtencion.atesfecha}"/>' readonly="true">
										<div style="float: left; margin-rigth: 1em; margin-bottom: 1em;" id="calendar-container"></div></td>
                  </tr>
							  </TABLE>
<!--//////////////////////////////-->
	</form>
<c:remove var="filtroTipoConvivencia"/>
<script>
	<c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
	<c:if test="${sessionScope.nuevoAtencion.atesmostrar==1}">document.frmNuevo.atesmostrar[0].checked=true;</c:if>
	<c:if test="${sessionScope.nuevoAtencion.atesmostrar==2}">document.frmNuevo.atesmostrar[1].checked=true;</c:if>
	ponerSeleccionado();
</script>
<script type="text/javascript">
  function dateChanged(calendar) {
    if (calendar.dateClicked) {
      var y_ = calendar.date.getFullYear();
      var m = parseInt(calendar.date.getMonth())+1;     // integer, 0..11
      var d = calendar.date.getDate();      // integer, 1..31
      if(parseInt(m)<10){
      	var m_="0"+m;
     	}else{
     		var m_=m;
     	}
      if(parseInt(d)<10){
      	var d_="0"+d;
      }else{
      	var d_=d;
      }
      document.frmNuevo.atesfecha.value=d_+"/"+m_+"/"+y_;
    }
  };

  Calendar.setup(
    {
      flat         : "calendar-container", // ID of the parent element
      flatCallback : dateChanged           // our callback function
    }
  );
</script>