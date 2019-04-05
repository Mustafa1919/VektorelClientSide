package spook.gui;

public class ChatHistory {
	private String chatHistory ; 
	
	public ChatHistory() {
		this.chatHistory = "";
	}
	
	public String addHistory(String message) {
		return chatHistory += (message +"\n");
	}
	
	public String getHistory() {
		return chatHistory;
	}

}
