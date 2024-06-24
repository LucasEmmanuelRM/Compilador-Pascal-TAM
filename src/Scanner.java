import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {
    private BufferedReader fileReader;
    private char charCorrente;
	private int counterToMarkCharacter;

    private byte currentKind;
    private StringBuffer grafiaCorrente;

    private int linhaCorrente = 1;
    private int colunaCorrente = 0;

    public Scanner(String pathToFile) {
		counterToMarkCharacter = 0;
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
		fileReader.mark(counterToMarkCharacter);
		counterToMarkCharacter++;

		if(fileReader.read() != -1) {
			fileReader.reset();
			return false;
		}
		return true;
	}

    public void getNextCaracter() {
        try {
            int character = fileReader.read();
            if (character != -1) {
                this.charCorrente = (char) character;
                colunaCorrente++;
            } else {
                charCorrente = '\000';  // End of file character
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            charCorrente = '\000';  // Handle IO exception by setting to EOF
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
        return caracter >= '0' && caracter <= '9';
    }

    protected boolean isLetter(char caracter) {
        return (caracter >= 'a' && caracter <= 'z') || (caracter >= 'A' && caracter <= 'Z');
    }

    protected boolean isSimpleType(String word) {
        switch (word) {
            case "integer":
            case "boolean":
                return true;
            default:
                return false;
        }
    }

    private byte scanToken() {
        if (isLetter(charCorrente)) {
            takeIt();
            while (isLetter(charCorrente) || isDigit(charCorrente)) {
                takeIt();
            }
            switch (grafiaCorrente.toString()) {
                case "program": return Token.PROGRAM;
                case "true":
                case "false": return Token.BOOLLITERAL;
                case "or": return Token.SOMA;
                case "and": return Token.MULT;
                case "while": return Token.WHILE;
                case "var": return Token.VAR;
                case "then": return Token.THEN;
                case "let": return Token.LET;
                case "in": return Token.IN;
                case "if": return Token.IF;
                case "end": return Token.END;
                case "else": return Token.ELSE;
                case "do": return Token.DO;
                case "begin": return Token.BEGIN;
                default: if (isSimpleType(grafiaCorrente.toString())) {
                    return Token.SIMPLETYPE;
                }
                return Token.IDENTIFIER;
            }
        }

        if (isDigit(charCorrente)) {
            takeIt();
            while (isDigit(charCorrente)) {
                takeIt();
            }
            return Token.INTLITERAL;
        }

        switch (charCorrente) {
            case ':':
                takeIt();
                if (charCorrente == '=') {
                    takeIt();
                    return Token.BECOMES;
                } else {
                    return Token.COLON;
                }
            case ';': takeIt(); return Token.SEMICOLON;
            case '(': takeIt(); return Token.LPAREN;
            case ')': takeIt(); return Token.RPAREN;
            case '!': takeIt(); return Token.EXCLAMATION;
            case '+': takeIt(); return Token.SOMA;
            case '-': takeIt(); return Token.SUB;
            case '*': takeIt(); return Token.MULT;
            case '/': takeIt(); return Token.DIV;
            case '<': takeIt(); return Token.MENOR;
            case '>': takeIt(); return Token.MAIOR;
            case '=': takeIt(); return Token.IGUAL;
            case ',': takeIt(); return Token.COMMA;
            case '.': takeIt(); return Token.PERIOD;
            case '\000': takeIt(); return Token.EOT;
            default: break;
        }

        takeIt();
        return Token.ERRO;
    }

    private boolean scanSeparator(){
        switch (charCorrente) {
            case '\n':
                takeIt();
                linhaCorrente++;
                colunaCorrente = 0;
                return true;
            case '\t':
                takeIt();
                colunaCorrente += 2;
                return true;
            case '!':
                takeIt();
                while (charCorrente != '\n' && charCorrente != '\000') {
                    takeIt();
                }
                return true;
            case ' ':
                takeIt();
                return true;
            default:
                return false;
        }
    }

    public Token scan() {
        while(scanSeparator()) {
            // Continuar enquanto houver separadores
        }
        grafiaCorrente = new StringBuffer("");
        int colunaInicial = colunaCorrente;
        currentKind = scanToken();
        // System.out.printf("Scanned token: kind=%d, grafia=%s, line=%d, column=%d\n", currentKind, grafiaCorrente.toString(), linhaCorrente, colunaInicial);
        return new Token(currentKind, grafiaCorrente.toString(), linhaCorrente, colunaInicial);
    }
}