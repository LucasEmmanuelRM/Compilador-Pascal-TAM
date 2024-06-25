public class Attribute {
  public String identifier;
  public Type tipo;
  public Node.NodeDeclaracaoDeVariavel declaracaoDeVariavel;

  public Attribute(String identifier, Type tipo, Node.NodeDeclaracaoDeVariavel declaracaoDeVariavel) {
    this.identifier = identifier;
    this.tipo = tipo;
    this.declaracaoDeVariavel = declaracaoDeVariavel;
  }
}