<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtroEvaluacion" class="siges.evaluacion.beans.FiltroBeanEvaluacion" scope="session"/><jsp:setProperty name="filtroEvaluacion" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="3" scope="page"/>
<script language="JavaScript">
			<!--
			var soloLectura=<c:out value="${sessionScope.NivelPermiso}"/>;
			var log=1;
			function getCantidad(forma,tipo,nombre){
				var jj=0;
				for(var i=0;i<forma.elements.length;i++){
					if(forma.elements[i].type==tipo && forma.elements[i].name==nombre){jj++;}
				}
				return jj;
			}

			function ponerChecks2(forma){
				var a = new Array();
				var b = new Array();
				var jj=getCantidad(forma,"hidden","nota");
				var kk=getCantidad(forma,"checkbox","chkdesc");
				if(jj>1){
					for(var i=0;i<forma.nota.length;i++){
						a=forma.nota[i].value.split('|');	
						forma.nota[i].value=a[0]+'|';
						if(kk>1){
							for(var ii=0;ii<forma.chkdesc.length;ii++){
								b=forma.chkdesc[ii].value.split('|');
								for(var j=0;j<b.length;j++){
									if(a[0]==b[0]){
										if(forma.chkdesc[ii].checked){
											forma.nota[i].value=forma.nota[i].value+b[1]+',';
											break;
										}
									}
								}
							}
						}else{
								b=forma.chkdesc.value.split('|');
								for(var j=0;j<b.length;j++){
									if(a[0]==b[0]){
										if(forma.chkdesc.checked){
											forma.nota[i].value=forma.nota[i].value+b[1]+',';
											break;
										}
									}
								}
						}
					}
				}else{
						a=forma.nota.value.split('|');	
						forma.nota.value=a[0]+'|';
						if(kk>1){
							for(var ii=0;ii<forma.chkdesc.length;ii++){
								b=forma.chkdesc[ii].value.split('|');
								for(var j=0;j<b.length;j++){
									if(a[0]==b[0]){
										if(forma.chkdesc[ii].checked){
											forma.nota.value=forma.nota.value+b[1]+',';
											break;
										}
									}
								}
							}
						}else{
								b=forma.chkdesc.value.split('|');
								for(var j=0;j<b.length;j++){
									if(a[0]==b[0]){
										if(forma.chkdesc.checked){
											forma.nota.value=forma.nota.value+b[1]+',';
											break;
										}
									}
								}
						}						
					}
				}
			
			function ponerChecks(forma){
				var a = new Array();
				var b = new Array();
				var c = new Array();
				var jj=getCantidad(forma,"hidden","nota");
				var kk=getCantidad(forma,"checkbox","chkdesc");
				if(forma.nota){
					if(jj>1){
						for(var i=0;i<forma.nota.length;i++){
							a=forma.nota[i].value.split('|');	
							if(kk>1){
							for(var ii=0;ii<forma.chkdesc.length;ii++){
								b=forma.chkdesc[ii].value.split('|');				
								for(var j=0;j<b.length;j++){
									if(a[0]==b[0]){
										c=a[1].split(',');
										for(var k=0;k<c.length;k++){
											if(c[k]==b[1]){
												forma.chkdesc[ii].checked=true;
												break;
											}
										}
									}
								}
							}
							}else{
									b=forma.chkdesc.value.split('|');				
									for(var j=0;j<b.length;j++){
										if(a[0]==b[0]){
										c=a[1].split(',');
										for(var k=0;k<c.length;k++){
											if(c[k]==b[1]){
												forma.chkdesc[ii].checked=true;
												break;
											}
										}
									}
								}
							}						
						}	
					}else{
							a=forma.nota.value.split('|');	
							if(kk>1){
							for(var ii=0;ii<forma.chkdesc.length;ii++){
								b=forma.chkdesc[ii].value.split('|');				
								for(var j=0;j<b.length;j++){
									if(a[0]==b[0]){
										c=a[1].split(',');
										for(var k=0;k<c.length;k++){
											if(c[k]==b[1]){
												forma.chkdesc[ii].checked=true;
												break;
											}
										}
									}
								}
							}
						}else{
								b=forma.chkdesc.value.split('|');				
								for(var j=0;j<b.length;j++){
									if(a[0]==b[0]){
										for(var k=1;k<a.length;k++){
											if(a[k]==b[1]){
												forma.chkdesc.checked=true;
												break;
											}
										}
										
									}
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
					ponerChecks2(document.frmNuevo);
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Aceptar";
					document.frmNuevo.submit();
				}	
			}
			
			function inhabilitar(forma){
				var jj=getCantidad(forma,"hidden","nota");
				var kk=getCantidad(forma,"checkbox","chkdesc");
				if(jj>1){
					for(var i=0;i<forma.nota.length;i++){
						forma.nota[i].disabled=true;
					}
				}else{
					forma.nota.disabled=true;
				}
				if(kk>1){
					for(var i=0;i<forma.chkdesc.length;i++){
						forma.chkdesc[i].disabled=true;
					}
				}else{
					forma.chkdesc.disabled=true;
				}
				forma.action='';
				forma.target='_blank';
				forma.cmd0.style.display='none';
			}
			//-->
		</script>
<%@include file="../mensaje.jsp"%>
	<font size="1"><FORM ACTION='<c:url value="/evaluacion/ListaDescriptor.jsp"/>?ext=1' METHOD="POST" name='fr'><input type="hidden" name="val" value=""><input type="hidden" name="num" value=""></form>
	<FORM ACTION='<c:url value="/evaluacion/EvaluacionGuardar.jsp"/>' METHOD="POST" name='frmNuevo' onSubmit="return validarForma(frmNuevo)">
		<input type="hidden" name="cmd" value="">
		<input type="hidden" name="ext2">
		<INPUT TYPE="hidden" NAME="height" VALUE='125'>
		<INPUT TYPE="hidden" NAME="tipo"  VALUE='<c:out value="${pageScope.tip}"/>'>
		<input type="hidden" name="lectura" value='<c:out value="${sessionScope.filtroEvaluacion.lectura}"/>'>
			<c:if test="${requestScope.evalMax==1}">
	    <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
				<tr><th>No se puede evaluar en línea este grupo porque la cantidad de descriptores excede el limite permitido (15). <br>Debe evaluar este grupo por medio de plantillas.<th></tr>	
			</table>
			</c:if>
			<c:if test="${requestScope.evalMax==0 && (empty sessionScope.filtroResultado || empty sessionScope.filtroDescriptores)}">
	    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr><c:if test="${empty sessionScope.filtroResultado}"><th>No hay Estudiantes a evaluar<th></c:if>
				<c:if test="${empty sessionScope.filtroDescriptores}"><th>No hay Descriptores a evaluar</th></c:if></tr>	
			</table>
			</c:if>
			<c:if test="${requestScope.evalMax==0 && (!empty sessionScope.filtroResultado && !empty sessionScope.filtroDescriptores)}">
			<table style="display:" id='t1' name='t1' width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
				<caption>Formulario de evaluación</caption>
				<tr>
					<td colspan="1">
					<input class='boton' id="cmd0" name="cmd0" style="display:" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)">
					</td><script>if(soloLectura==1)document.write('<th><span class="style2">Lista de evaluación solo para consultar</span></th>');</script>
					<c:if test="${sessionScope.filtroEvaluacion.cerrar=='1'}"><th><span class="style2">El registro de evaluación está cerrado</span></th></c:if>
					<c:if test="${sessionScope.filtroEvaluacion.lectura=='1'}"><th><span class="style2">No tiene permiso de evaluar esta lista</span></th></c:if>
					<th><span class="style2">Nota:</span> Sitúe el cursor sobre cada abreviatura para ver el nombre.</th>
				</tr>
			</table>
			<table style="display:" id='t2' name='t2' width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>					
					<th class="EncabezadoColumna" colspan='3'>Datos de estudiante</th>
					<th class="EncabezadoColumna" colspan='10'>&nbsp;</th>
				</tr>
				<tr>
					<th class="EncabezadoColumna" width='1%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='23%'>Apellidos</th>
					<th class="EncabezadoColumna" width='20%'>Nombres</th>
					<c:forEach begin="0" items="${sessionScope.filtroDescriptores}" var="fila3"><th class="EncabezadoColumna" ><dfn title='<c:out value="${fila3[3]}"/>'><c:out value="${fila3[1]}"/></dfn></th></c:forEach>
				</tr>
				<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="fila" varStatus="st2"><c:set var="x" value="${fila[0]}" scope="request"/>
				<tr>
					<td class='Fila<c:out value="${st2.count%2}"/>' align='center'><b><c:out value="${st2.count}"/></b></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td>
					<input type="hidden" name="nota" value='<%=filtroEvaluacion.seleccionDescriptor((String)request.getAttribute("x"))%>'>
					<c:forEach begin="0" items="${sessionScope.filtroDescriptores}" var="fila3">
						<th class='Fila<c:out value="${st2.count%2}"/>' ><input type='checkbox' class='Fila<c:out value="${st2.count%2}"/>' name='chkdesc' value='<c:out value="${fila[0]}"/>|<c:out value="${fila3[0]}"/>'></th>
					</c:forEach>
				</tr>
				</c:forEach>
				<script>ponerChecks(document.frmNuevo);</script>
			</table>
			</c:if>
		</form>
<script>
<c:if test="${sessionScope.filtroEvaluacion.cerrar=='1'}">inhabilitar(document.frmNuevo);</c:if>
<c:if test="${sessionScope.filtroEvaluacion.lectura=='1'}">inhabilitar(document.frmNuevo);</c:if>
if(soloLectura==1){ inhabilitar(document.frmNuevo);}
</script>