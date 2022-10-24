package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adresse;

public interface AdresseDAO {

	Adresse getById(int id);
	
	Adresse insert(Adresse adresse);
}
