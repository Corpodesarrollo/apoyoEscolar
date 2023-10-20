<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/><jsp:setProperty name="horario" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="112" scope="page"/>
<SCRIPT language=javascript src='<c:url value="/etc/js/rotacion.js"/>'></SCRIPT>
<script languaje='javaScript'>
		<!--
		var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;
			var nav4=window.Event ? true : false;
			var visibilidad=1;
			var visible=0;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			
			function hacerValidaciones_frm(forma){
				validarLista(forma.sede,"- Sede",1)
				validarLista(forma.jornada,"- Jornada",1)
				validarLista(forma.vigencia,"- Vigencia",1)
			}
			
	function setheight(tipo){
		switch(tipo){
			case 0:	document.frmLista.height.value=Htipo_receso;		break;
			case 10:	document.frm.height.value=Hestructura;		break;
			case 20:	document.frm.height.value=Hfijar_espacio;		break;
			case 60:	document.frm.height.value=Hfijar_asignatura;		break;
			case 70:	document.frm.height.value=Hfijar_espacio_docente;		break;
			case 40:	document.frm.height.value=Hinhabilitar_espacio;		break;
			case 50:	document.frm.height.value=Hinhabilitar_docentes;		break;
			case 80:	document.frm.height.value=Hpriorizar;		break;
			case 100:	document.frm.height.value=Hfijar_horario;		break;
			case 110:	document.frm.height.value=Hborrar_horario;		break;
			case 120:	document.frm.height.value=Hinhabilitar_hora;		break;
		}
	}
	
			function lanzar(tipo){
				setheight(tipo);
				document.frm.tipo.value=tipo;
				document.frm.action='<c:url value="/rotacion/ControllerEditar.do"/>';
				document.frm.target="";
				document.frm.submit();
			}

			function lanzar2(tipo){
				setheight(tipo);
				document.frm.tipo.value=tipo;
				document.frm.action='<c:url value="/rotacion/ControllerEditar.do"/>';
				document.frm.target="";
				document.frm.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frm)){
					var niv=document.frm.grupo.options[document.frm.grupo.selectedIndex].value;
					if(niv=='-1'){
						if(confirm('¿Realmente desea eliminar la parte del horario seleccionada?')){
						document.frm.ext2.disabled=true;
						document.frm.ext2.value='';
						document.frm.tipo.value=tipo;
						document.frm.cmd.value="Guardar";
						document.frm.send.value="1";
						document.frm.submit();
						}
					}else{
							//document.frm.ext2.value='/rotacion/ControllerEditar.do?tipo=110';
							document.frm.tipo.value=tipo;
							document.frm.cmd.value="Guardar";
							document.frm.send.value="1";
							document.frm.submit();
					}
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
			function borrar_combo2(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-- Todos --","-1");
			}
			
			function setVisible(){if(visibilidad==1 && document.getElementById('th1')){ document.getElementById('th1').style.display='none'; document.getElementById('th2').style.display='none';}}
			
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4,combo_hijo6){
				setVisible();				
				borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
				<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.horario.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
				var niv=combo_padre.options[combo_padre.selectedIndex].value;
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
				filtro2(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo,document.frm.asignatura);
			}
			
			function filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_hijo2,combo_hijo3){
				setVisible();				
				var id=0;
				borrar_combo(combo_hijo); borrar_combo(combo_hijo2); 
				<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st"><c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4" varStatus="st2">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3"><c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.horario.grado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
				Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach></c:forEach>
				var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.value;
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
			
			function filtro3(combo_padre,combo_padre2,combo_padre4,combo_padre3,combo_hijo){
				setVisible();				
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
				<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3"><c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] && fila2[5]==fila3[5] }">Sel_Hijos[k] = '<c:if test="${sessionScope.horario.grupo== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
				var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value+'|'+combo_padre4.options[combo_padre4.selectedIndex].value;
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
				borrar_combo2(combo_hijo);
			}
			
			function filtroDim(combo_hijo){
				borrar_combo(combo_hijo);
				combo_hijo.options[1] = new Option("Corporal",1<c:if test="${sessionScope.horario.asignatura==1}">,"SELECTED"</c:if>);<c:if test="${sessionScope.horario.asignatura==1}">combo_hijo.selectedIndex = 1;</c:if>
				combo_hijo.options[2] = new Option("Comunicativa",2<c:if test="${sessionScope.horario.asignatura==2}">,"SELECTED"</c:if>);<c:if test="${sessionScope.horario.asignatura==2}">combo_hijo.selectedIndex = 2;</c:if>
				combo_hijo.options[3] = new Option("Cognitiva",3<c:if test="${sessionScope.horario.asignatura==3}">,"SELECTED"</c:if>);<c:if test="${sessionScope.horario.asignatura==3}">combo_hijo.selectedIndex = 3;</c:if>
				combo_hijo.options[4] = new Option("Ética",4<c:if test="${sessionScope.horario.asignatura==4}">,"SELECTED"</c:if>);<c:if test="${sessionScope.horario.asignatura==4}">combo_hijo.selectedIndex = 4;</c:if>
				combo_hijo.options[5] = new Option("Estética",5<c:if test="${sessionScope.horario.asignatura==5}">,"SELECTED"</c:if>);<c:if test="${sessionScope.horario.asignatura==5}">combo_hijo.selectedIndex = 5;</c:if>
			}
			function filtroVig(cmb1,cmb2,cmb3){
				cmb1.selectedIndex=0;
				cmb2.selectedIndex=0;
				cmb3.selectedIndex=0;
			}
			function ajaxAsignatura(forma,forma2){
				borrar_combo(forma.asignatura);
				forma2.inst.value='<c:out value="${sessionScope.login.instId}"/>';
				forma2.sede.value=forma.sede.options[forma.sede.selectedIndex].value;
				forma2.jor.value=forma.jornada.options[forma.jornada.selectedIndex].value;
				forma2.met.value=forma.metodologia.value;
				forma2.gra.value=forma.grado.options[forma.grado.selectedIndex].value;
				forma2.vig.value=forma.vigencia.options[forma.vigencia.selectedIndex].value;
				forma2.combo.value="3";
  			forma2.target="frame";
				forma2.submit();
			}
			
			//-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/rotacion/NuevoGuardar.jsp"/>'>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="send" value="">
	<input type="hidden" name="jer" value='<c:out value="${sessionScope.horario.jerGrupo}"/>'>
	<input type="hidden" name="ext2" value=''>
	<INPUT TYPE="hidden" NAME="height" VALUE='165'>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
				<td rowspan="2" width="600" bgcolor="#FFFFFF">
						<a href="javaScript:lanzar(10)"><img src='<c:url value="/etc/img/tabs/estructura_0.gif"/>' alt='Estructura' height="26" border="0"></a>
						<a href="javaScript:lanzar(20)"><img src="../etc/img/tabs/espacios_0.gif" alt="Fijar Espacios Físicos" height="26" border="0"></a>
						<a href="javaScript:lanzar(60)"><img src='<c:url value="/etc/img/tabs/fijar_asignatura_0.gif"/>' alt='Fijar Asignatura' height="26" border="0"></a>
						<a href="javaScript:lanzar(70)"><img src='<c:url value="/etc/img/tabs/espacio_docente_0.gif"/>' alt='Fijar Espacio por Docente' height="26" border="0"></a>
						<a href="javaScript:lanzar(40)"><img src="../etc/img/tabs/espfis_jornada_0.gif" alt="Inhabilitar Espacios Físicos" height="26" border="0"></a>
						<a href="javaScript:lanzar(50)"><img src='<c:url value="/etc/img/tabs/inhabilitar_docentes_0.gif"/>' alt='Inhabilitar Docentes' height="26" border="0"></a><br>
						<a href="javaScript:lanzar2(80)"><img src='<c:url value="/etc/img/tabs/priorizar_0.gif"/>' alt='Priorizar' height="26" border="0"></a>
						<a href="javaScript:lanzar2(100)"><img src='<c:url value="/etc/img/tabs/fijar_horario_0.gif"/>' alt='Fijar horario' height="26" border="0"></a>
						<img src='<c:url value="/etc/img/tabs/borrar_horario_1.gif"/>' alt='Borrar horario' height="26" border="0">
						<a href="javaScript:lanzar(120)"><img src='<c:url value="/etc/img/tabs/inhabilitar_horas_0.gif"/>' alt='Inhabilitar Horas' height="26" border="0"></a>
						<a href="javaScript:lanzar(130)"><img src='<c:url value="/etc/img/tabs/docente_grupo_0.gif"/>' alt='Docente por grupo' height="26" border="0"></a>
						<a href="javaScript:lanzar(140)"><img src='<c:url value="/etc/img/tabs/espacio_grado_0.gif"/>' alt='Espacio por grado' height="26" border="0"></a>
				</td>						
		</tr>
	</table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE align='center' width="95%" cellpadding="1" cellSpacing="0">
		<caption>Formulario de borrar horario</caption>
		<tr>
		  <td colspan="4" width="45%" bgcolor="#FFFFFF">
			<script>if(locked==0){document.write('<input class="boton" name="cmd1" type="button" value="Aceptar" onClick="guardar(document.frm.tipo.value)">');}</script>
			<c:if test="${sessionScope.isLockedRotacion==1}"><span class="style2">Nota: Información de solo lectura. Proceso de rotación pendiente por aprobar o rechazar</span></c:if>
		  </td>
		</tr>
			<tr>
					<td><span class="style2" >*</span>Sede:</td>
				    <td colspan=3>
						<select name="sede" onChange='setVisible();filtro(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo,document.frm.metodologia,document.frm.asignatura)' style='width:300px;'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.horario.sede== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
						</select>											
				   </td>
					<td><span class="style2" >*</span>Jornada:</td>
				  <td>
						<select name="jornada" style='width:150px;' onChange='setVisible();'>
							<option value='-1'>-- Seleccione uno --</option>
						</select>							
				  </td>
				</tr><tr>
				  <td><span class="style2" >*</span>Metodologia:</td>
					<td><select name="metodologia" style='width:150px;' onChange='setVisible();filtro2(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo,document.frm.asignatura);'><option value='-1'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila"><option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.horario.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
					</select>
					</td>
					<td><span class="style2">*</span> Vigencia:</td>
					<td>
						<select name="vigencia" style='width:50px;' onChange='setVisible();filtroVig(document.frm.grado,document.frm.grupo,document.frm.asignatura);'>
							<option value='-99'>--</option>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.horario.vigencia}">selected</c:if>><c:out value="${vig}"/></option>
							</c:forEach>
						</select>
					</td>			
				  <td>Grado:</td>
				  <td><select name="grado" onChange='setVisible();filtro3(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo);ajaxAsignatura(document.frm,document.frmAux)' style='width:90px;'><option value='-1'>-- Seleccione uno --</option></select></td>
				</tr>
				<tr>
				  <td>Asignatura:</td>
					<td colspan="3"><select name="asignatura" style='width:150px;' onChange='setVisible();'><option value='-1'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.filtroAsignaturaF}" var="fila"><option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.horario.asignatura==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
					</select></td>
				  <td style="display:;">Grupo:</td>
				  <td style="display:;"><select name="grupo" style='width:90px;' onChange='setVisible();'><option value='-1'>-- Seleccione uno --</option></select></td>
				</tr>
</table>
</form>
<script>
document.frm.sede.onchange();
document.frm.metodologia.onchange();
filtro3(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo);
</script>
<iframe name="frame" id="frame" width='0' height='0' style="display:none;"></iframe>
		<form method="post" name="frmAux" action="<c:url value="/rotacion/FiltroAux.do"/>">
			<input type="hidden" name="combo" value="">
			<input type="hidden" name="inst" value="">
			<input type="hidden" name="sede" value="">
			<input type="hidden" name="jor" value="">
			<input type="hidden" name="met" value="">
			<input type="hidden" name="gra" value="">
			<input type="hidden" name="asi" value="">
			<input type="hidden" name="vig" value="">
			<input type="hidden" name="ext" value="1">
		</form>