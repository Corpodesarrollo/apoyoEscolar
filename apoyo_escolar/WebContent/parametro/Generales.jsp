<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate value="${now}" type="time" timeStyle="long" pattern="yyyy" scope="request" var="anho_actual"/>
	<script language='javaScript'>
	<!--
		var nav4=window.Event ? true : false;
		
		function valorarHorario(forma){
       if(forma.g_parhorario_.checked) forma.g_parhorario.value='1';
       else forma.g_parhorario.value='0';
		}
		
		function valorarBoletin(forma){
       if(forma.g_parboletin_.checked) forma.g_parboletin.value='1';
       else forma.g_parboletin.value='0';
		}
		
		function valorarLnotas(forma){
       if(forma.g_parlibro_.checked) forma.g_parlibro.value='1';
       else forma.g_parlibro.value='0';
		}
		
		function valorarInte(forma){
       if(forma.g_parintegracion_.checked) forma.g_parintegracion.value='1';
       else forma.g_parintegracion.value='0';
		}
		
		function valorarInteMatr(forma){
       if(forma.g_parintegracionMatr_.checked) forma.g_parintegracionMatr.value='1';
       else forma.g_parintegracionMatr.value='0';
       
       
		}
		
		function valorarRepEst(forma){
       if(forma.g_parestadistico_.checked) forma.g_parestadistico.value='1';
       else forma.g_parestadistico.value='0';
		}
		
		function valorarCertCons(forma){
       if(forma.g_parcertificado_.checked) forma.g_parcertificado.value='1';
       else forma.g_parcertificado.value='0';
		}

		function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}

		function hacerValidaciones_frm(frm){
		}

		function lanzar(tipo){			
			document.frm.tipo.value=tipo;					
			document.frm.submit();
		}

		function guardar(tipo){
			if(document.frm.g_parvigencia.value!=document.frm.g_parvigencia_.value){
				if(confirm('¿Desea cambiar el año de vigencia?')){
					document.frm.g_parvigencia.value=document.frm.g_parvigencia_.value;
				}
			}
			document.frm.tipo.value=tipo;
			document.frm.cmd.value="Guardar";
			document.frm.submit();
		}

		function cancelar(){
			if (confirm('¿Desea cancelar la configuración?')){
					location.href='<c:url value="/bienvenida.jsp"/>';
			}
		}

		function borrar_combo(combo){
			for(var i = combo.length - 1; i >= 0; i--) {
				if(navigator.appName == "Netscape") combo.options[i] = null;
				else combo.remove(i);
			}
			combo.options[0] = new Option("--Seleccione uno--","-1");
		}
		
		
		var visibilidad=0;

		function visible(){
		forma = document.frm;
			if(forma.g_parintegracionMatr.value == 0 && document.getElementById('tabla')){
				visibilidad=0;
				document.getElementById('tabla').style.display='none';				
			}
			else if(forma.g_parintegracionMatr.value == 1 && document.getElementById('tabla')){
				visibilidad=1;
				document.getElementById('tabla').style.display='';
			}
		}
	//-->
	</script>
	<%@include file="../mensaje.jsp" %>
	<font size='1'>
		<form method="post" name="frm" onSubmit=" return validarForma(frm)" action='<c:url value="/parametro/NuevoGuardar.jsp"/>'>
			<input type="hidden" name="tipo" value="1">
			<input type="hidden" name="cmd" value="Cancelar">
			<input type="hidden" name="g_parvigencia" value="<c:out value="${sessionScope.parametros.g_parvigencia}"/>">
			<input type="hidden" name="g_parhorario" value="<c:out value="${sessionScope.parametros.g_parhorario}"/>">
			<input type="hidden" name="g_parboletin" value="<c:out value="${sessionScope.parametros.g_parboletin}"/>">
			<input type="hidden" name="g_parlibro" value="<c:out value="${sessionScope.parametros.g_parlibro}"/>">
			<input type="hidden" name="g_parintegracion" value="<c:out value="${sessionScope.parametros.g_parintegracion}"/>">
			<input type="hidden" name="g_parestadistico" value="<c:out value="${sessionScope.parametros.g_parestadistico}"/>">
			<input type="hidden" name="g_parcertificado" value="<c:out value="${sessionScope.parametros.g_parcertificado}"/>">
			<table border="0" align="center" width="100%">
				<tr>
		  		<td width="45%">
            <input  name="cmd1" type="button"   value="Guardar" onClick="guardar(-1)">
			  	</td>
				</tr>	
	  	</table>
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
					<td rowspan="2" width="469" bgcolor="#FFFFFF">
						<img src="../etc/img/tabs/conf_general_1.gif" alt="Parámetros Generales" height="26" border="0">
						<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/recolector_0.gif" alt="Recolector" height="26" border="0"></a>
						<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/plantillas_batch_0.gif" alt="Plantillas Batch" height="26" border="0"></a>
						<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/importacion_batch_0.gif" alt="Importación Batch" height="26" border="0"></a>
					</td>
				</tr>
			</table>
			<br>
			<table border="0" align="center" width="65%" cellpadding="0" cellspacing="5">
			<caption>Cambio de Vigencia</caption>
				<tr>
					<th>Año Vigencia:</th>
					<td>
						<SELECT name="g_parvigencia_">
							<option value="<c:out value="${anho_actual-1}"/>" <c:if test="${anho_actual-1==sessionScope.parametros.g_parvigencia}">SELECTED</c:if>><c:out value="${anho_actual-1}"/></option>
							<option value="<c:out value="${anho_actual}"/>" <c:if test="${anho_actual==sessionScope.parametros.g_parvigencia}">SELECTED</c:if>><c:out value="${anho_actual}"/></option>
							<option value="<c:out value="${anho_actual+1}"/>" <c:if test="${anho_actual+1==sessionScope.parametros.g_parvigencia}">SELECTED</c:if>><c:out value="${anho_actual+1}"/></option>
						</select>
					</td>
				</tr>
			</table>
			<br>
			<table border="0" align="center" width="65%" cellpadding="0" cellspacing="5">
			<caption>Habilitar Restricción de Horarios</caption>
				<tr>
					<th>Habilitado:</th>
					<td>
						<INPUT type='checkbox' name='g_parhorario_' value="1" onClick='valorarHorario(document.frm)' <c:if test="${sessionScope.parametros.g_parhorario==1}">checked</c:if>>
					</td>
				</tr>
			</table>
			<br>
			<table border="0" align="center" width="65%" cellpadding="0" cellspacing="5">
			<caption>Proceso de generación de Boletines</caption>
				<tr>
					<th>Habilitado:</th>
					<td>
						<INPUT type='checkbox' name='g_parboletin_' value="1" onClick='valorarBoletin(document.frm);' <c:if test="${sessionScope.parametros.g_parboletin==1}">checked</c:if>>
					</td>
				</tr>
			</table>
			<br>
			<table border="0" align="center" width="65%" cellpadding="0" cellspacing="5">
			<caption>Proceso de generación de Libros de Notas</caption>
				<tr>
					<th>Habilitado:</th>
					<td>
						<INPUT type='checkbox' name='g_parlibro_' value="1" onClick='valorarLnotas(document.frm);' <c:if test="${sessionScope.parametros.g_parlibro==1}">checked</c:if>>
					</td>
				</tr>
			</table>
			<br>
			<table border="0" align="center" width="65%" cellpadding="0" cellspacing="5">
			<caption>Proceso de Integraci&oacute;n con Matriculas</caption>
				<tr>
					<th>Habilitado:</th>
					<td>
						<INPUT type='checkbox' name='g_parintegracion_' value="1" onClick='valorarInte(document.frm);' <c:if test="${sessionScope.parametros.g_parintegracion==1}">checked</c:if>>
					</td>
				</tr>
			</table>
			<br>
			<table border="0" align="center" width="65%" cellpadding="0" cellspacing="5">
			<caption>Proceso de generación de Reportes Estadisticos</caption>
				<tr>
					<th>Habilitado:</th>
					<td>
						<INPUT type='checkbox' name='g_parestadistico_' value="1" onClick='valorarRepEst(document.frm);' <c:if test="${sessionScope.parametros.g_parestadistico==1}">checked</c:if>>
					</td>
				</tr>
			</table>
			<br>
			<table border="0" align="center" width="65%" cellpadding="0" cellspacing="5">
			<caption>Proceso de generación de Certificados y Constancias</caption>
				<tr>
					<th>Habilitado:</th>
					<td>
						<INPUT type='checkbox' name='g_parcertificado_' value="1" onClick='valorarCertCons(document.frm);' <c:if test="${sessionScope.parametros.g_parcertificado==1}">checked</c:if>>
					</td>
				</tr>
			</table>
			<br>
		<!-- NOTA: Este servicio fue reemplazado por el procedimiento almacenado ACTUALIZAR_MATRICULA 
		<table border="0" align="center" width="65%" cellpadding="0" cellspacing="5">
			<caption>Proceso de Integraci&oacute;n con Matriculas en Linea</caption>
				<tr>
					<th>Habilitado:</th>
					<td>
						<INPUT type='checkbox' name='g_parintegracion_' value="1" onClick='valorarInte(document.frm);' <c:if test="${sessionScope.parametros.g_parintegracion==1}">checked</c:if>>
					</td>
				</tr>
			</table>
			<br>			 
		 -->	
			<table border="0" align="center" width="65%" cellpadding="0" cellspacing="5">
			<caption>Proceso de Integraci&oacute;n con Matriculas  Automática</caption>
				<tr>
					<th>Habilitado:</th>
					<td>
						<INPUT type='checkbox' name='g_parintegracionMatr_' value="1" onClick='valorarInteMatr(document.frm);visible();' <c:if test="${sessionScope.parametros.g_parintegracionMatr == 1}">checked</c:if>>
						<INPUT type="hidden" name='g_parintegracionMatr' value='<c:out value="${sessionScope.parametros.g_parintegracionMatr}"/>'  >
					</td>
				</tr>
			 
				  <table style="display:none" border="0" id='tabla' align="center" width="60%" cellpadding="0" cellspacing="0">
				<tr>
					<td width="150px">D&iacute;a de Ejecuci&oacute;n:.</td>
					<td>
						<select name='g_parintegracionMatr_dia' style='width:100px;'>
							<option value="0" <c:if test="${sessionScope.parametros.g_parintegracionMatr_dia==0}">SELECTED</c:if>>Todos los dias</option>
							<option value="1" <c:if test="${sessionScope.parametros.g_parintegracionMatr_dia==1}">SELECTED</c:if>>Lunes</option>
							<option value="2" <c:if test="${sessionScope.parametros.g_parintegracionMatr_dia==2}">SELECTED</c:if>>Martes</option>
							<option value="3" <c:if test="${sessionScope.parametros.g_parintegracionMatr_dia==3}">SELECTED</c:if>>Mi&eacute;rcoles</option>
							<option value="4" <c:if test="${sessionScope.parametros.g_parintegracionMatr_dia==4}">SELECTED</c:if>>Jueves</option>
							<option value="5" <c:if test="${sessionScope.parametros.g_parintegracionMatr_dia==5}">SELECTED</c:if>>Viernes</option>
							<option value="6" <c:if test="${sessionScope.parametros.g_parintegracionMatr_dia==6}">SELECTED</c:if>>Sabado</option>
							<option value="7" <c:if test="${sessionScope.parametros.g_parintegracionMatr_dia==7}">SELECTED</c:if>>Domingo</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Hora de Ejecuci&oacute;n (0-23 horas):</td>
					<td>
						<select name='g_parintegracionMatr_hora' style='width:60px;'>
							<c:forEach begin="0" end="23" step="1" var="hora">
								<option value="<c:out value="${hora}"/>" <c:if test="${sessionScope.parametros.g_parintegracionMatr_hora==hora}">SELECTED</c:if>>
									<c:out value="${ hora}"/>
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			 	<tr>
			 	
			 	 <td colspan="2">
			 	   <table width="80%" border="1" align="center"><caption>Mensaje</caption><tr><td align="left"><c:if test="${ empty sessionScope.parametros.g_parintegracionMatr_msj }">No hay ningún mensaje</c:if> <c:out value="${sessionScope.parametros.g_parintegracionMatr_msj }"/></td></tr> 
			 	   </table>
			 	  </td>
			 	  </tr>
			</table>
				</tr>
			</table>
			<br>
		</form>
	</font>
		<script>
		valorarInteMatr(document.frm);
	  visible();
	</script>