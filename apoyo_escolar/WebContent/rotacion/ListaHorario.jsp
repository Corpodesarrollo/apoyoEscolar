<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<html><head><title>ESPACION FISICO</title><%@include file="../parametros.jsp"%>
		<script languaje='javaScript'>
var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;
			var esp = new Array();
			var dia;
			var hora;
			var asignatura;
			var docente;
			var grupo;
			var valor;
			var valor_;
			var num;
			esp=opener.document.forms['fr'].elements['esp_'].value.split("#");
			dia=opener.document.forms['fr'].elements['dia_'].value;
			num=opener.document.forms['fr'].elements['num_'].value;
			hora=opener.document.forms['fr'].elements['hora_'].value;
			asignatura=opener.document.forms['fr'].elements['asig_'].value;
			docente=opener.document.forms['fr'].elements['doc_'].value;
			grupo=opener.document.forms['fr'].elements['grupo_'].value;			
			valor=opener.document.forms['fr'].elements['valor_'].value;
			valor_=valor.split("|");
			
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-- Seleccione uno --","");
			}
			
			function aceptar(forma){
				var val;
				var txt;
				if(forma.espacio.selectedIndex==0){
					val=grupo+"|"+hora+"|"+dia+"|"+asignatura+"|"+docente+"|";
					txt="";
				}else{
					val=grupo+"|"+hora+"|"+dia+"|"+asignatura+"|"+docente+"|"+forma.espacio.options[forma.espacio.selectedIndex].value;
					txt=forma.espacio.options[forma.espacio.selectedIndex].text;
				}
				opener.document.forms['frmHor'].elements['horas'][num].value=val;
				opener.document.forms['frmHor'].elements['horas_'][num].value=val;
				opener.document.forms['frmHor'].elements['horas_'][num].checked=true;
				opener.document.forms['frmHor'].elements['txt'][num].value=txt;
				parent.close();
			}
			function cancelar(){
		    	parent.close();
			}
			
			function cargar(forma){
				cargarChecks(forma.espacio);
			}
			
			function cargarChecks(combo_hijo2){
				if(valor_.length>1){
					var es=valor_[5];
					//alert(valor+","+es);
				}
				borrar_combo(combo_hijo2);
					for(var i=0;i<esp.length;i++){
						var esp_=esp[i].split("|");
						if(esp_.length>1){
							if(esp_[0]==es){
								combo_hijo2.options[i+1] = new Option(esp_[1],esp_[0],"SELECTED");combo_hijo2.selectedIndex = i+1;
							}else{
								combo_hijo2.options[i+1] = new Option(esp_[1],esp_[0]);
							}
						}	
					}
			}
			//-->
		</script>
</head>
<body onLoad='cargar(frmHora)'>
		<FORM ACTION="" METHOD="POST" name="frmHora">
			<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
				<caption>Espacio Físico</caption>
				<tr><th>
					<INPUT class='boton' TYPE="button" NAME="cmd" VALUE="Aceptar" onClick="return aceptar(frmHora)">
				</th></tr>
			</table>
			<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
					<td>Espacio físico:</td>
					<td>
						<select name="espacio" style='width:150px;'>
						<option value=''>-- Seleccione uno --</option>
						</select>
					</td>
				</tr>
			</table>
		</FORM>
	</body>
</html>