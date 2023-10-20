<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="planDeEstudiosVO" class="siges.gestionAcademica.planDeEstudios.vo.PlanDeEstudiosVO" scope="session"/>
<jsp:setProperty name="planDeEstudiosVO" property="*"/>
<jsp:useBean id="filtroPlanDeEstudiosVO" class="siges.gestionAcademica.planDeEstudios.vo.FiltroPlanDeEstudiosVO" scope="session"/>
<jsp:setProperty name="filtroPlanDeEstudiosVO" property="*"/>
<c:import url="/planDeEstudios/Nuevo.do"/>