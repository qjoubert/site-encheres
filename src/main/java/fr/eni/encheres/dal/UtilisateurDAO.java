package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	
	Utilisateur getUtilisateurByEmail(String email);
	
	Utilisateur getUtilisateurByNo(int noUtilisateur);
	
	Utilisateur getUtilisateurByPseudo(String pseudo);
	
	Utilisateur insertUtilisateur(Utilisateur utilisateur);
	
	Utilisateur modifierUtilisateur(Utilisateur user);
	
	void supprimerUtilisateur(Utilisateur utilisateur);
}
