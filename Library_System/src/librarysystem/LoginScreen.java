package librarysystem;

import business.LoginException;
import business.SystemController;
import librarysystem.ruleSet.RuleException;
import librarysystem.ruleSet.RuleSet;
import librarysystem.ruleSet.RuleSetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//this is the first view that executes when the program runs
//{101=[101:xyz, LIBRARIAN], 102=[102:abc, ADMIN], 103=[103:111, BOTH]}
public class LoginScreen extends JFrame implements LibWindow {

	public static final LoginScreen INSTANCE = new LoginScreen();
	private boolean isInitialized = false;

	@Override
	public void init() {

		// this.setVisible(true);
		this.setBounds(100, 100, 827, 581);
		createMyGUI();
		add(mainPanel);
	}

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	// declaring variables
	JPanel jPBody2;
	JPanel mainPanel;
	JPanel jPButtonPanel;

	private JLabel jLTitle;
	JLabel jLUserName;
	JTextField jTFUserName;
	private JLabel jLPassword;
	JPasswordField jTFPassword;

	JButton jBLogin;

	private LoginScreen() {
		UIController.INSTANCE.loginScreen = this;
	}

	public JTextField getjTFUserName() {
		return jTFUserName;
	}

	public JPasswordField getjTFPassword() {
		return jTFPassword;
	}

	private void createMyGUI() {

		// creating new object of respective swing classes
		mainPanel = new JPanel();
		jLTitle = new JLabel();
		jPBody2 = new JPanel();
		jTFUserName = new JTextField();
		jTFPassword = new JPasswordField();
		jLUserName = new JLabel();
		jLPassword = new JLabel();
		jBLogin = new JButton();

		// this is the main panel
		mainPanel.setBackground(new java.awt.Color(211, 211, 211));
		mainPanel.setToolTipText("");
		mainPanel.setLayout(null);

		// this is the title
		jLTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		jLTitle.setText("Library System");
		mainPanel.add(jLTitle);
		jLTitle.setBounds(182, 20, 198, 40);

		jPBody2.setLayout(null);
		jPBody2.setBounds(197, 70, 442, 294);

		// assigning values to respective objects
		jPBody2.add(jTFUserName);
		jTFUserName.setBounds(61, 104, 133, 33);

		jPBody2.add(jTFPassword);
		jTFPassword.setBounds(241, 104, 133, 33);

		// this is for username
		jLUserName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
		jLUserName.setText("Username");
		jPBody2.add(jLUserName);
		jLUserName.setBounds(61, 66, 108, 28);

		// this is for password
		jLPassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
		jLPassword.setText("Password");
		jPBody2.add(jLPassword);
		jLPassword.setBounds(241, 66, 108, 28);

		// login button

		jBLogin.setText("Login");
		jPBody2.add(jBLogin);
		jBLogin.setBounds(167, 169, 92, 28);
		addLoginButtonListener(jBLogin);
		mainPanel.add(jPBody2);
		jPBody2.setBounds(197, 70, 442, 294);
		getContentPane().add(mainPanel);
		this.setResizable(false);

	}

	private void addLoginButtonListener(JButton button) {
		button.addActionListener(evt -> {
			SystemController sys = new SystemController();
			try {

				RuleSet loginRules = RuleSetFactory.getRuleSet(LoginScreen.this);
				loginRules.applyRules(LoginScreen.this);

				String username = jTFUserName.getText();
				char[] pass = jTFPassword.getPassword();
				String password = new String(pass);

				// Call controller
				sys.login(username, password);

			} catch (LoginException | RuleException ex) {
				new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
			}
		});
	}

}
