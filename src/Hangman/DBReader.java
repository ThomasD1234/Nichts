package Hangman;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBReader {

	private static Connection con;
	private static boolean hasData = false;
	
	public static Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			if(con == null){
				con = DriverManager.getConnection("jdbc:sqlite:SQLITETest1.db");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con; 
	}
	
	DBReader() {
		try {
			initialise();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void initialise() throws SQLException {
		if( !hasData ) {
			hasData = true;
			
			Statement state = getConnection().createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='words'"); //sqlite_master => Mastertable mit Daten über alle anderen Tabellen in der DB
			if ( !res.next() ) {
				System.out.println("Building the Words table with prepopulated values.");
				
		// Erzeuge Tabele words
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE words(id integer NOT NULL,"
						+ "word varchar(60)," + "umschreibung varchar(120),"
						+ "kategorie varchar(60)," + "primary key(id));" );
		
		// Erzeuge Tabelle kategorien
				Statement state3 = con.createStatement();
				state3.execute("CREATE TABLE kategorien(id integer," + "kategorie varchar(60)," + "primary key(id));");
				
		// Erzeuge Tabelle status
				Statement state4 = con.createStatement();
				state4.execute("CREATE TABLE status(name varchar(60)," + "wert varchar(60)," + "primary key(name));" );
				
		// Erzeuge Tabelle spieler
				Statement state5 = con.createStatement();
				state5.execute("CREATE TABLE spieler(id integer," + "spielerName varchar(60)," + "highscore integer," + "primary key(id));");
				
		// Befülle Tabelle words
				PreparedStatement prep = con.prepareStatement("INSERT INTO words values(?,?,?,?);");
				prep.setString(2, "Ente");
				prep.setString(3, "Bewohner von Stadteichen");
				prep.setString(4, "Tier");
				prep.execute();
				
				PreparedStatement prep2 = con.prepareStatement("INSERT INTO words values(?,?,?,?);");
				prep2.setString(2, "Esel");
				prep2.setString(3, "7353");
				prep2.setString(4, "Tier");
				prep2.execute();
				
		// Befülle Tabelle kategorien
				PreparedStatement prep3 = con.prepareStatement("INSERT INTO kategorien values(?,?);");
				prep3.setString(2, "Tier");
				prep3.execute();
								
		// Befülle Tabelle status
				PreparedStatement prep4 = con.prepareStatement("INSERT INTO status values(?,?);");
				prep4.setString(1, "Fehlversuche");
				prep4.setString(2, "8");
				prep4.execute();
				
				PreparedStatement prep5 = con.prepareStatement("INSERT INTO status values(?,?);");
				prep5.setString(1, "Letztes Wort");
				prep5.setString(2, "");
				prep5.execute();
				
				PreparedStatement prep6 = con.prepareStatement("INSERT INTO status values(?,?);");
				prep6.setString(1, "Letzte Eingaben");
				prep6.setString(2, "");
				prep6.execute();
				
				PreparedStatement prep7 = con.prepareStatement("INSERT INTO status values(?,?);");
				prep7.setString(1, "Letzte Kategorien");
				prep7.setString(2, "0");
				prep7.execute();
				
		// Befülle Tabelle spieler
				PreparedStatement prep8 = con.prepareStatement("INSERT INTO spieler values(?,?,?);");
				prep8.setString(2, "Franz");
				prep8.setString(3, "12000");
				prep8.execute();
			}	
		}
	}
	
	public String getRandomWord(int[] kategorieIDs) throws IllegalArgumentException, SQLException {
		Statement state = getConnection().createStatement();
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
	
	// con Objekt nur mit getConnection aufrufen
	
	