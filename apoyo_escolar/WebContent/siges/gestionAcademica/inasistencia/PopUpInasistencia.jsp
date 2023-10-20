<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<c:import url="/parametros.jsp"/>
<script>
		var indice='<c:out value="${requestScope.popIndice}"/>';
		var est='';
		var dia='';
		
		function aceptar(){
			if(document.frm.asignatura){ 
				if(document.frm.asignatura.length){
					var param=est+'|'+dia+'|'+document.frm.motivo.options[document.frm.motivo.selectedIndex].value+'|';
					for(var j = 0; j<document.frm.asignatura.length; j++) {
						param+=document.frm.asignatura[j].value+'@'+document.frm.asignatura2[j].options[document.frm.asignatura2[j].selectedIndex].value+'|';
					}
					param=param.substring(0,param.length-1);
					opener.document.forms['frmNuevo'].elements['falla'][indice].value=param;
					parent.close();
				}else{
					var param=est+'|'+dia+'|'+document.frm.motivo.options[document.frm.motivo.selectedIndex].value+'|';
					param+=document.frm.asignatura.value+'@'+document.frm.asignatura2.options[document.frm.asignatura2.selectedIndex].value+'|';
					param=param.substring(0,param.length-1);
					opener.document.forms['frmNuevo'].elements['falla'][indice].value=param;
					parent.close();
				}	
			}else{
				alert('No hay asignaturas este dia, no puede registrar ausencia');
				opener.document.forms['frmNuevo'].elements['falla'][indice].checked=false;
			}	
		}
		
		function cancelar(){
			parent.close();
		}

		function init(){
			var param=opener.document.forms['frmNuevo'].elements['falla'][indice].value;
			var items=param.split('|');
			est=items[0];
			dia=items[1];
			if(document.frm.motivo && document.frm.motivo.length){
				for(var i = 0; i <document.frm.motivo.length; i++) {
					if(document.frm.motivo.options[i].value==parseInt(items[2])){
						document.frm.motivo.selectedIndex = i;
					}
				}
			}
			for(var i=3;i<items.length;i++){
				var asig=items[i].split('@');
				if(document.frm.asignatura){
					if(document.frm.asignatura.length){
						for(var j = 0; j<document.frm.asignatura.length; j++) {
							if(parseInt(document.frm.asignatura[j].value)==parseInt(asig[0])){
								for(var k = 0; k <document.frm.asignatura2[j].length; k++) {
									if(document.frm.asignatura2[j].options[k].value==parseInt(asig[1])){
										document.frm.asignatura2[j].selectedIndex = k;
									}
								}
							}
						}
					}else{
							if(parseInt(document.frm.asignatura.value)==parseInt(asig[0])){
								for(var k = 0; k <document.frm.asignatura2.length; k++) {
									if(document.frm.asignatura2.options[k].value==parseInt(asig[1])){
										document.frm.asignatura2.selectedIndex = k;
									}
								}
							}
					}	
				}
			}
		}
		
</script>
</head>
<body onload="javaScript:init()">
<form method="post" name="frm">
<table cellpadding="0" cellspacing="0" border="0" align="center" width="99%">
	<caption>Selección de parámetros de inasistencia</caption>
	<tr>
	<th>Motivo:</th>
	<td>
					<select name="motivo" style='width:120px;'>
						<option value='-99'>--Sin justificar--</option>
						<c:forEach begin="0" items="${requestScope.popMotivo}" var="mot">
							<option value="<c:out value="${mot.codigo}"/>"><c:out value="${mot.nombre}"/></option>
						</c:forEach>
					</select>
	</td></tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	
	<tr><td colspan="2">
	<table width="100%">
	<caption>ASIGNATURAS</caption>
	 <tr>
	   <td>
		<c:forEach begin="0" items="${requestScope.popAsignatura}" var="asig">
			<tr>
				<td><c:out value="${asig.nombre}"/></td>
					<td>
						<input type="hidden" value='<c:out value="${asig.codigo}"/>' name='asignatura'>
						<select name="asignatura2" style='width:55px;'>
						<c:forEach begin="0" step="1"  end="${asig.padre}" var="ih"><option value="<c:out value="${ih}"/>"><c:out value="${ih}"/> horas</option></c:forEach>
						</select>
				</td>
			</tr>
		</c:forEach>
	     </td>
	    </tr>	
	   </table>
	 </td>
	 </tr> 
	 <tr><td colspan="2">&nbsp;</td></tr>
	<tr>
			<td>
				<input type="button" onclick="javaScript:aceptar()" value="Aceptar" class="boton">
			</td>
			<td>	
				<input type="button" onclick="javaScript:cancelar()" value="Cancelar" class="boton">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<span class="style2">Nota:</span> 
				Si asigna 0 horas a una asignatura, no se registrara inasistencia de esa asignatura.<br>
				Si pone 0 horas en todas las asignaturas, no se registrara la ausencia de ese dia.
			</td>
		</tr>
</table>
<!--//////////////////-->		
</form>
</body>