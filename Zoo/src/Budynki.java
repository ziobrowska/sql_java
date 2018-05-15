import java.sql.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Budynki extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection connection;
	private JTable table;
	private JTextField id_budynku;
	private JTextField uwagi;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Budynki frame = new Budynki();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void displayData(){
		try {
			String query = "SELECT * FROM budynki";
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
	public Budynki() {
		connection = MysqlConnection.databaseConnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 905, 568);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(90, 90, 90));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblBudynki = new JLabel("BUDYNKI");
		lblBudynki.setHorizontalAlignment(SwingConstants.CENTER);
		lblBudynki.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBudynki.setForeground(new Color(255,255,255));
		lblBudynki.setBounds(473, 35, 184, 36);
		contentPane.add(lblBudynki);
		
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
		button.setBounds(25, 11, 65, 23);
		contentPane.add(button);
		
		JButton btnDodajBudynek = new JButton("Dodaj budynek");
		btnDodajBudynek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "INSERT INTO budynki(id_budynku, uwagi) VALUES (?,?)";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id_budynku.getText());
					statement.setString(2, uwagi.getText());
					statement.execute();
					id_budynku.setText("");
					uwagi.setText("");
					
					displayData();
					statement.close();

	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDodajBudynek.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDodajBudynek.setBackground(new Color(255,255,255));
		btnDodajBudynek.setBounds(91, 264, 133, 30);
		contentPane.add(btnDodajBudynek);
		
		JButton btnEdit = new JButton("Edytuj budynek");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update budynki SET uwagi = '" + uwagi.getText() + "' where id_budynku = '"+ id_budynku.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_budynku.setText("");
					uwagi.setText("");
					
					
					displayData();
					statement.close();

	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
			
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEdit.setBackground(new Color(255,255,255));
		btnEdit.setBounds(91, 305, 133, 30);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Usu\u0144 budynek");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "DELETE FROM budynki where id_budynku = '"+ id_budynku.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_budynku.setText("");
			
					displayData();
					statement.close();
				
	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBackground(new Color(255,255,255));
		btnDelete.setBounds(91, 346, 133, 30);
		contentPane.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(329, 122, 516, 283);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String id_bu = (table.getModel().getValueAt(table.getSelectedRow(), 0)).toString();
					String query = "SELECT * FROM Budynki where id_budynku = ?";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id_bu);
					ResultSet resultset = statement.executeQuery();
					
					while(resultset.next()){
						id_budynku.setText(resultset.getString("id_budynku"));
						uwagi.setText(resultset.getString("uwagi"));
						
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
		
		JLabel lblid_budynku = new JLabel("ID budynku");
		lblid_budynku.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblid_budynku.setForeground(new Color(255,255,255));
		lblid_budynku.setBounds(50, 125, 110, 14);
		contentPane.add(lblid_budynku);
		
		JLabel lbluwagi = new JLabel("Uwagi");
		lbluwagi.setForeground(new Color(255,255,255));
		lbluwagi.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbluwagi.setBounds(50, 151, 72, 14);
		contentPane.add(lbluwagi);
		
		id_budynku = new JTextField();
		id_budynku.setColumns(10);
		id_budynku.setBounds(170, 122, 107, 20);
		contentPane.add(id_budynku);
		
		uwagi = new JTextField();
		uwagi.setColumns(10);
		uwagi.setBounds(170, 149, 107, 20);
		contentPane.add(uwagi);
		
		JLabel label = new JLabel("");
		label.setBounds(25, 380, 65, 57);
		label.setIcon(new ImageIcon(getClass().getResource("")));	
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(146, 380, 68, 57);
		label_1.setIcon(new ImageIcon(getClass().getResource("")));
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(278, 380, 72, 57);
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("")));
		contentPane.add(lblNewLabel);
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(415, 373, 72, 64);
		label_2.setIcon(new ImageIcon(getClass().getResource("")));
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("");
		label_3.setBounds(550, 387, 65, 50);
		label_3.setIcon(new ImageIcon(getClass().getResource("")));
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("");
		label_4.setBounds(679, 373, 73, 66);
		label_4.setIcon(new ImageIcon(getClass().getResource("")));
		contentPane.add(label_4);
		
		JButton btnSzukajPoId = new JButton("Szukaj po ID budynku");
		btnSzukajPoId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "SELECT * FROM budynki where id_budynku = '"+ id_budynku.getText() + "'  ";
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
		btnSzukajPoId.setBackground(Color.WHITE);
		btnSzukajPoId.setBounds(71, 203, 162, 36);
		contentPane.add(btnSzukajPoId);
		
		JButton button_1 = new JButton("<=");
		button_1.setBackground(Color.WHITE);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					displayData();
								
			}
		});
		button_1.setBounds(780, 414, 65, 23);
		contentPane.add(button_1);
	}
}
