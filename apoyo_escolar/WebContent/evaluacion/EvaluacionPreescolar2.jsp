<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="tip" value="6" scope="page"/>
<jsp:useBean id="filtroComportamiento" class="siges.evaluacion.beans.FiltroComportamiento" scope="session"/><jsp:setProperty name="filtroComportamiento" property="*"/>
		<script language="JavaScript">
			<!--
			var nav4=window.Event ? true : false;

			function cambioNota(obj){
				var tab=parseInt(obj.tabIndex);
				var forma=document.frmNuevo;
				if(forma.eval2[tab+1]){
					forma.eval2[tab+1].focus();
				}
			}
			
			function cancelar(){
				parent.location.href='<c:url value="/bienvenida.jsp"/>';
			}

			function valorar(obj){
        if(obj.checked){
        	document.frmNuevo.filCerrar.value='1';
        }else{
        	document.frmNuevo.filCerrar.value='0';
        }
			}
			
			function hacerValidaciones_frmNuevo(forma){
				/*
				if(forma.eval){
					if(forma.eval.length){
						for(var i=0;i<forma.eval.length;i++){						
							if(trim(forma.eval3[i].value)!=''){
								if(forma.eval2[i].selectedIndex==0){
									validarLista(forma.eval2[i],'- Nota (necesaria para registrar la observación)',1)
								}
							}
						}
					}
				}
				*/
			}
			
			function updateDatos(forma){
				if(forma.filCerrar_.checked){
					if(confirm("Seleccionó la opción 'cerrar Notas', y una vez cerrado, no se podra abrir de nuevo para evaluación, ¿confirma el cierre? \n Si pulsa Aceptar, se guardará y se cerrará las notas.\n Si pulsa Cancelar, se guardará y no se cerrará las notas.")){
						forma.filCerrar_.checked=true; valorar(forma.filCerrar_);
					}else{
						forma.filCerrar_.checked=false; valorar(forma.filCerrar_);
					}
				}
				
				//8/Oct/2021 John Heredia se inactiva validacion metodo eval.length no permite obtener cuando la tabla solo tiene 1 registro
			/*	if(forma.eval){
					if(forma.eval.length){
						for(var i=0;i<forma.eval.length;i++){
							//if(forma.eval2[i].selectedIndex==0){
								//forma.eval[i].disabled=true;
							//	forma.eval[i].value='-99';
							//}else{
								forma.eval[i].value=forma.eval[i].value+'|'+forma.eval2[i].options[forma.eval2[i].selectedIndex].value+'|'+forma.eval3[i].value;
							//}
						}
					}
				}*/
				return true;
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					if(updateDatos(document.frmNuevo)){
						document.frmNuevo.tipo.value=tipo;
						document.frmNuevo.cmd.value="Aceptar";
						document.frmNuevo.submit();
					}	
				}
			}
			function getCantidad(forma,tipo,nombre){
				var jj=0;
				for(var i=0;i<forma.elements.length;i++){
					if(forma.elements[i].type==tipo && forma.elements[i].name==nombre){jj++;}
				}
				return jj;
			}
			
			function inhabilitar(forma){
				var jj=getCantidad(forma,"hidden","eval");
				if(jj>1){
					for(var i=0;i<forma.eval.length;i++){
						forma.eval2[i].disabled=true;
						forma.eval3[i].disabled=true;
					}
				}else{
					forma.eval2.disabled=true;
					forma.eval3.disabled=true;
				}
				forma.action='';
				forma.target='_blank';
				forma.filCerrar_.disabled=true;
				forma.cmd0.style.display='none';
			}
			//-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/evaluacion/Save.jsp"/>' METHOD="POST" name='frmNuevo' onSubmit="return validarForma(frmNuevo)">
		<input type="hidden" name="filCerrar" value='<c:out value="${sessionScope.filtroComportamiento.filCerrar}"/>'>
		<input type="hidden" name="filLectura" value='<c:out value="${sessionScope.filtroComportamiento.filLectura}"/>'>
		<input type="hidden" name="cmd" value="">
		<input type="hidden" name="ext2">
		<INPUT TYPE="hidden" NAME="height" VALUE='125'>
		<INPUT TYPE="hidden" NAME="tipo"  VALUE='<c:out value="${pageScope.tip}"/>'>
		
		<c:if test="${sessionScope.TIPO_EVAL_PREESCOLAR=='2'}">
			<table border="0" align="center" width="100%">
				<tr><th><span class="style2">Evaluación por asignatura</span><th></tr>
			</table>
		</c:if>
	
		<c:if test="${empty requestScope.estudiantesDim}">
	    <table border="0" align="center" width="100%">
			<tr><th>No hay estudiantes a evaluar<th></tr>
		</table>
		</c:if>
		<c:if test="${!empty requestScope.estudiantesDim}">
		
		
			<table style="display:" id='t1' name='t1' width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
			<caption>Formulario de evaluación</caption>
				<tr>
			    <td colspan="6">
						<input class='boton' id="cmd0" name="cmd0" type="button" value="Guardar" style="display:" onClick="guardar(document.frmNuevo.tipo.value)">
					</td>
					<c:if test="${sessionScope.NivelPermiso=='1'}"><th><span class="style2">El registro de evaluación es de solo lectura</span></th></c:if>
					<c:if test="${sessionScope.filtroComportamiento.filCerrar=='1'}"><th><span class="style2">El registro de evaluación esta cerrado</span></th></c:if>
					<c:if test="${sessionScope.filtroComportamiento.filLectura=='1'}"><th><span class="style2">No tiene permiso de evaluar esta lista</span></th></c:if>
					<td align="right">Cerrar notas: <input type="checkbox" name="filCerrar_" onClick='valorar(this)' value='1' <c:if test="${sessionScope.filtroComportamiento.filCerrar==1}">CHECKED</c:if>></td>
				</tr>
			</table>
	    <table style="display:" id='t2' name='t2' width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>
					<th class="EncabezadoColumna" colspan='3'>Datos de estudiante</th>
					<th class="EncabezadoColumna" rowspan='2'>Nota<br></th>
					<th class="EncabezadoColumna" rowspan='2'>Observación</th>
				</tr>
				<tr>
					<th class="EncabezadoColumna" width='1%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='23%'>Apellidos</th>
					<th class="EncabezadoColumna" width='20%'>Nombres</th>
				</tr>
				<c:forEach begin="0" items="${requestScope.estudiantesDim}" var="estudiante" varStatus="st2">
				<input type="hidden" name="eval" value='<c:out value="${estudiante.evalCodigo}"/>'>
				<tr>
					<td class='Fila<c:out value="${st2.count%2}"/>' align='center'><b><c:out value="${estudiante.evalConsecutivo}"/></b></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${estudiante.evalApellido}"/></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${estudiante.evalNombre}"/></td>
					<th class='Fila<c:out value="${st2.count%2}"/>' >
						<SELECT NAME='eval2' class='Fila<c:out value="${st2.count%2}"/>' style="width:120px;" tabindex='<c:out value="${st2.index}"/>' onchange="javaScript:cambioNota(this)">
							<option value='-99'>&nbsp;</option>
							<c:forEach begin="0" items="${requestScope.escalaDim}" var="escala">
							<option value='<c:out value="${escala.codigo}"/>' <c:if test="${escala.codigo==estudiante.evalNota}">SELECTED</c:if>><c:out value="${escala.nombre}"/></option>
							</c:forEach>
						</select>
					</th>
					<th class='Fila<c:out value="${st2.count%2}"/>' >
						<textarea class='Fila<c:out value="${st2.count%2}"/>' name='eval3' cols="75" rows="2" wrap='virtual'><c:out value="${estudiante.evalObservacion}"/></textarea>
					</th>
				</tr>
				</c:forEach>
			</table>
		</c:if>
		</form>
<script>
<c:if test="${sessionScope.filtroComportamiento.filCerrar=='1' || sessionScope.filtroComportamiento.filLectura=='1'}">inhabilitar(document.frmNuevo);</c:if>
//INHABILITAR COMPONENTES SI LA EVALAUCION DE PREESCOLAR ES POR ASIGNATURA
<c:if test="${sessionScope.TIPO_EVAL_PREESCOLAR=='2'}">inhabilitar(document.frmNuevo);</c:if>
</script>