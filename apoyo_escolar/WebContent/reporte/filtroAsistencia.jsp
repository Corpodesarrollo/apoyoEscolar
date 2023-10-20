<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtroA" class="siges.reporte.beans.FiltroBeanAsistencia" scope="session"/><jsp:setProperty name="filtroA" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="1" scope="page"/>
		<script languaje='javaScript'>
		<!--
			var extensiones = new Array();
			extensiones[0]=".xls";
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}

			function hacerValidaciones_frmNuevo(forma){
				   validarLista(forma.sede, "- Sede", 1);
				   validarLista(forma.jornada, "- Jornada", 1);
				   validarLista(forma.metodologia, "- Metodología", 1);
				   validarLista(forma.grado, "- Grado", 1);
				   validarLista(forma.grupo, "- Grupo", 1);
			}

			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/reporte/ControllerFiltroEdit.do"/>";
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Buscar";
					document.frmNuevo.submit();
				}
			}
			function cancelar(){
				if (confirm('¿Desea cancelar la generación del reporte?')){
 					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.target="";
		            document.frmNuevo.submit(); 
				}
			}

			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}

			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Todos--","-9");
			}
			
			function borrar_combo1(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-- Seleccione una --","-9");
			}
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
				//combo_hijo4.selectedIndex=0;
				if(combo_padre.selectedIndex==0){
					borrar_combo1(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3); 
				}else{
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtroA.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
							</c:if></c:forEach>
							Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>						
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -9;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;							
						}
						if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
					</c:if>
				}	
			}

			function filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_hijo2){
					borrar_combo1(combo_hijo); borrar_combo(combo_hijo2);
					var id=0;
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st"><c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3"><c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroA.grado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
						Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach></c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value;
						var val_padre = -9;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;
						}
						if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
				</c:if>
			}



			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_padre4,combo_hijo){
				if(combo_padre.selectedIndex==0 || combo_padre2.selectedIndex==0 || combo_padre3.selectedIndex==0)
					borrar_combo1(combo_hijo); else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3">
								<c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] && fila2[5]==fila3[5]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtroA.grupo== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[5]}"/>|<c:out value="${fila3[4]}"/>'; k++;
								</c:if>
							</c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value+'|'+combo_padre4.options[combo_padre4.selectedIndex].value;
						var val_padre = -9;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;
						}
						if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
					</c:if>
				}	
			}
			//-->
		</script>
<%@include file="../mensaje.jsp"%>
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/reporte/FiltroGuardarAsistencia.jsp"/>'>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Asistencia a entrega de boletines</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Generar" onClick="guardar(document.frmNuevo.tipo.value)">
				<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
			</td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>											
			<td rowspan="2" width="588" bgcolor="#FFFFFF"><img src="../etc/img/tabs/asistencia_entrega_boletines_1.gif" width="84" height="26" border="0"><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/evaluacion_logros_grupo_0.gif" width="84" height="26" border="0"></a><a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/evaluacion_asig_grupo_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/evaluacion_areas_grupo_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(5)"><a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/evaluacion_logros_grupo1_0.gif" width="84" height="26" border="0"></a><a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/evaluacion_asig_por_grupo_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/evaluacion_area_por_grupo_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/eval_tipo_de_descriptor_0.gif" width="84"  height="26" border="0"></a></td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
										<tr>
											<td><span class="style2" >*</span>Sede:</td>
										    <td>
												<select name="sede" onChange='filtro(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo,document.frmNuevo.metodologia)' style='width:300px;'>
													<option value='-9'>-- Seleccione una --</option>
													<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
														<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroA.sede== fila[0]}">SELECTED</c:if>>
														<c:out value="${fila[1]}"/></option>
													</c:forEach>
												</select>											
										   </td>
											<td><span class="style2" >*</span>Jornada:</td>
										  <td>
												<select name="jornada" style='width:120px;'>
													<option value='-9'>-- seleccione uno --</option>
												</select>							
										  </td>
								  </tr>
										<tr>
										  <td><span class="style2" >*</span>Metodolog&iacute;a:</td>
										  <td>
                        <select name="metodologia" style='width:120px;' onChange='filtro2(document.frmNuevo.sede,document.frmNuevo.jornada,document.frmNuevo.metodologia,document.frmNuevo.grado,document.frmNuevo.grupo)'>
                        <option value='-9'>-- Seleccione una --</option>
                        <c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
												<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroA.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
                        </c:forEach>
                        </select>
                      </td>
										  <td><span class="style2" >*</span>Grado:</td>
										  <td>
                        <select name="grado" onChange='filtro3(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.metodologia,document.frmNuevo.grado,document.frmNuevo.grupo)' style='width:120px;'>
										  	<option value='-9'>-- seleccione uno --</option>
										  	</select>
                      </td>
								  </tr>
										<tr>
											<td><span class="style2" >*</span>Grupo:</td>
											<td>
												<select name="grupo" style='width:120px;'>
													<option value='-9'>-- seleccione uno --</option>
												</select>
											</td>
											<td>Orden:</td>
										  <td>
												<select name="orden">
													<option value='-9' <c:if test="${sessionScope.filtroA.orden== '-9'}">SELECTED</c:if>>Apellidos</option>
													<option value='0' <c:if test="${sessionScope.filtroA.orden== '0'}">SELECTED</c:if>>Identificación</option>
													<option value='1' <c:if test="${sessionScope.filtroA.orden== '1'}">SELECTED</c:if>>Nombres</option>
													<option value='2' <c:if test="${sessionScope.filtroA.orden== '2'}">SELECTED</c:if>>Código</option>
												</select>										  
										  </td>
										</tr>
								</TABLE>
<!--//////////////////////////////-->
</form>
<script>document.frmNuevo.sede.onchange();
document.frmNuevo.metodologia.onchange();
document.frmNuevo.grado.onchange();</script>