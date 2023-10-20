<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroPromocion" class="siges.gestionAcademica.promocion.beans.FiltroPromocion" scope="session"/>
<jsp:setProperty name="filtroPromocion" property="*"/>
<c:import url="promocion/ControllerPromocionSave.do"/>