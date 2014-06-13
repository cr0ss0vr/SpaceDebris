package server;

import java.awt.event.KeyEvent;
import java.io.File;
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
	
	public void getDbConnection(){

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

	
	public void getAllHistory() throws SQLException{ // can now be accessed from an Arraylist!
		prevInput = new ArrayList<String>();
		prevInput = sqlhelper.select("server/Server", "INPUTLOG", "*");
	} // to reference data call prevInput.get(int index)
	
	public void MoreCalls() {

	}

	@Override
	public void controls() { // needs to be connected to the ui somehow
		// TODO Auto-generated method stub
		
		if(kbd.getKeyState(KeyEvent.VK_UP)){
			taIn.setText("up");
		}
		else if(kbd.getKeyState(KeyEvent.VK_DOWN)){
			taIn.setText("down");
		}
	}
}