<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/><jsp:setProperty name="nuevoBasica" property="*"/>
<jsp:useBean id="nuevoFamiliar" class="siges.estudiante.beans.Familiar" scope="session"/><jsp:setProperty name="nuevoFamiliar" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/>
<jsp:useBean id="academicaVO" class="siges.estudiante.beans.AcademicaVO" scope="session"/><jsp:setProperty name="academicaVO" property="*"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="nuevoAtencion" class="siges.estudiante.beans.AtencionEspecial" scope="session"/><jsp:setProperty name="nuevoAtencion" property="*"/>
<%pageContext.setAttribute("filtroTipoConvivencia",Recursos.recurso[Recursos.CONVIVENCIA]);%>
<%pageContext.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);%>
<%@include file="../parametros.jsp"%><c:set var="tip" value="4" scope="page"/>
<style type="text/css">@import url(<c:url value="/etc/css/calendar-green.css"/>);</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje=' javaScript'>			
		<!--
		function cancelar(){
			if (confirm('¿DESEA CANCELAR LA INSERCIÓN DE UN NUEVO REGISTRO?')){
 				document.frmNuevo.cmd.value="Cancelar";
                document.frmNuevo.submit();
			}
		}

		function borrar_combo(combo){
			for(var i = combo.length - 1; i >= 0; i--) {
				if(navigator.appName == "Netscape") combo.options[i] = null;
				else combo.remove(i);
			}
			combo.options[0] = new Option("--Todos--","-9");
		}

		function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
			this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
		}

		function filtrarTipoConflicto(combo_padre,combo_hijo){
			borrar_combo(combo_hijo); 
			<c:if test="${!empty sessionScope.filtroClaseConflicto && !empty sessionScope.filtroTipoConflicto}">var Padres = new Array();
				<c:forEach begin="0" items="${sessionScope.filtroClaseConflicto}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${sessionScope.filtroTipoConflicto}" var="fila2"><c:if test="${fila[0]==fila2[2]}">
						Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoConvivencia.idTipo== fila2[0]}">SELECTED</c:if>';
						id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; 
						id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if>
					</c:forEach>
					Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
				</c:forEach>
				var niv=combo_padre.options[combo_padre.selectedIndex].value;
				var val_padre = -9;
				for(var k=0;k<Padres.length;k++){
					if(Padres[k].id_Padre[0]==niv) val_padre=k;							
				}
				if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
					for(i=0; i < no_hijos; i++){
						if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
							combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
						}else
							combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
					}
				}
		</c:if>
		}					
		
			function reporte(tipo){
				if (confirm('¿Desea generar el reporte de la información de convivencia del estudiante?')){
 					document.frmNuevo.cmd.value="Reporte1";
					document.frmNuevo.tipo.value=tipo;
     				document.frmNuevo.action="<c:url value="/estudiante/ReporteGuardarConvivencia.jsp"/>";
				    document.frmNuevo.target="";
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
			validarLista(forma.contipo, "- Tipo de convivencia",1)
			validarCampo(forma.condescripcion, "- Descripción",1,100)
			validarCampo(forma.confecha, "- Fecha en que ocurrio",6,10)
			valFecha(forma.confecha,"- Formato de Fecha (dd/mm/aaaa)")
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
	<c:if test="${!empty sessionScope.filtroConvivencia}">
 <center>
 <div style="width:98%;height:100px;overflow:auto;">
	<form method="post" name="frmConvivencia"  onSubmit=" return validarForma(frmConvivencia)" action='<c:url value="/estudiante/ControllerNuevoEdit.do"/>'>
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value=""><INPUT TYPE="hidden" NAME="height" VALUE='130'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
		<table border="0" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
			<tr>
				<td colspan='4' align='left'>
					<input class='boton' name="cmd1" type="submit" value="<c:if test="${empty sessionScope.ModoConsulta}">Editar</c:if><c:if test="${!empty sessionScope.ModoConsulta}">Ver</c:if>" onClick="return editar(document.frmNuevo.tipo.value,'Editar')">
					<c:if test="${empty sessionScope.ModoConsulta}"><input class='boton' name="cmd1" type="submit" value="Nuevo" onClick="return editar(document.frmNuevo.tipo.value,'Nuevo')" style='display:<c:out value="${permisoBoton}"/>'></c:if>
					<c:if test="${empty sessionScope.ModoConsulta}"><input class='boton' name="cmd1" type="submit" value="Eliminar" onClick="return editar(document.frmNuevo.tipo.value,'Eliminar')" style='display:<c:out value="${permisoBoton}"/>'></c:if>
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="silver">
			<tr bgcolor="#CCCCCC"> 
				<td width='1%'></td>				
				<th>Tipo de convivencia</th>				
				<th>Descripción</b></th>				
				<th>Fecha</b></th>
			</tr>
			<c:forEach begin="0" items="${sessionScope.filtroConvivencia}" var="fila">
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
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/estudiante/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<tr><td width="45%" bgcolor="#FFFFFF"><c:if test="${empty sessionScope.ModoConsulta}"><input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)" style='display:<c:out value="${permisoBoton}"/>'></c:if><c:if test="${sessionScope.ModoConsulta==1}"><a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a></c:if></tr>
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoBasica2.estcodigo}"/>">
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='130'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
    <tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
      <td rowspan="2" width="510" bgcolor="#FFFFFF">
			<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_familiar_0.gif" alt="Ficha Familiar"  height="26" border="0"></a></c:if>
			<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>
			<img src="../etc/img/tabs/inf_convivencia_1.gif" alt="Convivencia"  height="26" border="0">				
			<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_academica_0.gif" alt="Inf. Académica"  height="26" border="0"></a>
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/atencion_especial_0.gif" alt="Atención Especial"  height="26" border="0"></a></c:if>
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/fotografia_0.gif" alt="Subir Foto Estudiante"  height="26" border="0"></a></c:if></td>
			<td align='CENTER'><b><c:out value="${sessionScope.nuevoBasica.estnombre1}"/> <c:out value="${sessionScope.nuevoBasica.estnombre2}"/> <c:out value="${sessionScope.nuevoBasica.estapellido1}"/></b></td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
								    <tr>
										<td><span class="style2">*</span> Tipo de convivencia:</td>
										<td>
											<select name="contipo" style='width:250px;' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
												<option value="-1">--seleccione una--</option>
													<c:forEach begin="0" items="${filtroTipoConvivencia}" var="fila">
														<option value="<c:out value="${fila[0]}"/>"  <c:if test="${sessionScope.nuevoConvivencia.contipo== fila[0]}">SELECTED</c:if>>
															<c:out value="${fila[1]}"/></option>
													</c:forEach>													
											</select>
										</td>
										
										<td>Periodo:</td>
										<td>
											<select name="periodo" <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
										         <option value="-1">--seleccione uno--</option>
									 	          <c:forEach begin="0" items="${filtroPeriodo}" var="fila">
										     	     <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoConvivencia.periodo== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/>
											   </c:forEach>
										     </select>
								       </td>
								       
									</tr>
									
								    <tr>
										<td> Clase de conflicto:</td>
										<td colspan="3">
											<select name="idClase" style='width:300px;' onChange='filtrarTipoConflicto(document.frmNuevo.idClase, document.frmNuevo.idTipo)' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
												<option value="-9">--seleccione una--</option>
													<c:forEach begin="0" items="${sessionScope.filtroClaseConflicto}" var="fila">
														<option value="<c:out value="${fila[0]}"/>"  <c:if test="${sessionScope.nuevoConvivencia.idClase== fila[0]}">SELECTED</c:if>>
															<c:out value="${fila[1]}"/></option>
													</c:forEach>													
											</select>
										</td>
										</tr>
										<tr>
										<td>Tipo de conflicto:</td>
										<td colspan="3">
											<select name="idTipo" style='width:300px;' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
										         <option value="-9">--seleccione uno--</option>
										     </select>
								       </td>
								       
									</tr>
								</table>
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
										<td><span class="style2">*</span> Descripción:</td>
										<td><textarea name="condescripcion" cols="60" rows="2" <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>><c:out value="${sessionScope.nuevoConvivencia.condescripcion}"/></textarea></td>
									</tr>
									<tr>
									  <td>Acuerdos a los que se llegaron:</td>
										<td><textarea name="conacuerdos" cols="60" rows="2" <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>><c:out value="${sessionScope.nuevoConvivencia.conacuerdos}"/></textarea></td>
									</tr>
									<tr>
									  <td>Seguimiento que se le hace:</td>
										<td><textarea name="conseguimiento" cols="60" rows="2" <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>><c:out value="${sessionScope.nuevoConvivencia.conseguimiento}"/></textarea></td>
									</tr>
									<tr>
									  <td><span class="style2">*</span> Fecha en que ocurrió:</td>
									  <td><input type="text" name="confecha"  maxlength="10" size="12" value='<c:out value="${sessionScope.nuevoConvivencia.confecha}"/>' readOnly ><br>
										<div style="float: left; margin-rigth: 1em; margin-bottom: 1em;" id="calendar-container"></div>
										</td>
									</tr>
							  </TABLE>
	</form>
<c:remove var="filtroTipoConvivencia"/>
<script>
	<c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
	ponerSeleccionado();
</script>
<script type="text/javascript">

document.frmNuevo.idClase.onchange();

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