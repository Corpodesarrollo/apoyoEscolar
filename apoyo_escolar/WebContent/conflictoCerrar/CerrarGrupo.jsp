<jsp:useBean id="conflictocerrar" class="siges.conflictoCerrar.beans.ConflictoCerrar" scope="session"/>
<jsp:setProperty name="conflictocerrar" property="*"/>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<script languaje='javaScript'>
		<!--
			var nav4=window.Event ? true : false;
			
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
				validarLista(forma.sede,"- Sede",1)
				validarLista(forma.jornada,"- Jornada",1)
				validarLista(forma.metodologia,"- Metodologia",1)
				validarLista(forma.grado,"- Grado",1)
				validarLista(forma.grupo,"- Grupo",1)
				validarLista(forma.periodo,"- Periodo",1)
			} 
			function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/conflictoCerrar/ControllerEditar.do"/>";
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.send.value="1";
					if(confirm("Si desea cerrar el grupo seleccionado haga click en aceptar")){
						//alert("El grupo fue abierto satisfactoriamente")
						document.frmNuevo.submit();
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
				combo.options[0] = new Option("-seleccione una-","-1");
			}
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3){
				borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
				<c:if test="${!empty requestScope.filtroSedeAbrir && !empty requestScope.filtroJornadaAbrir}">
					var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroSedeAbrir}" var="fila" varStatus="st">
						id_Hijos=new Array();Hijos= new Array();Sel_Hijos= new Array();id_Padre= new Array();var k=0;
						<c:forEach begin="0" items="${requestScope.filtroJornadaAbrir}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
								Sel_Hijos[k] = '<c:if test="${sessionScope.conflictocerrar.jornada== fila2[0]}">SELECTED</c:if>';
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
			function filtro2(combo_padre,combo_padre2,combo_hijo,combo_hijo2,combo_padre3,combo_hijo3){
				borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
				var id=0;
				<c:if test="${!empty requestScope.filtroJornadaAbrir && !empty requestScope.filtroGradoAbrir}">
					var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroJornadaAbrir}" var="fila2"  varStatus="st">
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaAbrir}" var="fila4">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGradoAbrir}" var="fila3">
								<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila4[0]==fila3[5]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.conflictocerrar.grado== fila3[0]}">SELECTED</c:if>';
									id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>';
									id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>';
									k++;
								</c:if>
							</c:forEach>
							Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>
					</c:forEach>
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
			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_padre4){
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroSedeAbrir && !empty requestScope.filtroJornadaAbrir && !empty requestScope.filtroGradoAbrir && !empty requestScope.filtroGrupoAbrir}">
					var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroGradoAbrir}" var="fila2"  varStatus="st">
						id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGrupoAbrir}" var="fila3">
							<c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] && fila2[5]==fila3[5]}">
								Sel_Hijos[k] = '<c:if test="${sessionScope.conflictocerrar.grupo== fila3[0]}">SELECTED</c:if>';
								id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>';
								id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>';
								k++;
							</c:if>
						</c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
					</c:forEach>
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
					}
				</c:if>
			}
			//-->
	</script>
<%@include file="../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/conflictoCerrar/NuevoGuardar.jsp"/>'>
	<br>
	<table border="1" align="center" bordercolor="#FFFFFF" width="95%">
		<caption>Cerrar Grupo para conflicto escolar</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
				<input class='boton' name="cmd1" type="button" value="Cerrar Grupo" onClick="guardar(4)">
		  </td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='4'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="send" value="">
	<!--<input type="hidden" name="periodo" value="0">-->
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
			<td rowspan="2" width="235" bgcolor="#FFFFFF">
				<img src="../etc/img/tabs/cerrar_grupo_ce_1.gif" border="0" height="26" alt='Cerrar Grupo de conflicto escolar'>
			</td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE width="100%" cellpadding="3" cellSpacing="0">
		<tr>
			<td><span class="style2">*</span> Sede:</td>
			<td colspan="3">
				<select name="sede" style='width:400px;' onChange='filtro(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo)'>
					<option value='-1'>-seleccione una-</option>
					<c:forEach begin="0" items="${requestScope.filtroSedeAbrir}" var="fila">
						<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.conflictocerrar.sede== fila[0]}">SELECTED</c:if>>
						<c:out value="${fila[1]}"/></option>
					</c:forEach>
				</select>											
			</td>	
		</tr>
		<tr>
			<td><span class="style2">*</span> Jornada:</td>
			<td>
				<select name="jornada" style='width:150px;' >
					<option value='-1'>-seleccione una-</option>
				</select>							
			</td>
			<td><span class="style2">*</span> Metodologia:</td>
			<td>
				<select name="metodologia" style='width:150px;' onChange='filtro2(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo,document.frmNuevo.metodologia)'>
					<option value='-1'>-seleccione una-</option>
					<c:forEach begin="0" items="${requestScope.filtroMetodologiaAbrir}" var="fila">
						<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.conflictocerrar.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
					</c:forEach>
				</select>							
			</td>
		</tr>
		<tr>	
			<td><span class="style2">*</span> Grado:</td>
			<td>
				<select name="grado" style='width:150px;' onChange='filtro3(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo,document.frmNuevo.metodologia);'>
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
			 <td><span class="style2" >*</span>Periodo:</td>
			<td><select name="periodo" style='width:150px;'>
				<option value='-1'>-- Seleccione uno --</option>
				<c:forEach begin="0" items="${requestScope.filtroPeriodoAbrir}" var="fila">
					<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.conflictocerrar.periodo==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
				</c:forEach>
			</select></td> 
		</tr>
		<tr><td colspan="4"><span class="style2">*</span> Campos obligatorios</td></tr>
  </TABLE>
	</font>
<!--//////////////////////////////-->
</form>
<script>
filtro(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo)
filtro2(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo,document.frmNuevo.metodologia)
filtro3(document.frmNuevo.sede, document.frmNuevo.jornada,document.frmNuevo.grado,document.frmNuevo.grupo,document.frmNuevo.metodologia)
</script>