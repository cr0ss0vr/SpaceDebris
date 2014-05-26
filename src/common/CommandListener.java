package common;

import server.ServerCore;

public class CommandListener {
	boolean enabled = true;
	
	public CommandListener(){
		
		enabled = true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String handleCommand(String stp) {
		if(!enabled){
			if(stp.toLowerCase().startsWith("tenable")){
				enabled = true;
				return "Commands Enabled.";
			}else{
				return "Commands Disabled.";
			}
		}else{
			//cannot be a switch case, it wont recognise .startsWith, which means commands couldnt take arguments.
			if(stp.toLowerCase().startsWith("help") || stp.toLowerCase().startsWith("?")){
					return "Command list:\n"
							+ "tEnable - Toggles command recognition."
							+ "say - Says something from the server.";
			}else if(stp.toLowerCase().startsWith("say ")){
				String s2 = stp.substring(stp.indexOf(" ")).trim();
				return "Server: "+ s2;
			}else if(stp.toLowerCase().startsWith("tenable")){
				enabled=false;
				return "Commands Disabled.";
			}else if(stp.toLowerCase().startsWith("quit") || stp.toLowerCase().startsWith("exit")){
				setServerState("quitting");
				return "Quitting!";
			}else if(stp.toLowerCase().startsWith("clear") || stp.toLowerCase().startsWith("cls")){
				return "Screen Cleared.";
			}
			else{
				return "No such command.";
			}
		}
	}
}
