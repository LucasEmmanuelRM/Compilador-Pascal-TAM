import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {
	public BufferedReader fileReader;

	private int contadorCaractere;
	private char charCorrente;

	private byte currentKind;
	private StringBuffer grafiaCorrente;
        
	private int linhaCorrente = 1;
	private int colunaCorrente;


	public Scanner(String pathToFile) {
		contadorCaractere = 0;
		try {
			String currentDirectory = System.getProperty("user.dir");

			FileReader poorFileReader = new FileReader(
				currentDirectory + pathToFile
			);
			BufferedReader fileReader = new BufferedReader(poorFileReader);
			
			int character;
			// Lê o arquivo caractere por caractere
			if((character = fileReader.read()) != -1) {
				this.charCorrente = (char)character;
			}
			this.fileReader = fileReader;

		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean isEOF() throws IOException {	
		fileReader.mark(contadorCaractere);
		contadorCaractere++;

		if(fileReader.read() != -1) {
			fileReader.reset();
			return false;
		}
		return true;
	}

	public void getNextCaracter() {
		int character;
                
		switch (charCorrente) {
		case '\n':
			linhaCorrente++;
			colunaCorrente = 1;
			break;
		case '\t':
			colunaCorrente += 2;
			break;
		default:
			colunaCorrente++;
			break;
		}
                
		try {
			if((character = fileReader.read()) != -1) {
				this.charCorrente = (char)character;
			}
		}	catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void readFile(String pathToFile) {
		// Cria um objeto FileReader que representa o arquivo que queremos ler
		try {
			String currentDirectory = System.getProperty("user.dir");
      System.out.println("O diretório atual é: " + currentDirectory);

			FileReader fileReader = new FileReader(
				currentDirectory + pathToFile
			);
			int character;
			// Lê o arquivo caractere por caractere
			while ((character = fileReader.read()) != -1) {
				System.out.print((char) character);
			}
			System.out.println();

			fileReader.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void takeIt() {
		grafiaCorrente.append(charCorrente);
		getNextCaracter();
	}

	protected boolean isDigit(char caracter) {
		if(caracter >= '0' && caracter <= '9') {
			return true;
		}
		return false;
	}

	protected boolean isLetter(char caracter) {
		if(
				(caracter >= 'a' && caracter <= 'z')
			) {
			return true;
		}
		return false;
	}

	protected boolean isSimpleType(String word) {
		switch(word) {
			case "integer":
			case "boolean":
				return true;
			default:
				return false;
		}
	} 

	private byte scanToken() {

		if(isLetter(charCorrente)) {
			takeIt();
			while(isLetter(charCorrente) || isDigit(charCorrente)) {
				takeIt();
			}
			if(grafiaCorrente.toString().equals("program")) {
				return Token.PROGRAM;
			}
			if(
				grafiaCorrente.toString().equals("true") || 
				grafiaCorrente.toString().equals("false")) {
					return Token.BOOLLITERAL;
			}
			if(grafiaCorrente.toString().equals("or")) {
				return Token.SOMA;
			}
			if(grafiaCorrente.toString().equals("and")) {
				return Token.MULT;
			}
			if(isSimpleType(grafiaCorrente.toString())) {
				return Token.SIMPLETYPE;
			}
			return Token.IDENTIFIER;
		}
		
		if(isDigit(charCorrente)){
			takeIt();
			while(isDigit(charCorrente)){
				takeIt();
			}
			return Token.INTLITERAL;
		}

		if(charCorrente == ':') {
			takeIt();
			if(charCorrente == '=') {
				takeIt();
				return Token.BECOMES;
			} else {
				return Token.COLON;
			}
		}

		if(charCorrente == ';') {
			takeIt();
			return Token.SEMICOLON;
		}

		if(charCorrente == '(') {
			takeIt();
			return Token.LPAREN;
		}

		if(charCorrente == ')') {
			takeIt();
			return Token.RPAREN;
		}

		if(charCorrente == '!') {
			takeIt();
			return Token.EXCLAMATION;
		}

		if(charCorrente == '+') {
			takeIt();
			return Token.SOMA;
		}

		if(charCorrente == '-') {
			takeIt();
			return Token.SUB;
		}

		if(charCorrente == '*') {
			takeIt();
			return Token.MULT;
		}

		if(charCorrente == '/') {
			takeIt();
			return Token.DIV;
		}

		if(charCorrente == '<') {
			takeIt();
			return Token.MENOR;
		}

		if(charCorrente == '>') {
			takeIt();
			return Token.MAIOR;
		}

		if(charCorrente == '=') {
			takeIt();
			return Token.IGUAL;
		}

		if(charCorrente == '\000') {
			takeIt();
			return Token.EOT;
		}

		takeIt();
		return Token.ERRO;
	}

	private void scanSeparator() {
		switch(charCorrente) {
			case '!':
				takeIt();

				while(charCorrente != '\n') {
					takeIt();
				}
				break;

			case ' ':
			case '\n':
			case '\t':
				takeIt();
				break;
		}
	}

	public Token scan() {
		while(charCorrente == '!' || charCorrente == ' ' || charCorrente == '\n' || charCorrente == '\t') {
			scanSeparator();
		}
		grafiaCorrente = new StringBuffer("");
		currentKind = scanToken();

		return new Token(currentKind, grafiaCorrente.toString(), linhaCorrente, colunaCorrente);
	}
}