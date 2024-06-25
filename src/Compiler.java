import java.util.ArrayList;

public class Compiler {

	public static String fileName = "/src/Teste_Fonte.txt";

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(fileName);
		int counter = 0;
		ArrayList<Token> arrayOfTokens = new ArrayList<>();
		Node.NodePrograma programaAST = null;

		// Análise léxica
		try {
			do {
				Token currentToken = scanner.scan();
				arrayOfTokens.add(counter, currentToken);

				counter++;
			} while (!scanner.isEOF());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		for(int i = 0; i < arrayOfTokens.size(); i++ ) {
			if(arrayOfTokens.get(i).kind == Token.ERRO) {
				Token token = arrayOfTokens.get(i);
				new Erro(
					"\n" + "Token \"" + token.grafia + "\" inválido" + "\n" + 
					"Linha: " + token.linha + " Coluna: " + token.coluna + "\n"
				);
			}
		}

		// Análise Sintática
		Parser sintaticParser = new Parser(arrayOfTokens);
		programaAST = sintaticParser.parse();

		// Printagem da AST (Árvore sintática abstrata)	
		System.out.println("");
		Printer printer = new Printer();
		printer.print(programaAST);

		// Análise de contexto
		Checker checker = new Checker();
		checker.check(programaAST);

		// Geração de código
		Coder Coder = new Coder();
		Coder.openFile("src/Teste_Objeto.txt");
		Coder.printCode(programaAST);
		Coder.closeFile();
	}
}