package fr.eni.encheres.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;

public class EnchereManager {
	
	private EnchereDAO enchereDAO;
	
	public EnchereManager() {
		enchereDAO = DAOFactory.getEnchereDAO();
	}
	
	public List<Enchere> selectAll() {
		return enchereDAO.selectAll();
	}
	
	public List<Enchere> selectAvecFiltres(String filtres) {
		List<Enchere> encheres = new ArrayList<>();
		
		return encheres;
	}
}
