import java.util.Scanner;

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
