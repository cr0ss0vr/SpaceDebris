package server;

import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.SqlHelper;

@SuppressWarnings("serial")
public class ServerMain extends ServerCore{
	private static SqlHelper sqlhelper = new SqlHelper();
	
	public static void main(String[] args){
		new ServerMain().run();		
	}

	public void dbConnect(){
		sqlhelper.connectDb("server/Server");		
	}
	
	public void getHistory(){

		File f = new File("server/Server.db");
		if(!f.exists()){
			sqlhelper.createTable("server/Server", "INPUTLOG", "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," + 
							  	  " HISTORY           TEXT    NOT NULL");
		}
		try {
			getAllHistory();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
		}
	}

	
	public ArrayList<String> getAllHistory() throws SQLException{ // can now be accessed from an Arraylist!
		ResultSet res = sqlhelper.select("server/Server", "INPUTLOG", "*");
		ArrayList<String> hist = new ArrayList<String>();	
		while(res.next()){
			print(res.getNString(currentID));
			hist.add(res.getNString(currentID));
		}
		return hist; //superuseful now!
	}

	public void MoreCalls() {

	}

	public void controls() {
		int i =0;
		if(kbd.getKeyState(KeyEvent.VK_UP)){
			taIn.setText((String) prevInput.get(i));
		}else if(kbd.getKeyState(KeyEvent.VK_DOWN)){
			
		}
		
	}
}