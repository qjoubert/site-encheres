package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurManager {
	
	private static UtilisateurManager instance;
	private UtilisateurDAO utilisateurDAO;
	
	private UtilisateurManager() {
		utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}
	
	public static UtilisateurManager getInstance() {
		if (instance == null) {
			instance = new UtilisateurManager();
		}
		return instance;
	}
	
	public Utilisateur seConnecter(String login, String mdp) throws BusinessException {
		Utilisateur utilisateur = null;
		BusinessException be = null;
		
		if (login.contains("@")) {
			utilisateur = utilisateurDAO.getUtilisateurByEmail(login);
		} else {
			utilisateur = utilisateurDAO.getUtilisateurByPseudo(login);
		}
		if (utilisateur == null || !utilisateur.getMotDePasse().equals(mdp)) {
			be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.LOGIN_MDP_KO);
			throw be;
		}
		return utilisateur;
	}
	
	public Utilisateur creerCompte(String pseudo, String nom, String prenom, String email, String telephone,
			String motDePasse, String confirmationMotDePasse, String rue, String codePostal, String ville, int credit, boolean administrateur) throws BusinessException {
		
		Adresse adresse = new Adresse(rue, codePostal, ville);
		Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, motDePasse, 0, false, adresse);
		validerUtilisateur(utilisateur, confirmationMotDePasse);
		utilisateur = utilisateurDAO.insertUtilisateur(utilisateur);
		return utilisateur;
	}
	
	public Utilisateur getUtilisateur(int noUtilisateur) {
		return utilisateurDAO.getUtilisateurByNo(noUtilisateur);
	}
	
	private void validerUtilisateur(Utilisateur utilisateur, String confirmationMotDePasse) throws BusinessException {
		BusinessException be = new BusinessException();
		if (emailDejaExistant(utilisateur.getEmail())) {
			be.ajouterErreur(CodesResultatBLL.EMAIL_DEJA_EXISTANT);
		}
		if (pseudoDejaExistant(utilisateur.getPseudo())) {
			be.ajouterErreur(CodesResultatBLL.PSEUDO_DEJA_EXISTANT);
		}
		if (!utilisateur.getMotDePasse().equals(confirmationMotDePasse)) {
			be.ajouterErreur(CodesResultatBLL.ERREUR_CONFIRMATION_MDP);
		}
		if (!utilisateur.getPseudo().matches("\\w+")) {
			be.ajouterErreur(CodesResultatBLL.PSEUDO_NON_VALIDE);
		}
		if (!utilisateur.getNom().matches("[A-Za-z]+")) {
			be.ajouterErreur(CodesResultatBLL.NOM_NON_VALIDE);
		}
		if (!utilisateur.getPrenom().matches("[A-Za-z]+")) {
			be.ajouterErreur(CodesResultatBLL.PRENOM_NON_VALIDE);
		}
		if (!utilisateur.getEmail().matches(".+@[\\w]+\\.[A-Za-z]+")) {
			be.ajouterErreur(CodesResultatBLL.EMAIL_NON_VALIDE);
		}
		if (!utilisateur.getAdresse().getCodePostal().matches("\\d{5}")) {
			be.ajouterErreur(CodesResultatBLL.CP_NON_VALIDE);
		}
		if (be.hasErreurs()) {
			throw be;
		}
	}
	
	private boolean emailDejaExistant(String email) {
		return utilisateurDAO.getUtilisateurByEmail(email) != null;
	}

	private boolean pseudoDejaExistant(String pseudo) {
		return utilisateurDAO.getUtilisateurByPseudo(pseudo) != null;
	}
	
	public Utilisateur majProfil(Utilisateur user, String confirmationMotDePasse,
			Utilisateur utilisateurSession, String motDePasseNew) throws BusinessException{
		
		if(!motDePasseNew.isEmpty()) {
			user.setMotDePasse(motDePasseNew);
		}
		validerMajUtilisateur(user, confirmationMotDePasse,utilisateurSession,motDePasseNew);
		
		user = utilisateurDAO.modifierUtilisateur(user);
		return user;
	}

	private void validerMajUtilisateur(Utilisateur utilisateur, String confirmationMotDePasse,Utilisateur utilisateurSession, String motDePasseNew) throws BusinessException {
		BusinessException be = new BusinessException();
		
		if(!utilisateur.getMotDePasse().equals(utilisateurSession.getMotDePasse())&& motDePasseNew.isEmpty()) {
			be.ajouterErreur(CodesResultatBLL.ERREUR_MDP);
		}
		
		if(!utilisateur.getPseudo().equals(utilisateurSession.getPseudo())) {
			if(utilisateurDAO.getUtilisateurByPseudo(utilisateur.getPseudo())!=null){
				be.ajouterErreur(CodesResultatBLL.PSEUDO_DEJA_EXISTANT);
			}
		}
		if(!utilisateur.getEmail().equals(utilisateurSession.getEmail())) {
			if(utilisateurDAO.getUtilisateurByEmail(utilisateur.getEmail())!=null){
				be.ajouterErreur(CodesResultatBLL.EMAIL_DEJA_EXISTANT);
			}
		}
		if(!confirmationMotDePasse.isEmpty()&&!utilisateur.getMotDePasse().equals(confirmationMotDePasse)) {
			be.ajouterErreur(CodesResultatBLL.ERREUR_CONFIRMATION_MDP);
		}
		if (!utilisateur.getPseudo().matches("\\w+")) {
			be.ajouterErreur(CodesResultatBLL.PSEUDO_NON_VALIDE);
		}
		if (!utilisateur.getNom().matches("[A-Za-z]+")) {
			be.ajouterErreur(CodesResultatBLL.NOM_NON_VALIDE);
		}
		if (!utilisateur.getPrenom().matches("[A-Za-z]+")) {
			be.ajouterErreur(CodesResultatBLL.PRENOM_NON_VALIDE);
		}
		if (!utilisateur.getEmail().matches(".+@[\\w]+\\.[A-Za-z]+")) {
			be.ajouterErreur(CodesResultatBLL.EMAIL_NON_VALIDE);
		}
		if (!utilisateur.getAdresse().getCodePostal().matches("\\d{5}")) {
			be.ajouterErreur(CodesResultatBLL.CP_NON_VALIDE);
		}
		if (be.hasErreurs()) {
			throw be;
		}
	}
	
	public void supProfil(Utilisateur user, String confirmationMotDePasse, Utilisateur utilisateurSession,
			String motDePasseNew) throws BusinessException {

		validerMajUtilisateur(user, confirmationMotDePasse, utilisateurSession, motDePasseNew);
		utilisateurDAO.supprimerUtilisateur(user);
	}
}
