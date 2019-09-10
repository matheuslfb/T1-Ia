import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {


    public String TESTS_PATH = System.getProperty("user.dir") + "/tests/";

    /*Leitura de Arquivo*/
    public Scanner setupFilePath(String filename) {
        try {
            Scanner scanner = new Scanner(new File(TESTS_PATH + filename)).useDelimiter("\n");
            return scanner;

        } catch (FileNotFoundException e) {
            System.out.println("Erro na leitura do arquivo");
        }
        return null;
    }
}
