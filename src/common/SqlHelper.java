//beanfarmer
package common;

import java.sql.*;

public class SqlHelper {

private Connection c;
private Statement stmt;

	public SqlHelper(){
		c = null;
		stmt = null;
	}

	public void connectDb(String name){
		c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Created database successfully");
	  }
	
	public void createTable(String nameDb, String tableName, String values){
		c = null;
		stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+nameDb+".db");
			stmt = c.createStatement();
			
			String sql = "CREATE TABLE "+ tableName.toUpperCase() + " " + values + ");";
			
			//String warn = stmt.getWarnings().getMessage();
			
			//System.out.println(warn);
			
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println(tableName + " added");
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			//System.exit(0);
		}
	}
	
	public void insert(String nameDb, String tableName, String values){
	    c = null;
	    stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+nameDb+".db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      
	      String sql = "INSERT INTO " + tableName.toUpperCase() + " VALUES("+
	                   values + ");"; 
	      stmt.executeUpdate(sql);
	      
	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Records created successfully");
	  }
	
	
	public ResultSet select(String nameDb, String tableName, String selection){
		 	c = null;
		 	stmt = null;
		 	ResultSet temp = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:"+nameDb+".db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      temp = stmt.executeQuery( "SELECT " + selection + " FROM " + tableName + ";" );
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Select completed successfully");
			return temp;
	}
	
	//-- update and delete todo 
	//-- damn git
	
}


