package spook.encapsulation.data;

import spook.staticNumber.Code;
import spook.staticNumber.PriorityCode;
import spook.util.CharacterSeparator;

public class Encapsulation {
	
	
	public String EncapsulationLogOn(String userName , String passwd , String profileName , String ip) {
		return PriorityCode.LogOn + userName + CharacterSeparator.Separator + passwd + CharacterSeparator.Separator + profileName + 
				CharacterSeparator.Separator + ip ;
	}
	
	public String EncapsulationLogIn(String userName , String passwd , String ip) {
		return PriorityCode.LogIn + userName + CharacterSeparator.Separator + passwd + CharacterSeparator.Separator + ip ;
	}
	
	public String EncapsulationSql(String senderUsername , String receiverUsername , String message , String ip , String commend , Code code) {
		if (code == Code.User)
			return PriorityCode.sqlCommend + commend + PriorityCode.UserSql + senderUsername + CharacterSeparator.Separator +
					receiverUsername + CharacterSeparator.Separator + message + CharacterSeparator.Separator + ip;
		else if (code == Code.Message)
			return PriorityCode.sqlCommend + commend + PriorityCode.MessageSql + senderUsername + CharacterSeparator.Separator +
					receiverUsername + CharacterSeparator.Separator + message + CharacterSeparator.Separator + ip;
		else
			return null;
	}

}
