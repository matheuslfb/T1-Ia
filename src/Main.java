
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws InterruptedException,IOException {
				
		LeitorArquivo leitor = new LeitorArquivo();
		VisualizarMatriz visualizar = new VisualizarMatriz();
		

        
        String arquivo = args[0];
        int[][] matriz = leitor.leituraArquivo(arquivo);

        visualizar.visualizarMatriz(matriz);
        
        AlgoritmoGenetico ag = new AlgoritmoGenetico();
        AlgoritmoAEstrela aestrela = new AlgoritmoAEstrela();
        ag.algoritmoGenetico();
        System.out.println("Terminou o genético");
        aestrela.buscaCaminhoOtimoAEstrela(matriz);
        System.out.println("Terminou o A*");
		
	}

}
