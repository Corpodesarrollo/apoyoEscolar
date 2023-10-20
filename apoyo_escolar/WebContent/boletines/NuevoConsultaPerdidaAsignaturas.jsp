<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="filtroConsultaAsignaturasPerdidas" class="siges.boletines.beans.FiltroConsultaAsignaturasPerdidas" scope="session"/><jsp:setProperty name="filtroConsultaAsignaturasPerdidas" property="*"/>
<%@include file="../parametros.jsp"%>
 <link type="text/css" href="http://jquery-ui.googlecode.com/svn/tags/1.7/themes/redmond/jquery-ui.css" rel="stylesheet" />
 
  <script type="text/javascript" src="jquery-1.3.2.min.js"></script>
 
  <script type="text/javascript" src="jquery-ui.min.js"></script>
		<script languaje='JavaScript'>

		var areasselect="";
		var asigselect="";
		$(function() { 
			$('html').css('font-size', 10);
			$("#dialogasi").dialog({
	    		autoOpen: false,
	            modal: true,
	            title: "Asignaturas",
	            resizable: false,
	            width: 400,
	            minWidth: 400,
	            maxWidth: 650,
	            show: "fold",
	            hide: "scale"
			});
	    });
		
		function desasi(){
			if(document.frm.tasig.checked==true)
				document.frm.asig.disabled=true;
			else
				document.frm.asig.disabled=false;
		}
		
		function asigsel(check){
			var areassp;
			if(check.checked==true){
				if(asigselect!="")
					asigselect=asigselect+","+check.value;
				else
					asigselect=asigselect+check.value;
			}
			else{
				areassp=asigselect.split(",");
				if(areassp.length!=1){
					for(var i=0;i<areassp.length;i++){
						if(areassp[i]==check.value)
							areassp[i]="";
					}
					asigselect="";
					for(var j=0;j<areassp.length;j++){
						if(areassp[j]!=""){
							if (j==0)
								asigselect=asigselect+areassp[j];
							else
								asigselect=asigselect+","+areassp[j];
						}
					}
				}
				else
					asigselect="";
				
			}
		}
	    
	    function MostrarDialogasi() {
	    	var metods=document.frm.metodologia.options[document.frm.metodologia.selectedIndex].value;
	    	var pes;
	    	var areasel;
	    	var pescheck;
	    	//var txt = document.createElement('<br/>');
	    	var erTd = document.getElementById("dialogasi");
	    	var areassp=areasselect.split(",");
	    	var asigsp=asigselect.split(",");
	    	while (erTd.hasChildNodes())
	    		erTd.removeChild(erTd.firstChild);
	    	<c:forEach begin="0" items="${requestScope.FiltroAsignaturas}" var="per" varStatus="st">
	    			pes='<c:out value="${per[3]}"/>';
	    			aresel='<c:out value="${per[0]}"/>';
	    			pescheck='<c:out value="${per[1]}"/>';
	    			if (metods==pes && asigselect==""){
	    				var erInput = document.createElement('INPUT');
	    				erInput.setAttribute("type","checkbox");
	    				erInput.setAttribute("value","<c:out value="${per[1]}"/>");
	    				erInput.setAttribute("id","areas");
	    				erInput.setAttribute("title","titulo_checkbox");
	    				erInput.onclick = function() { asigsel(this); };
	    				erTd.appendChild(erInput);
	    				erTd.appendChild(document.createTextNode("<c:out value="${per[2]}"/>"));
	    				erTd.appendChild(document.createElement("br"));
	    			}
	    			if (metods==pes && asigselect!=""){
	    				var erInput = document.createElement('INPUT');
	    				erInput.setAttribute("type","checkbox");
	    				erInput.setAttribute("value","<c:out value="${per[1]}"/>");
	    				erInput.setAttribute("id","areas");
	    				erInput.setAttribute("title","titulo_checkbox");
	    				erInput.onclick = function() { asigsel(this); };
	    				erTd.appendChild(erInput);
	    				for(var u=0;u<asigsp.length;u++){
    						if(pescheck==asigsp[u])
    							erInput.checked = "checked";
    					}
	    				erTd.appendChild(document.createTextNode("<c:out value="${per[2]}"/>"));
	    				erTd.appendChild(document.createElement("br"));
	    			}
	    			if (metods==pes && asigselect==""){
	    				for (var l=0;l<areassp.length;l++){
	    					if(areassp[l]==aresel){
	    						var erInput = document.createElement('INPUT');
	    	    				erInput.setAttribute("type","checkbox");
	    	    				erInput.setAttribute("value","<c:out value="${per[1]}"/>");
	    	    				erInput.setAttribute("id","areas");
	    	    				erInput.setAttribute("title","titulo_checkbox");
	    	    				erInput.onclick = function() { asigsel(this); };
	    	    				erTd.appendChild(erInput);
	    	    				erTd.appendChild(document.createTextNode("<c:out value="${per[2]}"/>"));
	    	    				erTd.appendChild(document.createElement("br"));
	    					}
	    				}
	    			}
	    			if (metods==pes && asigselect!=""){
	    				for (var l=0;l<areassp.length;l++){
	    					if(areassp[l]==aresel){
	    						var erInput = document.createElement('INPUT');
	    	    				erInput.setAttribute("type","checkbox");
	    	    				erInput.setAttribute("value","<c:out value="${per[1]}"/>");
	    	    				erInput.setAttribute("id","areas");
	    	    				erInput.setAttribute("title","titulo_checkbox");
	    	    				erInput.onclick = function() { asigsel(this); };
	    	    				erTd.appendChild(erInput);
	    	    				if(asigselect!=""){
	    	    					for(var u=0;u<asigsp.length;u++){
	    	    						if(pescheck==asigsp[u])
	    	    							erInput.checked = "checked";
	    	    					}
	    	    				}
	    	    				erTd.appendChild(document.createTextNode("<c:out value="${per[2]}"/>"));
	    	    				erTd.appendChild(document.createElement("br"));
	    					}
	    				}
	    			}
			    	//<input type="checkbox" name="areas" value="<c:out value="${per[0]}"/>" /> <c:out value="${per[1]}"/><br>
	    	</c:forEach>
	    	$("#dialogasi").dialog('open');
	      //$('#dialog').dialog('option', 'modal', true).dialog('open');
	      return true;
	    }
			function hacerValidaciones_frm(forma){
				var grupotipoconsulta = document.getElementsByName("tipoconsulta");
				var estado=false;
				
				for(var i=0;i<grupotipoconsulta.length;i++)
				  {
					if(grupotipoconsulta[i].checked){
					 estado=true;	
					}
				}
				
				
				if(estado){
				validarLista(forma.sede, "- Sede", 1);
				validarLista(forma.jornada, "- Jornada", 1);			
				validarLista(forma.metodologia, "- Metodología", 1);			
				validarLista(forma.grado, "- Grado", 1);
				//validarLista(forma.grupo, "- Grupo", 1);
				
				if(grupotipoconsulta[1].checked ||grupotipoconsulta[2].checked){
				    validarLista(forma.periodo, "- Periodo", 1);
				}
				
				if(document.frm.tasig.checked==false && asigselect==""){
					appendErrorMessage("- Seleccione por lo menos una Asignatura de detalle")
				}
				}else{
					appendErrorMessage("- Seleccione por una opción de consulta");
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
					//document.frm.gruponom.value=document.frm.grupo.options[document.frm.grupo.selectedIndex].text;
					
					var grupotipoconsulta = document.getElementsByName("tipoconsulta");
					if(grupotipoconsulta[1].checked){
					document.frm.periodonom.value=document.frm.periodo.options[document.frm.periodo.selectedIndex].text;
					}else{
					document.frm.periodonom.value=-1;	
					}
					
					document.frm.tipo.value=tipo;
					document.frm.cmd.value="Buscar";
					if(document.frm.tasig.checked==true){
						document.frm.asigsel.value="0";
						<c:if test="${requestScope.roldocasig==1}">
						<c:forEach begin="0" items="${requestScope.FiltroAsignaturas}" var="per" varStatus="st">
		    				aux='<c:out value="${per[3]}"/>';
						if (metods==aux){
							if (aredoc=="")
								aredoc=aredoc+<c:out value="${per[1]}"/>;
								else
									aredoc=aredoc+","+<c:out value="${per[1]}"/>;
						}
		    			</c:forEach>
						</c:if>
						if (aredoc!=""){
							document.frm.asigsel.value=aredoc;
						}
					}else{
						document.frm.asigsel.value=asigselect;
					}
					document.frm.submit();
				}
			}
			
function guardar2(tipo){
					document.frm.tipo.value=tipo;
					document.getElementById('opcionalarma').checked = true;
					
				    document.frm.cmd.value="Buscar";
					document.frm.submit();
				
			}
			
			function cancelar(){
				document.frm.sede.selectedIndex=0;
				document.frm.jornada.selectedIndex=0;
				document.frm.metodologia.selectedIndex=0;
				document.frm.grado.selectedIndex=0;
				///document.frm.grupo.selectedIndex=0;
				document.frm.periodo.selectedIndex=0;
				document.frm.tasig.checked="checked";
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
				combo.options[0] = new Option("--Seleccione una--","-9");
			}
			function borrar_combo1(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione una--","-9");
			}
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
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
									Sel_Hijos[k] = '<c:if test="${requestScope.jornadaMantenerFil== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
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
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3"><c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${requestScope.gradoMantenerFil== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
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
									Sel_Hijos[k] = '<c:if test="${requestScope.grupoMantenerFil== fila3[0]}">SELECTED</c:if>';
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
							
							

			function validarValorClic(){
				//evalasignatura
				var grupotipoconsulta = document.getElementsByName("tipoconsulta");
				var secperiodo1=document.getElementById("seccPeriodo1");
				var secperiodo2=document.getElementById("seccPeriodo2");
				var idtabla=document.getElementById("parametrosconsulta");
				var resdatos=document.getElementById("resultadosdatos");
				
				//resdatos.style.display='none';
				/*resdatos.style.display='none';
				resdatos.style.visibility='hidden';*/
			//valor 1 es:est pierden la misma asignatura
			//valor 2 es:est pierden asig por periodo
			//valor 3 es:est pierden asig por inasistencia
				for(var i=0;i<grupotipoconsulta.length;i++)
				  {
					if(grupotipoconsulta[i].checked){
				       idtabla.style.display='block';
				     if(grupotipoconsulta[i].value=="1")
				     {
				    	 
				    	    secperiodo1.style.display='none';
				    	    secperiodo2.style.display='none';
				    	    break;
				       }
				     
				     else if(grupotipoconsulta[i].value=="2")
				      {
				    	 
				    	 secperiodo1.style.display='block';
				    	 secperiodo2.style.display='block';
				    	          break;
				       } else  if(grupotipoconsulta[i].value=="3")
					      {
				    	   secperiodo1.style.display='block';
				    	   secperiodo2.style.display='block';
				    	   break;
					       }          
					}
				     
				    }
				  }

							
		</script>
<%@include file="../mensaje.jsp"%>
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="FiltroGuardarConsultaPerdidaAsignaturas.jsp"/>' >
	<table border="0" align="center" width="100%">
	<caption>Consulta Estudiantes pierden Asignatura</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
      	<input class='boton' name="cmd1" type="button" value="Generar" onClick="guardar(document.frm.tipo.value)">
        <input class='boton' name="cmd12" type="reset" value="Cancelar"  onClick="cancelar()" >
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
	<input type="hidden" name="sedenom" value='<c:out value="${sessionScope.filtroConsultaAsignaturasPerdidas.sedenom}"/>'>
	<input type="hidden" name="jornadanom" value='<c:out value="${sessionScope.filtroConsultaAsignaturasPerdidas.jornadanom}"/>'>
	<input type="hidden" name="metodologianom" value='<c:out value="${sessionScope.filtroConsultaAsignaturasPerdidas.metodologianom}"/>'>
	<input type="hidden" name="gradonom" value='<c:out value="${sessionScope.filtroConsultaAsignaturasPerdidas.gradonom}"/>'>
	<input type="hidden" name="periodonom" value='<c:out value="${sessionScope.filtroConsultaAsignaturasPerdidas.periodonom}"/>'>

	<table border="0" align="center" width="100%">
		<tr height="1">
			<td rowspan="2" width="420">
			  <a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/resumen_eval_areas_0.gif" alt="Resumen de Evaluación de Áreas" width="84"  height="26" border="0"></a>
			  <a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/resumen_eval_asig_0.gif" alt="Resumen de Evaluación de Asignaturas" width="84" height="26" border="0"></a>
			  <img src="../etc/img/tabs/resumen_eval_consulta_asi_1.png" alt="Consultar asignaturas perdidas" width="84" height="26" border="0">
			  </td>
		</tr>
		<tr>
		</tr>
  </table>
  <table width="100%" cellpadding="2" cellSpacing="0">
    <tr>
       <td>Generar estadístico estudiantes pierden la misma asignatura
       </td>
       <td>
        <input type="radio" name="tipoconsulta" value="1" onClick="validarValorClic();document.getElementById('resultadosdatos').style.display='none';"  <c:if test="${sessionScope.filtroConsultaAsignaturasPerdidas.tipoconsulta == '1'}">CHECKED</c:if> />
       </td>
    </tr>
    <tr>
       <td>Generar estadístico estudiantes pierden asignatura x periodo
       </td>
       <td>
        <input type="radio" name="tipoconsulta" value="2" onClick="validarValorClic();document.getElementById('resultadosdatos').style.display='none';"  <c:if test="${sessionScope.filtroConsultaAsignaturasPerdidas.tipoconsulta == '2'}">CHECKED</c:if> />
       </td>
    </tr>
    <tr>
       <td>Generar estadístico estudiantes pierden x insasistencia
       </td>
       <td>
        <input type="radio" name="tipoconsulta" value="3" onClick="validarValorClic();document.getElementById('resultadosdatos').style.display='none';"  <c:if test="${sessionScope.filtroConsultaAsignaturasPerdidas.tipoconsulta == '3'}">CHECKED</c:if> />
       </td>
    </tr>
    <tr style="display:none;">
       <td>activar alarma
       </td>
       <td>
        <input type="radio" name="tipoconsulta" value="6" id="opcionalarma" />
       </td>
    </tr>
  </table>
	<TABLE width="100%" cellpadding="3" cellSpacing="0" style="display:none;" id="parametrosconsulta">
		<tr>
			<td style='<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'><span class="style2" >*</span>Sede:</td>
			<td style='<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'>
				<select name="sede" onChange='filtro(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo,document.frm.metodologia)' style='width:300px;<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'>
					<option value='-9'>-- Seleccione una --</option>
					<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
						<option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope.sedeMantenerFil== fila[0]}">SELECTED</c:if> ><c:out value="${fila[1]}"/></option>
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
				<option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope.metodologiaMantenerFil==fila[0]}">SELECTED</c:if> ><c:out value="${fila[1]}"/></option>
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
		<tr >
			<td style="display:none;visibility: hidden;"><span class="style2" ></span>Grupo:</td>
			<td style="display:none;visibility: hidden;">
      	<select name="grupo" style='width:120px;'>
        <option value='-9'>-- seleccione uno --</option>
        </select>
      </td>
			<td id="seccPeriodo1" ><span class="style2" >*</span>Periodo:</td>
			<td id="seccPeriodo2" >
      	<select name="periodo" style='width:120px;'>
        <option value='-9'>-- seleccione uno --</option>
        <c:forEach begin="0" items="${requestScope.filtroPeriodoF}" var="per">
	        <option value="<c:out value="${per.codigo}"/>" <c:if test="${requestScope.periodoMantenerFil== per.codigo}">SELECTED</c:if>> 
	        <c:out value="${per.nombre}"/> </option>
        </c:forEach>
        </select>
      </td>
		</tr>
		
		<tr style="display: none;">
		<td><span class="style2" >*</span>Asignaturas:</td>
			<td>
			<input class='boton' name="asig" type="button" value="Detalle" onClick="MostrarDialogasi()" style="width:50px" disabled="disabled" ><input type="checkbox" id="tasig" name="tasig" value="0" onClick="desasi()" checked="checked"/>Todas las Asignaturas    
      		<div id="dialogasi"></div>
      </td>
      </tr>
      
      </TABLE>
	
	<div id="resultadosdatos">
	
	<c:if test='${!empty sessionScope.filtroResultado && sessionScope.opcionseleccionada=="1"}'>
	 <c:set var="nulopc1" value="true" scope="page" />
	
	<c:if test="${sessionScope.rolususario=='410'}">              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				      <input  class='boton' name="impr1" type="button" value="Generar Alarma" style="margin-top:2px;margin-bottom:2px;" onClick="guardar2(4); return false;" />
				       </td>
				       </tr>
				       </table>
     </c:if>
	
	
	<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
	        <tr>
	        <td>
	        <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
	        </td>
	        </tr>
	        </table>
	<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="grupofilas" varStatus ="statusgrupo">
     <c:if test="${!empty grupofilas}" >
     <c:set var="nulopc1" value="false" scope="page" />
	
	          <c:forEach begin="0" items="${grupofilas}" var="grupoasignaturas" varStatus ="statusasig" >
	          

	          <c:forEach begin="0" items="${grupoasignaturas}" var="grupoestuvalores" varStatus ="statusestu" >
	          
	          
	          
	            <c:if test="${!empty grupoestuvalores}">
	          
	            <c:set var="colperiodos" value="5" scope="page" />
	          
	            <c:forEach begin="5" end="5" items="${grupoestuvalores}" var="filaperiodos" >
	                          
				               <c:set var="colperiodos" value="${filaperiodos[0]}" scope="page" />	
				</c:forEach>
	    
	    <table style="margin-bottom:10px;" id='t2' name='t2'  border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>					
					<th class="EncabezadoColumna" colspan='4'>Datos de estudiante</th>
					<th class="EncabezadoColumna" colspan='<c:out value="${pageScope.colperiodos}"/>'>
					   <c:forEach begin="3" end="3" items="${grupoestuvalores}" var="fila1" >
				          Area:<c:out value="${fila1[0]}"/>
				       </c:forEach>
				    </th>
    			</tr>
    			 <tr>
    			 <th class="EncabezadoColumna" colspan="4"></th>
    			 <th class="EncabezadoColumna" colspan='<c:out value="${pageScope.colperiodos}"/>'>
    			   <c:forEach begin="2" end="2" items="${grupoestuvalores}" var="fila2" >
				          Asignatura:<c:out value="${fila2[0]}"/>
				       </c:forEach>
					</th>
    			 </tr>
				<tr>
					<th class="EncabezadoColumna" width='5%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='32%'>Grupo</th>
					<th class="EncabezadoColumna" width='32%'>Apellidos</th>
					<th class="EncabezadoColumna" width='32%'>Nombres</th>
					
					 
					
					<c:forEach var="i" begin="1" end="${pageScope.colperiodos}" step="1" varStatus ="status">
					    <th class="EncabezadoColumna">
                      P<c:out value="${i}" /> 
                    </th>
	                </c:forEach>
				</tr>
				
				<c:forEach begin="4" end="4" items="${grupoestuvalores}" var="fila4" varStatus="st2"><c:set var="gruponom1" value="${fila4[0]}" scope="page" /></c:forEach>
				 <c:set var="valorminimoaprob" value="${sessionScope.tipoEvalAprobMin}" scope="page" />	
				   
				<c:forEach begin="0" end="1" items="${grupoestuvalores}" var="grupoestu" >		
				
				<c:forEach begin="0" items="${grupoestu}" var="fila" varStatus="st2">		
				
						
				<c:set var="totalest" value='${st2.count}' scope="page" />
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
					<td class='Fila1' align='center'><b><c:out value="${st2.count}"/></b></td>
					<th class='Fila1' align='center'><c:out value="${pageScope.gruponom1}" /></th>
					<td class='Fila1' ><c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/></td>
					<td class='Fila1' ><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td>
					
									
					<c:forEach var="i" begin="6" end="${pageScope.colperiodos*3}" step="2" varStatus ="status">             
					
					<th class='Fila1' >
					<c:if test="${sessionScope.tipoEval==2}">
						<SELECT  NAME='nota_' class='Fila1' onKeyPress='acepteNumeros(event,document.frmNuevo.nota_,<c:out value="${st2.count}"/>,1)' onChange='next(document.frmNuevo.nota_,<c:out value="${st2.count}"/>,1)'>
							<option value='-1'>&nbsp;</option>
							<c:forEach begin="0" items="${sessionScope.filtroNota2}" var="fila3"><option value='<c:out value="${fila[0]}"/>|<c:out value="${fila3[0]}"/>' <c:if test="${fila3[0]==fila[i]}">SELECTED</c:if>><c:out value="${fila3[1]}"/></option></c:forEach>
						</select>
						<input type='hidden' name='nota' value=''></input>
						</c:if>
						<c:if test="${sessionScope.tipoEval==1}">						
							<input type='text' name='notaEst' class='Fila1' onKeyPress='return acepteNumeros3(event,document.frmNuevo.notaEst,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)' size='4' maxlength='4' value='<c:if test="${(fila[i]!='NR') && (fila[i]<pageScope.valorminimoaprob) }"><c:out value="X"/></c:if>'>
							<input type='hidden' name='nota' value='<c:out value="${fila[0]}"/>'></input>
						</c:if>
						<c:if test="${sessionScope.tipoEval==3}">
							<input type='text' name='notaEst' class='Fila1' onKeyPress='acepteNumeros3(event,document.frmNuevo.notaEst,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)'  size='4' maxlength='4' value='<c:if test="${(fila[i]!='NR') && (fila[i]<pageScope.valorminimoaprob)  }"><c:out value="X"/></c:if>'>
							<input type='hidden' name='nota' value='<c:out value="${fila[0]}"/>'></input>
						</c:if>
					
					</th>	
					
					</c:forEach>
									
				</tr>
	            </c:forEach>
				</c:forEach>
				<tr>
				  <th colspan='2'>Total:
				  </th>
				  <th colspan='1'>
				    <c:out value="${pageScope.totalest}"/>
				  </th>
				</tr>
			</table>
			
			       </c:if>
	               </c:forEach>  
	                 
	               </c:forEach>
                     </c:if>
	               </c:forEach>
	               
	            <c:if test="${pageScope.nulopc1=='true'}">
	                <p>- No se encuentran registros bajo el criterio de busqueda.</p>
	            </c:if>  
	        
	        <c:if test="${sessionScope.rolususario=='410'}">              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				      <input  class='boton' name="impr1" type="button" value="Generar Alarma" style="margin-top:2px;margin-bottom:2px;" onClick="guardar2(4); return false;" />
				       </td>
				       </tr>
				       </table>
                </c:if>
	        
	        
			<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
			<tr>
	        <td>
	        <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
	        </td>
	        </tr>
	        </table>
		</c:if> 
		 <c:if test='${!empty sessionScope.filtroResultado && sessionScope.opcionseleccionada=="2"}'>
	
	<c:if test="${sessionScope.rolususario=='410'}">              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				      <input  class='boton' name="impr1" type="button" value="Generar Alarma" style="margin-top:2px;margin-bottom:2px;" onClick="guardar2(4); return false;" />
				       </td>
				       </tr>
				       </table>
     </c:if>
	
	
		    <c:set var="nulopc2" value="true" scope="page" />
		 	<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
		 	<tr>
	        <td>
	        <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
	        </td>
	        </tr>
	        </table>
	<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="grupofilas" varStatus ="statusgrupo">
     <c:if test="${!empty grupofilas}" >
     <c:set var="nulopc2" value="false" scope="page" />
	
	          <c:forEach begin="0" items="${grupofilas}" var="grupoasignaturas" varStatus ="statusasig" >
	          

	          <c:forEach begin="0" items="${grupoasignaturas}" var="grupoestuvalores" varStatus ="statusestu" >
	          
	          
	          
	            <c:if test="${!empty grupoestuvalores}">
	          
	    
	    <table style="margin-bottom:10px;" id='t2' name='t2' width="70%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>					
					<th class="EncabezadoColumna" colspan='4'>Estudiantes pierden asignatura x periodo</th>
				</tr>
				<tr>
					<th class="EncabezadoColumna" colspan='4'>
					   <c:forEach begin="3" end="3" items="${grupoestuvalores}" var="fila1" >
				          Area:<c:out value="${fila1[0]}"/>
				       </c:forEach>
				    </th>
    			</tr>
    			 <tr>
    			 <th class="EncabezadoColumna" colspan='4'>
    			   <c:forEach begin="2" end="2" items="${grupoestuvalores}" var="fila2" >
				          Asignatura:<c:out value="${fila2[0]}"/>
				       </c:forEach>
					</th>
    			 </tr>
				<tr>
					<th class="EncabezadoColumna" width='5%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='12%'>Grado</th>
					<th class="EncabezadoColumna" width='12%'>Grupo</th>
					<th class="EncabezadoColumna" width='72%'>Estudiante</th>
				</tr>
				<c:forEach begin="4" end="4" items="${grupoestuvalores}" var="fila4" varStatus="st2"><c:set var="gruponom1" value="${fila4[0]}" scope="page" /></c:forEach>
				 <c:set var="valorminimoaprob" value="${sessionScope.tipoEvalAprobMin}" scope="page" />	
				   
				<c:forEach begin="0" end="1" items="${grupoestuvalores}" var="grupoestu" >		
				
				<c:forEach begin="0" items="${grupoestu}" var="fila" varStatus="st2">		
				
						
				<c:set var="totalest" value='${st2.count}' scope="page" />
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
					<td class='Fila1' align='center'><b><c:out value="${st2.count}"/></b></td>
					<th class='Fila1' align='center'><c:out value="${sessionScope.gradoconsulta}"/></th>
					<th class='Fila1' align='center'><c:out value="${pageScope.gruponom1}" /></th>
					<td class='Fila1' ><c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td>									
				</tr>
	            </c:forEach>
				</c:forEach>
				<tr>
				  <th colspan='3'>Total:
				  </th>
				  <th colspan='1'>
				    <c:out value="${pageScope.totalest}"/>
				  </th>
				</tr>
			</table>
			
			       </c:if>
	               </c:forEach>  
	                 
	               </c:forEach>
                     </c:if>
	               </c:forEach>
	               
	         <c:if test="${pageScope.nulopc2=='true'}">
	                <p>- No se encuentran registros bajo el criterio de busqueda.</p>
	            </c:if> 
	        
	        <c:if test="${sessionScope.rolususario=='410'}">              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				      <input  class='boton' name="impr1" type="button" value="Generar Alarma" style="margin-top:2px;margin-bottom:2px;" onClick="guardar2(4); return false;" />
				       </td>
				       </tr>
				       </table>
                </c:if>
	         
			<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
	        <tr>
	        <td>
	        <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
	        </td>
	        </tr>
	        </table>
		 
		 </c:if>
		 
		 		 <c:if test='${!empty sessionScope.filtroResultado && sessionScope.opcionseleccionada=="3"}'>
	
	<c:if test="${sessionScope.rolususario=='410'}">              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				      <input  class='boton' name="impr1" type="button" value="Generar Alarma" style="margin-top:2px;margin-bottom:2px;" onClick="guardar2(4); return false;" />
				       </td>
				       </tr>
				       </table>
     </c:if>
	
	
		   <c:set var="nulopc3" value="true" scope="page" />
		 	<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
<tr>
       <td>
       <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
       </td>
       </tr>
       </table>
<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="grupototal" varStatus ="statustotal">
     <c:if test="${!empty grupototal}" >
     <c:set var="nulopc3" value="false" scope="page" />

        


<c:forEach begin="0" items="${grupototal}" var="grupoasignaturas" varStatus ="statusestu">
     <c:if test="${!empty grupoasignaturas}" >

         
         
         <c:forEach begin="2" end="2" items="${grupoasignaturas}" var="listaasignaturas" varStatus ="statusesasig" >
             <c:set var="listaasignaturasarray" value="${listaasignaturas[0]}" scope="page" /> 
            
         </c:forEach>
         
         <c:forEach begin="3" end="3" items="${grupoasignaturas}" var="listaih" varStatus ="statusih" >
             <c:set var="listaiharray" value="${listaih[0]}" scope="page" />
            
         </c:forEach>
         
         
         <c:forEach begin="0" end="0" items="${grupoasignaturas}" var="listaestudiantes2" varStatus ="statuseestu" >
             <c:set var="listaestudiantes2array" value="${listaestudiantes2}" scope="page" /> 
            
         </c:forEach>
         
         <c:forEach begin="4" end="4" items="${grupoasignaturas}" var="grupo" >
             <c:set var="grupovalue" value="${grupo}" scope="page" /> 
            
         </c:forEach>
         
         <c:forEach begin="0" items="${listaasignaturasarray}" var="asignatura" varStatus ="contasignatura" >
           
              
         <c:forEach begin="${contasignatura.count}" end="${contasignatura.count}" items="${listaiharray}" var="ih">
             <c:set var="ihtmp" value="${ih}" scope="page" /> 
             
         </c:forEach>
     			<c:set var="ahiestudiantes" value="no" scope="page" />             
           
             <table style="margin-bottom:10px;" id='t2' name='t2' width="70%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr> 
				<th class="EncabezadoColumna" colspan='4'>Estudiantes pierden asignatura x inasistencia</th>
				</tr>
				<tr>
				<th class="EncabezadoColumna" colspan='4'>
				  <c:forEach begin="2" end="2" items="${grupoasignaturas}" var="fila2a" >
				           Area:<c:out value="${asignatura[2]}"/>
				     </c:forEach>   
				   </th>
				    </tr>
				    <tr>
				    <th class="EncabezadoColumna" colspan='4'>
				      <c:forEach begin="2" end="2" items="${grupoasignaturas}" var="fila2b" >
				         Asignatura:<c:out value="${asignatura[0]}"/>
				      </c:forEach>
				</th>
				    </tr>
				<tr>
				<th class="EncabezadoColumna" width='5%'>&nbsp;</th>
				<th class="EncabezadoColumna" width='12%'>Grado</th>
				<th class="EncabezadoColumna" width='12%'>Grupo</th>
				<th class="EncabezadoColumna" width='72%'>Estudiante</th>
				</tr>
				<c:set var="totalest" value='0' scope="page" /> 
				
				<c:forEach begin="0" items="${listaestudiantes2array}" var="fila" varStatus="st2"> 
				<c:if test="${fila[6+contasignatura.count]>ihtmp && ihtmp!=0}">
				<c:set var="ahiestudiantes" value="si" scope="page" />             
				          
				<c:set var="totalest" value='${st2.count}' scope="page" />
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
				<td class='Fila1' align='center'><b><c:out value="${st2.count}"/></b></td>
				<th class='Fila1' align='center'><c:out value="${sessionScope.gradoconsulta}"/></th>
				<th class='Fila1' align='center'><c:out value="${pageScope.grupovalue}"/></th>
				<td class='Fila1' ><c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td> 
				</tr>
				</c:if>
				           </c:forEach>
				           
				             <c:if test="${ahiestudiantes=='no'}">
				             <tr>
				<td class='Fila1' align='center' colspan="4"><p>No posee estudiantes registrados.</p></td> 
				 </tr>             
				            </c:if>
				<tr>
				 <th colspan='3'>Total:
				 </th>
				 <th colspan='1'>
				   <c:out value="${pageScope.totalest}"/>
				 </th>
				</tr>
				</table>
			
				</c:forEach>
				       </c:if>
				              </c:forEach>  
				                </c:if>
				                </c:forEach>
				 <c:if test="${pageScope.nulopc3=='true'}">
	                <p>- No se encuentran registros bajo el criterio de busqueda.</p>
	            </c:if>              
				                     
				<c:if test="${sessionScope.rolususario=='410'}">              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				      <input  class='boton' name="impr1" type="button" value="Generar Alarma" style="margin-top:2px;margin-bottom:2px;" onClick="guardar2(4); return false;" />
				       </td>
				       </tr>
				       </table>
                </c:if> 
				 				              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				       <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
				       </td>
				       </tr>
				       </table>

		 
		 </c:if>
		 
		 
		 		 <c:if test='${!empty sessionScope.filtroResultado && sessionScope.opcionseleccionada=="4"}'>
	
	<c:if test="${sessionScope.rolususario=='410'}">              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				      <input  class='boton' name="impr1" type="button" value="Generar Alarma" style="margin-top:2px;margin-bottom:2px;" onClick="guardar2(4); return false;" />
				       </td>
				       </tr>
				       </table>
     </c:if>

		 		  <c:set var="nulopc4" value="false" scope="page" />
		 
		 	<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
		 	<tr>
	        <td>
	        <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
	        </td>
	        </tr>
	        </table>
	<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="grupofilas" varStatus ="statusgrupo">
     <c:if test="${!empty grupofilas}" >
      <c:set var="nulopc4" value="true" scope="page" />
    
     
     <c:forEach begin="0" items="${grupofilas}" var="grupofilas1" varStatus ="statusfila">
     <c:if test="${!empty grupofilas1}" >
     
     
     <c:forEach begin="0" items="${grupofilas1}" var="grupofilas2" varStatus ="statusfila1">
     <c:if test="${!empty grupofilas2}" > 
	
	
	          <c:forEach begin="0" items="${grupofilas2}" var="grupoasignaturas" varStatus ="statusasig" >
	          

	          <c:forEach begin="0" items="${grupoasignaturas}" var="grupoestuvalores" varStatus ="statusestu" >
	          
	          
	          
	            <c:if test="${!empty grupoestuvalores}">
	          
	    
	    <table style="margin-bottom:10px;" id='t2' name='t2' width="70%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>					
					<th class="EncabezadoColumna" colspan='4'>Estudiantes pierden asignatura x periodo</th>
				</tr>
				<tr>
					<th class="EncabezadoColumna" colspan='4'>
					   <c:forEach begin="3" end="3" items="${grupoestuvalores}" var="fila1" >
				          Area:<c:out value="${fila1[0]}"/>
				       </c:forEach>
				    </th>
    			</tr>
    			 <tr>
    			 <th class="EncabezadoColumna" colspan='4'>
    			   <c:forEach begin="2" end="2" items="${grupoestuvalores}" var="fila2" >
				          Asignatura:<c:out value="${fila2[0]}"/>
				       </c:forEach>
					</th>
    			 </tr>
				<tr>
					<th class="EncabezadoColumna" width='5%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='12%'>Grado</th>
					<th class="EncabezadoColumna" width='12%'>Grupo</th>
					<th class="EncabezadoColumna" width='72%'>Estudiante</th>
				</tr>

				<c:forEach begin="5" end="5" items="${grupoestuvalores}" var="fila5" varStatus="st3"><c:set var="gradonom1" value="${fila5[0]}" scope="page" /></c:forEach>
				<c:forEach begin="4" end="4" items="${grupoestuvalores}" var="fila4" varStatus="st2"><c:set var="gruponom1" value="${fila4[0]}" scope="page" /></c:forEach>
				 <c:set var="valorminimoaprob" value="${sessionScope.tipoEvalAprobMin}" scope="page" />	
				   
				<c:forEach begin="0" end="1" items="${grupoestuvalores}" var="grupoestu" >		
				
				<c:forEach begin="0" items="${grupoestu}" var="fila" varStatus="st2">		
				
						
				<c:set var="totalest" value='${st2.count}' scope="page" />
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
					<td class='Fila1' align='center'><b><c:out value="${st2.count}"/></b></td>
					<th class='Fila1' align='center'><c:out value="${pageScope.gradonom1}" /></th>
					<th class='Fila1' align='center'><c:out value="${pageScope.gruponom1}" /></th>
					<td class='Fila1' ><c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td>									
				</tr>
				 
	            </c:forEach>
				</c:forEach>
				<tr>
				  <th colspan='3'>Total:
				  </th>
				  <th colspan='1'>
				    <c:out value="${pageScope.totalest}"/>
				  </th>
				</tr>
			</table>
			
			       </c:if>
	               </c:forEach>  
	                 
	               </c:forEach>
                     </c:if>
	               </c:forEach>
	               </c:if>
	               </c:forEach>
	               </c:if>
	               <c:if test="${pageScope.nulopc3=='true'}">
	                    <p>- No se encuentran registros bajo el criterio de busqueda.</p>
	               </c:if> 
	               </c:forEach>
	               
	         <c:if test="${sessionScope.rolususario=='410'}">              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				      <input  class='boton' name="impr1" type="button" value="Generar Alarma" style="margin-top:2px;margin-bottom:2px;" onClick="guardar2(4); return false;" />
				       </td>
				       </tr>
				       </table>
                </c:if>
	               
			<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
	        <tr>
	        <td>
	        <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
	        </td>
	        </tr>
	        </table>
		 
		 </c:if>
		 
		 
		  <c:if test='${!empty sessionScope.filtroResultado && sessionScope.opcionseleccionada=="5"}'>
		   <c:set var="nulopc5" value="true" scope="page" />

<c:if test="${sessionScope.rolususario=='410'}">              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				      <input  class='boton' name="impr1" type="button" value="Generar Alarma" style="margin-top:2px;margin-bottom:2px;" onClick="guardar2(4); return false;" />
				       </td>
				       </tr>
				       </table>
     </c:if>
	

		 	<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
<tr>
       <td>
       <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
       </td>
       </tr>
       </table>
<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="grupototal" varStatus ="statustotal">
     <c:if test="${!empty grupototal}" >
     <c:set var="nulopc5" value="false" scope="page" />

  <c:forEach begin="0" items="${grupototal}" var="gradosarray" varStatus ="gradoscont">
     <c:if test="${!empty gradosarray}" >      


<c:forEach begin="0" items="${gradosarray}" var="grupoasignaturas" varStatus ="statusestu">
     <c:if test="${!empty grupoasignaturas}" >

         
         
         <c:forEach begin="2" end="2" items="${grupoasignaturas}" var="listaasignaturas" varStatus ="statusesasig" >
             <c:set var="listaasignaturasarray" value="${listaasignaturas[0]}" scope="page" /> 
            
         </c:forEach>
         
         <c:forEach begin="3" end="3" items="${grupoasignaturas}" var="listaih" varStatus ="statusih" >
             <c:set var="listaiharray" value="${listaih[0]}" scope="page" />
            
         </c:forEach>
         
         
         <c:forEach begin="0" end="0" items="${grupoasignaturas}" var="listaestudiantes2" varStatus ="statuseestu" >
             <c:set var="listaestudiantes2array" value="${listaestudiantes2}" scope="page" /> 
            
         </c:forEach>
         
         <c:forEach begin="4" end="4" items="${grupoasignaturas}" var="grupo" >
             <c:set var="grupovalue" value="${grupo}" scope="page" /> 
            
         </c:forEach>
         
         <c:forEach begin="0" items="${listaasignaturasarray}" var="asignatura" varStatus ="contasignatura" >
           
              
         <c:forEach begin="${contasignatura.count}" end="${contasignatura.count}" items="${listaiharray}" var="ih">
             <c:set var="ihtmp" value="${ih}" scope="page" /> 
             
         </c:forEach>
     			<c:set var="ahiestudiantes" value="no" scope="page" />             
            
             <table style="margin-bottom:10px;" id='t2' name='t2' width="70%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr> 
				<th class="EncabezadoColumna" colspan='4'>Estudiantes pierden asignatura x inasistencia</th>
				</tr>
				<tr>
				<th class="EncabezadoColumna" colspan='4'>
				  <c:forEach begin="2" end="2" items="${grupoasignaturas}" var="fila2a" >
				           Area:<c:out value="${asignatura[2]}"/>
				     </c:forEach>   
				   </th>
				    </tr>
				    <tr>
				    <th class="EncabezadoColumna" colspan='4'>
				      <c:forEach begin="2" end="2" items="${grupoasignaturas}" var="fila2b" >
				         Asignatura:<c:out value="${asignatura[0]}"/>
				      </c:forEach>
				</th>
				    </tr>
				<tr>
				<th class="EncabezadoColumna" width='5%'>&nbsp;</th>
				<th class="EncabezadoColumna" width='12%'>Grado</th>
				<th class="EncabezadoColumna" width='12%'>Grupo</th>
				<th class="EncabezadoColumna" width='72%'>Estudiante</th>
				</tr>
				
				<c:set var="totalest" value='0' scope="page" /> 
				
				<c:forEach begin="0" items="${listaestudiantes2array}" var="fila" varStatus="st2"> 
				<c:if test="${fila[8+contasignatura.count]>ihtmp && ihtmp!=0}">
				<c:set var="ahiestudiantes" value="si" scope="page" />             
				          
				<c:set var="totalest" value='${st2.count}' scope="page" />
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
				<td class='Fila1' align='center'><b><c:out value="${st2.count}"/></b></td>
				<th class='Fila1' align='center'><c:out value="${fila[6]}"/></th>
				<th class='Fila1' align='center'><c:out value="${fila[7]}"/></th>
				<td class='Fila1' ><c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td> 
				</tr>
				</c:if>
				           </c:forEach>
				           
				             <c:if test="${ahiestudiantes=='no'}">
				             <tr>
				<td class='Fila1' align='center' colspan="4"><p>No posee estudiantes registrados.</p></td> 
				 </tr>             
				            </c:if>
				<tr>
				 <th colspan='3'>Total:
				 </th>
				 <th colspan='1'>
				   <c:out value="${pageScope.totalest}"/>
				 </th>
				</tr>
				</table>
				
				
				
				</c:forEach>
				       </c:if>
				              </c:forEach>  
				                </c:if>
				                </c:forEach>
				                   </c:if>
				                   </c:forEach>
				 <c:if test="${pageScope.nulopc5=='true'}">
	                <p>- No se encuentran registros bajo el criterio de busqueda.</p>
	            </c:if>              
				  
				  
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				       <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
				       </td>
				       </tr>
				       </table>
				                   
				<c:if test="${sessionScope.rolususario=='410'}">              
				<table border="1" align="center" bordercolor="#FFFFFF" width="100%" style="margin-left:5px;">
				       <tr>
				       <td>
				      <input  class='boton' name="impr1" type="button" value="Generar Alarma" style="margin-top:2px;margin-bottom:2px;" onClick="guardar2(4); return false;" />
				       </td>
				       </tr>
				       </table>
                </c:if>
		 
		 </c:if>
		
	     </div>
	
<!--//////////////////////////////-->
</form>
<script>
document.frm.sede.onchange();
document.frm.metodologia.onchange();
document.frm.grado.onchange();
validarValorClic();

<c:if test='${!empty sessionScope.alarma}'>
      var aviso='Alarmas agregadas correctamente';
      alert(aviso);
</c:if>

</script>