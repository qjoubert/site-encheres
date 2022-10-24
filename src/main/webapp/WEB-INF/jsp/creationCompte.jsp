<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Créer un compte</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base-styles.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/creationCompte.css">
</head>
<body>
	<jsp:include page="../include/header.jsp"></jsp:include>
	<main>
		<h1>Créer un compte</h1>
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
		<form action="${pageContext.request.contextPath}/creationCompte" method="post">
			<label for="pseudo">Pseudo : </label>
			<input type="text" id="pseudo" name="pseudo" required>
			
			<label for="nom">Nom : </label>
			<input type="text" id="nom" name="nom" required>
			
			<label for="prenom">Prénom : </label>
			<input type="text" id="prenom" name="prenom" required>
			
			<label for="email">Email : </label>
			<input type="text" id="email" name="email" required>
			
			<label for="telephone">Telephone : </label>
			<input type="text" id="telephone" name="telephone">
			
			<label for="rue">Rue : </label>
			<input type="text" id="rue" name="rue" required>
			
			<label for="codePostal">Code postal : </label>
			<input type="text" id="codePostal" name="codePostal" required>
			
			<label for="ville">Ville : </label>
			<input type="text" id="ville" name="ville" required>
			
			<label for="motDePasse">Mot de passe : </label>
			<input type="password" id="motDePasse" name="motDePasse" required>
			
			<label for="password">Confirmation : </label>
			<input type="password" id="password" name="confirmationMotDePasse" required>
			
			<div class="form-btns">
				<input type="submit" class="btn" value="Créer">
				<a class="btn" href="${pageContext.request.contextPath}/accueil">Annuler</a>
			</div>
		</form>
	</main>
	
	<jsp:include page="../include/footer.jsp"></jsp:include>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/responsiveNav.js"></script>
</body>
</html>