public class Token {
    
    public byte kind;
    public String grafia;
    public int linha;
    public int coluna;

    // Se kind for reconhecido e tiver uma palavra reservada, o tipo do token passa a ser kind
    public Token (byte kind, String grafia, int linha, int coluna) {
        this.kind = kind;
        this.grafia = grafia;
        this.linha = linha;
        this.coluna = coluna;

        if (kind == IDENTIFIER) {
            for(byte i = BEGIN; i <= END; i++) {
                if(grafia.equals(grafias[i])) {
                    this.kind = i;
                    break;
                }
            }
        }
    }

    // Isso aqui são constantes para identificar cada token
    public final static byte 
        IDENTIFIER = 0, INTLITERAL = 1, BOOLLITERAL = 2,
        BEGIN = 3, DO = 4, ELSE = 5, END = 6, IF = 7, 
        IN = 8, LET = 9, THEN = 10, VAR = 11,
        WHILE = 12, SEMICOLON = 13, COLON = 14,
  
        BECOMES = 15, IS = 16, LPAREN = 17,
        RPAREN = 18, EOT = 19, EXCLAMATION = 20,
  
        SOMA = 21, SUB = 22, MULT = 23,
        DIV = 24, MENOR = 25, MAIOR = 26,
        IGUAL = 27,

        SIMPLETYPE = 28, PROGRAM = 29, ERRO = 30,
        COMMA = 31;

    // Essa parte é a grafia correspondente a cada token, na mesma ordem acima
    private final static String[] grafias = {
        "<identifier>", "<integer-literal>", "<integer-boolean>",
        "begin", "do", "else", "end", "if",
        "in", "let", "then", "var",
        "while", ";", ":",

        ":=", "~", "(",
        ")", "<eot>", "!",

        "+", "-", "*",
        "/", "<", ">",
        "=",

        "<tipo-simples>", "program", "<erro>",
        ","
    };

}