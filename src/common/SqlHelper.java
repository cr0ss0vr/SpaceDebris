// SQLite helper class for java created by beanfarmer 
// this uses the sqlite-jdbc library 
// "SQLite JDBC, developed by Taro L. Saito, is a library for accessing and creating SQLite database files in Java."
// this helper class has been made for educational 
// feel free to use this code at your own risk and remember credit where credit is due. 
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
	    try{
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
	      c.close();
	    }catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Created database successfully");
	  }
	
	public void createTable(String nameDb, String tableName, String values){
		c = null;
		stmt = null;
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+nameDb+".db");
			stmt = c.createStatement();
			
			String sql = "CREATE TABLE "+ tableName.toUpperCase() + " " + values + ");";
			
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println(tableName + " added");
			c.close();
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			//System.exit(0);
		}
	}
	
	public void insert(String nameDb, String tableName, String values){
	    c = null;
	    stmt = null;
	    try{
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
	    }catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Records created successfully");
	  }
	
	
	public ResultSet select(String nameDb, String tableName, String selection){
		 	c = null;
		 	stmt = null;
		 	ResultSet temp = null;
		    try{
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:"+nameDb+".db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      temp = stmt.executeQuery( "SELECT " + selection + " FROM " + tableName + ";" );
		      stmt.close();
		      c.close();
		    }catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Select completed successfully");
			return temp;
	}
	
	public void update(String dbName, String tableName, String id, String colName, String value){
		c = null;
		stmt = null;
		try{
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      
		      String sql = "UPDATE " + tableName.toUpperCase() + " set "+
		                   colName + " = " + value + "where ID=" + id +";"; 
		      stmt.executeUpdate(sql);
		      
		      stmt.close();
		      c.commit();
		      c.close();
		    }catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("updated created successfully");
		
	}
	
	public void delete(String dbName, String tableName, String id){
		c = null;
		stmt = null;
		try{
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      
		      String sql = "DELETE from " + tableName.toUpperCase() +
		                   "where ID=" + id +";"; 
		      stmt.executeUpdate(sql);
		      
		      stmt.close();
		      c.commit();
		      c.close();
		    }catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("row deleted successfully");
	}
	 
	
}


