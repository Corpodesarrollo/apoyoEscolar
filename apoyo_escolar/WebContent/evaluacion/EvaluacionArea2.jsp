<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtroEvaluacion" class="siges.evaluacion.beans.FiltroBeanEvaluacion" scope="session"/><jsp:setProperty name="filtroEvaluacion" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="4" scope="page"/>
<link rel="stylesheet" type="text/css" href="../etc/css/popup.css" media="all" />
<script language="javascript" type="text/javascript" src="../etc/js/jquery-1.3.2.min.js"></script>
<script language="javascript" type="text/javascript" src="../etc/js/csspopup.js"></script>
<!--[if IE]>
<style type="text/css" media="all">
	#capaPopUp 	{filter:alpha(opacity=65);}
</style>
<![endif]-->
		<script language="JavaScript">
			<!--
			var soloLectura=<c:out value="${sessionScope.NivelPermiso}"/>;
			var log=1;
			var est=0;
			var nav4=window.Event ? true : false;
			var notaMin=0;
			var notaMax=0;
      function digitaNumeros(eve){
        var key=nav4?eve.which :eve.keyCode;
        return(key<=13 || (key>=48 && key<=57));
      }

      function ponerTodos(forma){
			if (!confirm('¿Está seguro de que quiere reemplazar la evaluación actual en toda la columna?')) return;
			var pos=-1;
			var jj=getCantidad(forma,"select-one","nota_");
			pos=forma.encNota.selectedIndex;
			if(jj>1){
				for(var i=0;i<forma.nota_.length;i++){
					forma.nota_[i].selectedIndex=pos;
				}
			}else{
					forma.nota_.selectedIndex=pos;
			}
		}

			function ponerTodosRec(forma){
				if (!confirm('¿Está seguro de que quiere reemplazar la recuperación actual en toda la columna?')) return;
				var pos=-1;
				var jj=getCantidad(forma,"select-one","rec");
				pos=forma.encRec.selectedIndex;
				if(jj>1){
					for(var i=0;i<forma.rec.length;i++){
						forma.rec[i].selectedIndex=pos;
					}
				}else{
						forma.rec.selectedIndex=pos;
				}
			}
			

			
			function getCantidad(forma,tipo,nombre){
				var jj=0;
				for(var i=0;i<forma.elements.length;i++){
					if(forma.elements[i].type==tipo && forma.elements[i].name==nombre){jj++;}
				}
				return jj;
			}
			
			function acepteNumeros(eve,combo,a,b){
				var key=nav4?eve.which :eve.keyCode;
				if(key==13){next(combo,a,b);return;}
				var tecla=String.fromCharCode(key).toLowerCase();				
				if (combo[0].length) {
					for (var i = 0; i < combo[0].length; i++) {
						if (combo[0].options[i].text){
							if(combo[0].options[i].text.toLowerCase()==tecla){
								next(combo,a,b);
								return;
							}
						}
					}
				}
			}			
      function acepteNumeros2(eve,combo,combo2,a,b){
        var key=nav4?eve.which :eve.keyCode;
        if(key==13) sigNota(combo,combo2,a,b);
				return(key<=13 || (key>=48 && key<=57));
      }

      function acepteNumeros3(eve,combo,combo2,a,b){
	         // alert("a: "+a+"  b: "+b);
	          var key=nav4?eve.which :eve.keyCode;
	          //alert("key: "+key);
	          if(key==13){
	               sigNota(combo,combo2,a,b);
	           }
	          if((key>=48 && key<=57) || key==46 || key==8 || key==0){
	  				return(true);
	          }
	          else{
	        		return(false);
	          }
	         }
      
           function mostrar(){
        	   var elementos = document.getElementsByName("cerrar_1");
        	   if(elementos[0].checked){
        		   document.getElementById("spanabrirPop1").style.display="block";
        		   document.getElementById("spanabrirPop").style.display="none";
        		   // document.getElementById("spanabrirPop").innerHTML = '<a href="javascript:void(0);" title="Abrir PopUp" id="abrirPop">Consultar estudiantes que están perdiendo (Deshabilitado)</a>';
        	   }else{
        		   document.getElementById("spanabrirPop").style.display="block";
        		   document.getElementById("spanabrirPop1").style.display="none";  
        		 //  document.getElementById("spanabrirPop").innerHTML = '<a href="javascript:void(0);" title="Abrir PopUp" id="abrirPop">Consultar estudiantes que están perdiendo</a>';   
        	   }
        	 }
      
			function valorar(forma){
        if(forma.cerrar_.checked) forma.cerrar.value='1';
        else forma.cerrar.value='0';			
			}

			function hacerValidaciones_frmNuevo(forma){
				if(forma.cerrar_.checked){
					if(confirm("Seleccionó la opción 'cerrar Notas', y una vez cerrado, no se podra abrir de nuevo para evaluación, ¿confirma el cierre? \n Si pulsa Aceptar, se guardara y se cerrara las notas.\n Si pulsa Cancelar, se guardara y no se cerrara las notas.")){
						forma.cerrar_.checked=true; valorar(forma);
					}else{
						forma.cerrar_.checked=false; valorar(forma);
					}
				}
				if(forma.ausjus.length){
					for(var i=1;i<forma.ausjus.length;i++){
						validarEnteroOpcional(forma.ausjus[i], "- Ausencia", 0, 999)
					}
				}else{
					validarEnteroOpcional(forma.ausjus, "- Ausencia", 0, 999)
				}


				<c:if test="${sessionScope.tipoEval==2}">				
				if(document.frmNuevo.nota_){
					if(document.frmNuevo.nota.length){
						for(j = 0;j< document.frmNuevo.nota.length;j++){
							if(forma.nota_[j].value<0){
								if(trim(forma.ausjus[j].value)!=''){
									validarLista(forma.nota_[j],'- Nota (necesaria para registrar la ausencia)',1)
								}
								if(forma.rec[j].value>0){
									validarLista(forma.nota_[j],'- Nota (necesaria para registrar la recuperación)',1)
								}
							}						
							}
						
					}else{
						if(forma.nota_.value<0){
							if(trim(forma.ausjus.value)!=''){
								validarLista(forma.nota_,'- Nota (necesaria para registrar la ausencia)',1)
							}
							if(forma.rec.value>0){
								validarLista(forma.nota_,'- Nota (necesaria para registrar la recuperación)',1)
							}
						}	
						
					}
				}
				</c:if>
				
				<c:if test="${sessionScope.tipoEval==1}">
				notaMin=<c:out value="${sessionScope.tipoEvalMin}"/>;
				notaMax=<c:out value="${sessionScope.tipoEvalMax}"/>;	
				if(document.frmNuevo.nota){
					if(document.frmNuevo.nota.length){
						for(j = 0;j< document.frmNuevo.notaEst.length;j++){
							if(document.frmNuevo.notaEst[j].value!=""){
								validarFloat(document.frmNuevo.notaEst[j], "- Nota: No es númnero o está fuera del rango.", notaMin, notaMax);
							}else if(trim(forma.notaEst[j].value)==''){
								if(trim(forma.ausjus[j].value)!=''){
									validarCampo(forma.notaEst[j],'- Nota (necesaria para registrar la ausencia)',1)
								}
								if(trim(forma.recEst[j].value)!=''){
									validarCampo(forma.notaEst[j],'- Nota (necesaria para registrar la recuperación)',1)
								}
							}
						}
						
					}else{
						if(document.frmNuevo.notaEst.value!=""){
							validarFloat(document.frmNuevo.notaEst, "- Nota: No es númnero o está fuera del rango.", notaMin, notaMax);
						}else if(trim(forma.notaEst.value)==''){
							if(trim(forma.ausjus.value)!=''){
								validarCampo(forma.notaEst,'- Nota (necesaria para registrar la ausencia)',1)
							}
							if(trim(forma.recEst.value)!=''){
								validarCampo(forma.notaEst,'- Nota (necesaria para registrar la recuperación)',1)
							}
						}
						
					}
				}
				</c:if>
				<c:if test="${sessionScope.tipoEval==3}">
				notaMin=<c:out value="${sessionScope.tipoEvalMin}"/>;
				notaMax=<c:out value="${sessionScope.tipoEvalMax}"/>;	
				if(document.frmNuevo.nota){
					if(document.frmNuevo.nota.length){
						for(j = 0;j< document.frmNuevo.notaEst.length;j++){
							if(document.frmNuevo.notaEst[j].value!=''){
								validarFloat(document.frmNuevo.notaEst[j], "- Nota: No es número o está fuera del rango.", notaMin, notaMax);
							}else if(trim(forma.notaEst[j].value)==''){
								if(trim(forma.ausjus[j].value)!=''){
									validarCampo(forma.notaEst[j],'- Nota (necesaria para registrar la ausencia)',1)
								}
								if(trim(forma.recEst[j].value)!=''){
									validarCampo(forma.notaEst[j],'- Nota (necesaria para registrar la recuperación)',1)
								}
							}
						}
						
					}else{
						if(document.frmNuevo.notaEst.value!=''){
							validarFloat(document.frmNuevo.notaEst, "- Nota: No es número o está fuera del rango.", notaMin, notaMax);
						}else if(trim(forma.notaEst.value)==''){
							if(trim(forma.ausjus.value)!=''){
								validarCampo(forma.notaEst,'- Nota (necesaria para registrar la ausencia)',1)
							}
							if(trim(forma.recEst.value)!=''){
								validarCampo(forma.notaEst,'- Nota (necesaria para registrar la recuperación)',1)
							}
						}
						
					}
				}
				</c:if>
				
			}
			
			function cancelar(){
				parent.location.href='<c:url value="/bienvenida.jsp"/>';
			}

			function setDefault(forma){
			}

			function guardar(tipo){
				var continua=1;
				var cantidadError=0;
					
				var msjError="Verifique la información suministrada: \n";
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Aceptar";
					//document.frmNuevo.ext2.value='/evaluacion/ControllerEvaluacionEdit.do?tipo=2';

					<c:if test="${sessionScope.tipoEval==2}">						
					if(document.frmNuevo.nota){
						if(document.frmNuevo.nota.length){							
								for(j = 0;j< document.frmNuevo.nota.length;j++){
									<c:if test="${sessionScope.tipoEval==2}">
									if(document.frmNuevo.rec[j].value!=-1){
										document.frmNuevo.nota[j].value = document.frmNuevo.nota_[j].value+"|"+document.frmNuevo.rec[j].value;
									}else{
										document.frmNuevo.nota[j].value = document.frmNuevo.nota_[j].value+"|";
									}
								</c:if>
								<c:if test="${sessionScope.tipoEval!=2}">
									document.frmNuevo.nota[j].value = document.frmNuevo.nota_[j].value+"|"+document.frmNuevo.rec[j].value;
								</c:if> 
								}								
						}else{						
							<c:if test="${sessionScope.tipoEval==2}">
								if(document.frmNuevo.rec.value!=-1){
									document.frmNuevo.nota.value = document.frmNuevo.nota_.value+"|"+document.frmNuevo.rec.value;
								}else{
									document.frmNuevo.nota.value = document.frmNuevo.nota_.value+"|";
								}
							</c:if>
							<c:if test="${sessionScope.tipoEval!=2}">
								document.frmNuevo.nota.value = document.frmNuevo.nota_.value+"|"+document.frmNuevo.rec.value;
							</c:if>
						}
					}
					</c:if>
					
					<c:if test="${sessionScope.tipoEval==1}">
					notaMin=<c:out value="${sessionScope.tipoEvalMin}"/>;
					notaMax=<c:out value="${sessionScope.tipoEvalMax}"/>;	
					if(document.frmNuevo.nota){
						if(document.frmNuevo.nota.length){
							for(j = 0;j< document.frmNuevo.notaEst.length;j++){
								if(document.frmNuevo.notaEst[j].value!=""){
									if(document.frmNuevo.notaEst[j].value < notaMin || document.frmNuevo.notaEst[j].value > notaMax){
										cantidadError=cantidadError+1;
									}
								}
							}
							if(cantidadError==0){
								for(j = 0;j< document.frmNuevo.nota.length;j++){
									document.frmNuevo.nota[j].value = document.frmNuevo.nota[j].value+"|"+document.frmNuevo.notaEst[j].value+"|"+document.frmNuevo.recEst[j].value; 
								}
							}	
						}else{
							if(document.frmNuevo.notaEst.value!=""){
								if(document.frmNuevo.notaEst.value < notaMin || document.frmNuevo.notaEst.value > notaMax){
									cantidadError=cantidadError+1;
								}
							}
							if(cantidadError==0){
								document.frmNuevo.nota.value = document.frmNuevo.nota.value+"|"+document.frmNuevo.notaEst.value+"|"+document.frmNuevo.recEst.value;
							}
						}
					}
					</c:if>
					<c:if test="${sessionScope.tipoEval==3}">
					notaMin=<c:out value="${sessionScope.tipoEvalMin}"/>;
					notaMax=<c:out value="${sessionScope.tipoEvalMax}"/>;	
					if(document.frmNuevo.nota){
						if(document.frmNuevo.nota.length){
							for(j = 0;j< document.frmNuevo.notaEst.length;j++){
								if(document.frmNuevo.notaEst[j].value!=""){
									if(document.frmNuevo.notaEst[j].value < notaMin || document.frmNuevo.notaEst[j].value > notaMax){
										cantidadError=cantidadError+1;
									}
								}
							}
							if(cantidadError==0){
								for(j = 0;j< document.frmNuevo.nota.length;j++){
									document.frmNuevo.nota[j].value = document.frmNuevo.nota[j].value+"|"+document.frmNuevo.notaEst[j].value+"|"+document.frmNuevo.recEst[j].value; 
								}
							}	
						}else{
							if(document.frmNuevo.notaEst.value!=""){
								if(document.frmNuevo.notaEst.value < notaMin || document.frmNuevo.notaEst.value > notaMax){
									cantidadError=cantidadError+1;
								}
							}
							if(cantidadError==0){
								document.frmNuevo.nota.value = document.frmNuevo.nota.value+"|"+document.frmNuevo.notaEst.value+"|"+document.frmNuevo.recEst.value;
							}
						}
					}
					</c:if>
					
					if(cantidadError>0){
						continua=0;
						if(cantidadError>1){
						msjError=msjError+"  "+cantidadError+" registros estan fuera del rango del valor mínimo y máximo de la nota";
						}else if(cantidadError==1){
							msjError=msjError+"  "+cantidadError+" registro esta fuera del rango del valor mínimo y máximo de la nota";}
						alert(msjError);
					}
					if(continua==1){
						document.frmNuevo.submit();
					}
				}
			}
			
			function next(combo,estudiante,logro){
				if(estudiante==est){
					estudiante=1;
					if(logro==log){ logro=1;
					}else{ logro++;}
				}else{
					estudiante++;
				}	
				var posicion=((estudiante*log)-(log-logro))-1;
				if(combo[posicion]) combo[posicion].focus();
			}

			function sigNota(combo,combo2,estudiante,logro){
				if(estudiante==est){
					estudiante=1;
					combo=combo2;					
				}else
					estudiante++;
				var posicion=((estudiante))-1;
				if(combo[posicion]) combo[posicion].focus();
				//else alert(posicion);
			}
			
			function inhabilitar(forma){
				<c:if test="${sessionScope.tipoEval==2}">	
				var jj=getCantidad(forma,"select-one","nota");
				if(jj>1){
					for(var i=0;i<forma.nota.length;i++){
						forma.nota_[i].disabled=true;
						forma.rec[i].disabled=true;
						forma.ausjus[i].disabled=true;
					}
				}else{
						forma.nota_.disabled=true;
						forma.rec.disabled=true;
						forma.ausjus.disabled=true;
				}
				if(document.frmNuevo.encNota){
					forma.encNota.disabled=true;	
				}
				</c:if>	

				<c:if test="${sessionScope.tipoEval==1}">					
				if(document.frmNuevo.nota){
					if(document.frmNuevo.nota.length){
						for(j = 0;j< document.frmNuevo.notaEst.length;j++){
							if(document.frmNuevo.notaEst[j]){
								document.frmNuevo.notaEst[j].disabled=true;
								document.frmNuevo.recEst[j].disabled=true;
								document.frmNuevo.ausjus[j].disabled=true;
							}
						}						
					}else{
						if(document.frmNuevo.notaEst){
							document.frmNuevo.notaEst.disabled=true;
							document.frmNuevo.recEst.disabled=true;
							document.frmNuevo.ausjus.disabled=true;
						}						
					}
				}
				</c:if>

				<c:if test="${sessionScope.tipoEval==3}">					
				if(document.frmNuevo.nota){
					if(document.frmNuevo.nota.length){
						for(j = 0;j< document.frmNuevo.notaEst.length;j++){
							if(document.frmNuevo.notaEst[j]){
								document.frmNuevo.notaEst[j].disabled=true;
								document.frmNuevo.recEst[j].disabled=true;
								document.frmNuevo.ausjus[j].disabled=true;
							}
						}						
					}else{
						if(document.frmNuevo.notaEst){
							document.frmNuevo.notaEst.disabled=true;
							document.frmNuevo.recEst.disabled=true;
							document.frmNuevo.ausjus.disabled=true;
						}						
					}
				}
				</c:if>

				forma.action='';
				forma.target='_blank';
				forma.cerrar_.disabled=true;
				forma.cmd0.style.display='none';
			}


			function inhabilitarFinal(forma){
				<c:if test="${sessionScope.tipoEval==2}">	
				var jj=getCantidad(forma,"select-one","nota");
				if(jj>1){
					for(var i=0;i<forma.nota.length;i++){
						forma.nota_[i].disabled=true;
						forma.rec[i].disabled=true;
						forma.ausjus[i].disabled=true;
					}
				}else{
						forma.nota_.disabled=true;
						forma.rec.disabled=true;
						forma.ausjus.disabled=true;
				}
				if(document.frmNuevo.encNota){
					forma.encNota.disabled=true;	
				}
				</c:if>	

				<c:if test="${sessionScope.tipoEval==1}">					
				if(document.frmNuevo.nota){
					if(document.frmNuevo.nota.length){
						for(j = 0;j< document.frmNuevo.notaEst.length;j++){
							if(document.frmNuevo.notaEst[j]){
								document.frmNuevo.notaEst[j].disabled=true;
								//document.frmNuevo.recEst[j].disabled=true;
								document.frmNuevo.ausjus[j].disabled=true;
							}
						}						
					}else{
						if(document.frmNuevo.notaEst){
							document.frmNuevo.notaEst.disabled=true;
							//document.frmNuevo.recEst.disabled=true;
							document.frmNuevo.ausjus.disabled=true;
						}						
					}
				}
				</c:if>

				<c:if test="${sessionScope.tipoEval==3}">					
				if(document.frmNuevo.nota){
					if(document.frmNuevo.nota.length){
						for(j = 0;j< document.frmNuevo.notaEst.length;j++){
							if(document.frmNuevo.notaEst[j]){
								document.frmNuevo.notaEst[j].disabled=true;
								//document.frmNuevo.recEst[j].disabled=true;
								document.frmNuevo.ausjus[j].disabled=true;
							}
						}						
					}else{
						if(document.frmNuevo.notaEst){
							document.frmNuevo.notaEst.disabled=true;
							//document.frmNuevo.recEst.disabled=true;
							document.frmNuevo.ausjus.disabled=true;
						}						
					}
				}
				</c:if>

				//forma.action='';
				//forma.target='_blank';
				//forma.cerrar_.disabled=true;
				//forma.cmd0.style.display='none';
				
			}
			
			//-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/evaluacion/EvaluacionGuardar.jsp"/>' METHOD="POST" name='frmNuevo' onSubmit="return validarForma(frmNuevo)">
		<input type="hidden" name="cerrar" value='<c:out value="${sessionScope.filtroEvaluacion.cerrar}"/>'>
		<input type="hidden" name="lectura" value='<c:out value="${sessionScope.filtroEvaluacion.lectura}"/>'>
		<input type="hidden" name="cmd" value="">
		<input type="hidden" name="ext2">
		<INPUT TYPE="hidden" NAME="height" VALUE='125'>
		<INPUT TYPE="hidden" NAME="tipo"  VALUE='<c:out value="${pageScope.tip}"/>'>
		<input type='hidden' name='ausjus2' value='-99'>
		<c:if test="${empty sessionScope.filtroResultado}">
	    <table border="0" align="center" width="100%">
			<tr><c:if test="${empty sessionScope.filtroResultado}"><th>No hay estudiantes a evaluar<th></c:if></tr>
		</table>
		</c:if>
			<c:if test="${!empty sessionScope.filtroResultado}">
			<table style="display:" id='t1' name='t1' 	width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<caption>Formulario de evaluación</caption>
				<tr>
			    <td colspan="6">
						<input class='boton' id="cmd0" name="cmd0" type="button" value="Guardar" style="display:" onClick="guardar(document.frmNuevo.tipo.value)">
						<c:if test="${!empty requestScope.plantilla}"><a href='<c:url value="/${requestScope.plantilla}"/>' target='_blank'><img src="../etc/img/xls.gif" alt="Descargar Archivo Excel" border="0"></a></c:if>
						<c:if test="${sessionScope.tipoEval==1}">
							<span class="style3"><b>Valor mínimo nota: <c:out value="${sessionScope.tipoEvalMin}"/> | Valor máximo nota: <c:out value="${sessionScope.tipoEvalMax}"/></b></span>							
						</c:if>
						<c:if test="${sessionScope.tipoEval==3}">
							<span class="style3"><b>Valor mínimo nota: <c:out value="${sessionScope.tipoEvalMin}"/> % | Valor máximo nota: <c:out value="${sessionScope.tipoEvalMax}"/> %</b></span>
						</c:if>
				  </td><script>if(soloLectura==1)document.write('<th><span class="style2">Lista de evaluación solo para consultar</span></th>');</script>
					<c:if test="${sessionScope.filtroEvaluacion.cerrar=='1'}"><th><span class="style2">El registro de evaluación está cerrado</span></th></c:if>					
					<c:if test="${sessionScope.filtroEvaluacion.lectura=='1'}"><th><span class="style2">No tiene permiso de evaluar esta lista</span></th></c:if>
					<td align="right">Cerrar notas: <input type="checkbox" name="cerrar_" onClick='valorar(document.frmNuevo)' value='1' <c:if test="${sessionScope.filtroEvaluacion.cerrar=='1'}">CHECKED</c:if>></td>
				</tr>
				<tr>
				<td>
				
				 <span id="spanabrirPop" ><a href="javascript:void(0);" title="Abrir PopUp" id="abrirPop">Consultar estudiantes que están perdiendo</a></span>
				
				
				
				</td>
				</tr>
			</table>
	    <table style="display:" id='t2' name='t2' width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>					
					<th class="EncabezadoColumna" colspan='3'>Datos de estudiante</th>
					<th class="EncabezadoColumna" rowspan='2'>Nota<br>
						<c:if test="${sessionScope.tipoEval==2}">
						<SELECT NAME='encNota' style='BACKGROUND:#ffffff;COLOR:#000000;' onKeyPress='ponerTodos(document.frmNuevo)' onchange='ponerTodos(document.frmNuevo)'>
							<option value='-1'>&nbsp;</option>
							<c:forEach begin="0" items="${sessionScope.filtroNota2}" var="fila3"><option value='<c:out value="${fila3[0]}"/>'><c:out value="${fila3[1]}"/></option></c:forEach>
							<option value='-2' selected>--</option>
						</select>
					</c:if>
					<c:if test="${sessionScope.tipoEval==3}">
						%
					</c:if>
					</th>
					<th class="EncabezadoColumna" rowspan='2'>Recuperación
					<c:if test="${sessionScope.tipoEval==2}">
					
						<SELECT NAME='encRec' style='BACKGROUND:#ffffff;COLOR:#000000;' onKeyPress='ponerTodosRec(document.frmNuevo)' onchange='ponerTodosRec(document.frmNuevo)'>
							<option value='-1'>&nbsp;</option>
							<c:forEach begin="0" items="${sessionScope.filtroNota2}" var="fila3"><option value='<c:out value="${fila3[0]}"/>'><c:out value="${fila3[1]}"/></option></c:forEach>
							<option value='-2' selected>--</option>
						</select>
					</c:if>	
					</th>
					<th class="EncabezadoColumna" rowspan='2'>Ausencia</th>
				</tr>
				<tr>
					<th class="EncabezadoColumna" width='5%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='35%'>Apellidos</th>
					<th class="EncabezadoColumna" width='35%'>Nombres</th>
				</tr>
				<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="fila" varStatus="st2">
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
					<td class='Fila<c:out value="${st2.count%2}"/>' align='center'><b><c:out value="${st2.count}"/></b></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td>
					<th class='Fila<c:out value="${st2.count%2}"/>' >
					<c:if test="${sessionScope.tipoEval==2}">
						<SELECT  NAME='nota_' class='Fila<c:out value="${st2.count%2}"/>' onKeyPress='acepteNumeros(event,document.frmNuevo.nota_,<c:out value="${st2.count}"/>,1)' onChange='next(document.frmNuevo.nota_,<c:out value="${st2.count}"/>,1)'>
							<option value='-1'>&nbsp;</option>
							<c:forEach begin="0" items="${sessionScope.filtroNota2}" var="fila3"><option value='<c:out value="${fila[0]}"/>|<c:out value="${fila3[0]}"/>' <c:if test="${fila3[0]==fila[6]}">SELECTED</c:if>><c:out value="${fila3[1]}"/></option></c:forEach>
						</select>
						<input type='hidden' name='nota' value=''></input>
						</c:if>
						<c:if test="${sessionScope.tipoEval==1}">						
							<input type='text' name='notaEst' class='Fila<c:out value="${st2.count%2}"/>' onKeyPress='return acepteNumeros3(event,document.frmNuevo.notaEst,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)' size='4' maxlength='4' value='<c:out value="${fila[6]}"/>'>
							<input type='hidden' name='nota' value='<c:out value="${fila[0]}"/>'></input>
						</c:if>
						<c:if test="${sessionScope.tipoEval==3}">
							<input type='text' name='notaEst' class='Fila<c:out value="${st2.count%2}"/>' onKeyPress='acepteNumeros3(event,document.frmNuevo.notaEst,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)'  size='4' maxlength='4' value='<c:out value="${fila[6]}"/>'>
							<input type='hidden' name='nota' value='<c:out value="${fila[0]}"/>'></input>
						</c:if>
					
					</th>	
					
					<th class='Fila<c:out value="${st2.count%2}"/>' >
					<c:if test="${sessionScope.tipoEval==2}">
						<SELECT  NAME='rec' class='Fila<c:out value="${st2.count%2}"/>' onKeyPress='acepteNumeros(event,document.frmNuevo.rec,<c:out value="${st2.count}"/>,1)' onChange='next(document.frmNuevo.rec,<c:out value="${st2.count}"/>,1)'>
							<option value='-1'>&nbsp;</option>
							<c:forEach begin="0" items="${sessionScope.filtroNota2}" var="fila3"><option value='<c:out value="${fila3[0]}"/>' <c:if test="${fila3[0]==fila[8]}">SELECTED</c:if>><c:out value="${fila3[1]}"/></option></c:forEach>
						</select>
						</c:if>
						<c:if test="${sessionScope.tipoEval==1}">						
							<input type='text' name='recEst' class='Fila<c:out value="${st2.count%2}"/>' onKeyPress='return acepteNumeros3(event,document.frmNuevo.notaEst,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)' size='4' maxlength='4' value='<c:out value="${fila[8]}"/>'>
							<input type='hidden' name='rec' value='<c:out value="${fila[0]}"/>'></input>
						</c:if>
						<c:if test="${sessionScope.tipoEval==3}">
							<input type='text' name='recEst' class='Fila<c:out value="${st2.count%2}"/>' onKeyPress='acepteNumeros3(event,document.frmNuevo.notaEst,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)'  size='4' maxlength='4' value='<c:out value="${fila[8]}"/>'>
							<input type='hidden' name='rec' value='<c:out value="${fila[0]}"/>'></input>
						</c:if>
					
					</th>	
					
					<th class='Fila<c:out value="${st2.count%2}"/>'><input type='text' name='ausjus' class='Fila<c:out value="${st2.count%2}"/>'  onKeyPress='return acepteNumeros2(event,document.frmNuevo.ausjus,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)' size='3' maxlength='3' value='<c:if test="${fila[7]!=0}"><c:out value="${fila[7]}"/></c:if>'></th>
				</tr>
				</c:forEach>
			</table></c:if>
		</form>
		
		 <div id="capaPopUp"></div>
    <div id="popUpDiv">
        <div id="capaContent">
            <div>
             <a href="javascript:void(0);" title="Cerrar" id="cerrar">[X]</a>
              <c:if test="${sessionScope.tipoEval==1  || sessionScope.tipoEval==3}">
				      <c:set var="valorminimoaprob" value="${sessionScope.tipoEvalAprobMin}" scope="page" />	
				</c:if>
				<c:if test="${sessionScope.tipoEval!=1 || sessionScope.tipoEval!=3}">
				    <c:set var="valorminimoaprob" value="${sessionScope.tipoEvalAprobMin}" scope="page" />
				</c:if>
				
				<c:set var="minimaina" value="${sessionScope.minimoinasistenciaarea}" scope="page" />
				
              <table>
              <tr>
                 <td class='Fila1' align='center'>La raz&oacute;n de perdida del estudiante est&aacute; resaltada en color:</td>
				<td class='Fila0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>	
              </tr>
              <tr>
                <td class='Fila1'  colspan="2">Nota minima de aprobación es: <c:out value="${pageScope.valorminimoaprob}"/></td>
              </tr>
              <tr>
              <td>El número maximo de inasistencias permitido es:<c:out value="${sessionScope.minimoinasistenciaarea}"/></td>
              </tr>
              </table>
              
              <table style="display:" id='t2' name='t2' width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>					
					<th class="EncabezadoColumna" colspan='3'>Datos de estudiante</th>
					<th class="EncabezadoColumna" rowspan='2'>Nota<br>
					<c:if test="${sessionScope.tipoEval==2}">
						<SELECT NAME='encNota' style='BACKGROUND:#ffffff;COLOR:#000000;' onKeyPress='ponerTodos(document.frmNuevo)' onchange='ponerTodos(document.frmNuevo)'>
							<option value='-1'>&nbsp;</option>
							<c:forEach begin="0" items="${sessionScope.filtroNota2}" var="fila3"><option value='<c:out value="${fila3[0]}"/>'><c:out value="${fila3[1]}"/></option></c:forEach>
							<option value='-2' selected>--</option>
						</select>
					</c:if>
					<c:if test="${sessionScope.tipoEval==3}">
						%
					</c:if>
					</th>
					<th class="EncabezadoColumna" rowspan='2'>Recuperación
					<c:if test="${sessionScope.tipoEval==2}">
					
						<SELECT NAME='encRec' style='BACKGROUND:#ffffff;COLOR:#000000;' onKeyPress='ponerTodosRec(document.frmNuevo)' onchange='ponerTodosRec(document.frmNuevo)'>
							<option value='-1'>&nbsp;</option>
							<c:forEach begin="0" items="${sessionScope.filtroNota2}" var="fila3"><option value='<c:out value="${fila3[0]}"/>'><c:out value="${fila3[1]}"/></option></c:forEach>
							<option value='-2' selected>--</option>
						</select>
					</c:if>	
					</th>
					<th class="EncabezadoColumna" rowspan='2'>Ausencia
									
					</th>
				</tr>
				<tr>
					<th class="EncabezadoColumna" width='5%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='32%'>Apellidos</th>
					<th class="EncabezadoColumna" width='32%'>Nombres</th>
				</tr>
				<%System.out.println("EVAL_ASIG_JSP: inicio foreach: "+new java.util.Date()); %>
				
				
				
				<c:set var="estadoconteoestudiantes" value="true" scope="page" />
				
				<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="fila" varStatus="st2">
				<c:if test="${ (fila[6]<pageScope.valorminimoaprob && fila[6]!=null )  || fila[8]<pageScope.valorminimoaprob  || pageScope.minimaina < fila[7]  }">
		
		                <c:set var="estadoconteoestudiantes" value="false" scope="page" />
				
				<c:set var="clasenota" value="${1}" scope="page" />
				<c:set var="claseasistencias" value="${1}" scope="page" />
				<c:set var="clasenotarecuperada" value="${1}" scope="page" />
				
				
				
				<c:if test="${ fila[6]<pageScope.valorminimoaprob || fila[6]==null}">
				   <c:set var="clasenota" value="${0}" scope="page" />
				</c:if>
				
				<c:if test="${ fila[8]<pageScope.valorminimoaprob}">
				   <c:set var="clasenotarecuperada" value="${0}" scope="page" />
				</c:if>
				
				<c:if test="${ pageScope.minimaina < fila[7] }">
				  <c:set var="claseasistencias" value="${0}" scope="page" />
				</c:if>
				
				
				
				
				
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
					<td class='Fila1' align='center'><b><c:out value="${st2.count}"/></b></td>
					<td class='Fila1' ><c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/></td>
					<td class='Fila1' ><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td>
					<th class='Fila1' >
					<c:if test="${sessionScope.tipoEval==2}">
						<SELECT  NAME='nota_' class='Fila1' onKeyPress='acepteNumeros(event,document.frmNuevo.nota_,<c:out value="${st2.count}"/>,1)' onChange='next(document.frmNuevo.nota_,<c:out value="${st2.count}"/>,1)'>
							<option value='-1'>&nbsp;</option>
							<c:forEach begin="0" items="${sessionScope.filtroNota2}" var="fila3"><option value='<c:out value="${fila[0]}"/>|<c:out value="${fila3[0]}"/>' <c:if test="${fila3[0]==fila[6]}">SELECTED</c:if>><c:out value="${fila3[1]}"/></option></c:forEach>
						</select>
						<input type='hidden' name='nota' value=''></input>
						</c:if>
						<c:if test="${sessionScope.tipoEval==1}">						
							<input type='text' name='notaEst' class='Fila<c:out value="${pageScope.clasenota}"/>' onKeyPress='return acepteNumeros3(event,document.frmNuevo.notaEst,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)' size='4' maxlength='4' value='<c:out value="${fila[6]}"/>'>
							<input type='hidden' name='nota' value='<c:out value="${fila[0]}"/>'></input>
						</c:if>
						<c:if test="${sessionScope.tipoEval==3}">
							<input type='text' name='notaEst' class='Fila<c:out value="${pageScope.clasenota}"/>' onKeyPress='acepteNumeros3(event,document.frmNuevo.notaEst,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)'  size='4' maxlength='4' value='<c:out value="${fila[6]}"/>'>
							<input type='hidden' name='nota' value='<c:out value="${fila[0]}"/>'></input>
						</c:if>
					
					</th>	
					
					<th class='Fila1' >
					<c:if test="${sessionScope.tipoEval==2}">
						<SELECT  NAME='rec' class='Fila1' onKeyPress='acepteNumeros(event,document.frmNuevo.rec,<c:out value="${st2.count}"/>,1)' onChange='next(document.frmNuevo.rec,<c:out value="${st2.count}"/>,1)'>
							<option value='-1'>&nbsp;</option>
							<c:forEach begin="0" items="${sessionScope.filtroNota2}" var="fila3"><option value='<c:out value="${fila3[0]}"/>' <c:if test="${fila3[0]==fila[8]}">SELECTED</c:if>><c:out value="${fila3[1]}"/></option></c:forEach>
						</select>
						</c:if>
						<c:if test="${sessionScope.tipoEval==1}">						
							<input type='text' name='recEst' class='Fila<c:out value="${pageScope.clasenotarecuperada}"/>' onKeyPress='return acepteNumeros3(event,document.frmNuevo.notaEst,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)' size='4' maxlength='4' value='<c:out value="${fila[8]}"/>'>
							<input type='hidden' name='rec' value='<c:out value="${fila[0]}"/>'></input>
						</c:if>
						<c:if test="${sessionScope.tipoEval==3}">
							<input type='text' name='recEst' class='Fila<c:out value="${pageScope.clasenotarecuperada}"/>' onKeyPress='acepteNumeros3(event,document.frmNuevo.notaEst,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)'  size='4' maxlength='4' value='<c:out value="${fila[8]}"/>'>
							<input type='hidden' name='rec' value='<c:out value="${fila[0]}"/>'></input>
						</c:if>
					
					</th>	
									
					<th class='Fila1'><input type='text' name='ausjus' id='ausjus' class='Fila<c:out value="${pageScope.claseasistencias}"/>'  onKeyPress='return acepteNumeros2(event,document.frmNuevo.ausjus,document.frmNuevo.ausjus,<c:out value="${st2.count}"/>,1)' size='3' maxlength='3' value='<c:if test="${fila[7]!=0}"><c:out value="${fila[7]}"/></c:if>'></th>
				</tr>
				</c:if>
				</c:forEach>
				<c:if test="${estadoconteoestudiantes=='true'}">
				<tr>
				   <td colspan="6">
				     No ahi estudiantes perdiendo actualmente.
				   </td>
				</tr>
				</c:if>
				<%System.out.println("EVAL_ASIG_JSP: fin foreach: "+new java.util.Date()); %>
				<tr>
				<td  class='Fila1' colspan="1" > <a href="javascript:void(0);" title="Cerrar" id="cerrar1">[X]</a></td>
				</tr>
			</table>
            
            </div>
           
        </div>
    </div>		
		
		
<script>
<c:if test="${sessionScope.filtroEvaluacion.cerrar=='1'}">inhabilitar(document.frmNuevo);</c:if>
<c:if test="${sessionScope.filtroEvaluacion.lectura=='1'}">inhabilitar(document.frmNuevo);</c:if>
if(soloLectura==1){ inhabilitar(document.frmNuevo);}

//VALIDACION PARA MODO DE EVALUACION SI ES PROMEDIADO
<c:if test="${sessionScope.tipoEval==1}">
<c:if test="${sessionScope.modo_eval==2}">
	<c:if test="${sessionScope.filtroEvaluacion.periodo==7}">
		inhabilitarFinal(document.frmNuevo);
	</c:if>
	<c:if test="${sessionScope.filtroEvaluacion.periodo!=7}">
		inhabilitar(document.frmNuevo);
	</c:if>
</c:if>
</c:if>

<c:if test="${sessionScope.tipoEval==3}">
	<c:if test="${sessionScope.modo_eval==2}">
		<c:if test="${sessionScope.filtroEvaluacion.periodo==7}">
			inhabilitarFinal(document.frmNuevo);
		</c:if>
		<c:if test="${sessionScope.filtroEvaluacion.periodo!=7}">
			inhabilitar(document.frmNuevo);
		</c:if>
	</c:if>
</c:if>

</script>