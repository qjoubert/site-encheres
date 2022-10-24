package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.EnchereDAO;

public class EnchereDAOJdbcImpl implements EnchereDAO {
	
	private final String SELECT_ALL = "SELECT * FROM ENCHERES";

	@Override
	public void delete(int id) {
		
	}

	@Override
	public void insert(Enchere e) {
		
	}

	@Override
	public List<Enchere> selectAll() {
		List<Enchere> encheres = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection();
				Statement stmt = cnx.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(SELECT_ALL)) {				
				while(rs.next()) {
					encheres.add(new Enchere(
							rs.getInt("no_utilisateur"),
							rs.getInt("no_article"),
							rs.getTimestamp("date_enchere"),
							rs.getInt("montant_enchere")
							));
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return encheres;
	}

	@Override
	public List<Enchere> selectAvecFiltres(String filtres) {
		return null;
	}

	@Override
	public Enchere selectById(int id) {
		return null;
	}

	@Override
	public void update(Enchere e) {
		
	}
}
