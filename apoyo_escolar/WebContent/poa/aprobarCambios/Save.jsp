<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="cambioVO" class="poa.aprobarCambios.vo.CambioVO" scope="session"/>
<jsp:setProperty name="cambioVO" property="*"/>
<jsp:useBean id="filtroCambioVO" class="poa.aprobarCambios.vo.FiltroCambiosVO" scope="session"/>
<jsp:setProperty name="filtroCambioVO" property="*"/>
<c:import url="/poa/aprobarCambios/Nuevo.do"/>