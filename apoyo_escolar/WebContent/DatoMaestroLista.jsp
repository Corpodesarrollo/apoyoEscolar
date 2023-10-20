<%if(request.getParameter("exel")!=null)
	response.setContentType("application/vnd.ms-excel");
else	
	response.setContentType("text/html");%>
<%@include file="parametros.jsp"%>
<html>
	<head>
		<title>DATOS MAESTROS - informe de  <%=(String)request.getAttribute("titulo")%> -</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">		
		<script language="JavaScript">
				<!--				
				function excel(){
					var a="DatoMaestroListaEdit?"
					if(document.frm.lista1) a=a+"lista1="+document.frm.lista1.value+"&";
					if(document.frm.lista2) a=a+"lista2="+document.frm.lista2.value+"&";
					if(document.frm.lista3) a=a+"lista3="+document.frm.lista3.value+"&";
					if(document.frm.lista4) a=a+"lista4="+document.frm.lista4.value+"&";
					a=a+"busqueda=1&exel=1&tipo=1";
					window.open(a);
				}
				function validarcombos(forma){
					if(validarForma(document.frm)){
						forma.submit();
					}
				}
				
				function buscar(forma){
					location.href='<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1"/>';
				}
				
				function hacerValidaciones_frm(forma){
					switch(forma.dato.value){				
						case "29":
							validarLista(forma.lista1, "- Área", 1)
						break;						
						case "30":
							validarLista(forma.lista1, "- Área", 1)
						break;						
						case "34":
							validarLista(forma.lista1, "- Grado", 1)
							validarLista(forma.lista2, "- Asignatura", 1)
						break;
						case "37":
							validarLista(forma.lista1, "- Grado", 1)
							validarLista(forma.lista2, "- Asignatura", 1)
						break;						
						
						case "35": 
							validarLista(forma.lista1, "- Grado", 1)
							validarLista(forma.lista2, "- Asignatura", 1)
							validarLista(forma.lista3, "- Contenido", 1)
						break;
						case "39":
							validarLista(forma.lista1, "- Grado", 1)
							validarLista(forma.lista2, "- Área", 1)
						break;						
					}					
				}
				
				function editar1(n){
					document.frm.text.value=n;
					document.frm.action='<c:url value="/datoMaestro/DatoMaestroEditarEdit.do?ext=1"/>';
					document.frm.target=2;
					document.frm.submit();
				}
			
				function editar2(n,m){
					document.frm.text.value=n;
					document.frm.text2.value=m;
					document.frm.action='<c:url value="/datoMaestro/DatoMaestroEditarEdit.do?ext=1"/>&text2='+document.frm.text2.value;	
					document.frm.target=2;
					document.frm.submit();
				}
				
				function eliminar1(n){
					document.frm.text.value=n;
		     if (confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
							document.frm.action='<c:url value="/datoMaestro/DatoMaestroEditarSave.do?ext=1"/>&cmd=Eliminar';
							document.frm.target=2;
							document.frm.submit();
					}else{return false;}
				}

				function eliminar2(n,m){
					document.frm.text.value=n;
					document.frm.text2.value=m;
			     if (confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
							document.frm.action='<c:url value="/datoMaestro/DatoMaestroEditarSave.do?ext=1"/>&text2='+document.frm.text2.value+'&cmd=Eliminar';	
							document.frm.target=2;
							document.frm.submit();
					}else{return false;}
				}
				
				function lista(forma,n){
					switch(document.frm.dato.value) {
						 case "34":case"37":case"39":
							 if(n==1){//lista1
								document.frm.x.value=document.frm.lista1.options[document.frm.lista1.selectedIndex].value;
								document.frm.action='<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1"/>';
								document.frm.submit();
							}
						break;						
						 case "35":
							 if(n==1){//lista1
								document.frm.x.value=document.frm.lista1.options[document.frm.lista1.selectedIndex].value;
								document.frm.action='<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1"/>';
								document.frm.submit();
							}
							 if(n==2){//lista2
								if(document.frm.lista1.selectedIndex>0){ 
									if(document.frm.lista2.selectedIndex>0){
									document.frm.x.value=document.frm.lista1.options[document.frm.lista1.selectedIndex].value;
									document.frm.y.value=document.frm.lista2.options[document.frm.lista2.selectedIndex].value;
									document.frm.action='<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1"/>';
									document.frm.submit();									
								}
							 }
							}
						break;						
					}
				}

				function informe(){
					switch(document.frm.dato.value) {	
						case "19":case "21":case "22":case "23": case "10": case "29": case "30": case "34":case "35":case "36":case "37": case "39":
							if(document.frm.cmd3) document.frm.cmd3.style.display='';							
						break;
					}					 
					if (self.parent.frames.length == 0){
						cabecera.style.display='';
					}	
					mensaje(document.getElementById("msg"));
				}
				function imprimir(){
					window.print();
				}
				
				function guardar(){
					window.document.execCommand('saveas')
				}

				function borrar(){
					if(document.frm.cmd)  document.frm.cmd.style.display='none';
					if(document.frm.cmd1) document.frm.cmd1.style.display='none';
					if(document.frm.cmd2) document.frm.cmd2.style.display='none';
					if(document.frm.cmd3) document.frm.cmd3.style.display='none';
					if(document.frm.cmd4) document.frm.cmd4.style.display='none';
					if(document.frm.cmd5) document.frm.cmd5.style.display='none';
				}
				function cancelar(){						
					parent.location.href='<c:url value="/bienvenida.jsp"/>';
				}
				//-->
			</script>			
		<style type="text/css">
		<!--
		.style2 {color: #FF6666}
		-->
		</style>			
</head>
<body onLoad="informe()"><%@include file="mensaje.jsp"%>
	<table border="0" align="center" bordercolor="#ffffff" width="70%" id="cabecera" style="display:none">
		<tr>			
			<td bordercolor="#ffffff"  colspan='2' width='30%'> 
				<img src="img/sed2.jpg">
			</td>
			<th bordercolor="#ffffff"  colspan='2' >
				<font size='-1'>
					<br>
					<br>
					<br><br>
				</font>
				<b>Informe de <%=(String)request.getAttribute("titulo")%></b><br>
				<font size='-2'>
					<p align='right'>Fecha de generación:&nbsp &nbsp
						<%java.util.Date fecha=new java.util.Date();
						String mes[]={"ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic"};%>
						<%=String.valueOf(fecha.getDate()+"-"+mes[fecha.getMonth()]+"-"+(fecha.getYear()+1900))%>
					</p>
				</font>
			</th>
		</tr>
	</table>
	<%if(request.getParameter("tipo")!=null){%>	
	<form action="<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1&busqueda=1&tipo=1"/>" method="post" name="frm">
	<%}else{%>	
	<form action="<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1&busqueda=1"/>" method="post" name="frm">
	<%}%>
	<input type='hidden' name ='text' value=''>
	<input type='hidden' name='dato' value='<%=request.getSession().getAttribute("dato")%>'>
	<input type='hidden' name='x' value=''><input type='hidden' name='y' value=''><input type='hidden' name='z' value=''>
	<%if(!request.getAttribute("filtro").equals("")){%>
		<table border="0" align="center" bordercolor="#FFFFFF" width="70%">
			<caption>·.·.·.· Filtro de busqueda de <%=(String)request.getAttribute("titulo")%> ·.·.·.·</caption>
			<%=(String)request.getAttribute("filtro")%>
			<tr>
				<td align='center' colspan='2'>
					<input type='button' id="cmd" name="cmd" value="Buscar" class="boton" style="width:100px;display:" onClick ="validarcombos(document.frm)">
				</td>
			</tr>
		</table>
	    <%}%>
	
	<%if(request.getAttribute("tabla")!=null){%>
	<%if(!request.getAttribute("tabla").equals("")){%>
		<%if(request.getParameter("lista1")!=null){%>
			<input type='hidden' name='lista1' value='<%=(String)request.getParameter("lista1")%>'>
		<%}%>
		<%if(request.getParameter("lista2")!=null){%>
			<input type='hidden' name='lista2' value='<%=(String)request.getParameter("lista2")%>'>
		<%}%>
		<%if(request.getParameter("lista3")!=null){%>
			<input type='hidden' name='lista3' value='<%=(String)request.getParameter("lista3")%>'>
		<%}%>
		<%if(request.getParameter("lista4")!=null){%>
			<input type='hidden' name='lista4' value='<%=(String)request.getParameter("lista4")%>'>
		<%}%>
		
		<table border="0" align="center" bordercolor="#000000" width="100%">
			<tr><td bordercolor="#ffffff"><input class="boton" id="cmd3" name="cmd3" type="button"  style="width:100px;display:none" value="Buscar" onClick ="buscar()">
			  	</td></tr>
			<tr>
				<th bordercolor="#ffffff">
					<table border="1" cellpadding="1" cellspacing="0" bordercolor="Silver" align="center" width="100%">
						<caption>·.·.·.· <%=(String)request.getAttribute("titulo")%> ·.·.·.·</caption>
						<%=request.getAttribute("tabla")%>
						<tr><th></th></tr>
					</table>
				</th>
			</tr>
		</table>
	    <%}%>
    <%}%>
	</form>
</body>
</html>