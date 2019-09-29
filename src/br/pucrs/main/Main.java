package br.pucrs.main;

import java.io.IOException;
import java.util.Scanner;

import br.pucrs.classes.AlgoritmoAEstrela;
import br.pucrs.classes.AlgoritmoGenetico;
import br.pucrs.classes.LeitorArquivo;
import br.pucrs.classes.VisualizarMatriz;

public class Main {

	public static void main(String[] args) throws InterruptedException,IOException {
				
		LeitorArquivo leitor = new LeitorArquivo();
		VisualizarMatriz visualizar = new VisualizarMatriz();
		
		Scanner teclado = new Scanner(System.in);
				
        System.out.println("Digite qual nome do arquivo deseja testar: ");
        
        String nomeLeitura = teclado.next();
        int[][] matriz = leitor.leituraArquivo(nomeLeitura);
        teclado.close();
        visualizar.visualizarMatriz(matriz);
        
        AlgoritmoGenetico ag = new AlgoritmoGenetico();
        AlgoritmoAEstrela aestrela = new AlgoritmoAEstrela();
        ag.algoritmoGenetico();
        aestrela.buscaCaminhoOtimoAEstrela(matriz);
		
	}

}
