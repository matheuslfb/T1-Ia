package br.pucrs.t1;

import java.io.*;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		
		String nomeLeitura = "";
		
		Scanner teclado = new Scanner(System.in);
		
		InputStream stream = Main.class.getResourceAsStream("/br.pucrs.txt/"+ nomeLeitura + ".txt");
		if (stream == null)
			JOptionPane.showMessageDialog(null, "Resource não localizado");

		Scanner input = null;
		try {
			input = new Scanner(stream);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Scanner error");
		}

	}

}
