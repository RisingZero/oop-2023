package social;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serial;
import javax.swing.*;


public class SocialGui extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private boolean logged = false;
	private String code;
	private String user;
	private Social social;

	// The following components are declared public
	// in order to allow testing the user interface
	
	/**
	 * The code of the person to log in
	 */
	public JTextField id ;
	
	/**
	 * The button to perform login
	 */
	public JButton login ;
	
	/**
	 * The label that shall contain the info
	 * of the logged in person 
	 */
	public JLabel name ;
	
	/**
	 * The list of friends of the person
	 * that is logged in
	 */
	public JList<String> friends ;
	

	public SocialGui(Social m){
		
		social = m;
		
		setLayout(new BorderLayout());
		
		JPanel pane = new JPanel();
		pane.setLayout(new FlowLayout());
		
		id = new JTextField(20);
		login = new JButton("Login");
		id.addActionListener((ActionEvent e) -> {
			String s = e.getActionCommand();
			
			if (s.equals("submit")) {
				onLoginClick(id.getText());
			}
		});
		login.addActionListener((ActionEvent e) -> {
			String s = e.getActionCommand();
			
			if (s.equals("click")) {
				onLoginClick(id.getText());
			}
		});
		

		pane.add(id);
		pane.add(login);
		
		this.add(pane, BorderLayout.NORTH);
		this.setSize(1080,720);
	}
	
	public void onLoginClick(String code) {
		try {
			user = social.getPerson(code);
			this.code = code;
			logged = true;
			return;
		} catch (NoSuchCodeException e) {
			JFrame alert = new JFrame("alert");
			alert.add(new JLabel("ERROR: no user with this code"));
			alert.setSize(400,300);
			alert.setVisible(true);
		}
		
	}


}
