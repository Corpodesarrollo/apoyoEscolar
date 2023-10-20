<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<html><head><title>LISTA DE DESCRIPTORES</title><%@include file="../parametros.jsp"%>
		<script languaje='javaScript'>
			<!--
			var cod;
			var des = new Array();
			var des_ = new Array();
			function aceptar(frmDesc){
		 		ponervalor(frmDesc);
				if(frmDesc.num.value!=-1){
					opener.document.forms['frmNuevo'].elements['nota'][frmDesc.num.value].value=frmDesc.lista.value;
					opener.document.forms['frmNuevo'].elements['nota_'][frmDesc.num.value].value=frmDesc.lista_.value;
				}else{
					opener.document.forms['frmNuevo'].elements['nota'].value=frmDesc.lista.value;
					opener.document.forms['frmNuevo'].elements['nota_'].value=frmDesc.lista_.value;
				}
				parent.close();
			}
			
			function ponervalor(forma){
				des = new Array();
				var x=0;
				if(forma.chkdesc.length){
					for(var j=0;j<forma.chkdesc.length;j++){
						if(forma.chkdesc[j].checked){
							des[x]=forma.chkdesc[j].value;
							des_[x]=forma.chkdesc_[j].value;
							x++;
						}
					}
				}else{
					if(forma.chkdesc.checked){
						des[0]=forma.chkdesc.value;
						des_[0]=forma.chkdesc_.value;
					}
				}
				if(des.length){
					var m=des.join(",");
					var n=des_.join(",");
					forma.lista.value=cod+"|"+m;
					forma.lista_.value=n;
				}else{
					forma.lista.value=cod+"|";
					forma.lista_.value="";
				}	
			}
			function cancelar(){
        parent.close();
			}
			function cargar(forma){
				cargarChecks(forma);
			}
			function cargarChecks(forma){
				var a=forma.lista.value;
				a=trim(a);
				var ar=a.split("|");
				cod=ar[0];
				if(ar[1] && ar[1]!=""){
					var d=ar[1].split(",");
					for(var i=0;i<d.length;i++){
						if(forma.chkdesc.length){
							for(var j=0;j<forma.chkdesc.length;j++){
								if(d[i]==forma.chkdesc[j].value){
									forma.chkdesc[j].checked=true;
								}
							}
						}else{
							if(d[i]==forma.chkdesc.value){
								forma.chkdesc.checked=true;
							}
						}
					}
				}
			}
			//-->
		</script>
</head>
<body onLoad='cargar(frmDesc)'>
		<FORM ACTION="" METHOD="POST" name="frmDesc">
		<input type="hidden" name="lista" value="<%=request.getParameter("val")%>">
		<input type="hidden" name="num" value="<%=request.getParameter("num")%>">
		<input type="hidden" name="lista_" value="">
			<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
				<caption>Asignación de Descriptores</caption>
				<tr><td>
					<INPUT class='boton' TYPE="button" NAME="cmd" VALUE="Aceptar" onClick="return aceptar(frmDesc)">
					<INPUT class='boton' TYPE="button" NAME="cmd2" VALUE="Cancelar" onClick="cancelar()">
				</td></tr>
			</table>
			<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
				<tr>
				<th class="EncabezadoColumna">Abreviatura :</th><th class="EncabezadoColumna" width="70%">Nombre :</th>
				</tr><c:forEach begin="0" items="${sessionScope.filtroDescriptores}" var="fila3" varStatus="st2"><tr>
					<td class='Fila<c:out value="${st2.count%2}"/>'><input class='Fila<c:out value="${st2.count%2}"/>' type='checkbox' name='chkdesc' value='<c:out value="${fila3[0]}"/>'><c:out value="${fila3[1]}"/><input type='hidden' name='chkdesc_' value='<c:out value="${fila3[1]}"/>'></td>
					<td class='Fila<c:out value="${st2.count%2}"/>'><c:out value="${fila3[3]}"/></td>
				</tr></c:forEach>
			</table>
		</FORM>
	</body>
</html>