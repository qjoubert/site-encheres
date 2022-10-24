package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.DAOFactory;

public class AdresseManager {
	
	private static AdresseManager instance;
	private AdresseDAO adresseDAO;
	
	private AdresseManager() {
		adresseDAO = DAOFactory.getAdresseDAO();
	}
	
	public static AdresseManager getInstance() {
		if (instance == null) {
			instance = new AdresseManager();
		}
		return instance;
	}
	
	public Adresse creerAdresse(Adresse adresse) throws BusinessException {
		validerAdresse(adresse);
		return adresseDAO.insert(adresse);
	}
	
	private void validerAdresse(Adresse adresse) throws BusinessException {
		BusinessException be = new BusinessException();
		if (!adresse.getRue().matches("[\\w+\\s*]+")) {
			be.ajouterErreur(CodesResultatBLL.RUE_NON_VALIDE);
		}
		if (!adresse.getCodePostal().matches("\\d{5}")) {
			be.ajouterErreur(CodesResultatBLL.CP_NON_VALIDE);
		}
		if (!adresse.getVille().matches("[[A-Za-z-]+\\s*]+")) {
			be.ajouterErreur(CodesResultatBLL.VILLE_NON_VALIDE);
		}
		if (be.hasErreurs()) {
			throw be;
		}
	}
}
