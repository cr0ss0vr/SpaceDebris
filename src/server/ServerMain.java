package server;

@SuppressWarnings("serial")
public class ServerMain extends ServerCore{
	
	int i = 0;
	
	public static void main(String[] args){
		new ServerMain().run();
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