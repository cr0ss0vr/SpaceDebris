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
		if(!enabled){
			if(stp.toLowerCase().startsWith("tenable")){
				enabled = true;
				return "Commands Enabled.";
			}else{
				return "Commands Disabled.";
			}
		}else{
			switch(stp.toLowerCase()){
				default: 
					return "No such command.";
				case "help":
					return "Command list:\n"
							+ "tEnable - Toggles command recognition.";
				case "tenable":
					enabled=false;
					return "Commands Disabled.";
			}
//			if(stp.toLowerCase().startsWith("help")){
//				return "There is no help. This is the only command.";
//			}
		}
	}
}
