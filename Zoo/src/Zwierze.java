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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComboBox;

public class Zwierze extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField id_gatunku;
	private JTextField id_obiektu;
	private JTextField id_zwierzecia;
	private JTextField plec;
	private Connection connection;
	private JTextField nr_zw;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Zwierze frame = new Zwierze();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void displayData(){
		try {
			String query = "SELECT * FROM zwierzeta";
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
	public Zwierze() {
		connection = MysqlConnection.databaseConnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 907, 542);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(90,90,90));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("ZWIERZÊTA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setForeground(new Color(255,255,255));
		lblNewLabel.setBounds(449, 26, 184, 36);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(332, 110, 523, 306);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String nrzw = (table.getModel().getValueAt(table.getSelectedRow(), 0)).toString();
					String query = "SELECT * FROM zwierzeta where nr_zw = ?";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, nrzw);
					ResultSet resultset = statement.executeQuery();
					
					while(resultset.next()){
						id_zwierzecia.setText(resultset.getString("identyfikator"));
						id_gatunku.setText(resultset.getString("id_gatunku"));
						plec.setText(resultset.getString("plec"));
						id_obiektu.setText(resultset.getString("id_obiektu"));
						nr_zw.setText(resultset.getString("nr_zw"));	
						
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
		
		JButton btnNewButton = new JButton("MENU");
		btnNewButton.setBackground(new Color(255,255,255));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainView mainview = new MainView();
				mainview.setVisible(true);
			}
		});
		btnNewButton.setBounds(20, 11, 90, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("ID GATUNKU");
		lblNewLabel_1.setForeground(new Color(255,255,255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(61, 136, 106, 26);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblid_obiektu = new JLabel("ID OBIEKTU");
		lblid_obiektu.setForeground(new Color(255,255,255));
		lblid_obiektu.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblid_obiektu.setBounds(61, 204, 70, 14);
		contentPane.add(lblid_obiektu);
		
		JLabel lblid_zwierzecia = new JLabel("IDENTYFIKATOR");
		lblid_zwierzecia.setForeground(new Color(255,255,255));
		lblid_zwierzecia.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblid_zwierzecia.setBounds(61, 111, 106, 14);
		contentPane.add(lblid_zwierzecia);
		
		JLabel lblplec = new JLabel("P\u0141E\u0106");
		lblplec.setForeground(new Color(255,255,255));
		lblplec.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblplec.setBounds(61, 173, 55, 14);
		contentPane.add(lblplec);
		
		id_gatunku = new JTextField();
		id_gatunku.setBounds(177, 139, 132, 20);
		contentPane.add(id_gatunku);
		id_gatunku.setColumns(10);
		
		id_obiektu = new JTextField();
		id_obiektu.setColumns(10);
		id_obiektu.setBounds(177, 201, 132, 20);
		contentPane.add(id_obiektu);
		
		id_zwierzecia = new JTextField();
		id_zwierzecia.setColumns(10);
		id_zwierzecia.setBounds(177, 108, 132, 20);
		contentPane.add(id_zwierzecia);
		
		plec = new JTextField();
		plec.setColumns(10);
		plec.setBounds(177, 170, 132, 20);
		contentPane.add(plec);
		
				
		JButton btnNewButton_1 = new JButton("Dodaj zwierz\u0119");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "INSERT INTO zwierzeta(identyfikator, id_gatunku, plec, id_obiektu) VALUES (?,?,?,?)";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id_zwierzecia.getText());
					statement.setString(2, id_gatunku.getText());
					statement.setString(3, plec.getText());
					statement.setString(4, id_obiektu.getText());
					statement.execute();
					nr_zw.setText("");
					id_zwierzecia.setText("");
					id_gatunku.setText("");
					plec.setText("");
					id_obiektu.setText("");
					displayData();
					statement.close();
	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBackground(new Color(255,255,255));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(118, 381, 126, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnDelete = new JButton("Usu\u0144 zwierz\u0119");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "DELETE FROM zwierzeta where nr_zw = '"+ nr_zw.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					nr_zw.setText("");
					id_zwierzecia.setText("");
					id_gatunku.setText("");
					plec.setText("");
					id_obiektu.setText("");
					displayData();
					statement.close();
				
	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDelete.setBackground(new Color(255,255,255));
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBounds(118, 415, 126, 23);
		contentPane.add(btnDelete);
		
		JButton btnEdit = new JButton("Edytuj zwierz\u0119");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update zwierzeta SET identyfikator = '" + id_zwierzecia.getText() + "', id_gatunku = '" + id_gatunku.getText() + "', plec = '" + plec.getText() + "', id_obiektu = '" + id_obiektu.getText() + "' where nr_zw = '"+ nr_zw.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					nr_zw.setText("");
					id_zwierzecia.setText("");
					id_gatunku.setText("");
					plec.setText("");
					id_obiektu.setText("");
						
					displayData();
					statement.close();
				
	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		
		btnEdit.setBackground(new Color(255,255,255));
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEdit.setBounds(118, 451, 126, 26);
		contentPane.add(btnEdit);
		
		JLabel dogId = new JLabel("Nr kolejny");
		dogId.setForeground(new Color(255,255,255));
		dogId.setFont(new Font("Tahoma", Font.BOLD, 11));
		dogId.setBounds(61, 235, 70, 14);
		contentPane.add(dogId);
		
		nr_zw = new JTextField();
		nr_zw.setColumns(10);
		nr_zw.setBounds(177, 232, 132, 20);
		contentPane.add(nr_zw);
		
		JButton btnDogsInfo = new JButton("<=");
		btnDogsInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayData();
				}
		});
		btnDogsInfo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDogsInfo.setBackground(new Color(255,255,255));
		btnDogsInfo.setBounds(794, 439, 61, 23);
		contentPane.add(btnDogsInfo);
				
		JLabel image = new JLabel("");
		image.setBounds(177, 381, 67, 57);
		image.setIcon(new ImageIcon(getClass().getResource("")));	
		contentPane.add(image);
		
		JLabel label = new JLabel("");
		label.setBounds(701, 393, 46, 36);
		label.setIcon(new ImageIcon(getClass().getResource("")));
		contentPane.add(label);
		
		JButton btnSzukajPoId = new JButton("Szukaj po ID OBIEKTU");
		btnSzukajPoId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				String query = "SELECT * FROM zwierzeta where id_obiektu = '"+ id_obiektu.getText() + "'  ";
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
		btnSzukajPoId.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSzukajPoId.setBounds(94, 325, 177, 36);
		contentPane.add(btnSzukajPoId);
		
		JButton btnSzukajPoIdentyfikator = new JButton("Szukaj po IDENTYFIKATOR");
		btnSzukajPoIdentyfikator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "SELECT * FROM zwierzeta where identyfikator = '"+ id_zwierzecia.getText() + "'  ";
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
		btnSzukajPoIdentyfikator.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSzukajPoIdentyfikator.setBackground(Color.WHITE);
		btnSzukajPoIdentyfikator.setBounds(94, 278, 177, 36);
		contentPane.add(btnSzukajPoIdentyfikator);
		
	
	}
}