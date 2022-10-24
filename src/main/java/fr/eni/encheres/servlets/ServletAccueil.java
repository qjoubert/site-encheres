package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

@WebServlet("/accueil")
public class ServletAccueil extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String transaction = request.getParameter("tr");
		if (transaction == null) {
			request.setAttribute("articles", ArticleManager.getInstance().getArticles());
		}
		else if (transaction.equals("achat")) {
			Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
			int noUtilisateur = utilisateur.getNoUtilisateur();
			request.setAttribute("articles", ArticleManager.getInstance().getAchatsOuverts(noUtilisateur));
			request.setAttribute("filtresAchats", "ouvertes");
		}
		request.setAttribute("categories", CategorieManager.getInstance().selectAll());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
		Integer noUtilisateur = utilisateur == null ? null : utilisateur.getNoUtilisateur();
		String recherche = request.getParameter("recherche");
		String categorie = request.getParameter("categorie");
		String transaction = request.getParameter("transaction");
		String[] filtresAchats = request.getParameterValues("achats");
		String[] filtresVentes = request.getParameterValues("ventes");
		Integer noCategorie = categorie.isEmpty() ? null : Integer.parseInt(request.getParameter("categorie"));
		List<Article> articles = ArticleManager.getInstance().getArticlesFiltres(noUtilisateur, recherche, noCategorie, 
				transaction, filtresAchats, filtresVentes);
		request.setAttribute("transaction", transaction);
		request.setAttribute("filtresAchats", Arrays.toString(filtresAchats));
		request.setAttribute("filtresVentes", Arrays.toString(filtresVentes));
		request.setAttribute("articles", articles);
		request.setAttribute("categories", CategorieManager.getInstance().selectAll());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
	}

}
