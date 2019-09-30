import java.util.Random;

public class AlgoritmoGenetico {

	public final static int SIZE = 21; // total de cargas
	public final static int TAM = 11; // tamanho da popula�ao: quantidade de solu�oes
	public final static int MAX = 50; // numero maximo de gera�oes (itera�oes)

	public void algoritmoGenetico() {

		// int[] cargas = GeraCargas(); // cargas definidas em aula
		int[][] populacao = new int[TAM][SIZE + 1]; // popula�ao atual: contem os cromossomos (solu�oes candidatas)
		int[][] populacaoIntermediaria = new int[TAM][SIZE + 1]; // popula�ao intermediaria: corresponde a popula�ao em
																	// constru�ao
																	// Obs: A ultima coluna de cada linha da matriz e
																	// para armazenar o valor da
																	// fun�ao de aptidao,
																	// que indica o quao boa eh a solu�ao

		// ===========> Ciclo do AG
		System.out.println("=================================================================");
		System.out.println("Encontrando a melhor distribuicao usando Algoritmos Geneticos ...");
		System.out.println("=================================================================");
		inicializaPopulacao(populacao); // cria solu��es aleatoriamente

		for (int geracao = 0; geracao < MAX; geracao++) {
			System.out.println("Gera��o:" + geracao);
			calculaFuncoesDeAptidao(populacao); // APTIDAO
			int melhor = pegaAltaTerra(populacao, populacaoIntermediaria); // ELITISMO
			if (populacaoIntermediaria[0][SIZE] == 99999) {
				printaMatriz(populacao);
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

	private void printaMatriz(int[][] populacao) {
		System.out.println("__________________________________________________________________");
		for (int i = 0; i < populacao.length; i++) {
			System.out.print("(" + i + ") ");
			for (int j = 0; j < populacao[i].length - 1; j++) {
				System.out.print(populacao[i][j] + " ");
			}
			System.out.println(" Aptidao: " + populacao[i][populacao[i].length - 1]);
		}
		System.out.println("__________________________________________________________________");
	}

	/**
	 * Gera popula��o inicial: conjunto de solu��es candidatas
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
	 * Fun��o de aptidao: heuristica que estima a qualidade de uma solu��o
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
	 * Sele��o por elitismo. Encontra a melhor solu��o e copia para a popula��o
	 * intermediaria
	 */
	private int pegaAltaTerra(int[][] populacao, int[][] populacaoIntermediaria) {
		int highlander = 0;
		int menor = populacao[0][SIZE];

		for (int i = 1; i < populacao.length; i++) {
			if (populacao[i][SIZE] > menor) {
				menor = populacao[i][SIZE];
				highlander = i;
			}
		}
		System.out.println("Sele��o por elitismo - melhor dessa geracao: " + highlander);

		for (int i = 0; i < SIZE + 1; i++) {
			populacaoIntermediaria[0][i] = populacao[highlander][i];
		}
		return highlander;
	}

	/**
	 * Sele��o por torneio. Escolhe cromossomo (solu��o) para cruzamento
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
	 * Cruzamento uniponto: gera dois filhos e coloca na popula��o intermediaria
	 */
	private void cruzamento(int[][] intermediaria, int[][] populacao) {
		int[] pai;
		int[] pai2;
		// int corte = SIZE / 2; // tam/2
		int corte = 10;
		// int linha = 1;

		for (int i = 1; i < TAM; i = i + 2) {
			do {
				pai = torneio(populacao);
				pai2 = torneio(populacao);
			} while (pai == pai2);
			System.out.println("Gerando dois filhos...");
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
	 * Muta��o � uma transforma��o que ocorre no cromossomo do individuo durante sua
	 * forma��o. A muta��o altera os genes do cromossomo
	 */
	private void mutacao(int[][] intermediaria) {
		Random r = new Random();
		// System.out.println("Tentando mutacao");

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

	private int[] getNovoValorLatitudeELongetude(int lat, int lng, int movimento) {
		switch (movimento) {
		case 0:
			lat--;
			break;

		case 1:
			lat++;
			break;

		case 2:
			lng--;
			break;

		case 3:
			lng++;
			break;

		default:
			throw new IllegalArgumentException("Movimento invalido");
		}
		return new int[] { lat, lng };
	}

	/**
	 * Analisa o mapa para ver esta nas bordas para n�o dar erro e penaliza o agente
	 * por sair do mapa
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
		case 4:// o agente n�o se mexeou ficou parado
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
