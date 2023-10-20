<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="organizacionParams" class="siges.institucion.organizacion.beans.Params" scope="page"/>
<%@include file="../../parametros.jsp"%>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje='javaScript'>			
			<!--
			var fichaLideres=1;
			var fichaGobierno=1;
			var fichaAsociacion=1;
			
			var personero=0;
			var contralor=0;
			var presidente=0;
			var consejo=0;
			<c:forEach begin="0" items="${requestScope.filtroLideres}" var="fila" varStatus="st">
				<c:if test="${fila[6]==pageScope.organizacionParams.PARAM_PERSONERO}">personero++;</c:if>
				<c:if test="${fila[6]==pageScope.organizacionParams.PARAM_CONTRALOR}">contralor++;</c:if>
				<c:if test="${fila[6]==pageScope.organizacionParams.PARAM_PRESIDENTE}">presidente++;</c:if>
				<c:if test="${fila[6]==pageScope.organizacionParams.PARAM_CONSEJO}">consejo++;</c:if>
			</c:forEach>
			
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
			
			function editar(forma,tipo,nombre,tipo2,lider){	
				forma.target='centro';
				forma.ext.value='';
				forma.tipo2.value=tipo;
				forma.cmd.value=nombre;
				forma.tipoLider.value=lider;
				if(forma.cmd.value=='Eliminar'){
					if (isChecked(forma.r)){
						if(!confirm("Realmente desea eliminarlo?"))
							return false;
					}
				}
				if(!validarForma(forma)) return false;
				if(forma.cmd.value=='Nuevo' || forma.cmd.value=='Editar'){
					remote = window.open("","3","width=600,height=250,resizable=no,toolbar=no,directories=no,menubar=no,status=yes");
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
			
			function setP(forma,val1,val2){
				forma.s.value=val1;
				forma.t.value=val2;
			}
			
			function hacerValidaciones_frm(forma){
				if(forma.cmd.value!='Nuevo')
					validarSeleccion(forma.r, "- Debe seleccionar un item")
			}
		//-->
	</script>
	<%@include file="../../mensaje.jsp"%>
	<form method="post" align="center" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/institucion/organizacion/ControllerOrganizacionEdit.do"/>'>
	<input type="hidden" name="tipo2" value='<c:out value="${pageScope.organizacionParams.FICHA_LID}"/>'>
	<input type="hidden" name="forma1" value='<c:out value="${pageScope.organizacionParams.FICHA_LID}"/>'>
	<input type="hidden" name="forma2" value='<c:out value="${pageScope.organizacionParams.FICHA_LID2}"/>'>
	<input type="hidden" name="tipoPersonero" value='<c:out value="${pageScope.organizacionParams.PARAM_PERSONERO}"/>'>
	<input type="hidden" name="tipoContralor" value='<c:out value="${pageScope.organizacionParams.PARAM_CONTRALOR}"/>'>
	<input type="hidden" name="tipoPresidente" value='<c:out value="${pageScope.organizacionParams.PARAM_PRESIDENTE}"/>'>
	<input type="hidden" name="tipoConsejo" value='<c:out value="${pageScope.organizacionParams.PARAM_CONSEJO}"/>'>
	<input type="hidden" name="cmd" value="">
	<INPUT TYPE="hidden" NAME="height" VALUE='150'>
  <input type="hidden" name="tipoLider" value="">
  <input type="hidden" name="s" value="">
  <input type="hidden" name="t" value="">
  <input type="hidden" name="ext" value="">
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
	<!-- FICHAS A MOSTRAR AL USUARIO -->
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>			
				<td rowspan="2" width="600" bgcolor="#FFFFFF"><script>
				if(fichaLideres==1)document.write('<img src="<c:url value="/etc/img/tabs/lideres_1.gif"/>" alt=""  height="26" border="0">');
				if(fichaGobierno==1)document.write('<a href="javaScript:lanzar(<c:out value="${pageScope.organizacionParams.FICHA_GOB}"/>)"><img src="<c:url value="/etc/img/tabs/gobierno_escolar_0.gif"/>" alt=""  height="26" border="0"></a>');
				if(fichaAsociacion==1)document.write('<a href="javaScript:lanzar(<c:out value="${pageScope.organizacionParams.FICHA_ASO}"/>)"><img src="<c:url value="/etc/img/tabs/asociacion_padres_0.gif"/>" alt=""  height="26" border="0"></a>');
				</script></td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#ffffff">
			<caption>PERSONERO ESTUDIANTIL</caption>
			<tr>
				<th colspan='4' align='left'>
					<input name="cmd1" type="submit" class="boton" value="Editar" onClick="return editar(document.frm,document.frm.forma1.value,'Editar',document.frm.forma2.value,document.frm.tipoPersonero.value)" >
					<input name="cmd1" type="submit" class="boton" value="Eliminar" onClick="return editar(document.frm,document.frm.forma1.value,'Eliminar',document.frm.forma2.value,document.frm.tipoPersonero.value)"  style='display:<c:out value="${permisoBoton}"/>'>
					<input name="cmd1" type="submit" class='boton' value="Nuevo" onClick="return editar(document.frm,document.frm.forma1.value,'Nuevo',document.frm.forma2.value,document.frm.tipoPersonero.value)"  style='display:<c:out value="${permisoBoton}"/>'>
				</th>
			</tr>
			<tr>
				<th width='10' class="EncabezadoColumna"></th>
				<th class="EncabezadoColumna"><b>Sede</b></th>
				<th class="EncabezadoColumna"><b>Jornada</b></th>
				<th class="EncabezadoColumna"><b>Nombre</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroLideres}" var="fila" varStatus="st">
				<c:if test="${fila[6]==pageScope.organizacionParams.PARAM_PERSONERO}">
				<tr>				
					<td class='Fila<c:out value="${st.count%2}"/>' width='1%'>
						<input type='radio' name='r' value='<c:out value="${fila[0]}"/>' onClick='setP(document.frm,"<c:out value="${fila[1]}"/>","<c:out value="${fila[2]}"/>")'>
					</td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[5]}"/></td>
				</tr>
				</c:if>
			</c:forEach>
		</table>
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#ffffff">
			<caption>CONTRALOR</caption>
			<tr>
				<th colspan='4' align='left'>
					<input name="cmd1" type="submit" class='boton' value="Editar" onClick="return editar(document.frm,document.frm.forma1.value,'Editar',document.frm.forma2.value,document.frm.tipoContralor.value)" >
					<input name="cmd1" type="submit" class='boton' value="Eliminar" onClick="return editar(document.frm,document.frm.forma1.value,'Eliminar',document.frm.forma2.value,document.frm.tipoContralor.value)"  style='display:<c:out value="${permisoBoton}"/>'>
					<input name="cmd1" type="submit" class='boton' value="Nuevo" onClick="return editar(document.frm,document.frm.forma1.value,'Nuevo',document.frm.forma2.value,document.frm.tipoContralor.value)"  style='display:<c:out value="${permisoBoton}"/>'>
				</th>
			</tr>
			<tr>
				<th width='10' class="EncabezadoColumna"></th>
				<th class="EncabezadoColumna"><b>Sede</b></th>
				<th class="EncabezadoColumna"><b>Jornada</b></th>
				<th class="EncabezadoColumna"><b>Nombre</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroLideres}" var="fila" varStatus="st">
				<c:if test="${fila[6]==pageScope.organizacionParams.PARAM_CONTRALOR}">
				<tr>				
					<td class='Fila<c:out value="${st.count%2}"/>' width='1%'>
						<input type='radio' name='r' value='<c:out value="${fila[0]}"/>' onClick='setP(document.frm,"<c:out value="${fila[1]}"/>","<c:out value="${fila[2]}"/>")'>
					</td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[5]}"/></td>
				</tr>
				</c:if>
			</c:forEach>
		</table>
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#ffffff">
			<caption>PRESIDENTE DE CONSEJO ESTUDIANTIL</caption>
			<tr>
				<th colspan='4' align='left'>
					<input name="cmd1" type="submit" class='boton' value="Editar" onClick="return editar(document.frm,document.frm.forma1.value,'Editar',document.frm.forma2.value,document.frm.tipoPresidente.value)" >
					<input name="cmd1" type="submit" class='boton' value="Eliminar" onClick="return editar(document.frm,document.frm.forma1.value,'Eliminar',document.frm.forma2.value,document.frm.tipoPresidente.value)"  style='display:<c:out value="${permisoBoton}"/>'>
					<input name="cmd1" type="submit" class='boton' value="Nuevo" onClick="return editar(document.frm,document.frm.forma1.value,'Nuevo',document.frm.forma2.value,document.frm.tipoPresidente.value)"  style='display:<c:out value="${permisoBoton}"/>'>
				</th>
			</tr>
			<tr>
				<th width='10' class="EncabezadoColumna"></th>
				<th class="EncabezadoColumna"><b>Sede</b></th>
				<th class="EncabezadoColumna"><b>Jornada</b></th>
				<th class="EncabezadoColumna"><b>Nombre</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroLideres}" var="fila" varStatus="st">
				<c:if test="${fila[6]==pageScope.organizacionParams.PARAM_PRESIDENTE}">
				<tr>				
					<td class='Fila<c:out value="${st.count%2}"/>' width='1%'>
						<input type='radio' name='r' value='<c:out value="${fila[0]}"/>' onClick='setP(document.frm,"<c:out value="${fila[1]}"/>","<c:out value="${fila[2]}"/>")'>
					</td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[5]}"/></td>
				</tr>
				</c:if>
			</c:forEach>
		</table>
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#ffffff">
			<caption>INTEGRANTE DEL CONSEJO ESTUDIANTIL</caption>
			<tr>
				<th colspan='4' align='left'>
					<input name="cmd1" type="submit" class='boton' value="Editar" onClick="return editar(document.frm,document.frm.forma1.value,'Editar',document.frm.forma2.value,document.frm.tipoConsejo.value)" >
					<input name="cmd1" type="submit" class='boton' value="Eliminar" onClick="return editar(document.frm,document.frm.forma1.value,'Eliminar',document.frm.forma2.value,document.frm.tipoConsejo.value)"  style='display:<c:out value="${permisoBoton}"/>'>
					<input name="cmd1" type="submit" class='boton' value="Nuevo" onClick="return editar(document.frm,document.frm.forma1.value,'Nuevo',document.frm.forma2.value,document.frm.tipoConsejo.value)"  style='display:<c:out value="${permisoBoton}"/>'>
				</th>
			</tr>
			<tr>
				<th width='10' class="EncabezadoColumna"></th>
				<th class="EncabezadoColumna"><b>Sede</b></th>
				<th class="EncabezadoColumna"><b>Jornada</b></th>
				<th class="EncabezadoColumna"><b>Nombre</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroLideres}" var="fila" varStatus="st">
				<c:if test="${fila[6]==pageScope.organizacionParams.PARAM_CONSEJO}">
				<tr>				
					<td class='Fila<c:out value="${st.count%2}"/>' width='1%'>
						<input type='radio' name='r' value='<c:out value="${fila[0]}"/>' onClick='setP(document.frm,"<c:out value="${fila[1]}"/>","<c:out value="${fila[2]}"/>")'>
					</td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[5]}"/></td>
				</tr>
				</c:if>
			</c:forEach>
		</table>
</form>