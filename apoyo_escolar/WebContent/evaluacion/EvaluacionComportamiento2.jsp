<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="tip" value="10" scope="page"/>
<jsp:useBean id="filtroComportamiento" class="siges.evaluacion.beans.FiltroComportamiento" scope="session"/><jsp:setProperty name="filtroComportamiento" property="*"/>
		<script language="JavaScript">
			<!--
			var soloLectura=<c:out value="${sessionScope.NivelPermiso}"/>;
			var nav4=window.Event ? true : false;
			var est=0;
			var notaMin=0;
			var notaMax=0;
			
			function cancelar(){
				parent.location.href='<c:url value="/bienvenida.jsp"/>';
			}

			 function acepteNumeros3(eve,combo,combo2,a,b){
		         // alert("a: "+a+"  b: "+b);
		          var key=nav4?eve.which :eve.keyCode;
		          //alert("key: "+key);
		          if(key==13){
		               sigNota(combo,combo2,a,b);
		           }
		          if((key>=48 && key<=57) || key==46  || key==8 || key==0){
		  				return(true);
		          }
		          else{
		        		return(false);
		          }
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
			
			function cambioNota(obj){
				var tab=parseInt(obj.tabIndex);
				var forma=document.frmNuevo;
				if(forma.eval2[tab+1]){
					forma.eval2[tab+1].focus();
				}
			}

			function hacerValidaciones_frmNuevo(forma){
				
			}
			
			function updateDatos(forma){
				if(forma.eval){
					if(forma.eval.length){
						for(var i=0;i<forma.eval.length;i++){
							forma.eval[i].value=forma.eval[i].value+'|'+forma.eval3[i].value;
							/*
							<c:if test="${sessionScope.tipoEval==2}">
							if(forma.eval2[i].selectedIndex==0){
								//forma.eval[i].disabled=true;
								forma.eval[i].value='-99';
							}else{
								forma.eval[i].value=forma.eval[i].value+'|'+forma.eval2[i].options[forma.eval2[i].selectedIndex].value+'|'+forma.eval3[i].value;
							}
							</c:if>

							<c:if test="${sessionScope.tipoEval==4}">
							
							if(forma.eval2[i].selectedIndex==0){
								//forma.eval[i].disabled=true;
								forma.eval[i].value='-99';
							}else{
								
								forma.eval[i].value=forma.eval[i].value+'|'+forma.eval2[i].options[forma.eval2[i].selectedIndex].value+'|'+forma.eval3[i].value;
								//alert("SEL: "+forma.eval[i].value);
							}
							</c:if>
							
							<c:if test="${sessionScope.tipoEval==1}">
							if(forma.eval2[i].value==''){
								//forma.eval[i].disabled=true;
								forma.eval[i].value='-99';
							}else{
								forma.eval[i].value=forma.eval[i].value+'|'+forma.eval2[i].value+'|'+forma.eval3[i].value;
							}
							</c:if>
							<c:if test="${sessionScope.tipoEval==3}">
							if(forma.eval2[i].value==''){
								//forma.eval[i].disabled=true;								
								forma.eval[i].value='-99';
							}else{
								forma.eval[i].value=forma.eval[i].value+'|'+forma.eval2[i].value+'|'+forma.eval3[i].value;
							}
							</c:if>
							*/
						}
					}
				}
				return true;
			}
			
			function guardar(tipo){
				<c:if test="${sessionScope.tipoEval==1}">
				notaMin=<c:out value="${sessionScope.tipoEvalMin}"/>;
				notaMax=<c:out value="${sessionScope.tipoEvalMax}"/>;
				</c:if>
				<c:if test="${sessionScope.tipoEval==3}">
				notaMin=<c:out value="${sessionScope.tipoEvalMin}"/>;
				notaMax=<c:out value="${sessionScope.tipoEvalMax}"/>;
				</c:if>
				//alert("GUARDAR");
				if(validarForma(document.frmNuevo)){
					//alert("PASO VALIDAR");
					if(updateDatos(document.frmNuevo)){
					//	alert("PASO UPDATE DATOS");
						document.frmNuevo.tipo.value=tipo;
						document.frmNuevo.cmd.value="Aceptar";
						document.frmNuevo.submit();
					}	
				}
			}


			function inhabilitar(forma){
				<c:if test="${sessionScope.tipoEval==2}">	
				var jj=getCantidad(forma,"select-one","nota");
				if(jj>1){
					for(var i=0;i<forma.eval.length;i++){
						forma.eval2[i].disabled=true;
						forma.eval3[i].disabled=true;
					}
				}else{
						forma.eval2.disabled=true;
						forma.eval3.disabled=true;
				}				
				</c:if>	

				<c:if test="${sessionScope.tipoEval==4}">	
				var jj=getCantidad(forma,"select-one","nota");
				if(jj>1){
					for(var i=0;i<forma.eval.length;i++){
						forma.eval2[i].disabled=true;
						forma.eval3[i].disabled=true;
					}
				}else{
						forma.eval2.disabled=true;
						forma.eval3.disabled=true;
				}				
				</c:if>	

				<c:if test="${sessionScope.tipoEval==1}">					
				if(document.frmNuevo.eval){
					if(document.frmNuevo.eval.length){
						for(j = 0;j< document.frmNuevo.eval2.length;j++){
							if(document.frmNuevo.eval2[j]){
								document.frmNuevo.eval2[j].disabled=true;
								document.frmNuevo.eval3[j].disabled=true;
							}
						}						
					}else{
						if(document.frmNuevo.eval2){
							document.frmNuevo.eval2.disabled=true;
							document.frmNuevo.eval3.disabled=true;
						}						
					}
				}
				</c:if>

				<c:if test="${sessionScope.tipoEval==3}">					
				if(document.frmNuevo.eval){
					if(document.frmNuevo.eval.length){
						for(j = 0;j< document.frmNuevo.eval2.length;j++){
							if(document.frmNuevo.eval2[j]){
								document.frmNuevo.eval2[j].disabled=true;
								document.frmNuevo.eval3[j].disabled=true;
							}
						}						
					}else{
						if(document.frmNuevo.eval2){
							document.frmNuevo.eval2.disabled=true;
							document.frmNuevo.eval3.disabled=true;
						}						
					}
				}
				</c:if>

				forma.action='';
				forma.target='_blank';
				//forma.cerrar_.disabled=true;
				forma.cmd0.style.display='none';
			}
			
			//-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/evaluacion/Save.jsp"/>' METHOD="POST" name='frmNuevo' onSubmit="return validarForma(frmNuevo)">
		<input type="hidden" name="cerrar" value=''>
		<input type="hidden" name="lectura" value=''>
		<input type="hidden" name="cmd" value="">
		<input type="hidden" name="ext2">
		<INPUT TYPE="hidden" NAME="height" VALUE='125'>
		<INPUT TYPE="hidden" NAME="tipo"  VALUE='<c:out value="${pageScope.tip}"/>'>
		<c:if test="${empty requestScope.estudiantesCom}">
	    <table border="0" align="center" width="100%">
			<tr><th>No hay estudiantes a evaluar<th></tr>
		</table>
		</c:if>
		<c:if test="${!empty requestScope.estudiantesCom}">
			<table style="display:" id='t1' name='t1' width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
			<caption>Formulario de evaluación</caption>
				<tr>
			    <td colspan="6">
						<input class='boton' id="cmd0" name="cmd0" type="button" value="Guardar" style="display:" onClick="guardar(document.frmNuevo.tipo.value)">
						
						<c:if test="${sessionScope.tipoEval==1}">
							<span class="style3"><b>Valor mínimo nota: <c:out value="${sessionScope.tipoEvalMin}"/> | Valor máximo nota: <c:out value="${sessionScope.tipoEvalMax}"/></b></span>							
						</c:if>
						<c:if test="${sessionScope.tipoEval==3}">
							<span class="style3"><b>Valor mínimo nota: <c:out value="${sessionScope.tipoEvalMin}"/> % | Valor máximo nota: <c:out value="${sessionScope.tipoEvalMax}"/> %</b></span>
						</c:if>
						
					</td><script>if(soloLectura==1)document.write('<th><span class="style2">Lista de evaluación solo para consultar</span></th>');</script>
				</tr>
			</table>
	    <table style="display:" id='t2' name='t2' width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>
					<th class="EncabezadoColumna" colspan='3'>Datos de estudiante</th>
					<th class="EncabezadoColumna" rowspan='2'>Observación</th>
				</tr>
				<tr>
					<th class="EncabezadoColumna" width='1%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='23%'>Apellidos</th>
					<th class="EncabezadoColumna" width='20%'>Nombres</th>
				</tr>
				<c:forEach begin="0" items="${requestScope.estudiantesCom}" var="estudiante" varStatus="st2">
				<input type="hidden" name="eval" value='<c:out value="${estudiante.evalCodigo}"/>'>
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
					<td class='Fila<c:out value="${st2.count%2}"/>' align='center'><b><c:out value="${estudiante.evalConsecutivo}"/></b></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${estudiante.evalApellido}"/></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${estudiante.evalNombre}"/></td>
					<th class='Fila<c:out value="${st2.count%2}"/>' >
						<input type="hidden" name="eval2" id="eval2">
						<textarea class='Fila<c:out value="${st2.count%2}"/>' name='eval3' id='eval3' cols="75" rows="2" wrap='virtual'><c:out value="${estudiante.evalObservacion}"/></textarea>
					</th>
				</tr>
				</c:forEach>
			</table>
		</c:if>
		</form>
<script>
if(soloLectura==1){ inhabilitar(document.frmNuevo);}
</script>