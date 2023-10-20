<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/><jsp:setProperty name="horario" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="42" scope="page"/>
<script languaje='javaScript'>
		<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			
			function remotoEstados(){
				remote = window.open('<c:url value="/rotacion2/ControllerEditar.do?tipo=31&ext=1&servTarget=3"/>',"3","width=700,height=550,resizable=no,toolbar=no,directories=no,menubar=no,status=no,scrollbars=yes");
				if (remote.opener == null) remote.opener = window;
				remote.opener.name = "centro";
				remote.focus();
			}
			
			function guardar(tipo){
				if(validarForma(document.frm)){
					document.frm.tipo.value=tipo;
					document.frm.cmd.value="Guardar";
					document.frm.send.value="1";
					document.frm.ext2.value='/rotacion2/ControllerEditar.do?tipo=41';
					document.frm.submit();
				}
			}
			
			function ver(){
					document.frm.tipo.value=15;
					document.frm.cmd.value="Ver";
					document.frm.submit();
			}
			
			function hacerValidaciones_frm(forma){
				validarLista(forma.sede,"- Sede",1)
				validarLista(forma.jornada,"- Jornada",1)
				validarLista(forma.grado,"- Grado",1)
			}
			
			function lanzar(tipo){
				document.frm.tipo.value=tipo;
				document.frm.action='<c:url value="/rotacion2/ControllerEditar.do"/>';
				document.frm.target="";
				document.frm.submit();
			}
			
			function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			
			//-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/rotacion2/NuevoGuardar.jsp"/>'>
	<br>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Ver horario por asignatura</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
				<input class='boton' name="cmd1" type="button" value="Aceptar" onClick="guardar(document.frm.tipo.value)">
				<input name="cmd12" class="boton" type="button" value="Estado Grupos" onClick="remotoEstados()">
				<input name="cmd12" class="boton" type="button" value="Ver Solicitudes" onClick="ver()">
		  </td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="send" value="">
	<input type="hidden" name="jer" value='<c:out value="${sessionScope.horario.jerGrupo}"/>'>
	<input type="hidden" name="ext2" value=''>
	<INPUT TYPE="hidden" NAME="height" VALUE='140'>	
	<input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
	<input type="hidden" name="sede" value='<c:out value="${sessionScope.horario.sede}"/>'>
	<input type="hidden" name="jornada" value='<c:out value="${sessionScope.horario.jornada}"/>'>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>					
			<td rowspan="2" width="420" bgcolor="#FFFFFF"><a href="javaScript:lanzar(13)"><img src="../etc/img/tabs/horario_grupo_0.gif" alt=""  height="26" border="0"></a><a href="javaScript:lanzar(17)"><img src="../etc/img/tabs/inconsistencias_generales_0.gif" alt=""  height="26" border="0"></a><img src="../etc/img/tabs/horario_asignatura_1.gif" alt="Asignatura"  height="26" border="0"></td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE width="100%" cellpadding="3" cellSpacing="0">
		<tr>
			  <td><span class="style2" >*</span>Grado:</td>
			  <td>
           <select name="grado" style='width:150px;'>
           <option value='-1'>-- Seleccione uno --</option>
					 <c:forEach begin="0" items="${requestScope.filtroGradoR2}" var="fila"><option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.horario.grado== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
           </select>
           </td>
		</tr>
	  </TABLE>
</form>