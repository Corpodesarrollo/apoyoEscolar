<%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion2" class="siges.rotacion2.beans.Rotacion2" scope="session"/><jsp:setProperty name="rotacion2" property="*"/>
<jsp:useBean id="validacion" class="siges.rotacion2.beans.Validacion" scope="session"/><jsp:setProperty name="validacion" property="*"/>
<script languaje="javaScript">
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-- Seleccione uno --","-9");
	}
	
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-- Todos --","-9");
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
		document.frmNuevo.tipo.value=96;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.sede_.value=document.frmNuevo.sede.options[document.frmNuevo.sede.selectedIndex].text;
			document.frmNuevo.jornada_.value=document.frmNuevo.jornada.options[document.frmNuevo.jornada.selectedIndex].text;
			if(document.frmNuevo.grado.selectedIndex==0){
				document.frmNuevo.nombreGrado.value='   ';
			}else{
				document.frmNuevo.nombreGrado.value=document.frmNuevo.grado.options[document.frmNuevo.grado.selectedIndex].text;
			}	
			if(document.frmNuevo.grupo_.selectedIndex==0){			
				document.frmNuevo.nombreGrupo.value='   ';
			}else{
				document.frmNuevo.nombreGrupo.value=document.frmNuevo.grupo_.options[document.frmNuevo.grupo_.selectedIndex].text;
			}			
			document.frmNuevo.tipo.value=11;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}
	}
	
	function ver(){
			document.frmNuevo.tipo.value=15;
			document.frmNuevo.cmd.value="Ver";
			document.frmNuevo.submit();
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.sede, "- Sede",1)
		validarLista(forma.jornada, "- Jornada",1)
		validarLista(forma.metodologia, "- Metodologia",1)
		validarLista(forma.vigencia, "- Vigencia",1)
	}

	function lanzar(tipo){
		document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.action='<c:url value="/rotacion2/ControllerEditar.do"/>';
		document.frmNuevo.ext2.value='/rotacion2/ControllerFiltro.do';
		document.frmNuevo.target="";
		document.frmNuevo.submit();
	}
	
	function lanzar2(tipo){
		document.frmLista.tipo.value=tipo;
		document.frmLista.action='<c:url value="/rotacion2/ControllerEditar.do"/>';
		document.frmLista.ext2.value='';
		document.frmLista.target="";
		document.frmLista.submit();
	}
	
	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3){
		borrar_combo(combo_hijo); borrar_combo2(combo_hijo2); 
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array();Hijos= new Array();Sel_Hijos= new Array();id_Padre= new Array();var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
					<c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion2.jornada==fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>';Hijos[k] = '<c:out value="${fila2[1]}"/>';id_Padre[k] = '<c:out value="${fila2[2]}"/>';k++;</c:if></c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value;
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

	function filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo){
		var id=0;
		var vec=new Array();
		var au=0;
		borrar_combo2(combo_hijo);
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2}">var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2" varStatus="st"><c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4" varStatus="st2">
					id_Hijos=new Array();Hijos= new Array();Sel_Hijos= new Array();id_Padre= new Array();var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
						<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion2.grado==fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>';Hijos[k] = '<c:out value="${fila3[1]}"/>';id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>';k++;</c:if></c:forEach>
					Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach></c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.value;
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){ var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						vec[au]=i;au+=1;
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); 
						combo_hijo.selectedIndex = i+1;
					}else	combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>
	}

			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_hijo){
				borrar_combo2(combo_hijo);
				<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
				<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3"><c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] }">Sel_Hijos[k] = '<c:if test="${sessionScope.horario.grupo== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>'; k++;</c:if></c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
				var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value;
				var val_padre = -1;
				for(var k=0;k<Padres.length;k++){
					if(Padres[k].id_Padre[0]==niv) val_padre=k;
				}
				if(val_padre!=-1){ var no_hijos = Padres[val_padre].Hijos.length;
					for(i=0; i < no_hijos; i++){
						if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
							combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
						}else
							combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
					}
				}
				</c:if>
			}

//-->
</script>
<%@include file="../mensaje.jsp" %>
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/rotacion2/NuevoGuardar.jsp"/>'>
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="ext2" VALUE=''>
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="institucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="institucion_" value='<c:out value="${sessionScope.login.inst}"/>'>
			<input type="hidden" name="sede_" value='<c:out value="${sessionScope.validacion.sede_}"/>'>
			<input type="hidden" name="jornada_" value='<c:out value="${sessionScope.validacion.jornada_}"/>'>
			<input type="hidden" name="nombreGrado" value='<c:out value="${sessionScope.validacion.nombreGrado}"/>'>
			<input type="hidden" name="nombreGrupo" value='<c:out value="${sessionScope.validacion.nombreGrupo}"/>'>
		<table align="center" width="95%" cellpadding="2" cellSpacing="0" >
				<tr>
				  <td width="45%" align="left">
				  	<input name="cmd12" class="boton" type="button" value="Validar" onClick="guardar()">
					  <input name="cmd12" class="boton" type="button" value="Ver Solicitudes" onClick="ver()">
					</td>
				</tr>
				<tr>
					<td rowspan="2" width="780">
						<img src='<c:url value="/etc/img/tabs/generar_horario_1.gif"/>' alt='Generar Horario' height="26" border="0">
					</td>
				</tr>
  	  </table>
  	  <table cellpadding="2" cellSpacing="0" align="center" width="95%">
				<caption>formulario de solicitud de rotación</caption>
  	  	<tr>
						<td><span class="style2" >*</span>Sede:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    <td>
							<select name="sede" onChange='filtro(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.metodologia)' style='width:300px;'>
								<option value='-9'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
									<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.rotacion2.sede==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
							</select>
				   </td>
						<td><span class="style2" >*</span>Jornada:</td>
					  <td>
							<select name="jornada" style='width:150px;' >
								<option value='-9'>-- Seleccione uno --</option>
							</select>
					  </td>
			   </tr>
			   <tr>
					<td><span class="style2">*</span>Metodologia:</td>
			    <td>
						<select name="metodologia" style='width:120px;' onChange='filtro2(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.metodologia,document.frmNuevo.grado)'>
							<option value='-9'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.rotacion2.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
						</select>
			   </td>
				<td><span class="style2" ></span>Grado:</td>
			  <td>
          <select name="grado" style='width:150px;' >
	          <option value='-9'>-- Todos --</option>
	          </select>
					</td>
				</tr>
				<tr>
				  <td>Grupo:</td>
				  <td><select name="grupo_" style='width:90px;'><option value='-9'>-- Todos --</option></select></td>
					<td><span class="style2" >*</span>Vigencia:</td>
				  <td>
						<select name="vigencia" style='width:50px;'>
							<option value='-9'>--</option>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="fila">
								<option value='<c:out value="${fila}"/>' <c:if test="${sessionScope.rotacion2.vigencia==fila}">SELECTED</c:if>><c:out value="${fila}"/></option>
							</c:forEach>
						</select>
				  </td>
				</tr>
			</table>
 	  </form>
<script>
document.frmNuevo.sede.onchange();
document.frmNuevo.metodologia.onchange();
</script>