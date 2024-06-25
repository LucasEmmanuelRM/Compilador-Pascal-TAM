import java.util.ArrayList;








public class Parser {
  private int currentTokenId;

  private int currentIndex;
  private ArrayList<Token> arrayOfTokens;

  public Parser(ArrayList<Token> arrayList) {
    this.currentIndex = 0;
    this.arrayOfTokens = arrayList;

    this.currentTokenId = this.arrayOfTokens.get(currentIndex).kind;
  }

  private void accept(int tokenId) {

    if (tokenId == currentTokenId) {
      currentIndex++;
      if (this.arrayOfTokens.size() > currentIndex) {
        currentTokenId = this.arrayOfTokens.get(currentIndex).kind;
      }
    } else {
      new Erro(
        "Símbolo não aceito: " + arrayOfTokens.get(currentIndex).grafia + "\n"
        + "Linha: " + arrayOfTokens.get(currentIndex).linha
        + " Coluna: " + arrayOfTokens.get(currentIndex).coluna
      );
    }
  }

  private void acceptIt() {

    currentIndex++;
    if (this.arrayOfTokens.size() > currentIndex) {
      currentTokenId = this.arrayOfTokens.get(currentIndex).kind;
    }
  }

  // Regras da gramática LL1 como métodos
  private Node.NodeComandoAtribuicao parse_atribuicao() {
    Node.NodeComandoAtribuicao comandoAtribuicao = new Node.NodeComandoAtribuicao(this.arrayOfTokens.get(currentIndex));

    comandoAtribuicao.variavel = parse_variavel();
    accept(Token.BECOMES);
    comandoAtribuicao.expressao = parse_expressao();
    accept(Token.SEMICOLON);

    return comandoAtribuicao;
  }

  private Node.NodeComando parse_comando() {
    Node.NodeComando comando;

    switch (currentTokenId) {
      case Token.IDENTIFIER:
        comando = parse_atribuicao();
        break;
      case Token.IF:
        comando = parse_condicional();
        break;
      case Token.WHILE:
        comando = parse_iterativo();
        break;
      case Token.BEGIN:
        comando = parse_comandoComposto();
        break;
      default:
        new Erro(
          "Comando que começa com \"" + 
          arrayOfTokens.get(currentIndex).grafia + 
          "\" não identificado" 
          + "Linha: " + arrayOfTokens.get(currentIndex).linha 
      + " Coluna: " + arrayOfTokens.get(currentIndex).coluna
        );
        comando = null;
    }
    return comando;
  }

  private Node.NodeComandoComposto parse_comandoComposto() {
    Node.NodeComandoComposto comandoComposto = new Node.NodeComandoComposto(arrayOfTokens.get(currentIndex));

    accept(Token.BEGIN);
    comandoComposto.comandos = parse_listaDeComandos();
    accept(Token.END);

    return comandoComposto;
  }

  private Node.NodeComandoCondicional parse_condicional() {
    Node.NodeComandoCondicional condicional = new Node.NodeComandoCondicional(this.arrayOfTokens.get(currentIndex));

    accept(Token.IF);
    condicional.expressao = parse_expressao();
    accept(Token.THEN);
    condicional.comando1 = parse_comando();

    condicional.comando2 = null;

    if (currentTokenId == Token.ELSE) {
      acceptIt();
      condicional.comando2 = parse_comando();
    }
    return condicional;
  }

  private Node.NodeCorpo parse_corpo() {
    Node.NodeCorpo corpo = new Node.NodeCorpo();

    corpo.declaracoes = parse_declaracoes();
    corpo.comandoComposto = parse_comandoComposto();

    return corpo;
  }

  private Node.NodeDeclaracao parse_declaracao() {
    Node.NodeDeclaracao declaracao = new Node.NodeDeclaracao();
    declaracao.declaracaoDeVariavel = parse_declaracaoDeVariavel();

    return declaracao;
  }

  private Node.NodeDeclaracaoDeVariavel parse_declaracaoDeVariavel() {
    Node.NodeDeclaracaoDeVariavel declaracaoDeVariavel = new Node.NodeDeclaracaoDeVariavel();

    accept(Token.VAR);
    declaracaoDeVariavel.IDs = parse_listaDeIds();
    accept(Token.COLON);
    declaracaoDeVariavel.tipo = parse_tipo();

    return declaracaoDeVariavel;
  }

  private Node.NodeDeclaracoes parse_declaracoes() {
    Node.NodeDeclaracoes declaracoes = new Node.NodeDeclaracoes();
    declaracoes.declaracoes = new ArrayList<>();

    while (currentTokenId == Token.VAR) {
      declaracoes.declaracoes.add(parse_declaracao());
      accept(Token.SEMICOLON);
    }

    return declaracoes;
  }

  private Node.NodeExpressao parse_expressao() {
    Node.NodeExpressao expressao = new Node.NodeExpressao(this.arrayOfTokens.get(currentIndex));

    expressao.expressaoSimples1 = parse_expressaoSimples();
    expressao.operadorRelacional = null;
    expressao.expressaoSimples2 = null;

    if (currentTokenId == Token.MENOR || currentTokenId == Token.IGUAL || currentTokenId == Token.MAIOR) {

      switch (currentTokenId) {
        case Token.MENOR:
            Node.NodeOperadorRelacional operadorRelacionalMenor = new Node.NodeOperadorRelacional
            (arrayOfTokens.get(currentIndex).grafia, Token.MENOR);
            expressao.operadorRelacional = operadorRelacionalMenor;
            break;
    
        case Token.IGUAL:
            Node.NodeOperadorRelacional operadorRelacionalIgual = new Node.NodeOperadorRelacional
            (arrayOfTokens.get(currentIndex).grafia, Token.IGUAL);
            expressao.operadorRelacional = operadorRelacionalIgual;
            break;
    
        case Token.MAIOR:
            Node.NodeOperadorRelacional operadorRelacionalMaior = new Node.NodeOperadorRelacional
            (arrayOfTokens.get(currentIndex).grafia, Token.MAIOR);
            expressao.operadorRelacional = operadorRelacionalMaior;
            break;

        default:
            break;
      }

      acceptIt();

      expressao.expressaoSimples2 = parse_expressaoSimples();
    }

    return expressao;
  }

  private Node.NodeExpressaoSimples parse_expressaoSimples() {
    Node.NodeExpressaoSimples expressaoSimples = new Node.NodeExpressaoSimples();
    expressaoSimples.termo = parse_termo();
    expressaoSimples.operadoresAditivos = new ArrayList<Node.NodeOperadorAditivo>();
    expressaoSimples.termos = new ArrayList<Node.NodeTermo>();

    while (currentTokenId == Token.SOMA || currentTokenId == Token.SUB) {

      switch(currentTokenId){
        case Token.SOMA:
            Node.NodeOperadorAditivo operadorAditivo = new Node.NodeOperadorAditivo
            (arrayOfTokens.get(currentIndex).grafia, Token.SOMA);
            expressaoSimples.operadoresAditivos.add(operadorAditivo);
            break;

        case Token.SUB:
            Node.NodeOperadorAditivo operadorSubtrativo = new Node.NodeOperadorAditivo
            (arrayOfTokens.get(currentIndex).grafia, Token.SUB);
            expressaoSimples.operadoresAditivos.add(operadorSubtrativo);
            break;

        default:
            break;
      }

      acceptIt();

      expressaoSimples.termos.add(parse_termo());
    }

    return expressaoSimples;
  }

  private Node.NodeFator parse_fator() {
    Node.NodeFator fator = null;
    Type tipo;
    Node.NodeLiteral aux2;
    
    switch (currentTokenId) {
      case Token.IDENTIFIER:
        Node.NodeVariavel aux1 = new Node.NodeVariavel(
          new Node.NodeID(
            this.arrayOfTokens.get(currentIndex).grafia,
            this.arrayOfTokens.get(currentIndex)
          ),
          this.arrayOfTokens.get(currentIndex)
        );
        fator = aux1;

        acceptIt();
        break;

      case Token.INTLITERAL:
        tipo = new Type(Type.INT);
        aux2 = new Node.NodeLiteral(arrayOfTokens.get(currentIndex).grafia, tipo);
        fator = aux2;

        acceptIt();
        break;
        
      case Token.BOOLLITERAL:
        tipo = new Type(Type.BOOL);
        aux2 = new Node.NodeLiteral(arrayOfTokens.get(currentIndex).grafia, tipo);
        fator = aux2;

        acceptIt();
        break;

      case Token.LPAREN:
        acceptIt();

        Node.NodeExpressao expressao = new Node.NodeExpressao(this.arrayOfTokens.get(currentIndex));
        expressao = parse_expressao();

        accept(Token.RPAREN);

        fator = expressao;
        break;

      default:
        new Erro(
          "Símbolo \"" + arrayOfTokens.get(currentIndex).grafia + "\" não identificado"  + "\n" + 
          "Linha: " + arrayOfTokens.get(currentIndex).linha + " " + 
          "Coluna: " + arrayOfTokens.get(currentIndex).coluna
        );
    }
    return fator;
  }

  private Node.NodeComandoIterativo parse_iterativo() {
    Node.NodeComandoIterativo comandoIterativo = new Node.NodeComandoIterativo(
      arrayOfTokens.get(currentIndex)
    );

    accept(Token.WHILE);
    comandoIterativo.expressao = parse_expressao();
    accept(Token.DO);
    comandoIterativo.comando = parse_comando();

    return comandoIterativo;
  }

  private ArrayList<Node.NodeComando> parse_listaDeComandos() {
    ArrayList<Node.NodeComando> comandos = new ArrayList<>();

    while (
      currentTokenId == Token.IDENTIFIER ||
      currentTokenId == Token.IF ||
      currentTokenId == Token.WHILE ||
      currentTokenId == Token.BEGIN
    ) {
      comandos.add(parse_comando());
    }

    return comandos;
  }

  private ArrayList<Node.NodeID> parse_listaDeIds() {
    ArrayList<Node.NodeID> IDs = new ArrayList<>();

    Node.NodeID ID_aux1 = new Node.NodeID(
      this.arrayOfTokens.get(currentIndex).grafia,
      this.arrayOfTokens.get(currentIndex)
    );
    ID_aux1.valor = arrayOfTokens.get(currentIndex).grafia;
    IDs.add(ID_aux1);
    
    accept(Token.IDENTIFIER);

    while (currentTokenId == Token.COMMA) {
      acceptIt();

      Node.NodeID ID_aux2 = new Node.NodeID(
        this.arrayOfTokens.get(currentIndex).grafia, 
        this.arrayOfTokens.get(currentIndex)
      );
      ID_aux2.valor = arrayOfTokens.get(currentIndex).grafia;
      IDs.add(ID_aux2);

      accept(Token.IDENTIFIER);
    }

    return IDs;
  }

  private Node.NodePrograma parse_programa() {
    Node.NodePrograma programaAST = new Node.NodePrograma();

    accept(Token.PROGRAM);
    programaAST.id = new Node.NodeID(
      this.arrayOfTokens.get(currentIndex).grafia,
      this.arrayOfTokens.get(currentIndex)
    );
    programaAST.id.valor = arrayOfTokens.get(currentIndex).grafia;
    accept(Token.IDENTIFIER);

    accept(Token.SEMICOLON);
    programaAST.corpo = parse_corpo();
    accept(Token.PERIOD);

    return programaAST;
  }

  private Node.NodeTermo parse_termo() {
    Node.NodeTermo termo = new Node.NodeTermo();
    termo.fatores = new ArrayList<Node.NodeFator>();
    termo.operadoresMultiplicativos = new ArrayList<Node.NodeOperadorMultiplicativo>();

    termo.fator = parse_fator();
    while (currentTokenId == Token.MULT || currentTokenId == Token.DIV) {

      switch(currentTokenId){
        case Token.MULT:
            Node.NodeOperadorMultiplicativo operadorMultiplicativo = new Node.NodeOperadorMultiplicativo
            (arrayOfTokens.get(currentIndex).grafia, Token.MULT);
            termo.operadoresMultiplicativos.add(operadorMultiplicativo);
            break;

        case Token.DIV:
            Node.NodeOperadorMultiplicativo operadorDivisor = new Node.NodeOperadorMultiplicativo
            (arrayOfTokens.get(currentIndex).grafia, Token.MULT);
            termo.operadoresMultiplicativos.add(operadorDivisor);
            break;
        
        default:
            break;
      }

      acceptIt();

      termo.fatores.add(parse_fator());
    }
    return termo;
  }

  private Node.NodeTipo parse_tipo() {
    Node.NodeTipo tipo = new Node.NodeTipo();

    String tipoString = arrayOfTokens.get(currentIndex).grafia;
    Type tipoType = Type.evaluateString(tipoString);
    tipo.tipoSimples = new Node.NodeTipoSimples(tipoString, tipoType);

    accept(Token.SIMPLETYPE);
    return tipo;
  }

  private Node.NodeVariavel parse_variavel() {
    
    Node.NodeID ID = new Node.NodeID(
      this.arrayOfTokens.get(currentIndex).grafia,
      this.arrayOfTokens.get(currentIndex)
    );
    ID.valor = arrayOfTokens.get(currentIndex).grafia;

    Node.NodeVariavel variavel = new Node.NodeVariavel(
      ID, arrayOfTokens.get(currentIndex)
    );

    accept(Token.IDENTIFIER);

    return variavel;
  }

  public Node.NodePrograma parse() {
    return parse_programa();
  }
}