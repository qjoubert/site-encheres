package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;

public class ArticleManager {
	
	private static ArticleManager instance;
	private ArticleDAO articleDAO;
	
	private ArticleManager() {
		articleDAO = DAOFactory.getArticleDAO();
	}
	
	public static ArticleManager getInstance() {
		if (instance == null) {
			instance = new ArticleManager();
		}
		return instance;
	}
	
	public void creerArticle(Article article) throws BusinessException {
		validerArticle(article);
		articleDAO.insertArticle(article);
	}
	
	public List<Article> getAchats(int noUtilisateur) {
		return articleDAO.getAchats(noUtilisateur); 
	}
	
	public List<Article> getAchatsOuverts(int noUtilisateur) {
		return articleDAO.getArticlesFiltres(noUtilisateur, null, null, "achat", new String[]{"ouvertes"}, null);
	}

	public Article getArticleById(int id) {
		return articleDAO.getArticleById(id);
	}
	
	public List<Article> getArticles() {
		return articleDAO.selectAll();
	}
	
	public List<Article> getArticlesFiltres(Integer noUtilisateur, String recherche, Integer noCategorie, 
			String transaction, String[] filtresAchats, String[] filtresVentes) {
		if (recherche == null && noCategorie == null && transaction == null) {
			return getArticles();
		}
		return articleDAO.getArticlesFiltres(noUtilisateur, recherche.trim(), noCategorie, transaction, filtresAchats, filtresVentes);
	}
	
	public List<Article> getVentes(int noUtilisateur) {
		return articleDAO.getVentes(noUtilisateur); 
	}
	
	private void validerArticle(Article article) throws BusinessException {
		BusinessException be = new BusinessException();
		if (article.getNomArticle().length() > 30) {
			be.ajouterErreur(CodesResultatBLL.NOM_ARTICLE_TROP_LONG);
		}
		if (article.getDescription().length() > 300) {
			be.ajouterErreur(CodesResultatBLL.DESCRIPTION_ARTICLE_TROP_LONGUE);
		}
		if (article.getDateDebutEncheres().compareTo(LocalDate.now()) < 0) {
			be.ajouterErreur(CodesResultatBLL.ERREUR_DATE_DEBUT_ENCHERES);
		}
		if (article.getDateDebutEncheres().compareTo(article.getDateFinEncheres()) > 0) {
			be.ajouterErreur(CodesResultatBLL.ERREUR_DATE_FIN_ENCHERES);
		}
		if (article.getPrixInitial() < 0) {
			be.ajouterErreur(CodesResultatBLL.PRIX_INITIAL_NEGATIF);
		}
		if (be.hasErreurs()) {
			throw be;
		}
	}
}
