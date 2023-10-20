<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="organizacionParams" class="siges.institucion.organizacion.beans.Params" scope="page"/>
<%@include file="../../parametros.jsp"%>
	<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje='javaScript'>
			<!--
			var fichaLideres=1;
			var fichaGobierno=1;
			var fichaAsociacion=1;
			function cancelar(){
					document.frm.cmd.value="Cancelar";
					document.frm.submit();
			}
			
			function lanzar(tipo){
				document.frm.tipo2.value=tipo;
				document.frm.target='centro';
				document.frm.action="<c:url value="/institucion/organizacion/ControllerOrganizacionEdit.do"/>";
				document.frm.submit();
			}
			
			function editar(forma,tipo,nombre,tipo2){	
				forma.target='centro';
				forma.ext.value='';
				forma.tipo2.value=tipo;
				forma.cmd.value=nombre;
				if(forma.cmd.value=='Eliminar'){
					if (isChecked(forma.r)){
						if(!confirm("Realmente desea eliminarlo?"))
							return false;
					}
				}
				if(!validarForma(forma)) return false;
				if(forma.cmd.value=='Nuevo' || forma.cmd.value=='Editar'){
					remote = window.open("","3","width=650,height=250,resizable=no,toolbar=no,directories=no,menubar=no,status=yes");
					forma.target='3';
					forma.ext.value='1';
					forma.tipo2.value=tipo2;
					remote.moveTo(200,200);
					if (remote.opener == null) remote.opener = window;
					remote.opener.name = "centro";
					remote.focus();
				}
				return true;
			}
			
			function hacerValidaciones_frm(forma){
				if(forma.cmd.value!='Nuevo')
					validarSeleccion(forma.r, "- Debe seleccionar un item")
			}
			
			function hacerValidaciones_frm2(forma){
				if(forma.cmd.value!='Nuevo'){
					validarSeleccion(forma.r, "- Debe seleccionar un item")
				}	
			}
			
			function setP(forma,s,t,u){
				forma.s.value=s;
				forma.t.value=t;
				forma.u.value=u;
			}
		-->
	</script>
	<%@include file="../../mensaje.jsp"%>
	<form method="post" align="center" name="frm"  onSubmit="return validarForma(frm)" action='<c:url value="/institucion/organizacion/ControllerOrganizacionEdit.do"/>'>
	<input type="hidden" name="tipo2" value='<c:out value="${pageScope.organizacionParams.FICHA_GOB}"/>'>
	<input type="hidden" name="forma1" value='<c:out value="${pageScope.organizacionParams.FICHA_GOB}"/>'>
	<input type="hidden" name="forma2" value='<c:out value="${pageScope.organizacionParams.FICHA_GOB2}"/>'>
	<input type="hidden" name="cmd" value="">
	<input type="hidden" name="ext" value="">
	<INPUT TYPE="hidden" NAME="height" VALUE='150'>
  <input type="hidden" name="s" value="">
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoInstitucion.inscoddane}"/>">
	<!-- FICHAS A MOSTRAR AL USUARIO -->
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%">
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>			
				<td rowspan="2" width="600" bgcolor="#FFFFFF">
					<script>
						if(fichaLideres==1)document.write('<a href="javaScript:lanzar(<c:out value="${pageScope.organizacionParams.FICHA_LID}"/>)"><img src="<c:url value="/etc/img/tabs/lideres_0.gif"/>" alt=""  height="26" border="0"></a>');
						if(fichaGobierno==1)document.write('<img src="<c:url value="/etc/img/tabs/gobierno_escolar_1.gif"/>" alt=""  height="26" border="0">');
						if(fichaAsociacion==1)document.write('<a href="javaScript:lanzar(<c:out value="${pageScope.organizacionParams.FICHA_ASO}"/>)"><img src="<c:url value="/etc/img/tabs/asociacion_padres_0.gif"/>" alt=""  height="26" border="0"></a>');
					</script>
				</td>
			</tr>
		</table>
	<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#ffffff">
		<caption>CONSEJO DIRECTIVO</caption>
			<tr>
				<th colspan='4' align='left'>
					<c:if test="${!empty requestScope.filtroConsejos}">
					<input name="cmd1" type="submit" class='boton' value="Editar" onClick="return editar(document.frm,document.frm.forma1.value,'Editar',document.frm.forma2.value)" >
					<input name="cmd1" type="submit" class='boton' value="Eliminar" onClick="return editar(document.frm,document.frm.forma1.value,'Eliminar',document.frm.forma2.value)"  style='display:<c:out value="${permisoBoton}"/>'>
					</c:if>
					<input name="cmd1" type="submit" class='boton' value="Nuevo" onClick="return editar(document.frm,document.frm.forma1.value,'Nuevo',document.frm.forma2.value)"  style='display:<c:out value="${permisoBoton}"/>'>
				</th>
			</tr>
			<tr>
				<th width='10' class="EncabezadoColumna">&nbsp;</th>
				<th width='15%' class="EncabezadoColumna"><b>Identificación</b></th>
				<th width='40%' class="EncabezadoColumna"><b>Nombre</b></th>
				<th width='35%' class="EncabezadoColumna"><b>Cargo</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroConsejos}" var="fila" varStatus="st">
			<tr>				
				<td class='Fila<c:out value="${st.count%2}"/>' width='1%'>
					<input type='radio' name='r' value='<c:out value="${fila[0]}"/>' onClick='document.frm.s.value="<c:out value="${fila[1]}"/>"'>
				</td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/>: <c:out value="${fila[3]}"/></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[5]}"/></td>
			</tr>
			</c:forEach>
		</table>
	</form>
	<form method="post" align="center" name="frm2"  onSubmit="return validarForma(frm2)" action='<c:url value="/institucion/organizacion/ControllerOrganizacionEdit.do"/>'>
	<input type="hidden" name="tipo2" value='<c:out value="${pageScope.organizacionParams.FICHA_GOB3}"/>'>
	<input type="hidden" name="forma1" value='<c:out value="${pageScope.organizacionParams.FICHA_GOB3}"/>'>
	<input type="hidden" name="forma2" value='<c:out value="${pageScope.organizacionParams.FICHA_GOB4}"/>'>
	<input type="hidden" name="cmd" value="">
	<input type="hidden" name="ext" value="">
	<INPUT TYPE="hidden" NAME="height" VALUE='150'>
  <input type="hidden" name="s" value="">
  <input type="hidden" name="t" value="">
  <input type="hidden" name="u" value="">
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoInstitucion.inscoddane}"/>">
	<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#ffffff">
		<caption>CONSEJO ACADÉMICO</caption>
			<tr>
				<th colspan='4' align='left'>
					<c:if test="${!empty requestScope.filtroConsejos2}">
					<input name="cmd1" type="submit" class='boton' value="Editar" onClick="return editar(document.frm2,document.frm2.forma1.value,'Editar',document.frm2.forma2.value)" >
					<input name="cmd1" type="submit" class='boton' value="Eliminar" onClick="return editar(document.frm2,document.frm2.forma1.value,'Eliminar',document.frm2.forma2.value)"  style='display:<c:out value="${permisoBoton}"/>'>
					</c:if>
					<input name="cmd1" type="submit" class='boton' value="Nuevo" onClick="return editar(document.frm2,document.frm2.forma1.value,'Nuevo',document.frm2.forma2.value)"  style='display:<c:out value="${permisoBoton}"/>'>
				</th>
			</tr>
			<tr>
				<th width='10' class="EncabezadoColumna">&nbsp;</th>
				<th width='15%' class="EncabezadoColumna"><b>Identificación</b></th>
				<th width='40%' class="EncabezadoColumna"><b>Nombre</b></th>
				<th width='40%' class="EncabezadoColumna"><b>Cargo</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroConsejos2}" var="fila" varStatus="st">
			<tr>				
				<td class='Fila<c:out value="${st.count%2}"/>' width='1%'>
					<input type='radio' name='r' value='<c:out value="${fila[0]}"/>' onClick='setP(document.frm2,"<c:out value="${fila[1]}"/>","<c:out value="${fila[2]}"/>","<c:out value="${fila[3]}"/>");'>
				</td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/>: <c:out value="${fila[5]}"/></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[6]}"/></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[7]}"/></td>
			</tr>
			</c:forEach>
		</table>
	</form>