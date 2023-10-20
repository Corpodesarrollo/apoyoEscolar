<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="nuevoConflictoFiltro" class="siges.conflictoGrupo.beans.ConflictoFiltro" scope="session"/>
<%@ page import="siges.util.Recursos" %>
<%@include file="../parametros.jsp"%>
	<%pageContext.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);%>
		<script languaje='javaScript'>			
			<!--
		  var nav4=window.Event ? true : false;
		  
		  function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-- Seleccione uno --","-1");
			}
		  
		  function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
		  
		  function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
				borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
				<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoConflictoFiltro.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>
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
				filtro2(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo);
			}
			
			function filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_hijo2){
				var id=0;
				borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
				<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st"><c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4" varStatus="st2">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3"><c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoConflictoFiltro.grado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
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
			
			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_padre4,combo_hijo){
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
				<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3"><c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3]  && fila2[5]==fila3[5]}">Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoConflictoFiltro.grupo== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[5]}"/>|<c:out value="${fila3[4]}"/>'; k++;</c:if></c:forEach>
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
			}
			
		  function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		  }

			function cancelar(){
				if (confirm('¿Desea cancelar la inserción de la información de gobierno escolar?')){
					document.frm.cmd.value="Cancelar";
					document.frm.submit();
				}
			}

			function hacerValidaciones_frm(forma){				
				validarLista(forma.sede, "- Sede",1)
				validarLista(forma.jornada, "- Jornada",1)
				validarLista(forma.metodologia, "- Metodologia",1)
				validarLista(forma.grado, "- Grado",1)
				validarLista(forma.grupo, "- Grupo",1)
				validarLista(forma.periodo, "- Periodo",1)
			}					

			function inhabilitarFormulario(){
				for(var i=0;i<document.frm.elements.length;i++){
					if(document.frm.elements[i].type != "hidden" && document.frm.elements[i].type != "button" && document.frm.elements[i].type != "submit")
					document.frm.elements[i].disabled=true;
				}	
			}
			function lanzar(t,c){
				guardar(t,c)
				//document.frm.tipo.value=tipo;
				//document.frm.action='<c:url value="/conflicto/ControllerEditar.do"/>';
				//document.frm.submit();
			}
			function guardar(tipo,cat){
				if(validarForma(document.frm)){
					//document.frm.cesednom.value=document.frm.cesede.options[document.frm.cesede.selectedIndex].text;
					document.frm.cepernom.value=document.frm.periodo.options[document.frm.periodo.selectedIndex].text;
					document.frm.tipo.value=tipo;
					document.frm.categoria.value=cat;
					document.frm.cmd.value="Guardar";
					document.frm.submit();
				}
			}
		-->
	</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
		<form method="post" name="frm" onSubmit=" return validarForma(frm)"  action='<c:url value="/conflictoGrupo/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
			  <td width="45%" >
       <input class='boton' name="cmd1" type="button" value="Ver Formulario" onClick="guardar(10,-1)">
			</tr>		
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value="">
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value=''>
	<input type="hidden" name="cesednom" value=''>
	<input type="hidden" name="cepernom" value=''>
	<input type="hidden" name="categoria" value=''>
	<!--<input type="hidden" name="periodo" value='0'>-->
	<input type="hidden" name="readonly" value='<c:out value="${requestScope.readonly}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
				<td rowspan="2" width="600" bgcolor="#FFFFFF">
					<img src="../etc/img/tabs/confilcto_escolar_grupo_1.gif" alt="Filtro" border="0"  height="26">
				</td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		<TABLE width="100%" cellpadding="3" cellSpacing="0">
		<caption></caption>
			<tr>
				<td><span class="style2" >*</span>Sede:</td>
		    <td>
					<select name="sede" onChange='filtro(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo,document.frm.metodologia)' style='width:300px;'>
						<option value='-1'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
							<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoConflictoFiltro.sede == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2" >*</span>Jornada:</td>
			  <td>
					<select name="jornada" style='width:150px;' >
						<option value='-1'>-- Seleccione uno --</option>
					</select>							
			  </td>
			</tr>
			<tr>
			  <td><span class="style2">*</span>Metodologia:</td>
			  <td>
          <select name="metodologia"  style='width:150px;' onChange='filtro2(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo)'>
            <option value='-1'>-- Seleccione uno --</option>
           	<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
           		<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoConflictoFiltro.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/>
						</c:forEach>
          </select>
				</td>			
			  <td><span class="style2" >*</span>Grado:</td>
			  <td>
                <select name="grado" onChange='filtro3(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo);' style='width:150px;'>
                 <option value='-1'>-- Seleccione uno --</option>
                </select>
				</td>
			</tr>
		  <tr>
				<td><span class="style2" >*</span>Grupo:</td>
			  <td>
                <select name="grupo" style='width:150px;' onChange=''>
    	            <option value='-1'>-- Seleccione uno --</option>
                </select>
              </td>
				<td><span class="style2">*</span> Periodo:</td>
				<td>
					<select name="periodo">
          	<option value="-1">--seleccione uno--</option>
           	<c:forEach begin="0" items="${filtroPeriodo}" var="fila">
           		<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoConflictoFiltro.periodo== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/>
						</c:forEach>
           </select>
        </td>
			</tr> 
  </TABLE>
	</form>
<script>
document.frm.sede.onchange();
document.frm.metodologia.onchange();
document.frm.grado.onchange();
</script>