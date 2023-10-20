<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtroLog" class="siges.boletines.beans.FiltroBeanFormulario" scope="session"/><jsp:setProperty name="filtroLog" property="*"/>
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

			function hacerValidaciones_frm(forma){
				   validarLista(forma.grado, "- Grado", 1);
			}

			function lista(forma){
				borrar_combo1(forma.estudiante);
				forma.tipo.value=4;
				forma.accion.value=1;
				forma.action='<c:url value="/boletines/FiltroGuardarReporte2.jsp"/>';
				forma.submit();
			}

			function lanzar(tipo){
				document.frm.tipo.value=tipo;
				document.frm.action="<c:url value="/boletines/ControllerBoletinFiltroEdit.do"/>";
				document.frm.submit();
			}
			
			function lanzar2(tipo){
				document.frm.tipo.value='8';
				document.frm.cmd.value='1';
				document.frm.action="<c:url value="/siges/gestionAdministrativa/plantillaBoletin/PlantillaBoletin.do"/>";
				document.frm.target="";
				document.frm.submit();
			}
			
			
			function guardar(tipo){
				if(validarForma(document.frm)){
					document.frm.periodonom.value=document.frm.periodo.options[document.frm.periodo.selectedIndex].text;
					document.frm.tipo.value=tipo;
					document.frm.cmd.value="Buscar";					
					document.frm.submit();
				}
			}
			
			function cancelar(){
				if (confirm('¿Desea cancelar la generación del reporte?')){
 					document.frm.cmd.value="Cancelar";
					//document.frm.target="";
          document.frm.submit(); 
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
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtroLog.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
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
						<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st">
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">
						id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
						<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroLog.grado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
						Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach></c:forEach>
						var niv=combo_padre.value+'|'+combo_padre2.value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value;
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
					borrar_combo(combo_hijo); 
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3">
								<c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] && fila2[5]==fila3[5]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtroLog.grupo== fila3[0]}">SELECTED</c:if>';
									id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; 
									Hijos[k] = '<c:out value="${fila3[1]}"/>'; 
									id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[5]}"/>|<c:out value="${fila3[4]}"/>'; k++;
								</c:if>
							</c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>
						var niv=combo_padre.value+'|'+combo_padre2.value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value+'|'+combo_padre4.options[combo_padre4.selectedIndex].value;
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

			//-->
	</script>
	<%@include file="../mensaje.jsp"%>
	<form method="post" name="frm"  onSubmit="return validarForma(frm)" action='<c:url value="/boletines/FiltroGuardarReporte.jsp"/>'>
	<table border="0" align="center" width="100%">
	<caption>Reporte de Logros Pendientes</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
       <input class="boton" name="cmd1" type="button" value="Generar" onClick="guardar(document.frm.tipo.value)">
			 <input class="boton" name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		  </td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
		<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
		<input type="hidden" name="accion" value="0">
		<input type="hidden" name="cmd" value="Cancelar">
		<input type="hidden" name="periodonom" value='<c:out value="${sessionScope.filtroLog.periodonom}"/>'>
	  <input type="hidden" name="sede" value='<c:out value="${sessionScope.login.sedeId}"/>'>
		<input type="hidden" name="jornada" value='<c:out value="${sessionScope.login.jornadaId}"/>'>
		<table border="0" align="center" width="100%">
			<tr height="1">
				<td rowspan="2" width="420">
					<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/Boletines_0.gif" width="84"  height="26" border="0"></a>
					<img src="../etc/img/tabs/logros_pendientes_1.gif" width="84"  height="26" border="0">
					<a href="javaScript:lanzar2(8)"><img src="../etc/img/tabs/boletin_plantilla_0.gif" width="84"  height="26" border="0"></a>
				</td>
			</tr>
	  </table>
								<TABLE border="0" width="100%" cellpadding="3" cellSpacing="0">
										<tr>
										  <td><span class="style2" >*</span>Metodologia:</td>
										  <td>
                        <select name="metodologia" onChange='filtro2(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo)' style='width:120px;'>
										  		<option value='-9'>-- seleccione uno --</option>
													<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
														<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroLog.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
													</c:forEach>
										  	</select>
                      </td>
										  <td><span class="style2" >*</span>Grado:</td>
										  <td>
                          <select name="grado" onChange='filtro3(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo)' style='width:120px;'>
										  		<option value='-9'>-- seleccione uno --</option>
													<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila">
														<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroLog.grado== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
													</c:forEach>
										  	</select>
                      </td>
											<td>Grupo:</td>
											<td>
												<select name="grupo" style='width:120px;' onChange="lista(document.frm)">
													<option value='-9'>-- seleccione uno --</option>
												</select>
											</td>
								    </tr>
										<tr>
											<td>Estudiante:</td>
										  <td colspan="3">
											<select name="estudiante" style='width:300px;'>
											  <option value='-9'>-- seleccione uno --</option>
											   <c:forEach begin="0" items="${requestScope.logros_estudiantes}" var="fila">
												  <option value='<c:out value="${fila[0]}"/>'<c:if test="${sessionScope.filtroLog.estudiante== fila[0]}">SELECTED</c:if>> <c:out value="${fila[1]}"/> <c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/></option>
											   </c:forEach>
											</select>
										  </td>
											<td style='display:none;'>Orden:</td>
										  <td style='display:none;'>
	  								  	<select name="orden">
										  		<option value='-9' <c:if test="${sessionScope.filtroLog.orden== '-9'}">SELECTED</c:if>>Apellidos</option>
										  		<option value='0' <c:if test="${sessionScope.filtroLog.orden== '0'}">SELECTED</c:if>>Identificación</option>
										  		<option value='1' <c:if test="${sessionScope.filtroLog.orden== '1'}">SELECTED</c:if>>Nombres</option>
										  		<option value='2' <c:if test="${sessionScope.filtroLog.orden== '2'}">SELECTED</c:if>>Código</option>
										  	</select>
										  </td>
		 							  </tr>
										<tr>
											<td> <span class="style2" >*</span>Periodo:</td>
											<td>
                         <select name="periodo" style='width:120px;'>
                           <option value='-9'>-- Seleccione uno --</option>
                           <c:forEach begin="0" items="${requestScope.filtroPeriodoF}" var="fila">
                           <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroLog.periodo== fila[0]}">SELECTED</c:if>> <c:out value="${fila[1]}"/></option>
                           </c:forEach>
                         </select>
                       </td>
										</tr>
								</TABLE>
<!--//////////////////////////////-->
</form>
<script>
//filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_hijo2)
filtro2(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo);
document.frm.grado.onchange();</script>