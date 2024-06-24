
public interface Visitor {
  public void visit_NodeComando(Node.NodeComando comando);
  public void visit_NodeComandoAtribuicao(Node.NodeComandoAtribuicao comando);
  public void visit_NodeComandoComposto(Node.NodeComandoComposto comando);
  public void visit_NodeComandoCondicional(Node.NodeComandoCondicional comando);
  public void visit_NodeComandoIterativo(Node.NodeComandoIterativo comando);
  public void visit_NodeCorpo(Node.NodeCorpo corpo);
  public void visit_NodeDeclaracao(Node.NodeDeclaracao declaracao);
  public void visit_NodeDeclaracaoDeVariavel(Node.NodeDeclaracaoDeVariavel declaracao);
  public void visit_NodeDeclaracoes(Node.NodeDeclaracoes declaracoes);
  public void visit_NodeExpressao(Node.NodeExpressao expressao);
  public void visit_NodeExpressaoSimples(Node.NodeExpressaoSimples expressao);
  public void visit_NodeFator(Node.NodeFator fator);
  public void visit_NodeID(Node.NodeID ID);
  public void visit_NodeLiteral(Node.NodeLiteral literal);
  public void visit_NodeOperador(Node.NodeOperador operador);
  public void visit_NodeOperadorAditivo(Node.NodeOperadorAditivo operador);
  public void visit_NodeOperadorMultiplicativo(Node.NodeOperadorMultiplicativo operador);
  public void visit_NodeOperadorRelacional(Node.NodeOperadorRelacional operador);
  public void visit_NodePrograma(Node.NodePrograma programa);
  public void visit_NodeTermo(Node.NodeTermo termo);
  public void visit_NodeTipo(Node.NodeTipo tipo);
  public void visit_NodeTipoSimples(Node.NodeTipoSimples tipoSimples);
  public void visit_NodeVariavel(Node.NodeVariavel variavel);
}
