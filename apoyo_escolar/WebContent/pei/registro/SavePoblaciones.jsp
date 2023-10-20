<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="poblacionesPEIVO" class="pei.registro.vo.PoblacionesVO" scope="session"/>
<jsp:setProperty name="poblacionesPEIVO" property="*"/>
<c:import url="/pei/registro/Nuevo.do"/>