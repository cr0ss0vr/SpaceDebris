package common;

import java.util.ArrayList;

public class CommandListener {
	boolean enabled = true;
	ArrayList<String> prevInput = new ArrayList<String>();
	
	public CommandListener(){
		
		enabled = true;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void getPrevInput(ArrayList<String> prevInputSource){
		this.prevInput = prevInputSource;
	}

	public String handleCommand(String stp) {
		
		if(stp.toLowerCase().startsWith("tenable")){
			if(enabled){
				enabled = false;
				return "Commands Disabled.";
			}else{
				enabled = true;
				return "Commands Enabled.";
			}
		}
		
		if(enabled){
			//cannot be a switch case, it wont recognise .startsWith, which means commands couldnt take arguments.
			if(stp.toLowerCase().startsWith("help") || stp.toLowerCase().startsWith("?")){
					return "  Command list:\n \n"
							+ "    tEnable - Toggles command recognition. \n"
							+ "    say - Says something from the server. \n"
							+ "    clear - clears the screen. \n"
							+ "	   history - gets the command history \n"
							+ "    exit - shuts down the server correctly. \n \n";
			}else if(stp.toLowerCase().startsWith("say ")){
				String s2 = stp.substring(stp.indexOf(" ")).trim();
				return "Server: "+ s2;
			}else if(stp.toLowerCase().startsWith("history")){
				StringBuilder SB = new StringBuilder();
				for(int i = 0; i < prevInput.size(); i++){
					SB.append(prevInput.get(i) + " \n");
				}
				String returnMe = SB.toString();
				System.out.println(returnMe + " history command!");
				return returnMe;
			}else{
				return "No such command.";
			}
		}else{
			return "Commands disabled.";
		}
	}
}
