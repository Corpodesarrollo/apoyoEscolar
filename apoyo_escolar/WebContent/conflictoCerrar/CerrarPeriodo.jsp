<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="conflictocerrar" class="siges.conflictoCerrar.beans.ConflictoCerrar" scope="session"/>
<jsp:setProperty name="conflictocerrar" property="*"/>
<%pageContext.setAttribute("filtroPeriodos",Recursos.recursoEstatico[Recursos.PERIODO]);%>
<%pageContext.setAttribute("filtroMetodologia",Recursos.recurso[Recursos.METODOLOGIA]);%>
<script languaje='javaScript'>
		<!--
			var nav4=window.Event ? true : false;
			
			var per1=new Array();
			var met1=new Array();
			var i=1;
			var est=new Array();
			est[0]='Abierto';
			est[1]='Cerrado';
			<c:forEach begin="0" items="${requestScope.listaPeriodos}" var="fila2">
				per1[<c:out value="${fila2[0]}"/>]='<c:out value="${fila2[1]}"/>';
			</c:forEach>
			<c:forEach begin="0" items="${filtroMetodologia}" var="fila2">
				met1[<c:out value="${fila2[0]}"/>]='<c:out value="${fila2[1]}"/>';
			</c:forEach>
			
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
			}
			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/conflictoCerrar/ControllerEditar.do"/>";
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			function guardar(tipo,tipo2,tipo3,tipo4,tipo5){
				if(confirm("Se dispone a cerrar un periodo para conflico escolar. \n si pulsa 'Aceptar' se cerrara el periodo. \n si pulsa cancelar no se cerrara el periodo")){
					if(validarForma(document.frmNuevo)){
						document.frmNuevo.tipo.value=6;
						document.frmNuevo.periodo.value=tipo2;
						document.frmNuevo.estper.value=tipo3;
						document.frmNuevo.sede.value=tipo4;
						document.frmNuevo.jornada.value=tipo5;
						//alert("El periodo fue cerrado satisfactoriamente")
						document.frmNuevo.submit();
					}
				}
			}
			function guardar2(tipo,tipo2,tipo3,tipo4,tipo5){
				if(confirm("Abrir implica abrir todos los Grupos ese periodo. \n si pulsa 'Aceptar' se abrira el periodo y los grupos. \n si pulsa cancelar no se abrira el periodo")){
					if(validarForma(document.frmNuevo)){
						document.frmNuevo.tipo.value=5;
						document.frmNuevo.periodo.value=tipo2;
						document.frmNuevo.estper.value=tipo3;
						document.frmNuevo.sede.value=tipo4;
						document.frmNuevo.jornada.value=tipo5;
						document.frmNuevo.submit();
					}
				}
			}
			
			function cancelar(){
 				document.frmNuevo.cmd.value="Cancelar";
				document.frmNuevo.target="";
      	document.frmNuevo.submit(); 
			}
			function cerrar(){
				if (confirm('Nota:\n Hay grupos que no se han cerrado ¿Desea cerrar los grupos y el periodo de todas maneras?\n ')){
 					document.frmNuevo.cmd.value="Cerrar";
 					document.frmNuevo.tipo.value=7;
     			document.frmNuevo.submit(); 
				}
			}
			//-->
		</script>
<%@include file="../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/conflictoCerrar/NuevoGuardar.jsp"/>'>
	<br>
	<table border="1" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Abrir/Cerrar periodo de Conflicto Escolar</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
			</td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value=''>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="sede" value="<c:out value="${sessionScope.conflictocerrar.sede}"/>">
	<input type="hidden" name="jornada" value="<c:out value="${sessionScope.conflictocerrar.jornada}"/>">
	<input type="hidden" name="periodo" value="<c:out value="${sessionScope.conflictocerrar.periodo}"/>">
	<input type="hidden" name="estper" value="<c:out value="${sessionScope.conflictocerrar.estper}"/>">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
			<td rowspan="2" width="235" bgcolor="#FFFFFF">
				<img src="../etc/img/tabs/conf_abrir_cerrar_periodo_1.gif" border="0"  height="26" alt='Abrir/Cerrar Periodo de conflicto escolar'>
			</td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<table width="95%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
	<c:if test="${empty requestScope.filtroPeriodosAbiertos}"><tr><th>No hay periodos para este Colegio</th></tr></c:if>									
	<c:if test="${!empty requestScope.filtroPeriodosAbiertos}">
		<c:forEach begin="0" items="${requestScope.filtroSedesJornadas}" var="fila0" varStatus="st0">
		
		<c:set scope="page" var="cont1" value="${tamanoListaPeriodos}"/>
		
		<!-- Encontrar Tamaño de la lista periodos -->
		<c:forEach items="${requestScope.listaPeriodos}" varStatus="tamanoListaPeriodos">
			<c:set scope="page" var="cont1" value="${tamanoListaPeriodos.index+1}"/>
		</c:forEach>
		<!-- ***************** -->
		
		<tr><th colspan='5' class="EncabezadoColumna"><c:out value="${fila0[2]}"/></th></tr>
		<tr>
			<th class="EncabezadoColumna">Periodo</th>
			<th class="EncabezadoColumna">Estado</th>
			<th class="EncabezadoColumna">Fecha de cierre</th>
			<th class="EncabezadoColumna">Fecha de última actualización</th>
			<th class="EncabezadoColumna" width='5%'>&nbsp;</th>
		</tr>
		<c:forEach begin="0" items="${requestScope.filtroPeriodosAbiertos}" var="fila" varStatus="st">
		<c:if test="${fila0[0]==fila[20] && fila0[1]==fila[21]}">
			
			
				<c:if test="${pageScope.cont1 >= 1}">
					<tr>
						<td><script>document.write(per1[<c:out value="${fila[0]}"/>]);</script></td>
						<td><script>document.write(est[<c:out value="${fila[1]}"/>]);</script></td>
						<td><c:out value="${fila[2]}"/>&nbsp;</td>
						<td><c:out value="${fila[3]}"/>&nbsp;</td>
						<th>
							<c:if test="${fila[1]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[0]}"/>,<c:out value="${fila[1]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>)'></c:if>
							<c:if test="${fila[1]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[0]}"/>,<c:out value="${fila[1]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>)'></c:if>
						</th>
					</tr>
				</c:if>
				<c:if test="${pageScope.cont1 >= 2}">
					<tr>
						<td><script>document.write(per1[<c:out value="${fila[4]}"/>]);</script></td>
						<td><script>document.write(est[<c:out value="${fila[5]}"/>]);</script></td>
						<td><c:out value="${fila[6]}"/>&nbsp;</td>
						<td><c:out value="${fila[7]}"/>&nbsp;</td>
						<th>
							<c:if test="${fila[5]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[4]}"/>,<c:out value="${fila[5]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>)'></c:if>
							<c:if test="${fila[5]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[4]}"/>,<c:out value="${fila[5]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>)'></c:if>
						</th>
					</tr>
				</c:if>
				<c:if test="${pageScope.cont1 >= 3}">
					<tr>
						<td><script>document.write(per1[<c:out value="${fila[8]}"/>]);</script></td>
						<td><script>document.write(est[<c:out value="${fila[9]}"/>]);</script></td>
						<td><c:out value="${fila[10]}"/>&nbsp;</td>
						<td><c:out value="${fila[11]}"/>&nbsp;</td>
						<th>
							<c:if test="${fila[9]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[8]}"/>,<c:out value="${fila[9]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>)'></c:if>
							<c:if test="${fila[9]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[8]}"/>,<c:out value="${fila[9]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>)'></c:if>
						</th>
					</tr>
				</c:if>
				<c:if test="${pageScope.cont1 >= 4}">
					<tr>
						<td><script>document.write(per1[<c:out value="${fila[12]}"/>]);</script></td>
						<td><script>document.write(est[<c:out value="${fila[13]}"/>]);</script></td>
						<td><c:out value="${fila[14]}"/>&nbsp;</td>
						<td><c:out value="${fila[15]}"/>&nbsp;</td>
						<th>
							<c:if test="${fila[13]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[12]}"/>,<c:out value="${fila[13]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>)'></c:if>
							<c:if test="${fila[13]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[12]}"/>,<c:out value="${fila[13]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>)'></c:if>
						</th>
					</tr>
				</c:if>
				<c:if test="${pageScope.cont1 >= 5}">
					<tr>
						<td><script>document.write(per1[<c:out value="${fila[16]}"/>]);</script></td>
						<td><script>document.write(est[<c:out value="${fila[17]}"/>]);</script></td>
						<td><c:out value="${fila[18]}"/>&nbsp;</td>
						<td><c:out value="${fila[19]}"/>&nbsp;</td>
						<th>
							<c:if test="${fila[17]==0}"><input class='boton' type='button' name='cmdcerrar' value='Cerrar' onClick='guardar(document.frmNuevo.tipo.value,<c:out value="${fila[16]}"/>,<c:out value="${fila[17]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>)'></c:if>
							<c:if test="${fila[17]==1}"><input class='boton' type='button' name='cmdabrir' value='Abrir' onClick='guardar2(document.frmNuevo.tipo.value,<c:out value="${fila[16]}"/>,<c:out value="${fila[17]}"/>,<c:out value="${fila0[0]}"/>,<c:out value="${fila0[1]}"/>)'></c:if>
						</th>
					</tr>
				</c:if>
			
			
		</c:if>
		</c:forEach>
		<tr><th colspan='5' class="EncabezadoColumna"></th></tr>
		</c:forEach>
	</c:if>									
	</TABLE>
<c:if test="${!empty requestScope.gruposAbiertos}">
	<br>
	<center><input class='boton' type="button" name='cmdack' onclick="cerrar();" value="CERRAR GRUPOS Y PERIODO"></center>
	<table width="95%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
	<caption>LISTA DE GRUPOS QUE NO HAN SIDO CERRADOS</caption>
	<tr>
		<th class="EncabezadoColumna" width='3%'>Nº</th>
		<th class="EncabezadoColumna">Grado</th>
		<th class="EncabezadoColumna">Grupo</th>
	</tr>
	<c:forEach begin="0" items="${requestScope.gruposAbiertos}" var="fila" varStatus="st">
		<tr>
			<td><c:out value="${st.count}"/></td>
			<td><c:out value="${fila[1]}"/></td>
			<td><c:out value="${fila[2]}"/></td>
		</tr>
	</c:forEach>
	</TABLE>
</c:if>
	</font>		
<!--//////////////////////////////-->
</form>