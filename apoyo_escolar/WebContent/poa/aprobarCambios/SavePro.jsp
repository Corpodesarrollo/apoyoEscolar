<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="cambioActividadesSinVO" class="poa.aprobacionActividades.vo.PlaneacionVO" scope="session"/>
<jsp:setProperty name="cambioActividadesSinVO" property="*"/>
<c:import url="/poa/aprobarCambios/Nuevo.do"/>