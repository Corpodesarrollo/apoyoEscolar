<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="pruebaVO" class="articulacion.apcierArtic.vo.PruebaVO" scope="session"/>
<jsp:setProperty name="pruebaVO" property="*"/>
<c:import url="/articulacion/apcierArtic/Lista.do"/>