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

public class Obiekty extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection connection;
	private JTable table;
	private JTextField id_obiektu;
	private JTextField rodzaj;
	private JTextField id_budynku;
	private JTextField id_pawilonu;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Obiekty frame = new Obiekty();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void displayData(){
		try {
			String query = "SELECT * FROM obiekty";
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
	public Obiekty() {
		connection = MysqlConnection.databaseConnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 901, 558);
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
		button.setBounds(22, 11, 65, 23);
		contentPane.add(button);
		
		JLabel lblMedicalData = new JLabel("OBIEKTY");
		lblMedicalData.setHorizontalAlignment(SwingConstants.CENTER);
		lblMedicalData.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblMedicalData.setForeground(new Color(255,255,255));
		lblMedicalData.setBounds(484, 11, 184, 36);
		contentPane.add(lblMedicalData);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(305, 69, 557, 386);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String id = (table.getModel().getValueAt(table.getSelectedRow(), 0)).toString();
					String query = "SELECT * FROM obiekty where id_obiektu = ?";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id);
					ResultSet resultset = statement.executeQuery();
					
					while(resultset.next()){
						id_obiektu.setText(resultset.getString("id_obiektu"));
						rodzaj.setText(resultset.getString("rodzaj"));
						id_budynku.setText(resultset.getString("id_budynku"));
						id_pawilonu.setText(resultset.getString("id_pawilonu"));
					}
					
					statement.close();
					resultset.close();
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnDodajObiekt = new JButton("Dodaj obiekt");
		btnDodajObiekt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "INSERT INTO obiekty(id_obiektu, rodzaj, id_budynku, id_pawilonu) VALUES (?,?,?,?)";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id_budynku.getText());
					statement.setString(2, rodzaj.getText());
					statement.setString(3, id_budynku.getText());
					statement.setString(4, id_pawilonu.getText());
					statement.execute();
					
					id_obiektu.setText("");
					rodzaj.setText("");
					id_budynku.setText("");
					id_pawilonu.setText("");		
					displayData();
					statement.close();

				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDodajObiekt.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDodajObiekt.setBackground(new Color(255,255,255));
		btnDodajObiekt.setBounds(82, 343, 124, 30);
		contentPane.add(btnDodajObiekt);
		
		JButton btnEdit = new JButton("Edytuj obiekt");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update obiekty SET rodzaj  = '" + rodzaj.getText() + "', id_budynku  = '" + id_budynku.getText() + "', id_pawilonu  = '" + id_pawilonu.getText() + "' where id_obiektu = '"+ id_obiektu.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_obiektu.setText("");
					rodzaj.setText("");
					id_budynku.setText("");
					id_pawilonu.setText("");
					
					displayData();
					statement.close();

	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEdit.setBackground(new Color(255,255,255));
		btnEdit.setBounds(82, 384, 124, 30);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Usu\u0144 obiekt");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "DELETE FROM obiekty where id_obiektu = '"+ id_obiektu.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_obiektu.setText("");
					rodzaj.setText("");
					id_budynku.setText("");
					id_pawilonu.setText("");
					
					displayData();
					statement.close();
				
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBackground(new Color(255,255,255));
		btnDelete.setBounds(82, 425, 124, 30);
		contentPane.add(btnDelete);
		
		JButton btnMedicalData = new JButton("<=");
		btnMedicalData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayData();
			}
		});
		btnMedicalData.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMedicalData.setBackground(new Color(255,255,255));
		btnMedicalData.setBounds(803, 467, 59, 23);
		contentPane.add(btnMedicalData);
		
		id_obiektu = new JTextField();
		id_obiektu.setColumns(10);
		id_obiektu.setBounds(126, 69, 141, 20);
		contentPane.add(id_obiektu);
		
		rodzaj = new JTextField();
		rodzaj.setColumns(10);
		rodzaj.setBounds(126, 100, 141, 20);
		contentPane.add(rodzaj);
		
		id_budynku = new JTextField();
		id_budynku.setColumns(10);
		id_budynku.setBounds(126, 131, 141, 20);
		contentPane.add(id_budynku);
		
		id_pawilonu = new JTextField();
		id_pawilonu.setColumns(10);
		id_pawilonu.setBounds(126, 162, 141, 20);
		contentPane.add(id_pawilonu);
		
		JLabel lblMedicalId = new JLabel("ID pawilonu");
		lblMedicalId.setForeground(new Color(255,255,255));
		lblMedicalId.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMedicalId.setBounds(22, 165, 72, 14);
		contentPane.add(lblMedicalId);
		
		JLabel lblid_budynku = new JLabel("ID budynku");
		lblid_budynku.setForeground(new Color(255,255,255));
		lblid_budynku.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblid_budynku.setBounds(22, 134, 72, 14);
		contentPane.add(lblid_budynku);
		
		JLabel lblrodzaj = new JLabel("Rodzaj");
		lblrodzaj.setForeground(new Color(255,255,255));
		lblrodzaj.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblrodzaj.setBounds(22, 104, 72, 14);
		contentPane.add(lblrodzaj);
		
		JLabel lblMedicalId_1 = new JLabel("ID obiektu");
		lblMedicalId_1.setForeground(new Color(255,255,255));
		lblMedicalId_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMedicalId_1.setBounds(22, 73, 72, 14);
		contentPane.add(lblMedicalId_1);
		
		JButton btnSzukajPoId = new JButton("Szukaj po ID obiektu");
		btnSzukajPoId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "SELECT * FROM obiekty where id_obiektu = '"+ id_obiektu.getText() + "'  ";
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
		btnSzukajPoId.setBackground(Color.WHITE);
		btnSzukajPoId.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSzukajPoId.setBounds(61, 239, 169, 35);
		contentPane.add(btnSzukajPoId);
		
		JButton btnSzukajPoId_1 = new JButton("Szukaj po ID budynku");
		btnSzukajPoId_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "SELECT * FROM obiekty where id_budynku = '"+ id_budynku.getText() + "'  ";
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
		btnSzukajPoId_1.setBackground(Color.WHITE);
		btnSzukajPoId_1.setBounds(61, 285, 169, 30);
		contentPane.add(btnSzukajPoId_1);
	}
}
