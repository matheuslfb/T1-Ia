import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LeitorArquivo {

	public int[][] leituraArquivo(String nomeArquivo) throws IOException {
		FileReader leitorArquivo = new FileReader(configurarCaminhoArquivo(nomeArquivo));
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
		bufferedReader.close();
		return matriz;
	}

	private String configurarCaminhoArquivo(String fileName) {
		File caminhoArquivo = new File("");
		String separator = System.getProperty("file.separator");
		return caminhoArquivo.getAbsolutePath() + separator
				+ "txt" + separator + fileName;
		// return caminhoArquivo.getAbsolutePath() + separator + "src" + separator + "br" + separator + "pucrs" + separator
		// 		+ "txt" + separator + fileName;
	}
}
