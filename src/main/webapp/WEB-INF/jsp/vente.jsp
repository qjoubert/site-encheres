<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Nouvelle vente</title>
</head>
<body>
	<header>
		<h1>ENI-Enchères</h1>
		<h2>Nouvelle vente</h2>
	</header>
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
		<form action="${pageContext.request.contextPath}/vente" method="post">
			<label>
				Article : 
				<input type="text" name="nomArticle" required>
			</label>
			<label>
				Description : 
				<textarea name="description" rows="5" cols="20" required></textarea>
			</label>
			<label>
				Catégorie 
				<select name="noCategorie">
					<c:forEach var="categorie" items="${categories}">
						<option value="${categorie.getNoCategorie()}">${categorie.getLibelle()}</option>
					</c:forEach>
				</select>
			</label>
			<label>
				Mise à prix : 
				<input type="number" name="prixInitial">
			</label>
			<label>
				Début de l'enchère
				<input type="date" name="dateDebutEncheres" required>
			</label>
			<label>
				Fin de l'enchère
				<input type="date" name="dateFinEncheres" required>
			</label>
			<fieldset>
				<legend>Retrait</legend>
				<label>
					Rue : 
					<input type="text" name="rue" value="${utilisateur.getAdresse().getRue()}">
				</label>
				<label>
					Code Postal : 
					<input type="text" name="codePostal" value="${utilisateur.getAdresse().getCodePostal()}">
				</label>
				<label>
					Ville : 
					<input type="text" name="ville" value="${utilisateur.getAdresse().getVille()}">
				</label>
			</fieldset>
			<input type="submit" value="Enregistrer">
			<a href="${pageContext.request.contextPath}/accueil">Annuler</a>
		</form>
	</main>
</body>
</html>