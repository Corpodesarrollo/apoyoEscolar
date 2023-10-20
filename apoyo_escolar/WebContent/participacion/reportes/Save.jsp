<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroItemsVO" class="participacion.reportes.vo.FiltroItemsVO" scope="session"/>
<jsp:setProperty name="filtroItemsVO" property="*"/>
<c:import url="/participacion/reportes/Nuevo.do"/>