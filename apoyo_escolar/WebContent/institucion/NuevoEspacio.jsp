<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
  <jsp:useBean id="nuevoInstitucion" class="siges.institucion.beans.Institucion" scope="session"/><jsp:setProperty name="nuevoInstitucion" property="*"/>
  <jsp:useBean id="nuevoJornada" class="siges.institucion.beans.Jornada" scope="session"/><jsp:setProperty name="nuevoJornada" property="*"/>
  <jsp:useBean id="nuevoSede" class="siges.institucion.beans.Sede" scope="session"/>  <jsp:setProperty name="nuevoSede" property="*"/>
  <jsp:useBean id="nuevoEspacio" class="siges.institucion.beans.Espacio" scope="session"/>
  <jsp:useBean id="nuevoNivel" class="siges.institucion.beans.Nivel" scope="session"/>  <jsp:setProperty name="nuevoNivel" property="*"/>
	<jsp:useBean id="nuevoTransporte" class="siges.institucion.beans.Transporte" scope="session"/><jsp:setProperty name="nuevoTransporte" property="*"/>
  <jsp:useBean id="nuevoCafeteria" class="siges.institucion.beans.Cafeteria" scope="session"/><jsp:setProperty name="nuevoCafeteria" property="*"/>
<%pageContext.setAttribute("filtroTipoEspacio",Recursos.recurso[Recursos.TIPOESPACIO]);
pageContext.setAttribute("filtroTipoOcupante",Recursos.recurso[Recursos.TIPOOCUPANTE]);%>
<%@include file="../parametros.jsp"%>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
    <script languaje='javaScript'>
    <!--
			var fichaBasico=1;
			var fichaSede=1;
			var fichaEspacio=1;
			var fichaTransporte=0;
			var fichaCafeteria=0;
			var fichaConflicto=0;
      var nav4=window.Event ? true : false;
      function acepteNumeros(eve){
        var key=nav4?eve.which :eve.keyCode;
        return(key<=13 || (key>=48 && key<=57));
      }
      function hacerValidaciones_frmNuevo(forma){
        validarLista(forma.espcodsede,"- Sede",1)
        validarLista(forma.esptipo,"- Tipo de espacio fisico",1)
        validarCampo(forma.espnombre, "- Nombre", 1, 60)
        validarEntero(forma.espcapacidad, "- Capacidad", 1, 9999)
        validarEnteroOpcional(forma.esparea, "- Area en Mts. Cuadrados", 1, 9999)
        validarLista(forma.espestado,"- Estado",1)
        validarSeleccion(forma.sedjorespcodjor,"- Jornada (debe seleccionar por lo menos uno)")
        validarCampoOpcional(forma.espfuncion, "- Función (máximo 250 caractéres)", 1, 250)
        validarCampoOpcional(forma.espdescripcion, "- Ubicación (máximo 500 caractéres)", 1, 250)
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
          document.frmNuevo.tipo2.value=tipo;
          document.frmNuevo.action="<c:url value="/institucion/ControllerNuevoEdit.do"/>";
          document.frmNuevo.submit();
      }
      function cancelar(){
        //if (confirm('¿Desea cancelar la inserción de la información de espacios?')){
           document.frmNuevo.cmd.value="Cancelar";
           document.frmNuevo.submit();
        //}
      }
      
      function editar(tipo,nombre,m,n){
        document.frm.tipo2.value=tipo;
        document.frm.cmd.value=nombre;
        document.frm.r.value=m;
        document.frm.s.value=n;
        if(document.frm.cmd.value=='Eliminar'){
          if(!confirm("¿Realmente desea eliminar el espacio físico?"))
            return;
	      }
        document.frm.submit();
      }
      
      function nuevo(tipo,nombre){
        document.frm.tipo2.value=tipo;
        document.frm.cmd.value=nombre;
        document.frm.submit();
      }
      
      function Localidad(id_Instituciones, Instituciones, Sel_Instituciones,id_Localidad){
        this.id_Instituciones= id_Instituciones;
        this.Instituciones= Instituciones;
        this.Sel_Instituciones= Sel_Instituciones;
        this.id_Localidad=id_Localidad;
      }
      function guardar(tipo){
        <c:forEach begin="0" items="${sessionScope.filtroJornadasInstitucion}" var="fila" varStatus="st">
          if(document.getElementById('<c:out value="${fila[0]}"/>').style.display == 'none')
            document.frmNuevo.sedjorespcodjor[<c:out value="${st.index}"/>].checked=false;
        </c:forEach>
        if(tipo!=3){
					if(document.frm){
						if(document.frm.nomSede){
							if(document.frm.nomSede.length){
								for(var i=0;i<document.frm.nomSede.length;i++){
									if(document.frm.nomSede.value==document.frmNuevo.espnombre.value){
										alert('El nombre ya esta siendo usado por otro espacio, modifiquelo');
										return false;
									}
								}
							}else{
								if(document.frm.nomSede.value==document.frmNuevo.espnombre.value){
									alert('El nombre ya esta siendo usado por otro espacio, modifiquelo');
									return false;
								}
							}
						}
					}
				}	
        if(validarForma(document.frmNuevo)){
          document.frmNuevo.tipo2.value=tipo;
          document.frmNuevo.cmd.value="Guardar";
          document.frmNuevo.submit();
        }
      }
      function borrar_combo(combo){
        <c:forEach begin="0" items="${sessionScope.filtroJornadasInstitucion}" var="fila" varStatus="st">document.getElementById('<c:out value="${fila[0]}"/>').style.display = 'none';</c:forEach>
      }

      function filtro1(combo_nivel,combo_institucion){
        if(combo_nivel.selectedIndex==0){
          borrar_combo(combo_institucion);
        }else{
          borrar_combo(combo_institucion);
          <c:if test="${!empty sessionScope.filtroSede && !empty sessionScope.filtroJornadaSede}">
          var Localidades = new Array();
            <c:forEach begin="0" items="${sessionScope.filtroSede}" var="fila" varStatus="st">
              <c:set var="z" value="${fila[0]}" scope="request"/>
              id_Instituciones=new Array();Instituciones= new Array();Sel_Instituciones= new Array();id_Localidad= new Array();
              var k=0;
              <c:forEach begin="0" items="${sessionScope.filtroJornadaSede}" var="fila2">
                <c:if test="${fila[0]==fila2[2]}"><c:set var="y" value="${fila2[2]}" scope="request"/>
                  Sel_Instituciones[k] = '<%=nuevoEspacio.seleccion((String)request.getAttribute("y"),(String)request.getAttribute("z"))%>';
                  id_Instituciones[k] = '<c:out value="${fila2[0]}"/>';
                  Instituciones[k] = '<c:out value="${fila2[1]}"/>';
                  id_Localidad[k] = '<c:out value="${fila2[2]}"/>';
                  k++;
                </c:if>
              </c:forEach>
              Localidades [<c:out value="${st.index}"/>] = new Localidad(id_Instituciones, Instituciones, Sel_Instituciones,id_Localidad);
            </c:forEach>
            var niv=combo_nivel.options[combo_nivel.selectedIndex].value;
            var val_padre = -1;
            for(var k=0;k<Localidades.length;k++){
              if(Localidades[k].id_Localidad[0]==niv)
                val_padre=k;
            }
            if(val_padre!=-1){
              var no_Instituciones = Localidades[val_padre].Instituciones.length;
              for(i=0; i < no_Instituciones; i++){
                document.getElementById(""+Localidades[val_padre].id_Instituciones[i]).style.display = '';
              }
            }
          </c:if>
        }
      }
      //-->
    </script>
  <%@include file="../mensaje.jsp"%>
  <font size="1">
  <c:if test="${!empty sessionScope.filtroEspacios}">
  <center>
	<table cellpadding="0" cellspacing="0" border="1" ALIGN="CENTER" width="95%" bordercolor="#F0F0F0"><tr><td>
	<div style="width:100%;height:160px;overflow:auto;">
  <form method="post" name="frm"  onSubmit=" return validarForma(frm)" action='<c:url value="/institucion/ControllerNuevoEdit.do"/>'>
  <input type="hidden" name="tipo2" value="3">
  <input type="hidden" name="cmd" value=""><INPUT TYPE="hidden" NAME="height" VALUE='150'>
  <input type="hidden" name="r" value=""><input type="hidden" name="s" value="">
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
    <table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
			<caption>LISTA DE ESPACIOS FÍSICOS</caption>
      <tr>
        <td colspan='4'>
          <input  class='boton' name="cmd1" type="submit" value="Nuevo" onClick="nuevo(3,'Nuevo')" style='display:<c:out value="${permisoBoton}"/>'>
        </td>
      </tr>
      <tr>
        <th class="EncabezadoColumna" width="10%"></th>
        <th class="EncabezadoColumna" width="20%"><b>Sede</b></th>
        <th class="EncabezadoColumna" width="30%"><b>Tipo</b></th>
        <th class="EncabezadoColumna" width="40%"><b>Nombre</b></th>
      </tr>
      <c:forEach begin="0" items="${sessionScope.filtroEspacios}" var="fila" varStatus="st">
      <tr>
        <td width='1%' class='Fila<c:out value="${st.count%2}"/>'>
          <a href='javaScript:editar(3,"Editar",<c:out value="${fila[0]}"/>,<c:out value="${fila[1]}"/>,"<c:out value="${fila[3]}"/>");'><img border='0' src="../etc/img/editar.png" width='15' height='15'></a>
          <a href='javaScript:editar(3,"Eliminar",<c:out value="${fila[0]}"/>,<c:out value="${fila[1]}"/>,"<c:out value="${fila[3]}"/>");'><img border='0' src="../etc/img/eliminar.png" width='15' height='15'></a>
        </td>
        <td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
        <td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></td>
        <td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/><input type="hidden" name="nomSede" value='<c:out value="${fila[3]}"/>'></td>
      </tr>
      </c:forEach>
    </table>
  </form>
  </div>
	</td></tr></table>
  </center>
  </c:if>
  <form name="f" target='1' action="<c:url value="/institucion/ControllerFiltroSave"/>" ></form>
  <form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/institucion/NuevoGuardar.jsp"/>">
  <table border="0" align="center" bordercolor="#FFFFFF" width="100%">
    <tr>
      <td width="45%" bgcolor="#FFFFFF">
        <input  class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(3)" style='display:<c:out value="${permisoBoton}"/>'>
      </td>
    </tr>
  </table>
<!--//////////////////-->
  <!-- FICHAS A MOSTRAR AL USUARIO -->
  <input type="hidden" name="tipo2" value="3">
  <input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
      <tr height="1">
        <td width="10" bgcolor="#FFFFFF">&nbsp;</td>
        <td rowspan="2" width="600" bgcolor="#FFFFFF"><script>if(fichaBasico==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Inf Básica"  height="26" border="0"></a>');if(fichaSede==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/sedes_0.gif" alt="Sedes"  height="26" border="0"></a>');if(fichaEspacio==1)document.write('<img src="../etc/img/tabs/espacios_fisicos_1.gif" alt="Espacios "  height="26" border="0">');if(fichaTransporte==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/transporte_0.gif" alt="Transporte"  height="26" border="0"></a>');if(fichaCafeteria==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/cafeteria_0.gif" alt="Cafeteria"  height="26" border="0"></a>');if(fichaConflicto==1)document.write('<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/conflicto_escolar_0.gif" alt="Conflicto escolar" border="0"  height="26"></a>');</script></td>
      </tr>
  </table>
    <!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
                <TABLE width="100%" cellpadding="3" cellSpacing="0">
                  <tr>
                    <td><span class="style2">*</span> Sede: </td>
                    <td colspan="3">
                      <select name="espcodsede" onChange='filtro1(document.frmNuevo.espcodsede,document.frmNuevo.sedjorespcodjor)' style='width:300px;'>
                          <option value="-1">--seleccione uno--</option>
                          <c:forEach begin="0" items="${sessionScope.filtroSede}" var="fila">
                            <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoEspacio.espcodsede== fila[0]}">SELECTED</c:if>>
                              <c:out value="${fila[1]}"/></option>
                          </c:forEach>
                      </select>
                    </td>
                   </tr> 
                  <tr>
                    <td><span class="style2">*</span> Nombre:</td>
                    <td colspan="3"><input type="text" name="espnombre"  maxlength="60" size='50' value='<c:out value="${sessionScope.nuevoEspacio.espnombre}"/>'></td>
                  </tr>  
                  <tr>
                    <td><span class="style2">*</span> Tipo de espacio: </td>
                    <td>
                      <select name="esptipo" style='width:150px;'>
                          <option value="-1">--seleccione uno--</option>
                          <c:forEach begin="0" items="${filtroTipoEspacio}" var="fila">
                            <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoEspacio.esptipo== fila[0]}">SELECTED</c:if>>
                              <c:out value="${fila[1]}"/></option>
                          </c:forEach>
                      </select>
                    </td>
                    <td>Tipo de ocupante:</td>
                    <td>
                      <select name="esptipoocupante" style='width:150px;' >
                          <option value="-1">--seleccione uno--</option>
                          <c:forEach begin="0" items="${filtroTipoOcupante}" var="fila">
                            <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoEspacio.esptipoocupante== fila[0]}">SELECTED</c:if>>
                              <c:out value="${fila[1]}"/></option>
                          </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td>Área (Mts.<sup>2</sup>): </td>
                    <td><input type="text" name="esparea"  maxlength="4" size='5' value='<c:out value="${sessionScope.nuevoEspacio.esparea}"/>' onKeyPress='return acepteNumeros(event)'></td>
                    <td><span class="style2">*</span> Capacidad:</td>
                    <td><input type="text" name="espcapacidad"  maxlength="4" size='5'value='<c:out value="${sessionScope.nuevoEspacio.espcapacidad}"/>' onKeyPress='return acepteNumeros(event)' ></td>
									<tr>	
									</tr>
                      <td><span class="style2">*</span> Estado:</td>
                      <td>
                      <input type="radio" name="espestado" value="0" <c:if test="${sessionScope.nuevoEspacio.espestado== '0'}">CHECKED</c:if> >Inhabilitado&nbsp;&nbsp;&nbsp;
                      <input type="radio" name="espestado" value="1" <c:if test="${sessionScope.nuevoEspacio.espestado== '1'}">CHECKED</c:if> >Habilitado
                  </td>
                </tr>
                <tr>
                  <td>Función:</td>
                  <td colspan='3'><textarea name="espfuncion" cols="70" rows="2" wrap='virtual' ><c:out value="${sessionScope.nuevoEspacio.espfuncion}"/></textarea></td>
                </tr>
                <tr>
                  <td>Ubicación:</td>
                  <td colspan='3'><textarea name="espdescripcion" cols="70" rows="2" wrap='virtual' ><c:out value="${sessionScope.nuevoEspacio.espdescripcion}"/></textarea></td>
                </tr>
                <tr>
                  <td><span class="style2">*</span> Jornadas:</td>
                  <td colspan='3'>
                  <TABLE width="100%" cellpadding="0" cellSpacing="0" align="left">	
	                  <c:forEach begin="0" items="${sessionScope.filtroJornadasInstitucion}" var="fila">
	                    <tr id='<c:out value="${fila[0]}"/>' style="display:none">
	                      <td>
	                      <c:set var="z" value="${fila[0]}" scope="request"/>
	                      <input type='checkbox' name='sedjorespcodjor' value="<c:out value="${fila[0]}"/>" <%=nuevoEspacio.seleccion((String)request.getAttribute("z"))%> >
	                      <c:out value="${fila[1]}"/>
	                      </td>
	                    </tr>
	                  </c:forEach>
									</TABLE>                  
                  </td>
                </tr>
                </TABLE>
  </font>
<!--//////////////////////////////-->
</form>
<c:remove var="filtroTipoEspacio"/><c:remove var="filtroTipoOcupante"/> 
<script>
document.frmNuevo.espcodsede.onchange();
<c:if test="${sessionScope.nuevoEspacio.estado=='1'}">document.frmNuevo.espcodsede.disabled=true;</c:if>
<c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
</script>