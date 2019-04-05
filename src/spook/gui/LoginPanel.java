package spook.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import spook.controller.LoginController;
import spook.encapsulation.data.Encapsulation;
import spook.security.PasswdHashKod;
import spook.server.connection.OutputServer;
import spook.server.connection.ServerConnection;
import spook.staticNumber.PriorityCode;
import spook.util.CharacterSeparator;
import spook.util.SocketInfo;
import spook.util.WhoAmI;

public class LoginPanel extends JPanel {

	private JTextField txtUserName;
	private JPasswordField txtPasswd;
	private JTextField txtServerIp;

	private JLabel lblNewLabel;
	private JLabel lblifre;
	private JLabel lblServerIp;

	private JButton btnLogin;
	private JButton btnCancel;
	
	private LoginController loginController;
	private ServerConnection serverConnection;
	private Encapsulation encapsulation;
	private PasswdHashKod hashKod;
	private MainFrame mainFrame;

	public LoginPanel() {
		loginController = new LoginController();
		encapsulation = new Encapsulation();
		hashKod = new PasswdHashKod();
		try {
			serverConnection = ServerConnection.getServerConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	private void initialize() {
		setLayout(null);
		setBounds(0, 0, 480, 540);

		lblNewLabel = new JLabel("Kullanıcı Adı : ");
		lblNewLabel.setBounds(78, 134, 89, 14);
		add(lblNewLabel);

		txtUserName = new JTextField();
		txtUserName.setBounds(203, 131, 154, 20);
		add(txtUserName);
		txtUserName.setColumns(10);

		lblifre = new JLabel("Şifre : ");
		lblifre.setBounds(78, 188, 89, 14);
		add(lblifre);

		txtPasswd = new JPasswordField();
		txtPasswd.setColumns(10);
		txtPasswd.setBounds(203, 185, 154, 20);
		add(txtPasswd);

		btnLogin = new JButton("Giriş");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnLogin_ActionPerformed(arg0);
			}
		});

		btnLogin.setBounds(88, 358, 89, 23);
		add(btnLogin);

		btnCancel = new JButton("İptal");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				serverConnection.setSenderData(PriorityCode.LogOut);
				serverConnection.run();
				System.exit(0);
			}
		});
		btnCancel.setBounds(237, 358, 89, 23);
		add(btnCancel);

		lblServerIp = new JLabel("Server Ip : ");
		lblServerIp.setBounds(78, 238, 89, 14);
		add(lblServerIp);

		txtServerIp = new JTextField();
		txtServerIp.setColumns(10);
		txtServerIp.setBounds(203, 235, 154, 20);
		add(txtServerIp);
	}

	protected void btnLogin_ActionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(loginController.checkIp(txtServerIp.getText()) && loginController.checkUserName(txtUserName.getText()) && 
				loginController.checkPasswd(txtPasswd.getText())) {
			SocketInfo.serverIp = txtServerIp.getText();
			try {
				serverConnection.setSenderData(encapsulation.EncapsulationLogIn(txtUserName.getText(), hashKod.EncryptionPasswd(txtPasswd.getText()),
						InetAddress.getLocalHost().getHostAddress()));
				serverConnection.run();
				new OutputServer().run();
				System.out.println(serverConnection.getReceiverData());
				if(serverConnection.getReceiverData().substring(0, 3).equals(PriorityCode.ListReturn)) {
					WhoAmI.userName = txtUserName.getText();
					WhoAmI.friendList = serverConnection.getReceiverData().substring(3).split(CharacterSeparator.Separator);
					//profil name de gir
					//sohbet ekranina gec
					getRootPane().removeAll();
//					removeAll();
//					mainFrame = new MainFrame();
//					mainFrame = mainFrame.getMainFrame();
//					mainFrame.getContentPane().removeAll();
					ChatPanel chatPanel = ChatPanel.getChatPanel();
//					mainFrame.getContentPane().add(chatPanel);
//					add(chatPanel);
					getRootPane().add(chatPanel);
					chatPanel.updateUI();
					chatPanel.setVisible(true);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(LoginPanel.this, "Bilgileri Kontrol Ederek Giriş Yapınız.");
			}
			
		}
		else {
			JOptionPane.showMessageDialog(LoginPanel.this, "Girilen Ip Adresi Hatalı");
		}
		
	}
}
