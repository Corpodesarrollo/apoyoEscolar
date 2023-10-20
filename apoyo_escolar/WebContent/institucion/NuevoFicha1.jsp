<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
<%pageContext.setAttribute("filtroTipoEnfasis",Recursos.recurso[Recursos.TIPOENFASIS]);
pageContext.setAttribute("filtroTipoModalidad",Recursos.recurso[Recursos.TIPOMODALIDAD]);
pageContext.setAttribute("filtroTipoPrograma",Recursos.recurso[Recursos.TIPOPROGRAMA]);
pageContext.setAttribute("filtroTipoEspecialidad",Recursos.recurso[Recursos.TIPOESPECIALIDAD]);%>
<jsp:useBean id="fichaVO" class="siges.institucion.beans.FichaVO" scope="session"/>
<%@include file="../parametros.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href='<c:url value="/etc/css/calendar-win2k-1.css"/>' title="win2k-cold-1" />
<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
    <script languaje='javaScript'>
    <!--
			var tipoFicha=3;
			var fichaFicha1=1;
			var fichaFicha2=1;
			var fichaFicha3=1;
      var nav4=window.Event ? true : false;
      function acepteNumeros(eve){
        var key=nav4?eve.which :eve.keyCode;
        return(key<=13 || (key>=48 && key<=57));
      }
			
		function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
			this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
		}
	
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-- Seleccione uno --","-1");
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-- Seleccione uno --","-1");
	}
	
      function hacerValidaciones_frmNuevo(forma){
		    //validarLista(forma.fichaEnfasis,"- Enfasis",1)
		    validarLista(forma.fichaModalidad,"- Modalidad",1)
		    validarCampo(forma.fichaResolucion,"- Resolución",1,20)
		    validarCampo(forma.fichaFechaResolucion,"- Fecha de resolución",1,20)
		    validarFechaUnCampo(forma.fichaFechaResolucion, "- Fecha de resolución");
		    //validarLista(forma.fichaEspecialidad,"- Especialidad",1)
		    //validarSeleccion(forma.fichaPrograma_, "- Tipo de programa");
      }
			
      function hacerValidaciones_frm(forma){
        if(forma.cmd.value!='Nuevo')
          validarSeleccion(forma.r, "- Debe seleccionar un item")
      }
			
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
			
      function lanzar(tipo){
          document.frmNuevo.tipo.value=tipo;
          document.frmNuevo.action="<c:url value="/institucion/ControllerFichaEdit.do"/>";
          document.frmNuevo.submit();
      }
			
      function cancelar(){
        //if (confirm('¿Desea cancelar la inserción de la información de espacios?')){
           document.frmNuevo.cmd.value="Cancelar";
           document.frmNuevo.submit();
        //}
      }
			
      function editar(tipo,nombre){
        document.frm.tipo.value=tipo;
        document.frm.cmd.value=nombre;
        if(document.frm.cmd.value=='Eliminar'){
          if (isChecked(document.frm.r)){
            if(!confirm("¿Realmente desea eliminar el espacio físico?"))
              return false;
          }
        }
        return true;
      }
			
      function guardar(){
        if(validarForma(document.frmNuevo)){
        	validarHidden();
          document.frmNuevo.tipo.value=2;
          document.frmNuevo.cmd.value="Guardar";
          document.frmNuevo.submit();
        }
      }

	function validarHidden(){
		var i=0;
		 if(document.frmNuevo.fichaPrograma){
			 if(document.frmNuevo.fichaPrograma.length){
			 	for(i=0;i<document.frmNuevo.fichaPrograma.length;i++){
			 		if(!document.frmNuevo.fichaPrograma_[i].checked){
			 			document.frmNuevo.fichaPrograma[i].value=-99;
			 		}
			 	}
			 }
		 }
	}
	function filtro(combo_padre,combo_hijo4){
		var id=0;
		var vec=new Array();
		var au=0;
		var est,_est,est_,_gra,gra_,sed_,_sed,_jor,jor_;
		borrar_combo(combo_hijo4); 
		<c:if test="${!empty filtroTipoModalidad && !empty filtroTipoEspecialidad}">
			var Padres = new Array();
			<c:forEach begin="0" items="${filtroTipoModalidad}" var="fila1" varStatus="st">
				id_Hijos=new Array();
				Hijos= new Array();
				Sel_Hijos= new Array();
				id_Padre= new Array();
				var k=0;
				<c:forEach begin="0" items="${filtroTipoEspecialidad}" var="fila2" varStatus="st">
					<c:if test="${fila1[0]==fila2[2]}">
						Sel_Hijos[k] = '<c:forEach begin="0" items="${sessionScope.fichaVO.fichaEspecialidad}" var="filax"><c:if test="${filax == fila2[0]}">SELECTED</c:if></c:forEach>';
						id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
						Hijos[k] = '<c:out value="${fila2[1]}"/>';
						id_Padre[k] = '<c:out value="${fila1[0]}"/>';
						k++;
					</c:if>
				</c:forEach>
				Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value;
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){
				var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						vec[au]=i;au+=1;
						combo_hijo4.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
						combo_hijo4.selectedIndex = i+1;
					}else{
						combo_hijo4.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
					}
				}
			}
		</c:if>
	}
      //-->
    </script>
  <%@include file="../mensaje.jsp"%>
  <font size="1">
  <form name="f" target='1' action="<c:url value="/institucion/ControllerFichaSave"/>" ></form>
  <form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/institucion/NuevoFichaGuardar.jsp"/>">
  <table border="0" align="center" bordercolor="#FFFFFF" width="100%">
    <tr>
      <td width="45%" bgcolor="#FFFFFF">
        <input  class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()" style='display:<c:out value="${permisoBoton}"/>'>
      </td>
    </tr>
  </table>
<!--//////////////////-->
  <!-- FICHAS A MOSTRAR AL USUARIO -->
  <input type="hidden" name="tipo">
  <input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="height" VALUE='150'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
      <tr height="1">
        <td width="10" bgcolor="#FFFFFF">&nbsp;</td>
        <td rowspan="2" width="600" bgcolor="#FFFFFF">
					<script>
					if(fichaFicha1==1)document.write('<img src="../etc/img/tabs/ficha_tecnica1_1.gif" alt=""  height="26" border="0">');
					if(fichaFicha2==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/ficha_tecnica2_0.gif" alt=""  height="26" border="0"></a>');
					if(fichaFicha3==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/ficha_tecnica3_0.gif" alt=""  height="26" border="0"></a>');					
					</script>
				</td>
      </tr>
  </table>
    <!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
                <TABLE width="98%" cellpadding="3" cellSpacing="0" align='center'>
									<caption>INFORMACIÓN DE COLEGIO<caption>
										<tr style="display:none">
                    <td	><span class="style2">*</span> Enfásis del colegio:</td>
                    <td>
                      <select name="fichaEnfasis" style='width:150px;' >
                          <option value="-1">--seleccione uno--</option>
                          <c:forEach begin="0" items="${filtroTipoEnfasis}" var="fila">
                            <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.fichaVO.fichaEnfasis== fila[0]}">SELECTED</c:if>>
                              <c:out value="${fila[1]}"/></option>
                          </c:forEach>
                      </select>										
										</td>
										</tr>
										<tr>
                    <td><span class="style2">*</span> Modalidad:</td>
                    <td>
                      <select name="fichaModalidad" style='width:150px;' onChange='filtro(document.frmNuevo.fichaModalidad,document.frmNuevo.fichaEspecialidad);'>
                          <option value="-1">--seleccione uno--</option>
                          <c:forEach begin="0" items="${filtroTipoModalidad}" var="fila">
                            <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.fichaVO.fichaModalidad== fila[0]}">SELECTED</c:if>>
                              <c:out value="${fila[1]}"/></option>
                          </c:forEach>
                      </select>										
										</td>
                </tr>
                  <tr>
                    <td><span class="style2">*</span> Resolución:</td>
                    <td>
                    <input name="fichaResolucion" type="text" size="20" maxlength="20" value='<c:out value="${sessionScope.fichaVO.fichaResolucion}"/>'>
					</td>
                    <td><span class="style2">*</span> Fecha de Resolución (dd/mm/yyyy):</td>
                    <td>
                    	<input name="fichaFechaResolucion" id="fichaFechaResolucion" type="text" size="10" maxlength="10" value='<c:out value="${sessionScope.fichaVO.fichaFechaResolucion}"/>' readOnly>
							<img 	src='<c:url value="/etc/img/calendario.gif"/>' 
									alt="Seleccione fecha" id="imgFecha"
									style="cursor: pointer;"
									title="Date selector"
									onmouseover="this.style.background='red';"
									onmouseout="this.style.background=''" />                    	
					</td>
                </tr>
                  <tr style="display:none">
                    <td><span class="style2">*</span> Especialiad:</td>
                    <td>
                      <select name="fichaEspecialidad" style='width:150px;'><option value="-1">--seleccione uno--</option></select>										
										</td>
                </tr>
                  <tr><td>Total docentes:</td>
									<td><font style='COLOR:#006699;'><c:out value="${sessionScope.fichaVO.fichaTotDoc }"/></font></td>
									</tr>
                  <tr>
                    <td>Total sedes:</td>
										<td><font style='COLOR:#006699;'><c:out value="${sessionScope.fichaVO.fichaTotSedes}"/></font></td>
									</tr>
                  <tr>
                    <td>Total jornadas:</td>
										<td><font style='COLOR:#006699;'><c:out value="${sessionScope.fichaVO.fichaTotJornadas}"/></font></td>
									</tr>
                </TABLE>
				<!-- <table width="98%" cellpadding="3" cellSpacing="0" align='center'>
								<caption>TIPOS DE PROGRAMA<caption>
                <tr>
	                <td><span class="style2">*</span> Tipo de programa:</td>
	                <td>
		                <c:forEach begin="0" items="${filtroTipoPrograma}" var="fila">
		                <input type="hidden" name="fichaPrograma" value='<c:out value="${fila[0]}"/>'>
	                	<input type="checkbox" name="fichaPrograma_" value='<c:out value="${fila[0]}"/>' <c:forEach begin="0" items="${sessionScope.fichaVO.fichaPrograma}" var="item"><c:if test="${item==fila[0]}">checked</c:if></c:forEach>> <c:out value="${fila[1]}"/><br>
	                	</c:forEach>
  	              </td>
                </tr>
                </table>
  </font>
<!--//////////////////////////////-->
</form>
<script>
<c:if test="${sessionScope.fichaVO.fichaEstado=='1'}">
filtro(document.frmNuevo.fichaModalidad,document.frmNuevo.fichaEspecialidad);
</c:if>
</script>
<script type="text/javascript">
Calendar.setup({ inputField : "fichaFechaResolucion",ifFormat : "%d/%m/%Y",button : "imgFecha",align : "Br"});
</script>
