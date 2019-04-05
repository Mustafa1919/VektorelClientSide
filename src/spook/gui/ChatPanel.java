package spook.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import spook.encapsulation.data.Encapsulation;
import spook.security.PasswdHashKod;
import spook.server.connection.ServerConnection;
import spook.staticNumber.Code;
import spook.staticNumber.PriorityCode;
import spook.util.CharacterSeparator;
import spook.util.WhoAmI;

public class ChatPanel extends JPanel {
	private static ChatPanel chatPanel = null;
	private ServerConnection connection;
	private PasswdHashKod hashKod;
	private Encapsulation encapsulation;

	private JTable table;

	private JButton btnSend;
	private JButton btnBack;
	private JEditorPane textArea;
	private JTextPane textPane;
	private JScrollPane panelMessage;

	private JScrollPane scrol;
	private JScrollPane chatScroll;
	private Map<String, ChatHistory> history;
	private String whichUser;

	private String[][] listFriend;
	private Thread thread;

	private ChatPanel() {
		listFriend = new String[WhoAmI.friendList.length-1][1];
		listFriendMethod();
		encapsulation = new Encapsulation();
		hashKod = new PasswdHashKod();
		this.history = new HashMap<String, ChatHistory>();
		listeningServer();
		thread.start();
		initialize();
	}

	private void initialize() {
		setLayout(null);
		setBounds(0, 0, 480, 600);

		textArea = new JEditorPane();
		textArea.setBounds(210, 420, 260, 70);

		btnSend = new JButton("Gönder");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// btnSend_Action_Performed(arg0 , WhoAmI.profileName);
				btnSend_Action_Performed(arg0, whichUser);
			}
		});
		btnSend.setBounds(365, 498, 105, 31);
		add(btnSend);

		String[] baslik = { "Arkadas Listesi" };

		table = new JTable(listFriend, baslik) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selected = table.getSelectedRow();
				whichUser = table.getModel().getValueAt(selected, 0).toString();
				textPane.setText(getHistory(whichUser).getHistory());
			}
		});
		table.setBounds(0, 0, 200, 490);
		scrol = new JScrollPane(table);
		scrol.setBounds(0, 0, 200, 490);
		add(scrol);

		scrol.setViewportView(table);

		chatScroll = new JScrollPane();
		chatScroll.setBounds(210, 420, 260, 70);
		add(chatScroll);

		chatScroll.setViewportView(textArea);

		textPane = new JTextPane();

		panelMessage = new JScrollPane();
		panelMessage.setBounds(210, 10, 260, 400);
		add(panelMessage);

		panelMessage.setViewportView(textPane);
		textPane.setEditable(false);

		btnBack = new JButton("Geri");
		btnBack.setBounds(10, 502, 105, 31);
		add(btnBack);

	}

	protected void addUserMap(String userName, ChatHistory chatHistory) {
		history.put(userName, chatHistory);
	}

	protected boolean ifThere(String userName) {
		Set<String> keySet = history.keySet();
		for (String s : keySet) {
			if (s.equals(userName))
				return true;
		}
		return false;
	}

	protected ChatHistory getHistory(String userName) {
		if (!ifThere(userName))
			addUserMap(userName, new ChatHistory());
		return history.get(userName);
	}

	protected void btnSend_Action_Performed(ActionEvent arg0, String userName) {
		String message = textArea.getText();
		if (!message.isEmpty()) {
			textArea.setText("");
			textPane.setText(getHistory(whichUser).addHistory(userName + " : " + message));
			try {
				connection = ServerConnection.getServerConnection();
				connection.setSenderData(encapsulation.EncapsulationSql(WhoAmI.userName, whichUser, message,
						InetAddress.getLocalHost().getHostAddress(), PriorityCode.Save, Code.Message));
				connection.run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	protected void listeningServer() {
		 thread = new Thread(new Runnable() {
			ServerConnection connection;
			DataInputStream inputStream;
			String data;

			@Override
			public void run() {
				try {
					connection = ServerConnection.getServerConnection();
					inputStream = connection.getInputStream();
					while (true) {
						data = inputStream.readUTF();
						System.out.println(data);
						String[] inMessage = data.split(CharacterSeparator.Separator);
						getHistory(inMessage[0]).addHistory(inMessage[0] + ":" + inMessage[1]);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
//		thread.run();
	}

	public static ChatPanel getChatPanel() {
		if(chatPanel == null)
			chatPanel = new ChatPanel();
		return chatPanel;
	}
	
	protected void listFriendMethod() {
		int temp = 0;
		for(int i=0; i<WhoAmI.friendList.length; i++) {
			if(!WhoAmI.friendList[i].equals(WhoAmI.userName)) {
				listFriend[temp][0] = WhoAmI.friendList[i];
				temp++;
			}
		}
	}
}
