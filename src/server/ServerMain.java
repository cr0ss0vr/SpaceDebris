package server;

import java.io.File;
import java.sql.SQLException;

import common.SqlHelper;

@SuppressWarnings("serial")
public class ServerMain extends ServerCore{
	
	
	int i = 0;
	boolean isFirstRun = true;
	
	private static SqlHelper sqlhelper = new SqlHelper();
	
	public static void main(String[] args){
		new ServerMain().run();		
	}

	public void dbConnect(){
		sqlhelper.connectDb("Server");		
	}
	
	public void getHistory(){

		File f = new File("Server.db");
		if(!f.exists()){
			sqlhelper.createTable("Server", "INPUTLOG", "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," + 
							  	  " HISTORY           TEXT    NOT NULL");
			isFirstRun = false;
		}
		
		try {
			getAllHistory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void MoreCalls() {
		
		/*
		}*/
	}
}