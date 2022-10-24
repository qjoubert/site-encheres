package fr.eni.encheres.bo;

import java.sql.Timestamp;

public class Enchere {
	
	private int noAcheteur;
	private int noArticle;
	private Timestamp dateEnchere;
	private int montantEnchere;
	
	public Enchere(int noAcheteur, int noArticle, Timestamp dateEnchere, int montantEnchere) {
		this.noAcheteur = noAcheteur;
		this.noArticle = noArticle;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}

	public int getNoAcheteur() {
		return noAcheteur;
	}

	public void setNoAcheteur(int noAcheteur) {
		this.noAcheteur = noAcheteur;
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public Timestamp getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Timestamp dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	
}
