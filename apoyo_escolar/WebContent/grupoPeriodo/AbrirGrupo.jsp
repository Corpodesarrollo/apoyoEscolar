<jsp:useBean id="abrirGrupo" class="siges.grupoPeriodo.beans.AbrirGrupo" scope="session"/>
<jsp:setProperty name="abrirGrupo" property="*"/>
<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="tip" value="1" scope="page"/>
<script languaje='javaScript'>
		<!--
		
		<%
		int lindx;
		if (request.getHeader("Referer").lastIndexOf('?')==-1)
			lindx=request.getHeader("Referer").length();
		else
			lindx=request.getHeader("Referer").lastIndexOf('?');%>
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
				validarLista(forma.tipoapertura,"- Abrir grupo por",1)
				validarLista(forma.sede,"- Sede",1)
				validarLista(forma.jornada,"- Jornada",1)
				validarLista(forma.metodologia,"- Metodologia",1)
				validarLista(forma.grado,"- Grado",1)
				validarLista(forma.grupo,"- Grupo",1)
				validarLista(forma.asignatura,"- Area/Dimensión/Asignatura",1)
				validarLista(forma.periodo,"- Periodo",1)
			} 
			function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/grupoPeriodo/ControllerAbrirEdit.do"/>";
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			function lanzarServicio(n){
				document.frmNuevo.action='<c:url value="/observacion/Nuevo.do"/>';  	
				document.frmNuevo.tipo.value=n;
				document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.send.value="1";
					document.frmNuevo.submit();
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
				combo.options[0] = new Option("-seleccione una-","-1");
			}
			function filtroDim(combo_padre,combo_hijo){
				if(combo_hijo && combo_hijo.length<2){
					if(combo_padre && combo_padre.options && combo_padre.selectedIndex){
						var n=parseInt(combo_padre.options[combo_padre.selectedIndex].value);
						var i=combo_hijo.length;
						if(n<=0 || n==40 || n==41){
							//var i=1
							borrar_combo(combo_hijo);
							combo_hijo.options[i++] = new Option("Corporal",1);
							combo_hijo.options[i++] = new Option("Comunicativa",2);
							combo_hijo.options[i++] = new Option("Cognitiva",3);
							combo_hijo.options[i++] = new Option("Ética",4);
							combo_hijo.options[i++] = new Option("Estética",5);
							combo_hijo.options[i++] = new Option("Comportamiento",6);
						}
						if(n==30 || n==31){
							//var i=1
							borrar_combo(combo_hijo);
							combo_hijo.options[i++] = new Option("Comportamiento Social",1);
							combo_hijo.options[i++] = new Option("Comunicativa",2);
							combo_hijo.options[i++] = new Option("Científica",3);
							combo_hijo.options[i++] = new Option("Convivencia",4);
							combo_hijo.options[i++] = new Option("Lúdica",5);
							combo_hijo.options[i++] = new Option("Comportamiento",6);
						}
					}	
				}
			}
			function borrarFiltro(combo1,combo2,combo3,combo4,combo5,combo6){
				//borrar_combo(combo1);
				//combo1.selectedIndex=0;
				borrar_combo(combo2);
				//borrar_combo(combo3);
				borrar_combo(combo4);
				borrar_combo(combo5);
				borrar_combo(combo6);
				document.frmNuevo.sede.onchange(); 
				document.frmNuevo.grado.onchange(); 
			}			
			
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
				borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3); borrar_combo(combo_hijo4); 
				<c:if test="${!empty requestScope.filtroSedeAbrir && !empty requestScope.filtroJornadaAbrir}">var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroSedeAbrir}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaAbrir}" var="fila2">
				<c:if test="${fila[0]==fila2[2]}">
				<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>
				Sel_Hijos[k] = '<c:if test="${sessionScope.abrirGrupo.jornada== fila2[0]}">SELECTED</c:if>';
				<% }else{ %>
				Sel_Hijos[k] ='';
				<% }%>
				id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if>
				</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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
				}</c:if>
			}
			function filtro2(combo_padre,combo_padre2,combo_hijo,combo_hijo2,combo_padre3,combo_hijo3){
				borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
				var id=0;
				<c:if test="${!empty requestScope.filtroJornadaAbrir && !empty requestScope.filtroGradoAbrir}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroJornadaAbrir}" var="fila2"  varStatus="st">
					<c:forEach begin="0" items="${requestScope.filtroMetodologiaAbrir}" var="fila4">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGradoAbrir}" var="fila3">
					<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila4[0]==fila3[5]}">
					<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>
					Sel_Hijos[k] = '<c:if test="${sessionScope.abrirGrupo.grado== fila3[0]}">SELECTED</c:if>';
					<% }else{ %>
					Sel_Hijos[k] ='';
					<% }%>
					id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if>
					</c:forEach>Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach></c:forEach>
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
				}</c:if>
			}
			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_padre4){
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroSedeAbrir && !empty requestScope.filtroJornadaAbrir && !empty requestScope.filtroGradoAbrir && !empty requestScope.filtroGrupoAbrir}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroGradoAbrir}" var="fila2"  varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGrupoAbrir}" var="fila3">
					<c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] && fila2[5]==fila3[5]}">
					<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>
					Sel_Hijos[k] = '<c:if test="${sessionScope.abrirGrupo.grupo== fila3[0]}">SELECTED</c:if>';
					<% }else{ %>
					Sel_Hijos[k] ='';
					<% }%>
					id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if>
					</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
					var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value+'|'+combo_padre4.value;
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
				}</c:if>
			}
			function filtro4(combo_padre,combo_hijo,combo_padre2,combotipo){
				borrar_combo(combo_hijo); 
				var tipo=combotipo.options[combotipo.selectedIndex].value;				
				if(tipo==2){
					<c:if test="${!empty requestScope.filtroGradoAbrir && !empty requestScope.filtroAsignaturaAbrir}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoAbrir}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroAsignaturaAbrir}" var="fila2">
						<c:if test="${fila[0]==fila2[2] && fila[5]==fila2[3]}">
						<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>
						Sel_Hijos[k] = '<c:if test="${sessionScope.abrirGrupo.asignatura== fila2[0]}">SELECTED</c:if>';
						<% }else{ %>
						Sel_Hijos[k] ='';
						<% }%>
						id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>|<c:out value="${fila2[3]}"/>'; k++;</c:if>
						</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.value;
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
					}</c:if>
				}else{
					<c:if test="${!empty requestScope.filtroGradoAbrir && !empty requestScope.filtroAreaAbrir}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoAbrir}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroAreaAbrir}" var="fila2">
						<c:if test="${fila[0]==fila2[2] && fila[5]==fila2[3]}">
						<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>
						Sel_Hijos[k] = '<c:if test="${sessionScope.abrirGrupo.asignatura== fila2[0]}">SELECTED</c:if>';
						<% }else{ %>
						Sel_Hijos[k] ='';
						<% }%>
						id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>|<c:out value="${fila2[3]}"/>'; k++;</c:if>
						</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.value;
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
					}</c:if>
				}	
				filtroDim(combo_padre,combo_hijo);	
			}
			//-->
	</script>
<%@include file="../mensaje.jsp"%>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/grupoPeriodo/AbrirGrupoGuardar.jsp"/>'>
	<br>
	<table border="0" align="center" width="95%">
	<caption>Abrir grupo</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
				<input class='boton' name="cmd1" type="button" value="Aceptar" onClick="guardar(document.frmNuevo.tipo.value)">
		  </td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="send" value="">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			<td rowspan="2" width="500" bgcolor="#FFFFFF">
				<img src="../etc/img/tabs/abrir_grupo_1.gif" border="0"  height="26" alt='-Abrir Grupo-'>
				<c:if test="${sessionScope.login.perfil!=421}"><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/cerrar_periodo_0.gif" alt="Cerrar Periodo"  height="26" border="0"></a></c:if>
				<a href='javaScript:lanzarServicio(<c:out value="${paramsVO.FICHA_OBSERVACION_PERIODO}"/>)'><img src="../etc/img/tabs/observacion_periodo_0.gif" alt="Observación periodo"  height="26" border="0"></a>
				<a href='javaScript:lanzarServicio(<c:out value="${paramsVO.FICHA_OBSERVACION_GRUPO}"/>)'><img src="../etc/img/tabs/observacion_grupo_0.gif" alt="Observación grupo" height="26" border="0"></a>
				</td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
										<td colspan="2" align="right"><span class="style2">*</span> Abrir grupo por:</td>
										<td colspan="2">
											<select name="tipoapertura" onChange='borrarFiltro(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.metodologia,document.frmNuevo.grado,document.frmNuevo.grupo,document.frmNuevo.asignatura)'>
												<option value='-1' <c:if test="${sessionScope.abrirGrupo.tipoapertura==-1}"><% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>' SELECTED '<% } %></c:if>>-seleccione una-</option>
												<option value='1' <c:if test="${sessionScope.abrirGrupo.tipoapertura==1}"><% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>' SELECTED '<% } %></c:if>>Área/Dimensión</option>
												<option value='2' <c:if test="${sessionScope.abrirGrupo.tipoapertura==2}"><% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>' SELECTED '<% } %></c:if>>Asignatura</option>
											</select>											
										</td>	
									</tr>
									<tr>
										<td><span class="style2">*</span> Sede:</td>
										<td colspan="3">
											<select name="sede" style='width:400px;' onChange='filtro(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo,document.frmNuevo.asignatura)'>
												<option value='-1'>-seleccione una-</option>
												<c:forEach begin="0" items="${requestScope.filtroSedeAbrir}" var="fila">
													<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.abrirGrupo.sede== fila[0]}"><% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>' SELECTED '<% } %></c:if>>
													<c:out value="${fila[1]}"/></option>
												</c:forEach>
											</select>											
										</td>	
									</tr>
									<tr>
										<td><span class="style2">*</span> Jornada:</td>
										<td>
											<select name="jornada" style='width:150px;' onChange='document.frmNuevo.metodologia.onchange();'>
												<option value='-1'>-seleccione una-</option>
											</select>							
										</td>
										<td><span class="style2">*</span> Metodologia:</td>
										<td>
											<select name="metodologia" style='width:150px;' onChange='filtro2(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo,document.frmNuevo.metodologia,document.frmNuevo.asignatura)'>
												<option value='-1'>-seleccione una-</option>
												<c:forEach begin="0" items="${requestScope.filtroMetodologiaAbrir}" var="fila">
													<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.abrirGrupo.metodologia==fila[0]}"><% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>' SELECTED '<% } %></c:if>><c:out value="${fila[1]}"/></option>
												</c:forEach>
											</select>							
										</td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Grado:</td>
										<td>
											<select name="grado" style='width:150px;' onChange='javaScript:filtro3(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo,document.frmNuevo.metodologia);filtro4(document.frmNuevo.grado,document.frmNuevo.asignatura,document.frmNuevo.metodologia,document.frmNuevo.tipoapertura);'>
												<option value='-1'>-seleccione una-</option>
											</select>											
										</td>	
										<td><span class="style2">*</span> Grupo:</td>
										<td>
											<select name="grupo" style='width:150px;'>
												<option value='-1'>-seleccione una-</option>
											</select>							
										</td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Area/Dimensión/Asignatura:</td>
										<td>
											<select name="asignatura" style='width:150px;'>
												<option value='-1'>-seleccione una-</option>
											</select>							
										</td>
										<td><span class="style2" >*</span>Periodo:</td>
										<td><select name="periodo" style='width:150px;'>
											<option value='-1'>-- Seleccione uno --</option>
											<c:forEach begin="0" items="${requestScope.filtroPeriodoAbrir}" var="fila">
												<option value="<c:out value="${fila.codigo}"/>" <c:if test="${sessionScope.abrirGrupo.periodo==fila.codigo}"><% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerAbrirEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("AbrirGrupoGuardar.jsp")){ %>' SELECTED '<% } %></c:if>><c:out value="${fila.nombre}"/></option>
											</c:forEach>
										</select></td>
									</tr>
									<tr><td colspan="4"><span class="style2">*</span> Campos obligatorios</td></tr>
							  </TABLE>
<!--//////////////////////////////-->
</form>
<script>
document.frmNuevo.tipoapertura.onchange();
document.frmNuevo.sede.onchange(); 
filtro2(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo,document.frmNuevo.metodologia,document.frmNuevo.asignatura);
document.frmNuevo.grado.onchange(); 
</script>