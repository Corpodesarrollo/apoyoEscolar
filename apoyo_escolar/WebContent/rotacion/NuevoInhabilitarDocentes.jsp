<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="rotacion/ControllerFiltro.do?tipo=50"/></div>
	</td></tr>
</table>
<script languaje="javaScript">
<!--
	var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;
	function llenarBlqminimo(combo2,combo){
		var n=parseInt(combo2.selectedIndex)-1;
		var max;
		var k=0;
		borrar_combo2(combo);
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
					aux='<c:out value="${sessionScope.rotacion.rotihdhoraini}"/>';
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
	
	function llenarBlqminimo2(combo2,combo){
		var n=parseInt(combo2.selectedIndex)-1;
		var max;
		var k=0;
		borrar_combo2(combo);
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
					aux='<c:out value="${sessionScope.rotacion.rotihdhorafin}"/>';
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
		combo.options[0] = new Option("-- Seleccione uno --","-1");
	}
	
	function borrar_combo2(combo){
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
		document.frmNuevo.tipo.value=56;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.tipo.value=51;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.rotihdestructura, "- Seleccione una estructura",1)
		validarLista(forma.rotihddocente, "- Seleccione un docente",1)
		validarLista(forma.rotihddia, "- Seleccione una dia", 1)
		validarLista(forma.rotihdhoraini, "- Hora de inicio", 1)
		validarLista(forma.rotihdhorafin, "- Hora de fin", 1)
		validarCampo(forma.rotihdcausa, "- Causa", 1, 50)
	}

	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	function filtro2(combo_padre){
		<c:if test="${!empty requestScope.listaEstructura}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila" varStatus="st">
				id_Hijos=new Array();Hijos= new Array();Sel_Hijos= new Array();id_Padre= new Array();
				var k=0;Sel_Hijos[k] = '';
				id_Hijos[k] = '<c:out value="${fila.estSede}"/>';Hijos[k] = '<c:out value="${fila.estJornada}"/>';id_Padre[k] = '<c:out value="${fila.estCodigo}"/>';k++;
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>var niv=combo_padre.options[combo_padre.selectedIndex].value;
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){
				var id=Padres[val_padre].id_Hijos[0];
				var id2=Padres[val_padre].Hijos[0];
				document.frmNuevo.rotihdsede.value=id;
				document.frmNuevo.rotihdjornada.value=id2;
			}
		</c:if>
	}
	
	function filtro4(combo_hijo){
		borrar_combo(combo_hijo);
		var id=0;
		var seddoc;
		var jordoc;
		<c:if test="${!empty requestScope.filtroDocente}">
			var sede_=document.frmNuevo.rotihdsede.value;
			var jornada_=document.frmNuevo.rotihdjornada.value;
			var Padres = new Array();
			id_Hijos=new Array();
			Hijos = new Array();
			Sel_Hijos= new Array();
			id_Padre= new Array();
			var k=0;
			<c:forEach begin="0" items="${requestScope.filtroDocente}" var="fila2">
				seddoc='<c:out value="${fila2[2]}"/>';
				jordoc='<c:out value="${fila2[3]}"/>';
				if(seddoc==sede_ && jordoc==jornada_){
					Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion.rotihddocente == fila2[0]}">SELECTED</c:if>';
					id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
					Hijos[k] = '<c:out value="${fila2[1]}"/>';
					id_Padre[k] = '<c:out value="${fila2[2]}"/>|<c:out value="${fila2[3]}"/>';
					k++;
				}
			</c:forEach>
			Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			//var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value;
			var niv=sede_+'|'+jornada_;
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){
				var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
						combo_hijo.selectedIndex = i+1;
					}else	combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>
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
			<input type="hidden" name="rotstrmetodologia" value='<c:out value="${sessionScope.filtroRotacion.filMetodologia}"/>'>
			<input type="hidden" name="rotihdsede" value="<c:out value="${sessionScope.rotacion.rotihdsede}"/>">
			<input type="hidden" name="rotihdjornada" value="<c:out value="${sessionScope.rotacion.rotihdjornada}"/>">
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
				<caption>Inhabilitar Docentes</caption>
				<tr>
					<td><span class="style2" >*</span>Estructura:</td>
			    <td>
						<select name="rotihdestructura" onChange='llenarBlqminimo(document.frmNuevo.rotihdestructura,document.frmNuevo.rotihdhoraini);llenarBlqminimo2(document.frmNuevo.rotihdestructura,document.frmNuevo.rotihdhorafin);filtro2(document.frmNuevo.rotihdestructura);filtro4(document.frmNuevo.rotihddocente)' style='width:200px;'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila">
								<option value="<c:out value="${fila.estCodigo}"/>" <c:if test="${sessionScope.rotacion.rotihdestructura == fila.estCodigo}">SELECTED</c:if>><c:out value="${fila.estNombre}"/></option>
							</c:forEach>
						</select>
						<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila">
							<input type="hidden" name="numblq" value="<c:out value="${fila.estBloqueXJornada}"/>">
							<input type="hidden" name="numhor" value="<c:out value="${fila.estDurBloque}"/>">
						</c:forEach>
			   </td>
					<td><span class="style2" >*</span>Docente:</td>
			    <td>
						<select name="rotihddocente" style='width:200px;'>
							<option value='-1'>-- Seleccione uno --</option>
						</select>
			    </td>
				</tr>
				<tr>
		    	<td><span class="style2">*</span>D&iacute;a:</td>
					<td>
						<select name="rotihddia">
							<option value='-1'>-- Seleccione uno --</option>
							<option value='1' <c:if test="${sessionScope.rotacion.rotihddia == 1}">SELECTED</c:if>>Lunes</option>
							<option value='2' <c:if test="${sessionScope.rotacion.rotihddia == 2}">SELECTED</c:if>>Martes</option>
							<option value='3' <c:if test="${sessionScope.rotacion.rotihddia == 3}">SELECTED</c:if>>Miercoles</option>
							<option value='4' <c:if test="${sessionScope.rotacion.rotihddia == 4}">SELECTED</c:if>>Jueves</option>
							<option value='5' <c:if test="${sessionScope.rotacion.rotihddia == 5}">SELECTED</c:if>>Viernes</option>
						</select>
					</td>
					<td><span class="style2">*</span>Hora Inicial:&nbsp;&nbsp;
						<select name="rotihdhoraini"><option value='-1'>--</option></select>
					</td>
					<td><span class="style2">*</span>Hora Final:&nbsp;&nbsp;
						<select name="rotihdhorafin"><option value='-1'>--</option></select>
					</td>
				</tr>
				<tr>
					<td><span class="style2">*</span>Motivo:</td>
					<td colspan=3>
						<textarea name="rotihdcausa" cols="80" rows="2"><c:if test="${sessionScope.rotacion.estado==1}"><c:out value="${sessionScope.rotacion.rotihdcausa}"/></c:if></textarea>
					</td>
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
		  llenarBlqminimo(document.frmNuevo.rotihdestructura,document.frmNuevo.rotihdhoraini);llenarBlqminimo2(document.frmNuevo.rotihdestructura,document.frmNuevo.rotihdhorafin);
			filtro4(document.frmNuevo.rotihddocente);
  	</c:if>
  </script>