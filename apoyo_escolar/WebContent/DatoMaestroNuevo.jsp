<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %> 
<%if(request.getAttribute("filtro")!=null && !request.getAttribute("filtro").equals("")){%>
<%@include file="parametros.jsp"%>
<html>
	<head>
		<title>DATO MAESTRO - NUEVO</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">		
			<script language="JavaScript">
			<!--
			var nav4 = window.Event ? true : false;
			function acceptNum(evt){	
			// NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57	
			var key = nav4 ? evt.which : evt.keyCode;	
			return (key <= 13 || (key >= 48 && key <= 57));
			}
			//-->
		</script>			
		<style type="text/css">
		<!--
		.style2 {color: #FF6666}
		-->
		</style>
		<script language="javascript1.2">
			<!--
			function refrescar(frm,frm2){				
				<%if(request.getAttribute("ok")!=null){%>
				frm.target="1"; frm.submit();
				limpiar(frm2);
				<%}%>
				mensaje(document.getElementById("msg"));
				}
				function limpiar(frm){
					if(frm.lista1) frm.lista1.selectedIndex=0;
					if(frm.lista2) frm.lista2.selectedIndex=0;
					if(frm.lista3) frm.lista3.selectedIndex=0;
					if(frm.lista4) frm.lista4.selectedIndex=0;
					if(frm.lista5) frm.lista5.selectedIndex=0;
					if(frm.lista6) frm.lista6.selectedIndex=0;
					if(frm.texto1) frm.texto1.value="";
					if(frm.texto2) frm.texto2.value="";
					if(frm.texto3) frm.texto3.value="";
					if(frm.texto4) frm.texto4.value="";
					if(frm.texto5) frm.texto5.value="";
					if(frm.texto6) frm.texto6.value="";
					if(frm.texto7) frm.texto7.value="";
					if(frm.texto8) frm.texto8.value="";
					if(frm.texto9) frm.texto9.value="";
				}

				function ayuda(){
	 			 remote = window.open("");
				 remote.location.href='<c:url value="/tutor/datosmaestros/insertar.html"/>';
				}
				function otro(){
					window==window.parent;
					window==window.frames['1'];
					window.refresh;
				}
				function hacerValidaciones_frmNuevo(forma){
					switch(forma.dato.value) {
//Campos de G_Constante
						case "1": case "2": case "3":	case "5": case "9": case "11": case "12": case "14": case "15": case "16": case "17": case "18": case "19": case "20": case "24": case "25": case "26": case "44": case"45":case"46":case"47":
						validarEntero(forma.texto1, "- Código (numérico [0-999])",0,999)
						validarCampo(forma.texto2, "- Nombre", 1, 50)
							if (trim(forma.texto3.value).length > 0){
						   validarCampo(forma.texto3, "- Abreviatura [1-10] ", 1,10)
							}
						validarEntero(forma.texto4, "- Orden (numérico [1-99])",1,99)
						break;
						case "50":
						validarEntero(forma.texto1, "- Código (numérico [1-11])",0,99999999999)
						validarCampo(forma.texto2, "- Nombre", 1, 20)
						validarEntero(forma.texto3, "- Orden (numérico [1-11])",1,99999999999)
						break;
						case "43":
						validarEntero(forma.texto1, "- Código (numérico [1-99])",1,99)
						validarCampo(forma.texto2, "- Nombre", 1, 50)
							if (trim(forma.texto3.value).length > 0){
						   validarCampo(forma.texto3, "- Abreviatura [1-10] ", 1,10)
							}
						validarEntero(forma.texto4, "- Orden (numérico [1-99])",1,99)
						break;
						case "4":
						validarEntero(forma.texto1, "- Código (numérico [1-999])",1,999)
						validarCampo(forma.texto2, "- Nombre [1-50]", 1, 50)
						validarCampo(forma.texto3, "- Abreviatura [1-10] ", 1,10)
						validarEntero(forma.texto4, "- Orden (numérico [1-99])",1,99)
						break;
						case "6": case"8":
						validarEntero(forma.texto1, "- Código (numérico [1-99])",1,99)
						validarCampo(forma.texto2, "- Nombre", 1, 50)
						break;
						case "10":
						validarLista(forma.lista1, "- Municipio", 1)
						validarEntero(forma.texto1, "- Código (numérico [1-999])",1,999)
						validarCampo(forma.texto2, "- Nombre", 1, 50)
						break;
						case "13":
						validarEntero(forma.texto1, "- Código (numérico [1-99])",1,99)
						validarCampo(forma.texto2, "- Nombre", 1, 50)
						break;
						case "21":
						validarLista(forma.lista1, "- Departamento", 1)
						validarEntero(forma.texto1, "- Código (numérico [1-999])",1,999)
						validarCampo(forma.texto2, "- Nombre", 1, 50)
						break;
						case "22":							
						validarLista(forma.lista1, "- Tipo de Área", 1)
						validarEntero(forma.texto1, "- Código (numérico [1-9999])",1,9999)
						validarCampo(forma.texto2, "- Nombre", 1, 50)
						break;
						case "23":
						validarLista(forma.lista1, "- Nivel", 1)
						validarEntero(forma.texto2, "- Código (numérico [1-999])",1,999)
						validarCampo(forma.texto3, "- Nombre", 1, 200)
						validarCampo(forma.texto4, "- Abreviatura [1-15] ", 1,15)
						validarEntero(forma.texto5, "- Orden (numérico [1-99])",1,99)
						break;
						case "27":
						   validarLista(forma.lista1, "- Municipio", 1)
							validarEntero(forma.texto2, "- Código (numérico [1-999])",1,999)
							validarCampo(forma.texto3, "- Nombre", 1, 50)
							validarCampo(forma.texto4, "- Dirección", 1, 50)
							validarCampo(forma.texto5, "- Teléfono", 1, 20)
							if (trim(forma.texto6.value).length > 0){ 				
									validarCampo(forma.texto6, "- Director", 1, 50)
							}
						break;
						case "28":
			  			  validarLista(forma.lista1, "- Área", 1)
								if (trim(forma.texto2.value).length == 0)
										forma.texto2.value=forma.lista1.options[forma.lista1.selectedIndex].text;
								validarCampo(forma.texto2, "- Nombre [1-60]", 1, 60)
								if (trim(forma.texto3.value).length == 0)								
										forma.texto3.value='1';
								validarEntero(forma.texto3, "- Orden (numérico [1-999])",1,999)		
								if (trim(forma.texto4.value).length == 0)								
										forma.texto4.value= forma.texto2.value.substring(0,8);
								validarCampo(forma.texto4, "- Abreviatura 1-10]", 1, 10)
								if (!forma.chk){
									appendErrorMessage('- Debe haber grados para poder ingresar áreas');
									if (_campoError == null) {
										_campoError = forma.lista1;
									}
									return false;
								}else{
									validarSeleccion(forma.chk,"-Grado")
								}	
					   break;
						case "29":
							validarLista(forma.lista1, "- Área", 1)
						    validarEntero(forma.texto1, "- Código (numérico [1-9999])",1,9999)
							validarCampo(forma.texto2, "- Nombre", 1, 50)
						break;
						
					case "30":	
							validarLista(forma.lista1, "- Área", 1)
							validarLista(forma.lista2, "- Asignatura", 1)
								if (trim(forma.texto3.value).length == 0)
										forma.texto3.value=forma.lista2.options[forma.lista2.selectedIndex].text;
								validarCampo(forma.texto3, "- Nombre [1-60]", 1, 60)
								if (trim(forma.texto4.value).length == 0)
										forma.texto4.value='1';
								validarEntero(forma.texto4, "- Orden (numérico [1-999])",1,999)
								if (trim(forma.texto5.value).length == 0)
										forma.texto5.value= forma.texto3.value.substring(0,8);
								validarCampo(forma.texto5, "- Abreviatura [1-10]", 1, 10)
								if (!forma.chk){
									appendErrorMessage('- Debe haber grados para poder ingresar asignaturas');
									if (_campoError == null) {
										_campoError = forma.lista1;
									}
									return false;
								}else{
									validarSeleccion(forma.chk,"-Grado")
								}										
								if (forma.chk && forma.ih){
									for (var i = 0; i < forma.chk.length; i++){
									if((forma.chk[i]).checked){
										forma.ih[i].disabled=false;
										if(!validarCampo(forma.ih[i], "- Debe indicar la intensidad horaria en el grado: "+forma.chk[i].value+"", 1, 2) )
											return false;
									 }else{
										forma.ih[i].disabled=true;
									 }
									}
								}
							break;	
							
							
						case "31":
							validarCampo(forma.texto1, "- Criterios[1-3000]", 1, 3000)
							validarCampo(forma.texto2, "- Procedimientos[1-3000]", 1, 3000)
							validarCampo(forma.texto3, "- Planes especiales de apoyo[1-3000]", 1, 3000)
						break;
						case "32":
							validarLista(forma.lista1, "- Nivel", 1)	
							validarLista(forma.lista2, "- Grado", 1)	
							validarCampo(forma.texto3, "- Nombre", 1, 50)
							validarEntero(forma.texto4, "- Orden (numérico [1-99])",1,99)
						break;
					case "34":
							validarLista(forma.lista1, "- Grado", 1)
							validarLista(forma.lista2, "- Asignatura", 1)
						  validarCampo(forma.texto3, "- Nombre [1-50]", 1, 50)
						  validarCampo(forma.texto4, "- Abreviatura [1-10]", 1,10)
							if (trim(forma.texto5.value).length > 0)
							   validarCampo(forma.texto5, "- Descripción ",1,3000)
						break;	
					case "35":	
							validarLista(forma.lista1, "- Grado", 1)
							validarLista(forma.lista2, "- Asignatura", 1)
							validarLista(forma.lista3, "- Contenido", 1)
							validarCampo(forma.texto4, "- Nombre [1-50]", 1, 50)
						  validarCampo(forma.texto5, "- Abreviatura [1-10]", 1,10)
							if (trim(forma.texto6.value).length > 0)
							validarCampo(forma.texto6, "- Descripción ",1,3000)
						break;

					case "36":
							validarLista(forma.lista2, "- Vigencia", 1)
							validarLista(forma.lista3, "- Abreviatura", 1)
							validarCampo(forma.texto4, "- Nombre", 1, 50)
							validarCampo(forma.texto5, "- Periodo inicial [6-10]",6,10)
							valFecha(forma.texto5,"- Periodo inicial  (dd/mm/aaaa)")
							validarCampo(forma.texto6, "- Periodo final [6-10]",6,10)
							valFecha(forma.texto6,"- Periodo final  (dd/mm/aaaa)")
							validarEntero(forma.texto7, "- Orden (numérico [1-99])",1,99)
						break;	

						case "37":
							validarLista(forma.lista1, "- Grado", 1)
							validarLista(forma.lista2, "- Asignatura", 1)
							validarLista(forma.lista3, "- Periodo inicial", 1)
							validarLista(forma.lista4, "- Periodo final", 1)
							validarCampo(forma.texto5, "- Logro [1-1000]", 1, 1000)
							validarCampo(forma.texto6, "- Abreviatura [1-10]", 1,10)
							if (trim(forma.texto7.value).length > 0)
							   validarCampo(forma.texto7, "- Descripción ",1,3000)
						break;	

						case "38":
							validarLista(forma.lista1, "- Grado", 1)
							validarLista(forma.lista2, "- Asignatura", 1)
							validarLista(forma.lista3, "- Logro", 1)
							validarLista(forma.lista4, "- Periodo", 1)
							validarCampo(forma.texto2, "- Nombre", 1, 200)
							validarCampo(forma.texto3, "- Abreviatura [1-10]", 1,10)
							validarCampo(forma.texto4, "- Descripción ",1,200)
						break;	

						case "39":
							validarLista(forma.lista1, "- Grado", 1)
							validarLista(forma.lista2, "- Área", 1)
							validarLista(forma.lista3, "- Tipo descriptor", 1)
							validarLista(forma.lista4, "- Periodo inicial", 1)
							validarLista(forma.lista5, "- Periodo final", 1)
							validarCampo(forma.texto6, "- Descriptor [1-1000]", 1, 1000)
							validarCampo(forma.texto7, "- Abreviatura [1-10]", 1,10)
							if (trim(forma.texto8.value).length > 0)							
								 validarCampo(forma.texto8, "- Descripción ",1,3000)
						break;	

						case "40":
						validarEntero(forma.texto1, "- Código (numérico [1-99])",1,99)
						validarCampo(forma.texto2, "- Nombre", 1, 50)
						break;

						case "41":
							validarLista(forma.lista1, "- Sede", 1)
							validarLista(forma.lista2, "- Jornada", 1)
							validarLista(forma.lista3, "- Metodología", 1)
							validarLista(forma.lista4, "- Grado", 1)
							validarLista(forma.lista5, "- Tipo Espacio", 1)
							validarLista(forma.lista6, "- Espacio", 1)
							validarLista(forma.lista7, "- Coordinador", 1)
							validarCampo(forma.texto1, "- Nombre", 1, 50)
							validarCampo(forma.texto2, "- Cupo", 1,5)
							validarCampo(forma.texto3, "- Orden", 1,5)
						break;						

						case"42":
						   validarLista(forma.lista1, "- Tipo Escala", 1)
						   validarCampo(forma.texto1, "- Código(numérico [1-99])",1,99)
						   validarCampo(forma.texto2, "- Nombre",1,50)
						   validarCampo(forma.texto3, "- Abreviatura [1]", 1,1)
					   break;
					}						
				}
	
				function lista(forma,n){
					switch(forma.dato.value){
						 case "28":
							 if(n==1){  //lista1
								forma.x.value=forma.lista1.options[forma.lista1.selectedIndex].value;
 								forma.texto2.value=forma.lista1.options[forma.lista1.selectedIndex].text;
								forma.texto4.value= forma.texto2.value.substring(0,8);								
								forma.action='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>';
								forma.submit();							
							}
						break;
						 case "30":					
						 if(n==1){//lista1
								forma.x.value=forma.lista1.options[forma.lista1.selectedIndex].value;
								forma.action='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>';
								forma.submit();
							}
							 if(n==2){//lista2
								if(forma.lista1.selectedIndex>0){
									if(forma.lista2.selectedIndex>0){
									forma.x.value=forma.lista1.options[forma.lista1.selectedIndex].value;
									forma.y.value=forma.lista2.options[forma.lista2.selectedIndex].value;
									forma.texto3.value=forma.lista2.options[forma.lista2.selectedIndex].text;
									forma.texto4.value='1';
									forma.texto5.value= forma.texto3.value.substring(0,8);																				
									forma.action='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>';
									forma.submit();
								}
							 }
							}						
						 break;						
						 case"32":case"34":case"37":case"39":
							 if(n==1){  //lista1
								forma.x.value=forma.lista1.options[forma.lista1.selectedIndex].value;
								forma.action='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>';
								forma.submit();
							}
						break;	
						case"38":
							 if(n==1){//lista1
								forma.x.value=forma.lista1.options[forma.lista1.selectedIndex].value;
								forma.action='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>';
								forma.submit();
							}
							 if(n==2){//lista2
								if(forma.lista1.selectedIndex>0){ 
									if(forma.lista2.selectedIndex>0){
									forma.x.value=forma.lista1.options[forma.lista1.selectedIndex].value;
									forma.y.value=forma.lista2.options[forma.lista2.selectedIndex].value;
									forma.action='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>';
									forma.submit();
								}
							 }
							}
						break;
						case"35":
							 if(n==1){//lista1
								forma.x.value=forma.lista1.options[forma.lista1.selectedIndex].value;
								forma.action='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>';
								forma.submit();
							}
							 if(n==2){//lista2
								if(forma.lista1.selectedIndex>0){
									if(forma.lista2.selectedIndex>0){
										forma.x.value=forma.lista1.options[forma.lista1.selectedIndex].value;
										forma.y.value=forma.lista2.options[forma.lista2.selectedIndex].value;
										forma.action='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>';
										forma.submit();
								}
							 }
							}
						break;
					}
				}
			
			function horas(forma,m){
				switch(forma.dato.value){
				case "30":
				for(var i=0;i<=m;i++){
					if(forma.chk[i].checked==true){
					forma.intensidad1.disabled=false;
					forma.intensidad1.focus();
					}	
				}	
				break;	
				}
			}
				function boton(num){
					if(num==1)
						document.frmNuevo.reset();
				}
			//-->
		</script>
</head>
<body onload="refrescar(frm,frmNuevo)">
	<form method="post" name="frm" action='<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1"/>'>
		<input type='hidden' name='lista1' value=''>
		<input type='hidden' name='lista2' value=''>
		<input type='hidden' name='lista3' value=''>
		<input type='hidden' name='lista4' value=''>
	</form>
	<%@include file="mensaje.jsp"%>	
	<form method="post" name="frmNuevo" onSubmit='return validarForma(frmNuevo)' action='<c:url value="/datoMaestro/DatoMaestroNuevoSave.do?ext=1"/>'>
		<input type='hidden' name='dato' value='<%=request.getSession().getAttribute("dato")%>'>
		<input type='hidden' name='x' value=''><input type='hidden' name='y' value=''><input type='hidden' name='z' value=''>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>·.·.·.· Nuevo registro de <%=(String)request.getAttribute("titulo")%>·.·.·.·</caption>
			<tr>
			  <td>
					<input class="boton" name="cmd" type="submit" value="Aceptar" >
<!-- 					<input class="boton" id="cmd2" name="cmd2" type="button" style="display:" value="Ayuda" onClick ="ayuda()">		 -->
				</td>
			</tr>
		</table>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<%=(String)request.getAttribute("filtro")%>
		</table>
	</form>
</body>
</html>
<%}%>