public class Printer implements Visitor {

  int indentacao = 0;
  int tabulacao = 2;

  String stringDeindentacao = "| ";

  void indent() {
    for (int j = 0; j < indentacao; j++)
      System.out.print(stringDeindentacao);
  }

  public void print(Node.NodePrograma programa) {
    System.out.println("---> Impressão da árvore");
    programa.visit(this);
  }

  // Implementações de Printagem
  public void visit_NodeComando(Node.NodeComando comando) {
    if (comando != null) {
      if (comando instanceof Node.NodeComandoAtribuicao) {
        ((Node.NodeComandoAtribuicao) comando).visit(this);
      } else if (comando instanceof Node.NodeComandoCondicional) {
        ((Node.NodeComandoCondicional) comando).visit(this);
      } else if (comando instanceof Node.NodeComandoIterativo) {
        ((Node.NodeComandoIterativo) comando).visit(this);
      } else if (comando instanceof Node.NodeComandoComposto) {
        ((Node.NodeComandoComposto) comando).visit(this);
      }
    }
  }

  public void visit_NodeComandoAtribuicao(Node.NodeComandoAtribuicao comando) {
    if (comando != null) {
      if (comando.variavel != null) {
        comando.variavel.ID.visit(this);
      }
      if (comando.expressao != null) {
        comando.expressao.visit(this);
      }
    }
  }

  public void visit_NodeComandoComposto(Node.NodeComandoComposto comando) {
    if (comando != null) {
      if (comando.comandos != null) {
        for (int i = 0; i < comando.comandos.size(); i++) {
          comando.comandos.get(i).visit(this);
        }
      }
    }
  }

  public void visit_NodeComandoCondicional(Node.NodeComandoCondicional comando) {
  }

  public void visit_NodeComandoIterativo(Node.NodeComandoIterativo comando) {
  }

  public void visit_NodeCorpo(Node.NodeCorpo corpo) {
    if (corpo != null) {
      if (corpo.declaracoes != null) {
        this.indent();
        System.out.println("Declaracoes:");
        indentacao += tabulacao;
        corpo.declaracoes.visit(this);
        indentacao -= tabulacao;
      }
      if (corpo.comandoComposto != null) {
        this.indent();
        System.out.println("Comando composto:");
        indentacao += tabulacao;
        corpo.comandoComposto.visit(this);
        indentacao -= tabulacao;
      }
    }
  }

  public void visit_NodeDeclaracao(Node.NodeDeclaracao declaracao) {
    if (declaracao != null) {
      if (declaracao.declaracaoDeVariavel != null) {
        this.indent();
        System.out.println("Declaracao:");
        declaracao.declaracaoDeVariavel.visit(this);
      }
    }
  }

  public void visit_NodeDeclaracaoDeVariavel(Node.NodeDeclaracaoDeVariavel declaracao) {
    if (declaracao != null) {
      this.indent();
      System.out.println("Declaracao de Variavel:");
      indentacao += tabulacao;
      if (declaracao.IDs != null) {
        for (int i = 0; i < declaracao.IDs.size(); i++) {
          declaracao.IDs.get(i).visit(this);
        }
      }
      if (declaracao.tipo != null) {
        this.indent();
        System.out.println("Tipo:");
        declaracao.tipo.visit(this);
      }
      indentacao -= tabulacao;
    }
  }

  public void visit_NodeDeclaracoes(Node.NodeDeclaracoes declaracoes) {
    if (declaracoes != null) {
      if (declaracoes.declaracoes != null) {
        indentacao += tabulacao;
        this.indent();
        System.out.println("Declarações:");
        for (int i = 0; i < declaracoes.declaracoes.size(); i++) {
          this.indent();
          System.out.println("Declaração " + (i + 1) + ":");
          declaracoes.declaracoes.get(i).visit(this);
        }
        indentacao -= tabulacao;
      }
    }
  }

  public void visit_NodeExpressao(Node.NodeExpressao expressao) {
    if (expressao != null) {
      if (expressao.expressaoSimples1 != null) {
        this.indent();
        System.out.println("Expressão Simples 1:");
        expressao.expressaoSimples1.visit(this);
      }
      if (expressao.operadorRelacional != null) {
        this.indent();
        System.out.println("Operador Relacional:");
        expressao.operadorRelacional.visit(this);
      }
      if (expressao.expressaoSimples2 != null) {
        this.indent();
        System.out.println("Expressão Simples 2:");
        expressao.expressaoSimples2.visit(this);
      }
    }
  }

  public void visit_NodeExpressaoSimples(Node.NodeExpressaoSimples expressaoSimples) {
    if (expressaoSimples != null) {
      if (expressaoSimples.termo != null) {
        this.indent();
        System.out.println("Termo:");
        expressaoSimples.termo.visit(this);
      }
      if (expressaoSimples.operadoresAditivos != null && expressaoSimples.termos != null) {
        for (int i = 0; i < expressaoSimples.operadoresAditivos.size(); i++) {
          this.indent();
          System.out.println("Operador Aditivo:");
          expressaoSimples.operadoresAditivos.get(i).visit(this);

          this.indent();
          System.out.println("Termo:");
          expressaoSimples.termos.get(i).visit(this);
        }
      }
    }
  }

  public void visit_NodeFator(Node.NodeFator fator) {
    if (fator != null) {
      if (fator instanceof Node.NodeVariavel) {
        this.indent();
        System.out.println("Variável:");
        ((Node.NodeVariavel) fator).visit(this);
      } else if (fator instanceof Node.NodeLiteral) {
        this.indent();
        System.out.println("Literal:");
        ((Node.NodeLiteral) fator).visit(this);
      } else if (fator instanceof Node.NodeExpressao) {
        this.indent();
        System.out.println("Expressão:");
        ((Node.NodeExpressao) fator).visit(this);
      }
    }
  }

  public void visit_NodeID(Node.NodeID ID) {
    if (ID != null) {
      this.indent();
      System.out.println(ID.valor);
    }
  }

  public void visit_NodeLiteral(Node.NodeLiteral literal) {
    if (literal != null) {
      this.indent();
      System.out.println(literal.valor);
    }
  }

  public void visit_NodeOperador(Node.NodeOperador operador) {
    if (operador != null) {
      indentacao += tabulacao;
      this.indent();
      System.out.println(operador.valor);
      indentacao -= tabulacao;
    }
  }

  public void visit_NodeOperadorAditivo(Node.NodeOperadorAditivo operador) {
    if (operador != null) {
      indentacao += tabulacao;
      this.indent();
      System.out.println(operador.valor);
      indentacao -= tabulacao;
    }
  }

  public void visit_NodeOperadorMultiplicativo(Node.NodeOperadorMultiplicativo operador) {
    if (operador != null) {
      indentacao += tabulacao;
      this.indent();
      System.out.println(operador.valor);
      indentacao -= tabulacao;
    }
  }

  public void visit_NodeOperadorRelacional(Node.NodeOperadorRelacional operador) {
    if (operador != null) {
      indentacao += tabulacao;
      this.indent();
      System.out.println(operador.valor);
      indentacao -= tabulacao;
    }
  }

  public void visit_NodePrograma(Node.NodePrograma programa) {
    if (programa != null) {
      this.indent();
      System.out.println("Programa:");

      if (programa.id != null) {
        this.indent();
        System.out.print("ID: ");
        programa.id.visit(this);
      }
      indentacao += tabulacao;

      if (programa.corpo != null) {
        this.indent();
        System.out.println("Corpo:");
        programa.corpo.visit(this);
      }

      indentacao -= tabulacao;
    }
  }

  public void visit_NodeTermo(Node.NodeTermo termo) {
    indentacao += tabulacao;
    if (termo != null) {
      if (termo.fator != null) {
        termo.fator.visit(this);
      }
      if (termo.operadoresMultiplicativos != null && termo.fatores != null) {
        for (int i = 0; i < termo.operadoresMultiplicativos.size(); i++) {
          termo.operadoresMultiplicativos.get(i).visit(this);
          termo.fatores.get(i).visit(this);
        }
      }
    }
    indentacao -= tabulacao;
  }

  public void visit_NodeTipo(Node.NodeTipo tipo) {
    if (tipo != null) {
      if (tipo.tipoSimples != null) {
        tipo.tipoSimples.visit(this);
      }
    }
  }

  public void visit_NodeTipoSimples(Node.NodeTipoSimples tipoSimples) {
    indentacao += tabulacao;
    this.indent();
    System.out.println(tipoSimples.tipo);
    indentacao -= tabulacao;
  }

  public void visit_NodeVariavel(Node.NodeVariavel variavel) {
    if (variavel != null) {
      variavel.ID.visit(this);
    }
  }
  
  public Type getType_NodeExpressao(Node.NodeExpressao expressao) {
    return null;
  }
  public Type getType_NodeExpressaoSimples(Node.NodeExpressaoSimples expressao) {
    return null;
  }
  public Type getType_NodeFator(Node.NodeFator fator) {
    return null;
  }
  public Type getType_NodeLiteral(Node.NodeLiteral literal) {
    return null;
  }
  public Type getType_NodeTermo(Node.NodeTermo termo) {
    return null;
  }
  public Type getType_NodeVariavel(Node.NodeVariavel variavel) {
    return null;
  }

}