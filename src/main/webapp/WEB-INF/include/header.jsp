<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
	<a href="accueil"><img class="logo" src="img/logo.png" alt="logo"></a>
	<svg id="toggle" clip-rule="evenodd" fill="#8D52FF" fill-rule="evenodd" stroke-linejoin="round" stroke-miterlimit="2" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path d="m12.002 2.005c5.518 0 9.998 4.48 9.998 9.997 0 5.518-4.48 9.998-9.998 9.998-5.517 0-9.997-4.48-9.997-9.998 0-5.517 4.48-9.997 9.997-9.997zm4.998 13.245c0-.414-.336-.75-.75-.75h-8.5c-.414 0-.75.336-.75.75s.336.75.75.75h8.5c.414 0 .75-.336.75-.75zm0-3.248c0-.414-.336-.75-.75-.75h-8.5c-.414 0-.75.336-.75.75s.336.75.75.75h8.5c.414 0 .75-.336.75-.75zm0-3.252c0-.414-.336-.75-.75-.75h-8.5c-.414 0-.75.336-.75.75s.336.75.75.75h8.5c.414 0 .75-.336.75-.75z" fill-rule="nonzero"/></svg>
	<nav id="nav" class="toggle-off">
		<c:choose> 
			<c:when test="${empty utilisateur}">
				<a href="${pageContext.request.contextPath}/connexion">S'inscrire - Se connecter</a> 
			</c:when>
	 		<c:otherwise>
					<a href="${pageContext.request.contextPath}/enchere">Enchère</a> 
				<a href ="${pageContext.request.contextPath}/vente">Vendre un Article</a>
				<a href ="${pageContext.request.contextPath}/profil?id=${sessionScope.utilisateur.noUtilisateur}">Mon Profil</a>
				<a href ="${pageContext.request.contextPath}/deconnexion">Déconnexion</a>
	 		</c:otherwise>
		</c:choose>
	</nav>
</header>
		
		
		
		
		
		
		
		
		