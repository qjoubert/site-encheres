package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.AdresseManager;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletVente
 */
@WebServlet("/vente")
public class ServletVente extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Categorie> categories = CategorieManager.getInstance().selectAll();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/WEB-INF/jsp/vente.jsp").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
		
		String nomArticle = request.getParameter("nomArticle").trim();
		String description = request.getParameter("description").trim();
		int noCategorie = Integer.parseInt(request.getParameter("noCategorie"));
		int prixInitial = request.getParameter("prixInitial") == null ? 0 : Integer.parseInt(request.getParameter("prixInitial").trim());
		LocalDate dateDebutEncheres = LocalDate.parse(request.getParameter("dateDebutEncheres"), dtf);
		LocalDate dateFinEncheres = LocalDate.parse(request.getParameter("dateFinEncheres"), dtf);
		
		String rue = request.getParameter("rue").trim();
		String codePostal = request.getParameter("codePostal").trim();
		String ville = request.getParameter("ville").trim();
		Adresse adresse = new Adresse(utilisateur.getAdresse().getNoAdresse(), utilisateur.getNoUtilisateur(), rue, codePostal, ville);
		Adresse adresseRetrait = null;
		try {
			if (!adresse.equals(utilisateur.getAdresse())) {
				System.out.println("adresse retrait != adresse utilisateur");
				adresse.setNoUtilisateur(null);
				adresseRetrait = AdresseManager.getInstance().creerAdresse(adresse);
			}
			
			Categorie categorie = CategorieManager.getInstance().getCategorie(noCategorie);
			Article article = new Article(
					adresseRetrait, nomArticle, description, dateDebutEncheres, 
					dateFinEncheres, prixInitial, null, utilisateur, categorie);
			ArticleManager.getInstance().creerArticle(article);
			response.sendRedirect("accueil");
		} catch (BusinessException be) {
			request.setAttribute("listeCodesErreur", be.getListeCodesErreur());
			List<Categorie> categories = CategorieManager.getInstance().selectAll();
			request.setAttribute("categories", categories);
			request.getRequestDispatcher("/WEB-INF/jsp/vente.jsp").forward(request, response);;
		}
		
	}

}
