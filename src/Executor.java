import java.io.IOException;

public class Executor {

    public static void exec(String comando) {

        try {
            Runtime.getRuntime().exec(comando);

            System.out.println("caminho: " + comando);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
