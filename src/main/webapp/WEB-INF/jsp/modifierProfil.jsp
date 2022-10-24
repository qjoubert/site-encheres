<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Modifier le profil</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base-styles.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/modifierProfil.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>

<body>
	<jsp:include page="../include/header.jsp"></jsp:include>
	<main>
		<h1>Mon profil</h1>
		
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
		
		<form action="${pageContext.request.contextPath}/modifier" method="post">
			<label for="pseudo">Pseudo :	</label>
			<input type="text" id="pseudo" name="pseudo" required value= "${utilisateur.getPseudo()}">
			
			<label for="nom">Nom : </label>
			<input type="text" id="nom" name="nom" required value="${utilisateur.getNom()}">
			
			<label for="prenom">Prénom : </label>
			<input type="text" id="prenom" name="prenom" required value="${utilisateur.getPrenom()}">
			
			<label for="email">Email : </label>
			<input type="text" id="email" name="email" required value="${utilisateur.getEmail()}">
			
			<label for="telephone">Téléphone : </label>
			<input type="text" id="telephone" name="telephone" value="${utilisateur.getTelephone()}">
			
			<label for="rue">Rue : </label>
			<input type="text" id="rue" name="rue" required value="${utilisateur.getAdresse().getRue()}">
			
			<label for="codePostal">Code postal : </label>
			<input type="text" id="codePostal" name="codePostal" required value="${utilisateur.getAdresse().getCodePostal()}">
			
			<label for="ville">Ville : </label>
			<input type="text" id="ville" name="ville" required value="${utilisateur.getAdresse().getVille()}">
			
			<label for="motDePasseAncien">Mot de passe actuel : </label>
			<input type="password" id="motDePasseAncien" name="motDePasseAncien" required>
			
			<div id="break"></div>
			
			<label for="motDePasseNew">Nouveau mot de passe : </label>
			<input type="password" id="motDePasseNew" name="motDePasseNew">
			
			<label for="confirmationMotDePasse">Confirmation : </label>
			<input type="password" id="confirmationMotDePasse" name="confirmationMotDePasse" >
			
			<p>Crédit : ${utilisateur.getCredit()}</p>
			<input class="btn" id="btn-enregistrer" type="submit" value="Enregistrer">
			<input class="btn" id="btn-supprimer" type="submit" name="supprimermoncompte" value="Supprimer mon compte">
			<a class="btn" id="btn-retour" href="${pageContext.request.contextPath}/accueil">Retour</a>
		</form>
	</main>
	<jsp:include page="../include/footer.jsp"></jsp:include>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/responsiveNav.js"></script>
</body>
</html>