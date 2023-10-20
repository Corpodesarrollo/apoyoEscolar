<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/>
<jsp:useBean id="nuevoFamiliar" class="siges.estudiante.beans.Familiar" scope="session"/><jsp:setProperty name="nuevoFamiliar" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="params2VO" class="siges.filtro.vo.FichaVO" scope="page"/>
<%
pageContext.setAttribute("filtroConvenio",Recursos.recurso[Recursos.CONVENIO]);
pageContext.setAttribute("filtroNivel",Recursos.recurso[Recursos.NIVEL]);
pageContext.setAttribute("filtroDepartamento",Recursos.recurso[Recursos.DEPARTAMENTO]);
pageContext.setAttribute("filtroMunicipio",Recursos.recurso[Recursos.MUNICIPIO]);
pageContext.setAttribute("filtroDocumento",Recursos.recurso[Recursos.TIPODOCUMENTO]);
pageContext.setAttribute("filtroEtnia",Recursos.recurso[Recursos.ETNIA]);
pageContext.setAttribute("filtroCondicion",Recursos.recurso[Recursos.CONDICION]);
pageContext.setAttribute("filtroFamilia",Recursos.recurso[Recursos.FAMILIA]);
%>
<%@include file="../parametros.jsp"%><c:set var="tip" value="1" scope="page"/>
		<script languaje='javaScript'>
		<!--
			//array de los componentes que no hay que deshabilitar para edicion
		    var nombre = new Array();
		    var i=0;
		    nombre[i++]="estsede";
		    nombre[i++]="estjor";
		    nombre[i++]="estmet";
		    nombre[i++]="estgra";
		    nombre[i++]="estgru";
		    nombre[i++]="estnombre1";
		    nombre[i++]="estnombre2";
		    nombre[i++]="estapellido1";
		    nombre[i++]="estapellido2";
		    nombre[i++]="estfechanac";
		    nombre[i++]="estgenero";
		    //nombre[i++]="";
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
				//validarLista(forma.estexpdoccoddep,"- Departamento de expedición del documento",1)
				//validarLista(forma.estexpdoccodmun,"- Municipio de expedición del documento",1)
				//validarLista(forma.esttipodoc,"- Tipo de documento",1)
			  	//validarCampo(forma.estnumdoc, "- Número de identificación", 1, 12)
				validarCampo(forma.estapellido1, "- Primer apellido", 1, 20)
				validarCampo(forma.estapellido2, "- Segundo apellido", 1, 20)
				validarCampo(forma.estnombre1, "- Primer nombre", 1,20)
				if (trim(forma.estnombre2.value).length > 0) 
			  	validarCampo(forma.estnombre2, "- Segundo nombre", 1,20)
 			  	validarCampo(forma.estfechanac, "- Fecha de nacimiento",6,10)
				valFecha(forma.estfechanac,"- Fecha de nacimiento (dd/mm/aaaa)")
				validarLista(forma.estgenero,"- Género",1)
				validarLista(forma.estsede,"- Sede",1)
				validarLista(forma.estjor,"- Jornada",1)
				validarLista(forma.estmet,"- Metodologìa",1)
				validarLista(forma.estgra,"- Grado",1)
				validarLista(forma.estgru,"- Grupo",1)
			}
			function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;					
				document.frmNuevo.action="<c:url value="/estudiante/ControllerNuevoEdit.do"/>";
				document.frmNuevo.submit();
			}
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}	
			}
			function cancelar(){
				if (confirm('¿Desea cancelar la edición de la información básica del estudiante?')){
 					document.frmNuevo.cmd.value="Cancelar";
          			document.frmNuevo.submit(); 
				}
			}
			
			function inhabilitarFormulario(){
				var existe=0;
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					existe=0;
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit"){
						for(var j=0;j<nombre.length;j++){
							if(document.frmNuevo.elements[i].name==nombre[j])
							existe=1;
						}
						if(existe==0)
							document.frmNuevo.elements[i].disabled=true;
					}
				}
			}
			
			function inhabilitarFormularioTodo(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
						document.frmNuevo.elements[i].disabled=true;
					if(document.frmNuevo.elements[i].name== "cmd1")
						document.frmNuevo.elements[i].disabled=true;
				}				
			}
			
			function ponerSeleccionado(){
				if(document.frm){
					if(document.frm.id){
						if(document.frm.id.length){
							for(var i=0;i<document.frm.id.length;i++){
								if(document.frm.id[i].value==document.frmNuevo.ELHPSELECCIONADO.value){
									document.frm.id[i].checked=true;
									document.frm.id[i].focus();
									break;
								}
							}
						}else{
							if(document.frm.id.value==document.frmNuevo.ELHPSELECCIONADO.value){
								document.frm.id.checked=true;;
								document.frm.id.focus();
							}
						}
					}
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
				combo.options[0] = new Option("--Seleccione uno--","-1");
			}
			
			function filtro(combo_padre,combo_hijo){
				if(combo_padre.selectedIndex==0)
					borrar_combo(combo_hijo); else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty filtroDepartamento && !empty filtroMunicipio}">
						var Padres = new Array();
						<c:forEach begin="0" items="${filtroDepartamento}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${filtroMunicipio}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
								<c:set var="y" value="${fila2[2]}" scope="request"/>
									Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoBasica.estexpdoccodmun== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
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
			
			function filtro2(combo_padre,combo_hijo){
				if(combo_padre.selectedIndex==0)
					borrar_combo(combo_hijo); else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty filtroDepartamento && !empty filtroMunicipio}">
						var Padres = new Array();
						<c:forEach begin="0" items="${filtroDepartamento}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${filtroMunicipio}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
								<c:set var="y" value="${fila2[2]}" scope="request"/>
									Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoBasica.estlugnaccodmun== fila2[0]}">SELECTED</c:if>';
									id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
							</c:if></c:forEach>
							Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>						
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
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
			}
			
			//para lo de ubicacion del niño			
			function filtro11(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoBasica.estjor==fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>
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
				/*
			function filtro12(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_hijo2){
					   alert("sddd"); 
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4"><c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3"><c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoBasica.estgra== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach></c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value;
						var val_padre = -9;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;
						}
						
						if(val_padre!=-9){ 
						   
						    var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
							alert(Padres[val_padre].Sel_Hijos[i]);  
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); 
									combo_hijo.selectedIndex = i+1;
									
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
				</c:if>}
				
			function filtro13(combo_padre,combo_padre2,combo_padre3,combo_hijo){
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3"><c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] }">Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoBasica.estgru== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>'; k++;</c:if></c:forEach>
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
				</c:if>}*/
				
				
					function filtro12(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_hijo2){
					
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
					var id=0;
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st"><c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3"><c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtro.grado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
						Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach></c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.value;
						var val_padre = -9;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;
						}
						
						if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
							//alert(' - ' + Padres[val_padre].Sel_Hijos[i] )
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
				</c:if>
				var codGrado = <c:out value="${sessionScope.nuevoBasica.estgra}"/>
				 <c:if test="${!empty sessionScope.nuevoBasica.estgra}">
				 for(var i=0;i<document.frmNuevo.estgra.length ; i++ ){
				   if(codGrado == document.frmNuevo.estgra[i].value){
				     document.frmNuevo.estgra.options.selectedIndex = i;
				    }
				  }
				</c:if>
				}
				
			function filtro13(combo_padre,combo_padre2,combo_padre3,combo_hijo){
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3">
							    <c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] }">
							       Sel_Hijos[k] = '<c:if test="${sessionScope.filtro.grupo== fila3[0]}">SELECTED</c:if>';
							       id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; 
							       Hijos[k] = '<c:out value="${fila3[1]}"/>'; 
							       id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>'; k++;</c:if>
							    
							 </c:forEach>
							// alert("id_Padre " + id_Padre)
						  Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
					  </c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value;
						var val_padre = -9;
						for(var k=0;k<Padres.length;k++){
						//alert("niv " + niv)
							if(Padres[k].id_Padre[0]==niv){ val_padre=k; 
							  //alert('val_padre ' + val_padre)
							}
						}
						if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
						//alert('no_hijos ' + no_hijos);
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
								 
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
						
						
				</c:if>
				
				
					var codGrado = <c:out value="${sessionScope.nuevoBasica.estgru}"/>
				 <c:if test="${!empty sessionScope.nuevoBasica.estgru}">
				 for(var i=0;i<document.frmNuevo.estgru.length ; i++ ){
				   if(codGrado == document.frmNuevo.estgru[i].value){
				     document.frmNuevo.estgru.options.selectedIndex = i;
				    }
				  }
				  </c:if>
				}
				
			//-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/estudiante/NuevoGuardar.jsp"/>'>
	<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
			  <td width="45%">                
				<!--<input  class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)">-->
				<c:if test="${sessionScope.ModoConsulta==1}"><a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a></c:if>
			  </td>   	
			</tr>	
	  </table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoBasica.estcodigo}"/>">
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='130'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
    <tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
      <td rowspan="2" width="588" bgcolor="#FFFFFF">
			<img src="../etc/img/tabs/datos_basicos_1.gif" alt="Info. B&aacute;sica"  height="26" border="0">
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_familiar_0.gif" alt="Info. Familiar"  height="26" border="0"></a></c:if>
			<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Inf. Salud"  height="26" border="0"></a>
			<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>
			<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_academica_0.gif" alt="Inf. Académica"  height="26" border="0"></a>
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/atencion_especial_0.gif" alt="Atención Especial"  height="26" border="0"></a></c:if>
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/fotografia_0.gif" alt="Subir Foto Estudiante"  height="26" border="0"></a></c:if>
		</td>
		</tr>
  </table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr><td><hr></td><td colspan="3">DATOS BÁSICOS</td></tr>
									<tr>
										<td><span class="style2">*</span> Depto de expedición del documento:</td>
										<td>
											<select name="estexpdoccoddep" onChange='filtro(document.frmNuevo.estexpdoccoddep, document.frmNuevo.estexpdoccodmun)' style='width:150px;'>
													<option value="-1">--seleccione uno--</option>
													<c:forEach begin="0" items="${filtroDepartamento}" var="fila">
														<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoBasica.estexpdoccoddep== fila[0]}">SELECTED</c:if>>
															<c:out value="${fila[1]}"/></option>
													</c:forEach>													
											</select>
										</td>
										<td><span class="style2">*</span> Munic. de expedición del documento:</td>
										<td>
											<select name="estexpdoccodmun" style='width:150px;'>
													<option value="-1">--seleccione uno--</option>
											</select>
										</td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Tipo de documento:</td>
										<td>
											<select name="esttipodoc" style='width:150px;'> 
													<option value="-1">--seleccione uno--</option>
													<c:forEach begin="0" items="${filtroDocumento}" var="fila">
														<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoBasica.esttipodoc== fila[0]}">SELECTED</c:if>>
														<c:out value="${fila[1]}"/></option>
													</c:forEach>													
											</select>
										</td>
										<td ><span class="style2">*</span> Número de documento:</td>
										<td ><input type="text" size='27' name="estnumdoc"  maxlength="15" value='<c:out value="${sessionScope.nuevoBasica.estnumdoc}"/>' onKeyPress='return acepteNumeros(event)'></td>
									</tr>
									<tr>
										<td ><span class="style2">*</span> Primer nombre:</td>
										<td >
											<input type="text"  size='27' name="estnombre1"  maxlength="20" value='<c:out value="${sessionScope.nuevoBasica.estnombre1}"/>'>
										</td>
                                      <td > Segundo nombre:</td>
										<td >
											<input type="text"  size='27' name="estnombre2"  maxlength="20" value='<c:out value="${sessionScope.nuevoBasica.estnombre2}"/>'>
										</td>
									</tr>
									<tr>
                                      <td  ><span class="style2">*</span> Primer apellido:</td>
                                      <td  >
										  <input type="text"  size='27' name="estapellido1"  maxlength="20" value='<c:out value="${sessionScope.nuevoBasica.estapellido1}"/>'>
									  </td>
                                      <td ><span class="style2">*</span> Segundo apellido:</td>
										<td >
											<input type="text"  size='27' name="estapellido2"  maxlength="20" value='<c:out value="${sessionScope.nuevoBasica.estapellido2}"/>'>
										</td>
								</tr>
								<tr>
									<td><span class="style2">*</span> Fecha de nacimiento:</td>
									<td><input type="text"  maxlength='10'  size='12' name="estfechanac" value='<c:out value="${sessionScope.nuevoBasica.estfechanac}"/>'>(dd/mm/aaaa)</td>
									<td><span class="style2">*</span> G&eacute;nero:</td>
									<td><select name="estgenero" style='width:150px;'><option value="-1">--seleccione uno--</option>
										<option value="1" <c:if test="${sessionScope.nuevoBasica.estgenero== '1'}">SELECTED</c:if>>Masculino</option>
					                    <option value="2" <c:if test="${sessionScope.nuevoBasica.estgenero== '2'}">SELECTED</c:if>>Femenino</option>
								    </select>
									</td>
								</tr>
								<tr><td><hr></td><td colspan="3">UBICACIÓN ACADÉMICA</td></tr>
								</table>
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
										<td><span class="style2">*</span> Sede:</td>
										<td>
											<select name="estsede" onChange='filtro11(document.frmNuevo.estsede, document.frmNuevo.estjor,document.frmNuevo.estgra,document.frmNuevo.estgru,document.frmNuevo.estmet)' style='width:300px;'>
											<option value='-9'>-- seleccione uno --</option>
											<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
											<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoBasica.estsede== fila[0]}">SELECTED</c:if>>
											<c:out value="${fila[1]}"/></option>
											</c:forEach>
											</select>
										</td>	
										<td><span class="style2">*</span> Jornada:</td>
										<td>
											<select name="estjor" style='width:120px;'>
											<option value='-9'>-- seleccione uno --</option></select>
										</td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Metodología:</td>
										<td>
								            <select name="estmet" style='width:120px;' onChange='filtro12(document.frmNuevo.estsede, document.frmNuevo.estjor,document.frmNuevo.estmet,document.frmNuevo.estgra,document.frmNuevo.estgru); '>
									            <option value='-9'>-- seleccione uno --</option>
									            <c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila"><option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.nuevoBasica.estmet==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
								            </select>
										</td>
										<td><span class="style2">*</span> Grado:</td>
										<td><select name="estgra" onChange='filtro13(document.frmNuevo.estsede, document.frmNuevo.estjor,document.frmNuevo.estgra,document.frmNuevo.estgru)' style='width:120px;'><option value='-9'>-- seleccione uno --</option></select></td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Grupo:</td>
										<td><select name="estgru" style='width:120px;'><option value='-9'>-- seleccione uno --</option></select></td>
									</tr>	
									<tr><td><hr></td><td colspan="3">FOTOGRAFIA</td></tr>
								</TABLE>
								<table width="100%" border="0">
									<TR><td><img src='<c:url value="/recursos/imagen.jpg?tipo=${params2VO.RECURSO_FOTOGRAFIA_ESTUDIANTE}&param=${sessionScope.nuevoBasica.estcodigo}"/>' width="113" height="149"></td></TR>
								</table>
	</font>
</form>
<c:remove var="filtroConvenio"/><c:remove var="filtroNivel"/><c:remove var="filtroDepartamento"/><c:remove var="filtroMunicipio"/><c:remove var="filtroDocumento"/><c:remove var="filtroEtnia"/><c:remove var="filtroCondicion"/><c:remove var="filtroFamilia"/>
<script>
document.frmNuevo.estexpdoccoddep.onchange();
document.frmNuevo.estsede.onchange();
document.frmNuevo.estmet.onchange();
document.frmNuevo.estgra.onchange();
<c:if test="${sessionScope.nuevoBasica.estado=='1'}">inhabilitarFormulario();</c:if>
<c:if test="${sessionScope.nuevoBasica.estado=='2'}">inhabilitarFormularioTodo();</c:if>
ponerSeleccionado();
</script>
