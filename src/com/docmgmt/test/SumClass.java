package com.docmgmt.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import com.docmgmt.server.webinterface.services.pdf.PdfPropUtilities;

public class SumClass extends JFrame {

	JLabel statusbar;
	private int totalFileCorrected = 0;;
	int moduleWiseCouter=0;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				SumClass s = new SumClass();
				s.setVisible(true);
			}
		});

	}

	public SumClass() {

		initUI();
	}
	private void initUI() {		
		JPanel panel = new JPanel();		
		ImageIcon icon = new ImageIcon ( Toolkit.getDefaultToolkit().getImage("D://images/1.png"));
		setIconImage(icon.getImage());	
		statusbar = new JLabel(" Sarjen System Pvt. Ltd.");
		statusbar.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));		
		panel.setLayout(null);
		panel.setBackground(new Color(22, 160, 133));
		JButton close = new JButton("X");
	
		close.setName("close");
		close.setBounds(1, 1, 5, 5);
		Font newButtonFont = new Font(close.getFont().getName(), close
				.getFont().getStyle(), 26);
		close.setFont(newButtonFont);
		close.addActionListener(new ButtonListener());
		close.setBackground(Color.WHITE);
		close.setForeground(Color.BLACK);

		final JButton open = new JButton("Select Dossier or File");
		open.setName("select");
		open.setFont(newButtonFont);
		open.setBackground(Color.WHITE);
		open.setForeground(Color.BLACK);
		open.addActionListener(new ButtonListener());

		panel.add(close);
		panel.add(open);
		TestPannel pan=new TestPannel();
		pan.setLayout(null);
		pan.add(close);
		pan.add(open);
		add(pan);
		//add(panel);
		add(statusbar, BorderLayout.SOUTH);

		setTitle("Auto Correction of PDF Properties for ECTD Submission");
		setSize(1000, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// initUIbackground.setLayout(new FlowLayout());
		getContentPane().setBackground(Color.WHITE);

		open.setBounds((getWidth() / 2) -150, getHeight() / 2 - 50, 300, 50);
		setUndecorated(true);
		setResizable(false);
		getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
		//SplashScreen s=new SplashScreen("D://images/pic2.png", this, 5000);
		addComponentListener(new ComponentListener() {

			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub	
				open.setBounds((getWidth() / 2) - 90, getHeight() / 2 - 40,
						180, 40);
			}

			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}
	
	class TestPannel extends JPanel {

	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        BufferedImage t;
			try {
				t = ImageIO.read(new File("D://images//Untitled.png"));
				  g.drawImage(t,0,0,getWidth(),getHeight(),this);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }		
	}
	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JButton o = (JButton) e.getSource();

			String label = o.getText();
			if (o.getName().equalsIgnoreCase("close"))
				System.exit(0);
			if (o.getName().equalsIgnoreCase("select")) {
				System.out.println(label);
				final JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setDialogTitle("Select Dossier");
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(SumClass.this) == JFileChooser.APPROVE_OPTION) {
					totalFileCorrected=0;

					// TODO Auto-generated method stub
					new Thread(new Runnable() {

						public void run() {
							// TODO Auto-generated method stub							
							if(chooser.getSelectedFile().isDirectory())
							{
								autoCorrectPdfPropFolder(chooser.getSelectedFile());
							}
							else if(chooser.getSelectedFile().getName().endsWith(".pdf")){
								
								PdfPropUtilities prop=new PdfPropUtilities();
								prop.autoCorrectPdfProp(chooser.getSelectedFile().getAbsolutePath());
							}
							else
							{
								JOptionPane.showMessageDialog(null,"Invalid File");
								statusbar.setText("Invalid File");
							}
						}
					}).start();
				}
				// Integer opt = j.showSaveDialog(this);
			}
			// statusbar.setText(" " + label );
		}
	}

	class SplashScreen extends JWindow {
		public SplashScreen(String filename, Frame f, int waitTime) {
			super(f);
			JLabel l = new JLabel(new ImageIcon(filename));
	
			getContentPane().add(l, BorderLayout.CENTER);
			pack();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension labelSize = l.getPreferredSize();
			setLocation(screenSize.width / 2 - (labelSize.width / 2),
					screenSize.height / 2 - (labelSize.height / 2));
		
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					setVisible(false);
					dispose();
				}
			});
			final int pause = waitTime;
			final Runnable closerRunner = new Runnable() {
				public void run() {
					setVisible(false);
					dispose();
				}
			};
			Runnable waitRunner = new Runnable() {
				public void run() {
					try {
						
						Thread.sleep(pause);
						SwingUtilities.invokeAndWait(closerRunner);
					} catch (Exception e) {
						e.printStackTrace();
						// can catch InvocationTargetException
						// can catch InterruptedException
					}
				}
			};
			setVisible(true);
			Thread splashThread = new Thread(waitRunner, "SplashThread");
			splashThread.start();
		}

	}

	public void autoCorrectPdfPropFolder(java.io.File folder) {
		// final java.io.File folder = new java.io.File("D:\\vijay\\PDFFiles");
		listFilesForFolder(folder);
		System.out.println("Completed");
		statusbar.setText("Completed.... Total File Corrected-"
				+ totalFileCorrected);
	}

	public void listFilesForFolder(final java.io.File folder) {
		for (final java.io.File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println("File To Correct->"
						+ fileEntry.getAbsolutePath());
				statusbar.setText("Correcting->" + fileEntry.getAbsolutePath());

				final String FileToCorrect = fileEntry.getAbsolutePath();

				if (FileToCorrect.endsWith(".pdf")) {
					new Thread(new Runnable() {
						public void run() {
							totalFileCorrected++;
							PdfPropUtilities pdfUtil = new PdfPropUtilities();
							pdfUtil.autoCorrectPdfProp(FileToCorrect);
						}

					}).start();
					try {
						Thread.sleep(2000);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}