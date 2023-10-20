<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="seccionNavegadorVO" class="navegador.adminNavegador.vo.SeccionNavegadorVO" scope="session"/>
<jsp:setProperty name="seccionNavegadorVO" property="*"/>
<c:import url="/navegador/adminNavegador/Nuevo.do"/>