package spook.server.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import spook.util.SocketInfo;

public class ServerConnection extends Thread {

	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Socket socket;
	private String senderData = null;
	private String receiverData = null;

	public ServerConnection() throws Exception {
		socket = new Socket(SocketInfo.serverIp, SocketInfo.serverSocket);
		inputStream = new DataInputStream(socket.getInputStream());
		outputStream = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		
		try {
			while(true) {
				outputStream.writeUTF(senderData);
				receiverData = inputStream.readUTF();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void close() throws Exception {
		//kapatmadan once servera kapatma mesaji at
		socket.close();
		inputStream.close();
		outputStream.close();
	}
	
	public void setSenderData(String data) {
		this.senderData = data;
	}
	public String getReceiverData() {
		return this.receiverData;
	}
}
