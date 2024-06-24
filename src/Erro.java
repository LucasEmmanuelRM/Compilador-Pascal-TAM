public class Erro {

    public Erro(String message) {
        try {
            throw new Exception(message);
        } catch (Exception e) {
            System.out.println("\nERRO: " + e.getMessage());
        }
    }

}