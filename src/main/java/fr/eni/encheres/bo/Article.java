package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Article {
	
	private Integer noArticle;
	private Adresse adresseRetrait;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private Integer prixInitial;
	private Integer prixVente;
	private Utilisateur vendeur;
	private Categorie categorie;
	
	public Article(Integer noArticle, Adresse adresseRetrait, String nomArticle, String description,
			LocalDate dateDebutEncheres, LocalDate dateFinEncheres, Integer prixInitial, Integer prixVente,
			Utilisateur vendeur, Categorie categorie) {
		this.noArticle = noArticle;
		this.adresseRetrait = adresseRetrait;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.vendeur = vendeur;
		this.categorie = categorie;
	}
	
	public Article(Adresse adresseRetrait, String nomArticle, String description,
			LocalDate dateDebutEncheres, LocalDate dateFinEncheres, Integer prixInitial, Integer prixVente,
			Utilisateur vendeur, Categorie categorie) {
		this(null, adresseRetrait, nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, vendeur, categorie);
	}
	
	public boolean estModifiable() {
		return dateDebutEncheres.compareTo(LocalDate.now()) > 0;
	}
	
	public String getFormattedDateDebutEncheres() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return dateDebutEncheres.format(dtf);
	}
	
	public String getFormattedDateFinEncheres() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return dateFinEncheres.format(dtf);
	}

	public Integer getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(Integer noArticle) {
		this.noArticle = noArticle;
	}

	public Adresse getAdresseRetrait() {
		return adresseRetrait;
	}

	public void setAdresseRetrait(Adresse adresseRetrait) {
		this.adresseRetrait = adresseRetrait;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public Integer getPrixInitial() {
		return prixInitial;
	}

	public void setPrixInitial(Integer prixInitial) {
		this.prixInitial = prixInitial;
	}

	public Integer getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(Integer prixVente) {
		this.prixVente = prixVente;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
}
