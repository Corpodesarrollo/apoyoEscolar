<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/><jsp:setProperty name="horario" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="21" scope="page"/>
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
			
			function hacerValidaciones_frm(forma){
			}
			function ver(){
					document.frm.tipo.value=15;
					document.frm.cmd.value="Ver";
					document.frm.submit();
			}
			
			function lanzar(tipo){
				document.frm.tipo.value=tipo;
				document.frm.action='<c:url value="/rotacion2/ControllerEditar.do"/>';
				document.frm.target="";
				document.frm.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frm)){
					document.frm.tipo.value=tipo;
					document.frm.cmd.value="Guardar";
					document.frm.send.value="1";
					document.frm.ext2.value='/rotacion2/ControllerEditar.do?tipo=13';
					document.frm.submit();
				}
			}

			function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
			
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-- Seleccione uno --","-1");
			}
			
			//-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/rotacion2/NuevoGuardar.jsp"/>'>
	<br>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Ver inconsistencias</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
				<input class='boton' name="cmd1" type="button" value="Aceptar" onClick="ver()">
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
	<input type="hidden" name="ext2" value=''>
	<INPUT TYPE="hidden" NAME="height" VALUE='140'>	
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
				<td rowspan="2" width= bgcolor="#FFFFFF"><a href="javaScript:lanzar(13)"><img src="../etc/img/tabs/horario_grupo_0.gif" alt=""  height="26" border="0"></a><img src="../etc/img/tabs/inconsistencias_generales_1.gif" alt="Grupo"  height="26" border="0"><a href="javaScript:lanzar(41)"><img src="../etc/img/tabs/horario_asignatura_0.gif" alt=""  height="26" border="0"></a></td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
  <br>
				<TABLE width="98%" align='center' cellpadding="1" cellSpacing="0" border='1'>
				<tr><th colspan="6" class="EncabezadoColumna">INCONSISTENCIAS</th></tr>
								<c:if test="${requestScope.listaIncGral!=null}">
										<tr><th class="EncabezadoColumna" width="10%">&nbsp;</th><th class="EncabezadoColumna">Mensaje</th><th class="EncabezadoColumna">Valor</th></tr>
										<c:forEach begin="0" items="${requestScope.listaIncGral}" var="fila" varStatus="st"><tr>
											<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></td>
											<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[0]}"/></td>
											<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
										</tr></c:forEach>
								</c:if>
								</TABLE>
</form>