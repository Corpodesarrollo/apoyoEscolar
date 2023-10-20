<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="rangoVO" class="participacion.instancia.vo.RangoVO" scope="session"/>
<jsp:setProperty name="rangoVO" property="*"/>
<jsp:useBean id="filtroRangoVO" class="participacion.instancia.vo.FiltroRangoVO" scope="session"/>
<jsp:setProperty name="filtroRangoVO" property="*"/>
<c:import url="/participacion/instancia/Nuevo.do"/>