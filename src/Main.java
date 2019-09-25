import java.util.Scanner;

   /*
        Generating all the 8 successor of this cell

            N.W   N   N.E
              \   |   /
               \  |  /
            W----Cell----E
                 / | \
               /   |  \
            S.W    S   S.E

        Cell-->Popped Cell (i, j)
        N -->  North       (i-1, j)
        S -->  South       (i+1, j)
        E -->  East        (i, j+1)
        W -->  West           (i, j-1)
        N.E--> North-East  (i-1, j+1)
        N.W--> North-West  (i-1, j-1)
        S.E--> South-East  (i+1, j+1)
        S.W--> South-West  (i+1, j-1)

   */

   /*
     Parametros do arquivo de entrada:

     1º linha = tamanho da matriz

     Legenda:
         Caminho nao percorrido = 0
         Parede = 1
         Caminho ja percorrido = 3
         Player = E
         Saida = S
         Entrada = sempre no [0,0]
    */


public class Main {


    private static Scanner scanner;

    public static void main(String[] args) {
        setup();
        try {
            while(scanner.hasNext()){
                String s = scanner.nextLine();
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public static void setup(){
        Reader reader = new Reader();
        scanner = reader.setupFilePath("labirinto1_10.txt");
    }
}
