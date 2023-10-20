<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtrogroup" class="siges.grupo.beans.FiltroBeanGrupos" scope="session"/>
<%@include file="../parametros.jsp"%>
		<script language="JavaScript">
			<!--
			function hacerValidaciones_frmResultado(forma){
				if(forma.id)
					validarSeleccion(forma.id, "- Debe seleccionar un item");
				else{
					return false;
				}	
			}
			function buscar(){
				document.frmResultado.cmd.value='';
				document.frmResultado.ext2.value='';
				document.frmResultado.action='<c:url value="/grupo/ControllerGrupoFiltroEdit.do"/>';
				document.frmResultado.submit();
			}
			function cancelarFiltro(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			function editar(id_,id2_,grado_,metod_){
				document.frmResultado.id.value=id_;
				document.frmResultado.id2.value=id2_;
				document.frmResultado.id_grado.value=grado_;
				document.frmResultado.id_metod.value=metod_;
			   if(document.frmResultado.id){
				    if(document.frmResultado.id2){
					   if(validarForma(document.frmResultado)){
						  document.frmResultado.cmd.value='Editar';
						  document.frmResultado.ext2.value='/grupo/FiltroResultado.jsp';
						  document.frmResultado.action='<c:url value="/grupo/ControllerGrupoFiltroSave.do"/>';
						  document.frmResultado.submit();
					}
				  }	
				}
			}

			function eliminar(id_,id2_,grado_,metod_){
				document.frmResultado.id.value=id_;
				document.frmResultado.id2.value=id2_;
				document.frmResultado.id_grado.value=grado_;
				document.frmResultado.id_metod.value=metod_;
				if(document.frmResultado.id){
				  if(document.frmResultado.id2){
				    if(document.frmResultado.id_grado){
					  if(document.frmResultado.id_metod){					
						if(validarForma(document.frmResultado)){
						  if (confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
							document.frmResultado.cmd.value='Eliminar';
							//document.frmResultado.ext2.value='/grupo/FiltroResultado.jsp';
							document.frmResultado.action='<c:url value="/grupo/ControllerGrupoFiltroSave.do"/>';
							document.frmResultado.submit();
						  }
						}						
					 }	
				   }	
				}
			  }
			}	

			//-->
		</script>
  <%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/grupo/ControllerGrupoFiltroEdit.do"/>' METHOD="POST" name='frmResultado' onSubmit="return validarForma(frmResultado)">
			<input type="hidden" name="cmd" value="">
			<input type="hidden" name="id2" value="">
			<input type="hidden" name="id_grado" value="">
			<input type="hidden" name="id_metod" value="">
			<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='130'><INPUT TYPE="hidden" NAME="height" VALUE='130'>
			<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			  <caption>Administración de Grupos >> Grupos >> Resultados de busqueda</caption>
					<tr>
						<td colspan="6">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Editar" onClick="editarFiltro()">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Cancelar" onClick="cancelarFiltro()">
						</td>
					</tr>
				</table>
				<table border="1" align="center" width="90%" cellpadding="0" cellspacing="0" bordercolor="Silver">
					<tr>
						<th class="EncabezadoColumna" width="40px">
						<img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'>
						 	<img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></th>
						<th class="EncabezadoColumna">Grado</th><th class="EncabezadoColumna">C&oacute;digo</th><th class="EncabezadoColumna">Nombre</th><th class="EncabezadoColumna">Cupo</th><th class="EncabezadoColumna">Coodinador</th><th class="EncabezadoColumna">Orden</th>
					</tr>
					<c:if test="${empty sessionScope.filtroListaGrupo}">
					<tr><tD class="Fila1" align='center' colspan='6'>NO HAY REGISTROS DE ESTA BUSQUEDA</td></tr></c:if>
					<c:if test="${!empty sessionScope.filtroListaGrupo}">
					<c:forEach begin="0" items="${sessionScope.filtroListaGrupo}" var="fila" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>' width="40px;">
							<a href='javaScript:editar(<c:out value="${fila[1]}"/>,<c:out value="${fila[5]}"/>,<c:out value="${fila[6]}"/>,<c:out value="${fila[7]}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}">
							<a href='javaScript:eliminar(<c:out value="${fila[1]}"/>,<c:out value="${fila[5]}"/>,<c:out value="${fila[6]}"/>,<c:out value="${fila[7]}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						 </th>
								 
					<!--<td class='Fila<c:out value="${st.count%2}"/>' width='10'>
						<INPUT class='Fila<c:out value="${st.count%2}"/>' type='radio' name='id' value='<c:out value="${fila[1]}"/>' onClick="javaScript:document.frmResultado.id2.value='<c:out value="${fila[5]}"/>', document.frmResultado.id_grado.value='<c:out value="${fila[6]}"/>', document.frmResultado.id_metod.value='<c:out value="${fila[7]}"/>'">
						</td> -->

						<td class='Fila<c:out value="${st.count%2}"/>'><center><c:out value="${fila[0]}"/></center></td><!--GRADO -->
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td><!--GRUCODIGO -->
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></td><!--GRUNOMBRE -->
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td><!--GRUCUPO -->
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td><!--COORDINADOR -->
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[8]}"/></td><!--ORDEN -->
																					<!--fila[5]            GRUCODJERAR  -->
					</tr></c:forEach></c:if>										<!--fila[6]            COD_GRADO    -->
				</table>															<!--fila[7]            COD_METOD    -->
		</form>



