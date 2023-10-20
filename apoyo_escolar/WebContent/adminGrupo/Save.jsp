<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="competenciasVO" class="siges.planEstudios.vo.CompetenciasVO" scope="session"/>
<jsp:setProperty name="competenciasVO" property="*"/>
<jsp:useBean  id="dimensionVO" class="siges.planEstudios.vo.DimensionesVO" scope="session"/>
<jsp:setProperty name="dimensionVO" property="*"/>
<jsp:useBean  id="filtroComsVO" class="siges.planEstudios.vo.FiltroComsVO" scope="session"/>
<jsp:setProperty name="filtroComsVO" property="*"/>
<c:import url="/planEstudios/Nuevo.do"/>