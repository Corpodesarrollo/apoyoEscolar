<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroBorrarVO" class="articulacion.artAdminPlanEstudios.vo.FiltroBorrarVO" scope="session"/>
<jsp:setProperty name="filtroBorrarVO" property="*"/>
<c:import url="/articulacion/artAdminPlanEstudios/Filtro.do"/>