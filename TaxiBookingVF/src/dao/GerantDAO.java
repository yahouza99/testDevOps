package dao;

import java.sql.SQLException;

import models.Gerant;

public interface GerantDAO {
	
	Gerant authentifier(String login,String motdepasse) throws SQLException;
	
	Gerant resetPassword(String login) throws SQLException;
	

}
