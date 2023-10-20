<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="filtrolibro" class="siges.librodeNotas.beans.FiltroBeanLibro" scope="session"/><jsp:setProperty name="filtrolibro" property="*"/>
<%@include file="../parametros.jsp"%>
		<script languaje='JavaScript'>
			
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frm(forma){
				validarLista(forma.metodologia, "- Metodologia", 1);
				validarLista(forma.ano, "- Vigencia", 1);
				validarLista(forma.tipoProm, "- Tipo Promoción", 1);
			}

			function lanzar(tipo){
				document.frm.tipo.value=tipo;
				document.frm.action="<c:url value="/boletines/ControllerLibroFiltroEdit.do"/>";
				document.frm.target="";
				document.frm.submit();
			}

			function guardar(tipo){
				if(validarForma(document.frm)){	
					if(document.frm.mostrarFR_.checked)
						document.frm.mostrarFirmaRector.value='1';
					else
						document.frm.mostrarFirmaRector.value='-9';				
					document.frm.tipo.value=tipo;
					document.frm.cmd.value="Buscar";
					document.frm.submit();			
				}	
			}

			
			function cancelar(){
				if (confirm('¿Desea cancelar la generación del libro de notas?')){
 					document.frm.cmd.value="Cancelar";
					location.href='<c:url value="/bienvenida.jsp"/>';
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
			
			function borrar_combo1(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-- Seleccione una --","-9");
			}
			
			
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo4){
				combo_hijo4.selectedIndex=0;
				if(combo_padre.selectedIndex==0){
					borrar_combo1(combo_hijo); borrar_combo(combo_hijo2); 
				}else{
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtrolibro.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
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
			
			
			function filtro2(combo_padre3,combo_hijo){
					borrar_combo(combo_hijo);
					var id=0;
					<c:if test="${!empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
								<c:if test="${fila3[5]==fila4[0]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtrolibro.grado== fila3[0]}">SELECTED</c:if>';
									id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; 
									Hijos[k] = '<c:out value="${fila3[1]}"/>'; 
									id_Padre[k] = '<c:out value="${fila3[5]}"/>'; k++;
								</c:if>
							</c:forEach>
						  Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>
						var niv=combo_padre3.options[combo_padre3.selectedIndex].value;
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
			
		</script>
	<%@include file="../mensaje.jsp"%>
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/librodeNotas/FiltroGuardar.jsp"/>'>
	<br>
	<table border="0" align="center" width="100%">
	<caption>Generar Libro de Notas</caption>
		<tr>
		  <td width="45%" >
       	<input class="boton" name="cmd1" type="button" value="Generar" onClick="guardar(document.frm.tipo.value)">
        <input class="boton" name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		 </td>
		</tr>
	</table>
<!--//////////////////-->	
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>	
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<input type="hidden" name="sede" value='<c:out value="${sessionScope.login.sedeId}"/>'>
	<input type="hidden" name="jornada" value='<c:out value="${sessionScope.login.jornadaId}"/>'>
	<input type="hidden" name="mostrarFirmaRector" value="-9">
	
	<table border="0" align="center" width="100%">
		<tr height="1">
			<td rowspan="2" width="420" bgcolor="#FFFFFF"><img src="../etc/img/tabs/libro_notas_1.gif" alt="Generaci&oacute;n de Libros de Notas" width="84"  height="26" border="0"></td>
		</tr>
  </table>
  
	<table border="0" align="center" width="98%" cellpadding="2" cellspacing="2">
		<caption class="Fila0" colspan="4" align="center"><b>Filtro Generación Libros de Notas</b></caption>
	</table>
	
	<TABLE width="100%" cellpadding="3" cellSpacing="0">
		<tr>
		<td ><span class="style2" >*</span>Vigencia</td>
			<td>
      	<select name="ano" style='width:80px;'>
        	<option value='-9'>-- Seleccione una --</option>
          <c:forEach begin="2005" end="${requestScope.AnioActual}" var="fila" >
          <option value="<c:out value="${fila}"/>" <c:if test="${sessionScope.filtrolibro.ano==fila}">SELECTED</c:if>> <c:out value="${fila}"/>
          </c:forEach>
        </select>
			</td>
			
		
		</tr>
		<tr>
			<td><span class="style2" >*</span>Metodologia</td>
			<td>
				<select name="metodologia" onChange='filtro2(document.frm.metodologia,document.frm.grado)' style='width:180px;'>
				<option value='-9'>-- Seleccione una --</option>
				<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
				<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtrolibro.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
				</c:forEach>
				</select>											
	    </td>
			<td>Grado</td>
			<td>
      	<select name="grado" style='width:150px;' >
        	<option value='-9'>-- Todos --</option>
					<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila">
					<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtrolibro.grado== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
					</c:forEach>
				</select>
      </td>
      <tr>
      	<td><span class="style2">*</span>Tipo de Promoción
			  </td>
			  <td>
			    <select   name='tipoProm'  id='tipoProm'>
				 <option value='-3'>-- Seleccione uno --</option>
				  <c:forEach begin="0" items="${requestScope.filtroTipoProm}" var="item">
							<option value="<c:out value="${item.codigo}"/>"  <c:if test="${sessionScope.filtrolibro.tipoProm ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
				  </c:forEach> 
			    </select>
			   </td>
      </tr>
      
      </tr> 
		<tr>
			 <td width="110px">Mostrar Observación de promoción:</td>
				<td><input type="checkbox" name="mostrarFR_" id="mostrarFR_"  CHECKED ></td>		 
		  </tr> 
		  
		</tr>
	</TABLE>
<!--//////////////////////////////-->
</form>
<script>
document.frm.metodologia.onchange();
</script>