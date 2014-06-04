package common;

public class CommandListener {
	boolean enabled = true;
	
	public CommandListener(){
		
		enabled = true;
	}

	public boolean isEnabled() {
		return enabled;
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
							+ "    exit - shuts down the server correctly. \n \n";
			}else if(stp.toLowerCase().startsWith("say ")){
				String s2 = stp.substring(stp.indexOf(" ")).trim();
				return "Server: "+ s2;
			}else{
				return "No such command.";
			}
		}else{
			return "Commands disabled.";
		}
	}
}
