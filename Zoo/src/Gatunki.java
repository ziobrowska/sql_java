import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import java.sql.*;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gatunki extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection connection;
	private JTable table;
	private JTextField id_gatunku;
	private JTextField pelna_nazwa;
	private JTextField id_pracownika;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gatunki frame = new Gatunki();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void displayData(){
		try {
			String query = "SELECT * FROM gatunki";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultset = statement.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultset));
			
			statement.close();
			resultset.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the frame.
	 */
	public Gatunki() {
		connection = MysqlConnection.databaseConnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 903, 524);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(90, 90, 90));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton button = new JButton("MENU");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainView mainview = new MainView();
				mainview.setVisible(true);
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 11));
		button.setBackground(new Color(255,255,255));
		button.setBounds(22, 11, 65, 23);
		contentPane.add(button);
		
		JLabel lblMedicalData = new JLabel("GATUNKI");
		lblMedicalData.setHorizontalAlignment(SwingConstants.CENTER);
		lblMedicalData.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblMedicalData.setForeground(new Color(255,255,255));
		lblMedicalData.setBounds(443, 28, 235, 32);
		contentPane.add(lblMedicalData);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(302, 97, 554, 315);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String id = (table.getModel().getValueAt(table.getSelectedRow(), 0)).toString();
					String query = "SELECT * FROM gatunki where id_gatunku = ?";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id);
					ResultSet resultset = statement.executeQuery();
					
					while(resultset.next()){
						id_gatunku.setText(resultset.getString("id_gatunku"));
						pelna_nazwa.setText(resultset.getString("pelna_nazwa"));
						id_pracownika.setText(resultset.getString("id_pracownika"));
					}
					
					statement.close();
					resultset.close();
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		table.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(table);
		
		JButton btnDodajGatunek = new JButton("Dodaj gatunek");
		btnDodajGatunek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "INSERT INTO gatunki(id_gatunku, pelna_nazwa, id_pracownika) VALUES (?,?,?)";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id_gatunku.getText());
					statement.setString(2, pelna_nazwa.getText());
					statement.setString(3, id_pracownika.getText());
					statement.execute();
					
					id_gatunku.setText("");
					pelna_nazwa.setText("");
					id_pracownika.setText("");		
					displayData();
					statement.close();

				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDodajGatunek.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDodajGatunek.setBackground(new Color(255,255,255));
		btnDodajGatunek.setBounds(80, 337, 164, 32);
		contentPane.add(btnDodajGatunek);
		
		JButton btnEdit = new JButton("Edytuj gatunek ");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update gatunki SET pelna_nazwa  = '" + pelna_nazwa.getText() + "', id_pracownika  = '" + id_pracownika.getText() + "' where id_gatunku = '"+ id_gatunku.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_gatunku.setText("");
					pelna_nazwa.setText("");
					id_pracownika.setText("");
					
					displayData();
					statement.close();

	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEdit.setBackground(new Color(255,255,255));
		btnEdit.setBounds(80, 380, 164, 32);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Usu\u0144 gatunek");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "DELETE FROM gatunki where id_gatunku = '"+ id_gatunku.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_gatunku.setText("");
					pelna_nazwa.setText("");
					id_pracownika.setText("");
					
					displayData();
					statement.close();
				
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBackground(new Color(255,255,255));
		btnDelete.setBounds(79, 423, 165, 32);
		contentPane.add(btnDelete);
		
		JButton btnMedicalData = new JButton("<=");
		btnMedicalData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayData();
			}
		});
		btnMedicalData.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMedicalData.setBackground(new Color(255,255,255));
		btnMedicalData.setBounds(791, 428, 65, 23);
		contentPane.add(btnMedicalData);
		
		
		id_gatunku = new JTextField();
		id_gatunku.setColumns(10);
		id_gatunku.setBounds(148, 100, 127, 20);
		contentPane.add(id_gatunku);
		
		pelna_nazwa = new JTextField();
		pelna_nazwa.setColumns(10);
		pelna_nazwa.setBounds(148, 131, 127, 20);
		contentPane.add(pelna_nazwa);
		
		id_pracownika = new JTextField();
		id_pracownika.setColumns(10);
		id_pracownika.setBounds(148, 162, 127, 20);
		contentPane.add(id_pracownika);
		
		JLabel lblMedicalId = new JLabel("ID PRACOWNIKA");
		lblMedicalId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMedicalId.setForeground(new Color(255,255,255));
		lblMedicalId.setBounds(22, 165, 116, 14);
		contentPane.add(lblMedicalId);
		
		JLabel lblpelna_nazwa = new JLabel("PE\u0141NA NAZWA");
		lblpelna_nazwa.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblpelna_nazwa.setForeground(new Color(255,255,255));
		lblpelna_nazwa.setBounds(22, 134, 103, 14);
		contentPane.add(lblpelna_nazwa);
		
		JLabel lblid_gatunku = new JLabel("ID GATUNKU");
		lblid_gatunku.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblid_gatunku.setForeground(new Color(255,255,255));
		lblid_gatunku.setBounds(22, 104, 116, 14);
		contentPane.add(lblid_gatunku);
		
		JButton btnSzukajGatunku = new JButton("Szukaj po ID GATUNKU");
		btnSzukajGatunku.setBackground(Color.WHITE);
		btnSzukajGatunku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * FROM gatunki where id_gatunku = '"+ id_gatunku.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					ResultSet resultset = statement.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(resultset));
														
					statement.close();
					resultset.close();
				} catch (Exception error) {
					error.printStackTrace();
				}

			}
		});
		btnSzukajGatunku.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSzukajGatunku.setBounds(68, 232, 183, 34);
		contentPane.add(btnSzukajGatunku);
		
		JButton btnSzukajPoId = new JButton("Szukaj po ID PRACOWNIKA");
		btnSzukajPoId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * FROM gatunki where id_pracownika = '"+ id_pracownika.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					ResultSet resultset = statement.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(resultset));
									
					statement.close();
					resultset.close();
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnSzukajPoId.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSzukajPoId.setBackground(Color.WHITE);
		btnSzukajPoId.setBounds(68, 277, 183, 32);
		contentPane.add(btnSzukajPoId);
		
	}
}
