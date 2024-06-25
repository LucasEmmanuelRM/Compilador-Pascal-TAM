import java.util.ArrayList;

public class Node {

    public abstract static class nodeAST {}

    public abstract static class NodeComando {
        public Token token;
      
        public NodeComando(Token token) {
          this.token = token;
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeComando(this);
        }
    }


    public static class NodeComandoAtribuicao extends NodeComando {
        public NodeVariavel variavel;
        public NodeExpressao expressao;
      
        public NodeDeclaracaoDeVariavel declaracaoDeVariavel;
      
        public NodeComandoAtribuicao(Token token) {
          super(token);
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeComandoAtribuicao(this);
        }
    }


    public static class NodeComandoComposto extends NodeComando {
        public ArrayList<NodeComando> comandos;
      
        public NodeComandoComposto(Token token) {
          super(token);
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeComandoComposto(this);
        }
    }


    public static class NodeComandoCondicional extends NodeComando {
        public NodeExpressao expressao;
        public NodeComando comando1;
        public NodeComando comando2;
      
        public NodeComandoCondicional(Token token) {
          super(token);
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeComandoCondicional(this);
        }
    }


    public static class NodeComandoIterativo extends NodeComando {
        public NodeExpressao expressao;
        public NodeComando comando;
      
        public NodeComandoIterativo(Token token) {
          super(token);
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeComandoIterativo(this);
        }
    }


    public static class NodeCorpo {
        public NodeDeclaracoes declaracoes;
        public NodeComandoComposto comandoComposto;
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeCorpo(this);
        }
    }


    public static class NodeDeclaracao {
        public NodeDeclaracaoDeVariavel declaracaoDeVariavel;
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeDeclaracao(this);
        }
    }


    public static class NodeDeclaracaoDeVariavel {
        public ArrayList<NodeID> IDs;
        public NodeTipo tipo;
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeDeclaracaoDeVariavel(this);
        }
    }


    public static class NodeDeclaracoes {
        public ArrayList<NodeDeclaracao> declaracoes;
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeDeclaracoes(this);
        }
    }


    public static class NodeExpressao extends NodeFator {
        public NodeExpressaoSimples expressaoSimples1;
        public NodeOperadorRelacional operadorRelacional;
        public NodeExpressaoSimples expressaoSimples2;
        public Token token;
      
        public NodeExpressao(Token token) {
          this.token = token;
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeExpressao(this);
        }

        public Type getType(Visitor visitor) {
          return visitor.getType_NodeExpressao(this);
        }
      }


    public static class NodeExpressaoSimples {
        public NodeTermo termo;
      
        public ArrayList<NodeOperadorAditivo> operadoresAditivos;
        public ArrayList<NodeTermo> termos;
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeExpressaoSimples(this);
        }

        public Type getType(Visitor visitor) {
          return visitor.getType_NodeExpressaoSimples(this);
        }
    }


    public abstract static class NodeFator {
        public void visit(Visitor visitor) {
          visitor.visit_NodeFator(this);
        }
    }


    public static class NodeID {
        public String valor;
        public Token token;
      
        public NodeID(String valor, Token token) {
          this.valor = valor;
          this.token = token;
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeID(this);
        }
    }


    public static class NodeLiteral extends NodeFator {
        public String valor;
        public Type tipo;
      
        public NodeLiteral(String valor, Type tipo) {
          this.valor = valor;
          this.tipo = tipo;
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeLiteral(this);
        }
    }

    
    public abstract static class NodeOperador {
        public byte operador;
        public String valor;
      
        public NodeOperador(String valor, byte tokenDeOperador) {
          this.valor = valor;
          this.operador = tokenDeOperador;
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeOperador(this);
        }
    }


    public static class NodeOperadorAditivo extends NodeOperador {
        public NodeOperadorAditivo(String valor, byte tokenDeOperador) {
          super(valor, tokenDeOperador);
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeOperadorAditivo(this);
        }
    }


    public static class NodeOperadorMultiplicativo extends NodeOperador {

        public NodeOperadorMultiplicativo(String valor, byte tokenDeOperador) {
          super(valor, tokenDeOperador);
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeOperadorMultiplicativo(this);
        }
    }


    public static class NodeOperadorRelacional extends NodeOperador {

        public NodeOperadorRelacional(String valor, byte tokenDeOperador) {
          super(valor, tokenDeOperador);
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeOperadorRelacional(this);
        }
    }


    public static class NodePrograma {
        public NodeID id;
        public NodeCorpo corpo;
      
        public void visit(Visitor visitor) {
          visitor.visit_NodePrograma(this);
        }
    }


    public static class NodeTermo {
        public NodeFator fator;
      
        public ArrayList<NodeOperadorMultiplicativo> operadoresMultiplicativos;
        public ArrayList<NodeFator> fatores;
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeTermo(this);
        }
    }


    public static class NodeTipo {
        public NodeTipoSimples tipoSimples;
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeTipo(this);
        }
    }


    public static class NodeTipoSimples {
        public String tipo;
        public Type tipoType; 
      
        public NodeTipoSimples(String tipo, Type tipoType) {
          this.tipo = tipo;
          this.tipoType = tipoType;
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeTipoSimples(this);
        }
    }


    public static class NodeVariavel extends NodeFator {
        public NodeID ID;
        public Type tipo;
        public Token token;
      
        public NodeVariavel(NodeID ID, Token token) {
          this.ID = ID;
          this.token = token;
        }
      
        public void visit(Visitor visitor) {
          visitor.visit_NodeVariavel(this);
        }
    }

}
