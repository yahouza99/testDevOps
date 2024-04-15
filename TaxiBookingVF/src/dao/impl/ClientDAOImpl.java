package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import dao.ClientDAO;
import dao.factory.Factory;
import models.Client;

public class ClientDAOImpl implements ClientDAO{
	
	static Connection con = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

	@Override
	public Boolean add(Client client) throws SQLException {
		Boolean isAdded=false;
		String query = "INSERT INTO client (nom, prenom, dateNais, adresse, login, motPasse) VALUES (?,?,?,?,?,?)";
		con =Factory.dbConnect();
        ps = con.prepareStatement(query);
        ps.setString(1, client.getNom());
        ps.setString(2, client.getPrenom());
        ps.setDate(3, java.sql.Date.valueOf(client.getDateNais()));
        ps.setString(4, client.getAdresse());
        ps.setString(5, client.getLogin());
        ps.setString(6, client.getMotPasse());
        if(ps.executeUpdate()>0) {
        	isAdded=true;}
        con.close();
        return isAdded;
	}

	@Override
	public Boolean update(Client client) throws SQLException {
		String query = "UPDATE client SET nom = ?, prenom=?, dateNais=?,adresse=?,login=?,motPasse=? WHERE taxi_id=?";
		con =Factory.dbConnect();
        ps = con.prepareStatement(query);
        ps.setString(1, client.getNom());
        ps.setString(2, client.getPrenom());
        ps.setDate(3, java.sql.Date.valueOf(client.getDateNais()));
        ps.setString(4, client.getAdresse());
        ps.setString(5, client.getLogin());
        ps.setString(6, client.getMotPasse());
        ps.setLong(1, client.getClient_id());
        con.close();
        return  ps.executeUpdate()>0;
	}

	@Override
	public Boolean deleteByID(Long client_id) throws SQLException {
		String query = "DELETE FROM client taxi WHERE taxi_id=?";
		con =Factory.dbConnect();
        ps = con.prepareStatement(query);
        ps.setLong(1, client_id);
        con.close();
        return  ps.executeUpdate()>0;
	}

	@Override
	public Client getOneById(Long client_id) throws SQLException {
		String query="SELECT * FROM client WHERE client_id=? LIMIT 1";
		con=Factory.dbConnect();
		ps=con.prepareStatement(query);
		ps.setLong(1, client_id);
		rs=ps.executeQuery();
		if(rs.next()) {
			Client client = new Client();
			client.setClient_id(rs.getLong("client_id"));
			client.setAdresse(rs.getString("adresse"));
			client.setDateNais(rs.getDate("dateNais").toLocalDate());
			client.setLogin(rs.getString("login"));
			client.setMotPasse(rs.getString("motPasse"));
			client.setNom(rs.getString("nom"));
			client.setPrenom(rs.getString("prenom"));
			con.close();
			return client;
		}
		
		return null;
	}

	@Override
	public Set<Client> getAll() throws SQLException {
		Set<Client> clients = new HashSet<Client>();
		String query="SELECT * FROM client";
		con=Factory.dbConnect();
		ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		while(rs.next()) {
			Client client = new Client();
			client.setClient_id(rs.getLong("client_id"));
			client.setAdresse(rs.getString("adresse"));
			client.setDateNais(rs.getDate("dateNais").toLocalDate());
			client.setLogin(rs.getString("login"));
			client.setMotPasse(rs.getString("motPasse"));
			client.setNom(rs.getString("nom"));
			client.setPrenom(rs.getString("prenom"));
			clients.add(client);
		}
		con.close();
		return clients;
	}

	@Override
	public Client  authentifier(String login, String motdepasse) throws SQLException {
		String query="select * from client WHERE UPPER(login)=UPPER('"+login+"') and motPasse='"+motdepasse+"' LIMIT 1";
		con=Factory.dbConnect();
		Statement stmt =con.createStatement();
		rs= stmt.executeQuery(query);
		if(rs.next()) {
			Client client = new Client();
			client.setClient_id(rs.getLong("client_id"));
			client.setAdresse(rs.getString("adresse"));
			client.setDateNais(rs.getDate("dateNais").toLocalDate());
			client.setLogin(rs.getString("login"));
			client.setMotPasse(rs.getString("motPasse"));
			client.setNom(rs.getString("nom"));
			client.setPrenom(rs.getString("prenom"));
			con.close();
			return client;
		}
		
		return null;
	}

}
