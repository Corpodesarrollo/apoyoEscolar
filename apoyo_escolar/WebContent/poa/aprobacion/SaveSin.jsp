<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="aprobacionSinVO" class="poa.aprobacion.vo.PlaneacionVO" scope="session"/>
<jsp:setProperty name="aprobacionSinVO" property="*"/>
<jsp:useBean id="filtroAprobacionVO" class="poa.aprobacion.vo.FiltroPlaneacionVO" scope="session"/>
<jsp:setProperty name="filtroAprobacionVO" property="*"/>
<c:import url="/poa/aprobacion/Nuevo.do"/>