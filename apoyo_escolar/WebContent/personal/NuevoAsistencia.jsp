<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/><jsp:setProperty name="nuevoPersonal" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="laboralVO" class="siges.personal.beans.LaboralVO" scope="session"/><jsp:setProperty name="laboralVO" property="*"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/>
<jsp:useBean id="formacionVO" class="siges.personal.beans.FormacionVO" scope="session"/><jsp:setProperty name="formacionVO" property="*"/>
<jsp:useBean id="paramsVO" class="siges.personal.beans.ParamsVO" scope="page"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="7" scope="page"/>
<style type="text/css">@import url(<c:url value="/etc/css/calendar-green.css"/>);</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<%@include file="../parametros.jsp"%>
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
					document.frmNuevo.cmd.value="Cancelar";
	        document.frmNuevo.submit();
			}
		
		function hacerValidaciones_frmNuevo(forma){
			validarCampo(forma.asiFecha, "- Fecha",1,10)
			validarCampo(forma.asiMotivo, "- Motivo",1,10)
			validarSeleccion(forma.asiJustificada, "- Justificada")
		}
		
		function hacerValidaciones_frm(forma){
			if(forma.cmd.value!='Nuevo')
				validarSeleccion(forma.r, "- Debe seleccionar un item")
		}
			
			function cargar(forma){
				mensaje(document.getElementById("msg"));
			}
			
			function lanzar(tipo){	
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/personal/ControllerNuevoEdit.do"/>";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				if(validarForma(frmNuevo)){
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
		//-->		
		</script>
	</head>
<body onLoad='cargar(frmNuevo)'>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<c:if test="${!empty requestScope.filtroAsistencia}">
  <center>
 <div style="width:98%;height:100px;overflow:auto;">
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/personal/ControllerNuevoEdit.do"/>'>
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	
		  <c:if test="${  (sessionScope.login.perfil == paramsVO.PERFIL_RECTOR or sessionScope.login.perfil == paramsVO.PERFIL_ADMIN_ACADEMICO )}">
	 <input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframePer}"/>'>
	</c:if>
	
	
	<INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<input type="hidden" name="cmd" value="">
		<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="silver">
			<tr><td>
				<input name="cmd1" class='boton'  type="submit" value="Editar" onClick="return editar(document.frmNuevo.tipo.value,'Editar')">
				<input name="cmd1" class='boton'  type="submit" value="Nuevo" onClick="return editar(document.frmNuevo.tipo.value,'Nuevo')" style='display:<c:out value="${permisoBoton}"/>'>
				<input name="cmd1" class='boton'  type="submit" value="Eliminar" onClick="return editar(document.frmNuevo.tipo.value,'Eliminar')" style='display:<c:out value="${permisoBoton}"/>'>
			<td></tr>
		</table>	
		<table border="1" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="silver">
			<tr>
				<td width='1%'></td>				
				<th width='10%'>Fecha</th>
				<th>Motivo</b></th>
				<th width='1%'>Justificada</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroAsistencia}" var="fila">
			<tr>				
				<td width='1%'><input type='radio' name='r' value='<c:out value="${fila[0]}"/>'></td>
				<td>   <c:out value="${fila[1]}"/></td>
				<td> <c:if test="${fila[2] == 1 }">ENFERMEDAD</c:if>
				     <c:if test="${fila[2] == 2 }">PERMISO DE LOS PADRES</c:if>
				     <c:if test="${fila[2] == 3 }">CITA MEDICA</c:if>
				     <c:if test="${fila[2] == 4 }">SIN INFORMACION</c:if>
				     <c:if test="${fila[2] == 5 }">OTRO</c:if>
	
				</td>
				<td><c:out value="${fila[3]}"/></td>
			</tr>
			</c:forEach>
		</table>
	</center>
	</form>
	</div>
	</c:if>
	<form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action='<c:url value="/personal/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
			  <td width="45%" bgcolor="#FFFFFF">
          <input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)" style='display:<c:out value="${permisoBoton}"/>'>
				</td>
			</tr>	
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	
	
		  <c:if test="${  (sessionScope.login.perfil == paramsVO.PERFIL_RECTOR or sessionScope.login.perfil == paramsVO.PERFIL_ADMIN_ACADEMICO )}">
	 <input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframePer}"/>'>
	</c:if>
	
	<INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<INPUT TYPE="hidden" NAME="asiCodigo" VALUE='<c:out value="${sessionScope.asistenciaVO.asiCodigo}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
			<td rowspan="2" width="510" bgcolor="#FFFFFF">
				<script>
				if(fichaPersonal==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>');
				if(fichaSalud==1)document.write('<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>');
				if(fichaConvivencia==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>');
				if(fichaSede==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/Doc_Jor0.gif" alt="Datos B&aacute;sicos"  height="26" border="0"></a>');
				if(fichaLaboral==1)document.write('<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_laboral_0.gif" alt="Laboral"  height="26" border="0"></a>');
				if(fichaAcademica==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/for_academica_0.gif" alt="Academica"  height="26" border="0"></a><br>');
				if(fichaAsistencia==1)document.write('<img src="../etc/img/tabs/asistencia_1.gif" alt="Asistencia" height="26" border="0">');
				if(fichaCarga==1)document.write('<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/carga_academica_0.gif" alt=""  height="26" border="0"></a>');
				if(fichaFoto==1)document.write('<a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/fotografia_0.gif" alt=""  height="26" border="0"></a>');
			  </script>
			</td>
			<td align="right"><c:out value="${sessionScope.nuevoPersonal.pernombre1}"/> <c:out value="${sessionScope.nuevoPersonal.perapellido1}"/></td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="1" cellSpacing="0" border=0>
								<tr>
									  <td><span class="style2">*</span> Motivo de la inasistencia:</th>
										<td colspan=3>
										 
										
											<select name="asiMotivo" style='width:120px;'>
												<option value='-1'>-- Seleccione uno --</option>
													<option value="1" <c:if test="${sessionScope.asistenciaVO.asiMotivo == 1}">SELECTED</c:if>>ENFERMEDAD</option>
													<option value="2" <c:if test="${sessionScope.asistenciaVO.asiMotivo == 2}">SELECTED</c:if>>PERMISO DE LOS PADRES</option>
													<option value="3" <c:if test="${sessionScope.asistenciaVO.asiMotivo ==3}">SELECTED</c:if>>CITA MEDICA</option>
													<option value="4" <c:if test="${sessionScope.asistenciaVO.asiMotivo ==4}">SELECTED</c:if>>SIN INFORMACION</option>
													<option value="5" <c:if test="${sessionScope.asistenciaVO.asiMotivo ==5}">SELECTED</c:if>>OTRO</option>
											</select>	
											
											
										</td>
                  </tr>
									<tr>
									  <td>Observaciones especiales:</th>
										<td colspan=3><textarea name="asiObservacion" cols="70" rows="2"><c:out value="${sessionScope.asistenciaVO.asiObservacion}"/></textarea></td>
                  </tr>
									<tr>
									  <td><span class="style2">*</span> Ausencia justificada:</th>
									  <td>
											<input type="radio" name="asiJustificada" value="1" <c:if test="${sessionScope.asistenciaVO.asiJustificada==1}">CHECKED</c:if>>Si&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="asiJustificada" value="0" <c:if test="${sessionScope.asistenciaVO.asiJustificada==0}">CHECKED</c:if>>No
									  </td>									  
									  <td><span class="style2">*</span> Fecha de la inasistencia:</th>
									  <td><input type="text" name="asiFecha"  maxlength="10" size="12" value='<c:out value="${sessionScope.asistenciaVO.asiFecha}"/>' readOnly></td>
									</tr>
									<tr>
									  <td></th>
									  <td></th>
									  <td colspan=2><div style="float: left; margin-rigth: 1em; margin-bottom: 1em;" id="calendar-container"></div></th>
									</tr>
									</TABLE>
</form>
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
</body>
</html>