<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
<%pageContext.setAttribute("filtroTipoEnfasis",Recursos.recurso[Recursos.TIPOENFASIS]);
pageContext.setAttribute("filtroTipoModalidad",Recursos.recurso[Recursos.TIPOMODALIDAD]);
pageContext.setAttribute("filtroTipoEspecialidad",Recursos.recurso[Recursos.TIPOESPECIALIDAD]);%>
<jsp:useBean id="fichaVO" class="siges.institucion.beans.FichaVO" scope="session"/>
<%@include file="../parametros.jsp"%>
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
      
      function ReportePDF(){
    		document.frmFiltro.key.value=10;
    		document.frmFiltro.submit();
    	}
      function ReporteXLS(){
  		document.frmFiltro.key.value=11;
  		document.frmFiltro.submit();
  	}
			
      //-->
    </script>
  <%@include file="../mensaje.jsp"%>
  <font size="1">
  <FORM ACTION='<c:url value="/Inicio.dos"/>' METHOD="POST" name='frmFiltro' target="_blank">
  <input type="hidden" name="key" value="5">
  </form>
  <form name="f" target='1' action="<c:url value="/institucion/ControllerFichaSave"/>" ></form>
  <form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/institucion/NuevoFichaGuardar.jsp"/>">
<!--//////////////////-->
<a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
    <input  class='boton' name="imprPDF" type="button" value="PDF" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:ReportePDF()" />&nbsp;<input  class='boton' name="imprXLS" type="button" value="EXCEL" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:ReporteXLS()" />
  <!-- FICHAS A MOSTRAR AL USUARIO -->
  <input type="hidden" name="tipo">
  <input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="height" VALUE='150'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
      <tr height="1">
        <td width="10" bgcolor="#FFFFFF">&nbsp;</td>
        <td rowspan="2" width="600" bgcolor="#FFFFFF">
					<script>
					if(fichaFicha1==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/ficha_tecnica1_0.gif" alt=""  height="26" border="0"></a>');
					if(fichaFicha2==1)document.write('<img src="../etc/img/tabs/ficha_tecnica2_1.gif" alt=""  height="26" border="0">');
					if(fichaFicha3==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/ficha_tecnica3_0.gif" alt=""  height="26" border="0"></a>');					
					</script>
				</td>
      </tr>
  </table>
    <!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->   
    
                <TABLE width="98%" cellpadding="3" cellSpacing="0"  align='center'>
								<caption>ESTADÍSTICAS DE DOCENTE</caption>
                  <tr>
                    <td width='100%'>TOTAL DOCENTES: &nbsp;&nbsp;<b><font style='COLOR:#006699;'><c:out value="${sessionScope.fichaVO.fichaTotDoc}"/></font></b></td>
									</tr>
                <tr><td><hr color='red'></td></tr>
                
                <tr>
                    <td>DOCENTES POR GÉNERO:<br>
                   <!-- 
											<TABLE width="100%" cellpadding="0" cellSpacing="0"  border=1>
													<tr>
														<th class="EncabezadoColumna">Sede - Jornada</th>
														<th class="EncabezadoColumna">&nbsp;M&nbsp;</th>
														<th class="EncabezadoColumna">&nbsp;F&nbsp;</th>
													</tr>
                          <c:forEach begin="0" items="${sessionScope.fichaVO.fichaDocXGen}" var="fila" varStatus="st">
													<tr>
														<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[0]}"/> - <c:out value="${fila[1]}"/></td>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></th>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></th>
													</tr>	
                          </c:forEach>
											</table>		
										</td>
                </tr>
                -->
                <tr>                
                <td>
                <TABLE width="100%" cellpadding="0" cellSpacing="0"  border=1>
                    <tr>						
                      <th class="EncabezadoColumna">Sede</th>
                      <th class="EncabezadoColumna">Jornada</th>
					  <th class="EncabezadoColumna">&nbsp;M&nbsp;</th>
					  <th class="EncabezadoColumna">&nbsp;F&nbsp;</th>
					 </tr>
                   <c:forEach begin="0" items="${sessionScope.fichaVO.listaSede}" var="sedeItem" varStatus="st">
					  <tr><td class='Fila<c:out value="${st.count%2}"/>'>
					      <c:out value="${sedeItem.sedNombre}"/>
					    </td>
					    <td>
					     <table border="0"  width="100%" >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem_" varStatus="st2">
					       <tr><td class='Fila<c:out value="${st2.count%2}"/>'  >
					         <c:out value="${jordItem_.jordNombre}"/>
					        </td>
					      </tr>
					      </c:forEach>
					      </table>
					    </td>
					      <td>
					     <table width="100%"  >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem_M" varStatus="st3">
					       <tr>
					       <td align="center"  class='Fila<c:out value="${st3.count%2}"/>'  ><c:out value="${jordItem_M.totalM}"/>
					        </td>
					        </tr>					        
					     </c:forEach>
					      </table>
					    </td>
					     <td>
					     <!-- Conteo femenino -->
					     <table width="100%"  >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem__F" varStatus="st4">
					       <tr>
					        <td align="center"  class='Fila<c:out value="${st4.count%2}"/>'   ><c:out value="${jordItem__F.totalF}"/>
					        </td>
					        </tr>					        
					     </c:forEach>
					     <tr>
					      </table>
					    </td>
					 </tr>	
                   </c:forEach>
                   <tr><th   colspan="2">Total</th><th><c:out value="${sessionScope.fichaVO.totalConsolGeneroF }"/></th><th><c:out value="${sessionScope.fichaVO.totalConsolGeneroM }"/></th></tr>
                  </table>
                 </td> 
                </tr>
                
                <tr><td><hr color='red'></td></tr>
                <tr>
                    <td>DOCENTES POR EDAD Y GÉNERO:<br>
					 <!-- 
					 					<TABLE width="100%" cellpadding="0" cellSpacing="0"  border=1>
													<tr>
														<th rowspan=2 class="EncabezadoColumna">Sede - Jornada</td>
														<th colspan=2 class="EncabezadoColumna">&nbsp;Entre 20 y 30&nbsp;</td>
														<th colspan=2 class="EncabezadoColumna">&nbsp;Entre 30 y 40&nbsp;</td>
														<th colspan=2 class="EncabezadoColumna">&nbsp;Entre 40 y 50&nbsp;</td>
														<th colspan=2 class="EncabezadoColumna">&nbsp;Mayores de 50&nbsp;</td>
													</tr>
													<tr>
														<th class="EncabezadoColumna">&nbsp;M&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;F&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;M&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;F&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;M&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;F&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;M&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;F&nbsp;</td>
													</tr>
                          <c:forEach begin="0" items="${sessionScope.fichaVO.fichaDocXEdad}" var="fila" varStatus="st">
													<tr>
														<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[0]}"/> - <c:out value="${fila[1]}"/></td>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></th>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></th>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></th>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[5]}"/></th>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[6]}"/></th>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[7]}"/></th>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[8]}"/></th>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[9]}"/></th>
													</tr>	
                          </c:forEach>
											</table>		
										</td>
                </tr>
                -->
                 <tr>                
                <td>
                <TABLE width="100%" cellpadding="0" cellSpacing="0"  border=1>
                 	<tr>
														<th rowspan=2 class="EncabezadoColumna">Sede</td>
														<th rowspan=2 class="EncabezadoColumna">Jornada</td>
														<th colspan=2 class="EncabezadoColumna">&nbsp;Entre 20 y 30&nbsp;</td>
														<th colspan=2 class="EncabezadoColumna">&nbsp;Entre 30 y 40&nbsp;</td>
														<th colspan=2 class="EncabezadoColumna">&nbsp;Entre 40 y 50&nbsp;</td>
														<th colspan=2 class="EncabezadoColumna">&nbsp;Mayores de 50&nbsp;</td>
													</tr>
													<tr>
														<th class="EncabezadoColumna">&nbsp;M&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;F&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;M&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;F&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;M&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;F&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;M&nbsp;</td>
														<th class="EncabezadoColumna">&nbsp;F&nbsp;</td>
													</tr>
                   <c:forEach begin="0" items="${sessionScope.fichaVO.listaSede}" var="sedeItem" varStatus="st">
					  <tr><td class='Fila<c:out value="${st.count%2}"/>'>
					      <c:out value="${sedeItem.sedNombre}"/>
					    </td>
					    <td>
					     <table border="0"  width="100%" >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem_" varStatus="st2">
					       <tr><td class='Fila<c:out value="${st2.count%2}"/>'  >
					         <c:out value="${jordItem_.jordNombre}"/>
					        </td>
					      </tr>
					      </c:forEach>
					      </table>
					    </td>
					      <td>
					     <table width="100%"  >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem_M" varStatus="st3">
					       <tr>
					       <td align="center"  class='Fila<c:out value="${st3.count%2}"/>'  ><c:out value="${jordItem_M.totalEdadM_20_30}"/>
					        </td>
					        </tr>					        
					     </c:forEach>
					      </table>
					    </td>
					     <td>
					     <!-- Conteo femenino -->
					     <table width="100%"  >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem__F" varStatus="st4">
					       <tr>
					        <td align="center"  class='Fila<c:out value="${st4.count%2}"/>'   ><c:out value="${jordItem__F.totalEdadF_20_30}"/>
					        </td>
					        </tr>					        
					     </c:forEach>
					     <tr>
					      </table>
					    </td>
					        <td>
					     <!-- Conteo femenino -->
					     <table width="100%"  >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem__F" varStatus="st4">
					       <tr>
					        <td align="center"  class='Fila<c:out value="${st4.count%2}"/>'   ><c:out value="${jordItem__F.totalEdadM_30_40}"/>
					        </td>
					        </tr>					        
					     </c:forEach>
					     <tr>
					      </table>
					    </td>
					        <td>
					     <!-- Conteo femenino -->
					     <table width="100%"  >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem__F" varStatus="st4">
					       <tr>
					        <td align="center"  class='Fila<c:out value="${st4.count%2}"/>'   ><c:out value="${jordItem__F.totalEdadF_30_40}"/>
					        </td>
					        </tr>					        
					     </c:forEach>
					     <tr>
					      </table>
					    </td>
					        <td>
					     <!-- Conteo femenino -->
					     <table width="100%"  >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem__F" varStatus="st4">
					       <tr>
					        <td align="center"  class='Fila<c:out value="${st4.count%2}"/>'   ><c:out value="${jordItem__F.totalEdadM_40_50}"/>
					        </td>
					        </tr>					        
					     </c:forEach>
					     <tr>
					      </table>
					    </td>
					        <td>
					     <!-- Conteo femenino -->
					     <table width="100%"  >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem__F" varStatus="st4">
					       <tr>
					        <td align="center"  class='Fila<c:out value="${st4.count%2}"/>'   ><c:out value="${jordItem__F.totalEdadF_40_50}"/>
					        </td>
					        </tr>					        
					     </c:forEach>
					     <tr>
					      </table>
					    </td>
					        <td>
					     <!-- Conteo femenino -->
					     <table width="100%"  >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem__F" varStatus="st4">
					       <tr>
					        <td align="center"  class='Fila<c:out value="${st4.count%2}"/>'   ><c:out value="${jordItem__F.totalEdadM_50_mas}"/>
					        </td>
					        </tr>					        
					     </c:forEach>
					     <tr>
					      </table>
					    </td>
					            <td>
					     <!-- Conteo femenino -->
					     <table width="100%"  >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem__F" varStatus="st4">
					       <tr>
					        <td align="center"  class='Fila<c:out value="${st4.count%2}"/>'   ><c:out value="${jordItem__F.totalEdadF_50_mas}"/>
					        </td>
					        </tr>					        
					     </c:forEach>
					     <tr>
					      </table>
					    </td>
					 </tr>	
                   </c:forEach>
                   <tr><th   colspan="2">Total</th><th><c:out value="${sessionScope.fichaVO.totalConsolEdadM_20_30 }"/></th><th><c:out value="${sessionScope.fichaVO.totalConsolEdadF_20_30 }"/></th><th><c:out value="${sessionScope.fichaVO.totalConsolEdadM_30_40 }"/></th><th><c:out value="${sessionScope.fichaVO.totalConsolEdadF_30_40 }"/></th><th><c:out value="${sessionScope.fichaVO.totalConsolEdadM_40_50 }"/></th><th><c:out value="${sessionScope.fichaVO.totalConsolEdadF_40_50 }"/></th><th><c:out value="${sessionScope.fichaVO.totalConsolEdadM_50_mas }"/></th><th><c:out value="${sessionScope.fichaVO.totalConsolEdadF_50_mas }"/></th></tr>
                  </table>
                 </td> 
                </tr>
                
                <tr><td><hr color='red'></td></tr>
                <tr>
                    <td>DOCENTES POR CATEGORIA EN EL ESCALAFÓN NACIONAL DOCENTE Y GÉNERO:<br>
										   <c:forEach begin="0" items="${sessionScope.fichaVO.fichaDocXEscalafon}" var="fila" varStatus="st">
											
											<p>Decreto No. 2277 de Septiembre de 1979</p>
											<TABLE width="100%" cellpadding="0" cellSpacing="0"  border=1>
											<tr><th class="EncabezadoColumna" colspan=34>Sede - Jornada</td></tr>
											<tr><th colspan=34><c:out value="${fila[0]}"/> - <c:out value="${fila[1]}"/></td></tr>
													<tr>
														<th colspan=2 class="Fila0">&nbsp;1&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;2&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;3&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;4&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;5&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;6&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;7&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;8&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;9&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;10&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;11&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;12&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;13&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;14&nbsp;</td>
													</tr>
													<tr>
														<th class='Fila0'>&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
													</tr>
													<tr>
														<th class='Fila1'><c:out value="${fila[22]}"/></th>
														<th class='Fila1'><c:out value="${fila[23]}"/></th>
														<th class='Fila1'><c:out value="${fila[24]}"/></th>
														<th class='Fila1'><c:out value="${fila[25]}"/></th>
														<th class='Fila1'><c:out value="${fila[26]}"/></th>
														<th class='Fila1'><c:out value="${fila[27]}"/></th>
														<th class='Fila1'><c:out value="${fila[28]}"/></th>
														<th class='Fila1'><c:out value="${fila[29]}"/></th>
														<th class='Fila1'><c:out value="${fila[30]}"/></th>
														<th class='Fila1'><c:out value="${fila[31]}"/></th>
														<th class='Fila1'><c:out value="${fila[32]}"/></th>
														<th class='Fila1'><c:out value="${fila[33]}"/></th>
														<th class='Fila1'><c:out value="${fila[34]}"/></th>
														<th class='Fila1'><c:out value="${fila[35]}"/></th>
														<th class='Fila1'><c:out value="${fila[36]}"/></th>
														<th class='Fila1'><c:out value="${fila[37]}"/></th>
														<th class='Fila1'><c:out value="${fila[38]}"/></th>
														<th class='Fila1'><c:out value="${fila[39]}"/></th>
														<th class='Fila1'><c:out value="${fila[40]}"/></th>
														<th class='Fila1'><c:out value="${fila[41]}"/></th>
														<th class='Fila1'><c:out value="${fila[42]}"/></th>
														<th class='Fila1'><c:out value="${fila[43]}"/></th>
														<th class='Fila1'><c:out value="${fila[44]}"/></th>
														<th class='Fila1'><c:out value="${fila[45]}"/></th>
														<th class='Fila1'><c:out value="${fila[46]}"/></th>
														<th class='Fila1'><c:out value="${fila[47]}"/></th>
														<th class='Fila1'><c:out value="${fila[48]}"/></th>
														<th class='Fila1'><c:out value="${fila[49]}"/></th>
														
													</tr>	
											</table><br>		
											
											
											<p>Decreto No. 1278 de 2002</p>
																						<TABLE width="100%" cellpadding="0" cellSpacing="0"  border=1>
													<tr>
														<th colspan=2 class="Fila0">&nbsp;A1&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;A2&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;A3&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;B1&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;B2&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;B3&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;C1&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;C2&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;C3&nbsp;</td>
														<th colspan=2 class="Fila0">&nbsp;D1&nbsp;</td>
													</tr>
													<tr>
														<th class='Fila0'>&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
														<th class="Fila0">&nbsp;M&nbsp;</td>
														<th class="Fila0">&nbsp;F&nbsp;</td>
													</tr>
													<tr>
														<th class='Fila1'><c:out value="${fila[2]}"/></th>
														<th class='Fila1'><c:out value="${fila[3]}"/></th>
														<th class='Fila1'><c:out value="${fila[4]}"/></th>
														<th class='Fila1'><c:out value="${fila[5]}"/></th>
														<th class='Fila1'><c:out value="${fila[6]}"/></th>
														<th class='Fila1'><c:out value="${fila[7]}"/></th>
														<th class='Fila1'><c:out value="${fila[8]}"/></th>
														<th class='Fila1'><c:out value="${fila[9]}"/></th>
														<th class='Fila1'><c:out value="${fila[10]}"/></th>
														<th class='Fila1'><c:out value="${fila[11]}"/></th>
														<th class='Fila1'><c:out value="${fila[12]}"/></th>
														<th class='Fila1'><c:out value="${fila[13]}"/></th>
														<th class='Fila1'><c:out value="${fila[14]}"/></th>
														<th class='Fila1'><c:out value="${fila[15]}"/></th>
														<th class='Fila1'><c:out value="${fila[16]}"/></th>
														<th class='Fila1'><c:out value="${fila[17]}"/></th>
														<th class='Fila1'><c:out value="${fila[18]}"/></th>
														<th class='Fila1'><c:out value="${fila[19]}"/></th>
														<th class='Fila1'><c:out value="${fila[20]}"/></th>
														<th class='Fila1'><c:out value="${fila[21]}"/></th>
													</tr>	
											</table><br>		
											
											
											
                      </c:forEach>
										</td>
                </tr>
                </TABLE>
                <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
                
  </font>
<!--//////////////////////////////-->
</form>