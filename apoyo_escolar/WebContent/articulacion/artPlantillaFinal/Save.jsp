<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="plantillaFinalVO" class="articulacion.artPlantillaFinal.vo.PlantillaFinalVO" scope="session"/>
<jsp:setProperty name="plantillaFinalVO" property="*"/>
<c:import url="/articulacion/artPlantillaFinal/Filtro.do"/>