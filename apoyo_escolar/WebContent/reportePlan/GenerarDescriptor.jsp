<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="filtroDescriptores" class="siges.reportePlan.beans.FiltroBeanReportesDescriptores" scope="session"/><jsp:setProperty name="filtroDescriptores" property="*"/>
<%@include file="../parametros.jsp"%>
		<script languaje='JavaScript'>
			
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frm(forma){
				   validarLista(forma.filvigencia, "- Vigencia", 1); 
				   validarLista(forma.metodologia, "- Metodologia", 1);			
			}

			function lanzar(tipo){
				document.frm.tipo.value=tipo;					
				document.frm.action="<c:url value="/reportePlan/ControllerReporteFiltroEdit.do"/>";
				document.frm.target="";
				document.frm.submit();
			}

			function guardar(tipo){
				if(validarForma(document.frm)){	
				    document.frm.sedenom.value='';//document.frm.sede.options[document.frm.sede.selectedIndex].text;
				    document.frm.jornadanom.value='';//=document.frm.jornada.options[document.frm.jornada.selectedIndex].text;
					document.frm.tipo.value=tipo;					
					document.frm.cmd.value="Buscar";
					document.frm.submit();		
				}	
			}
			
			function cancelar(){
				if (confirm('¿Desea cancelar la generación del reporte?')){
 					document.frm.cmd.value="Cancelar";
					parent.location.href='<c:url value="/bienvenida.jsp"/>';
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
				//combo_hijo4.selectedIndex=0;
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
									Sel_Hijos[k] = '<c:if test="${requestScope.filtroDescriptores.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
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
			
			function filtro2(combo_padre,combo_hijo){
					borrar_combo(combo_hijo);					
					<c:if test="${!empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
								<c:set var="y" value="${fila2[2]}" scope="request"/>									
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtroDescriptores.grado== fila2[0]}">SELECTED</c:if>';
									id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; 
									Hijos[k] = '<c:out value="${fila2[1]}"/>'; 
									id_Padre[k] = '<c:out value="${fila2[2]}"/>'; 
									k++;
							</c:if></c:forEach>
							Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>						
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
						}
					</c:if>
					
			}

			function filtro3(combo_padre,combo_padre2,combo_hijo){
				if(combo_padre2.selectedIndex==0){
					borrar_combo(combo_hijo);
				}else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroMetodologiaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroArea}">
						var Padres = new Array();
							<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila4" varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroArea}" var="fila3">
								<c:if test="${fila3[3]==fila4[0] && fila3[2]==fila4[2]}">
									Sel_Hijos[k] = '<c:if test="${requestScope.filtroDescriptores.area== fila3[0]}">SELECTED</c:if>';
									id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; 
									Hijos[k] = '<c:out value="${fila3[1]}"/>'; 
									id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>';
									k++;
								</c:if>
							</c:forEach>
							Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
							</c:forEach>
						var niv=combo_padre.value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value;						
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
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/reportePlan/FiltroGuardarDescriptor.jsp"/>'>
		<input type="hidden" name="repTipo" id="repTipo" value="9">
	<input type="hidden" name="repOrden" id="repOrden" value="2">
	<table border="0" align="center" width="100%">
	<caption>Generar Reporte de Descriptores</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
       	<input class='boton' name="cmd1" type="button" value="Generar" onClick="guardar(document.frm.tipo.value)">
        <input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		 </td>
		</tr>
	</table>
<!--//////////////////-->	
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>	
	<input type="hidden" name="sedenom" value='<c:out value="${requestScope.filtroDescriptores.sedenom}"/>'>
	<input type="hidden" name="jornadanom" value='<c:out value="${requestScope.filtroDescriptores.jornadanom}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<table border="0" align="center" width="100%">
		<tr height="1">
			<td rowspan="2" width="420"><a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/plan_de_estudios_0.gif" alt="Reporte de &Aacute;reas y Asignaturas" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/logro_0.gif" alt="Reporte de Logros" width="84"  height="26" border="0"></a><img src="../etc/img/tabs/descriptor_1.gif" alt="Reporte de Descriptores" width="84"  height="26" border="0"></td>
		</tr>
  </table>
	<TABLE width="100%" cellpadding="3" cellSpacing="0">
		<tr>
		<td width="20%"><span class="style2">*</span>Vigencia:</td>
			  <td width="30%">
					<select name="filvigencia"  style="width:120px;" >
					  <option value="-99" selected>--Seleccione uno--</option>
				       <c:forEach begin="0" items="${requestScope.listaVigencia}" var="metod">
					    <option value="<c:out value="${metod.codigo}"/>"    ><c:out value="${metod.nombre}"/></option>
					   </c:forEach>
				    </select>
		 </td>
		 <td>Metodologia:</td>
	
		<td>
      	<select name="metodologia" style='width:120px;' onChange='filtro2(document.frm.metodologia,document.frm.grado)'>
        <option value='-9'>-- seleccione una --</option>
					<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
					<option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope.filtroDescriptores.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
					</c:forEach>
        </select>
      </td>
      	</tr>
		<tr>
			<td>Grado:</td>
			<td>
      	<select name="grado" style='width:120px;' onChange='filtro3(document.frm.metodologia,document.frm.grado,document.frm.area)'>
        <option value='-9'>-- Todos --</option>
        </select>
      </td>
	
			<td>Área:</td>
			<td>
				<select name="area" style='width:150px;'>
        	<option value='-9'>-- Todos --</option>
 				</select>
      </td>
				</tr>
	
		<tr>
			<td>&nbsp;Periodo inicial:</td>
			<td> 
				<select name="periodoinicial">
	      <option value='-9'>-- Todos --</option>
	      <c:forEach begin="0" items="${requestScope.filtroPeriodo}" var="fila">
	      <option value='<c:out value="${fila.codigo}"/>'><c:out value="${fila.nombre}"/></option>
	      </c:forEach>
	      </select>
			</td>
			<td>Periodo final:</td>
			<td> 
				<select name="periodofinal">
	      <option value='-9'>-- Todos --</option>
	      <c:forEach begin="0" items="${requestScope.filtroPeriodo}" var="fila">
	      <option value='<c:out value="${fila.codigo }"/>'><c:out value="${fila.nombre }"/></option>
	      </c:forEach>
	    	</select>
			</td>
	  </tr>
	  	<tr>
			<td>Orden:</td>
			<td>
	      <select name="orden">
					<option value='-9' <c:if test="${requestScope.filtroDescriptores.orden== '-9'}">SELECTED</c:if>>Área</option>
					<option value='1' <c:if test="${requestScope.filtroDescriptores.orden== '1'}">SELECTED</c:if>>Código</option>
				</select>																			  											
      </td>
		</tr>
	</TABLE>
<!--//////////////////////////////-->
</form>
<script>
//document.frm.sede.onchange();
document.frm.metodologia.onchange();
</script>