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
import spook.util.SocketInfo;
import spook.util.WhoAmI;

public class LogOnPanel extends JPanel{
	
	private JTextField txtUserName;
	private JPasswordField txtPasswd;
	private JTextField txtProfileName;

	private JLabel lblNewLabel;
	private JLabel lblifre;
	private JLabel lblProfileName;

	private JButton btnLogon;
	private JButton btnCancel;
	
	private LoginController loginController;
	private ServerConnection serverConnection;
	private Encapsulation encapsulation;
	private JTextField txtIp;
	private PasswdHashKod hashKod;
	
	public LogOnPanel() {
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

		btnLogon = new JButton("Giriş");
		btnLogon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnLogon_ActionPerformed(arg0);
			}
		});

		btnLogon.setBounds(88, 358, 89, 23);
		add(btnLogon);

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

		lblProfileName = new JLabel("Profil Name : ");
		lblProfileName.setBounds(78, 238, 89, 14);
		add(lblProfileName);

		txtProfileName = new JTextField();
		txtProfileName.setColumns(10);
		txtProfileName.setBounds(203, 235, 154, 20);
		add(txtProfileName);
		
		JLabel lblIp = new JLabel("Server Ip : ");
		lblIp.setBounds(78, 286, 89, 14);
		add(lblIp);
		
		txtIp = new JTextField();
		txtIp.setColumns(10);
		txtIp.setBounds(203, 283, 154, 20);
		add(txtIp);
		
	}

	protected void btnLogon_ActionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if( loginController.checkUserName(txtUserName.getText()) && loginController.checkPasswd(txtPasswd.getText())) {
			try {
				serverConnection.setSenderData(encapsulation.EncapsulationLogOn(txtUserName.getText(), hashKod.EncryptionPasswd(txtPasswd.getText()) ,
						txtProfileName.getText(), InetAddress.getLocalHost().getHostAddress()));
				serverConnection.run();
				new OutputServer().run();
				serverConnection.close();
				getRootPane().removeAll();
				MainFrame loginPanel = new MainFrame();
				getRootPane().add(loginPanel);
				loginPanel.setVisible(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(LogOnPanel.this, "Bilgileri Kontrol Ederek Giriş Yapınız.");
			}
			
		}
		else {
			JOptionPane.showMessageDialog(LogOnPanel.this, "Girilen Ip Adresi Hatalı");
		}
	}
}
