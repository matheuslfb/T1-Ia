package br.pucrs.classes;

import java.util.Random;

public class AlgoritmoGenetico {

	public final static int SIZE = 50; // total de cargas
	public final static int TAM = 100; // tamanho da popula�ao: quantidade de solu�oes
	public final static int MAX = 10000; // numero maximo de gera�oes (itera�oes)

	public void algoritmoGenetico() {

		// int[] cargas = GeraCargas(); // cargas definidas em aula
		int[][] populacao = new int[TAM][SIZE + 1]; // popula�ao atual: contem os cromossomos (solu�oes candidatas)
		int[][] populacaoIntermediaria = new int[TAM][SIZE + 1]; // popula�ao intermediaria: corresponde a popula�ao em
																	// constru�ao
																	// Obs: A ultima coluna de cada linha da matriz e para armazenar o valor da
																	// fun�ao de aptidao,
																	// que indica o quao boa eh a solu�ao

		// ===========> Ciclo do AG
		System.out.println("=================================================================");
		System.out.println("Encontrando a melhor distribui��o usando Algoritmos Geneticos ...");
		System.out.println("=================================================================");
		inicializaPopulacao(populacao); // cria soluçoes aleatoriamente

		for (int geracao = 0; geracao < MAX; geracao++) {
			System.out.println("Geraçao:" + geracao);
			calculaFuncoesDeAptidao(populacao); // APTIDAO
			int melhor = pegaAltaTerra(populacao, populacaoIntermediaria); // ELITISMO
			if (populacaoIntermediaria[0][SIZE] == 99999) {
				printaMatriz(populacao);
				System.out.println("Achou a melhor distribui��o: " + melhor);
				// GG
				break;
			}
			printaMatriz(populacao);
			crossOver(populacaoIntermediaria, populacao);// CROSSOVER
			if (geracao % 2 == 0)
				mutacao(populacaoIntermediaria);// MUTACAO
			populacao = populacaoIntermediaria;
		}
	}

	private static void printaMatriz(int[][] populacao) {
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
	private static void inicializaPopulacao(int[][] populacao) {
		for (int i = 0; i < populacao.length; i++) {
			for (int j = 0; j < populacao[i].length - 1; j++) {
				populacao[i][j] = randomFill();
			}
		}
	}

	private static void calculaFuncoesDeAptidao(int[][] populacao) {
		for (int i = 0; i < populacao.length; i++) {
			populacao[i][50] = funcaoDeAptidao(populacao[i]);
		}
	}

	/**
	 * Sele��o por elitismo. Encontra a melhor solu��o e copia para a popula��o
	 * intermediaria
	 */
	private static int pegaAltaTerra(int[][] populacao, int[][] populacaoIntermediaria) {
		int highlander = 0;
		int menor = populacao[0][SIZE];

		for (int i = 1; i < populacao.length; i++) {
			if (populacao[i][SIZE] > menor) {
				menor = populacao[i][SIZE];
				highlander = i;
			}
		}
		System.out.println("Sele��o por elitismo - melhor dessa gera��o: " + highlander);

		for (int i = 0; i < SIZE + 1; i++) {
			populacaoIntermediaria[0][i] = populacao[highlander][i];
		}
		return highlander;
	}

	/**
	 * Sele��o por torneio. Escolhe cromossomo (solu��o) para cruzamento
	 * ******************************************************* ***************
	 */
	private static int[] torneio(int[][] populacao) {
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
	private static void crossOver(int[][] intermediaria, int[][] populacao) {
		int[] pai;
		int[] pai2;
		int corte = SIZE / 2; // tam/2
		int linha = 1;
		for (int i = 0; i < TAM; i = i + 2) {
			do {
				pai = torneio(populacao);
				pai2 = torneio(populacao);
			} while (pai == pai2);
			System.out.println("Gerando dois filhos...");
			for (int j = 0; j < corte; j++) {
				intermediaria[linha][j] = pai[j];
				intermediaria[linha + 1][j] = pai2[j];
			}
			for (int j = corte; j < SIZE; j++) {
				intermediaria[linha][j] = pai2[j];
				intermediaria[linha + 1][j] = pai[j];
			}
			linha++;
		}
	}

	/**
	 * Muta��o
	 * 
	 */
	private static void mutacao(int[][] intermediaria) {
		Random r = new Random();
		// System.out.println("Tentando mutacao");

		for (int cont = 1; cont <= 2; cont++) {

			int linha = r.nextInt(TAM);
			int coluna = r.nextInt(SIZE);

			intermediaria[linha][coluna] = randomFill(intermediaria[linha][coluna]);

			System.out.println("Mutou o cromossomo : " + linha);
		}

	}

	/**
	 * Fun��o de aptidao: heuristica que estima a qualidade de uma solu��o
	 */
	private static int funcaoDeAptidao(int[] individuo) {
		int score = 0;
		int[][] mapa = aptStart(); // recebe mapa novo

		int[] novoStart = getPositionStart(mapa);
		int tempLat = novoStart[1];
		int tempLng = novoStart[0];

		mapa = spawn(tempLat, tempLng, mapa);

		for (int i = 0; i < individuo.length - 1; i++) {

			int[] latlng = getPosition(mapa);
			int la = latlng[1];
			int lo = latlng[0];

			// individuo[i] = movimento
			int movement = individuo[i];
			score += moveIt(la, lo, movement, mapa);

		}
		return score;
	}

	private static int randomFill() {// usado para gerar pops

		Random rand = new Random();
		int randomNum = rand.nextInt(4);
		return randomNum;
	}

	private static int randomFill(int val) {// usado pra mutacao
		int randomNum;
		do {
			Random rand = new Random();
			randomNum = rand.nextInt(3);
		} while (randomNum == val);
		return randomNum;
	}

	private static int moveIt(int lat, int lng, int movement, int[][] mapa) {

		int[] latLngNovo = getNewLatLong(lat, lng, movement);
		int score = analize(latLngNovo[1], latLngNovo[0], mapa);
		ModificaMapa(lat, lng, latLngNovo[1], latLngNovo[0], mapa);
		return score;
	}

	private static int[][] ModificaMapa(int lat, int lng, int latNovo, int lngNovo, int[][] mapa) {
		// SE a posicao nova esta dentro do mapa e nao eh uma parede (anda)
		if (latNovo >= 0 && lngNovo >= 0 && latNovo < mapa.length && lngNovo < mapa.length
				&& mapa[lngNovo][latNovo] != 1) {
			mapa[lat][lng] = 3;
			mapa[latNovo][lngNovo] = 4;
		}
		return mapa;
	}

	private static int[] getNewLatLong(int lat, int lng, int movement) {
		switch (movement) {
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

	private static int analize(int lat, int lng, int[][] mapa) {
		// se estiver nas bordas nem procura pra nao dar erro
		if (lat < 0 || lng < 0 || lat > 9 || lng > 9) {
			return score(-1);
		} else {
			return score(mapa[lng][lat]);
		}
	}

	private static int score(int novaPosicao) {
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
			retorno = 99999; // terminou
			break;

		case 4:
			retorno = 0;
			// System.out.println("Sem movimentacao");
			break;

		default:
			throw new IllegalArgumentException("Argumento inv�lido");

		}
		return retorno;
	}

	private static int[][] spawn(int tempLat, int tempLng, int[][] mapa) {
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa.length; j++) {
				if (j == tempLat && i == tempLng) {
					mapa[i][j] = 4;
				}
			}
		}
		return mapa;
	}

	private static int[][] aptStart() {
		int[][] mapa = Gerador.labZero();
		return mapa;
	}

	private static int[] getPosition(int[][] mapa) {
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa.length; j++) {
				if (mapa[i][j] == 4) {
					return new int[] { j, i };
				}
			}
		}
		return null;

	}

	private static int[] getPositionStart(int[][] mapa) {
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
