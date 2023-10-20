<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="rotacion/ControllerFiltro.do?tipo=20"/></div>
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
		document.frmNuevo.tipo.value=26;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			if(document.frmNuevo.exclusivo.checked){
				document.frmNuevo.roteagexclusivo.value='2';
			}else{
				document.frmNuevo.roteagexclusivo.value='1';
			}
			document.frmNuevo.tipo.value=21;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.roteagsede, "- Seleccione una sede",1)
		validarLista(forma.roteagjornada, "- Seleccione una jornada",1)
		validarLista(forma.roteagespacio, "- Seleccione un espacio físico",1)
		validarLista(forma.roteagasignatura, "- Seleccione una asignatura",1)
		validarLista(forma.roteaggrados, "- Debe seleccionar al menos un grado",0)
	}

	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}
	
	//sede-jorn-grado-metod-espacio
	function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
		borrar_combo(combo_hijo);
		borrar_combo2(combo_hijo2);
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
						Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion.roteagjornada == fila2[0]}">SELECTED</c:if>';
						id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
						Hijos[k] = '<c:out value="${fila2[1]}"/>';
						id_Padre[k] = '<c:out value="${fila2[2]}"/>';
						k++;
					</c:if>
				</c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
			if(combo_padre.options)
				var niv=combo_padre.options[combo_padre.selectedIndex].value;
			else
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
		//filtro2(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4)
	}
	
	function filtro2(combo_padre,combo_padre2,combo_hijo2,combo_hijo3,combo_hijo4){
		var id=0;
		borrar_combo(combo_hijo4);
		<c:if test="${!empty requestScope.filtroJornadaF && !empty requestScope.filtroEspFis}">var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila" varStatus="st">
				id_Hijos=new Array();Hijos= new Array();Sel_Hijos= new Array();id_Padre= new Array();var k=0;
				<c:forEach begin="0" items="${requestScope.filtroEspFis}" var="fila2">
					<c:if test="${fila[2]==fila2[0] && fila[0]==fila2[4]}">
						Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion.roteagespacio == fila2[1]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[1]}"/>';Hijos[k] = '<c:out value="${fila2[2]}"/>';id_Padre[k] = '<c:out value="${fila2[0]}"/>|<c:out value="${fila2[4]}"/>';k++;
					</c:if></c:forEach>Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
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
						combo_hijo4.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
						combo_hijo4.selectedIndex = i+1;
					}else	combo_hijo4.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>
	}
	

	//sede-jorn-metod-grado-asig	
	function filtro3(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_padre4){
		var id=0;
		var vec=new Array();
		var au=0;
		borrar_combo2(combo_hijo);
		<c:if test="${!empty requestScope.filtroGradoF2 && !empty requestScope.filtroGradoAsignatura && !empty requestScope.filtroJornadaF}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila0" varStatus="st0">
				<c:forEach begin="0" items="${requestScope.filtroAsignatura}" var="fila" varStatus="st2">
					id_Hijos= new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGradoAsignatura}" var="fila2">
						<c:if test="${fila0[2]==fila2[2] && fila0[0]==fila2[3] && fila[3]==fila2[4] && fila[0]==fila2[5]}">
							Sel_Hijos[k] = '<c:forEach begin="0" items="${sessionScope.rotacion.roteaggrados}" var="filax"><c:if test="${filax == fila2[0]}">SELECTED</c:if></c:forEach>';
							id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
							Hijos[k] = '<c:out value="${fila2[1]}"/>';
							id_Padre[k] = '<c:out value="${fila2[2]}"/>|<c:out value="${fila2[3]}"/>|<c:out value="${fila2[4]}"/>|<c:out value="${fila2[5]}"/>';
							k++;
						</c:if>
					</c:forEach>
					Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
				</c:forEach>
			</c:forEach>
			if(combo_padre.options)
				var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.value+'|'+combo_padre4.options[combo_padre4.selectedIndex].value;
			else
				var niv=combo_padre.value+'|'+combo_padre2.value+'|'+combo_padre3.value+'|'+combo_padre4.value;
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
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action="<c:url value="/rotacion/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="rotstrmetodologia" value='<c:out value="${sessionScope.filtroRotacion.filMetodologia}"/>'>
			<input type="hidden" name="roteagjerjornada" value="<c:out value="${sessionScope.rotacion.roteagjerjornada}"/>">
			<input type="hidden" name="roteagexclusivo" value="<c:out value="${sessionScope.rotacion.roteagexclusivo}"/>">
			<c:if test="${sessionScope.login.sedeId!='' && sessionScope.login.jornadaId!=''}">
				<input type="hidden" name="roteagsede" value="<c:out value="${sessionScope.login.sedeId}"/>">
				<input type="hidden" name="roteagjornada" value="<c:out value="${sessionScope.login.jornadaId}"/>">
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
						<td colspan="1">Sede: </td><td colspan="1"><font style='COLOR:#006699;'><c:out value="${sessionScope.login.sede}"/></font></td>
						<td colspan="1">Jornada: </td><td colspan="1"><font style='COLOR:#006699;'><c:out value="${sessionScope.login.jornada}"/></font></td>
					</c:if>
					<c:if test="${sessionScope.login.sedeId=='' && sessionScope.login.jornadaId==''}">
						<td><span class="style2" >*</span>Sede:</td>
				    <td >
							<select name="roteagsede" onChange='filtro(document.frmNuevo.roteagsede, document.frmNuevo.roteagjornada,document.frmNuevo.roteaggrados,document.frmNuevo.rotstrmetodologia,document.frmNuevo.roteagespacio)' style='width:300px;'>
								<option value='-1'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
									<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.rotacion.roteagsede==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
								</c:forEach>
							</select>
				    </td>
						<td><span class="style2" >*</span>Jornada:</td>
					  <td>
							<select name="roteagjornada" style='width:150px;' onChange='filtro2(document.frmNuevo.roteagsede, document.frmNuevo.roteagjornada,document.frmNuevo.roteaggrados,document.frmNuevo.rotstrmetodologia,document.frmNuevo.roteagespacio);filtro3(document.frmNuevo.roteagsede, document.frmNuevo.roteagjornada,document.frmNuevo.rotstrmetodologia,document.frmNuevo.roteaggrados,document.frmNuevo.roteagasignatura)'>
								<option value='-1'>-- Seleccione uno --</option>
							</select>
						</td>
				 </c:if>
				</tr>
				<tr>
					<td><span class="style2" >*</span>Espacio F&iacute;sico:</td>
			    <td>
						<select name="roteagespacio" onChange='' style='width:300px;'>
							<option value='-1'>-- Seleccione uno --</option>
						</select>
			    </td>
					<td rowspan="4"><span class="style2" >*</span>Grado:</td>
				  <td  rowspan="4">
	          <select name="roteaggrados" style='width:150px;' multiple size=5></select>
					</td>
				</tr>
				<tr>
				<td><span class="style2" >*</span>Asignatura:</td>
			    <td>
						<select name="roteagasignatura" onChange='filtro3(document.frmNuevo.roteagsede, document.frmNuevo.roteagjornada,document.frmNuevo.rotstrmetodologia,document.frmNuevo.roteaggrados,document.frmNuevo.roteagasignatura)' style='width:300px;'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroAsignatura}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.rotacion.roteagasignatura == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>
			    </td>
				</tr>
				<tr>
				</tr>
				<tr>
					<td>Exclusivo:&nbsp;&nbsp;
	          <input type='checkbox' name="exclusivo" value='1' <c:if test="${sessionScope.rotacion.roteagexclusivo==2}">CHECKED</c:if>>
					</td>
					<td><span class="style2" >*</span>Vigencia:&nbsp;&nbsp;<c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/>
						<input type="hidden" name="vigencia" value='<c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/>'>
					</td>
				</tr>
  	  </table>
 	  </form>
  <script>
		<c:if test="${sessionScope.rotacion.estado==1}">
			<c:if test="${sessionScope.login.sedeId=='' && sessionScope.login.jornadaId==''}">
		  	filtro(document.frmNuevo.roteagsede, document.frmNuevo.roteagjornada,document.frmNuevo.roteaggrados,document.frmNuevo.rotstrmetodologia,document.frmNuevo.roteagespacio);
				filtro2(document.frmNuevo.roteagsede, document.frmNuevo.roteagjornada,document.frmNuevo.roteaggrados,document.frmNuevo.rotstrmetodologia,document.frmNuevo.roteagespacio)
		  	filtro3(document.frmNuevo.roteagsede, document.frmNuevo.roteagjornada,document.frmNuevo.rotstrmetodologia,document.frmNuevo.roteaggrados,document.frmNuevo.roteagasignatura);
	  	</c:if>
	  </c:if>
	  <c:if test="${sessionScope.login.sedeId!='' && sessionScope.login.jornadaId!=''}">
		  filtro2(document.frmNuevo.roteagsede, document.frmNuevo.roteagjornada,document.frmNuevo.roteaggrados,document.frmNuevo.rotstrmetodologia,document.frmNuevo.roteagespacio)
		  filtro3(document.frmNuevo.roteagsede, document.frmNuevo.roteagjornada,document.frmNuevo.rotstrmetodologia,document.frmNuevo.roteaggrados,document.frmNuevo.roteagasignatura)
  	</c:if>
  </script>