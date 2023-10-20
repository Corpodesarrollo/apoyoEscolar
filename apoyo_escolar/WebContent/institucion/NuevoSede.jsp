<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %> 
  <jsp:useBean id="nuevoInstitucion" class="siges.institucion.beans.Institucion" scope="session"/><jsp:setProperty name="nuevoInstitucion" property="*"/>
  <jsp:useBean id="nuevoJornada" class="siges.institucion.beans.Jornada" scope="session"/><jsp:setProperty name="nuevoJornada" property="*"/>
  <jsp:useBean id="nuevoSede" class="siges.institucion.beans.Sede" scope="session"/>
  <jsp:useBean id="nuevoEspacio" class="siges.institucion.beans.Espacio" scope="session"/><jsp:setProperty name="nuevoEspacio" property="*"/>
  <jsp:useBean id="nuevoNivel" class="siges.institucion.beans.Nivel" scope="session"/>  <jsp:setProperty name="nuevoNivel" property="*"/>
  <jsp:useBean id="nuevoTransporte" class="siges.institucion.beans.Transporte" scope="session"/><jsp:setProperty name="nuevoTransporte" property="*"/>
  <jsp:useBean id="nuevoCafeteria" class="siges.institucion.beans.Cafeteria" scope="session"/><jsp:setProperty name="nuevoCafeteria" property="*"/>
<%@include file="../parametros.jsp"%>
    <script languaje='javaScript'>
    <!--
			var fichaBasico=1;
			var fichaSede=1;
			var fichaEspacio=1;
			var fichaTransporte=0;
			var fichaCafeteria=0;
			var fichaConflicto=0;
      var a="";
      var nav4=window.Event ? true : false;
      function acepteNumeros(eve){
        var key=nav4?eve.which :eve.keyCode;
        return(key<=13 || (key>=48 && key<=57));
      }
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
      function hacerValidaciones_frmNuevo(forma){
        validarSeleccion(forma.sedtipo,"- Tipo de sede")
        validarCampo(forma.sednombre, "- Nombre", 1, 60)
        validarSeleccion(forma.sedzona,"- Zona")
        validarSeleccion(forma.sedjorcodjor,"- Jornada (debe seleccionar por lo menos uno)")
        validarSeleccion(forma.sedjornivcodnivel,"- Niveles por jornada (debe seleccionar por lo menos uno)")
        if (forma.sedinternet_.checked){
          validarSeleccion(forma.sedinternettipo,"- Tipo de conexión")
          validarCampo(forma.sedinternetprov, "- Proveedor de internet", 1, 50)
        }
      }

      function hacerValidaciones_frm(forma){
        if(forma.cmd.value!='Nuevo')
          validarSeleccion(forma.r, "- Debe seleccionar un item")
      }

      function valorar(forma){
        if(forma.sedagua_.checked) forma.sedagua.value='1';
        else forma.sedagua.value='0';
        if(forma.sedluz_.checked) forma.sedluz.value='1';
        else forma.sedluz.value='0';
        if(forma.sedtel_.checked) forma.sedtel.value='1';
        else forma.sedtel.value='0';
        if(forma.sedalcantarillado_.checked) forma.sedalcantarillado.value='1';
        else forma.sedalcantarillado.value='0';
        if(forma.sedgas_.checked) forma.sedgas.value='1';
        else forma.sedgas.value='0';
        if(forma.sedinternet_.checked) forma.sedinternet.value='1';
        else forma.sedinternet.value='0';
      }

      function habilitar2(forma){
        if (forma.sedinternet_.checked){
          for (var i = 0; i < forma.sedinternettipo.length; i++)
          forma.sedinternettipo[i].disabled=false;
          forma.sedinternetprov.disabled=false;
        }else{
          for (var i = 0; i < forma.sedinternettipo.length; i++){
            forma.sedinternettipo[i].checked=false;
            forma.sedinternettipo[i].disabled=true;
          }
          forma.sedinternetprov.value=" ";
          forma.sedinternetprov.disabled=true;
        }
      }
        function habilitar(forma){
        if(forma.sedjornivcodnivel && forma.sedjorcodjor){
        var n=Math.round(forma.sedjornivcodnivel.length/forma.sedjorcodjor.length);
          for (var i = 0; i < forma.sedjorcodjor.length; i++){
            if (forma.sedjorcodjor[i].checked){
              for (var j = (i*n); j < ((i+1)*n); j++){
                forma.sedjornivcodnivel[j].disabled=false;
              }
            }else{
              for (var j = (i*n); j < ((i+1)*n); j++){
                forma.sedjornivcodnivel[j].checked=false;
                forma.sedjornivcodnivel[j].disabled=true;
              }
            }
          }
        }
      }

      function lanzar(tipo){
          document.frmNuevo.tipo2.value=tipo;
          document.frmNuevo.action="<c:url value="/institucion/ControllerNuevoEdit.do"/>";
          document.frmNuevo.submit();
      }
      /*
			function guardar(tipo){
					if(validarForma(document.frmNuevo)){
						document.frmNuevo.tipo2.value=tipo;
						document.frmNuevo.cmd.value="Guardar";
						document.frmNuevo.submit();
					}
				}
			*/
      function cancelar(){
        //if (confirm('¿Desea cancelar la inserción de la sede ?')){
          document.frmNuevo.cmd.value="Cancelar";
          document.frmNuevo.submit();
        //}
      }
      
      function editar(tipo,nombre,m,n,o,p){
        if(document.frm.cmd.value=='Eliminar'){
            if(!confirm("¿Realmente desea eliminarlo?")){
            	return;
            }
        }
        document.frm.tipo2.value=tipo;
        document.frm.cmd.value=nombre;
        document.frm.r.value=m;
				document.frm.nombresede_.value=n;
				document.frm.daneinst_.value=o;
				document.frm.danesede_.value=p;
        document.frm.submit();
      }
      
			function ponerSeleccionado(){
				if(document.frmInst){
					if(document.frmInst.id){
						if(document.frmInst.id.length){
							for(var i=0;i<document.frmInst.id.length;i++){
								if(document.frmInst.id[i].value==document.frmNuevo.ELHPSELECCIONADO.value){
									document.frmInst.id[i].checked=true;
									document.frmInst.id[i].focus();
								}
							}
						}else{
							if(document.frmInst.id.value==document.frmNuevo.ELHPSELECCIONADO.value){
								document.frmInst.id.checked=true;;
								document.frmInst.id.focus();
							}
						}
					}
				}
			}
      //-->
    </script>
  <%@include file="../mensaje.jsp"%>
  <font size="1">
  <c:if test="${!empty sessionScope.filtroSedes}">
  <center>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="95%" bordercolor="#F0F0F0"><tr><td>
	<div style="width:100%;height:100px;overflow:auto;">
  <form method="post" name="frm" onSubmit=" return validarForma(frm)" action='<c:url value="/institucion/ControllerNuevoEdit.do"/>'>
  <input type="hidden" name="tipo2" value="2">
  <input type="hidden" name="cmd" value=""><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
	<!--para comunicacion con matriculas-->
	<input type="hidden" name="r" value=""><input type="hidden" name="danesede_" value=""><input type="hidden" name="daneinst_" value=""><input type="hidden" name="nombresede_" value="">
    <table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
			<caption>LISTA DE SEDES</caption>
      <tr>
				<th class="EncabezadoColumna" width="5%" align="center">&nbsp;</th>
				<th class="EncabezadoColumna" width="15%"><b>Código</b></th>
				<th class="EncabezadoColumna" width="50%"><b>Nombre</b></th>
			</tr>
      <c:forEach begin="0" items="${sessionScope.filtroSedes}" var="fila"  varStatus="st">
      <tr>
				<td class='Fila<c:out value="${st.count%2}"/>'><a href='javaScript:editar(2,"Editar",<c:out value="${fila[0]}"/>,"<c:out value="${fila[1]}"/>",<c:out value="${fila[2]}"/>,<c:out value="${fila[3]}"/>);'><img border='0' src="../etc/img/editar.png" width='15' height='15'></a></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[0]}"/></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
			</tr>
    </c:forEach></table>
  </form>
  </div>
	</td></tr></table>
  </center>
  </c:if>
  <form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/institucion/NuevoGuardar.jsp"/>">
  <table border="0" align="center" bordercolor="#FFFFFF" width="100%">
    <tr>
      <td width="45%" bgcolor="#FFFFFF">
        <input  class='boton'  name="cmd1" type="button" value="Guardar" onClick="guardar(2)" style="display:none">
      </td>
    </tr>
  </table>
<!--//////////////////-->
  <!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoInstitucion.inscoddane}"/>">
  <input type="hidden" name="tipo2" value="2">
  <input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
  <input type="hidden" name="sedagua" value='<c:out value="${sessionScope.nuevoSede.sedagua}"/>'>
  <input type="hidden" name="sedluz" value='<c:out value="${sessionScope.nuevoSede.sedluz}"/>'>
  <input type="hidden" name="sedtel" value='<c:out value="${sessionScope.nuevoSede.sedtel}"/>'>
  <input type="hidden" name="sedalcantarillado" value='<c:out value="${sessionScope.nuevoSede.sedalcantarillado}"/>'>
  <input type="hidden" name="sedgas" value='<c:out value="${sessionScope.nuevoSede.sedgas}"/>'>
  <input type="hidden" name="sedinternet" value='<c:out value="${sessionScope.nuevoSede.sedinternet}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
      <tr height="1">
        <td width="10" bgcolor="#FFFFFF">&nbsp;</td>
        <td rowspan="2" width="600" bgcolor="#FFFFFF"><script>if(fichaBasico==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Inf Básica"  height="26" border="0"></a>');if(fichaSede==1)document.write('<img src="../etc/img/tabs/sedes_1.gif" alt="Sedes"  height="26" border="0">');if(fichaEspacio==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/espacios_fisicos_0.gif" alt="Espacios"  height="26" border="0"></a>');if(fichaTransporte==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/transporte_0.gif" alt="Transporte"  height="26" border="0"></a>');if(fichaCafeteria==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/cafeteria_0.gif" alt="Cafeteria"  height="26" border="0"></a>');if(fichaConflicto==1)document.write('<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/conflicto_escolar_0.gif" alt="Conflicto escolar" border="0"  height="26"></a>');</script></td>
      </tr>
  </table>
    <!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
                <TABLE width="100%" border='0' cellpadding="3" cellSpacing="0">
                  <tr>
                    <td><span class="style2">*</span> Tipo de sede: </td>
                    <td>
                      <input type="radio" name="sedtipo" value="1" <c:if test="${sessionScope.nuevoSede.sedtipo== '1'}">CHECKED</c:if>>Principal&nbsp;&nbsp;&nbsp;
                      <input type="radio" name="sedtipo" value="2" <c:if test="${sessionScope.nuevoSede.sedtipo== '2'}">CHECKED</c:if>>Otra sede
                    </td>
                    <td>Código DANE anterior: </td>
                    <td><input type="text" name="sedcoddaneanterior"  maxlength="12" value='<c:out value="${sessionScope.nuevoSede.sedcoddaneanterior}"/>' onKeyPress='return acepteNumeros(event)'></td>
                  </tr>
                  <tr>
                    <td><span class="style2">*</span> Nombre:</td>
                    <td><input type="text" name="sednombre"  maxlength="60" value='<c:out value="${sessionScope.nuevoSede.sednombre}"/>'></td>
                    <td><span class="style2">*</span> Zona:</td>
                    <td>
                      <input type="radio" name="sedzona" value="1" <c:if test="${sessionScope.nuevoSede.sedzona== '1'}">CHECKED</c:if>>Urbana&nbsp;&nbsp;&nbsp;
                      <input type="radio" name="sedzona" value="2" <c:if test="${sessionScope.nuevoSede.sedzona== '2'}">CHECKED</c:if>>Rural
                    </td>
                  </tr>
                  <tr>
                    <td>Vereda: </td>
                    <td><input type="text" name="sedvereda"  maxlength="60" value='<c:out value="${sessionScope.nuevoSede.sedvereda}"/>'></td>
                    <td>Barrio:</td>
                    <td><input type="text" name="sedbarrio"  maxlength="60" value='<c:out value="${sessionScope.nuevoSede.sedbarrio}"/>'></td>
                  </tr>
                  <tr>
                    <td>Resguardo:</td>
                    <td><input type="text" name="sedresguardo"  maxlength="60" value='<c:out value="${sessionScope.nuevoSede.sedresguardo}"/>'></td>
                    <td>Dirección:</td>
                    <td><input type="text" name="seddireccion"  maxlength="60" value='<c:out value="${sessionScope.nuevoSede.seddireccion}"/>'></td>
                  </tr>
                  <tr>
                    <td>Teléfono:</td>
                    <td><input type="text" name="sedtelefono"  maxlength="60" value='<c:out value="${sessionScope.nuevoSede.sedtelefono}"/>'></td>
                    <td>Fax:</td>
                    <td><input type="text" name="sedfax"  maxlength="60" value='<c:out value="${sessionScope.nuevoSede.sedfax}"/>'></td>
                  </tr>
                <tr>
                  <td>Correo electrónico:</td>
                  <td ><input name="sedcorreo" type="text" maxlength="50" value='<c:out value="${sessionScope.nuevoSede.sedcorreo}"/>' ></td>
                </tr>
                <tr>
                <td colspan='2'  valign='top'>
                <TABLE border='1' width="100%" cellpadding="0" cellSpacing="0" bordercolor="#FFFFFF">
                  <tr>
                    <td><span class="style2">*</span> <b>Jornadas:</b></td>
                    <td><span class="style2">*</span> <b>Niveles:</b></td>
                  </tr>
                    <c:forEach begin="0" items="${sessionScope.filtroJornadasInstitucion}" var="fila"  varStatus="st">
                    <c:set var="z" value="${fila[0]}" scope="request"/>
                  <tr><td>
                    <input type='checkbox' name='sedjorcodjor' onClick='habilitar(document.frmNuevo)' value='<c:out value="${fila[0]}"/>' <%=nuevoSede.seleccion((String)request.getAttribute("z"))%>><c:out value="${fila[1]}"/>
                    </td><td>
                    <c:forEach begin="0" items="${sessionScope.filtroNivelesInstitucion}" var="fila2" varStatus="st2">
                      <c:set var="w" value="${fila2[0]}" scope="request"/>
                      <input type='checkbox' name='sedjornivcodnivel' disabled value='<c:out value="${fila[0]}"/>|<c:out value="${fila2[0]}"/>' <%=nuevoSede.seleccion2((String)request.getAttribute("z")+"|"+(String)request.getAttribute("w"))%>>
                      <c:out value="${fila2[1]}"/><br></c:forEach>
                  </td></tr></c:forEach>
                </table>
                </td>
                <td colspan='2' valign='top'>
                <TABLE width="100%" cellpadding="0" cellSpacing="0">
                  <tr><td colspan='3'><b>Servicios públicos con los que cuenta:</b></td></tr>
                  <tr>
                  <td width="33%"><input type='checkbox' name='sedagua_' onClick='valorar(document.frmNuevo)' value='1' <c:if test="${sessionScope.nuevoSede.sedagua== '1'}">CHECKED</c:if>>Agua</td>
                  <td width="33%"><input type='checkbox' name='sedluz_' onClick='valorar(document.frmNuevo)' value='1' <c:if test="${sessionScope.nuevoSede.sedluz== '1'}">CHECKED</c:if>>Luz</td>
                  <td width="33%"><input type='checkbox' name='sedtel_' onClick='valorar(document.frmNuevo)' value='1' <c:if test="${sessionScope.nuevoSede.sedtel== '1'}">CHECKED</c:if>>Telefono</td>
                  </tr>
                  <tr>
                  <td><input type='checkbox' name='sedalcantarillado_'  onClick='valorar(document.frmNuevo)' value='1' <c:if test="${sessionScope.nuevoSede.sedalcantarillado== '1'}">CHECKED</c:if>>Alcantarillado</td>
                  <td><input type='checkbox' name='sedgas_'  onClick='valorar(document.frmNuevo)' value='1' <c:if test="${sessionScope.nuevoSede.sedgas== '1'}">CHECKED</c:if>>gas</td>
                  <td><input type='checkbox' name='sedinternet_' onClick='javaScript:habilitar2(document.frmNuevo);valorar(document.frmNuevo);' value='1' <c:if test="${sessionScope.nuevoSede.sedinternet== '1'}">CHECKED</c:if>>Internet</td>
                  </tr>
                  <tr><td colspan='3'><b>Tipo de conexión a internet (solo si tiene internet):</b></td></tr>
                  <tr>
                  <td><input type='radio' name='sedinternettipo' value='1' <c:if test="${sessionScope.nuevoSede.sedinternettipo=='1'}">CHECKED</c:if>>Módem o ISDN</td>
                  <td><input type='radio' name='sedinternettipo' value='2' <c:if test="${sessionScope.nuevoSede.sedinternettipo=='2'}">CHECKED</c:if>>Banda ancha</td>
                  <td><input type='radio' name='sedinternettipo' value='3' <c:if test="${sessionScope.nuevoSede.sedinternettipo=='3'}">CHECKED</c:if>>Inalámbrica</td>
                  </tr>
                  <tr>
                  <td><input type='radio' name='sedinternettipo' value='4' <c:if test="${sessionScope.nuevoSede.sedinternettipo=='4'}">CHECKED</c:if>>otro</td>
                  </tr>
                  <tr><td colspan='3'><b>Nombre del proveedor de internet (solo si tiene internet):</b></td></tr>
                  <tr>
                  <td colspan='3'><input type='text' maxlength='50' size='50' name='sedinternetprov' value='<c:out value="${sessionScope.nuevoSede.sedinternetprov}"/>'></td>
                  </tr>
                  </table>
                </td>
                </tr>
                </TABLE>
  </font>
</form>
<script>
habilitar(document.frmNuevo);habilitar2(document.frmNuevo);
<c:if test="${sessionScope.nuevoInstitucion.estado=='1'}">inhabilitarFormulario();</c:if>
ponerSeleccionado();
</script>