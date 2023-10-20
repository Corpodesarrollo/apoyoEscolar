<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
<jsp:useBean id="nuevoInstitucion" class="siges.institucion.beans.Institucion" scope="session"/>
<jsp:useBean id="nuevoJornada" class="siges.institucion.beans.Jornada" scope="session"/><jsp:setProperty name="nuevoJornada" property="*"/>
<jsp:useBean id="nuevoSede" class="siges.institucion.beans.Sede" scope="session"/><jsp:setProperty name="nuevoSede" property="*"/>
<jsp:useBean id="nuevoEspacio" class="siges.institucion.beans.Espacio" scope="session"/><jsp:setProperty name="nuevoEspacio" property="*"/>
<jsp:useBean id="nuevoNivel" class="siges.institucion.beans.Nivel" scope="session"/><jsp:setProperty name="nuevoNivel" property="*"/>  
<jsp:useBean id="nuevoTransporte" class="siges.institucion.beans.Transporte" scope="session"/><jsp:setProperty name="nuevoTransporte" property="*"/>
<jsp:useBean id="nuevoCafeteria" class="siges.institucion.beans.Cafeteria" scope="session"/><jsp:setProperty name="nuevoCafeteria" property="*"/>
<%pageContext.setAttribute("filtroDepartamento",Recursos.recurso[Recursos.DEPARTAMENTO]);
pageContext.setAttribute("filtroMunicipio",Recursos.recurso[Recursos.LOCALIDAD]);
pageContext.setAttribute("filtroCalendario",Recursos.recurso[Recursos.CALENDARIO]);
pageContext.setAttribute("filtroPropiedad",Recursos.recurso[Recursos.PROPIEDAD]);
pageContext.setAttribute("filtroDiscapacidadInstitucion",Recursos.recurso[Recursos.DISCAPACIDADINSTITUCION]);
pageContext.setAttribute("filtroIdioma",Recursos.recurso[Recursos.IDIOMA]);
pageContext.setAttribute("filtroAsociacion",Recursos.recurso[Recursos.ASOCIACION]);
pageContext.setAttribute("filtroTarifa",Recursos.recurso[Recursos.TARIFA]);
pageContext.setAttribute("filtroJornada",Recursos.recurso[Recursos.JORNADA]);
pageContext.setAttribute("filtroNivel",Recursos.recurso[Recursos.NIVEL]);
pageContext.setAttribute("filtroMetodologia",Recursos.recurso[Recursos.METODOLOGIA]);%>
<%@include file="../parametros.jsp"%>
    <script languaje='javaScript'>
    <!--
			var fichaBasico=1;
			var fichaSede=1;
			var fichaEspacio=1;
			var fichaTransporte=0;
			var fichaCafeteria=0;
			var fichaConflicto=0;
      var metod='';
      var nav4=window.Event ? true : false;
      function acepteNumeros(eve){
        var key=nav4?eve.which :eve.keyCode;
        return(key<=13 || (key>=48 && key<=57));
      }
      function hacerValidaciones_frmNuevo(forma){
        validarCampo(forma.inscoddane, "- Codigo DANE", 1, 12)
        validarLista(forma.inscoddepto,"- Departamento",1)
        validarLista(forma.inscodmun,"- Municipio",1)
        validarCampo(forma.insnombre, "- Nombre", 1, 60)
        //validarSeleccion(forma.inssector,"- Sector (debe seleccionar por lo menos uno)")
        validarLista(forma.inszona,"- Zona",1)
        validarLista(forma.inscalendario,"- Calendario",1)
        if(forma.inscalendario.options[3].selected)
          validarCampo(forma.inscalendariootro, "- otro calendario", 1, 20)
        if(forma.rector[0].checked){
          validarCampo(forma.insrectornombre, "- Nombre del rector", 1, 60)
          validarCampo(forma.insrectorcc, "- Identificación del rector", 1, 12)
        }
        if(forma.representante[0].checked){
          validarCampo(forma.insreplegalnombre, "- Nombre del representante", 1, 60)
          validarCampo(forma.insreplegalcc, "- Identificación del representante", 1, 12)
        }
        //jornadas
        validarSeleccion(forma.jorcodigo,"- Jornada (debe seleccionar por lo menos uno)")
        //niveles
        validarSeleccion(forma.nivcodigo,"- Nivel académico (debe seleccionar por lo menos uno)")
        for (var i = 0; i < forma.nivcodigo.length; i++) {
          if (forma.nivcodigo[i].checked){
            //validarCampo(forma.nivnombre[i], "- Nombre del nivel "+(i+1), 1, 20)
            //if(metod=='1')
              //validarLista(forma.nivcodmetod[i],"- Metodologia del nivel "+(i+1),1)
          }
        }
        validarLista(forma.insmetodologia,"- Metodologia por",1)
        validarLista(forma.insnivellogro,"- Nivel de logros por",1)
      }

      function metodologia(){
        if(document.frmNuevo.insmetodologia.options[0].selected){
          document.frmNuevo.inscodmetodologia.selectedIndex=0;
          document.frmNuevo.inscodmetodologia.disabled=true;
          metod='';
        }
        if(document.frmNuevo.insmetodologia.options[1].selected){
          document.frmNuevo.inscodmetodologia.selectedIndex=0;
          document.frmNuevo.inscodmetodologia.disabled=false;
          document.frmNuevo.inscodmetodologia.focus;
          metod='';
        }
        if(document.frmNuevo.insmetodologia.options[2].selected){
          document.frmNuevo.inscodmetodologia.selectedIndex=0;
          document.frmNuevo.inscodmetodologia.disabled=true;
          metod='1';
        }
        if(document.frmNuevo.insmetodologia.options[3].selected){
          document.frmNuevo.inscodmetodologia.selectedIndex=0;
          document.frmNuevo.inscodmetodologia.disabled=true;
          metod='';
        }
      }

      function habilitar(forma){
        for (var i = 0; i < forma.nivcodigo.length; i++) {
          if (forma.nivcodigo[i].checked){
            //forma.nivnombre[i].disabled=false;
            //if(metod=='1')
              //forma.nivcodmetod[i].disabled=false;
            //else
              //forma.nivcodmetod[i].disabled=true;
            //forma.nivorden[i].disabled=false;
          }else{
            //forma.nivnombre[i].value='';
            //forma.nivorden[i].value='';
            //forma.nivcodmetod[i].selectedIndex=0;;
            //forma.nivnombre[i].disabled=true;
            //forma.nivcodmetod[i].disabled=true;
            //forma.nivorden[i].disabled=true;
          }
        }
      }
      function lanzar(tipo){
        document.frmNuevo.tipo2.value=tipo;
        document.frmNuevo.action="<c:url value="/institucion/ControllerNuevoEdit.do"/>";
        document.frmNuevo.submit();
      }
      function guardar(tipo){
        if(validarForma(document.frmNuevo)){
          document.frmNuevo.tipo2.value=tipo;
          document.frmNuevo.cmd.value="Guardar";
          document.frmNuevo.submit();
        }
      }
      function cancelar(){
        //if (confirm('¿Desea cancelar la inserción de la información básica del colegio?')){
           document.frmNuevo.cmd.value="Cancelar";
                    document.frmNuevo.submit();
        //}
      }

      function cambioSelect(n){
        if(n==1){
          if(document.frmNuevo.inscalendario.options[document.frmNuevo.inscalendario.length-1].selected){
            document.frmNuevo.inscalendariootro.disabled=false;
            document.frmNuevo.inscalendariootro.focus();
          }else{
            document.frmNuevo.inscalendariootro.value=" ";
            document.frmNuevo.inscalendariootro.disabled=true;
          }
        }
		/*
        if(n==2){
          if(document.frmNuevo.insasocprivada.options[document.frmNuevo.insasocprivada.length-1].selected){
            document.frmNuevo.insasocprivadaotro.disabled=false;
            document.frmNuevo.insasocprivadaotro.focus();
          }else{
            document.frmNuevo.insasocprivadaotro.value=" ";
            document.frmNuevo.insasocprivadaotro.disabled=true;
          }
        }
		*/
      }

    function habilitarChk(tipo){
      if(tipo==1){
        document.frmNuevo.insrectornombre.disabled= false;
        document.frmNuevo.insrectorcc.disabled= false;
        document.frmNuevo.insrectoranoposesion.disabled= false;
        document.frmNuevo.insrectortel.disabled= false;
        document.frmNuevo.insrectorcorreo.disabled= false;
      }
      if(tipo==2){
        document.frmNuevo.insreplegalnombre.disabled= false;
        document.frmNuevo.insreplegalcc.disabled= false;
        document.frmNuevo.insreplegaltel.disabled= false;
        document.frmNuevo.insreplegalcorreo.disabled= false;
      }
    }
    function inhabilitarChk(tipo){
      if(tipo==1){
        document.frmNuevo.insrectornombre.disabled= true;
        document.frmNuevo.insrectornombre.value=' ' ;
        document.frmNuevo.insrectoranoposesion.selectedIndex=0;
        document.frmNuevo.insrectoranoposesion.disabled= true;
        document.frmNuevo.insrectorcc.disabled= true;
        document.frmNuevo.insrectorcc.value= ' ';
        document.frmNuevo.insrectortel.disabled= true;
        document.frmNuevo.insrectortel.value= ' ';
        document.frmNuevo.insrectorcorreo.disabled= true;
        document.frmNuevo.insrectorcorreo.value= ' ';
      }
      if(tipo==2){
        document.frmNuevo.insreplegalnombre.disabled= true;
        document.frmNuevo.insreplegalnombre.value=' ' ;
        document.frmNuevo.insreplegalcc.disabled= true;
        document.frmNuevo.insreplegalcc.value= ' ';
        document.frmNuevo.insreplegaltel.disabled= true;
        document.frmNuevo.insreplegaltel.value= ' ';
        document.frmNuevo.insreplegalcorreo.disabled= true;
        document.frmNuevo.insreplegalcorreo.value= ' ';
      }
    }

			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
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
				borrar_combo(combo_hijo);
        <c:if test="${!empty filtroDepartamento && !empty filtroMunicipio}">var Padres = new Array();
          <c:forEach begin="0" items="${filtroDepartamento}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
          <c:forEach begin="0" items="${filtroMunicipio}" var="fila2">
          <c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoInstitucion.inscodmun== fila2[0]}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if>
					</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
          var niv=combo_padre.options[combo_padre.selectedIndex].value;//TRAER DEL PRMER COMBO LA OPCION ESCOGIDA PARA TRAER EL ARRAY DE ESA LOCALIDAD
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
  <%@include file="../mensaje.jsp"%>
  <form name="f" target='1' action='<c:url value="/institucion/ControllerFiltroSave.do"/>' ></form>
  <form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action='<c:url value="/institucion/NuevoGuardar.jsp"/>'>
  <table border="0" align="center" bordercolor="#FFFFFF" width="100%">
    <tr>
      <td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(1)" style="display:none">
      </td>
    </tr>
  </table>
<!--//////////////////-->
  <!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoInstitucion.inscoddane}"/>">
  <input type="hidden" name="tipo2" value="1">
  <input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
    <tr height="1">
      <td width="10" bgcolor="#FFFFFF">&nbsp;</td>
      <td rowspan="2" width="600" bgcolor="#FFFFFF"><script>if(fichaBasico==1)document.write('<img src="../etc/img/tabs/datos_basicos_1.gif" alt="Inf Básica" border="0"  height="26">');if(fichaSede==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/sedes_0.gif" alt="Sedes"  height="26" border="0"></a>');if(fichaEspacio==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/espacios_fisicos_0.gif" alt="Espacios físicos"  height="26" border="0"></a>');if(fichaTransporte==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/transporte_0.gif" alt="Transporte"  height="26" border="0"></a>');if(fichaCafeteria==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/cafeteria_0.gif" alt="Cafeteria"  height="26" border="0"></a>');if(fichaConflicto==1)document.write('<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/conflicto_escolar_0.gif" alt="Conflicto escolar" border="0"  height="26"></a>');</script></td>
      </tr>
  </table>
    <!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
                <TABLE width="100%" cellpadding="3" cellSpacing="0">
                  <tr><th colspan='4'>DATOS BÁSICOS</th></tr>
                  <tr><input type="hidden" name=""  maxlength="20" value='<c:out value="${sessionScope.nuevoInstitucion.inscodigo}"/>'>                    
                    <td width="15%">Código DANE: </td>
                    <td width="30%"><input type="text" name="inscoddane"  maxlength="12" value='<c:out value="${sessionScope.nuevoInstitucion.inscoddane}"/>' onKeyPress='return acepteNumeros(event)'></td>
                      <select name="inscoddepto" style='display:none;' onChange='filtro(document.frmNuevo.inscoddepto, document.frmNuevo.inscodmun)' style='width:150px;'>
                          <option value="-1">--seleccione uno--</option><c:forEach begin="0" items="${filtroDepartamento}" var="fila"><option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoInstitucion.inscoddepto== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
                      </select>
                    <td><span class="style2">*</span>Localidad:</td>
                    <td><select name="inscodmun" style='width:150px;'><option value="-1">--seleccione uno--</option></select></td>
                  </tr>
                  <tr>
                    <td><span class="style2">*</span>Nombre:</td>
                    <td colspan='3'>
                    	<textarea name="insnombre" cols="80" rows="2"><c:out value="${sessionScope.nuevoInstitucion.insnombre}"/></textarea>
											
                    </td>
                  </tr>
                  <tr>
                    <td><span class="style2">*</span>Sector:</td>
                    <td>
                    <input type="radio" name="inssector" value="1" <c:if test="${sessionScope.nuevoInstitucion.inssector== '1'}">CHECKED</c:if>>Oficial&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="inssector" value="2" <c:if test="${sessionScope.nuevoInstitucion.inssector== '2'}">CHECKED</c:if>>Privado
                    <td><span class="style2">*</span>Zona:</td>
                    <td>
                      <select name="inszona" style='width:150px;'>
                        <option value="-1">--seleccione una--</option>
                        <option value="1" <c:if test="${sessionScope.nuevoInstitucion.inszona== '1'}">SELECTED</c:if>>Urbana</option>
                        <option value="2" <c:if test="${sessionScope.nuevoInstitucion.inszona== '2'}">SELECTED</c:if>>Rural</option>
                        <option value="3" <c:if test="${sessionScope.nuevoInstitucion.inszona== '3'}">SELECTED</c:if>>Urbana y Rural</option>
                      </select>
                    </td>
                </tr>
                <tr>
                  <td><span class="style2">*</span>Calendario:</td>
                  <td>
                    <select name="inscalendario" onChange="cambioSelect(1)" style='width:150px;'>
                      <option value="-1">--seleccione uno--</option>
                        <c:forEach begin="0" items="${filtroCalendario}" var="fila">
                        <option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.nuevoInstitucion.inscalendario==fila[0]}">SELECTED</c:if>>
                        <c:out value="${fila[1]}"/></option>
                        </c:forEach>
                    </select>
									</td>
									<td>Rector:</td>
                  <td><input name="insrectornombre" type="text" size="35" value='<c:out value="${sessionScope.nuevoInstitucion.insrectornombre}"/>'>
                  </td>
                </tr>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellSpacing="10">
                <tr><th colspan='4'>INFORMACIÓN ADICIONAL</th></tr>
                  <tr>
                    <td>propiedad jurídica:</td>
                    <td>
                      <select name="inspropiedad" style='width:150px;'>
                        <option value="-1">--seleccione uno--</option>
                        <c:forEach begin="0" items="${filtroPropiedad}" var="fila">
                        <option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.nuevoInstitucion.inspropiedad==fila[0]}">SELECTED</c:if>>
                        <c:out value="${fila[1]}"/></option>
                        </c:forEach>
                      </select>
                    </td>
                    <td >Número de sedes:</td>
                    <td >
                    <input name="insnumsedes" type="text" size='5' maxlength="5" value='<c:out value="${sessionScope.nuevoInstitucion.insnumsedes}"/>'  onKeyPress='return acepteNumeros(event)'>
                    </td>
                  </tr>
                  <tr>
                    <td>Género:</td>
                    <td >
                      <select name="insgenero">
                        <option value="-1">--seleccione uno--</option>
                        <option value="1" <c:if test="${sessionScope.nuevoInstitucion.insgenero== '1'}">SELECTED</c:if>>Masculino</option>
                        <option value="2" <c:if test="${sessionScope.nuevoInstitucion.insgenero== '2'}">SELECTED</c:if>>Femenino</option>
                        <option value="3" <c:if test="${sessionScope.nuevoInstitucion.insgenero== '3'}">SELECTED</c:if>>Mixto</option>
                      </select>
                    </td>
                    <td>Subsidio:</td>
                    <td>
                      <input type="radio" name="inssubsidio" value="S" <c:if test="${sessionScope.nuevoInstitucion.inssubsidio== 'S'}">CHECKED</c:if>>si
                      <input type="radio" name="inssubsidio" value="N" <c:if test="${sessionScope.nuevoInstitucion.inssubsidio== 'N'}">CHECKED</c:if>>no
                    </td>
                  </tr>
                  <tr>
                    <td>Discapacidades:</td>
                    <td >
                      <select name="insdiscapacidad">
                        <option value="-1">--seleccione uno--</option>
                        <c:forEach begin="0" items="${filtroDiscapacidadInstitucion}" var="fila">
                        <option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.nuevoInstitucion.insdiscapacidad==fila[0]}">SELECTED</c:if>>
                        <c:out value="${fila[1]}"/></option>
                        </c:forEach>
                      </select>
                    </td>
                    <td>Capacidades excepcionales:</td>
                    <td>
                      <input type="radio" name="inscapexcepcional" value="S" <c:if test="${sessionScope.nuevoInstitucion.inscapexcepcional== 'S'}">CHECKED</c:if>>si
                      <input type="radio" name="inscapexcepcional" value="N" <c:if test="${sessionScope.nuevoInstitucion.inscapexcepcional== 'N'}">CHECKED</c:if>>no
                    </td>
                  </tr>
                  <tr>
                    <td>Étnia:</td>
                    <td>
                      <input type="radio" name="insetnia" value="S" <c:if test="${sessionScope.nuevoInstitucion.insetnia== 'S'}">CHECKED</c:if>>si
                      <input type="radio" name="insetnia" value="N" <c:if test="${sessionScope.nuevoInstitucion.insetnia== 'N'}">CHECKED</c:if>>no
                    </td>
										<td>Página Web:</td>
										<td>
										<input name="inspaginaweb" type="text" size="35" value='<c:out value="${sessionScope.nuevoInstitucion.inspaginaweb}"/>'>
										</td>										
                  </tr>
                <tr><th colspan='4'>INFORMACION COMPLEMENTARIA</th></tr>
                  <tr>
                    <td>Idioma:</td>
                    <td>
                      <select name="insidioma" style='width:150px;'>
                        <option value="-1">--seleccione uno--</option>
                        <c:forEach begin="0" items="${filtroIdioma}" var="fila">
                        <option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.nuevoInstitucion.insidioma==fila[0]}">SELECTED</c:if>>
                        <c:out value="${fila[1]}"/></option>
                        </c:forEach>
                      </select>
                    </td>
                    <td>Régimen de costos:</td>
                    <td>
                      <select name="insregimencostos" style='width:150px;'>
                        <option value="-1">--seleccione uno--</option>
                        <option value="1" <c:if test="${sessionScope.nuevoInstitucion.insregimencostos== '1'}">SELECTED</c:if>>Libertad vigilada</option>
                        <option value="2" <c:if test="${sessionScope.nuevoInstitucion.insregimencostos== '2'}">SELECTED</c:if>>Libertad regulada</option>
                        <option value="3" <c:if test="${sessionScope.nuevoInstitucion.insregimencostos== '3'}">SELECTED</c:if>>Régimen controlado</option>
                      </select>
                    </td>
                  </tr>
                  <tr>
                  <td>Rango de tarifa:</td>
                    <td>
                      <select name="instarifacostos" style='width:150px;'>
                        <option value="-1">--seleccione uno--</option>
                        <c:forEach begin="0" items="${filtroTarifa}" var="fila">
                        <option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.nuevoInstitucion.instarifacostos==fila[0]}">SELECTED</c:if>>
                        <c:out value="${fila[1]}"/></option>
                        </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td>Informe PEI:</td>
                    <td colspan='3'>
                      <textarea name="insinformepei" cols="80" rows="2"><c:out value="${sessionScope.nuevoInstitucion.insinformepei}"/></textarea>
                    </td>
                  </tr>
<!--////////////////////////////////////////////-->
                <tr><th colspan='4'>JORNADAS Y NIVELES</th></tr>
                <tr>
                  <td><span class="style2">*</span>Jornadas:</td>
                  <td colspan=1>
                    <c:forEach begin="0" items="${filtroJornada}" var="fila">
                      <c:set var="z" value="${fila[0]}" scope="request"/>
                      <input type='checkbox' name='jorcodigo' value="<c:out value="${fila[0]}"/>" <%=nuevoJornada.seleccion((String)request.getAttribute("z"))%>><c:out value="${fila[1]}"/>&nbsp&nbsp<br>
                    </c:forEach>
                  </td>
                  <td><span class="style2">*</span>Niveles:</td>
                  <td colspan=1>
                    <c:forEach begin="0" items="${filtroNivel}" var="fila">
                      <c:set var="z" value="${fila[0]}" scope="request"/>
                      <input type='checkbox' name='nivcodigo' value="<c:out value="${fila[0]}"/>" <%=nuevoNivel.seleccion((String)request.getAttribute("z"))%>><c:out value="${fila[1]}"/>&nbsp&nbsp<br>
                    </c:forEach>
                  </td>
                </tr>
                </TABLE>
<!--//////////////////////////////-->
</form>
<c:remove var="filtroDepartamento"/><c:remove var="filtroMunicipio"/><c:remove var="filtroLocalidad"/><c:remove var="filtroCalendario"/><c:remove var="filtroNucleo"/><c:remove var="filtroPropiedad"/><c:remove var="filtroDiscapacidadInstitucion"/><c:remove var="filtroIdioma"/><c:remove var="filtroAsociacion"/><c:remove var="filtroTarifa"/>
<script>
/*
if(!document.frmNuevo.rector[0].checked && !document.frmNuevo.rector[1].checked)
 document.frmNuevo.rector[0].checked=true;
if(document.frmNuevo.rector[0].checked)
 habilitarChk(1);
if(document.frmNuevo.rector[1].checked)
  inhabilitarChk(1);
if(!document.frmNuevo.representante[0].checked && !document.frmNuevo.representante[1].checked)
  document.frmNuevo.representante[0].checked=true;
if(document.frmNuevo.representante[0].checked)
  habilitarChk(2);
if(document.frmNuevo.representante[1].checked)
  inhabilitarChk(2);
*/
document.frmNuevo.inscoddepto.onchange();
habilitar(document.frmNuevo);
//cambioSelect(1);
//cambioSelect(2);
<c:if test="${sessionScope.nuevoInstitucion.estado=='1'}">inhabilitarFormulario();</c:if>
ponerSeleccionado();
</script>