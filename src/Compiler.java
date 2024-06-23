import java.util.ArrayList;

public class Compiler {

	public static String fileName = "/src/Teste.txt";

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(fileName);
		int counter = 0;
		ArrayList<Token> arrayOfTokens = new ArrayList<>();

		// Análise léxica
		try {
			do {
				Token currentToken = scanner.scan();
				arrayOfTokens.add(counter, currentToken);

				counter++;
			} while (scanner.isEOF() == false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		for(int i = 0; i < arrayOfTokens.size(); i++ ) {
			if(arrayOfTokens.get(i).kind == Token.ERRO) {
				Token token = arrayOfTokens.get(i);
				new Erro(
					"\n" + "Token " + token.grafia + " inválido" + "\n" + 
					"Linha: " + token.linha + " Coluna: " + token.coluna + "\n"
				);
			}
		}
        System.out.println("yo");
	}
}