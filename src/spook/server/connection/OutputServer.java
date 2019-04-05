package spook.server.connection;

import java.io.DataInputStream;

public class OutputServer extends Thread {

	private ServerConnection connection;
	private DataInputStream inputStream;

	@Override
	public void run() {
		String input;
		try {
			connection = ServerConnection.getServerConnection();
			inputStream = connection.getInputStream();

			input = inputStream.readUTF();
			connection.setReceiverData(input);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
