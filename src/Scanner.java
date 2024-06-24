import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {
    private BufferedReader fileReader;
    private char charCorrente;

    private byte currentKind;
    private StringBuffer grafiaCorrente;

    private int linhaCorrente = 1;
    private int colunaCorrente = 0;

    public Scanner(String pathToFile) {
        try {
            String currentDirectory = System.getProperty("user.dir");
            FileReader poorFileReader = new FileReader(currentDirectory + pathToFile);
            fileReader = new BufferedReader(poorFileReader);
            getNextCaracter();  // Initialize charCorrente
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isEOF() throws IOException {
        fileReader.mark(1);
        int readResult = fileReader.read();
        fileReader.reset();
        return readResult == -1;
    }

    public void getNextCaracter() {
        try {
            int character = fileReader.read();
            if (character != -1) {
                charCorrente = (char) character;
                if (charCorrente == '\n') {
                    linhaCorrente++;
                    colunaCorrente = 0;
                } else {
                    colunaCorrente++;
                }
            } else {
                charCorrente = '\000';  // End of file character
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            charCorrente = '\000';  // Handle IO exception by setting to EOF
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
            case '\000': takeIt(); return Token.EOT;
            default: takeIt(); return Token.ERRO;
        }
    }

    private void scanSeparator() {
        while (charCorrente == ' ' || charCorrente == '\n' || charCorrente == '\t' || charCorrente == '!') {
            if (charCorrente == '!') {
                takeIt();
                while (charCorrente != '\n' && charCorrente != '\000') {
                    takeIt();
                }
            } else {
                getNextCaracter();
            }
        }
    }

    public Token scan() {
        scanSeparator();
        grafiaCorrente = new StringBuffer();
        currentKind = scanToken();
        return new Token(currentKind, grafiaCorrente.toString(), linhaCorrente, colunaCorrente);
    }
}