
import java.sql.*;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection connection;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
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
	public MainView() {
		connection = MysqlConnection.databaseConnect();
		setBackground(new Color(220, 220, 220));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 893, 595);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(90,90,90));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("System zarz¹dzania ZOO");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255,255,255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(173, 93, 552, 64);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Zwierzêta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Zwierze zwierzeta = new Zwierze();
				zwierzeta.setVisible(true);
				zwierzeta.displayData();
			}
		});
		btnNewButton.setBackground(new Color(255,255,255));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(64, 328, 140, 46);
		contentPane.add(btnNewButton);
		
		JButton btnEmployees = new JButton("Gatunki");
		btnEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				Gatunki gat = new Gatunki();
				gat.setVisible(true);
				gat.displayData();
				
			}
		});
	
		btnEmployees.setBackground(new Color(255,255,255));
		btnEmployees.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEmployees.setBounds(64, 243, 140, 46);
		contentPane.add(btnEmployees);
		
		JButton btnAdoptedDogs = new JButton("Obiekty");
		btnAdoptedDogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Obiekty obiekty = new Obiekty();
				obiekty.setVisible(true);
				obiekty.displayData();
			}
		});
		btnAdoptedDogs.setBackground(new Color(255,255,255));
		btnAdoptedDogs.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAdoptedDogs.setBounds(371, 243, 138, 46);
		contentPane.add(btnAdoptedDogs);
		
		
		JButton btnTasks = new JButton("Pracownicy");
		btnTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Pracownicy prac = new Pracownicy();
				prac.setVisible(true);
				prac.displayData();
			}
		});
		btnTasks.setBackground(new Color(255,255,255));
		btnTasks.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTasks.setBounds(688, 243, 131, 46);
		contentPane.add(btnTasks);
		
		JButton btnNewButton_1 = new JButton("Budynki");
		btnNewButton_1.setBackground(new Color(255,255,255));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				Budynki bud = new Budynki();
				bud.setVisible(true);
				bud.displayData();
			}
		});
		btnNewButton_1.setBounds(371, 328, 138, 46);
		contentPane.add(btnNewButton_1);
		
		JButton btnAdoptedDogs_1 = new JButton("Zadania");
		btnAdoptedDogs_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Zadania zad = new Zadania();
				zad.setVisible(true);
				zad.displayData();
			}
		});
		btnAdoptedDogs_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAdoptedDogs_1.setBackground(new Color(255,255,255));
		btnAdoptedDogs_1.setBounds(688, 413, 131, 45);
		contentPane.add(btnAdoptedDogs_1);
		
		JButton btnPaw = new JButton("Pawilony");
		btnPaw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Pawilony paw = new Pawilony();
				paw.setVisible(true);
				paw.displayData();
			}
		});
		btnPaw.setBackground(new Color(255,255,255));
		btnPaw.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPaw.setBounds(371, 413, 138, 45);
		contentPane.add(btnPaw);
		
		JButton btnAdoptedDogs_2 = new JButton("Przydzia³y");
		btnAdoptedDogs_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Przydzialy prz = new Przydzialy();
				prz.setVisible(true);
				prz.displayData();
			}
		});
		btnAdoptedDogs_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAdoptedDogs_2.setBackground(new Color(255,255,255));
		btnAdoptedDogs_2.setBounds(688, 329, 131, 45);
		contentPane.add(btnAdoptedDogs_2);
		
		
		JButton btnLogOut = new JButton("Wyloguj");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				LogView login = new LogView();
				login.setVisible(true);
			}
		});
		btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLogOut.setBackground(new Color(255,255,255));
		btnLogOut.setBounds(774, 24, 90, 30);
		contentPane.add(btnLogOut);
				
	}
}
