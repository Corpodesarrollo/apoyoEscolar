<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
	<script language='javaScript'>
	<!--
		var visibilidad=0;

		function visible(){
			if(visibilidad==1 && document.getElementById('tabla')){
				visibilidad=0;
				document.getElementById('tabla').style.display='none';				
			}
			else if(visibilidad==0 && document.getElementById('tabla')){
				visibilidad=1;
				document.getElementById('tabla').style.display='';
			}
		}
		var nav4=window.Event ? true : false;

		function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}

		function hacerValidaciones_frmIbatch(frmIbatch){

		}

		function lanzar(tipo){			
			document.frmIbatch.tipo.value=tipo;
			document.frmIbatch.submit();
		}

		function guardar(tipo){
			if(validarForma(document.frmIbatch)){
				document.frmIbatch.tipo.value=tipo;
				document.frmIbatch.cmd.value="Guardar";
				document.frmIbatch.submit();
			}
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
		
		function valorar(forma){
       if(forma.g_parimp_activo_.checked) forma.g_parimp_activo.value='1';
       else forma.g_parimp_activo.value='0';
		}
	//-->
	</script>
	<%@include file="../mensaje.jsp" %>
	<font size='1'>
		<form method="post" name="frmIbatch"  onSubmit="return validarForma(frmIbatch)" action='<c:url value="/parametro/NuevoGuardar.jsp"/>'>
			<input type="hidden" name="tipo" value="1">
			<input type="hidden" name="cmd" value="Cancelar">
			<input type="hidden" name="g_parimp_activo" value="<c:out value="${sessionScope.parametros.g_parpla_activo}"/>">
			<table border="0" align="center" width="100%">
				<tr>
		  		<td width="45%">
            <input name="cmd1" type="button" value="Guardar" onClick="guardar(-5)">
			  	</td>
				</tr>
	  	</table>

			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
					<td rowspan="2" width="469" bgcolor="#FFFFFF">
						<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/conf_general_0.gif" alt="Parámetros Generales" height="26" border="0"></a>
						<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/recolector_0.gif" alt="Recolector" height="26" border="0"></a>
						<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/plantillas_batch_0.gif" alt="Plantillas Batch" height="26" border="0"></a>
						<img src="../etc/img/tabs/importacion_batch_1.gif" alt="Importación Batch" height="26" border="0">
					</td>
				</tr>
			</table>
			<br>
			<table border="0" align="center" width="80%" cellpadding="0" cellspacing="0">
				<tr>
					<td>Habilitar:</td>
					<td>
						<INPUT type='checkbox' value="1" onClick='valorar(document.frmIbatch);visible()' name='g_parimp_activo_' <c:if test="${sessionScope.parametros.g_parimp_activo==1}">checked</c:if>>
					</td>
				</tr>
			</table>
			
			<table style="display:none" id='tabla' align="center" width="80%" cellpadding="0" cellspacing="0">
				<tr>
					<td>D&iacute;a de Ejecuci&oacute;n:</td>
					<td>
						<select name='g_parimp_dia' style='width:250px;'>
							<option value="0" <c:if test="${sessionScope.parametros.g_parimp_dia==0}">SELECTED</c:if>>Todos los dias</option>
							<option value="1" <c:if test="${sessionScope.parametros.g_parimp_dia==1}">SELECTED</c:if>>Lunes</option>
							<option value="2" <c:if test="${sessionScope.parametros.g_parimp_dia==2}">SELECTED</c:if>>Martes</option>
							<option value="3" <c:if test="${sessionScope.parametros.g_parimp_dia==3}">SELECTED</c:if>>Mi&eacute;rcoles</option>
							<option value="4" <c:if test="${sessionScope.parametros.g_parimp_dia==4}">SELECTED</c:if>>Jueves</option>
							<option value="5" <c:if test="${sessionScope.parametros.g_parimp_dia==5}">SELECTED</c:if>>Viernes</option>
							<option value="6" <c:if test="${sessionScope.parametros.g_parimp_dia==6}">SELECTED</c:if>>Sabado</option>
							<option value="7" <c:if test="${sessionScope.parametros.g_parimp_dia==7}">SELECTED</c:if>>Domingo</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Hora de Ejecuci&oacute;n (0-23 horas):</td>
					<td>
						<select name='g_parimp_hora' style='width:250px;'>
							<c:forEach begin="0" end="23" step="1" var="ghora">
								<option value="<c:out value="${ghora}"/>" <c:if test="${sessionScope.parametros.g_parimp_hora==ghora}">SELECTED</c:if>>
									<c:out value="${ghora}"/>
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Tiempo de espera para consultar (minutos):</td>
					<td>
						<select name='g_parimp_sleep' style='width:250px;'>
							<c:forEach begin="1" end="55" step="1" var="ghilo">
								<option value="<c:out value="${ghilo}"/>" <c:if test="${sessionScope.parametros.g_parimp_sleep==ghilo}">SELECTED</c:if>>
									<c:out value="${ghilo}"/>
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</font>
	<script>
		if(document.frmIbatch.g_parimp_activo_.checked==true)	visible()
	</script>