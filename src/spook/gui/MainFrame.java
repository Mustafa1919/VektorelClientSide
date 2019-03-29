package spook.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame{
	
	public MainFrame() {
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		setBounds(100, 100, 480	, 540);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton btnLogIn = new JButton("Giriş Yap");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getContentPane().removeAll();
				LoginPanel loginPanel = new LoginPanel();
				getContentPane().add(loginPanel);
				loginPanel.updateUI();
				loginPanel.setVisible(true);
			}
		});
		btnLogIn.setBounds(158, 109, 116, 32);
		getContentPane().add(btnLogIn);
		
		JButton btnLogOn = new JButton("Kayıt Ol");
		btnLogOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getContentPane().removeAll();
				LogOnPanel logOnPanel = new LogOnPanel();
				getContentPane().add(logOnPanel);
				logOnPanel.updateUI();
				logOnPanel.setVisible(true);
			}
		});
		btnLogOn.setBounds(158, 218, 116, 32);
		getContentPane().add(btnLogOn);
		
		
	}
}
