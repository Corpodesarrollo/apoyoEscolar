<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
<jsp:useBean id="filtroVO" class="siges.ruta.vo.FiltroVO" scope="session"/>
<jsp:useBean id="gestacionVO" class="siges.ruta.vo.GestacionVO" scope="session"/>
<jsp:useBean id="rutaParams" class="siges.ruta.vo.ParamsVO" scope="page"/>
<%@include file="../parametros.jsp"%>
<%pageContext.setAttribute("filtroDiscapacidad",Recursos.recurso[Recursos.DISCAPACIDAD]);%>
		<script language="JavaScript">
			<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve,txt){
				var key=nav4?eve.which :eve.keyCode;
				if(key==46){
					if(txt.value.indexOf('.')>-1 || txt.value==''){
					return false;
					}
				}
				return(key<=13 || (key>=48 && key<=57) || key==46);
			}
			
			var disc=new Array();
			<c:forEach begin="0" items="${filtroDiscapacidad}" var="fila" varStatus="st">disc[<c:out value="${fila[0]}"/>]='<c:out value="${fila[1]}"/>';</c:forEach>
			
			function hacerValidaciones_frm(forma){
			}
			
			function nuevo(forma){
				forma.tipo.value='';
				forma.action='<c:url value="/ruta/Nuevo.do"/>';
				forma.submit();
			}
			
			function buscar(){
				document.frm.cmd.value='';
				document.frm.tipo.value=document.frm.tipo2.value;
				document.frm.action='<c:url value="/ruta/Nuevo.do"/>';
				document.frm.submit();
			}
			
			function cancelarFiltro(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			
			function setValores(forma){
				if(forma.gesEstudiante.length){
					for(var i=0;i<forma.gesEstudiante.length;i++){
						if(forma.gesGenero[i]){
							if(forma.gesGenero[i].value=='1'){//hombre
								if(forma.gesPadre_[i].checked){
									forma.gesPadre[i].value='1';
								}else{
									forma.gesPadre[i].value='-1';
								}
							}
							if(forma.gesGenero[i].value=='2'){//mujer
								if(forma.gesGestante_[i].checked){//gestante
									forma.gesGestante[i].value='1';
								}else{
									forma.gesGestante[i].value='-1';
								}
								if(forma.gesLactante_[i].checked){//lactante
									forma.gesLactante[i].value='1';
								}else{
									forma.gesLactante[i].value='-1';
								}
							}
						}
					}
				}else{
						if(forma.gesGenero){
							if(forma.gesGenero.value=='1'){//hombre
								if(forma.gesPadre_.checked){
									forma.gesPadre.value='1';
								}else{
									forma.gesPadre.value='-1';
								}
							}
							if(forma.gesGenero.value=='2'){//mujer
								if(forma.gesGestante_.checked){//gestante
									forma.gesGestante.value='1';
								}else{
									forma.gesGestante.value='-1';
								}
								if(forma.gesLactante_.checked){//lactante
									forma.gesLactante.value='1';
								}else{
									forma.gesLactante.value='-1';
								}
							}
						}
				}
			}
			function guardar(){
				if(document.frm.gesEstudiante){
					if(validarForma(document.frm)){
						setValores(document.frm);
						document.frm.cmd.value='Guardar';
						document.frm.submit();
					}
				}else{
					alert('No hay estudiantes a guardar');
				}
			}
			
			//-->
		</script>
  <%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/ruta/FiltroGuardar.jsp"/>' METHOD="POST" name='frm' onSubmit="return validarForma(frm)">
			<input type="hidden" name="cmd" value="">
			<input type="hidden" name="tipo" value='<c:out value="${pageScope.rutaParams.FICHA_GESTACION_RESULTADO}"/>'>
			<input type="hidden" name="tipo2" value='<c:out value="${pageScope.rutaParams.FICHA_GESTACION_FILTRO}"/>'>
			<INPUT type='hidden' name='id'>
				<table border="0" align="center" width="98%" cellpadding="0" cellspacing="0">
				<caption>Gestación - Resultados</caption>
					<tr>
						<td colspan="6">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Guardar" onClick="guardar()">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()">
						</td>
					</tr>
				</table>
				<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="Silver">
					<tr>
						<th class="EncabezadoColumna" width='1%'>N</th>
						<th class="EncabezadoColumna" colspan='2'>Identificación</th>
						<th class="EncabezadoColumna">Apellidos</th>
						<th class="EncabezadoColumna">Nombres</th>
						<th class="EncabezadoColumna">Discapacidad</th>
						<th class="EncabezadoColumna">Gestante</th>
						<th class="EncabezadoColumna">Lactante</th>
						<th class="EncabezadoColumna">Padre</th>
					</tr>
					<c:if test="${empty requestScope.resultadoBusqueda}"><tr><th colspan='6'>NO HAY REGISTROS DE ESTA BUSQUEDA</th></tr></c:if>
					<c:if test="${!empty requestScope.resultadoBusqueda}">
					<c:forEach begin="0" items="${requestScope.resultadoBusqueda}" var="fila" varStatus="st">
					<input type='hidden' name='gesEstudiante' value='<c:out value="${fila[0]}"/>'>
					<input type='hidden' name='gesGenero' value='<c:out value="${fila[8]}"/>'>
					<input type='hidden' name='gesJerarquia' value='<c:out value="${fila[9]}"/>'>
					<tr>
						<tD class='Fila<c:out value="${st.count%2}"/>'><B><c:out value="${st.count}"/></B></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><script>document.write(disc[<c:out value="${fila[10]}"/>]);</script>&nbsp;</td>
						<c:if test="${fila[8]==2}">
						<th class='Fila<c:out value="${st.count%2}"/>'><input class='Fila<c:out value="${st.count%2}"/>' type='checkbox' name='gesGestante_' value="1" <c:if test="${fila[5]==1}">checked</c:if>></th>
						<th class='Fila<c:out value="${st.count%2}"/>'><input class='Fila<c:out value="${st.count%2}"/>' type='checkbox' name='gesLactante_' value="1" <c:if test="${fila[6]==1}">checked</c:if>></th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;</th>
						<input type='hidden' name='gesPadre_' value='-1'>
						<input type='hidden' name='gesGestante' value='<c:out value="${fila[5]}"/>'>
						<input type='hidden' name='gesLactante' value='<c:out value="${fila[6]}"/>'>
						<input type='hidden' name='gesPadre' value='-1'>
						</c:if>
						<c:if test="${fila[8]==1}">
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'><input class='Fila<c:out value="${st.count%2}"/>' type='checkbox' name='gesPadre_' value="1" <c:if test="${fila[7]==1}">checked</c:if>></th>
						<input type='hidden' name='gesGestante_' value='-1'>
						<input type='hidden' name='gesLactante_' value='-1'>
						<input type='hidden' name='gesGestante' value='-1'>
						<input type='hidden' name='gesLactante' value='-1'>
						<input type='hidden' name='gesPadre' value='<c:out value="${fila[7]}"/>'>
						</c:if>
					</tr>
					</c:forEach>
					</c:if>
				</table>
</form>