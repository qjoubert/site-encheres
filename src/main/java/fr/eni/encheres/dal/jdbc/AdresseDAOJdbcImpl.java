package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.ConnectionProvider;

public class AdresseDAOJdbcImpl implements AdresseDAO {
	
	private final String INSERT_ADRESSE = "INSERT INTO ADRESSES (no_utilisateur, rue, code_postal, ville) VALUES (?,?,?,?);";
	private final String SELECT_BY_ID = "SELECT no_adresse, no_utilisateur, rue, code_postal, ville FROM ADRESSES WHERE no_adresse = ?;";

	@Override
	public Adresse getById(int id) {
		Adresse adresse = null;
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					adresse = new Adresse(
							rs.getInt("no_adresse"),
							rs.getInt("no_utilisateur"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville")
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adresse;
	}

	@Override
	public Adresse insert(Adresse adresse) {
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(INSERT_ADRESSE, PreparedStatement.RETURN_GENERATED_KEYS)) {
			if (adresse.getNoUtilisateur() == null) {
				pstmt.setNull(1, Types.INTEGER);
			}
			else {
				pstmt.setInt(1, adresse.getNoUtilisateur());
			}
			pstmt.setString(2, adresse.getRue());
			pstmt.setString(3, adresse.getCodePostal());
			pstmt.setString(4, adresse.getVille());
			pstmt.execute();
			try (ResultSet keys = pstmt.getGeneratedKeys()) {
				if (keys.next()) {
					adresse.setNoAdresse(keys.getInt(1));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return adresse;
	}

}
