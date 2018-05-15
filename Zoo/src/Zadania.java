import java.awt.BorderLayout;
import java.sql.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Zadania extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private JTextField id_czynnosci;
	private JTextField rodzaj;
	private JTextField miejsce;
	private JTextField od;
	private JTextField dO;
	private JTextField id_pracownika;
	private Connection connection = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Zadania	 frame = new Zadania();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void displayData(){
		try {
			String query = "SELECT * FROM czynnosci";
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
	
	public Zadania() {
		connection = MysqlConnection.databaseConnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 905, 515);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(90,90,90));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblEmployees = new JLabel("ZADANIA DLA PRACOWNIK\u00D3W");
		lblEmployees.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmployees.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblEmployees.setForeground(new Color(255,255,255));
		lblEmployees.setBounds(323, 28, 466, 32);
		contentPane.add(lblEmployees);
		
		JButton button = new JButton("MENU");
		button.setFont(new Font("Tahoma", Font.BOLD, 11));
		button.setBackground(new Color(255,255,255));
		button.setBounds(10, 11, 65, 23);
		contentPane.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainView mainview = new MainView();
				mainview.setVisible(true);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(293, 84, 550, 314);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					String employeeid = (table.getModel().getValueAt(table.getSelectedRow(), 0)).toString();
					String query = "SELECT * FROM czynnosci where id_czynnosci = ?";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, employeeid);
					ResultSet resultset = statement.executeQuery();
					
					while(resultset.next()){
						id_czynnosci.setText(resultset.getString("id_czynnosci"));
						rodzaj.setText(resultset.getString("rodzaj"));
						miejsce.setText(resultset.getString("miejsce"));
						od.setText(resultset.getString("od"));
						dO.setText(resultset.getString("dO"));
						id_pracownika.setText(resultset.getString("id_pracownika"));
											
					}
					
					statement.close();
					resultset.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		table.setBackground(new Color(255,255,255));
		table.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		JLabel lblid_czynnosci = new JLabel("ID ZADANIA");
		lblid_czynnosci.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblid_czynnosci.setForeground(new Color(255,255,255));
		lblid_czynnosci.setBounds(24, 92, 102, 14);
		contentPane.add(lblid_czynnosci);
		
		JLabel lblrodzaj = new JLabel("RODZAJ");
		lblrodzaj.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblrodzaj.setForeground(new Color(255,255,255));
		lblrodzaj.setBounds(24, 120, 72, 14);
		contentPane.add(lblrodzaj);
		
		JLabel lblmiejsceNumber = new JLabel("OBIEKT");
		lblmiejsceNumber.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblmiejsceNumber.setForeground(new Color(255,255,255));
		lblmiejsceNumber.setBounds(24, 145, 89, 14);
		contentPane.add(lblmiejsceNumber);
		
		JLabel lblod = new JLabel("OD");
		lblod.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblod.setForeground(new Color(255,255,255));
		lblod.setBounds(24, 170, 72, 14);
		contentPane.add(lblod);
		
		JLabel lbldO = new JLabel("DO");
		lbldO.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbldO.setForeground(new Color(255,255,255));
		lbldO.setBounds(24, 195, 72, 14);
		contentPane.add(lbldO);
		
		JLabel lblid_pracownika = new JLabel("ID PRACOWNIKA");
		lblid_pracownika.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblid_pracownika.setForeground(new Color(255,255,255));
		lblid_pracownika.setBounds(24, 220, 119, 14);
		contentPane.add(lblid_pracownika);
		
		id_czynnosci = new JTextField();
		id_czynnosci.setColumns(10);
		id_czynnosci.setBounds(136, 90, 134, 20);
		contentPane.add(id_czynnosci);
		
		rodzaj = new JTextField();
		rodzaj.setColumns(10);
		rodzaj.setBounds(136, 118, 134, 20);
		contentPane.add(rodzaj);
		
		miejsce = new JTextField();
		miejsce.setColumns(10);
		miejsce.setBounds(136, 142, 134, 20);
		contentPane.add(miejsce);
		
		od = new JTextField();
		od.setColumns(10);
		od.setBounds(136, 167, 134, 20);
		contentPane.add(od);
		
		dO = new JTextField();
		dO.setColumns(10);
		dO.setBounds(136, 192, 134, 20);
		contentPane.add(dO);
		
		id_pracownika = new JTextField();
		id_pracownika.setColumns(10);
		id_pracownika.setBounds(136, 217, 134, 20);
		contentPane.add(id_pracownika);
		
		JButton btnDodajZadanie = new JButton("Dodaj zadanie");
		btnDodajZadanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "INSERT INTO czynnosci(id_czynnosci, rodzaj, miejsce, od, dO, id_pracownika) VALUES (?,?,?,?,?,?)";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id_czynnosci.getText());
					statement.setString(2, rodzaj.getText());
					statement.setString(3, miejsce.getText());
					statement.setString(4, od.getText());
					statement.setString(5, dO.getText());
					statement.setString(6, id_pracownika.getText());
					statement.execute();
					
					id_czynnosci.setText("");
					rodzaj.setText("");
					miejsce.setText("");
					od.setText("");
					dO.setText("");
					id_pracownika.setText("");
								
					displayData();
					statement.close();
	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnDodajZadanie.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDodajZadanie.setBackground(new Color(255,255,255));
		btnDodajZadanie.setBounds(82, 361, 119, 23);
		contentPane.add(btnDodajZadanie);
		
		JButton btnUsuZadanie = new JButton("Usu\u0144 zadanie");
		btnUsuZadanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "DELETE FROM czynnosci where id_czynnosci = '"+ id_czynnosci.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_czynnosci.setText("");
					rodzaj.setText("");
					miejsce.setText("");
					od.setText("");
					dO.setText("");
					id_pracownika.setText("");
					
			
					displayData();
					statement.close();
				
	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnUsuZadanie.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUsuZadanie.setBackground(new Color(255,255,255));
		btnUsuZadanie.setBounds(82, 395, 119, 23);
		contentPane.add(btnUsuZadanie);
		
		JButton btnEdytujZadanie = new JButton("Edytuj zadanie");
		btnEdytujZadanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update czynnosci SET rodzaj = '" + rodzaj.getText() + "', miejsce = '" + miejsce.getText() + "', od = '" + od.getText() + "', do = '" + dO.getText() + "', id_pracownika = '"+ id_pracownika.getText() + "' where id_czynnosci = '"+ id_czynnosci.getText() + "'  ";
					PreparedStatement statement = connection.prepareStatement(query);
					statement.execute();
					id_czynnosci.setText("");
					rodzaj.setText("");
					miejsce.setText("");
					od.setText("");
					dO.setText("");
					id_pracownika.setText("");
					
					displayData();
					statement.close();
	
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnEdytujZadanie.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEdytujZadanie.setBackground(new Color(255,255,255));
		btnEdytujZadanie.setBounds(82, 429, 119, 23);
		contentPane.add(btnEdytujZadanie);
		
		JButton btnTasksInfo = new JButton("Pracownicy INFO");
		btnTasksInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT DISTINCT czynnosci.id_pracownika, pracownicy.imie, pracownicy.nazwisko  FROM czynnosci INNER JOIN pracownicy ON czynnosci.id_pracownika=pracownicy.id_pracownika;";
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
		btnTasksInfo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTasksInfo.setBackground(new Color(255,255,255));
		btnTasksInfo.setBounds(707, 409, 136, 23);
		contentPane.add(btnTasksInfo);
		
		JButton btnEmployeeInfo = new JButton("<=");
		btnEmployeeInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					displayData();
			}
		});
		btnEmployeeInfo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEmployeeInfo.setBackground(new Color(255,255,255));
		btnEmployeeInfo.setBounds(507, 409, 65, 23);
		contentPane.add(btnEmployeeInfo);
		
		JButton btnSzukajPoId = new JButton("Szukaj po ID ZADANIA");
		btnSzukajPoId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * FROM czynnosci where id_czynnosci = '"+ id_czynnosci.getText() + "'  ";
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
		btnSzukajPoId.setBounds(53, 259, 185, 23);
		contentPane.add(btnSzukajPoId);
		
		JButton btnSzukajPoObiekt = new JButton("Szukaj po OBIEKT");
		btnSzukajPoObiekt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * FROM czynnosci where miejsce = '"+ miejsce.getText() + "'  ";
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
		btnSzukajPoObiekt.setBackground(Color.WHITE);
		btnSzukajPoObiekt.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSzukajPoObiekt.setBounds(53, 293, 185, 23);
		contentPane.add(btnSzukajPoObiekt);
		
		JButton btnSzukajPoId_1 = new JButton("Szukaj po ID PRACOWNIKA");
		btnSzukajPoId_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSzukajPoId_1.setBackground(Color.WHITE);
		btnSzukajPoId_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * FROM czynnosci where id_pracownika = '"+ id_pracownika.getText() + "'  ";
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
		btnSzukajPoId_1.setBounds(53, 327, 185, 23);
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
		btnObiektyInfo.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnObiektyInfo.setBackground(Color.WHITE);
		btnObiektyInfo.setBounds(581, 409, 116, 23);
		contentPane.add(btnObiektyInfo);
	}
}
