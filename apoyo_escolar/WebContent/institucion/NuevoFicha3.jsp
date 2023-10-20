<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
<%pageContext.setAttribute("filtroTipoEnfasis",Recursos.recurso[Recursos.TIPOENFASIS]);
pageContext.setAttribute("filtroTipoModalidad",Recursos.recurso[Recursos.TIPOMODALIDAD]);
pageContext.setAttribute("filtroTipoEspecialidad",Recursos.recurso[Recursos.TIPOESPECIALIDAD]);%>
<jsp:useBean id="fichaVO" class="siges.institucion.beans.FichaVO" scope="session"/>
<%@include file="../parametros.jsp"%>
<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
    
    



  <style type="text/css">
      .Fila_0{
		    background: #C9efef; 
				font-size: 10px;
		}

		.Fila_1{
		    background: #ebebeb; 
				font-size: 10px;
		}
		
		.table1{
	    FONT-FAMILY: Arial;
	    FONT-SIZE: 10px;
	    background: #d8d8d8;
    } 
    
 

  </style>


    <script languaje='javaScript'>
    <!--
			var tipoFicha=3;
			var fichaFicha1=1;
			var fichaFicha2=1;
			var fichaFicha3=1;
      var nav4=window.Event ? true : false;
			
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
			
      function lanzar(tipo){
          document.frmNuevo.tipo.value=tipo;
          document.frmNuevo.action="<c:url value="/institucion/ControllerFichaEdit.do"/>";
          document.frmNuevo.target = "_self";
          document.frmNuevo.submit();
      }
      
      function ReporteXLS(){
    		document.frmNuevo.key.value=9;
    		document.frmNuevo.action ='<c:url value="/Inicio.dos"/>';
    		document.frmNuevo.target = "_blank"; 
    		document.frmNuevo.submit();
    	}
			
      //-->
    </script>
  <%@include file="../mensaje.jsp"%>
  <font size="1">
  <form name="f" target='1' action="<c:url value="/institucion/ControllerFichaSave"/>" ></form>
  <form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/institucion/NuevoFichaGuardar.jsp"/>">
<!--//////////////////-->
  <!-- FICHAS A MOSTRAR AL USUARIO -->
  <input type="hidden" name="tipo">
  <input type="hidden" name="key">
  <input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="height" VALUE='150'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
      <tr height="1">
        <td width="10" bgcolor="#FFFFFF">&nbsp;</td>
        <td rowspan="2" width="600" bgcolor="#FFFFFF">
					<script>
					if(fichaFicha1==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/ficha_tecnica1_0.gif" alt=""  height="26" border="0"></a>');
					if(fichaFicha2==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/ficha_tecnica2_0.gif" alt=""  height="26" border="0"></a>');
					if(fichaFicha3==1)document.write('<img src="../etc/img/tabs/ficha_tecnica3_1.gif" alt=""  height="26" border="0">');
					</script>
				</td>
      </tr>
  </table>
    <!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
   <TABLE width="98%" cellpadding="3" cellSpacing="0" align='CENTER' >
   		<caption>REPORTE C600</caption>
   		<tr>
                    <td><a href="javaScript:ReporteXLS()">Generar Información para C600</a></td>
        </tr>
   </TABLE>
                <TABLE width="98%" cellpadding="3" cellSpacing="0" align='CENTER' >
								<caption>ESTADÍSTICAS DE COLEGIO</caption>
                  <tr>
                    <td>NUMERO DE SEDES: &nbsp;&nbsp;<b><font style='COLOR:#006699;'><c:out value="${sessionScope.fichaVO.fichaTotSedes}"/></font></b></td>
									</tr>
                <tr><td><hr color='red'></td></tr>
                  <tr>
                    <td>NUMERO DE JORNADAS: &nbsp;&nbsp;<b><font style='COLOR:#006699;'><c:out value="${sessionScope.fichaVO.fichaTotJornadas}"/></font></b></td>
									</tr>
                <tr><td><hr color='red'></td></tr>
                <tr>
                    <td>ESTUDIANTES POR JORNADA, GRADO Y GÉNERO<BR>
					<!-- 
					<TABLE width="100%" cellpadding="0" cellSpacing="0"  border=1>
													<tr>
														<th class="EncabezadoColumna">Sede - Jornada</th>
														<th class="EncabezadoColumna">Grado</th>
														<th class="EncabezadoColumna">M</th>
														<th class="EncabezadoColumna">F</th>
													</tr>
                          <c:forEach begin="0" items="${sessionScope.fichaVO.fichaEstXJorGrado}" var="fila" varStatus="st">
													<tr>
														<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[0]}"/> - <c:out value="${fila[1]}"/></td>
														<td class='Fila<c:out value="${st.count%2}"/>'>(<c:out value="${fila[2]}"/>) <c:out value="${fila[3]}"/></td>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></th>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[5]}"/></th>
													</tr>	
                          </c:forEach>
											</table>		
										</td>
                </tr>
                 -->
                <tr>                
                <td>
                <TABLE    width="100%" cellpadding="0" cellSpacing="0"  border=1>
                   		<tr>
						 <th class="EncabezadoColumna">Sede</th>
						 <th class="EncabezadoColumna">Jornada - Grado</th> 
						 <th class="EncabezadoColumna">M</th>
						 <th class="EncabezadoColumna">F</th>
						</tr>
                   <c:forEach begin="0" items="${sessionScope.fichaVO.listaSede}" var="sedeItem" varStatus="st">
					  <tr>
					  
					    <td  class='Fila<c:out value="${st.count%2}"/>'>
					      <c:out value="${sedeItem.sedNombre}"/>
					    </td>
					    
					    <td>
					     <table border="0"   width="100%" >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem_" varStatus="st2">
					       <tr  style="width: 50px;"  >
					        <td   style="width: 70px;" class='Fila<c:out value="${st2.count%2}"/>'  ><c:out value="${jordItem_.jordNombre}"/></td>
					        <td bordercolor="1" >
					          <table  class='table<c:out value="${st2.count%2}"/>' border="0" height="100%" width="100%"  >
					            <c:forEach begin="0" items="${jordItem_.listaGrado}" var="gradoItem_" varStatus="st33">
					              <tr><td   align="left"  class='Fila<c:out value="${st2.count%2}"/>'   ><c:out value="${gradoItem_.graNombre }"/></td></tr>
					            </c:forEach> 
					           </table> 
					        </td>
					        </tr>					        
					     </c:forEach>
					      </table> 
					    </td>
					    
					    <td>
					     <table border="0"  width="100%" >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem_" varStatus="st2">
					       <tr>
					        <td>
					          <table  class='table<c:out value="${st2.count%2}"/>'  border="0"  height="100%" width="100%"  >
					            <c:forEach begin="0" items="${jordItem_.listaGrado}" var="gradoItem_" varStatus="st33">
					              <tr><td align="right"  class='Fila<c:out value="${st2.count%2}"/>'  ><c:out value="${gradoItem_.totalEstM }"/></td></tr>
					            </c:forEach> 
					           </table> 
					        </td>
					        </tr>					        
					     </c:forEach>
					      </table>
					    </td>
					    
					    <td>
					     <table border="0"   width="100%" >
					     <c:forEach begin="0" items="${sedeItem.listaJornada}" var="jordItem_" varStatus="st2">
					       <tr>
					        <td>
					          <table class='table<c:out value="${st2.count%2}"/>'  border="0" height="100%" width="100%"  >
					            <c:forEach begin="0" items="${jordItem_.listaGrado}" var="gradoItem_" varStatus="st33">
					              <tr><td align="right"  class='Fila<c:out value="${st2.count%2}"/>'  ><c:out value="${gradoItem_.totalEstF }"/></td></tr>
					            </c:forEach> 
					           </table> 
					        </td>
					        </tr>					        
					     </c:forEach>
					      </table>
					    </td>
					    
					 </tr>	
                   </c:forEach>
                   <tr><th   colspan="2">Total</th><th><c:out value="${sessionScope.fichaVO.totalConsolEstGeneroM }"/></th><th><c:out value="${sessionScope.fichaVO.totalConsolEstGeneroF }"/></th></tr>
                  </table>
                 </td> 
                </tr>
                
                <tr><td><hr color='red'></td></tr>
                <tr>
                    <td>ESTUDIANTES POR GRADO Y GÈNERO<BR>
						<!-- 
											<TABLE width="100%" cellpadding="0" cellSpacing="0"  border=1>
													<tr>
														<th class="EncabezadoColumna">Grado</th>
														<th class="EncabezadoColumna">M</th>
														<th class="EncabezadoColumna">F</th>
													</tr>
                          <c:forEach begin="0" items="${sessionScope.fichaVO.fichaEstXGrado}" var="fila" varStatus="st">
													<tr>
														<td class='Fila<c:out value="${st.count%2}"/>'>(<c:out value="${fila[0]}"/>) <c:out value="${fila[1]}"/></td>
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
                <TABLE    width="80%" align="center" cellpadding="2" cellSpacing="1"  border=1>
                   		<tr>
						 <th class="EncabezadoColumna">Grado</th> 
						 <th class="EncabezadoColumna">M</th>
						 <th class="EncabezadoColumna">F</th>
						</tr>
                   <c:forEach begin="0" items="${sessionScope.fichaVO.listaGradoGenero}" var="gradoItem_" varStatus="st">
					   <tr><td   align="left"  class='Fila<c:out value="${st.count%2}"/>'   ><c:out value="${gradoItem_.graNombre }"/>
					      </td>
					    
					   <td align="right"  class='Fila<c:out value="${st.count%2}"/>'  ><c:out value="${gradoItem_.totalEstGradoM }"/> 
					   </td>
					    
					   <td align="right"  class='Fila<c:out value="${st.count%2}"/>'  ><c:out value="${gradoItem_.totalEstGradoF }"/> 
					   </td>
					    
					    
					 </tr>	
                   </c:forEach>
                   <tr><th   >Total</th><th align="right" ><c:out value="${sessionScope.fichaVO.totalConsolEstGeneroGradoM }"/></th><th align="right"><c:out value="${sessionScope.fichaVO.totalConsolEstGeneroGradoF }"/></th></tr>
                  </table>
                 </td> 
                </tr>
                
                
                
                <tr><td><hr color='red'></td></tr>
                <tr>
                    <td>ESPACIOS FÍSICOS POR SEDE Y TIPO DE ESPACIO<BR>
					<!-- 
											<TABLE width="100%" cellpadding="0" cellSpacing="0"  border=1>
													<tr>
														<td class="EncabezadoColumna">Sede</td>
														<td class="EncabezadoColumna">Tipo de espacio</td>
														<td class="EncabezadoColumna">Cantidad</td>
													</tr>
                          <c:forEach begin="0" items="${sessionScope.fichaVO.fichaEspacioXTipo}" var="fila" varStatus="st">
													<tr>
														<td class='Fila<c:out value="${st.count%2}"/>' ><c:out value="${fila[1]}"/></td>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></th>
														<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></th>
													</tr>	
                          </c:forEach>
					  </table>		
					</td>
                  </tr>
                  -->
                  <tr>                
                <td>
                <TABLE    width="80%" align="center" cellpadding="2" cellSpacing="1"  border=1>
                   <c:if test="${sessionScope.fichaVO.totalConsoEspacio > 0 }">
                   		<tr>
						 <th class="EncabezadoColumna">Sede</th> 
						 <th class="EncabezadoColumna">Tipo de espacio</th>
						 <th class="EncabezadoColumna">Cantidad</th>
						</tr>
                   <c:forEach begin="0" items="${sessionScope.fichaVO.listaSede}" var="sedeItem" varStatus="st">
					  <c:if test="${ sedeItem.banderaEspacio == 1}">
					  
					 <tr>
					  
					    <td  class='Fila<c:out value="${st.count%2}"/>'>
					      <c:out value="${sedeItem.sedNombre}"/>
					    </td>
					    
					    <td>
					     <table  class='table<c:out value="${st.count%2}"/>' border="0"   width="100%" >
					       <c:forEach begin="0" items="${sedeItem.listaEspacio}" var="item" varStatus="st2">
					         <tr><td class='Fila<c:out value="${st.count%2}"/>'  ><c:out value="${item.nombre}"/></td>
					         </tr>
					       </c:forEach> 
					      </table>
					     </td>
					     
					         <td>
					     <table  class='table<c:out value="${st.count%2}"/>' border="0"   width="100%"    >
					       <c:forEach begin="0" items="${sedeItem.listaEspacio}" var="item" varStatus="st2">
					         <tr><td  align="right" class='Fila<c:out value="${st.count%2}"/>'  ><c:out value="${item.codigo}"/></td>
					         </tr>
					       </c:forEach> 
					      </table>
					     </td>
					    </tr> 
					   </c:if> 
                   </c:forEach>
                   <tr><th   colspan="2" >Total</th><th align="right" ><c:out value="${sessionScope.fichaVO.totalConsoEspacio}"/></th></tr>
                  </c:if>
                  <c:if test="${sessionScope.fichaVO.totalConsoEspacio == 0 }">
                    <tr><td>No se encontro información de espacios físicos</td></tr>
                  </c:if>
                  </table>
                 </td> 
                </tr>
                </TABLE>
  </font>
<!--//////////////////////////////-->
</form>