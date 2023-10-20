<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %> 
<jsp:useBean id="nuevoInstitucion" class="siges.institucion.beans.Institucion" scope="session"/><jsp:setProperty name="nuevoInstitucion" property="*"/>
<jsp:useBean id="nuevoJornada" class="siges.institucion.beans.Jornada" scope="session"/><jsp:setProperty name="nuevoJornada" property="*"/>
<jsp:useBean id="nuevoSede" class="siges.institucion.beans.Sede" scope="session"/><jsp:setProperty name="nuevoSede" property="*"/>
<jsp:useBean id="nuevoEspacio" class="siges.institucion.beans.Espacio" scope="session"/><jsp:setProperty name="nuevoEspacio" property="*"/>
<jsp:useBean id="nuevoNivel" class="siges.institucion.beans.Nivel" scope="session"/><jsp:setProperty name="nuevoNivel" property="*"/>
<jsp:useBean id="nuevoTransporte" class="siges.institucion.beans.Transporte" scope="session"/><jsp:setProperty name="nuevoTransporte" property="*"/>
<jsp:useBean id="nuevoCafeteria" class="siges.institucion.beans.Cafeteria" scope="session"/><jsp:setProperty name="nuevoCafeteria" property="*"/>
<%@include file="../parametros.jsp"%>
<script languaje='javaScript'>
    <!--
    var extensiones = new Array();
    extensiones[0]=".jpg";
    extensiones[1]=".gif";
      var nav4=window.Event ? true : false;
      function acepteNumeros(eve){
        var key=nav4?eve.which :eve.keyCode;
        return(key<=13 || (key>=48 && key<=57));
      }
      function hacerValidaciones_frmNuevoSimbolo(forma){
        validarExtension(forma.insescudo, "- Bandera (debe ser gif o jpg)",extensiones)
        validarExtension(forma.insbandera, "- Escudo (debe ser gif o jpg)",extensiones)
      }

      function lanzarSimbolo(tipo){
          document.frmNuevoSimbolo.tipo2.value=tipo;
          document.frmNuevoSimbolo.action="<c:url value="/institucion/ControllerNuevoEdit.do"/>";
          document.frmNuevoSimbolo.submit();
      }
      function guardarSimbolo(tipo){
        if(validarForma(document.frmNuevoSimbolo)){
          document.frmNuevoSimbolo.encoding="multipart/form-data";
          document.frmNuevoSimbolo.tipo2.value=tipo;
          document.frmNuevoSimbolo.cmd.value="Guardar";
          document.frmNuevoSimbolo.submit();
        }
      }
      function cancelarSimbolo(){
        if (confirm('¿Desea cancelar la inserción de los simbolos?')){
          document.frmNuevoSimbolo.cmd.value="Cancelar";
          document.frmNuevoSimbolo.action='<c:url value="/institucion/NuevoGuardar.jsp"/>';
          document.frmNuevoSimbolo.submit();
        }
      }
      //-->
    </script>
<%@include file="../mensaje.jsp"%>
  <font size="1">
  <form name="f" method="post" action='<c:url value="/institucion/ControllerFiltroSave.do"/>'></form>
  <form method="post" name="frmNuevoSimbolo" onSubmit=" return validarForma(frmNuevoSimbolo)" action='<c:url value="/institucion/ControllerNuevoSave2.do"/>'>
  <table border="1" align="center" bordercolor="#FFFFFF" width="100%">
    <tr>
      <td width="45%" bgcolor="#FFFFFF">
        <input  class='boton' name="delete" type="reset" value="Borrar">
        <input  class='boton' name="cmd1" type="button" value="Guardar" onClick="guardarSimbolo(4)">
        <input  class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelarSimbolo()">
        <input  class='boton' name="cmd13" type="button" value="Ayuda" onClick="ayuda()">
      </td>
    </tr>
  </table>
<!--//////////////////-->
  <!-- FICHAS A MOSTRAR AL USUARIO -->
  <input type="hidden" name="tipo2" value="4">
  <input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
      <tr height="1">
        <td width="10" bgcolor="#FFFFFF">&nbsp;</td>
        <td rowspan="2" width="600" bgcolor="#FFFFFF"><a href="javaScript:lanzarSimbolo(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Info Básica"  height="26" border="0"></a><a href="javaScript:lanzarSimbolo(2)"><img src="../etc/img/tabs/sedes_0.gif" alt="Sedes"  height="26" border="0"></a><a href="javaScript:lanzarSimbolo(3)"><img src="../etc/img/tabs/espacios_fisicos_0.gif" alt="Espacios"  height="26" border="0"></a><img src="../etc/img/tabs/simbolo_1.gif" alt="Simbolos"  height="26" border="0"><a href="javaScript:lanzarSimbolo(5)"><img src="../etc/img/tabs/gobierno_escolar_0.gif" alt="Gobierno"  height="26" border="0"></a><a href="javaScript:lanzarSimbolo(6)"><img src="../etc/img/tabs/transporte_0.gif" alt="Transporte"  height="26" border="0"></a><a href="javaScript:lanzarSimbolo(7)"><img src="../etc/img/tabs/cafeteria_0.gif" alt="Cafeteria"  height="26" border="0"></a></td>
      </tr>
  </table>
    <!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
                <TABLE width="100%" cellpadding="3" cellSpacing="0">
                  <tr>
                    <td> Escudo:</td>
                    <th>
                    <img src='<c:url value="/${requestScope.escudo}"/>' width='100' height='100' border='1'><br>
                    <input type='file' name='insescudo' value='<c:out value="${sessionScope.nuevoInstitucion.insescudo}"/>'>
                    </th>
                    <td> Bandera:</td>
                    <th>
                    <img src='<c:url value="/${requestScope.bandera}"/>' width='100' height='100' border='1'><br>
                    <input type='file' name='insbandera' value='<c:out value="${sessionScope.nuevoInstitucion.insbandera}"/>'>
                    </th>
                  </tr>
                  <tr>
                    <td> Lema:</td>
                    <td colspan='3'><input type='text' name='inslema' size='50'  value='<c:out value="${sessionScope.nuevoInstitucion.inslema}"/>'></td>
                  </tr>
                  <tr>
                    <td> Himno:</td>
                    <td>
                    <textarea name="inshimno" cols="25" rows="15" wrap='virtual'><c:out value="${sessionScope.nuevoInstitucion.inshimno}"/></textarea>
                    </td>
                    <td> Historia:</td>
                    <td>
                    <textarea name="inshistoria" cols="25" rows="15" wrap='virtual'><c:out value="${sessionScope.nuevoInstitucion.inshistoria}"/></textarea>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2"><span class="style2">*</span> Campos obligatorios</td>
                    <td colspan="2">&nbsp;</td>
                  </tr>
                </TABLE>
  </font>
<!--//////////////////////////////-->
</form>