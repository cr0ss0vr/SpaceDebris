package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import common.SqlHelper;

@SuppressWarnings("serial")
public class ServerMain extends ServerCore{
	
	
	int i = 0;
	
	private static SqlHelper sqlhelper = new SqlHelper();
	
	public static void main(String[] args){
		sqlhelper.connectDb("inputLog");
		new ServerMain().run();
		sqlhelper.createTable("inputLog", "server", "(ID INT PRIMARY KEY	NOT NULL,+"+"HISTORY	TEXT	NOT NULL"+")");
		sqlhelper.insert("inputLog", "server", "1, 'hello world!'");
		ResultSet res = sqlhelper.select("inputLog","server", "*");
		
		try {
			while(res.next()){
				String result = res.getString("HISTORY");
				System.out.println(result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void MoreCalls() {
		
//		for (;i < 25; i++) {
//			if(i>=1){
//				taOut.append("\n" + "Test " + (i + 1));
//			}else{
//				taOut.append("Test " + (i + 1));
//			}
//			vert.setValue(vert.getMaximum());
//			taOut.setCaretPosition(taOut.getDocument().getLength());
//		}
	}
}