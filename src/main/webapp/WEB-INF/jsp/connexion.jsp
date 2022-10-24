<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Se connecter</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base-styles.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/connexion.css">
</head>
<body>
	<jsp:include page="../include/header.jsp"></jsp:include>
	
	<main>
		<c:if test="${!empty listeCodesErreur}">
			<div class="alert alert-danger" role="alert">
			  <strong>Erreur!</strong>
			  <ul>
			  	<c:forEach var="code" items="${listeCodesErreur}">
			  		<li>${LecteurMessage.getMessageErreur(code)}</li>
			  	</c:forEach>
			  </ul>
			</div>
		</c:if>
		<form action="${pageContext.request.contextPath}/connexion" method="post">
			<label for="pseudo">Identifiant : </label>
			<input type="text" id="pseudo" name="pseudo" required>
			
			<label for="motDePasse">Mot de passe : </label>
			<input type="password" id="motDePasse" name="motDePasse" required>
			
				<input type="submit" class="btn" value="Connexion">
				<div class="container">
					<label>
						<input type="checkbox" name="sauvegarder">
						Se souvenir de moi
					</label>
					<a href="">Mot de passe oublié</a>
				</div>
		</form>
		<a id="lien-creation-compte" class="btn" href="${pageContext.request.contextPath}/creationCompte">Créer un compte</a>
	</main>
	
	<jsp:include page="../include/footer.jsp"></jsp:include>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/responsiveNav.js"></script>
</body>
</html>