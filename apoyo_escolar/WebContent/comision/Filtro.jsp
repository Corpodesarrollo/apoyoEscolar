<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>
<jsp:useBean id="filtroComision" class="siges.comision.beans.FiltroBeanCom" scope="session"/>
<jsp:setProperty name="filtroComision" property="*"/>
<%@include file="../parametros.jsp"%>
		<script languaje='javaScript'>
			<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frm(forma){
				if(forma.id.value=='')	forma.id.value=' ';
				if(forma.nombre1.value=='') forma.nombre1.value=' ';
				if(forma.nombre2.value=='') forma.nombre2.value=' ';
				if(forma.apellido1.value=='')	forma.apellido1.value=' ';
				if(forma.apellido2.value=='')	forma.apellido2.value=' ';
			}

			function cancelar(){
					parent.location.href='<c:url value="/bienvenida.jsp"/>';
			}
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Todos--","-1");
			}
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3){
				if(combo_padre.selectedIndex==0){
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3); 
				}else{
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
					<c:if test="${!empty sessionScope.filtroSedeF && !empty sessionScope.filtroJornadaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${sessionScope.filtroSedeF}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${sessionScope.filtroJornadaF}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtroComision.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
							</c:if></c:forEach>
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
			}
			function filtro2(combo_padre,combo_padre2,combo_hijo,combo_hijo2){
				if(combo_padre.selectedIndex==0 || combo_padre2.selectedIndex==0){
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2); 
				}else{
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
					<c:if test="${!empty sessionScope.filtroSedeF && !empty sessionScope.filtroJornadaF && !empty sessionScope.filtroGradoF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${sessionScope.filtroJornadaF}" var="fila2"  varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${sessionScope.filtroGradoF}" var="fila3">
								<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtroComision.grado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>'; k++;
								</c:if>
							</c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value;
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
			}
			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_hijo){
				if(combo_padre.selectedIndex==0 || combo_padre2.selectedIndex==0 || combo_padre3.selectedIndex==0)
					borrar_combo(combo_hijo); else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty sessionScope.filtroSedeF && !empty sessionScope.filtroJornadaF && !empty sessionScope.filtroGradoF && !empty sessionScope.filtroGrupoF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${sessionScope.filtroGradoF}" var="fila2"  varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${sessionScope.filtroGrupoF}" var="fila3">
								<c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] }">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtroComision.grupo== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>'; k++;
								</c:if>
							</c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>
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
			}
			//-->
		</script>
<%@include file="../mensaje.jsp"%>	
	<font size="1">
		<FORM  METHOD="POST" name='frm' onSubmit=" return validarForma(frm)" action='<c:url value="/comision/FiltroGuardar.jsp"/>'>
			<INPUT TYPE="hidden" NAME="nivel"  VALUE="1">
			<table border="1" align="center" bordercolor="#FFFFFF" width="80%">
			<caption>Comisi&oacute;n de Evaluaci&oacute;n, Filtro de busqueda</caption>
				<tr>
				  <td>
						<INPUT TYPE="submit" NAME="cmd1" VALUE="Aceptar" class="boton">
					</td>
				</tr>			
			</table>
			<table border="0" align="center" bordercolor="#FFFFFF" width="80%">
				<tr>
					<td>Sede:</td>
					<td>
						<select name="sede" onChange='filtro(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo)' style='width:300px;'>
							<option value='-1'>-- Todos --</option>
							<c:forEach begin="0" items="${sessionScope.filtroSedeF}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroComision.sede== fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>											
					</td>	
					<td>Jornada:</td>
					<td>
						<select name="jornada" onChange='filtro2(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo)' style='width:120px;'>
							<option value='-1'>-- Todos --</option>
						</select>							
					</td>
				</tr>
				<tr>
				<td><span class="style2">*</span>Metodología:</td>
				<td>
					<select name="metodologia" style='width:120px;'>
						<option value='-1'>-- Todos --</option>
						<c:forEach begin="0" items="${sessionScope.filtroMetodologiaF}" var="fila">
						<option value='<c:out value="${fila[0]}"/>'><c:out value="${fila[1]}"/></option>
						</c:forEach>
						</select>
						</td>	
				<td>Grado:</td>
					<td>
						<select name="grado" onChange='filtro3(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo)' style='width:120px;'>
							<option value='-1'>-- Todos --</option>
						</select>											
					</td>	
				</tr>
				<tr>
					<td>Grupo:</td>
					<td>
						<select name="grupo" style='width:120px;'>
							<option value='-1'>-- Todos --</option>
						</select>							
					</td>
					<td>Nº de identificación:</td>
					<td>
						<input type='text' name='id'  maxlength="12" value='<c:out value="${sessionScope.filtroComision.id}"/>' onKeyPress='return acepteNumeros(event)'>
					</td>	
				</tr>	
				<tr>
					<td>Primer nombre:</td>
					<td>
						<input type='text' name='nombre1'  maxlength="10" value='<c:out value="${sessionScope.filtroComision.nombre1}"/>'>
					</td>	
					<td>Segundo nombre:</td>
					<td>
						<input type='text' name='nombre2'  maxlength="10"  value='<c:out value="${sessionScope.filtroComision.nombre2}"/>'>
					</td>	
				</tr>	
				<tr>
					<td>Primer apellido:</td>
					<td>
						<input type='text' name='apellido1'  maxlength="10" value='<c:out value="${sessionScope.filtroComision.apellido1}"/>'>
					</td>	
					<td>Segundo apellido:</td>
					<td>
						<input type='text' name='apellido2'  maxlength="10"  value='<c:out value="${sessionScope.filtroComision.apellido2}"/>'>
					</td>	
				</tr>	
				<tr>
					<td>Ordenado por:</td>
					<td>
						<select name="orden">
							<option value='-1' <c:if test="${sessionScope.filtroComision.orden== '-1'}">SELECTED</c:if>>Codigo interno</option>
							<option value='0' <c:if test="${sessionScope.filtroComision.orden== '0'}">SELECTED</c:if>>Número de identificación</option>
							<option value='1' <c:if test="${sessionScope.filtroComision.orden== '1'}">SELECTED</c:if>>Nombres</option>
							<option value='2' <c:if test="${sessionScope.filtroComision.orden== '2'}">SELECTED</c:if>>Apellidos</option>
						</select>	
					</td>
				</tr>
			</table>
		</FORM>
	</font>		
<script>document.frm.sede.onchange();document.frm.jornada.onchange();document.frm.grado.onchange();</script>