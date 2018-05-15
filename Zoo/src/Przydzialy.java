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

public class Przydzialy extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection connection;
	private JTable table;
	private JTextField id_obiektu;
	private JTextField id_pracownika;
	private JTextField od;
	private JTextField dO;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Przydzialy frame = new Przydzialy();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void displayData(){
		try {
			String query = "SELECT * FROM przydzialy";
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
	public Przydzialy() {
		connection = MysqlConnection.databaseConnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 904, 521);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(90,90,90));
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
		button.setBounds(22, 15, 65, 23);
		contentPane.add(button);
		
		JLabel lblMedicalData = new JLabel("PRZYDZIA\u0141Y DO OBIEKT\u00D3W");
		lblMedicalData.setHorizontalAlignment(SwingConstants.CENTER);
		lblMedicalData.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblMedicalData.setForeground(new Color(255,255,255));
		lblMedicalData.setBounds(392, 15, 375, 32);
		contentPane.add(lblMedicalData);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(346, 71, 503, 335);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String id = (table.getModel().getValueAt(table.getSelectedRow(), 0)).toString();
					String query = "SELECT * FROM przydzialy where id_obiektu = ?";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id);
					ResultSet resultset = statement.executeQuery();
					
					while(resultset.next()){
						id_obiektu.setText(resultset.getString("id_obiektu"));
						id_pracownika.setText(resultset.getString("id_pracownika"));
						od.setText(resultset.getString("od"));
						dO.setText(resultset.getString("dO"));
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
		
		JButton btnDodajPrzydzia = new JButton("Dodaj przydzia\u0142");
		btnDodajPrzydzia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "INSERT INTO przydzialy(id_obiektu, id_pracownika, od, dO) VALUES (?,?,?,?)";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id_obiektu.getText());
					statement.setString(2, id_pracownika.getText());
					statement.setString(3, od.getText());
					statement.setString(4, dO.getText());
					statement.execute();
					
					id_obiektu.setText("");
					id_pracownika.setText("");
					od.setText("");
					dO.setText("");		
					displayData();
					statement.close();

				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDodajPrzydzia.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDodajPrzydzia.setBackground(new Color(255,255,255));
		btnDodajPrzydzia.setBounds(103, 333, 129, 32);
		contentPane.add(btnDodajPrzydzia);
		
		JButton btnEdit = new JButton("Edytuj przydzia\u0142");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update przydzialy SET id_pracownika  = '" + id_pracownika.getText() + "', od  = '" + od.getText() + "', dO  = '" + dO.getText() + "' where id_obiektu = '"+ id_obiektu.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_obiektu.setText("");
					id_pracownika.setText("");
					od.setText("");
					dO.setText("");
					
					displayData();
					statement.close();

	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEdit.setBackground(new Color(255,255,255));
		btnEdit.setBounds(103, 376, 129, 30);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Usu\u0144 przydzia\u0142");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "DELETE FROM przydzialy where id_obiektu = '"+ id_obiektu.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_obiektu.setText("");
					id_pracownika.setText("");
					od.setText("");
					dO.setText("");
					
					displayData();
					statement.close();
				
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBackground(new Color(255,255,255));
		btnDelete.setBounds(103, 417, 129, 32);
		contentPane.add(btnDelete);
		
		id_obiektu = new JTextField();
		id_obiektu.setColumns(10);
		id_obiektu.setBounds(178, 100, 129, 20);
		contentPane.add(id_obiektu);
		
		id_pracownika = new JTextField();
		id_pracownika.setColumns(10);
		id_pracownika.setBounds(178, 71, 129, 20);
		contentPane.add(id_pracownika);
		
		od = new JTextField();
		od.setColumns(10);
		od.setBounds(178, 131, 129, 20);
		contentPane.add(od);
		
		dO = new JTextField();
		dO.setColumns(10);
		dO.setBounds(178, 162, 129, 20);
		contentPane.add(dO);
		
		JLabel lblMedicalId = new JLabel("Do");
		lblMedicalId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMedicalId.setForeground(new Color(255,255,255));
		lblMedicalId.setBounds(50, 164, 72, 14);
		contentPane.add(lblMedicalId);
		
		JLabel lblod = new JLabel("Od");
		lblod.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblod.setForeground(new Color(255,255,255));
		lblod.setBounds(50, 133, 72, 14);
		contentPane.add(lblod);
		
		JLabel lblid_pracownika = new JLabel("ID pracownika");
		lblid_pracownika.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblid_pracownika.setForeground(new Color(255,255,255));
		lblid_pracownika.setBounds(50, 71, 118, 14);
		contentPane.add(lblid_pracownika);
		
		JLabel lblMedicalId_1 = new JLabel("ID obiektu");
		lblMedicalId_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMedicalId_1.setForeground(new Color(255,255,255));
		lblMedicalId_1.setBounds(50, 102, 72, 14);
		contentPane.add(lblMedicalId_1);
		
		JButton btnSzukajPoId = new JButton("Szukaj po ID pracownika");
		btnSzukajPoId.setBackground(Color.WHITE);
		btnSzukajPoId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * FROM przydzialy where id_pracownika = '"+ id_pracownika.getText() + "'  ";
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
		btnSzukajPoId.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSzukajPoId.setBounds(72, 230, 192, 32);
		contentPane.add(btnSzukajPoId);
		
		JButton btnSzukajPoId_1 = new JButton("Szukaj po ID obiektu");
		btnSzukajPoId_1.setBackground(Color.WHITE);
		btnSzukajPoId_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * FROM przydzialy where id_obiektu = '"+ id_obiektu.getText() + "'  ";
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
		btnSzukajPoId_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSzukajPoId_1.setBounds(72, 273, 192, 32);
		contentPane.add(btnSzukajPoId_1);
		
		JButton btnObiektyInfo = new JButton("Obiekty INFO");
		btnObiektyInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * from obiekty;";
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
		btnObiektyInfo.setBackground(Color.WHITE);
		btnObiektyInfo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnObiektyInfo.setBounds(738, 422, 111, 23);
		contentPane.add(btnObiektyInfo);
		
		JButton btnPracownicyInfo = new JButton("Pracownicy INFO");
		btnPracownicyInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						String query = "SELECT * from pracownicy;";
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
		btnPracownicyInfo.setBackground(Color.WHITE);
		btnPracownicyInfo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPracownicyInfo.setBounds(599, 422, 129, 23);
		contentPane.add(btnPracownicyInfo);
		
		JButton button_1 = new JButton("<=");
		button_1.setBackground(Color.WHITE);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayData();
			}
		});
		button_1.setBounds(523, 422, 65, 23);
		contentPane.add(button_1);
	}
}
