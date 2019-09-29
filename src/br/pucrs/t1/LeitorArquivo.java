package br.pucrs.t1;

import java.io.*;


public class LeitorArquivo {
		
	public int[][] leituraArquivo(String nomeArquivo) throws IOException {
		FileReader leitorArquivo = new FileReader(nomeArquivo);
		BufferedReader bufferedReader = new BufferedReader(leitorArquivo);

        int tamanhoMatriz = Integer.parseInt(bufferedReader.readLine());

        int[][] matriz = new int[tamanhoMatriz][tamanhoMatriz];

        for (int i = 0; i < tamanhoMatriz; i++) {
            String[] linha = bufferedReader.readLine().split(" ");
            for (int j = 0; j < tamanhoMatriz; j++) {
                if (linha[j].equals("E")) {
                    matriz[i][j] = 9;
                } else if (linha[j].equals("S")) {
                    matriz[i][j] = 8;
                } else {
                    matriz[i][j] = Integer.parseInt(linha[j]);
                }
            }
        }
        return matriz;
	}
}
