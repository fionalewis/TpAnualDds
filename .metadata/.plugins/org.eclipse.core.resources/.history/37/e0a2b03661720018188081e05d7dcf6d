package app.frames;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaClienteSGE {

	private JFrame frmSgeCliente;
	private JPanel contentPane;
	
	int xx, xy;

	//Launch the application
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaClienteSGE window = new VentanaClienteSGE();
					window.frmSgeCliente.setVisible(true);
					window.frmSgeCliente.setUndecorated(true);
					window.frmSgeCliente.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the application
	
	public VentanaClienteSGE() {
		initialize();
	}

	// Initialize the contents of the frame.

	private void initialize() {
		frmSgeCliente = new JFrame();
		frmSgeCliente.setBounds(100, 100, 450, 300);
		frmSgeCliente.setMaximumSize(new Dimension(770,490));
		frmSgeCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSgeCliente.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginWindowSGE.class.getResource("/app/Icon.png")));
		frmSgeCliente.setBackground(Color.WHITE);
		frmSgeCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSgeCliente.setTitle("SGE - Cliente - Men\u00FA principal");
		frmSgeCliente.setBounds(100, 100, 770, 490);
		frmSgeCliente.setLocationRelativeTo(null);
		frmSgeCliente.getContentPane().setLayout(null);//
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmSgeCliente.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSge = new JLabel("SGE");
		lblSge.setHorizontalAlignment(SwingConstants.CENTER);
		lblSge.setForeground(Color.WHITE);
		lblSge.setFont(new Font("Minion Pro", Font.BOLD, 45));
		lblSge.setBounds(95, 45, 138, 60);
		contentPane.add(lblSge);
		
		JLabel lblSistemaDeGestin = new JLabel("Sistema de Gesti\u00F3n Energ\u00E9tica");
		lblSistemaDeGestin.setHorizontalAlignment(SwingConstants.CENTER);
		lblSistemaDeGestin.setForeground(Color.WHITE);
		lblSistemaDeGestin.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblSistemaDeGestin.setBounds(22, 92, 287, 60);
		contentPane.add(lblSistemaDeGestin);
		
		JLabel lblClienteMen = new JLabel("Cliente - Men\u00FA principal:");
		lblClienteMen.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteMen.setForeground(Color.WHITE);
		lblClienteMen.setFont(new Font("Minion Pro", Font.PLAIN, 17));
		lblClienteMen.setBounds(22, 141, 287, 29);
		contentPane.add(lblClienteMen);
		
		Button profileButtonOption = new Button("Perfil");
		profileButtonOption.setForeground(Color.WHITE);
		profileButtonOption.setFont(new Font("Segoe UI", Font.BOLD, 12));
		profileButtonOption.setBackground(new Color(218, 9, 40));
		profileButtonOption.setBounds(21, 175, 272, 29);
		contentPane.add(profileButtonOption);
		
		Button devicesButtonOption = new Button("Dispositivos");
		devicesButtonOption.setForeground(Color.WHITE);
		devicesButtonOption.setFont(new Font("Segoe UI", Font.BOLD, 12));
		devicesButtonOption.setBackground(new Color(218, 9, 40));
		devicesButtonOption.setBounds(22, 210, 272, 29);
		contentPane.add(devicesButtonOption);
		
		Button botonDeConsumo = new Button("Consumos");
		botonDeConsumo.setForeground(Color.WHITE);
		botonDeConsumo.setFont(new Font("Segoe UI", Font.BOLD, 12));
		botonDeConsumo.setBackground(new Color(218, 9, 40));
		botonDeConsumo.setBounds(21, 245, 272, 29);
		contentPane.add(botonDeConsumo);
		
		Button button_3 = new Button("Hogar Eficiente");
		button_3.setForeground(Color.WHITE);
		button_3.setFont(new Font("Segoe UI", Font.BOLD, 12));
		button_3.setBackground(new Color(218, 9, 40));
		button_3.setBounds(21, 280, 272, 29);
		contentPane.add(button_3);
		
		Button button_4 = new Button("Reglas");
		button_4.setForeground(Color.WHITE);
		button_4.setFont(new Font("Segoe UI", Font.BOLD, 12));
		button_4.setBackground(new Color(218, 9, 40));
		button_4.setBounds(22, 315, 272, 29);
		contentPane.add(button_4);
		
		Button signOutButtonOption = new Button("Cerrar Sesi\u00F3n");
		signOutButtonOption.setForeground(Color.WHITE);
		signOutButtonOption.setFont(new Font("Segoe UI", Font.BOLD, 12));
		signOutButtonOption.setBackground(new Color(218, 9, 40));
		signOutButtonOption.setBounds(22, 350, 272, 29);
		contentPane.add(signOutButtonOption);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(VentanaClienteSGE.class.getResource("/app/LoginBackground.jpg")));
		label.setBounds(0, 0, 770, 490);
		contentPane.add(label);
	}

}