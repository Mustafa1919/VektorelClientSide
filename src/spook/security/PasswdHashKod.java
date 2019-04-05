package spook.security;

public class PasswdHashKod {
	
	public String EncryptionPasswd(String passwd) {
		//şifreyi hashlama yap
		char[] arrayChar = passwd.toCharArray();
		long result = 1;
		for(int i=0; i<arrayChar.length; i++) {
			result *= ((long)arrayChar[i] / arrayChar.length);
		}
		return String.valueOf(result);
	}
	
	public String EncryptionMessage(String message) {
		//mesaji sifrele
		String senderMessage = "";
		String[] temp = message.split(" ");
		for(int i=0; i<temp.length; i++) {
			if(i == temp.length-1)
				senderMessage = senderMessage + EncryptionPasswd(temp[i]);
			else
				senderMessage = senderMessage + EncryptionPasswd(temp[i]) + " ";
		}
		return senderMessage;
	}
	
	public String DecryptionMessage(String message) {
		String decryptionMessage = "";
		String[] temp = message.split(" ");
		char[] tempArray ;
		long result;
 		for(int i=0; i<temp.length; i++) {
 			tempArray = temp[i].toCharArray();
 			result = Long.valueOf(temp[i]);
 			result *= tempArray.length;
 		}
		
		return decryptionMessage;
	}
	
}
