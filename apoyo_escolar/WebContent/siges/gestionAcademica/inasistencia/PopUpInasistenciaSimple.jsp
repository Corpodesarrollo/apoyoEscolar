<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<c:import url="/parametros.jsp"/>
<script>
		var indice='<c:out value="${requestScope.popIndice}"/>';
		var est='';
		var dia='';
		
		function aceptar(){
			var param=est+'|'+dia+'|'+document.frm.motivo.options[document.frm.motivo.selectedIndex].value;
			opener.document.forms['frmNuevo'].elements['falla'][indice].value=param;
			parent.close();
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
		<tr>
			<td>
				<input type="button" onclick="javaScript:aceptar()" value="Aceptar" class="boton">
			</td>
			<td>	
				<input type="button" onclick="javaScript:cancelar()" value="Cancelar" class="boton">
			</td>
		</tr>
</table>
</form>
</body>