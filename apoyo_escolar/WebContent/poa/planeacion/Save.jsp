<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="planeacionVO" class="poa.planeacion.vo.PlaneacionVO" scope="session"/>
<jsp:setProperty name="planeacionVO" property="*"/>
<jsp:useBean id="filtroPlaneacionVO" class="poa.planeacion.vo.FiltroPlaneacionVO" scope="session"/>
<jsp:setProperty name="filtroPlaneacionVO" property="*"/>
<c:import url="/poa/planeacion/Nuevo.do"/>