package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticleDAO {
	
	List<Article> getAchats(int noUtilisateur);

	Article getArticleById(int id);

	List<Article> getArticlesFiltres(Integer noUtilisateur, String recherche, Integer noCategorie, 
			String transaction, String[] filtresAchats, String[] filtresVentes);
	
	List<Article> getVentes(int noUtilisateur);

	void insertArticle(Article article);
	
	List<Article> selectAll();

}
