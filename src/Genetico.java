import java.util.Random;
public class Genetico {
    public final static int SIZE = 50; // total de cargas
    public final static int TAM = 100; // tamanho da populaçao: quantidade de soluçoes USAR NUMERO PAR PASOKASPOKASPO
    public final static int MAX = 10000; // numero maximo de geraçoes (iteraçoes)

    // tirar essa main dps(mandar pro simulador bombar

    public void algoritmoGenetico(int[][] lab1) {

        //int[] cargas = GeraCargas(); // cargas definidas em aula
        int[][] populacao = new int[TAM][SIZE + 1]; // populaçao atual: contem os cromossomos (soluçoes candidatas)
        int[][] populacaoIntermediaria = new int[TAM][SIZE + 1]; // populaçao intermediaria: corresponde a populaçao em
        // construçao
        // Obs: A ultima coluna de cada linha da matriz e para
        // armazenar o valor da funçao de aptidao,
        // que indica o quao boa eh a soluçao

        // ===========> Ciclo do AG
        System.out.println("=================================================================");
        System.out.println("Encontrando a melhor distribuiçao usando Algoritmos Geneticos ...");
        System.out.println("=================================================================");
        inicializaPopulacao(populacao); // cria soluçoes aleatoriamente

        for (int geracao = 0; geracao < MAX; geracao++) {
            System.out.println("Geraçao:" + geracao);
            calculaFuncoesDeAptidao(populacao); //APTIDAO
            int melhor = pegaAltaTerra(populacao, populacaoIntermediaria); // ELITISMO
            if (populacaoIntermediaria[0][SIZE] == 99999) {
                printaMatriz(populacao);
                System.out.println(">>>> Achou a melhor distribuiçao: " + melhor);
                //GG 
                break;
            }
            printaMatriz(populacao);
            crossOver(populacaoIntermediaria, populacao);//CROSSOVER
            if (geracao % 2 == 0)
                mutacao(populacaoIntermediaria);//MUTACAO
            populacao = populacaoIntermediaria;
        }
    }
}