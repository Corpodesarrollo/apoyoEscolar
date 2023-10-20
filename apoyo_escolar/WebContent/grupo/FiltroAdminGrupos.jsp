<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="filtrogroup" class="siges.grupo.beans.FiltroBeanGrupos" scope="session"/><jsp:setProperty name="filtrogroup" property="*"/>
<%@include file="../parametros.jsp"%>
		<script languaje='JavaScript'>
			
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frm(forma){
				   validarLista(forma.sede, "- Sede", 1);
				   validarLista(forma.jornada, "- Jornada", 1);			
				   validarLista(forma.metodologia, "- Metodología", 1);
			}

			function lanzar(tipo){	
				document.frm.tipo.value=tipo;					
				document.frm.action="<c:url value="/grupo/ControllerGrupoFiltroEdit.do"/>";
				document.frm.target="";
				document.frm.submit();
			}

			
			function cancelar(){
				if (confirm('¿No desea asignar Grupos?'))
					parent.location.href='<c:url value="/bienvenida.jsp"/>';				
			}

			function buscar(){
				if(validarForma(document.frm)){
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
			
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo4){
				combo_hijo4.selectedIndex=0;
				if(combo_padre.selectedIndex==0){
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
				}else{
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtrogroup.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
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
			
			function filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo){
				if(combo_padre.selectedIndex==0 || combo_padre2.selectedIndex==0){
					borrar_combo(combo_hijo);
				}else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">
							<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
								<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtrogroup.grado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;
								</c:if>
							</c:forEach></c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.value;
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


			function filtro3(combo_padre,combo_hijo){
				borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroMetodologiaF && !empty requestScope.filtroGradoF2}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"><c:if test="${fila[0]==fila2[2] && (fila2[0]>=0 || fila2[0]==40 || fila2[0]==41)}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtrogroup.grado== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.value;
						var val_padre = -1;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv)
								val_padre=k;
						}
					if(val_padre!=-1){
						var no_hijos = Padres[val_padre].Hijos.length;
						for(i=0; i < no_hijos; i++){
							if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
							}else
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
						}
				}</c:if>
			}
		</script>
<%@include file="../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/grupo/FiltroGuardar.jsp"/>'>
	
	<br>
	<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
	<caption>
	Gestion Administrativa - Administraci&oacute;n de Grupos, Filtro de busqueda -
	</caption>
		<tr>
		  <td width="100%" bgcolor="#FFFFFF">
        	<input  name="cmd1" type="button" value="Buscar"  onClick="buscar()" class='boton'>
            <input  name="cmd12" type="button" value="Cancelar" onClick="cancelar()" class='boton'>
		</tr>
	</table>
<!--//////////////////-->	
	<!-- FICHAS A MOST1RAR AL USUARIO -->
	<INPUT TYPE="hidden" NAME="nivel"  VALUE="1">
	<INPUT TYPE="hidden" NAME="tipo"  VALUE="1">
	<!-- <input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'> -->
	<input type="hidden" name="cmd" value="Buscar">
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='180'>

	<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
		<tr height="1">
			<td rowspan="2" width="100" bgcolor="#FFFFFF"><img src="../etc/img/tabs/grupos_1.gif" alt="Administraci&oacute;n de Grupos" width="84"  height="26" border="0"></td>
		</tr>
		<tr>
		</tr>
  </table>
			<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>
				<td>
					<TABLE width="100%" cellpadding="0" cellSpacing="10">
						<tr>
							<td>
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
										<tr>
											<td><span class="style2" >*</span>Sede:</td>
										    <td>
												<select name="sede" onChange='filtro(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.metodologia)' style='width:300px;'>
													<option value='-9'>-- Todos --</option>
													<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
													<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtrogroup.sede== fila[0]}">SELECTED</c:if>>
													<c:out value="${fila[1]}"/></option>
													</c:forEach>
												</select>											
										   </td>
											<td><span class="style2" >*</span>Jornada:</td>
										  	<td>
												<select name="jornada" style='width:120px;' onChange='filtro2(document.frm.sede,document.frm.jornada,document.frm.metodologia,document.frm.grado)'>
													<option value='-9'>-- Todos --</option>
												</select>																	  	
										  </td>
								  </tr>
										<tr>
										<td><span class="style2">*</span>Metodología:</td>
										<td>
											<select name="metodologia" style='width:120px;' onChange='filtro2(document.frm.sede,document.frm.jornada,document.frm.metodologia,document.frm.grado)'>
												<option value='-3'>-- Seleccione uno --</option>
												<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
													<option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.filtroEvaluacion.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
												</c:forEach>
											</select>
										</td>	
										  <td>Grado:</td>
										  <td>
                                            <select name="grado" style='width:120px;'>
                                              <option value='-9'>-- Todos --</option>
                                            </select>
                                          </td>
                                          </tr>
                                          <tr>
										  <td>Ordenado por:</td>
										  <td>
                                          <select name="orden">
										  		<option value='-9' <c:if test="${sessionScope.filtrogroup.orden== '-9'}">SELECTED</c:if>>Grado</option>
										  		<option value='1' <c:if test="${sessionScope.filtrogroup.orden== '1'}">SELECTED</c:if>>Código
										  		interno</option>
										  	</select>
                                          </td>
								  </tr>
										<tr><td colspan="6"><hr></td></tr>
								</TABLE>
							</td>
						</tr>
					</table>
				</td>								
			<tr bgcolor="#000000" height="1">
			</tr>
		</table>	

<!--//////////////////////////////-->
</form>

<script>
document.frm.sede.onchange();
document.frm.jornada.onchange();
</script>