import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class Dogs extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField name;
	private JTextField breed;
	private JTextField gender;
	private JTextField birthDate;
	private JTextField size;
	private Connection connection;
	private JTextField textField_5;
	private JTextField medicalId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dogs frame = new Dogs();
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
	public Dogs() {
		connection = MysqlConnection.databaseConnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 494);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Dogs");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 25));
		lblNewLabel.setBounds(238, 11, 115, 34);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(238, 99, 504, 233);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(255, 222, 173));
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setBackground(new Color(238, 232, 170));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainView mainview = new MainView();
				mainview.setVisible(true);
			}
		});
		btnNewButton.setBounds(653, 11, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(20, 77, 55, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblBreed = new JLabel("Breed");
		lblBreed.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBreed.setBounds(20, 102, 55, 14);
		contentPane.add(lblBreed);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGender.setBounds(20, 127, 55, 14);
		contentPane.add(lblGender);
		
		JLabel lblBirthDate = new JLabel("Birth date");
		lblBirthDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBirthDate.setBounds(20, 152, 67, 14);
		contentPane.add(lblBirthDate);
		
		JLabel lblSize = new JLabel("Size");
		lblSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSize.setBounds(20, 178, 55, 14);
		contentPane.add(lblSize);
		
		JLabel lblMedicalid = new JLabel("Medical_id");
		lblMedicalid.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMedicalid.setBounds(20, 211, 67, 14);
		contentPane.add(lblMedicalid);
		
		medicalId = new JTextField();
		medicalId.setColumns(10);
		medicalId.setBounds(92, 208, 107, 20);
		contentPane.add(medicalId);
		
		name = new JTextField();
		name.setBounds(92, 74, 107, 20);
		contentPane.add(name);
		name.setColumns(10);
		
		breed = new JTextField();
		breed.setColumns(10);
		breed.setBounds(92, 99, 107, 20);
		contentPane.add(breed);
		
		gender = new JTextField();
		gender.setColumns(10);
		gender.setBounds(92, 124, 107, 20);
		contentPane.add(gender);
		
		birthDate = new JTextField();
		birthDate.setColumns(10);
		birthDate.setBounds(92, 149, 107, 20);
		contentPane.add(birthDate);
		
		size = new JTextField();
		size.setColumns(10);
		size.setBounds(92, 177, 107, 20);
		contentPane.add(size);
		
		JButton btnNewButton_1 = new JButton("Add");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "INSERT INTO Dogs(gender,name, date_birth, breed, size, medical_id) VALUES (?,?,?,?,?,?)";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, gender.getText());
					statement.setString(2, name.getText());
					statement.setString(3, birthDate.getText());
					statement.setString(4, breed.getText());
					statement.setString(5, size.getText());
					statement.setString(6, medicalId.getText());
					statement.execute();
					
					statement.close();
					gender.setText("");
					name.setText("");
					birthDate.setText("");
					breed.setText("");
					size.setText("");
					medicalId.setText("");
	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBackground(new Color(238, 232, 170));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(52, 266, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(238, 232, 170));
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBounds(52, 300, 89, 23);
		contentPane.add(btnDelete);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update Dogs SET gender = '" + gender.getText() + "',name = '" + name.getText() + "', data_birth = '" + birthDate.getText() + "', breed = '" + breed.getText() + "', size = + 'size.getText() + ', medical_id = + 'medicalId.getText() +' ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					
					statement.close();
				
	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnEdit.setBackground(new Color(238, 232, 170));
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEdit.setBounds(52, 334, 89, 23);
		contentPane.add(btnEdit);
		
		JButton btnNewButton_2 = new JButton("Dogs arrival info");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_2.setBackground(new Color(238, 232, 170));
		btnNewButton_2.setBounds(391, 378, 130, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Load view");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "SELECT * FROM Dogs";
					PreparedStatement statement = connection.prepareStatement(query);
					ResultSet resultset = statement.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(resultset));
					
					statement.close();
					resultset.close();
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_3.setBackground(new Color(238, 232, 170));
		btnNewButton_3.setBounds(274, 378, 107, 23);
		contentPane.add(btnNewButton_3);
		

	}
}
