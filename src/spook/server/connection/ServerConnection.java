package spook.server.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import spook.staticNumber.PriorityCode;
import spook.util.SocketInfo;

public class ServerConnection extends Thread {

	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Socket socket;
	private String senderData = null;
	private String receiverData = null;
	private static ServerConnection connection = null;

	private ServerConnection() throws Exception {
		socket = new Socket(SocketInfo.serverIp, SocketInfo.serverSocket);
		inputStream = new DataInputStream(socket.getInputStream());
		outputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	public static ServerConnection getServerConnection() throws Exception{
		if(connection == null)
			connection = new ServerConnection();
		return connection;
	}
	
	@Override
	public void run() {

		try {
			outputStream.writeUTF(senderData);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void close() throws Exception {
		outputStream.writeUTF(PriorityCode.LogOut);
		socket.close();
		inputStream.close();
		outputStream.close();
		connection = null;
	}
	
	public DataInputStream getInputStream() {
		return inputStream;
	}

	public void setSenderData(String data) {
		this.senderData = data;
	}
	
	public void setReceiverData(String data) {
		this.receiverData = data;
	}
	
	public String getReceiverData() {
		return this.receiverData;
	}
}
