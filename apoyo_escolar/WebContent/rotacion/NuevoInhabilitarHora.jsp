<%@ page language="java" errorPage="" %><%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="rotacion/ControllerFiltro.do?tipo=120"/></div>
	</td></tr>
</table>
<script languaje="javaScript">
<!--
	var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;
	function llenarBlqminimo(combo2,combo){
		var n=parseInt(combo2.selectedIndex)-1;
		var max;
		var k=0;
		borrar_combo(combo);
		if(n>=0){
			if(document.frmNuevo.numblq){
				if(document.frmNuevo.numblq.length){
					max=parseInt(document.frmNuevo.numblq[n].value)*parseInt(document.frmNuevo.numhor[n].value);
				}
				else{
					max=parseInt(document.frmNuevo.numblq.value)*parseInt(document.frmNuevo.numhor.value);
				}
			}
			var aux;
			for(var i=0;i<max;i++){
				<c:if test="${sessionScope.rotacion.estado==1}">aux='<c:out value="${sessionScope.rotacion.rotihhoraini}"/>';</c:if>
				<c:if test="${sessionScope.rotacion.estado!=1}">
					aux=-1;
				</c:if>
				if(parseInt(aux)==parseInt(i+1)){
					combo.options[k+1] = new Option(i+1,i+1,"SELECTED");
					combo.selectedIndex = k+1;
				}
				else{
					combo.options[k+1] = new Option(i+1,i+1);
				}
				k++;
			}
		}
	}
	
	function llenarBlqminimo2(combo2,combo){
		var n=parseInt(combo2.selectedIndex)-1;
		var max;
		var k=0;
		borrar_combo(combo);
		if(n>=0){
			if(document.frmNuevo.numblq){
				if(document.frmNuevo.numblq.length){
					max=parseInt(document.frmNuevo.numblq[n].value)*parseInt(document.frmNuevo.numhor[n].value);
				}
				else{
					max=parseInt(document.frmNuevo.numblq.value)*parseInt(document.frmNuevo.numhor.value);
				}
			}
			var aux;
			for(var i=0;i<max;i++){
				<c:if test="${sessionScope.rotacion.estado==1}">
					aux='<c:out value="${sessionScope.rotacion.rotihhorafin}"/>';
				</c:if>
				<c:if test="${sessionScope.rotacion.estado!=1}">
					aux=-1;
				</c:if>
				if(parseInt(aux)==parseInt(i+1)){
					combo.options[k+1] = new Option(i+1,i+1,"SELECTED");
					combo.selectedIndex = k+1;
				}
				else{
					combo.options[k+1] = new Option(i+1,i+1);
				}
				k++;
			}
		}
	}

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--","-1");
	}
	
	var nav4=window.Event ? true : false;

	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function nuevo(){
		document.frmNuevo.tipo.value=126;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.tipo.value=121;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.rotihestructura, "- Seleccione una estructura",1)
		validarLista(forma.rotihdia, "- Seleccione una dia", 1)
		validarLista(forma.rotihhoraini, "- Hora de inicio", 1)
		validarLista(forma.rotihhorafin, "- Hora de fin", 1)
		validarCampo(forma.rotihcausa, "- Causa", 1, 50)
	}

	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}
//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action="<c:url value="/rotacion/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
			<input type="hidden" name="vigencia" value='<c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/>'>
			<table align="center" width="100%">
				<tr>
				  <td width="45%" align="left">
					<script>
					if(locked==0){
					  document.write('<input name="cmd2" class="boton" type="button" value="Guardar" onClick="guardar()">&nbsp;');
					  document.write('<input name="cmd1" class="boton" type="button" value="Nuevo" onClick="nuevo()">');
					}
					</script>
					  <c:if test="${sessionScope.isLockedRotacion==1}">
						  <span class="style2">Nota: Información de solo lectura. Proceso de rotación pendiente por aprobar o rechazar</span>
					  </c:if>
					</td>
				</tr>
  	  </table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>Inhabilitar Horas</caption>
				<tr>
					<td><span class="style2" >*</span>Estructura:</td>
			    <td>
						<select name="rotihestructura" onChange='llenarBlqminimo(document.frmNuevo.rotihestructura,document.frmNuevo.rotihhoraini);llenarBlqminimo2(document.frmNuevo.rotihestructura,document.frmNuevo.rotihhorafin)' style='width:150px;'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila">
								<option value="<c:out value="${fila.estCodigo}"/>" <c:if test="${sessionScope.rotacion.rotihestructura == fila.estCodigo}">SELECTED</c:if>><c:out value="${fila.estNombre}"/></option>
							</c:forEach>
						</select>
						<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila">
							<input type="hidden" name="numblq" value="<c:out value="${fila.estBloqueXJornada}"/>">
							<input type="hidden" name="numhor" value="<c:out value="${fila.estDurBloque}"/>">
						</c:forEach>
			   </td>
			    <td><span class="style2">*</span>D&iacute;a:</td>
					<td>
						<select name="rotihdia">
							<option value='-1'>-- Seleccione uno --</option>
							<option value='1' <c:if test="${sessionScope.rotacion.rotihdia == 1}">SELECTED</c:if>>Lunes</option>
							<option value='2' <c:if test="${sessionScope.rotacion.rotihdia == 2}">SELECTED</c:if>>Martes</option>
							<option value='3' <c:if test="${sessionScope.rotacion.rotihdia == 3}">SELECTED</c:if>>Miercoles</option>
							<option value='4' <c:if test="${sessionScope.rotacion.rotihdia == 4}">SELECTED</c:if>>Jueves</option>
							<option value='5' <c:if test="${sessionScope.rotacion.rotihdia == 5}">SELECTED</c:if>>Viernes</option>
						</select>
					</td>			    
					<td><span class="style2">*</span>Hora Inicial:</td>
					<td><select name="rotihhoraini"><option value='-1'>--</option></select></td>
					<td><span class="style2">*</span>Hora Final:&nbsp;&nbsp;
					<select name="rotihhorafin"><option value='-1'>--</option></select></td>
				</tr>
				<tr>
					<td><span class="style2">*</span>Motivo:</td>
					<td colspan=7><textarea name="rotihcausa" cols="80" rows="2"><c:if test="${sessionScope.rotacion.estado==1}"><c:out value="${sessionScope.rotacion.rotihcausa}"/></c:if></textarea></td>
				</tr>
				<tr valign="top">
					<td><span class="style2" >*</span>Vigencia:</td>
					<td><c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/></td>
				</tr>
  	  </table>
 	  </form>
  </font>
  <script>
	  <c:if test="${sessionScope.rotacion.estado==1}">
		  llenarBlqminimo(document.frmNuevo.rotihestructura,document.frmNuevo.rotihhoraini);llenarBlqminimo2(document.frmNuevo.rotihestructura,document.frmNuevo.rotihhorafin)
  	</c:if>
  </script>