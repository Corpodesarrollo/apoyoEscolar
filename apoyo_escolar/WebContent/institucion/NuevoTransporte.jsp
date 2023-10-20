<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
<jsp:useBean id="nuevoInstitucion" class="siges.institucion.beans.Institucion" scope="session"/><jsp:setProperty name="nuevoInstitucion" property="*"/>
<jsp:useBean id="nuevoJornada" class="siges.institucion.beans.Jornada" scope="session"/><jsp:setProperty name="nuevoJornada" property="*"/>
<jsp:useBean id="nuevoSede" class="siges.institucion.beans.Sede" scope="session"/><jsp:setProperty name="nuevoSede" property="*"/>
<jsp:useBean id="nuevoEspacio" class="siges.institucion.beans.Espacio" scope="session"/><jsp:setProperty name="nuevoEspacio" property="*"/>
<jsp:useBean id="nuevoNivel" class="siges.institucion.beans.Nivel" scope="session"/>  <jsp:setProperty name="nuevoNivel" property="*"/>
<jsp:useBean id="nuevoTransporte" class="siges.institucion.beans.Transporte" scope="session"/>
<jsp:useBean id="nuevoCafeteria" class="siges.institucion.beans.Cafeteria" scope="session"/><jsp:setProperty name="nuevoCafeteria" property="*"/>
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
				validarLista(forma.tracodjerar,"- Sede",1)
				validarCampo(forma.tranombre, "- Nombre de la ruta", 1, 50)
				validarCampo(forma.traconductor, "- Nombre del conductor", 1, 50)
				validarCampo(forma.traplaca, "- Número de placa del vehiculo", 1, 7)
				validarCampo(forma.traciudad, "- Ciudad de registro del vehiculo", 1, 30)
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
        //if (confirm('¿Desea cancelar la inserción del transporte?')){
           document.frmNuevo.cmd.value="Cancelar";
           document.frmNuevo.submit();
        //}
      }
      function editar(tipo,nombre){
        document.frm.tipo2.value=tipo;
        document.frm.cmd.value=nombre;
        if(document.frm.cmd.value=='Eliminar'){
          if (isChecked(document.frm.r)){
            if(!confirm("Realmente desea eliminarlo?"))
              return false;
          }
        }
        return true;
      }
      function guardar(tipo){
        if(validarForma(document.frmNuevo)){
          document.frmNuevo.tipo2.value=tipo;
          document.frmNuevo.cmd.value="Guardar";
          document.frmNuevo.submit();
        }
      }
    //-->
    </script>
<%@include file="../mensaje.jsp"%>
  <font size="1">
  <c:if test="${!empty sessionScope.filtroTransportes}">
  <center>
 	<div style="width:80%;height:80px;overflow:auto;">
  <form method="post" name="frm"  onSubmit=" return validarForma(frm)" action='<c:url value="/institucion/ControllerNuevoEdit.do"/>'>
  <input type="hidden" name="tipo2" value="6">
  <input type="hidden" name="cmd" value=""><INPUT TYPE="hidden" NAME="height" VALUE='150'>
  <input type="hidden" name="s" value="">
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
      <tr>
        <td></td>
        <td><b>Ruta</b></td>
        <td><b>Nombre</b></td>
        <td><b>Conductor</b></td>
        <td><b>Placa</b></td>
      </tr>
      <c:forEach begin="0" items="${sessionScope.filtroTransportes}" var="fila">
      <tr>
        <td width='10%'>
          <input type='radio' name='r' value='<c:out value="${fila[0]}"/>' onClick='document.frm.s.value="<c:out value="${fila[1]}"/>"'>
        </td>
        <td><c:out value="${fila[0]}"/></td>
        <td><c:out value="${fila[2]}"/></td>
        <td><c:out value="${fila[3]}"/></td>
        <td><c:out value="${fila[4]}"/></td>
      </tr>
      </c:forEach>
      <tr>
        <th colspan='4'>
          <input class='boton' name="cmd1" type="submit" value="Editar" onClick="return editar(6,'Editar')">
          <input class='boton' name="cmd1" type="submit" value="Nuevo" onClick="return editar(6,'Nuevo')"  style='display:<c:out value="${permisoBoton}"/>'>
          <input class='boton' name="cmd1" type="submit" value="Eliminar" onClick="return editar(6,'Eliminar')"  style='display:<c:out value="${permisoBoton}"/>'>
        </th>
      </tr>
    </table>
  </form>
  </div>
  </center>
  </c:if>
  <form name='f' target='1' action='<c:url value="/institucion/ControllerFiltroSave.do"/>'></form>
  <form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)" action='<c:url value="/institucion/NuevoGuardar.jsp"/>'>
  <table border="0" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="silver">
    <tr>
      <td width="45%" bgcolor="#FFFFFF">
        <input  class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(6)"  style='display:<c:out value="${permisoBoton}"/>'>
      </td>
    </tr>
  </table>
<!--//////////////////-->
  <!-- FICHAS A MOSTRAR AL USUARIO -->
  <input type="hidden" name="tipo2" value="6">
  <input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
      <tr height="1">
        <td width="10" bgcolor="#FFFFFF">&nbsp;</td>
        <td rowspan="2" width="600" bgcolor="#FFFFFF"><script>if(fichaBasico==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Inf Básica"  height="26" border="0"></a>');if(fichaSede==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/sedes_0.gif" alt="Sedes"  height="26" border="0"></a>');if(fichaEspacio==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/espacios_fisicos_0.gif" alt="Espacios "  height="26" border="0"></a>');if(fichaTransporte==1)document.write('<img src="../etc/img/tabs/transporte_1.gif" alt="Transporte"  height="26" border="0">');if(fichaCafeteria==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/cafeteria_0.gif" alt="Cafeteria"  height="26" border="0"></a>');if(fichaConflicto==1)document.write('<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/conflicto_escolar_0.gif" alt="Conflicto escolar" border="0"  height="26"></a>');</script></td>
      </tr>
  </table>
    <!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
                <TABLE width="100%" cellpadding="3" cellSpacing="0">
                  <tr>
                    <td><span class="style2">*</span> Sede: </td>
                    <td>
                      <select name="tracodjerar" style='width:220px;'>
                          <option value="-1">--seleccione uno--</option>
                          <c:forEach begin="0" items="${sessionScope.filtroSedeJerarquia}" var="fila">
                            <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoTransporte.tracodjerar== fila[0]}">SELECTED</c:if>>
                              <c:out value="${fila[1]}"/></option>
                          </c:forEach>
                      </select>
                    </td>
                    <td><span class="style2">*</span> Nombre de la ruta: </td>
                    <td>
                      <input type="text" name="tranombre"  maxlength="60" value='<c:out value="${sessionScope.nuevoTransporte.tranombre}"/>'>
                    </td>
                  </tr>
                  <tr>
                    <td><span class="style2">*</span> Nombre del conductor:</td>
                    <td><input type="text" name="traconductor"  maxlength="60" value='<c:out value="${sessionScope.nuevoTransporte.traconductor}"/>'></td>
                    <td>Celular del conductor:</td>
                    <td><input name="tracelular" type="text" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.nuevoTransporte.tracelular}"/>' size="15"  maxlength="10"></td>
                  </tr>
                  <tr>
                    <td>Cupos del vehiculo: </td>
                    <td><input name="tracupos" type="text" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.nuevoTransporte.tracupos}"/>' size="5"  maxlength="3"></td>
                    <td colspan="2"><span class="style2">*</span> Placa número: <input name="traplaca" type="text" value='<c:out value="${sessionScope.nuevoTransporte.traplaca}"/>' size="7"  maxlength="7"> 
                    de: <input type="text" name="traciudad"  maxlength="30" value='<c:out value="${sessionScope.nuevoTransporte.traciudad}"/>'></td>
                  </tr>
                  <tr>
                      <td>Descripcion del vehiculo:</td>
                      <td><textarea name="tradescripcion" cols="30" rows="3" wrap='virtual'><c:out value="${sessionScope.nuevoTransporte.tradescripcion}"/></textarea></td>
					  <td>Costo de la ruta completa: <p>Costo de la media ruta: </td>
					  <td><input name="tracostocompleto" type="text" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.nuevoTransporte.tracostocompleto}"/>' size="10"  maxlength="10"><p>
					  <input type="text" name="tracostomedia"  maxlength="60" value='<c:out value="${sessionScope.nuevoTransporte.tracostomedia}"/>' onKeyPress='return acepteNumeros(event)'>
					  </td>
                  </tr>
                <tr>
                  <td colspan="2"><span class="style2">*</span> Campos obligatorios</td>
                  <td colspan="2">&nbsp;</td>
                </tr>
                </TABLE>
  </font>
</form>
</body>
</html>
<script>
<c:if test="${sessionScope.nuevoTransporte.estado=='1'}">document.frmNuevo.tracodjerar.disabled=true;</c:if>
<c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
</script>