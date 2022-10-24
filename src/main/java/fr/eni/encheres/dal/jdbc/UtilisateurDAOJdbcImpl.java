package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
	
	private final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur) VALUES (?,?,?,?,?,?,?,?);";
	private final String SELECT_BY_EMAIL = "SELECT u.no_utilisateur, no_adresse, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS u JOIN ADRESSES a ON u.no_utilisateur = a.no_utilisateur WHERE email = ?;";
	private final String SELECT_BY_NO = "SELECT u.no_utilisateur, no_adresse, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS u JOIN ADRESSES a ON u.no_utilisateur = a.no_utilisateur WHERE u.no_utilisateur = ?;";
	private final String SELECT_BY_PSEUDO = "SELECT u.no_utilisateur, no_adresse, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS u JOIN ADRESSES a ON u.no_utilisateur = a.no_utilisateur WHERE pseudo = ?;";
	private final String UPDATE_UTILISATEUR ="UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, mot_de_passe=? WHERE no_utilisateur =?;";
	private final String UPDATE_ADRESSE ="UPDATE ADRESSES SET rue=?,code_postal=?,ville=? WHERE no_utilisateur=?;";
	private final String DELETE_ADRESSES = "DELETE FROM ADRESSES WHERE no_utilisateur=?;";
	private final String DELETE_UTILISATEURS = "DELETE FROM UTILISATEURS WHERE no_utilisateur=?;";
	
	@Override
	public Utilisateur getUtilisateurByEmail(String email) {
		return getUtilisateurByLogin(email, SELECT_BY_EMAIL);
	}

	private Utilisateur getUtilisateurByLogin(String login, String requete) {
		Utilisateur utilisateur = null;
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(requete)) {
			pstmt.setString(1, login);
			try (ResultSet rs = pstmt.executeQuery()) {				
				if (rs.next()) {
					Adresse adresse = new Adresse(
							rs.getInt("no_adresse"),
							rs.getInt("no_utilisateur"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville")
					);
					utilisateur = new Utilisateur(
							rs.getInt("no_utilisateur"),
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("mot_de_passe"),
							rs.getInt("credit"),
							rs.getBoolean("administrateur"),
							adresse
					);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return utilisateur;
	}

	@Override
	public Utilisateur getUtilisateurByNo(int noUtilisateur) {
		Utilisateur utilisateur = null;
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO)) {
			pstmt.setInt(1, noUtilisateur);
			try (ResultSet rs = pstmt.executeQuery()) {				
				if (rs.next()) {
					Adresse adresse = new Adresse(
							rs.getInt("no_adresse"),
							rs.getInt("no_utilisateur"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville")
					);
					utilisateur = new Utilisateur(
							rs.getInt("no_utilisateur"),
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("mot_de_passe"),
							rs.getInt("credit"),
							rs.getBoolean("administrateur"),
							adresse
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return utilisateur;
	}

	@Override
	public Utilisateur getUtilisateurByPseudo(String pseudo) {
		return getUtilisateurByLogin(pseudo, SELECT_BY_PSEUDO);
	}

	@Override
	public Utilisateur insertUtilisateur(Utilisateur utilisateur) {
		AdresseDAO adresseDAO = DAOFactory.getAdresseDAO();
		int noUtilisateur = 0;
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getMotDePasse());
			pstmt.setInt(7, utilisateur.getCredit());
			pstmt.setBoolean(8, utilisateur.isAdministrateur());
			pstmt.execute();
			try (ResultSet keys = pstmt.getGeneratedKeys()) {		
				if (keys.next()) {
					noUtilisateur = keys.getInt(1);
					utilisateur.setNoUtilisateur(noUtilisateur);
					utilisateur.getAdresse().setNoUtilisateur(noUtilisateur);
					adresseDAO.insert(utilisateur.getAdresse());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return utilisateur;
	}
	
	@Override
	public Utilisateur modifierUtilisateur (Utilisateur utilisateur) {
		  try(	Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_UTILISATEUR)){
			  pstmt.setString(1, utilisateur.getPseudo());
				pstmt.setString(2, utilisateur.getNom());
				pstmt.setString(3, utilisateur.getPrenom());
				pstmt.setString(4, utilisateur.getEmail());
				pstmt.setString(5, utilisateur.getTelephone());
				pstmt.setString(6, utilisateur.getMotDePasse());
			    pstmt.setInt(7,utilisateur.getNoUtilisateur());
			    pstmt.executeUpdate();
			    
			    try(PreparedStatement pstmt2 = cnx.prepareStatement(UPDATE_ADRESSE)) {
			    	pstmt2.setString(1, utilisateur.getAdresse().getRue());
					pstmt2.setString(2, utilisateur.getAdresse().getCodePostal());
					pstmt2.setString(3, utilisateur.getAdresse().getVille());
					pstmt2.setInt(4, utilisateur.getAdresse().getNoUtilisateur());
					pstmt2.executeUpdate();		    
			    }
			  
		  } catch (SQLException e) {
			e.printStackTrace();
		}
		return utilisateur;
	}
	
	@Override
	public  void supprimerUtilisateur (Utilisateur utilisateur) {
		try(	Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(DELETE_ADRESSES)){
			pstmt.setInt(1, utilisateur.getAdresse().getNoUtilisateur());
			pstmt.executeUpdate();
				try(PreparedStatement pstmt3 = cnx.prepareStatement(DELETE_UTILISATEURS)){
					pstmt3.setInt(1, utilisateur.getNoUtilisateur());
					pstmt3.executeUpdate();
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
