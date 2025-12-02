public class Main {
    public static void main(String[] args) throws Exception {

        GerenciadorComandos ger = new GerenciadorComandos();
        ReconhecedorVoz reconhecedorVoz = new ReconhecedorVoz(ger);

        try {
            reconhecedorVoz.iniciar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
