<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="ciudadaniaPEIVO" class="pei.registro.vo.CiudadaniaVO" scope="session"/>
<jsp:setProperty name="ciudadaniaPEIVO" property="*"/>
<c:import url="/pei/registro/Nuevo.do"/>