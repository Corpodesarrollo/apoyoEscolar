<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/><jsp:setProperty name="nuevoBasica" property="*"/>
<jsp:useBean id="nuevoFamiliar" class="siges.estudiante.beans.Familiar" scope="session"/><jsp:setProperty name="nuevoFamiliar" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="academicaVO" class="siges.estudiante.beans.AcademicaVO" scope="session"/><jsp:setProperty name="academicaVO" property="*"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/>
<jsp:useBean id="nuevoAtencion" class="siges.estudiante.beans.AtencionEspecial" scope="session"/><jsp:setProperty name="nuevoAtencion" property="*"/>
<%pageContext.setAttribute("filtroGrado",Recursos.recurso[Recursos.GRADO]);%>
<%@include file="../parametros.jsp"%>
<style type="text/css">@import url(<c:url value="/etc/css/calendar-green.css"/>);</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<c:set var="tip" value="6" scope="page"/>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje='javaScript'>			
		<!--
		function cancelar(){
			if (confirm('¿DESEA CANCELAR LA INSERCIÓN DE UN NUEVO REGISTRO?')){
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
			validarCampo(forma.asiFecha, "- Fecha",1,10)
			validarLista(forma.asiMotivo, "- Motivo",1)
			validarSeleccion(forma.asiJustificada, "- Justificada")
		}
		
		function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action='<c:url value="/estudiante/ControllerNuevoEdit.do"/>';
				document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}
			}
			
			function editar(tipo,nombre){	
				document.frmConvivencia.tipo.value=tipo;
				document.frmConvivencia.cmd.value=nombre;
				if(document.frmConvivencia.cmd.value=='Eliminar'){
					if (isChecked(document.frmConvivencia.r)){
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
			-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<c:if test="${!empty requestScope.filtroAsistencia}">
 <center>
 <div style="width:98%;height:100px;overflow:auto;">
	<form method="post" name="frmConvivencia"  onSubmit=" return validarForma(frmConvivencia)" action='<c:url value="/estudiante/ControllerNuevoEdit.do"/>'>
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="">
	<INPUT TYPE="hidden" NAME="height" VALUE='130'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
		<table border="0" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="silver">
			<tr>
				<td colspan='4' align='left'>
					<input class='boton' name="cmd1" type="submit" value="Editar" onClick="return editar(document.frmNuevo.tipo.value,'Editar')">
					<input class='boton' name="cmd1" type="submit" value="Nuevo" onClick="return editar(document.frmNuevo.tipo.value,'Nuevo')" style='display:<c:out value="${permisoBoton}"/>'>
					<input class='boton' name="cmd1" type="submit" value="Eliminar" onClick="return editar(document.frmNuevo.tipo.value,'Eliminar')" style='display:<c:out value="${permisoBoton}"/>'>
				</td>
			</tr>
		</table>	
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="silver">
			<tr bgcolor="#CCCCCC"> 
				<td class="EncabezadoColumna" width='1%'>&nbsp;</td>				
				<th class="EncabezadoColumna" width='10%'>Periodo</th>
				<th class="EncabezadoColumna" width='10%'>Fecha</th>
				<th class="EncabezadoColumna" width='1%'>Justificada</b></th>
				<th class="EncabezadoColumna" >Motivo</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroAsistencia}" var="fila" varStatus="st">
			<tr>				
				<td class='Fila<c:out value="${st.count%2}"/>' width='1%'><input type='radio' name='r' value='<c:out value="${fila[0]}"/>'></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></td>
			</tr>
			</c:forEach>
		</table>
	</center>
	</form>
	</div>
	</c:if>
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/estudiante/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>
			  <td width="45%" bgcolor="#FFFFFF">
	          <input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)" style='display:<c:out value="${permisoBoton}"/>'>
			</tr>		
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoBasica2.estcodigo}"/>">
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="height" VALUE='130'>
	<INPUT TYPE="hidden" NAME="asiCodigo" VALUE='<c:out value="${sessionScope.asistenciaVO.asiCodigo}"/>'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%">
    <tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
      <td rowspan="2" width="504" bgcolor="#FFFFFF"><a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_familiar_0.gif" alt="Ficha Familiar"  height="26" border="0"></a><a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a><a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a><a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_academica_0.gif" alt="Academica"  height="26" border="0"></a><img src="../etc/img/tabs/asistencia_1.gif" alt="Asistencia"  height="26" border="0"><a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/atencion_especial_0.gif" alt="Atención Especial"  height="26" border="0"></a><a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/fotografia_0.gif" alt="Subir Foto Estudiante"  height="26" border="0"></a></td>
			<td align='CENTER'><b><c:out value="${sessionScope.nuevoBasica.estnombre1}"/> <c:out value="${sessionScope.nuevoBasica.estnombre2}"/> <c:out value="${sessionScope.nuevoBasica.estapellido1}"/></b></td>
		</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
									  <td><span class="style2">*</span> Motivo:</th>
										<td>
											<select name="asiMotivo" style='width:250px;'>
													<option value="-1">--seleccione uno--</option>
													<c:forEach begin="0" items="${requestScope.listaMotivoAusencia}" var="fila">
														<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.asistenciaVO.asiMotivo==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
													</c:forEach>													
											</select>
										</td>
										<td><span class="style2">*</span> Periodo:</th>
										<td>
											<select name="asiPeriodo" style='width:100px;'>
													<option value="-1">--Seleccione uno--</option>
													<c:forEach begin="0" items="${requestScope.lPeriodo}" var="fila">
														<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.asistenciaVO.asiPeriodo==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
													</c:forEach>													
											</select>
										</td>
                  </tr>
									<tr>
									  <td>Observaciones especiales:</th>
										<td colspan=3><textarea name="asiObservacion" cols="70" rows="2"><c:out value="${sessionScope.asistenciaVO.asiObservacion}"/></textarea></td>
                  </tr>
									<tr>
									  <td><span class="style2">*</span> Justificada:</th>
									  <td>
											<input type="radio" name="asiJustificada" value="1" <c:if test="${sessionScope.asistenciaVO.asiJustificada==1}">CHECKED</c:if>>Si&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="asiJustificada" value="0" <c:if test="${sessionScope.asistenciaVO.asiJustificada==0}">CHECKED</c:if>>No
									  </td>									  
									  <td><span class="style2">*</span> Fecha:</th>
									  <td><input type="text" name="asiFecha"  maxlength="10" size="12" value='<c:out value="${sessionScope.asistenciaVO.asiFecha}"/>' readOnly></td>
									</tr>
									<tr>
									  <td></th>
									  <td></th>
									  <td colspan=2><div style="float: left; margin-rigth: 1em; margin-bottom: 1em;" id="calendar-container"></div></th>
									</tr>
	</TABLE>
</form>
<script>
<c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
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
      document.frmNuevo.asiFecha.value=d_+"/"+m_+"/"+y_;
    }
  };

  Calendar.setup(
    {
      flat         : "calendar-container", // ID of the parent element
      flatCallback : dateChanged           // our callback function
    }
  );
</script>