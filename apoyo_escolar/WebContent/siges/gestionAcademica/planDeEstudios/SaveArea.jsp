<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="areaPlanVO" class="siges.gestionAcademica.planDeEstudios.vo.AreaVO" scope="session"/>
<jsp:setProperty name="areaPlanVO" property="*"/>
<jsp:useBean id="filtroAreaPlanVO" class="siges.gestionAcademica.planDeEstudios.vo.FiltroAreaVO" scope="session"/>
<jsp:setProperty name="filtroAreaPlanVO" property="*"/>
<c:import url="/planDeEstudios/Nuevo.do"/>