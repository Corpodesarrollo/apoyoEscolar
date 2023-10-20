<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
<jsp:useBean id="filtroVO" class="siges.ruta.vo.FiltroVO" scope="session"/>
<jsp:useBean id="nutricionVO" class="siges.ruta.vo.NutricionVO" scope="session"/>
<jsp:useBean id="rutaParams" class="siges.ruta.vo.ParamsVO" scope="page"/>
<%@include file="../parametros.jsp"%>
<%pageContext.setAttribute("filtroDiscapacidad",Recursos.recurso[Recursos.DISCAPACIDAD]);%>
		<script language="JavaScript">
			<!--
			var nav4=window.Event ? true : false;
			var disc=new Array();
			<c:forEach begin="0" items="${filtroDiscapacidad}" var="fila" varStatus="st">disc[<c:out value="${fila[0]}"/>]='<c:out value="${fila[1]}"/>';</c:forEach>
			
			function acepteNumeros(eve,txt){
				var key=nav4?eve.which :eve.keyCode;
				if(key==46){
					if(txt.value.indexOf('.')>-1 || txt.value==''){
					return false;
					}
				}
				return(key<=13 || (key>=48 && key<=57) || key==46);
			}

			function hacerValidaciones_frm(forma){
				if(forma.nutEstudiante){
					for(var i=0;i<forma.nutEstudiante.length;i++){
						if(trim(forma.nutEstatura[i].value)!=''){
							if(!validarFloat(forma.nutEstatura[i], 'La talla debe estar entre 95 y 200 Cms.', 95, 200)) return false;
						}
						if(trim(forma.nutPeso[i].value)!=''){
							if(!validarFloat(forma.nutPeso[i], 'El peso debe estar entre 10 y 95 Kg.', 10, 95)) return false;
						}
					}	
				}
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
			
			function guardar(){
				if(document.frm.nutEstudiante){
					if(validarForma(document.frm)){
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
			<input type="hidden" name="tipo" value='<c:out value="${pageScope.rutaParams.FICHA_NUTRICION_RESULTADO}"/>'>
			<input type="hidden" name="tipo2" value='<c:out value="${pageScope.rutaParams.FICHA_NUTRICION_FILTRO}"/>'>
			<INPUT type='hidden' name='id'>
				<table border="0" align="center" width="98%" cellpadding="0" cellspacing="0">
				<caption>Nutrición - Resultados</caption>
					<tr>
						<td colspan="6">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Guardar" onClick="guardar()">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()">
						</td>
					</tr>
					<tr>
						<td colspan="6"><font style='FONT-SIZE:10pt;'><span class="style2">Nota:</span> La talla se debe indicar en <span class="style2">CENTIMETROS</span> y el peso se debe indicar en <span class="style2">KILOGRAMOS</font></span></td>
					</tr>
				</table>
				<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="Silver">
					<tr>
						<th class="EncabezadoColumna" width='1%'>N</th>
						<th class="EncabezadoColumna" colspan='2'>Identificación</th>
						<th class="EncabezadoColumna">Apellidos</th>
						<th class="EncabezadoColumna">Nombres</th>
						<th class="EncabezadoColumna">Discapacidad</th>
						<th class="EncabezadoColumna" title='en Centimetros'>Talla</th>
						<th class="EncabezadoColumna" title='en Kilogramos'>Peso</th>
					</tr>
					<c:if test="${empty requestScope.resultadoBusqueda}"><tr><th colspan='6'>NO HAY REGISTROS DE ESTA BUSQUEDA</th></tr></c:if>
					<c:if test="${!empty requestScope.resultadoBusqueda}">
					<c:forEach begin="0" items="${requestScope.resultadoBusqueda}" var="fila" varStatus="st">
					<input type='hidden' name='nutEstudiante' value='<c:out value="${fila[0]}"/>'>
					<input type='hidden' name='nutJerarquia' value='<c:out value="${fila[7]}"/>'>
					<tr>
						<tD class='Fila<c:out value="${st.count%2}"/>'><B><c:out value="${st.count}"/></B></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><script>document.write(disc[<c:out value="${fila[8]}"/>]);</script>&nbsp;</td>
						<td class='Fila<c:out value="${st.count%2}"/>'><center><input class='Fila<c:out value="${st.count%2}"/>' type='text' title='en Centimetros' name='nutEstatura' size='4' maxlength='5' onKeyPress='return acepteNumeros(event,this)' value='<c:out value="${fila[5]}"/>'></center></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><center><input class='Fila<c:out value="${st.count%2}"/>' type='text' title='en Kilogramos' name='nutPeso' size='4' maxlength='5' onKeyPress='return acepteNumeros(event,this)' value='<c:out value="${fila[6]}"/>'></center></td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
</form>