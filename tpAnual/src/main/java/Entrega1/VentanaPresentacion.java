package Entrega1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/*
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;*/

public class VentanaPresentacion extends JFrame{

	private static final long serialVersionUID = 5939205634861883779L;
	ImageIcon img = new ImageIcon("C:\\Users\\Salome\\Downloads\\iconn.png");
	ImageIcon img2 = new ImageIcon("C:\\Users\\Salome\\Downloads\\icoino.png");	
	
	public VentanaPresentacion() {
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("SGE - Hogar Eficiente");
		setLocationRelativeTo(null);
		setSize(500,500);
		setIconImage(img.getImage());
		inicializarComp();
		//setJMenuBar(menubar);
		
	}
	
	
	public void inicializarComp() {
		JPanel userMenu = new JPanel();
		//userMenu.setBackground(Color.WHITE);
		getContentPane().add(userMenu);
		//userMenu.setLayout(null);
		

		JLabel texto = new JLabel("Cliente - Menú principal:",SwingConstants.LEFT);
		JLabel imagen = new JLabel(img2);
		texto.setFont(new Font("Tahoma",Font.BOLD,15));
		texto.setOpaque(true);
		userMenu.add(texto);
		userMenu.add(imagen);
		
	}

}
