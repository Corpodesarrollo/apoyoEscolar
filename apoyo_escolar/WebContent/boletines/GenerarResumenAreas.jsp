<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="filtroResumenAreas" class="siges.boletines.beans.FiltroBeanResumenAreas" scope="session"/><jsp:setProperty name="filtroResumenAreas" property="*"/>
<%@include file="../parametros.jsp"%>
 <link type="text/css" href="http://jquery-ui.googlecode.com/svn/tags/1.7/themes/redmond/jquery-ui.css" rel="stylesheet" />
 
  <script type="text/javascript" src="jquery-1.3.2.min.js"></script>
 
  <script type="text/javascript" src="jquery-ui.min.js"></script>
		<script languaje='JavaScript'>
		$(function() { 
			$('html').css('font-size', 10);
			$('#dialog').dialog({
	    		autoOpen: false,
	            modal: true,
	            title: 'Áreas',
	            resizable: false,
	            width: 400,
	            minWidth: 400,
	            maxWidth: 650,
	            show: 'fold',
	            hide: 'scale',
	            buttons: {
	                Continuar: function() {
	                    $(this).dialog( "close" );
	                }
	            }
			});
	    });
	 	
		function desarea(){
			if(document.frm.tareas.checked==true)
				document.frm.areas.disabled=true;
			else
				document.frm.areas.disabled=false;	
		}
		
		var areasselect="";
		var asigselect="";
		function areassel(check){
			var areassp;
			if(check.checked==true){
				if(areasselect!="")
					areasselect=areasselect+","+check.value;
				else
					areasselect=areasselect+check.value;
			}
			else{
				areassp=areasselect.split(",");
				if(areassp.length!=1){
					for(var i=0;i<areassp.length;i++){
						if(areassp[i]==check.value)
							areassp[i]="";
					}
					areasselect="";
					for(var j=0;j<areassp.length;j++){
						if(areassp[j]!=""){
							if (j==0)
								areasselect=areasselect+areassp[j];
							else
								areasselect=areasselect+","+areassp[j];
						}
					}
				}
				else
					areasselect="";
				
			}
		}
		
	    function MostrarDialog() {
	    	var metods=document.frm.metodologia.options[document.frm.metodologia.selectedIndex].value;
	    	var pes;
	    	var pescheck;
	    	var erTd = document.getElementById('dialog');
	    	var txt = document.createElement('br');
	    	var areassp=areasselect.split(",");
	    	while (erTd.hasChildNodes())
	    		erTd.removeChild(erTd.firstChild);
	    	<c:forEach begin="0" items="${requestScope.FiltroAreas}" var="per" varStatus="st">
	    			pes='<c:out value="${per[2]}"/>';
	    			pescheck='<c:out value="${per[0]}"/>';
	    			if (metods==pes){
	    				var erInput = document.createElement('INPUT');
	    				erInput.setAttribute("type","checkbox");
	    				erInput.setAttribute("value","<c:out value="${per[0]}"/>");
	    				erInput.setAttribute("id","areas");
	    				erInput.setAttribute("title","titulo_checkbox");
	    				erInput.onclick = function() { areassel(this); };
	    				erTd.appendChild(erInput);
	    				if(areasselect!=""){
	    					for(var u=0;u<areassp.length;u++){
	    						if(pescheck==areassp[u])
	    							erInput.checked = "checked";
	    					}
	    				}
	    				erTd.appendChild(document.createTextNode("<c:out value="${per[1]}"/>"));
	    				erTd.appendChild(document.createElement("br"));
	    			}
			    	//<input type="checkbox" name="areas" value="<c:out value="${per[0]}"/>" /> <c:out value="${per[1]}"/><br>
	    	</c:forEach>
	    	$("#dialog").dialog('open');
	      return true;
	    }  
	    
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frm(forma){
				validarLista(forma.sede, "- Sede", 1);
				validarLista(forma.jornada, "- Jornada", 1);			
				validarLista(forma.metodologia, "- Metodología", 1);			
				validarLista(forma.grado, "- Grado", 1);			
				validarLista(forma.periodo, "- Periodo", 1);
				if(document.frm.tareas.checked==false && areasselect==""){
					appendErrorMessage("- Seleccione por lo menos un Area de detalle")
				}
			}

			function lanzar(tipo){
				document.frm.tipo.value=tipo;
				document.frm.action="<c:url value="/boletines/ControllerResumenFiltroEdit.do"/>";
				document.frm.target="";
				document.frm.submit();
			}

			function guardar(tipo){
				var aredoc="";
				var aux;
				var metods=document.frm.metodologia.options[document.frm.metodologia.selectedIndex].value;
				if(validarForma(document.frm)){
					document.frm.sedenom.value=document.frm.sede.options[document.frm.sede.selectedIndex].text;
					document.frm.jornadanom.value=document.frm.jornada.options[document.frm.jornada.selectedIndex].text;
					document.frm.metodologianom.value=document.frm.metodologia.options[document.frm.metodologia.selectedIndex].text;
					document.frm.gradonom.value=document.frm.grado.options[document.frm.grado.selectedIndex].text;
					document.frm.gruponom.value=document.frm.grupo.options[document.frm.grupo.selectedIndex].text;
					document.frm.periodonom.value=document.frm.periodo.options[document.frm.periodo.selectedIndex].text;
					document.frm.tipo.value=tipo;
					document.frm.cmd.value="Buscar";
					if(document.frm.tareas.checked==true){
						document.frm.areassel.value="0";
						<c:if test="${requestScope.roldocasig==1}">
								<c:forEach begin="0" items="${requestScope.FiltroAreas}" var="per" varStatus="st">
								aux='<c:out value="${per[2]}"/>';
				    				if (metods==aux){
										if (aredoc=="")
											aredoc=aredoc+<c:out value="${per[0]}"/>;
											else
												aredoc=aredoc+","+<c:out value="${per[0]}"/>;

				    				}
				    			</c:forEach>
						</c:if>
						if (aredoc!="")
							document.frm.areassel.value=aredoc;
					}else
						document.frm.areassel.value=areasselect;
					document.frm.target="ModuloReportes";
					document.frm.action='<c:url value="/boletines/FiltroGuardarAreas.jsp"/>';
					window.open("","ModuloReportes","width=800,height=500,menubar=yes,scrollbars=1").focus();
					document.frm.submit();
				}	
			}
			
			function cancelar(){
				document.frm.sede.selectedIndex=0;
				document.frm.jornada.selectedIndex=0;
				document.frm.metodologia.selectedIndex=0;
				document.frm.grado.selectedIndex=0;
				document.frm.grupo.selectedIndex=0;
				document.frm.periodo.selectedIndex=0;
				document.frm.tareas.checked="checked";
				document.frm.sede.onChange;
				areasselect="";
				asigselect="";
				/*if (confirm('¿No desea generar Resumen de Áreas?')){
 					document.frm.cmd.value="Cancelar";
					parent.location.href='<c:url value="/bienvenida.jsp"/>';
				}*/
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
									Sel_Hijos[k] = '<c:if test="${sessionScope.login.perfil==410 || sessionScope.login.perfil==421}"><c:if test="${sessionScope.filtroResumenAreas.jornada== fila2[0]}">SELECTED</c:if></c:if><c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}"><c:if test="${sessionScope.login.jornadaId== fila2[0]}">SELECTED</c:if></c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
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
					borrar_combo1(combo_hijo); borrar_combo(combo_hijo2);
					var id=0;
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st"><c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3"><c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroResumenAreas.grado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
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
				document.frm.grado.onchange();
				}
					
					
					
							//sede. jornada, metod,grado, grupo
			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_padre4,combo_hijo){
				if(combo_padre.selectedIndex==0 || combo_padre2.selectedIndex==0 || combo_padre3.selectedIndex==0)
					borrar_combo(combo_hijo); else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3">
								<c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] && fila2[5]==fila3[5]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtroResumenAreas.grupo== fila3[0]}">SELECTED</c:if>';
									id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; 
									Hijos[k] = '<c:out value="${fila3[1]}"/>'; 
									id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[5]}"/>|<c:out value="${fila3[4]}"/>'; k++;
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
		</script>
<%@include file="../mensaje.jsp"%>
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/boletines/FiltroGuardarAreas.jsp"/>' >
	<table border="0" align="center" width="100%">
	<caption>Generar Resumen de &Aacute;reas</caption>
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
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="areassel" value="0">
	<input type="hidden" name="asigsel" value="0">
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<input type="hidden" name="sedenom" value='<c:out value="${sessionScope.filtroResumenAreas.sedenom}"/>'>
	<input type="hidden" name="jornadanom" value='<c:out value="${sessionScope.filtroResumenAreas.jornadanom}"/>'>
	<input type="hidden" name="metodologianom" value='<c:out value="${sessionScope.filtroResumenAreas.metodologianom}"/>'>
	<input type="hidden" name="gradonom" value='<c:out value="${sessionScope.filtroResumenAreas.gradonom}"/>'>
	<input type="hidden" name="gruponom" value='<c:out value="${sessionScope.filtroResumenAreas.gruponom}"/>'>
	<input type="hidden" name="periodonom" value='<c:out value="${sessionScope.filtroResumenAreas.periodonom}"/>'>

	<table border="0" align="center" width="100%">
		<tr height="1">
			<td rowspan="2" width="420">
			  <img src="../etc/img/tabs/resumen_eval_areas_1.gif" alt="Resumen de Evaluación de Áreas" width="84"  height="26" border="0">
			  <a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/resumen_eval_asig_0.gif" alt="Resumen de Evaluación de Asignaturas" width="84" height="26" border="0"></a>
			  <a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/resumen_eval_consulta_asi_0.png" alt="Consultar asignaturas perdidas" width="84" height="26" border="0"></a>
			  </td>
		</tr>
		<tr>
		</tr>
  </table>
	<TABLE width="100%" cellpadding="3" cellSpacing="0">
		<tr>
			<td style='<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'><span class="style2" >*</span>Sede:</td>
			<td style='<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'>
				<select name="sede" onChange='filtro(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo,document.frm.metodologia)' style='width:300px;<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'>
					<option value='-9'>-- Seleccione una --</option>
					<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
						<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.login.perfil==410 || sessionScope.login.perfil==421}"><c:if test="${sessionScope.filtroResumenAreas.sede== fila[0]}">SELECTED</c:if></c:if> <c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}"><c:if test="${sessionScope.login.sedeId== fila[0]}">SELECTED</c:if></c:if> ><c:out value="${fila[1]}"/></option>
					</c:forEach>
				</select>											
		  </td>
			<td style='<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'><span class="style2" >*</span>Jornada:</td>
			<td style='<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'>
				<select name="jornada" style='width:120px;<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'>
					<option value='-9'>-- seleccione uno --</option>
				</select>							
			</td>
		</tr>
		<tr>
			<td><span class="style2" >*</span>Metodolog&iacute;a:</td>
			<td>
	      <select name="metodologia" style='width:120px;' onChange='filtro2(document.frm.sede,document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo)'>
	      <option value='-9'>-- Seleccione una --</option>
	      <c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
				<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroResumenAreas.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
	      </c:forEach>
	      </select>
      </td>
			<td><span class="style2" >*</span>Grado:</td>
			<td>
      	<select name="grado" onChange='filtro3(document.frm.sede, document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo)' style='width:120px;'>
        <option value='-9'>-- seleccione uno --</option>
        </select>
      </td>
		</tr>
		<tr>
			<td><span class="style2" ></span>Grupo:</td>
			<td>
      	<select name="grupo" style='width:120px;'>
        <option value='-9'>-- seleccione uno --</option>
        </select>
      </td>
			<td><span class="style2" >*</span>Periodo:</td>
			<td>
      	<select name="periodo" style='width:120px;'>
        <option value='-9'>-- seleccione uno --</option>
        <c:forEach begin="0" items="${requestScope.filtroPeriodoF}" var="per">
	        <option value="<c:out value="${per.codigo}"/>" <c:if test="${sessionScope.filtrob.periodo== per.codigo}">SELECTED</c:if>> 
	        <c:out value="${per.nombre}"/> </option>
        </c:forEach>
        </select>
      </td>
		</tr>
		
		<tr>
			<td><span class="style2" >*</span>Áreas:</td>
			<td>
			<input class='boton' name="areas" type="button" value="Detalle" onClick="MostrarDialog()" style="width:50px"  disabled="disabled" ><input type="checkbox" id="tareas" name="tareas" value="0" onClick="desarea()" checked="checked"/>Todas las Áreas     
			<div id="dialog"></div>
      </td>
		</tr>
      </tr>
		
		<tr style='display:none;'>
			<td>Reporte en formato EXCEL:</td>
			<td><input type="radio" name="formato" value="1"  <c:if test="${sessionScope.filtroResumenAreas.formato== '1'}"></c:if>></td>
			<td>Reporte en formato PDF:</td>
			<td><input type="radio" name="formato" value="2" <c:if test="${sessionScope.filtroResumenAreas.formato== '2'}"></c:if>></td>
		</tr>
	</TABLE>
<!--//////////////////////////////-->
</form>
<script>
document.frm.sede.onchange();
document.frm.metodologia.onchange();
document.frm.grado.onchange();
</script>