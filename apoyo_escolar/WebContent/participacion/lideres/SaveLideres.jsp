<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="lideresVO" class="participacion.lideres.vo.LideresVO" scope="session"/>
<jsp:setProperty name="lideresVO" property="*"/>
<jsp:useBean id="filtroLideresVO" class="participacion.lideres.vo.FiltroLideresVO" scope="session"/>
<jsp:setProperty name="filtroLideresVO" property="*"/>
<c:import url="/participacion/lideres/Nuevo.do"/>