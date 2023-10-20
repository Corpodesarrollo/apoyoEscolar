<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="filtroEst" class="articulacion.repHorariosArt.vo.FiltroBeanRepEstudiante" scope="session"/><jsp:setProperty name="filtroEst" property="*"/>
<%@include file="../parametros.jsp"%>
		<script languaje='JavaScript'>
			
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frm(forma){
			  // validarLista(forma.grado, "- Grado", 1);
			   //validarLista(forma.grupo, "- Grupo", 1);
			}

			function lanzar(tipo){	
				document.frm.tipo.value=tipo;
				document.frm.action="<c:url value="/repHorariosArt/ControllerRepHorariosArtFiltroEdit.do"/>";
				document.frm.target="";
				document.frm.submit();
			}

			function guardar(tipo){
				if(validarForma(document.frm)){
					document.frm.tipo.value=tipo;
					document.frm.cmd.value="Buscar";
					document.frm.submit();
				}	
			}
			function cancelar(){
				if (confirm('¿No desea generar Boletines?')){
 					document.frm.cmd.value="Cancelar";
					location.href='<c:url value="/bienvenida.jsp"/>';
				}
			}
			function cambiarAusenciasT(obj){
				if(obj.checked==true){
					obj.value=1;
				}else{
					obj.value=0;
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
				combo.options[0] = new Option("--Seleccione uno--","-9");
			}
			function borrar_combo1(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione una--","-9");
			}	
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
				combo_hijo4.selectedIndex=0;
				if(combo_padre.selectedIndex==0){
					borrar_combo1(combo_hijo); borrar_combo1(combo_hijo2); borrar_combo1(combo_hijo3); 
				}else{
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="session"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtro.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
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
							//sede, jornada,metodologia,grado,grupo	
			function filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_hijo2){
				var id=0;
				if(combo_padre3.selectedIndex==0)
					borrar_combo(combo_hijo);
			    else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">;var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st">
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">
						id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
							<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">
							Sel_Hijos[k] = '<c:if test="${sessionScope.filtro.grado== fila3[0]}">SELECTED</c:if>';
							id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; 
							id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; 
							k++;
							</c:if>
						</c:forEach>
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
			}
					
					
					
			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_hijo){
				if(combo_padre3.selectedIndex==0)
					borrar_combo(combo_hijo); else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3">
								<c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] && fila2[5]==fila3[5]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtro.grupo== fila3[0]}">SELECTED</c:if>';
									id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; 
									Hijos[k] = '<c:out value="${fila3[1]}"/>'; 
									id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>'; k++;
								</c:if>
							</c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>
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
			}					
		</script>
<%@include file="../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/repHorariosArt/FiltroGuardarEstudiante.jsp"/>'>
	<br>
	<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
	<caption>
	Gestion Administrativa - Generar Reportes Horarios Articulación, Filtro de busqueda -
	</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        	<input  name="cmd1" type="button" value="Generar" onClick="guardar(document.frm.tipo.value)">
            <input  name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		 </td>
		</tr>
	</table>
<!--//////////////////-->	
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>	
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<input type="hidden" name="sede" value='<c:out value="${sessionScope.login.sedeId}"/>'>
	<input type="hidden" name="jornada" value='<c:out value="${sessionScope.login.jornadaId}"/>'>
	<input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>

	<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
		<tr height="1">
			<td rowspan="2" width="420" bgcolor="#FFFFFF"><img src="../etc/img/tabs/Horario_Estudiante_1.gif" width="84"  height="26" border="0"><a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/horario_grupo_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/horario_docente_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/horario_espacio_fisico_0.gif" width="84"  height="26" border="0"></a></td>
		</tr>
		<tr>
		</tr>
  </table>
			<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>
				<td>
					<TABLE width="100%" cellpadding="0" cellSpacing="10">
						<tr>
							<td>
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
										<tr>
										  <td colspan="4"><hr></td>
									  </tr>
										<tr style='display:none;'>
											<td><span class="style2" >*</span>Sede:</td>
										    <td>
										    
<!-- 												<select name="sede" onChange='filtro(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo,document.frm.metodologia)' style='width:300px;'>
													<option value='-9'>-- Seleccione una --</option>
													<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
														<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtro.sede== fila[0]}">SELECTED</c:if>>
														<c:out value="${fila[1]}"/></option>
													</c:forEach>
												</select>											
-->												
												
										   </td>
											<td><span class="style2" >*</span>Jornada:</td>
										  <td><!--onChange='filtro2(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo)'-->
<!-- 
												<select name="jornada" style='width:120px;'>
													<option value='-9'>-- seleccione uno --</option>
												</select>							
-->												
										  </td>
									  </tr>
										<tr>
										  <td><span class="style2">*</span><b>&nbsp;Vigencia:</b></td>
											<td>
												<select name="filAnoVigenciaArt" style='width:50px;'>
													<option value="-99">--</option>
													<c:forEach begin="0" items="${requestScope.VigenciaArtHor}" var="vig">
														<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.filtroEst.filAnoVigenciaArt}">selected</c:if>><c:out value="${vig}"/></option>
													</c:forEach>
												</select>
												<select name="filPerVigenciaArt" style='width:30px;'>
													<option value="-99">--</option>
													<c:forEach begin="0" items="${requestScope.PeriodoArtHor}" var="vig">
														<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.filtroEst.filPerVigenciaArt}">selected</c:if>><c:out value="${vig}"/></option>
													</c:forEach>
												</select>
											</td>
											
										 <td><span class="style2">*</span><b>&nbsp;Componente:</b></td>
											<td>
												<select name="filComponenteArt" style='width:100px;'>
													<option value="-99">--Seleccione una--</option>
													<c:forEach begin="0" items="${requestScope.itemCom}" var="comp">
														<option value="<c:out value="${comp.codigo}"/>" <c:if test="${comp.codigo==sessionScope.filtroEst.filComponenteArt}">selected</c:if>><c:out value="${comp.nombre}"/></option>
													</c:forEach>
												</select>
											</td>                                          
								  </tr>
								  <tr>
								    
								  </tr>
								</TABLE>
								
							</td>
						</tr>
					</table>
				</td>								
			<tr bgcolor="#000000" height="1">
			</tr>
		</table>	
	</font>		
	
<!--//////////////////////////////-->
</form>
<script>
document.frm.grado.onchange();
</script>