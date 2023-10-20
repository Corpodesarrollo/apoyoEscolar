<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroGrupos" class="siges.adminGrupo.vo.FiltroGrupoVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminGrupo.vo.ParamsVO" scope="page"/>
<html>
<head>
  
<script languaje="javaScript">

  
	 
  function editar(vig, inst, metodo,codCodigo){
	  //alert("params inst:"+inst+"  vig:"+vig+"  metod:"+metodo+"  codigo:"+codCodigo);
	 if(document.frmFiltro.param){
			 document.frmFiltro.param[0].value = inst;
			 document.frmFiltro.param[1].value = vig;
			 document.frmFiltro.param[2].value = metodo;			 		 
			 document.frmFiltro.param[3].value =  codCodigo;
			 document.frmFiltro.cmd.value = document.frmFiltro.EDITAR.value;
			 document.frmFiltro.submit();
		}
	}
	
	
  function eliminar(inst, vig, metodo,codCodigo){
   if(document.frmFiltro.param  && confirm("¿Esta seguro de eliminar esta información?")){
			 document.frmFiltro.param[0].value = inst;
			 document.frmFiltro.param[1].value = vig;
			 document.frmFiltro.param[2].value = metodo;			  
			 document.frmFiltro.param[3].value =  codCodigo;
		document.frmFiltro.cmd.value = document.frmFiltro.ELIMINAR.value;
		document.frmFiltro.submit();
	}
  }
	
  function buscar(){
	if(validarForma(document.frmFiltro)){
	    document.frmFiltro.cmd.value = document.frmFiltro.BUSCAR.value;
		document.frmFiltro.submit();
	}
  }
	
  function hacerValidaciones_frmFiltro(forma){
    if(forma.filComvigencia){
      validarLista(forma.filComvigencia,'- Vigencia');
      validarLista(forma.filCommetodo,'- Metodologia');
   }
  }
	   
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 
	<form method="post" name="frmFiltro"  action='<c:url value="/planEstudios/Save.jsp"/>'>
		
		 
	
	    <input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DIMENSIONES}"/>'>
		<input type="hidden" name="cmd" value=''>
		<c:forEach begin="0" end="12"><input type="hidden" name="param" value=''></c:forEach>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="filCominst" id="filCominst" value='<c:out value="${sessionScope.filtroComsVO.filCominst}"/>'>
		<input type="hidden" name="filComvigencia_kd" id="filComvigencia_" value='<c:out value="${sessionScope.filtroComsVO.filComvigencia}"/>'>		
        <input type="hidden" name="filCommetodo_" id="filCommetodo_" value='<c:out value="${sessionScope.filtroComsVO.filCommetodo}"/>'>
		 
		<table cellpadding="2" cellspacing="2"  width="100%">
		<tr> <td   bgcolor="#FFFFFF"><input class='boton' name="cmd1" type="button" value="Buscar" onClick="buscar()"></td>
			 </tr>
	      <tr><td width="80px"><font color="red">*</font>Vigencia</td>
		      <td><select name="filComvigencia"   id="filComvigencia" style="width: 60px;"></select>
			  </td>
			  <td width="80px"><font color="red">*</font>Metodologia</td>
			    <td width="120px"><select name="filCommetodo" id="filCommetodo" style="width: 160px;"></select>
			  </td>
		   </tr>
		 </table>
		
		 <!-- LISTA DE RESULTADOS -->
         <div style="width:100%;height:100px;overflow:auto;vertical-align:top;background-color:#ffffff;">
		   <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray" class="table01" style="text-align: left;" >
			 	<caption>Lista Dimensiones</caption>
			 	<c:if test="${empty requestScope.listaDimensiones}"><tr><th class="Fila1" colspan='6'>No hay datos</th></tr></c:if>
				<c:if test="${!empty requestScope.listaDimensiones}">
					<tr> 
						<th class="EncabezadoColumna" align="center">&nbsp;</th>						
						<th class="EncabezadoColumna" align="center">Nombre</th>
						<th class="EncabezadoColumna" align="center">Abreviatura</th> 
						<th class="EncabezadoColumna" align="center">Orden</th>
					</tr>
					<c:forEach begin="0" items="${requestScope.listaDimensiones}" var="lista" varStatus="st">
						<tr>
						  <th class='Fila<c:out value="${st.count%2}"/>' width="40px;">
							<a href='javaScript:editar(<c:out value="${lista.vigencia}"/>,<c:out value="${lista.codinst}"/>,<c:out value="${lista.codmetod}"/>,<c:out value="${lista.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2 }">
							<a href='javaScript:eliminar(<c:out value="${lista.vigencia}"/>,<c:out value="${lista.codinst}"/>,<c:out value="${lista.codmetod}"/>,<c:out value="${lista.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						   </th>							 
							<td class='Fila<c:out value="${st.count%2}"/>'  align="center" ><c:out value="${lista.nombre}"/> </td>
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.abrev}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.orden}"/></td>							
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>		 
	 </form>	 
	</body>
</html>