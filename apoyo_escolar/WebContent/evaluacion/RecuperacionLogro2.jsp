<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtroEvaluacion" class="siges.evaluacion.beans.FiltroBeanEvaluacion" scope="session">
<jsp:setProperty name="filtroEvaluacion" property="*"/>
</jsp:useBean>
<%@include file="../parametros.jsp"%>
<script language="JavaScript">
			<!--
			var log=0;
			var est=0;
			var nav4=window.Event ? true : false;
			var ar = new Array();
			var opt = new Array();
			var opt2 = new Array();
			var opt3 = new Array();
			var i=0;
			var combo0='';
			var s="";
			<c:forEach begin="0" items="${filtroEvaluacion.motivo}" var="fila3">s="<c:out value="${fila3}"/>";
			ar=s.split("|");opt[i]=ar[0]+"|"+ar[1]+"|"+ar[2];opt2[i]=ar[4];opt3[i]=ar[3];i++;
			</c:forEach>					
			function acepteNumeros(n,eve,combo,a,b){
				var key=nav4?eve.which :eve.keyCode;
				if(key==13){next(combo,a,b);return;}
				var tecla=String.fromCharCode(key).toLowerCase();
				if (combo[n].length) {
					for (var i = 0; i < combo[n].length; i++) {
						if (combo[n].options[i].text){
							if(combo[n].options[i].text.toLowerCase()==tecla){
								next(combo,a,b);
								return;
							}
						}
					}
				}
			}
			function hacerValidaciones_frmNuevo(forma){}
			
			function borrarItem(combo){
				if(navigator.appName == "Netscape") combo.options[0] = null;
					else combo.remove(0);
			}
			
			function combo(forma,lista,s){					
				if(lista==0){ combo0=s;}
				for(var i=0;i<opt.length;i++){
					//alert(""+s+"\n"+opt[i]+"\n-"+opt2[i]+"-");
					if(s==opt[i] && opt2[i]!='' && forma.nota[lista].options){
						forma.nota[lista].options[2] = new Option("R",s+"|-99","SELECTED");
						forma.nota[lista].selectedIndex = 2;
						borrarItem(forma.nota[lista]);
						//forma.nota[lista].disabled=true;
						break;
					}else{
						if(s==opt[i] && opt3[i]=="7"){
							forma.nota[lista].selectedIndex = 1;
							if(forma.nota[lista].options) forma.nota[lista].options[2] = new Option("R",s+"|-99");
							break;
						}
					}
				}
			}
			function ponerRecuperacion(nota){
				if(nota.length){
					for(var i=0;i<nota.length;i++){
						if(nota[i].options[nota[i].selectedIndex].text=='S' || nota[i].options[nota[i].selectedIndex].text=='' || nota[i].options[nota[i].selectedIndex].text=='TX'){
							nota[i].disabled=true;
						}
						if(nota[i].options[nota[i].selectedIndex].text=='N'){
							for(var j=0;j<nota[i].options.length;j++){
								if(nota[i].options[j].text=='S'){
									if(navigator.appName == "Netscape") nota[i].options[j] = null;
									else nota[i].remove(j);
									break;
								}
							}
						}
					}
				}
			}
						
			function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			function guardar(tipo){
			  if(validarForma(document.frmNuevo)){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value="Aceptar";
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
				if(combo[posicion] && combo[posicion].disabled==false){
					combo[posicion].focus();
				}
				//else alert(posicion);
			}
			function inhabilitar(forma){
				for(var i=0;i<forma.nota.length;i++){
					forma.nota[i].disabled=true;
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
		<INPUT TYPE="hidden" NAME="tipo"  VALUE="">
		<input type="hidden" name="ext2">
		<INPUT TYPE="hidden" NAME="height" VALUE='150'>
		<input type="hidden" name="lectura" value='<c:out value="${sessionScope.filtroEvaluacion.lectura}"/>'>
		<c:if test="${empty sessionScope.filtroResultado || empty sessionScope.filtroLogros}">
	    <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
			<tr><c:if test="${empty sessionScope.filtroResultado}"><th>No hay estudiantes a evaluar<th></c:if>
			<c:if test="${empty sessionScope.filtroLogros}"><th>No hay logros a evaluar</th></c:if></tr>	
		</table>
			</c:if>
			<c:if test="${!empty sessionScope.filtroResultado && !empty sessionScope.filtroLogros}">
			<table style="display:" id='t1' name='t1' width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
				<caption>Formulario de evaluación</caption>
				<tr>
			    <td width="20%">
						<input class='boton' id="cmd0" name="cmd0" style="display:" type="button" value="Guardar" onClick="guardar(5)">
						<c:if test="${!empty requestScope.plantilla}"><a href='<c:url value="/${requestScope.plantilla}"/>' target='_blank'><img src="../etc/img/xls.gif" alt="Descargar Archivo Excel" border="0"></a></c:if>
				</td>
					<c:if test="${sessionScope.filtroEvaluacion.cerrar=='1'}"><th><span class="style2">El registro de evaluación esta cerrado</span></th></c:if>
					<c:if test="${sessionScope.filtroEvaluacion.lectura=='1'}"><th><span class="style2">No tiene permiso de evaluar esta lista</span></th></c:if>
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
					<th width='1%' class="EncabezadoColumna" >&nbsp;</th>
					<th class="EncabezadoColumna" >Apellidos</th>
					<th class="EncabezadoColumna" >Nombres</th>
					<c:forEach begin="0" items="${sessionScope.filtroLogros}" var="fila2" varStatus="st"><th class="EncabezadoColumna" ><c:out value="${fila2[1]}"/></th></c:forEach>
				</tr>
				<c:set var="con" value="-1"/>
				<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="fila" varStatus="st2"><c:set var="x" value="${fila[0]}" scope="request"/>
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
					<td  class='Fila<c:out value="${st2.count%2}"/>' align='center'><b><c:out value="${st2.count}"/></b></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td>
					<c:forEach begin="0" items="${sessionScope.filtroLogros}" var="fila2" varStatus="st"><c:set var="y" value="${fila2[0]}" scope="request"/><c:set var="y2" value="${fila2[3]}" scope="request"/>
					<th class='Fila<c:out value="${st2.count%2}"/>' ><c:set var="con" value="${con+1}"/>
						<SELECT  class='Fila<c:out value="${st2.count%2}"/>' NAME='nota' onKeyPress='acepteNumeros(<c:out value="${con}"/>,event,document.frmNuevo.nota,<c:out value="${st2.count}"/>,<c:out value="${st.count}"/>)' onChange='next(document.frmNuevo.nota,<c:out value="${st2.count}"/>,<c:out value="${st.count}"/>)'>
							<c:forEach begin="0" items="${sessionScope.filtroNotaRec}" var="fila3"><c:set var="z" value="${fila3[0]}" scope="request"/>
							<option value='<c:out value="${requestScope.x}"/>|<c:out value="${requestScope.y}"/>|<c:out value="${requestScope.y2}"/>|<c:out value="${requestScope.z}"/>'><c:out value="${fila3[1]}"/></option>
							</c:forEach>
						</select>
						<script>combo(document.frmNuevo,<c:out value="${con}"/>,'<c:out value="${requestScope.x}"/>|<c:out value="${requestScope.y}"/>|<c:out value="${requestScope.y2}"/>');</script>
					</th></c:forEach>
				</tr></c:forEach>
			</table>
			<script>if(document.frmNuevo.nota.length) combo(document.frmNuevo,0,combo0);</script>
			</c:if>
		</form>
<script>
<c:if test="${sessionScope.filtroEvaluacion.cerrar=='1'}">inhabilitar(document.frmNuevo);</c:if>
<c:if test="${sessionScope.filtroEvaluacion.lectura=='1'}">inhabilitar(document.frmNuevo);</c:if>
ponerRecuperacion(document.frmNuevo.nota);
</script>