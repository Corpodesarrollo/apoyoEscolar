<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/><jsp:setProperty name="nuevoPersonal" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="laboralVO" class="siges.personal.beans.LaboralVO" scope="session"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="formacionVO" class="siges.personal.beans.FormacionVO" scope="session"/><jsp:setProperty name="formacionVO" property="*"/>
<jsp:useBean id="paramsVO" class="siges.personal.beans.ParamsVO" scope="page"/>
<%pageContext.setAttribute("filtroGrado",Recursos.recurso[Recursos.GRADO]);%>
<%@include file="../parametros.jsp"%><c:set var="tip" value="5" scope="page"/>
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
				validarCampo(forma.labNomEntid, "- Nombre de la entidad (máximo 60 de caracteres)",1,60)
				validarCampo(forma.labCargo, "- Cargo desempeñado (máximo 60 de caracteres)",0,60)
				validarCampo(forma.labDescripcion, "- Descripcion (máximo 100 de caracteres)",1,100)
				validarCampo(forma.labContacto, "- Nombre del contacto (máximo 60 de caracteres)",1,60)
				validarCampo(forma.labTelefono, "- Teléfono de contacto en la entidad (máximo 10 de caracteres)",1,10)
				validarCampo(forma.labFechaInicio, "- Fecha de inicio",1,60)
				validarCampo(forma.labFechaFinal, "- Fecha de finalización",1,60) 
				validarRangoFechas(forma.labFechaInicio ,forma.labFechaFinal,'La fecha inicial debe ser menor que la fecha final');
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
	<c:if test="${!empty requestScope.filtroLaboral}">
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
				<input name="cmd1" class='boton' type="submit" value="Editar" onClick="return editar(5,'Editar')">
				<input name="cmd1" class='boton' type="submit" value="Nuevo" onClick="return editar(5,'Nuevo')" style='display:<c:out value="${permisoBoton}"/>'>
				<input name="cmd1" class='boton' type="submit" value="Eliminar" onClick="return editar(5,'Eliminar')" style='display:<c:out value="${permisoBoton}"/>'>
			<td></tr>
		</table>	
		<table border="1" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="silver">
			<tr>
				<td></td>
				<th><b>Entidad</b></th>
				<th><b>Cargo</b></th>
				<th><b>Fecha de inicio</b></th>
				<th><b>Fecha de finalización</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroLaboral}" var="fila">
			<tr>				
				<td width='1%'><input type='radio' name='r' value='<c:out value="${fila[0]}"/>'></td>
				<td><c:out value="${fila[1]}"/></td>
				<td><c:out value="${fila[2]}"/></td>
				<td><c:out value="${fila[3]}"/></td>
				<td><c:out value="${fila[4]}"/></td>
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
        
         <c:if test="${sessionScope.laboralVO.labEstado}">
          <input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar(5)" style='display:<c:out value="${permisoBoton}"/>'>
         </c:if>
         
          <c:if test="${sessionScope.laboralVO.labEstado != 1}">
           <input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar(5)" style='display:<c:out value="${permisoBoton}"/>'>
         </c:if>
         <c:if test="${sessionScope.laboralVO.labEstado == 1}">
           <input class="boton" name="cmd1" type="button" value="Actualizar" onClick="guardar(5)" style='display:<c:out value="${permisoBoton}"/>'>
         </c:if>
          
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
	<INPUT TYPE="hidden" NAME="labId" VALUE='<c:out value="${sessionScope.laboralVO.labId}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
			<td rowspan="2" width="510" bgcolor="#FFFFFF">
				<script>
				if(fichaPersonal==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>');
				if(fichaSalud==1)document.write('<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>');
				if(fichaConvivencia==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>');
				if(fichaSede==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/Doc_Jor0.gif" alt="Datos B&aacute;sicos"  height="26" border="0"></a>');
				if(fichaLaboral==1)document.write('<img src="../etc/img/tabs/inf_laboral_1.gif" alt="Laboral"  height="26" border="0">');
				if(fichaAcademica==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/for_academica_0.gif" alt="Academica"  height="26" border="0"></a><br>');
				if(fichaAsistencia==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/asistencia_0.gif" alt="Asistencia" height="26" border="0"></a>');
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
										<td><span class="style2">*</span>Nombre de la Entidad: </td>
										<td colspan=3>
										<input type="text" maxlength='100' size='70' name="labNomEntid" value='<c:out value="${sessionScope.laboralVO.labNomEntid}"/>'>
										</td>
									</tr>
									<tr>
										<td>Cargo desempeñado: </td>										
										<td colspan=3>
											<input type="text" maxlength='100' size='70' name="labCargo" value='<c:out value="${sessionScope.laboralVO.labCargo}"/>'>
										</td>
									</tr>
									<tr>
										<td><span class="style2">*</span>Descripción del trabajo :</td>
										<td colspan=3><textarea name="labDescripcion" cols="70" rows="2"><c:out value="${sessionScope.laboralVO.labDescripcion}"/></textarea></td>
									</tr>
									<tr>
										<td><span class="style2">*</span>Contacto en la entidad:</td>
										<td><input name="labContacto" type="text" size='30' maxlength="60" value='<c:out value="${sessionScope.laboralVO.labContacto}"/>'></td>
										<td><span class="style2">*</span>Teléfono de contacto en la entidad:</td>
										<td><input name="labTelefono" type="text" size='12' maxlength="10" value='<c:out value="${sessionScope.laboralVO.labTelefono}"/>'></td>
								  </tr>
									<tr>
										<td valign='top'><span class="style2">*</span>Fecha de inicio:</td>
										<td>
										  <input name="labFechaInicio" type="text" value='<c:out value="${sessionScope.laboralVO.labFechaInicio}"/>' size="12" maxlength='10' readOnly>											
										</td>
									    <td valign='top'><span class="style2">*</span>Fecha de finalización: </td>
										<td>
											<input name="labFechaFinal" type="text" value='<c:out value="${sessionScope.laboralVO.labFechaFinal}"/>' size="12" maxlength='10'  readOnly>
										</td>
								    </tr>
									<tr>
										<td colspan=2><div style="float: left; margin-rigth: 1em; margin-bottom: 1em;" id="calendar-container"></div></td>
										<td colspan=2><div style="float: left; margin-rigth: 1em; margin-bottom: 1em;" id="calendar-container2"></div></td>
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
      document.frmNuevo.labFechaInicio.value=d_+"/"+m_+"/"+y_;
    }
  };

  function dateChanged2(calendar) {
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
      document.frmNuevo.labFechaFinal.value=d_+"/"+m_+"/"+y_;
    }
  };
	
  Calendar.setup(
    {
      flat         : "calendar-container", // ID of the parent element
      flatCallback : dateChanged           // our callback function
    }
  );
  Calendar.setup(
    {
      flat         : "calendar-container2", // ID of the parent element
      flatCallback : dateChanged2           // our callback function
    }
  );
</script>
</body>
</html>