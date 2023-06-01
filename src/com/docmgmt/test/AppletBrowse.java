package com.docmgmt.test;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import com.docmgmt.server.webinterface.services.pdf.PdfPropUtilities;

public class AppletBrowse extends JApplet {
	public void init() {
	
		
		setLayout(new GridBagLayout());
		JButton browse = new JButton("...");
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Browse the folder to process");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getCurrentDirectory(): "
							+ chooser.getCurrentDirectory());
					System.out.println("getSelectedFile() : "
							+ chooser.getSelectedFile());

					autoCorrectPdfProp(chooser.getSelectedFile());

				} else {
					System.out.println("No Selection ");
				}
			}
		});
		add(browse);
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		JFileChooser chooser = new JFileChooser();
	}

	public void autoCorrectPdfProp(File pathToCorrect) {
		System.out.println("Path->" + pathToCorrect);

		PdfPropUtilities propUtil = new PdfPropUtilities();
		propUtil.autoCorrectPdfPropFolder(pathToCorrect);

	}

}
