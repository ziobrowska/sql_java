import java.sql.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;


public class LogView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogView frame = new LogView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogView() {
		connection = MysqlConnection.databaseConnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 628, 476);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(90,90,90));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("Zaloguj");
		btnNewButton.setBackground(new Color(255,255,255));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "SELECT * FROM Users WHERE username=? AND password=?";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, textField.getText());
					statement.setString(2, passwordField.getText());
					ResultSet resultset = statement.executeQuery();
					
				    if(resultset.next()){
				    	dispose();
				    	MainView mainview = new MainView();
				    	mainview.setVisible(true);	
				    }
				    else{
				    	JOptionPane.showMessageDialog(null, "Niepoprawna nazwa u¿ytkownika lub has³o. Spróbuj ponownie");
				    	textField.setText("");
				    	passwordField.setText("");
				    	
				    }
					statement.close();
					resultset.close();
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		});
		
		btnNewButton.setBounds(405, 309, 116, 37);
		contentPane.add(btnNewButton);

		
		textField = new JTextField();
		textField.setBounds(229, 192, 292, 37);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(229, 244, 292, 37);
		contentPane.add(passwordField);
		
		JLabel lblUsername = new JLabel("U¿ytkownik:");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setForeground(new Color(255,255,255));
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUsername.setBounds(90, 201, 116, 28);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Has³o:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(new Color(255,255,255));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setBounds(90, 243, 116, 34);
		contentPane.add(lblPassword);
		
		JLabel lblDogshelterManagement = new JLabel("System zarz\u0105dzania ZOO");
		lblDogshelterManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblDogshelterManagement.setForeground(new Color(255,255,255));
		lblDogshelterManagement.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDogshelterManagement.setBounds(105, 80, 399, 66);
		contentPane.add(lblDogshelterManagement);
	
		
	}
}
