<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="desarrolloAdministrativoPEIVO" class="pei.registro.vo.DesarrolloAdministrativoVO" scope="session"/>
<jsp:setProperty name="desarrolloAdministrativoPEIVO" property="*"/>
<c:import url="/pei/registro/Nuevo.do"/>