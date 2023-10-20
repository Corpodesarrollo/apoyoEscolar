<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="actaVO" class="participacion.acta.vo.ActaVO" scope="session"/>
<jsp:setProperty name="actaVO" property="*"/>
<jsp:useBean id="filtroActaVO" class="participacion.acta.vo.FiltroActaVO" scope="session"/>
<jsp:setProperty name="filtroActaVO" property="*"/>
<c:import url="/participacion/acta/Nuevo.do"/>