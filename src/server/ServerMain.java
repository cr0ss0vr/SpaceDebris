package server;

import java.util.logging.Logger;

import common.SqlHelper;

@SuppressWarnings("serial")
public class ServerMain extends ServerCore{
	
	
	int i = 0;
	
	private static SqlHelper sqlhelper = new SqlHelper();
	
	public static void main(String[] args){
		sqlhelper.connectDb("test");
		new ServerMain().run();
		sqlhelper.createTable("test", "server", "(ID INT PRIMARY KEY	NOT NULL,+"+"history	TEXT	NOT NULL"+")");
		
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