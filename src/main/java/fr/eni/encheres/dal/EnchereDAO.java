package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Enchere;

public interface EnchereDAO{
	
	void delete(int id);

	void insert(Enchere e);
	
	List<Enchere> selectAll();

	List<Enchere> selectAvecFiltres(String filtres);

	Enchere selectById(int id);
	
	void update(Enchere e);
}
