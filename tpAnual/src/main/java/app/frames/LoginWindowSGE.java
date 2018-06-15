package app.frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entregas.Programa;

import java.awt.Color;
import java.awt.Button;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class LoginWindowSGE extends JFrame {

	private static final long serialVersionUID = -3749148458692850051L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	
	private JFrame controllingFrame = new JFrame();
	
	int xx, xy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindowSGE frame = new LoginWindowSGE();
					frame.setUndecorated(true); //
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the frame
	
	public LoginWindowSGE() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginWindowSGE.class.getResource("/entrega1/Icon.png")));
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SGE - Iniciar sesión");
		setBounds(100, 100, 770, 490);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);//
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel closeButton_label = new JLabel("");
		closeButton_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		closeButton_label.setHorizontalAlignment(SwingConstants.CENTER);
		closeButton_label.setIcon(new ImageIcon(LoginWindowSGE.class.getResource("/entrega1/closeButton.png")));
		closeButton_label.setBounds(734, 4, 32, 32);
		contentPane.add(closeButton_label);
		
		JLabel minimizeButton_label = new JLabel("");
		minimizeButton_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setState(Frame.ICONIFIED);
			}
		});
		minimizeButton_label.setHorizontalAlignment(SwingConstants.CENTER);
		minimizeButton_label.setIcon(new ImageIcon(LoginWindowSGE.class.getResource("/app/minimizeButton.png")));
		minimizeButton_label.setBounds(698, 4, 32, 32);
		contentPane.add(minimizeButton_label);
		
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
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblUsuario.setBounds(30, 163, 108, 19);
		contentPane.add(lblUsuario);
		
		JTextArea usernameField = new JTextArea();
		usernameField.setLineWrap(true);
		usernameField.setForeground(Color.LIGHT_GRAY);
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		usernameField.setBounds(30, 184, 272, 19);
		contentPane.add(usernameField);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setHorizontalAlignment(SwingConstants.LEFT);
		lblContrasea.setForeground(Color.WHITE);
		lblContrasea.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblContrasea.setBounds(30, 214, 108, 19);
		contentPane.add(lblContrasea);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.LIGHT_GRAY);
		passwordField.setBounds(30, 233, 272, 20);
		contentPane.add(passwordField);
		
		JLabel lblConfirmarContrasea = new JLabel("Confirmar contrase\u00F1a");
		lblConfirmarContrasea.setHorizontalAlignment(SwingConstants.LEFT);
		lblConfirmarContrasea.setForeground(Color.WHITE);
		lblConfirmarContrasea.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblConfirmarContrasea.setBounds(30, 264, 189, 19);
		contentPane.add(lblConfirmarContrasea);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(30, 284, 272, 20);
		contentPane.add(confirmPasswordField);
		
		Button button = new Button("Iniciar Sesi\u00F3n");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String username = usernameField.getText();
				char[] pass = passwordField.getPassword();
				char[] lap = Programa.cprueba.getPassword().toCharArray();
				if(username == Programa.cprueba.getNombre() && lap == pass){
					JOptionPane.showMessageDialog(controllingFrame,"Ha ingresado los datos correctamente.");
		        } else {
		        	JOptionPane.showMessageDialog(controllingFrame,"Contraseña incorrecta. Por favor, intente de nuevo.","Error de logueo",JOptionPane.ERROR_MESSAGE);					
				}
				
			}
		});
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(218,9,40));
		button.setFont(new Font("Segoe UI", Font.BOLD, 12));
		button.setBounds(30, 333, 272, 29);
		contentPane.add(button);
		
		JLabel label = new JLabel("");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				xx = arg0.getX();
		        xy = arg0.getY();
			}
		});
		label.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				LoginWindowSGE.this.setLocation(x - xx, y - xy); 
			}
		});
		label.setIcon(new ImageIcon(LoginWindowSGE.class.getResource("/app/LoginBackground.jpg")));
		label.setBounds(0, 0, 770, 490);
		contentPane.add(label);
	}
}