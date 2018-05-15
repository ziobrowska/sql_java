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

public class Pracownicy extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection connection;
	private JTable table;
	private JTextField id_pracownika;
	private JTextField imie;
	private JTextField nazwisko;
	private JTextField dzial;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pracownicy frame = new Pracownicy();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void displayData(){
		try {
			String query = "SELECT * FROM pracownicy";
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
	public Pracownicy() {
		connection = MysqlConnection.databaseConnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 907, 562);
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
		button.setBackground(new Color(255, 255, 255));
		button.setBounds(22, 15, 65, 23);
		contentPane.add(button);
		
		JLabel lblMedicalData = new JLabel("PRACOWNICY");
		lblMedicalData.setHorizontalAlignment(SwingConstants.CENTER);
		lblMedicalData.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblMedicalData.setForeground(new Color(255, 255, 255));
		lblMedicalData.setBounds(446, 15, 235, 32);
		contentPane.add(lblMedicalData);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(342, 70, 487, 358);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String id = (table.getModel().getValueAt(table.getSelectedRow(), 0)).toString();
					String query = "SELECT * FROM pracownicy where id_pracownika = ?";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id);
					ResultSet resultset = statement.executeQuery();
					
					while(resultset.next()){
						id_pracownika.setText(resultset.getString("id_pracownika"));
						imie.setText(resultset.getString("imie"));
						nazwisko.setText(resultset.getString("nazwisko"));
						dzial.setText(resultset.getString("dzial"));
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
		
		JButton btnDodajPracownika = new JButton("Dodaj pracownika");
		btnDodajPracownika.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "INSERT INTO pracownicy(id_pracownika, imie, nazwisko, dzial) VALUES (?,?,?,?)";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id_pracownika.getText());
					statement.setString(2, imie.getText());
					statement.setString(3, nazwisko.getText());
					statement.setString(4, dzial.getText());
					statement.execute();
					
					id_pracownika.setText("");
					imie.setText("");
					nazwisko.setText("");
					dzial.setText("");		
					displayData();
					statement.close();

				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDodajPracownika.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDodajPracownika.setBackground(new Color(255, 255, 255));
		btnDodajPracownika.setBounds(102, 384, 136, 32);
		contentPane.add(btnDodajPracownika);
		
		JButton btnEdit = new JButton("Edytuj pracownika");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update pracownicy SET imie  = '" + imie.getText() + "', nazwisko  = '" + nazwisko.getText() + "', dzial  = '" + dzial.getText() + "' where id_pracownika = '"+ id_pracownika.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_pracownika.setText("");
					imie.setText("");
					nazwisko.setText("");
					dzial.setText("");
					
					displayData();
					statement.close();

	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnEdit.setBackground(new Color(255, 255, 255));
		btnEdit.setBounds(102, 427, 136, 30);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Usu\u0144 pracownika");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "DELETE FROM pracownicy where id_pracownika = '"+ id_pracownika.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_pracownika.setText("");
					imie.setText("");
					nazwisko.setText("");
					dzial.setText("");
					
					displayData();
					statement.close();
				
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBackground(new Color(255, 255, 255));
		btnDelete.setBounds(102, 468, 136, 32);
		contentPane.add(btnDelete);
		
		JButton btnMedicalData = new JButton("<=");
		btnMedicalData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayData();
			}
		});
		btnMedicalData.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMedicalData.setBackground(new Color(255, 255, 255));
		btnMedicalData.setBounds(772, 450, 65, 23);
		contentPane.add(btnMedicalData);
		
		id_pracownika = new JTextField();
		id_pracownika.setColumns(10);
		id_pracownika.setBounds(149, 70, 136, 20);
		contentPane.add(id_pracownika);
		
		imie = new JTextField();
		imie.setColumns(10);
		imie.setBounds(149, 100, 136, 20);
		contentPane.add(imie);
		
		nazwisko = new JTextField();
		nazwisko.setColumns(10);
		nazwisko.setBounds(149, 131, 136, 20);
		contentPane.add(nazwisko);
		
		dzial = new JTextField();
		dzial.setColumns(10);
		dzial.setBounds(149, 162, 136, 20);
		contentPane.add(dzial);
		
		JLabel lblMedicalId = new JLabel("Dzial");
		lblMedicalId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMedicalId.setForeground(new Color(255, 255, 255));
		lblMedicalId.setBounds(22, 165, 72, 14);
		contentPane.add(lblMedicalId);
		
		JLabel lblnazwisko = new JLabel("Nazwisko");
		lblnazwisko.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblnazwisko.setForeground(new Color(255, 255, 255));
		lblnazwisko.setBounds(22, 134, 72, 14);
		contentPane.add(lblnazwisko);
		
		JLabel lblimie = new JLabel("Imiê");
		lblimie.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblimie.setForeground(new Color(255, 255, 255));
		lblimie.setBounds(22, 104, 94, 14);
		contentPane.add(lblimie);
		
		JLabel lblMedicalId_1 = new JLabel("ID pracownika");
		lblMedicalId_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMedicalId_1.setForeground(new Color(255, 255, 255));
		lblMedicalId_1.setBounds(22, 73, 117, 14);
		contentPane.add(lblMedicalId_1);
		
		JButton btnNewButton = new JButton("Szukaj po ID pracownika");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT pracownicy.imie, pracownicy.nazwisko, pracownicy.dzial FROM pracownicy where id_pracownika = '"+ id_pracownika.getText() + "'  ";
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
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(77, 202, 195, 32);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Szukaj po nazwisku");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * FROM pracownicy where nazwisko = '"+ nazwisko.getText() + "'  ";
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
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(77, 245, 195, 32);
		contentPane.add(btnNewButton_1);
		
		JButton btnSzukajPoDziale = new JButton("Poka\u017C zadania");
		btnSzukajPoDziale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT czynnosci.id_czynnosci, czynnosci.rodzaj, czynnosci.miejsce, czynnosci.od, czynnosci.do FROM czynnosci where id_pracownika = '"+ id_pracownika.getText() + "'  ";
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
		btnSzukajPoDziale.setBackground(Color.WHITE);
		btnSzukajPoDziale.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSzukajPoDziale.setBounds(77, 331, 195, 32);
		contentPane.add(btnSzukajPoDziale);
		
		JButton button_1 = new JButton("Poka\u017C przydzia\u0142y");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT przydzialy.id_obiektu, przydzialy.od, przydzialy.do FROM przydzialy where id_pracownika = '"+ id_pracownika.getText() + "'  ";
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
		button_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		button_1.setBackground(Color.WHITE);
		button_1.setBounds(77, 288, 195, 32);
		contentPane.add(button_1);
	}
}
