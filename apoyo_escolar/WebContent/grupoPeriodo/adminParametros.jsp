<jsp:useBean id="adminVO" class="siges.grupoPeriodo.beans.AdminVO" scope="session"/><jsp:setProperty name="adminVO" property="*"/>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="tip" value="1" scope="page"/>
<script languaje='javaScript'>
		<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
				validarEntero(forma.adminLogro, "- Cantidad máxima de logros", 0,99)
				validarEntero(forma.adminDescriptor, "- Cantidad máxima de descriptores", 0,99)
				validarFloat(forma.adminMaxAsignatura, "- Porcentaje mínimo para aprobar una asignatura", 0,100)
			} 
			
			/*function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/grupoPeriodo/ControllerAdminEdit.do"/>";
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}*/
			function guardar(forma,tipo){
				if(validarForma(forma)){
					if(forma.adminHorario_){
						if(forma.adminHorario_.checked){
							forma.adminHorario.value='1';
						}else{
							forma.adminHorario.value='0';
						}
					}
					forma.tipo.value=tipo;
					forma.submit();
				}
			}
			
			function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			
			function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value=6;
				document.frmNuevo.action = '<c:url value="/adminParamsInst/Nuevo.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
			}
			
			//-->
	</script>
<%@include file="../mensaje.jsp"%>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/grupoPeriodo/adminParametrosGuardar.jsp"/>'>
	<br>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Administración de parámetros de colegio</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
				<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo,2)">
				<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		  </td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="send" value="">
	<input type="hidden" name="adminHorario" value='<c:out value="${sessionScope.adminVO.adminHorario}"/>'>
	<input type="hidden" name="adminInst" value='<c:out value="${sessionScope.login.instId}"/>'>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			<td rowspan="2" bgcolor="#FFFFFF">
			  <img src="../etc/img/tabs/validacion_horarios_1.gif" border="0"  height="26" alt='-Validacion de horarios-'>
			   <a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/adm_periodos_0.gif"/>' alt="Parametros" height="26" border="0"></a>
			   <a href="javaScript:lanzar(4)"><img src='<c:url value="/etc/img/tabs/adm_nivelEval_0.gif"/>' alt="Nivel Evaluacion" height="26" border="0"></a>
		       <a href="javaScript:lanzar(2)"><img src='<c:url value="/etc/img/tabs/adm_escalaConpt_0.gif"/>' alt="Escala Conceptual" height="26" border="0"></a>
    		   <a href="javaScript:lanzar(3)"><img src='<c:url value="/etc/img/tabs/adm_escalaNum_0.gif"/>' alt="Escala Numérica" height="26" border="0"></a>
			   <a href="javaScript:lanzar(40)"><img src='<c:url value="/etc/img/tabs/adm_perProgFechas_0.gif"/>' alt="Programar Periodos" height="26" border="0"></a>
			   <a href="javaScript:lanzar(110)"><img src='<c:url value="/etc/img/tabs/adm_ponporperiodo_0.png"/>' alt="Ponderación por periodos" height="26" border="0"></a>
			 
			 </td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE width="95%" cellpadding="1" cellSpacing="0" border=0 align="center">
		<c:if test="${sessionScope.adminVO.adminLectura=='1'}">
			<tr>
				<td colspan="2"><span class="style2">NOTA:
					Solamente el docente que quede registrado en el horario para ese grupo y esa área/asignatura/dimensión podrá ingresar información en línea para evaluaciones o importar plantillas de evaluación.
					Generar plantillas de evaluaciones o consultar la información de las evaluaciones lo podrán hacer todos los docentes que estén registrados para esa sede y jornada.
					</span>
				</td>	
			</tr>	
			<tr>
				<td colspan="1" width='35%' valign='top'><br><br>Restricción de horarios</td>
				<td colspan="1" align='LEFT'  valign='top'><br><br>
				<input type='checkbox' name='adminHorario_' value='1' <c:if test="${sessionScope.adminVO.adminHorario=='1'}">CHECKED</c:if>>
				</td>	
			</tr>
		</c:if>
		<c:if test="${sessionScope.adminVO.adminLectura=='0'}">
			<tr>
				<td colspan="2"><span class="style2">NOTA:
					El administrador general del sistema ha deshabilitado la validación para todos los colegios
					de manera que no hay restricciones de horario para evaluar o importar evaluaciones.
					</span>
				</td>	
			</tr>	
		</c:if>
		<tr>
		<td>Cantidad máxima de logros:</td>
		<td><input type="text" size="3" name="adminLogro" maxlength="2" value='<c:out value="${sessionScope.adminVO.adminLogro}"/>' onKeyPress='return acepteNumeros(event)'></td>
		</tr>
		<tr>
		<td>Cantidad máxima de descriptores:</td>
		<td><input type="text" size="3" name="adminDescriptor" maxlength="2" value='<c:out value="${sessionScope.adminVO.adminDescriptor}"/>' onKeyPress='return acepteNumeros(event)'></td>
		</tr>
		<tr>
		<td>Porcentaje mínimo para aprobar una asignatura:</td>
		<td><input type="text" size="5" name="adminMaxAsignatura" maxlength="5" value='<c:out value="${sessionScope.adminVO.adminMaxAsignatura}"/>' onKeyPress='return acepteNumeros(event)'></td>
		</tr>
		<tr>
		<td>Manejo de plan de estudios:</td>
		<td>
			<input type="radio" name="adminPlanEstudios" value="0" <c:if test="${sessionScope.adminVO.adminPlanEstudios=='0'}">CHECKED</c:if>>Unificado<br>
			<input type="radio" name="adminPlanEstudios" value="1" <c:if test="${sessionScope.adminVO.adminPlanEstudios=='1'}">CHECKED</c:if>>Por docente
		</td>
		</tr>
	</TABLE>
</form>