package fr.eni.encheres.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletModifierProfil
 */
@WebServlet("/modifier")
public class ServletModifierProfil extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur utilisateurSession = (Utilisateur) request.getSession().getAttribute("utilisateur");
		int noUtilisateur= utilisateurSession.getNoUtilisateur();
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		String motDePasse =request.getParameter("motDePasseAncien");
		String motDePasseNew= request.getParameter("motDePasseNew");
		String confirmationMotDePasse = request.getParameter("confirmationMotDePasse");
		int credit = utilisateurSession.getCredit();
		
		Adresse adresse = new Adresse(utilisateurSession.getNoUtilisateur(), rue, codePostal, ville);
		Utilisateur user = new Utilisateur(noUtilisateur, pseudo, nom, prenom, email, telephone, motDePasse, credit, false, adresse);
		
		try {
			if (request.getParameter("supprimermoncompte") != null) {
				
				 UtilisateurManager.getInstance().supProfil(user, confirmationMotDePasse, utilisateurSession,
							motDePasseNew);
						request.getSession().setAttribute("utilisateur", null);
						response.sendRedirect("accueil");
						
					}else {
					user = UtilisateurManager.getInstance().majProfil(user, confirmationMotDePasse, utilisateurSession,
							motDePasseNew);
					request.getSession().setAttribute("utilisateur", user);
					response.sendRedirect("accueil");
					}
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		}
	}
}
