package spook.controller;

import spook.util.CharacterSeparator;

public class LoginController {
	
	public boolean checkIp(String ip) {
		if(ip.split(".").length == 4) {
			return true;
		}
		return false;
	}
	
	public boolean checkUserName(String userName) {
		if(userName.contains(CharacterSeparator.Separator))
			return false;
		else if(userName.length() <= 3 || userName.length() >= 15)
			return false;
		return true;
	}
	
	public boolean checkPasswd(String passwd) {
		if(passwd.contains(CharacterSeparator.Separator))
			return false;
		else if(passwd.length() <= 4 || passwd.length() >= 15)
			return false;
		return true;
	}

}
