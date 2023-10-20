<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
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
		document.frmNuevo.tipo.value=36;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.tipo.value=31;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.rotdagsede, "- Seleccione una sede",1)
		validarLista(forma.rotdagjornada, "- Seleccione una jornada",1)
		validarLista(forma.rotdagdocente, "- Seleccione un docente",1)
		validarLista(forma.rotdagasignatura, "- Seleccione una asignatura",1)
		validarLista(forma.rotdaggrados, "- Debe seleccionar al menos un grado",0)
		validarCampo(forma.rotdaghoras, "- Total horas semanales", 1, 2)
	}

	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
		borrar_combo(combo_hijo); borrar_combo2(combo_hijo2); borrar_combo(combo_hijo4);
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
						Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion.rotdagjornada == fila2[0]}">SELECTED</c:if>';
						id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
						Hijos[k] = '<c:out value="${fila2[1]}"/>';
						id_Padre[k] = '<c:out value="${fila2[2]}"/>';
						k++;
					</c:if>
				</c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
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
	
	function filtro4(combo_padre,combo_padre2,combo_hijo){
		borrar_combo(combo_hijo);
		var id=0;
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroDocente}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
				id_Hijos=new Array();
				Hijos = new Array();
				Sel_Hijos= new Array();
				id_Padre= new Array();
				var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila1">
					<c:forEach begin="0" items="${requestScope.filtroDocente}" var="fila2">
						<c:if test="${fila[0]==fila1[2] && fila[0]==fila2[2] && fila1[0]==fila2[3] && fila1[2]==fila2[2]}">
							Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion.rotdagdocente == fila2[0]}">SELECTED</c:if>';
							id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
							Hijos[k] = '<c:out value="${fila2[1]}"/>';
							id_Padre[k] = '<c:out value="${fila2[2]}"/>|<c:out value="${fila2[3]}"/>';
							k++;
						</c:if>
					</c:forEach>
				</c:forEach>
				Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
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
		<c:if test="${!empty requestScope.filtroGradoF2 && !empty requestScope.filtroGradoAsignatura}">
			var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
						id_Hijos= new Array();
						Hijos= new Array();
						Sel_Hijos= new Array();
						id_Padre= new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoAsignatura}" var="fila4" varStatus="st2">
							<c:if test="${fila3[5]==fila4[2] && fila3[0]==fila4[1]}">
								Sel_Hijos[k] = '<c:forEach begin="0" items="${sessionScope.rotacion.rotdaggrados}" var="filax"><c:if test="${filax == fila3[0]}">SELECTED</c:if></c:forEach>';
								id_Hijos[k] = '<c:out value="${fila3[0]}"/>';
								Hijos[k] = '<c:out value="${fila3[1]}"/>';
								id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>';
								k++;
							</c:if>
						</c:forEach>
				Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
			if(combo_padre.options){
				var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.value;
			}else{
				var niv=combo_padre.value+'|'+combo_padre2.value+'|'+combo_padre3.value;
			}
			var val_padre = -1;			
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){ var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						vec[au]=i;au+=1;
						combo_hijo.options[i] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
						combo_hijo.selectedIndex = i;
					}else	combo_hijo.options[i] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
			if(combo_hijo.length && vec.length){
				for(var j=0;j<combo_hijo.length;j++){
					for(var k=0;k<vec.length;k++){
						if(j==vec[k]){
							combo_hijo.options[j].selected=true;
						}
					}
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
			<INPUT TYPE="hidden" NAME="ext2" VALUE='/rotacion/ControllerFiltro.do'>
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
			<c:if test="${sessionScope.login.sedeId!='' && sessionScope.login.jornadaId!=''}">
				<input type="hidden" name="rotdagsede" value="<c:out value="${sessionScope.login.sedeId}"/>">
				<input type="hidden" name="rotdagjornada" value="<c:out value="${sessionScope.login.jornadaId}"/>">
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
				<caption>Relaci&oacute;n Docentes-Asignaturas-Grados</caption>
				<tr>
					<c:if test="${sessionScope.login.sedeId!='' && sessionScope.login.jornadaId!=''}">
						<td colspan="3">Sede: <c:out value="${sessionScope.login.sede}"/></td>
						<td colspan="2">Jornada: <c:out value="${sessionScope.login.jornada}"/></td>
					</c:if>
					<c:if test="${sessionScope.login.sedeId=='' && sessionScope.login.jornadaId==''}">
						<td><span class="style2" >*</span>Sede:</td>
				    <td colspan="2">
							<select name="rotdagsede" onChange='filtro(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdaggrados,document.frmNuevo.metodologia,document.frmNuevo.rotdagdocente)' style='width:300px;'>
								<option value='-1'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
									<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.rotacion.rotdagsede == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
							</select>
				   </td>
						<td><span class="style2" >*</span>Jornada:</td>
					  <td>
							<select name="rotdagjornada" style='width:150px;' onChange='filtro4(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdagdocente)'>
								<option value='-1'>-- Seleccione uno --</option>
							</select>
					  </td>
				  </c:if>
				</tr>
				<tr>
					<td><span class="style2" >*</span>Docente:</td>
			    <td colspan="2">
						<select name="rotdagdocente" onChange='' style='width:300px;'>
							<option value='-1'>-- Seleccione uno --</option>
						</select>
			    </td>
					<td><span class="style2" >*</span>Asignatura:</td>
			    <td colspan="2">
						<select name="rotdagasignatura" onChange='filtro2(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.metodologia,document.frmNuevo.rotdaggrados)' style='width:300px;'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroAsignatura}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.rotacion.rotdagasignatura == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
						</select>
			    </td>
				</tr>
				<tr>
					<td><span class="style2" >*</span>Grado:</td>
				  <td>
	          <select name="rotdaggrados" style='width:150px;' multiple></select>
					</td>
					<td><span class="style2" >*</span>Total Horas Semanales:</td>
				  <td>
	          <input type="text" size="3" maxlength="2" name="rotdaghoras" onKeyPress='return acepteNumeros(event)' <c:if test="${sessionScope.rotacion.estado==1}">value='<c:out value="${sessionScope.rotacion.rotdaghoras}"/>'</c:if>/>
					</td>
				</tr>
  	  </table>
 	  </form>
  </font>
	<script>
		<c:if test="${sessionScope.rotacion.estado==1}">
				filtro(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdaggrados,document.frmNuevo.metodologia,document.frmNuevo.rotdagdocente)
				filtro4(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdagdocente)
				filtro2(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.metodologia,document.frmNuevo.rotdaggrados)
		</c:if>
		if(!document.frmNuevo.rotdagsede.options){
			filtro4(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdagdocente)
			filtro2(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.metodologia,document.frmNuevo.rotdaggrados)
		}
	</script>

  <c:if test="${requestScope.refrescar==1}">
		<form method="post" name="frmR" action="<c:url value="/rotacion/ControllerEditar.do"/>">
 			<INPUT TYPE="hidden" NAME="ext2" VALUE='/rotacion/ControllerFiltro.do'>
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="39">
			<INPUT TYPE="hidden" NAME="mensaje" VALUE="<c:out value="${requestScope.mensaje}"/>">
  	</form>
	  <script>document.frmR.submit();</script>
  </c:if>