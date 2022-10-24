package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class ArticleDAOJdbcImpl implements ArticleDAO {
	
	private final String INSERT_ARTICLE = "INSERT INTO ARTICLES (no_adresse, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_vendeur, no_categorie) VALUES (?,?,?,?,?,?,?,?);";
	private final String SELECT_ACHATS = "SELECT no_article, no_adresse, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_vendeur, no_categorie FROM ARTICLES WHERE no_vendeur <> ?;";
	private final String SELECT_ALL = "SELECT no_article, no_adresse, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_vendeur, no_categorie FROM ARTICLES;";
	private final String SELECT_BY_ID = "SELECT no_article, no_adresse, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_vendeur, no_categorie FROM ARTICLES WHERE no_article = ?;";
	private final String SELECT_FILTRES = "SELECT no_article, no_adresse, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_vendeur, no_categorie FROM ARTICLES WHERE ";
	private final String SELECT_VENTES = "SELECT no_article, no_adresse, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_vendeur, no_categorie FROM ARTICLES WHERE no_vendeur = ?;";
	
	private Article buildArticle(ResultSet rs) {
		AdresseDAO adresseDAO = DAOFactory.getAdresseDAO();
		CategorieDAO categorieDAO = DAOFactory.getCategorieDAO();
		UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();
		Article article = null;
		Adresse adresse = null;
		try {
			if (rs.getInt("no_adresse") != 0) {
				adresse = adresseDAO.getById(rs.getInt("no_adresse"));
			}
			article = new Article(
					rs.getInt("no_article"),
					adresse,
					rs.getString("nom_article"),
					rs.getString("description"),
					rs.getDate("date_debut_encheres").toLocalDate(),
					rs.getDate("date_fin_encheres").toLocalDate(),
					rs.getInt("prix_initial"),
					rs.getInt("prix_vente"),
					utilisateurDAO.getUtilisateurByNo(rs.getInt("no_vendeur")),
					categorieDAO.getById(rs.getInt("no_categorie"))
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return article;
	}

	@Override
	public List<Article> getAchats(int noUtilisateur) {
		return getByTransaction(noUtilisateur, SELECT_ACHATS);
	}

	@Override
	public Article getArticleById(int id) {
		Article article = null;
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					article = buildArticle(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return article;
	}

	@Override
	public List<Article> getArticlesFiltres(Integer noUtilisateur, String recherche, Integer noCategorie, 
			String transaction, String[] filtresAchats, String[] filtresVentes) {
		List<Article> articles = new ArrayList<>();
		List<String> paramsTypes = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		String rechercheParam = "";
		String noCategorieParam = "";
		String transactionParam = "";
		String achatsParam = "";
		String ventesParam = "";
		
		if (recherche != null && !recherche.isEmpty()) {
			rechercheParam = "nom_article LIKE CONCAT('%',?,'%')";
			paramsTypes.add("string");
			params.add(recherche);
		}
		if (noCategorie != null) {
			if (!rechercheParam.isEmpty()) {
				noCategorieParam += " AND ";
			}
			noCategorieParam += "no_categorie = ?";
			paramsTypes.add("int");
			params.add(noCategorie);
		}
		if (transaction != null) {
			if (!rechercheParam.isEmpty() || !noCategorieParam.isEmpty()) {
				transactionParam += " AND ";
			}
			if (transaction.equals("achat")) {
				transactionParam += "no_vendeur <> ?";
				paramsTypes.add("int");
				params.add(noUtilisateur);
				if (filtresAchats != null) {
					for (String filtre : filtresAchats) {
						if (filtre.equals("ouvertes")) {
							achatsParam += " AND date_debut_encheres <= ? AND date_fin_encheres >= ?";
							paramsTypes.add("date");
							paramsTypes.add("date");
							params.add(LocalDate.now());
							params.add(LocalDate.now());
						}
					}
				}
			}
			else if (transaction.equals("vente")) {
				transactionParam += "no_vendeur = ?";
				paramsTypes.add("int");
				params.add(noUtilisateur);
				if (filtresVentes != null) {
					for (String filtre : filtresVentes) {
						if (filtre.equals("encours")) {
							ventesParam += " AND (date_debut_encheres <= ? AND date_fin_encheres >= ?";
							paramsTypes.add("date");
							paramsTypes.add("date");
							params.add(LocalDate.now());
							params.add(LocalDate.now());
						}
						else if (filtre.equals("nondebutees")) {
							ventesParam += Arrays.toString(filtresVentes).contains("encours") ? " OR " : " AND (";
							ventesParam += "date_debut_encheres > ?";
							paramsTypes.add("date");
							params.add(LocalDate.now());
						}
						else if (filtre.equals("terminees")) {
							ventesParam += Arrays.toString(filtresVentes).contains("encours") ||
									Arrays.toString(filtresVentes).contains("nondebutees") ? " OR " : " AND (";
							ventesParam += "date_fin_encheres < ?";
							paramsTypes.add("date");
							params.add(LocalDate.now());
						}
					}
					ventesParam += ")";
				}
			}
		}
		String requete = String.format("%s%s%s%s%s%s;", SELECT_FILTRES, rechercheParam, noCategorieParam, 
				transactionParam, achatsParam, ventesParam);
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(requete)) {
			int indexParams = 0;
			int indexPstmt = 1;
			for (String type : paramsTypes) {
				if (type.equals("string")) {
					pstmt.setString(indexPstmt, (String) params.get(indexParams));
				} 
				else if (type.equals("int")) {
					pstmt.setInt(indexPstmt, (Integer) params.get(indexParams));
				}
				else if (type.equals("date")) {
					pstmt.setDate(indexPstmt, Date.valueOf((LocalDate) params.get(indexParams)));
				}
				indexParams++;
				indexPstmt++;
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					articles.add(buildArticle(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}
	
	private List<Article> getByTransaction(int noUtilisateur, String requete) {
		List<Article> articles = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(requete)) {
			pstmt.setInt(1, noUtilisateur);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					articles.add(buildArticle(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<Article> getVentes(int noUtilisateur) {
		return getByTransaction(noUtilisateur, SELECT_VENTES);
	}

	@Override
	public void insertArticle(Article article) {
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS)) {
			if (article.getAdresseRetrait() == null) {				
				pstmt.setNull(1, Types.INTEGER);
			} else {				
				pstmt.setInt(1, article.getAdresseRetrait().getNoAdresse());
			}
			pstmt.setString(2, article.getNomArticle());
			pstmt.setString(3, article.getDescription());
			pstmt.setDate(4, Date.valueOf(article.getDateDebutEncheres()));
			pstmt.setDate(5, Date.valueOf(article.getDateFinEncheres()));
			pstmt.setInt(6, article.getPrixInitial());
			pstmt.setInt(7, article.getVendeur().getNoUtilisateur());
			pstmt.setInt(8, article.getCategorie().getNoCategorie());
			pstmt.execute();
			try (ResultSet keys = pstmt.getGeneratedKeys()) {
				if (keys.next()) {
					article.setNoArticle(keys.getInt(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Article> selectAll() {
		List<Article> articles = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection();
				Statement stmt = cnx.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(SELECT_ALL)) {
				while (rs.next()) {
					articles.add(buildArticle(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}
}
