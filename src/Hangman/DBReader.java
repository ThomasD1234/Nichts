package Hangman;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBReader {

	private static DBReader dbReader;
	private Connection con;
	private static boolean hasData = false;
	
	public static DBReader getConnection() {
		if (dbReader == null) {
			dbReader = new DBReader();
		}
		return dbReader;
	}
	
	private DBReader() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:SQLITETest1.db");
			initialise();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void initialise() throws SQLException {
		if( !hasData ) {
			hasData = true;
			
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='words'"); //sqlite_master => Mastertable mit Daten über alle anderen Tabellen in der DB
			if ( !res.next() ) {
				System.out.println("Building the Words table with prepopulated values.");
				// need build the table
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE words(id integer,"
						+ "word varchar(60)," + "umschreibung varchar(120),"
						+ "primary key(id));");
			
				// inserting some sample data
				PreparedStatement prep = con.prepareStatement("INSERT INTO words values(?,?,?);");
				prep.setString(2, "Ente");
				prep.setString(3, "Bewohner von Stadteichen");
				prep.execute();
				
				PreparedStatement prep2 = con.prepareStatement("INSERT INTO words values(?,?,?);");
				prep2.setString(2, "Esel");
				prep2.setString(3, "7353");
				prep2.execute();
			}	
		}
	}
	
	public String getRandomWord(int[] kategorieIDs) throws IllegalArgumentException, SQLException {
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT word FROM words ORDER BY RANDOM() LIMIT 1"); //gezogene Query
		res.next();
		return res.getString("word");
	}
	
	
	// Kategorie hinzufügen
	public int addKategorie(String kategorieName) {
	//TODO: addKategorie Funktion Schreiben
		return 0;
	}
	
	public int addWort(String wort, int kategorieID) {
		return kategorieID;
		// TODO: addWort Funktion schreiben
	}
	
	
	public int addSpieler(String spielerName) {
		// TODO: addSpieler Funktion
		return 0;
	}
	
	public Game getStatus() {
		return null;
		// TODO: getStatus Funktion
	}
	
	public Game setStatus() {
		return null;
// TODO: setStatus Funktion
	}
	
	public Game loadGame() {
		return null;
		// TODO: Spiel Laden
	}
	
	public Game saveGame() {
		return null;
		// TODO: Spiel Speichern
	}
	
}
	
	
	
	