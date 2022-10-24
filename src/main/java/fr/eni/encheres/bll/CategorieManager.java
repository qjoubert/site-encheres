package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;

public class CategorieManager {
	
	private static CategorieManager instance;
	private CategorieDAO categorieDAO;
	
	private CategorieManager() {
		categorieDAO = DAOFactory.getCategorieDAO();
	}
	
	public static CategorieManager getInstance() {
		if (instance == null) {
			instance = new CategorieManager();
		}
		return instance;
	}
	
	public Categorie getCategorie(int id) {
		return categorieDAO.getById(id);
	}
	
	public List<Categorie> selectAll() {
		return categorieDAO.selectAll();
	}
}
