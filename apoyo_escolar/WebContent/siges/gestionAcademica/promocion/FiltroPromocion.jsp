<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="filtroPromocion" class="siges.gestionAcademica.promocion.beans.FiltroPromocion" scope="session"/>
<jsp:setProperty name="filtroPromocion" property="*"/>


<c:set var="tip" value="1" scope="page"/>
<script languaje='javaScript'>
		<!--
		
	 
		<%
		int lindx;
		if (request.getHeader("Referer").lastIndexOf('?')==-1)
			lindx=request.getHeader("Referer").length();
		else
			lindx=request.getHeader("Referer").lastIndexOf('?');%>
    var imagen1;
    //imagen1=new Image
   // imagen1.src='<c:url value="/etc/img/spinner.gif"/>';
    var ScrollSpeed = 100;  // milliseconds between scrolls
	var ScrollChars = 1;    // chars scrolled per time period
	var stop = 1;
	var flag = 0;
	var flagSpinner = 0;
	var msg2 = "";
	var msg = "";
	var decimas = 0  
	var segundos = 0  
	var minutos = 0  
	var ValorCrono = ""  ;
	CronoEjecutandose = true;  
	CronoID= null;
    function init(){stop = 1;flagSpinner = 0;}
	function Run(){ stop = 0;   flagSpinner=0;    SetupTicker();}
	function end(){stop = 1; DetenerCrono(); flagSpinner=0;} 
	function DetenerCrono (){if(CronoEjecutandose)
	                              clearTimeout(CronoID);
	                              CronoEjecutandose = false;
	                             } 
	                             
	                             
	                             
	function RunTicker() {
	 
	  if(stop == 0){ 
	 
	   CronoID = window.setTimeout('RunTicker()',ScrollSpeed);
	  
	   document.getElementById("txtmsg").innerHTML = "BUSCANDO";
	   
	   document.getElementById("barraCargar").innerHTML = msg2;
	   
	   flag++;
	   
	   if(flag == 6) flag=0;
	   
	   msg2 = msg.substring(0,flag);
	   decimas++;  
		 if ( decimas > 9 ){decimas = 0;segundos++;  
			 if ( segundos > 59 ){segundos = 0;minutos++;  
				 if ( minutos > 99 ){alert('Fin de la cuenta');DetenerCrono();return true;  
				 }  
			 }  
		 }  
		 ValorCrono = (minutos < 10) ? "0" + minutos : minutos  
		 ValorCrono += (segundos < 10) ? ":0" + segundos : ":" + segundos  
		 ValorCrono += ":" + decimas   
		 CronoEjecutandose = true;  
		 return true  
	 }
	}

	 
   
		
		
		    
    function SetupTicker() { 
      flag = 0;  
      msg = "... .";
      RunTicker();
    }
		
		
		
		
    
    
 
 
			var fichaPromocion=1;
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function setVisible(){if(document.getElementById('t1')){ document.getElementById('t1').style.display='none'; document.getElementById('t2').style.display='none';}}
			function hacerValidaciones_frmFiltro(forma){
				validarLista(forma.metodologia,"- Metodologia",1)
				validarLista(forma.grado,"- Grado",1)
				validarLista(forma.grupo,"- Grupo",1)
				validarLista(forma.tipoPromocion,"- Tipo de promoción.",1)

			}
			function lanzar(tipo){
				document.frmFiltro.tipo.value=tipo;
				document.frmFiltro.action="<c:url value="/promocion/ControllerPromocionEdit.do"/>";
				document.frmFiltro.submit();
			}
			function buscarPromocion(tipo){
				if(validarForma(document.frmFiltro)){
					document.frmFiltro.metodologia_.value=document.frmFiltro.metodologia.options[document.frmFiltro.metodologia.selectedIndex].text;
					document.frmFiltro.grado_.value=document.frmFiltro.grado.options[document.frmFiltro.grado.selectedIndex].text;
					document.frmFiltro.grupo_.value=document.frmFiltro.grupo.options[document.frmFiltro.grupo.selectedIndex].text;
					document.frmFiltro.tipo.value=tipo;
					document.frmFiltro.cmd.value="Buscar";
					document.frmFiltro.ext2.value='/promocion/ControllerPromocionEdit.do?tipo=1';  //peticion
				  
				   Run();
				  document.frmFiltro.submit();
				}	
			}
			function cancelarEvalArea(){
				if (confirm('¿Desea cancelar la promoción?')){
 					document.frmFiltro.cmd.value="Cancelar";
					document.frmFiltro.submit(); 
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
			function filtro2(combo_padre0,combo_padre,combo_hijo){
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroGrado2 && !empty requestScope.filtroGrupo2}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGrupo2}" var="fila2"><c:if test="${fila[0]==fila2[2] && fila[2]==fila2[3]}">
					<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerPromocionEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("PromocionGuardar.jsp")){ %>
					Sel_Hijos[k] = '<c:if test="${sessionScope.filtroPromocion.grupo== fila2[0]}">SELECTED</c:if>';
					<% }else{ %>
					Sel_Hijos[k] ='';
					<% }%>
					id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[4]}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
					var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre0.value;;
					var val_padre = -1;
					for(var k=0;k<Padres.length;k++){
						if(Padres[k].id_Padre[0]==niv){
							val_padre=k;
						}
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
			function filtro3(combo_padre,combo_hijo,combo_hijo2){
				borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
					<c:if test="${!empty requestScope.filtroMetodologia && !empty requestScope.filtroGrado2}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila2"><c:if test="${fila[0]==fila2[2]}">
							<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerPromocionEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("PromocionGuardar.jsp")){ %>
							Sel_Hijos[k] = '<c:if test="${sessionScope.filtroPromocion.grado== fila2[0]}">SELECTED</c:if>';
							<% }else{ %>
							Sel_Hijos[k] ='';
							<% }%>id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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
			//-->
		</script>   
<c:import url="/mensaje.jsp"/>

	<form method="post" name="frmFiltro"  onSubmit="return validarForma(frmFiltro)" action='<c:url value="/siges/gestionAcademica/promocion/PromocionGuardar.jsp"/>'>
	<br>
	<table cellpadding="0" cellspacing="0" border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Promoci&oacute;n </caption>
		<tr><td width="45%" bgcolor="#FFFFFF"></td></tr>
			<tr>		
			  <td width="45%" bgcolor="#FFFFFF">
            <input class='boton' name="cmd1" type="button"  class="boton" value="Buscar" onClick="buscarPromocion(document.frmFiltro.tipo.value)">
			  </td>
			<td> <span align = "center"  id="txtmsg"   name="txtmsg" style="font-weight:bold;font-size:10pt;color: #FF6666"> </span><span align = "center"  id="barraCargar" name="barraCargar" style="font-weight:bold;font-size:9pt;color: #FF6666"> </span>
			</td>
			</tr>
		<tr><td width="45%" bgcolor="#FFFFFF"></td></tr>
	  </table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
	<INPUT TYPE="hidden" NAME="height" VALUE='130'>
	<input type="hidden" name="grado_" value='<c:out value="${sessionScope.filtroPromocion.grado_}"/>'>
	<input type="hidden" name="grupo_" value='<c:out value="${sessionScope.filtroPromocion.grupo_}"/>'>
	<input type="hidden" name="metodologia_" value='<c:out value="${sessionScope.filtroPromocion.metodologia_}"/>'>
	<input type="hidden" name="metodologia_Default" value='<c:out value="${sessionScope.filtroPromocion.metodologia_}"/>'>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
			<td rowspan="2" width="600" bgcolor="#FFFFFF"><img src='<c:url value="/etc/img/tabs/Promocion_1.gif"/>' alt="Promocion" width="84"  height="26" border="0"></td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE width="100%" cellpadding="3" cellSpacing="0">
			<tr>
				<td><span class="style2">*</span>Metodologia:</td>
				<td>
					<select name="metodologia" id="metodologia" style='width:120px;' onChange='setVisible();filtro3(document.frmFiltro.metodologia,document.frmFiltro.grado,document.frmFiltro.grupo);'>
						<option value='-3'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="fila">
							<option value="<c:out value="${fila[0]}"/>" '<c:if test="${sessionScope.filtroPromocion.metodologia==fila[0]}"><% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerPromocionEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("PromocionGuardar.jsp")){ %>' SELECTED '<% } %></c:if>'><c:out value="${fila[1]}"/></option>
						</c:forEach>													
					</select>
				</td>	
				<td><span class="style2">*</span>Grado:</td>
				<td><select name="grado" id="grado" style='width:120px;' onChange='setVisible();filtro2(document.frmFiltro.metodologia,document.frmFiltro.grado, document.frmFiltro.grupo);'><option value='-3'>-- Seleccione uno --</option></select></td>	
				<td><span class="style2">*</span>Grupo:</td>
				<td><select name="grupo" id="grupo" style='width:100px;' onChange='setVisible()'><option value='-1'>-- Seleccione uno --</option></select></td>
			</tr>
			<tr>
			<td>Orden:</td>
				<td>
					<select name="orden" style='width:100px;' onChange='setVisible()'>
						<option value='0' <c:if test="${sessionScope.filtroPromocion.orden=='0'}">SELECTED</c:if>>Apellidos</option>
						<option value='1' <c:if test="${sessionScope.filtroPromocion.orden=='1'}">SELECTED</c:if>>Nombres</option>
						<option value='2' <c:if test="${sessionScope.filtroPromocion.orden=='2'}">SELECTED</c:if>>Identificación</option>
						<option value='3' <c:if test="${sessionScope.filtroPromocion.orden=='3'}">SELECTED</c:if>>Código</option>
					</select>
				</td>
             <td><span class="style2">*</span>Tipo de Promoción
			  </td>
			  <td>
			    <select   name='tipoPromocion'  id='tipoPromocion' onchange="JavaScript:setVisible();"  >
				 <option value='-3'>-- Seleccione uno --</option>
				  <c:forEach begin="0" items="${requestScope.filtroTipoProm}" var="fila">
							<option value="<c:out value="${fila[0]}"/>"  <c:if test="${sessionScope.filtroPromocion.tipoPromocion == fila[0]}"><% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerPromocionEdit.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("PromocionGuardar.jsp")){ %>' SELECTED '<% } %></c:if>><c:out value="${fila[1]}"/></option>
				  </c:forEach> 
			    </select>
			   </td>
			</tr>   

	</TABLE>
<!--//////////////////////////////-->
</form>
<script type="text/javascript">


filtro3(document.frmFiltro.metodologia,document.frmFiltro.grado,document.frmFiltro.grupo);

document.frmFiltro.grado.onchange();
 

</script>