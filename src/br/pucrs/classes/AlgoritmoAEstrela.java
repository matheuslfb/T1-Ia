package br.pucrs.classes;

import java.util.ArrayList;

public class AlgoritmoAEstrela {

	private ArrayList<Nodo> adjacentes;
	private ArrayList<Nodo> visitados;
	private Nodo nodoInicial;
	private Nodo nodoFinal;

	public AlgoritmoAEstrela() {
		adjacentes = new ArrayList<Nodo>();
		visitados = new ArrayList<Nodo>();
	}

	public void buscaCaminhoOtimoAEstrela(int[][] matriz) throws InterruptedException {
		carregaVariaveis(matriz);

		visitados.add(nodoInicial);

		insereAdjacentes(matriz, nodoInicial);
		Nodo nodoAtual = nodoInicial;
		while (!nodoAtualIsNodoFinal(nodoFinal, nodoAtual)) {
			for (Nodo nodo : adjacentes) {
				nodo = heuristica(nodo, nodoFinal);
			}
			nodoAtual = findNodoMaisProximoDadaHeuristica();
			visitados.add(nodoAtual);
			adjacentes.remove(nodoAtual);
			insereAdjacentes(matriz, nodoAtual);
			for (Nodo nodo : adjacentes) {
				if (getValorDoNodoNaMatriz(nodo, matriz) == 9)
					nodoAtual = nodo;
			}
			printMatriz(matriz);
			Thread.sleep(1000);
		}
		while (nodoAtual != null) {
			matriz[nodoAtual.getX()][nodoAtual.getY()] = 2;
			nodoAtual = nodoAtual.getNodoPai();
		}
	}

	private void printMatriz(int[][] matriz) {
		int[][] newMatriz = matriz;

		for (Nodo nodo : adjacentes) {
			newMatriz[nodo.getX()][nodo.getY()] = 3;
		}
		for (Nodo nodo : visitados) {
			newMatriz[nodo.getX()][nodo.getY()] = 2;
		}

		for (int i = 0; i < newMatriz.length; i++) {
			for (int j = 0; j < newMatriz[i].length; j++) {
				System.out.print(newMatriz[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

	private boolean nodoAtualIsNodoFinal(Nodo nodoFinal, Nodo nodoAtual) {
		return (nodoAtual.getX() == nodoFinal.getX()) && (nodoAtual.getY() == nodoFinal.getY());
	}

	private Nodo findNodoMaisProximoDadaHeuristica() {
		Nodo menorF = adjacentes.get(0);
		for (Nodo adjacente : adjacentes) {
			if (menorF.getFuncao() < adjacente.getFuncao()) {
				menorF = adjacente;
			}
		}
		return menorF;
	}

	private int getValorDoNodoNaMatriz(Nodo nodo, int[][] matriz) {
		return matriz[nodo.getX()][nodo.getY()];
	}

	private void insereAdjacentes(int[][] matriz, Nodo nodoPai) {
		if (nodoPai.getX() + 1 < matriz.length)
			if (matriz[nodoPai.getX() + 1][nodoPai.getY()] != 1
					&& !isNodoInVisitados(nodoPai.getX() + 1, nodoPai.getY()))
				adjacentes.add(new Nodo(nodoPai.getX() + 1, nodoPai.getY(), nodoPai));

		if (nodoPai.getX() - 1 >= 0)
			if (matriz[nodoPai.getX() - 1][nodoPai.getY()] != 1
					&& !isNodoInVisitados(nodoPai.getX() - 1, nodoPai.getY()))
				adjacentes.add(new Nodo(nodoPai.getX() - 1, nodoPai.getY(), nodoPai));

		if (nodoPai.getY() + 1 < matriz[0].length)
			if (matriz[nodoPai.getX()][nodoPai.getY() + 1] != 1
					&& !isNodoInVisitados(nodoPai.getX(), nodoPai.getY() + 1))
				adjacentes.add(new Nodo(nodoPai.getX(), nodoPai.getY() + 1, nodoPai));

		if (nodoPai.getY() - 1 >= 0)
			if (matriz[nodoPai.getX()][nodoPai.getY() - 1] != 1
					&& !isNodoInVisitados(nodoPai.getX(), nodoPai.getY() - 1))
				adjacentes.add(new Nodo(nodoPai.getX(), nodoPai.getY() - 1, nodoPai));
	}

	private Nodo heuristica(Nodo nodo1, Nodo nodo2) {
		nodo1.setFuncao(Math.abs(nodo1.getX() - nodo2.getX()) + Math.abs(nodo1.getY() - nodo2.getY()));
		return nodo1;
	}

	private boolean isNodoInVisitados(int x, int y) {
		for (Nodo nodo : visitados) {
			if (nodo.getX() == x && nodo.getY() == y)
				return true;
		}
		return false;
	}

	private void carregaVariaveis(int[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if (matriz[i][j] == 9)
					nodoInicial = new Nodo(i, j, null);
				if (matriz[i][j] == 8)
					nodoFinal = new Nodo(i, j, null);
			}
		}
	}

}
