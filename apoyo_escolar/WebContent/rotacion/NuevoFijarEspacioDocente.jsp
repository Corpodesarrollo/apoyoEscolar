<%@ page language="java" errorPage=""%>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="rotacion/ControllerFiltro.do?tipo=70"/></div>
	</td></tr>
</table>
<script languaje="javaScript">
<!--
	var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;

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
		document.frmNuevo.tipo.value=76;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.tipo.value=71;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.rtefdosede, "- Seleccione una sede",1)
		validarLista(forma.rtefdojornada, "- Seleccione una jornada",1)
		validarLista(forma.rtefdoespacio, "- Seleccione un espacio físico",1)
		validarLista(forma.rtefdodocente, "- Seleccione un docente",1)
	}

	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	//rtefdosede,rtefdojornada,metodologia,rtefdoespacio,rtefdodocente
	function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
		borrar_combo(combo_hijo); borrar_combo(combo_hijo3); borrar_combo(combo_hijo4); 
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
				id_Hijos=new Array();
				Hijos= new Array();
				Sel_Hijos= new Array();
				id_Padre= new Array();
				var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
					<c:if test="${fila[0]==fila2[2]}">
						Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion.rtefdojornada == fila2[0]}">SELECTED</c:if>';
						id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
						Hijos[k] = '<c:out value="${fila2[1]}"/>';
						id_Padre[k] = '<c:out value="${fila2[2]}"/>';
						k++;
					</c:if>
				</c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
			var niv=combo_padre.value;
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
		//filtro3(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4)
	}
	
	//rtefdosede, rtefdojornada,metodologia,rtefdoespacio,rtefdodocente
	function filtro3(combo_padre,combo_padre2,combo_hijo2,combo_hijo3,combo_hijo4){
		var id=0;
		borrar_combo(combo_hijo4);
		<c:if test="${!empty requestScope.filtroJornadaF && !empty requestScope.filtroEspFis}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila" varStatus="st">
				id_Hijos=new Array();Hijos= new Array();Sel_Hijos= new Array();id_Padre= new Array();var k=0;
				<c:forEach begin="0" items="${requestScope.filtroEspFis}" var="fila2">
					<c:if test="${fila[2]==fila2[0] && fila[0]==fila2[4]}">
						Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion.rtefdoespacio == fila2[1]}">SELECTED</c:if>';
						id_Hijos[k] = '<c:out value="${fila2[1]}"/>';
						Hijos[k] = '<c:out value="${fila2[2]}"/>';
						id_Padre[k] = '<c:out value="${fila2[0]}"/>|<c:out value="${fila2[4]}"/>';
						k++;
					</c:if>
				</c:forEach>
				Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
			if(combo_padre.options){
				var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value
			}else{
				var niv=combo_padre.value+'|'+combo_padre2.value;
			}
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){
				var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo3.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
						combo_hijo3.selectedIndex = i+1;
					}else	combo_hijo3.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>
	}

	//rtefdosede, rtefdojornada,metodologia,rtefdoespacio,rtefdodocente
	function filtro4(combo_padre,combo_padre2,combo_hijo2,combo_hijo3,combo_hijo4){
		borrar_combo(combo_hijo4);
		var id=0;
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroDocente}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila1">
				id_Hijos=new Array();
				Hijos = new Array();
				Sel_Hijos= new Array();
				id_Padre= new Array();
				var k=0;
					<c:forEach begin="0" items="${requestScope.filtroDocente}" var="fila2">
						<c:if test="${fila[0]==fila1[2] && fila[0]==fila2[2] && fila1[0]==fila2[3] && fila1[2]==fila2[2]}">
							//alert('<c:out value="${fila2[1]}"/>'+" , "+k+" , "+'<c:out value="${fila2[2]}"/>|<c:out value="${fila2[3]}"/>')
							Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion.rtefdodocente == fila2[0]}">SELECTED</c:if>';
							id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
							Hijos[k] = '<c:out value="${fila2[1]}"/>';
							id_Padre[k] = '<c:out value="${fila2[2]}"/>|<c:out value="${fila2[3]}"/>';
							k++;
						</c:if>
					</c:forEach>
					Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
				</c:forEach>
			</c:forEach>
			if(combo_padre.options){
				var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value;
			}else{
				var niv=combo_padre.value+'|'+combo_padre2.value;
			}
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){
				var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo4.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
						combo_hijo4.selectedIndex = i+1;
					}else	combo_hijo4.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
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
			<input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
			<input type="hidden" name="rtefdojercodigo" value="<c:out value="${sessionScope.rotacion.rtefdojercodigo}"/>">
			<input type="hidden" name="vigencia" value='<c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/>'>
			<c:if test="${sessionScope.login.sedeId!='' && sessionScope.login.jornadaId!=''}">
				<input type="hidden" name="rtefdosede" value="<c:out value="${sessionScope.login.sedeId}"/>">
				<input type="hidden" name="rtefdojornada" value="<c:out value="${sessionScope.login.jornadaId}"/>">
			</c:if>

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
				<caption>Fijar Espacios F&iacute;sicos</caption>
				<tr>
					<c:if test="${sessionScope.login.sedeId!='' && sessionScope.login.jornadaId!=''}">
						<td>Sede: </td><td><c:out value="${sessionScope.login.sede}"/></td>
						<td>Jornada: </td><td><c:out value="${sessionScope.login.jornada}"/></td>
					</c:if>
					<c:if test="${sessionScope.login.sedeId=='' && sessionScope.login.jornadaId==''}">
						<td><span class="style2" >*</span>Sede:</td>
				    <td>
							<select name="rtefdosede" onChange='filtro(document.frmNuevo.rtefdosede, document.frmNuevo.rtefdojornada,document.frmNuevo.metodologia,document.frmNuevo.rtefdoespacio,document.frmNuevo.rtefdodocente)' style='width:300px;'>
								<option value='-1'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
									<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.rotacion.rtefdosede == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
								</c:forEach>
							</select>
				   </td>
						<td><span class="style2" >*</span>Jornada:</td>
					  <td>
							<select name="rtefdojornada" style='width:150px;' onChange='filtro3(document.frmNuevo.rtefdosede, document.frmNuevo.rtefdojornada,document.frmNuevo.metodologia,document.frmNuevo.rtefdoespacio,document.frmNuevo.rtefdodocente);filtro4(document.frmNuevo.rtefdosede, document.frmNuevo.rtefdojornada,document.frmNuevo.metodologia,document.frmNuevo.rtefdoespacio,document.frmNuevo.rtefdodocente)'>
								<option value='-1'>-- Seleccione uno --</option>
							</select>
					  </td>
					 </c:if>
				</tr>
				<tr>
					<td><span class="style2" >*</span>Espacio F&iacute;sico:</td>
			    <td>
						<select name="rtefdoespacio" onChange='' style='width:200px;'>
							<option value='-1'>-- Seleccione uno --</option>
						</select>
			    </td>
					<td><span class="style2" >*</span>Docente:</td>
			    <td>
						<select name="rtefdodocente" onChange='' style='width:200px;'>
							<option value='-1'>-- Seleccione uno --</option>
						</select>
			    </td>
				</tr>
				<tr>
					<td><span class="style2" >*</span>Vigencia:</td><td><c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/></td>
				</tr>
  	  </table>
 	  </form>
  </font>
  <script>
		<c:if test="${sessionScope.rotacion.estado==1}">
			<c:if test="${sessionScope.login.sedeId=='' && sessionScope.login.jornadaId==''}">
		  	filtro(document.frmNuevo.rtefdosede, document.frmNuevo.rtefdojornada,document.frmNuevo.metodologia,document.frmNuevo.rtefdoespacio,document.frmNuevo.rtefdodocente)
				filtro3(document.frmNuevo.rtefdosede, document.frmNuevo.rtefdojornada,document.frmNuevo.metodologia,document.frmNuevo.rtefdoespacio,document.frmNuevo.rtefdodocente)
		  	filtro4(document.frmNuevo.rtefdosede, document.frmNuevo.rtefdojornada,document.frmNuevo.metodologia,document.frmNuevo.rtefdoespacio,document.frmNuevo.rtefdodocente)
	  	</c:if>
	  </c:if>
	  <c:if test="${sessionScope.login.sedeId!='' && sessionScope.login.jornadaId!=''}">
			filtro3(document.frmNuevo.rtefdosede, document.frmNuevo.rtefdojornada,document.frmNuevo.metodologia,document.frmNuevo.rtefdoespacio,document.frmNuevo.rtefdodocente)
	  	filtro4(document.frmNuevo.rtefdosede, document.frmNuevo.rtefdojornada,document.frmNuevo.metodologia,document.frmNuevo.rtefdoespacio,document.frmNuevo.rtefdodocente)
  	</c:if>
  </script>