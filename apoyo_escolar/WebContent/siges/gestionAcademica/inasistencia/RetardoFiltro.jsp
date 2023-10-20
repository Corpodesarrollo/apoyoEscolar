<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="filtroInasistencia" class="siges.gestionAcademica.inasistencia.vo.FiltroInasistencia" scope="session"/><jsp:setProperty name="filtroInasistencia" property="*"/>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.inasistencia.vo.ParamVO" scope="page"/>
<c:import url="/parametros.jsp"/>
<script languaje='javaScript'>
		<!--
			
			
			
    var imagen1;
    imagen1=new Image
    imagen1.src='<c:url value="/etc/img/spinner.gif"/>';
    var ScrollSpeed = 100;  // milliseconds between scrolls
	var ScrollChars = 1;    // chars scrolled per time period
	var stop = 0;
	var flag = 0;
	var flagSpinner = 0;
	var msg2 = "";
	var decimas = 0  
	var segundos = 0  
	var minutos = 0  
	var ValorCrono = ""  ;
    
    function SetupTicker() { flag = 0; msg = "... .";RunTicker();}
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
	function init(){stop = 1;flagSpinner = 0;}
	function Run(){   stop = 0;flagSpinner=0; SetupTicker();}
	function end(){stop = 1;DetenerCrono(); flagSpinner=0;} 
	function DetenerCrono (){if(CronoEjecutandose)clearTimeout(CronoID);CronoEjecutandose = false;} 
	 
   
		
		
		
		
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function setVisible(){
			if(document.getElementById('t1')){ 
				document.getElementById('t1').style.display='none'; 
				document.getElementById('t2').style.display='none';}
			}
			function hacerValidaciones_frmFiltro(forma){
				validarLista(forma.filGrado,"- Metodologia",1)
				validarLista(forma.filGrado,"- Grado",1)
				validarLista(forma.filGrupo,"- Grupo",1)
				validarLista(forma.filPeriodo,"- Periodo",1)
				validarLista(forma.filMes,"- Mes",1)
				validarLista(forma.filQuincena,"- Quincena",1)
			}
			function lanzar(tipo){
				document.frmFiltro.tipo.value=tipo;
				document.frmFiltro.cmd.value='';
				document.frmFiltro.action='<c:url value="/inasistencia/Filtro.do"/>';
				document.frmFiltro.submit();
			}
			
			function buscar(){
				if(validarForma(document.frmFiltro)){
					document.frmFiltro.cmd.value='<c:out value="${paramsVO.CMD_BUSCAR}"/>';
					 Run();
					document.frmFiltro.submit();
				}
			}
			
			function cancelar(){
 					document.frmFiltro.cmd.value='<c:out value="${paramsVO.CMD_CANCELAR}"/>';
					document.frmFiltro.submit(); 
			}
			
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione uno--","-99");
			}
			
			function filtroMetodologia(combo_padre,combo_hijo){
				borrar_combo(combo_hijo); 
					var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.lMetodologia}" var="metod" varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.lGrado}" var="grado">
						<c:if test="${metod.codigo==grado.padre}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroInasistencia.filGrado==grado.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grado.codigo}"/>'; Hijos[k] = '<c:out value="${grado.nombre}"/>'; id_Padre[k] = '<c:out value="${grado.padre}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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
			}
			function filtroGrado(combo_abuelo,combo_padre,combo_hijo){
				borrar_combo(combo_hijo); 
					var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.lGrado}" var="grado" varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.lGrupo}" var="grupo">
						<c:if test="${grado.codigo==grupo.padre && grado.padre==grupo.padre2}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroInasistencia.filGrupo==grupo.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grupo.codigo}"/>'; Hijos[k] = '<c:out value="${grupo.nombre}"/>'; id_Padre[k] = '<c:out value="${grupo.padre2}"/>|<c:out value="${grupo.padre}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_abuelo.options[combo_abuelo.selectedIndex].value+'|'+combo_padre.options[combo_padre.selectedIndex].value;
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
					}
			
			function filtroPeriodo(combo_padre,combo_hijo){
				borrar_combo(combo_hijo); 
					var Padres = new Array();
					var niv=combo_padre.options[combo_padre.selectedIndex].value;
						<c:forEach begin="0" items="${requestScope.lMesesVal}" var="grado" varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.lMes}" var="grupo">
						if (niv==1){<c:if test="${grupo.codigo>=grado[0]-1 && grupo.codigo<=grado[1]-1}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroInasistencia.filMes==grupo.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grupo.codigo}"/>'; Hijos[k] = '<c:out value="${grupo.nombre}"/>'; id_Padre[k] = niv; k++;</c:if>}
						if (niv==2){<c:if test="${grupo.codigo>=grado[2]-1 && grupo.codigo<=grado[3]-1}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroInasistencia.filMes==grupo.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grupo.codigo}"/>'; Hijos[k] = '<c:out value="${grupo.nombre}"/>'; id_Padre[k] = niv; k++;</c:if>}
						if (niv==3){<c:if test="${grupo.codigo>=grado[4]-1 && grupo.codigo<=grado[5]-1}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroInasistencia.filMes==grupo.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grupo.codigo}"/>'; Hijos[k] = '<c:out value="${grupo.nombre}"/>'; id_Padre[k] = niv; k++;</c:if>}
						if (niv==4){<c:if test="${grupo.codigo>=grado[6]-1 && grupo.codigo<=grado[7]-1}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroInasistencia.filMes==grupo.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grupo.codigo}"/>'; Hijos[k] = '<c:out value="${grupo.nombre}"/>'; id_Padre[k] = niv; k++;</c:if>}
						if (niv==5){<c:if test="${grupo.codigo>=grado[8]-1 && grupo.codigo<=grado[9]-1}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroInasistencia.filMes==grupo.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grupo.codigo}"/>'; Hijos[k] = '<c:out value="${grupo.nombre}"/>'; id_Padre[k] = niv; k++;</c:if>}
						if (niv==6){<c:if test="${grupo.codigo>=grado[10]-1 && grupo.codigo<=grado[11]-1}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroInasistencia.filMes==grupo.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grupo.codigo}"/>'; Hijos[k] = '<c:out value="${grupo.nombre}"/>'; id_Padre[k] = niv; k++;</c:if>}
						if (niv==7){<c:if test="${grupo.codigo>=grado[12]-1 && grupo.codigo<=grado[13]-1}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroInasistencia.filMes==grupo.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grupo.codigo}"/>'; Hijos[k] = '<c:out value="${grupo.nombre}"/>'; id_Padre[k] = niv; k++;</c:if>}
						</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var val_padre = -1;
						
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv)
								val_padre=k;
						}
					if(val_padre!=-1){
						var no_hijos = Padres[val_padre].Hijos.length;
						for(i=0; i < no_hijos; i++){
							if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); 
								combo_hijo.selectedIndex=i+1;
							}else
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
						}
				}
			}
			//-->
		</script>
<c:import url="/mensaje.jsp"/>
<font size="1">
<form method="post" name="frmFiltro"  onSubmit="return validarForma(frmFiltro)" action='<c:url value="/siges/gestionAcademica/inasistencia/Save.jsp"/>'>
<br>
<table cellpadding="0" cellspacing="0" border="0" align="center" bordercolor="#FFFFFF" width="95%">
<caption>Filtro de retardo</caption>
	<tr><td width="45%" bgcolor="#FFFFFF"></td></tr>
		<tr>		
		  <td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Buscar" onClick="buscar()">
		  </td>
		   <td> <span align = "center"  id="txtmsg"   name="txtmsg" style="font-weight:bold;font-size:10pt;color: #FF6666"> </span><span align = "center"  id="barraCargar" name="barraCargar" style="font-weight:bold;font-size:9pt;color: #FF6666"> </span></td>
		</tr>
	<tr><td width="45%" bgcolor="#FFFFFF"></td></tr>
  </table>
<!--//////////////////-->		
<!-- FICHAS A MOSTRAR AL USUARIO -->
<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_RETARDO}"/>'>
<input type="hidden" name="cmd">
<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
	<tr height="1">
		<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
		<td width="600" bgcolor="#FFFFFF">
			<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_INASISTENCIA}"/>)"><img src='<c:url value="/etc/img/tabs/Inasistencia_0.gif"/>' alt="Inasistencia"  height="26" border="0"></a>
			<img src='<c:url value="/etc/img/tabs/retardo_1.gif"/>' alt="-Retardo-" width="84"  height="26" border="0">
			<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_SALIDA_TARDE}"/>)"><img src='<c:url value="/etc/img/tabs/salida_tarde_0.gif"/>' alt="Salida Tarde"  height="26" border="0"></a>
		</td>
		</tr>
	</table>
	<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE width="100%" cellpadding="1" cellSpacing="0">
			<tr>											
				<td><span class="style2">*</span>Metodologia:</td>
				<td>
					<select name="filMetodologia" style='width:120px;' onChange='setVisible();filtroMetodologia(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado);'>
						<option value='-99'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.lMetodologia}" var="metod">
							<option value="<c:out value="${metod.codigo}"/>" <c:if test="${sessionScope.filtroInasistencia.filMetodologia==metod.codigo}">SELECTED</c:if>><c:out value="${metod.nombre}"/></option>
						</c:forEach>
					</select>
				</td>	
				<td><span class="style2">*</span>Grado:</td>
				<td>
					<select name="filGrado" style='width:120px;' onChange='setVisible();filtroGrado(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado,document.frmFiltro.filGrupo);'>
						<option value='-99'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.lGrado}" var="grado">
							<option value="<c:out value="${grado.codigo}"/>" <c:if test="${sessionScope.filtroInasistencia.filGrado==grado.codigo}">SELECTED</c:if>><c:out value="${grado.nombre}"/></option>
						</c:forEach>
					</select>
				</td>	
				<td><span class="style2">*</span>Grupo:</td>
				<td><select name="filGrupo" style='width:100px;' onChange='setVisible()'>
							<option value='-99'>-- Seleccione uno --</option>
						</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Periodo:</td>
				<td><select name="filPeriodo" style='width:120px;' onChange='setVisible();filtroPeriodo(document.frmFiltro.filPeriodo,document.frmFiltro.filMes)'>
						<option value='-99'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.lPeriodo}" var="fila">
							<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroInasistencia.filPeriodo==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
						</c:forEach>
				</select></td>
				<td><span class="style2">*</span>Mes:</td>
				<td><select name="filMes" style='width:100px;' onChange='setVisible()'>
						<option value='-99'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.lMes}" var="mes">
							<option value="<c:out value="${mes.codigo}"/>" <c:if test="${sessionScope.filtroInasistencia.filMes==mes.codigo}">SELECTED</c:if>><c:out value="${mes.nombre}"/></option>
						</c:forEach>
				  </select>
				 </td>
				 <td><span class="style2">*</span>Quincena:</td>
				  <td>  
					<select name="filQuincena" style='width:100px;' onChange='setVisible()'>
						<option value='-99' > --Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.lQuincena}" var="mes">
							<option value="<c:out value="${mes.codigo}"/>" <c:if test="${sessionScope.filtroInasistencia.filQuincena == mes.codigo}">SELECTED</c:if>><c:out value="${mes.nombre}"/></option>
						</c:forEach>
				  </select>
				 </td>
			</tr>	
	</TABLE>
<!--//////////////////////////////-->
</form>
<script>
filtroMetodologia(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado);
filtroGrado(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado,document.frmFiltro.filGrupo);
document.frmFiltro.filPeriodo.onchange();
</script>