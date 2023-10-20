<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="cierreVO" class="siges.grupoPeriodo.beans.CierreVO" scope="session"/><jsp:setProperty name="cierreVO" property="*"/>
<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="2" scope="page"/>
<%pageContext.setAttribute("filtroPeriodos",Recursos.recursoEstatico[Recursos.PERIODO]);%>
<%pageContext.setAttribute("filtroMetodologia",Recursos.recurso[Recursos.METODOLOGIA]);%>
<script language='javaScript'>
		<!--
		var per1=new Array();
		var met1=new Array();
		var i=1;
		var est=new Array();

		
		
		est[0]='Abierto';
		est[1]='Cerrado';
		<c:forEach begin="0" items="${requestScope.filtroPeriodos_per}" var="per">per1[<c:out value="${per.codigo}"/>]='<c:out value="${per.nombre}"/>';</c:forEach>
		<c:forEach begin="0" items="${filtroMetodologia}" var="fila2">met1[<c:out value="${fila2[0]}"/>]='<c:out value="${fila2[1]}"/>';</c:forEach>
		var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
			}
			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/grupoPeriodo/ControllerAbrirEdit.do"/>";
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			
			function lanzarServicio(n){
				document.frmNuevo.action='<c:url value="/observacion/Nuevo.do"/>';  	
				document.frmNuevo.tipo.value=n;
				document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo,tipo2,tipo3,sed,jor,estadoAnterior, estadoNumPeriodos){
				//alert("estadoAnterior:"+estadoAnterior+"  ESTADONUMPERIODOS:"+estadoNumPeriodos+" tipo: "+tipo+" tipo2: "+tipo2+" tipo3: "+tipo3);
				var cerrar=1;
				var periodoCerrar=tipo2;
				if(periodoCerrar==1){
					cerrar=1;
				}else{
					if(periodoCerrar==7){
						if(estadoNumPeriodos==1)
							cerrar=1;
						else
							cerrar=0;
					}else{					
						if(estadoAnterior==1)
							cerrar=1;
						else
							cerrar=0;
					}
				}
				if(cerrar==1){
					if(validarForma(document.frmNuevo)){
						document.frmNuevo.tipo.value=tipo;
						document.frmNuevo.cieSed.value=sed;
						document.frmNuevo.cieJor.value=jor;
						document.frmNuevo.ciePer.value=tipo2;
						document.frmNuevo.cieEsta.value=tipo3;
						document.frmNuevo.cmd.value="Guardar";
						document.frmNuevo.submit();
					}
				}else{
					alert("No se puede cerrar el período solicitado, verifique que el período anterior ya este cerrado");
				}
			}
			function guardar2(tipo,tipo2,tipo3,sed,jor,estadoSiguiente, estadoPerFinal){
				//alert("estadoSiguiente:"+estadoSiguiente+"  ESTADONUMPERIODOS:"+estadoNumPeriodos);
				var cerrar=1;
				var periodoAbrir=tipo2;
				if(periodoAbrir==7){
					cerrar=1;
				}else{
					if(periodoAbrir==<c:out value="${sessionScope.login.logNumPer}"/>){
						if(estadoPerFinal==0)
							cerrar=1;
						else
							cerrar=0;
					}else{					
						if(estadoSiguiente==0)
							cerrar=1;
						else
							cerrar=0;
					}
				}
				if(cerrar==1){
					if(confirm("Abrir implica abrir todos los Grupos-Asignatura, Grupos-Area y  Grupos-Dimensión de ese periodo. \n si pulsa 'Aceptar' se abrira el periodo y los grupos. \n si pulsa cancelar no se abrira el periodo")){
						if(validarForma(document.frmNuevo)){
							document.frmNuevo.tipo.value=tipo;
							document.frmNuevo.cieSed.value=sed;
							document.frmNuevo.cieJor.value=jor;
							document.frmNuevo.ciePer.value=tipo2;
							document.frmNuevo.cieEsta.value=tipo3;
							document.frmNuevo.cmd.value="Abrir";
							document.frmNuevo.submit();
						}
					}
				}else{
					alert("No se puede abrir el período solicitado, verifique que el siguiente período este abierto");
				}
			}
			
			function cancelar(){
 					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.target="";
          			document.frmNuevo.submit(); 
			}
			function cerrar(){
				if (confirm('Nota:\n Hay grupos y asignaturas que no se han cerrado ¿Desea cerrar los grupos y el periodo de todas maneras?\n ')){
 					document.frmNuevo.cmd.value="Cerrar";
          			document.frmNuevo.submit(); 
				}
			}
			
			function hacerValidaciones_frm(forma){
				var grupotipoconsulta = document.getElementsByName("tipoconsulta");
				var estado=false;
				
				for(var i=0;i<grupotipoconsulta.length;i++)
				  {
					if(grupotipoconsulta[i].checked){
					   //alert('paso bnn');
						return true;	
					}
				}
				alert('Debe seleciconar por lo menos una opcion');
				return false;
				}
			
			function guardarConsultatipo(tipo){
				if(hacerValidaciones_frm(document.frm)){
					//alert('es '+document.frm);
					//document.getElementsById('tipo').value=tipo;
					//document.frm.tipo.value=tipo;
					document.frm.submit();
					
				}
			}
			//-->
		</script>
<%@include file="../mensaje.jsp"%>
	<font size="1">
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
				<td rowspan="2" width="500" bgcolor="#FFFFFF">
				<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/abrir_grupo_0.gif" alt="Logros"  height="26" border="0"></a>
				<img src="../etc/img/tabs/cerrar_periodo_1.gif" border="0"  height="26" alt='-Asignatura-'>
				<a href='javaScript:lanzarServicio(<c:out value="${paramsVO.FICHA_OBSERVACION_PERIODO}"/>)'><img src="../etc/img/tabs/observacion_periodo_0.gif" alt="Observación periodo"  height="26" border="0"></a>
				<a href='javaScript:lanzarServicio(<c:out value="${paramsVO.FICHA_OBSERVACION_GRUPO}"/>)'><img src="../etc/img/tabs/observacion_grupo_0.gif" alt="Observación grupo" height="26" border="0"></a>
				</td>
		</tr>
  </table>
  <c:if test="${!empty requestScope.gruposAbiertos}">
				                <form method="post" name="frm" id="frm" onSubmit="return hacerValidaciones_frm(frm)" action='<c:url value="../boletines/FiltroGuardarConsultaPerdidaAsignaturas.jsp"/>' >
									<table border="0" width="95%"  align="center">
									<caption>Consulta Estudiantes pierden Asignatura</caption>
										<tr>
										  <td width="45%" bgcolor="#FFFFFF">
								      	<input class='boton' name="cmd1" type="button" value="Generar Consulta" onClick="guardarConsultatipo(1)">
								        </td>
										</tr>
									</table>
										<!--//////////////////-->	
										<!-- FICHAS A MOSTRAR AL USUARIO -->
										<input type="hidden" name="tipo" id="tipo" value='1'>	
										<input type="hidden" name="cmd" value="Cancelar">
										<input type="hidden" name="areassel" value="0">
										<input type="hidden" name="asigsel" value="0">
										<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='180'>
										<input type="hidden" name="sedenom" value='<c:out value="${sessionScope.filtroConsultaAsignaturasPerdidas.sedenom}"/>'>
										<input type="hidden" name="jornadanom" value='<c:out value="${sessionScope.filtroConsultaAsignaturasPerdidas.jornadanom}"/>'>
										<input type="hidden" name="metodologianom" value='<c:out value="${sessionScope.filtroConsultaAsignaturasPerdidas.metodologianom}"/>'>
										<input type="hidden" name="gradonom" value='<c:out value="${sessionScope.filtroConsultaAsignaturasPerdidas.gradonom}"/>'>
										<input type="hidden" name="periodonom" value='<c:out value="${sessionScope.filtroConsultaAsignaturasPerdidas.periodonom}"/>'>
									
									 <table width="95%"  align="center" cellpadding="2" cellSpacing="0">
									    <tr>
									       <td>Generar estadístico estudiantes pierden asignatura x periodo
									       </td>
									       <td>
									        <input type="radio" name="tipoconsulta" value="4" />
									       </td>
									    </tr>
									    <tr>
									       <td>Generar estadístico estudiantes pierden x insasistencia
									       </td>
									       <td>
									        <input type="radio" name="tipoconsulta" value="5" />
									       </td>
									    </tr>
									  </table>
									  <input type="hidden" name="sede" value='<c:out value="${objetoConsultaperdidas.cieSed}"/>'>
									  <input type="hidden" name="jornada" value='<c:out value="${objetoConsultaperdidas.cieJor}"/>'>
									  <input type="hidden" name="periodo" value='<c:out value="${objetoConsultaperdidas.ciePer}"/>'>
									  
									</form>
									</c:if>
				
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/grupoPeriodo/AbrirGrupoGuardar.jsp"/>'>
	<br>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Gestion Académica - Cierre de Periodos - Vigencia: <c:out value="${sessionScope.login.vigencia_inst}"/></caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
				<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
			</td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="ciePer" value="<c:out value="${sessionScope.cierreVO.ciePer}"/>">
	<input type="hidden" name="cieEsta" value="<c:out value="${sessionScope.cierreVO.cieEsta}"/>">
	<input type="hidden" name="cieSed" value="<c:out value="${sessionScope.cierreVO.cieSed}"/>">
	<input type="hidden" name="cieJor" value="<c:out value="${sessionScope.cierreVO.cieJor}"/>">
<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<table width="95%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
									<c:if test="${empty requestScope.filtroPeriodosAbiertos}"><tr><th>No hay periodos para este colegio</th></tr></c:if>									
									<c:if test="${!empty requestScope.filtroPeriodosAbiertos}">
										<c:forEach begin="0" items="${requestScope.filtroSedesJornadas}" var="fila0" varStatus="st0">
										<tr><th colspan='5' class="EncabezadoColumna"><c:out value="${fila0[2]}"/></th></tr>
										<tr>
											<th class="EncabezadoColumna">Periodo</th>
											<th class="EncabezadoColumna">Estado</th>											
											<th class="EncabezadoColumna">Fecha de última actualización</th>
											<th class="EncabezadoColumna" width='5%'>&nbsp;</th>
										</tr>
										<c:forEach begin="0" items="${requestScope.filtroPeriodosAbiertos}" var="fila" varStatus="st">
										<c:if test="${fila0[0]==fila[20] && fila0[1]==fila[21]}">
										
										<!-- SETIAR ESTADO DEL ULTIMO PERIODO SEGUN NUMEROS DE PERIODO -->
										 <script language='javaScript'>
										 var estadoNumPer=0;	
											<c:if test="${sessionScope.login.logNumPer==1}">
												estadoNumPer=<c:out value="${fila[1]}"/>;
											</c:if>
											<c:if test="${sessionScope.login.logNumPer==2}">
												estadoNumPer=<c:out value="${fila[5]}"/>;
											</c:if>
											<c:if test="${sessionScope.login.logNumPer==3}">
												estadoNumPer=<c:out value="${fila[9]}"/>;
											</c:if>
											<c:if test="${sessionScope.login.logNumPer==4}">
												estadoNumPer=<c:out value="${fila[13]}"/>;
											</c:if>
											<c:if test="${sessionScope.login.logNumPer==5}">
												estadoNumPer=<c:out value="${fila[23]}"/>;
											</c:if>
											<c:if test="${sessionScope.login.logNumPer==6}">
												estadoNumPer=<c:out value="${fila[27]}"/>;
											</c:if>
											<c:if test="${sessionScope.login.logNumPer==7}">
												estadoNumPer=<c:out value="${fila[17]}"/>;
											</c:if>			
											//alert("ESTADO NUM PER "+estadoNumPer);
										</script> 
										
										<!-- VALIDACION PARA NUMERO DE PERIODOS PARA MOSTRAR FILAS P1 -->	
										<c:if test="${sessionScope.login.logNumPer>0}">
										
											 
										<tr>
											<td><script>document.write(per1[<c:out value="${fila[0]}"/>]);</script></td>
											<td><script>document.write(est[<c:out value="${fila[1]}"/>]);</script></td>
											
											<td><c:out value="${fila[3]}"/>&nbsp;</td>
											<th>
												<c:if test="${fila[1]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[0]}"/>,<c:out value="${fila[1]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,-1,estadoNumPer)'></c:if>
												<c:if test="${fila[1]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[0]}"/>,<c:out value="${fila[1]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[5]}"/>,<c:out value="${fila[17]}"/>)'></c:if>
											</th>
										</tr>
										</c:if>
										<!-- VALIDACION PARA NUMERO DE PERIODOS PARA MOSTRAR FILAS P2-->										
										<c:if test="${sessionScope.login.logNumPer>1}">
										<tr>
											<td><script>document.write(per1[<c:out value="${fila[4]}"/>]);</script></td>
											<td><script>document.write(est[<c:out value="${fila[5]}"/>]);</script></td>
											
											<td><c:out value="${fila[7]}"/>&nbsp;</td>
											<th>
												<c:if test="${fila[5]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[4]}"/>,<c:out value="${fila[5]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[1]}"/>,estadoNumPer)'></c:if>
												<c:if test="${fila[5]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[4]}"/>,<c:out value="${fila[5]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[9]}"/>,<c:out value="${fila[17]}"/>)'></c:if>
											</th>
										</tr>
										</c:if>
										
										<!-- VALIDACION PARA NUMERO DE PERIODOS PARA MOSTRAR FILAS P3 -->										
										<c:if test="${sessionScope.login.logNumPer>2}">
										<tr>
											<td><script>document.write(per1[<c:out value="${fila[8]}"/>]);</script></td>
											<td><script>document.write(est[<c:out value="${fila[9]}"/>]);</script></td>
											
											<td><c:out value="${fila[11]}"/>&nbsp;</td>
											<th>
												<c:if test="${fila[9]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[8]}"/>,<c:out value="${fila[9]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[5]}"/>,estadoNumPer)'></c:if>
												<c:if test="${fila[9]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[8]}"/>,<c:out value="${fila[9]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[13]}"/>,<c:out value="${fila[17]}"/>)'></c:if>
											</th>
										</tr>
										</c:if>
										<!-- VALIDACION PARA NUMERO DE PERIODOS PARA MOSTRAR FILAS P4-->
										<c:if test="${sessionScope.login.logNumPer>3}">
										<tr>
											<td><script>document.write(per1[<c:out value="${fila[12]}"/>]);</script></td>
											<td><script>document.write(est[<c:out value="${fila[13]}"/>]);</script></td>
											
											<td><c:out value="${fila[15]}"/>&nbsp;</td>
											<th>
												<c:if test="${fila[13]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[12]}"/>,<c:out value="${fila[13]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[9]}"/>,estadoNumPer)'></c:if>
												<c:if test="${fila[13]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[12]}"/>,<c:out value="${fila[13]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[23]}"/>,<c:out value="${fila[17]}"/>)'></c:if>
											</th>
										</tr>
									</c:if>									
										
										
										<!-- VALIDACION PARA NUMERO DE PERIODOS PARA MOSTRAR FILAS P5-->
										<c:if test="${sessionScope.login.logNumPer>4}">
										<tr>
											<td><script>document.write(per1[<c:out value="${fila[22]}"/>]);</script></td>
											<td><script>document.write(est[<c:out value="${fila[23]}"/>]);</script></td>
											
											<td><c:out value="${fila[25]}"/>&nbsp;</td>
											<th>
												<c:if test="${fila[23]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[22]}"/>,<c:out value="${fila[23]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[13]}"/>,estadoNumPer)'></c:if>
												<c:if test="${fila[23]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[22]}"/>,<c:out value="${fila[23]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[27]}"/>,<c:out value="${fila[17]}"/>)'></c:if>
											</th>
										</tr>
										</c:if>										
										
										
										<!-- VALIDACION PARA NUMERO DE PERIODOS PARA MOSTRAR FILAS P6-->										
										<c:if test="${sessionScope.login.logNumPer>5}">
										
										<tr>
											<td><script>document.write(per1[<c:out value="${fila[26]}"/>]);</script></td>
											<td><script>document.write(est[<c:out value="${fila[27]}"/>]);</script></td>
											
											<td><c:out value="${fila[29]}"/>&nbsp;</td>
											<th>
												<c:if test="${fila[27]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[26]}"/>,<c:out value="${fila[27]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[23]}"/>,estadoNumPer)'></c:if>
												<c:if test="${fila[27]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[26]}"/>,<c:out value="${fila[27]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[17]}"/>,<c:out value="${fila[17]}"/>)'></c:if>
											</th>
										</tr>
										
										
										</c:if>
										
										<!-- PERIODO FINAL SIEMPRE VA-->
										<tr>
											<td><script>document.write(per1[<c:out value="${fila[16]}"/>]);</script></td>
											<td><script>document.write(est[<c:out value="${fila[17]}"/>]);</script></td>
											
											<td><c:out value="${fila[19]}"/>&nbsp;</td>
											<th>
												<c:if test="${fila[17]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[16]}"/>,<c:out value="${fila[17]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,<c:out value="${fila[27]}"/>,<c:if test="${sessionScope.login.logNumPer==1}"><c:out value="${fila[1]}"/></c:if><c:if test="${sessionScope.login.logNumPer==2}"><c:out value="${fila[5]}"/></c:if><c:if test="${sessionScope.login.logNumPer==3}"><c:out value="${fila[9]}"/></c:if><c:if test="${sessionScope.login.logNumPer==4}"><c:out value="${fila[13]}"/></c:if><c:if test="${sessionScope.login.logNumPer==5}"><c:out value="${fila[23]}"/></c:if><c:if test="${sessionScope.login.logNumPer==6}"><c:out value="${fila[27]}"/></c:if><c:if test="${sessionScope.login.logNumPer==7}"><c:out value="${fila[17]}"/></c:if>)'></c:if>
												<c:if test="${fila[17]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[16]}"/>,<c:out value="${fila[17]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>,-1,<c:out value="${fila[17]}"/>)'></c:if>
											</th>
										</tr>	
										<script>//alert("ESTADO NUM PER "+estadoNumPer );</script>									
										</c:if>
										</c:forEach>
										<tr><th colspan='5' class="EncabezadoColumna"></th></tr>
										</c:forEach>
									</c:if>									
									</TABLE>
									
									
									
									
									
									
								<c:if test="${!empty requestScope.gruposAbiertos}">
				       <br>
											<center><input class='boton' type="button" name='cmdack' onclick="cerrar();" value="cerrar grupos"></center>
													<table width="95%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
													<caption>LISTA DE GRUPOS Y ÁREAS/ASIGNATURAS/DIMENSIONES QUE NO HAN SIDO CERRADAS</caption>
													<tr>
														<th class="EncabezadoColumna" width='3%'>Nº</th>
														<th class="EncabezadoColumna">Metodología</th>
														<th class="EncabezadoColumna">Grado</th>
														<th class="EncabezadoColumna">Grupo</th>
														<th class="EncabezadoColumna">Área/Asignatura/Dimensión</th>
													</tr>
													<c:forEach begin="0" items="${requestScope.gruposAbiertos}" var="fila" varStatus="st">
														<tr>
															<td><c:out value="${st.count}"/></td>
															<td><script>document.write(met1[<c:out value="${fila[0]}"/>]);</script></td>
															<td><c:out value="${fila[1]}"/></td>
															<td><c:out value="${fila[2]}"/></td>
															<td><c:out value="${fila[4]}"/></td>
														</tr>
													</c:forEach>
													</TABLE>
												</c:if>
					</font>		
				<!--//////////////////////////////-->
				</form>
				
				
				
				
<script>if(document.frmNuevo.cmdack) document.frmNuevo.cmdack.focus();</script>