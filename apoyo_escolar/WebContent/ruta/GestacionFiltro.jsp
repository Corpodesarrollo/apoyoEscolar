<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="filtroVO" class="siges.ruta.vo.FiltroVO" scope="session"/>
<jsp:useBean id="rutaParams" class="siges.ruta.vo.ParamsVO" scope="page"/>
<%@include file="../parametros.jsp"%>
<%pageContext.setAttribute("filtroPeriodos",Recursos.recursoEstatico[Recursos.PERIODO]);%>
		<script languaje='javaScript'>
			<!--
			var fichaNutricion=1;
			var fichaGestacion=0;
			
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			
			function hacerValidaciones_frm(forma){
				forma.id.value=trim(forma.id.value);
				forma.nombre1.value=trim(forma.nombre1.value);
				forma.nombre2.value=trim(forma.nombre2.value);
				forma.apellido1.value=trim(forma.apellido1.value);
				forma.apellido2.value=trim(forma.apellido2.value);
				if(forma.id.value=='' && forma.nombre1.value=='' && forma.nombre2.value=='' && forma.apellido1.value=='' && forma.apellido2.value==''){
					validarLista(forma.sede,"- Sede",1)
					validarLista(forma.jornada,"- Jornada",1)
					validarLista(forma.grado,"- Grado",1)
					validarLista(forma.grupo,"- Grupo",1)
				}
				validarLista(forma.periodo,"- Periodo",1)
				if(forma.id.value=='')	forma.id.value=' ';
				if(forma.nombre1.value=='') forma.nombre1.value=' ';
				if(forma.nombre2.value=='') forma.nombre2.value=' ';
				if(forma.apellido1.value=='')	forma.apellido1.value=' ';
				if(forma.apellido2.value=='')	forma.apellido2.value=' ';
			}

			function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			
			function lanzar(tipo){
				document.frm.tipo.value=tipo;
				document.frm.target='centro';
				document.frm.action='<c:url value="/ruta/Nuevo.do"/>';
				document.frm.submit();
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
			
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroVO.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>
							Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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
				</c:if>}
				
			function filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_hijo2){
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
					var id=0;
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st"><c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3"><c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroVO.grado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
						Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach></c:forEach>
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
				</c:if>}
				
			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_hijo){
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3"><c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] }">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroVO.grupo== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>'; k++;</c:if></c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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
				</c:if>}
			//-->
		</script>
<%@include file="../mensaje.jsp"%>	
	<font size="1">
		<FORM  METHOD="POST" name='frm' onSubmit=" return validarForma(frm)" action='<c:url value="/ruta/FiltroGuardar.jsp"/>'>
			<INPUT TYPE="hidden" NAME="nivel"  VALUE="1">
			<INPUT TYPE="hidden" NAME="cmd" VALUE="Buscar">
			<INPUT TYPE="hidden" NAME="tipo"  VALUE='<c:out value="${pageScope.rutaParams.FICHA_GESTACION_FILTRO}"/>'>
			<input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
			<table border="0" align="center" bordercolor="#FFFFFF" width="95%" cellpadding="0" cellSpacing="0">
			<caption>Gestación - Filtro de busqueda</caption>
				<tr>
				  <td>
						<INPUT class='boton' TYPE="submit" NAME="cmd1"  VALUE="Aceptar">
					</td>
				</tr>			
			</table>
		<table cellpadding="2" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
				<td width="600" bgcolor="#FFFFFF">
				<script>
				if(fichaNutricion==1){
					document.write('<a href="javaScript:lanzar(<c:out value="${pageScope.rutaParams.FICHA_NUTRICION_FILTRO}"/>)"><img src="<c:url value="/etc/img/tabs/nutricion_0.gif"/>" alt=""  height="26" border="0"></a>');
				}
				if(fichaGestacion==1){
					document.write('<img src="<c:url value="/etc/img/tabs/gestacion_1.gif"/>" alt=""  height="26" border="0">');
				}
				</script></td>
			</tr>
	  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
			<table border="0" align="center" bordercolor="#FFFFFF" width="95%" cellpadding="2" cellSpacing="0">
				<tr>
					<td><span class="style2">*</span> Sede:</td>
					<td colspan="3">
						<select name="sede" onChange='filtro(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo,document.frm.metodologia)' style='width:406px;'>
						<option value='-9'>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
						<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroVO.sede== fila[0]}">SELECTED</c:if>>
						<c:out value="${fila[1]}"/></option>
						</c:forEach>
						</select>											
					</td>	
				</tr>
				<tr>
					<td><span class="style2">*</span> Jornada:</td>
					<td>
						<select name="jornada" style='width:120px;' onChange='filtro2(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo)'>
						<option value='-9'>--Seleccione uno--</option>
						</select>							
					</td>
				<td><span class="style2">*</span> Grado:</td>
					<td>
            <select name="grado" onChange='filtro3(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo)' style='width:120px;'>
            <option value='-9'>--Seleccione uno--</option>
            </select>
					</td>	
				</tr>
				<tr>
					<td><span class="style2">*</span>Grupo:</td>
					<td>
						<select name="grupo" style='width:120px;'>
							<option value='-9'>--Seleccione uno--</option>
						</select>							
					</td>
					<td><span class="style2">*</span> Periodo:</td>
					<td>
						<select name="periodo" style='width:100px;'>
						<option value='-9'>--Seleccione uno--</option>
						<c:forEach begin="0" items="${filtroPeriodos}" var="fila">
						<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroVO.periodo==fila[0]}">SELECTED</c:if>>
						<c:out value="${fila[1]}"/></option>
						</c:forEach>
						</select>											
					</td>	
				</tr>
				<tr>
					<td>Ordenado por:</td>
					<td>
						<select name="orden">
							<option value='2' <c:if test="${sessionScope.filtro.orden== '2'}">SELECTED</c:if>>Apellidos</option>
							<option value='0' <c:if test="${sessionScope.filtro.orden== '0'}">SELECTED</c:if>>Identificación</option>
							<option value='1' <c:if test="${sessionScope.filtro.orden== '1'}">SELECTED</c:if>>Nombres</option>
						</select>	
					</td>
				</tr>	
				</table>
				<table border="0" align="center" bordercolor="#FFFFFF" width="95%" cellpadding="2" cellSpacing="0">
				<tr>
				<td colspan='1'><hr></td>
				<td colspan='3'>BÚSQUEDA POR IDENTIFICACIÓN</td>
				</tr>
				<tr>
					<td>Nº de identificación:</td>
					<td>
						<input type='text' name='id'  maxlength="15" value='<c:out value="${sessionScope.filtroVO.id}"/>' >
					</td>	
					<td>Primer nombre:</td>
					<td>
						<input type='text' name='nombre1' value='<c:out value="${sessionScope.filtroVO.nombre1}"/>'>
					</td>	
				</tr>	
				<tr>
					<td>Segundo nombre:</td>
					<td>
						<input type='text' name='nombre2' value='<c:out value="${sessionScope.filtroVO.nombre2}"/>'>
					</td>	
					<td>Primer apellido:</td>
					<td>
						<input type='text' name='apellido1' value='<c:out value="${sessionScope.filtroVO.apellido1}"/>'>
					</td>	
				</tr>	
				<tr>
					<td>Segundo apellido:</td>
					<td>
						<input type='text' name='apellido2' value='<c:out value="${sessionScope.filtroVO.apellido2}"/>'>
					</td>	
				</tr>	
			</table>
		</FORM>
	</font>		
<script>
document.frm.sede.onchange();
filtro2(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo);
document.frm.grado.onchange();</script>