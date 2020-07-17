package com.crud.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Statement;

public class DaoStuff implements StuffDAOInterface {
	private DaoStuff() {

	}

	public static class SingleHelper {
		private static final DaoStuff INSTANCE = new DaoStuff();
	}

	public static DaoStuff getInstance() {
		return SingleHelper.INSTANCE;
	}

	@Override
	public Optional<Stuff> find(String id) throws SQLException {
		String sql = "SELECET stuff_id, name, description, quantity, location FROM stuff WHERE stuff_id = ?";
		int id_stuff = 0, quantity = 0;
		String name = "";
		String description = "";
		String location = "";
		
		Connection conn = DataSourceFactory.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, id);
		ResultSet result = statement.executeQuery();
		if (result.next()) {
			id_stuff = result.getInt("stuff_id");
			name = result.getString("name");
			description = result.getString("description");
			quantity = result.getInt("quantity");
			location = result.getString("location");
		}

		return Optional.of(new Stuff(id_stuff, name, description, quantity, location));
	}

	@Override
	public List<Stuff> findAll() throws SQLException {
		List<Stuff> listStuff = new ArrayList<>();
		String sql = "SELECET stuff_id, name, description, quantity, location FROM stuff";
		Connection conn = DataSourceFactory.getConnection();
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);

		while (result.next()) {
			int id = result.getInt("stuff_id");
			String name = result.getString("name");
			String description = result.getString("description");
			int quantity = result.getInt("quantity");
			String location = result.getString("location");

			Stuff stuff = new Stuff(id, name, description, quantity, location);
			listStuff.add(stuff);
		}
		return listStuff;
	}

	@Override
	public boolean save(Stuff o) throws SQLException {
		String sql = "INSERT into stuff(name, description, quantity, location) VALUES (?,?,?,?)";
		boolean rowInserted = false;
		Connection conn = DataSourceFactory.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, o.getName());
		statement.setString(2, o.getDescription());
		statement.setInt(3, o.getQuantity());
		statement.setString(4, o.getLocation());
		rowInserted = statement.executeUpdate() > 0;
		return rowInserted;
	}

	@Override
	public boolean update(Stuff o) throws SQLException {
		String sql = "UPDATE stuff SET name = ?, description = ?, quantity = ?, location = ?";
		sql += "WHERE user_id = ?";
		boolean rowUpdated = false;
		Connection conn = DataSourceFactory.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, o.getName());
		statement.setString(2, o.getDescription());
		statement.setInt(3, o.getQuantity());
		statement.setString(4, o.getLocation());
		statement.setInt(5, o.getId());
		rowUpdated = statement.executeUpdate() > 0;
		return rowUpdated;
	}

	@Override
	public boolean delete(Stuff o) throws SQLException {
		String sql = "DELETE FROM stuff where user_id = ?";
		boolean rowDeleted = false;
		Connection conn = DataSourceFactory.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, o.getId());
		rowDeleted = statement.executeUpdate() > 0;
		return rowDeleted;
	}

}
