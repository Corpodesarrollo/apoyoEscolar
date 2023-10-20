<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/><jsp:setProperty name="nuevoPersonal" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="formacionVO" class="siges.personal.beans.FormacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.personal.beans.ParamsVO" scope="page"/>
<%pageContext.setAttribute("filtroGrado",Recursos.recurso[Recursos.GRADO]);%>
<%@include file="../parametros.jsp"%><c:set var="tip" value="6" scope="page"/>
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
	    validarCampo(forma.forNomInst, "- Nombre de la institución",1,200)
			validarLista(forma.forPrograma, "- Tipo de programa",1)
	    validarLista(forma.forSemestres, "- Número de semestres",1)
	    validarCampo(forma.forTitulo, "- Título obtenido",1,200)
	    validarCampo(forma.forCarrera, "- Curso/carrera",1,200)
	    validarLista(forma.forTermino, "- Situación actual",1)
			if(forma.forTermino.options[forma.forTermino.selectedIndex].value==1){
				validarLista(forma.forAnho, "- Año de terminación",1)
			}	
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
	<c:if test="${!empty requestScope.filtroFormacion}">
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
				<input name="cmd1"  class='boton' type="submit" value="Editar" onClick="return editar(document.frm.tipo.value,'Editar')">
				<input name="cmd1"  class='boton' type="submit" value="Nuevo" onClick="return editar(document.frm.tipo.value,'Nuevo')" style='display:<c:out value="${permisoBoton}"/>'>
				<input name="cmd1"  class='boton' type="submit" value="Eliminar" onClick="return editar(document.frm.tipo.value,'Eliminar')" style='display:<c:out value="${permisoBoton}"/>'>
			<td></tr>
		</table>	
		<table border="1" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="silver">
			<tr>
				<td></td>
				<th><b>Institución</b></th>
				<th><b>Curso/carrera</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroFormacion}" var="fila">
			<tr>				
				<td width='1%'><input type='radio' name='r' value='<c:out value="${fila[0]}"/>'></td>
				<td><c:out value="${fila[1]}"/></td>
				<td><c:out value="${fila[2]}"/></td>
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
            
              <c:if test="${ sessionScope.formacionVO.forEstado != 1}"> 
               <input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)" style='display:<c:out value="${permisoBoton}"/>'>
              </c:if>
              
              <c:if test="${ sessionScope.formacionVO.forEstado == 1}"> 
               <input class="boton" name="cmd1" type="button" value="Actualizar" onClick="guardar(document.frmNuevo.tipo.value)" style='display:<c:out value="${permisoBoton}"/>'>
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
	<INPUT TYPE="hidden" NAME="forId" VALUE='<c:out value="${sessionScope.formacionVO.forId}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
			<td rowspan="2" width="510" bgcolor="#FFFFFF">
				<script>
				if(fichaPersonal==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>');
				if(fichaSalud==1)document.write('<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>');
				if(fichaConvivencia==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>');
				if(fichaSede==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/Doc_Jor0.gif" alt="Datos B&aacute;sicos"  height="26" border="0"></a>');
				if(fichaLaboral==1)document.write('<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_laboral_0.gif" alt="Laboral"  height="26" border="0"></a>');
				if(fichaAcademica==1)document.write('<img src="../etc/img/tabs/for_academica_1.gif" alt="Academica"  height="26" border="0"><br>');
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
										<td><span class="style2">*</span>Tipo de programa:</td>
										<td>
											<select name="forPrograma" style='width:120px;'>
												<option value='-1'>-- Seleccione uno --</option>
													<option value="1" <c:if test="${sessionScope.formacionVO.forPrograma==1}">SELECTED</c:if>>Técnico</option>
													<option value="2" <c:if test="${sessionScope.formacionVO.forPrograma==2}">SELECTED</c:if>>Tecnológico</option>
													<option value="3" <c:if test="${sessionScope.formacionVO.forPrograma==3}">SELECTED</c:if>>Profesional</option>
											</select>											
										</td>
										<td><span class="style2">*</span>Número de semestres:</td>
										<td>
											<select name="forSemestres" style='width:70px;'>
												<option value="-1">--seleccione una--</option>
													<c:forEach begin="1" end="12" var="fila">
														<option value='<c:out value="${fila}"/>'  <c:if test="${sessionScope.formacionVO.forSemestres==fila}">SELECTED</c:if>><c:out value="${fila}"/></option>
													</c:forEach>													
											</select>
										</td>
									</tr>
								<tr>
										<td><span class="style2">*</span>Nombre de la Institución:</td>
										<td colspan=3>
										<input type="text" maxlength='70' size='70' name="forNomInst" value='<c:out value="${sessionScope.formacionVO.forNomInst}"/>'>
										</td>
									</tr>
								<tr>
										<td><span class="style2">*</span>Nombre del curso/carrera:</td>
										<td colspan=3>
										<input type="text" maxlength='70' size='70' name="forCarrera" value='<c:out value="${sessionScope.formacionVO.forCarrera}"/>'>
										</td>
									</tr>
								<tr>
										<td><span class="style2">*</span>Título/certificado otorgado:</td>
										<td colspan=3>
										<input type="text" maxlength='70' size='70' name="forTitulo" value='<c:out value="${sessionScope.formacionVO.forTitulo}"/>'>
										</td>
									</tr>
									<tr>
										<td><span class="style2">*</span>Situación actual:</td>
										<td>
											<select name="forTermino" style='width:120px;'>
												<option value='-1'>-- Seleccione una --</option>
													<option value="1" <c:if test="${sessionScope.formacionVO.forTermino==1}">SELECTED</c:if>>Terminado</option>
													<option value="2" <c:if test="${sessionScope.formacionVO.forTermino==2}">SELECTED</c:if>>En curso</option>
													<option value="3" <c:if test="${sessionScope.formacionVO.forTermino==3}">SELECTED</c:if>>Suspendido</option>
											</select>											
										</td>
										<td>Año:</td>
										<td>
											<select name="forAnho" style='width:70px;'>
												<option value="-1">--seleccione una--</option>
													<c:forEach begin="1980" end="2020" var="fila">
														<option value='<c:out value="${fila}"/>'  <c:if test="${sessionScope.formacionVO.forAnho==fila}">SELECTED</c:if>><c:out value="${fila}"/></option>
													</c:forEach>													
											</select>
										</td>
									</tr>
								</TABLE>
</form>
</body>
</html>