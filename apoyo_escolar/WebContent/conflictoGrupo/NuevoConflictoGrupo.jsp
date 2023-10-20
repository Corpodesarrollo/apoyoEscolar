<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="nuevoConflictoGrupo" class="siges.conflictoGrupo.beans.ConflictoGrupo" scope="session"/><jsp:setProperty name="nuevoConflictoGrupo" property="*"/>
<jsp:useBean id="nuevoConflictoFiltro" class="siges.conflictoGrupo.beans.ConflictoFiltro" scope="session"/><jsp:setProperty name="nuevoConflictoFiltro" property="*"/>
<%@include file="../parametros.jsp"%>
	<script languaje='javaScript'>
		<!--
		  var nav4=window.Event ? true : false;

		  function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
		  }
			
			function cancelar(){
				//if (confirm('¿DESEA CANCELAR LA INSERCIÓN DE UN NUEVO REGISTRO?')){
					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.submit();
				//}
			}
			
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
			
			function lanzar(tipo,cat){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.categoria.value=cat;
				if(tipo==0)	document.frmNuevo.action="<c:url value="/conflictoGrupo/ControllerNuevo.do"/>";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo,cat){
				if(validar1(document.frmNuevo)){
					if(validar2(document.frmNuevo)){
						document.frmNuevo.tipo.value=tipo;
						document.frmNuevo.categoria.value=cat;
						document.frmNuevo.cmd.value="Guardar";
						document.frmNuevo.submit();
					}
				}
			}
			
			function validar2(forma){
				var num1,num2,cont1=0,cont2=0;
				<c:forEach begin="0" items="${requestScope.filtroTipo}" var="fila">
					num1=parseInt(document.frmNuevo.cg<c:out value="${fila[0]}"/>.value)
					num2=parseInt(document.frmNuevo.cge<c:out value="${fila[0]}"/>.value)
					if(!isNaN(num1) && isNaN(num2)){
						cont1++;
					}
					if(isNaN(num1) && !isNaN(num2)){
						cont2++;
					}
				</c:forEach>
				if(cont1>0 && cont2==0){
					alert("- Debe definir el número de eventos si definió la cantidad de estudiantes")
					return false;
				}else if(cont1==0 && cont2>0){
					alert("- Debe definir la cantidad de estudiantes si definió el número de eventos")
					return false;
				}else if(cont1>0 && cont2>0){
					alert("- Debe definir el número de eventos si definió la cantidad de estudiantes \n - Debe definir la cantidad de estudiantes si definió el número de eventos")
					return false;
				}else{
					return true;
				}
			}
			
			function validar1(forma){
				var cont=0,na,cp;
				<c:forEach begin="0" items="${requestScope.filtroTipo}" var="fila">
					na=parseInt(document.frmNuevo.cg<c:out value="${fila[0]}"/>.value);
					cp=parseInt(document.frmNuevo.cupo.value);
					if(na>cp){
						cont++;
					}
				</c:forEach>
				if(cont==0){
					return true;
				}
				else{
					alert("- El número de alumnos excede el limite del cupo para este grupo")
					return false;
				}
			}
		-->
	</script>
	<%@include file="../mensaje.jsp"%>
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/conflictoGrupo/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
				<td width="45%" bgcolor="#FFFFFF">
				<c:if test="${requestScope.readonly!=1}">
	        <input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(11,-1)">
        </c:if>
				</td>
			</tr>		
		</table>
	<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value="">
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value=''>
	<input type="hidden" name="categoria" value=''>
	<input type="hidden" name="readonly" value='<c:out value="${requestScope.readonly}"/>'>
	<input type="hidden" name="jergrupo" value='<c:out value="${requestScope.jergrupo}"/>'>
	<input type="hidden" name="cupo" value='<c:out value="${sessionScope.nuevoConflictoGrupo.cupo}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>			
				<td rowspan="2" width="600" bgcolor="#FFFFFF">
					<img src="../etc/img/tabs/confilcto_escolar_grupo_1.gif" alt="Filtro" border="0"  height="26">
				</td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		<table cellpadding="0" cellspacing="0" border="0" width="95%" align="center">
		<caption><c:if test="${sessionScope.nuevoConflictoGrupo.cupo!=-99}">N&uacute;mero de Alumnos en este grupo: <c:out value="${sessionScope.nuevoConflictoGrupo.cupo}"/></c:if></caption>
			<tr>
				<td>
					<c:forEach begin="0" items="${requestScope.filtroClase}" var="fila">
						<TABLE align="center" width="90%" cellpadding="0" cellSpacing="5" border="0">
						<caption></caption>
							<tr>
								<th><c:out value="${fila[1]}"/></th>
								<th width="" align="center">N&uacute;mero de Alumnos</th>
								<th width="" align="center">N&uacute;mero de Eventos</th>
								<th width="" align="center">Género</th>
							</tr>
							
							<c:forEach begin="0" items="${requestScope.filtroTipo}" var="fila2">
								<c:if test="${fila[0]==fila2[3]}">
									<tr>
										<td title=""><c:out value="${fila2[1]}"/></td>
										<td id="uno" name="uno" colspan="" align="center" style="display:"><input type="text" name="cg<c:out value="${fila2[0]}"/>" onKeyPress='return acepteNumeros(event)' maxlength="3" size="3" <c:if test="${requestScope.readonly==1}">readonly</c:if>/></td>
										<td id="dos" name="dos" colspan="" align="center" style="display:"><input type="text" name="cge<c:out value="${fila2[0]}"/>" onKeyPress='return acepteNumeros(event)' maxlength="3" size="3" <c:if test="${requestScope.readonly==1}">readonly</c:if>/></td>
										<td id="tres" name="tres" colspan="" align="center" style="display:">
											<select name="cgee<c:out value="${fila2[0]}"/>" <c:if test="${requestScope.readonly==1}">readonly</c:if> >
												<option value='-9'> -- </option>
												<option value='0'> Niñas </option>
												<option value='1'> Niños </option>
												<option value='2'> Ambos </option>
											</select>		  
										 </td>
									</tr>
								</c:if>
							</c:forEach>
							
							<tr><td colspan="3"><hr/></td></tr>
						</table>
				</c:forEach>
				</td>								
	  </table>
<!--//////////////////////////////-->
</form>
<script>
<c:forEach begin="0" items="${sessionScope.nuevoConflictoGrupo.numalumnos}" var="fila">
	if(document.frmNuevo.<c:out value="${fila[0]}"/>)
		document.frmNuevo.<c:out value="${fila[0]}"/>.value='<c:out value="${fila[1]}"/>';
</c:forEach>
<c:forEach begin="0" items="${sessionScope.nuevoConflictoGrupo.numeventos}" var="fila2">
	if(document.frmNuevo.<c:out value="${fila2[0]}"/>)
		document.frmNuevo.<c:out value="${fila2[0]}"/>.value='<c:out value="${fila2[1]}"/>';
</c:forEach>

<c:forEach begin="0" items="${sessionScope.nuevoConflictoGrupo.genero}" var="fila3">
	var combo=document.frmNuevo.<c:out value="${fila3[0]}"/>;
	if(combo){
	  for(var i=0;i<combo.length;i++){
	    //alert(combo.length);
		if(combo.options[i].value == '<c:out value="${fila3[1]}"/>'){
			//alert('entro al if -'+combo.options[i].value);
			combo.selectedIndex=i;
		}
	  }
	}
</c:forEach>

</script>