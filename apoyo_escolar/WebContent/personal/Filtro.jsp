<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="filtroPersonal" class="siges.personal.beans.FiltroPersonal" scope="session"/><jsp:setProperty name="filtroPersonal" property="*"/>
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
				combo.options[0] = new Option("--Todos--","-99");
			}
			function filtro(combo_padre,combo_hijo){
				if(combo_padre.selectedIndex==0){
					borrar_combo(combo_hijo);
				}else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty sessionScope.filtroSedeF && !empty sessionScope.filtroJornadaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${sessionScope.filtroSedeF}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${sessionScope.filtroJornadaF}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtroPersonal.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
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
			//-->
		</script>
<%@include file="../mensaje.jsp"%>
		<FORM  METHOD="POST" name='frm' onSubmit=" return validarForma(frm)" action='<c:url value="/personal/FiltroGuardar.jsp"/>'>
			<INPUT TYPE="hidden" NAME="nivel"  VALUE="1"><br>
			<table border="0" align="center" width="90%" cellpadding="1" cellspacing="0" >
				<caption>Filtro de busqueda</caption>
				<tr>
				  <td>
						<INPUT class='boton' TYPE="submit" NAME="cmd1"  VALUE="Aceptar">
					</td>
				</tr>
			</table>
			<table border="0" align="center" width="90%" cellpadding="1" cellspacing="0">
				<tr><th colspan="4" class="Fila0">Búsqueda por ubicación</th></tr>
				<tr>
					<td width="20%">Sede:</td>
					<td colspan="3">
						<select name="sede" onChange='filtro(document.frm.sede, document.frm.jornada)' style='width:300px;'>
							<option value='-99'>-- Todos --</option>
							<c:forEach begin="0" items="${sessionScope.filtroSedeF}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroPersonal.sede== fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>											
					</td>	
				</tr>	
				<tr>	
					<td>Jornada:</td>
					<td colspan="3">
						<select name='jornada' style='width:150px;'>
							<option value='-99'>-- Todos --</option>
						</select>
					</td>
				</tr>
				<tr><th colspan="4" class="Fila0">Búsqueda por identificación</th></tr>
				<tr>
					<td>Tipo de personal:</td>
					<td>
						<select name="tipoPersona">
							<option value='0' <c:if test="${sessionScope.filtroPersonal.tipoPersona== ''}">SELECTED</c:if>>--Todos--</option>
							<option value='410' <c:if test="${sessionScope.filtroPersonal.tipoPersona== '410'}">SELECTED</c:if>>Rector(a)</option>
							<option value='422' <c:if test="${sessionScope.filtroPersonal.tipoPersona== '422'}">SELECTED</c:if>>Docente</option>
							<option value='421' <c:if test="${sessionScope.filtroPersonal.tipoPersona== '421'}">SELECTED</c:if>>Académico</option>
							<option value='423' <c:if test="${sessionScope.filtroPersonal.tipoPersona== '423'}">SELECTED</c:if>>Administrativo</option>
						</select>	
					</td>
					<td>Nº de identificación:</td>
					<td>
						<input type='text' name='id'  maxlength="12" value='<c:out value="${sessionScope.filtroPersonal.id}"/>' onKeyPress='return acepteNumeros(event)'>
					</td>	
				</tr>	
				<tr>
					<td>Primer nombre:</td>
					<td>
						<input type='text' name='nombre1' value='<c:out value="${sessionScope.filtroPersonal.nombre1}"/>'>
					</td>	
					<td>Segundo nombre:</td>
					<td>
						<input type='text' name='nombre2' value='<c:out value="${sessionScope.filtroPersonal.nombre2}"/>'>
					</td>	
				</tr>	
				<tr>
					<td>Primer apellido:</td>
					<td>
						<input type='text' name='apellido1' value='<c:out value="${sessionScope.filtroPersonal.apellido1}"/>'>
					</td>	
					<td>Segundo apellido:</td>
					<td>
						<input type='text' name='apellido2' value='<c:out value="${sessionScope.filtroPersonal.apellido2}"/>'>
					</td>	
				</tr>	
				<tr><th colspan="4" class="Fila0">Criterio de ordenación</th></tr>
				<tr>
					<td>Orden:</td>
					<td>
						<select name="orden">
							<option value='1' <c:if test="${sessionScope.filtroPersonal.orden== '0'}">SELECTED</c:if>>Número de identificación</option>
							<option value='2' <c:if test="${sessionScope.filtroPersonal.orden== '1'}">SELECTED</c:if>>Nombres</option>
							<option value='3' <c:if test="${sessionScope.filtroPersonal.orden== '2'}">SELECTED</c:if>>Apellidos</option>
						</select>	
					</td>
				</tr>
			</table>
		</FORM>
<script>document.frm.sede.onchange();</script>