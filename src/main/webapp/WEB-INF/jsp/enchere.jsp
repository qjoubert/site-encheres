<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
	<meta charset="UTF-8">
	<title>Enchère</title>
</head>
<body>
	<header>
		<h1>ENI-Enchères</h1>
	</header>
	
	<main>
		<h2>Détail vente</h2>
		<c:if test="${!empty article}">
			<h3>${article.getNomArticle()}</h3>
			<p>Description : ${article.getDescription()}</p>
			<p>Catégorie : ${article.getCategorie().getLibelle()}</p>
			<p>Meilleure offre : *** A implémenter après la création d'enchères ***</p>
			<p>Mise à prix : ${article.getPrixInitial()} points</p>
			<p>Début de l'enchère : ${article.getFormattedDateDebutEncheres()}</p>
			<p>Fin de l'enchère : ${article.getFormattedDateFinEncheres()}</p>
			<p>
				Retrait : 
				<c:choose>
					<c:when test="${!empty article.getAdresseRetrait()}">
						${article.getAdresseRetrait().formatted()}
					</c:when>
					<c:otherwise>
						${article.getVendeur().getAdresse().formatted()}
					</c:otherwise>
				</c:choose>
			</p>
			<p>Vendeur : ${article.getVendeur().getPseudo()}</p>
			<c:choose>
				<c:when test="${utilisateur.getNoUtilisateur() == article.getVendeur().getNoUtilisateur()}">
					<c:if test="${article.estModifiable()}">
						<a href="${pageContext.request.contextPath}/modifierVente?id=${article.getNoArticle()}">
							Modifier la vente
						</a>
					</c:if>
				</c:when>
				<c:otherwise>
					<form action="${pageContext.request.contextPath}/enchere" method="post">
						<label>
							Ma proposition :
							<input type="number" name="enchere">
						</label>
						<input type="submit" value="Enchérir">
					</form>
				</c:otherwise>
			</c:choose>
		</c:if>
	</main>
</body>
</html>