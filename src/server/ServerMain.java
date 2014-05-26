package server;

import server.ServerCore;
import common.CommandListener;

@SuppressWarnings("serial")
public class ServerMain extends ServerCore{
	
	int i = 0;
	
	public static void main(String[] args){
		new ServerMain().run();
	}

	public void MoreCalls() {

	}

	@Override
	public void taOutUpdate(String stp){
		//new command listener implementation
		taOut.append(stp+"\n");
		taOut.append(cmdList.handleCommand(stp)+"\n");// handleCommand returns command's string
		taOut.setCaretPosition(taOut.getDocument().getLength());
		taIn.setText("");
	}
}