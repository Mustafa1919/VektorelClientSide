package spook.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import spook.controller.LoginController;
import spook.server.connection.ServerConnection;
import spook.util.SocketInfo;
import spook.util.WhoAmI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {

	private JTextField txtUserName;
	private JTextField txtPasswd;
	private JTextField txtServerIp;

	private JLabel lblNewLabel;
	private JLabel lblifre;
	private JLabel lblServerIp;

	private JButton btnLogin;
	private JButton btnCancel;
	
	private LoginController loginController;
	private ServerConnection serverConnection;

	public LoginPanel() {
		loginController = new LoginController();
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

		txtPasswd = new JTextField();
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
				serverConnection = new ServerConnection();
				//veri transfer formatina cevir
				//sifre hashle
				//mesaj sifrele
				serverConnection.setSenderData(txtUserName.getText()+txtPasswd.getText());
				serverConnection.start();
				if(serverConnection.getReceiverData().equals("")) {
					WhoAmI.userName = txtUserName.getText();
					//profil name de gir
					//sohbet ekranina gec
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(LoginPanel.this, "Bilgileri Kontrol Ederek Giriş Yapınız.");
			}
			
		}
		else {
			JOptionPane.showMessageDialog(LoginPanel.this, "Girilen Ip Adresi Hatalı");
		}
		
	}
}
