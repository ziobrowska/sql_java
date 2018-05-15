import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Pawilony extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection connection;
	private JTextField id_pawilonu;
	private JTextField temat;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pawilony frame = new Pawilony();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void displayData(){
		try {
			String query = "SELECT * FROM pawilony";
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
	public Pawilony() {
		connection = MysqlConnection.databaseConnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 904, 559);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(90, 90, 90));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblDogsLocation = new JLabel("PAWILONY");
		lblDogsLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblDogsLocation.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDogsLocation.setForeground(new Color(255,255,255));
		lblDogsLocation.setBounds(458, 32, 184, 36);
		contentPane.add(lblDogsLocation);
		
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
		button.setBounds(29, 22, 65, 23);
		contentPane.add(button);
		
		id_pawilonu = new JTextField();
		id_pawilonu.setColumns(10);
		id_pawilonu.setBounds(120, 124, 151, 20);
		contentPane.add(id_pawilonu);
		
		temat = new JTextField();
		temat.setColumns(10);
		temat.setBounds(120, 155, 151, 20);
		contentPane.add(temat);
		
		JLabel lblid_pawilonu = new JLabel("ID Pawilonu");
		lblid_pawilonu.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblid_pawilonu.setForeground(new Color(255,255,255));
		lblid_pawilonu.setBounds(29, 127, 93, 14);
		contentPane.add(lblid_pawilonu);
		
		JLabel lbltematNumber = new JLabel("Temat");
		lbltematNumber.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbltematNumber.setForeground(new Color(255,255,255));
		lbltematNumber.setBounds(29, 158, 81, 14);
		contentPane.add(lbltematNumber);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(305, 115, 547, 220);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String id_paw = (table.getModel().getValueAt(table.getSelectedRow(), 0)).toString();
					String query = "SELECT * FROM pawilony where id_pawilonu = ?";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id_paw);
					ResultSet resultset = statement.executeQuery();
					
					while(resultset.next()){
						id_pawilonu.setText(resultset.getString("id_pawilonu"));
						temat.setText(resultset.getString("temat"));
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
		
		JButton btnDodajPawilon = new JButton("Dodaj pawilon");
		btnDodajPawilon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "INSERT INTO pawilony(id_pawilonu, temat) VALUES (?,?)";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id_pawilonu.getText());
					statement.setString(2, temat.getText());
					statement.execute();
					
					id_pawilonu.setText("");
					temat.setText("");		
					displayData();
					statement.close();

				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDodajPawilon.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDodajPawilon.setBackground(new Color(255,255,255));
		btnDodajPawilon.setBounds(86, 288, 141, 30);
		contentPane.add(btnDodajPawilon);
		
		JButton btnEdit = new JButton("Edytuj pawilon");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update pawilony SET temat  = '" + temat.getText() + "' where id_pawilonu = '"+ id_pawilonu.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_pawilonu.setText("");
					temat.setText("");
					
					displayData();
					statement.close();

	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEdit.setBackground(new Color(255,255,255));
		btnEdit.setBounds(86, 329, 141, 30);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Usu\u0144 pawilon");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "DELETE FROM pawilony where id_pawilonu = '"+ id_pawilonu.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_pawilonu.setText("");
					temat.setText("");
					displayData();
					statement.close();
				
	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBackground(new Color(255,255,255));
		btnDelete.setBounds(86, 370, 141, 30);
		contentPane.add(btnDelete);
		
		JButton btnSzukajPoId = new JButton("Szukaj po ID pawilonu");
		btnSzukajPoId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "SELECT * FROM pawilony where id_pawilonu = '"+ id_pawilonu.getText() + "'  ";
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
		btnSzukajPoId.setBounds(74, 228, 169, 36);
		contentPane.add(btnSzukajPoId);
		
		JButton button_1 = new JButton("<=");
		button_1.setBackground(Color.WHITE);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayData();
			}
		});
		button_1.setBounds(787, 358, 65, 23);
		contentPane.add(button_1);
	}

}
