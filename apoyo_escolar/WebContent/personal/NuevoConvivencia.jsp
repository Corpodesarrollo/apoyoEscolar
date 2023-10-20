<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/><jsp:setProperty name="nuevoPersonal" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.personal.beans.ParamsVO" scope="page"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="laboralVO" class="siges.personal.beans.LaboralVO" scope="session"/><jsp:setProperty name="laboralVO" property="*"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="formacionVO" class="siges.personal.beans.FormacionVO" scope="session"/><jsp:setProperty name="formacionVO" property="*"/>
<%pageContext.setAttribute("filtroTipoConvivencia",Recursos.recurso[Recursos.CONVIVENCIA]);%>
<%@include file="../parametros.jsp"%><c:set var="tip" value="2" scope="page"/>
<style type="text/css">@import url(<c:url value="/etc/css/calendar-green.css"/>);</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje=' javaScript'>
		<!--
			var fichaPersonal=1;
			var fichaSede=1;
			var fichaConvivencia=1;
			var fichaSalud=1;
			var fichaLaboral=1;
			var fichaAcademica=1;
			var fichaAsistencia=1;
			var fichaCarga=1;
			var fichaFoto=1;
			
		function cancelar(){
			//if(confirm('¿DESEA CANCELAR LA INSERCIÓN DE UN NUEVO REGISTRO?')){
 				document.frmNuevo.cmd.value="Cancelar";
				document.frmNuevo.ext2.value="";
        document.frmNuevo.submit();
			//}
		}
			function hacerValidaciones_frmNuevo(forma){								
				validarLista(forma.contipo, "- Tipo de convivencia",1)
				validarCampo(forma.condescripcion, "- Descripción (máximo 100 caracteres)",1,100)
				validarCampo(forma.conacuerdos , "- Acuerdo (máximo 100 caracteres)",0,100)
				validarCampo(forma.conseguimiento , "- Seguimiento (máximo 100 caracteres)",0,100)
				   
				   
				validarCampo(forma.confecha, "- Fecha en que ocurrio",6,10)
				valFecha(forma.confecha,"- Formato de Fecha (dd/mm/aaaa)")
			}
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
			function lanzar(tipo){				
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.action="<c:url value="/personal/ControllerNuevoEdit.do"/>";
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
				document.frm.tipo.value=tipo;
				document.frm.cmd.value=nombre;
				if(document.frm.cmd.value=='Eliminar'){
					if (isChecked(document.frm.r)){
						if(!confirm("Realmente desea eliminarlo?"))
							return false;
					}
				}
				return true;	
			}
			function hacerValidaciones_frm(forma){
				if(forma.cmd.value!='Nuevo')
					validarSeleccion(forma.r, "- Debe seleccionar un item")
			}
			function ponerSeleccionado(){
				if(document.frmResultado){
					if(document.frmResultado.id){
						if(document.frmResultado.id.length){
							for(var i=0;i<document.frmResultado.id.length;i++){
								if(document.frmResultado.id[i].value==document.frmNuevo.ELHPSELECCIONADO.value){
									document.frmResultado.id[i].checked=true;
									document.frmResultado.id[i].focus();
									break;
								}
							}
						}else{
							if(document.frmResultado.id.value==document.frmNuevo.ELHPSELECCIONADO.value){
								document.frmResultado.id.checked=true;;
								document.frmResultado.id.focus();
							}
						}
					}
				}
			}
			-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
 <c:if test="${!empty sessionScope.filtroConvivencia}">
  <center>
 <div style="width:98%;height:100px;overflow:auto;">
	<form method="post" name="frm"  onSubmit=" return validarForma(frm)" action='<c:url value="/personal/ControllerNuevoEdit.do"/>'>
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	
	
		<c:if test="${  (sessionScope.login.perfil == paramsVO.PERFIL_RECTOR or sessionScope.login.perfil == paramsVO.PERFIL_ADMIN_ACADEMICO )}">
	 <input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframePer}"/>'>
	</c:if>
 	
 	<INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<input type="hidden" name="cmd" value="">
		<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="silver">
			<tr>
				<td colspan='4'>
					<input class='boton' name="cmd1" type="submit" value="Editar" onClick="return editar(document.frmNuevo.tipo.value,'Editar')">
					<input class='boton' name="cmd1" type="submit" value="Nuevo" onClick="return editar(document.frmNuevo.tipo.value,'Nuevo')" style='display:<c:out value="${permisoBoton}"/>'>
					<input class='boton' name="cmd1" type="submit" value="Eliminar" onClick="return editar(document.frmNuevo.tipo.value,'Eliminar')" style='display:<c:out value="${permisoBoton}"/>'>
				</td>
			</tr>
		</table>	
		<table border="1" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="silver">
			<tr>
				<td width='1%'>&nbsp;</td>
				<th><b>Tipo de convivencia</b></th>
				<th><b>Descripción</b></th>
				<th><b>Fecha</b></th>
			</tr>
			<c:forEach begin="0" items="${sessionScope.filtroConvivencia}" var="fila">
			<tr>				
				<td width='1%'>
					<input type='radio' name='r' value='<c:out value="${fila[0]}"/>'>
				</td>
				<td><c:forEach begin="0" items="${filtroTipoConvivencia}" var="fila2"><c:if test="${fila[1]== fila2[0]}"><c:out value="${fila2[1]}"/></c:if></c:forEach></td>
				<td><c:out value="${fila[2]}"/></td>
				<td><c:out value="${fila[3]}"/></td>
			</tr>
			</c:forEach>
		</table>
	</center>
	</form>
	</div>
	</c:if>
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/personal/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="silver">
			<tr>		
			  <td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)"  style='display:<c:out value="${permisoBoton}"/>'>
			</tr>
		</table>
<!--//////////////////-->
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoPersonal.pernumdocum}"/>">
	
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	
	<c:if test="${  (sessionScope.login.perfil == paramsVO.PERFIL_RECTOR or sessionScope.login.perfil == paramsVO.PERFIL_ADMIN_ACADEMICO )}">
	 <input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframePer}"/>'>
	</c:if>
	
	<INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<input type="hidden" name="cmd" value="Cancelar">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
			<td rowspan="2" width="510" bgcolor="#FFFFFF"><script>
			if(fichaPersonal==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>');
			if(fichaSalud==1)document.write('<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>');
			if(fichaConvivencia==1)document.write('<img src="../etc/img/tabs/inf_convivencia_1.gif" alt="Convivencia"  height="26" border="0">');
			if(fichaSede==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/Doc_Jor0.gif" alt="Datos B&aacute;sicos"  height="26" border="0"></a>');
			if(fichaLaboral==1)document.write('<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_laboral_0.gif" alt="Laboral"  height="26" border="0"></a>');
			if(fichaAcademica==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/for_academica_0.gif" alt="Academica"  height="26" border="0"></a><br>');
			if(fichaAsistencia==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/asistencia_0.gif" alt="Asistencia" height="26" border="0"></a>');
			if(fichaCarga==1)document.write('<img src="../etc/img/tabs/carga_academica_0.gif" alt=""  height="26" border="0">');
			if(fichaFoto==1)document.write('<a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/fotografia_0.gif" alt=""  height="26" border="0"></a>');
			</script></td>
			<td align="right"><c:out value="${sessionScope.nuevoPersonal.pernombre1}"/> <c:out value="${sessionScope.nuevoPersonal.perapellido1}"/></td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
								    <tr>
										<td><span class="style2">*</span> Tipo de convivencia:</td>
										<td colspan=3>
											<select name="contipo" style='width:200px;'>
												<option value="-1">-- seleccione una --</option>
													<c:forEach begin="0" items="${filtroTipoConvivencia}" var="fila">
														<option value="<c:out value="${fila[0]}"/>"  <c:if test="${sessionScope.nuevoConvivencia.contipo== fila[0]}">SELECTED</c:if>>
															<c:out value="${fila[1]}"/></option>
													</c:forEach>													
											</select>
										</td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Descripción:</td>
										<td colspan=3><textarea name="condescripcion" cols="80"  rows="2"><c:out value="${sessionScope.nuevoConvivencia.condescripcion}"/></textarea></td>
                </tr>
									<tr>
									  <td>Acuerdos a los que se llegaron:</td>
										<td colspan=3><textarea name="conacuerdos" cols="80" rows="2"><c:out value="${sessionScope.nuevoConvivencia.conacuerdos}"/></textarea></td>
                </tr>
									<tr>
									  <td>Seguimiento que se le hace:</td>
										<td colspan=3><textarea name="conseguimiento" cols="80" rows="2"><c:out value="${sessionScope.nuevoConvivencia.conseguimiento}"/></textarea></td>
									</tr>
									<tr>
									  <td valign='top'><span class="style2">*</span> Fecha en que ocurrio:</td>
									  <td colspan=3 valign='top'>
											<input type="text" name="confecha"  maxlength="15" size='15' value='<c:out value="${sessionScope.nuevoConvivencia.confecha}"/>' readOnly='true'>&nbsp;&nbsp;(dd/mm/aaaa)
											<div style="float: left; margin-rigth: 1em; margin-bottom: 1em;" id="calendar-container"></div>
										</td>
									</tr>
							  </TABLE>
<!--//////////////////////////////-->
</form><c:remove var="filtroTipoConvivencia"/>
<script><c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
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
      document.frmNuevo.confecha.value=d_+"/"+m_+"/"+y_;
    }
  };

  Calendar.setup(
    {
      flat         : "calendar-container", // ID of the parent element
      flatCallback : dateChanged           // our callback function
    }
  );
</script>