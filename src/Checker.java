public class Checker implements Visitor {

    public IdentificationTable identificationTable = new IdentificationTable();

    public void check(Node.NodePrograma programa) {
        System.out.println("");
        programa.visit(this);
    }

    @Override
    public void visit_NodeComando(Node.NodeComando comando) {
        if (comando != null)
            comando.visit(this);
    }

    @Override
    public void visit_NodeComandoAtribuicao(Node.NodeComandoAtribuicao comando) {
        if (comando != null) {

            Attribute atributo = identificationTable.retrieve(comando.variavel.ID.valor);
            if (atributo != null) {
                comando.declaracaoDeVariavel = atributo.declaracaoDeVariavel;
            } else {
                // Erro variável não declarada
                new Erro(
                    "A variável "+ "\"" + 
                comando.variavel.ID.valor + "\"" + " não foi declarada\n" 
                + "Linha: " +  comando.variavel.token.linha + "\""
                + " Coluna: " + comando.variavel.token.coluna
                );
                
            }

            Type tipoExpressao = getType_NodeExpressao(comando.expressao);

            if (comando.variavel != null) {
                if (!tipoExpressao.equals(atributo.tipo)) {
                    new Erro("Tipo de atribuição inválida: "
                        + comando.token.grafia + "... \n"
                        + "Linha: " + comando.token.linha + " Coluna: " + comando.token.coluna 
                    );
                }
            } else {
                new Erro("A variável desse comando está com valor NULL\n");
            }
        }
    }

    @Override
    public void visit_NodeComandoComposto(Node.NodeComandoComposto comando) {
        if (comando != null) {
            if (comando.comandos != null) {
                for (int i = 0; i < comando.comandos.size(); i++) {
                    comando.comandos.get(i).visit(this);
                }
            }
        }
    }

    @Override
    public void visit_NodeComandoCondicional(Node.NodeComandoCondicional comando) {
        if (comando != null) {
            // Verificar se a expressão é booleana
            if (
                comando.expressao != null && 
                this.getType_NodeExpressao(comando.expressao).kind == Type.BOOL
            ) {
                if (comando.comando1 != null) {
                    comando.comando1.visit(this);
                }
                if (comando.comando2 != null) {
                    comando.comando2.visit(this);
                }
            } else {
                new Erro("A expressão do comando condicional deveria ser do tipo BOOLEAN\n" +
                "Linha: " + comando.expressao.token.linha + " Coluna: " + comando.expressao.token.coluna);
            }
        }
    }

   
    public void visit_NodeComandoIterativo(Node.NodeComandoIterativo comando) {
        if (comando != null) {
            // Verificar se a expressão é booleana
            if (comando.expressao != null) {
                Type tipo = this.getType_NodeExpressao(comando.expressao);

                if(tipo.kind == Type.BOOL) {
                    if (comando.comando != null) {
                        comando.comando.visit(this);
                    }
                } else {
                    new Erro("A expressão deveria ser do tipo BOOLEAN\n"+ 
                        "Linha: " + comando.expressao.token.linha + " Coluna: " +
                        comando.expressao.token.coluna
                    );
                }
            }
        }
    }

    @Override
    public void visit_NodeCorpo(Node.NodeCorpo corpo) {
        if (corpo != null) {
            corpo.declaracoes.visit(this);
            corpo.comandoComposto.visit(this);
        }
    }

    @Override
    public void visit_NodeDeclaracao(Node.NodeDeclaracao declaracao) {
        if (declaracao != null) {
            declaracao.declaracaoDeVariavel.visit(this);
        }
    }

    @Override
    public void visit_NodeDeclaracaoDeVariavel(Node.NodeDeclaracaoDeVariavel declaracao) {
        for (int i = 0; i < declaracao.IDs.size(); i++) {
            if (identificationTable.retrieve(declaracao.IDs.get(i).valor) == null) {

                identificationTable.enter(
                        declaracao.IDs.get(i).valor,
                        declaracao.tipo.tipoSimples.tipoType,
                        declaracao);
                declaracao.IDs.get(i).visit(this);
            } else {
                // Erro variavel já declarada
                new Erro(
                    "Variável \"" + declaracao.IDs.get(i).token.grafia + "\" já declarada\n" + 
                    "Linha: " + declaracao.IDs.get(i).token.linha + " Coluna: " 
                    + declaracao.IDs.get(i).token.coluna
                
                );
            }
        }
    }

    @Override
    public void visit_NodeDeclaracoes(Node.NodeDeclaracoes declaracoes) {
        if (declaracoes != null) {
            for (int i = 0; i < declaracoes.declaracoes.size(); i++) {
                declaracoes.declaracoes.get(i).visit(this);
            }
        }
    }

    @Override
    public void visit_NodeExpressao(Node.NodeExpressao expressao) {

        if (expressao != null) {
            if (expressao.expressaoSimples1 != null) {
                expressao.expressaoSimples1.visit(this);
            }
            if (expressao.operadorRelacional != null) {
                expressao.operadorRelacional.visit(this);
            }
            if (expressao.expressaoSimples2 != null) {
                expressao.expressaoSimples2.visit(this);
            }
        }
    }

    @Override
    public void visit_NodeExpressaoSimples(Node.NodeExpressaoSimples expressao) {
        if (expressao != null) {
            if (expressao.termo != null) {
                expressao.termo.visit(this);
            }
            if (expressao.operadoresAditivos != null) {
                for (int i = 0; i < expressao.operadoresAditivos.size(); i++) {
                    if (expressao.operadoresAditivos.get(i) != null) {
                        expressao.operadoresAditivos.get(i).visit(this);
                        expressao.termos.get(i).visit(this);
                    }
                }
            }
        }
    }

    @Override
    public void visit_NodeFator(Node.NodeFator fator) {
        if (fator instanceof Node.NodeVariavel) {
            ((Node.NodeVariavel) fator).visit(this);
        } else if (fator instanceof Node.NodeLiteral) {
            ((Node.NodeLiteral) fator).visit(this);
        } else if (fator instanceof Node.NodeExpressao) {
            ((Node.NodeExpressao) fator).visit(this);
        }
    }

    @Override
    public void visit_NodeID(Node.NodeID ID) {
        if (ID != null && identificationTable.retrieve(ID.valor) != null) {
            return;
        }
    }

    @Override
    public void visit_NodeLiteral(Node.NodeLiteral literal) {
        if (literal != null) {
        }
        return;
    }

    @Override
    public void visit_NodeOperador(Node.NodeOperador operador) {
        if (operador != null) {
            return;
        }
    }

    @Override
    public void visit_NodeOperadorAditivo(Node.NodeOperadorAditivo operador) {
        if (operador != null) {
            return;
        }
    }

    @Override
    public void visit_NodeOperadorMultiplicativo(Node.NodeOperadorMultiplicativo operador) {
        if (operador != null) {
            return;
        }
    }

    @Override
    public void visit_NodeOperadorRelacional(Node.NodeOperadorRelacional operador) {
        if (operador != null) {
            return;
        }
    }

    @Override
    public void visit_NodePrograma(Node.NodePrograma programa) {
        if (programa != null) {
            programa.corpo.visit(this);
        }
    }

    @Override
    public void visit_NodeTermo(Node.NodeTermo termo) {
        if (termo != null) {
            termo.fator.visit(this);
        }
    }

    @Override
    public void visit_NodeTipo(Node.NodeTipo tipo) {
        if (tipo != null) {
            tipo.visit(this);
        }
    }

    @Override
    public void visit_NodeTipoSimples(Node.NodeTipoSimples tipoSimples) {
        if (tipoSimples != null) {
            tipoSimples.visit(this);
        }
    }

    @Override
    public void visit_NodeVariavel(Node.NodeVariavel variavel) {
        if (variavel != null) {
            variavel.ID.visit(this);
        }

    }

    public Type getType_NodeExpressao(Node.NodeExpressao expressao) {
        Type typeExpresaoSimples1, typeExpresaoSimples2;
        Type type = null;

        if (expressao != null) {
            if (expressao.expressaoSimples1 != null) {
                typeExpresaoSimples1 = getType_NodeExpressaoSimples(expressao.expressaoSimples1);
                type = typeExpresaoSimples1;

                if (expressao.expressaoSimples2 != null) {
                    typeExpresaoSimples2 = getType_NodeExpressaoSimples(expressao.expressaoSimples2);

                    type = Type.evaluate(
                        typeExpresaoSimples1, typeExpresaoSimples2, 
                        expressao.operadorRelacional
                    );
                }
                return type;
            }
        }
        return type;
    }

    public Type getType_NodeExpressaoSimples(Node.NodeExpressaoSimples expressaoSimples) {
        Type type = null;
        if (expressaoSimples != null) {

            if (expressaoSimples.termo != null && expressaoSimples.operadoresAditivos.isEmpty()) {
                type = getType_NodeTermo(expressaoSimples.termo);
            } else {
                Type tipoResultado = null;
                Type tipoTermoAnterior = getType_NodeTermo(expressaoSimples.termo);

                for (int i = 0; i < expressaoSimples.termos.size(); i++) {
                    Node.NodeTermo termo = expressaoSimples.termos.get(i);
                    Type tipoTermo = getType_NodeTermo(termo);

                    Node.NodeOperador operador = expressaoSimples.operadoresAditivos.get(i);

                    tipoTermoAnterior = Type.evaluate(tipoTermoAnterior, tipoTermo, operador);
                }
                tipoResultado = tipoTermoAnterior;
                type = tipoResultado;
            }  
        }
        
        return type;
    }

    public Type getType_NodeFator(Node.NodeFator fator) {
        Type type = null;
        if (fator instanceof Node.NodeVariavel) {
            type = getType_NodeVariavel((Node.NodeVariavel) fator);
        } else if (fator instanceof Node.NodeLiteral) {
            type = getType_NodeLiteral((Node.NodeLiteral) fator);
        } else if (fator instanceof Node.NodeExpressao) {
            type = getType_NodeExpressao((Node.NodeExpressao) fator);
        }

        return type;
    }

    public Type getType_NodeLiteral(Node.NodeLiteral literal) {
        Type type = null;
        if (literal != null) {
            type = literal.tipo;
        }
        return type;
    }

    public Type getType_NodeTermo(Node.NodeTermo termo) {
        Type type = null;
        if (termo != null) {

            if (termo.fator != null && termo.fatores.isEmpty()) {
                type = getType_NodeFator(termo.fator);
            } else {
                Type tipoResultado = null;
                Type tipoFatorAnterior = getType_NodeFator(termo.fator);

                for (int i = 0; i < termo.fatores.size(); i++) {
                    Node.NodeFator fator = termo.fatores.get(i);

                    Type tipoFator = getType_NodeFator(fator);
                    Node.NodeOperador operador = termo.operadoresMultiplicativos.get(i);

                    tipoFatorAnterior = Type.evaluate(tipoFatorAnterior, tipoFator, operador);
                }
                tipoResultado = tipoFatorAnterior;
                type = tipoResultado;
            }
        }
        return type;
    }

    public Type getType_NodeVariavel(Node.NodeVariavel variavel) {
        Type type = null;

        if (variavel != null && variavel.ID != null) {
            Attribute atributo = identificationTable.retrieve(variavel.ID.valor);
            if (atributo != null) {
                type = atributo.tipo;
            } else {
                new Erro(
                    "Variável \"" + variavel.token.grafia + "\" + não declarada\n" + 
                    "Linha: " + variavel.token.linha + " Coluna: " 
                    + variavel.token.coluna
                
                );
            }
        }
        return type;
    }
}