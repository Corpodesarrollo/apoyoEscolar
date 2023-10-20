<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean  id="filtroReportesVO" class="siges.gestionAdministrativa.comparativos.vo.FiltroReportesVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.comparativos.vo.ParamsVO" scope="page"/>

<script>
<!--	//alert("llego ajax");
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--","-9");
	}
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos--","-9");
	}

	function borrar_combo4(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccion uno--","-9");
	}
 
 	function borrar_combo3(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos","-9");
	}
	
	
	 
	<c:choose>

	<c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_MUN}">
	
 	 borrar_combo2(parent.document.frmNuevo.loc);
 	 
 	  <c:forEach begin="0" items="${sessionScope.listaLocalidad}" var="grupo" varStatus="st">
	    parent.document.frmNuevo.loc.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	   </c:forEach>	

	   
	   borrar_combo2(parent.document.frmNuevo.jornd); 
	  	  <c:forEach begin="0" items="${requestScope.listaJornada}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.jornd.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		   
		   borrar_combo2(parent.document.frmNuevo.metodo); 
		  	<c:forEach begin="0" items="${requestScope.listaMetodo}" var="grupo" varStatus="st">
			   parent.document.frmNuevo.metodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
			</c:forEach> 

			borrar_combo2(parent.document.frmNuevo.grado); 
		  	  <c:forEach begin="0" items="${requestScope.listaGrado}" var="grupo" varStatus="st">
			    parent.document.frmNuevo.grado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
			   </c:forEach> 

			   parent.document.frmNuevo.fechaProm.value="<c:out value="${sessionScope.fechaProm}"/>";					   
			   parent.document.frmNuevo.fechaPromValida.value="<c:out value="${sessionScope.fechaPromValida}"/>";

			   //VALIDACION RANGOS PARA REPORTE 4
			   if(parent.document.frmNuevo.tipoReporte.value==4){
			   var msj="";
				 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==1){
					 	parent.document.frmNuevo.valorIni.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
						parent.document.frmNuevo.valorFin.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
						msj="Escala: Numérica; Valor mínimo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>+"; Valor máximo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>;
				 }
					 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==2){
					  	 borrar_combo4(parent.document.frmNuevo.escala); 
					  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
						    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
						   </c:forEach>
						 msj="Escala: Conceptual - Rango entre 0% y 100%"; 
						 parent.document.frmNuevo.escala.disabled=false;
					 }
						 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==3){
						  	 borrar_combo4(parent.document.frmNuevo.escala); 
						  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
							    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
							   </c:forEach>
							   msj="Escala: MEN - Rango entre 0% y 100%";
							   parent.document.frmNuevo.escala.disabled=false;
							   
						 }
				 //parent.document.frmNuevo.txtmsg.innerHTML = msj;
				 //msj="HOLA: "+<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>;
				   parent.document.getElementById("txtmsg").innerHTML = msj;	
			   }
	
	</c:when>

	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_INST}">
	  	 borrar_combo2(parent.document.frmNuevo.inst); 
	  	  <c:forEach begin="0" items="${sessionScope.listaColegio}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.inst.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    parent.document.frmNuevo.inst.options[<c:out value="${st.count}"/>].title = '<c:out value="${grupo.nombre}"/>';
		   </c:forEach> 

		   borrar_combo2(parent.document.frmNuevo.jornd); 
		  	  <c:forEach begin="0" items="${requestScope.listaJornada}" var="grupo" varStatus="st">
			    parent.document.frmNuevo.jornd.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
			   </c:forEach> 
			   
			   borrar_combo2(parent.document.frmNuevo.metodo); 
			  	<c:forEach begin="0" items="${requestScope.listaMetodo}" var="grupo" varStatus="st">
				   parent.document.frmNuevo.metodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
				</c:forEach> 

				borrar_combo2(parent.document.frmNuevo.grado); 
			  	  <c:forEach begin="0" items="${requestScope.listaGrado}" var="grupo" varStatus="st">
				    parent.document.frmNuevo.grado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
				   </c:forEach> 

				   parent.document.frmNuevo.fechaProm.value="<c:out value="${sessionScope.fechaProm}"/>";					   
				   parent.document.frmNuevo.fechaPromValida.value="<c:out value="${sessionScope.fechaPromValida}"/>";

				 //VALIDACION RANGOS PARA REPORTE 4
				   if(parent.document.frmNuevo.tipoReporte.value==4){
				   var msj="";
					 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==1){
						 	parent.document.frmNuevo.valorIni.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
							parent.document.frmNuevo.valorFin.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
							msj="Escala: Numérica; Valor mínimo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>+"; Valor máximo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>;
					 }else
						 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==2){
						  	 borrar_combo4(parent.document.frmNuevo.escala); 
						  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
							    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
							   </c:forEach>
							 msj="Escala: Conceptual - Rango entre 0% y 100%"; 
							 parent.document.frmNuevo.escala.disabled=false;
						 }else
							 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==3){
							  	 borrar_combo4(parent.document.frmNuevo.escala); 
							  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
								    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
								   </c:forEach>
								   msj="Escala: MEN - Rango entre 0% y 100%";
								   parent.document.frmNuevo.escala.disabled=false;
								   
							 }
					 //parent.document.getElementById("txtmsg").innerHTML = msj;					 
					 parent.document.getElementById("txtmsg").innerHTML = msj;
				   }
	 
	</c:when>

	
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_SEDE}">
	  	 borrar_combo2(parent.document.frmNuevo.sede); 
	  	  <c:forEach begin="0" items="${sessionScope.listaSede}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.sede.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 

		   borrar_combo2(parent.document.frmNuevo.metodo); 
		  	<c:forEach begin="0" items="${requestScope.listaMetodo}" var="grupo" varStatus="st">
			   parent.document.frmNuevo.metodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
			</c:forEach> 
			

		   borrar_combo2(parent.document.frmNuevo.jornd); 
		  	  <c:forEach begin="0" items="${requestScope.listaJornada}" var="grupo" varStatus="st">
			    parent.document.frmNuevo.jornd.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
			   </c:forEach> 
			   
			  
				borrar_combo2(parent.document.frmNuevo.grado); 
			  	  <c:forEach begin="0" items="${requestScope.listaGrado}" var="grupo" varStatus="st">
				    parent.document.frmNuevo.grado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
				   </c:forEach>


				   borrar_combo4(parent.document.frmNuevo.periodo); 
				  	  <c:forEach begin="0" items="${requestScope.listaPeriodo}" var="grupo" varStatus="st">
					    parent.document.frmNuevo.periodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
					   </c:forEach>  
					   
					   parent.document.frmNuevo.fechaProm.value="<c:out value="${sessionScope.fechaProm}"/>";					   
					   parent.document.frmNuevo.fechaPromValida.value="<c:out value="${sessionScope.fechaPromValida}"/>";


					 //VALIDACION RANGOS PARA REPORTE 4
					   if(parent.document.frmNuevo.tipoReporte.value==4){
					   var msj="";
						 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==1){
							 	parent.document.frmNuevo.valorIni.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
								parent.document.frmNuevo.valorFin.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
								parent.document.frmNuevo.valorIni_.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
								parent.document.frmNuevo.valorFin_.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
								msj="Escala: Numérica; Valor mínimo: "+<c:out value='${sessionScope.filtroReportesVO.valorIni}'/>+"; Valor máximo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>;
								parent.document.frmNuevo.escala.disabled=true;
						 }
							 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==2){
							  	 borrar_combo4(parent.document.frmNuevo.escala); 
							  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
								    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
								   </c:forEach>
								 msj="Escala: Conceptual - Rango entre 0% y 100%"; 
								 parent.document.frmNuevo.escala.disabled=false;
							 }
								 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==3){
								  	 borrar_combo4(parent.document.frmNuevo.escala); 
								  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
									    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
									   </c:forEach>
									   msj="Escala: MEN - Rango entre 0% y 100%";
									   parent.document.frmNuevo.escala.disabled=false;
									   
								 }
						 parent.document.getElementById("txtmsg").innerHTML = msj;
						 //parent.document.frmNuevo.txtmsg.innerHTML = msj;	
								 	
					   }
	 
	</c:when>
	
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_JORD}">
	  	 borrar_combo2(parent.document.frmNuevo.jornd); 
	  	  <c:forEach begin="0" items="${sessionScope.listaJornada}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.jornd.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 

		   
			   
			  
				borrar_combo2(parent.document.frmNuevo.grado); 
			  	  <c:forEach begin="0" items="${requestScope.listaGrado}" var="grupo" varStatus="st">
				    parent.document.frmNuevo.grado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
				   </c:forEach>


				   //VALIDACION RANGOS PARA REPORTE 4
				   if(parent.document.frmNuevo.tipoReporte.value==4){
				   var msj="";
					 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==1){
						 	parent.document.frmNuevo.valorIni.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
							parent.document.frmNuevo.valorFin.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
							parent.document.frmNuevo.valorIni_.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
							parent.document.frmNuevo.valorFin_.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
							msj="Escala: Numérica; Valor mínimo: "+<c:out value='${sessionScope.filtroReportesVO.valorIni}'/>+"; Valor máximo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>;
							parent.document.frmNuevo.escala.disabled=true;
					 }
						 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==2){
						  	 borrar_combo4(parent.document.frmNuevo.escala); 
						  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
							    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
							   </c:forEach>
							 msj="Escala: Conceptual - Rango entre 0% y 100%"; 
							 parent.document.frmNuevo.escala.disabled=false;
						 }
							 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==3){
							  	 borrar_combo4(parent.document.frmNuevo.escala); 
							  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
								    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
								   </c:forEach>
								   msj="Escala: MEN - Rango entre 0% y 100%";
								   parent.document.frmNuevo.escala.disabled=false;
								   
							 }
					 parent.document.getElementById("txtmsg").innerHTML = msj;
					 //parent.document.frmNuevo.txtmsg.innerHTML = msj;	
							 	
				   }
				   
	 
	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_METD}">
	  	 borrar_combo2(parent.document.frmNuevo.metodo); 
	  	  <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="grupo" varStatus="st">
		    parent.document.frmNuevo.metodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 

		   borrar_combo2(parent.document.frmNuevo.grado); 
		  	  <c:forEach begin="0" items="${requestScope.listaGrado}" var="grupo" varStatus="st">
			    parent.document.frmNuevo.grado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
			   </c:forEach> 


			   //VALIDACION RANGOS PARA REPORTE 4
			   if(parent.document.frmNuevo.tipoReporte.value==4){
			   var msj="";
				 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==1){
					 	parent.document.frmNuevo.valorIni.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
						parent.document.frmNuevo.valorFin.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
						parent.document.frmNuevo.valorIni_.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
						parent.document.frmNuevo.valorFin_.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
						msj="Escala: Numérica; Valor mínimo: "+<c:out value='${sessionScope.filtroReportesVO.valorIni}'/>+"; Valor máximo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>;
						parent.document.frmNuevo.escala.disabled=true;
				 }
					 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==2){
					  	 borrar_combo4(parent.document.frmNuevo.escala); 
					  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
						    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
						   </c:forEach>
						 msj="Escala: Conceptual - Rango entre 0% y 100%"; 
						 parent.document.frmNuevo.escala.disabled=false;
					 }
						 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==3){
						  	 borrar_combo4(parent.document.frmNuevo.escala); 
						  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
							    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
							   </c:forEach>
							   msj="Escala: MEN - Rango entre 0% y 100%";
							   parent.document.frmNuevo.escala.disabled=false;
							   
						 }
				 parent.document.getElementById("txtmsg").innerHTML = msj;
				 //parent.document.frmNuevo.txtmsg.innerHTML = msj;	
						 	
			   }
			   
		 
	</c:when>
	
		  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRAD}">
	  	 borrar_combo2(parent.document.frmNuevo.grado); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.grado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 


		   //VALIDACION RANGOS PARA REPORTE 4
		   if(parent.document.frmNuevo.tipoReporte.value==4){
		   var msj="";
			 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==1){
				 	parent.document.frmNuevo.valorIni.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
					parent.document.frmNuevo.valorFin.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
					parent.document.frmNuevo.valorIni_.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
					parent.document.frmNuevo.valorFin_.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
					msj="Escala: Numérica; Valor mínimo: "+<c:out value='${sessionScope.filtroReportesVO.valorIni}'/>+"; Valor máximo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>;
					parent.document.frmNuevo.escala.disabled=true;
			 }
				 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==2){
				  	 borrar_combo4(parent.document.frmNuevo.escala); 
				  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
					    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
					   </c:forEach>
					 msj="Escala: Conceptual - Rango entre 0% y 100%"; 
					 parent.document.frmNuevo.escala.disabled=false;
				 }
					 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==3){
					  	 borrar_combo4(parent.document.frmNuevo.escala); 
					  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
						    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
						   </c:forEach>
						   msj="Escala: MEN - Rango entre 0% y 100%";
						   parent.document.frmNuevo.escala.disabled=false;
						   
					 }
			 parent.document.getElementById("txtmsg").innerHTML = msj;
			 //parent.document.frmNuevo.txtmsg.innerHTML = msj;	
					 	
		   }
		 
	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRUP }">
	  	 borrar_combo2(parent.document.frmNuevo.grupo); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrupo}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.grupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 


		   //VALIDACION RANGOS PARA REPORTE 4
		   if(parent.document.frmNuevo.tipoReporte.value==4){
		   var msj="";
			 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==1){
				 	parent.document.frmNuevo.valorIni.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
					parent.document.frmNuevo.valorFin.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
					parent.document.frmNuevo.valorIni_.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
					parent.document.frmNuevo.valorFin_.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
					msj="Escala: Numérica; Valor mínimo: "+<c:out value='${sessionScope.filtroReportesVO.valorIni}'/>+"; Valor máximo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>;
					parent.document.frmNuevo.escala.disabled=true;
			 }
				 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==2){
				  	 borrar_combo4(parent.document.frmNuevo.escala); 
				  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
					    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
					   </c:forEach>
					 msj="Escala: Conceptual - Rango entre 0% y 100%"; 
					 parent.document.frmNuevo.escala.disabled=false;
				 }
					 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==3){
					  	 borrar_combo4(parent.document.frmNuevo.escala); 
					  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
						    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
						   </c:forEach>
						   msj="Escala: MEN - Rango entre 0% y 100%";
						   parent.document.frmNuevo.escala.disabled=false;
						   
					 }
			 parent.document.getElementById("txtmsg").innerHTML = msj;
			 //parent.document.frmNuevo.txtmsg.innerHTML = msj;	
					 	
		   }
		  
	</c:when>


	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_RANGO}">
	 var msj="";
	 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==1){
		 	parent.document.frmNuevo.valorIni.value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'
			parent.document.frmNuevo.valorFin.value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'
			msj="Escala: Numérica; Valor mínimo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>+"; Valor máximo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>;
	 }else
		 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==2){
		  	 borrar_combo2(parent.document.frmNuevo.escala); 
		  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
			    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
			   </c:forEach>
			 msj="Escala: Conceptual - Rango entre 0% y 100%"; 
		 }else
			 if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==3){
			  	 borrar_combo2(parent.document.frmNuevo.escala); 
			  	  <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="esc" varStatus="st">
				    parent.document.frmNuevo.escala.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esc.nombre}"/>','<c:out value="${esc.codigo}"/>' );
				   </c:forEach>
				   msj="Escala: MEN - Rango entre 0% y 100%";
				   
			 }
	 parent.document.getElementById("txtmsg").innerHTML = msj;			 
	  
</c:when>
	
	
	</c:choose>	
//-->
</script>