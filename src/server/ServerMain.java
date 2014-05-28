package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import common.SqlHelper;

@SuppressWarnings("serial")
public class ServerMain extends ServerCore{
	
	
	int i = 0;
	boolean isFirstRun = true;
	
	private static SqlHelper sqlhelper = new SqlHelper();
	
	public static void main(String[] args){
		sqlhelper.connectDb("inputLog");
		new ServerMain().run();		
	}

	@Override
	public void MoreCalls() {
		if(isFirstRun){
			sqlhelper.createTable("inputLog", "server", "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," + 
							  	  " HISTORY           TEXT    NOT NULL");
			isFirstRun = false;
		}
		/*
		}*/
	}
}