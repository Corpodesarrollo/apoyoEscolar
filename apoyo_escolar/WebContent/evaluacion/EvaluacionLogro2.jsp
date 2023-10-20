<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtroEvaluacion" class="siges.evaluacion.beans.FiltroBeanEvaluacion" scope="session"/><jsp:setProperty name="filtroEvaluacion" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="1" scope="page"/>
<script language="JavaScript">
			<!--
			var soloLectura=<c:out value="${sessionScope.NivelPermiso}"/>;
			var log=0;
			var est=0;
			var nav4=window.Event ? true : false;
			function getCantidad(forma,tipo,nombre){
				var jj=0;
				for(var i=0;i<forma.elements.length;i++){
					if(forma.elements[i].type==tipo && forma.elements[i].name==nombre){jj++;}
				}
				return jj;
			}

			function ponerTodos(forma,m,n){
				if (!confirm('¿Está seguro de que quiere reemplazar la evaluación actual en toda la columna?')) return;				
				var pos=-1;
				var jj=getCantidad(forma,"select-one","encNota");
				if(jj>1){
					pos=forma.encNota[m].selectedIndex;
				}else{
					pos=forma.encNota.selectedIndex;
				}
				jj=getCantidad(forma,"select-one","nota");
				if(jj>1){
					for(var i=0;i<forma.nota.length;i++){
						var valor=forma.nota[i].options[1].value.split('|');
						var id=valor[1];
						if(id==n){
							forma.nota[i].selectedIndex=pos;
						}
					}
				}else{
						var valor=forma.nota.options[1].value.split('|');
						var id=valor[1];
						if(id==n){
							forma.nota.selectedIndex=pos;
						}
				}
			}
			
			function acepteNumeros(eve,combo,a,b){				
				var key=nav4?eve.which :eve.keyCode;
				if(key==13){next(combo,a,b);return;}
				var tecla=String.fromCharCode(key).toLowerCase();				
				if (combo[0].length) {
					for (var i = 0; i < combo[0].length; i++) {
						if (combo[0].options[i].text){
							if(combo[0].options[i].text.toLowerCase()==tecla){
								next(combo,a,b);
								return;
							}
						}
					}
				}
			}
			function hacerValidaciones_frmNuevo(forma){
			}
			
			function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Aceptar";
					//document.frmNuevo.ext2.value='/evaluacion/ControllerEvaluacionEdit.do?tipo=1';
					document.frmNuevo.submit();
				}	
			}
			function next(combo,estudiante,logro){
				if(estudiante==est){
					estudiante=1;
					if(logro==log)
						logro=1;
					else
						logro++;
				}else
					estudiante++;
				
				var posicion=((estudiante*log)-(log-logro))-1;
				if(combo[posicion]){
					combo[posicion].focus();
				}
			}
			function inhabilitar(forma){
				var jj=0;
				for(var i=0;i<forma.elements.length;i++){
					if(forma.elements[i].type == "select-one" && forma.elements[i].name=='nota'){jj++;}
				}
				if(jj>1){
					for(var i=0;i<forma.nota.length;i++){
						forma.nota[i].disabled=true;
					}
				}else{
					forma.nota.disabled=true;
				}
					forma.action='';
					forma.target='_blank';
					forma.cmd0.style.display='none';
			}
			//-->
		</script>
<%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/evaluacion/EvaluacionGuardar.jsp"/>' METHOD="POST" name='frmNuevo' onSubmit="return validarForma(frmNuevo)">
		<input type="hidden" name="cmd" value="">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${pageScope.tip}"/>'>
		<input type="hidden" name="ext2">
		<INPUT TYPE="hidden" NAME="height" VALUE='125'>
		<input type="hidden" name="lectura" value='<c:out value="${sessionScope.filtroEvaluacion.lectura}"/>'>
			<c:if test="${requestScope.evalMax==1}">
	    <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
				<tr><th>No se puede evaluar en línea este grupo porque la cantidad de logros excede el limite permitido (15). <br>Debe evaluar este grupo por medio de plantillas.<th></tr>	
			</table>
			</c:if>
		<c:if test="${requestScope.evalMax==0 && (empty sessionScope.filtroResultado || empty sessionScope.filtroLogros)}">
	    <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
				<tr><c:if test="${empty sessionScope.filtroResultado}"><th>No hay estudiantes a evaluar<th></c:if>
				<c:if test="${empty sessionScope.filtroLogros}"><th>No hay logros a evaluar</th></c:if></tr>	
			</table>
			</c:if>
			<c:if test="${requestScope.evalMax==0 && (!empty sessionScope.filtroResultado && !empty sessionScope.filtroLogros)}">
			<table style="display:" id='t1' name='t1' 	width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<caption>Formulario de evaluación</caption>
				<tr>
			    <td>
						<input class='boton' id="cmd0" name="cmd0" style="display:" type="button" value="Guardar" onClick="guardar(1)">
						<c:if test="${!empty requestScope.plantilla}"><a href='<c:url value="/${requestScope.plantilla}"/>' target='_blank'><img src="../etc/img/xls.gif" alt="Descargar Archivo Excel" border="0"></a></c:if>
				</td><script>if(soloLectura==1)document.write('<th><span class="style2">Lista de evaluación solo para consultar</span></th>');</script>
					<c:if test="${sessionScope.filtroEvaluacion.cerrar=='1'}"><th><span class="style2">El registro de evaluación está cerrado</span></th></c:if>
					<c:if test="${sessionScope.filtroEvaluacion.lectura=='1'}"><th><span class="style2">No tiene permiso de evaluar esta lista</span></th></c:if>
					<th><span class="style2">Nota:</span> Sitúe el cursor sobre cada abreviatura para ver el nombre.</th>
				</tr>
			</table>
	    <table style="display:" id='t2' name='t2' width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>					
					<th class="EncabezadoColumna" colspan='3'>Datos de estudiante</th>					
					<c:forEach begin="0" items="${sessionScope.filtroLogros}" var="fila2" varStatus="st">
						<c:if test="${st.last}"><script>log=<c:out value="${st.count}"/>;</script>
							<th class="EncabezadoColumna" colspan='<c:out value="${st.count}"/>'>Evaluación de logros</th>
						</c:if>
					</c:forEach>
				</tr>
				<tr>
					<th class="EncabezadoColumna" width='1%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='23%'>Apellidos</th>
					<th class="EncabezadoColumna" width='20%'>Nombres</th>
					<c:forEach begin="0" items="${sessionScope.filtroLogros}" var="fila2" varStatus="st">
					<th class="EncabezadoColumna" ><dfn title='<c:out value="${fila2[2]}"/>'><c:out value="${fila2[1]}"/></dfn><br>
						<SELECT NAME='encNota' style='BACKGROUND:#ffffff;COLOR:#000000;' onKeyPress='ponerTodos(document.frmNuevo,<c:out value="${st.index}"/>,"<c:out value="${fila2[0]}"/>")' onchange='ponerTodos(document.frmNuevo,<c:out value="${st.index}"/>,"<c:out value="${fila2[0]}"/>")'>
							<option value='-1'>&nbsp;</option>
							<c:forEach begin="0" items="${sessionScope.filtroNota}" var="fila3">
							<option value='<c:out value="${fila3[0]}"/>'><c:out value="${fila3[1]}"/></option>
							</c:forEach>
							<!-- <option value='-2'>TX</option> -->
						</select>
					</th></c:forEach>
				</tr>
				<tr><th colspan='3'></th></tr>
				<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="fila" varStatus="st2">
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
					<td class='Fila<c:out value="${st2.count%2}"/>' align='center'><b><c:out value="${st2.count}"/></b></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td>
					<c:forEach begin="0" items="${sessionScope.filtroLogros}" var="fila2" varStatus="st">
					<th class='Fila<c:out value="${st2.count%2}"/>' >
						<SELECT NAME='nota' class='Fila<c:out value="${st2.count%2}"/>' onKeyPress='acepteNumeros(event,document.frmNuevo.nota,<c:out value="${st2.count}"/>,<c:out value="${st.count}"/>)' onChange='next(document.frmNuevo.nota,<c:out value="${st2.count}"/>,<c:out value="${st.count}"/>)'><option value='-1'>&nbsp;</option><c:set var="x" value="${fila[0]}" scope="request"/><c:set var="y" value="${fila2[0]}" scope="request"/><c:set var="y2" value="${fila2[3]}" scope="request"/>
							
							<c:forEach begin="0" items="${sessionScope.filtroNota}" var="fila3">
							<c:set var="z" value="${fila3[0]}" scope="request"/>
							<option value='<c:out value="${requestScope.x}"/>|<c:out value="${requestScope.y}"/>|<c:out value="${requestScope.y2}"/>|<c:out value="${requestScope.z}"/>' 
							<%=filtroEvaluacion.seleccion((String)request.getAttribute("x")+"|"+(String)request.getAttribute("y")+"|"+(String)request.getAttribute("y2")+"|"+(String)request.getAttribute("z"))%>>
							<c:out value="${fila3[1]}"/>
							</option>
							</c:forEach>
							<!-- <option value='<c:out value="${requestScope.x}"/>|<c:out value="${requestScope.y}"/>|<c:out value="${requestScope.y2}"/>|-2' 
							 <%=filtroEvaluacion.seleccion((String)request.getAttribute("x")+"|"+(String)request.getAttribute("y")+"|"+(String)request.getAttribute("y2")+"|-2")%>>TX</option> -->
						</select>
					</th>
					</c:forEach>
				</tr>
				</c:forEach>
			</table>
			</c:if>
		</form>
<script>
<c:if test="${sessionScope.filtroEvaluacion.cerrar=='1'}">inhabilitar(document.frmNuevo);</c:if>
<c:if test="${sessionScope.filtroEvaluacion.lectura=='1'}">inhabilitar(document.frmNuevo);</c:if>
if(soloLectura==1){ inhabilitar(document.frmNuevo);}
</script>