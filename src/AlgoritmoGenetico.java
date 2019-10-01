import java.io.IOException;
import java.util.Random;

public class AlgoritmoGenetico {

	public final static int SIZE = 30; // total de cargas
	public final static int TAM = 20; // tamanho da população: quantidade de soluções
	public final static int MAX = 50; // numero maximo de geraï¿½oes (iteraï¿½oes)
	public String aux ="";
	
	public void algoritmoGenetico() throws IOException {

		LeitorArquivo testArquivo = new LeitorArquivo();
		// int[] cargas = GeraCargas(); // cargas definidas em aula
		int[][] populacao = new int[TAM][SIZE + 1]; // população atual: contem os cromossomos (soluções candidatas)
		int[][] populacaoIntermediaria = new int[TAM][SIZE + 1]; // populaï¿½ao intermediaria: corresponde a população
																	// em
																	// construï¿½ao
																	// Obs: A ultima coluna de cada linha da matriz e
																	// para armazenar o valor da
																	// função de aptidao,
																	// que indica o quao boa eh a solução

		// ===========> Ciclo do AG
		System.out.println("=================================================================");
		aux += "=================================================================\n";
		System.out.println("Encontrando a melhor distribuicao usando Algoritmos Geneticos ...");
		aux += "Encontrando a melhor distribuicao usando Algoritmos Geneticos ...\n";
		System.out.println("=================================================================");
		aux += "=================================================================\n";
		inicializaPopulacao(populacao); // cria soluções aleatoriamente

		for (int geracao = 0; geracao < MAX; geracao++) {
			aux += "Geração: " + geracao + "\n";
			System.out.println("Geraï¿½ï¿½o:" + geracao);
			calculaFuncoesDeAptidao(populacao); // APTIDAO
			int melhor = pegaAltaTerra(populacao, populacaoIntermediaria); // ELITISMO
			if (populacaoIntermediaria[0][SIZE] == 999) {
				printaMatriz(populacao);
				aux += "Achou a melhor distribuicao: " + melhor + "\n";
				testArquivo.gravarGeneticoTxt(aux);
				System.out.println("Achou a melhor distribuicao: " + melhor);
				break;
			}
			printaMatriz(populacao);			
			cruzamento(populacaoIntermediaria, populacao);// CROSSOVER
			if (geracao % 2 == 0)
				mutacao(populacaoIntermediaria);// MUTACAO
			populacao = populacaoIntermediaria;
		}
	}

	private void printaMatriz(int[][] populacao) throws IOException {
		LeitorArquivo testArquivo = new LeitorArquivo();
		System.out.println("__________________________________________________________________");
		aux += "__________________________________________________________________\n";
		for (int i = 0; i < populacao.length; i++) {
			aux += "(" + i + ") ";
			System.out.print("(" + i + ") ");
			for (int j = 0; j < populacao[i].length - 1; j++) {
				aux += populacao[i][j] + " ";
				System.out.print(populacao[i][j] + " ");
			}
			aux += " Aptidao: " + populacao[i][populacao[i].length - 1] + "\n";
			System.out.println(" Aptidao: " + populacao[i][populacao[i].length - 1]);
		}
		aux += "__________________________________________________________________\n";
		System.out.println("__________________________________________________________________");

		testArquivo.gravarGeneticoTxt(aux);
	}

	/**
	 * Gera população inicial: conjunto de soluções candidatas
	 */
	private void inicializaPopulacao(int[][] populacao) {
		for (int i = 0; i < populacao.length; i++) {
			for (int j = 0; j < populacao[i].length - 1; j++) {
				populacao[i][j] = randomFill();
			}
		}
	}

	private void calculaFuncoesDeAptidao(int[][] populacao) {
		for (int i = 0; i < populacao.length; i++) {
			populacao[i][SIZE] = funcaoDeAptidao(populacao[i]);
		}
	}

	/**
	 * Função de aptidao: heuristica que estima a qualidade de uma solução
	 */
	private int funcaoDeAptidao(int[] individuo) {
		int score = 0;
		int[][] mapa = aptStart(); // recebe mapa novo

		int[] incio = getPosicaoInicial(mapa);
		int tempLatitude = incio[1];
		int tempLongetude = incio[0];

		mapa = spawn(tempLatitude, tempLongetude, mapa);

		for (int i = 0; i < individuo.length - 1; i++) {

			int[] matrizLatitudeLongetude = getPosicao(mapa);
			int latitude = matrizLatitudeLongetude[1];
			int longitude = matrizLatitudeLongetude[0];

			int movement = individuo[i];
			score += movimentarNoMapa(latitude, longitude, movement, mapa);

		}
		return score;
	}

	/**
	 * Seleção por elitismo. Encontra a melhor solução e copia para a população
	 * intermediaria
	 * @throws IOException 
	 */
	private int pegaAltaTerra(int[][] populacao, int[][] populacaoIntermediaria) throws IOException {
		LeitorArquivo leitor = new LeitorArquivo();
		int highlander = 0;
		int menor = populacao[0][SIZE];

		for (int i = 1; i < populacao.length; i++) {
			if (populacao[i][SIZE] > menor) {
				menor = populacao[i][SIZE];
				highlander = i;
			}
		}
		aux += "Seleção por elitismo - melhor dessa geração: " + highlander + "\n";
		leitor.gravarGeneticoTxt(aux);
		System.out.println("Seleção por elitismo - melhor dessa geração: " + highlander);

		for (int i = 0; i < SIZE + 1; i++) {
			populacaoIntermediaria[0][i] = populacao[highlander][i];
		}
		return highlander;
	}

	/**
	 * Seleção por torneio. Escolhe cromossomo (solução) para cruzamento
	 * ******************************************************* ***************
	 */
	private int[] torneio(int[][] populacao) {
		Random r = new Random();
		int l1 = r.nextInt(populacao.length);
		int l2 = r.nextInt(populacao.length);

		if (populacao[l1][SIZE] > populacao[l2][SIZE]) {
			System.out.println("Cromossomo selecionado para cruzamento: " + l1);
			return populacao[l1];
		} else {
			System.out.println("Cromossomo selecionado para cruzamento: " + l2);
			return populacao[l2];
		}
	}

	/**
	 * Cruzamento uniponto: gera dois filhos e coloca na população intermediaria
	 */
	private void cruzamento(int[][] intermediaria, int[][] populacao) {
		int[] pai;
		int[] pai2;
		// int corte = SIZE / 2; // tam/2
		int corte = 10;
		// int linha = 1;

		for (int i = 1; i < TAM -1; i = i + 2) {
			do {
				pai = torneio(populacao);
				pai2 = torneio(populacao);
			} while (pai == pai2);
			for (int j = 0; j < corte; j++) {
				intermediaria[i][j] = pai[j];
				intermediaria[i + 1][j] = pai2[j];
			}
			for (int j = corte; j < SIZE; j++) {
				intermediaria[i][j] = pai2[j];
				intermediaria[i + 1][j] = pai[j];
			}
		}
	}

	/**
	 * Mutação é uma transformação que ocorre no cromossomo do individuo durante sua
	 * formação. A mutação altera os genes do cromossomo
	 */
	private void mutacao(int[][] intermediaria) {
		Random r = new Random();

		for (int cont = 1; cont <= 2; cont++) {
			int linha = r.nextInt(TAM);
			int coluna = r.nextInt(SIZE);
			if (intermediaria[linha][coluna] == 0)
				intermediaria[linha][coluna] = 1;
			else
				intermediaria[linha][coluna] = 0;

			System.out.println("Mutou o cromossomo : " + linha);
		}

	}

	private int randomFill() {// usado para gerar pops
		Random rand = new Random();
		int randomNum = rand.nextInt(4);
		return randomNum;
	}

	private int movimentarNoMapa(int latitude, int longetude, int movimento, int[][] mapa) {
		int[] matrizNovaLatitudeELongetude = getNovoValorLatitudeELongetude(latitude, longetude, movimento);
		int score = analisaMapa(matrizNovaLatitudeELongetude[1], matrizNovaLatitudeELongetude[0], mapa);
		atualizaMapa(latitude, longetude, matrizNovaLatitudeELongetude[1], matrizNovaLatitudeELongetude[0], mapa);
		return score;
	}

	private int[][] atualizaMapa(int latitude, int longetude, int latitudeNova, int longitudeNova, int[][] mapa) {
		// SE a posicao nova esta dentro do mapa e nao eh uma parede (anda)
		if (latitudeNova >= 0 && longitudeNova >= 0 && latitudeNova < mapa.length && longitudeNova < mapa.length
				&& mapa[longitudeNova][latitudeNova] != 1) {
			mapa[latitude][longetude] = 3;
			mapa[latitudeNova][longitudeNova] = 4;
		}
		return mapa;
	}

	private int[] getNovoValorLatitudeELongetude(int latitude, int longetude, int movimento) {
		switch (movimento) {
		case 0:
			latitude--;
			break;

		case 1:
			latitude++;
			break;

		case 2:
			longetude--;
			break;

		case 3:
			longetude++;
			break;

		default:
			throw new IllegalArgumentException("Movimento invalido");
		}
		return new int[] { latitude, longetude };
	}

	/**
	 * Analisa o mapa para ver esta nas bordas para nï¿½o dar erro e penaliza o
	 * agente por sair do mapa
	 * 
	 */
	private int analisaMapa(int latitude, int longetude, int[][] mapa) {
		if (latitude < 0 || longetude < 0 || latitude > 9 || longetude > 9) {
			return pontuacao(-1);
		} else {
			return pontuacao(mapa[longetude][latitude]);
		}
	}

	private int pontuacao(int novaPosicao) {
		int retorno = 0;
		switch (novaPosicao) {
		case 1:
			retorno = -5;
			break;
		case 3:
			retorno = -1;
			break;
		case -1:
			retorno = -20;
			break;
		case 0:
			retorno = 10;
			break;
		case 9:
			retorno = -10;
			break;
		case 8:
			retorno = 999; // terminou
			break;
		case 4:// o agente não se mexeou, ficou parado
			retorno = 0;
			break;
		default:
			throw new IllegalArgumentException("Opcao invalida");

		}
		return retorno;
	}

	private int[][] spawn(int tempLat, int tempLng, int[][] mapa) {
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa.length; j++) {
				if (j == tempLat && i == tempLng) {
					mapa[i][j] = 4;
				}
			}
		}
		return mapa;
	}

	private int[][] aptStart() {
		int[][] mapa = Gerador.labZero();
		return mapa;
	}

	private int[] getPosicao(int[][] mapa) {
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa.length; j++) {
				if (mapa[i][j] == 4) {
					return new int[] { j, i };
				}
			}
		}
		return null;

	}

	private int[] getPosicaoInicial(int[][] mapa) {
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa.length; j++) {
				if (mapa[i][j] == 9) {
					mapa[i][j] = 4;
					int[] ret = { j, i };
					return ret;
				}
			}
		}
		return null;
	}
}
