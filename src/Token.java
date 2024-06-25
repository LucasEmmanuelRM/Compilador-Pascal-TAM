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
        RPAREN = 18, EOT = 19,
  
        SOMA = 20, SUB = 21, MULT = 22,
        DIV = 23, MENOR = 24, MAIOR = 25,
        IGUAL = 26,

        SIMPLETYPE = 27, PROGRAM = 28, ERRO = 29,
        COMMA = 30, PERIOD = 31;

    // Essa parte é a grafia correspondente a cada token, na mesma ordem acima
    private final static String[] grafias = {
        "<identifier>", "<integer-literal>", "<integer-boolean>",
        "begin", "do", "else", "end", "if",
        "in", "let", "then", "var",
        "while", ";", ":",

        ":=", "~", "(",
        ")", "<eot>",

        "+", "-", "*",
        "/", "<", ">",
        "=",

        "<tipo-simples>", "program", "<erro>",
        ",", "."
    };

}