package server;

@SuppressWarnings("serial")
public class ServerMain extends ServerCore{
	
	int i = 0;
	
	public static void main(String[] args){
		new ServerMain().run();
	}

	@Override
	public void MoreCalls() {
		for (;i < 25; i++) {
			taOut.setCaretPosition(taOut.getDocument().getLength());
			taOut.append("Test " + (i + 1) + "\n");
			vert.setValue(vert.getMaximum());
		}
	}
}